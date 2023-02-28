package com.laiketui.domain.coupon;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lkt_coupon_activity")
public class CouponActivityModel {

    /**
     * 全部商品
     */
    @Transient
    public static final int USE_RANG_TYPE_ALL = 1;
    /**
     * 指定商品
     */
    @Transient
    public static final int USE_RANG_TYPE_PRODUCT = 2;
    /**
     * 指定分类
     */
    @Transient
    public static final int USE_RANG_TYPE_CLASS = 3;


    /**
     * 未启用
     */
    @Transient
    public static final int COUPON_ACTIVITY_STATUS_NOT_USE = 0;
    /**
     * 启用
     */
    @Transient
    public static final int COUPON_ACTIVITY_STATUS_OPEN = 1;
    /**
     * 禁用
     */
    @Transient
    public static final int COUPON_ACTIVITY_STATUS_DISABLE = 2;
    /**
     * 已结束
     */
    @Transient
    public static final int COUPON_ACTIVITY_STATUS_END = 3;

    /**
     * 不显示
     */
    @Transient
    public static final int ACTIVITY_IS_DISPLAY_NOT_SHOW = 0;
    /**
     * 显示
     */
    @Transient
    public static final int ACTIVITY_IS_DISPLAY_SHOW = 1;

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
     * 店铺id
     */
    private Integer mch_id;

    /**
     * 优惠券名称
     */
    private String name;

    /**
     * 优惠券类型
     * 1免邮 2满减 3折扣 4会员赠送
     */
    private Integer activity_type;

    /**
     * 会员等级ID
     */
    private Integer grade_id;

    /**
     * 优惠券面值
     */
    private BigDecimal money;

    /**
     * 折扣值
     */
    private BigDecimal discount;

    /**
     * 消费满多少
     */
    private BigDecimal z_money;

    /**
     * 满多少赠券
     */
    private Integer shopping;

    /**
     * 免邮任务 0：完善资料 1：绑定手机
     */
    private Integer free_mail_task;

    /**
     * 发行数量
     */
    private Integer circulation;

    /**
     * 剩余数量
     */
    private Integer num;

    /**
     * 领取限制
     */
    private Integer receive;

    /**
     * 使用限制
     */
    private Integer use_num;

    /**
     * 开始时间
     */
    private Date start_time;

    /**
     * 结束时间
     */
    private Date end_time;

    /**
     * 优惠券使用范围 1：全部商品 2:指定商品 3：指定分类
     */
    private Integer type;

    /**
     * 活动指定商品类型id
     */
    private String product_class_id;

    /**
     * 活动指定商品id
     */
    private String product_id;

    /**
     * 备注
     */
    private String content;

    /**
     * 跳转方式 1.首页 2.
     * <p>
     * 自定义
     */
    private Integer skip_type;

    /**
     * 跳转路径
     */
    private String url;

    /**
     * 状态 0：未启用 1：启用 2:禁用 3：已结束
     */
    private Integer status;

    /**
     * 修改时间
     */
    private Date add_time;

    /**
     * 资格
     */
    private String qualifications;

    /**
     * 回收站 0.不回收 1.回收
     */
    private Integer recycle;

    /**
     * 有效时间
     */
    private Integer day;

    /**
     * 使用说明
     */
    @Column(name = "Instructions")
    private String instructions;

    /**
     * 是否显示 0.不显示 1.显示
     */
    @Column(name = "is_display")
    private Integer is_display;

    public Integer getIs_display() {
        return is_display;
    }

    public void setIs_display(Integer is_display) {
        this.is_display = is_display;
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
     * 获取优惠券名称
     *
     * @return name - 优惠券名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置优惠券名称
     *
     * @param name 优惠券名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取优惠券类型
     *
     * @return activity_type - 优惠券类型
     */
    public Integer getActivity_type() {
        return activity_type;
    }

    /**
     * 设置优惠券类型
     *
     * @param activity_type 优惠券类型
     */
    public void setActivity_type(Integer activity_type) {
        this.activity_type = activity_type;
    }

    /**
     * 获取会员等级ID
     *
     * @return grade_id - 会员等级ID
     */
    public Integer getGrade_id() {
        return grade_id;
    }

    /**
     * 设置会员等级ID
     *
     * @param grade_id 会员等级ID
     */
    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    /**
     * 获取优惠券面值
     *
     * @return money - 优惠券面值
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置优惠券面值
     *
     * @param money 优惠券面值
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取折扣值
     *
     * @return discount - 折扣值
     */
    public BigDecimal getDiscount() {
        return discount;
    }

    /**
     * 设置折扣值
     *
     * @param discount 折扣值
     */
    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    /**
     * 获取消费满多少
     *
     * @return z_money - 消费满多少
     */
    public BigDecimal getZ_money() {
        return z_money;
    }

    /**
     * 设置消费满多少
     *
     * @param z_money 消费满多少
     */
    public void setZ_money(BigDecimal z_money) {
        this.z_money = z_money;
    }

    /**
     * 获取满多少赠券
     *
     * @return shopping - 满多少赠券
     */
    public Integer getShopping() {
        return shopping;
    }

    /**
     * 设置满多少赠券
     *
     * @param shopping 满多少赠券
     */
    public void setShopping(Integer shopping) {
        this.shopping = shopping;
    }

    /**
     * 获取免邮任务 0：完善资料 1：绑定手机
     *
     * @return free_mail_task - 免邮任务 0：完善资料 1：绑定手机
     */
    public Integer getFree_mail_task() {
        return free_mail_task;
    }

    /**
     * 设置免邮任务 0：完善资料 1：绑定手机
     *
     * @param free_mail_task 免邮任务 0：完善资料 1：绑定手机
     */
    public void setFree_mail_task(Integer free_mail_task) {
        this.free_mail_task = free_mail_task;
    }

    /**
     * 获取发行数量
     *
     * @return circulation - 发行数量
     */
    public Integer getCirculation() {
        return circulation;
    }

    /**
     * 设置发行数量
     *
     * @param circulation 发行数量
     */
    public void setCirculation(Integer circulation) {
        this.circulation = circulation;
    }

    /**
     * 获取剩余数量
     *
     * @return num - 剩余数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置剩余数量
     *
     * @param num 剩余数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取领取限制
     * 为0代表没有领取限制
     * @return receive - 领取限制
     */
    public Integer getReceive() {
        return receive;
    }

    /**
     * 设置领取限制
     *
     * @param receive 领取限制
     */
    public void setReceive(Integer receive) {
        this.receive = receive;
    }

    /**
     * 获取使用限制
     *
     * @return use_num - 使用限制
     */
    public Integer getUse_num() {
        return use_num;
    }

    /**
     * 设置使用限制
     *
     * @param use_num 使用限制
     */
    public void setUse_num(Integer use_num) {
        this.use_num = use_num;
    }

    /**
     * 获取开始时间
     *
     * @return start_time - 开始时间
     */
    public Date getStart_time() {
        return start_time;
    }

    /**
     * 设置开始时间
     *
     * @param start_time 开始时间
     */
    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    /**
     * 获取结束时间
     *
     * @return end_time - 结束时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 设置结束时间
     *
     * @param end_time 结束时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 获取优惠券使用范围 1：全部商品 2:指定商品 3：指定分类
     *
     * @return type - 优惠券使用范围 1：全部商品 2:指定商品 3：指定分类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置优惠券使用范围 1：全部商品 2:指定商品 3：指定分类
     *
     * @param type 优惠券使用范围 1：全部商品 2:指定商品 3：指定分类
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取活动指定商品类型id
     *
     * @return product_class_id - 活动指定商品类型id
     */
    public String getProduct_class_id() {
        return product_class_id;
    }

    /**
     * 设置活动指定商品类型id
     *
     * @param product_class_id 活动指定商品类型id
     */
    public void setProduct_class_id(String product_class_id) {
        this.product_class_id = product_class_id == null ? null : product_class_id.trim();
    }

    /**
     * 获取活动指定商品id
     *
     * @return product_id - 活动指定商品id
     */
    public String getProduct_id() {
        return product_id;
    }

    /**
     * 设置活动指定商品id
     *
     * @param product_id 活动指定商品id
     */
    public void setProduct_id(String product_id) {
        this.product_id = product_id == null ? null : product_id.trim();
    }

    /**
     * 获取备注
     *
     * @return content - 备注
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置备注
     *
     * @param content 备注
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取跳转方式 1.首页 2.
     * <p>
     * 自定义
     *
     * @return skip_type - 跳转方式 1.首页 2.
     * <p>
     * 自定义
     */
    public Integer getSkip_type() {
        return skip_type;
    }

    /**
     * 设置跳转方式 1.首页 2.
     * <p>
     * 自定义
     *
     * @param skip_type 跳转方式 1.首页 2.
     *                  <p>
     *                  自定义
     */
    public void setSkip_type(Integer skip_type) {
        this.skip_type = skip_type;
    }

    /**
     * 获取跳转路径
     *
     * @return url - 跳转路径
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置跳转路径
     *
     * @param url 跳转路径
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取状态 0：未启用 1：启用 2:禁用 3：已结束
     *
     * @return status - 状态 0：未启用 1：启用 2:禁用 3：已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0：未启用 1：启用 2:禁用 3：已结束
     *
     * @param status 状态 0：未启用 1：启用 2:禁用 3：已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取修改时间
     *
     * @return add_time - 修改时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置修改时间
     *
     * @param add_time 修改时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * 获取资格
     *
     * @return qualifications - 资格
     */
    public String getQualifications() {
        return qualifications;
    }

    /**
     * 设置资格
     *
     * @param qualifications 资格
     */
    public void setQualifications(String qualifications) {
        this.qualifications = qualifications == null ? null : qualifications.trim();
    }

    /**
     * 获取回收站 0.不回收 1.回收
     *
     * @return recycle - 回收站 0.不回收 1.回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0.不回收 1.回收
     *
     * @param recycle 回收站 0.不回收 1.回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    /**
     * 获取有效时间
     *
     * @return day - 有效时间
     */
    public Integer getDay() {
        return day;
    }

    /**
     * 设置有效时间
     *
     * @param day 有效时间
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * 获取使用说明
     *
     * @return Instructions - 使用说明
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * 设置使用说明
     *
     * @param instructions 使用说明
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions == null ? null : instructions.trim();
    }
}