package com.laiketui.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户登陆参数
 *
 * @author Trick
 * @date 2020/11/6 15:42
 */
@ApiModel(description="用户登陆参数")
public class UserLoginVo extends MainVo{

    @ApiModelProperty(value="推送客户端ID",name="clientid")
    private String clientid;
    @ApiModelProperty(value="电话号码",name="phone")
    private String phone;
    @ApiModelProperty(value="密码",name="password")
    private String password;
    @ApiModelProperty(value="推荐人userid",name="pid")
    private String pid;

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
