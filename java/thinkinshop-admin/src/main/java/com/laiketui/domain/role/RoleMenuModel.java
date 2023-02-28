package com.laiketui.domain.role;

import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_role_menu")
public class RoleMenuModel {
    /**
     * 角色ID
     */
    private Integer role_id;

    /**
     * 菜单ID
     */
    private Integer menu_id;

    /**
     * 添加时间
     */
    private Date add_date;

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