package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * 用户注册请求
 *
 * @author SongLongKuan
 * @date 2025/4/4
 */
@Data
public class RegisterRequest {
    
    /**
     * 登录账号，4-20位字母、数字或下划线
     */
    private String loginName;
    
    /**
     * 登录密码，6-20位字母、数字或特殊字符
     */
    private String password;

    /**
     * 卡密
     */
    private String kaPassword;

    /**
     * 图形验证码(可选)
     */
    private String captchaCode;
    
    /**
     * 图形验证码UUID(可选)
     */
    private String captchaUuid;
    
    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 设备ID
     */
    private String deviceId;
}
