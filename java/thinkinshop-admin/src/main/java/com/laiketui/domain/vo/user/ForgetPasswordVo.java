package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 找回密码参数
 *
 * @author Trick
 * @date 2021/6/17 9:43
 */
@ApiModel(description = "后台登录")
public class ForgetPasswordVo extends MainVo {

    @ApiModelProperty(value = "手机号", name = "phone")
    private String phone;
    @ApiModelProperty(value = "手机验证码", name = "pcode")
    private String pcode;
    @ApiModelProperty(value = "图形验证码", name = "imgCode")
    private String imgCode;
    @ApiModelProperty(value = "密码", name = "pwd")
    private String pwd;
    @ApiModelProperty(value = "重复密码", name = "rpwd")
    private String rpwd;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRpwd() {
        return rpwd;
    }

    public void setRpwd(String rpwd) {
        this.rpwd = rpwd;
    }
}
