package com.laiketui.domain.vo.plugin.group;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 拼团商品信息适配
 * {"pid":"1153","cid":9451,"num":1,"groupnum":5,"price":22.2,
 * "frompage":"kaituan","activity_no":"15","platform_activities_id":"0",“ptcode”:"KT2417235301829"}
 *
 * @author Trick
 * @date 2021/4/15 14:37
 */
public class ProductInfoParamVo implements Serializable {
    @ApiModelProperty(name = "pid", value = "商品id")
    private Integer pid;
    @ApiModelProperty(name = "cid", value = "规格id")
    private Integer cid;
    @ApiModelProperty(name = "num", value = "数量")
    private Integer num;
    @ApiModelProperty(name = "groupnum", value = "拼团人数")
    private Integer groupnum;
    @ApiModelProperty(name = "price", value = "价格")
    private BigDecimal price;
    @ApiModelProperty(name = "frompage", value = "类型 kaituan/cantuan")
    @JSONField(name = "frompage")
    private String fromapge;
    @ApiModelProperty(name = "activity_no", value = "插件活动id")
    @JSONField(alternateNames = {"activity_no","bargain_id"})
    private Integer activityNo;
    @ApiModelProperty(name = "platform_activities_id", value = "平台活动id")
    @JSONField(name = "platform_activities_id")
    private String platformActivitiesId;

    @ApiModelProperty(name = "ptcode", value = "拼团编号")
    private String ptcode;


    public String getPtcode() {
        return ptcode;
    }

    public void setPtcode(String ptcode) {
        this.ptcode = ptcode;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getGroupnum() {
        return groupnum;
    }

    public void setGroupnum(Integer groupnum) {
        this.groupnum = groupnum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFromapge() {
        return fromapge;
    }

    public void setFromapge(String fromapge) {
        this.fromapge = fromapge;
    }

    public Integer getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(Integer activityNo) {
        this.activityNo = activityNo;
    }

    public String getPlatformActivitiesId() {
        return platformActivitiesId;
    }

    public void setPlatformActivitiesId(String platformActivitiesId) {
        this.platformActivitiesId = platformActivitiesId;
    }
}
