package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑物流
 *
 * @author Trick
 * @date 2021/7/6 18:06
 */
@ApiModel(description = "添加/编辑物流")
public class ExpressSaveVo extends MainVo {
    @ApiModelProperty(name = "id", value = "主键")
    private Integer id;
    @ApiModelProperty(name = "value", value = "物流公司名称")
    private String name;
    @ApiModelProperty(name = "code", value = "编码")
    private String code;
    @ApiModelProperty(name = "sort", value = "排序")
    private Integer sort;
    @ApiModelProperty(name = "switchse", value = "开关 1=开 2=关")
    private Integer switchse;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getSwitchse() {
        return switchse;
    }

    public void setSwitchse(Integer switchse) {
        this.switchse = switchse;
    }
}
