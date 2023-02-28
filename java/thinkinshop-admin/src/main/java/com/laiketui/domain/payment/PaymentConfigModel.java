package com.laiketui.domain.payment;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 支付方式
 *
 * @author Trick
 * @date 2021/1/28 14:31
 */
@Table(name = "lkt_payment_config")
public class PaymentConfigModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 支付方式id
     */
    private Integer pid;

    /**
     * 是否显示 0否 1是
     */
    private Integer status;

    /**
     * 配置参数,json数据对象
     */
    private String config_data;

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
     * 获取支付方式id
     *
     * @return pid - 支付方式id
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置支付方式id
     *
     * @param pid 支付方式id
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }

    /**
     * 获取是否显示 0否 1是
     *
     * @return status - 是否显示 0否 1是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否显示 0否 1是
     *
     * @param status 是否显示 0否 1是
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取配置参数,json数据对象
     *
     * @return config_data - 配置参数,json数据对象
     */
    public String getConfig_data() {
        return config_data;
    }

    /**
     * 设置配置参数,json数据对象
     *
     * @param config_data 配置参数,json数据对象
     */
    public void setConfig_data(String config_data) {
        this.config_data = config_data == null ? null : config_data.trim();
    }
}