package com.laiketui.common.mapper;

import com.laiketui.domain.product.FreightModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 运费 sql
 *
 * @author Trick
 * @date 2020/11/16 11:03
 */
public interface FreightModelMapper extends BaseMapper<FreightModel> {


    /**
     * 获取运费信息
     *
     * @param freightModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/16 11:05
     */
    @Select("select id,name from lkt_freight where store_id = #{store_id} and mch_id = #{mch_id} order by id")
    List<Map<String, Object>> getFreightInfo(FreightModel freightModel) throws LaiKeApiException;


    /**
     * 动态统计运费
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 15:08
     */
    int countFreightDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 设置默认
     *
     * @param storeId       -
     * @param mchId         -
     * @param updateDefault -
     * @param isDefault     -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 17:15
     */
    @Update("update lkt_freight set is_default = #{updateDefault} where store_id = #{storeId} and mch_id = #{mchId} and is_default = #{isDefault}")
    int setDefault(int storeId, int mchId, int updateDefault, int isDefault) throws LaiKeApiException;


    /**
     * 获取运费商品信息动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 15:18
     */
    List<Map<String, Object>> getFreightInfoLeftGoodsDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取运费商品信息动态sql 统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/3 13:49
     */
    int countFreightInfoLeftGoodsDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 运费信息动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 10:18
     */
    List<Map<String, Object>> getFreightInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

}