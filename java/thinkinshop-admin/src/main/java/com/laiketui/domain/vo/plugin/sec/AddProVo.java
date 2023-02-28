package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑秒杀商品
 * 新秒杀 2021-10-15 14:01:39
 * @author Trick
 * @date 2021/5/7 10:14
 */
@ApiModel(description = "添加/编辑秒杀商品")
public class AddProVo extends MainVo {
    @ApiModelProperty(name = "id", value = "标签id")
    private String id;
    @ApiModelProperty(name = "secGoodsId", value = "秒杀商品id(修改)")
    private Integer secGoodsId;
    @ApiModelProperty(name = "goodsJson", value = "商品json集  [{goodsId:1,attr:[{attrId:1,num:10},...]},...] ")
    private String goodsJson;
    @ApiModelProperty(name = "price", value = "秒杀价格")
    private BigDecimal price;
    @ApiModelProperty(name = "priceType", value = "价格单位 0=百分比 1=固定值")
    private Integer priceType;
    @ApiModelProperty(name = "startDate", value = "开始时间")
    private String startDate;
    @ApiModelProperty(name = "endDate", value = "结束时间")
    private String endDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSecGoodsId() {
        return secGoodsId;
    }

    public void setSecGoodsId(Integer secGoodsId) {
        this.secGoodsId = secGoodsId;
    }

    public String getGoodsJson() {
        return goodsJson;
    }

    public void setGoodsJson(String goodsJson) {
        this.goodsJson = goodsJson;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPriceType() {
        return priceType;
    }

    public void setPriceType(Integer priceType) {
        this.priceType = priceType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
