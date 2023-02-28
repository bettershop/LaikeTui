package com.laiketui.common.mapper;

import com.laiketui.domain.product.CommentsModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 评论
 *
 * @author Trick
 * @date 2020/10/23 16:47
 */
public interface CommentsModelMapper extends BaseMapper<CommentsModel> {


    /**
     * 根据商品id获取数据
     * 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/23 16:47
     */
    List<CommentsModel> getCommentsDynamicByPid(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取评论数据
     *
     * @param map -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/23 17:41
     */
    List<Map<String, Object>> getCommentsUserDynamicByPid(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 统计评论数量
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/14 11:24
     */
    int countCommentsUserDynamicByPid(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取订单评论信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 16:51
     */
    List<Map<String, Object>> getCommentsOrderDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取订单评论信息 - 统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 16:51
     */
    int countCommentsOrderDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Select("<script>" +
            "select count(1) from lkt_order a where a.store_id=#{storeId}  " +
            "and status = 5 and recycle = 0 " +
            " <if test='mchId != null '> " +
            "AND CONVERT(REPLACE(mch_id, ',', ''),unsigned INTEGER) = #{mchId} " +
            " </if> " +
            "and not EXISTS(select x.id from lkt_comments x where x.store_id=a.store_id and x.oid=a.sno)"+
            "</script>")
    int countCommentsOrderNum(int storeId,Integer mchId) throws LaiKeApiException;
}