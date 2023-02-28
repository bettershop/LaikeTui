package com.laiketui.domain.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "lkt_user_address")
public class UserAddress implements Serializable {
    /**
     * 地址id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 收货人
     */
    private String name;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 省id
     */
    private String sheng;

    /**
     * 市id
     */
    private String city;

    /**
     * 区域id
     */
    private String quyu;

    /**
     * 收货地址（不加省市区）
     */
    private String address;

    /**
     * 省市区+详细地址
     */
    private String address_xq;

    /**
     * 邮政编号
     */
    private Integer code = 0;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 是否默认地址 1默认
     */
    private Integer is_default;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getQuyu() {
        return quyu;
    }

    public void setQuyu(String quyu) {
        this.quyu = quyu;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress_xq() {
        return address_xq;
    }

    public void setAddress_xq(String address_xq) {
        this.address_xq = address_xq;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }
}