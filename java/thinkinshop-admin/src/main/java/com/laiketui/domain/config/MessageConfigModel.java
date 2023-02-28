package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;


/**
 * 短信模板
 *
 * @author Trick
 * @date 2021/1/18 16:24
 */
@Table(name = "lkt_message_config")
public class MessageConfigModel {
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
     * accessKeyId
     */
    @Column(name = "accessKeyId")
    private String accessKeyId;

    /**
     * accessKeySecret
     */
    @Column(name = "accessKeySecret")
    private String accessKeySecret;

    /**
     * 短信签名
     */
    @Column(name = "SignName")
    private String signName;

    /**
     * 创建时间
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
     * 获取accessKeyId
     *
     * @return accessKeyId - accessKeyId
     */
    public String getAccessKeyId() {
        return accessKeyId;
    }

    /**
     * 设置accessKeyId
     *
     * @param accessKeyId accessKeyId
     */
    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId == null ? null : accessKeyId.trim();
    }

    /**
     * 获取accessKeySecret
     *
     * @return accessKeySecret - accessKeySecret
     */
    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    /**
     * 设置accessKeySecret
     *
     * @param accessKeySecret accessKeySecret
     */
    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret == null ? null : accessKeySecret.trim();
    }

    /**
     * 获取短信签名
     *
     * @return SignName - 短信签名
     */
    public String getSignName() {
        return signName;
    }

    /**
     * 设置短信签名
     *
     * @param signName 短信签名
     */
    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    /**
     * 获取创建时间
     *
     * @return add_time - 创建时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置创建时间
     *
     * @param add_time 创建时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }
}