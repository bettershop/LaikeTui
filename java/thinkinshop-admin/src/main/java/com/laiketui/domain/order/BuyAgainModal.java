package com.laiketui.domain.order;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_buy_again")
public class BuyAgainModal {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商户ID
     */
    private Integer store_id;

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 产品ID
     */
    @Column(name = "Goods_id")
    private Integer goods_id;

    /**
     * 数量
     */
    @Column(name = "Goods_num")
    private Integer goods_num;

    /**
     * 添加时间
     */
    @Column(name = "Create_time")
    private Date create_time;

    /**
     * 商品属性id
     */
    @Column(name = "Size_id")
    private String size_id;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商户ID
     *
     * @return store_id - 商户ID
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商户ID
     *
     * @param store_id 商户ID
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
     * 获取产品ID
     *
     * @return Goods_id - 产品ID
     */
    public Integer getGoods_id() {
        return goods_id;
    }

    /**
     * 设置产品ID
     *
     * @param goods_id 产品ID
     */
    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    /**
     * 获取数量
     *
     * @return Goods_num - 数量
     */
    public Integer getGoods_num() {
        return goods_num;
    }

    /**
     * 设置数量
     *
     * @param goods_num 数量
     */
    public void setGoods_num(Integer goods_num) {
        this.goods_num = goods_num;
    }

    /**
     * 获取添加时间
     *
     * @return Create_time - 添加时间
     */
    public Date getCreate_time() {
        return create_time;
    }

    /**
     * 设置添加时间
     *
     * @param create_time 添加时间
     */
    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    /**
     * 获取商品属性id
     *
     * @return Size_id - 商品属性id
     */
    public String getSize_id() {
        return size_id;
    }

    /**
     * 设置商品属性id
     *
     * @param size_id 商品属性id
     */
    public void setSize_id(String size_id) {
        this.size_id = size_id == null ? null : size_id.trim();
    }
}