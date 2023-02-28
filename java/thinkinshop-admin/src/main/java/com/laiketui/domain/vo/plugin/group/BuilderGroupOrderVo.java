package com.laiketui.domain.vo.plugin.group;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 创建拼团订单参数
 *
 * @author Trick
 * @date 2021/3/29 14:19
 */
@ApiModel(description = "创建拼团订单参数")
public class BuilderGroupOrderVo extends MainVo {

    @ApiModelProperty(name = "ptcode", value = "拼团编号")
    private String ptcode;
    @ApiModelProperty(name = "activityNo", value = "活动编号")
    @ParamsMapping("activity_no")
    private Integer activityNo;
    @ApiModelProperty(name = "goodsId", value = "商品id")
    @ParamsMapping("pro_id")
    private Integer goodsId;
    @ApiModelProperty(name = "attrId", value = "规格id")
    @ParamsMapping("sizeid")
    private Integer attrId;
    @ApiModelProperty(name = "num", value = "数量")
    private Integer num;
    @ApiModelProperty(name = "addressId", value = "地址id")
    @ParamsMapping("address_id")
    private Integer addressId;
    @ApiModelProperty(name = "frompage", value = "参团订单-cantuan/开团订单-kaituan")
    private String frompage;
    @ApiModelProperty(name = "payType", value = "支付方式")
    @ParamsMapping("pay_type")
    private String payType;
    @ApiModelProperty(name = "groupNum", value = "拼团人数")
    @ParamsMapping("man_num")
    private Integer groupNum;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPtcode() {
        return ptcode;
    }

    public void setPtcode(String ptcode) {
        this.ptcode = ptcode;
    }

    public Integer getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(Integer activityNo) {
        this.activityNo = activityNo;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public String getFrompage() {
        return frompage;
    }

    public void setFrompage(String frompage) {
        this.frompage = frompage;
    }
}
