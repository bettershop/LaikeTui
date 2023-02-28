package com.laiketui.domain.order;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import java.util.Date;


/**
 * 订单临时表
 *
 * @author Trick
 * @date 2020/12/23 17:02
 */
@Table(name = "lkt_order_data")
public class OrderDataModel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信订单号
     */
    private String trade_no;

    private String data;

    /**
     * 订单类型
     */
    @Column(name = "order_type")
    private String order_type;

    /**
     * 创建时间
     */
    private Date addtime;

    /**
     * 支付状态 0未支付 1已支付
     */
    private Integer status;

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取微信订单号
     *
     * @return trade_no - 微信订单号
     */
    public String getTrade_no() {
        return trade_no;
    }

    /**
     * 设置微信订单号
     *
     * @param trade_no 微信订单号
     */
    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no == null ? null : trade_no.trim();
    }

    /**
     * @return data
     */
    public String getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }

    /**
     * 获取创建时间
     *
     * @return addtime - 创建时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置创建时间
     *
     * @param addtime 创建时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * 获取支付状态 0未支付 1已支付
     *
     * @return status - 支付状态 0未支付 1已支付
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置支付状态 0未支付 1已支付
     *
     * @param status 支付状态 0未支付 1已支付
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}