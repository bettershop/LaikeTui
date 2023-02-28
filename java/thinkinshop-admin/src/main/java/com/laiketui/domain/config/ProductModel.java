package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_product_config")
public class ProductModel {
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
     * 配置
     */
    private String config;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 是否显示下架商品 0.不显示  1.显示
     */
    private Integer is_open;

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
     * 获取配置
     *
     * @return config - 配置
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置配置
     *
     * @param config 配置
     */
    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
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
     * 获取是否显示下架商品 0.不显示  1.显示
     *
     * @return is_open - 是否显示下架商品 0.不显示  1.显示
     */
    public Integer getIs_open() {
        return is_open;
    }

    /**
     * 设置是否显示下架商品 0.不显示  1.显示
     *
     * @param is_open 是否显示下架商品 0.不显示  1.显示
     */
    public void setIs_open(Integer is_open) {
        this.is_open = is_open;
    }
}