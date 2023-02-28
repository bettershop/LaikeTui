package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 配置钱包参数
 *
 * @author Trick
 * @date 2021/1/11 15:02
 */
@ApiModel(description = "配置钱包参数")
public class WalletVo extends MainVo {

    @ApiModelProperty(value="最小充值金额",name="minMoney")
    private BigDecimal minMoney;
    @ApiModelProperty(value="最小提现金额",name="minOutMoney")
    private BigDecimal minOutMoney;
    @ApiModelProperty(value="最大提现金额",name="maxOutMoney")
    private BigDecimal maxOutMoney;
    @ApiModelProperty(value="手续费",name="serviceMoney")
    private BigDecimal serviceMoney;
    @ApiModelProperty(value="钱包单位",name="unit")
    private String unit;

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public BigDecimal getMinOutMoney() {
        return minOutMoney;
    }

    public void setMinOutMoney(BigDecimal minOutMoney) {
        this.minOutMoney = minOutMoney;
    }

    public BigDecimal getMaxOutMoney() {
        return maxOutMoney;
    }

    public void setMaxOutMoney(BigDecimal maxOutMoney) {
        this.maxOutMoney = maxOutMoney;
    }

    public BigDecimal getServiceMoney() {
        return serviceMoney;
    }

    public void setServiceMoney(BigDecimal serviceMoney) {
        this.serviceMoney = serviceMoney;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
