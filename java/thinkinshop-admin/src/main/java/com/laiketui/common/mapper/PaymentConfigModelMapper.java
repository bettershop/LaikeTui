package com.laiketui.common.mapper;

import com.laiketui.domain.payment.PaymentConfigModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 支付配置参数
 *
 * @author Trick
 * @date 2020/10/9 18:09
 */
public interface PaymentConfigModelMapper extends BaseMapper<PaymentConfigModel> {


    /**
     * 获取支付参数
     *
     * @param storeId -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/9 18:09
     */
    @Select("SELECT c.config_data FROM lkt_payment as p " +
            "left join lkt_payment_config as c on c.pid = p.id and c.store_id = #{storeId} " +
            "where p.class_name = 'jsapi_wechat'")
    String getPaymentConfigData(int storeId) throws LaiKeApiException;


    /**
     * 获取支付信息动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 16:31
     */
    List<Map<String, Object>> getPaymentConfigDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 支付配置信息
     *
     * @param storeId
     * @return
     * @throws LaiKeApiException
     */
    @Select("select config_data from lkt_payment as p left join lkt_payment_config as c on c.pid = p.id " +
            "where c.store_id= #{storeId} and c.status = 1  and p.status = 1 and p.class_name = #{className} ")
    String getPaymentConfigInfo(int storeId, String className) throws LaiKeApiException;


    /**
     * 获取支付方式状态
     *
     * @param storeId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/21 17:45
     */
    @Select("select c.status,p.class_name from lkt_payment_config c left join lkt_payment p on c.pid=p.id where c.store_id=#{storeId}")
    List<Map<String, Object>> getPaymentTypeInfo(int storeId) throws LaiKeApiException;

    @Update("UPDATE lkt_payment_config SET status = #{status} WHERE pid = #{pid} and store_id=#{storeId} ")
    int updateStaus (int pid,int storeId,int status);

}