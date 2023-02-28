package com.laiketui.domain.vo.user;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户注册
 *
 * @author Trick
 * @date 2021/2/25 13:47
 */
@ApiModel(description = "用户注册参数")
public class UserRegisterVo extends MainVo {

    @ApiModelProperty(value = "账号", name = "zhanghao")
    @ParamsMapping("userId")
    private String zhanghao;
    @ApiModelProperty(value = "手机号", name = "phone")
    private String phone;
    @ApiModelProperty(value = "密码", name = "mima")
    @ParamsMapping("password")
    private String mima;
    @ApiModelProperty(value = "验证码", name = "pcode")
    @ParamsMapping("keyCode")
    private String pcode;

    @ApiModelProperty(value = "推荐人", name = "pid")
    private String pid;

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public String getPcode() {
        return pcode;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }
}
