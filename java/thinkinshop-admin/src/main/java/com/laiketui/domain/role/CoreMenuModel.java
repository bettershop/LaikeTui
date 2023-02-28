package com.laiketui.domain.role;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_core_menu")
public class CoreMenuModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 上级id
     */
    private Integer s_id;

    /**
     * 菜单名称
     */
    private String title;

    /**
     * 菜单标识
     */
    private String name;

    /**
     * 图标
     */
    private String image;

    /**
     * 点击后图标
     */
    private String image1;

    /**
     * 菜单模块标识
     */
    private String module;

    /**
     * 菜单文件标识
     */
    private String action;

    /**
     * 路径
     */
    private String url;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 第几级
     */
    private Integer level;

    /**
     * 是否是核心
     */
    private Integer is_core;

    /**
     * 是否是插件
     */
    private Integer is_plug_in;

    /**
     * 类型 0.后台管理 1.小程序 2.app 3.微信公众号 4.PC 5.生活号 6.报表 7.支付宝小程序
     */
    private Integer type;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * 回收站 0.不回收 1.回收
     */
    private Integer recycle;

    /**
     *  是否显示 0.不显示 1.显示
     */
    private Integer is_display;

    /**
     * 导览简介
     */
    private String briefintroduction;

    /**
     *  导览排序
     */
    private Integer guide_sort;

    /**
     * 导览名称
     */
    private String guide_name;

    public Integer getIs_display() {
        return is_display;
    }

    public void setIs_display(Integer is_display) {
        this.is_display = is_display;
    }

    public String getBriefintroduction() {
        return briefintroduction;
    }

    public void setBriefintroduction(String briefintroduction) {
        this.briefintroduction = briefintroduction;
    }

    public Integer getGuide_sort() {
        return guide_sort;
    }

    public void setGuide_sort(Integer guide_sort) {
        this.guide_sort = guide_sort;
    }

    public String getGuide_name() {
        return guide_name;
    }

    public void setGuide_name(String guide_name) {
        this.guide_name = guide_name;
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
     * 获取上级id
     *
     * @return s_id - 上级id
     */
    public Integer getS_id() {
        return s_id;
    }

    /**
     * 设置上级id
     *
     * @param s_id 上级id
     */
    public void setS_id(Integer s_id) {
        this.s_id = s_id;
    }

    /**
     * 获取菜单名称
     *
     * @return title - 菜单名称
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置菜单名称
     *
     * @param title 菜单名称
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取菜单标识
     *
     * @return name - 菜单标识
     */
    public String getName() {
        return name;
    }

    /**
     * 设置菜单标识
     *
     * @param name 菜单标识
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取图标
     *
     * @return image - 图标
     */
    public String getImage() {
        return image;
    }

    /**
     * 设置图标
     *
     * @param image 图标
     */
    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    /**
     * 获取点击后图标
     *
     * @return image1 - 点击后图标
     */
    public String getImage1() {
        return image1;
    }

    /**
     * 设置点击后图标
     *
     * @param image1 点击后图标
     */
    public void setImage1(String image1) {
        this.image1 = image1 == null ? null : image1.trim();
    }

    /**
     * 获取菜单模块标识
     *
     * @return module - 菜单模块标识
     */
    public String getModule() {
        return module;
    }

    /**
     * 设置菜单模块标识
     *
     * @param module 菜单模块标识
     */
    public void setModule(String module) {
        this.module = module == null ? null : module.trim();
    }

    /**
     * 获取菜单文件标识
     *
     * @return action - 菜单文件标识
     */
    public String getAction() {
        return action;
    }

    /**
     * 设置菜单文件标识
     *
     * @param action 菜单文件标识
     */
    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
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
     * 获取第几级
     *
     * @return level - 第几级
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置第几级
     *
     * @param level 第几级
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取是否是核心
     *
     * @return is_core - 是否是核心
     */
    public Integer getIs_core() {
        return is_core;
    }

    /**
     * 设置是否是核心
     *
     * @param is_core 是否是核心
     */
    public void setIs_core(Integer is_core) {
        this.is_core = is_core;
    }

    /**
     * 获取是否是插件
     *
     * @return is_plug_in - 是否是插件
     */
    public Integer getIs_plug_in() {
        return is_plug_in;
    }

    /**
     * 设置是否是插件
     *
     * @param is_plug_in 是否是插件
     */
    public void setIs_plug_in(Integer is_plug_in) {
        this.is_plug_in = is_plug_in;
    }

    /**
     * 设置类型 0.控制台 1.商城
     *
     * @return type - 类型 0.后台管理 1.小程序 2.app 3.微信公众号 4.PC 5.生活号 6.报表 7.支付宝小程序
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0.控制台 1.商城
     *
     * @param type 类型 0.控制台 1.商城
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取添加时间
     *
     * @return add_time - 添加时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置添加时间
     *
     * @param add_time 添加时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
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
}