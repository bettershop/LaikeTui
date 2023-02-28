package com.laiketui.domain.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table(name = "lkt_user_grade")
public class UserGradeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 等级名称
     */
    private String name;

    /**
     * 打折率
     */
    private BigDecimal rate;

    /**
     * 包月金额
     */
    private BigDecimal money;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 包季金额
     */
    private BigDecimal money_j;

    /**
     * 包年金额
     */
    private BigDecimal money_n;

    /**
     * 备注
     */
    private String remark;

    /**
     * 会员充值背景图
     */
    private String imgurl;

    /**
     * 我的会员背景展示图
     */
    private String imgurl_my;

    /**
     * 等级中心背景图
     */
    private String imgurl_s;

    /**
     * 会员级别 普通-0   白银-1  黄金-2  黑金-3
     */
    private Integer level;

    /**
     * 赠送商品id
     */
    private Integer pro_id;

    /**
     * 会员昵称字体颜色
     */
    private String font_color;

    /**
     * 标识文字颜色
     */
    private String date_color;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取等级名称
     *
     * @return name - 等级名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置等级名称
     *
     * @param name 等级名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取打折率
     *
     * @return rate - 打折率
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * 设置打折率
     *
     * @param rate 打折率
     */
    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * 获取包月金额
     *
     * @return money - 包月金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置包月金额
     *
     * @param money 包月金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
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
     * 获取包季金额
     *
     * @return money_j - 包季金额
     */
    public BigDecimal getMoney_j() {
        return money_j;
    }

    /**
     * 设置包季金额
     *
     * @param money_j 包季金额
     */
    public void setMoney_j(BigDecimal money_j) {
        this.money_j = money_j;
    }

    /**
     * 获取包年金额
     *
     * @return money_n - 包年金额
     */
    public BigDecimal getMoney_n() {
        return money_n;
    }

    /**
     * 设置包年金额
     *
     * @param money_n 包年金额
     */
    public void setMoney_n(BigDecimal money_n) {
        this.money_n = money_n;
    }

    /**
     * 获取备注
     *
     * @return remark - 备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取会员充值背景图
     *
     * @return imgurl - 会员充值背景图
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置会员充值背景图
     *
     * @param imgurl 会员充值背景图
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
     * 获取我的会员背景展示图
     *
     * @return imgurl_my - 我的会员背景展示图
     */
    public String getImgurl_my() {
        return imgurl_my;
    }

    /**
     * 设置我的会员背景展示图
     *
     * @param imgurl_my 我的会员背景展示图
     */
    public void setImgurl_my(String imgurl_my) {
        this.imgurl_my = imgurl_my == null ? null : imgurl_my.trim();
    }

    /**
     * 获取等级中心背景图
     *
     * @return imgurl_s - 等级中心背景图
     */
    public String getImgurl_s() {
        return imgurl_s;
    }

    /**
     * 设置等级中心背景图
     *
     * @param imgurl_s 等级中心背景图
     */
    public void setImgurl_s(String imgurl_s) {
        this.imgurl_s = imgurl_s == null ? null : imgurl_s.trim();
    }

    /**
     * 获取会员级别 普通-0   白银-1  黄金-2  黑金-3
     *
     * @return level - 会员级别 普通-0   白银-1  黄金-2  黑金-3
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置会员级别 普通-0   白银-1  黄金-2  黑金-3
     *
     * @param level 会员级别 普通-0   白银-1  黄金-2  黑金-3
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取赠送商品id
     *
     * @return pro_id - 赠送商品id
     */
    public Integer getPro_id() {
        return pro_id;
    }

    /**
     * 设置赠送商品id
     *
     * @param pro_id 赠送商品id
     */
    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    /**
     * 获取会员昵称字体颜色
     *
     * @return font_color - 会员昵称字体颜色
     */
    public String getFont_color() {
        return font_color;
    }

    /**
     * 设置会员昵称字体颜色
     *
     * @param font_color 会员昵称字体颜色
     */
    public void setFont_color(String font_color) {
        this.font_color = font_color == null ? null : font_color.trim();
    }

    /**
     * 获取标识文字颜色
     *
     * @return date_color - 标识文字颜色
     */
    public String getDate_color() {
        return date_color;
    }

    /**
     * 设置标识文字颜色
     *
     * @param date_color 标识文字颜色
     */
    public void setDate_color(String date_color) {
        this.date_color = date_color == null ? null : date_color.trim();
    }
}