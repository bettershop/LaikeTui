package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 平台订单详情
 * @author wangxain
 */
@ApiModel(description = "平台订单详情")
public class AdminOrderDetailVo extends MainVo {

    /**类型*/
    @ApiModelProperty(hidden = true)
    private String type;
    /**订单操作类型 编辑/查看 */
    @ApiModelProperty(hidden = true)
    private String orderType = "modify";
    /**订单号*/
    @ApiModelProperty(name = "id", value = "订单号")
    private String id;
    /**订单号*/
    @ApiModelProperty(name = "sNo", value = "订单号")
    private String sNo;

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
