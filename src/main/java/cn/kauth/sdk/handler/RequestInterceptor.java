package cn.kauth.sdk.handler;


import cn.kauth.sdk.KauthCoreCore;
import cn.kauth.sdk.info.ServiceConfig;
import cn.kauth.sdk.tools.AesUtil;
import cn.kauth.sdk.tools.SessionManager;
import cn.kauth.sdk.tools.SignTools;
import cn.kauth.sdk.tools.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import lombok.NonNull;
import okhttp3.*;
import okio.Buffer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * 请求拦截器
 * 负责添加请求头和加密请求体
 */
public class RequestInterceptor implements Interceptor {

    private static final String PROGRAM_ID_HEADER = "Program-Id";
    private static final String NONCE = "ka-nonce";
    private static final String TIME = "ka-time";
    private static final String SIGN_TYPE = "ka-sign-type";
    private static final String SIGN = "ka-sign";

    private final Gson gson;

    public RequestInterceptor() {
        this.gson = new Gson();
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        KauthCoreCore kauthCoreCore = KauthCoreCore.getInstance();
        ServiceConfig serviceConfig = kauthCoreCore.getServiceConfig();
        // 获取原始请求体
        String originalBody = "";
        if (originalRequest.body() != null) {
            Buffer buffer = new Buffer();
            originalRequest.body().writeTo(buffer);
            originalBody = buffer.readString(StandardCharsets.UTF_8);
        }
        // 处理请求体：格式化JSON并加密
        String encryptedBody = "";
        String plainBody = "";
        if (StringUtils.isNotBlank(originalBody)) {
            JsonObject dataJson = gson.fromJson(originalBody, JsonObject.class);
            plainBody = dataJson.toString();
            encryptedBody = AesUtil.encrypt(plainBody, serviceConfig.getProgramSecret());
        }
        // 生成签名参数
        String deviceId = kauthCoreCore.getDeviceId();
        String ka_nonce = deviceId + "_" + UUID.randomUUID().toString().replaceAll("-", "");
        long ka_time = System.currentTimeMillis();

        // 获取URL路径（用于签名）
        String url = originalRequest.url().encodedPath();

        // 生成签名
        String sign = SignTools.sign(serviceConfig, url, plainBody, ka_nonce, ka_time);
        if (StringUtils.isBlank(sign)) {
            throw new IOException("客户端签名生成失败");
        }
        // 创建加密后的请求体
        RequestBody newBody = null;
        if (StringUtils.isNotBlank(encryptedBody)) {
            newBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), encryptedBody);
        }

        // 构建新的请求，添加请求头
        Request.Builder requestBuilder = originalRequest.newBuilder()
                .header(PROGRAM_ID_HEADER, String.valueOf(serviceConfig.getProgramId()))
                .header(NONCE, ka_nonce)
                .header(TIME, String.valueOf(ka_time))
                .header(SIGN_TYPE, serviceConfig.getKauthSignEnums().getValue())
                .header(SIGN, sign);
        String token = SessionManager.getAccessToken();
        // 添加accesstoken（如果存在）
        if (StringUtils.isNotBlank(token)) {
            requestBuilder.header("accesstoken", token);
        }
        // 如果有请求体，替换为加密后的
        if (newBody != null) {
            requestBuilder.method(originalRequest.method(), newBody);
        }
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
}
