package com.laiketui.domain.log;

import javax.persistence.*;
import java.util.Date;


/**
 * 签到
 *
 * @author Trick
 * @date 2021/2/8 17:25
 */
@Table(name = "lkt_sign_record")
public class SignRecordModel {
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
     * 用户ID
     */
    private String user_id;

    /**
     * 签到积分
     */
    private Integer sign_score;

    /**
     * 总积分
     */
    private Integer total_score;

    /**
     * 事件
     */
    private String record;

    /**
     * 积分过期时间  永不过期则为null
     */
    @Column(name = "score_invalid")
    private Date score_invalid;

    /**
     * 签到时间
     */
    private Date sign_time;

    /**
     * 类型: 0:签到 1:消费 2:首次关注得积分 3:转积分给好友 4:好友转积分 5:系统扣除 6:系统充值 7:抽奖 8:会员购物积分 9:分销升级奖励积分 10:积分过期
     */
    private Integer type;

    /**
     * 是否删除 0.未删除 1.删除
     */
    private Integer recovery;

    /**
     * 订单编号
     */
    @Column(name = "sNo")
    private String sNo;

    public Date getScore_invalid() {
        return score_invalid;
    }

    public void setScore_invalid(Date score_invalid) {
        this.score_invalid = score_invalid;
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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID
     *
     * @param user_id 用户ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取签到积分
     *
     * @return sign_score - 签到积分
     */
    public Integer getSign_score() {
        return sign_score;
    }

    /**
     * 设置签到积分
     *
     * @param sign_score 签到积分
     */
    public void setSign_score(Integer sign_score) {
        this.sign_score = sign_score;
    }

    /**
     * 获取总积分
     *
     * @return total_score - 总积分
     */
    public Integer getTotal_score() {
        return total_score;
    }

    /**
     * 设置总积分
     *
     * @param total_score 总积分
     */
    public void setTotal_score(Integer total_score) {
        this.total_score = total_score;
    }

    /**
     * 获取事件
     *
     * @return record - 事件
     */
    public String getRecord() {
        return record;
    }

    /**
     * 设置事件
     *
     * @param record 事件
     */
    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
    }

    /**
     * 获取签到时间
     *
     * @return sign_time - 签到时间
     */
    public Date getSign_time() {
        return sign_time;
    }

    /**
     * 设置签到时间
     *
     * @param sign_time 签到时间
     */
    public void setSign_time(Date sign_time) {
        this.sign_time = sign_time;
    }

    /**
     * 获取类型: 0:签到 1:消费 2:首次关注得积分 3:转积分给好友 4:好友转积分 5:系统扣除 6:系统充值 7:抽奖 8:会员购物积分 9:分销升级奖励积分 10:积分过期
     *
     * @return type - 类型: 0:签到 1:消费 2:首次关注得积分 3:转积分给好友 4:好友转积分 5:系统扣除 6:系统充值 7:抽奖 8:会员购物积分 9:分销升级奖励积分 10:积分过期
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型: 0:签到 1:消费 2:首次关注得积分 3:转积分给好友 4:好友转积分 5:系统扣除 6:系统充值 7:抽奖 8:会员购物积分 9:分销升级奖励积分 10:积分过期
     *
     * @param type 类型: 0:签到 1:消费 2:首次关注得积分 3:转积分给好友 4:好友转积分 5:系统扣除 6:系统充值 7:抽奖 8:会员购物积分 9:分销升级奖励积分 10:积分过期
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取是否删除 0.未删除 1.删除
     *
     * @return recovery - 是否删除 0.未删除 1.删除
     */
    public Integer getRecovery() {
        return recovery;
    }

    /**
     * 设置是否删除 0.未删除 1.删除
     *
     * @param recovery 是否删除 0.未删除 1.删除
     */
    public void setRecovery(Integer recovery) {
        this.recovery = recovery;
    }

    /**
     * 获取订单编号
     *
     * @return sNo - 订单编号
     */
    public String getsNo() {
        return sNo;
    }

    /**
     * 设置订单编号
     *
     * @param sNo 订单编号
     */
    public void setsNo(String sNo) {
        this.sNo = sNo == null ? null : sNo.trim();
    }
}