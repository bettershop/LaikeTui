package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加用户参数
 *
 * @author Trick
 * @date 2021/1/8 10:54
 */
@ApiModel(description = "添加用户参数")
public class AddUserVo extends MainVo {

    @ApiModelProperty(value = "头像", name = "headerUrl")
    private String headerUrl;
    @ApiModelProperty(value = "会员名称", name = "userName")
    private String userName;
    @ApiModelProperty(value = "会员等级", name = "grade")
    private Integer grade;
    @ApiModelProperty(value = "会员账号", name = "zhanghao")
    private String zhanghao;
    @ApiModelProperty(value = "会员密码", name = "mima")
    private String mima;
    @ApiModelProperty(value = "手机号", name = "phone")
    private String phone;
    @ApiModelProperty(value = "来源", name = "source")
    private Integer source;

    public String getHeaderUrl() {
        return headerUrl;
    }

    public void setHeaderUrl(String headerUrl) {
        this.headerUrl = headerUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
