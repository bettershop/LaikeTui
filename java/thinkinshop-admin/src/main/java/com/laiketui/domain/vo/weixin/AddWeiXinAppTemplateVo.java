package com.laiketui.domain.vo.weixin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑微信小程序模板
 *
 * @author Trick
 * @date 2021/1/20 11:33
 */
@ApiModel(description = "添加/编辑微信小程序模板参数")
public class AddWeiXinAppTemplateVo extends MainVo {
    @ApiModelProperty(value = "列表id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "模板名称", name = "templateName")
    private String templateName;
    @ApiModelProperty(value = "行业类型", name = "industry")
    private Integer industry;
    @ApiModelProperty(value = "模板展示图", name = "templateImageUrl")
    private String templateImageUrl;
    @ApiModelProperty(value = "小程序模板id", name = "templateId")
    private String templateId;
    @ApiModelProperty(value = "模板简介", name = "text")
    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public Integer getIndustry() {
        return industry;
    }

    public void setIndustry(Integer industry) {
        this.industry = industry;
    }

    public String getTemplateImageUrl() {
        return templateImageUrl;
    }

    public void setTemplateImageUrl(String templateImageUrl) {
        this.templateImageUrl = templateImageUrl;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
