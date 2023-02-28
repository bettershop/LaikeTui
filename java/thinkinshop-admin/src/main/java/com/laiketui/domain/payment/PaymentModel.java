package com.laiketui.domain.payment;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "lkt_payment")
public class PaymentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 支付名称
     */
    private String name;

    /**
     * 1:线上、2:线下
     */
    private Byte type;

    /**
     * 支付类名称
     */
    private String class_name;

    /**
     * 描述
     */
    private String description;

    /**
     * 支付方式logo图片路径
     */
    private String logo;

    /**
     * 状态 0启用 1禁用
     */
    private Integer status;

    /**
     * 排序
     */
    private Short sort;

    /**
     * 支付说明
     */
    private String note;

    /**
     * 手续费
     */
    private BigDecimal poundage;

    /**
     * 手续费方式 1百分比 2固定值
     */
    private Byte poundage_type;

    /**
     * 1:PC端 2:移动端 3:通用
     */
    private Byte client_type;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取支付名称
     *
     * @return name - 支付名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置支付名称
     *
     * @param name 支付名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取1:线上、2:线下
     *
     * @return type - 1:线上、2:线下
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置1:线上、2:线下
     *
     * @param type 1:线上、2:线下
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取支付类名称
     *
     * @return class_name - 支付类名称
     */
    public String getClass_name() {
        return class_name;
    }

    /**
     * 设置支付类名称
     *
     * @param class_name 支付类名称
     */
    public void setClass_name(String class_name) {
        this.class_name = class_name == null ? null : class_name.trim();
    }

    /**
     * 获取描述
     *
     * @return description - 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     *
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取支付方式logo图片路径
     *
     * @return logo - 支付方式logo图片路径
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置支付方式logo图片路径
     *
     * @param logo 支付方式logo图片路径
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 获取状态 0启用 1禁用
     *
     * @return status - 状态 0启用 1禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0启用 1禁用
     *
     * @param status 状态 0启用 1禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取排序
     *
     * @return sort - 排序
     */
    public Short getSort() {
        return sort;
    }

    /**
     * 设置排序
     *
     * @param sort 排序
     */
    public void setSort(Short sort) {
        this.sort = sort;
    }

    /**
     * 获取支付说明
     *
     * @return note - 支付说明
     */
    public String getNote() {
        return note;
    }

    /**
     * 设置支付说明
     *
     * @param note 支付说明
     */
    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    /**
     * 获取手续费
     *
     * @return poundage - 手续费
     */
    public BigDecimal getPoundage() {
        return poundage;
    }

    /**
     * 设置手续费
     *
     * @param poundage 手续费
     */
    public void setPoundage(BigDecimal poundage) {
        this.poundage = poundage;
    }

    /**
     * 获取手续费方式 1百分比 2固定值
     *
     * @return poundage_type - 手续费方式 1百分比 2固定值
     */
    public Byte getPoundage_type() {
        return poundage_type;
    }

    /**
     * 设置手续费方式 1百分比 2固定值
     *
     * @param poundage_type 手续费方式 1百分比 2固定值
     */
    public void setPoundage_type(Byte poundage_type) {
        this.poundage_type = poundage_type;
    }

    /**
     * 获取1:PC端 2:移动端 3:通用
     *
     * @return client_type - 1:PC端 2:移动端 3:通用
     */
    public Byte getClient_type() {
        return client_type;
    }

    /**
     * 设置1:PC端 2:移动端 3:通用
     *
     * @param client_type 1:PC端 2:移动端 3:通用
     */
    public void setClient_type(Byte client_type) {
        this.client_type = client_type;
    }
}