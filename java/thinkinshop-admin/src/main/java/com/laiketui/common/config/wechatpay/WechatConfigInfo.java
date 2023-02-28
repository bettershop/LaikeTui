package com.laiketui.common.config.wechatpay;


import com.laiketui.libs.wechatpay.IWXPayDomain;
import com.laiketui.libs.wechatpay.WXPayConfig;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @author wangxian
 */
public class WechatConfigInfo extends WXPayConfig {

    private byte[] certData;
    /**微信公众号appid*/
    private String appID;
    /**公众号密钥*/
    private String appSecreKey;
    /**微信商户号*/
    private String mchID;
    /**密钥*/
    private String key;
    /**证书路径*/
    private String certPath;
    /**链接超时*/
    private int connTimeout=80000;
    /**读取超时*/
    private int readTimeoutMs = 100000;
    /**支付回调通知地址*/
    private String notifyUrl;

    public String getAppSecreKey() {
        return appSecreKey;
    }

    public void setAppSecreKey(String appSecreKey) {
        this.appSecreKey = appSecreKey;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public WechatConfigInfo(String appID, String mchID, String key, String certPath, int connTimeout, int readTimeoutMs) throws Exception {

        setAppID(appID);
        setCertPath(certPath);
        setConnTimeout(connTimeout);
        setReadTimeoutMs(readTimeoutMs);
        setKey(key);
        setMchID(mchID);

        File file = new File(getCertPath());
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public WechatConfigInfo(String appID, String mchID, String key, String certPath, String notifyUrl, String appSecreKey) throws Exception {
        setAppID(appID);
        setCertPath(certPath);
        setConnTimeout(connTimeout);
        setReadTimeoutMs(readTimeoutMs);
        setKey(key);
        setMchID(mchID);
        setNotifyUrl(notifyUrl);
        setAppSecreKey(appSecreKey);

        File file = new File(getCertPath());
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public void setReadTimeoutMs(int readTimeoutMs) {
        this.readTimeoutMs = readTimeoutMs;
    }


    @Override
    public String getAppID() {
        return this.appID;
    }

    @Override
    public String getMchID() {
        return this.mchID;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public InputStream getCertStream() {
        return new ByteArrayInputStream(this.certData);
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    @Override
    protected IWXPayDomain getWXPayDomain() {
        return null;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCertPath(String certPath) {
        this.certPath = certPath;
    }

    public String getCertPath() {
        return certPath;
    }

    public void setAppID(String appID) {
        this.appID = appID;
    }

    public void setMchID(String mchID) {
        this.mchID = mchID;
    }

}
