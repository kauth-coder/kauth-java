package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * 用户注册响应
 *
 * @author SongLongKuan
 * @date 2025/4/4
 */
@Data
public class RegisterResponse {
    
    /**
     * 新注册用户的唯一标识
     */
    private Long userId;
    
    /**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 用户登录账号
     */
    private String loginName;
}
