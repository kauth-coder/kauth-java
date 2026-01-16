package cn.kauth.sdk.tools;


import cn.kauth.sdk.info.response.LoginResponse;

public class SessionManager {
    private static String accessToken;
    private static LoginResponse userInfo;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String token) {
        accessToken = token;
    }

    public static LoginResponse getUserInfo() {
        return userInfo;
    }

    public static void setUserInfo(LoginResponse info) {
        userInfo = info;
    }

    public static boolean isLoggedIn() {
        return accessToken != null && !accessToken.isEmpty();
    }

    public static void clearSession() {
        accessToken = null;
        userInfo = null;
    }
}
