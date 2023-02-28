package com.laiketui.domain.vo.pay;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

import java.math.BigDecimal;

/**
 * 支付
 * @author wangxain
 */
@ApiModel(description = "支付")
public class PaymentVo extends MainVo {

    @ParamsMapping("type")
    private String payType;
    /**支付金额*/
    private String payment_money;
    /**地址id*/
    private String address_id;
    /**备注*/
    private String remarks;
    /**订单列表*/
    private String order_list;
    /**等级*/
    @ParamsMapping("grade_l")
    private int grade;
    /**订单ID*/
    private int order_id;
    /**订单号*/
    @ParamsMapping("order_no")
    private String sNo;
    /**参数*/
    private String parameter;
    /**支付类型*/
    private String type;
    /**JSAPI code*/
    private String code;
    /**支付标题 商品名*/
    private String title;
    /** jsapi 支付刷新界面 */
    private String url;
    /** 充值金额 */
    private BigDecimal total;

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayment_money() {
        return payment_money;
    }

    public void setPayment_money(String payment_money) {
        this.payment_money = payment_money;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getOrder_list() {
        return order_list;
    }

    public void setOrder_list(String order_list) {
        this.order_list = order_list;
    }
}
