package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 更新远程数据请求
 */
@Data
public class RemoteDataUpdateReq {

    /**
     * 数据键名，最大长度32
     */
    private String key;
    
    /**
     * 新的数据值，最大长度255
     */
    private String value;
}
