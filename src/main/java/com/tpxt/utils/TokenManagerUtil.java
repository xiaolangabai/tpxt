package com.tpxt.utils;

import com.tpxt.entity.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TokenManagerUtil {

    private static Map<String, Object> token = null;

    public static Map<String, Object> getTokenManager(){
        if(token == null){
            token = new ConcurrentHashMap<>();
        }
        return token;
    }

    public static void putTokenInfo(String token ,User user){
        user.setExpireTime(System.currentTimeMillis());
        Map<String, Object> tokenManager = getTokenManager();
        tokenManager.put(token, user);
    }

    public static User getTokenInfo(String token){
        return (User) getTokenManager().get(token);
    }
}
