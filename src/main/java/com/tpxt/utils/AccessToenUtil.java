package com.tpxt.utils;

import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 微信获取access_token工具类
 * @author zhousihao
 */
public class AccessToenUtil {

    public static String getAccessToken(){
        Map<String, String> map = new HashMap<>();
        map.put("hospitalId", "107");
        JSONObject jsonObject = HttpUtil.sendPost("http://his.mobimedical.cn/index.php?g=Notify&m=FrontServerZL&a=getBDaccesstoken", map);
        return jsonObject.getString("accesstoken");
    }

    public static JSONObject getUserInfo(String openId){
        String accessToken = getAccessToken();
        JSONObject jsonObject = HttpUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/info?access_token="+ accessToken +"&openid="+ openId +"&lang=zh_CN");
        return jsonObject;
    }
}
