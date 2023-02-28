package com.laiketui.domain.mch;

import com.laiketui.domain.Page;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "lkt_mch")
public class MchModel {


    /**
     * 分页
     */
    Page page;

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * 未营业
     */
    @Transient
    public static final Integer IS_OPEN_NOT_IN_BUSINESS = 0;
    /**
     * 营业中
     */
    @Transient
    public static final Integer IS_OPEN_IN_BUSINESS = 1;
    /**
     * 打样
     */
    @Transient
    public static final Integer IS_OPEN_PROOFING = 2;

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
     * 店铺名称
     */
    private String name;

    /**
     * 店铺信息
     */
    private String shop_information;

    /**
     * 经营范围
     */
    private String shop_range;

    /**
     * 真实姓名
     */
    private String realname;

    /**
     * 身份证号码
     */
    @Column(name = "ID_number")
    private String ID_number;

    /**
     * 联系电话
     */
    private String tel;

    private String sheng;

    private String shi;

    private String xian;

    /**
     * 联系地址
     */
    private String address;

    /**
     * 店铺Logo
     */
    private String logo;

    /**
     * 店铺性质：0.个人 1.企业
     */
    private String shop_nature;

    /**
     * 营业执照
     */
    private String business_license;

    /**
     * 申请时间
     */
    private Date add_time;

    /**
     * 审核时间
     */
    private Date review_time;

    /**
     * 审核状态：0.待审核 1.审核通过 2.审核不通过
     */
    private String review_status;

    /**
     * 拒绝理由
     */
    private String review_result;

    /**
     * 商户积分
     */
    private BigDecimal integral_money;

    /**
     * 商户余额
     */
    private BigDecimal account_money;

    /**
     * 收藏数量
     */
    private Integer collection_num;

    /**
     * 是否营业：0.未营业 1.营业中 2.打烊
     */
    private String is_open;

    /**
     * 是否被系统关闭：0.否 1.是
     */
    private String is_lock;

    /**
     * 店铺经营范围
     */
    private String confines;

    /**
     * 营业时间
     */
    private String business_hours;

    private Integer roomid;

    /**
     * 旧房间ID
     */
    private Integer old_roomid;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 可取现金额
     */
    private BigDecimal cashable_money;

    /**
     * 是否回收 0否 1是
     */
    private Integer recovery;

    public Integer getRecovery() {
        return recovery;
    }

    public void setRecovery(Integer recovery) {
        this.recovery = recovery;
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
     * 获取店铺名称
     *
     * @return name - 店铺名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置店铺名称
     *
     * @param name 店铺名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取店铺信息
     *
     * @return shop_information - 店铺信息
     */
    public String getShop_information() {
        return shop_information;
    }

    /**
     * 设置店铺信息
     *
     * @param shop_information 店铺信息
     */
    public void setShop_information(String shop_information) {
        this.shop_information = shop_information == null ? null : shop_information.trim();
    }

    /**
     * 获取经营范围
     *
     * @return shop_range - 经营范围
     */
    public String getShop_range() {
        return shop_range;
    }

    /**
     * 设置经营范围
     *
     * @param shop_range 经营范围
     */
    public void setShop_range(String shop_range) {
        this.shop_range = shop_range == null ? null : shop_range.trim();
    }

    /**
     * 获取真实姓名
     *
     * @return realname - 真实姓名
     */
    public String getRealname() {
        return realname;
    }

    /**
     * 设置真实姓名
     *
     * @param realname 真实姓名
     */
    public void setRealname(String realname) {
        this.realname = realname == null ? null : realname.trim();
    }

    /**
     * 获取身份证号码
     *
     * @return ID_number - 身份证号码
     */
    public String getID_number() {
        return ID_number;
    }

    /**
     * 设置身份证号码
     *
     * @param ID_number 身份证号码
     */
    public void setID_number(String ID_number) {
        this.ID_number = ID_number == null ? null : ID_number.trim();
    }

    /**
     * 获取联系电话
     *
     * @return tel - 联系电话
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置联系电话
     *
     * @param tel 联系电话
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * @return sheng
     */
    public String getSheng() {
        return sheng;
    }

    /**
     * @param sheng
     */
    public void setSheng(String sheng) {
        this.sheng = sheng == null ? null : sheng.trim();
    }

    /**
     * @return shi
     */
    public String getShi() {
        return shi;
    }

    /**
     * @param shi
     */
    public void setShi(String shi) {
        this.shi = shi == null ? null : shi.trim();
    }

    /**
     * @return xian
     */
    public String getXian() {
        return xian;
    }

    /**
     * @param xian
     */
    public void setXian(String xian) {
        this.xian = xian == null ? null : xian.trim();
    }

    /**
     * 获取联系地址
     *
     * @return address - 联系地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置联系地址
     *
     * @param address 联系地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取店铺Logo
     *
     * @return logo - 店铺Logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置店铺Logo
     *
     * @param logo 店铺Logo
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 获取店铺性质：0.个人 1.企业
     *
     * @return shop_nature - 店铺性质：0.个人 1.企业
     */
    public String getShop_nature() {
        return shop_nature;
    }

    /**
     * 设置店铺性质：0.个人 1.企业
     *
     * @param shop_nature 店铺性质：0.个人 1.企业
     */
    public void setShop_nature(String shop_nature) {
        this.shop_nature = shop_nature;
    }

    /**
     * 获取营业执照
     *
     * @return business_license - 营业执照
     */
    public String getBusiness_license() {
        return business_license;
    }

    /**
     * 设置营业执照
     *
     * @param business_license 营业执照
     */
    public void setBusiness_license(String business_license) {
        this.business_license = business_license == null ? null : business_license.trim();
    }

    /**
     * 获取申请时间
     *
     * @return add_time - 申请时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置申请时间
     *
     * @param add_time 申请时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * 获取审核时间
     *
     * @return review_time - 审核时间
     */
    public Date getReview_time() {
        return review_time;
    }

    /**
     * 设置审核时间
     *
     * @param review_time 审核时间
     */
    public void setReview_time(Date review_time) {
        this.review_time = review_time;
    }

    /**
     * 获取审核状态：0.待审核 1.审核通过 2.审核不通过
     *
     * @return review_status - 审核状态：0.待审核 1.审核通过 2.审核不通过
     */
    public String getReview_status() {
        return review_status;
    }

    /**
     * 设置审核状态：0.待审核 1.审核通过 2.审核不通过
     *
     * @param review_status 审核状态：0.待审核 1.审核通过 2.审核不通过
     */
    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }

    /**
     * 获取拒绝理由
     *
     * @return review_result - 拒绝理由
     */
    public String getReview_result() {
        return review_result;
    }

    /**
     * 设置拒绝理由
     *
     * @param review_result 拒绝理由
     */
    public void setReview_result(String review_result) {
        this.review_result = review_result == null ? null : review_result.trim();
    }

    /**
     * 获取商户积分
     *
     * @return integral_money - 商户积分
     */
    public BigDecimal getIntegral_money() {
        return integral_money;
    }

    /**
     * 设置商户积分
     *
     * @param integral_money 商户积分
     */
    public void setIntegral_money(BigDecimal integral_money) {
        this.integral_money = integral_money;
    }

    /**
     * 获取商户余额
     *
     * @return account_money - 商户余额
     */
    public BigDecimal getAccount_money() {
        return account_money;
    }

    /**
     * 设置商户余额
     *
     * @param account_money 商户余额
     */
    public void setAccount_money(BigDecimal account_money) {
        this.account_money = account_money;
    }

    /**
     * 获取收藏数量
     *
     * @return collection_num - 收藏数量
     */
    public Integer getCollection_num() {
        return collection_num;
    }

    /**
     * 设置收藏数量
     *
     * @param collection_num 收藏数量
     */
    public void setCollection_num(Integer collection_num) {
        this.collection_num = collection_num;
    }

    /**
     * 获取是否营业：0.未营业 1.营业中 2.打烊
     *
     * @return is_open - 是否营业：0.未营业 1.营业中 2.打烊
     */
    public String getIs_open() {
        return is_open;
    }

    /**
     * 设置是否营业：0.未营业 1.营业中 2.打烊
     *
     * @param is_open 是否营业：0.未营业 1.营业中 2.打烊
     */
    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    /**
     * 获取是否被系统关闭：0.否 1.是
     *
     * @return is_lock - 是否被系统关闭：0.否 1.是
     */
    public String getIs_lock() {
        return is_lock;
    }

    /**
     * 设置是否被系统关闭：0.否 1.是
     *
     * @param is_lock 是否被系统关闭：0.否 1.是
     */
    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }

    /**
     * 获取店铺经营范围
     *
     * @return confines - 店铺经营范围
     */
    public String getConfines() {
        return confines;
    }

    /**
     * 设置店铺经营范围
     *
     * @param confines 店铺经营范围
     */
    public void setConfines(String confines) {
        this.confines = confines == null ? null : confines.trim();
    }

    /**
     * 获取营业时间
     *
     * @return business_hours - 营业时间
     */
    public String getBusiness_hours() {
        return business_hours;
    }

    /**
     * 设置营业时间
     *
     * @param business_hours 营业时间
     */
    public void setBusiness_hours(String business_hours) {
        this.business_hours = business_hours == null ? null : business_hours.trim();
    }

    /**
     * @return roomid
     */
    public Integer getRoomid() {
        return roomid;
    }

    /**
     * @param roomid
     */
    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    /**
     * 获取旧房间ID
     *
     * @return old_roomid - 旧房间ID
     */
    public Integer getOld_roomid() {
        return old_roomid;
    }

    /**
     * 设置旧房间ID
     *
     * @param old_roomid 旧房间ID
     */
    public void setOld_roomid(Integer old_roomid) {
        this.old_roomid = old_roomid;
    }

    /**
     * 获取经度
     *
     * @return longitude - 经度
     */
    public String getLongitude() {
        return longitude;
    }

    /**
     * 设置经度
     *
     * @param longitude 经度
     */
    public void setLongitude(String longitude) {
        this.longitude = longitude == null ? null : longitude.trim();
    }

    /**
     * 获取纬度
     *
     * @return latitude - 纬度
     */
    public String getLatitude() {
        return latitude;
    }

    /**
     * 设置纬度
     *
     * @param latitude 纬度
     */
    public void setLatitude(String latitude) {
        this.latitude = latitude == null ? null : latitude.trim();
    }

    /**
     * 获取可取现金额
     *
     * @return cashable_money - 可取现金额
     */
    public BigDecimal getCashable_money() {
        return cashable_money;
    }

    /**
     * 设置可取现金额
     *
     * @param cashable_money 可取现金额
     */
    public void setCashable_money(BigDecimal cashable_money) {
        this.cashable_money = cashable_money;
    }
}