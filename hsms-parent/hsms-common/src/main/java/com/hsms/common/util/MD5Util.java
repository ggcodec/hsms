package com.hsms.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * md5加密
 *
 * @author haotchen
 * @time 2022/11/7-18:27
 */
public class MD5Util {

    public static String getMD5(String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException{
        final MessageDigest md5 = MessageDigest.getInstance("MD5");
        final byte[] byteArray = msg.getBytes("UTF-8");
        final byte[] md5Bytes = md5.digest(byteArray);

        //将生成的md5Bytes转成16进制形式
        final StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++)
        {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
