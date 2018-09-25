package com.tpxt.result;

import com.tpxt.entity.Result;

/**
 * 响应结果集合
 * 其中大于0表示成功响应，小于等于0表示失败响应
 * @author zhoush
 */
public class ResultUtil {

    public static Result successResult(){
        return successResult(null);
    }

    public static Result successResult(Object data){
        return new Result("1", "success", data);
    }

    public static Result failedResult(){
        return failedResult("0");
    }

    public static Result failedResult(String code){
        return new Result(code, "error", null);
    }

    public static Result failedResult(String code, String msg){
        return new Result(code, msg, null);
    }
}
