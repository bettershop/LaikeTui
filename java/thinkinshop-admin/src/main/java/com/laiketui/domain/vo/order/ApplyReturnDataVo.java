package com.laiketui.domain.vo.order;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 申请售后
 *
 * @author Trick
 * @date 2020/12/4 15:14
 */
@ApiModel(description = "申请售后参数")
public class ApplyReturnDataVo extends MainVo {
    @ApiModelProperty(value = "订单详情id", name = "order_details_id")
    @ParamsMapping("order_details_id")
    private String orderDetailsId;
    @ApiModelProperty(value = "订单金额", name = "refund_amount")
    @ParamsMapping("refund_amount")
    private BigDecimal refundAmount;
    @ApiModelProperty(value = "用户申请的退款金额", name = "refund_apply_money")
    @ParamsMapping("refund_apply_money")
    private BigDecimal refundApplyMoney;
    @ApiModelProperty(value = "退货原因", name = "explain")
    private String explain;
    @ApiModelProperty(value = "退货类型", name = "type")
    private int type;

    @ApiModelProperty(value = "图片总数量", name = "upload_z_num")
    @ParamsMapping("upload_z_num")
    private int uploadMaxNum;
    @ApiModelProperty(value = "当前上传图片的标记", name = "upload_num")
    @ParamsMapping("upload_num")
    private int uploadNum;

    public int getUploadMaxNum() {
        return uploadMaxNum;
    }

    public void setUploadMaxNum(int uploadMaxNum) {
        this.uploadMaxNum = uploadMaxNum;
    }

    public int getUploadNum() {
        return uploadNum;
    }

    public void setUploadNum(int uploadNum) {
        this.uploadNum = uploadNum;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }

    public BigDecimal getRefundApplyMoney() {
        return refundApplyMoney;
    }

    public void setRefundApplyMoney(BigDecimal refundApplyMoney) {
        this.refundApplyMoney = refundApplyMoney;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
