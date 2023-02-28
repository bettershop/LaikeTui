package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;

/**
 * 活动表
 *
 * @author Trick
 * @date 2021/2/22 12:22
 */
@Table(name = "lkt_activity")
public class ActivityModel {

    /**
     * 活动专题
     */
    @Transient
    public static final int ACTIVITY_TYPE_SPECIAL = 0;
    /**
     * 营销插件
     */
    @Transient
    public static final int ACTIVITY_TYPE_PLUGIN = 1;

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
     * 类型 0.活动专题 1.营销插件
     */
    private Integer activity_type;

    /**
     * 插件类型
     */
    private Integer plug_type;

    /**
     * 标题
     */
    private String name;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 是否有宣传图 0.没有宣传图 1.有宣传图
     */
    private Integer is_image;

    /**
     * 宣传图
     */
    private String image;

    /**
     * 跳转路径
     */
    private String url;

    /**
     * 商品ID
     */
    private String p_id;

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
     * 满减id
     */
    private String subtraction_id;

    public String getSubtraction_id() {
        return subtraction_id;
    }

    public void setSubtraction_id(String subtraction_id) {
        this.subtraction_id = subtraction_id;
    }

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
     * 获取类型 0.活动专题 1.营销插件
     *
     * @return activity_type - 类型 0.活动专题 1.营销插件
     */
    public Integer getActivity_type() {
        return activity_type;
    }

    /**
     * 设置类型 0.活动专题 1.营销插件
     *
     * @param activity_type 类型 0.活动专题 1.营销插件
     */
    public void setActivity_type(Integer activity_type) {
        this.activity_type = activity_type;
    }

    /**
     * 获取插件类型
     *
     * @return plug_type - 插件类型
     */
    public Integer getPlug_type() {
        return plug_type;
    }

    /**
     * 设置插件类型
     *
     * @param plug_type 插件类型
     */
    public void setPlug_type(Integer plug_type) {
        this.plug_type = plug_type;
    }

    /**
     * 获取标题
     *
     * @return name - 标题
     */
    public String getName() {
        return name;
    }

    /**
     * 设置标题
     *
     * @param name 标题
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取副标题
     *
     * @return subtitle - 副标题
     */
    public String getSubtitle() {
        return subtitle;
    }

    /**
     * 设置副标题
     *
     * @param subtitle 副标题
     */
    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    /**
     * 获取是否有宣传图 0.没有宣传图 1.有宣传图
     *
     * @return is_image - 是否有宣传图 0.没有宣传图 1.有宣传图
     */
    public Integer getIs_image() {
        return is_image;
    }

    /**
     * 设置是否有宣传图 0.没有宣传图 1.有宣传图
     *
     * @param is_image 是否有宣传图 0.没有宣传图 1.有宣传图
     */
    public void setIs_image(Integer is_image) {
        this.is_image = is_image;
    }

    /**
     * 获取宣传图
     *
     * @return image - 宣传图
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置宣传图
     *
     * @param image 宣传图
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取跳转路径
     *
     * @return url - 跳转路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置跳转路径
     *
     * @param url 跳转路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取商品ID
     *
     * @return p_id - 商品ID
     */
    public String getP_id() {
        return p_id;
    }

    /**
     * 设置商品ID
     *
     * @param p_id 商品ID
     */
    public void setP_id(String p_id) {
        this.p_id = p_id == null ? null : p_id.trim();
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