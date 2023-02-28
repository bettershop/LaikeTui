package com.laiketui.domain.weixin;

import javax.persistence.*;
import java.util.Date;

/**
 * 小程序模板
 *
 * @author Trick
 * @date 2021/1/20 10:40
 */
@Table(name = "lkt_third_template")
public class ThirdTemplateModel {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 不应用
     */
    @Transient
    public static final Integer IS_USE_STOP = 0;
    /**
     * 应用
     */
    @Transient
    public static final Integer IS_USE_OPEN = 1;

    /**
     * 微信端模板id
     */
    private String template_id;

    /**
     * wx模板描述
     */
    private String wx_desc;

    /**
     * wx模板版本号
     */
    private String wx_version;

    /**
     * 添加进开放平台模板库时间戳
     */
    private Integer wx_create_time;

    /**
     * 后台定义的模板编号
     */
    private String lk_version;

    /**
     * 后台模板描述
     */
    private String lk_desc;

    /**
     * 模板图片路劲
     */
    private String img_url;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 数据字典-行业
     */
    private String trade_data;

    /**
     * 是否应用 0：不应用 1：应用
     */
    private Integer is_use;

    /**
     * 模板更新时间
     */
    private Date update_time;

    /**
     * 模板名称
     */
    private String title;

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
     * 获取微信端模板id
     *
     * @return template_id - 微信端模板id
     */
    public String getTemplate_id() {
        return template_id;
    }

    /**
     * 设置微信端模板id
     *
     * @param template_id 微信端模板id
     */
    public void setTemplate_id(String template_id) {
        this.template_id = template_id == null ? null : template_id.trim();
    }

    /**
     * 获取wx模板描述
     *
     * @return wx_desc - wx模板描述
     */
    public String getWx_desc() {
        return wx_desc;
    }

    /**
     * 设置wx模板描述
     *
     * @param wx_desc wx模板描述
     */
    public void setWx_desc(String wx_desc) {
        this.wx_desc = wx_desc == null ? null : wx_desc.trim();
    }

    /**
     * 获取wx模板版本号
     *
     * @return wx_version - wx模板版本号
     */
    public String getWx_version() {
        return wx_version;
    }

    /**
     * 设置wx模板版本号
     *
     * @param wx_version wx模板版本号
     */
    public void setWx_version(String wx_version) {
        this.wx_version = wx_version == null ? null : wx_version.trim();
    }

    /**
     * 获取添加进开放平台模板库时间戳
     *
     * @return wx_create_time - 添加进开放平台模板库时间戳
     */
    public Integer getWx_create_time() {
        return wx_create_time;
    }

    /**
     * 设置添加进开放平台模板库时间戳
     *
     * @param wx_create_time 添加进开放平台模板库时间戳
     */
    public void setWx_create_time(Integer wx_create_time) {
        this.wx_create_time = wx_create_time;
    }

    /**
     * 获取后台定义的模板编号
     *
     * @return lk_version - 后台定义的模板编号
     */
    public String getLk_version() {
        return lk_version;
    }

    /**
     * 设置后台定义的模板编号
     *
     * @param lk_version 后台定义的模板编号
     */
    public void setLk_version(String lk_version) {
        this.lk_version = lk_version == null ? null : lk_version.trim();
    }

    /**
     * 获取后台模板描述
     *
     * @return lk_desc - 后台模板描述
     */
    public String getLk_desc() {
        return lk_desc;
    }

    /**
     * 设置后台模板描述
     *
     * @param lk_desc 后台模板描述
     */
    public void setLk_desc(String lk_desc) {
        this.lk_desc = lk_desc == null ? null : lk_desc.trim();
    }

    /**
     * 获取模板图片路劲
     *
     * @return img_url - 模板图片路劲
     */
    public String getImg_url() {
        return img_url;
    }

    /**
     * 设置模板图片路劲
     *
     * @param img_url 模板图片路劲
     */
    public void setImg_url(String img_url) {
        this.img_url = img_url == null ? null : img_url.trim();
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
     * 获取数据字典-行业
     *
     * @return trade_data - 数据字典-行业
     */
    public String getTrade_data() {
        return trade_data;
    }

    /**
     * 设置数据字典-行业
     *
     * @param trade_data 数据字典-行业
     */
    public void setTrade_data(String trade_data) {
        this.trade_data = trade_data == null ? null : trade_data.trim();
    }

    /**
     * 获取是否应用 0：不应用 1：应用
     *
     * @return is_use - 是否应用 0：不应用 1：应用
     */
    public Integer getIs_use() {
        return is_use;
    }

    /**
     * 设置是否应用 0：不应用 1：应用
     *
     * @param is_use 是否应用 0：不应用 1：应用
     */
    public void setIs_use(Integer is_use) {
        this.is_use = is_use;
    }

    /**
     * 获取模板更新时间
     *
     * @return update_time - 模板更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 设置模板更新时间
     *
     * @param update_time 模板更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 获取模板名称
     *
     * @return title - 模板名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置模板名称
     *
     * @param title 模板名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }
}