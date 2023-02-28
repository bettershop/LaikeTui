package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laiketui.api.common.PublicPushService;
import com.laiketui.common.mapper.SystemMessageModelMapper;
import com.laiketui.domain.home.SystemMessageModel;
import com.laiketui.domain.message.TemplateData;
import com.laiketui.domain.message.WxMsgBean;
import com.laiketui.domain.user.User;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

/**
 * 推送
 * @author wangxian
 */
@Service("publicPushService")
public class PublicPushServiceImpl implements PublicPushService {

    private Logger logger = LoggerFactory.getLogger(PublicPushServiceImpl.class);

    @Autowired
    private SystemMessageModelMapper systemMessageModelMapper;

    private boolean status;

    @Value("${push.appid}")
    private String appid;

    @Value("${push.appkey}")
    private String appkey;

    @Value("${push.mastersecret}")
    private String mastersecret;

    /**
     * 调用的时候组装数据
     * @param params
     */
    @Override
    public void pushMessage(Map params) {
        try{
            logger.error("推送开始，参数：{}",params);
            int storeId = DataUtils.getIntegerVal(params,"storeId");
            User user = (User) MapUtils.getObject(params,"user");
            String title = DataUtils.getStringVal(params,"title");
            String context = DataUtils.getStringVal(params,"context");
            String sender = DataUtils.getStringVal(params,"sender");

            SystemMessageModel systemMessageModel = new SystemMessageModel();
            systemMessageModel.setStore_id(storeId);
            systemMessageModel.setSenderid(sender);
            systemMessageModel.setRecipientid(user.getUser_id());
            systemMessageModel.setTitle(title);
            systemMessageModel.setTime(new Date());
            systemMessageModel.setContent(context);
            systemMessageModelMapper.insert(systemMessageModel);

            if(!status){
                logger.info("推送未开启");
                return;
            }

            JSONObject pushMsgJson = new JSONObject();
            pushMsgJson.put("title",title);
            pushMsgJson.put("content",context);
            pushMsgJson.put("payload",context);
            //个推推送
            String cid = user.getClientid();

            pushMsgJson.put("cid",cid);
            pushMsgJson.put("appid",appid);
            pushMsgJson.put("appkey",appkey);
            pushMsgJson.put("masterkey",mastersecret);
            pushToSingle(pushMsgJson.getInnerMap());
            logger.error(" 推送成功");
        }catch (Exception e){
            e.printStackTrace();
            logger.error(" 推送失败：{} ",e.getMessage());
        }


    }

    /**
     * 调用的时候组装数据即可
     * @param params
     */
    @Override
    public void pushMessageWechat(Map params) {
        try {
            String accessToken =  getAccessToken(params);
            RestTemplate restTemplate = new RestTemplate();
            //这里简单起见我们每次都获取最新的access_token（时间开发中，应该在access_token快过期时再重新获取）
            String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + accessToken;
            //拼接推送的模版
            WxMsgBean wxMssVo = new WxMsgBean();
            String openid = MapUtils.getString(params,"openid");
            //用户的openid（要发送给那个用户，通常这里应该动态传进来的）
            wxMssVo.setTouser(openid);
            //订阅消息模板id
            wxMssVo.setTemplate_id(MapUtils.getString(params,"tmpid"));
            wxMssVo.setPage(MapUtils.getString(params,"page"));
            Map<String, TemplateData> m = MapUtils.getMap(params,"data");
            wxMssVo.setData(m);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, wxMssVo, String.class);
            logger.info("推送订阅消息结果：{}",responseEntity.getBody());
        }catch (Exception e){
            e.printStackTrace();
            logger.error("推送订阅消息异常：{}",e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.ABNORMAL_BIZ,"推送订阅消息异常","pushMessageWechat");
        }
    }

    /**
     *
     * @param params
     * @return
     */
    public String getAccessToken(Map params) {
        RestTemplate restTemplate = new RestTemplate();
        params.put("APPID", appid);
        params.put("APPSECRET",appkey);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        String expires_in = object.getString("expires_in");
        logger.info("有效时长expires_in：{}" , expires_in);
        return Access_Token;
    }

    /**
     * 对单个用户推送消息
     * @param params
     */
    private void pushToSingle(Map params) {
        // 返回别名对应的每个cid的推送详情

    }




}
