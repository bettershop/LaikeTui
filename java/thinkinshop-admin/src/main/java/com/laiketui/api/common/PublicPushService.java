package com.laiketui.api.common;

import java.util.Map;

/**
 * 推送
 * @author wangxian
 */
public interface PublicPushService {

    /**
     * 个推 推送消息（app/站内消息）
     * @param params
     */
    void pushMessage(Map params);

    /**
     * 微信小程序订阅消息推送
     * @param params
     */
    void pushMessageWechat(Map params);

}
