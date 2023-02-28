package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 品牌
 *
 * @author Trick
 * @date 2020/12/30 17:39
 */
@ApiModel(description = "品牌")
public class BrandClassVo extends MainVo {

    @ApiModelProperty(value = "品牌id", name = "brandId")
    private Integer brandId;
    @ApiModelProperty(value = "品牌名称", name = "brandName")
    private String brandName;
    @ApiModelProperty(value = "品牌logo", name = "brandLogo")
    private String brandLogo;
    @ApiModelProperty(value = "品牌分类", name = "brandClass")
    private String brandClass;
    @ApiModelProperty(value = "产地", name = "producer")
    private Integer producer;
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getBrandClass() {
        return brandClass;
    }

    public void setBrandClass(String brandClass) {
        this.brandClass = brandClass;
    }

    public Integer getProducer() {
        return producer;
    }

    public void setProducer(Integer producer) {
        this.producer = producer;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
