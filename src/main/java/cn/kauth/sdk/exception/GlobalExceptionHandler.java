package cn.kauth.sdk.exception;

import cn.kauth.sdk.KauthApi;
import cn.kauth.sdk.KauthApiService;
import cn.kauth.sdk.info.request.ConsumerProgramScriptErrorReportReq;
import cn.kauth.sdk.info.response.ApiResult;
import retrofit2.Response;

import java.util.Objects;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {
    private final String appName;
    private final String deviceId;

    public GlobalExceptionHandler(String appName, String deviceId) {
        this.appName = appName;
        this.deviceId = deviceId;
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace();
        KauthApiService kauthApiService = KauthApi.getKauthApiService();
        if (Objects.isNull(kauthApiService)) {
            return;
        }
        ConsumerProgramScriptErrorReportReq consumerProgramScriptErrorReportReq = new ConsumerProgramScriptErrorReportReq();
        consumerProgramScriptErrorReportReq.setScriptName(appName);
        consumerProgramScriptErrorReportReq.setErrorMessage(e.getMessage());

        java.io.StringWriter sw = new java.io.StringWriter();
        java.io.PrintWriter pw = new java.io.PrintWriter(sw);
        e.printStackTrace(pw);
        consumerProgramScriptErrorReportReq.setStackTrace(sw.toString());

        StackTraceElement[] stackTrace = e.getStackTrace();
        if (stackTrace != null && stackTrace.length > 0) {
            consumerProgramScriptErrorReportReq.setLine(stackTrace[0].getLineNumber());
        }

        consumerProgramScriptErrorReportReq.setOs(System.getProperty("os.name"));
        consumerProgramScriptErrorReportReq.setOsVersion(System.getProperty("os.version"));

        consumerProgramScriptErrorReportReq.setDeviceId(deviceId);
        try {
            Response<ApiResult<Void>> execute = kauthApiService.reportScriptError(consumerProgramScriptErrorReportReq).execute();
            ApiResult<Void> voidApiResult = execute.body();
            if (Objects.nonNull(voidApiResult)) {

            }
        } catch (Exception ignored) {
        }
    }

}