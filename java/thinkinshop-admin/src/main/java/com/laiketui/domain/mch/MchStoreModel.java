package com.laiketui.domain.mch;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_mch_store")
public class MchStoreModel {
    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 店铺ID
     */
    private Integer mch_id;

    /**
     * 门店名称
     */
    private String name;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 营业时间
     */
    private String business_hours;

    /**
     * 省
     */
    private String sheng;

    /**
     * 市
     */
    private String shi;

    /**
     * 县
     */
    private String xian;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 是否默认 0：不是 ；1： 是
     */
    private Integer is_default;

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
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
     * @return mch_id - 店铺ID
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺ID
     *
     * @param mch_id 店铺ID
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取门店名称
     *
     * @return name - 门店名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置门店名称
     *
     * @param name 门店名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取联系电话
     *
     * @return mobile - 联系电话
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置联系电话
     *
     * @param mobile 联系电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取营业时间
     *
     * @return business_hours - 营业时间
     */
    public String getBusiness_hours() {
        return business_hours;
    }

    /**
     * 设置营业时间
     *
     * @param business_hours 营业时间
     */
    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours == null ? null : business_hours.trim();
    }

    /**
     * 获取省
     *
     * @return sheng - 省
     */
    public String getSheng() {
        return sheng;
    }

    /**
     * 设置省
     *
     * @param sheng 省
     */
    public void setSheng(String sheng) {
        this.sheng = sheng == null ? null : sheng.trim();
    }

    /**
     * 获取市
     *
     * @return shi - 市
     */
    public String getShi() {
        return shi;
    }

    /**
     * 设置市
     *
     * @param shi 市
     */
    public void setShi(String shi) {
        this.shi = shi == null ? null : shi.trim();
    }

    /**
     * 获取县
     *
     * @return xian - 县
     */
    public String getXian() {
        return xian;
    }

    /**
     * 设置县
     *
     * @param xian 县
     */
    public void setXian(String xian) {
        this.xian = xian == null ? null : xian.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
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
     * 获取经度
     *
     * @return longitude - 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }
}