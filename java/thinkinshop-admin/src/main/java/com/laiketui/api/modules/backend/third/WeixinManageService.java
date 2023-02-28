package com.laiketui.api.modules.backend.third;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.weixin.AddWeiXinAppTemplateVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 微信管理
 *
 * @author Trick
 * @date 2021/1/19 16:33
 */
public interface WeixinManageService {


    /**
     * 获取微信引导图列表
     *
     * @param vo -
     * @param id -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 16:34
     */
    Map<String, Object> getWeiXinGuideImageInfo(MainVo vo, Integer id) throws LaiKeApiException;


    /**
     * 添加/编辑微信引导图
     *
     * @param vo     -
     * @param id     -
     * @param imgUrl -
     * @param sort   -
     * @param type   -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 16:58
     */
    boolean addWeiXinGuideImage(MainVo vo, Integer id, String imgUrl, Integer sort, int type) throws LaiKeApiException;


    /**
     * 删除引导图
     *
     * @param vo -
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 17:07
     */
    boolean delWeiXinGuideImage(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 获取模板设置
     *
     * @param vo           -
     * @param id           -
     * @param templateType -
     * @param isUse        -
     * @param templateName -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 10:24
     */
    Map<String, Object> getWeiXinTemplateInfo(MainVo vo, Integer id, Integer templateType, Integer isUse, String templateName) throws LaiKeApiException;


    /**
     * 添加/编辑微信模板
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 11:37
     */
    boolean addWeiXinTemplate(AddWeiXinAppTemplateVo vo) throws LaiKeApiException;


    /**
     * 停用/启用 模板
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 12:11
     */
    boolean setUseWeiXinTemplate(int id) throws LaiKeApiException;

    /**
     * 删除微信模板
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 12:06
     */
    boolean delWeiXinTemplate(int id) throws LaiKeApiException;

    /**
     * 获取微信预授权码
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 14:18
     */
    Map<String, Object> getPreAuthorizationCode(MainVo vo) throws LaiKeApiException;


    /**
     * 获取已授权的app信息
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 17:31
     */
    Map<String, Object> getAuthorizationAppInfo(MainVo vo) throws LaiKeApiException;


    /**
     * 授权事件接收URL.在第三方平台创建审核通过后，微信服务器会向其 ”授权事件接收URL” 每隔 10 分钟以 POST 的方式推送 component_verify_ticket
     * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/api/component_verify_ticket.html
     *
     * @param request  -
     * @param response -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 10:22
     */
    String ticketCallBack(HttpServletRequest request, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 撤销审核
     *
     * @param storeId -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 14:27
     */
    String revokeExamine(int storeId) throws LaiKeApiException;


    /**
     * 小程序提交审核
     *
     * @param storeId    -
     * @param templateId -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 16:30
     */
    String submitAppExamine(int storeId, String templateId) throws LaiKeApiException;

    /**
     * 删除小程序
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 14:36
     */
    boolean delThirdMiniApp(int id) throws LaiKeApiException;


    /**
     * 下载普通二维码
     *
     * @param storeId -
     * @param width   -
     * @param type    - 1=普通二维码 2=小程序码 3=体验版
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/21 14:44
     */
    String downQrcode(int storeId, int width, int type) throws LaiKeApiException;


}
