package com.laiketui.api.modules.backend.app;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.weixin.AddWeiXinAppTemplateVo;
import com.laiketui.root.exception.LaiKeApiException;

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
    boolean addWeiXinGuideImage(MainVo vo, Integer id, String imgUrl, int sort, int type) throws LaiKeApiException;


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
     * @param vo      -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 17:31
     */
    Map<String, Object> getAuthorizationAppInfo(MainVo vo) throws LaiKeApiException;
}
