package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 版本表
 *
 * @author Trick
 * @date 2021/1/22 9:54
 */
@Table(name = "lkt_edition")
public class EditionModel {
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
     * 版本号
     */
    private String edition;

    /**
     * 路径
     */
    private String edition_url;

    /**
     * 是否是热更新 0:是 1:不是
     */
    private Integer type;

    /**
     * 更新内容
     */
    private String content;

    /**
     * 添加时间
     */
    private Date add_date;

    private String appname;

    /**
     * android下载地址
     */
    private String android_url;

    /**
     * ios下载地址
     */
    private String ios_url;

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
     * 获取版本号
     *
     * @return edition - 版本号
     */
    public String getEdition() {
        return edition;
    }

    /**
     * 设置版本号
     *
     * @param edition 版本号
     */
    public void setEdition(String edition) {
        this.edition = edition == null ? null : edition.trim();
    }

    /**
     * 获取路径
     *
     * @return edition_url - 路径
     */
    public String getEdition_url() {
        return edition_url;
    }

    /**
     * 设置路径
     *
     * @param edition_url 路径
     */
    public void setEdition_url(String edition_url) {
        this.edition_url = edition_url == null ? null : edition_url.trim();
    }

    /**
     * 获取是否是热更新 0:是 1:不是
     *
     * @return type - 是否是热更新 0:是 1:不是
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置是否是热更新 0:是 1:不是
     *
     * @param type 是否是热更新 0:是 1:不是
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取更新内容
     *
     * @return content - 更新内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置更新内容
     *
     * @param content 更新内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取添加时间
     *
     * @return add_date - 添加时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置添加时间
     *
     * @param add_date 添加时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * @return appname
     */
    public String getAppname() {
        return appname;
    }

    /**
     * @param appname
     */
    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    /**
     * 获取android下载地址
     *
     * @return android_url - android下载地址
     */
    public String getAndroid_url() {
        return android_url;
    }

    /**
     * 设置android下载地址
     *
     * @param android_url android下载地址
     */
    public void setAndroid_url(String android_url) {
        this.android_url = android_url == null ? null : android_url.trim();
    }

    /**
     * 获取ios下载地址
     *
     * @return ios_url - ios下载地址
     */
    public String getIos_url() {
        return ios_url;
    }

    /**
     * 设置ios下载地址
     *
     * @param ios_url ios下载地址
     */
    public void setIos_url(String ios_url) {
        this.ios_url = ios_url == null ? null : ios_url.trim();
    }
}