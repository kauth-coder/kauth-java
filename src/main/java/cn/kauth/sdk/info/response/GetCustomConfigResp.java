package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-22
 * @Description: 获取自定义配置响应
 */
@Data
public class GetCustomConfigResp {
    
    /**
     * 自定义配置内容
     */
    private String config;
}
