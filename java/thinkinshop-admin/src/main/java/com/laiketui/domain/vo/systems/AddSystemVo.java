package com.laiketui.domain.vo.systems;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改系统基础配置
 *
 * @author Trick
 * @date 2021/1/19 9:16
 */
@ApiModel(description = "添加/修改系统基础配置参数")
public class AddSystemVo extends MainVo {

    @ApiModelProperty(value = "isRegister", name = "名称 1.注册 2.免注册")
    private Integer isRegister;
    @ApiModelProperty(value = "logoUrl", name = "公司logo")
    private String logoUrl;
    @ApiModelProperty(value = "wxHeader", name = "微信默认头像")
    private String wxHeader;
    @ApiModelProperty(value = "pageDomain", name = "h5页面地址")
    private String pageDomain;
    @ApiModelProperty(value = "messageSaveDay", name = "前端消息保留天数,为空则不删除")
    private Integer messageSaveDay;
    @ApiModelProperty(value = "appLoginValid", name = "移动端登录有效期,小时")
    private Integer appLoginValid;
    @ApiModelProperty(value = "serverClient", name = "客服脚本")
    private String serverClient;
    @ApiModelProperty(value = "tencentKey", name = "客服")
    private String tencentKey;
    @ApiModelProperty(value = "unipush", name = "是否开启unipush推送 0.不推送 1.推送")
    private Integer unipush;
    @ApiModelProperty(value = "pushAppkey", name = "推送Appkey")
    private String pushAppkey;
    @ApiModelProperty(value = "pushAppid", name = "推送Appid")
    private String pushAppid;
    @ApiModelProperty(value = "pushMasterEcret", name = "推送秘钥")
    private String pushMasterEcret;
    @ApiModelProperty(value = "isExpress", name = "是否开启快递100 0.不开启 1.开启")
    private Integer isExpress;
    @ApiModelProperty(value = "expressAddress", name = "查询接口地址")
    private String expressAddress;
    @ApiModelProperty(value = "expressNumber", name = "用户编号")
    private String expressNumber;
    @ApiModelProperty(value = "expressKey", name = "接口调用key")
    private String expressKey;
    @ApiModelProperty(value = "isKicking", name = "是否登录踢人  0.不开启 1.开启")
    private Integer isKicking;
    @ApiModelProperty(value = "hideYourWallet", name = "是否隐藏钱包 0.不隐藏 1.隐藏")
    private Integer hideYourWallet;

    public String getTencentKey() {
        return tencentKey;
    }

    public void setTencentKey(String tencentKey) {
        this.tencentKey = tencentKey;
    }

    public Integer getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Integer isRegister) {
        this.isRegister = isRegister;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getWxHeader() {
        return wxHeader;
    }

    public void setWxHeader(String wxHeader) {
        this.wxHeader = wxHeader;
    }

    public String getPageDomain() {
        return pageDomain;
    }

    public void setPageDomain(String pageDomain) {
        this.pageDomain = pageDomain;
    }

    public Integer getMessageSaveDay() {
        return messageSaveDay;
    }

    public void setMessageSaveDay(Integer messageSaveDay) {
        this.messageSaveDay = messageSaveDay;
    }

    public Integer getAppLoginValid() {
        return appLoginValid;
    }

    public void setAppLoginValid(Integer appLoginValid) {
        this.appLoginValid = appLoginValid;
    }

    public String getServerClient() {
        return serverClient;
    }

    public void setServerClient(String serverClient) {
        this.serverClient = serverClient;
    }

    public Integer getUnipush() {
        return unipush;
    }

    public void setUnipush(Integer unipush) {
        this.unipush = unipush;
    }

    public String getPushAppkey() {
        return pushAppkey;
    }

    public void setPushAppkey(String pushAppkey) {
        this.pushAppkey = pushAppkey;
    }

    public String getPushAppid() {
        return pushAppid;
    }

    public void setPushAppid(String pushAppid) {
        this.pushAppid = pushAppid;
    }

    public String getPushMasterEcret() {
        return pushMasterEcret;
    }

    public void setPushMasterEcret(String pushMasterEcret) {
        this.pushMasterEcret = pushMasterEcret;
    }

    public Integer getIsExpress() {
        return isExpress;
    }

    public void setIsExpress(Integer isExpress) {
        this.isExpress = isExpress;
    }

    public String getExpressAddress() {
        return expressAddress;
    }

    public void setExpressAddress(String expressAddress) {
        this.expressAddress = expressAddress;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressKey() {
        return expressKey;
    }

    public void setExpressKey(String expressKey) {
        this.expressKey = expressKey;
    }

    public Integer getIsKicking() {
        return isKicking;
    }

    public void setIsKicking(Integer isKicking) {
        this.isKicking = isKicking;
    }

    public Integer getHideYourWallet() {
        return hideYourWallet;
    }

    public void setHideYourWallet(Integer hideYourWallet) {
        this.hideYourWallet = hideYourWallet;
    }
}
