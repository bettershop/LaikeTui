package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSONObject;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WxOpenIdUtils {

    private static final Logger logger = LoggerFactory.getLogger(WxOpenIdUtils.class);
    /**
     * 获取 openid
     * @param appid
     * @param appsecret
     * @param code
     * @return
     */
    public static String getGzhOpenid(String appid,String appsecret,String code) throws Exception {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appid +  "&secret=" + appsecret + "&code=" + code + "&grant_type=authorization_code";
        logger.info("获取openid地址,{}",url);
        JSONObject retObject = doGet(url);
        logger.info("结果1，{}",retObject);
        String openid = retObject.getJSONObject("data").getString("openid");
        logger.info("openid，{}",openid);
//        url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + appid + "&grant_type=refresh_token&refresh_token=" + refresh_token;
//        logger.info("url，{}",url);
//        retObject = doGet(url);
//        logger.info("结果2，{}",retObject);
//        String openid = retObject.getJSONObject("data").getString("openid");
//        logger.info("结果3，{}",openid);
        return openid;
    }

    /**
     * 获取 openid
     * @param appid
     * @param appsecret
     * @param code
     * @return
     */
    public static String getMiniOpenid(String appid,String appsecret,String code) throws Exception {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid +  "&secret=" + appsecret + "&js_code=" + code + "&grant_type=authorization_code";
        JSONObject retObject = doGet(url);
        String openid = retObject.getJSONObject("data").getString("openid");
        return openid;
    }



    /**
     * 发送请求获取数据
     * @param url
     * @return
     */
    public static JSONObject doGet(String url){
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            JSONObject returnJSON = new JSONObject();
            Call call = okHttpClient.newCall(request);
            Response response = call.execute();
//            logger.info("doGet返回结果,{}",response.body().string());
            returnJSON.put("data",JSONObject.parseObject(response.body().string()));
            return returnJSON;
        }catch (Exception e){
            logger.info("doGet异常,{}",e.getMessage());
            e.printStackTrace();
            return null;
        }

    }

}

