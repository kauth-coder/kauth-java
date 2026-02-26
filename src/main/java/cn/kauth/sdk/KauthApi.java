package cn.kauth.sdk;

import cn.kauth.sdk.enums.PongFailEnums;
import cn.kauth.sdk.handler.RequestInterceptor;
import cn.kauth.sdk.handler.ResponseInterceptor;
import cn.kauth.sdk.info.ServiceConfig;
import cn.kauth.sdk.info.request.InitConfigReq;
import cn.kauth.sdk.info.response.ApiResult;
import cn.kauth.sdk.info.response.LoginResponse;
import cn.kauth.sdk.interfaces.PongCallback;
import cn.kauth.sdk.tools.SessionManager;
import cn.kauth.sdk.tools.StringUtils;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class KauthApi {


    private static ExecutorService executorService;

    private static KauthApiService kauthApiService;


    /**
     * 服务配置
     */
    private static ServiceConfig serviceConfig = new ServiceConfig();

    public static ServiceConfig getServiceConfig() {
        return serviceConfig;
    }

    public static KauthApiService getKauthApiService() {
        return kauthApiService;
    }

    /**
     * 初始化Kauth API
     *
     * @param baseUrl API基础URL
     */
    public static void initKauthApi(String baseUrl) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new RequestInterceptor())
                .addInterceptor(new ResponseInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        kauthApiService = retrofit.create(KauthApiService.class);
    }


    public static void init(InitConfigReq initConfigReq) {
        initConfig(initConfigReq);
        initKauthApi(initConfigReq.getApiDomain());
    }


    private static String initConfig(InitConfigReq initConfigReq) {
        if (Objects.isNull(initConfigReq)) {
            return "fail:参数不能为空";
        }
        serviceConfig.setApiDomain(initConfigReq.getApiDomain());
        serviceConfig.setProgramId(initConfigReq.getProgramId());
        serviceConfig.setProgramSecret(initConfigReq.getProgramSecret());
        serviceConfig.setMerchantPublicKey(initConfigReq.getMerchantPublicKey());
        serviceConfig.setKauthSignEnums(initConfigReq.getSignType());
        if (StringUtils.isBlank(serviceConfig.getApiDomain())) {
            serviceConfig.setApiDomain("https://api.kauth.cn");
        }
        if (Objects.isNull(serviceConfig.getProgramId())) {
            return "fail:programId 不能为空";
        }
        if (StringUtils.isBlank(serviceConfig.getMerchantPublicKey())) {
            return "fail:merchantPublicKey 不能为空";
        }
        if (StringUtils.isBlank(serviceConfig.getProgramSecret())) {
            return "fail:programSecret 程序密钥不能为空";
        }

        return "ok";
    }


    /**
     * 启动自动心跳功能
     *
     * @param maxConnFail:  允许最大失败次数（比如网络抖动等可能会发生心跳失败，你可以输入一个你能接受的最大失败次数，比如 10次）
     * @param pongCallback: 心跳失败的回调钩子
     */
    public static void startAutoPong(int maxConnFail, PongCallback pongCallback) {
        if (Objects.nonNull(executorService)) {
            executorService.shutdown();
        }
        executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                int currentConnFail = 0;
                do {
                    LoginResponse userInfo = SessionManager.getUserInfo();
                    if (Objects.isNull(userInfo)) {
                        pongCallback.callback(PongFailEnums.INVALID_LOGIN, "登录已失效，请尝试重新登录！");
                        return;
                    }
                    Call<ApiResult<Void>> pong = KauthApi.getKauthApiService().pong();
                    try {
                        Response<ApiResult<Void>> execute = pong.execute();
                        ApiResult<Void> pongApiResult = execute.body();
                        if (Objects.isNull(pongApiResult)) {
                            currentConnFail++;
                        } else if (Objects.equals(pongApiResult.getCode(), 200)) {
                            currentConnFail = 0;
                        } else if (pongApiResult.getCode() < 200) {
                            currentConnFail++;
                        } else if (Objects.equals(pongApiResult.getCode(), 1050)) {
                            pongCallback.callback(PongFailEnums.INVALID_LOGIN, pongApiResult.getMsg());
                            return;
                        } else if (pongApiResult.getCode() > 200) {
                            pongCallback.callback(PongFailEnums.OTHER_FAIL, pongApiResult.getMsg());
                            return;
                        }
                        if (currentConnFail >= maxConnFail) {
                            pongCallback.callback(PongFailEnums.MAXFAIL_CONNECTION, "心跳失败已达最大次数");
                            return;
                        }
                    } catch (Exception exception) {
                        currentConnFail++;
                    }
                    try {
                        Thread.sleep(userInfo.getPongInterval());
                    } catch (InterruptedException e) {

                    }
                } while (!Thread.interrupted());
            }
        });
    }

}
