package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_comments_img")
public class CommentsImgModel {
    /**
     * 图片id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 评论图片
     */
    private String comments_url;

    /**
     * 评论id
     */
    private Integer comments_id;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * 类型 0:评论  1:追评
     */
    private Integer type;

    /**
     * 获取图片id
     *
     * @return id - 图片id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置图片id
     *
     * @param id 图片id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取评论图片
     *
     * @return comments_url - 评论图片
     */
    public String getComments_url() {
        return comments_url;
    }

    /**
     * 设置评论图片
     *
     * @param comments_url 评论图片
     */
    public void setComments_url(String comments_url) {
        this.comments_url = comments_url == null ? null : comments_url.trim();
    }

    /**
     * 获取评论id
     *
     * @return comments_id - 评论id
     */
    public Integer getComments_id() {
        return comments_id;
    }

    /**
     * 设置评论id
     *
     * @param comments_id 评论id
     */
    public void setComments_id(Integer comments_id) {
        this.comments_id = comments_id;
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

    /**
     * 获取类型 0:评论  1:追评
     *
     * @return type - 类型 0:评论  1:追评
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:评论  1:追评
     *
     * @param type 类型 0:评论  1:追评
     */
    public void setType(Integer type) {
        this.type = type;
    }
}