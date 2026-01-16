package cn.kauth.sdk;

import cn.kauth.sdk.info.request.*;
import cn.kauth.sdk.info.response.*;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface KauthApiService {

    // ==================== 用户认证模块 ====================

    /**
     * 获取图形验证码
     */
    @POST("/api/consumer/user/captcha")
    Call<ApiResult<CaptchaResponse>> getCaptcha(@Body CaptchaRequest captchaRequest);

    /**
     * 用户密码登录
     */
    @POST("/api/consumer/user/pwdlogin")
    Call<ApiResult<LoginResponse>> pwdLogin(@Body PwdLoginRequest pwdLoginRequest);

    /**
     * 通过卡密进行登录
     */
    @POST("/api/consumer/user/kaLogin")
    Call<ApiResult<LoginResponse>> kaLogin(@Body KaLoginRequest kaLoginRequest);

    /**
     * 退出登录
     */
    @POST("/api/consumer/user/loginOut")
    Call<ApiResult<Void>> loginOut();

    /**
     * 试用登录
     */
    @POST("/api/consumer/user/trialLogin")
    Call<ApiResult<LoginResponse>> trialLogin(@Body TrialLoginReq trialLoginReq);

    /**
     * 用户注册
     */
    @POST("/api/consumer/user/register")
    Call<ApiResult<RegisterResponse>> register(@Body RegisterRequest registerRequest);

    /**
     * 账号充值
     */
    @POST("/api/consumer/user/recharge")
    Call<ApiResult<Void>> recharge(@Body RechargeRequest rechargeRequest);

    /**
     * 以卡充卡
     */
    @POST("/api/consumer/user/rechargeKa")
    Call<ApiResult<Void>> rechargeKa(@Body KaRechargeKaReq rechargeKaRequest);

    /**
     * 修改密码
     */
    @POST("/api/consumer/user/changePassword")
    Call<ApiResult<Void>> changePassword(@Body ResetPwdRequest resetPwdRequest);

    /**
     * 解绑设备
     */
    @POST("/api/consumer/user/unbindDevice")
    Call<ApiResult<Void>> unbindDevice(@Body UnbindDeviceRequest unbindDeviceRequest);

    /**
     * 未登录状态下，卡密解绑设备
     */
    @POST("/api/consumer/user/unbindDeviceKaPwd")
    Call<ApiResult<Void>> unbindDeviceKaPwd(@Body UnbindDeviceKaPwdRequest unbindDeviceKaPwdRequest);

    /**
     * 获取用户信息
     */
    @POST("/api/consumer/user/userInfo")
    Call<ApiResult<UserInfo>> userInfo();

    /**
     * 心跳
     */
    @POST("/api/consumer/user/pong")
    Call<ApiResult<Void>> pong();

    // ==================== 配置管理模块 ====================

    /**
     * 更新用户配置
     */
    @POST("/api/consumer/custom/config/updateUserConfig")
    Call<ApiResult<Void>> updateUserConfig(@Body UpdateCustomConfigReq updateCustomConfigReq);

    /**
     * 获取用户配置
     */
    @POST("/api/consumer/custom/config/getUserConfig")
    Call<ApiResult<GetCustomConfigResp>> getUserConfig();

    /**
     * 更新卡密配置
     */
    @POST("/api/consumer/custom/config/updateKaConfig")
    Call<ApiResult<Void>> updateKaConfig(@Body UpdateCustomConfigReq updateCustomConfigReq);

    /**
     * 获取卡密配置
     */
    @POST("/api/consumer/custom/config/getKaConfig")
    Call<ApiResult<GetCustomConfigResp>> getKaConfig();

    // ==================== 程序管理模块 ====================

    /**
     * 获取程序详情
     */
    @POST("/api/consumer/program/detail")
    Call<ApiResult<ProgramDetailResponse>> getProgramDetail();

    /**
     * 获取服务器时间
     */
    @POST("/api/consumer/program/serverTime")
    Call<ApiResult<ServerTimeResp>> getServerTime();

    // ==================== 脚本错误模块 ====================

    /**
     * 报告脚本错误
     */
    @POST("/api/consumer/scriptError/report")
    Call<ApiResult<Void>> reportScriptError(@Body ConsumerProgramScriptErrorReportReq scriptErrorReportReq);

    // ==================== 设备管理模块 ====================

    /**
     * 卡密解绑设备
     */
    @POST("/api/consumer/device/cardUnBindDevice")
    Call<ApiResult<Void>> cardUnBindDevice();

    // ==================== 远程控制模块 ====================

    /**
     * 获取远程变量
     */
    @POST("/api/remote/getRemoteVar")
    Call<ApiResult<RemoteNormalVarResp>> getRemoteVar(@Body GetRemoteVarReq getRemoteVarReq);

    /**
     * 获取远程数据
     */
    @POST("/api/remote/getRemoteData")
    Call<ApiResult<RemoteNormalVarResp>> getRemoteData(@Body GetRemoteVarReq getRemoteVarReq);

    /**
     * 添加远程数据
     */
    @POST("/api/remote/addRemoteData")
    Call<ApiResult<Void>> addRemoteData(@Body RemoteDataAddReq remoteDataAddReq);

    /**
     * 更新远程数据
     */
    @POST("/api/remote/updateRemoteData")
    Call<ApiResult<Void>> updateRemoteData(@Body RemoteDataUpdateReq remoteDataUpdateReq);

    /**
     * 删除远程数据
     */
    @POST("/api/remote/deleteRemoteData")
    Call<ApiResult<Void>> deleteRemoteData(@Body RemoteDataDeleteReq remoteDataDeleteReq);

    /**
     * 调用函数
     */
    @POST("/api/remote/callFunction")
    Call<ApiResult<CallFunctionResp>> callFunction(@Body CallFunctionReq callFunctionReq);

    /**
     * 获取最新脚本
     */
    @POST("/api/remote/getNewestScript")
    Call<ApiResult<GetNewestScriptResp>> getNewestScript(@Body GetNewestScriptReq getNewestScriptReq);


    /**
     * 脚本下载V2
     */
    @POST("/api/remote/scriptDownloadV2")
    Call<ApiResult<RemoteScriptDownloadResp>> scriptDownloadV2(@Body ScriptDownloadReq scriptDownloadReq);

}
