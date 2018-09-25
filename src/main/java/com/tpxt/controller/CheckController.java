package com.tpxt.controller;

import com.tpxt.entity.Content;
import com.tpxt.entity.Result;
import com.tpxt.entity.User;
import com.tpxt.result.ResultUtil;
import com.tpxt.service.ContentService;
import com.tpxt.service.OtherInfoService;
import com.tpxt.utils.StringUtil;
import com.tpxt.utils.TokenManagerUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 后台审核控制器
 * @author zhousihao
 */
@RestController
public class CheckController {

    @Resource
    private ContentService contentService;

    @Resource
    private OtherInfoService otherInfoService;

    /**
     * 后台管理更新审核数据
     * @param contentId
     * @param checked
     * @return
     */
    @RequestMapping("/check")
    public Result check(int contentId, String token,String checked){
        User user = TokenManagerUtil.getTokenInfo(token);
        if(user == null){
            return ResultUtil.failedResult("-2", "用户未登录，请重新登录");
        }
        long expireTime = TokenManagerUtil.getTokenInfo(token).getExpireTime();
        if(expireTime == 0){
            return ResultUtil.failedResult("-2", "用户未登录，请重新登录");
        }
        if(System.currentTimeMillis() - expireTime > 30*60*1000L){
            return ResultUtil.failedResult("-2", "认证已过期，请重新登录");
        }
        //如果认证通过，重置有效时间，以后方法过多将提成通用方法
        TokenManagerUtil.putTokenInfo(token, user);
        Content content = new Content();
        content.setId(contentId);
        content.setChecked(checked);
        if(!contentService.updateChecked(content)){
            return ResultUtil.failedResult("-1", "审核失败");
        }
        return ResultUtil.successResult();
    }

    @RequestMapping("/del")
    public Result removeContent(int contentId, String token){
        User user = TokenManagerUtil.getTokenInfo(token);
        if(user == null){
            return ResultUtil.failedResult("-2", "用户未登录，请重新登录");
        }
        long expireTime = TokenManagerUtil.getTokenInfo(token).getExpireTime();
        if(expireTime == 0){
            return ResultUtil.failedResult("-2", "用户未登录，请重新登录");
        }
        if(System.currentTimeMillis() - expireTime > 30*60*1000L){
            return ResultUtil.failedResult("-2", "认证已过期，请重新登录");
        }
        //如果认证通过，重置有效时间，以后方法过多将提成通用方法
        TokenManagerUtil.putTokenInfo(token, user);
        Map<String, Object> params = new HashMap<>();
        params.put("id", contentId);
        if(!contentService.delContent(params)){
            return ResultUtil.failedResult("-1", "删除失败");
        }
        return ResultUtil.successResult();
    }

    /**
     * 后台管理获取审核数据
     * @param index
     * @return
     */
    @RequestMapping("/unchecked")
    public Result unChecked(String index, String token, String checked){
        User user = TokenManagerUtil.getTokenInfo(token);
        if(user == null){
            return ResultUtil.failedResult("-2", "用户未登录，请重新登录");
        }
        long expireTime = TokenManagerUtil.getTokenInfo(token).getExpireTime();
        if(expireTime == 0){
            return ResultUtil.failedResult("-2", "用户未登录，请重新登录");
        }
        if(System.currentTimeMillis() - expireTime > 30*60*1000L){
            return ResultUtil.failedResult("-2", "认证已过期，请重新登录");
        }
        //如果认证通过，重置有效时间，以后方法过多将提成通用方法
        TokenManagerUtil.putTokenInfo(token, user);
        int startIndex = 1;
        if(StringUtil.isNotEmpty(index)){
            startIndex = Integer.parseInt(index);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("checked", checked);
        map.put("start", (startIndex-1)*10);
        map.put("size", 10);
        map.put("status", "1");
        List<Content> list = contentService.listByStatus(map);
        Map<String, Object> resultMap = new HashMap<>();
        Map<String, Object> params = new HashMap<>();
        params.put("checked", checked);
        params.put("status", "1");
        int totalCount = otherInfoService.enrollNumCondition(params);
//        int totalPage = totalCount/10  +(totalCount%10==0? 0 : 1 ) ;
        resultMap.put("totalCount", totalCount);
        resultMap.put("content", list);
        return ResultUtil.successResult(resultMap);
    }
}
