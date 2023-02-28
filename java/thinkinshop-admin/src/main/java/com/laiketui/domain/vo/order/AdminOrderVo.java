package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

/**
 * 申请售后
 * @author wangxian
 */
@ApiModel(description = "管理后台订单列表参数")
public class AdminOrderVo  extends MainVo {

    /**管理员账户*/
    private String adminName;
    /**订单类型*/
    private String otype;
    /**店铺名*/
    private String mchName;
    /**订单状态*/
    private String status;
    /**订单状态*/
    private String newsStatus;
    /**发货状态*/
    private String deliveryStatus;
    /**未查看信息*/
    private String readd;
    /**新订单：今日订单*/
    private String xOrder;
    /**订单状态*/
    private String ostatus;
    /**订单号*/
    private String sNo;
    /**品牌*/
    private String brand;
    /**来源*/
    private String source;
    /**开始时间*/
    private String startdate;
    /**结束时间*/
    private String enddate;
    /**店铺id*/
    private Integer mchId;
    /**导出数据类型*/
    private String pageto;
    /**订单id*/
    private String oid;
    /**多个订单号*/
    private String data;
    /**多个订单号*/
    private String orderOpType;

    public String getOrderOpType() {
        return orderOpType;
    }

    public void setOrderOpType(String orderOpType) {
        this.orderOpType = orderOpType;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    public String getPageto() {
        return pageto;
    }

    public void setPageto(String pageto) {
        this.pageto = pageto;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNewsStatus() {
        return newsStatus;
    }

    public void setNewsStatus(String newsStatus) {
        this.newsStatus = newsStatus;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getReadd() {
        return readd;
    }

    public void setReadd(String readd) {
        this.readd = readd;
    }

    public String getxOrder() {
        return xOrder;
    }

    public void setxOrder(String xOrder) {
        this.xOrder = xOrder;
    }

    public String getOstatus() {
        return ostatus;
    }

    public void setOstatus(String ostatus) {
        this.ostatus = ostatus;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getMchId() {
        return mchId;
    }

    public void setMchId(int mchId) {
        this.mchId = mchId;
    }
}
