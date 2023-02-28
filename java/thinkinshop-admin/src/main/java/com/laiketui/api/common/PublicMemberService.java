package com.laiketui.api.common;

import com.laiketui.root.exception.LaiKeApiException;

import java.math.BigDecimal;

/**
 * 会员通用方法
 *
 * @author wangxian
 */
public interface PublicMemberService {

    /**
     * 获取用户优惠
     *
     * @param productType
     * @param userId
     * @param storeId
     * @return
     */
    double getMemberGradeRate(String productType, String userId, int storeId);

    /**
     * 会员结算
     *
     * @param storeId -
     * @param userId  -
     * @param sNo     - 订单号
     * @param zPrice  - 订单总金额
     * @param type    - 1=支付后 0=收货后
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/11/11 9:53
     */
    void memberSettlement(int storeId, String userId, String sNo, BigDecimal zPrice, int type) throws LaiKeApiException;


    /**
     * 钱包退还
     * 【php RefundUtils.return_user_money】
     *
     * @param storeId -
     * @param userId  -
     * @param price   -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 17:13
     */
    void returnUserMoney(int storeId, String userId, BigDecimal price) throws LaiKeApiException;
}
