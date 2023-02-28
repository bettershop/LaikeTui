package com.laiketui.domain.vo.goods;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品搜索参数
 *
 * @author Trick
 * @date 2021/2/26 11:00
 */
@ApiModel(description = "商品搜索参数")
public class GoodsSearchVo extends MainVo {

    @ApiModelProperty(value = "关键字", name = "keyword")
    private String keyword;
    @ApiModelProperty(value = "查询条件", name = "queryCriteria")
    @ParamsMapping("query_criteria")
    private String queryCriteria;
    @ApiModelProperty(value = "排序条件", name = "sortCriteria")
    @ParamsMapping("sort_criteria")
    private String sortCriteria;
    @ApiModelProperty(value = "类型(0=全部 1=热销 2=店铺)", name = "type")
    private Integer type = 0;
    @ApiModelProperty(value = "排序", name = "sort")
    private String sort = "asc";
    @ApiModelProperty(value = "分页", name = "num")
    @ParamsMapping("num")
    private Integer num;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
