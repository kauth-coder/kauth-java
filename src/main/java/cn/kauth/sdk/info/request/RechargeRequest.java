package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-08-19
 * @Description: 账户充值请求
 */
@Data
public class RechargeRequest {

    /**
     * 登录账号，4-20位字母、数字或下划线
     */
    private String loginName;

    /**
     * 充值卡密
     */
    private String kaPassword;

    /**
     * 设备ID
     */
    private String deviceId;

    /**
     * 图形验证码(可选)
     */
    private String captchaCode;

    /**
     * 图形验证码UUID(可选)
     */
    private String captchaUuid;

}
