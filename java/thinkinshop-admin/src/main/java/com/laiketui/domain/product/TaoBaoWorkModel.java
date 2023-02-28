package com.laiketui.domain.product;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_taobao_work")
public class TaoBaoWorkModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 商城id
     */
    private Integer store_id;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 状态：0.待执行 1.执行中 2.执行完成
     */
    private Integer status;

    /**
     * 任务创建时间
     */
    private Date creattime;

    /**
     * 执行时间
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
     * 获取任务标题
     *
     * @return title - 任务标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置任务标题
     *
     * @param title 任务标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取状态：0.待执行 1.执行中 2.执行完成
     *
     * @return status - 状态：0.待执行 1.执行中 2.执行完成
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：0.待执行 1.执行中 2.执行完成
     *
     * @param status 状态：0.待执行 1.执行中 2.执行完成
     */
    public void setStatus(Integer status) {
        this.status = status;
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
}