package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_sign_config")
public class SignConfigModel {
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
     * 签到插件是否启用 0：未启用 1：启用
     */
    private Integer is_status;

    /**
     * 签到次数
     */
    private Integer score_num;

    /**
     * 签到有效开始时间
     */
    private String starttime;

    /**
     * 签到有效结束时间
     */
    private String endtime;

    /**
     * 是否提醒 0：不提醒 1：提醒
     */
    private Integer is_remind;

    /**
     * 间隔时间
     */
    private String reset;

    /**
     * 图片
     */
    private String imgurl;

    /**
     * 领取积分
     */
    private Integer score;

    /**
     * 连续设置
     */
    private String continuity;

    /**
     * 签到详情
     */
    private String detail;

    /**
     * 积分使用说明
     */
    @Column(name = "Instructions")
    private String instructions;

    /**
     * 修改时间
     */
    private Date modify_date;

    /**
     * 是否允许多次 0:不允许  1:允许
     */
    private Integer is_many_time;

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
     * 获取签到插件是否启用 0：未启用 1：启用
     *
     * @return is_status - 签到插件是否启用 0：未启用 1：启用
     */
    public Integer getIs_status() {
        return is_status;
    }

    /**
     * 设置签到插件是否启用 0：未启用 1：启用
     *
     * @param is_status 签到插件是否启用 0：未启用 1：启用
     */
    public void setIs_status(Integer is_status) {
        this.is_status = is_status;
    }

    /**
     * 获取签到次数
     *
     * @return score_num - 签到次数
     */
    public Integer getScore_num() {
        return score_num;
    }

    /**
     * 设置签到次数
     *
     * @param score_num 签到次数
     */
    public void setScore_num(Integer score_num) {
        this.score_num = score_num;
    }

    /**
     * 获取签到有效开始时间
     *
     * @return starttime - 签到有效开始时间
     */
    public String getStarttime() {
        return starttime;
    }

    /**
     * 设置签到有效开始时间
     *
     * @param starttime 签到有效开始时间
     */
    public void setStarttime(String starttime) {
        this.starttime = starttime == null ? null : starttime.trim();
    }

    /**
     * 获取签到有效结束时间
     *
     * @return endtime - 签到有效结束时间
     */
    public String getEndtime() {
        return endtime;
    }

    /**
     * 设置签到有效结束时间
     *
     * @param endtime 签到有效结束时间
     */
    public void setEndtime(String endtime) {
        this.endtime = endtime == null ? null : endtime.trim();
    }

    /**
     * 获取是否提醒 0：不提醒 1：提醒
     *
     * @return is_remind - 是否提醒 0：不提醒 1：提醒
     */
    public Integer getIs_remind() {
        return is_remind;
    }

    /**
     * 设置是否提醒 0：不提醒 1：提醒
     *
     * @param is_remind 是否提醒 0：不提醒 1：提醒
     */
    public void setIs_remind(Integer is_remind) {
        this.is_remind = is_remind;
    }

    /**
     * 获取间隔时间
     *
     * @return reset - 间隔时间
     */
    public String getReset() {
        return reset;
    }

    /**
     * 设置间隔时间
     *
     * @param reset 间隔时间
     */
    public void setReset(String reset) {
        this.reset = reset == null ? null : reset.trim();
    }

    /**
     * 获取图片
     *
     * @return imgurl - 图片
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置图片
     *
     * @param imgurl 图片
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
     * 获取领取积分
     *
     * @return score - 领取积分
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置领取积分
     *
     * @param score 领取积分
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取连续设置
     *
     * @return continuity - 连续设置
     */
    public String getContinuity() {
        return continuity;
    }

    /**
     * 设置连续设置
     *
     * @param continuity 连续设置
     */
    public void setContinuity(String continuity) {
        this.continuity = continuity == null ? null : continuity.trim();
    }

    /**
     * 获取签到详情
     *
     * @return detail - 签到详情
     */
    public String getDetail() {
        return detail;
    }

    /**
     * 设置签到详情
     *
     * @param detail 签到详情
     */
    public void setDetail(String detail) {
        this.detail = detail == null ? null : detail.trim();
    }

    /**
     * 获取积分使用说明
     *
     * @return Instructions - 积分使用说明
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * 设置积分使用说明
     *
     * @param instructions 积分使用说明
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions == null ? null : instructions.trim();
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
     * 获取是否允许多次 0:不允许  1:允许
     *
     * @return is_many_time - 是否允许多次 0:不允许  1:允许
     */
    public Integer getIs_many_time() {
        return is_many_time;
    }

    /**
     * 设置是否允许多次 0:不允许  1:允许
     *
     * @param is_many_time 是否允许多次 0:不允许  1:允许
     */
    public void setIs_many_time(Integer is_many_time) {
        this.is_many_time = is_many_time;
    }
}