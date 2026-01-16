package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-08-20
 * @Description: 解绑设备请求（卡密方式）
 */
@Data
public class UnbindDeviceKaPwdRequest {

    /**
     * 卡密
     */
    private String kaPwd;

    /**
     * 设备ID
     */
    private String deviceId;
}
