package com.laiketui.domain.vo.user;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 会员等级订单接口参数
 *
 * @author Trick
 * @date 2020/12/23 12:02
 */
@ApiModel(description = "会员等级订单参数")
public class GradeOrderVo extends MainVo {

    @ApiModelProperty(value = "等级id", name = "id")
    private int id;

    @ApiModelProperty(value = "支付方式 支付方式 wallet_pay-钱包支付 aliPay-支付宝支付 app_wechat - app微信支付 jsapi_wechat-微信内网页支付   mini_wechat-小程序微信支付", name = "type")
    @ParamsMapping("type")
    private String payType;
    @ApiModelProperty(value = "余额支付密码", name = "password")
    private String password;
    @ApiModelProperty(value = "充值时长 1-包月 2-包季 3-包年", name = "method")
    private int method;
    @ApiModelProperty(value = "充值用途 1-充值 2-续费 3-升级", name = "flag")
    private int flag;
    @ApiModelProperty(value = "推荐人id", name = "tui_id")
    @ParamsMapping("tui_id")
    private String tuiId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getTuiId() {
        return tuiId;
    }

    public void setTuiId(String tuiId) {
        this.tuiId = tuiId;
    }
}
