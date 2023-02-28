package com.laiketui.common.mapper;

import com.laiketui.domain.coupon.CouponPresentationRecordModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 优惠卷领取记录
 *
 * @author Trick
 * @date 2021/1/25 17:45
 */
public interface CouponPresentationRecordModelMapper extends BaseMapper<CouponPresentationRecordModel> {


    /**
     * 获取优惠卷领取记录信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/25 17:45
     */
    List<Map<String, Object>> getCouponGiveInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取优惠卷领取记录信息-统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/25 17:45
     */
    int countCouponGiveInfo(Map<String, Object> map) throws LaiKeApiException;

}