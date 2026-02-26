package cn.kauth.sdk;

import cn.kauth.sdk.info.ServiceConfig;
import cn.kauth.sdk.info.request.InitConfigReq;
import cn.kauth.sdk.tools.StringUtils;

import java.util.Objects;

public class KauthCoreCore {


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
        serviceConfig.setKauthSignEnums(initConfigReq.getSignType());
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


    public ServiceConfig getServiceConfig() {
        return serviceConfig;
    }
}
