package com.laiketui.domain.mch;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_mch_browse")
public class MchBrowseModel {
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
    private String mch_id;

    /**
     * token
     */
    private String token;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 事件
     */
    private String event;

    /**
     * 时间
     */
    private Date add_time;

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
    public String getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺id
     *
     * @param mch_id 店铺id
     */
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id == null ? null : mch_id.trim();
    }

    /**
     * 获取token
     *
     * @return token - token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
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
     * 获取事件
     *
     * @return event - 事件
     */
    public String getEvent() {
        return event;
    }

    /**
     * 设置事件
     *
     * @param event 事件
     */
    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    /**
     * 获取时间
     *
     * @return add_time - 时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置时间
     *
     * @param add_time 时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }
}