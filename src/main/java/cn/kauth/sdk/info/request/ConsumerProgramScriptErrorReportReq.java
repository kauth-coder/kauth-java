package cn.kauth.sdk.info.request;

import lombok.Data;

/**
 * C端脚本错误上报请求
 *
 * @author SongLongKuan
 * @date 2025/11/22
 */
@Data
public class ConsumerProgramScriptErrorReportReq {

    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 错误信息
     */
    private String errorMessage;

    /**
     * 堆栈信息
     */
    private String stackTrace;

    /**
     * 出错行号
     */
    private Integer line;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 系统版本
     */
    private String osVersion;

    /**
     * 设备ID
     */
    private String deviceId;

}
