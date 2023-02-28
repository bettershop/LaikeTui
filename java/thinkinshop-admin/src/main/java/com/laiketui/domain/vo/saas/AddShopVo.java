package com.laiketui.domain.vo.saas;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑商城
 *
 * @author Trick
 * @date 2021/1/28 11:45
 */
@ApiModel(description = "添加/编辑商城参数")
public class AddShopVo {
    @ApiModelProperty(value = "商城id", name = "storeId")
    private Integer storeId;

    @ApiModelProperty(value = "商城名称", name = "storeName")
    private String storeName;
    @ApiModelProperty(value = "商城编号", name = "storeNo")
    private String storeNo;
    @ApiModelProperty(value = "商城根目录域名", name = "storeDomain")
    private String storeDomain;

    @ApiModelProperty(value = "商户logo", name = "logo")
    private String logUrl;
    @ApiModelProperty(value = "官方网站", name = "website")
    private String website;
    @ApiModelProperty(value = "备案信息", name = "recordInformation")
    private String recordInformation;
    @ApiModelProperty(value = "版权信息", name = "copyrightInformation")
    private String copyrightInformation;
    @ApiModelProperty(value = "联系电话", name = "contactNumber")
    private String contactNumber;
    @ApiModelProperty(value = "联系地址", name = "contactAddress")
    private String contactAddress;

    @ApiModelProperty(value = "公司名称", name = "company")
    private String company;
    @ApiModelProperty(value = "手机号", name = "mobile")
    private String mobile;
    @ApiModelProperty(value = "价格", name = "price")
    private BigDecimal price;
    @ApiModelProperty(value = "邮箱", name = "email")
    private String email;
    @ApiModelProperty(value = "管理员账号", name = "adminAccount")
    private String adminAccount;
    @ApiModelProperty(value = "管理员密码", name = "adminPwd")
    private String adminPwd;
    @ApiModelProperty(value = "管理员权限id", name = "roleId")
    private Integer roleId;
    @ApiModelProperty(value = "到期时间", name = "endDate")
    private String endDate;
    @ApiModelProperty(value = "是否启用 0:启用 2:锁定", name = "endDate")
    private Integer isOpen;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getLogUrl() {
        return logUrl;
    }

    public void setLogUrl(String logUrl) {
        this.logUrl = logUrl;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getRecordInformation() {
        return recordInformation;
    }

    public void setRecordInformation(String recordInformation) {
        this.recordInformation = recordInformation;
    }

    public String getCopyrightInformation() {
        return copyrightInformation;
    }

    public void setCopyrightInformation(String copyrightInformation) {
        this.copyrightInformation = copyrightInformation;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreNo() {
        return storeNo;
    }

    public void setStoreNo(String storeNo) {
        this.storeNo = storeNo;
    }

    public String getStoreDomain() {
        return storeDomain;
    }

    public void setStoreDomain(String storeDomain) {
        this.storeDomain = storeDomain;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdminAccount() {
        return adminAccount;
    }

    public void setAdminAccount(String adminAccount) {
        this.adminAccount = adminAccount;
    }

    public String getAdminPwd() {
        return adminPwd;
    }

    public void setAdminPwd(String adminPwd) {
        this.adminPwd = adminPwd;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }
}
