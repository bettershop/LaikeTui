package com.laiketui.domain.vo.plugin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分销列表查询参数
 *
 * @author Trick
 * @date 2021/2/4 16:42
 */
@ApiModel(description = "分销列表查询参数")
public class DistributionQueryVo extends MainVo {
    @ApiModelProperty(name = "id", value = "id")
    private Integer id;

    @ApiModelProperty(name = "fatherUid", value = "推荐人id")
    private String fatherUid;
    @ApiModelProperty(name = "userName", value = "用户名称/id")
    private String userName;
    @ApiModelProperty(name = "phone", value = "手机号码")
    private String phone;
    @ApiModelProperty(name = "grade", value = "分销等级")
    private Integer grade;
    @ApiModelProperty(name = "startDate", value = "开始时间")
    private String startDate;
    @ApiModelProperty(name = "endDate", value = "结束时间")
    private String endDate;
    @ApiModelProperty(name = "sortColumn", value = "排序字段")
    private String sortColumn;
    @ApiModelProperty(name = "sortType", value = "排序方式 1=降序 2=升序")
    private Integer sortType;

    public String getFatherUid() {
        return fatherUid;
    }

    public void setFatherUid(String fatherUid) {
        this.fatherUid = fatherUid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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

    public String getSortColumn() {
        return sortColumn;
    }

    public void setSortColumn(String sortColumn) {
        this.sortColumn = sortColumn;
    }

    public Integer getSortType() {
        return sortType;
    }

    public void setSortType(Integer sortType) {
        this.sortType = sortType;
    }
}
