package cn.kauth.sdk.handler;


import cn.kauth.sdk.KauthCoreCore;
import cn.kauth.sdk.info.ServiceConfig;
import cn.kauth.sdk.info.response.ParseResult;
import cn.kauth.sdk.tools.AesUtil;
import cn.kauth.sdk.tools.SignTools;
import cn.kauth.sdk.tools.StringUtils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import lombok.NonNull;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.util.Objects;

/**
 * 响应拦截器
 * 负责验证服务器签名和解密响应体
 */
public class ResponseInterceptor implements Interceptor {

    private static final Long TIMESTAMP_RECENT = 120000L;
    private static final String NONCE = "ka-nonce";
    private static final String TIME = "ka-time";
    private static final String SIGN = "ka-sign";
    private static final String SIGN_TYPE = "ka-sign-type";

    private final Gson gson;

    public ResponseInterceptor() {
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());

        // 读取原始响应体
        String responseBodyString = originalResponse.body().string();

        // 解析响应
        ParseResult parseResult;
        try {
            parseResult = gson.fromJson(responseBodyString, ParseResult.class);
        } catch (JsonSyntaxException e) {
            throw new IOException("服务器返回的数据格式错误: " + e.getMessage());
        }

        // 如果请求不成功，直接返回原始响应
        if (!parseResult.getSuccess()) {
            return createResponse(originalResponse, responseBodyString);
        }

        // 获取响应头中的签名信息
        String responseNonce = originalResponse.header(NONCE);
        String responseTime = originalResponse.header(TIME);
        String responseSign = originalResponse.header(SIGN);
        String responseSignType = originalResponse.header(SIGN_TYPE);

        // 验证响应头完整性
        if (StringUtils.isBlank(responseNonce)) {
            throw new IOException("服务器的 responseNonce 数据是空的");
        }
        if (StringUtils.isBlank(responseTime)) {
            throw new IOException("服务器的 responseTime 数据是空的");
        }
        if (StringUtils.isBlank(responseSign)) {
            throw new IOException("服务器的 responseSign 数据是空的");
        }
        if (StringUtils.isBlank(responseSignType)) {
            throw new IOException("服务器的 responseSignType 数据是空的");
        }

        // 验证时间戳
        long serverTime;
        try {
            serverTime = Long.parseLong(responseTime);
        } catch (NumberFormatException e) {
            throw new IOException("服务器时间戳格式错误");
        }

        if (System.currentTimeMillis() - serverTime >= TIMESTAMP_RECENT) {
            throw new IOException("请求超时");
        }
        KauthCoreCore kauthCoreCore = KauthCoreCore.getInstance();
        ServiceConfig serviceConfig = kauthCoreCore.getServiceConfig();
        // 解密响应体
        String decryptedBody = null;
        if (StringUtils.isNotBlank(parseResult.getData())) {
            decryptedBody = AesUtil.decrypt(parseResult.getData(), serviceConfig.getProgramSecret());
            if (decryptedBody == null) {
                throw new IOException("响应体解密失败");
            }
        }

        // 验证服务器签名
        String url = chain.request().url().encodedPath();
        boolean verifyResponseSign = SignTools.verifyResponseSign(
                serviceConfig,
                url,
                decryptedBody,
                responseNonce,
                serverTime,
                responseSign
        );

        if (!verifyResponseSign) {
            throw new IOException("服务器签名验证失败");
        }

        // 构建新的响应体
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("msg", parseResult.getMsg());
        jsonObject.addProperty("code", parseResult.getCode());
        jsonObject.addProperty("traceId", parseResult.getTraceId());
        jsonObject.addProperty("elapse", parseResult.getElapse());
        jsonObject.addProperty("respTime", parseResult.getRespTime());
        jsonObject.addProperty("success", Objects.equals(200, parseResult.getCode()));

        // 添加解密后的数据
        if (StringUtils.isNotBlank(decryptedBody)) {
            try {
                JsonObject dataJson = gson.fromJson(decryptedBody, JsonObject.class);
                jsonObject.add("data", dataJson);
            } catch (JsonSyntaxException e) {
                throw new IOException("服务器返回data不是json字符串");
            }
        }

        String newResponseBody = jsonObject.toString();
        return createResponse(originalResponse, newResponseBody);
    }

    /**
     * 创建新的响应
     */
    private Response createResponse(Response originalResponse, String bodyString) {
        ResponseBody newBody = ResponseBody.create(MediaType.parse("application/json; charset=utf-8"), bodyString);
        return originalResponse.newBuilder()
                .body(newBody)
                .build();
    }
}
