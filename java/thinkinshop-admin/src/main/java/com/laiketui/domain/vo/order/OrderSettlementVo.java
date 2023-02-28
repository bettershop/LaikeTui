package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 后台订单结算查询参数
 *
 * @author Trick
 * @date 2021/7/7 11:34
 */
@ApiModel(description = "后台订单结算查询参数")
public class OrderSettlementVo extends MainVo {
    @ApiModelProperty(value = "订单id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "结算订单号/订单号", name = "search")
    private String search;
    @ApiModelProperty(value = "店铺名称", name = "mchName")
    private String mchName;
    @ApiModelProperty(value = "结算状态1=已结算 0=待结算", name = "status")
    private Integer status;
    @ApiModelProperty(value = "开始日期", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束日期", name = "startDate")
    private String endDate;


    @ApiModelProperty(value = "店铺id 后台无需店铺id", name = "mchId", hidden = true)
    private Integer mchId;

    @ApiModelProperty(value = "订单类型", name = "orderType", hidden = true)
    private String orderType;

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Integer getMchId() {
        return mchId;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
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
