package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;

/**
 * 跳转路径
 *
 * @author Trick
 * @date 2021/6/30 14:00
 */
@Table(name = "lkt_jump_path")
public class JumpPathModel {
    //跳转类型
    public interface JumpType{
        //分类
        @Transient
        int JUMP_TYPE0_GOODS_CLASS = 1;
        //商品
        @Transient
        int JUMP_TYPE0_GOODS = 2;
        //店铺
        @Transient
        int JUMP_TYPE0_MCH = 3;

        //移动端
        @Transient
        int JUMP_TYPE_APP = 1;
        //pc端
        @Transient
        int JUMP_TYPE_PC = 2;
    }

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
     * 跳转类型 1.分类 2.商品 3.店铺
     */
    private Integer type0;

    /**
     * 类型 1.移动端 2.PC商城
     */
    private Integer type;

    /**
     * 标题
     */
    private String name;

    /**
     * 路径
     */
    private String url;

    /**
     * 是否启用 0.不开启 1.开启
     */
    private Integer status;

    /**
     * 是否有参数 0.没有参数 1.有参数
     */
    private Integer parameter_status;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 上级id
     */
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

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
     * 获取跳转类型 1.分类 2.商品 3.店铺
     *
     * @return type0 - 跳转类型 1.分类 2.商品 3.店铺
     */
    public Integer getType0() {
        return type0;
    }

    /**
     * 设置跳转类型 1.分类 2.商品 3.店铺
     *
     * @param type0 跳转类型 1.分类 2.商品 3.店铺
     */
    public void setType0(Integer type0) {
        this.type0 = type0;
    }

    /**
     * 获取类型 1.移动端 2.PC商城
     *
     * @return type - 类型 1.移动端 2.PC商城
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 1.移动端 2.PC商城
     *
     * @param type 类型 1.移动端 2.PC商城
     */
    public void setType(Integer type) {
        this.type = type;
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
     * 获取是否启用 0.不开启 1.开启
     *
     * @return status - 是否启用 0.不开启 1.开启
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否启用 0.不开启 1.开启
     *
     * @param status 是否启用 0.不开启 1.开启
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取是否有参数 0.没有参数 1.有参数
     *
     * @return parameter_status - 是否有参数 0.没有参数 1.有参数
     */
    public Integer getParameter_status() {
        return parameter_status;
    }

    /**
     * 设置是否有参数 0.没有参数 1.有参数
     *
     * @param parameter_status 是否有参数 0.没有参数 1.有参数
     */
    public void setParameter_status(Integer parameter_status) {
        this.parameter_status = parameter_status;
    }

    /**
     * 获取参数
     *
     * @return parameter - 参数
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * 设置参数
     *
     * @param parameter 参数
     */
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
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