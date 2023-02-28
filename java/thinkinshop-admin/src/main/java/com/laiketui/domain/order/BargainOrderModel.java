package com.laiketui.domain.order;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Trick
 */
@Table(name = "lkt_bargain_order")
public class BargainOrderModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer store_id;

    private String user_id;

    private String order_no;

    private Integer attr_id;

    public interface OrderStatus {
        /**
         * 失败
         */
        int ERROR = -1;
        /**
         * 进行中
         */
        int ONGOING = 0;
        /**
         * 成功
         */
        int SUCCESS = 1;
        /**
         * 付款状态
         */
        int GO_PAY = 2;
    }

    /**
     * 剩余价格
     */
    private BigDecimal original_price;

    /**
     * 商品最低价
     */
    private BigDecimal min_price;

    private Integer goods_id;

    /**
     * 状态 -1--失败 0--进行中 1--成功 2--付款
     */
    private Integer status;

    private Integer is_delete;

    private Integer addtime;

    /**
     * 砍价方式数据
     */
    private String status_data;

    /**
     * 砍价活动id
     */
    private Integer bargain_id;

    private Integer achievetime;

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
     * @return store_id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * @param store_id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * @return user_id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * @param user_id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * @return order_no
     */
    public String getOrder_no() {
        return order_no;
    }

    /**
     * @param order_no
     */
    public void setOrder_no(String order_no) {
        this.order_no = order_no == null ? null : order_no.trim();
    }

    /**
     * @return attr_id
     */
    public Integer getAttr_id() {
        return attr_id;
    }

    /**
     * @param attr_id
     */
    public void setAttr_id(Integer attr_id) {
        this.attr_id = attr_id;
    }

    /**
     * 获取剩余价格
     *
     * @return original_price - 剩余价格
     */
    public BigDecimal getOriginal_price() {
        return original_price;
    }

    /**
     * 设置剩余价格
     *
     * @param original_price 剩余价格
     */
    public void setOriginal_price(BigDecimal original_price) {
        this.original_price = original_price;
    }

    /**
     * 获取商品最低价
     *
     * @return min_price - 商品最低价
     */
    public BigDecimal getMin_price() {
        return min_price;
    }

    /**
     * 设置商品最低价
     *
     * @param min_price 商品最低价
     */
    public void setMin_price(BigDecimal min_price) {
        this.min_price = min_price;
    }

    /**
     * @return goods_id
     */
    public Integer getGoods_id() {
        return goods_id;
    }

    /**
     * @param goods_id
     */
    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    /**
     * 获取状态 -1--失败 0--进行中 1--成功 2--付款
     *
     * @return status - 状态 -1--失败 0--进行中 1--成功 2--付款
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 -1--失败 0--进行中 1--成功 2--付款
     *
     * @param status 状态 -1--失败 0--进行中 1--成功 2--付款
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return is_delete
     */
    public Integer getIs_delete() {
        return is_delete;
    }

    /**
     * @param is_delete
     */
    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }

    /**
     * @return addtime
     */
    public Integer getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }

    /**
     * 获取砍价方式数据
     *
     * @return status_data - 砍价方式数据
     */
    public String getStatus_data() {
        return status_data;
    }

    /**
     * 设置砍价方式数据
     *
     * @param status_data 砍价方式数据
     */
    public void setStatus_data(String status_data) {
        this.status_data = status_data == null ? null : status_data.trim();
    }

    /**
     * 获取砍价活动id
     *
     * @return bargain_id - 砍价活动id
     */
    public Integer getBargain_id() {
        return bargain_id;
    }

    /**
     * 设置砍价活动id
     *
     * @param bargain_id 砍价活动id
     */
    public void setBargain_id(Integer bargain_id) {
        this.bargain_id = bargain_id;
    }

    /**
     * @return achievetime
     */
    public Integer getAchievetime() {
        return achievetime;
    }

    /**
     * @param achievetime
     */
    public void setAchievetime(Integer achievetime) {
        this.achievetime = achievetime;
    }
}