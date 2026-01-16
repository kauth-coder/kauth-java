package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-22
 * @Description: 修改自定义配置请求
 */
@Data
public class UpdateCustomConfigReq {
    /**
     * 自定义配置内容
     */
    private String config;
}
