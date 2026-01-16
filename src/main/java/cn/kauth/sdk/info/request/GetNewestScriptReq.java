package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-24
 * @Description: 获取最新的脚本
 */
@Data
public class GetNewestScriptReq {
    /**
     * 脚本名称
     */
    private String scriptName;
}
