package com.laiketui.common.utils;

import com.laiketui.api.common.order.OrderDubboService;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.utils.help.SpringHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.laiketui.root.consts.ErrorCode.BizErrorCode.PARAMATER_ERROR;

/**
 * 订单流程工厂
 *
 * @author Trick
 * @date 2021/4/12 15:21
 */
@Component
public class OrderServiceFactory {

    @Autowired
    private SpringHelper springHelper;

    /**
     * 获取订单实现
     *
     * @param orderType -
     * @return OrderDubboService
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/12 15:24
     */
    public OrderDubboService getOrderService(String orderType) throws LaiKeApiException {
        OrderDubboService orderDubboService;
        switch (orderType) {
            case DictionaryConst.OrdersType.ORDERS_HEADER_GM:
                orderDubboService = springHelper.getBean(com.laiketui.common.service.dubbo.order.OrderDubboServiceImpl.class);
                break;
            default:
                throw new LaiKeApiException(PARAMATER_ERROR, "参数错误");
        }
        return orderDubboService;
    }

}
