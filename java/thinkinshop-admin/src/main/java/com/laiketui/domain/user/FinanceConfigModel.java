package com.laiketui.domain.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 钱包配置表
 * @author Trick
 */
@Table(name = "lkt_finance_config")
public class FinanceConfigModel {
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
     * 插件id
     */
    private Integer plug_ins_id;

    /**
     * 最小充值金额
     */
    private BigDecimal min_cz;

    /**
     * 最少提现金额
     */
    private BigDecimal min_amount;

    /**
     * 最大提现金额
     */
    private BigDecimal max_amount;

    /**
     * 手续费
     */
    private BigDecimal service_charge;

    /**
     * 小程序钱包单位
     */
    private String unit;

    /**
     * 修改时间
     */
    private Date modify_date;

    /**
     * 提现倍数
     */
    private Integer multiple;

    /**
     * 转账倍数
     */
    private Integer transfer_multiple;

    /**
     * 充值倍数
     */
    private Integer cz_multiple;

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
     * 获取插件id
     *
     * @return plug_ins_id - 插件id
     */
    public Integer getPlug_ins_id() {
        return plug_ins_id;
    }

    /**
     * 设置插件id
     *
     * @param plug_ins_id 插件id
     */
    public void setPlug_ins_id(Integer plug_ins_id) {
        this.plug_ins_id = plug_ins_id;
    }

    /**
     * 获取最小充值金额
     *
     * @return min_cz - 最小充值金额
     */
    public BigDecimal getMin_cz() {
        return min_cz;
    }

    /**
     * 设置最小充值金额
     *
     * @param min_cz 最小充值金额
     */
    public void setMin_cz(BigDecimal min_cz) {
        this.min_cz = min_cz;
    }

    /**
     * 获取最少提现金额
     *
     * @return min_amount - 最少提现金额
     */
    public BigDecimal getMin_amount() {
        return min_amount;
    }

    /**
     * 设置最少提现金额
     *
     * @param min_amount 最少提现金额
     */
    public void setMin_amount(BigDecimal min_amount) {
        this.min_amount = min_amount;
    }

    /**
     * 获取最大提现金额
     *
     * @return max_amount - 最大提现金额
     */
    public BigDecimal getMax_amount() {
        return max_amount;
    }

    /**
     * 设置最大提现金额
     *
     * @param max_amount 最大提现金额
     */
    public void setMax_amount(BigDecimal max_amount) {
        this.max_amount = max_amount;
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
     * 获取小程序钱包单位
     *
     * @return unit - 小程序钱包单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置小程序钱包单位
     *
     * @param unit 小程序钱包单位
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    /**
     * 获取修改时间
     *
     * @return modify_date - 修改时间
     */
    public Date getModify_date() {
        return modify_date;
    }

    /**
     * 设置修改时间
     *
     * @param modify_date 修改时间
     */
    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    /**
     * 获取提现倍数
     *
     * @return multiple - 提现倍数
     */
    public Integer getMultiple() {
        return multiple;
    }

    /**
     * 设置提现倍数
     *
     * @param multiple 提现倍数
     */
    public void setMultiple(Integer multiple) {
        this.multiple = multiple;
    }

    /**
     * 获取转账倍数
     *
     * @return transfer_multiple - 转账倍数
     */
    public Integer getTransfer_multiple() {
        return transfer_multiple;
    }

    /**
     * 设置转账倍数
     *
     * @param transfer_multiple 转账倍数
     */
    public void setTransfer_multiple(Integer transfer_multiple) {
        this.transfer_multiple = transfer_multiple;
    }

    /**
     * 获取充值倍数
     *
     * @return cz_multiple - 充值倍数
     */
    public Integer getCz_multiple() {
        return cz_multiple;
    }

    /**
     * 设置充值倍数
     *
     * @param cz_multiple 充值倍数
     */
    public void setCz_multiple(Integer cz_multiple) {
        this.cz_multiple = cz_multiple;
    }
}