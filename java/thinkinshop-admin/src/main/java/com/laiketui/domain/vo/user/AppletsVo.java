package com.laiketui.domain.vo.user;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 小程序登录授权参数
 * @author sunH_
 */
@ApiModel(description="小程序登录授权参数")
public class AppletsVo extends MainVo {
    @ApiModelProperty(value="token", name="accessId")
    private String accessId;

    @ApiModelProperty(value="用户名称", name="nickName")
    private String nickName;

    @ApiModelProperty(value="头像", name="headImgUrl")
    @ParamsMapping({"headimgurl"})
    private String headImgUrl;

    @ApiModelProperty(value="性别(0.未知  1.男  2.女)", name="sex")
    private Integer sex;

    @ApiModelProperty(value="微信code", name="code")
    private String code;

    @ApiModelProperty(value="上级id", name="pid")
    private String pid;

    public AppletsVo(String accessId, String nickName, String headImgUrl, Integer sex, String code, String pid) {
        this.accessId = accessId;
        this.nickName = nickName;
        this.headImgUrl = headImgUrl;
        this.sex = sex;
        this.code = code;
        this.pid = pid;
    }

    @Override
    public String getAccessId() {
        return accessId;
    }

    @Override
    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
