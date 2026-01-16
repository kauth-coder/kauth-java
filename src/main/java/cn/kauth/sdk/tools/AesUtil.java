package cn.kauth.sdk.tools;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * AES加解密工具类
 * 使用ECB模式和零填充
 *
 * @author VerifyHub
 * @date 2025/4/4
 */
public class AesUtil {

    private static final String ALGORITHM = "AES";
    private static final String CIPHER_ALGORITHM = "AES/ECB/NoPadding";

    /**
     * AES解密
     *
     * @param content 加密内容
     * @param secret  密钥
     * @return 解密后的内容
     */
    public static String decrypt(String content, String secret) {
        if (content == null || content.isEmpty() || secret == null || secret.isEmpty()) {
            return null;
        }
        try {
            byte[] keyBytes = paddingKey(secret).getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, keySpec);

            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(content));
            // 去除填充的零
            return new String(decryptedBytes, StandardCharsets.UTF_8).trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES加密
     *
     * @param content 内容
     * @param secret  密钥
     * @return 加密后的内容
     */
    public static String encrypt(String content, String secret) {
        if (content == null || content.isEmpty() || secret == null || secret.isEmpty()) {
            return null;
        }
        try {
            byte[] keyBytes = paddingKey(secret).getBytes(StandardCharsets.UTF_8);
            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);

            // 计算填充长度，确保数据长度是16的倍数
            byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
            int padding = 16 - (contentBytes.length % 16);
            byte[] paddedContent = new byte[contentBytes.length + padding];
            System.arraycopy(contentBytes, 0, paddedContent, 0, contentBytes.length);
            // 使用0进行填充
            for (int i = contentBytes.length; i < paddedContent.length; i++) {
                paddedContent[i] = 0;
            }

            byte[] encryptedBytes = cipher.doFinal(paddedContent);
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 密钥填充到16位
     * AES加密要求密钥长度为16位
     *
     * @param key 原始密钥
     * @return 填充后的密钥
     */
    private static String paddingKey(String key) {
        if (key.length() >= 16) {
            return key;
        }
        StringBuilder sb = new StringBuilder(key);
        while (sb.length() < 16) {
            sb.append("0");
        }
        return sb.toString();
    }


}
