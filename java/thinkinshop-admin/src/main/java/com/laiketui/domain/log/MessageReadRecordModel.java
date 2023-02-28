package com.laiketui.domain.log;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_message_read_record")
public class MessageReadRecordModel {
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
     * 接收用户id
     */
    private String user_id;

    /**
     * 消息id
     */
    private Integer message_id;

    /**
     * 消息类型
     */
    private String type;

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
     * 获取接收用户id
     *
     * @return user_id - 接收用户id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置接收用户id
     *
     * @param user_id 接收用户id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取消息id
     *
     * @return message_id - 消息id
     */
    public Integer getMessage_id() {
        return message_id;
    }

    /**
     * 设置消息id
     *
     * @param message_id 消息id
     */
    public void setMessage_id(Integer message_id) {
        this.message_id = message_id;
    }

    /**
     * 获取消息类型
     *
     * @return type - 消息类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置消息类型
     *
     * @param type 消息类型
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * @return add_time
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * @param add_time
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }
}