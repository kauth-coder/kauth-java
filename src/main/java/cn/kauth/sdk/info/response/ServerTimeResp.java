package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-22
 * @Description: 服务器时间响应
 */
@Data
public class ServerTimeResp {
    /**
     * 服务器时间字符串格式 yyyy-MM-dd HH:mm:ss
     */
    private String serverTimeStr;

    /**
     * 服务器时间戳（毫秒）
     */
    private Long serverTimeMill;

    /**
     * 服务器时间戳（秒）
     */
    private Long serverTimeSec;
}
