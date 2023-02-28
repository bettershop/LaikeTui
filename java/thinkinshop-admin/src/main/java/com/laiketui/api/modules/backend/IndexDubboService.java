package com.laiketui.api.modules.backend;


import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.DefaultViewVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 首页
 *
 * @author Trick
 * @date 2020/12/28 14:34
 */
public interface IndexDubboService {

    /**
    * 商城首页
    *
    * @param vo -
    * @return Map
    * @throws LaiKeApiException-
    * @author Trick
    * @date 2021/8/13 17:18
    */
    Map<String, Object> home(MainVo vo, Integer mchId) throws LaiKeApiException;

    /**
     * 首页
     * 【php JurisdictionAction.getDefaultView】
     *
     * @param vo       -
     * @param response -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/28 14:37
     */
    Map<String, Object> index(DefaultViewVo vo, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 获取商品状态信息
     * 【php JurisdictionAction.execute】
     *
     * @param vo  -
     * @param ids -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/28 16:07
     */
    Map<String, Object> goodsStatus(MainVo vo, String ids) throws LaiKeApiException;


    /**
     * 是否显示下架商品
     * 【php JurisdictionAction.is_open】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/28 16:44
     */
    Map<String, Object> isopen(MainVo vo) throws LaiKeApiException;
}
