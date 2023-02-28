package com.laiketui.api.common;

import com.laiketui.domain.vo.main.RefundVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 公共售后接口
 * php>RefundUtils
 *
 * @author Trick
 * @date 2020/12/2 11:43
 */
public interface PublicRefundService {


    /**
     * 售后
     * 【php RefundUtils.refund】
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 12:04
     */
    boolean refund(RefundVo vo) throws LaiKeApiException;


    /**
     * 获取售后详细信息
     * 【php RefundUtils.refund_page】
     *
     * @param storeId -
     * @param id      - 售后id
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 10:58
     */
    Map<String, Object> refundPageById(int storeId, int id) throws LaiKeApiException;

    /**
     * 获取售后状态 - 订单
     *
     * @param storeId -
     * @param orderNo -
     * @return String
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/28 16:56
     */
    String getRefundStatus(int storeId, String orderNo) throws LaiKeApiException;

    /**
     * 获取售后状态 - 订单商品
     *
     * @param storeId  -
     * @param detailId -
     * @return String
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/28 16:56
     */
    String getRefundStatus(int storeId, int detailId) throws LaiKeApiException;
}
