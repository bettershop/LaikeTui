package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单列表参数-商户后台管理
 *
 * @author Trick
 * @date 2021/7/19 14:35
 */
@ApiModel(description = "订单查询列表参数")
public class AdminOrderListVo extends MainVo {

    @ApiModelProperty(value = "订单id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "订单号", name = "orderNo")
    private String orderNo;

    @ApiModelProperty(value = "关键字", name = "keyWord")
    private String keyWord;
    @ApiModelProperty(value = "店铺名称", name = "mchName")
    private String mchName;
    @ApiModelProperty(value = "订单状态", name = "status")
    private Integer status;
    @ApiModelProperty(value = "特殊订单分类 1=实物订单 2=自提订单 3=虚拟订单 4=活动订单 ,7=积分,8=秒杀,", name = "selfLifting", hidden = true)
    private Integer selfLifting;
    @ApiModelProperty(value = "下单类型", name = "operationType")
    private Integer operationType;
    @ApiModelProperty(value = "开始日期", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束日期", name = "endDate")
    private String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
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

    public Integer getSelfLifting() {
        return selfLifting;
    }

    public void setSelfLifting(Integer selfLifting) {
        this.selfLifting = selfLifting;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
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
