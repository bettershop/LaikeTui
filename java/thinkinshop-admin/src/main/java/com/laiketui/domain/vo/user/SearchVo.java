package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 搜索参数
 *
 * @author Trick
 * @date 2021/6/18 11:01
 */
@ApiModel(description = "搜索参数")
public class SearchVo extends MainVo {
    @ApiModelProperty(value = "主目录Id", name = "cid")
    private Integer cid;
    @ApiModelProperty(value = "分配目录id集", name = "classId")
    private String classId;
    @ApiModelProperty(value = "品牌id集", name = "brandId")
    private String brandId;
    @ApiModelProperty(value = "关键字", name = "keyword")
    private String keyword;
    @ApiModelProperty(value = "金额", name = "amount")
    private String amount;
    @ApiModelProperty(value = "排序类型(price 或 volume)", name = "sort")
    private String sort;
    @ApiModelProperty(value = "1=降序 0=升序", name = "sortType")
    private int sortType = 0;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }
}
