package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 编辑个人资料
 *
 * @author Trick
 * @date 2021/6/24 10:25
 */
@ApiModel(description = "编辑个人资料")
public class EditResourcesVo extends MainVo {

    @ApiModelProperty(value = "头像url", name = "headimgurl")
    private String headimgurl;
    @ApiModelProperty(value = "用户昵称", name = "userName")
    private String userName;
    @ApiModelProperty(value = "性别  0:未知 1:男 2:女", name = "sex")
    private Integer sex;
    @ApiModelProperty(value = "出生日期", name = "birthday")
    private String birthday;

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}
