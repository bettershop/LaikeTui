package com.laiketui.domain.mch;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_user_collection")
public class UserCollectionModel {
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
     * 用户ID
     */
    private String user_id;

    /**
     * 产品id
     */
    private Integer p_id;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * 店铺ID
     */
    private Integer mch_id;

    /**
     * 收藏类型 1.普通收藏 2.积分商城收藏
     */
    private Integer type;

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
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID
     *
     * @param user_id 用户ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取产品id
     *
     * @return p_id - 产品id
     */
    public Integer getP_id() {
        return p_id;
    }

    /**
     * 设置产品id
     *
     * @param p_id 产品id
     */
    public void setP_id(Integer p_id) {
        this.p_id = p_id;
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
     * 获取店铺ID
     *
     * @return mch_id - 店铺ID
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺ID
     *
     * @param mch_id 店铺ID
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取收藏类型 1.普通收藏 2.积分商城收藏
     *
     * @return type - 收藏类型 1.普通收藏 2.积分商城收藏
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置收藏类型 1.普通收藏 2.积分商城收藏
     *
     * @param type 收藏类型 1.普通收藏 2.积分商城收藏
     */
    public void setType(Integer type) {
        this.type = type;
    }
}