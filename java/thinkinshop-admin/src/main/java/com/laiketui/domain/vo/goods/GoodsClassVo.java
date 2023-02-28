package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品类别
 *
 * @author Trick
 * @date 2020/12/30 10:03
 */
@ApiModel(description = "类别参数")
public class GoodsClassVo extends MainVo {
    @ApiModelProperty(value = "分类名称", name = "className")
    private String className;
    @ApiModelProperty(value = "分类id", name = "classId")
    private Integer classId;
    @ApiModelProperty(value = "父类id", name = "fatherId")
    private Integer fatherId;
    @ApiModelProperty(value = "当前级别", name = "level")
    private Integer level;
    @ApiModelProperty(value = "商品id(1=查询下级,2=查询上级,3=根据id查询 默认查询一级)", name = "type")
    private Integer type;

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getFatherId() {
        return fatherId;
    }

    public void setFatherId(Integer fatherId) {
        this.fatherId = fatherId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
