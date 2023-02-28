package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_stock")
public class StockModel {
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
     * 商品id
     */
    private Integer product_id;

    /**
     * 属性id
     */
    private Integer attribute_id;

    /**
     * 总库存
     */
    private Integer total_num;

    /**
     * 入库/出库
     */
    private Integer flowing_num;

    /**
     * 类型 0.入库 1.出库 2.预警
     */
    private Integer type;

    /**
     * 购买方
     */
    private String user_id;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 内容
     */
    private String content;

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
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * 设置商品id
     *
     * @param product_id 商品id
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /**
     * 获取属性id
     *
     * @return attribute_id - 属性id
     */
    public Integer getAttribute_id() {
        return attribute_id;
    }

    /**
     * 设置属性id
     *
     * @param attribute_id 属性id
     */
    public void setAttribute_id(Integer attribute_id) {
        this.attribute_id = attribute_id;
    }

    /**
     * 获取总库存
     *
     * @return total_num - 总库存
     */
    public Integer getTotal_num() {
        return total_num;
    }

    /**
     * 设置总库存
     *
     * @param total_num 总库存
     */
    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    /**
     * 获取入库/出库
     *
     * @return flowing_num - 入库/出库
     */
    public Integer getFlowing_num() {
        return flowing_num;
    }

    /**
     * 设置入库/出库
     *
     * @param flowing_num 入库/出库
     */
    public void setFlowing_num(Integer flowing_num) {
        this.flowing_num = flowing_num;
    }

    /**
     * 获取类型 0.入库 1.出库 2.预警
     *
     * @return type - 类型 0.入库 1.出库 2.预警
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0.入库 1.出库 2.预警
     *
     * @param type 类型 0.入库 1.出库 2.预警
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取购买方
     *
     * @return user_id - 购买方
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置购买方
     *
     * @param user_id 购买方
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
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
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}