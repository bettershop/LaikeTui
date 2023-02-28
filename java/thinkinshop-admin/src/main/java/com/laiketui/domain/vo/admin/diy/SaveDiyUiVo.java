package com.laiketui.domain.vo.admin.diy;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑 ui导航栏
 *
 * @author Trick
 * @date 2021/7/1 9:29
 */
@ApiModel(description = "添加/编辑 ui导航栏")
public class SaveDiyUiVo extends MainVo {
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "ui导航栏名称", name = "name")
    private String name;
    @ApiModelProperty(value = "图片路径url", name = "picUrl")
    private String picUrl;
    @ApiModelProperty(value = "跳转地址", name = "url")
    private String url;
    @ApiModelProperty(value = "是否显示", name = "isShow")
    private Integer isShow;
    @ApiModelProperty(value = "跳转类型 1.分类 2.商品 3.店铺", name = "type0")
    private Integer type0;

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

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getType0() {
        return type0;
    }

    public void setType0(Integer type0) {
        this.type0 = type0;
    }
}
