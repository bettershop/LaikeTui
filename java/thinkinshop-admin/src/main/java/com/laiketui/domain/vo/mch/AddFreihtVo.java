package com.laiketui.domain.vo.mch;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加运费
 *
 * @author Trick
 * @date 2020/12/1 16:10
 */
@ApiModel(description = "添加运费")
public class AddFreihtVo extends MainVo {

    @ApiModelProperty(value = "运费id", name = "id")
    @ParamsMapping("id")
    private Integer fid;

    @ApiModelProperty(value = "店铺id", name = "shop_id")
    @ParamsMapping("shop_id")
    private Integer shopId;
    @ApiModelProperty(value = "规则名称", name = "name")
    private String name;
    @ApiModelProperty(value = "类型", name = "type")
    private Integer type;
    @ApiModelProperty(value = "是否默认", name = "is_default")
    @ParamsMapping("is_default")
    private Integer isDefault;
    @ApiModelProperty(value = "运费规则", name = "hidden_freight")
    @ParamsMapping("hidden_freight")
    private String hiddenFreight;

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public String getHiddenFreight() {
        return hiddenFreight;
    }

    public void setHiddenFreight(String hiddenFreight) {
        this.hiddenFreight = hiddenFreight;
    }
}
