package com.laiketui.domain.home;

import javax.persistence.*;
import java.util.Date;


/**
 * 第三方授权表
 *
 * @author Trick
 * @date 2021/1/20 14:20
 */
@Table(name = "lkt_third")
public class ThirdModel {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 获取token的凭证
     */
    private String ticket;

    /**
     * 凭证更新时间
     */
    private Date ticket_time;

    /**
     * 授权token
     */
    private String token;

    /**
     * token过期时间戳
     */
    private Long token_expires;

    /**
     * 第三方平台appid
     */
    private String appid;

    /**
     * 第三方平台秘钥
     */
    private String appsecret;

    /**
     * 消息检验Token，第三方平台设置
     */
    private String check_token;

    /**
     * 消息加减密key
     */
    private String encrypt_key;

    /**
     * 服务器域名
     */
    private String serve_domain;

    /**
     * 业务域名
     */
    private String work_domain;

    /**
     * 授权回调地址
     */
    private String redirect_url;

    /**
     * 小程序接口地址
     */
    private String mini_url;

    /**
     * 客服接口url
     */
    private String kefu_url;

    /**
     * 体验二维码url
     */
    private String qr_code;

    /**
     * H5页面地址
     */
    @Column(name = "H5")
    private String h5;

    /**
     * 根目录路径
     */
    private String endurl;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取获取token的凭证
     *
     * @return ticket - 获取token的凭证
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 设置获取token的凭证
     *
     * @param ticket 获取token的凭证
     */
    public void setTicket(String ticket) {
        this.ticket = ticket == null ? null : ticket.trim();
    }

    /**
     * 获取凭证更新时间
     *
     * @return ticket_time - 凭证更新时间
     */
    public Date getTicket_time() {
        return ticket_time;
    }

    /**
     * 设置凭证更新时间
     *
     * @param ticket_time 凭证更新时间
     */
    public void setTicket_time(Date ticket_time) {
        this.ticket_time = ticket_time;
    }

    /**
     * 获取授权token
     *
     * @return token - 授权token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置授权token
     *
     * @param token 授权token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取token过期时间戳
     *
     * @return token_expires - token过期时间戳
     */
    public Long getToken_expires() {
        return token_expires;
    }

    /**
     * 设置token过期时间戳
     *
     * @param token_expires token过期时间戳
     */
    public void setToken_expires(Long token_expires) {
        this.token_expires = token_expires;
    }

    /**
     * 获取第三方平台appid
     *
     * @return appid - 第三方平台appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置第三方平台appid
     *
     * @param appid 第三方平台appid
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 获取第三方平台秘钥
     *
     * @return appsecret - 第三方平台秘钥
     */
    public String getAppsecret() {
        return appsecret;
    }

    /**
     * 设置第三方平台秘钥
     *
     * @param appsecret 第三方平台秘钥
     */
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret == null ? null : appsecret.trim();
    }

    /**
     * 获取消息检验Token，第三方平台设置
     *
     * @return check_token - 消息检验Token，第三方平台设置
     */
    public String getCheck_token() {
        return check_token;
    }

    /**
     * 设置消息检验Token，第三方平台设置
     *
     * @param check_token 消息检验Token，第三方平台设置
     */
    public void setCheck_token(String check_token) {
        this.check_token = check_token == null ? null : check_token.trim();
    }

    /**
     * 获取消息加减密key
     *
     * @return encrypt_key - 消息加减密key
     */
    public String getEncrypt_key() {
        return encrypt_key;
    }

    /**
     * 设置消息加减密key
     *
     * @param encrypt_key 消息加减密key
     */
    public void setEncrypt_key(String encrypt_key) {
        this.encrypt_key = encrypt_key == null ? null : encrypt_key.trim();
    }

    /**
     * 获取服务器域名
     *
     * @return serve_domain - 服务器域名
     */
    public String getServe_domain() {
        return serve_domain;
    }

    /**
     * 设置服务器域名
     *
     * @param serve_domain 服务器域名
     */
    public void setServe_domain(String serve_domain) {
        this.serve_domain = serve_domain == null ? null : serve_domain.trim();
    }

    /**
     * 获取业务域名
     *
     * @return work_domain - 业务域名
     */
    public String getWork_domain() {
        return work_domain;
    }

    /**
     * 设置业务域名
     *
     * @param work_domain 业务域名
     */
    public void setWork_domain(String work_domain) {
        this.work_domain = work_domain == null ? null : work_domain.trim();
    }

    /**
     * 获取授权回调地址
     *
     * @return redirect_url - 授权回调地址
     */
    public String getRedirect_url() {
        return redirect_url;
    }

    /**
     * 设置授权回调地址
     *
     * @param redirect_url 授权回调地址
     */
    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url == null ? null : redirect_url.trim();
    }

    /**
     * 获取小程序接口地址
     *
     * @return mini_url - 小程序接口地址
     */
    public String getMini_url() {
        return mini_url;
    }

    /**
     * 设置小程序接口地址
     *
     * @param mini_url 小程序接口地址
     */
    public void setMini_url(String mini_url) {
        this.mini_url = mini_url == null ? null : mini_url.trim();
    }

    /**
     * 获取客服接口url
     *
     * @return kefu_url - 客服接口url
     */
    public String getKefu_url() {
        return kefu_url;
    }

    /**
     * 设置客服接口url
     *
     * @param kefu_url 客服接口url
     */
    public void setKefu_url(String kefu_url) {
        this.kefu_url = kefu_url == null ? null : kefu_url.trim();
    }

    /**
     * 获取体验二维码url
     *
     * @return qr_code - 体验二维码url
     */
    public String getQr_code() {
        return qr_code;
    }

    /**
     * 设置体验二维码url
     *
     * @param qr_code 体验二维码url
     */
    public void setQr_code(String qr_code) {
        this.qr_code = qr_code == null ? null : qr_code.trim();
    }

    /**
     * 获取H5页面地址
     *
     * @return H5 - H5页面地址
     */
    public String getH5() {
        return h5;
    }

    /**
     * 设置H5页面地址
     *
     * @param h5 H5页面地址
     */
    public void setH5(String h5) {
        this.h5 = h5 == null ? null : h5.trim();
    }

    /**
     * 获取根目录路径
     *
     * @return endurl - 根目录路径
     */
    public String getEndurl() {
        return endurl;
    }

    /**
     * 设置根目录路径
     *
     * @param endurl 根目录路径
     */
    public void setEndurl(String endurl) {
        this.endurl = endurl == null ? null : endurl.trim();
    }
}