package com.laiketui.domain.coupon;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_coupon_sno")
public class CouponOrderModal {
    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 优惠券id
     */
    private Integer coupon_id;

    /**
     * 订单号  Unknown column 's_no' in 'field list' 使用 @Column
     */
    @Column(name = "sNo")
    private String sNo;

    /**
     * 回收站 0:正常 1:回收
     */
    private Integer recycle;

    /**
     * 添加时间
     */
    private Date add_date;

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
     * 获取订单号
     *
     * @return sNo - 订单号
     */
    public String getsNo() {
        return sNo;
    }

    /**
     * 设置订单号
     *
     * @param sNo 订单号
     */
    public void setsNo(String sNo) {
        this.sNo = sNo == null ? null : sNo.trim();
    }

    /**
     * 获取回收站 0:正常 1:回收
     *
     * @return recycle - 回收站 0:正常 1:回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0:正常 1:回收
     *
     * @param recycle 回收站 0:正常 1:回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
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