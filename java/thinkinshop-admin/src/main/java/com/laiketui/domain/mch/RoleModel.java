package com.laiketui.domain.mch;

import javax.persistence.*;
import java.util.Date;


/**
 * 权限
 *
 * @author Trick
 * @date 2021/1/13 15:41
 */
@Table(name = "lkt_role")
public class RoleModel {

    /**
     * 角色
     */
    @Transient
    public static final Integer STATUS_ROLE = 0;
    /**
     * 客户端
     */
    @Transient
    public static final Integer STATUS_CLIENT = 1;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 许可
     */
    private String permission;

    /**
     * 状态 0:角色 1:客户端
     */
    private Integer status;

    /**
     * 管理员id
     */
    private Integer admin_id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 描述
     */
    private String role_describe;

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
     * 获取角色名称
     *
     * @return name - 角色名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置角色名称
     *
     * @param name 角色名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取许可
     *
     * @return permission - 许可
     */
    public String getPermission() {
        return permission;
    }

    /**
     * 设置许可
     *
     * @param permission 许可
     */
    public void setPermission(String permission) {
        this.permission = permission == null ? null : permission.trim();
    }

    /**
     * 获取状态 0:角色 1:客户端
     *
     * @return status - 状态 0:角色 1:客户端
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0:角色 1:客户端
     *
     * @param status 状态 0:角色 1:客户端
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取管理员id
     *
     * @return admin_id - 管理员id
     */
    public Integer getAdmin_id() {
        return admin_id;
    }

    /**
     * 设置管理员id
     *
     * @param admin_id 管理员id
     */
    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
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
     * 获取描述
     *
     * @return role_describe - 描述
     */
    public String getRole_describe() {
        return role_describe;
    }

    /**
     * 设置描述
     *
     * @param role_describe 描述
     */
    public void setRole_describe(String role_describe) {
        this.role_describe = role_describe == null ? null : role_describe.trim();
    }
}