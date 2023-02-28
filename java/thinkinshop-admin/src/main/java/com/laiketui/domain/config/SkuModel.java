package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;


/**
 * 属性表
 *
 * @author Trick
 * @date 2021/1/27 14:04
 */
@Table(name = "lkt_sku")
public class SkuModel {

    /**
     * 失效
     */
    @Transient
    public static final Integer SKU_STATUS_INVALID = 0;
    /**
     * 生效
     */
    @Transient
    public static final Integer SKU_STATUS_TAKE_EFFECT = 1;


    /**
     * 属性名
     */
    @Transient
    public static final Integer SKU_TYPE_ATTRIBUTE_NAME = 1;

    /**
     * 属性值
     */
    @Transient
    public static final Integer SKU_TYPE_ATTRIBUTE_VALUE = 2;

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
     * 上级ID
     */
    private Integer sid;

    /**
     * 数据编码
     */
    private String code;

    /**
     * 数据名称
     */
    private String name;

    /**
     * 是否生效 0:不是 1:是
     */
    private Integer status;

    /**
     * 类型 1.属性名 2.属性值
     */
    private Integer type;

    /**
     * 管理员名称
     */
    private String admin_name;

    /**
     * 回收站 0:正常 1:回收
     */
    private Integer recycle;

    /**
     * 添加时间
     */
    private Date add_date;

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
     * 获取上级ID
     *
     * @return sid - 上级ID
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置上级ID
     *
     * @param sid 上级ID
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取数据编码
     *
     * @return code - 数据编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置数据编码
     *
     * @param code 数据编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
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
     * 获取是否生效 0:不是 1:是
     *
     * @return status - 是否生效 0:不是 1:是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否生效 0:不是 1:是
     *
     * @param status 是否生效 0:不是 1:是
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取类型 1.属性名 2.属性值
     *
     * @return type - 类型 1.属性名 2.属性值
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 1.属性名 2.属性值
     *
     * @param type 类型 1.属性名 2.属性值
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取管理员名称
     *
     * @return admin_name - 管理员名称
     */
    public String getAdmin_name() {
        return admin_name;
    }

    /**
     * 设置管理员名称
     *
     * @param admin_name 管理员名称
     */
    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name == null ? null : admin_name.trim();
    }

    /**
     * 获取回收站 0:正常 1:回收
     *
     * @return recycle - 回收站 0:正常 1:回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0:正常 1:回收
     *
     * @param recycle 回收站 0:正常 1:回收
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
}