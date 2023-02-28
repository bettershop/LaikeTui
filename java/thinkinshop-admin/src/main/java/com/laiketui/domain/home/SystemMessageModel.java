package com.laiketui.domain.home;

import com.laiketui.domain.vo.PageModel;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Trick
 */
@Table(name = "lkt_system_message")
public class SystemMessageModel {


    /**
     * 分页
     */
    @Transient
    private PageModel pageModel;

    public PageModel getPageModel() {
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }

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
     * 发送人ID
     */
    private String senderid;

    /**
     * 接收人ID
     */
    private String recipientid;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间
     */
    private Date time;

    /**
     * 1未读  2 已读
     */
    private Integer type;

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
     * 获取发送人ID
     *
     * @return senderid - 发送人ID
     */
    public String getSenderid() {
        return senderid;
    }

    /**
     * 设置发送人ID
     *
     * @param senderid 发送人ID
     */
    public void setSenderid(String senderid) {
        this.senderid = senderid == null ? null : senderid.trim();
    }

    /**
     * 获取接收人ID
     *
     * @return recipientid - 接收人ID
     */
    public String getRecipientid() {
        return recipientid;
    }

    /**
     * 设置接收人ID
     *
     * @param recipientid 接收人ID
     */
    public void setRecipientid(String recipientid) {
        this.recipientid = recipientid == null ? null : recipientid.trim();
    }

    /**
     * 获取标题
     *
     * @return title - 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     *
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取时间
     *
     * @return time - 时间
     */
    public Date getTime() {
        return time;
    }

    /**
     * 设置时间
     *
     * @param time 时间
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * 获取1未读  2 已读
     *
     * @return type - 1未读  2 已读
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1未读  2 已读
     *
     * @param type 1未读  2 已读
     */
    public void setType(Integer type) {
        this.type = type;
    }
}