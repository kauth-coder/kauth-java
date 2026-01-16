package cn.kauth.sdk.info.request;

import lombok.Data;

import java.util.List;

/**
 * @author: songlongkuan
 * @CreateTime: 2025-09-23
 * @Description: 调用远程函数请求
 */
@Data
public class CallFunctionReq {
    
    /**
     * 函数名称
     */
    private String functionName;
    
    /**
     * 函数参数列表
     */
    private List<CallFunctionParamsReq> functionParams;

    /**
     * 函数参数
     */
    @Data
    public static class CallFunctionParamsReq {
        
        /**
         * 参数名称
         */
        private String paramName;
        
        /**
         * 参数值
         */
        private String paramValue;
        
        public CallFunctionParamsReq() {}
        
        public CallFunctionParamsReq(String paramName, String paramValue) {
            this.paramName = paramName;
            this.paramValue = paramValue;
        }
    }
}
