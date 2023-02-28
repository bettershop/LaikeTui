package com.laiketui.api.modules.backend.plugin;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.main.AddStoreConfigVo;
import com.laiketui.domain.vo.mch.AddMchVo;
import com.laiketui.domain.vo.user.WithdrawalVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 店铺管理
 *
 * @author Trick
 * @date 2021/1/26 11:32
 */
public interface MchManageService {


    /**
     * 获取商城店铺列表
     *
     * @param vo     -
     * @param id     -
     * @param isOpen -
     * @param name   -
     * @param promiseStatus -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 11:38
     */
    Map<String, Object> getMchInfo(MainVo vo, Integer id, Integer isOpen, String name, Integer promiseStatus) throws LaiKeApiException;


    /**
     * 修改商户信息
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 14:05
     */
    boolean modifyMchInfo(AddMchVo vo) throws LaiKeApiException;


    /**
     * 删除店铺
     *
     * @param vo     -
     * @param shopId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 15:23
     */
    boolean delMchInfo(MainVo vo, int shopId) throws LaiKeApiException;


    /**
     * 获取商户审核信息
     *
     * @param vo           -
     * @param id           -
     * @param reviewStatus -
     * @param name         -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 15:51
     */
    Map<String, Object> getMchExamineInfo(MainVo vo, Integer id, Integer reviewStatus, String name, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 商户审核通过/拒绝
     *
     * @param vo           -
     * @param mchId        -
     * @param reviewStatus -
     * @param text         -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 16:20
     */
    boolean examineMch(MainVo vo, int mchId, Integer reviewStatus, String text) throws LaiKeApiException;


    /**
     * 获取商品审核列表
     *
     * @param vo        -
     * @param mchName   -
     * @param goodsName -
     * @param goodsId   -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 16:40
     */
    Map<String, Object> getGoodsExamineInfo(MainVo vo, String mchName, String goodsName, Integer goodsId) throws LaiKeApiException;


    /**
     * 获取商品详细信息
     *
     * @param vo      -
     * @param goodsId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 17:26
     */
    Map<String, Object> getGoodsDetailInfo(MainVo vo, int goodsId) throws LaiKeApiException;


    /**
     * 商品审核
     *
     * @param vo      -
     * @param goodsId -
     * @param status  -
     * @param text    -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/27 13:42
     */
    boolean goodsExamine(MainVo vo, int goodsId, int status, String text) throws LaiKeApiException;


    /**
     * 获取提现列表
     *
     * @param vo     -
     * @param status -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/27 15:36
     */
    Map<String, Object> getWithdrawalInfo(WithdrawalVo vo, Integer status, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 店铺提现审核
     *
     * @param vo     -
     * @param id     -
     * @param stauts -
     * @param text   -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/27 15:59
     */
    boolean withdrawalExamineMch(MainVo vo, int id, int status, String text) throws LaiKeApiException;


    /**
     * 获取商城设置信息
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/27 17:43
     */
    Map<String, Object> getStoreConfigInfo(MainVo vo) throws LaiKeApiException;


    /**
     * 设置商城配置
     *
     * @param vo -
     * @return LaiKeApiException
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/27 17:47
     */
    boolean setStoreConfigInfo(AddStoreConfigVo vo) throws LaiKeApiException;
}
