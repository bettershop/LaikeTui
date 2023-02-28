package com.laiketui.domain.vo.pc;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单列表参数
 * 【php DeliveryHelper.b_mch_order_index】
 *
 * @author Trick
 * @date 2020/11/24 17:07
 */
@ApiModel("订单列表参数")
public class MchPcOrderIndexVo extends MainVo {

    @ApiModelProperty(value = "店铺id", name = "shopId")
    @ParamsMapping("shop_id")
    private int shopId;
    @ApiModelProperty(value = "订单类型", name = "orderType")
    @ParamsMapping("order_type")
    private String orderType;
    @ApiModelProperty(value = "订单状态 默认全部 -1=退款,0=未付款,1=待发货", name = "orderStauts")
    private Integer orderStauts;

    @ApiModelProperty(value = "开始时间", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private String endDate;
    @ApiModelProperty(value = "订单号/姓名/会员/电话号", name = "orderno")
    private String orderno;

    @ApiModelProperty(value = "用户id", name = "user_id", hidden = true)
    private String userId;

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
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

    public Integer getOrderStauts() {
        return orderStauts;
    }

    public void setOrderStauts(Integer orderStauts) {
        this.orderStauts = orderStauts;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
