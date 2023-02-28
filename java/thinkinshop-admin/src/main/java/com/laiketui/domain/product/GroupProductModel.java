package com.laiketui.domain.product;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 拼团产品
 *
 * @author Trick
 * @date 2021/2/20 11:31
 */
@Table(name = "lkt_group_product")
public class GroupProductModel {
    /**
     * 未开始
     */
    @Transient
    public static final int GROUP_GOODS_STATUS_NO_START = 1;
    /**
     * 活动中
     */
    @Transient
    public static final int GROUP_GOODS_STATUS_UNDER_WAY = 2;
    /**
     * 已结束
     */
    @Transient
    public static final int GROUP_GOODS_STATUS_END = 3;

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
     * 活动标题
     */
    private String group_title;

    /**
     * 规格id
     */
    private Integer attr_id;

    /**
     * 产品id
     */
    private Integer product_id;

    /**
     * 拼团等级价格参数
     */
    private String group_level;

    /**
     * 拼团参数数据
     */
    private String group_data;

    /**
     * 开始日期
     */
    private Date starttime;

    /**
     * 结束日期
     */
    private Date endtime;

    /**
     * 活动编号
     */
    private Integer activity_no;

    /**
     * 活动状态: 1.未开始 2.活动中 3.已结束
     */
    private Integer g_status;

    /**
     * 是否显示
     */
    private Integer is_show;

    /**
     * 是否删除
     */
    private Integer is_delete;

    /**
     * 是否为复制 1为复制 0不是复制
     */
    private Integer is_copy;

    /**
     * 平台活动id
     */
    private Integer platform_activities_id;

    /**
     * 店铺id
     */
    private Integer mch_id;

    /**
     * 审核状态 0待提交 1待审核 2审核通过 3审核拒绝
     */
    private Integer audit_status;

    /**
     * 审核失败原因
     */
    private String reason;

    /**
     * 活动库存
     */
    private Integer num;

    /**
     * 成本价
     */
    private BigDecimal price;

    /**
     * 申请时间
     */
    private Date addtime;

    /**
     * 是否免邮(1 是 0 否)默认0
     */
    private Integer free_freight;

    /**
     * 这个活动的销量拼团成功才加 已拼团
     */
    private Integer sales_volume;

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
     * 获取活动标题
     *
     * @return group_title - 活动标题
     */
    public String getGroup_title() {
        return group_title;
    }

    /**
     * 设置活动标题
     *
     * @param group_title 活动标题
     */
    public void setGroup_title(String group_title) {
        this.group_title = group_title == null ? null : group_title.trim();
    }

    /**
     * 获取规格id
     *
     * @return attr_id - 规格id
     */
    public Integer getAttr_id() {
        return attr_id;
    }

    /**
     * 设置规格id
     *
     * @param attr_id 规格id
     */
    public void setAttr_id(Integer attr_id) {
        this.attr_id = attr_id;
    }

    /**
     * 获取产品id
     *
     * @return product_id - 产品id
     */
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * 设置产品id
     *
     * @param product_id 产品id
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
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
     * 获取拼团参数数据
     *
     * @return group_data - 拼团参数数据
     */
    public String getGroup_data() {
        return group_data;
    }

    /**
     * 设置拼团参数数据
     *
     * @param group_data 拼团参数数据
     */
    public void setGroup_data(String group_data) {
        this.group_data = group_data == null ? null : group_data.trim();
    }

    /**
     * 获取开始日期
     *
     * @return starttime - 开始日期
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * 设置开始日期
     *
     * @param starttime 开始日期
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * 获取结束日期
     *
     * @return endtime - 结束日期
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置结束日期
     *
     * @param endtime 结束日期
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取活动编号
     *
     * @return activity_no - 活动编号
     */
    public Integer getActivity_no() {
        return activity_no;
    }

    /**
     * 设置活动编号
     *
     * @param activity_no 活动编号
     */
    public void setActivity_no(Integer activity_no) {
        this.activity_no = activity_no;
    }

    /**
     * 获取活动状态: 1.未开始 2.活动中 3.已结束
     *
     * @return g_status - 活动状态: 1.未开始 2.活动中 3.已结束
     */
    public Integer getG_status() {
        return g_status;
    }

    /**
     * 设置活动状态: 1.未开始 2.活动中 3.已结束
     *
     * @param g_status 活动状态: 1.未开始 2.活动中 3.已结束
     */
    public void setG_status(Integer g_status) {
        this.g_status = g_status;
    }

    /**
     * 获取是否显示
     *
     * @return is_show - 是否显示
     */
    public Integer getIs_show() {
        return is_show;
    }

    /**
     * 设置是否显示
     *
     * @param is_show 是否显示
     */
    public void setIs_show(Integer is_show) {
        this.is_show = is_show;
    }

    /**
     * 获取是否删除
     *
     * @return is_delete - 是否删除
     */
    public Integer getIs_delete() {
        return is_delete;
    }

    /**
     * 设置是否删除
     *
     * @param is_delete 是否删除
     */
    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }

    /**
     * 获取是否为复制 1为复制 0不是复制
     *
     * @return is_copy - 是否为复制 1为复制 0不是复制
     */
    public Integer getIs_copy() {
        return is_copy;
    }

    /**
     * 设置是否为复制 1为复制 0不是复制
     *
     * @param is_copy 是否为复制 1为复制 0不是复制
     */
    public void setIs_copy(Integer is_copy) {
        this.is_copy = is_copy;
    }

    /**
     * 获取平台活动id
     *
     * @return platform_activities_id - 平台活动id
     */
    public Integer getPlatform_activities_id() {
        return platform_activities_id;
    }

    /**
     * 设置平台活动id
     *
     * @param platform_activities_id 平台活动id
     */
    public void setPlatform_activities_id(Integer platform_activities_id) {
        this.platform_activities_id = platform_activities_id;
    }

    /**
     * 获取店铺id
     *
     * @return mch_id - 店铺id
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺id
     *
     * @param mch_id 店铺id
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取审核状态 0待提交 1待审核 2审核通过 3审核拒绝
     *
     * @return audit_status - 审核状态 0待提交 1待审核 2审核通过 3审核拒绝
     */
    public Integer getAudit_status() {
        return audit_status;
    }

    /**
     * 设置审核状态 0待提交 1待审核 2审核通过 3审核拒绝
     *
     * @param audit_status 审核状态 0待提交 1待审核 2审核通过 3审核拒绝
     */
    public void setAudit_status(Integer audit_status) {
        this.audit_status = audit_status;
    }

    /**
     * 获取审核失败原因
     *
     * @return reason - 审核失败原因
     */
    public String getReason() {
        return reason;
    }

    /**
     * 设置审核失败原因
     *
     * @param reason 审核失败原因
     */
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }

    /**
     * 获取活动库存
     *
     * @return num - 活动库存
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置活动库存
     *
     * @param num 活动库存
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取成本价
     *
     * @return price - 成本价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置成本价
     *
     * @param price 成本价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取申请时间
     *
     * @return addtime - 申请时间
     */
    public Date getAddtime() {
        return addtime;
    }

    /**
     * 设置申请时间
     *
     * @param addtime 申请时间
     */
    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    /**
     * 获取是否免邮(1 是 0 否)默认0
     *
     * @return free_freight - 是否免邮(1 是 0 否)默认0
     */
    public Integer getFree_freight() {
        return free_freight;
    }

    /**
     * 设置是否免邮(1 是 0 否)默认0
     *
     * @param free_freight 是否免邮(1 是 0 否)默认0
     */
    public void setFree_freight(Integer free_freight) {
        this.free_freight = free_freight;
    }

    /**
     * 获取这个活动的销量拼团成功才加 已拼团
     *
     * @return sales_volume - 这个活动的销量拼团成功才加 已拼团
     */
    public Integer getSales_volume() {
        return sales_volume;
    }

    /**
     * 设置这个活动的销量拼团成功才加 已拼团
     *
     * @param sales_volume 这个活动的销量拼团成功才加 已拼团
     */
    public void setSales_volume(Integer sales_volume) {
        this.sales_volume = sales_volume;
    }
}