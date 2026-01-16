package cn.kauth.sdk;


import cn.kauth.sdk.enums.KauthSignEnums;
import cn.kauth.sdk.info.ServiceConfig;
import cn.kauth.sdk.info.request.InitConfigReq;
import cn.kauth.sdk.tools.StringUtils;

import java.util.Objects;


public class KauthCoreCore {


    private String deviceId;

    private static KauthCoreCore instance = null;

    public static KauthCoreCore getInstance() {
        return instance;
    }

    /**
     * 服务配置
     */
    private ServiceConfig serviceConfig = new ServiceConfig();


    public static void init(InitConfigReq initConfigReq) {
        instance = new KauthCoreCore();
        instance.initConfig(initConfigReq);
        instance.deviceId = initConfigReq.getDeviceId();
        KAuthApi.initKauthApi(initConfigReq.getApiDomain());
    }


    private String initConfig(InitConfigReq initConfigReq) {
        if (Objects.isNull(initConfigReq)) {
            return "fail:参数不能为空";
        }
        serviceConfig.setApiDomain(initConfigReq.getApiDomain());
        serviceConfig.setProgramId(initConfigReq.getProgramId());
        serviceConfig.setProgramSecret(initConfigReq.getProgramSecret());
        serviceConfig.setMerchantPublicKey(initConfigReq.getMerchantPublicKey());
        if (Objects.equals("RSA", initConfigReq.getSignType())) {
            serviceConfig.setKauthSignEnums(KauthSignEnums.SIGN_TYPE_RSA);
        } else if (Objects.equals("HMAC_SHA256", initConfigReq.getSignType())) {
            serviceConfig.setKauthSignEnums(KauthSignEnums.SIGN_TYPE_HMAC_SHA256);
        } else {
            return "fail:未知的签名类型";
        }
        if (StringUtils.isBlank(serviceConfig.getApiDomain())) {
            serviceConfig.setApiDomain("https://api.kauth.cn");
        }
        if (Objects.isNull(serviceConfig.getProgramId())) {
            return "fail:programId 不能为空";
        }
        if (StringUtils.isBlank(serviceConfig.getMerchantPublicKey())) {
            return "fail:merchantPublicKey 不能为空";
        }
        if (StringUtils.isBlank(serviceConfig.getProgramSecret())) {
            return "fail:programSecret 程序密钥不能为空";
        }

        return "ok";
    }


    public String getDeviceId() {
        return deviceId;
    }


    /**
     * 简单的MD5摘要
     */
    private String md5(String input) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception ignored) {
            return input;
        }
    }


    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }
}
