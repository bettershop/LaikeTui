package com.laiketui.domain.mch;

import javax.persistence.*;


/**
 * 地址管理
 *
 * @author Trick
 * @date 2021/1/15 10:09
 */
@Table(name = "lkt_service_address")
public class ServiceAddressModel {
    /**
     * 售后地址id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 发货地址
     */
    @Transient
    public final static int TYPE_DELIVER_GOODS = 1;
    /**
     * 售后地址
     */
    @Transient
    public final static int TYPE_RETURN_GOODS = 2;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 收货人姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String tel;

    /**
     * 省id
     */
    private String sheng;

    /**
     * 市id
     */
    private String shi;

    /**
     * 区域id
     */
    private String xian;

    /**
     * 收货地址（不加省，市，区）
     */
    private String address;

    /**
     * 省市区+详细地址
     */
    private String address_xq;

    /**
     * 邮政编号
     */
    private Integer code;

    /**
     * 代表该地址是售后地址
     */
    private String uid;

    /**
     * 类型（1发货地址 2售后地址）
     */
    private Integer type;

    /**
     * 是否为默认收货地址 1.是  0.不是
     */
    private Integer is_default;

    /**
     * 获取售后地址id
     *
     * @return id - 售后地址id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置售后地址id
     *
     * @param id 售后地址id
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
     * 获取收货人姓名
     *
     * @return name - 收货人姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置收货人姓名
     *
     * @param name 收货人姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取联系电话
     *
     * @return tel - 联系电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置联系电话
     *
     * @param tel 联系电话
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * 获取省id
     *
     * @return sheng - 省id
     */
    public String getSheng() {
        return sheng;
    }

    /**
     * 设置省id
     *
     * @param sheng 省id
     */
    public void setSheng(String sheng) {
        this.sheng = sheng == null ? null : sheng.trim();
    }

    /**
     * 获取市id
     *
     * @return shi - 市id
     */
    public String getShi() {
        return shi;
    }

    /**
     * 设置市id
     *
     * @param shi 市id
     */
    public void setShi(String shi) {
        this.shi = shi == null ? null : shi.trim();
    }

    /**
     * 获取区域id
     *
     * @return xian - 区域id
     */
    public String getXian() {
        return xian;
    }

    /**
     * 设置区域id
     *
     * @param xian 区域id
     */
    public void setXian(String xian) {
        this.xian = xian == null ? null : xian.trim();
    }

    /**
     * 获取收货地址（不加省，市，区）
     *
     * @return address - 收货地址（不加省，市，区）
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置收货地址（不加省，市，区）
     *
     * @param address 收货地址（不加省，市，区）
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取省市区+详细地址
     *
     * @return address_xq - 省市区+详细地址
     */
    public String getAddress_xq() {
        return address_xq;
    }

    /**
     * 设置省市区+详细地址
     *
     * @param address_xq 省市区+详细地址
     */
    public void setAddress_xq(String address_xq) {
        this.address_xq = address_xq == null ? null : address_xq.trim();
    }

    /**
     * 获取邮政编号
     *
     * @return code - 邮政编号
     */
    public Integer getCode() {
        return code;
    }

    /**
     * 设置邮政编号
     *
     * @param code 邮政编号
     */
    public void setCode(Integer code) {
        this.code = code;
    }

    /**
     * 获取代表该地址是售后地址
     *
     * @return uid - 代表该地址是售后地址
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置代表该地址是售后地址
     *
     * @param uid 代表该地址是售后地址
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取类型（1发货地址 2售后地址）
     *
     * @return type - 类型（1发货地址 2售后地址）
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型（1发货地址 2售后地址）
     *
     * @param type 类型（1发货地址 2售后地址）
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否为默认收货地址 1.是  0.不是
     *
     * @return is_default - 是否为默认收货地址 1.是  0.不是
     */
    public Integer getIs_default() {
        return is_default;
    }

    /**
     * 设置是否为默认收货地址 1.是  0.不是
     *
     * @param is_default 是否为默认收货地址 1.是  0.不是
     */
    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }
}