package com.laiketui.api.modules.backend.order;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.OrderSettlementVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 订单结算
 *
 * @author Trick
 * @date 2021/7/7 11:31
 */
public interface OrderSettlementService {

    /**
     * 订单结算 列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/7 11:38
     */
    Map<String, Object> index(OrderSettlementVo vo, HttpServletResponse response) throws LaiKeApiException;

    /**
     * 订单详情
     *
     * @param vo      -
     * @param orderNo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/7 14:51
     */
    Map<String, Object> detail(MainVo vo, String orderNo) throws LaiKeApiException;

    /**
     * 删除结算订单
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/7 15:04
     */
    void del(MainVo vo, int id) throws LaiKeApiException;
}
