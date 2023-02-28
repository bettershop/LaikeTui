package com.laiketui.domain.user;

import com.laiketui.domain.Page;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Table(name = "lkt_user")
public class User implements Serializable {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC", strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getIsVip() {
        return isVip;
    }

    public void setIsVip(Integer isVip) {
        this.isVip = isVip;
    }

    private Page page;

    /**
     * 是否为游客
     */
    @Transient
    private Integer isVip;

    /**
     * 当前登录用户所属店铺
     */
    @Transient
    private Integer mchId;

    public Integer getMchId() {
        return mchId;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 用户昵称
     */
    private String user_name;

    /**
     * 授权id
     */
    private String access_id;

    /**
     * 授权密钥
     */
    private String access_key;

    /**
     * 微信id
     */
    private String wx_id;

    /**
     * 微信昵称
     */
    private String wx_name;

    /**
     * 公众号id
     */
    private String gzh_id;

    /**
     * 支付宝id
     */
    private String zfb_id;

    /**
     * 百度id
     */
    private String bd_id;

    /**
     * 头条id
     */
    private String tt_id;

    /**
     * 推送客户端ID
     */
    private String clientid;

    /**
     * 性别 0:未知 1:男 2:女
     */
    private Integer sex;

    /**
     * 微信头像
     */
    private String headimgurl;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 县
     */
    private String county;

    /**
     * 详细地址
     */
    private String detailed_address;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 积分
     */
    private Integer score;

    /**
     * 冻结的积分
     */
    private Integer lock_score;

    /**
     * 支付密码
     */
    private String password;

    /**
     * 注册时间
     */
    @Column(name = "Register_data")
    private Date register_data;

    /**
     * 邮箱
     */
    private String e_mail;

    /**
     * 真实姓名
     */
    private String real_name;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 出生日期
     */
    private Date birthday;

    /**
     * 微信号
     */
    private String wechat_id;

    /**
     * 地址
     */
    private String address;

    /**
     * 银行名称
     */
    @Column(name = "Bank_name")
    private String bank_name;

    /**
     * 持卡人
     */
    @Column(name = "Cardholder")
    private String cardholder;

    /**
     * 银行卡号
     */
    @Column(name = "Bank_card_number")
    private String bank_card_number;

    /**
     * 分享次数
     */
    private Integer share_num;

    /**
     * 推荐人
     */
    @Column(name = "Referee")
    private String referee;

    /**
     * 访问令牌
     */
    private String access_token;

    /**
     * 消费金
     */
    private BigDecimal consumer_money;

    /**
     * 分享图片id
     */
    private String img_token;

    /**
     * 账号
     */
    private String zhanghao;

    /**
     * 密码
     */
    private String mima;

    /**
     * 来源 1.小程序 2.app
     */
    private String source;

    /**
     * 登录次数
     */
    private Integer login_num;

    /**
     * 验证支付密码时间
     */
    private Date verification_time;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 是否冻结 0-不冻结 1-冻结
     */
    private String is_lock;

    /**
     * 最后一次登录时间
     */
    private Date last_time;

    /**
     * 会员级别 0--普通会员
     */
    private Integer grade;

    /**
     * 会员推荐人id
     */
    private String tui_id;

    /**
     * 充值会员等级时间
     */
    private Date grade_add;

    /**
     * 开通方式 1-包月 2-包季 3-包年
     */
    private String grade_m;

    /**
     * 会员等级到期时间
     */
    private Date grade_end;

    /**
     * 是否到期 0-未到期  1-已到期
     */
    private String is_out;

    /**
     * 是否同意续费弹框 0-不同意 1 同意
     */
    private String is_box;

    /**
     * 店铺token
     */
    private String mch_token;

    /**
     * 用户所选语言
     */
    private String lang;

    public String getMch_token() {
        return mch_token;
    }

    public void setMch_token(String mch_token) {
        this.mch_token = mch_token;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id;
    }

    public String getAccess_key() {
        return access_key;
    }

    public void setAccess_key(String access_key) {
        this.access_key = access_key;
    }

    public String getWx_id() {
        return wx_id;
    }

    public void setWx_id(String wx_id) {
        this.wx_id = wx_id;
    }

    public String getWx_name() {
        return wx_name;
    }

    public void setWx_name(String wx_name) {
        this.wx_name = wx_name;
    }

    public String getGzh_id() {
        return gzh_id;
    }

    public void setGzh_id(String gzh_id) {
        this.gzh_id = gzh_id;
    }

    public String getZfb_id() {
        return zfb_id;
    }

    public void setZfb_id(String zfb_id) {
        this.zfb_id = zfb_id;
    }

    public String getBd_id() {
        return bd_id;
    }

    public void setBd_id(String bd_id) {
        this.bd_id = bd_id;
    }

    public String getTt_id() {
        return tt_id;
    }

    public void setTt_id(String tt_id) {
        this.tt_id = tt_id;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDetailed_address() {
        return detailed_address;
    }

    public void setDetailed_address(String detailed_address) {
        this.detailed_address = detailed_address;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getLock_score() {
        return lock_score;
    }

    public void setLock_score(Integer lock_score) {
        this.lock_score = lock_score;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegister_data() {
        return register_data;
    }

    public void setRegister_data(Date register_data) {
        this.register_data = register_data;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getWechat_id() {
        return wechat_id;
    }

    public void setWechat_id(String wechat_id) {
        this.wechat_id = wechat_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getBank_card_number() {
        return bank_card_number;
    }

    public void setBank_card_number(String bank_card_number) {
        this.bank_card_number = bank_card_number;
    }

    public Integer getShare_num() {
        return share_num;
    }

    public void setShare_num(Integer share_num) {
        this.share_num = share_num;
    }

    public String getReferee() {
        return referee;
    }

    public void setReferee(String referee) {
        this.referee = referee;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public BigDecimal getConsumer_money() {
        return consumer_money;
    }

    public void setConsumer_money(BigDecimal consumer_money) {
        this.consumer_money = consumer_money;
    }

    public String getImg_token() {
        return img_token;
    }

    public void setImg_token(String img_token) {
        this.img_token = img_token;
    }

    public String getZhanghao() {
        return zhanghao;
    }

    public void setZhanghao(String zhanghao) {
        this.zhanghao = zhanghao;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getLogin_num() {
        return login_num;
    }

    public void setLogin_num(Integer login_num) {
        this.login_num = login_num;
    }

    public Date getVerification_time() {
        return verification_time;
    }

    public void setVerification_time(Date verification_time) {
        this.verification_time = verification_time;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }

    public Date getLast_time() {
        return last_time;
    }

    public void setLast_time(Date last_time) {
        this.last_time = last_time;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getTui_id() {
        return tui_id;
    }

    public void setTui_id(String tui_id) {
        this.tui_id = tui_id;
    }

    public Date getGrade_add() {
        return grade_add;
    }

    public void setGrade_add(Date grade_add) {
        this.grade_add = grade_add;
    }

    public String getGrade_m() {
        return grade_m;
    }

    public void setGrade_m(String grade_m) {
        this.grade_m = grade_m;
    }

    public Date getGrade_end() {
        return grade_end;
    }

    public void setGrade_end(Date grade_end) {
        this.grade_end = grade_end;
    }

    public String getIs_out() {
        return is_out;
    }

    public void setIs_out(String is_out) {
        this.is_out = is_out;
    }

    public String getIs_box() {
        return is_box;
    }

    public void setIs_box(String is_box) {
        this.is_box = is_box;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(page, user.page) &&
                Objects.equals(isVip, user.isVip) &&
                Objects.equals(store_id, user.store_id) &&
                Objects.equals(user_id, user.user_id) &&
                Objects.equals(user_name, user.user_name) &&
                Objects.equals(access_id, user.access_id) &&
                Objects.equals(access_key, user.access_key) &&
                Objects.equals(wx_id, user.wx_id) &&
                Objects.equals(wx_name, user.wx_name) &&
                Objects.equals(gzh_id, user.gzh_id) &&
                Objects.equals(zfb_id, user.zfb_id) &&
                Objects.equals(bd_id, user.bd_id) &&
                Objects.equals(tt_id, user.tt_id) &&
                Objects.equals(clientid, user.clientid) &&
                Objects.equals(sex, user.sex) &&
                Objects.equals(headimgurl, user.headimgurl) &&
                Objects.equals(province, user.province) &&
                Objects.equals(city, user.city) &&
                Objects.equals(county, user.county) &&
                Objects.equals(detailed_address, user.detailed_address) &&
                Objects.equals(money, user.money) &&
                Objects.equals(score, user.score) &&
                Objects.equals(lock_score, user.lock_score) &&
                Objects.equals(password, user.password) &&
                Objects.equals(register_data, user.register_data) &&
                Objects.equals(e_mail, user.e_mail) &&
                Objects.equals(real_name, user.real_name) &&
                Objects.equals(mobile, user.mobile) &&
                Objects.equals(birthday, user.birthday) &&
                Objects.equals(wechat_id, user.wechat_id) &&
                Objects.equals(address, user.address) &&
                Objects.equals(bank_name, user.bank_name) &&
                Objects.equals(cardholder, user.cardholder) &&
                Objects.equals(bank_card_number, user.bank_card_number) &&
                Objects.equals(share_num, user.share_num) &&
                Objects.equals(referee, user.referee) &&
                Objects.equals(access_token, user.access_token) &&
                Objects.equals(consumer_money, user.consumer_money) &&
                Objects.equals(img_token, user.img_token) &&
                Objects.equals(zhanghao, user.zhanghao) &&
                Objects.equals(mima, user.mima) &&
                Objects.equals(source, user.source) &&
                Objects.equals(login_num, user.login_num) &&
                Objects.equals(verification_time, user.verification_time) &&
                Objects.equals(parameter, user.parameter) &&
                Objects.equals(is_lock, user.is_lock) &&
                Objects.equals(last_time, user.last_time) &&
                Objects.equals(grade, user.grade) &&
                Objects.equals(tui_id, user.tui_id) &&
                Objects.equals(grade_add, user.grade_add) &&
                Objects.equals(grade_m, user.grade_m) &&
                Objects.equals(grade_end, user.grade_end) &&
                Objects.equals(is_out, user.is_out) &&
                Objects.equals(is_box, user.is_box) &&
                Objects.equals(mch_token, user.mch_token) &&
                Objects.equals(lang, user.lang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, page, isVip, store_id, user_id, user_name, access_id, access_key, wx_id, wx_name, gzh_id, zfb_id, bd_id, tt_id, clientid, sex, headimgurl, province, city, county, detailed_address, money, score, lock_score, password, register_data, e_mail, real_name, mobile, birthday, wechat_id, address, bank_name, cardholder, bank_card_number, share_num, referee, access_token, consumer_money, img_token, zhanghao, mima, source, login_num, verification_time, parameter, is_lock, last_time, grade, tui_id, grade_add, grade_m, grade_end, is_out, is_box, mch_token, lang);
    }
}