package com.laiketui.domain.vo.systems;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改 系统配置
 *
 * @author Trick
 * @date 2021/1/19 9:16
 */
@ApiModel(description = "添加/修改 系统配置参数")
public class SetSystemVo extends MainVo {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "公司logo", name = "logoUrl")
    private String logoUrl;
    @ApiModelProperty(value = "版权信息", name = "copyrightInformation")
    private String copyrightInformation;
    @ApiModelProperty(value = "recordInformation", name = "备案信息")
    private String recordInformation;
    @ApiModelProperty(value = "linkPageList", name = "登录页链接 json数组")
    private String linkPageJson;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCopyrightInformation() {
        return copyrightInformation;
    }

    public void setCopyrightInformation(String copyrightInformation) {
        this.copyrightInformation = copyrightInformation;
    }

    public String getRecordInformation() {
        return recordInformation;
    }

    public void setRecordInformation(String recordInformation) {
        this.recordInformation = recordInformation;
    }

    public String getLinkPageJson() {
        return linkPageJson;
    }

    public void setLinkPageJson(String linkPageJson) {
        this.linkPageJson = linkPageJson;
    }
}
