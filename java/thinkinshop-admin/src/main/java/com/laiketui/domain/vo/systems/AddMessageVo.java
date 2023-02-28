package com.laiketui.domain.vo.systems;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改短信模板
 *
 * @author Trick
 * @date 2021/1/18 15:04
 */
@ApiModel(description = "添加/修改短信列表")
public class AddMessageVo extends MainVo {
    @ApiModelProperty(value = "id", name = "短信模板id")
    private Integer id;


    @ApiModelProperty(value = "signName", name = "签名")
    private String signName;
    @ApiModelProperty(value = "name", name = "短信模板名称")
    private String name;
    @ApiModelProperty(value = "code", name = "短信模板代码")
    private String code;
    @ApiModelProperty(value = "content", name = "模板内容")
    private String content;
    @ApiModelProperty(value = "type", name = "短信类型id")
    private Integer type;
    @ApiModelProperty(value = "category", name = "短信类别id")
    private Integer category;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }
}
