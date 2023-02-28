package com.laiketui.domain.vo.admin.mall;


import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 保存App配置
 *
 * @author Trick
 * @date 2021/7/23 18:15
 */
@ApiModel(description = "保存App配置")
public class SaveTerminalAppVo extends MainVo {
    @ApiModelProperty(value = "主键id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "app名称", name = "appName")
    private String appName;
    @ApiModelProperty(value = "ios路径", name = "iosUrl")
    private String iosUrl;
    @ApiModelProperty(value = "android路径", name = "androidUrl")
    private String androidUrl;
    @ApiModelProperty(value = "是否自动更新 0:是 1:不是", name = "type")
    private Integer type;
    @ApiModelProperty(value = "content", name = "content")
    private String content;
    @ApiModelProperty(value = "app域名", name = "appDomainName")
    private String appDomainName;
    @ApiModelProperty(value = "版本", name = "edition")
    private String edition;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getIosUrl() {
        return iosUrl;
    }

    public void setIosUrl(String iosUrl) {
        this.iosUrl = iosUrl;
    }

    public String getAndroidUrl() {
        return androidUrl;
    }

    public void setAndroidUrl(String androidUrl) {
        this.androidUrl = androidUrl;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAppDomainName() {
        return appDomainName;
    }

    public void setAppDomainName(String appDomainName) {
        this.appDomainName = appDomainName;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}
