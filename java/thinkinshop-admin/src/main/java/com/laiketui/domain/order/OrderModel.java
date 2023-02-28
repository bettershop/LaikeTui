package com.laiketui.domain.order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lkt_order")
public class OrderModel {


    /**
     * 显示
     */
    @Transient
    public final static int RECYCLE_SHOW = 0;
    /**
     * 系统回收
     */
    @Transient
    public final static int RECYCLE_SYSTEM = 1;
    /**
     * 用户回收
     */
    @Transient
    public final static int RECYCLE_USER = 2;
    /**
     * 店铺回收
     */
    @Transient
    public final static int RECYCLE_MCH = 3;

    /**
     * 配送
     */
    @Transient
    public final static int SELF_LIFTING_DISTRIBUTION = 0;
    /**
     * 自提
     */
    @Transient
    public final static int SELF_LIFTING_PICKED_UP = 1;

    /**
     * 未支付 0:未付款 1:未发货 2:待收货 5:已完成 7:订单关闭
     */
    @Transient
    public static final int ORDER_UNPAY = 0;
    /**
     * 未发货
     */
    @Transient
    public final static int ORDER_PAYED = 1;
    /**
     * 待收货
     */
    @Transient
    public final static int ORDER_UNRECEIVE = 2;
    /**
     * 已完成
     */
    @Transient
    public final static int ORDER_FINISH = 5;

    /**
     * 订单关闭
     */
    @Transient
    public final static int ORDER_CLOSE = 7;


    /**
     * 未读
     */
    @Transient
    public final static int READD_UNREAD = 0;
    /**
     * 已读
     */
    @Transient
    public final static int READ = 1;

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
     * 用户id
     */
    private String user_id;

    /**
     * 姓名
     */
    private String name;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 数量
     */
    private Integer num;

    /**
     * 总价
     */
    private BigDecimal z_price;

    /**
     * 订单号
     */
    @Column(name = "sno")
    private String sNo;

    /**
     * 省
     */
    private String sheng;

    /**
     * 市
     */
    private String shi;

    /**
     * 县
     */
    private String xian;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 用户备注
     */
    private String remark;

    /**
     * 支付方式
     */
    private String pay;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * 支付时间
     */
    private Date pay_time;

    /**
     * 到货时间
     */
    private Date arrive_time;

    /**
     * 状态 0:未付款 1:未发货 2:待收货 5:已完成  7:订单关闭
     */
    private Integer status;

    /**
     * 优惠券id
     */
    private String coupon_id;

    /**
     * 满减活动ID
     */
    private Integer subtraction_id;

    /**
     * 消费金
     */
    private Integer consumer_money;

    /**
     * 满减活动名称
     */
    private String coupon_activity_name;

    /**
     * 活动ID
     */
    private Integer drawid;

    /**
     * 订单类型
     */
    private String otype;

    /**
     * 拼团编号
     */
    private String ptcode;

    /**
     * 拼团订单类型:kaituan开团 cantuan参团
     */
    private String pid;

    /**
     * 拼团状态:0,未付款 1,拼团中 2,拼团成功 3,拼团失败
     */
    private Short ptstatus;

    /**
     * 拼团人数
     */
    private String groupman;

    /**
     * 拼团退款单号
     */
    @Column(name = "refundsNo")
    private String refundsNo;

    /**
     * 微信支付单号
     */
    private String trade_no;

    /**
     * 是否匿名(0,否  1.是
     */
    private Integer is_anonymous;

    /**
     * 物流服务
     */
    private Integer logistics_service;

    /**
     * 总体评价
     */
    private Integer overall_evaluation;

    /**
     * 商品总价
     */
    private BigDecimal spz_price;

    /**
     * 查询出的满减金额
     */
    private BigDecimal reduce_price;

    /**
     * 查询出的优惠券金额
     */
    private BigDecimal coupon_price;

    /**
     * 红包金额
     */
    private String red_packet;

    /**
     * 积分
     */
    private Integer allow;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 来源 1.小程序 2.app 3.支付宝小程序 4.头条小程序 5.百度小程序 6.pc端 7.H5
     */
    private Integer source;

    /**
     * 提醒发货
     */
    private Integer delivery_status;

    /**
     * 是否已读（0，未读  1 已读）
     */
    private Integer readd;

    /**
     * 提醒
     * <p>
     * 发货时间间隔
     */
    private Date remind;

    /**
     * 抵扣余额
     */
    private BigDecimal offset_balance;

    /**
     * 店铺ID
     */
    private String mch_id;

    /**
     * 分销折扣
     */
    private BigDecimal zhekou;

    /**
     * 会员等级折扣
     */
    private BigDecimal grade_rate;

    /**
     * 会员购物积分
     */
    private Integer grade_score;

    /**
     * 会员返现金额
     */
    private BigDecimal grade_fan;

    /**
     * 父类订单号
     */
    @Column(name = "p_sNo")
    private String p_sNo;

    /**
     * 砍价活动id
     */
    private Integer bargain_id;

    /**
     * 分销折扣
     */
    private BigDecimal comm_discount;

    /**
     * 调起支付所用订单号
     */
    private String real_sno;

    /**
     * 官方订单号
     */
    @Column(name = "orderId")
    private String orderId;

    /**
     * 百度用户ID
     */
    @Column(name = "baiduId")
    private String baiduId;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * 自提 0.配送 1.自提
     */
    private Integer self_lifting;

    /**
     * 提现码
     */
    private String extraction_code;

    /**
     * 提现码二维码
     */
    private String extraction_code_img;

    /**
     * 是否发放佣金 0.否 1.是
     */
    private Integer is_put;

    /**
     * 总运费
     */
    private BigDecimal z_freight;

    /**
     * 优惠金额
     */
    private BigDecimal preferential_amount;

    /**
     * 回收站 0.显示 1.回收
     */
    private Integer recycle;

    /**
     * 下单门店
     */
    @Column(name = "single_store")
    private Integer single_store;

    /**
     * 取货门店
     */
    private Integer pick_up_store;

    /**
     * 是否结算 1=结算
     */
    @Column(name = "settlement_status")
    private Integer settlement_status;


    /**
     * 下单类型1用户下单2店铺下单3平台下单
     */
    @Column(name = "operation_type")
    private Integer operation_type;

    /**
     * 代客下单手动优惠金额
     */
    @Column(name = "manual_offer")
    private BigDecimal manual_offer;

    public BigDecimal getManual_offer() {
        return manual_offer;
    }

    public void setManual_offer(BigDecimal manual_offer) {
        this.manual_offer = manual_offer;
    }

    public Integer getSettlement_status() {
        return settlement_status;
    }

    public void setSettlement_status(Integer settlement_status) {
        this.settlement_status = settlement_status;
    }

    public Integer getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(Integer operation_type) {
        this.operation_type = operation_type;
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
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户id
     *
     * @param user_id 用户id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
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
     * 获取联系电话
     *
     * @return mobile - 联系电话
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * 设置联系电话
     *
     * @param mobile 联系电话
     */
    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置数量 - 商品总数量
     *
     * @param num 数量
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    /**
     * 获取总价
     *
     * @return z_price - 总价
     */
    public BigDecimal getZ_price() {
        return z_price;
    }

    /**
     * 设置总价
     *
     * @param z_price 总价
     */
    public void setZ_price(BigDecimal z_price) {
        this.z_price = z_price;
    }

    /**
     * 获取订单号
     *
     * @return sNo - 订单号
     */
    public String getsNo() {
        return sNo;
    }

    /**
     * 设置订单号
     *
     * @param sNo 订单号
     */
    public void setsNo(String sNo) {
        this.sNo = sNo == null ? null : sNo.trim();
    }

    /**
     * 获取省
     *
     * @return sheng - 省
     */
    public String getSheng() {
        return sheng;
    }

    /**
     * 设置省
     *
     * @param sheng 省
     */
    public void setSheng(String sheng) {
        this.sheng = sheng == null ? null : sheng.trim();
    }

    /**
     * 获取市
     *
     * @return shi - 市
     */
    public String getShi() {
        return shi;
    }

    /**
     * 设置市
     *
     * @param shi 市
     */
    public void setShi(String shi) {
        this.shi = shi == null ? null : shi.trim();
    }

    /**
     * 获取县
     *
     * @return xian - 县
     */
    public String getXian() {
        return xian;
    }

    /**
     * 设置县
     *
     * @param xian 县
     */
    public void setXian(String xian) {
        this.xian = xian == null ? null : xian.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取用户备注
     *
     * @return remark - 用户备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置用户备注
     *
     * @param remark 用户备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取支付方式
     *
     * @return pay - 支付方式
     */
    public String getPay() {
        return pay;
    }

    /**
     * 设置支付方式
     *
     * @param pay 支付方式
     */
    public void setPay(String pay) {
        this.pay = pay == null ? null : pay.trim();
    }

    /**
     * 获取添加时间
     *
     * @return add_time - 添加时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置添加时间
     *
     * @param add_time 添加时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * 获取支付时间
     *
     * @return pay_time - 支付时间
     */
    public Date getPay_time() {
        return pay_time;
    }

    /**
     * 设置支付时间
     *
     * @param pay_time 支付时间
     */
    public void setPay_time(Date pay_time) {
        this.pay_time = pay_time;
    }

    /**
     * 获取到货时间
     *
     * @return arrive_time - 到货时间
     */
    public Date getArrive_time() {
        return arrive_time;
    }

    /**
     * 设置到货时间
     *
     * @param arrive_time 到货时间
     */
    public void setArrive_time(Date arrive_time) {
        this.arrive_time = arrive_time;
    }

    /**
     * 获取状态 0:未付款 1:未发货 2:待收货 5:已完成  7:订单关闭
     *
     * @return status - 状态 0:未付款 1:未发货 2:待收货 5:已完成  7:订单关闭
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态 0:未付款 1:未发货 2:待收货 5:已完成  7:订单关闭
     *
     * @param status 状态 0:未付款 1:未发货 2:待收货 5:已完成  7:订单关闭
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取优惠券id
     *
     * @return coupon_id - 优惠券id
     */
    public String getCoupon_id() {
        return coupon_id;
    }

    /**
     * 设置优惠券id
     *
     * @param coupon_id 优惠券id
     */
    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id == null ? null : coupon_id.trim();
    }

    /**
     * 获取满减活动ID
     *
     * @return subtraction_id - 满减活动ID
     */
    public Integer getSubtraction_id() {
        return subtraction_id;
    }

    /**
     * 设置满减活动ID
     *
     * @param subtraction_id 满减活动ID
     */
    public void setSubtraction_id(Integer subtraction_id) {
        this.subtraction_id = subtraction_id;
    }

    /**
     * 获取消费金
     *
     * @return consumer_money - 消费金
     */
    public Integer getConsumer_money() {
        return consumer_money;
    }

    /**
     * 设置消费金
     *
     * @param consumer_money 消费金
     */
    public void setConsumer_money(Integer consumer_money) {
        this.consumer_money = consumer_money;
    }

    /**
     * 获取满减活动名称
     *
     * @return coupon_activity_name - 满减活动名称
     */
    public String getCoupon_activity_name() {
        return coupon_activity_name;
    }

    /**
     * 设置满减活动名称
     *
     * @param coupon_activity_name 满减活动名称
     */
    public void setCoupon_activity_name(String coupon_activity_name) {
        this.coupon_activity_name = coupon_activity_name == null ? null : coupon_activity_name.trim();
    }

    /**
     * 获取活动ID
     *
     * @return drawid - 活动ID
     */
    public Integer getDrawid() {
        return drawid;
    }

    /**
     * 设置活动ID
     *
     * @param drawid 活动ID
     */
    public void setDrawid(Integer drawid) {
        this.drawid = drawid;
    }

    /**
     * 获取订单类型
     *
     * @return otype - 订单类型
     */
    public String getOtype() {
        return otype;
    }

    /**
     * 设置订单类型
     *
     * @param otype 订单类型
     */
    public void setOtype(String otype) {
        this.otype = otype == null ? null : otype.trim();
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
     * 获取拼团订单类型:kaituan开团 cantuan参团
     *
     * @return pid - 拼团订单类型:kaituan开团 cantuan参团
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置拼团订单类型:kaituan开团 cantuan参团
     *
     * @param pid 拼团订单类型:kaituan开团 cantuan参团
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * 获取拼团状态:0,未付款 1,拼团中 2,拼团成功 3,拼团失败
     *
     * @return ptstatus - 拼团状态:0,未付款 1,拼团中 2,拼团成功 3,拼团失败
     */
    public Short getPtstatus() {
        return ptstatus;
    }

    /**
     * 设置拼团状态:0,未付款 1,拼团中 2,拼团成功 3,拼团失败
     *
     * @param ptstatus 拼团状态:0,未付款 1,拼团中 2,拼团成功 3,拼团失败
     */
    public void setPtstatus(Short ptstatus) {
        this.ptstatus = ptstatus;
    }

    /**
     * 获取拼团人数
     *
     * @return groupman - 拼团人数
     */
    public String getGroupman() {
        return groupman;
    }

    /**
     * 设置拼团人数
     *
     * @param groupman 拼团人数
     */
    public void setGroupman(String groupman) {
        this.groupman = groupman == null ? null : groupman.trim();
    }

    /**
     * 获取拼团退款单号
     *
     * @return refundsNo - 拼团退款单号
     */
    public String getRefundsNo() {
        return refundsNo;
    }

    /**
     * 设置拼团退款单号
     *
     * @param refundsNo 拼团退款单号
     */
    public void setRefundsNo(String refundsNo) {
        this.refundsNo = refundsNo == null ? null : refundsNo.trim();
    }

    /**
     * 获取微信支付单号
     *
     * @return trade_no - 微信支付单号
     */
    public String getTrade_no() {
        return trade_no;
    }

    /**
     * 设置微信支付单号
     *
     * @param trade_no 微信支付单号
     */
    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no == null ? null : trade_no.trim();
    }

    /**
     * 获取是否匿名(0,否  1.是
     *
     * @return is_anonymous - 是否匿名(0,否  1.是
     */
    public Integer getIs_anonymous() {
        return is_anonymous;
    }

    /**
     * 设置是否匿名(0,否  1.是
     *
     * @param is_anonymous 是否匿名(0,否  1.是
     */
    public void setIs_anonymous(Integer is_anonymous) {
        this.is_anonymous = is_anonymous;
    }

    /**
     * 获取物流服务
     *
     * @return logistics_service - 物流服务
     */
    public Integer getLogistics_service() {
        return logistics_service;
    }

    /**
     * 设置物流服务
     *
     * @param logistics_service 物流服务
     */
    public void setLogistics_service(Integer logistics_service) {
        this.logistics_service = logistics_service;
    }

    /**
     * 获取总体评价
     *
     * @return overall_evaluation - 总体评价
     */
    public Integer getOverall_evaluation() {
        return overall_evaluation;
    }

    /**
     * 设置总体评价
     *
     * @param overall_evaluation 总体评价
     */
    public void setOverall_evaluation(Integer overall_evaluation) {
        this.overall_evaluation = overall_evaluation;
    }

    /**
     * 获取商品总价
     *
     * @return spz_price - 商品总价
     */
    public BigDecimal getSpz_price() {
        return spz_price;
    }

    /**
     * 设置商品总价
     *
     * @param spz_price 商品总价
     */
    public void setSpz_price(BigDecimal spz_price) {
        this.spz_price = spz_price;
    }

    /**
     * 获取查询出的满减金额
     *
     * @return reduce_price - 查询出的满减金额
     */
    public BigDecimal getReduce_price() {
        return reduce_price;
    }

    /**
     * 设置查询出的满减金额
     *
     * @param reduce_price 查询出的满减金额
     */
    public void setReduce_price(BigDecimal reduce_price) {
        this.reduce_price = reduce_price;
    }

    /**
     * 获取查询出的优惠券金额
     *
     * @return coupon_price - 查询出的优惠券金额
     */
    public BigDecimal getCoupon_price() {
        return coupon_price;
    }

    /**
     * 设置查询出的优惠券金额
     *
     * @param coupon_price 查询出的优惠券金额
     */
    public void setCoupon_price(BigDecimal coupon_price) {
        this.coupon_price = coupon_price;
    }

    /**
     * 获取红包金额
     *
     * @return red_packet - 红包金额
     */
    public String getRed_packet() {
        return red_packet;
    }

    /**
     * 设置红包金额
     *
     * @param red_packet 红包金额
     */
    public void setRed_packet(String red_packet) {
        this.red_packet = red_packet == null ? null : red_packet.trim();
    }

    /**
     * 获取积分
     *
     * @return allow - 积分
     */
    public Integer getAllow() {
        return allow;
    }

    /**
     * 设置积分
     *
     * @param allow 积分
     */
    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    /**
     * 获取参数
     *
     * @return parameter - 参数
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * 设置参数
     *
     * @param parameter 参数
     */
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    /**
     * 获取来源 1.小程序 2.app 3.支付宝小程序 4.头条小程序 5.百度小程序 6.pc端 7.H5
     *
     * @return source - 来源 1.小程序 2.app 3.支付宝小程序 4.头条小程序 5.百度小程序 6.pc端 7.H5
     */
    public Integer getSource() {
        return source;
    }

    /**
     * 设置来源 1.小程序 2.app 3.支付宝小程序 4.头条小程序 5.百度小程序 6.pc端 7.H5
     *
     * @param source 来源 1.小程序 2.app 3.支付宝小程序 4.头条小程序 5.百度小程序 6.pc端 7.H5
     */
    public void setSource(Integer source) {
        this.source = source;
    }

    /**
     * 获取提醒发货
     *
     * @return delivery_status - 提醒发货
     */
    public Integer getDelivery_status() {
        return delivery_status;
    }

    /**
     * 设置提醒发货
     *
     * @param delivery_status 提醒发货
     */
    public void setDelivery_status(Integer delivery_status) {
        this.delivery_status = delivery_status;
    }

    /**
     * 获取是否已读（0，未读  1 已读）
     *
     * @return readd - 是否已读（0，未读  1 已读）
     */
    public Integer getReadd() {
        return readd;
    }

    /**
     * 设置是否已读（0，未读  1 已读）
     *
     * @param readd 是否已读（0，未读  1 已读）
     */
    public void setReadd(Integer readd) {
        this.readd = readd;
    }

    /**
     * 获取提醒
     * <p>
     * 发货时间间隔
     *
     * @return remind - 提醒
     * <p>
     * 发货时间间隔
     */
    public Date getRemind() {
        return remind;
    }

    /**
     * 设置提醒
     * <p>
     * 发货时间间隔
     *
     * @param remind 提醒
     *               <p>
     *               发货时间间隔
     */
    public void setRemind(Date remind) {
        this.remind = remind;
    }

    /**
     * 获取抵扣余额
     *
     * @return offset_balance - 抵扣余额
     */
    public BigDecimal getOffset_balance() {
        return offset_balance;
    }

    /**
     * 设置抵扣余额
     *
     * @param offset_balance 抵扣余额
     */
    public void setOffset_balance(BigDecimal offset_balance) {
        this.offset_balance = offset_balance;
    }

    /**
     * 获取店铺ID
     *
     * @return mch_id - 店铺ID
     */
    public String getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺ID
     *
     * @param mch_id 店铺ID
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id == null ? null : mch_id.trim();
    }

    /**
     * 获取分销折扣
     *
     * @return zhekou - 分销折扣
     */
    public BigDecimal getZhekou() {
        return zhekou;
    }

    /**
     * 设置分销折扣
     *
     * @param zhekou 分销折扣
     */
    public void setZhekou(BigDecimal zhekou) {
        this.zhekou = zhekou;
    }

    /**
     * 获取会员等级折扣
     *
     * @return grade_rate - 会员等级折扣
     */
    public BigDecimal getGrade_rate() {
        return grade_rate;
    }

    /**
     * 设置会员等级折扣
     *
     * @param grade_rate 会员等级折扣
     */
    public void setGrade_rate(BigDecimal grade_rate) {
        this.grade_rate = grade_rate;
    }

    /**
     * 获取会员购物积分
     *
     * @return grade_score - 会员购物积分
     */
    public Integer getGrade_score() {
        return grade_score;
    }

    /**
     * 设置会员购物积分
     *
     * @param grade_score 会员购物积分
     */
    public void setGrade_score(Integer grade_score) {
        this.grade_score = grade_score;
    }

    /**
     * 获取会员返现金额
     *
     * @return grade_fan - 会员返现金额
     */
    public BigDecimal getGrade_fan() {
        return grade_fan;
    }

    /**
     * 设置会员返现金额
     *
     * @param grade_fan 会员返现金额
     */
    public void setGrade_fan(BigDecimal grade_fan) {
        this.grade_fan = grade_fan;
    }

    /**
     * 获取父类订单号
     *
     * @return p_sNo - 父类订单号
     */
    public String getP_sNo() {
        return p_sNo;
    }

    /**
     * 设置父类订单号
     *
     * @param p_sNo 父类订单号
     */
    public void setP_sNo(String p_sNo) {
        this.p_sNo = p_sNo == null ? null : p_sNo.trim();
    }

    /**
     * 获取砍价活动id
     *
     * @return bargain_id - 砍价活动id
     */
    public Integer getBargain_id() {
        return bargain_id;
    }

    /**
     * 设置砍价活动id
     *
     * @param bargain_id 砍价活动id
     */
    public void setBargain_id(Integer bargain_id) {
        this.bargain_id = bargain_id;
    }

    /**
     * 获取分销折扣
     *
     * @return comm_discount - 分销折扣
     */
    public BigDecimal getComm_discount() {
        return comm_discount;
    }

    /**
     * 设置分销折扣
     *
     * @param comm_discount 分销折扣
     */
    public void setComm_discount(BigDecimal comm_discount) {
        this.comm_discount = comm_discount;
    }

    /**
     * 获取调起支付所用订单号
     *
     * @return real_sno - 调起支付所用订单号
     */
    public String getReal_sno() {
        return real_sno;
    }

    /**
     * 设置调起支付所用订单号
     *
     * @param real_sno 调起支付所用订单号
     */
    public void setReal_sno(String real_sno) {
        this.real_sno = real_sno == null ? null : real_sno.trim();
    }

    /**
     * 获取官方订单号
     *
     * @return orderId - 官方订单号
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * 设置官方订单号
     *
     * @param orderId 官方订单号
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    /**
     * 获取百度用户ID
     *
     * @return baiduId - 百度用户ID
     */
    public String getBaiduId() {
        return baiduId;
    }

    /**
     * 设置百度用户ID
     *
     * @param baiduId 百度用户ID
     */
    public void setBaiduId(String baiduId) {
        this.baiduId = baiduId == null ? null : baiduId.trim();
    }

    /**
     * 获取订单备注
     *
     * @return remarks - 订单备注
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * 设置订单备注
     *
     * @param remarks 订单备注
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    /**
     * 获取自提 0.配送 1.自提
     *
     * @return self_lifting - 自提 0.配送 1.自提
     */
    public Integer getSelf_lifting() {
        return self_lifting;
    }

    /**
     * 设置自提 0.配送 1.自提
     *
     * @param self_lifting 自提 0.配送 1.自提
     */
    public void setSelf_lifting(Integer self_lifting) {
        this.self_lifting = self_lifting;
    }

    /**
     * 获取提现码
     *
     * @return extraction_code - 提现码
     */
    public String getExtraction_code() {
        return extraction_code;
    }

    /**
     * 设置提现码
     *
     * @param extraction_code 提现码
     */
    public void setExtraction_code(String extraction_code) {
        this.extraction_code = extraction_code == null ? null : extraction_code.trim();
    }

    /**
     * 获取提现码二维码
     *
     * @return extraction_code_img - 提现码二维码
     */
    public String getExtraction_code_img() {
        return extraction_code_img;
    }

    /**
     * 设置提现码二维码
     *
     * @param extraction_code_img 提现码二维码
     */
    public void setExtraction_code_img(String extraction_code_img) {
        this.extraction_code_img = extraction_code_img == null ? null : extraction_code_img.trim();
    }

    /**
     * 获取是否发放佣金 0.否 1.是
     *
     * @return is_put - 是否发放佣金 0.否 1.是
     */
    public Integer getIs_put() {
        return is_put;
    }

    /**
     * 设置是否发放佣金 0.否 1.是
     *
     * @param is_put 是否发放佣金 0.否 1.是
     */
    public void setIs_put(Integer is_put) {
        this.is_put = is_put;
    }

    /**
     * 获取总运费
     *
     * @return z_freight - 总运费
     */
    public BigDecimal getZ_freight() {
        return z_freight;
    }

    /**
     * 设置总运费
     *
     * @param z_freight 总运费
     */
    public void setZ_freight(BigDecimal z_freight) {
        this.z_freight = z_freight;
    }

    /**
     * 获取优惠金额
     *
     * @return preferential_amount - 优惠金额
     */
    public BigDecimal getPreferential_amount() {
        return preferential_amount;
    }

    /**
     * 设置优惠金额
     *
     * @param preferential_amount 优惠金额
     */
    public void setPreferential_amount(BigDecimal preferential_amount) {
        this.preferential_amount = preferential_amount;
    }

    /**
     * 获取回收站 0.显示 1.回收
     *
     * @return recycle - 回收站 0.显示 1.回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0.显示 1.回收
     *
     * @param recycle 回收站 0.显示 1.回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    /**
     * 获取下单门店
     *
     * @return single_store - 下单门店
     */
    public Integer getSingle_store() {
        return single_store;
    }

    /**
     * 设置下单门店
     *
     * @param single_store 下单门店
     */
    public void setSingle_store(Integer single_store) {
        this.single_store = single_store;
    }

    /**
     * 获取取货门店
     *
     * @return pick_up_store - 取货门店
     */
    public Integer getPick_up_store() {
        return pick_up_store;
    }

    /**
     * 设置取货门店
     *
     * @param pick_up_store 取货门店
     */
    public void setPick_up_store(Integer pick_up_store) {
        this.pick_up_store = pick_up_store;
    }
}