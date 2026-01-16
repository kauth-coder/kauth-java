package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * 响应结果
 * @author VerifyHub
 * @date 2025/9/17 21:10
 */
@Data
public class ApiResult<T> {

    /**
     * 错误信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 链路追踪ID
     */
    private String traceId;

    /**
     * 消耗时间
     */
    private String elapse;
    /**
     * 响应时间
     */
    private String respTime;


    public Boolean success;

}