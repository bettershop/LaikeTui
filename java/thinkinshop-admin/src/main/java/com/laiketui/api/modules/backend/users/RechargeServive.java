package com.laiketui.api.modules.backend.users;

import com.laiketui.domain.vo.SaveAddressVo;
import com.laiketui.domain.vo.user.RechargeVo;
import com.laiketui.domain.vo.user.UserMoneyVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 充值列表
 *
 * @author Trick
 * @date 2021/1/11 15:49
 */
public interface RechargeServive {


    /**
     * 获取充值列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 15:50
     */
    Map<String, Object> getRechargeInfo(RechargeVo vo) throws LaiKeApiException;


    /**
     * 获取用户资金列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 16:13
     */
    Map<String, Object> getUserMoneyInfo(UserMoneyVo vo) throws LaiKeApiException;


    /**
     * 保存地址
     *
     * @param vo     -
     * @param userId -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 17:06
     */
    void saveAddress(SaveAddressVo vo, String userId) throws LaiKeApiException;
}
