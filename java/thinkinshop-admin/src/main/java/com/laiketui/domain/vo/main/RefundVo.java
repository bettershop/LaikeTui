package com.laiketui.domain.vo.main;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 售后参数
 *
 * @author Trick
 * @date 2020/12/2 11:46
 */
@ApiModel(description = "售后参数")
public class RefundVo extends MainVo {

    @ApiModelProperty(value = "用户名称", hidden = true)
    private String adminName;
    @ApiModelProperty(value = "售后id", name = "id")
    private int id;
    @ApiModelProperty(value = "售后类型  1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束", name = "type")
    @ParamsMapping("r_type")
    private Integer type;
    @ApiModelProperty(value = "拒绝理由", name = "text")
    private String text;
    @ApiModelProperty(value = "退款金额", name = "price")
    private BigDecimal price = new BigDecimal("0");
    @ApiModelProperty(value = "店铺id", name = "mchId")
    @ParamsMapping("shop_id")
    private int mchId;


    @ApiModelProperty(value = "快递公司编号(商品寄回流程)", name = "expressId")
    @ParamsMapping("express_id")
    private Integer expressId;
    @ApiModelProperty(value = "快递单号(商品寄回流程)", name = "courierNum")
    @ParamsMapping("courier_num")
    private String courierNum;


    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getMchId() {
        return mchId;
    }

    public void setMchId(int mchId) {
        this.mchId = mchId;
    }

    public String getCourierNum() {
        return courierNum;
    }

    public void setCourierNum(String courierNum) {
        this.courierNum = courierNum;
    }

    public Integer getExpressId() {
        return expressId;
    }

    public void setExpressId(Integer expressId) {
        this.expressId = expressId;
    }
}
