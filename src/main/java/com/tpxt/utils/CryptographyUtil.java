package com.tpxt.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

public class CryptographyUtil {

    /**
     * Md5加密
     * @param str
     * @return
     */
    public static String md5(String str){
        return new Md5Hash(str).toString();
    }

    public static void main(String[] args) {
        String password="123456";

        System.out.println("Md5加密："+CryptographyUtil.md5(password));
    }
}
