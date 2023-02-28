package com.laiketui.api.common.third;

import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 第三方处理接口
 *
 * @author Trick
 * @date 2021/1/20 16:05
 */
public interface PublicThirdService {


    /**
     * 更新授权token的component_access_token
     *
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 16:06
     */
    String componentAccessToken() throws LaiKeApiException;

    /**
     * 更新授权token的component_access_token
     *
     * @param componentVerifyTicket -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 16:06
     */
    String componentAccessToken(String componentVerifyTicket) throws LaiKeApiException;


    /**
     * 更新授权小程序的authorizer_access_token
     *
     * @param storeId -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 16:07
     */
    String authorizerAccessToken(int storeId) throws LaiKeApiException;


    /**
     * 获取预授权码
     *
     * @param appid -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 15:46
     */
    String getPreAuthorizationCode(String appid) throws LaiKeApiException;


    /**
     * 获取发布审核单的审核状态
     *
     * @param storeId -
     * @param auditid -审核单id
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 17:43
     */
    Map<String, String> getAuditstatus(int storeId, String auditid) throws LaiKeApiException;


    /**
     * 上传小程序代码
     *
     * @param storeId    -
     * @param templateId -
     * @param appid      -
     * @param apiUrl     -
     * @param kefuUrl    -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 16:42
     */
    String uploadCode(int storeId, String templateId, String appid, String apiUrl, String kefuUrl) throws LaiKeApiException;


    /**
     * 设置服务器域名
     *
     * @param storeId -
     * @param token   -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 17:18
     */
    boolean setServeDomain(int storeId, String token) throws LaiKeApiException;


    /**
     * 获取审核时可填写的类目信息
     *
     * @param token -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 17:48
     */
    Map<String, String> getExamineCategory(String token) throws LaiKeApiException;


    /**
     *  获取已上传的代码的页面列表
     * @author Trick
     * @date 2021/1/21 18:09
     * @param token -
     * @return Map
     * @throws   LaiKeApiException   -
     */
    Map<String, String> getUploadCodePageList(String token) throws LaiKeApiException;

    /**
     * 提交审核
     *
     * @param storeId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 17:45
     */
    boolean submitReview(int storeId) throws LaiKeApiException;
}
