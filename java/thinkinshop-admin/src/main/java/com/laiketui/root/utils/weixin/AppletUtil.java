package com.laiketui.root.utils.weixin;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laiketui.root.utils.okhttp.HttpUtils;

/**
 * @author sunH_
 */
public final class AppletUtil {

    public static String getAccessToken(String appId, String appSecret) {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
                + appId + "&secret=" + appSecret;
        String accessToken = "";
        String result = HttpUtils.get(url);
        JSONObject jsonObject = JSON.parseObject(result);
        accessToken = jsonObject.getString("access_token");
        return accessToken;
    }

    public static String sendMessage(String accessToken, String openId, String templateId) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
        JSONObject json = new JSONObject();
        json.put("touser", openId);
        json.put("template_id", templateId);
        // 发起请求并返回
        return HttpUtils.post(url, json.toJSONString());
    }
}
