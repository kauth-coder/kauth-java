package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * 图形验证码请求
 *
 * @author SongLongKuan
 * @date 2025/4/4
 */
@Data
public class CaptchaRequest {
    
    /**
     * 客户端生成的UUID，用于标识验证码
     */
    private String uuid;
}
