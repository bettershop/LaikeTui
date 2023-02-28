package com.laiketui.domain.user;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_user_bank_card")
public class UserBankCardModel {
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
     * 用户ID
     */
    private String user_id;

    /**
     * 持卡人
     */
    @Column(name = "Cardholder")
    private String cardholder;

    /**
     * 身份证
     */
    private String id_card;

    /**
     * 银行名称
     */
    @Column(name = "Bank_name")
    private String bank_name;

    /**
     * 支行名称
     */
    private String branch;

    /**
     * 银行卡号
     */
    @Column(name = "Bank_card_number")
    private String bank_card_number;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 是否默认地址 1默认
     */
    private Integer is_default;

    /**
     * 店铺ID
     */
    private Integer mch_id;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID
     *
     * @param user_id 用户ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取持卡人
     *
     * @return Cardholder - 持卡人
     */
    public String getCardholder() {
        return cardholder;
    }

    /**
     * 设置持卡人
     *
     * @param cardholder 持卡人
     */
    public void setCardholder(String cardholder) {
        this.cardholder = cardholder == null ? null : cardholder.trim();
    }

    /**
     * 获取身份证
     *
     * @return id_card - 身份证
     */
    public String getId_card() {
        return id_card;
    }

    /**
     * 设置身份证
     *
     * @param id_card 身份证
     */
    public void setId_card(String id_card) {
        this.id_card = id_card == null ? null : id_card.trim();
    }

    /**
     * 获取银行名称
     *
     * @return Bank_name - 银行名称
     */
    public String getBank_name() {
        return bank_name;
    }

    /**
     * 设置银行名称
     *
     * @param bank_name 银行名称
     */
    public void setBank_name(String bank_name) {
        this.bank_name = bank_name == null ? null : bank_name.trim();
    }

    /**
     * 获取支行名称
     *
     * @return branch - 支行名称
     */
    public String getBranch() {
        return branch;
    }

    /**
     * 设置支行名称
     *
     * @param branch 支行名称
     */
    public void setBranch(String branch) {
        this.branch = branch == null ? null : branch.trim();
    }

    /**
     * 获取银行卡号
     *
     * @return Bank_card_number - 银行卡号
     */
    public String getBank_card_number() {
        return bank_card_number;
    }

    /**
     * 设置银行卡号
     *
     * @param bank_card_number 银行卡号
     */
    public void setBank_card_number(String bank_card_number) {
        this.bank_card_number = bank_card_number == null ? null : bank_card_number.trim();
    }

    /**
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
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
     * 获取是否默认地址 1默认
     *
     * @return is_default - 是否默认地址 1默认
     */
    public Integer getIs_default() {
        return is_default;
    }

    /**
     * 设置是否默认地址 1默认
     *
     * @param is_default 是否默认地址 1默认
     */
    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
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
}