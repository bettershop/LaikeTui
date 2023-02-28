package com.laiketui.common.config.alipay;

import com.alibaba.fastjson.JSONObject;
import com.alipay.easysdk.kernel.Config;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author wangxian
 */
public final class AlipayConfigInfo {

    public  static Config config = new Config();

    public  static Config getAlipayConfig(){
        return config;
    }


    /**
     * 获取支付宝支付信息
     * @param configStr
     * @return
     */
    public static Config getOptions(String configStr) throws LaiKeApiException {
        try {
            configStr = URLDecoder.decode(configStr, GloabConst.Chartset.UTF_8);
            JSONObject payJsonObj = JSONObject.parseObject(configStr);

            config.protocol = "https";
            config.gatewayHost = "openapi.alipay.com";
            config.signType = "RSA2";
            config.appId = payJsonObj.getString("appid");
            config.merchantPrivateKey = payJsonObj.getString("rsaPrivateKey");
            config.alipayPublicKey = payJsonObj.getString("alipayrsaPublicKey");
            config.notifyUrl = payJsonObj.getString("notify_url");
            config.encryptKey = payJsonObj.getString("encryptKey");
            return config;
        } catch (LaiKeApiException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
