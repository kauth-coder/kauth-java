package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-08-20
 * @Description: 修改密码请求
 */
@Data
public class ResetPwdRequest {

    /**
     * 登录账号，4-20位字母、数字或下划线
     */
    private String loginName;

    /**
     * 旧密码，6-20位字母、数字或特殊字符
     */
    private String oldPassword;

    /**
     * 新密码，6-20位字母、数字或特殊字符
     */
    private String newPassword;

    /**
     * 确认密码，6-20位字母、数字或特殊字符
     */
    private String confirmPassword;

    /**
     * 图形验证码(可选)
     */
    private String captchaCode;

    /**
     * 图形验证码UUID(可选)
     */
    private String captchaUuid;

}
