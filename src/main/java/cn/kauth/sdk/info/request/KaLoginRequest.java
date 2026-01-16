package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * 卡密登录请求
 */
@Data
public class KaLoginRequest {

    /**
     * 卡密
     */
    private String kaPwd;

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

    /**
     * 平台类型
     */
    private String platformType;

}
