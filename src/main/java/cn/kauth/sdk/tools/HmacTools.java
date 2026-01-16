package cn.kauth.sdk.tools;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author: VerifyHub
 * @CreateTime: 2025-09-28
 * @Description: hmac工具类
 */
public class HmacTools {

    private static final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
    private static final int RECOMMENDED_KEY_LENGTH = 32; // 32字节 = 256位

    /**
     * 使用HMAC-SHA256算法对数据进行签名
     *
     * @param data 待签名的数据
     * @param key  密钥
     * @return 签名结果的Base64编码字符串
     * @throws RuntimeException 如果签名过程中发生错误
     */
    public static String signWithHmacSHA256(String data, String key) {
        try {
            // 创建密钥规范
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes("UTF-8"), HMAC_SHA256_ALGORITHM);

            // 获取Mac实例
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);

            // 执行签名
            byte[] rawHmac = mac.doFinal(data.getBytes("UTF-8"));

            // 将签名结果进行Base64编码并返回
            return Base64.getEncoder().encodeToString(rawHmac);
        } catch (NoSuchAlgorithmException | InvalidKeyException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to calculate HMAC-SHA256", e);
        }
    }

    /**
     * 使用HMAC-SHA256算法对数据进行签名（字节数组版本）
     *
     * @param data 待签名的数据
     * @param key  密钥（字节数组）
     * @return 签名结果的字节数组
     * @throws RuntimeException 如果签名过程中发生错误
     */
    public static byte[] signWithHmacSHA256(byte[] data, byte[] key) {
        try {
            // 创建密钥规范
            SecretKeySpec secretKeySpec = new SecretKeySpec(key, HMAC_SHA256_ALGORITHM);

            // 获取Mac实例
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(secretKeySpec);

            // 执行签名并返回结果
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to calculate HMAC-SHA256", e);
        }
    }

    /**
     * 验证HMAC-SHA256签名
     *
     * @param data      原始数据
     * @param key       密钥
     * @param signature 待验证的签名
     * @return 如果签名有效返回true，否则返回false
     */
    public static boolean verifyWithHmacSHA256(String data, String key, String signature) {
        try {
            // 计算数据的签名
            String expectedSignature = signWithHmacSHA256(data, key);

            // 比较签名（使用时间安全的比较方法）
            return constantTimeEquals(expectedSignature, signature);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 时间安全的字符串比较方法，防止时序攻击
     *
     * @param a 字符串a
     * @param b 字符串b
     * @return 如果两个字符串相等返回true，否则返回false
     */
    private static boolean constantTimeEquals(String a, String b) {
        if (a == null || b == null) {
            return a == b;
        }

        byte[] aBytes = a.getBytes();
        byte[] bBytes = b.getBytes();

        if (aBytes.length != bBytes.length) {
            return false;
        }

        int result = 0;
        for (int i = 0; i < aBytes.length; i++) {
            result |= aBytes[i] ^ bBytes[i];
        }

        return result == 0;
    }


}
