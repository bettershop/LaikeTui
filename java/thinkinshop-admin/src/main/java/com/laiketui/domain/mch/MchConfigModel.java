package com.laiketui.domain.mch;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "lkt_mch_config")
public class MchConfigModel {
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
     * 店铺默认logo
     */
    private String logo;

    /**
     * 结算方式
     */
    private String settlement;

    /**
     * 最低提现金额


     */
    private BigDecimal min_charge;

    /**
     * 最大提现金额


     */
    private BigDecimal max_charge;

    /**
     * 手续费
     */
    private BigDecimal service_charge;

    /**
     * 提现说明
     */
    private String illustrate;

    /**
     * 入驻协议
     */
    private String agreement;

    /**
     * 删除设置
     */
    private Integer delete_settings;

    /**
     * 是否开启插件
     */
    private Integer is_display;

    /**
     * 保证金开关
     */
    private Integer promise_switch;

    /**
     * 保证金金额
     */
    private BigDecimal promise_amt;

    /**
     * 保证金说明
     */
    private String promise_text;

    public String getPromise_text() {
        return promise_text;
    }

    public void setPromise_text(String promise_text) {
        this.promise_text = promise_text;
    }

    public Integer getIs_display() {
        return is_display;
    }

    public void setIs_display(Integer is_display) {
        this.is_display = is_display;
    }

    public Integer getPromise_switch() {
        return promise_switch;
    }

    public void setPromise_switch(Integer promise_switch) {
        this.promise_switch = promise_switch;
    }

    public BigDecimal getPromise_amt() {
        return promise_amt;
    }

    public void setPromise_amt(BigDecimal promise_amt) {
        this.promise_amt = promise_amt;
    }

    /**
     * 商品设置  1.上传商品 2.

自选商品
     */
    private String commodity_setup;

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
     * 获取店铺默认logo
     *
     * @return logo - 店铺默认logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置店铺默认logo
     *
     * @param logo 店铺默认logo
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 获取结算方式
     *
     * @return settlement - 结算方式
     */
    public String getSettlement() {
        return settlement;
    }

    /**
     * 设置结算方式
     *
     * @param settlement 结算方式
     */
    public void setSettlement(String settlement) {
        this.settlement = settlement == null ? null : settlement.trim();
    }

    /**
     * 获取最低提现金额


     *
     * @return min_charge - 最低提现金额


     */
    public BigDecimal getMin_charge() {
        return min_charge;
    }

    /**
     * 设置最低提现金额


     *
     * @param min_charge 最低提现金额


     */
    public void setMin_charge(BigDecimal min_charge) {
        this.min_charge = min_charge;
    }

    /**
     * 获取最大提现金额


     *
     * @return max_charge - 最大提现金额


     */
    public BigDecimal getMax_charge() {
        return max_charge;
    }

    /**
     * 设置最大提现金额


     *
     * @param max_charge 最大提现金额


     */
    public void setMax_charge(BigDecimal max_charge) {
        this.max_charge = max_charge;
    }

    /**
     * 获取手续费
     *
     * @return service_charge - 手续费
     */
    public BigDecimal getService_charge() {
        return service_charge;
    }

    /**
     * 设置手续费
     *
     * @param service_charge 手续费
     */
    public void setService_charge(BigDecimal service_charge) {
        this.service_charge = service_charge;
    }

    /**
     * 获取提现说明
     *
     * @return illustrate - 提现说明
     */
    public String getIllustrate() {
        return illustrate;
    }

    /**
     * 设置提现说明
     *
     * @param illustrate 提现说明
     */
    public void setIllustrate(String illustrate) {
        this.illustrate = illustrate == null ? null : illustrate.trim();
    }

    /**
     * 获取入驻协议
     *
     * @return agreement - 入驻协议
     */
    public String getAgreement() {
        return agreement;
    }

    /**
     * 设置入驻协议
     *
     * @param agreement 入驻协议
     */
    public void setAgreement(String agreement) {
        this.agreement = agreement == null ? null : agreement.trim();
    }

    /**
     * 获取删除设置
     *
     * @return delete_settings - 删除设置
     */
    public Integer getDelete_settings() {
        return delete_settings;
    }

    /**
     * 设置删除设置
     *
     * @param delete_settings 删除设置
     */
    public void setDelete_settings(Integer delete_settings) {
        this.delete_settings = delete_settings;
    }

    /**
     * 获取商品设置  1.上传商品 2.

自选商品
     *
     * @return commodity_setup - 商品设置  1.上传商品 2.

自选商品
     */
    public String getCommodity_setup() {
        return commodity_setup;
    }

    /**
     * 设置商品设置  1.上传商品 2.

自选商品
     *
     * @param commodity_setup 商品设置  1.上传商品 2.

自选商品
     */
    public void setCommodity_setup(String commodity_setup) {
        this.commodity_setup = commodity_setup == null ? null : commodity_setup.trim();
    }
}