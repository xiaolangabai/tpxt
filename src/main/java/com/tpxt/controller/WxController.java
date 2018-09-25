package com.tpxt.controller;

import com.alibaba.fastjson.JSONObject;
import com.tpxt.entity.Result;
import com.tpxt.result.ResultUtil;
import com.tpxt.utils.CheckUtil;
import com.tpxt.utils.HttpUtil;
import com.tpxt.utils.PropertiesUtil;
import com.tpxt.wx.RandomStringGenerator;
import com.tpxt.wx.SHA1;
import com.tpxt.wx.Token;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 微信授权
 * @author zhoush
 */
@Controller
public class WxController {

    @RequestMapping("/wx")
    public void wx(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        PrintWriter out = resp.getWriter();
        if(CheckUtil.checkSignature(signature, timestamp, nonce)){
            out.print(echostr);
        }
    }

    @RequestMapping("/wxopenid")
    @ResponseBody
    public Result wxOpenId(HttpServletRequest request){
        Properties properties = PropertiesUtil.getProperties();
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+
                properties.getProperty("appId")+
                "&secret="+
                properties.getProperty("appsecret")+
                "&code=" + code + "&grant_type=authorization_code";
        JSONObject jsonObject = HttpUtil.sendGet(url);
        return ResultUtil.successResult(jsonObject);
    }

    @RequestMapping("/wxsignature")
    @ResponseBody
    public Result wxSignature(HttpServletRequest request) throws UnsupportedEncodingException {
        String ticket = Token.getTicket();
        String noncestr = RandomStringGenerator.getRandomStringByLength(32);
        long timestamp = System.currentTimeMillis()/1000;
        String sign =  "jsapi_ticket="+ticket+"&noncestr="//请勿更换字符组装顺序
                +noncestr+"&timestamp="+timestamp
                +"&url="+request.getParameter("rUrl");//url为你当前访问的url路径，除去#与#后面的数据
        String signature = new SHA1().getDigestOfString(sign.getBytes("utf-8"));
        Map<String, Object> map = new HashMap<>();
        map.put("ticket", ticket);
        map.put("noncestr",noncestr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        return ResultUtil.successResult(map);
    }
}
