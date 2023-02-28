package com.laiketui.domain.api;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.laiketui.root.gateway.gwconst.LaiKeGWConst;

/**
 * @description: 来客接口返回结果类
 * @author: wx
 * @date: Created in 2019/10/21 16:09
 * @version: 1.0
 * @modified By:
 */
public final class Result {

    private  String code ;
    private  String message ;
    private  Object data ;

    public static Result success(Object data) {
        return new Result(LaiKeGWConst.GW_SUCCESS, "操作成功", data);
    }

    public static Result success(String code, String msg, Object data) {
        return new Result(code, msg, data);
    }

    public static Result success(String code, Object data) {
        return new Result(code, "操作成功", data);
    }

    public static Result success(String code,  String message) {
        return new Result(code, message, null);
    }

    public static Result error(Object data) {
        return new Result(LaiKeGWConst.GW_ERROR, "操作失败", data);
    }

    public static Result error(String code, String message, Object data) {
        return new Result(code, message, data);
    }

    public static Result error(String code, Object data) {
        return new Result(code, "操作失败", data);
    }

    public static Result error(String code,  String message) {
        return new Result(code, message, null);
    }

    /**
     * 导出文件的时候返回null
     * @return
     */
    public static Result exportFile() {
        return null;
    }

    public Result(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * http 接口放回的接口不做转换
     * @param data
     * @return
     */
    public static Result exchange(Object data) {
        return JSONObject.parseObject(data.toString(),Result.class, Feature.IgnoreNotMatch);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
