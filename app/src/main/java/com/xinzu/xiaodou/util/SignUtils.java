package com.xinzu.xiaodou.util;

import android.util.Log;

import org.apache.tomcat.util.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.HttpServletRequest;


public class SignUtils {
    public static final String TN_VENDORCODE = "66190";

    /**
     * 使用DES加密参数
     *
     * @param data
     * @param key
     * @return
     */
    public static String encodeData(String data, String key) {
        try {
            DESKeySpec ks = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            SecretKey sk = skf.generateSecret(ks);
            Cipher cip = Cipher.getInstance("DES");
            cip.init(Cipher.ENCRYPT_MODE, sk);
            return encodeBase64(cip.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }


    public static String temp() {


        return System.currentTimeMillis() + "";
    }


    /**
     * 使用DES解密参数
     *
     * @param data
     * @param key
     * @return
     */
    public static String decodeData(String data, String key) {
        try {
            DESKeySpec ks = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
            SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
            SecretKey sk = skf.generateSecret(ks);
            Cipher cip = Cipher.getInstance("DES");
            cip.init(Cipher.DECRYPT_MODE, sk);
            return new String(cip.doFinal(decodeBase64(data)), StandardCharsets.UTF_8);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "";
        }
    }

    /**
     * 使用MD5加密参数
     *
     * @param encryptParams
     * @return
     */
    public static String encodeSign(String encryptParams, String accessToken) {
        String param = encryptParams + accessToken;
        return md5Encrypt(param);
    }

    /**
     * MD5加密算法
     *
     * @param pwd 加密字符串
     * @return 加密后字符串
     */
    public final static String md5Encrypt(String pwd) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] strTemp = pwd.getBytes();
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从字节数组到十六进制字符串转换
     */
    public static String encodeBase64(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    /**
     * 从十六进制字符串到字节数组转换
     */
    public static byte[] decodeBase64(String base64String) {
        return Base64.decodeBase64(base64String);
    }

    /**
     * 途牛加密
     *
     * @param request
     * @return
     */
    public static Boolean
    sign(HttpServletRequest request) {
        Boolean bool = null;
        String timestamp = request.getParameter("timestamp");
        String sign = request.getParameter("sign");
        System.out.println("timestamp" + timestamp);
        System.out.println("sign" + sign);
        if (timestamp != null && !timestamp.equals("")) {
            if (sign != null && !sign.equals("")) {
                String vendorCode = TN_VENDORCODE;
                String md5 = "vendorCode=" + vendorCode + "&timestamp=" + timestamp;
                String md5String = KHMD5.getMD5String(md5);
                System.out.println("sign" + md5String);
                if (md5String.equals(sign)) {
                    bool = true;
                } else {
                    System.out.println("加密错误.....");
                    bool = false;
                }
            } else {
                bool = false;
            }

        } else {
            bool = false;
        }

        return bool;

    }
}
