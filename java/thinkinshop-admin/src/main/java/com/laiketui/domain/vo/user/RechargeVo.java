package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取充值列表参数
 *
 * @author Trick
 * @date 2021/1/11 15:51
 */
@ApiModel(description = "获取充值列表参数")
public class RechargeVo extends MainVo {

    @ApiModelProperty(value = "记录id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "userid", name = "userid")
    private String userid;

    @ApiModelProperty(value="会员名称",name="userName")
    private String userName;
    @ApiModelProperty(value="联系电话",name="phone")
    private String phone;
    @ApiModelProperty(value="开始时间",name="startDate")
    private String startDate;
    @ApiModelProperty(value="结束时间",name="endDate")
    private String endDate;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
