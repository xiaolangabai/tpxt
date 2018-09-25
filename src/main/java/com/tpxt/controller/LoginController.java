package com.tpxt.controller;

import com.tpxt.entity.Result;
import com.tpxt.entity.User;
import com.tpxt.result.ResultUtil;
import com.tpxt.service.UserService;
import com.tpxt.utils.CryptographyUtil;
import com.tpxt.utils.TokenManagerUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 登录控制
 * @author zhoush
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    @Resource
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    public Result login(String username, String password){
        User user = userService.getUser(username);
        String md5password = CryptographyUtil.md5(user.getPassword());
        if(!password.equals(md5password)){
            return ResultUtil.failedResult("-1", "登录失败，请重新登录");
        }
        String tokenUserName = user.getUserName();
        //改为时间戳，保证不同地方登陆的token不一致
        String tokenPassword = String.valueOf(System.currentTimeMillis());
        String token = CryptographyUtil.md5(tokenUserName+tokenPassword);
        TokenManagerUtil.putTokenInfo(token, user);
        return ResultUtil.successResult(token);
    }
}
