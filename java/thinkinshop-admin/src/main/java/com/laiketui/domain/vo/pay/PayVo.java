package com.laiketui.domain.vo.pay;

import io.swagger.annotations.ApiModelProperty;

/**
 * 公共支付vo
 *
 * @author Trick
 * @date 2021/4/1 10:09
 */
public class PayVo {
    @ApiModelProperty(value = "商城id")
    private int storeId;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "订单号")
    private String orderno;
    @ApiModelProperty(value = "支付方式")
    private String payType;
    @ApiModelProperty(value = "支付备注")
    private String remark;
    @ApiModelProperty(value = "第三方支付订单号")
    private String tradeNo;

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }
}
