package com.laiketui.domain.mch;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 店铺保证金
 *
 * @author Trick
 * @date 2021/10/25 16:47
 */
@Table(name = "lkt_mch_promise")
public class MchPromiseModel {

    public interface PromiseConstant {
        /**
         * 已交保证金
         */
        Integer STATUS_PAY = 1;
        /**
         * 保证金已退还
         */
        Integer STATUS_RETURN_PAY = 2;
    }

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * 店铺id
     */
    private Integer mch_id;

    /**
     * 保证金
     */
    private BigDecimal promise_amt;

    /**
     * 保证金状态 1=已交 2=已退还
     */
    private Integer status;

    /**
     * 支付方式
     */
    private String pay_type;

    /**
     * 是否退还 1=退还
     */
    private Integer is_return_pay;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 修改时间
     */
    private Date update_date;

    /**
     * 订单号
     */
    @Column(name = "orderNo")
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    public Integer getIs_return_pay() {
        return is_return_pay;
    }

    public void setIs_return_pay(Integer is_return_pay) {
        this.is_return_pay = is_return_pay;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
     * 获取保证金
     *
     * @return promise_amt - 保证金
     */
    public BigDecimal getPromise_amt() {
        return promise_amt;
    }

    /**
     * 设置保证金
     *
     * @param promise_amt 保证金
     */
    public void setPromise_amt(BigDecimal promise_amt) {
        this.promise_amt = promise_amt;
    }

    /**
     * 获取保证金状态 1=已交 2=已退还
     *
     * @return status - 保证金状态 1=已交 2=已退还
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置保证金状态 1=已交 2=已退还
     *
     * @param status 保证金状态 1=已交 2=已退还
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取支付方式
     *
     * @return pay_type - 支付方式
     */
    public String getPay_type() {
        return pay_type;
    }

    /**
     * 设置支付方式
     *
     * @param pay_type 支付方式
     */
    public void setPay_type(String pay_type) {
        this.pay_type = pay_type == null ? null : pay_type.trim();
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
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdate_date() {
        return update_date;
    }

    /**
     * 设置修改时间
     *
     * @param update_date 修改时间
     */
    public void setUpdate_date(Date update_date) {
        this.update_date = update_date;
    }
}