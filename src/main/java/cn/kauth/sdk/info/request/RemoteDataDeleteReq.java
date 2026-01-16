package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 删除远程数据请求
 */
@Data
public class RemoteDataDeleteReq {
    
    /**
     * 要删除的数据键名
     */
    private String key;
}