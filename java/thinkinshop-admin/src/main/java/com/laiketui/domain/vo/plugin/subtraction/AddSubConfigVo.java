package com.laiketui.domain.vo.plugin.subtraction;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑满减设置信息
 *
 * @author Trick
 * @date 2021/5/13 14:52
 */
@ApiModel(description = "添加/编辑满减设置信息")
public class AddSubConfigVo extends MainVo {
    @ApiModelProperty(name = "is_subtraction", value = "是否开启满减")
    @ParamsMapping("is_subtraction")
    private Integer isSubtraction;
    @ApiModelProperty(name = "rangeZfc", value = "满减应用范围")
    @ParamsMapping("range_zfc")
    private Integer rangeZfc;
    @ApiModelProperty(name = "proId", value = "满赠商品")
    @ParamsMapping("pro_id")
    private String proId;

    public Integer getIsSubtraction() {
        return isSubtraction;
    }

    public void setIsSubtraction(Integer isSubtraction) {
        this.isSubtraction = isSubtraction;
    }

    public Integer getRangeZfc() {
        return rangeZfc;
    }

    public void setRangeZfc(Integer rangeZfc) {
        this.rangeZfc = rangeZfc;
    }

    public String getProId() {
        return proId;
    }

    public void setProId(String proId) {
        this.proId = proId;
    }
}
