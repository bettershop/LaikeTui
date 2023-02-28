package com.laiketui.domain.log;

import com.laiketui.domain.Page;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lkt_mch_account_log")
public class MchAccountLogModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 分页
     */
    @Transient
    private Page page;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 店铺id
     */
    private String mch_id;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 商户积分
     */
    private BigDecimal integral_money;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 商户余额
     */
    private BigDecimal account_money;

    /**
     * 状态：1.入账 2.出账
     */
    private Integer status;

    /**
     * 类型：1.订单 2.退款 3.提现
     */
    private Integer type;

    /**
     * 时间
     */
    private Date addtime;

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
    public String getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺id
     *
     * @param mch_id 店铺id
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id == null ? null : mch_id.trim();
    }

    /**
     * 获取积分
     *
     * @return integral - 积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置积分
     *
     * @param integral 积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取商户积分
     *
     * @return integral_money - 商户积分
     */
    public BigDecimal getIntegral_money() {
        return integral_money;
    }

    /**
     * 设置商户积分
     *
     * @param integral_money 商户积分
     */
    public void setIntegral_money(BigDecimal integral_money) {
        this.integral_money = integral_money;
    }

    /**
     * 获取金额
     *
     * @return price - 金额
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置金额
     *
     * @param price 金额
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取商户余额
     *
     * @return account_money - 商户余额
     */
    public BigDecimal getAccount_money() {
        return account_money;
    }

    /**
     * 设置商户余额
     *
     * @param account_money 商户余额
     */
    public void setAccount_money(BigDecimal account_money) {
        this.account_money = account_money;
    }

    /**
     * 获取状态：1.入账 2.出账
     *
     * @return status - 状态：1.入账 2.出账
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：1.入账 2.出账
     *
     * @param status 状态：1.入账 2.出账
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取类型：1.订单 2.退款 3.提现
     *
     * @return type - 类型：1.订单 2.退款 3.提现
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型：1.订单 2.退款 3.提现
     *
     * @param type 类型：1.订单 2.退款 3.提现
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取时间
     *
     * @return addtime - 时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置时间
     *
     * @param addtime 时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Page getPage() {
        return page;
    }
}