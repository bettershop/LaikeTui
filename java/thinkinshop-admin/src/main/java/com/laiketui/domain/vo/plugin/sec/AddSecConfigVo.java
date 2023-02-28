package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑秒杀配置信息
 *
 * @author Trick
 * @date 2021/5/6 16:51
 */
@ApiModel(description = "添加/编辑秒杀配置信息")
public class AddSecConfigVo extends MainVo {
    @ApiModelProperty(name = "is_open", value = "是否开启秒杀 1是 0否")
    private Integer isOpen;
    @ApiModelProperty(name = "buy_num", value = "限购数量")
    private Integer buyNum;
    @ApiModelProperty(name = "remind", value = "秒杀提醒")
    private Integer remind;
    @ApiModelProperty(name = "rule", value = "秒杀规则")
    private String rule;

    @ApiModelProperty(name = "isHerald", value = "是否开启预告")
    private Integer isHerald = 0;
    @ApiModelProperty(name = "heraldTime", value = "预告时间 单位 小时")
    private BigDecimal heraldTime;

    @ApiModelProperty(name = "isFreeShipping", value = "是否开启包邮设置")
    private Integer isFreeShipping;
    @ApiModelProperty(name = "goodsNum", value = "满足包邮条件的商品数量")
    private Integer goodsNum;

    @ApiModelProperty(name = "autoReceivingGoodsDay", value = "自动收货时间 (天)")
    private BigDecimal autoReceivingGoodsDay;
    @ApiModelProperty(name = "orderInvalidTime", value = "订单失效时间 (小时)")
    private BigDecimal orderInvalidTime;
    @ApiModelProperty(name = "returnDay", value = "订单售后时间 (天)")
    private BigDecimal returnDay;
    @ApiModelProperty(name = "deliverRemind", value = "提醒限制 (天)")
    private BigDecimal deliverRemind;
    @ApiModelProperty(name = "autoCommentDay", value = "自动好评时间 (天)")
    private BigDecimal autoCommentDay;


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

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public Integer getRemind() {
        return remind;
    }

    public void setRemind(Integer remind) {
        this.remind = remind;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getIsHerald() {
        return isHerald;
    }

    public void setIsHerald(Integer isHerald) {
        this.isHerald = isHerald;
    }

    public BigDecimal getHeraldTime() {
        return heraldTime;
    }

    public void setHeraldTime(BigDecimal heraldTime) {
        this.heraldTime = heraldTime;
    }
}
