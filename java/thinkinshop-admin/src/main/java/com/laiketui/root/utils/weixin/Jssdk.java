package com.laiketui.root.utils.weixin;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.okhttp.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信小程序授权
 *
 * @author Trick
 * @date 2020/10/9 14:58
 */
@Component
public class Jssdk {

    private final static Logger logger = LoggerFactory.getLogger(Jssdk.class);

    @Autowired
    private RedisUtil redisutil;

    /**
     * 获取微信 ticket
     *
     * @param url -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/9 15:18
     */
    public Map<String, String> getTicket(String url, String appid, String secret) throws LaiKeApiException {
        Map<String, String> ret;
        String ticket = "";
        try {
            Object cacheObj = redisutil.get(GloabConst.RedisHeaderKey.WEIXIN_TICKET);
            if (cacheObj != null) {
                ticket = cacheObj + "";
            } else {
                //重新获取ticket
                String token = getAccessToken(appid, secret);
                String getTicketUrl = String.format(GloabConst.WeiXinUrl.TICKET_JSAPI_GET_URL, token);
                ticket = HttpUtils.get(getTicketUrl);
                if (ticket != null) {
                    Map<String, Object> resultMap = JSON.parseObject(ticket, new TypeReference<Map<String, Object>>() {
                    });
                    String errcode = "errcode";
                    String ok = "0";
                    String code = resultMap.containsKey(errcode) + "";
                    if (!StringUtils.isEmpty(code) && ok.equals(code)) {
                        String successKey = "ticket";
                        ticket = resultMap.get(successKey) + "";
                        //ticket保存到redis
                        redisutil.set(GloabConst.RedisHeaderKey.WEIXIN_TICKET, ticket, GloabConst.WeiXinUrl.ACCESSTOKEN_EXPIRES);
                    } else {
                        logger.error("token获取失败" + resultMap.get("errmsg"));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.GET_ACCESSTOKEN_FAIL, "ticket获取失败 errmsg=" + resultMap.get("errmsg"), "getTicket");
                    }
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getTicket");
                }
            }

            //ticket签名
            ret = sign(ticket, url);
            System.out.println(JSON.toJSONString(ret));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ticket获取失败" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.GET_TICKET_FAIL, "ticket获取失败", "getTicket");
        }

        return ret;
    }


    /**
     * 获取token
     *
     * @param appid  -
     * @param secret -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/9 16:06
     */
    public String getAccessToken(String appid, String secret) throws LaiKeApiException {
        String accessToken;
        try {
            Object cacheObj = redisutil.get(GloabConst.RedisHeaderKey.WEIXIN_ACCESS_TOKEN + appid);
            if (cacheObj != null) {
                accessToken = cacheObj + "";
            } else {
                //重新获取token
                String url = String.format(GloabConst.WeiXinUrl.ACCESSTOKEN_GET_URL, appid, secret);
                accessToken = HttpUtils.get(url);

                if (accessToken != null) {
                    Map<String, Object> resultMap = JSON.parseObject(accessToken, new TypeReference<Map<String, Object>>() {
                    });
                    String errcode = "errcode";
                    if (!resultMap.containsKey(errcode)) {
                        String successKey = "access_token";
                        accessToken = resultMap.get(successKey) + "";
                        //token保存到redis
                        redisutil.set(GloabConst.RedisHeaderKey.WEIXIN_ACCESS_TOKEN + appid, accessToken, GloabConst.WeiXinUrl.ACCESSTOKEN_EXPIRES);
                    } else {
                        logger.error("token获取失败" + resultMap.get("errmsg"));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.GET_ACCESSTOKEN_FAIL, "token获取失败 errmsg=" + resultMap.get("errmsg"), "getAccessToken");
                    }
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAccessToken");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("token获取失败" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.GET_ACCESSTOKEN_FAIL, "token获取失败", "getAccessToken");
        }
        return accessToken;
    }

    public Map<String, String> sign(String jsapiTicket, String url) {
        Map<String, String> ret = new HashMap<String, String>(16);
        String nonceStr = create_nonce_str();
        String timestamp = create_timestamp();
        String string1;
        String signature = "";

        //注意这里参数名必须全部小写，且必须有序
        string1 = "jsapi_ticket=" + jsapiTicket +
                "&noncestr=" + nonceStr +
                "&timestamp=" + timestamp +
                "&url=" + url;
        System.out.println(string1);

        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(string1.getBytes(StandardCharsets.UTF_8));
            signature = byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        ret.put("url", url);
        ret.put("jsapi_ticket", jsapiTicket);
        ret.put("nonceStr", nonceStr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);

        return ret;
    }


    /**
     * 数组转换16进制数据
     *
     * @param hash -
     * @return String
     * @author Trick
     * @date 2020/11/28 11:16
     */
    private String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
