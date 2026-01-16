package cn.kauth.sdk.info.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-24
 * @Description: 获取最新的脚本数据响应
 */
@Data
public class GetNewestScriptResp {

    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 版本号
     */
    private String versionNumber;

    /**
     * 版本说明
     */
    private String versionDescription;

    /**
     * 脚本类型 TEXT-文本脚本，BINARY-二进制文件
     */
    private String scriptType;

    /**
     * 脚本发布时间
     */
    private LocalDateTime scriptReleaseTime;
}
