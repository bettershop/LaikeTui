package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * 退货查询参数
 *
 * @author Trick
 * @date 2021/1/5 16:19
 */
@ApiModel(description = "售后查询参数")
public class RefundQueryVo extends MainVo {

    @ApiModelProperty(value = "售后订单id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "订单号", name = "orderno")
    private String orderno;
    @ApiModelProperty(value = "售货状态 1=退款中,2=退款成功,3=退款失败,4=换货中,5=换货成功,6=换货失败,7=待审核", name = "status")
    private Integer status;
    @ApiModelProperty(value = "开始时间", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private String endDate;

    @ApiModelProperty(value = "订单类型", name = "orderType", hidden = true)
    private String orderType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
