package com.laiketui.api.modules.backend.saas;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.saas.AddShopVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 商户管理
 *
 * @author Trick
 * @date 2021/1/28 9:20
 */
public interface ShopManageService {

    /**
     * 获取商城列表
     *
     * @param storeId   -
     * @param storeName -
     * @param startDate -
     * @param endDate   -
     * @param pageModel -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 9:21
     */
    Map<String, Object> getShopInfo(MainVo vo, String storeName, String startDate, String endDate, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 是否启用开关
     *
     * @param storeId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 11:10
     */
    boolean setStoreOpenSwitch(Integer storeId) throws LaiKeApiException;


    /**
     * 设置默认商城
     *
     * @param storeId -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/11 14:39
     */
    void setStoreDefaultSwitch(Integer storeId) throws LaiKeApiException;


    /**
     * 添加/编辑商城
     *
     * @param vo -
     * @param ip -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 11:42
     */
    boolean addStore(AddShopVo vo, String ip) throws LaiKeApiException;


    /**
     * 删除商城
     *
     * @param storeId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 15:01
     */
    boolean delStore(int storeId) throws LaiKeApiException;


    /**
     * 重置管理员密码
     *
     * @param adminId -
     * @param pwd     -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 15:25
     */
    boolean resetAdminPwd(int adminId, String pwd) throws LaiKeApiException;

    /**
     * 重置管理员密码
     *
     * @param adminId -
     * @param pwd     -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 15:25
     */
    boolean resetAdminPswd(int adminId, String pwd) throws LaiKeApiException;

    /**
     * 添加管理员
     * @param u
     * @param p
     * @return
     * @throws LaiKeApiException
     */
    boolean addAdmin(String u, String p) throws LaiKeApiException;
}
