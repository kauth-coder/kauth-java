package cn.kauth.sdk.tools;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA非对称加密解密工具类
 *
 * @author: VerifyHub
 * @CreateTime: 2025-09-18
 */
public class RsaTools {

    /**
     * RSA算法名称
     */
    private static final String RSA_ALGORITHM = "RSA";

    /**
     * RSA加密算法转换格式
     */
    private static final String RSA_TRANSFORMATION = "RSA/ECB/PKCS1Padding";

    /**
     * RSA密钥长度，默认2048位
     */
    private static final int RSA_KEY_SIZE = 2048;

    /**
     * 公钥标识
     */
    public static final String PUBLIC_KEY = "publicKey";

    /**
     * 私钥标识
     */
    public static final String PRIVATE_KEY = "privateKey";

    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 245; // 使用PKCS1Padding后，2048位密钥最大可加密117字节

    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 256; // 2048位RSA密钥加密后的密文块大小

    /**
     * 生成RSA密钥对
     *
     * @return 包含公钥和私钥的Map，key为PUBLIC_KEY和PRIVATE_KEY
     * @throws Exception 生成密钥对异常
     */
    public static Map<String, String> generateKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(RSA_KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        return keyMap;
    }

    /**
     * 生成RSA密钥对（指定密钥长度）
     *
     * @param keySize 密钥长度（建议1024、2048、4096）
     * @return 包含公钥和私钥的Map
     * @throws Exception 生成密钥对异常
     */
    public static Map<String, String> generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM);
        keyPairGenerator.initialize(keySize);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        Map<String, String> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, Base64.getEncoder().encodeToString(publicKey.getEncoded()));
        keyMap.put(PRIVATE_KEY, Base64.getEncoder().encodeToString(privateKey.getEncoded()));

        return keyMap;
    }

    /**
     * 公钥加密
     *
     * @param plainText    明文
     * @param publicKeyStr 公钥字符串（Base64编码）
     * @return 加密后的密文（Base64编码）
     * @throws Exception 加密异常
     */
    public static String encryptByPublicKey(String plainText, String publicKeyStr) {
        try {
            if (plainText == null || publicKeyStr == null) {
                throw new IllegalArgumentException("明文和公钥不能为空");
            }

            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            // 添加调试信息

            PublicKey publicKey;
            try {
                // 首先尝试X.509格式
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
                publicKey = keyFactory.generatePublic(keySpec);
            } catch (InvalidKeySpecException e) {
                // 如果X.509格式失败，重新抛出异常
                throw e;
            }

            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            byte[] data = plainText.getBytes("UTF-8");
            byte[] encryptedData = doFinal(cipher, data, MAX_ENCRYPT_BLOCK);

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密
     *
     * @param cipherText   密文（Base64编码）
     * @param publicKeyStr 公钥字符串（Base64编码）
     * @return 解密后的明文
     */
    public static String decryptByPublicKey(String cipherText, String publicKeyStr) {
        try {
            if (cipherText == null || publicKeyStr == null) {
                throw new IllegalArgumentException("密文和公钥不能为空");
            }

            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            // 添加调试信息

            PublicKey publicKey;
            try {
                // 首先尝试X.509格式
                X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
                publicKey = keyFactory.generatePublic(keySpec);
            } catch (InvalidKeySpecException e) {
                // 如果X.509格式失败，重新抛出异常
                throw e;
            }

            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);

            byte[] encryptedData = Base64.getDecoder().decode(cipherText);
            byte[] decryptedData = doFinal(cipher, encryptedData, MAX_DECRYPT_BLOCK);

            return new String(decryptedData, "UTF-8");
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * 私钥加密
     *
     * @param plainText     明文
     * @param privateKeyStr 私钥字符串（Base64编码）
     * @return 加密后的密文（Base64编码）
     * @throws Exception 加密异常
     */
    public static String encryptByPrivateKey(String plainText, String privateKeyStr) {
        try {
            if (plainText == null || privateKeyStr == null) {
                throw new IllegalArgumentException("明文和私钥不能为空");
            }

            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            // 添加调试信息

            PrivateKey privateKey;
            try {
                // 使用PKCS#8格式
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch (InvalidKeySpecException e) {
                // 如果PKCS#8格式失败，重新抛出异常
                throw e;
            }

            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);

            byte[] data = plainText.getBytes("UTF-8");
            byte[] encryptedData = doFinal(cipher, data, MAX_ENCRYPT_BLOCK);

            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 私钥解密
     *
     * @param cipherText    密文（Base64编码）
     * @param privateKeyStr 私钥字符串（Base64编码）
     * @return 解密后的明文
     * @throws Exception 解密异常
     */
    public static String decryptByPrivateKey(String cipherText, String privateKeyStr) {
        try {
            if (cipherText == null || privateKeyStr == null) {
                throw new IllegalArgumentException("密文和私钥不能为空");
            }

            byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
            PrivateKey privateKey;
            try {
                // 使用PKCS#8格式
                PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
                KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
                privateKey = keyFactory.generatePrivate(keySpec);
            } catch (InvalidKeySpecException e) {
                // 如果PKCS#8格式失败，重新抛出异常
                throw e;
            }

            Cipher cipher = Cipher.getInstance(RSA_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            byte[] encryptedData = Base64.getDecoder().decode(cipherText);
            byte[] decryptedData = doFinal(cipher, encryptedData, MAX_DECRYPT_BLOCK);

            return new String(decryptedData, "UTF-8");
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 分段处理加密/解密数据
     *
     * @param cipher       加密/解密器
     * @param data         待处理数据
     * @param maxBlockSize 最大块大小
     * @return 处理后的数据
     * @throws Exception 处理异常
     */
    private static byte[] doFinal(Cipher cipher, byte[] data, int maxBlockSize) throws Exception {
        int inputLen = data.length;
        int offset = 0;
        byte[] cache;
        int i = 0;

        // 分段处理
        java.io.ByteArrayOutputStream out = new java.io.ByteArrayOutputStream();
        while (inputLen - offset > 0) {
            if (inputLen - offset > maxBlockSize) {
                cache = cipher.doFinal(data, offset, maxBlockSize);
            } else {
                cache = cipher.doFinal(data, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * maxBlockSize;
        }
        byte[] result = out.toByteArray();
        out.close();
        return result;
    }

    /**
     * 从字符串获取公钥对象
     *
     * @param publicKeyStr 公钥字符串（Base64编码）
     * @return 公钥对象
     * @throws Exception 转换异常
     */
    public static PublicKey getPublicKeyFromString(String publicKeyStr) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
        // 添加调试信息
        try {
            // 首先尝试X.509格式
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePublic(keySpec);
        } catch (InvalidKeySpecException e) {
            // 如果X.509格式失败，重新抛出异常
            throw e;
        }
    }

    /**
     * 从字符串获取私钥对象
     *
     * @param privateKeyStr 私钥字符串（Base64编码）
     * @return 私钥对象
     * @throws Exception 转换异常
     */
    public static PrivateKey getPrivateKeyFromString(String privateKeyStr) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyStr);
        // 添加调试信息

        try {
            // 使用PKCS#8格式
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
            return keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            // 如果PKCS#8格式失败，重新抛出异常
            throw e;
        }
    }

    /**
     * 验证密钥对是否匹配
     *
     * @param publicKeyStr  公钥字符串
     * @param privateKeyStr 私钥字符串
     * @return true-匹配，false-不匹配
     */
    public static boolean verifyKeyPair(String publicKeyStr, String privateKeyStr) {
        try {
            String testData = "RSA密钥对验证测试数据";

            // 私钥加密，公钥解密
            String encrypted = encryptByPrivateKey(testData, privateKeyStr);
            String decrypted = decryptByPublicKey(encrypted, publicKeyStr);

            return testData.equals(decrypted);
        } catch (Exception e) {
            return false;
        }
    }
}
