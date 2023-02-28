package com.laiketui.domain.vo.main;

import java.math.BigDecimal;

/**
 * 支付宝退款参数
 *
 * @author Trick
 * @date 2020/12/2 18:10
 */
public class AliPayReturnVo {
    /**
     * 交易流水号
     */
    private String trade_no;
    /**
     * 订单号
     */
    private String out_trade_no;
    /**
     * 退款流水号
     */
    private String out_request_no;
    /**
     * 退款金额
     */
    private BigDecimal refund_amount;

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public BigDecimal getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(BigDecimal refund_amount) {
        this.refund_amount = refund_amount;
    }
}
