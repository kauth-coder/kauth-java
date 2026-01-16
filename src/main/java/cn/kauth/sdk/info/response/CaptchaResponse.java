package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * 图形验证码响应
 *
 * @author SongLongKuan
 * @date 2025/4/4
 */
@Data
public class CaptchaResponse {
    
    /**
     * 验证码图片Base64编码
     */
    private String captchaBase64;
    
    /**
     * 验证码唯一标识
     */
    private String uuid;
}
