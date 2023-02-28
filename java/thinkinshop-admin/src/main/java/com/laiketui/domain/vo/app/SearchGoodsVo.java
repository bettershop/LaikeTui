package com.laiketui.domain.vo.app;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品分类-搜索
 *
 * @author Trick
 * @date 2021/2/24 15:31
 */
@ApiModel(description = "商品分类-搜索")
public class SearchGoodsVo extends MainVo {
    @ApiModelProperty(value = "分类id", name = "cid")
    private Integer cid;

    @ApiModelProperty(value = "店铺id", name = "shopId")
    @ParamsMapping("shop_id")
    private Integer shopId;
    @ApiModelProperty(value = "关键字", name = "keyword")
    private String keyword;
    @ApiModelProperty(value = "查询条件", name = "queryCriteria")
    @ParamsMapping("query_criteria")
    private String queryCriteria;
    @ApiModelProperty(value = "排序条件", name = "sortCriteria")
    @ParamsMapping("sort_criteria")
    private String sortCriteria;
    @ApiModelProperty(value = "排序 asc desc", name = "sort")
    private String sort;
    @ApiModelProperty(value = "次数", name = "num")
    private Integer num;

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

    public String getQueryCriteria() {
        return queryCriteria;
    }

    public void setQueryCriteria(String queryCriteria) {
        this.queryCriteria = queryCriteria;
    }

    public String getSortCriteria() {
        return sortCriteria;
    }

    public void setSortCriteria(String sortCriteria) {
        this.sortCriteria = sortCriteria;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
