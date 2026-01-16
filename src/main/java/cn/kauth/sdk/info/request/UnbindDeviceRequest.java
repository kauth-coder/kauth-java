package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-08-20
 * @Description: 解绑设备请求（账密方式）
 */
@Data
public class UnbindDeviceRequest {
    
    /**
     * 登录账号，4-20位字母、数字或下划线
     */
    private String loginName;

    /**
     * 登录密码，6-20位字母、数字或特殊字符
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
