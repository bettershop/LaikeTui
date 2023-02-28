package com.laiketui.domain.vo;

import com.laiketui.domain.annotation.ParamsMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申请提现参数
 *
 * @author Trick
 * @date 2020/11/3 12:02
 */
@ApiModel(description="申请提现参数")
public class Withdrawals1Vo extends MainVo{

    @ApiModelProperty(value="店铺id",name="shop_id")
    @ParamsMapping("shop_id")
    private Integer shopId;
    @ApiModelProperty(value="提现id",name="id")
    private Integer id;
    @ApiModelProperty(value="银行卡id",name="bank_id")
    @ParamsMapping("bank_id")
    private Integer bankId;
    @ApiModelProperty(value="提现金额",name="amoney")
    private String amoney;
    @ApiModelProperty(value="手机号",name="mobile")
    private String mobile;
    @ApiModelProperty(value="验证码",name="keyCode")
    private String keyCode;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getAmoney() {
        return amoney;
    }

    public void setAmoney(String amoney) {
        this.amoney = amoney;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(String keyCode) {
        this.keyCode = keyCode;
    }
}
