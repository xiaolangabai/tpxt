package com.tpxt.controller;

import com.tpxt.entity.Content;
import com.tpxt.entity.Result;
import com.tpxt.result.ResultUtil;
import com.tpxt.service.ContentService;
import com.tpxt.service.OtherInfoService;
import com.tpxt.utils.NumberUtil;
import com.tpxt.utils.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhoush
 */
@Controller
@RequestMapping("/index")
public class IndexController {

    @Resource
    private ContentService contentService;

    @Resource
    private OtherInfoService otherInfoService;

    /**
     * 主页分页
     * @param index
     * @param request
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public Result index(String index, HttpServletRequest request){
            int startIndex = 1;
        if(StringUtil.isNotEmpty(index)){
            startIndex = Integer.parseInt(index);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("start", (startIndex-1)*10);
        map.put("size", 10);
        List<Content> contents = contentService.listByCondition(map);
        Map<String, Object> params = new HashMap<>();
        params.put("checked", 1);
        int totalCount = otherInfoService.enrollNumCondition(params);
        int totalPage = totalCount/10  +(totalCount%10 == 0? 0 : 1 ) ;
        if(totalPage == 0){
            totalPage = 1;
        }
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("totalPage", totalPage);
        resultMap.put("content", contents);
        return ResultUtil.successResult(resultMap);
    }

    /**
     * 详细页面
     * @param contentId
     * @param request
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Result detail(int contentId, HttpServletRequest request){
        Content content = contentService.getContentById(contentId);
        return ResultUtil.successResult(content);
    }

    /**
     * 主页查询
     * @param condition
     * @param request
     * @return
     */
    @RequestMapping("/search")
    @ResponseBody
    public Result contentById(String condition, HttpServletRequest request){
        List<Content> content;
        Map<String, Object> map;
        condition = condition.trim();
        if(StringUtils.isBlank(condition)){
            Map<String, Object> param = new HashMap<>();
            param.put("checked", "1");
            param.put("start", 0);
            param.put("size", 10);
            List<Content> contents = contentService.listByCondition(param);
            Map<String, Object> resultMap = new HashMap<>();
            int totalCount = otherInfoService.enrollNumCondition(param);
            int totalPage = totalCount/10  +(totalCount%10==0? 0 : 1 ) ;
            resultMap.put("totalPage", totalPage);
            resultMap.put("content", contents);
            return ResultUtil.successResult(resultMap);
        }
        //如果是数字则是id处理，如果不是当成名字处理
        if(NumberUtil.isNumber(condition)){
            int contentId = 0;
            if(StringUtil.isNotEmpty(condition)){
                contentId = Integer.parseInt(condition);
            }
            Content info = contentService.getContentById(contentId);
            content = new ArrayList<>();
            if(info != null) {
                content.add(info);
            }
            map = new HashMap<>();
            map.put("content", content);
            map.put("page", "1");
            map.put("totalPage", "1");
            return ResultUtil.successResult(map);
        }else{
            content = contentService.getContentByName(condition);
            map = new HashMap<>();
            if(content != null){
                map.put("content", content);
            }else{
                map.put("content", new ArrayList<Content>());
            }
            map.put("page", "1");
            map.put("totalPage", "1");
            return ResultUtil.successResult(map);
        }
    }

    /**
     * 更新访问量
     * @param request
     * @return
     */
    @RequestMapping("/updateViewCount")
    @ResponseBody
    public Result updateViewCount(HttpServletRequest request){
        if(otherInfoService.updateViewCount()){
            return ResultUtil.successResult();
        }
        return ResultUtil.failedResult();
    }

    /**
     * 获取其他主页信息
     * @param request
     * @return
     */
    @RequestMapping("/otherInfo")
    @ResponseBody
    public Result otherInfo(HttpServletRequest request){
        Map<String, Object> params = new HashMap<>();
        params.put("checked", 1);
        int enrollNum = otherInfoService.enrollNumCondition(params);
        int sumTicket = otherInfoService.sumTicket();
        long endTime = otherInfoService.getEndTime();
        long viewCount = otherInfoService.viewCount();
        Map<String, Object> map = new HashMap<>();
        //已报名
        map.put("enrollNum", enrollNum);
        //累计投票->现改为报名通过 2018年9月17日
        map.put("sumTicket", sumTicket);
        //截止日期
        map.put("endTime", endTime);
        //访问量
        map.put("viewCount", viewCount);
        return ResultUtil.successResult(map);
    }

    /**
     * 排名
     * @return
     */
    @RequestMapping("/rank")
    @ResponseBody
    public Result rank(){
        List<Content> list = contentService.rank();
        return ResultUtil.successResult(list);
    }
}
