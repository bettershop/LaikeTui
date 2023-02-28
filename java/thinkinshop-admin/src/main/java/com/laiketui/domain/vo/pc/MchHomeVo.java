package com.laiketui.domain.vo.pc;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 店铺首页参数
 *
 * @author Trick
 * @date 2021/6/21 15:30
 */
@ApiModel(description = "店铺首页参数")
public class MchHomeVo extends MainVo {

    @ApiModelProperty(value = "shopId", name = "店铺id")
    private Integer shopId;
    @ApiModelProperty(value = "cid", name = "分类id(71)")
    private Integer cid;
    @ApiModelProperty(value = "keyword", name = "关键字")
    private String keyword;
    @ApiModelProperty(value = "classId", name = "选择的分类ID(71,74)")
    private String classId;
    @ApiModelProperty(value = "minAmount", name = "最小金额")
    private BigDecimal minAmount;
    @ApiModelProperty(value = "maxAmount", name = "最大金额")
    private BigDecimal maxAmount;
    @ApiModelProperty(value = "sortType", name = "排序字段")
    private String sortType;
    @ApiModelProperty(value = "sort", name = "排序 0=升序 1=降序")
    private Integer sort = 1;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
