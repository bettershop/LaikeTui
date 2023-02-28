package com.laiketui.domain.message;

import java.io.Serializable;
import java.util.Map;

/**
 * @author wangxian
 */
public class WxMsgBean implements Serializable {
    /**用户openid*/
    private String touser;
    /**订阅消息模版id*/
    private String template_id;
    /**默认跳到小程序首页*/
    private String page = "pages/index/index";
    /**推送文字*/
    private Map<String, TemplateData> data;

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public Map<String, TemplateData> getData() {
        return data;
    }

    public void setData(Map<String, TemplateData> data) {
        this.data = data;
    }
}
