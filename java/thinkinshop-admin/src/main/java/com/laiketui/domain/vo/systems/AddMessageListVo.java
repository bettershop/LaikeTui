package com.laiketui.domain.vo.systems;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改短信列表
 *
 * @author Trick
 * @date 2021/1/18 11:02
 */
@ApiModel(description = "添加/修改短信列表")
public class AddMessageListVo extends MainVo {
    @ApiModelProperty(value = "短信列表id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "短信类型id", name = "type")
    private Integer type;
    @ApiModelProperty(value = "短信类别id", name = "category")
    private Integer category;
    @ApiModelProperty(value = "短信模板id", name = "smsTemplate")
    private Integer smsTemplateId;
    //尊敬的用户，店铺${store}处理退款订单${orderno}操作成功，退款金额：${amount} 传 store、orderno、amount
    @ApiModelProperty(value = "模板参数 多个用,隔开", name = "templateStr")
    private String templateStr;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getSmsTemplateId() {
        return smsTemplateId;
    }

    public void setSmsTemplateId(Integer smsTemplateId) {
        this.smsTemplateId = smsTemplateId;
    }

    public String getTemplateStr() {
        return templateStr;
    }

    public void setTemplateStr(String templateStr) {
        this.templateStr = templateStr;
    }
}
