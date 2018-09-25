package com.tpxt.utils;

import java.util.regex.Pattern;

/**
 * 数字工具类
 * @author zhoush
 */
public class NumberUtil {

    private static final Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");

    public static boolean isNumber(String str) {
        return pattern.matcher(str).matches();
    }
}
