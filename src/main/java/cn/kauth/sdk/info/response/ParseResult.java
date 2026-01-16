package cn.kauth.sdk.info.response;

import lombok.Data;

@Data
public class ParseResult {
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private String data;
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
