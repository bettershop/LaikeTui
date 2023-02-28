package com.laiketui.root.gateway.exception;

import com.laiketui.root.exception.LaiKeCommonException;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/23 14:38
 * @version: 1.0
 * @modified By:
 */
public class LaiKeGWException extends LaiKeCommonException {

    public LaiKeGWException(String code, String message, String method, String descinfo) {
        super(code, message, method, descinfo);
    }

    public LaiKeGWException(String code, String message) {
        super(code, message);
    }

    public LaiKeGWException(String code, String message, String method) {
        super(code, message, method);
    }

    public LaiKeGWException() {
    }
}
