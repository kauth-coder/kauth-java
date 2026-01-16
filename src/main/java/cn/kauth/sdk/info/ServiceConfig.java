package cn.kauth.sdk.info;

import cn.kauth.sdk.enums.KauthSignEnums;
import lombok.Data;

/**
 * 服务配置
 *
 * @author VerifyHub
 * @date 2025/9/17 20:51
 */
@Data
public class ServiceConfig {
    /**
     * 接口域名
     */
    private String apiDomain;

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
    private KauthSignEnums kauthSignEnums;


}
