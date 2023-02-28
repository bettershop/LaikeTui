package com.laiketui.domain.vo.plugin.integral;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑积分商品
 *
 * @author Trick
 * @date 2021/5/12 11:38
 */
@ApiModel(description = "添加/编辑积分商品")
public class AddIntegralVo extends MainVo {
    @ApiModelProperty(name = "id", value = "积分商品id")
    private Integer id;
    @ApiModelProperty(name = "goodsid", value = "商品id")
    private Integer goodsid;
    @ApiModelProperty(name = "stockNum", value = "库存数量")
    private Integer stockNum;
    @ApiModelProperty(name = "maxStockNum", value = "最大库存数量")
    private Integer maxStockNum;

    @ApiModelProperty(name = "integral", value = "积分")
    private BigDecimal integral;
    @ApiModelProperty(name = "price", value = "现金")
    private BigDecimal money;

    public Integer getId() {
        return id;
    }

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getMaxStockNum() {
        return maxStockNum;
    }

    public void setMaxStockNum(Integer maxStockNum) {
        this.maxStockNum = maxStockNum;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsid() {
        return goodsid;
    }

    public void setGoodsid(Integer goodsid) {
        this.goodsid = goodsid;
    }

    public BigDecimal getIntegral() {
        return integral;
    }

    public void setIntegral(BigDecimal integral) {
        this.integral = integral;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
