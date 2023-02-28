package com.laiketui.domain.systems;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 系统公告
 *
 * @author Trick
 * @date 2021/1/19 15:51
 */
@Table(name = "lkt_system_tell")
public class SystemTellModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城ID
     */
    private Integer store_id;

    /**
     * 公告标题
     */
    private String title;

    /**
     * 公告类型: 1--系统维护  2--版本更新
     */
    private Integer type;

    /**
     * 开始时间
     */
    private String startdate;

    /**
     * 结束时间
     */
    private String enddate;

    /**
     * 内容
     */
    private String content;

    /**
     * 时间类型: 1--区间时间  2--不限制
     */
    private Integer timetype;

    /**
     * 添加时间
     */
    private Date add_time;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城ID
     *
     * @return store_id - 商城ID
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城ID
     *
     * @param store_id 商城ID
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取公告标题
     *
     * @return title - 公告标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置公告标题
     *
     * @param title 公告标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取公告类型: 1--系统维护  2--版本更新
     *
     * @return type - 公告类型: 1--系统维护  2--版本更新
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置公告类型: 1--系统维护  2--版本更新
     *
     * @param type 公告类型: 1--系统维护  2--版本更新
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取开始时间
     *
     * @return startdate - 开始时间
     */
    public String getStartdate() {
        return startdate;
    }

    /**
     * 设置开始时间
     *
     * @param startdate 开始时间
     */
    public void setStartdate(String startdate) {
        this.startdate = startdate == null ? null : startdate.trim();
    }

    /**
     * 获取结束时间
     *
     * @return enddate - 结束时间
     */
    public String getEnddate() {
        return enddate;
    }

    /**
     * 设置结束时间
     *
     * @param enddate 结束时间
     */
    public void setEnddate(String enddate) {
        this.enddate = enddate == null ? null : enddate.trim();
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
     * 获取时间类型: 1--区间时间  2--不限制
     *
     * @return timetype - 时间类型: 1--区间时间  2--不限制
     */
    public Integer getTimetype() {
        return timetype;
    }

    /**
     * 设置时间类型: 1--区间时间  2--不限制
     *
     * @param timetype 时间类型: 1--区间时间  2--不限制
     */
    public void setTimetype(Integer timetype) {
        this.timetype = timetype;
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