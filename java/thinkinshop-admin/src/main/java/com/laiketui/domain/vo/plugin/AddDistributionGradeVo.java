package com.laiketui.domain.vo.plugin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑分销配置信息
 *
 * @author Trick
 * @date 2021/2/6 16:28
 */
@ApiModel(description = "添加/编辑分销配置信息")
public class AddDistributionGradeVo extends MainVo {
    @ApiModelProperty(name = "id", value = "id")
    private Integer id;

    @ApiModelProperty(name = "dengjiname", value = "等级名称")
    private String dengjiname;
    @ApiModelProperty(name = "zhekouIsOpen", value = "分销折扣开关")
    private Integer zhekouIsOpen;
    @ApiModelProperty(name = "discount", value = "分销折扣")
    private BigDecimal discount;
    @ApiModelProperty(name = "directType", value = "直推分销比例发放模式 0.百分比 1.固定金额")
    private Integer directType;
    @ApiModelProperty(name = "directM", value = "直推返佣")
    private BigDecimal directM;
    @ApiModelProperty(name = "indirectType", value = "间推分销比例发放模式 0.百分比 1.固定金额")
    private Integer indirectType;
    @ApiModelProperty(name = "indirectM", value = "间推返佣")
    private BigDecimal indirectM;
    @ApiModelProperty(name = "differentType", value = "级差奖比例发放模式 0.百分比 1.固定金额")
    private Integer differentType;
    @ApiModelProperty(name = "differentM", value = "级差返佣")
    private BigDecimal differentM;
    @ApiModelProperty(name = "siblingType", value = "平级奖比例发放模式 0.百分比 1.固定金额")
    private Integer siblingType;
    @ApiModelProperty(name = "siblingM", value = "同级返佣")
    private BigDecimal siblingM;
    @ApiModelProperty(name = "levelobj", value = "晋级条件")
    private String levelobj;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDengjiname() {
        return dengjiname;
    }

    public void setDengjiname(String dengjiname) {
        this.dengjiname = dengjiname;
    }

    public Integer getZhekouIsOpen() {
        return zhekouIsOpen;
    }

    public void setZhekouIsOpen(Integer zhekouIsOpen) {
        this.zhekouIsOpen = zhekouIsOpen;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
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

    public Integer getDifferentType() {
        return differentType;
    }

    public void setDifferentType(Integer differentType) {
        this.differentType = differentType;
    }

    public BigDecimal getDifferentM() {
        return differentM;
    }

    public void setDifferentM(BigDecimal differentM) {
        this.differentM = differentM;
    }

    public Integer getSiblingType() {
        return siblingType;
    }

    public void setSiblingType(Integer siblingType) {
        this.siblingType = siblingType;
    }

    public BigDecimal getSiblingM() {
        return siblingM;
    }

    public void setSiblingM(BigDecimal siblingM) {
        this.siblingM = siblingM;
    }

    public String getLevelobj() {
        return levelobj;
    }

    public void setLevelobj(String levelobj) {
        this.levelobj = levelobj;
    }
}
