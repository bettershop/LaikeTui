package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

/**
 * 换货收货
 * @author wangxian
 */
@ApiModel(description = "换货收货参数")
public class ReturnConfirmReceiptVo extends MainVo {
    /** lkt_return_order 换货记录id*/
    private int id;
    /** 新增：类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递
     * 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意款 10:拒绝(售后)11:同意并且寄回商品 12售后结束 */
    private int r_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getR_type() {
        return r_type;
    }

    public void setR_type(int r_type) {
        this.r_type = r_type;
    }
}
