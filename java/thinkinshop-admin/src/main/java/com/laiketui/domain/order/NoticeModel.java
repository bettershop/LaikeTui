package com.laiketui.domain.order;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_notice")
public class NoticeModel {
    /**
     * 配置id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 支付成功通知
     */
    private String pay_success;

    /**
     * 发货通知
     */
    private String order_delivery;

    /**
     * 订单发货通知
     */
    private String delivery;

    /**
     * 退款通知
     */
    private String refund_res;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 获取配置id
     *
     * @return id - 配置id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置配置id
     *
     * @param id 配置id
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
     * 获取支付成功通知
     *
     * @return pay_success - 支付成功通知
     */
    public String getPay_success() {
        return pay_success;
    }

    /**
     * 设置支付成功通知
     *
     * @param pay_success 支付成功通知
     */
    public void setPay_success(String pay_success) {
        this.pay_success = pay_success == null ? null : pay_success.trim();
    }

    /**
     * 获取发货通知
     *
     * @return order_delivery - 发货通知
     */
    public String getOrder_delivery() {
        return order_delivery;
    }

    /**
     * 设置发货通知
     *
     * @param order_delivery 发货通知
     */
    public void setOrder_delivery(String order_delivery) {
        this.order_delivery = order_delivery == null ? null : order_delivery.trim();
    }

    /**
     * 获取订单发货通知
     *
     * @return delivery - 订单发货通知
     */
    public String getDelivery() {
        return delivery;
    }

    /**
     * 设置订单发货通知
     *
     * @param delivery 订单发货通知
     */
    public void setDelivery(String delivery) {
        this.delivery = delivery == null ? null : delivery.trim();
    }

    /**
     * 获取退款通知
     *
     * @return refund_res - 退款通知
     */
    public String getRefund_res() {
        return refund_res;
    }

    /**
     * 设置退款通知
     *
     * @param refund_res 退款通知
     */
    public void setRefund_res(String refund_res) {
        this.refund_res = refund_res == null ? null : refund_res.trim();
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 设置更新时间
     *
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}