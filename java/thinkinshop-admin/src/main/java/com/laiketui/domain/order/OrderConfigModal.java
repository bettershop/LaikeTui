package com.laiketui.domain.order;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_order_config")
public class OrderConfigModal {
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
     * 承若天数
     */
    private Integer days;

    /**
     * 承若内容
     */
    private String content;

    /**
     * 退货时间
     */
    private Integer back;

    /**
     * 订单失效
     */
    private Integer order_failure;

    /**
     * 订单售后时间
     */
    private Integer order_after;

    /**
     * 单位
     */
    private String company;

    /**
     * 订单过期删除时间
     */
    private Integer order_overdue;

    /**
     * 单位
     */
    private String unit;

    /**
     * 修改时间
     */
    private Date modify_date;

    /**
     * 自动收货时间
     */
    private Integer auto_the_goods;

    /**
     * 发货时限
     */
    private Integer order_ship;

    /**
     * 提醒限制
     */
    private Integer remind;

    /**
     * 顾客编码
     */
    private String accesscode;

    /**
     * 月结卡号
     */
    private String custid;

    /**
     * 包邮设置 0.未开启 1.开启
     */
    private Integer package_settings;

    /**
     * 同件
     */
    private Integer same_piece;

    /**
     * 同单
     */
    private Integer same_order;

    /**
     * 自动评价设置几后自动好评
     */
    private Integer auto_good_comment_day;

    /**
     * 校验码
     */
    private String checkword;

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
     * 获取承若天数
     *
     * @return days - 承若天数
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 设置承若天数
     *
     * @param days 承若天数
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 获取承若内容
     *
     * @return content - 承若内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置承若内容
     *
     * @param content 承若内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取退货时间
     *
     * @return back - 退货时间
     */
    public Integer getBack() {
        return back;
    }

    /**
     * 设置退货时间
     *
     * @param back 退货时间
     */
    public void setBack(Integer back) {
        this.back = back;
    }

    /**
     * 获取订单失效-单位小时
     *
     * @return order_failure - 订单失效
     */
    public Integer getOrder_failure() {
        return order_failure;
    }

    /**
     * 设置订单失效
     *
     * @param order_failure 订单失效
     */
    public void setOrder_failure(Integer order_failure) {
        this.order_failure = order_failure;
    }

    /**
     * 获取订单售后时间
     *
     * @return order_after - 订单售后时间
     */
    public Integer getOrder_after() {
        return order_after;
    }

    /**
     * 设置订单售后时间
     *
     * @param order_after 订单售后时间
     */
    public void setOrder_after(Integer order_after) {
        this.order_after = order_after;
    }

    /**
     * 获取单位
     *
     * @return company - 单位
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置单位
     *
     * @param company 单位
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * 获取订单过期删除时间
     *
     * @return order_overdue - 订单过期删除时间
     */
    public Integer getOrder_overdue() {
        return order_overdue;
    }

    /**
     * 设置订单过期删除时间
     *
     * @param order_overdue 订单过期删除时间
     */
    public void setOrder_overdue(Integer order_overdue) {
        this.order_overdue = order_overdue;
    }

    /**
     * 获取单位
     *
     * @return unit - 单位
     */
    public String getUnit() {
        return unit;
    }

    /**
     * 设置单位
     *
     * @param unit 单位
     */
    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
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
     * 获取自动收货时间
     *
     * @return auto_the_goods - 自动收货时间
     */
    public Integer getAuto_the_goods() {
        return auto_the_goods;
    }

    /**
     * 设置自动收货时间
     *
     * @param auto_the_goods 自动收货时间
     */
    public void setAuto_the_goods(Integer auto_the_goods) {
        this.auto_the_goods = auto_the_goods;
    }

    /**
     * 获取发货时限
     *
     * @return order_ship - 发货时限
     */
    public Integer getOrder_ship() {
        return order_ship;
    }

    /**
     * 设置发货时限
     *
     * @param order_ship 发货时限
     */
    public void setOrder_ship(Integer order_ship) {
        this.order_ship = order_ship;
    }

    /**
     * 获取提醒限制
     *
     * @return remind - 提醒限制
     */
    public Integer getRemind() {
        return remind;
    }

    /**
     * 设置提醒限制 - 小时
     *
     * @param remind 提醒限制
     */
    public void setRemind(Integer remind) {
        this.remind = remind;
    }

    /**
     * 获取顾客编码
     *
     * @return accesscode - 顾客编码
     */
    public String getAccesscode() {
        return accesscode;
    }

    /**
     * 设置顾客编码
     *
     * @param accesscode 顾客编码
     */
    public void setAccesscode(String accesscode) {
        this.accesscode = accesscode == null ? null : accesscode.trim();
    }

    /**
     * 获取月结卡号
     *
     * @return custid - 月结卡号
     */
    public String getCustid() {
        return custid;
    }

    /**
     * 设置月结卡号
     *
     * @param custid 月结卡号
     */
    public void setCustid(String custid) {
        this.custid = custid == null ? null : custid.trim();
    }

    /**
     * 获取包邮设置 0.未开启 1.开启
     *
     * @return package_settings - 包邮设置 0.未开启 1.开启
     */
    public Integer getPackage_settings() {
        return package_settings;
    }

    /**
     * 设置包邮设置 0.未开启 1.开启
     *
     * @param package_settings 包邮设置 0.未开启 1.开启
     */
    public void setPackage_settings(Integer package_settings) {
        this.package_settings = package_settings;
    }

    /**
     * 获取同件
     *
     * @return same_piece - 同件
     */
    public Integer getSame_piece() {
        return same_piece;
    }

    /**
     * 设置同件
     *
     * @param same_piece 同件
     */
    public void setSame_piece(Integer same_piece) {
        this.same_piece = same_piece;
    }

    /**
     * 获取同单
     *
     * @return same_order - 同单
     */
    public Integer getSame_order() {
        return same_order;
    }

    /**
     * 设置同单
     *
     * @param same_order 同单
     */
    public void setSame_order(Integer same_order) {
        this.same_order = same_order;
    }

    /**
     * 获取自动评价设置几后自动好评
     *
     * @return auto_good_comment_day - 自动评价设置几后自动好评
     */
    public Integer getAuto_good_comment_day() {
        return auto_good_comment_day;
    }

    /**
     * 设置自动评价设置几后自动好评
     *
     * @param auto_good_comment_day 自动评价设置几后自动好评
     */
    public void setAuto_good_comment_day(Integer auto_good_comment_day) {
        this.auto_good_comment_day = auto_good_comment_day;
    }

    /**
     * 获取校验码
     *
     * @return checkword - 校验码
     */
    public String getCheckword() {
        return checkword;
    }

    /**
     * 设置校验码
     *
     * @param checkword 校验码
     */
    public void setCheckword(String checkword) {
        this.checkword = checkword == null ? null : checkword.trim();
    }
}