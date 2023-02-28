package com.laiketui.domain.vo.plugin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑分销商品
 *
 * @author Trick
 * @date 2021/2/8 10:23
 */
@ApiModel(description = "添加/编辑分销等级")
public class AddDistributionGoodsVo extends MainVo {
    @ApiModelProperty(name = "id", value = "分销商品id")
    private Integer id;

    @ApiModelProperty(name = "sid", value = "商品规格id")
    private Integer sid;
    @ApiModelProperty(name = "uplevel", value = "晋升等级id")
    private Integer uplevel = 0;
    @ApiModelProperty(name = "distributionRule", value = "分佣规则设置 1等级2自定义")
    private Integer distributionRule;
    @ApiModelProperty(name = "directType", value = "直推分销比例发放模式 0.百分比 1.固定金额")
    private Integer directType;
    @ApiModelProperty(name = "directM", value = "直推分佣金额")
    private BigDecimal directM;
    @ApiModelProperty(name = "indirectType", value = "间推分销比例发放模式 0.百分比 1.固定金额")
    private Integer indirectType;
    @ApiModelProperty(name = "indirectM", value = "间推分佣金额")
    private BigDecimal indirectM;
    @ApiModelProperty(name = "pv", value = "pv值")
    private BigDecimal pv;

    public BigDecimal getPv() {
        return pv;
    }

    public void setPv(BigDecimal pv) {
        this.pv = pv;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public Integer getUplevel() {
        return uplevel;
    }

    public void setUplevel(Integer uplevel) {
        this.uplevel = uplevel;
    }

    public Integer getDistributionRule() {
        return distributionRule;
    }

    public void setDistributionRule(Integer distributionRule) {
        this.distributionRule = distributionRule;
    }

    public Integer getDirectType() {
        return directType;
    }

    public void setDirectType(Integer directType) {
        this.directType = directType;
    }

    public BigDecimal getDirectM() {
        return directM;
    }

    public void setDirectM(BigDecimal directM) {
        this.directM = directM;
    }

    public Integer getIndirectType() {
        return indirectType;
    }

    public void setIndirectType(Integer indirectType) {
        this.indirectType = indirectType;
    }

    public BigDecimal getIndirectM() {
        return indirectM;
    }

    public void setIndirectM(BigDecimal indirectM) {
        this.indirectM = indirectM;
    }
}
