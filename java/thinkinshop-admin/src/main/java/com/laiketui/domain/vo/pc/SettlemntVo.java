package com.laiketui.domain.vo.pc;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单结算查询参数
 *
 * @author Trick
 * @date 2021/6/4 16:47
 */
@ApiModel("订单结算查询参数")
public class SettlemntVo extends MainVo {

    @ApiModelProperty(value = "结算id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "结算状态1=已结算 2=待结算", name = "status")
    private Integer status;
    @ApiModelProperty(value = "订单号", name = "orderno")
    private String orderno;
    @ApiModelProperty(value = "开始时间", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
