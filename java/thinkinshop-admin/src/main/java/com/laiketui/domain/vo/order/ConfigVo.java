package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 后台订单设置
 *
 * @author wangxain
 */
@ApiModel(description = "订单设置")
public class ConfigVo extends MainVo {
    /**
     * 多件包邮设置
     */
    @ApiModelProperty(name = "packageSettings", value = "是否开启包邮规则 1=是 0=否")
    private Integer packageSettings;
    @ApiModelProperty(name = "samePiece", value = "同件")
    private Integer samePiece;
    @ApiModelProperty(name = "sameOrder", value = "同单")
    private Integer sameOrder;

    @ApiModelProperty(name = "autoTheGoods", value = "自动收货时间 /天")
    private Integer autoTheGoods;
    @ApiModelProperty(name = "orderFailure", value = "订单失效时间 /小时")
    private Integer orderFailure;
    @ApiModelProperty(name = "orderAfter", value = "订单售后时间 /天")
    private Integer orderAfter;
    @ApiModelProperty(name = "remindDay", value = "提醒限制 /天")
    private Integer remindDay;
    @ApiModelProperty(name = "remindHour", value = "提醒限制 /小时")
    private Integer remindHour;
    @ApiModelProperty(name = "autoGoodCommentDay", value = "自动评价设置 /天")
    private Integer autoGoodCommentDay;

    public Integer getPackageSettings() {
        return packageSettings;
    }

    public void setPackageSettings(Integer packageSettings) {
        this.packageSettings = packageSettings;
    }

    public Integer getSamePiece() {
        return samePiece;
    }

    public void setSamePiece(Integer samePiece) {
        this.samePiece = samePiece;
    }

    public Integer getSameOrder() {
        return sameOrder;
    }

    public void setSameOrder(Integer sameOrder) {
        this.sameOrder = sameOrder;
    }

    public Integer getAutoTheGoods() {
        return autoTheGoods;
    }

    public void setAutoTheGoods(Integer autoTheGoods) {
        this.autoTheGoods = autoTheGoods;
    }

    public Integer getOrderFailure() {
        return orderFailure;
    }

    public void setOrderFailure(Integer orderFailure) {
        this.orderFailure = orderFailure;
    }

    public Integer getOrderAfter() {
        return orderAfter;
    }

    public void setOrderAfter(Integer orderAfter) {
        this.orderAfter = orderAfter;
    }

    public Integer getRemindDay() {
        return remindDay;
    }

    public void setRemindDay(Integer remindDay) {
        this.remindDay = remindDay;
    }

    public Integer getRemindHour() {
        return remindHour;
    }

    public void setRemindHour(Integer remindHour) {
        this.remindHour = remindHour;
    }

    public Integer getAutoGoodCommentDay() {
        return autoGoodCommentDay;
    }

    public void setAutoGoodCommentDay(Integer autoGoodCommentDay) {
        this.autoGoodCommentDay = autoGoodCommentDay;
    }
}
