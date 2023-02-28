package com.laiketui.domain.vo.plugin.group;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑配置信息
 *
 * @author Trick
 * @date 2021/5/11 9:32
 */
@ApiModel(description = "添加/编辑配置信息")
public class AddGroupConfigVo extends MainVo {
    @ApiModelProperty(name = "groupTime", value = "拼团时限(小时)")
    @ParamsMapping("group_time")
    private Integer groupTime;
    @ApiModelProperty(name = "openNum", value = "开团数量")
    @ParamsMapping("open_num")
    private Integer openNum;
    @ApiModelProperty(name = "canNum", value = "参团数量")
    @ParamsMapping("can_num")
    private Integer canNum;
    @ApiModelProperty(name = "canAgain", value = "是否重复参团 1 是 0 否")
    @ParamsMapping("can_again")
    private Integer canAgain;
    @ApiModelProperty(name = "openDiscount", value = "团长优惠 1 是 0 否")
    @ParamsMapping("open_discount")
    private Integer openDiscount;
    @ApiModelProperty(name = "rule", value = "规则")
    @ParamsMapping("rule")
    private String rule;
    @ApiModelProperty(name = "isOpen", value = "是否开启拼团")
    @ParamsMapping("is_open")
    private Integer isOpen;
    @ApiModelProperty(name = "heraldTime", value = "出现拼团先知的最大时间(小时)")
    @ParamsMapping("herald_time")
    private Integer heraldTime;

    public Integer getCanNum() {
        return canNum;
    }

    public void setCanNum(Integer canNum) {
        this.canNum = canNum;
    }

    public Integer getGroupTime() {
        return groupTime;
    }

    public void setGroupTime(Integer groupTime) {
        this.groupTime = groupTime;
    }

    public Integer getOpenNum() {
        return openNum;
    }

    public void setOpenNum(Integer openNum) {
        this.openNum = openNum;
    }

    public Integer getCanAgain() {
        return canAgain;
    }

    public void setCanAgain(Integer canAgain) {
        this.canAgain = canAgain;
    }

    public Integer getOpenDiscount() {
        return openDiscount;
    }

    public void setOpenDiscount(Integer openDiscount) {
        this.openDiscount = openDiscount;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getHeraldTime() {
        return heraldTime;
    }

    public void setHeraldTime(Integer heraldTime) {
        this.heraldTime = heraldTime;
    }
}
