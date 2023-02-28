package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑秒杀数段
 *
 * @author Trick
 * @date 2021/5/6 15:17
 */
@ApiModel(description = "添加/编辑秒杀时段")
public class AddSecondsVo extends MainVo {
    @ApiModelProperty(name = "id", value = "时间段id")
    private Integer id;

    @ApiModelProperty(name = "secName", value = "秒杀时段名称")
    private String secName;
    @ApiModelProperty(name = "startTime", value = "开始时间")
    private String startTime;
    @ApiModelProperty(name = "endTime", value = "结束时间")
    private String endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getSecName() {
        return secName;
    }

    public void setSecName(String secName) {
        this.secName = secName;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
