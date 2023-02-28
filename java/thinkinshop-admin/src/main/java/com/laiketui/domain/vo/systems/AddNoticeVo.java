package com.laiketui.domain.vo.systems;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改系统公告
 *
 * @author Trick
 * @date 2021/2/1 10:46
 */
@ApiModel(description = "添加/修改系统公告")
public class AddNoticeVo extends MainVo {
    @ApiModelProperty(name = "id", value = "公告Id")
    private Integer id;

    @ApiModelProperty(name = "title", value = "公告标题")
    private String title;
    @ApiModelProperty(name = "type", value = "公告类型: 1--系统维护  2--版本更新")
    private Integer type;
    @ApiModelProperty(name = "isTime", value = "公告类型: 是否时间限制")
    private Integer isTime = 1;
    @ApiModelProperty(name = "startDate", value = "开始时间")
    private String startDate;
    @ApiModelProperty(name = "endDate", value = "结束时间")
    private String endDate;
    @ApiModelProperty(name = "content", value = "公告内容")
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIsTime() {
        return isTime;
    }

    public void setIsTime(Integer isTime) {
        this.isTime = isTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
