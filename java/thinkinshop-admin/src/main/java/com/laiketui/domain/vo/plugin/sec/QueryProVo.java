package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询秒杀商品信息
 *
 * @author Trick
 * @date 2021/5/7 14:54
 */
@ApiModel(description = "添加/编辑秒杀商品")
public class QueryProVo extends MainVo {
    @ApiModelProperty(name = "my_class", value = "商品分类id")
    @ParamsMapping("my_class")
    private Integer myClass;
    @ApiModelProperty(name = "my_brand", value = "品牌id")
    @ParamsMapping("my_brand")
    private Integer myBrand;
    @ApiModelProperty(name = "pro_name", value = "商品名称")
    @ParamsMapping("pro_name")
    private String proName;
    @ApiModelProperty(name = "labelId", value = "标签id")
    private String labelId;

    public Integer getMyClass() {
        return myClass;
    }

    public void setMyClass(Integer myClass) {
        this.myClass = myClass;
    }

    public Integer getMyBrand() {
        return myBrand;
    }

    public void setMyBrand(Integer myBrand) {
        this.myBrand = myBrand;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }
}
