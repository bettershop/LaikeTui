package com.laiketui.domain.mch;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_admin")
public class AdminModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 系统管理员
     */
    @Transient
    public static final int TYPE_SYSTEM_ADMIN = 0;
    /**
     * 客户
     */
    @Transient
    public static final int TYPE_CLIENT = 1;
    /**
     * 商城管理员
     */
    @Transient
    public static final int TYPE_STORE_ADMIN = 2;
    /**
     * 店主
     */
    @Transient
    public static final int TYPE_SHOP_MAN = 3;


    /**
     * 禁用
     */
    @Transient
    public static final int STATUS_DISABLE = 1;
    /**
     * 启用
     */
    @Transient
    public static final int STATUS_OPEN = 2;

    /**
     * 上级id
     */
    private Integer sid;

    /**
     * 账号
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 管理类型
     */
    private Integer admin_type;

    /**
     * 许可
     */
    private String permission;

    /**
     * 角色
     */
    private String role;

    /**
     * 类型 0:系统管理员 1：客户 2:商城管理员 3:店主
     */
    private Integer type;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 店铺ID
     */
    private Integer shop_id;

    /**
     * 状态 1:禁用 2：启用
     */
    private Integer status;

    /**
     * 管理员头像
     */
    private String portrait;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 性别（1 男  2 女）
     */
    private Integer sex;

    /**
     * 手机号码
     */
    private String tel;

    /**
     * 令牌
     */
    private String token;

    /**
     * 登入ip地址
     */
    private String ip;

    /**
     * 回收站 0:不回收 1:回收
     */
    private Integer recycle;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 登录次数
     */
    private Integer login_num;

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
     * @return sid - 上级id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置上级id
     *
     * @param sid 上级id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取账号
     *
     * @return name - 账号
     */
    public String getName() {
        return name;
    }

    /**
     * 设置账号
     *
     * @param name 账号
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取管理类型
     *
     * @return admin_type - 管理类型
     */
    public Integer getAdmin_type() {
        return admin_type;
    }

    /**
     * 设置管理类型
     *
     * @param admin_type 管理类型
     */
    public void setAdmin_type(Integer admin_type) {
        this.admin_type = admin_type;
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
     * 获取角色
     *
     * @return role - 角色
     */
    public String getRole() {
        return role;
    }

    /**
     * 设置角色
     *
     * @param role 角色
     */
    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    /**
     * 获取类型 0:系统管理员 1：客户 2:商城管理员 3:店主
     *
     * @return type - 类型 0:系统管理员 1：客户 2:商城管理员 3:店主
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:系统管理员 1：客户 2:商城管理员 3:店主
     *
     * @param type 类型 0:系统管理员 1：客户 2:商城管理员 3:店主
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取店铺ID
     *
     * @return shop_id - 店铺ID
     */
    public Integer getShop_id() {
        return shop_id;
    }

    /**
     * 设置店铺ID
     *
     * @param shop_id 店铺ID
     */
    public void setShop_id(Integer shop_id) {
        this.shop_id = shop_id;
    }

    /**
     * 获取状态 1:禁用 2：启用
     *
     * @return status - 状态 1:禁用 2：启用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 1:禁用 2：启用
     *
     * @param status 状态 1:禁用 2：启用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取管理员头像
     *
     * @return portrait - 管理员头像
     */
    public String getPortrait() {
        return portrait;
    }

    /**
     * 设置管理员头像
     *
     * @param portrait 管理员头像
     */
    public void setPortrait(String portrait) {
        this.portrait = portrait == null ? null : portrait.trim();
    }

    /**
     * 获取昵称
     *
     * @return nickname - 昵称
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * 设置昵称
     *
     * @param nickname 昵称
     */
    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    /**
     * 获取生日
     *
     * @return birthday - 生日
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * 设置生日
     *
     * @param birthday 生日
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    /**
     * 获取性别（1 男  2 女）
     *
     * @return sex - 性别（1 男  2 女）
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置性别（1 男  2 女）
     *
     * @param sex 性别（1 男  2 女）
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取手机号码
     *
     * @return tel - 手机号码
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置手机号码
     *
     * @param tel 手机号码
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * 获取令牌
     *
     * @return token - 令牌
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置令牌
     *
     * @param token 令牌
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取登入ip地址
     *
     * @return ip - 登入ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置登入ip地址
     *
     * @param ip 登入ip地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取回收站 0:不回收 1:回收
     *
     * @return recycle - 回收站 0:不回收 1:回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0:不回收 1:回收
     *
     * @param recycle 回收站 0:不回收 1:回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
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
     * 获取登录次数
     *
     * @return login_num - 登录次数
     */
    public Integer getLogin_num() {
        return login_num;
    }

    /**
     * 设置登录次数
     *
     * @param login_num 登录次数
     */
    public void setLogin_num(Integer login_num) {
        this.login_num = login_num;
    }
}