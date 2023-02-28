package com.laiketui.domain.role;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_guide_menu")
public class GuideMenuModel {
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
     * 角色ID
     */
    private Integer role_id;

    /**
     * 菜单ID
     */
    private Integer menu_id;

    /**
     * 是否显示 0.不显示 1.显示
     */
    private Integer is_display;

    /**
     * 导览排序
     */
    private Integer guide_sort;

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
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Integer getRole_id() {
        return role_id;
    }

    /**
     * 设置角色ID
     *
     * @param role_id 角色ID
     */
    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Integer getMenu_id() {
        return menu_id;
    }

    /**
     * 设置菜单ID
     *
     * @param menu_id 菜单ID
     */
    public void setMenu_id(Integer menu_id) {
        this.menu_id = menu_id;
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
     * 获取导览排序
     *
     * @return guide_sort - 导览排序
     */
    public Integer getGuide_sort() {
        return guide_sort;
    }

    /**
     * 设置导览排序
     *
     * @param guide_sort 导览排序
     */
    public void setGuide_sort(Integer guide_sort) {
        this.guide_sort = guide_sort;
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