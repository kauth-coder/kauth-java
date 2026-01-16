package cn.kauth.sdk.enums;

/**
 * @author VerifyHub
 * @date 2025/9/17 20:49
 * 加密签名方式枚举
 */
public enum KauthSignEnums {
    SIGN_TYPE_RSA("RSA"),
    SIGN_TYPE_ECC("ECC"),
    SIGN_TYPE_HMAC_SHA256("HMAC_SHA256"),
    ;

    private final String value;

    KauthSignEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
