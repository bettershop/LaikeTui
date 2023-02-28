package com.laiketui.common.mapper;

import com.laiketui.domain.coupon.CouponOrderModal;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Update;


/**
 * 优惠券订单
 * @author wangxian
 */
public interface CouponOrderModalMapper extends BaseMapper<CouponOrderModal> {

    /**
     * 更新优惠券订单信息
     * @param storeId
     * @param userId
     * @param couponId
     * @return
     */
    @Update("update lkt_coupon_sno set recycle = 1 where store_id = #{storeId} and user_id = #{userId} and coupon_id =#{couponId} ")
    int updateCouponOrder( int storeId,String userId,int couponId );

}