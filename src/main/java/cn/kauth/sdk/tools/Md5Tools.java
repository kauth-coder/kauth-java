package cn.kauth.sdk.tools;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Tools {

    public static String md5(String plainText) {
        try {
            // 创建 MD5 消息摘要对象
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 更新数据
            md.update(plainText.getBytes());
            // 获取摘要字节数组
            byte[] digest = md.digest();
            // 将字节数组转换为十六进制字符串
            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                // 将每个字节转化为两位的十六进制字符串
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }
}
