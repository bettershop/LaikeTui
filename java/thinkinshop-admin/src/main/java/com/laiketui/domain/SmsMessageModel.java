package com.laiketui.domain;


import java.io.Serializable;

/**
 * 短信返回模型
 *
 * @author Trick
 * @date 2020/9/23 16:06
 */
public class SmsMessageModel implements Serializable {

    /**
     * 发送回执ID，可根据该ID在接口QuerySendDetails中查询具体的发送状态。
     */
    private String bizId;

    /**
     * 请求状态码。
     * 返回OK代表请求成功。
     */
    private String code;

    /**
     * 状态码的描述。
     */
    private String message;

    /**
     * 请求ID。
     */
    private String requestId;

    public String getBizId() {
        return bizId;
    }

    public void setBizId(String bizId) {
        this.bizId = bizId;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
