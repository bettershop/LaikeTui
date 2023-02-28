package com.laiketui.domain.vo.admin.diy;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑 营销活动
 *
 * @author Trick
 * @date 2021/7/1 16:37
 */
@ApiModel(description = "添加/编辑 营销活动")
public class AddDiyActivityVo extends MainVo {
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "类型 0.活动专题 1.营销插件", name = "activityType")
    private Integer activityType;
    @ApiModelProperty(value = "插件类型", name = "plugType")
    private Integer plugType;
    @ApiModelProperty(value = "标题", name = "name")
    private String name;
    @ApiModelProperty(value = "商品id集", name = "pid")
    private String pid;
    @ApiModelProperty(value = "满减id集", name = "subtractionId")
    private String subtractionId;

    public String getSubtractionId() {
        return subtractionId;
    }

    public void setSubtractionId(String subtractionId) {
        this.subtractionId = subtractionId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getActivityType() {
        return activityType;
    }

    public void setActivityType(Integer activityType) {
        this.activityType = activityType;
    }

    public Integer getPlugType() {
        return plugType;
    }

    public void setPlugType(Integer plugType) {
        this.plugType = plugType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
