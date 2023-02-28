package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_brand_class")
public class BrandClassModel {
    /**
     * 品牌id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer brand_id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 品牌logo
     */
    private String brand_pic;

    /**
     * 广告图
     */
    private String brand_image;

    /**
     * 品牌名称
     */
    private String brand_name;

    /**
     * 英文名
     */
    private String brand_y_name;

    /**
     * 产地
     */
    private String producer;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 0:启用 1:禁用
     */
    private Integer status;

    /**
     * 时间
     */
    private Date brand_time;

    private Integer sort;

    /**
     * 回收站 0.不回收 1.回收
     */
    private Integer recycle;

    /**
     * 所属分类
     */
    private String categories;

    /**
     * 获取品牌id
     *
     * @return brand_id - 品牌id
     */
    public Integer getBrand_id() {
        return brand_id;
    }

    /**
     * 设置品牌id
     *
     * @param brand_id 品牌id
     */
    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
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
     * 获取品牌logo
     *
     * @return brand_pic - 品牌logo
     */
    public String getBrand_pic() {
        return brand_pic;
    }

    /**
     * 设置品牌logo
     *
     * @param brand_pic 品牌logo
     */
    public void setBrand_pic(String brand_pic) {
        this.brand_pic = brand_pic == null ? null : brand_pic.trim();
    }

    /**
     * 获取广告图
     *
     * @return brand_image - 广告图
     */
    public String getBrand_image() {
        return brand_image;
    }

    /**
     * 设置广告图
     *
     * @param brand_image 广告图
     */
    public void setBrand_image(String brand_image) {
        this.brand_image = brand_image == null ? null : brand_image.trim();
    }

    /**
     * 获取品牌名称
     *
     * @return brand_name - 品牌名称
     */
    public String getBrand_name() {
        return brand_name;
    }

    /**
     * 设置品牌名称
     *
     * @param brand_name 品牌名称
     */
    public void setBrand_name(String brand_name) {
        this.brand_name = brand_name == null ? null : brand_name.trim();
    }

    /**
     * 获取英文名
     *
     * @return brand_y_name - 英文名
     */
    public String getBrand_y_name() {
        return brand_y_name;
    }

    /**
     * 设置英文名
     *
     * @param brand_y_name 英文名
     */
    public void setBrand_y_name(String brand_y_name) {
        this.brand_y_name = brand_y_name == null ? null : brand_y_name.trim();
    }

    /**
     * 获取产地
     *
     * @return producer - 产地
     */
    public String getProducer() {
        return producer;
    }

    /**
     * 设置产地
     *
     * @param producer 产地
     */
    public void setProducer(String producer) {
        this.producer = producer == null ? null : producer.trim();
    }

    /**
     * 获取备注
     *
     * @return remarks - 备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置备注
     *
     * @param remarks 备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * 获取状态 0:启用 1:禁用
     *
     * @return status - 状态 0:启用 1:禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0:启用 1:禁用
     *
     * @param status 状态 0:启用 1:禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取时间
     *
     * @return brand_time - 时间
     */
    public Date getBrand_time() {
        return brand_time;
    }

    /**
     * 设置时间
     *
     * @param brand_time 时间
     */
    public void setBrand_time(Date brand_time) {
        this.brand_time = brand_time;
    }

    /**
     * @return sort
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * @param sort
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取回收站 0.不回收 1.回收
     *
     * @return recycle - 回收站 0.不回收 1.回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0.不回收 1.回收
     *
     * @param recycle 回收站 0.不回收 1.回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    /**
     * 获取所属分类
     *
     * @return categories - 所属分类
     */
    public String getCategories() {
        return categories;
    }

    /**
     * 设置所属分类
     *
     * @param categories 所属分类
     */
    public void setCategories(String categories) {
        this.categories = categories == null ? null : categories.trim();
    }

}