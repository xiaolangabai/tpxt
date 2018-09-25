package com.tpxt.controller;

import com.alibaba.fastjson.JSONObject;
import com.tpxt.entity.Content;
import com.tpxt.entity.Result;
import com.tpxt.result.ResultUtil;
import com.tpxt.service.ContentService;
import com.tpxt.service.OtherInfoService;
import com.tpxt.service.VoteService;
import com.tpxt.utils.AccessToenUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 投票Controller
 * @author zhoush
 */
@Controller
@RequestMapping("/ticket")
public class VoteController {

    @Resource
    private VoteService voteService;

    @Resource
    private ContentService contentService;

    @Resource
    private OtherInfoService otherInfoService;

    /**
     * 报名
     * @param request
     * @return
     */
    @RequestMapping(value = "/enroll", method = RequestMethod.POST)
    @ResponseBody
    public Result enroll(HttpServletRequest request){
        if(otherInfoService.getEndTime() <= 0){
            return ResultUtil.failedResult("-1", "活动已结束");
        }
        JSONObject userInfo = AccessToenUtil.getUserInfo(request.getParameter("wxId"));
        if("0".equals(userInfo.getString("subscribe"))){
            return ResultUtil.failedResult("-1", "您还未关注公众号，请关注再报名");
        }
        if(userInfo.getString("subscribe") == null){
            return ResultUtil.failedResult("-1", "获取用户信息失败，请稍后重试");
        }
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String name = request.getParameter("name");
        String contactWay = request.getParameter("contactWay");
        if(StringUtils.isBlank(title)){
            return ResultUtil.failedResult();
        }
        if(StringUtils.isBlank(content)){
            return ResultUtil.failedResult();
        }
        if(StringUtils.isBlank(name)){
            return ResultUtil.failedResult();
        }
        if(StringUtils.isBlank(contactWay)){
            return ResultUtil.failedResult();
        }
        Content contentObj = new Content();
        contentObj.setTitle(title);
        contentObj.setContent(content);
        contentObj.setName(name);
        contentObj.setContactWay(contactWay);
        if(voteService.addVote(contentObj)){
            return ResultUtil.successResult();
        }
        return ResultUtil.failedResult("-1","更新失败");
    }

    /**
     * 投票
     * @param request
     * @return
     */
    @RequestMapping(value = "/vote", method = RequestMethod.POST)
    @ResponseBody
    public Result vote(HttpServletRequest request){
        if(otherInfoService.getEndTime() <= 0){
            return ResultUtil.failedResult("-1", "活动已结束");
        }
        String wxId = request.getParameter("wxId");
        if("undefined".equals(wxId)){
            return ResultUtil.failedResult("-1", "您还未关注公众号，请先关注公众号");
        }
        String contentId = request.getParameter("contentId");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String now = sdf.format(new Date());
        String startTime = now + " 00:00:00";
        String endTime = now + " 23:59:59";
        Map<String, Object> params = new HashMap<>();
        params.put("contentId", contentId);
        params.put("wxId", wxId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        if(voteService.voteRecord(params)){
            return ResultUtil.failedResult("-1","同一个用户一天只能投一次票");
        }
        Content contentInfo = contentService.getContentById(Integer.parseInt(contentId));
        contentInfo.setTicket(contentInfo.getTicket() + 1);
        if(!voteService.addRecord(params)){
            return ResultUtil.failedResult("-1", "投票失败");
        }
        if(!contentService.updateContent(contentInfo)){
            return ResultUtil.failedResult();
        }
        return ResultUtil.successResult();
    }
}
