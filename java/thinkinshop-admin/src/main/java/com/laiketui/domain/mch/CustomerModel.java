package com.laiketui.domain.mch;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 商城模型
 *
 * @author Trick
 * @date 2021/1/28 10:15
 */
@Table(name = "lkt_customer")
public class CustomerModel {


    /**
     * 启用
     */
    public static final Integer CUSTOMER_STATUS_OPEN =0;
    /**
     * 到期
     */
    public static final Integer CUSTOMER_STATUS_EXPIRE =1;
    /**
     * 锁定
     */
    public static final Integer CUSTOMER_STATUS_LOCK =2;

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 管理员id
     */
    private Integer admin_id;

    /**
     * 客户编号
     */
    private String customer_number;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 功能
     */
    @Column(name = "`function`")
    private String function;

    /**
     * 购买时间
     */
    private Date add_date;

    /**
     * 到期时间
     */
    private Date end_date;

    /**
     * 类型 0:启用 1:到期 2:锁定
     */
    private Integer status;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 回收站 0:不回收 1:回收 
     */
    private Integer recycle;

    /**
     * 官方网址
     * add date 2021-06-08 11:06:55
     */
    @Column(name = "official_website")
    private String official_website;
    /**
     * 联系地址
     * add date 2021-06-08 11:06:55
     */
    @Column(name = "contact_address")
    private String contact_address;
    /**
     * 联系电话
     * add date 2021-06-08 11:06:55
     */
    @Column(name = "contact_number")
    private String contact_number;
    /**
     * 版权信息
     * add date 2021-06-08 11:06:55
     */
    @Column(name = "copyright_information")
    private String copyright_information;
    /**
     * 备案信息
     * add date 2021-06-08 11:06:55
     */
    @Column(name = "record_information")
    private String record_information;
    /**
     * 商户logo
     * add date 2021-06-08 11:06:55
     */
    @Column(name = "merchant_logo")
    private String merchant_logo;
    /**
     * 是否默认
     * add date 2021-06-11 14:42:02
     */
    @Column(name = "is_default")
    private Integer is_default;

    public Integer getIs_default() {
        return is_default;
    }

    public void setIs_default(Integer is_default) {
        this.is_default = is_default;
    }

    public String getOfficial_website() {
        return official_website;
    }

    public void setOfficial_website(String official_website) {
        this.official_website = official_website;
    }

    public String getContact_address() {
        return contact_address;
    }

    public void setContact_address(String contact_address) {
        this.contact_address = contact_address;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getCopyright_information() {
        return copyright_information;
    }

    public void setCopyright_information(String copyright_information) {
        this.copyright_information = copyright_information;
    }

    public String getRecord_information() {
        return record_information;
    }

    public void setRecord_information(String record_information) {
        this.record_information = record_information;
    }

    public String getMerchant_logo() {
        return merchant_logo;
    }

    public void setMerchant_logo(String merchant_logo) {
        this.merchant_logo = merchant_logo;
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
     * 获取管理员id
     *
     * @return admin_id - 管理员id
     */
    public Integer getAdmin_id() {
        return admin_id;
    }

    /**
     * 设置管理员id
     *
     * @param admin_id 管理员id
     */
    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    /**
     * 获取客户编号
     *
     * @return customer_number - 客户编号
     */
    public String getCustomer_number() {
        return customer_number;
    }

    /**
     * 设置客户编号
     *
     * @param customer_number 客户编号
     */
    public void setCustomer_number(String customer_number) {
        this.customer_number = customer_number == null ? null : customer_number.trim();
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取手机
     *
     * @return mobile - 手机
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置手机
     *
     * @param mobile 手机
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
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
     * 获取功能
     *
     * @return function - 功能
     */
    public String getFunction() {
        return function;
    }

    /**
     * 设置功能
     *
     * @param function 功能
     */
    public void setFunction(String function) {
        this.function = function == null ? null : function.trim();
    }

    /**
     * 获取购买时间
     *
     * @return add_date - 购买时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置购买时间
     *
     * @param add_date 购买时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * 获取到期时间
     *
     * @return end_date - 到期时间
     */
    public Date getEnd_date() {
        return end_date;
    }

    /**
     * 设置到期时间
     *
     * @param end_date 到期时间
     */
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    /**
     * 获取类型 0:启用 1:到期 2:锁定
     *
     * @return status - 类型 0:启用 1:到期 2:锁定
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置类型 0:启用 1:到期 2:锁定
     *
     * @param status 类型 0:启用 1:到期 2:锁定
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取回收站 0:不回收 1:回收 
     *
     * @return recycle - 回收站 0:不回收 1:回收 
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0:不回收 1:回收 
     *
     * @param recycle 回收站 0:不回收 1:回收 
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }
}