package com.laiketui.domain.user;

import javax.persistence.*;
import java.util.Date;


/**
 * 等级会员首次开通表
 *
 * @author Trick
 * @date 2020/12/7 15:01
 */
@Table(name = "lkt_user_first")
public class UserFirstModal {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 会员等级id
     */
    private Integer grade_id;

    /**
     * 首次开通会员级别
     */
    private Integer level;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 是否使用了首次开通赠送商品券 0-未使用 1-已使用
     */
    private Byte is_use;

    /**
     * 订单编号
     */
    @Column(name = "sNo")
    private String sNo;

    /**
     * 兑换券失效时间
     */
    private Date end_time;

    /**
     * 兑换商品的规格id
     */
    private Integer attr_id;

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
     * 获取会员等级id
     *
     * @return grade_id - 会员等级id
     */
    public Integer getGrade_id() {
        return grade_id;
    }

    /**
     * 设置会员等级id
     *
     * @param grade_id 会员等级id
     */
    public void setGrade_id(Integer grade_id) {
        this.grade_id = grade_id;
    }

    /**
     * 获取首次开通会员级别
     *
     * @return level - 首次开通会员级别
     */
    public Integer getLevel() {
        return level;
    }

    /**
     * 设置首次开通会员级别
     *
     * @param level 首次开通会员级别
     */
    public void setLevel(Integer level) {
        this.level = level;
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
     * 获取是否使用了首次开通赠送商品券 0-未使用 1-已使用
     *
     * @return is_use - 是否使用了首次开通赠送商品券 0-未使用 1-已使用
     */
    public Byte getIs_use() {
        return is_use;
    }

    /**
     * 设置是否使用了首次开通赠送商品券 0-未使用 1-已使用
     *
     * @param is_use 是否使用了首次开通赠送商品券 0-未使用 1-已使用
     */
    public void setIs_use(Byte is_use) {
        this.is_use = is_use;
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

    /**
     * 获取兑换券失效时间
     *
     * @return end_time - 兑换券失效时间
     */
    public Date getEnd_time() {
        return end_time;
    }

    /**
     * 设置兑换券失效时间
     *
     * @param end_time 兑换券失效时间
     */
    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    /**
     * 获取兑换商品的规格id
     *
     * @return attr_id - 兑换商品的规格id
     */
    public Integer getAttr_id() {
        return attr_id;
    }

    /**
     * 设置兑换商品的规格id
     *
     * @param attr_id 兑换商品的规格id
     */
    public void setAttr_id(Integer attr_id) {
        this.attr_id = attr_id;
    }
}