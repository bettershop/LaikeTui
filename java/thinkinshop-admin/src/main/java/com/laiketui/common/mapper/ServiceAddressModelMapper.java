package com.laiketui.common.mapper;

import com.laiketui.domain.mch.ServiceAddressModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 地址管理
 *
 * @author Trick
 * @date 2021/1/15 9:58
 */
public interface ServiceAddressModelMapper extends BaseMapper<ServiceAddressModel> {


    /**
     * 设置默认地址
     *
     * @param storeId -
     * @param type    -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 10:04
     */
    @Update("UPDATE lkt_service_address a, " +
            "( SELECT x.id FROM lkt_service_address x WHERE x.store_id = #{storeId} AND x.type = #{type} AND x.uid = 'admin' AND NOT EXISTS ( SELECT temp.id FROM lkt_service_address temp WHERE temp.store_id = x.store_id AND temp.type = x.type AND temp.uid = x.uid AND temp.is_default = 1 ) ORDER BY x.id LIMIT 1 ) b " +
            " SET a.is_default = 1 WHERE a.id = b.id")
    int setDefualtAddress(int storeId, int type) throws LaiKeApiException;



    /**
     * 清空默认
     *
     * @param storeId -
     * @param type    -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 11:09
     */
    @Update("update lkt_service_address set is_default = 0 where store_id = #{storeId} and type=#{type}")
    int updateDefualtClear(int storeId, int type) throws LaiKeApiException;

    /**
     * 获取地址信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 10:12
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取地址信息-统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 10:12
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


}