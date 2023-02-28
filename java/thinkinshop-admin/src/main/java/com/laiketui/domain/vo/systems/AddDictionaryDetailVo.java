package com.laiketui.domain.vo.systems;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改字典表明细
 *
 * @author Trick
 * @date 2021/2/1 14:38
 */
@ApiModel(description = "添加/修改字典表明细")
public class AddDictionaryDetailVo extends MainVo {
    @ApiModelProperty(name = "id", value = "字典值id")
    private Integer id;

    @ApiModelProperty(name = "sid", value = "字典目录id")
    private Integer sid;
    @ApiModelProperty(name = "dataCode", value = "数据编码")
    private String dataCode;
    @ApiModelProperty(name = "valueCode", value = "值代码")
    private String valueCode;
    @ApiModelProperty(name = "valueName", value = "值")
    private String valueName;
    @ApiModelProperty(name = "isOpen", value = "是否生效")
    private Integer isOpen = 1;

    @ApiModelProperty(name = "attrId", value = "所属属性id")
    private String attrId;

    public String getAttrId() {
        return attrId;
    }

    public void setAttrId(String attrId) {
        this.attrId = attrId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getValueCode() {
        return valueCode;
    }

    public void setValueCode(String valueCode) {
        this.valueCode = valueCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
