package com.laiketui.domain.vo.plugin.auction;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑竞拍配置
 *
 * @author Trick
 * @date 2021/5/17 16:37
 */
@ApiModel(description = "添加/编辑竞拍配置")
public class AddAuctionConfigVo extends MainVo {
    @ApiModelProperty(name = "isOpen", value = "竞拍商品活动id")
    @ParamsMapping("is_open")
    private Integer isOpen;
    @ApiModelProperty(name = "content", value = "规则")
    private String content;
    @ApiModelProperty(name = "days", value = "保留天数")
    private Integer days;
    @ApiModelProperty(name = "waitTime", value = "出价等待时间")
    @ParamsMapping("wait_time")
    private Integer waitTime;
    @ApiModelProperty(name = "lowPepole", value = "最低开拍人数")
    @ParamsMapping("low_pepole")
    private Integer lowPepole;


    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public Integer getLowPepole() {
        return lowPepole;
    }

    public void setLowPepole(Integer lowPepole) {
        this.lowPepole = lowPepole;
    }
}
