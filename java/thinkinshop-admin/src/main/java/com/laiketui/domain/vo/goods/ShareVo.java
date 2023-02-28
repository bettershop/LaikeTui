package com.laiketui.domain.vo.goods;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 商品分享参数
 *
 * @author Trick
 * @date 2020/12/18 13:49
 */
@ApiModel(description = "商品分享参数")
public class ShareVo extends MainVo {
    @ApiModelProperty(value = "商品id/积分商品id", name = "proId")
    private int proId;
    @ApiModelProperty(value = "订单号", name = "orderno")
    @ParamsMapping("order_no")
    private String orderno;
    @ApiModelProperty(value = "砍价状态 砍价可选", name = "brStatus")
    private Integer brStatus;
    @ApiModelProperty(value = "活动编号", name = "activityNo")
    @ParamsMapping("activity_no")
    private Integer activityNo;
    @ApiModelProperty(value = "订单类型", name = "type")
    private String type;
    @ApiModelProperty(value = "-", name = "shareType")
    @ParamsMapping("share_type")
    private int shareType;

    @ApiModelProperty(value = "二维码跳转路径 可选", name = "path")
    private String path;
    @ApiModelProperty(value = "规格id", name = "attrId")
    private Integer attrId;

    @ApiModelProperty(value = "插件id", name = "pluginId")
    @ParamsMapping({"fx_id","bindding_id"})
    private Integer pluginId;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Integer getPluginId() {
        return pluginId;
    }

    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public Integer getBrStatus() {
        return brStatus;
    }

    public void setBrStatus(Integer brStatus) {
        this.brStatus = brStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    public Integer getActivityNo() {
        return activityNo;
    }

    public void setActivityNo(Integer activityNo) {
        this.activityNo = activityNo;
    }
}
