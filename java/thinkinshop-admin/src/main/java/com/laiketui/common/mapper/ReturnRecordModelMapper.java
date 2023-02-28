package com.laiketui.common.mapper;

import com.laiketui.domain.order.ReturnRecordModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;


/**
 * 售后信息记录表
 *
 * @author Trick
 * @date 2020/12/4 11:52
 */
public interface ReturnRecordModelMapper extends BaseMapper<ReturnRecordModel> {


    /**
     * 获取订单最后一条售后信息
     *
     * @param storeId -
     * @param oid     - 订单详情id
     * @return ReturnRecordModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/4 11:54
     */
    @Select("select * from lkt_return_record where store_id = #{storeId} and p_id = #{oid} order by re_time desc limit 1")
    ReturnRecordModel getReturnRecordByMax(int storeId, int oid) throws LaiKeApiException;

}