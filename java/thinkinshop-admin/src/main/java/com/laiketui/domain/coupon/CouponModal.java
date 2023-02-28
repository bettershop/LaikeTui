package com.laiketui.domain.coupon;

import javax.persistence.*;
import java.util.Date;


/**
 * 用户优惠卷
 *
 * @author Trick
 * @date 2021/1/26 9:43
 */
@Table(name = "lkt_coupon")
public class CouponModal {

    /**
     * 免邮
     */
    @Transient
    public static final String COUPON_TYPE_MY = "1";

    /**
     * 满减
     */
    @Transient
    public static final String COUPON_TYPE_MJ = "2";

    /**
     * 折扣
     */
    @Transient
    public static final String COUPON_TYPE_ZK = "3";

    /**
     * 会员赠送
     */
    @Transient
    public static final String COUPON_TYPE_HYZS = "4";


    /**
     * 启用
     */
    @Transient
    public static final Integer COUPON_STAUS_OPEN = 0;
    /**
     * 禁用
     */
    @Transient
    public static final Integer COUPON_STAUS_DISABLE = 1;


    /**
     * 未使用
     */
    @Transient
    public static final Integer COUPON_TYPE_NOT_USED = 0;
    /**
     * 使用中
     */
    @Transient
    public static final Integer COUPON_TYPE_IN_USE = 1;
    /**
     * 已使用
     */
    @Transient
    public static final Integer COUPON_TYPE_USED = 2;
    /**
     * 已过期
     */
    @Transient
    public static final Integer COUPON_TYPE_EXPIRED = 3;


    /**
     * 不能赠送
     */
    @Transient
    public static final Integer COUPON_NOT_GIVE = 0;
    /**
     * 能赠送
     */
    @Transient
    public static final Integer COUPON_GIVE = 1;


    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 店铺ID
     */
    private Integer mch_id;

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 优惠券金额
     */
    private Integer money;

    /**
     * 领取时间
     */
    private Date add_time;

    /**
     * 到期时间
     */
    private Date expiry_time;

    /**
     * 活动id
     */
    private Integer hid;

    /**
     * 类型 0:未使用 1:使用中 2:已使
     * <p>
     * 用 3:已过期
     */
    private Integer type;

    /**
     * 状态 0.开启 1.禁用
     */
    private Integer status;

    /**
     * 回收站 0.不回收 1.回收
     */
    private Integer recycle;


    /**
     * 是否为赠送 0.不为赠送 1.赠送
     */
    @Column(name = "free_or_not")
    private Integer free_or_not ;

    public Integer getFree_or_not() {
        return free_or_not;
    }

    public void setFree_or_not(Integer free_or_not) {
        this.free_or_not = free_or_not;
    }

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
     * 获取店铺ID
     *
     * @return mch_id - 店铺ID
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺ID
     *
     * @param mch_id 店铺ID
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
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
     * 获取优惠券金额
     *
     * @return money - 优惠券金额
     */
    public Integer getMoney() {
        return money;
    }

    /**
     * 设置优惠券金额
     *
     * @param money 优惠券金额
     */
    public void setMoney(Integer money) {
        this.money = money;
    }

    /**
     * 获取领取时间
     *
     * @return add_time - 领取时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置领取时间
     *
     * @param add_time 领取时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * 获取到期时间
     *
     * @return expiry_time - 到期时间
     */
    public Date getExpiry_time() {
        return expiry_time;
    }

    /**
     * 设置到期时间
     *
     * @param expiry_time 到期时间
     */
    public void setExpiry_time(Date expiry_time) {
        this.expiry_time = expiry_time;
    }

    /**
     * 获取活动id
     *
     * @return hid - 活动id
     */
    public Integer getHid() {
        return hid;
    }

    /**
     * 设置活动id
     *
     * @param hid 活动id
     */
    public void setHid(Integer hid) {
        this.hid = hid;
    }

    /**
     * 获取类型 0:未使用 1:使用中 2:已使
     * <p>
     * 用 3:已过期
     *
     * @return type - 类型 0:未使用 1:使用中 2:已使
     * <p>
     * 用 3:已过期
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:未使用 1:使用中 2:已使
     * <p>
     * 用 3:已过期
     *
     * @param type 类型 0:未使用 1:使用中 2:已使
     *             <p>
     *             用 3:已过期
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取状态 0.开启 1.禁用
     *
     * @return status - 状态 0.开启 1.禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0.开启 1.禁用
     *
     * @param status 状态 0.开启 1.禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取回收站 0.不回收 1.回收
     *
     * @return recycle - 回收站 0.不回收 1.回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0.不回收 1.回收
     *
     * @param recycle 回收站 0.不回收 1.回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }
}