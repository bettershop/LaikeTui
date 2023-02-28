package com.laiketui.common.mapper;

import com.laiketui.domain.coupon.CouponActivityModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 店铺卡卷活动 sql
 *
 * @author Trick
 * @date 2020/10/14 16:18
 */
public interface CouponActivityModelMapper extends BaseMapper<CouponActivityModel> {


    /**
     * 获取某商城所有 开启、未启用、禁用  优惠卷
     *
     * @param couponActivityModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/14 16:19
     */
    @Select("select id,status,end_time,start_time from lkt_coupon_activity where store_id = #{store_id} and recycle = #{recycle} and status in(0,1,2) order by start_time desc")
    List<CouponActivityModel> getCouponActivityAll(CouponActivityModel couponActivityModel) throws LaiKeApiException;


    /**
     * 插件状态修改
     *
     * @param couponActivityModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/14 16:33
     */
    @Update("update lkt_coupon_activity set status = #{status} where id = #{id}")
    int updateSwitchCouponActivity(CouponActivityModel couponActivityModel) throws LaiKeApiException;


    /**
     * 优惠卷数量修改
     *
     * @param id  -
     * @param num -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/8 9:54
     */
    @Update("update lkt_coupon_activity set num = num - #{num} where id = #{id}")
    int updateCouponByNum(int id, int num) throws LaiKeApiException;

    /**
     * 获取优惠卷活动
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 18:04
     */
    List<Map<String, Object>> getCouponActivityDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 动态统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/8 18:31
     */
    int countCouponActivityDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 清理指定过期的活动
     *
     * @param storeId -
     * @param mchId   -
     * @param endTime -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/11 14:35
     */
    @Update("update lkt_coupon_activity set recycle = 1 where store_id = #{storeId} and mch_id = #{mchId} " +
            " and status = 3 and end_time <= #{endTime}")
    int delActivity(int storeId, int mchId, Date endTime) throws LaiKeApiException;

    /**
     * 获取
     * @param storeId
     * @param couponId
     * @return
     */
    @Select("select a.activity_type,a.discount from lkt_coupon_activity as a " +
            "left join lkt_coupon as b on a.id = b.hid where a.store_id = #{storeId} and b.id = #{couponId} ")
    CouponActivityModel getOneCouponActivity(int storeId, int couponId);

}