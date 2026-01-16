package cn.kauth.sdk;

import cn.kauth.sdk.handler.RequestInterceptor;
import cn.kauth.sdk.handler.ResponseInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.concurrent.TimeUnit;

public class KAuthApi {

    private static KauthApiService kauthApiService;

    public static KauthApiService getKauthApiService() {
        return kauthApiService;
    }

    /**
     * 初始化Kauth API
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

}
