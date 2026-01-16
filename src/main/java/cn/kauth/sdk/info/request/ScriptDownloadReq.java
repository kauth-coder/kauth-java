package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-24
 * @Description: 获取脚本下载数据请求
 */
@Data
public class ScriptDownloadReq {

    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 版本号
     */
    private String versionNumber;
}
