package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取用户积分列表参数
 *
 * @author Trick
 * @date 2021/1/11 16:55
 */
@ApiModel(description="获取用户积分列表参数")
public class UserJiFenVo extends MainVo {

    @ApiModelProperty(value = "用户id", name = "userId")
    private Integer userId;

    @ApiModelProperty(value="会员名称",name="userName")
    private String userName;
    @ApiModelProperty(value="联系电话",name="phone")
    private String phone;
    @ApiModelProperty(value="开始时间",name="startDate")
    private String startDate;
    @ApiModelProperty(value="结束时间",name="endDate")
    private String endDate;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
