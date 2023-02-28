package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录参数
 *
 * @author Trick
 * @date 2021/5/26 9:42
 */
@ApiModel(description = "登录参数")
public class LoginVo extends MainVo {
    @ApiModelProperty(value = "账号/手机号", name = "login")
    private String login;
    @ApiModelProperty(value = "密码", name = "pwd")
    private String pwd;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
