package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取用户资金列表参数
 *
 * @author Trick
 * @date 2021/1/11 15:51
 */
@ApiModel(description = "获取用户资金列表参数")
public class UserMoneyVo extends MainVo {

    @ApiModelProperty(value = "用户id", name = "id")
    private Integer id;

    @ApiModelProperty(value="会员名称",name="userName")
    private String userName;
    @ApiModelProperty(value="用户来源",name="source")
    private int source;
    @ApiModelProperty(value="开始时间",name="startDate")
    private String startDate;
    @ApiModelProperty(value="结束时间",name="endDate")
    private String endDate;

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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
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
