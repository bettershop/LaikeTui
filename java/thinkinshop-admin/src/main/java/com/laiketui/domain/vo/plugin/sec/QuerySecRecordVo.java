package com.laiketui.domain.vo.plugin.sec;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询秒杀记录列表
 *
 * @author Trick
 * @date 2021/5/7 17:09
 */
@ApiModel(description = "查询秒杀记录列表")
public class QuerySecRecordVo extends MainVo {
    @ApiModelProperty(name = "pro", value = "商品名称")
    @ParamsMapping("pro")
    private String goodsName;
    @ApiModelProperty(name = "time_id", value = "秒杀时段id")
    @ParamsMapping("time_id")
    private Integer timeId;
    @ApiModelProperty(name = "user", value = "userId/昵称")
    private String user;
    @ApiModelProperty(name = "startdate", value = "开始时间")
    private String startdate;
    @ApiModelProperty(name = "enddate", value = "结束时间")
    private String enddate;


    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getTimeId() {
        return timeId;
    }

    public void setTimeId(Integer timeId) {
        this.timeId = timeId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}
