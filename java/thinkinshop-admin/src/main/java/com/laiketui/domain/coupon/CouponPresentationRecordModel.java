package com.laiketui.domain.coupon;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 优惠卷赠送记录表
 *
 * @author Trick
 * @date 2021/1/25 17:29
 */
@Table(name = "lkt_coupon_presentation_record")
public class CouponPresentationRecordModel {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 优惠券活动id
     */
    private Integer coupon_activity_id;

    /**
     * 优惠券id
     */
    private Integer coupon_id;

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 优惠券类型
     */
    private Integer activity_type;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城id
     *
     * @return store_id - 商城id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城id
     *
     * @param store_id 商城id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取优惠券活动id
     *
     * @return coupon_activity_id - 优惠券活动id
     */
    public Integer getCoupon_activity_id() {
        return coupon_activity_id;
    }

    /**
     * 设置优惠券活动id
     *
     * @param coupon_activity_id 优惠券活动id
     */
    public void setCoupon_activity_id(Integer coupon_activity_id) {
        this.coupon_activity_id = coupon_activity_id;
    }

    /**
     * 获取优惠券id
     *
     * @return coupon_id - 优惠券id
     */
    public Integer getCoupon_id() {
        return coupon_id;
    }

    /**
     * 设置优惠券id
     *
     * @param coupon_id 优惠券id
     */
    public void setCoupon_id(Integer coupon_id) {
        this.coupon_id = coupon_id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID
     *
     * @param user_id 用户ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取手机号
     *
     * @return mobile - 手机号
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机号
     *
     * @param mobile 手机号
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取优惠券类型
     *
     * @return activity_type - 优惠券类型
     */
    public Integer getActivity_type() {
        return activity_type;
    }

    /**
     * 设置优惠券类型
     *
     * @param activity_type 优惠券类型
     */
    public void setActivity_type(Integer activity_type) {
        this.activity_type = activity_type;
    }

    /**
     * 获取添加时间
     *
     * @return add_date - 添加时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置添加时间
     *
     * @param add_date 添加时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }
}