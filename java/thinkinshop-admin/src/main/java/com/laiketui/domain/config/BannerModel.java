package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_banner")
public class BannerModel {
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
     * 图片
     */
    private String image;

    /**
     * 跳转方式
     */
    private String open_type;

    /**
     * 链接
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 类型 1:小程序 2:app
     */
    private String type;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 店铺ID
     */
    private Integer mch_id;

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
     * 获取图片
     *
     * @return image - 图片
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置图片
     *
     * @param image 图片
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取跳转方式
     *
     * @return open_type - 跳转方式
     */
    public String getOpen_type() {
        return open_type;
    }

    /**
     * 设置跳转方式
     *
     * @param open_type 跳转方式
     */
    public void setOpen_type(String open_type) {
        this.open_type = open_type == null ? null : open_type.trim();
    }

    /**
     * 获取链接
     *
     * @return url - 链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置链接
     *
     * @param url 链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
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
     * 获取类型 1:小程序 2:app
     *
     * @return type - 类型 1:小程序 2:app
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型 1:小程序 2:app
     *
     * @param type 类型 1:小程序 2:app
     */
    public void setType(String type) {
        this.type = type;
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
     * 获取店铺ID
     *
     * @return mch_id - 店铺ID
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺ID
     *
     * @param mch_id 店铺ID
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }
}