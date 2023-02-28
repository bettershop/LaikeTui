package com.laiketui.common.mapper;

import com.laiketui.domain.mch.MchStoreModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 商户店铺 sql映射
 *
 * @author Trick
 * @date 2020/10/12 16:36
 */
public interface MchStoreModelMapper extends BaseMapper<MchStoreModel> {


    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取商户下门店信息+排序
     *
     * @param mchStoreModel -
     * @return List<Map < String, Object>>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/12 16:38
     */
    @Select("select * from lkt_mch_store where store_id = #{store_id} and mch_id = #{mch_id} order by id asc limit 1")
    List<Map<String, Object>> getMchStoreOrderBy(MchStoreModel mchStoreModel) throws LaiKeApiException;


    /**
     * 默认店铺转非默认
     *
     * @param storeId -
     * @param shopId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/30 15:51
     */
    @Update("update lkt_mch_store set is_default = 0 where store_id = #{storeId} and mch_id = #{shopId}")
    int updateNotDefault(int storeId, int shopId) throws LaiKeApiException;


    /**
     * 设置一个默认门店
     *
     * @param storeId -
     * @param shopId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/30 16:58
     */
    @Update("update lkt_mch_store set is_default = 1 where store_id = #{storeId} and mch_id = #{shopId} order by id limit 1")
    int setDefaultStore(int storeId, int shopId) throws LaiKeApiException;
}