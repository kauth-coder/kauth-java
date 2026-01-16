package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 新增远程数据请求
 */
@Data
public class RemoteDataAddReq {
    
    /**
     * 数据键名，最大长度32
     */
    private String key;
    
    /**
     * 数据值，最大长度255
     */
    private String value;

}
