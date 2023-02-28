package com.laiketui.domain.product;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Trick
 */
@Table(name = "lkt_comments")
public class CommentsModel {
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
     * 订单ID
     */
    private String oid;

    /**
     * 用户id
     */
    private String uid;

    /**
     * 商品id
     */
    private String pid;

    /**
     * 属性id
     */
    private Integer attribute_id;

    /**
     * 商品规格
     */
    private String size;

    /**
     * 评价内容
     */
    private String content;

    /**
     * 评价类型
     */
    @Column(name = "CommentType")
    private String commentType;

    /**
     * 匿名 
     */
    private String anonymous;

    private Date add_time;

    /**
     * 追评
     */
    private String review;

    /**
     * 追评时间
     */
    private Date review_time;

    /**
     * 订单详情id
     */
    private Integer order_detail_id;

    public Integer getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(Integer order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    /**
     * 评价类型-KJ 砍价评论-PT 拼团评论
     */
    private String type;

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
     * 获取订单ID
     *
     * @return oid - 订单ID
     */
    public String getOid() {
        return oid;
    }

    /**
     * 设置订单ID
     *
     * @param oid 订单ID
     */
    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    /**
     * 获取用户id
     *
     * @return uid - 用户id
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置用户id
     *
     * @param uid 用户id
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取商品id
     *
     * @return pid - 商品id
     */
    public String getPid() {
        return pid;
    }

    /**
     * 设置商品id
     *
     * @param pid 商品id
     */
    public void setPid(String pid) {
        this.pid = pid == null ? null : pid.trim();
    }

    /**
     * 获取属性id
     *
     * @return attribute_id - 属性id
     */
    public Integer getAttribute_id() {
        return attribute_id;
    }

    /**
     * 设置属性id
     *
     * @param attribute_id 属性id
     */
    public void setAttribute_id(Integer attribute_id) {
        this.attribute_id = attribute_id;
    }

    /**
     * 获取商品规格
     *
     * @return size - 商品规格
     */
    public String getSize() {
        return size;
    }

    /**
     * 设置商品规格
     *
     * @param size 商品规格
     */
    public void setSize(String size) {
        this.size = size == null ? null : size.trim();
    }

    /**
     * 获取评价内容
     *
     * @return content - 评价内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置评价内容
     *
     * @param content 评价内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取评价类型
     *
     * @return CommentType - 评价类型
     */
    public String getCommentType() {
        return commentType;
    }

    /**
     * 设置评价类型
     *
     * @param commentType 评价类型
     */
    public void setCommentType(String commentType) {
        this.commentType = commentType == null ? null : commentType.trim();
    }

    /**
     * 获取匿名 
     *
     * @return anonymous - 匿名 
     */
    public String getAnonymous() {
        return anonymous;
    }

    /**
     * 设置匿名 
     *
     * @param anonymous 匿名 
     */
    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous == null ? null : anonymous.trim();
    }

    /**
     * @return add_time
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * @param add_time
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }

    /**
     * 获取追评
     *
     * @return review - 追评
     */
    public String getReview() {
        return review;
    }

    /**
     * 设置追评
     *
     * @param review 追评
     */
    public void setReview(String review) {
        this.review = review == null ? null : review.trim();
    }

    /**
     * 获取追评时间
     *
     * @return review_time - 追评时间
     */
    public Date getReview_time() {
        return review_time;
    }

    /**
     * 设置追评时间
     *
     * @param review_time 追评时间
     */
    public void setReview_time(Date review_time) {
        this.review_time = review_time;
    }

    /**
     * 获取评价类型-KJ 砍价评论-PT 拼团评论
     *
     * @return type - 评价类型-KJ 砍价评论-PT 拼团评论
     */
    public String getType() {
        return type;
    }

    /**
     * 设置评价类型-KJ 砍价评论-PT 拼团评论
     *
     * @param type 评价类型-KJ 砍价评论-PT 拼团评论
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }
}