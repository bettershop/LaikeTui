package com.laiketui.domain.vo.admin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 店铺报表参数
 *
 * @author Trick
 * @date 2021/7/5 14:26
 */
@ApiModel(description = "店铺报表参数")
public class MchReportVo extends MainVo {
    @ApiModelProperty(value = "yesterday 昨日、week 近七天、month 本月 day=今天", name = "type")
    private String type;
    @ApiModelProperty(value = "开始时间", name = "startDate")
    private String startDate;
    @ApiModelProperty(value = "结束时间", name = "endDate")
    private String endDate;
    @ApiModelProperty(value = "0=升序 1=降序", name = "sortType", required = true)
    private int sortType;
    @ApiModelProperty(value = "店铺名称", name = "mchName")
    private String mchName;


    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public String getMchName() {
        return mchName;
    }

    public void setMchName(String mchName) {
        this.mchName = mchName;
    }
}
