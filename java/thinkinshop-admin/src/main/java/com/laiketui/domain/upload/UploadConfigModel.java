package com.laiketui.domain.upload;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 文件上传配置表
 *
 * @author Trick
 * @date 2020/9/28 14:25
 */
@Table(name = "lkt_upload_set")
public class UploadConfigModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     */
    private String upserver;

    /**
     * 类别
     */
    private String type;

    /**
     * 属性
     */
    private String attr;

    /**
     * 值
     */
    private String attrvalue;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     *
     * @return upserver - 上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     */
    public String getUpserver() {
        return upserver;
    }

    /**
     * 设置上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     *
     * @param upserver 上传服务器:1,本地　2,阿里云 3,腾讯云 4,七牛云
     */
    public void setUpserver(String upserver) {
        this.upserver = upserver;
    }

    /**
     * 获取类别
     *
     * @return type - 类别
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类别
     *
     * @param type 类别
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取属性
     *
     * @return attr - 属性
     */
    public String getAttr() {
        return attr;
    }

    /**
     * 设置属性
     *
     * @param attr 属性
     */
    public void setAttr(String attr) {
        this.attr = attr == null ? null : attr.trim();
    }

    /**
     * 获取值
     *
     * @return attrvalue - 值
     */
    public String getAttrvalue() {
        return attrvalue;
    }

    /**
     * 设置值
     *
     * @param attrvalue 值
     */
    public void setAttrvalue(String attrvalue) {
        this.attrvalue = attrvalue == null ? null : attrvalue.trim();
    }
}