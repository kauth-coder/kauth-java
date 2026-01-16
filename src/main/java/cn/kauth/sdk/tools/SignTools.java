package cn.kauth.sdk.tools;


import cn.kauth.sdk.info.ServiceConfig;

import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Objects;


public class SignTools {


    public static String sign(ServiceConfig serviceConfig, String url, String body, String nonce, long time) {
        String signTemplate = "url:" + url + "\nbody:" + (Objects.isNull(body) ? "" : body) + "\nnonce:" + nonce + "\ntime:" + time;
        String mded5 = Md5Tools.md5(signTemplate);
        switch (serviceConfig.getKauthSignEnums()) {
            case SIGN_TYPE_ECC:
                PublicKey merchantPublicKey = EccTools.loadPublicKey(Base64.getDecoder().decode(serviceConfig.getMerchantPublicKey()));
                byte[] bytes = EccTools.encryptByPublicKey(merchantPublicKey, mded5.getBytes(StandardCharsets.UTF_8));
                return Base64.getEncoder().encodeToString(bytes);
            case SIGN_TYPE_RSA:
                return RsaTools.encryptByPublicKey(mded5, serviceConfig.getMerchantPublicKey());
            case SIGN_TYPE_HMAC_SHA256:
                return HmacTools.signWithHmacSHA256(mded5, serviceConfig.getMerchantPublicKey());
        }
        return null;
    }

    public static boolean verifyResponseSign(ServiceConfig serviceConfig, String url, String body, String nonce, long time, String sign) {
        String signTemplate = "url:" + url + "\nbody:" + (Objects.isNull(body) ? "" : body) + "\nnonce:" + nonce + "\ntime:" + time;
        String mded5 = Md5Tools.md5(signTemplate);
        switch (serviceConfig.getKauthSignEnums()) {
            case SIGN_TYPE_ECC:
                PublicKey merchantPublicKey = EccTools.loadPublicKey(Base64.getDecoder().decode(serviceConfig.getMerchantPublicKey()));
                return EccTools.decryptByPublicKey(merchantPublicKey, mded5.getBytes(StandardCharsets.UTF_8), Base64.getDecoder().decode(sign));
            case SIGN_TYPE_RSA:
                String decryptSign = RsaTools.decryptByPublicKey(sign, serviceConfig.getMerchantPublicKey());
                return Objects.equals(decryptSign, mded5);
            case SIGN_TYPE_HMAC_SHA256:
                return HmacTools.verifyWithHmacSHA256(mded5, serviceConfig.getMerchantPublicKey(), sign);
        }
        return false;
    }

}
