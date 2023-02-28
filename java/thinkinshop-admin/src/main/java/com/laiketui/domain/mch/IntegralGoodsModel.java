package com.laiketui.domain.mch;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 积分商品表
 * @author Trick
 */
@Table(name = "lkt_integral_goods")
public class IntegralGoodsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer store_id;

    /**
     * 商品ID
     */
    private Integer goods_id;

    /**
     * 属性id
     */
    private Integer attr_id;

    /**
     * 兑换所需积分
     */
    private Integer integral;

    /**
     * 兑换所需余额
     */
    private BigDecimal money;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 库存
     */
    private Integer num;
    /**
     * 上架库存
     */
    @Column(name = "max_num")
    private Integer max_num;
    /**
     * 添加时间
     */
    private Date add_time;
    /**
     * 修改时间
     */
    private Date update_time;

    /**
     * 是否删除:0否   1是
     */
    private Integer is_delete;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getMax_num() {
        return max_num;
    }

    public void setMax_num(Integer max_num) {
        this.max_num = max_num;
    }

    public Date getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
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
     * 获取商品ID
     *
     * @return goods_id - 商品ID
     */
    public Integer getGoods_id() {
        return goods_id;
    }

    /**
     * 设置商品ID
     *
     * @param goods_id 商品ID
     */
    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    /**
     * 获取属性id
     *
     * @return attr_id - 属性id
     */
    public Integer getAttr_id() {
        return attr_id;
    }

    /**
     * 设置属性id
     *
     * @param attr_id 属性id
     */
    public void setAttr_id(Integer attr_id) {
        this.attr_id = attr_id;
    }

    /**
     * 获取兑换所需积分
     *
     * @return integral - 兑换所需积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置兑换所需积分
     *
     * @param integral 兑换所需积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取兑换所需余额
     *
     * @return money - 兑换所需余额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置兑换所需余额
     *
     * @param money 兑换所需余额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否删除:0否   1是
     *
     * @return is_delete - 是否删除:0否   1是
     */
    public Integer getIs_delete() {
        return is_delete;
    }

    /**
     * 设置是否删除:0否   1是
     *
     * @param is_delete 是否删除:0否   1是
     */
    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }
}