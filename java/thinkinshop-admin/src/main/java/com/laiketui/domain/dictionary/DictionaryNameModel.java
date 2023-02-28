package com.laiketui.domain.dictionary;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * 数据字典名称
 *
 * @author Trick
 * @date 2020/9/24 13:21
 */
@Table(name = "lkt_data_dictionary_name")
public class DictionaryNameModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 失效
     */
    @Transient
    public static final Integer STATUS_CLOSE = 0;
    /**
     * 生效
     */
    @Transient
    public static final Integer STATUS_OPEN = 1;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否生效 0:不是 1:是
     */
    private Integer status;

    /**
     * 管理员名称
     */
    private String admin_name;

    /**
     * 回收站 0:正常 1:回收
     */
    private Integer recycle;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 字典代码
     */
    private String dic_code;

    /**
     * 是否是系统核心字典
     */
    private int is_core;

    /**
     * 字典目录明细
     */
    private List<DictionaryListModel> dic_list;

    public String getDic_code() {
        return dic_code;
    }

    public void setDic_code(String dic_code) {
        this.dic_code = dic_code;
    }

    public int getIs_core() {
        return is_core;
    }

    public void setIs_core(int is_core) {
        this.is_core = is_core;
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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取是否生效 0:不是 1:是
     *
     * @return status - 是否生效 0:不是 1:是
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置是否生效 0:不是 1:是
     *
     * @param status 是否生效 0:不是 1:是
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取管理员名称
     *
     * @return admin_name - 管理员名称
     */
    public String getAdmin_name() {
        return admin_name;
    }

    /**
     * 设置管理员名称
     *
     * @param admin_name 管理员名称
     */
    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name == null ? null : admin_name.trim();
    }

    /**
     * 获取回收站 0:正常 1:回收
     *
     * @return recycle - 回收站 0:正常 1:回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置回收站 0:正常 1:回收
     *
     * @param recycle 回收站 0:正常 1:回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
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
}