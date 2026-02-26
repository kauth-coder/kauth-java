package cn.kauth.sdk.interfaces;

import cn.kauth.sdk.enums.PongFailEnums;

/**
 * 心跳的回调接口
 */
public interface PongCallback {

    /**
     * 心跳执行失败的回调
     *
     * @param pongFailEnums: 失败原因
     * @param rason:         失败原因 文字
     */
    void callback(PongFailEnums pongFailEnums, String rason);

}
