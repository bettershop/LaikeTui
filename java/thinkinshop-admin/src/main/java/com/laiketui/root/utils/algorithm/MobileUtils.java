package com.laiketui.root.utils.algorithm;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.laiketui.domain.SmsMessageModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;

import java.util.Map;
import java.util.regex.Pattern;


/**
 * 手机服务工具
 *
 * @author Trick
 * @date 2020/9/23 11:17
 */
public class MobileUtils {

    /**
     * accessKeyId
     */
    private static final String ACCESS_KEY_ID = "LTAI4Fm8MFnadgaCdi6GGmkN";
    /**
     * accessKeySecret
     */
    private static final String ACCESS_KEY_SECRET = "NhBAJuGtx218pvTE4IBTZcvRzrFrH4";
    /**
     * 超时时间
     */
    private static final String OUT_TIME = "10000";
    /**
     * 短信API产品名称（短信产品名固定，无需修改）
     */
    private static final String PRODUCT = "Dysmsapi";
    /**
     * 短信API产品域名（接口地址固定，无需修改）
     */
    private static final String DOMAIN = "dysmsapi.aliyuncs.com";


    /**
     * 发送短信
     *
     * @param phone - 手机号 model - 短信模板 paramMap - 模板参数 - signName - 签名
     * @return SmsMessageModel -
     * @author Trick
     * @date 2020/9/23 15:50
     */
    public static SmsMessageModel sendSms(String phone, String model, String signName, String key, String secret, Map<String, String> paramMap) {
        SmsMessageModel mobileUtils;
        try {
            //设置请求超时时间
            System.setProperty("sun.net.client.defaultConnectTimeout", OUT_TIME);
            System.setProperty("sun.net.client.defaultReadTimeout", OUT_TIME);

            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", key, secret);
            IAcsClient client = new DefaultAcsClient(profile);

            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain(DOMAIN);
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("RegionId", "cn-hangzhou");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", signName);
            request.putQueryParameter("TemplateCode", model);
            request.putQueryParameter("SmsUpExtendCode", "123");
            request.putQueryParameter("TemplateParam", JSON.toJSONString(paramMap));
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            System.out.println(JSON.toJSONString(request.getSysQueryParameters()));
            mobileUtils = JSONObject.parseObject(response.getData(), SmsMessageModel.class);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.SysErrorCode.SEND_SMS_FAIL, model + " 短信发送失败!", "sendSms");
        }

        return mobileUtils;
    }


    /**
     * 校验手机是否合规 2020年最全的国内手机号格式
     *
     * @date 2020/9/23 11:17
     */
    private static final String REGEX_MOBILE = "((\\+86|0086)?\\s*)((134[0-8]\\d{7})|(((13([0-3]|[5-9]))|(14[5-9])|15([0-3]|[5-9])|(16(2|[5-7]))|17([0-3]|[5-8])|18[0-9]|19(1|[8-9]))\\d{8})|(14(0|1|4)0\\d{7})|(1740([0-5]|[6-9]|[10-12])\\d{7}))";

    /**
     * 校验手机号
     *
     * @param phone 手机号
     * @return boolean true:是  false:否
     */
    public static boolean isMobile(String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }
        return Pattern.matches(REGEX_MOBILE, phone);
    }
}
