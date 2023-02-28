package com.laiketui.domain.vo.weixin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑第三方授权配置参数
 *
 * @author Trick
 * @date 2021/2/4 9:21
 */
@ApiModel(description = "添加/编辑第三方授权配置参数")
public class AddThridVo {
    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "appid", name = "appid")
    private String appid;
    @ApiModelProperty(value = "密钥", name = "appsecret")
    private String appsecret;
    @ApiModelProperty(value = "校验token", name = "checkToken")
    private String checkToken;
    @ApiModelProperty(value = "消息加解密key", name = "encryptKey")
    private String encryptKey;
    @ApiModelProperty(value = "服务器域名", name = "serveDomain")
    private String serveDomain;
    @ApiModelProperty(value = "业务域名", name = "workDomain")
    private String workDomain;
    @ApiModelProperty(value = "授权回调地址", name = "redirectUrl")
    private String redirectUrl;
    @ApiModelProperty(value = "小程序请求地址", name = "miniUrl")
    private String miniUrl;
    @ApiModelProperty(value = "客服请求地址", name = "kefuUrl")
    private String kefuUrl;
    @ApiModelProperty(value = "体验二维码地址", name = "qrCode")
    private String qrCode;
    @ApiModelProperty(value = "根目录路径", name = "endurl")
    private String endurl;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getCheckToken() {
        return checkToken;
    }

    public void setCheckToken(String checkToken) {
        this.checkToken = checkToken;
    }

    public String getEncryptKey() {
        return encryptKey;
    }

    public void setEncryptKey(String encryptKey) {
        this.encryptKey = encryptKey;
    }

    public String getServeDomain() {
        return serveDomain;
    }

    public void setServeDomain(String serveDomain) {
        this.serveDomain = serveDomain;
    }

    public String getWorkDomain() {
        return workDomain;
    }

    public void setWorkDomain(String workDomain) {
        this.workDomain = workDomain;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getMiniUrl() {
        return miniUrl;
    }

    public void setMiniUrl(String miniUrl) {
        this.miniUrl = miniUrl;
    }

    public String getKefuUrl() {
        return kefuUrl;
    }

    public void setKefuUrl(String kefuUrl) {
        this.kefuUrl = kefuUrl;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getEndurl() {
        return endurl;
    }

    public void setEndurl(String endurl) {
        this.endurl = endurl;
    }
}
