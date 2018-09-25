package com.tpxt.utils;

public class StringUtil {

    public static boolean isEmpty(String str){
        if(str==null || "".equals(str.trim())){
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str){
        return !isEmpty(str);
    }
}
