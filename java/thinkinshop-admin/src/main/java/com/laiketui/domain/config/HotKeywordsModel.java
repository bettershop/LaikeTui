package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "lkt_hotkeywords")
public class HotKeywordsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城ID
     */
    private Integer store_id;

    /**
     * 是否开启 0.未开启 1.

开启
     */
    private Integer is_open;

    /**
     * 关键词上限
     */
    private Integer num;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 店铺关键字
     */
    private String mch_keyword;

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
     * 获取商城ID
     *
     * @return store_id - 商城ID
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城ID
     *
     * @param store_id 商城ID
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取是否开启 0.未开启 1.

开启
     *
     * @return is_open - 是否开启 0.未开启 1.

开启
     */
    public Integer getIs_open() {
        return is_open;
    }

    /**
     * 设置是否开启 0.未开启 1.

开启
     *
     * @param is_open 是否开启 0.未开启 1.

开启
     */
    public void setIs_open(Integer is_open) {
        this.is_open = is_open;
    }

    /**
     * 获取关键词上限
     *
     * @return num - 关键词上限
     */
    public Integer getNum() {
        return num;
    }

    /**
     * 设置关键词上限
     *
     * @param num 关键词上限
     */
    public void setNum(Integer num) {
        this.num = num;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getMch_keyword() {
        return mch_keyword;
    }

    public void setMch_keyword(String mch_keyword) {
        this.mch_keyword = mch_keyword;
    }
}