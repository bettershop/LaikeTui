package com.laiketui.domain.vo.mch;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 资金管理列表参数
 *
 * @author Trick
 * @date 2021/6/7 9:48
 */
@ApiModel(description = "资金管理列表参数")
public class FinanceVo extends MainVo {

    @ApiModelProperty(value = "状态：1.入账 2.出账", name = "status")
    private Integer status;
    @ApiModelProperty(value = "账单类型 订单 退款(status必须是2) 提现", name = "type")
    private Integer type;
    @ApiModelProperty(value = "开始时间", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private String endDate;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
