package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 代客下单
 *
 * @author Trick
 * @date 2021/8/2 17:50
 */
@ApiModel(description = "代客下单")
public class HelpOrderVo extends MainVo {

    @ApiModelProperty(value = "会员userId", name = "userId",required = true)
    private String userId;
    @ApiModelProperty(value = "商品信息json数组 [{\"id\":\"17028\",\"pid\":\"1475\",\"num\":1}...]", name = "products",required = true)
    private String products;
    @ApiModelProperty(value = "优惠金额", name = "wipeOff",required = true)
    private String wipeOff;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getWipeOff() {
        return wipeOff;
    }

    public void setWipeOff(String wipeOff) {
        this.wipeOff = wipeOff;
    }

}
