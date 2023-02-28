package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 活动商品表
 *
 * @author Trick
 * @date 2021/2/22 13:56
 */
@Table(name = "lkt_activity_pro")
public class ActivityProModel {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城ID
     */
    private Integer store_id;

    /**
     * 活动ID
     */
    private Integer activity_id;

    /**
     * 商品ID
     */
    private Integer p_id;

    /**
     * 是否显示 0.不显示 1.显示
     */
    private Integer is_display;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城ID
     *
     * @return store_id - 商城ID
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城ID
     *
     * @param store_id 商城ID
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取活动ID
     *
     * @return activity_id - 活动ID
     */
    public Integer getActivity_id() {
        return activity_id;
    }

    /**
     * 设置活动ID
     *
     * @param activity_id 活动ID
     */
    public void setActivity_id(Integer activity_id) {
        this.activity_id = activity_id;
    }

    /**
     * 获取商品ID
     *
     * @return p_id - 商品ID
     */
    public Integer getP_id() {
        return p_id;
    }

    /**
     * 设置商品ID
     *
     * @param p_id 商品ID
     */
    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    /**
     * 获取是否显示 0.不显示 1.显示
     *
     * @return is_display - 是否显示 0.不显示 1.显示
     */
    public Integer getIs_display() {
        return is_display;
    }

    /**
     * 设置是否显示 0.不显示 1.显示
     *
     * @param is_display 是否显示 0.不显示 1.显示
     */
    public void setIs_display(Integer is_display) {
        this.is_display = is_display;
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