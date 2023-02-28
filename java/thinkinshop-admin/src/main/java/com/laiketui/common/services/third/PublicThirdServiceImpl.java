package com.laiketui.common.services.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.common.mapper.ThirdMiniInfoModelMapper;
import com.laiketui.common.mapper.ThirdModelMapper;
import com.laiketui.domain.home.ThirdModel;
import com.laiketui.domain.weixin.ThirdMiniInfoModel;
import com.laiketui.api.common.third.PublicThirdService;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.okhttp.HttpUtils;
import com.laiketui.root.utils.tool.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 第三方处理
 *
 * @author Trick
 * @date 2021/1/20 16:04
 */
@Service
public class PublicThirdServiceImpl implements PublicThirdService {
    private final Logger logger = LoggerFactory.getLogger(PublicThirdServiceImpl.class);

    @Override
    public String componentAccessToken() throws LaiKeApiException {
        String token;
        try {
            token = componentAccessToken(null);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新component_access_token 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "componentAccessToken");
        }
        return token;
    }

    @Override
    public String componentAccessToken(String componentVerifyTicket) throws LaiKeApiException {
        String token = null;
        try {
            ThirdModel thirdModel = thirdModelMapper.selectByPrimaryKey(1);
            if (thirdModel != null) {
                //appid
                String appid = thirdModel.getAppid();
                //密钥
                String appsecret = thirdModel.getAppsecret();
                //凭据
                String ticket;
                if (StringUtils.isEmpty(componentVerifyTicket)) {
                    ticket = thirdModel.getTicket();
                } else {
                    ticket = componentVerifyTicket;
                }
                //token失效时间戳
                Date outDate = new Date(thirdModel.getToken_expires());

                //原来token
                token = thirdModel.getToken();

                //判断是否失效
                if (DateUtil.dateCompare(new Date(), outDate) || !StringUtils.isEmpty(componentVerifyTicket)) {
                    //重新获取
                    String url = GloabConst.WeiXinUrl.COMPONENT_ACCESS_TOKEN_POST;
                    //装载参数
                    Map<String, String> parmaMap = new HashMap<>(16);
                    parmaMap.put("component_appid", appid);
                    parmaMap.put("component_appsecret", appsecret);
                    parmaMap.put("component_verify_ticket", ticket);
                    String resultJson = HttpUtils.post(url, JSON.toJSONString(parmaMap));
                    Map<String, String> resultMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
                    });
                    if (resultMap.containsKey("component_access_token")) {
                        token = resultMap.get("component_access_token");
                        //修改过期的token
                        ThirdModel thirdModelUpdate = new ThirdModel();
                        thirdModelUpdate.setId(thirdModel.getId());
                        thirdModelUpdate.setToken(token);
                        thirdModelUpdate.setToken_expires(DateUtil.getAddDateBySecond(new Date(), 3600).getTime());
                        int count = thirdModelMapper.updateByPrimaryKeySelective(thirdModelUpdate);
                        logger.debug("更新 component_access_token 状态{}", count > 0);
                    } else {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "请求授权令牌失败:" + resultJson);
                    }
                } else {
                    //未失效
                    return token;
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新component_access_token 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "componentAccessToken");
        }
        return token;
    }

    @Override
    public String authorizerAccessToken(int storeId) throws LaiKeApiException {
        String token = null;
        try {
            ThirdModel thirdModel = thirdModelMapper.selectByPrimaryKey(1);
            if (thirdModel != null) {
                //appid
                String appid = thirdModel.getAppid();
                //小程序基本信息及发布状态
                ThirdMiniInfoModel thirdMiniInfoModel = new ThirdMiniInfoModel();
                thirdMiniInfoModel.setStore_id(storeId);
                thirdMiniInfoModel = thirdMiniInfoModelMapper.selectOne(thirdMiniInfoModel);
                if (thirdMiniInfoModel != null) {
                    //token失效时间戳
                    Date outDate = new Date(thirdMiniInfoModel.getExpires_time());
                    //原来token
                    token = thirdMiniInfoModel.getAuthorizer_access_token();
                    if (DateUtil.dateCompare(new Date(), outDate)) {
                        //失效重新获取
                        String url = String.format(GloabConst.WeiXinUrl.API_AUTHORIZER_TOKEN_POST, this.componentAccessToken());
                        //装载参数
                        Map<String, String> parmaMap = new HashMap<>(16);
                        parmaMap.put("component_appid", appid);
                        parmaMap.put("authorizer_appid", thirdMiniInfoModel.getAuthorizer_appid());
                        parmaMap.put("authorizer_refresh_token", thirdMiniInfoModel.getAuthorizer_refresh_token());
                        String resultJson = HttpUtils.post(url, JSON.toJSONString(parmaMap));
                        Map<String, String> resultMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
                        });
                        if (resultMap.containsKey("authorizer_access_token")) {
                            ThirdMiniInfoModel thirdMiniInfoModelUpdate = new ThirdMiniInfoModel();
                            thirdMiniInfoModelUpdate.setId(thirdMiniInfoModel.getId());
                            thirdMiniInfoModelUpdate.setAuthorizer_expires(GloabConst.WeiXinUrl.ACCESSTOKEN_EXPIRES);
                            thirdMiniInfoModelUpdate.setAuthorizer_access_token(resultMap.get("authorizer_access_token"));
                            thirdMiniInfoModelUpdate.setAuthorizer_refresh_token(resultMap.get("authorizer_refresh_token"));
                            thirdMiniInfoModelMapper.updateByPrimaryKeySelective(thirdMiniInfoModel);

                            token = thirdMiniInfoModel.getAuthorizer_access_token();
                        } else {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取/刷新接口调用令牌失败:" + resultJson);
                        }
                    } else {
                        //未失效
                        return token;
                    }
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("更新authorizer_access_token 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "authorizerAccessToken");
        }
        return token;
    }


    @Override
    public String getPreAuthorizationCode(String appid) throws LaiKeApiException {
        String code;
        try {
            //获取预授权码
            String componentToken = this.componentAccessToken();
            String url = String.format(GloabConst.WeiXinUrl.API_CREATE_PREAUTHCODE_POST, componentToken);
            //装载参数
            Map<String, String> parmaMap = new HashMap<>(16);
            parmaMap.put("component_appid", appid);
            String resultJson = HttpUtils.post(url, JSON.toJSONString(parmaMap));
            Map<String, String> resultMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            if (resultMap.containsKey("pre_auth_code")) {
                code = resultMap.get("pre_auth_code");
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "请求预授权码失败:" + resultJson);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取小程序预授权码 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWeiXinPreAuthorizationCode");
        }
        return code;
    }

    @Override
    public Map<String, String> getAuditstatus(int storeId, String auditid) throws LaiKeApiException {
        Map<String, String> resultMap = new HashMap<>(16);
        try {
            String authorizerToken = this.authorizerAccessToken(storeId);
            String url = String.format(GloabConst.WeiXinUrl.GET_AUDITSTATUS_POST, authorizerToken);
            //装载参数
            Map<String, String> parmaMap = new HashMap<>(16);
            parmaMap.put("auditid", auditid);
            String resultJson = HttpUtils.post(url, JSON.toJSONString(parmaMap));
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            if (!resultJsonMap.containsKey("errcode")) {
                resultMap.put("status", resultJsonMap.get("status"));
                //当 status = 1 时，返回的拒绝原因; status = 4 时，返回的延后原因
                resultMap.put("reason", resultJsonMap.get("reason"));
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "请求审核状态失败:" + resultJson);
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取审核单审核状态 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAuditstatus");
        }
        return resultMap;
    }

    @Override
    public String uploadCode(int storeId, String templateId, String appid, String apiUrl, String kefuUrl) throws LaiKeApiException {
        try {
            //获取授权token
            String token = authorizerAccessToken(storeId);
            //提交url
            String url = String.format(GloabConst.WeiXinUrl.CODE_COMMIT_POST, token);
            //自定义配置
            String extJson;
            Map<String, Object> extConfigMap = new HashMap<>(16);
            extConfigMap.put("extEnable", true);
            extConfigMap.put("extAppid", appid);
            Map<String, String> extMap = new HashMap<>(16);
            extMap.put("url", apiUrl);
            extMap.put("kefu_url", kefuUrl);
            extConfigMap.put("ext", JSON.toJSONString(extMap));
            extJson = JSON.toJSONString(extConfigMap);

            //装载参数
            Map<String, String> parmaMap = new HashMap<>(16);
            parmaMap.put("user_version", "v1.0.0");
            parmaMap.put("user_desc", "来客官方授权");
            parmaMap.put("template_id", templateId);
            parmaMap.put("ext_json", extJson);

            String resultJson = HttpUtils.post(url, JSON.toJSONString(parmaMap));
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            System.out.println(resultJson);
            if ("0".equals(resultJsonMap.get("errcode"))) {
                return GloabConst.ManaValue.MANA_VALUE_SUCCESS;
            } else {
                return resultJson;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传小程序代码 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadCode");
        }
    }

    @Override
    public boolean setServeDomain(int storeId, String token) throws LaiKeApiException {
        try {
            //获取配置服务器域名
            ThirdModel thirdModel = new ThirdModel();
            thirdModel.setId(storeId);
            thirdModel = thirdModelMapper.selectOne(thirdModel);
            if (thirdModel == null || StringUtils.isEmpty(thirdModel.getServe_domain())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "小程序配置信息不存在");
            }
            //数据处理
            String serverDomain = thirdModel.getServe_domain();
            List<String> httpsList = new ArrayList<>();
            List<String> wssList = new ArrayList<>();
            String[] domainList = serverDomain.split(",");
            for (String domain : domainList) {
                domain = GloabConst.HttpProtocol.HTTPS + GloabConst.HttpProtocol.SYMBOL + domain;
                httpsList.add(domain);
                wssList.add(domain);
            }

            //设置服务器域名
            String setServerUrl = String.format(GloabConst.WeiXinUrl.MODIFY_DOMAIN_POST, token);
            //装载参数
            Map<String, String> parmaMap = new HashMap<>(16);
            parmaMap.put("action", "set");
            parmaMap.put("requestdomain", JSON.toJSONString(httpsList));
            parmaMap.put("wsrequestdomain", JSON.toJSONString(wssList));
            parmaMap.put("uploaddomain", JSON.toJSONString(httpsList));
            parmaMap.put("downloaddomain", JSON.toJSONString(httpsList));
            String resultJson = HttpUtils.post(setServerUrl, JSON.toJSONString(parmaMap));
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            logger.debug("设置服务器域名,接口返回结果:{}", resultJson);
            return "0".equals(resultJsonMap.get("errcode"));
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置服务器域名 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setServeDomain");
        }
    }

    @Override
    public Map<String, String> getExamineCategory(String token) throws LaiKeApiException {
        try {
            String url = String.format(GloabConst.WeiXinUrl.GET_EXAMINE_CATEGORY, token);
            String resultJson = HttpUtils.get(url);
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            System.out.println(resultJson);
            return resultJsonMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取类目 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getCategory");
        }
    }

    @Override
    public Map<String, String> getUploadCodePageList(String token) throws LaiKeApiException {
        try {
            String url = String.format(GloabConst.WeiXinUrl.GET_UPLOAD_CODE_PAGE_LIST, token);
            String resultJson = HttpUtils.get(url);
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            System.out.println(resultJson);
            return resultJsonMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取已上传的代码的页面列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUploadCodePageList");
        }
    }

    @Override
    public boolean submitReview(int storeId) throws LaiKeApiException {
        try {
            String authorToken = authorizerAccessToken(storeId);
            //获取类目
            Map<String, String> categoryMap = getExamineCategory(authorToken);
            String firstClass = null;
            String secondClass = null;
            String firstId = null;
            String secondId = null;
            if ("0".equals(categoryMap.get("errcode"))) {
                firstClass = categoryMap.get("first_class");
                secondClass = categoryMap.get("second_class");
                firstId = categoryMap.get("first_id");
                secondId = categoryMap.get("second_id");
            }
            //获取已上传的代码的页面列表
            Map<String, String> getUploadCodePageMap = getExamineCategory(authorToken);
            String address = "";
            if ("0".equals(getUploadCodePageMap.get("errcode"))) {
                List<String> codeList = JSON.parseObject(categoryMap.get("page_list"), new TypeReference<List<String>>() {
                });
                address = codeList.get(0);
            }
            //开始提交审核
            String submitUrl = String.format(GloabConst.WeiXinUrl.SUBMIT_AUDIT_POST, authorToken);
            //装载参数
            Map<String, Object> parmaMap = new HashMap<>(16);
            List<Map<String, String>> itemList = new ArrayList<>();
            Map<String, String> itemListMap = new HashMap<>(16);
            itemListMap.put("address", address);
            itemListMap.put("tag", "电商");
            itemListMap.put("first_class", firstClass);
            itemListMap.put("second_class", secondClass);
            itemListMap.put("first_id", firstId);
            itemListMap.put("second_id", secondId);
            itemListMap.put("title", "youyou授权");
            itemList.add(itemListMap);
            parmaMap.put("item_list", JSON.toJSONString(itemList));
            logger.debug("审核参数数据结构:{}", JSON.toJSONString(parmaMap));
            String resultJson = HttpUtils.post(submitUrl, JSON.toJSONString(parmaMap));
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            if ("0".equals(resultJsonMap.get("errcode"))) {
                //获取审核编号
                String auditid = resultJsonMap.get("auditid");
                ThirdMiniInfoModel thirdMiniInfoModelUpdate = new ThirdMiniInfoModel();
                thirdMiniInfoModelUpdate.setStore_id(storeId);
                thirdMiniInfoModelUpdate.setAuditid(auditid);
                thirdMiniInfoModelUpdate.setIssue_mark(1);
                int count = thirdMiniInfoModelMapper.updateByPrimaryKeySelective(thirdMiniInfoModelUpdate);
                if (count > 0) {
                    return true;
                }
                logger.error("更新小程序审核编号失败");
            }
            return false;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("提交审核 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "submitReview");
        }
    }

    @Autowired
    private ThirdModelMapper thirdModelMapper;

    @Autowired
    private ThirdMiniInfoModelMapper thirdMiniInfoModelMapper;
}
