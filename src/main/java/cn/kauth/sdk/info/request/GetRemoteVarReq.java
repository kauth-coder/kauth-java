package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 获取远程变量/数据请求
 */
@Data
public class GetRemoteVarReq {

    /**
     * 变量或数据的键名
     */
    private String key;
}
