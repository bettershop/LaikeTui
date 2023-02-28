package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户查询参数
 *
 * @author Trick
 * @date 2021/1/7 11:01
 */
@ApiModel(description="用户查询参数")
public class UserVo extends MainVo {
    @ApiModelProperty(value="用户id",name="uid")
    private String uid;
    @ApiModelProperty(value="会员名称",name="uname")
    private String uname;
    @ApiModelProperty(value="会员等级",name="grade")
    private Integer grade;
    @ApiModelProperty(value="来源",name="source")
    private Integer source;
    @ApiModelProperty(value="是否过期",name="isOverdue")
    private Integer isOverdue;
    @ApiModelProperty(value="联系电话",name="tel")
    private String tel;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getIsOverdue() {
        return isOverdue;
    }

    public void setIsOverdue(Integer isOverdue) {
        this.isOverdue = isOverdue;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
