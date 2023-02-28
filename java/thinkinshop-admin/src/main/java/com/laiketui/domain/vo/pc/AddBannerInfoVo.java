package com.laiketui.domain.vo.pc;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑轮播图参数
 *
 * @author Trick
 * @date 2021/1/22 10:45
 */
@ApiModel(description = "添加/编辑轮播图参数")
public class AddBannerInfoVo extends MainVo {
    @ApiModelProperty(value = "id", name = "轮播图id")
    private Integer id;

    @ApiModelProperty(value = "imageUrl", name = "轮播图url")
    private String imageUrl;
    @ApiModelProperty(value = "path", name = "路径")
    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
