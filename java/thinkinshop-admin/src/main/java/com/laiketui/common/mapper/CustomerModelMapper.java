package com.laiketui.common.mapper;

import com.laiketui.domain.mch.CustomerModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 商城 sql
 *
 * @author Trick
 * @date 2020/11/11 9:09
 */
public interface CustomerModelMapper extends BaseMapper<CustomerModel> {

    /**
     * 获取商城信息动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 9:38
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取商城信息动态sql - 统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 9:38
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 唯一字段验证
     *
     * @param email          -
     * @param name           -
     * @param customerNumber -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 13:52
     */
    @Select("select count(1) from lkt_customer where recycle = 0 and (email = #{email} OR name =#{name} OR customer_number = #{customerNumber})")
    int onlyVerification(String email, String name, String customerNumber) throws LaiKeApiException;


    /**
     * 获取自营店id
     *
     * @param storeId -
     * @return Integer
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/23 9:58
     */
    @Select("select b.shop_id from lkt_customer as a left join lkt_admin as b on a.admin_id = b.id where store_id = #{storeId}")
    Integer getStoreMchId(int storeId) throws LaiKeApiException;


    /**
     * 清除所有默认
     *
     * @return Integer
     * @author Trick
     * @date 2021/6/11 14:53
     */
    @Update("update lkt_customer set is_default=0 where is_default=1")
    Integer removeDefault();


    /**
     * 设置一个为默认
     *
     * @return Integer
     * @author Trick
     * @date 2021/6/11 14:53
     */
    @Update("update lkt_customer a,(select id from lkt_customer x where x.recycle=0 order by add_date desc limit 1) b set a.is_default=1 where a.is_default=0 and a.id = b.id")
    Integer setDefault();

}