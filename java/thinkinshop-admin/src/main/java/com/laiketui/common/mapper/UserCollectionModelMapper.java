package com.laiketui.common.mapper;

import com.laiketui.domain.mch.UserCollectionModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 我的收藏 sql
 *
 * @author Trick
 * @date 2020/11/9 9:15
 */
public interface UserCollectionModelMapper extends BaseMapper<UserCollectionModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取用户收藏的商品
     *
     * @param userCollectionModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/9 9:17
     */
    @Select("SELECT c.id, c.p_id, a.product_title, a.mch_id FROM lkt_user_collection AS c" +
            " LEFT JOIN lkt_product_list AS a ON c.p_id = a.id " +
            " WHERE a.store_id = #{store_id} AND c.type = 1 AND c.user_id = #{user_id}" +
            " ORDER BY c.add_time DESC ")
    List<Map<String, Object>> getUserGoodsCollection(UserCollectionModel userCollectionModel) throws LaiKeApiException;


    /**
     * 获取店铺收藏
     *
     * @param userCollectionModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/9 9:41
     */
    @Select("SELECT a.id AS shopid, c.id, c.mch_id, a.logo AS img, a. NAME AS mch_name, a.collection_num FROM lkt_user_collection AS c " +
            " LEFT JOIN lkt_mch AS a ON c.mch_id = a.id " +
            " WHERE a.store_id = #{store_id} AND c.type = 1 AND c.user_id = #{user_id} " +
            " ORDER BY c.add_time DESC")
    List<Map<String, Object>> getMchGoodsCollection(UserCollectionModel userCollectionModel) throws LaiKeApiException;


    /**
     * 删除商品+店铺收藏记录
     *
     * @param storeId -
     * @param pid     -
     * @param mchId   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 14:56
     */
    @Delete("delete from lkt_user_collection where store_id = #{storeId} and p_id = #{pid} or mch_id = #{mchId}")
    int delGoodsOrMchCollection(int storeId, int pid, Integer mchId) throws LaiKeApiException;

}