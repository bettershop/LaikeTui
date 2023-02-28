package com.laiketui.domain.mch;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lkt_distribution_grade")
public class DistributionGradeModel {
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

    private String sets;

    /**
     * 创建时间
     */
    private Date datetime;

    /**
     * 是否为代理商
     */
    private Integer is_agent;

    /**
     * 是否是普通分销商0 不是 1是
     */
    private Integer is_ordinary;

    /**
     * 推荐后消费金转余额比例
     */
    private Integer transfer_balance;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 购物折扣
     */
    private BigDecimal discount;

    /**
     * 积分
     */
    private Integer integral;

    /**
     * 会员专区佣金比例
     */
    private String member_proportion;

    /**
     * 会员专区消费金
     */
    private String member_consumer_money;

    /**
     * 升级条件
     */
    private String uplevel_obj;

    public String getUplevel_obj() {
        return uplevel_obj;
    }

    public void setUplevel_obj(String uplevel_obj) {
        this.uplevel_obj = uplevel_obj;
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
     * @return sets
     */
    public String getSets() {
        return sets;
    }

    /**
     * @param sets
     */
    public void setSets(String sets) {
        this.sets = sets == null ? null : sets.trim();
    }

    /**
     * 获取创建时间
     *
     * @return datetime - 创建时间
     */
    public Date getDatetime() {
        return datetime;
    }

    /**
     * 设置创建时间
     *
     * @param datetime 创建时间
     */
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    /**
     * 获取是否为代理商
     *
     * @return is_agent - 是否为代理商
     */
    public Integer getIs_agent() {
        return is_agent;
    }

    /**
     * 设置是否为代理商
     *
     * @param is_agent 是否为代理商
     */
    public void setIs_agent(Integer is_agent) {
        this.is_agent = is_agent;
    }

    /**
     * 获取是否是普通分销商0 不是 1是
     *
     * @return is_ordinary - 是否是普通分销商0 不是 1是
     */
    public Integer getIs_ordinary() {
        return is_ordinary;
    }

    /**
     * 设置是否是普通分销商0 不是 1是
     *
     * @param is_ordinary 是否是普通分销商0 不是 1是
     */
    public void setIs_ordinary(Integer is_ordinary) {
        this.is_ordinary = is_ordinary;
    }

    /**
     * 获取推荐后消费金转余额比例
     *
     * @return transfer_balance - 推荐后消费金转余额比例
     */
    public Integer getTransfer_balance() {
        return transfer_balance;
    }

    /**
     * 设置推荐后消费金转余额比例
     *
     * @param transfer_balance 推荐后消费金转余额比例
     */
    public void setTransfer_balance(Integer transfer_balance) {
        this.transfer_balance = transfer_balance;
    }

    /**
     * 获取排序号
     *
     * @return sort - 排序号
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序号
     *
     * @param sort 排序号
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取购物折扣
     *
     * @return discount - 购物折扣
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 设置购物折扣
     *
     * @param discount 购物折扣
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 获取积分
     *
     * @return integral - 积分
     */
    public Integer getIntegral() {
        return integral;
    }

    /**
     * 设置积分
     *
     * @param integral 积分
     */
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }

    /**
     * 获取会员专区佣金比例
     *
     * @return member_proportion - 会员专区佣金比例
     */
    public String getMember_proportion() {
        return member_proportion;
    }

    /**
     * 设置会员专区佣金比例
     *
     * @param member_proportion 会员专区佣金比例
     */
    public void setMember_proportion(String member_proportion) {
        this.member_proportion = member_proportion == null ? null : member_proportion.trim();
    }

    /**
     * 获取会员专区消费金
     *
     * @return member_consumer_money - 会员专区消费金
     */
    public String getMember_consumer_money() {
        return member_consumer_money;
    }

    /**
     * 设置会员专区消费金
     *
     * @param member_consumer_money 会员专区消费金
     */
    public void setMember_consumer_money(String member_consumer_money) {
        this.member_consumer_money = member_consumer_money == null ? null : member_consumer_money.trim();
    }
}