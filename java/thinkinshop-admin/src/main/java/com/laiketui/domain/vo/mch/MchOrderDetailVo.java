package com.laiketui.domain.vo.mch;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 订单详情参数
 *
 * @author Trick
 * @date 2020/11/27 11:34
 */
@ApiModel("订单详情参数")
public class MchOrderDetailVo extends MainVo {

    @ApiModelProperty(value = "店铺id", name = "shop_id")
    private int shopId;
    @ApiModelProperty(value = "用户id", name = "user_id")
    private String userId;
    @ApiModelProperty(value = "订单号", name = "sNo")
    private String sNo;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }
}
