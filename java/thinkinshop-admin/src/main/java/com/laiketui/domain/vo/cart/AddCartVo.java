package com.laiketui.domain.vo.cart;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加购物车参数
 *
 * @author Trick
 * @date 2021/2/25 17:52
 */
@ApiModel(description = "添加购物车参数")
public class AddCartVo extends MainVo {
    @ApiModelProperty(value = "商品id", name = "pro_id")
    @ParamsMapping("pro_id")
    private Integer goodsId;
    @ApiModelProperty(value = "数量", name = "num")
    private Integer num;
    @ApiModelProperty(value = "属性id", name = "attribute_id")
    @ParamsMapping("attribute_id")
    private Integer attributeId;
    @ApiModelProperty(value = "收藏类型(addcart,immediately)", name = "type")
    private String type;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAttributeId() {
        return attributeId;
    }

    public void setAttributeId(Integer attributeId) {
        this.attributeId = attributeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
