package cn.kauth.sdk.enums;

/**
 * 心跳失败的原因枚举
 */
public enum PongFailEnums {

    /**
     * 登录已失效
     */
    INVALID_LOGIN("INVALID_LOGIN"),

    /**
     * 最大连接失败
     */
    MAXFAIL_CONNECTION("MAXFAIL_CONNECTION"),

    /**
     * 其他失败
     */
    OTHER_FAIL("OTHER_FAIL"),
    ;

    private final String value;

    PongFailEnums(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
