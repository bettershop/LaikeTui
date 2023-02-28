package com.laiketui.domain.config;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 积分商城配置
 *
 * @author Trick
 * @date 2021/11/2 14:42
 */
@Table(name = "lkt_integral_config")
public class IntegralConfigModel {
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

    private String bg_img;

    private String content;

    /**
     * 插件状态 0关闭 1开启
     */
    private Integer status;


    /**
     * 订单失效 (单位 秒)
     */
    @Column(name = "order_failure")
    private Integer order_failure;

    /**
     * 订单售后时间 (单位秒) 默认7天
     */
    @Column(name = "order_after")
    private  Integer order_after;
    /**
     * 自动收货时间 (单位秒) 默认7天
     */
    @Column(name = "auto_the_goods")
    private  Integer auto_the_goods;
    /**
     * 提醒发货限制 间隔(单位 秒) 默认1小时
     */
    @Column(name = "deliver_remind")
    private  Integer deliver_remind;
    /**
     * 自动评价设置几后自动好评
     */
    @Column(name = "auto_good_comment_day")
    private  Integer auto_good_comment_day;

    /**
     * 多件包邮设置 0.未开启 1.开启
     */
    @Column(name = "package_settings")
    private Integer package_settings;

    /**
     * 同件
     */
    @Column(name = "same_piece")
    private Integer same_piece;

    /**
     * 积分比例(购物赠送积分=购物交易金额*赠送比例)
     */
    private BigDecimal proportion;


    /**
     * 发放状态(0=收货后 1=付款后)
     */
    @Column(name = "give_status")
    private Integer give_status;


    /**
     * 积分有效时间从积分获取后开始计算，至 x 失效
     */
    @Column(name = "overdue_time")
    private Integer overdue_time;

    public BigDecimal getProportion() {
        return proportion;
    }

    public void setProportion(BigDecimal proportion) {
        this.proportion = proportion;
    }

    public Integer getGive_status() {
        return give_status;
    }

    public void setGive_status(Integer give_status) {
        this.give_status = give_status;
    }

    public Integer getOverdue_time() {
        return overdue_time;
    }

    public void setOverdue_time(Integer overdue_time) {
        this.overdue_time = overdue_time;
    }

    public Integer getPackage_settings() {
        return package_settings;
    }

    public void setPackage_settings(Integer package_settings) {
        this.package_settings = package_settings;
    }

    public Integer getSame_piece() {
        return same_piece;
    }

    public void setSame_piece(Integer same_piece) {
        this.same_piece = same_piece;
    }

    public Integer getOrder_failure() {
        return order_failure;
    }

    public void setOrder_failure(Integer order_failure) {
        this.order_failure = order_failure;
    }

    public Integer getOrder_after() {
        return order_after;
    }

    public void setOrder_after(Integer order_after) {
        this.order_after = order_after;
    }

    public Integer getAuto_the_goods() {
        return auto_the_goods;
    }

    public void setAuto_the_goods(Integer auto_the_goods) {
        this.auto_the_goods = auto_the_goods;
    }

    public Integer getDeliver_remind() {
        return deliver_remind;
    }

    public void setDeliver_remind(Integer deliver_remind) {
        this.deliver_remind = deliver_remind;
    }

    public Integer getAuto_good_comment_day() {
        return auto_good_comment_day;
    }

    public void setAuto_good_comment_day(Integer auto_good_comment_day) {
        this.auto_good_comment_day = auto_good_comment_day;
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
     * @return bg_img
     */
    public String getBg_img() {
        return bg_img;
    }

    /**
     * @param bg_img
     */
    public void setBg_img(String bg_img) {
        this.bg_img = bg_img == null ? null : bg_img.trim();
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取插件状态 0关闭 1开启
     *
     * @return status - 插件状态 0关闭 1开启
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置插件状态 0关闭 1开启
     *
     * @param status 插件状态 0关闭 1开启
     */
    public void setStatus(Integer status) {
        this.status = status;
    }
}