package com.laiketui.domain.vo.plugin.bargin;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加砍价商品活动
 *
 * @author Trick
 * @date 2021/5/14 10:34
 */
@ApiModel(description = "添加砍价商品活动")
public class AddBargainGoodsVo extends MainVo {

    @ApiModelProperty(name = "minPrice", value = "砍价最低价")
    private BigDecimal minPrice;
    @ApiModelProperty(name = "attrid", value = "商品规格id")
    private Integer attrid;
    /**
     * {"goodsid":3,"startdate":"2021-05-14 11:18:23","enddate":"2021-05-31 11:18:27","barginman":50,"is_show":1}
     */
    @ApiModelProperty(name = "obj", value = "砍价其它字段信息 json")
    private String obj;
    /**
     * {"one_man":"1","min_one":"100","max_one":"2000"}
     * O:8:"stdClass":3:{s:7:"one_man";s:1:"1";s:7:"min_one";s:3:"100";s:7:"max_one";s:4:"2000";}
     */
    @ApiModelProperty(name = "statusData", value = "砍价方式参数 json")
    @ParamsMapping("status_data")
    private String statusData;

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public Integer getAttrid() {
        return attrid;
    }

    public void setAttrid(Integer attrid) {
        this.attrid = attrid;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getStatusData() {
        return statusData;
    }

    public void setStatusData(String statusData) {
        this.statusData = statusData;
    }
}
