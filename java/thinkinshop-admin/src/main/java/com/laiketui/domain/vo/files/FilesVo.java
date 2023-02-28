package com.laiketui.domain.vo.files;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletRequest;

/**
 * 图片列表参数
 *
 * @author Trick
 * @date 2021/7/8 10:10
 */
@ApiModel(description = "图片列表参数")
public class FilesVo extends MainVo {

    @ApiModelProperty(value = "分组id -1默认全部", name = "groupId")
    private Integer groupId=-1;
    @ApiModelProperty(value = "开始时间", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private String endDate;
    @ApiModelProperty(value = "标题", name = "title")
    private String title;


    @ApiModelProperty(value = "-", name = "-",hidden = true)
    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
