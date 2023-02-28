package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;

/**
 * 平台发货
 * @author wangxain
 */
@ApiModel(description = "平台发货")
public class AdminDeliveryVo extends MainVo {

    /**订单详情id*/
    private  String id;
    /**订单类型*/
    private  String otype;
    /**管理员用户名*/
    private  String adminName;
    /**订单号*/
    private  String sNo;
    /**未知*/
    private  String trade;
    /**快递公司id*/
    private  String express;
    /**快递号码*/
    private  String courier_num;
    /**快递类型*/
    private  String express_type;
    /**快递公司名称*/
    private  String express_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOtype() {
        return otype;
    }

    public void setOtype(String otype) {
        this.otype = otype;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getExpress() {
        return express;
    }

    public void setExpress(String express) {
        this.express = express;
    }

    public String getCourier_num() {
        return courier_num;
    }

    public void setCourier_num(String courier_num) {
        this.courier_num = courier_num;
    }

    public String getExpress_type() {
        return express_type;
    }

    public void setExpress_type(String express_type) {
        this.express_type = express_type;
    }

    public String getExpress_name() {
        return express_name;
    }

    public void setExpress_name(String express_name) {
        this.express_name = express_name;
    }
}
