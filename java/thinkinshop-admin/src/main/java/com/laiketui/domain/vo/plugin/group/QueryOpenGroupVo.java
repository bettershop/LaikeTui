package com.laiketui.domain.vo.plugin.group;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 开团记录列表参数
 *
 * @author Trick
 * @date 2021/5/8 14:52
 */
@ApiModel(description = "开团/参团记录列表参数")
public class QueryOpenGroupVo extends MainVo {
    /**
     * 是否是参团
     */
    private boolean isOpen = true;
    @ApiModelProperty(name = "platformId", value = "(平台)平台活动id")
    private Integer platformId;
    @ApiModelProperty(name = "activityNo", value = "(平台)活动编号")
    @ParamsMapping("activity_no")
    private String activityNo;

    @ApiModelProperty(name = "goodsName", value = "商品名称")
    private String goodsName;
    @ApiModelProperty(name = "groupManName", value = "团长名称")
    private String groupManName;
    @ApiModelProperty(name = "groupStatus", value = "拼团状态")
    private Integer groupStatus;
    @ApiModelProperty(name = "groupType", value = "拼团类型")
    private Integer groupType;

    public Integer getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Integer platformId) {
        this.platformId = platformId;
    }

    public String getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(String activityNo) {
        this.activityNo = activityNo;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGroupManName() {
        return groupManName;
    }

    public void setGroupManName(String groupManName) {
        this.groupManName = groupManName;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Integer getGroupType() {
        return groupType;
    }

    public void setGroupType(Integer groupType) {
        this.groupType = groupType;
    }
}
