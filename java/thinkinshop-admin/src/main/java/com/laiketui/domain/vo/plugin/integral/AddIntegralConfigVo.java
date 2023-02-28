package com.laiketui.domain.vo.plugin.integral;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑配置信息
 *
 * @author Trick
 * @date 2021/5/12 16:21
 */
@ApiModel(description = "添加/编辑配置信息")
public class AddIntegralConfigVo extends MainVo {
    @ApiModelProperty(name = "status", value = "是否开启插件")
    private Integer status;
    @ApiModelProperty(name = "imgUrls", value = "轮播图")
    private String imgUrls;
    @ApiModelProperty(name = "content", value = "规则内容")
    private String content;
    @ApiModelProperty(name = "isFreeShipping", value = "是否开启包邮设置")
    private Integer isFreeShipping;
    @ApiModelProperty(name = "goodsNum", value = "满足包邮条件的商品数量")
    private Integer goodsNum;
    @ApiModelProperty(name = "proportion", value = "积分比例")
    private BigDecimal proportion;
    @ApiModelProperty(name = "giveStatus", value = "发放状态(0=收货后 1=付款后)")
    private Integer giveStatus;
    @ApiModelProperty(name = "overdueTime", value = "积分有效时间从积分获取后开始计算，至 x 失效")
    private Integer overdueTime;

    @ApiModelProperty(name = "autoReceivingGoodsDay", value = "自动收货时间 (天)")
    private BigDecimal autoReceivingGoodsDay;
    @ApiModelProperty(name = "orderInvalidTime", value = "订单失效时间 (小时)")
    private BigDecimal orderInvalidTime;
    @ApiModelProperty(name = "returnDay", value = "订单售后时间 (天)")
    private BigDecimal returnDay;
    @ApiModelProperty(name = "deliverRemind", value = "提醒限制 (秒)")
    private BigDecimal deliverRemind;
    @ApiModelProperty(name = "autoCommentDay", value = "自动好评时间 (天)")
    private BigDecimal autoCommentDay;

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public Integer getGiveStatus() {
        return giveStatus;
    }

    public void setGiveStatus(Integer giveStatus) {
        this.giveStatus = giveStatus;
    }

    public Integer getOverdueTime() {
        return overdueTime;
    }

    public void setOverdueTime(Integer overdueTime) {
        this.overdueTime = overdueTime;
    }

    public BigDecimal getAutoReceivingGoodsDay() {
        return autoReceivingGoodsDay;
    }

    public void setAutoReceivingGoodsDay(BigDecimal autoReceivingGoodsDay) {
        this.autoReceivingGoodsDay = autoReceivingGoodsDay;
    }

    public BigDecimal getOrderInvalidTime() {
        return orderInvalidTime;
    }

    public void setOrderInvalidTime(BigDecimal orderInvalidTime) {
        this.orderInvalidTime = orderInvalidTime;
    }

    public BigDecimal getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(BigDecimal returnDay) {
        this.returnDay = returnDay;
    }

    public BigDecimal getDeliverRemind() {
        return deliverRemind;
    }

    public void setDeliverRemind(BigDecimal deliverRemind) {
        this.deliverRemind = deliverRemind;
    }

    public BigDecimal getAutoCommentDay() {
        return autoCommentDay;
    }

    public void setAutoCommentDay(BigDecimal autoCommentDay) {
        this.autoCommentDay = autoCommentDay;
    }

    public Integer getIsFreeShipping() {
        return isFreeShipping;
    }

    public void setIsFreeShipping(Integer isFreeShipping) {
        this.isFreeShipping = isFreeShipping;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(String imgUrls) {
        this.imgUrls = imgUrls;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
