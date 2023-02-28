package com.laiketui.domain.product;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_product_number")
public class ProductNumberModel {

    /**
     * 已使用
     */
    @Transient
    public static final Integer STATUS_USED = 1;

    /**
     * 撤销
     */
    @Transient
    public static final Integer STATUS_REVOKE = 2;


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
     * 店铺id
     */
    private String mch_id;

    /**
     * 操作人账号
     */
    private String operation;

    /**
     * 商品编号
     */
    private String product_number;

    /**
     * 状态：1.使用 2.撤销
     */
    private Integer status = STATUS_USED;

    /**
     * 时间
     */
    private Date addtime;

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
     * 获取店铺id
     *
     * @return mch_id - 店铺id
     */
    public String getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺id
     *
     * @param mch_id 店铺id
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id == null ? null : mch_id.trim();
    }

    /**
     * 获取操作人账号
     *
     * @return operation - 操作人账号
     */
    public String getOperation() {
        return operation;
    }

    /**
     * 设置操作人账号
     *
     * @param operation 操作人账号
     */
    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    /**
     * 获取商品编号
     *
     * @return product_number - 商品编号
     */
    public String getProduct_number() {
        return product_number;
    }

    /**
     * 设置商品编号
     *
     * @param product_number 商品编号
     */
    public void setProduct_number(String product_number) {
        this.product_number = product_number == null ? null : product_number.trim();
    }

    /**
     * 获取状态：1.使用 2.撤销
     *
     * @return status - 状态：1.使用 2.撤销
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：1.使用 2.撤销
     *
     * @param status 状态：1.使用 2.撤销
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取时间
     *
     * @return addtime - 时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置时间
     *
     * @param addtime 时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }
}