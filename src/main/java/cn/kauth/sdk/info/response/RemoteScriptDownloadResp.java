package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-24
 * @Description: 远程脚本下载地址响应
 */
@Data
public class RemoteScriptDownloadResp {

    /**
     * 脚本的临时下载地址
     */
    private String downloadUrl;
}
