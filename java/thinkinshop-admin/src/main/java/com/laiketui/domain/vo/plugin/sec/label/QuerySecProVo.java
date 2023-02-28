package com.laiketui.domain.vo.plugin.sec.label;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 秒杀标签商品查询
 *
 * @author Trick
 * @date 2021/10/27 16:55
 */
@ApiModel(description = "秒杀标签商品查询")
public class QuerySecProVo extends MainVo {
    @ApiModelProperty(name = "labelId", value = "标签id")
    private String labelId;
    @ApiModelProperty(name = "secGoodsId", value = "秒杀商品id")
    private String secGoodsId;
    @ApiModelProperty(name = "secStatus", value = "秒杀活动状态 0未开始 1进行中 2已结束")
    @ParamsMapping("secStatus")
    private Integer secStatus;
    @ApiModelProperty(name = "keyName", value = "商品编号/商品名称")
    @ParamsMapping("keyName")
    private String keyName;

    @ApiModelProperty(name = "startDate", value = "开始时间")
    private String startDate;
    @ApiModelProperty(name = "endDate", value = "结束时间")
    private String endDate;

    public String getLabelId() {
        return labelId;
    }

    public void setLabelId(String labelId) {
        this.labelId = labelId;
    }

    public String getSecGoodsId() {
        return secGoodsId;
    }

    public void setSecGoodsId(String secGoodsId) {
        this.secGoodsId = secGoodsId;
    }

    public Integer getSecStatus() {
        return secStatus;
    }

    public void setSecStatus(Integer secStatus) {
        this.secStatus = secStatus;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
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
}
