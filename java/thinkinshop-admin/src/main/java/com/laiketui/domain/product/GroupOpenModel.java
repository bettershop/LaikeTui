package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 用户开团表
 *
 * @author Trick
 */
@Table(name = "lkt_group_open")
public class GroupOpenModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 0:未付款 1:拼团中，2:拼团成功, 3：拼团失败,
     */
    public interface Ptstatus {
        /**
         * 未付款
         */
        int NOT_PAY = 0,
        /**
         * 拼团中
         */
        PT_ING = 1,
        /**
         * 拼团成功
         */
        PT_SUCCESS = 2,
        /**
         * 拼团失败
         */
        PT_FAIL = 3;
    }

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 微信id
     */
    private String uid;

    /**
     * 拼团商品id
     */
    private Integer ptgoods_id;

    /**
     * 拼团编号
     */
    private String ptcode;

    /**
     * 几人团
     */
    private Integer groupman;

    /**
     * 拼团人数
     */
    private Integer ptnumber;

    /**
     * 创建日期
     */
    private Date addtime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 0:未付款 1:拼团中，2:拼团成功, 3：拼团失败,
     */
    private Integer ptstatus;

    /**
     * 拼团标题
     */
    private String group_title;

    /**
     * 拼团等级价格参数
     */
    private String group_level;

    /**
     * 拼团数据
     */
    private String group_data;

    /**
     * 拼团活动编号
     */
    private Integer activity_no;

    /**
     * 平台活动管理id
     */
    private Integer platform_activities_id;

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
     * 获取微信id
     *
     * @return uid - 微信id
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置微信id
     *
     * @param uid 微信id
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取拼团商品id
     *
     * @return ptgoods_id - 拼团商品id
     */
    public Integer getPtgoods_id() {
        return ptgoods_id;
    }

    /**
     * 设置拼团商品id
     *
     * @param ptgoods_id 拼团商品id
     */
    public void setPtgoods_id(Integer ptgoods_id) {
        this.ptgoods_id = ptgoods_id;
    }

    /**
     * 获取拼团编号
     *
     * @return ptcode - 拼团编号
     */
    public String getPtcode() {
        return ptcode;
    }

    /**
     * 设置拼团编号
     *
     * @param ptcode 拼团编号
     */
    public void setPtcode(String ptcode) {
        this.ptcode = ptcode == null ? null : ptcode.trim();
    }

    /**
     * 获取几人团
     *
     * @return groupman - 几人团
     */
    public Integer getGroupman() {
        return groupman;
    }

    /**
     * 设置几人团
     *
     * @param groupman 几人团
     */
    public void setGroupman(Integer groupman) {
        this.groupman = groupman;
    }

    /**
     * 获取拼团人数
     *
     * @return ptnumber - 拼团人数
     */
    public Integer getPtnumber() {
        return ptnumber;
    }

    /**
     * 设置拼团人数
     *
     * @param ptnumber 拼团人数
     */
    public void setPtnumber(Integer ptnumber) {
        this.ptnumber = ptnumber;
    }

    /**
     * 获取创建日期
     *
     * @return addtime - 创建日期
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置创建日期
     *
     * @param addtime 创建日期
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * 获取结束时间
     *
     * @return endtime - 结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置结束时间
     *
     * @param endtime 结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取0:未付款 1:拼团中，2:拼团成功, 3：拼团失败,
     *
     * @return ptstatus - 0:未付款 1:拼团中，2:拼团成功, 3：拼团失败,
     */
    public Integer getPtstatus() {
        return ptstatus;
    }

    /**
     * 设置0:未付款 1:拼团中，2:拼团成功, 3：拼团失败,
     *
     * @param ptstatus 0:未付款 1:拼团中，2:拼团成功, 3：拼团失败,
     */
    public void setPtstatus(Integer ptstatus) {
        this.ptstatus = ptstatus;
    }

    /**
     * 获取拼团标题
     *
     * @return group_title - 拼团标题
     */
    public String getGroup_title() {
        return group_title;
    }

    /**
     * 设置拼团标题
     *
     * @param group_title 拼团标题
     */
    public void setGroup_title(String group_title) {
        this.group_title = group_title == null ? null : group_title.trim();
    }

    /**
     * 获取拼团等级价格参数
     *
     * @return group_level - 拼团等级价格参数
     */
    public String getGroup_level() {
        return group_level;
    }

    /**
     * 设置拼团等级价格参数
     *
     * @param group_level 拼团等级价格参数
     */
    public void setGroup_level(String group_level) {
        this.group_level = group_level == null ? null : group_level.trim();
    }

    /**
     * 获取拼团数据
     *
     * @return group_data - 拼团数据
     */
    public String getGroup_data() {
        return group_data;
    }

    /**
     * 设置拼团数据
     *
     * @param group_data 拼团数据
     */
    public void setGroup_data(String group_data) {
        this.group_data = group_data == null ? null : group_data.trim();
    }

    /**
     * 获取拼团活动编号
     *
     * @return activity_no - 拼团活动编号
     */
    public Integer getActivity_no() {
        return activity_no;
    }

    /**
     * 设置拼团活动编号
     *
     * @param activity_no 拼团活动编号
     */
    public void setActivity_no(Integer activity_no) {
        this.activity_no = activity_no;
    }

    /**
     * 获取平台活动管理id
     *
     * @return platform_activities_id - 平台活动管理id
     */
    public Integer getPlatform_activities_id() {
        return platform_activities_id;
    }

    /**
     * 设置平台活动管理id
     *
     * @param platform_activities_id 平台活动管理id
     */
    public void setPlatform_activities_id(Integer platform_activities_id) {
        this.platform_activities_id = platform_activities_id;
    }
}