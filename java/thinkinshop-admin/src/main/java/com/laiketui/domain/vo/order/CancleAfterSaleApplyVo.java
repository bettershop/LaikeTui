package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

/**
 * 撤销订单
 * @author wangxain
 */
@ApiModel(description = "撤销售后申请")
public class CancleAfterSaleApplyVo extends MainVo {

    /**售后记录id*/
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
