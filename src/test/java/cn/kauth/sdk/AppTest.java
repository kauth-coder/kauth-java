package cn.kauth.sdk;

import cn.kauth.sdk.enums.KauthSignEnums;
import cn.kauth.sdk.info.request.InitConfigReq;
import cn.kauth.sdk.info.request.KaLoginRequest;
import cn.kauth.sdk.info.response.ApiResult;
import cn.kauth.sdk.info.response.LoginResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.TestCase;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

/**
 *
 */
public class AppTest extends TestCase {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void testApp() throws IOException {
        //  初始化开始---------------
        InitConfigReq initConfigReq = new InitConfigReq();
        initConfigReq.setApiDomain("https://api.kauth.cn");
        initConfigReq.setProgramId(1959821336266936321L);
        initConfigReq.setMerchantPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCIpy3ae27yDJOUd5rW/S6tUbAmt/AqJm+VPonT9WJn5VME4FkYJUwdBmIWpzANVQmU+7CA3wv5eVFIOW0xMv9EyoFWDRR24Jt/hgDsZQtUPMaZPivKWxx2S4n4SJWWrGdIRkdC3+fmxrEri1qYicq8PO7mDIrwPR2I0USoKKOFMwIDAQAB");
        initConfigReq.setProgramSecret("F77VzI7UWAElpWrz");
        initConfigReq.setSignType(KauthSignEnums.SIGN_TYPE_RSA);
        KauthApi.init(initConfigReq);
        //  初始化结束---------------





        KauthApiService kauthApiService = KauthApi.getKauthApiService();
        KaLoginRequest kaLoginRequest = new KaLoginRequest();
        kaLoginRequest.setKaPwd("g90el4rz");
        kaLoginRequest.setDeviceId("888888");
        kaLoginRequest.setPlatformType("java");
        Call<ApiResult<LoginResponse>> apiResultCall = kauthApiService.kaLogin(kaLoginRequest);
        ApiResult<LoginResponse> responseApiResult = apiResultCall.execute().body();
        System.out.println(MAPPER.writeValueAsString(responseApiResult));

    }
}
