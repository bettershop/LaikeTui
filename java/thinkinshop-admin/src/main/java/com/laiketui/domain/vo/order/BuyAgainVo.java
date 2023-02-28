package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

/**
 * 再次购买
 * @author wangxain
 */
@ApiModel(description = "再次购买")
public class BuyAgainVo extends MainVo {

    /**订单id*/
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
