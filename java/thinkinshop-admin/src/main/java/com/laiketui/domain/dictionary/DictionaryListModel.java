package com.laiketui.domain.dictionary;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 数据字典值表
 *
 * @author Trick
 * @date 2021/2/1 14:06
 */
@Table(name = "lkt_data_dictionary_list")
public class DictionaryListModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 编码
     */
    private String code;

    /**
     * 数据字典名称ID
     */
    private Integer sid;

    /**
     * 上级名称
     */
    private String s_name;

    /**
     * value
     */
    private String value;

    /**
     * 值
     */
    private String text;

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
     * 获取编码
     *
     * @return code - 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置编码
     *
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取数据字典名称ID
     *
     * @return sid - 数据字典名称ID
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置数据字典名称ID
     *
     * @param sid 数据字典名称ID
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取上级名称
     *
     * @return s_name - 上级名称
     */
    public String getS_name() {
        return s_name;
    }

    /**
     * 设置上级名称
     *
     * @param s_name 上级名称
     */
    public void setS_name(String s_name) {
        this.s_name = s_name == null ? null : s_name.trim();
    }

    /**
     * 获取value
     *
     * @return value - value
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置value
     *
     * @param value value
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * 获取值
     *
     * @return text - 值
     */
    public String getText() {
        return text;
    }

    /**
     * 设置值
     *
     * @param text 值
     */
    public void setText(String text) {
        this.text = text == null ? null : text.trim();
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