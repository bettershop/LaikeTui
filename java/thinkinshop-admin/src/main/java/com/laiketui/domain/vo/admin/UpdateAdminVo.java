package com.laiketui.domain.vo.admin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改当前管理员基本信息
 *
 * @author Trick
 * @date 2021/9/6 11:30
 */
@ApiModel(description = "修改后台管理员基本信息")
public class UpdateAdminVo extends MainVo {

    @ApiModelProperty(value = "头像", name = "portrait")
    private String portrait;
    @ApiModelProperty(value = "昵称", name = "nickname")
    private String nickname;
    @ApiModelProperty(value = "生日", name = "birthday")
    private String birthday;
    @ApiModelProperty(value = "分类id", name = "cid")
    private String cid;
    @ApiModelProperty(value = "性别 1=男2=女", name = "sex")
    private Integer sex;
    @ApiModelProperty(value = "手机号", name = "phone")
    private String phone;

    @ApiModelProperty(value = "旧密码", name = "passwordOld")
    private String passwordOld;
    @ApiModelProperty(value = "新密码", name = "password")
    private String password;


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
