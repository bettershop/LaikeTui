package com.laiketui.domain.vo.plugin;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑分销配置
 *
 * @author Trick
 * @date 2021/2/7 15:18
 */
@ApiModel(description = "添加/编辑分销等级")
public class AddDistributionConfigVo extends MainVo {
    @ApiModelProperty(name = "status", value = "是否开启插件")
    private Integer status;
    @ApiModelProperty(name = "cengji", value = "分销层级（0.不开启分销机制  1.一级分销 2.二级分销  )")
    private Integer cengji;
    @ApiModelProperty(name = "uplevel", value = "等级晋升设置（1.满足任意一项升级，2.满足所有项升级）")
    private Integer uplevel;
    @ApiModelProperty(name = "neigou", value = "分销内购（1.关闭  2.开启）")
    private Integer neigou;
    @ApiModelProperty(name = "pay", value = "规则统计方式（1.付款后  2.完成后")
    private Integer pay;
    @ApiModelProperty(name = "yjjisuan", value = "佣金计算方式（0.默认佣金计算方式订单成交价 1利润 2规格售价 3PV值）")
    private Integer yjjisuan;
    @ApiModelProperty(name = "advertising", value = "加盟广告（0不开启1开启）")
    private Integer advertising;
    @ApiModelProperty(name = "adimage", value = "广告图地址")
    private String adimage;
    @ApiModelProperty(name = "relationship", value = "绑定方式（0注册确定1消费确定）")
    private Integer relationship;

    @ApiModelProperty(name = "content", value = "分销规则")
    private String content;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCengji() {
        return cengji;
    }

    public void setCengji(Integer cengji) {
        this.cengji = cengji;
    }

    public Integer getUplevel() {
        return uplevel;
    }

    public void setUplevel(Integer uplevel) {
        this.uplevel = uplevel;
    }

    public Integer getNeigou() {
        return neigou;
    }

    public void setNeigou(Integer neigou) {
        this.neigou = neigou;
    }

    public Integer getPay() {
        return pay;
    }

    public void setPay(Integer pay) {
        this.pay = pay;
    }

    public Integer getYjjisuan() {
        return yjjisuan;
    }

    public void setYjjisuan(Integer yjjisuan) {
        this.yjjisuan = yjjisuan;
    }

    public Integer getAdvertising() {
        return advertising;
    }

    public void setAdvertising(Integer advertising) {
        this.advertising = advertising;
    }

    public String getAdimage() {
        return adimage;
    }

    public void setAdimage(String adimage) {
        this.adimage = adimage;
    }

    public Integer getRelationship() {
        return relationship;
    }

    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
