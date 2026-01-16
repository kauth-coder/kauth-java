package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * 用户密码登录请求
 *
 * @author SongLongKuan
 * @date 2025/4/4
 */
@Data
public class PwdLoginRequest {
    
    /**
     * 登录账号
     */
    private String loginName;
    
    /**
     * 登录密码
     */
    private String password;
    
    /**
     * 图形验证码(可选)
     */
    private String captchaCode;
    
    /**
     * 图形验证码UUID(可选)
     */
    private String captchaUuid;

    /**
     * 设备ID
     */
    private String deviceId;
}
