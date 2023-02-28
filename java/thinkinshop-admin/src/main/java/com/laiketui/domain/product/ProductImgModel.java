package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_product_img")
public class ProductImgModel {
    /**
     * 图片id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 产品图片
     */
    private String product_url;

    /**
     * 所属产品id
     */
    private Integer product_id;

    /**
     * 用户id
     */
    private String seller_id;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 获取图片id
     *
     * @return id - 图片id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置图片id
     *
     * @param id 图片id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取产品图片
     *
     * @return product_url - 产品图片
     */
    public String getProduct_url() {
        return product_url;
    }

    /**
     * 设置产品图片
     *
     * @param product_url 产品图片
     */
    public void setProduct_url(String product_url) {
        this.product_url = product_url == null ? null : product_url.trim();
    }

    /**
     * 获取所属产品id
     *
     * @return product_id - 所属产品id
     */
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * 设置所属产品id
     *
     * @param product_id 所属产品id
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /**
     * 获取用户id
     *
     * @return seller_id - 用户id
     */
    public String getSeller_id() {
        return seller_id;
    }

    /**
     * 设置用户id
     *
     * @param seller_id 用户id
     */
    public void setSeller_id(String seller_id) {
        this.seller_id = seller_id == null ? null : seller_id.trim();
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