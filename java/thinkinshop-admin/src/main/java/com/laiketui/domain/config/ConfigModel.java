package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_config")
public class ConfigModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 是否需要注册 1.注册 2.免注册
     */
    private String is_register;

    /**
     * 公司logo
     */
    private String logo;

    /**
     * 首页logo
     */
    private String logo1;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 小程序id
     */
    private String appid;

    /**
     * 小程序密钥
     */
    private String appsecret;

    /**
     * 商城根目录域名
     */
    private String domain;

    /**
     * APP域名
     */
    private String app_domain_name;

    /**
     * H5域名
     */
    @Column(name = "H5_domain")
    private String h5_domain;

    /**
     * 商户id
     */
    private Integer mch_id;

    /**
     * ip地址
     */
    private String ip;

    /**
     * 图片上传域名
     */
    @Column(name = "uploadImg_domain")
    private String uploadImg_domain;

    /**
     * 上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     */
    private String upserver;

    /**
     * 图片上传位置
     */
    @Column(name = "uploadImg")
    private String uploadImg;

    /**
     * 文件上传位置
     */
    private String upload_file;

    /**
     * 修改时间
     */
    private Date modify_date;

    /**
     * 微信支付key
     */
    private String mch_key;

    /**
     * 支付证书文件地址
     */
    private String mch_cert;

    /**
     * 用户ID默认前缀
     */
    private String user_id;

    /**
     * 微信默认用户名称
     */
    private String wx_name;

    /**
     * 微信默认用户头像
     */
    private String wx_headimgurl;

    /**
     * 客服
     */
    private String customer_service;

    /**
     * 用户协议
     */
    private String agreement;

    /**
     * 关于我们
     */
    private String aboutus;

    /**
     * 消息保留天数
     */
    private Integer message_day;

    /**
     * 腾讯位置服务开发密钥
     */
    private String tencent_key;

    /**
     * 售后问题
     */
    private String after_sales_issues;

    /**
     * 支付问题
     */
    private String payment_issues;

    /**
     * 购物流程
     */
    private String shopping_process;

    /**
     * 付款方式
     */
    private String payment_method;

    /**
     * 退货政策
     */
    private String return_policy;

    /**
     * 取消订单
     */
    private String cancellation_order;

    /**
     * 退款流程
     */
    private String refund_process;

    /**
     * 退款说明
     */
    private String refund_instructions;

    /**
     * 移动端登录有效期
     */
    private Integer exp_time;
    /**
     * 是否推送 0.不推送 1.推送
     */
    private Integer is_push;
    /**
     * 推送Appkey
     */
    @Column(name = "push_Appkey")
    private String push_Appkey;
    /**
     * 推送Appid
     */
    @Column(name = "push_Appkey")
    private String push_Appid;
    /**
     * 推送秘钥
     */
    @Column(name = "push_MasterECRET")
    private String push_MasterECRET;
    /**
     * 是否开启快递100  0.不开启 1.开启
     */
    private Integer is_express;
    /**
     * 查询接口地址
     */
    private String express_address;
    /**
     * 用户编号
     */
    private String express_number;
    /**
     * 接口调用key
     */
    private String express_key;
    /**
     * 是否登录踢人  0.不开启 1.开启
     */
    @Column(name = "is_Kicking")
    private Integer is_Kicking;
    /**
     * 是否隐藏钱包 0.不隐藏 1.隐藏
     */
    private Integer Hide_your_wallet;

    public Integer getExp_time() {
        return exp_time;
    }

    public void setExp_time(Integer exp_time) {
        this.exp_time = exp_time;
    }

    public Integer getIs_push() {
        return is_push;
    }

    public void setIs_push(Integer is_push) {
        this.is_push = is_push;
    }

    public String getPush_Appkey() {
        return push_Appkey;
    }

    public void setPush_Appkey(String push_Appkey) {
        this.push_Appkey = push_Appkey;
    }

    public String getPush_Appid() {
        return push_Appid;
    }

    public void setPush_Appid(String push_Appid) {
        this.push_Appid = push_Appid;
    }

    public String getPush_MasterECRET() {
        return push_MasterECRET;
    }

    public void setPush_MasterECRET(String push_MasterECRET) {
        this.push_MasterECRET = push_MasterECRET;
    }

    public Integer getIs_express() {
        return is_express;
    }

    public void setIs_express(Integer is_express) {
        this.is_express = is_express;
    }

    public String getExpress_address() {
        return express_address;
    }

    public void setExpress_address(String express_address) {
        this.express_address = express_address;
    }

    public String getExpress_number() {
        return express_number;
    }

    public void setExpress_number(String express_number) {
        this.express_number = express_number;
    }

    public String getExpress_key() {
        return express_key;
    }

    public void setExpress_key(String express_key) {
        this.express_key = express_key;
    }

    public Integer getIs_Kicking() {
        return is_Kicking;
    }

    public void setIs_Kicking(Integer is_Kicking) {
        this.is_Kicking = is_Kicking;
    }

    public Integer getHide_your_wallet() {
        return Hide_your_wallet;
    }

    public void setHide_your_wallet(Integer hide_your_wallet) {
        Hide_your_wallet = hide_your_wallet;
    }

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城id
     *
     * @return store_id - 商城id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城id
     *
     * @param store_id 商城id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取是否需要注册 1.注册 2.免注册
     *
     * @return is_register - 是否需要注册 1.注册 2.免注册
     */
    public String getIs_register() {
        return is_register;
    }

    /**
     * 设置是否需要注册 1.注册 2.免注册
     *
     * @param is_register 是否需要注册 1.注册 2.免注册
     */
    public void setIs_register(String is_register) {
        this.is_register = is_register;
    }

    /**
     * 获取公司logo
     *
     * @return logo - 公司logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置公司logo
     *
     * @param logo 公司logo
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 获取首页logo
     *
     * @return logo1 - 首页logo
     */
    public String getLogo1() {
        return logo1;
    }

    /**
     * 设置首页logo
     *
     * @param logo1 首页logo
     */
    public void setLogo1(String logo1) {
        this.logo1 = logo1 == null ? null : logo1.trim();
    }

    /**
     * 获取公司名称
     *
     * @return company - 公司名称
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置公司名称
     *
     * @param company 公司名称
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * 获取小程序id
     *
     * @return appid - 小程序id
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置小程序id
     *
     * @param appid 小程序id
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 获取小程序密钥
     *
     * @return appsecret - 小程序密钥
     */
    public String getAppsecret() {
        return appsecret;
    }

    /**
     * 设置小程序密钥
     *
     * @param appsecret 小程序密钥
     */
    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret == null ? null : appsecret.trim();
    }

    /**
     * 获取商城根目录域名
     *
     * @return domain - 商城根目录域名
     */
    public String getDomain() {
        return domain;
    }

    /**
     * 设置商城根目录域名
     *
     * @param domain 商城根目录域名
     */
    public void setDomain(String domain) {
        this.domain = domain == null ? null : domain.trim();
    }

    /**
     * 获取APP域名
     *
     * @return app_domain_name - APP域名
     */
    public String getApp_domain_name() {
        return app_domain_name;
    }

    /**
     * 设置APP域名
     *
     * @param app_domain_name APP域名
     */
    public void setApp_domain_name(String app_domain_name) {
        this.app_domain_name = app_domain_name == null ? null : app_domain_name.trim();
    }

    /**
     * 获取H5域名
     *
     * @return H5_domain - H5域名
     */
    public String getH5_domain() {
        return h5_domain;
    }

    /**
     * 设置H5域名
     *
     * @param h5_domain H5域名
     */
    public void setH5_domain(String h5_domain) {
        this.h5_domain = h5_domain == null ? null : h5_domain.trim();
    }

    /**
     * 获取商户id
     *
     * @return mch_id - 商户id
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置商户id
     *
     * @param mch_id 商户id
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取ip地址
     *
     * @return ip - ip地址
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置ip地址
     *
     * @param ip ip地址
     */
    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    /**
     * 获取图片上传域名
     *
     * @return uploadImg_domain - 图片上传域名
     */
    public String getUploadImg_domain() {
        return uploadImg_domain;
    }

    /**
     * 设置图片上传域名
     *
     * @param uploadImg_domain 图片上传域名
     */
    public void setUploadImg_domain(String uploadImg_domain) {
        this.uploadImg_domain = uploadImg_domain == null ? null : uploadImg_domain.trim();
    }

    /**
     * 获取上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     *
     * @return upserver - 上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     */
    public String getUpserver() {
        return upserver;
    }

    /**
     * 设置上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     *
     * @param upserver 上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     */
    public void setUpserver(String upserver) {
        this.upserver = upserver;
    }

    /**
     * 获取图片上传位置
     *
     * @return uploadImg - 图片上传位置
     */
    public String getUploadImg() {
        return uploadImg;
    }

    /**
     * 设置图片上传位置
     *
     * @param uploadImg 图片上传位置
     */
    public void setUploadImg(String uploadImg) {
        this.uploadImg = uploadImg == null ? null : uploadImg.trim();
    }

    /**
     * 获取文件上传位置
     *
     * @return upload_file - 文件上传位置
     */
    public String getUpload_file() {
        return upload_file;
    }

    /**
     * 设置文件上传位置
     *
     * @param upload_file 文件上传位置
     */
    public void setUpload_file(String upload_file) {
        this.upload_file = upload_file == null ? null : upload_file.trim();
    }

    /**
     * 获取修改时间
     *
     * @return modify_date - 修改时间
     */
    public Date getModify_date() {
        return modify_date;
    }

    /**
     * 设置修改时间
     *
     * @param modify_date 修改时间
     */
    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

    /**
     * 获取微信支付key
     *
     * @return mch_key - 微信支付key
     */
    public String getMch_key() {
        return mch_key;
    }

    /**
     * 设置微信支付key
     *
     * @param mch_key 微信支付key
     */
    public void setMch_key(String mch_key) {
        this.mch_key = mch_key == null ? null : mch_key.trim();
    }

    /**
     * 获取支付证书文件地址
     *
     * @return mch_cert - 支付证书文件地址
     */
    public String getMch_cert() {
        return mch_cert;
    }

    /**
     * 设置支付证书文件地址
     *
     * @param mch_cert 支付证书文件地址
     */
    public void setMch_cert(String mch_cert) {
        this.mch_cert = mch_cert == null ? null : mch_cert.trim();
    }

    /**
     * 获取用户ID默认前缀
     *
     * @return user_id - 用户ID默认前缀
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID默认前缀
     *
     * @param user_id 用户ID默认前缀
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取微信默认用户名称
     *
     * @return wx_name - 微信默认用户名称
     */
    public String getWx_name() {
        return wx_name;
    }

    /**
     * 设置微信默认用户名称
     *
     * @param wx_name 微信默认用户名称
     */
    public void setWx_name(String wx_name) {
        this.wx_name = wx_name == null ? null : wx_name.trim();
    }

    /**
     * 获取微信默认用户头像
     *
     * @return wx_headimgurl - 微信默认用户头像
     */
    public String getWx_headimgurl() {
        return wx_headimgurl;
    }

    /**
     * 设置微信默认用户头像
     *
     * @param wx_headimgurl 微信默认用户头像
     */
    public void setWx_headimgurl(String wx_headimgurl) {
        this.wx_headimgurl = wx_headimgurl == null ? null : wx_headimgurl.trim();
    }

    /**
     * 获取客服
     *
     * @return customer_service - 客服
     */
    public String getCustomer_service() {
        return customer_service;
    }

    /**
     * 设置客服
     *
     * @param customer_service 客服
     */
    public void setCustomer_service(String customer_service) {
        this.customer_service = customer_service == null ? null : customer_service.trim();
    }

    /**
     * 获取用户协议
     *
     * @return agreement - 用户协议
     */
    public String getAgreement() {
        return agreement;
    }

    /**
     * 设置用户协议
     *
     * @param agreement 用户协议
     */
    public void setAgreement(String agreement) {
        this.agreement = agreement == null ? null : agreement.trim();
    }

    /**
     * 获取关于我们
     *
     * @return aboutus - 关于我们
     */
    public String getAboutus() {
        return aboutus;
    }

    /**
     * 设置关于我们
     *
     * @param aboutus 关于我们
     */
    public void setAboutus(String aboutus) {
        this.aboutus = aboutus == null ? null : aboutus.trim();
    }

    /**
     * 获取消息保留天数
     *
     * @return message_day - 消息保留天数
     */
    public Integer getMessage_day() {
        return message_day;
    }

    /**
     * 设置消息保留天数
     *
     * @param message_day 消息保留天数
     */
    public void setMessage_day(Integer message_day) {
        this.message_day = message_day;
    }

    /**
     * 获取腾讯位置服务开发密钥
     *
     * @return tencent_key - 腾讯位置服务开发密钥
     */
    public String getTencent_key() {
        return tencent_key;
    }

    /**
     * 设置腾讯位置服务开发密钥
     *
     * @param tencent_key 腾讯位置服务开发密钥
     */
    public void setTencent_key(String tencent_key) {
        this.tencent_key = tencent_key == null ? null : tencent_key.trim();
    }

    /**
     * 获取售后问题
     *
     * @return after_sales_issues - 售后问题
     */
    public String getAfter_sales_issues() {
        return after_sales_issues;
    }

    /**
     * 设置售后问题
     *
     * @param after_sales_issues 售后问题
     */
    public void setAfter_sales_issues(String after_sales_issues) {
        this.after_sales_issues = after_sales_issues == null ? null : after_sales_issues.trim();
    }

    /**
     * 获取支付问题
     *
     * @return payment_issues - 支付问题
     */
    public String getPayment_issues() {
        return payment_issues;
    }

    /**
     * 设置支付问题
     *
     * @param payment_issues 支付问题
     */
    public void setPayment_issues(String payment_issues) {
        this.payment_issues = payment_issues == null ? null : payment_issues.trim();
    }

    /**
     * 获取购物流程
     *
     * @return shopping_process - 购物流程
     */
    public String getShopping_process() {
        return shopping_process;
    }

    /**
     * 设置购物流程
     *
     * @param shopping_process 购物流程
     */
    public void setShopping_process(String shopping_process) {
        this.shopping_process = shopping_process == null ? null : shopping_process.trim();
    }

    /**
     * 获取付款方式
     *
     * @return payment_method - 付款方式
     */
    public String getPayment_method() {
        return payment_method;
    }

    /**
     * 设置付款方式
     *
     * @param payment_method 付款方式
     */
    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method == null ? null : payment_method.trim();
    }

    /**
     * 获取退货政策
     *
     * @return return_policy - 退货政策
     */
    public String getReturn_policy() {
        return return_policy;
    }

    /**
     * 设置退货政策
     *
     * @param return_policy 退货政策
     */
    public void setReturn_policy(String return_policy) {
        this.return_policy = return_policy == null ? null : return_policy.trim();
    }

    /**
     * 获取取消订单
     *
     * @return cancellation_order - 取消订单
     */
    public String getCancellation_order() {
        return cancellation_order;
    }

    /**
     * 设置取消订单
     *
     * @param cancellation_order 取消订单
     */
    public void setCancellation_order(String cancellation_order) {
        this.cancellation_order = cancellation_order == null ? null : cancellation_order.trim();
    }

    /**
     * 获取退款流程
     *
     * @return refund_process - 退款流程
     */
    public String getRefund_process() {
        return refund_process;
    }

    /**
     * 设置退款流程
     *
     * @param refund_process 退款流程
     */
    public void setRefund_process(String refund_process) {
        this.refund_process = refund_process == null ? null : refund_process.trim();
    }

    /**
     * 获取退款说明
     *
     * @return refund_instructions - 退款说明
     */
    public String getRefund_instructions() {
        return refund_instructions;
    }

    /**
     * 设置退款说明
     *
     * @param refund_instructions 退款说明
     */
    public void setRefund_instructions(String refund_instructions) {
        this.refund_instructions = refund_instructions == null ? null : refund_instructions.trim();
    }
}