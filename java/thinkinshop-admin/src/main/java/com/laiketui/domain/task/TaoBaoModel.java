package com.laiketui.domain.task;

import javax.persistence.*;
import java.util.Date;


/**
 * 淘宝抓取
 *
 * @author Trick
 * @date 2020/12/15 17:28
 */
@Table(name = "lkt_taobao")
public class TaoBaoModel {

    /**
     * 待获取
     */
    @Transient
    public static final Integer TAOBAO_STATUS_TO_BE_OBTAINED = 0;

    /**
     * 获取中
     */
    @Transient
    public static final Integer TAOBAO_STATUS_GETTING_ING = 1;
    /**
     * 获取成功
     */
    @Transient
    public static final Integer TAOBAO_STATUS_CRAWLING_SUCCESS = 2;
    /**
     * 获取失败
     */
    @Transient
    public static final Integer TAOBAO_STATUS_CRAWLING_FAIL = -1;

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
     * 所属任务ID
     */
    private Integer w_id;

    /**
     * 淘宝链接
     */
    private String link;

    /**
     * 商品ID
     */
    private String itemid;

    /**
     * 状态：0.待获取 1.获取中 2.获取成功 -1.获取失败
     */
    private Integer status;

    /**
     * 返回说明
     */
    private String msg;

    /**
     * 任务创建时间
     */
    private Date creattime;

    /**
     * 执行时间
     */
    private Date add_date;

    /**
     * 分类名称
     */
    private String cid;

    /**
     * 品牌id
     */
    private Integer brand_id;

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
     * 获取所属任务ID
     *
     * @return w_id - 所属任务ID
     */
    public Integer getW_id() {
        return w_id;
    }

    /**
     * 设置所属任务ID
     *
     * @param w_id 所属任务ID
     */
    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }

    /**
     * 获取淘宝链接
     *
     * @return link - 淘宝链接
     */
    public String getLink() {
        return link;
    }

    /**
     * 设置淘宝链接
     *
     * @param link 淘宝链接
     */
    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    /**
     * 获取商品ID
     *
     * @return itemid - 商品ID
     */
    public String getItemid() {
        return itemid;
    }

    /**
     * 设置商品ID
     *
     * @param itemid 商品ID
     */
    public void setItemid(String itemid) {
        this.itemid = itemid == null ? null : itemid.trim();
    }

    /**
     * 获取状态：0.待获取 1.获取中 2.获取成功 -1.获取失败
     *
     * @return status - 状态：0.待获取 1.获取中 2.获取成功 -1.获取失败
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：0.待获取 1.获取中 2.获取成功 -1.获取失败
     *
     * @param status 状态：0.待获取 1.获取中 2.获取成功 -1.获取失败
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取返回说明
     *
     * @return msg - 返回说明
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置返回说明
     *
     * @param msg 返回说明
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    /**
     * 获取任务创建时间
     *
     * @return creattime - 任务创建时间
     */
    public Date getCreattime() {
        return creattime;
    }

    /**
     * 设置任务创建时间
     *
     * @param creattime 任务创建时间
     */
    public void setCreattime(Date creattime) {
        this.creattime = creattime;
    }

    /**
     * 获取执行时间
     *
     * @return add_date - 执行时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置执行时间
     *
     * @param add_date 执行时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * 获取分类名称
     *
     * @return cid - 分类名称
     */
    public String getCid() {
        return cid;
    }

    /**
     * 设置分类名称
     *
     * @param cid 分类名称
     */
    public void setCid(String cid) {
        this.cid = cid == null ? null : cid.trim();
    }

    /**
     * 获取品牌id
     *
     * @return brand_id - 品牌id
     */
    public Integer getBrand_id() {
        return brand_id;
    }

    /**
     * 设置品牌id
     *
     * @param brand_id 品牌id
     */
    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }
}