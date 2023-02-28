package com.laiketui.domain.log;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Trick
 */
@Table(name = "lkt_files_record")
public class FilesRecordModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 商城id
     */
    private String store_id;

    /**
     * 平台类型
     */
    private String store_type;

    /**
     * 分组
     */
    @Column(name = "`group`")
    private String group;

    /**
     * 上传方式 1:本地 2:阿里云 3:腾讯云 4:七牛云
     */
    private String upload_mode;

    /**
     * 图片名称
     */
    private String image_name;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * 店铺id
     */
    private Integer mch_id;
    /**
     * 回收标识
     */
    private Integer recycle;

    /**
     * 图片标题
     */
    private String title;

    /**
     * 图片说明
     */
    @Column(name = "`explain`")
    private String explain;

    /**
     * 代替文本
     */
    @Column(name = "alternative_text")
    private String alternative_text;
    /**
     * 图像描述
     */
    @Column(name = "`describe`")
    private String describe;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getAlternative_text() {
        return alternative_text;
    }

    public void setAlternative_text(String alternative_text) {
        this.alternative_text = alternative_text;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getMch_id() {
        return mch_id;
    }

    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    public Integer getRecycle() {
        return recycle;
    }

    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商城id
     *
     * @return store_id - 商城id
     */
    public String getStore_id() {
        return store_id;
    }

    /**
     * 设置商城id
     *
     * @param store_id 商城id
     */
    public void setStore_id(String store_id) {
        this.store_id = store_id == null ? null : store_id.trim();
    }

    /**
     * 获取平台类型
     *
     * @return store_type - 平台类型
     */
    public String getStore_type() {
        return store_type;
    }

    /**
     * 设置平台类型
     *
     * @param store_type 平台类型
     */
    public void setStore_type(String store_type) {
        this.store_type = store_type == null ? null : store_type.trim();
    }

    /**
     * 获取分组
     *
     * @return group - 分组
     */
    public String getGroup() {
        return group;
    }

    /**
     * 设置分组
     *
     * @param group 分组
     */
    public void setGroup(String group) {
        this.group = group == null ? null : group.trim();
    }

    /**
     * 获取上传方式 1:本地 2:阿里云 3:腾讯云 4:七牛云
     *
     * @return upload_mode - 上传方式 1:本地 2:阿里云 3:腾讯云 4:七牛云
     */
    public String getUpload_mode() {
        return upload_mode;
    }

    /**
     * 设置上传方式 1:本地 2:阿里云 3:腾讯云 4:七牛云
     *
     * @param upload_mode 上传方式 1:本地 2:阿里云 3:腾讯云 4:七牛云
     */
    public void setUpload_mode(String upload_mode) {
        this.upload_mode = upload_mode == null ? null : upload_mode.trim();
    }

    /**
     * 获取图片名称
     *
     * @return image_name - 图片名称
     */
    public String getImage_name() {
        return image_name;
    }

    /**
     * 设置图片名称
     *
     * @param image_name 图片名称
     */
    public void setImage_name(String image_name) {
        this.image_name = image_name == null ? null : image_name.trim();
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
}