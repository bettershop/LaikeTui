package com.laiketui.domain.home;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * UI导航栏表
 *
 * @author Trick
 * @date 2021/2/23 10:55
 */
@Table(name = "lkt_ui_navigation_bar")
public class UiNavigationBarModel {
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
     * 数据名称
     */
    private String name;

    /**
     * 图片
     */
    private String image;

    /**
     * 跳转类型 1.分类 2.商品 3.店铺
     */
    private Integer type;

    /**
     * 路径
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 是否显示
     */
    private Integer isshow;

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
     * 获取数据名称
     *
     * @return name - 数据名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置数据名称
     *
     * @param name 数据名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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
     * 获取跳转类型 1.分类 2.商品 3.店铺
     *
     * @return type - 跳转类型 1.分类 2.商品 3.店铺
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置跳转类型 1.分类 2.商品 3.店铺
     *
     * @param type 跳转类型 1.分类 2.商品 3.店铺
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取路径
     *
     * @return url - 路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置路径
     *
     * @param url 路径
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
     * 获取是否显示
     *
     * @return isshow - 是否显示
     */
    public Integer getIsshow() {
        return isshow;
    }

    /**
     * 设置是否显示
     *
     * @param isshow 是否显示
     */
    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }
}