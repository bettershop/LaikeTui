package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

/**
 * 售后详情
 * @author wangxain
 */
@ApiModel(description = "售后详情")
public class RefundDetailsVo extends MainVo {

    /**订单id*/
    private int id;
    /**订单商品id*/
    private int pid;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }
}
