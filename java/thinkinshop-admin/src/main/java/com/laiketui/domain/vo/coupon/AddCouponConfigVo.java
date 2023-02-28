package com.laiketui.domain.vo.coupon;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑商城优惠卷配置
 *
 * @author Trick
 * @date 2021/1/22 15:49
 */
@ApiModel(description = "添加/编辑商城优惠卷配置参数")
public class AddCouponConfigVo extends MainVo {

    @ApiModelProperty(value = "是否开启优惠卷", name = "isOpen")
    private Integer isOpen;
    @ApiModelProperty(value = "是否自动清除过期优惠券", name = "isAutoClearCoupon")
    private Integer isAutoClearCoupon;
    @ApiModelProperty(value = "自动清除过期优惠券天数", name = "autoClearCouponDay")
    private Integer autoClearCouponDay;
    @ApiModelProperty(value = "是否自动清除过期活动", name = "isAutoClearaAtivity")
    private Integer isAutoClearaAtivity;
    @ApiModelProperty(value = "自动清除过期活动天数", name = "autoClearaAtivityDay")
    private Integer autoClearaAtivityDay;
    @ApiModelProperty(value = "限领设置 0：单张 1：多张", name = "limitType")
    private Integer limitType;
    @ApiModelProperty(value = "优惠卷类型 1=免邮券，2=满减券，3=折扣券，4=会员赠券", name = "limitType")
    private String couponType;

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getIsAutoClearCoupon() {
        return isAutoClearCoupon;
    }

    public void setIsAutoClearCoupon(Integer isAutoClearCoupon) {
        this.isAutoClearCoupon = isAutoClearCoupon;
    }

    public Integer getAutoClearCouponDay() {
        return autoClearCouponDay;
    }

    public void setAutoClearCouponDay(Integer autoClearCouponDay) {
        this.autoClearCouponDay = autoClearCouponDay;
    }

    public Integer getIsAutoClearaAtivity() {
        return isAutoClearaAtivity;
    }

    public void setIsAutoClearaAtivity(Integer isAutoClearaAtivity) {
        this.isAutoClearaAtivity = isAutoClearaAtivity;
    }

    public Integer getAutoClearaAtivityDay() {
        return autoClearaAtivityDay;
    }

    public void setAutoClearaAtivityDay(Integer autoClearaAtivityDay) {
        this.autoClearaAtivityDay = autoClearaAtivityDay;
    }

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
}
