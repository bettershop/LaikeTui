package com.laiketui.domain.weixin;

import javax.persistence.*;
import java.util.Date;


/**
 * 授权小程序信息表
 *
 * @author Trick
 * @date 2021/1/20 14:26
 */
@Table(name = "lkt_third_mini_info")
public class ThirdMiniInfoModel {

    /**
     * 未审核
     */
    @Transient
    public static final int THIRD_NOT_EXAMINE = 1;
    /**
     * 审核中
     */
    @Transient
    public static final int THIRD_EXAMINE_UNDER = 2;
    /**
     * 审核失败
     */
    @Transient
    public static final int THIRD_EXAMINE_FAIL = 3;
    /**
     * 审核成功
     */
    @Transient
    public static final int THIRD_EXAMINE_SUCCESS = 4;


    /**
     * 未发布
     */
    @Transient
    public static final int ISSUE_MARK_NOT = 1;
    /**
     * 发布失败
     */
    @Transient
    public static final int ISSUE_MARK_FAIL = 2;
    /**
     * 发布成功
     */
    @Transient
    public static final int ISSUE_MARK_SUCCESS = 3;

    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 授权方昵称
     */
    private String nick_name;

    /**
     * 授权小程序appid
     */
    private String authorizer_appid;

    /**
     * 授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌
     */
    private String authorizer_access_token;

    /**
     * 有效期（在授权的公众号或小程序具备API权限时，才有此返回值）
     */
    private Integer authorizer_expires;

    /**
     * 接口调用凭据刷新令牌
     */
    private String authorizer_refresh_token;

    /**
     * 更新时间
     */
    private Date update_time;

    /**
     * 授权给开发者的权限集列表
     */
    private String func_info;

    /**
     * 过期时间戳
     */
    private Integer expires_time;

    /**
     * 公司id
     */
    private String company_id;

    /**
     * 授权方头像
     */
    private String head_img;

    /**
     * 授权方认证类型，-1代表未认证，0代表微信认证
     */
    private String verify_type_info;

    /**
     * 小程序的原始ID
     */
    private String user_name;

    /**
     * 帐号介绍
     */
    private String signature;

    /**
     * 小程序的主体名称
     */
    private String principal_name;

    /**
     * 开通状况（0代表未开通，1代表已开通）
     */
    private String business_info;

    /**
     * 二维码图片的URL，开发者最好自行也进行保存
     */
    private String qrcode_url;

    /**
     * 根据这个字段判断是否为小程序类型授权
     */
    private String miniprograminfo;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 审核编号
     */
    private String auditid;

    /**
     * 审核状态 1：未审核 2：审核中 3：审核失败 4：审核成功
     */
    private Integer review_mark;

    /**
     * 发布状态 1：未发布  2：发布失败 3：发布成功
     */
    private Integer issue_mark;

    /**
     * 审核提价时间
     */
    private Date submit_time;

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
     * 获取授权方昵称
     *
     * @return nick_name - 授权方昵称
     */
    public String getNick_name() {
        return nick_name;
    }

    /**
     * 设置授权方昵称
     *
     * @param nick_name 授权方昵称
     */
    public void setNick_name(String nick_name) {
        this.nick_name = nick_name == null ? null : nick_name.trim();
    }

    /**
     * 获取授权小程序appid
     *
     * @return authorizer_appid - 授权小程序appid
     */
    public String getAuthorizer_appid() {
        return authorizer_appid;
    }

    /**
     * 设置授权小程序appid
     *
     * @param authorizer_appid 授权小程序appid
     */
    public void setAuthorizer_appid(String authorizer_appid) {
        this.authorizer_appid = authorizer_appid == null ? null : authorizer_appid.trim();
    }

    /**
     * 获取授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌
     *
     * @return authorizer_access_token - 授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌
     */
    public String getAuthorizer_access_token() {
        return authorizer_access_token;
    }

    /**
     * 设置授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌
     *
     * @param authorizer_access_token 授权方接口调用凭据（在授权的公众号或小程序具备API权限时，才有此返回值），也简称为令牌
     */
    public void setAuthorizer_access_token(String authorizer_access_token) {
        this.authorizer_access_token = authorizer_access_token == null ? null : authorizer_access_token.trim();
    }

    /**
     * 获取有效期（在授权的公众号或小程序具备API权限时，才有此返回值）
     *
     * @return authorizer_expires - 有效期（在授权的公众号或小程序具备API权限时，才有此返回值）
     */
    public Integer getAuthorizer_expires() {
        return authorizer_expires;
    }

    /**
     * 设置有效期（在授权的公众号或小程序具备API权限时，才有此返回值）
     *
     * @param authorizer_expires 有效期（在授权的公众号或小程序具备API权限时，才有此返回值）
     */
    public void setAuthorizer_expires(Integer authorizer_expires) {
        this.authorizer_expires = authorizer_expires;
    }

    /**
     * 获取接口调用凭据刷新令牌
     *
     * @return authorizer_refresh_token - 接口调用凭据刷新令牌
     */
    public String getAuthorizer_refresh_token() {
        return authorizer_refresh_token;
    }

    /**
     * 设置接口调用凭据刷新令牌
     *
     * @param authorizer_refresh_token 接口调用凭据刷新令牌
     */
    public void setAuthorizer_refresh_token(String authorizer_refresh_token) {
        this.authorizer_refresh_token = authorizer_refresh_token == null ? null : authorizer_refresh_token.trim();
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdate_time() {
        return update_time;
    }

    /**
     * 设置更新时间
     *
     * @param update_time 更新时间
     */
    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    /**
     * 获取授权给开发者的权限集列表
     *
     * @return func_info - 授权给开发者的权限集列表
     */
    public String getFunc_info() {
        return func_info;
    }

    /**
     * 设置授权给开发者的权限集列表
     *
     * @param func_info 授权给开发者的权限集列表
     */
    public void setFunc_info(String func_info) {
        this.func_info = func_info == null ? null : func_info.trim();
    }

    /**
     * 获取过期时间戳
     *
     * @return expires_time - 过期时间戳
     */
    public Integer getExpires_time() {
        return expires_time;
    }

    /**
     * 设置过期时间戳
     *
     * @param expires_time 过期时间戳
     */
    public void setExpires_time(Integer expires_time) {
        this.expires_time = expires_time;
    }

    /**
     * 获取公司id
     *
     * @return company_id - 公司id
     */
    public String getCompany_id() {
        return company_id;
    }

    /**
     * 设置公司id
     *
     * @param company_id 公司id
     */
    public void setCompany_id(String company_id) {
        this.company_id = company_id == null ? null : company_id.trim();
    }

    /**
     * 获取授权方头像
     *
     * @return head_img - 授权方头像
     */
    public String getHead_img() {
        return head_img;
    }

    /**
     * 设置授权方头像
     *
     * @param head_img 授权方头像
     */
    public void setHead_img(String head_img) {
        this.head_img = head_img == null ? null : head_img.trim();
    }

    /**
     * 获取授权方认证类型，-1代表未认证，0代表微信认证
     *
     * @return verify_type_info - 授权方认证类型，-1代表未认证，0代表微信认证
     */
    public String getVerify_type_info() {
        return verify_type_info;
    }

    /**
     * 设置授权方认证类型，-1代表未认证，0代表微信认证
     *
     * @param verify_type_info 授权方认证类型，-1代表未认证，0代表微信认证
     */
    public void setVerify_type_info(String verify_type_info) {
        this.verify_type_info = verify_type_info == null ? null : verify_type_info.trim();
    }

    /**
     * 获取小程序的原始ID
     *
     * @return user_name - 小程序的原始ID
     */
    public String getUser_name() {
        return user_name;
    }

    /**
     * 设置小程序的原始ID
     *
     * @param user_name 小程序的原始ID
     */
    public void setUser_name(String user_name) {
        this.user_name = user_name == null ? null : user_name.trim();
    }

    /**
     * 获取帐号介绍
     *
     * @return signature - 帐号介绍
     */
    public String getSignature() {
        return signature;
    }

    /**
     * 设置帐号介绍
     *
     * @param signature 帐号介绍
     */
    public void setSignature(String signature) {
        this.signature = signature == null ? null : signature.trim();
    }

    /**
     * 获取小程序的主体名称
     *
     * @return principal_name - 小程序的主体名称
     */
    public String getPrincipal_name() {
        return principal_name;
    }

    /**
     * 设置小程序的主体名称
     *
     * @param principal_name 小程序的主体名称
     */
    public void setPrincipal_name(String principal_name) {
        this.principal_name = principal_name == null ? null : principal_name.trim();
    }

    /**
     * 获取开通状况（0代表未开通，1代表已开通）
     *
     * @return business_info - 开通状况（0代表未开通，1代表已开通）
     */
    public String getBusiness_info() {
        return business_info;
    }

    /**
     * 设置开通状况（0代表未开通，1代表已开通）
     *
     * @param business_info 开通状况（0代表未开通，1代表已开通）
     */
    public void setBusiness_info(String business_info) {
        this.business_info = business_info == null ? null : business_info.trim();
    }

    /**
     * 获取二维码图片的URL，开发者最好自行也进行保存
     *
     * @return qrcode_url - 二维码图片的URL，开发者最好自行也进行保存
     */
    public String getQrcode_url() {
        return qrcode_url;
    }

    /**
     * 设置二维码图片的URL，开发者最好自行也进行保存
     *
     * @param qrcode_url 二维码图片的URL，开发者最好自行也进行保存
     */
    public void setQrcode_url(String qrcode_url) {
        this.qrcode_url = qrcode_url == null ? null : qrcode_url.trim();
    }

    /**
     * 获取根据这个字段判断是否为小程序类型授权
     *
     * @return miniprograminfo - 根据这个字段判断是否为小程序类型授权
     */
    public String getMiniprograminfo() {
        return miniprograminfo;
    }

    /**
     * 设置根据这个字段判断是否为小程序类型授权
     *
     * @param miniprograminfo 根据这个字段判断是否为小程序类型授权
     */
    public void setMiniprograminfo(String miniprograminfo) {
        this.miniprograminfo = miniprograminfo == null ? null : miniprograminfo.trim();
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
     * 获取审核编号
     *
     * @return auditid - 审核编号
     */
    public String getAuditid() {
        return auditid;
    }

    /**
     * 设置审核编号
     *
     * @param auditid 审核编号
     */
    public void setAuditid(String auditid) {
        this.auditid = auditid == null ? null : auditid.trim();
    }

    /**
     * 获取审核状态 1：未审核 2：审核中 3：审核失败 4：审核成功
     *
     * @return review_mark - 审核状态 1：未审核 2：审核中 3：审核失败 4：审核成功
     */
    public Integer getReview_mark() {
        return review_mark;
    }

    /**
     * 设置审核状态 1：未审核 2：审核中 3：审核失败 4：审核成功
     *
     * @param review_mark 审核状态 1：未审核 2：审核中 3：审核失败 4：审核成功
     */
    public void setReview_mark(Integer review_mark) {
        this.review_mark = review_mark;
    }

    /**
     * 获取发布状态 1：未发布  2：发布失败 3：发布成功
     *
     * @return issue_mark - 发布状态 1：未发布  2：发布失败 3：发布成功
     */
    public Integer getIssue_mark() {
        return issue_mark;
    }

    /**
     * 设置发布状态 1：未发布  2：发布失败 3：发布成功
     *
     * @param issue_mark 发布状态 1：未发布  2：发布失败 3：发布成功
     */
    public void setIssue_mark(Integer issue_mark) {
        this.issue_mark = issue_mark;
    }

    /**
     * 获取审核提价时间
     *
     * @return submit_time - 审核提价时间
     */
    public Date getSubmit_time() {
        return submit_time;
    }

    /**
     * 设置审核提价时间
     *
     * @param submit_time 审核提价时间
     */
    public void setSubmit_time(Date submit_time) {
        this.submit_time = submit_time;
    }
}