package com.laiketui.domain.coupon;

import javax.persistence.*;
import java.util.Date;


/**
 * 优惠卷配置表
 *
 * @author Trick
 * @date 2020/12/9 11:51
 */
@Table(name = "lkt_coupon_config")
public class CouponConfigModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 单张
     */
    @Transient
    public static final Integer LIMIT_TYPE_SINGLE = 0;

    /**
     * 多张
     */
    @Transient
    public static final Integer LIMIT_TYPE_MANY = 1;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 店铺id
     */
    private Integer mch_id;

    /**
     * 状态 0：未启用 1：启用
     */
    private Integer is_status;

    /**
     * 优惠券删除开关 0=关 1=开
     *  重新定义 2021-08-05 11:28:00 0=关 1=开
     */
    private Integer coupon_del;

    /**
     * 优惠券删除天数
     */
    private Integer coupon_day;

    /**
     * 优惠券活动删除 0=关 1=开
     *  重新定义 2021-08-05 11:28:00 0=关 1=开
     */
    private Integer activity_del;

    /**
     * 优惠券活动删除天数
     */
    private Integer activity_day;

    /**
     * 支付设置 0：只使用优惠券 1：可与其他优惠一起使用
     */
    private Integer payment_type;

    /**
     * 限领设置 0：单张 1：多张
     */
    private Integer limit_type;

    /**
     * 优惠券类型
     */
    private String coupon_type;

    /**
     * 修改时间
     */
    private Date modify_date;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
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
     * 获取店铺id
     *
     * @return mch_id - 店铺id
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺id
     *
     * @param mch_id 店铺id
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取状态 0：未启用 1：启用
     *
     * @return is_status - 状态 0：未启用 1：启用
     */
    public Integer getIs_status() {
        return is_status;
    }

    /**
     * 设置状态 0：未启用 1：启用
     *
     * @param is_status 状态 0：未启用 1：启用
     */
    public void setIs_status(Integer is_status) {
        this.is_status = is_status;
    }

    /**
     * 获取优惠券删除
     *
     * @return coupon_del - 优惠券删除
     */
    public Integer getCoupon_del() {
        return coupon_del;
    }

    /**
     * 设置优惠券删除
     *
     * @param coupon_del 优惠券删除
     */
    public void setCoupon_del(Integer coupon_del) {
        this.coupon_del = coupon_del;
    }

    /**
     * 获取优惠券删除天数
     *
     * @return coupon_day - 优惠券删除天数
     */
    public Integer getCoupon_day() {
        return coupon_day;
    }

    /**
     * 设置优惠券删除天数
     *
     * @param coupon_day 优惠券删除天数
     */
    public void setCoupon_day(Integer coupon_day) {
        this.coupon_day = coupon_day;
    }

    /**
     * 获取优惠券活动删除
     *
     * @return activity_del - 优惠券活动删除
     */
    public Integer getActivity_del() {
        return activity_del;
    }

    /**
     * 设置优惠券活动删除
     *
     * @param activity_del 优惠券活动删除
     */
    public void setActivity_del(Integer activity_del) {
        this.activity_del = activity_del;
    }

    /**
     * 获取优惠券活动删除天数
     *
     * @return activity_day - 优惠券活动删除天数
     */
    public Integer getActivity_day() {
        return activity_day;
    }

    /**
     * 设置优惠券活动删除天数
     *
     * @param activity_day 优惠券活动删除天数
     */
    public void setActivity_day(Integer activity_day) {
        this.activity_day = activity_day;
    }

    /**
     * 获取支付设置 0：只使用优惠券 1：可与其他优惠一起使用
     *
     * @return payment_type - 支付设置 0：只使用优惠券 1：可与其他优惠一起使用
     */
    public Integer getPayment_type() {
        return payment_type;
    }

    /**
     * 设置支付设置 0：只使用优惠券 1：可与其他优惠一起使用
     *
     * @param payment_type 支付设置 0：只使用优惠券 1：可与其他优惠一起使用
     */
    public void setPayment_type(Integer payment_type) {
        this.payment_type = payment_type;
    }

    /**
     * 获取限领设置 0：单张 1：多张
     *
     * @return limit_type - 限领设置 0：单张 1：多张
     */
    public Integer getLimit_type() {
        return limit_type;
    }

    /**
     * 设置限领设置 0：单张 1：多张
     *
     * @param limit_type 限领设置 0：单张 1：多张
     */
    public void setLimit_type(Integer limit_type) {
        this.limit_type = limit_type;
    }

    /**
     * 获取优惠券类型
     *
     * @return coupon_type - 优惠券类型
     */
    public String getCoupon_type() {
        return coupon_type;
    }

    /**
     * 设置优惠券类型
     *
     * @param coupon_type 优惠券类型
     */
    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type == null ? null : coupon_type.trim();
    }

    /**
     * 获取修改时间
     *
     * @return modify_date - 修改时间
     */
    public Date getModify_date() {
        return modify_date;
    }

    /**
     * 设置修改时间
     *
     * @param modify_date 修改时间
     */
    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }
}