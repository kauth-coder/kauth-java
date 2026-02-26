package cn.kauth.sdk.info.request;

import cn.kauth.sdk.enums.KauthSignEnums;
import lombok.Data;

@Data
public class InitConfigReq {
    /**
     * 接口域名
     */
    private String apiDomain ;

    /**
     * 程序id
     */
    private Long programId;
    /**
     * 商户公钥
     */
    private String merchantPublicKey;
    /**
     * 程序私钥
     */
    private String programSecret;
    /**
     * 签名方式
     */
    private KauthSignEnums signType;

}
