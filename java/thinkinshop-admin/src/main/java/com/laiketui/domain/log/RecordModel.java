package com.laiketui.domain.log;

import com.laiketui.domain.Page;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lkt_record")
public class RecordModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Page page;

    /**
     * 退款
     */
    @Transient
    public static final int RECORDTYPE_RETURNAMT = 5;
    /**
     * 购买会员
     */
    @Transient
    public static final int BUYING_MEMBERS = 4;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 操作金额
     */
    private BigDecimal money = new BigDecimal("0");

    /**
     * 原有金额
     */
    private BigDecimal oldmoney = new BigDecimal("0");

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 事件
     */
    private String event;

    /**
     * 类型 0:登录/退出 1:充值 2:申请提现 3:分享 4:余额消费 5:退款 6:红包提现 7:佣金 8:管理佣金 9:待定 10:消费金 11:系统扣款
     * 12:给好友转余额 13:转入余额 14:系统充值 15:系统充积分 16:系统充消费金 17:系统扣积分 18:系统扣消费金 19:消费金解封 20:抽奖中奖
     * 21:  提现成功 22:提现失败 23.取消订单  24分享获取红包 26 交竞拍押金 27 退竞拍押金 28 售后（仅退款） 29 售后（退货退款）30 会员返现
     */
    private Integer type;

    /**
     * 是否是店铺提现 0.不是店铺提现 1.是店铺提现
     */
    private Integer is_mch;

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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户id
     *
     * @param user_id 用户id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取操作金额
     *
     * @return money - 操作金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置操作金额
     *
     * @param money 操作金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取原有金额
     *
     * @return oldmoney - 原有金额
     */
    public BigDecimal getOldmoney() {
        return oldmoney;
    }

    /**
     * 设置原有金额
     *
     * @param oldmoney 原有金额
     */
    public void setOldmoney(BigDecimal oldmoney) {
        this.oldmoney = oldmoney;
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

    /**
     * 获取事件
     *
     * @return event - 事件
     */
    public String getEvent() {
        return event;
    }

    /**
     * 设置事件
     *
     * @param event 事件
     */
    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    /**
     * 获取类型 0:登录/退出 1:充值 2:申请提现 3:分享 4:余额消费 5:退款 6:红包提现 7:佣金 8:管理佣金 9:待定 10:消费金 11:系统扣款   12:给好友转余额 13:转入余额 14:系统充值 15:系统充积分 16:系统充消费金 17:系统扣积分 18:系统扣消费金 19:消费金解封 20:抽奖中奖 21:  提现成功 22:提现失败 23.取消订单  24分享获取红包 26 交竞拍押金 27 退竞拍押金 28 售后（仅退款） 29 售后（退货退款）30 会员返现
     * 31 = 积分消费
     * @return type - 类型 0:登录/退出 1:充值 2:申请提现 3:分享 4:余额消费 5:退款 6:红包提现 7:佣金 8:管理佣金 9:待定 10:消费金 11:系统扣款   12:给好友转余额 13:转入余额 14:系统充值 15:系统充积分 16:系统充消费金 17:系统扣积分 18:系统扣消费金 19:消费金解封 20:抽奖中奖 21:  提现成功 22:提现失败 23.取消订单  24分享获取红包 26 交竞拍押金 27 退竞拍押金 28 售后（仅退款） 29 售后（退货退款）30 会员返现
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:登录/退出 1:充值 2:申请提现 3:分享 4:余额消费 5:退款 6:红包提现 7:佣金 8:管理佣金 9:待定 10:消费金 11:系统扣款   12:给好友转余额 13:转入余额 14:系统充值 15:系统充积分 16:系统充消费金 17:系统扣积分 18:系统扣消费金 19:消费金解封 20:抽奖中奖 21:  提现成功 22:提现失败 23.取消订单  24分享获取红包 26 交竞拍押金 27 退竞拍押金 28 售后（仅退款） 29 售后（退货退款）30 会员返现
     * 31 = 积分消费
     * @param type 类型 0:登录/退出 1:充值 2:申请提现 3:分享 4:余额消费 5:退款 6:红包提现 7:佣金 8:管理佣金 9:待定 10:消费金 11:系统扣款   12:给好友转余额 13:转入余额 14:系统充值 15:系统充积分 16:系统充消费金 17:系统扣积分 18:系统扣消费金 19:消费金解封 20:抽奖中奖 21:  提现成功 22:提现失败 23.取消订单  24分享获取红包 26 交竞拍押金 27 退竞拍押金 28 售后（仅退款） 29 售后（退货退款）30 会员返现
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否是店铺提现 0.不是店铺提现 1.是店铺提现
     *
     * @return is_mch - 是否是店铺提现 0.不是店铺提现 1.是店铺提现
     */
    public Integer getIs_mch() {
        return is_mch;
    }

    /**
     * 设置是否是店铺提现 0.不是店铺提现 1.是店铺提现
     *
     * @param is_mch 是否是店铺提现 0.不是店铺提现 1.是店铺提现
     */
    public void setIs_mch(Integer is_mch) {
        this.is_mch = is_mch;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public RecordModel(Integer store_id, String user_id, BigDecimal money, BigDecimal oldmoney, Date add_date, String event, Integer type) {
        this.store_id = store_id;
        this.user_id = user_id;
        this.money = money;
        this.oldmoney = oldmoney;
        this.add_date = add_date;
        this.event = event;
        this.type = type;
    }

    public RecordModel() {
    }

}