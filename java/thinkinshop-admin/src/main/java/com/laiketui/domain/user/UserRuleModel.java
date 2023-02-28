package com.laiketui.domain.user;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 会员规则
 *
 * @author Trick
 * @date 2021/11/11 9:45
 */
@Table(name = "lkt_user_rule")
public class UserRuleModel {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 默认折率
     */
    private BigDecimal rate_now;

    /**
     * 支持的插件使用折率：1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍  5--分销  6--秒杀
     */
    private String active;

    /**
     * 有效期
     */
    private Integer wait;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 是否开启自动续费 0-不开启 1-开启
     */
    private Integer is_auto;

    /**
     * 自动续费提前提醒时间（天）
     */
    private Integer auto_time;

    /**
     * 开通方式 1-包月 2-包季 3-包年
     */
    private String method;

    /**
     * 是否开启余额支付 0-不开启 1-开启
     */
    private Integer is_wallet;

    /**
     * 升级方式 1-购买会员服务 2-补差额
     */
    private String upgrade;

    /**
     * 是否开启生日特权 0-不开启 1-开启
     */
    private Integer is_birthday;

    /**
     * 生日特权积分倍数
     */
    private Integer bir_multiple;

    /**
     * 是否开启会员赠送商品 0-不开启 1-开启
     */
    private Integer is_product;

    /**
     * 提现保证金
     */
    private BigDecimal bond;

    /**
     * 会员等比例积分 0-不开启 1-开启
     */
    private Integer is_jifen;

    /**
     * 积分发送规则 0-付款后 1-收货后
     */
    private Integer jifen_m;

    /**
     * 是否开启返现 0-不开启 1-开启
     */
    private Integer back;

    /**
     * 返现比例
     */
    private BigDecimal back_scale;

    /**
     * 会员分享海报
     */
    private String poster;

    /**
     * 是否开启推荐限制 0-不限制  1-限制
     */
    private Integer is_limit;

    /**
     * 可以推荐的会员级别
     */
    private Integer level;

    /**
     * 可以参与分销的会员级别id
     */
    private Integer distribute_l;

    /**
     * 增送商品的有效天数
     */
    private Integer valid;

    /**
     * 积分过期设置
     */
    private Integer score;

    /**
     * 购物赠送积分 0.未开启 

1.开启
     */
    private Integer bonus_points_shopping;

    /**
     * 赠送比例
     */
    private Integer proportion_of_gifts;

    /**
     * 发放时间 1.收货后  2.付款后
     */
    private Integer release_time;

    /**
     * 会员等级规则详情
     */
    private String rule;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取默认折率
     *
     * @return rate_now - 默认折率
     */
    public BigDecimal getRate_now() {
        return rate_now;
    }

    /**
     * 设置默认折率
     *
     * @param rate_now 默认折率
     */
    public void setRate_now(BigDecimal rate_now) {
        this.rate_now = rate_now;
    }

    /**
     * 获取支持的插件使用折率：1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍  5--分销  6--秒杀
     *
     * @return active - 支持的插件使用折率：1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍  5--分销  6--秒杀
     */
    public String getActive() {
        return active;
    }

    /**
     * 设置支持的插件使用折率：1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍  5--分销  6--秒杀
     *
     * @param active 支持的插件使用折率：1--正价商品 2--支持拼团 3--支持砍价 4--支持竞拍  5--分销  6--秒杀
     */
    public void setActive(String active) {
        this.active = active == null ? null : active.trim();
    }

    /**
     * 获取有效期
     *
     * @return wait - 有效期
     */
    public Integer getWait() {
        return wait;
    }

    /**
     * 设置有效期
     *
     * @param wait 有效期
     */
    public void setWait(Integer wait) {
        this.wait = wait;
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
     * 获取是否开启自动续费 0-不开启 1-开启
     *
     * @return is_auto - 是否开启自动续费 0-不开启 1-开启
     */
    public Integer getIs_auto() {
        return is_auto;
    }

    /**
     * 设置是否开启自动续费 0-不开启 1-开启
     *
     * @param is_auto 是否开启自动续费 0-不开启 1-开启
     */
    public void setIs_auto(Integer is_auto) {
        this.is_auto = is_auto;
    }

    /**
     * 获取自动续费提前提醒时间（天）
     *
     * @return auto_time - 自动续费提前提醒时间（天）
     */
    public Integer getAuto_time() {
        return auto_time;
    }

    /**
     * 设置自动续费提前提醒时间（天）
     *
     * @param auto_time 自动续费提前提醒时间（天）
     */
    public void setAuto_time(Integer auto_time) {
        this.auto_time = auto_time;
    }

    /**
     * 获取开通方式 1-包月 2-包季 3-包年
     *
     * @return method - 开通方式 1-包月 2-包季 3-包年
     */
    public String getMethod() {
        return method;
    }

    /**
     * 设置开通方式 1-包月 2-包季 3-包年
     *
     * @param method 开通方式 1-包月 2-包季 3-包年
     */
    public void setMethod(String method) {
        this.method = method == null ? null : method.trim();
    }

    /**
     * 获取是否开启余额支付 0-不开启 1-开启
     *
     * @return is_wallet - 是否开启余额支付 0-不开启 1-开启
     */
    public Integer getIs_wallet() {
        return is_wallet;
    }

    /**
     * 设置是否开启余额支付 0-不开启 1-开启
     *
     * @param is_wallet 是否开启余额支付 0-不开启 1-开启
     */
    public void setIs_wallet(Integer is_wallet) {
        this.is_wallet = is_wallet;
    }

    /**
     * 获取升级方式 1-购买会员服务 2-补差额
     *
     * @return upgrade - 升级方式 1-购买会员服务 2-补差额
     */
    public String getUpgrade() {
        return upgrade;
    }

    /**
     * 设置升级方式 1-购买会员服务 2-补差额
     *
     * @param upgrade 升级方式 1-购买会员服务 2-补差额
     */
    public void setUpgrade(String upgrade) {
        this.upgrade = upgrade == null ? null : upgrade.trim();
    }

    /**
     * 获取是否开启生日特权 0-不开启 1-开启
     *
     * @return is_birthday - 是否开启生日特权 0-不开启 1-开启
     */
    public Integer getIs_birthday() {
        return is_birthday;
    }

    /**
     * 设置是否开启生日特权 0-不开启 1-开启
     *
     * @param is_birthday 是否开启生日特权 0-不开启 1-开启
     */
    public void setIs_birthday(Integer is_birthday) {
        this.is_birthday = is_birthday;
    }

    /**
     * 获取生日特权积分倍数
     *
     * @return bir_multiple - 生日特权积分倍数
     */
    public Integer getBir_multiple() {
        return bir_multiple;
    }

    /**
     * 设置生日特权积分倍数
     *
     * @param bir_multiple 生日特权积分倍数
     */
    public void setBir_multiple(Integer bir_multiple) {
        this.bir_multiple = bir_multiple;
    }

    /**
     * 获取是否开启会员赠送商品 0-不开启 1-开启
     *
     * @return is_product - 是否开启会员赠送商品 0-不开启 1-开启
     */
    public Integer getIs_product() {
        return is_product;
    }

    /**
     * 设置是否开启会员赠送商品 0-不开启 1-开启
     *
     * @param is_product 是否开启会员赠送商品 0-不开启 1-开启
     */
    public void setIs_product(Integer is_product) {
        this.is_product = is_product;
    }

    /**
     * 获取提现保证金
     *
     * @return bond - 提现保证金
     */
    public BigDecimal getBond() {
        return bond;
    }

    /**
     * 设置提现保证金
     *
     * @param bond 提现保证金
     */
    public void setBond(BigDecimal bond) {
        this.bond = bond;
    }

    /**
     * 获取会员等比例积分 0-不开启 1-开启
     *
     * @return is_jifen - 会员等比例积分 0-不开启 1-开启
     */
    public Integer getIs_jifen() {
        return is_jifen;
    }

    /**
     * 设置会员等比例积分 0-不开启 1-开启
     *
     * @param is_jifen 会员等比例积分 0-不开启 1-开启
     */
    public void setIs_jifen(Integer is_jifen) {
        this.is_jifen = is_jifen;
    }

    /**
     * 获取积分发送规则 0-付款后 1-收货后
     *
     * @return jifen_m - 积分发送规则 0-付款后 1-收货后
     */
    public Integer getJifen_m() {
        return jifen_m;
    }

    /**
     * 设置积分发送规则 0-付款后 1-收货后
     *
     * @param jifen_m 积分发送规则 0-付款后 1-收货后
     */
    public void setJifen_m(Integer jifen_m) {
        this.jifen_m = jifen_m;
    }

    /**
     * 获取是否开启返现 0-不开启 1-开启
     *
     * @return back - 是否开启返现 0-不开启 1-开启
     */
    public Integer getBack() {
        return back;
    }

    /**
     * 设置是否开启返现 0-不开启 1-开启
     *
     * @param back 是否开启返现 0-不开启 1-开启
     */
    public void setBack(Integer back) {
        this.back = back;
    }

    /**
     * 获取返现比例
     *
     * @return back_scale - 返现比例
     */
    public BigDecimal getBack_scale() {
        return back_scale;
    }

    /**
     * 设置返现比例
     *
     * @param back_scale 返现比例
     */
    public void setBack_scale(BigDecimal back_scale) {
        this.back_scale = back_scale;
    }

    /**
     * 获取会员分享海报
     *
     * @return poster - 会员分享海报
     */
    public String getPoster() {
        return poster;
    }

    /**
     * 设置会员分享海报
     *
     * @param poster 会员分享海报
     */
    public void setPoster(String poster) {
        this.poster = poster == null ? null : poster.trim();
    }

    /**
     * 获取是否开启推荐限制 0-不限制  1-限制
     *
     * @return is_limit - 是否开启推荐限制 0-不限制  1-限制
     */
    public Integer getIs_limit() {
        return is_limit;
    }

    /**
     * 设置是否开启推荐限制 0-不限制  1-限制
     *
     * @param is_limit 是否开启推荐限制 0-不限制  1-限制
     */
    public void setIs_limit(Integer is_limit) {
        this.is_limit = is_limit;
    }

    /**
     * 获取可以推荐的会员级别
     *
     * @return level - 可以推荐的会员级别
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置可以推荐的会员级别
     *
     * @param level 可以推荐的会员级别
     */
    public void setLevel(Integer level) {
        this.level = level;
    }

    /**
     * 获取可以参与分销的会员级别id
     *
     * @return distribute_l - 可以参与分销的会员级别id
     */
    public Integer getDistribute_l() {
        return distribute_l;
    }

    /**
     * 设置可以参与分销的会员级别id
     *
     * @param distribute_l 可以参与分销的会员级别id
     */
    public void setDistribute_l(Integer distribute_l) {
        this.distribute_l = distribute_l;
    }

    /**
     * 获取增送商品的有效天数
     *
     * @return valid - 增送商品的有效天数
     */
    public Integer getValid() {
        return valid;
    }

    /**
     * 设置增送商品的有效天数
     *
     * @param valid 增送商品的有效天数
     */
    public void setValid(Integer valid) {
        this.valid = valid;
    }

    /**
     * 获取积分过期设置
     *
     * @return score - 积分过期设置
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 设置积分过期设置
     *
     * @param score 积分过期设置
     */
    public void setScore(Integer score) {
        this.score = score;
    }

    /**
     * 获取购物赠送积分 0.未开启 

1.开启
     *
     * @return bonus_points_shopping - 购物赠送积分 0.未开启 

1.开启
     */
    public Integer getBonus_points_shopping() {
        return bonus_points_shopping;
    }

    /**
     * 设置购物赠送积分 0.未开启 

1.开启
     *
     * @param bonus_points_shopping 购物赠送积分 0.未开启 

1.开启
     */
    public void setBonus_points_shopping(Integer bonus_points_shopping) {
        this.bonus_points_shopping = bonus_points_shopping;
    }

    /**
     * 获取赠送比例
     *
     * @return proportion_of_gifts - 赠送比例
     */
    public Integer getProportion_of_gifts() {
        return proportion_of_gifts;
    }

    /**
     * 设置赠送比例
     *
     * @param proportion_of_gifts 赠送比例
     */
    public void setProportion_of_gifts(Integer proportion_of_gifts) {
        this.proportion_of_gifts = proportion_of_gifts;
    }

    /**
     * 获取发放时间 1.收货后  2.付款后
     *
     * @return release_time - 发放时间 1.收货后  2.付款后
     */
    public Integer getRelease_time() {
        return release_time;
    }

    /**
     * 设置发放时间 1.收货后  2.付款后
     *
     * @param release_time 发放时间 1.收货后  2.付款后
     */
    public void setRelease_time(Integer release_time) {
        this.release_time = release_time;
    }

    /**
     * 获取会员等级规则详情
     *
     * @return rule - 会员等级规则详情
     */
    public String getRule() {
        return rule;
    }

    /**
     * 设置会员等级规则详情
     *
     * @param rule 会员等级规则详情
     */
    public void setRule(String rule) {
        this.rule = rule == null ? null : rule.trim();
    }
}