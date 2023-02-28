package com.laiketui.common.mapper;

import com.laiketui.domain.coupon.CouponModal;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 优惠卷数据模型
 *
 * @author Trick
 * @date 2020/12/7 12:06
 */
public interface CouponModalMapper extends BaseMapper<CouponModal> {
    /**
     * 获取用户可使用的优惠券
     *
     * @param map
     * @return
     */
    List<Map<String, Object>> getUsersCoupons(Map<String, Object> map);


    /**
     * 更新优惠券为已经使用：2 类型 0:未使用 1:使用中 2:已使用 3:已过期
     *
     * @param type
     * @param storeId
     * @param userId
     * @param id
     * @return
     */
    @Update("<script> " +
            "update lkt_coupon set type = #{type} where store_id = #{storeId} and id = #{id} " +
            "<if test='userId != \"\" and userId != null '> and user_id = #{userId} </if>" +
            "</script>")
    int updateCoupon(int type, int storeId, String userId, int id);

    /**
     * 获取优惠券相关信息
     *
     * @param storeId
     * @param userId
     * @param id
     * @return
     */
    @Select("select b.mch_id,b.activity_type,b.type,b.money,b.discount,b.product_class_id,b.product_id from lkt_coupon as a " +
            "left join lkt_coupon_activity as b on a.hid = b.id where a.store_id = #{storeId} and a.user_id = #{userId} and a.id = #{id}")
    List<Map<String, Object>> getCouponsInfoList(int storeId, String userId, int id);


    /**
     * 获取优惠卷 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 12:08
     */
    List<Map<String, Object>> getCouponInfoListDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取优惠卷 动态sql
     * left join lkt_coupon_activity lkt_order lkt_user
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/10 11:13
     */
    List<Map<String, Object>> getUserCouponActivityDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态统计 sql
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 17:35
     */
    int countCouponDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 动态统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/10 11:43
     */
    int countUserCouponActivityDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 删除指定过期的优惠卷
     *
     * @param storeId -
     * @param mchId   -
     * @param endTime -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/11 14:10
     */
    @Update("update lkt_coupon set recycle = 1 where store_id = #{storeId} and mch_id = #{mchId} " +
            " and type in (2,3) and expiry_time <= #{endTime}")
    int delCoupon(int storeId, int mchId, Date endTime) throws LaiKeApiException;


    /**
     * 删除未使用的优惠卷
     *
     * @param storeId -
     * @param hid     -
     * @param mchId   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/25 16:02
     */
    @Update("<script> " +
            "update lkt_coupon set recycle = 1 where store_id = #{storeId} " +
            " and type in (0,2,3)" +
            " and hid=#{hid}" +
            "<if test='mchId != null '> and mch_id = #{mchId} </if>" +
            "</script>")
    int delNotUsedCoupon(int storeId, int hid, Integer mchId) throws LaiKeApiException;


    /**
     * 根据活动动id修改优惠卷状态
     *
     * @param type -
     * @param hid  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/11 14:52
     */
    @Update("update lkt_coupon set type = #{type} where hid = #{hid} ")
    int updateCouponByHid(int type, int hid) throws LaiKeApiException;


    /**
     * 获取所有 未使用 使用中的优惠卷
     *
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/11 15:01
     */
    @Select("select id,expiry_time from lkt_coupon where type in (0,1) ")
    List<CouponModal> getEffectiveCouponInfoList() throws LaiKeApiException;
}