package cn.kauth.sdk.tools;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * ECC椭圆曲线算法工具类
 * 提供公钥/私钥生成、加密、解密功能
 *
 * @author VerifyHub
 * @date 2025/9/17 20:37
 */
public class EccTools {

    private static final String ALGORITHM = "EC";
    private static final String PROVIDER = "BC"; // Bouncy Castle provider
    private static final String TRANSFORMATION = "ECIES";

    static {
        // 添加Bouncy Castle Provider
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    /**
     * 生成ECC公钥私钥对
     *
     * @param keySize 密钥长度，通常为256
     * @return KeyPair 公钥私钥对
     * @throws Exception 异常
     */
    public static KeyPair generateKeyPair(int keySize) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
        keyPairGenerator.initialize(keySize);
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * 从字节数组恢复公钥
     *
     * @param publicKeyBytes 公钥字节数组
     * @return PublicKey 公钥对象
     * @throws Exception 异常
     */
    public static PublicKey loadPublicKey(byte[] publicKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, PROVIDER);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            return keyFactory.generatePublic(keySpec);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 从字节数组恢复私钥
     *
     * @param privateKeyBytes 私钥字节数组
     * @return PrivateKey 私钥对象
     * @throws Exception 异常
     */
    public static PrivateKey loadPrivateKey(byte[] privateKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM, PROVIDER);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            return keyFactory.generatePrivate(keySpec);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 公钥加密
     *
     * @param publicKey 公钥
     * @param data      待加密数据
     * @return byte[] 加密后的数据
     * @throws Exception 异常
     */
    public static byte[] encryptByPublicKey(PublicKey publicKey, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 私钥解密
     *
     * @param privateKey    私钥
     * @param encryptedData 加密数据
     * @return byte[] 解密后的数据
     * @throws Exception 异常
     */
    public static byte[] decryptByPrivateKey(PrivateKey privateKey, byte[] encryptedData) {
        try {
            Cipher cipher = Cipher.getInstance(TRANSFORMATION, PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (Exception exception) {
            return null;
        }
    }

    /**
     * 私钥加密
     *
     * @param privateKey 私钥
     * @param data       待加密数据
     * @return byte[] 加密后的数据
     * @throws Exception 异常
     */
    public static byte[] encryptByPrivateKey(PrivateKey privateKey, byte[] data) throws Exception {
        Signature signature = Signature.getInstance("SHA256withECDSA", PROVIDER);
        signature.initSign(privateKey);
        signature.update(data);
        return signature.sign();
    }

    /**
     * 公钥解密/验签
     *
     * @param publicKey  公钥
     * @param data       原始数据
     * @param signedData 签名数据
     * @return boolean 验证结果
     * @throws Exception 异常
     */
    public static boolean decryptByPublicKey(PublicKey publicKey, byte[] data, byte[] signedData) {
        try {
            Signature signature = Signature.getInstance("SHA256withECDSA", PROVIDER);
            signature.initVerify(publicKey);
            signature.update(data);
            return signature.verify(signedData);
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 将公钥转换为Base64字符串
     *
     * @param publicKey 公钥
     * @return String Base64编码的公钥
     */
    public static String publicKeyToBase64(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    /**
     * 将私钥转换为Base64字符串
     *
     * @param privateKey 私钥
     * @return String Base64编码的私钥
     */
    public static String privateKeyToBase64(PrivateKey privateKey) {
        return Base64.getEncoder().encodeToString(privateKey.getEncoded());
    }

    /**
     * 从Base64字符串恢复公钥
     *
     * @param publicKeyBase64 Base64编码的公钥
     * @return PublicKey 公钥对象
     * @throws Exception 异常
     */
    public static PublicKey loadPublicKeyFromBase64(String publicKeyBase64) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        return loadPublicKey(publicKeyBytes);
    }

    /**
     * 从Base64字符串恢复私钥
     *
     * @param privateKeyBase64 Base64编码的私钥
     * @return PrivateKey 私钥对象
     * @throws Exception 异常
     */
    public static PrivateKey loadPrivateKeyFromBase64(String privateKeyBase64) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        return loadPrivateKey(privateKeyBytes);
    }
}
