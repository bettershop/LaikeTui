package com.laiketui.root.exception;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/16 17:48
 * @version: 1.0
 * @modified By:
 */
public class LaiKeCommonException  extends LaiKeApiException {

    public LaiKeCommonException(String code, String message, String method, String descinfo) {
        super(code, message, method, descinfo);
    }

    public LaiKeCommonException(String code, String message, String method) {
        super(code, message, method);
    }

    public LaiKeCommonException(String code,String message) {
        super(code,message);
    }

    public LaiKeCommonException(String message) {
        super(message);
    }

    public LaiKeCommonException(){}


}
