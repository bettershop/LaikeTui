package com.laiketui.api.modules.backend.order;


import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 订单物流
 * @author wangxian
 */
public interface LogisticsService {

    /**
     * 物流信息
     * @param orderNo
     * @return
     * @throws LaiKeApiException
     */
    Map<String,Object> getLogistics(String orderNo) throws LaiKeApiException ;

}
