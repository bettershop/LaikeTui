package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/修改会员等级参数
 *
 * @author Trick
 * @date 2021/1/8 14:19
 */
@ApiModel(description = "添加/修改会员等级参数")
public class AddUserGradeVo extends MainVo {
    @ApiModelProperty(value = "等级id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "会员等级名称", name = "name")
    private String name;
    @ApiModelProperty(value = "会员背景图", name = "backImage")
    private String backImage;
    @ApiModelProperty(value = "小图标", name = "miniImage")
    private String miniImage;
    @ApiModelProperty(value = "字体颜色", name = "fontColor")
    private String fontColor;
    @ApiModelProperty(value = "日期时间", name = "dateColor")
    private String dateColor;
    @ApiModelProperty(value = "专属折扣", name = "discountRate")
    private BigDecimal discountRate;
    @ApiModelProperty(value = "包月金额", name = "money")
    private BigDecimal money;
    @ApiModelProperty(value = "包季金额", name = "moneyJ")
    private BigDecimal moneyJ;
    @ApiModelProperty(value = "包年金额", name = "moneyN")
    private BigDecimal moneyN;
    @ApiModelProperty(value = "赠送商品Id", name = "goodsId")
    private Integer goodsId;
    @ApiModelProperty(value = "备注", name = "remarks")
    private String remarks;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBackImage() {
        return backImage;
    }

    public void setBackImage(String backImage) {
        this.backImage = backImage;
    }

    public String getMiniImage() {
        return miniImage;
    }

    public void setMiniImage(String miniImage) {
        this.miniImage = miniImage;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public String getDateColor() {
        return dateColor;
    }

    public void setDateColor(String dateColor) {
        this.dateColor = dateColor;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoneyJ() {
        return moneyJ;
    }

    public void setMoneyJ(BigDecimal moneyJ) {
        this.moneyJ = moneyJ;
    }

    public BigDecimal getMoneyN() {
        return moneyN;
    }

    public void setMoneyN(BigDecimal moneyN) {
        this.moneyN = moneyN;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
