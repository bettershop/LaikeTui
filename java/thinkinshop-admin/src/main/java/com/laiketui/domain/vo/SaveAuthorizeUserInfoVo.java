package com.laiketui.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户授权时,保存用户信息 参数
 *
 * @author Trick
 * @date 2020/11/5 15:29
 */
@ApiModel(description="用户授权时,保存用户信息 参数")
public class SaveAuthorizeUserInfoVo extends MainVo {

    @ApiModelProperty(value="code",name="code")
    private String code;
    @ApiModelProperty(value="用户昵称",name="nickName")
    private String nickName;
    @ApiModelProperty(value="用户头像",name="headimgurl")
    private String headimgurl;
    @ApiModelProperty(value="用户性别",name="sex")
    private Integer sex;
    @ApiModelProperty(value="推荐人userid",name="pid")
    private String pid;
    @ApiModelProperty(value="推荐人授权id",name="access_id")
    private String accessId;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
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
}
