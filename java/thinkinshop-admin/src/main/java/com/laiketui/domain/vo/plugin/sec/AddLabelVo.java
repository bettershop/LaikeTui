package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑标签
 *
 * @author Trick
 * @date 2021/10/14 11:58
 */
@ApiModel(description = "添加/编辑秒杀标签")
public class AddLabelVo extends MainVo {
    @ApiModelProperty(name = "id", value = "id")
    private String id;

    @ApiModelProperty(name = "id", value = "标签名称")
    private String name;
    @ApiModelProperty(name = "title", value = "标签副标题")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
