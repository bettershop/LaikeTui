package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * This is description of java
 *
 * @author Trick
 * @date 2021/1/6 16:13
 */
@ApiModel(description = "售后查询参数")
public class CommentsInfoVo extends MainVo {

    @ApiModelProperty(value = "评论级别 GOOD=好评,NOTBAD=中评,BAD=差评", name = "type")
    private String type;
    @ApiModelProperty(value = "订单号", name = "orderno")
    private String orderno;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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
}
