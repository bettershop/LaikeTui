package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author Trick
 */
@Table(name = "lkt_express")
public class ExpressModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 快递名称
     */
    private String kuaidi_name;

    /**
     * 简称
     */
    private String type;

    /**
     * 快递开关 1=开 2=关
     */
    private Integer is_open;
    /**
     * 序号
     */
    private Integer sort;
    /**
     * 是否回收
     */
    private Integer recycle;
    /**
     * 日期
     */
    private Date add_date;

    public Integer getIs_open() {
        return is_open;
    }

    public void setIs_open(Integer is_open) {
        this.is_open = is_open;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

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
     * 获取快递名称
     *
     * @return kuaidi_name - 快递名称
     */
    public String getKuaidi_name() {
        return kuaidi_name;
    }

    /**
     * 设置快递名称
     *
     * @param kuaidi_name 快递名称
     */
    public void setKuaidi_name(String kuaidi_name) {
        this.kuaidi_name = kuaidi_name == null ? null : kuaidi_name.trim();
    }

    /**
     * 获取简称
     *
     * @return type - 简称
     */
    public String getType() {
        return type;
    }

    /**
     * 设置简称
     *
     * @param type 简称
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}