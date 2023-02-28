package com.laiketui.domain.vo.plugin.auction;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑竞拍商品信息
 *
 * @author Trick
 * @date 2021/5/14 15:28
 */
@ApiModel(description = "添加/编辑竞拍商品信息")
public class AddAuctionVo extends MainVo {
    @ApiModelProperty(name = "id", value = "竞拍商品活动id")
    private Integer id;
    @ApiModelProperty(name = "attrIdCheck", value = "所选商品的规格id")
    @ParamsMapping("attr_id_check")
    private Integer attrIdCheck;
    @ApiModelProperty(name = "title", value = "竞拍标题")
    private String title;
    @ApiModelProperty(name = "price", value = "起拍价")
    private BigDecimal price;
    @ApiModelProperty(name = "addPrice", value = "加价幅度")
    @ParamsMapping("add_price")
    private BigDecimal addPrice;
    @ApiModelProperty(name = "promise", value = "保证金")
    private BigDecimal promise;
    @ApiModelProperty(name = "starttime", value = "开始时间")
    private String starttime;
    @ApiModelProperty(name = "endtime", value = "结束时间")
    private String endtime;
    @ApiModelProperty(name = "isShow", value = "是否显示")
    @ParamsMapping("is_show")
    private Integer isShow;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAttrIdCheck() {
        return attrIdCheck;
    }

    public void setAttrIdCheck(Integer attrIdCheck) {
        this.attrIdCheck = attrIdCheck;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getAddPrice() {
        return addPrice;
    }

    public void setAddPrice(BigDecimal addPrice) {
        this.addPrice = addPrice;
    }

    public BigDecimal getPromise() {
        return promise;
    }

    public void setPromise(BigDecimal promise) {
        this.promise = promise;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }
}
