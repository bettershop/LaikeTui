package com.laiketui.domain.vo.app;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑app配置信息
 *
 * @author Trick
 * @date 2021/1/22 9:43
 */
@ApiModel(description = "添加/编辑app配置信息参数")
public class AppConfigInfoVo extends MainVo {

    @ApiModelProperty(value = "app名称", name = "appName")
    private String appName;
    @ApiModelProperty(value = "版本号", name = "appVersion")
    private String appVersion;
    @ApiModelProperty(value = "安卓下载地址", name = "androidDownloadUrl")
    private String androidDownloadUrl;
    @ApiModelProperty(value = "ios下载地址", name = "iosDownloadUrl")
    private String iosDownloadUrl;
    @ApiModelProperty(value = "是否更新", name = "isAutoUpdate")
    private Integer isAutoUpdate;
    @ApiModelProperty(value = "更新内容", name = "updateLogger")
    private String updateLogger;


    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAndroidDownloadUrl() {
        return androidDownloadUrl;
    }

    public void setAndroidDownloadUrl(String androidDownloadUrl) {
        this.androidDownloadUrl = androidDownloadUrl;
    }

    public String getIosDownloadUrl() {
        return iosDownloadUrl;
    }

    public void setIosDownloadUrl(String iosDownloadUrl) {
        this.iosDownloadUrl = iosDownloadUrl;
    }

    public Integer getIsAutoUpdate() {
        return isAutoUpdate;
    }

    public void setIsAutoUpdate(Integer isAutoUpdate) {
        this.isAutoUpdate = isAutoUpdate;
    }

    public String getUpdateLogger() {
        return updateLogger;
    }

    public void setUpdateLogger(String updateLogger) {
        this.updateLogger = updateLogger;
    }
}
