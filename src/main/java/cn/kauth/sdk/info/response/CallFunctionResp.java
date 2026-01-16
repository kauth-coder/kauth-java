package cn.kauth.sdk.info.response;

import lombok.Data;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 调用远程函数的返回
 */
@Data
public class CallFunctionResp {
    
    /**
     * 函数执行结果
     */
    private String result;
}
