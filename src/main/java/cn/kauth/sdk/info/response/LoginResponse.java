package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * 用户登录响应
 *
 * @author SongLongKuan
 * @date 2025/4/4
 */
@Data
public class LoginResponse {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 登录令牌
     */
    private String token;

    /**
     * 心跳间隔
     */
    private Long pongInterval;
}
