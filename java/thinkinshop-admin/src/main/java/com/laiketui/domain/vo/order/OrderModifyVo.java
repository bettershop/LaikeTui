package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * 后台订单修改
 * @author wangxain
 */
@ApiModel(description = "订单修改")
public class OrderModifyVo extends MainVo {

    /**修改条件*/
    private String where;
    /**更新*/
    private String updata;
    /**省*/
    private String sheng;
    /**市*/
    private String shi;
    /**县*/
    private String xian;
    /**订单号*/
    private String sNo;
    /**收货人信息*/
    private String address;
    /**收货人信息*/
    private String mobile;
    /**收货人姓名*/
    private String name;
    /**状态*/
    private Integer status;
    /**备注*/
    private String remarks;
    /**操作类型*/
    private String type;
    /**管理员名*/
    private String adminName;
    /**订单价格*/
    private BigDecimal zPrice;
    /**订单价格 修改：modify 查看：see*/
    private String orderOpType = "modify";

    public String getOrderOpType() {
        return orderOpType;
    }

    public void setOrderOpType(String orderOpType) {
        this.orderOpType = orderOpType;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public BigDecimal getzPrice() {
        return zPrice;
    }

    public void setzPrice(BigDecimal zPrice) {
        this.zPrice = zPrice;
    }

    public String getUpdata() {
        return updata;
    }

    public void setUpdata(String updata) {
        this.updata = updata;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
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

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }
}
