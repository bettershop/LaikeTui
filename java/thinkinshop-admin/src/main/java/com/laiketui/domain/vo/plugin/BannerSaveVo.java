package com.laiketui.domain.vo.plugin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑 轮播图
 *
 * @author Trick
 * @date 2021/6/30 14:47
 */
@ApiModel(description = "添加/编辑 轮播图")
public class BannerSaveVo extends MainVo {
    @ApiModelProperty(name = "id", value = "轮播图id")
    private Integer id;
    @ApiModelProperty(name = "type0", value = "跳转类型 1.分类 2.商品 3.店铺 0=自定义")
    private Integer type0;
    @ApiModelProperty(name = "url", value = "路径")
    private String url;
    @ApiModelProperty(name = "picUrl", value = "图片")
    private String picUrl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType0() {
        return type0;
    }

    public void setType0(Integer type0) {
        this.type0 = type0;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
