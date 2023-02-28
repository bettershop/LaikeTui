package com.laiketui.domain.vo.coupon;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取优惠卷会员列表参数
 *
 * @author Trick
 * @date 2021/1/25 10:58
 */
@ApiModel(description = "获取优惠卷会员列表参数")
public class CouponUserVo extends MainVo {
    @ApiModelProperty(value = "活动Id", name = "hid")
    private Integer hid;
    @ApiModelProperty(value = "会员等级", name = "grade")
    private Integer grade;
    @ApiModelProperty(value = "会员名称/手机号/userid", name = "name")
    private String name;

    public Integer getHid() {
        return hid;
    }

    public void setHid(Integer hid) {
        this.hid = hid;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
