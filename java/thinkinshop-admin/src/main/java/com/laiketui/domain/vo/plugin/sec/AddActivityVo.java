package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑活动
 *
 * @author Trick
 * @date 2021/5/7 9:44
 */
@ApiModel(description = "添加/编辑活动")
public class AddActivityVo extends MainVo {
    @ApiModelProperty(name = "id", value = "活动id")
    private Integer id;
    @ApiModelProperty(name = "name", value = "活动名称")
    private String name;
    @ApiModelProperty(name = "type", value = "活动类型")
    private Integer type;
    @ApiModelProperty(name = "starttime", value = "活动开始时间")
    private String starttime;
    @ApiModelProperty(name = "endtime", value = "活动结束时间")
    private String endtime;
    @ApiModelProperty(name = "isshow", value = "是否显示 1 是 0 否")
    private Integer isshow;

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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getIsshow() {
        return isshow;
    }

    public void setIsshow(Integer isshow) {
        this.isshow = isshow;
    }
}
