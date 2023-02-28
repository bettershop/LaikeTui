package com.laiketui.domain.vo.user;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/修改用户配置信息参数
 *
 * @author Trick
 * @date 2021/1/8 18:25
 */
@ApiModel(description = "添加/修改用户配置信息参数")
public class AddUserRuleVo extends MainVo {

    @ApiModelProperty(value = "微信头像", name = "wxImgUrl")
    private String wxImgUrl;
    @ApiModelProperty(value = "微信名称", name = "wxName")
    private String wxName;
    @ApiModelProperty(value = "是否自动续费", name = "isAutoSwitch")
    private int isAutoSwitch;
    @ApiModelProperty(value = "到期前提示", name = "autoDayExpire")
    private int autoDayExpire;
    @ApiModelProperty(value = "开通方式", name = "method")
    private String method;
    @ApiModelProperty(value = "可支持活动", name = "active")
    private String active;
    @ApiModelProperty(value = "是否开启余额支付", name = "isWallet")
    private int isWallet;
    @ApiModelProperty(value = "规则详情", name = "rule")
    private String rule;
    @ApiModelProperty(value = "晋升方式", name = "upgrade")
    private String upgrade;
    @ApiModelProperty(value = "生日特权开关", name = "isBirthday")
    private int isBirthday;
    @ApiModelProperty(value = "积分倍数", name = "birMultiple")
    private int birMultiple;
    @ApiModelProperty(value = "是否开启商品赠送", name = "isProduct")
    private int isProduct;
    @ApiModelProperty(value = "积分比例开关", name = "isJifen")
    private int isJifen;
    @ApiModelProperty(value = "积分发送规则 0-付款后 1-收货后", name = "jifenM")
    private int jifenM;
    @ApiModelProperty(value = "是否开启返现", name = "back")
    private int back;
    @ApiModelProperty(value = "返现比例", name = "backScale")
    private BigDecimal backScale;
    @ApiModelProperty(value = "会员海报", name = "poster")
    private String poster;

    @ApiModelProperty(value = "是否开启推荐限制", name = "isLimit")
    private int isLimit;
    @ApiModelProperty(value = "可以推荐的会员级别", name = "level")
    private int level;
    @ApiModelProperty(value = "可以参与分销的会员级别id", name = "distributeL")
    private int distributeL;
    @ApiModelProperty(value = "兑换商品有效期", name = "valid")
    private int valid;
    @ApiModelProperty(value = "积分比例过期", name = "score")
    private int score;
    @ApiModelProperty(value = "是否开启赠送积分", name = "bonusPointsShopping")
    private int bonusPointsShopping;
    @ApiModelProperty(value = "积分比例", name = "proportionOfGifts")
    private int proportionOfGifts;
    @ApiModelProperty(value = "发放时间 1.收货后  2.付款后", name = "releaseTime")
    private int releaseTime;

    public String getWxImgUrl() {
        return wxImgUrl;
    }

    public void setWxImgUrl(String wxImgUrl) {
        this.wxImgUrl = wxImgUrl;
    }

    public String getWxName() {
        return wxName;
    }

    public void setWxName(String wxName) {
        this.wxName = wxName;
    }

    public int getIsAutoSwitch() {
        return isAutoSwitch;
    }

    public void setIsAutoSwitch(int isAutoSwitch) {
        this.isAutoSwitch = isAutoSwitch;
    }

    public int getAutoDayExpire() {
        return autoDayExpire;
    }

    public void setAutoDayExpire(int autoDayExpire) {
        this.autoDayExpire = autoDayExpire;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getIsWallet() {
        return isWallet;
    }

    public void setIsWallet(int isWallet) {
        this.isWallet = isWallet;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getUpgrade() {
        return upgrade;
    }

    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade;
    }

    public int getIsBirthday() {
        return isBirthday;
    }

    public void setIsBirthday(int isBirthday) {
        this.isBirthday = isBirthday;
    }

    public int getBirMultiple() {
        return birMultiple;
    }

    public void setBirMultiple(int birMultiple) {
        this.birMultiple = birMultiple;
    }

    public int getIsProduct() {
        return isProduct;
    }

    public void setIsProduct(int isProduct) {
        this.isProduct = isProduct;
    }

    public int getIsJifen() {
        return isJifen;
    }

    public void setIsJifen(int isJifen) {
        this.isJifen = isJifen;
    }

    public int getJifenM() {
        return jifenM;
    }

    public void setJifenM(int jifenM) {
        this.jifenM = jifenM;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public BigDecimal getBackScale() {
        return backScale;
    }

    public void setBackScale(BigDecimal backScale) {
        this.backScale = backScale;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public int getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(int isLimit) {
        this.isLimit = isLimit;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDistributeL() {
        return distributeL;
    }

    public void setDistributeL(int distributeL) {
        this.distributeL = distributeL;
    }

    public int getValid() {
        return valid;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBonusPointsShopping() {
        return bonusPointsShopping;
    }

    public void setBonusPointsShopping(int bonusPointsShopping) {
        this.bonusPointsShopping = bonusPointsShopping;
    }

    public int getProportionOfGifts() {
        return proportionOfGifts;
    }

    public void setProportionOfGifts(int proportionOfGifts) {
        this.proportionOfGifts = proportionOfGifts;
    }

    public int getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(int releaseTime) {
        this.releaseTime = releaseTime;
    }
}
