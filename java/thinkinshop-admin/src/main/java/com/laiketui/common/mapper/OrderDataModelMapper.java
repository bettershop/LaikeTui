package com.laiketui.common.mapper;

import com.laiketui.domain.order.OrderDataModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Update;

public interface OrderDataModelMapper extends BaseMapper<OrderDataModel> {

    @Update("update lkt_order_data set status=#{status} where id=#{id}")
    Integer updateStatus(int id, int status) throws LaiKeApiException;

}