package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单修改
 *
 * @author Trick
 * @date 2021/8/2 10:14
 */
@ApiModel(description = "订单修改")
public class EditOrderVo extends MainVo {
    @ApiModelProperty(value = "订单号", name = "orderNo",required = true)
    private String orderNo;

    @ApiModelProperty(value = "收货人", name = "userName")
    private String userName;
    @ApiModelProperty(value = "联系电话", name = "tel")
    private String tel;
    @ApiModelProperty(value = "省", name = "shen")
    private String shen;
    @ApiModelProperty(value = "市", name = "shi")
    private String shi;
    @ApiModelProperty(value = "县", name = "xian")
    private String xian;

    @ApiModelProperty(value = "详细地址", name = "address")
    private String address;
    @ApiModelProperty(value = "订单备注", name = "remarks")
    private String remarks;

    @ApiModelProperty(value = "订单状态 (只有订单在未付款的情况下才能修改,只能修改成待发货)", name = "orderStatus")
    private Integer orderStatus;
    @ApiModelProperty(value = "订单金额 (只有订单在未付款的情况下才能修改)", name = "orderAmt")
    private String orderAmt;

    public String getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(String orderAmt) {
        this.orderAmt = orderAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShen() {
        return shen;
    }

    public void setShen(String shen) {
        this.shen = shen;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }
}
