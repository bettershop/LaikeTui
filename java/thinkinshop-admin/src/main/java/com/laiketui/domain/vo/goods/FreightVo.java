package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 运费参数
 *
 * @author Trick
 * @date 2020/12/31 15:15
 */

@ApiModel(description = "运费参数列表")
public class FreightVo extends MainVo {

    @ApiModelProperty(value = "运费id", name = "freightId")
    private Integer freightId;
    @ApiModelProperty(value = "运费模板名称", name = "freightName")
    private String freightName;
    @ApiModelProperty(value = "是否默认(0=非默认,1=默认)", name = "isDefault")
    private Integer isDefault;
    @ApiModelProperty(value = "状态(0=未使用,1=已使用)", name = "status")
    private Integer status;
    @ApiModelProperty(value = "规则", name = "freight")
    private String freight;

    public Integer getFreightId() {
        return freightId;
    }

    public void setFreightId(Integer freightId) {
        this.freightId = freightId;
    }

    public String getFreightName() {
        return freightName;
    }

    public void setFreightName(String freightName) {
        this.freightName = freightName;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFreight() {
        return freight;
    }

    public void setFreight(String freight) {
        this.freight = freight;
    }
}
