package com.laiketui.common.mapper;

import com.laiketui.domain.payment.PaymentModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 支付方式
 *
 * @author Trick
 * @date 2020/12/2 16:56
 */
public interface PaymentModelMapper extends BaseMapper<PaymentModel> {


    /**
     * 获取支付配置信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 16:59
     */
    List<Map<String, Object>> getPaymentConfigInfoDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取所有的支付状态
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/22 18:32
     */
    @Select("select c.status,p.class_name from lkt_payment_config c left join lkt_payment p on c.pid=p.id where c.store_id=#{storeId}")
    List<Map<String, Object>> getPaymentStatusAll(int storeId) throws LaiKeApiException;


    List<Map<String, Object>> getPaymentConfigInfo(Map<String, Object> map) throws LaiKeApiException;
    int countPaymentConfigInfo(Map<String, Object> map) throws LaiKeApiException;

}