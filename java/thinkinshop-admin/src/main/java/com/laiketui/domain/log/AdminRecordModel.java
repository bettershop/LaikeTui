package com.laiketui.domain.log;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 操作记录表
 *
 * @author Trick
 * @date 2021/1/7 17:44
 */
@Table(name = "lkt_admin_record")
public class AdminRecordModel {
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
     * 管理员账号
     */
    private String admin_name;

    /**
     * 事件
     */
    private String event;

    /**
     * 类型 0:登录/退出 1:添加 2:修改 3:删除 4:导出 5:启用/禁用 6:通过/拒绝 10删除订单
     */
    private Integer type;

    /**
     * 操作时间
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
     * 获取管理员账号
     *
     * @return admin_name - 管理员账号
     */
    public String getAdmin_name() {
        return admin_name;
    }

    /**
     * 设置管理员账号
     *
     * @param admin_name 管理员账号
     */
    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name == null ? null : admin_name.trim();
    }

    /**
     * 获取事件
     *
     * @return event - 事件
     */
    public String getEvent() {
        return event;
    }

    /**
     * 设置事件
     *
     * @param event 事件
     */
    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    /**
     * 获取类型 0:登录/退出 1:添加 2:修改 3:删除 4:导出 5:启用/禁用 6:通过/拒绝 10删除订单
     *
     * @return type - 类型 0:登录/退出 1:添加 2:修改 3:删除 4:导出 5:启用/禁用 6:通过/拒绝 10删除订单
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:登录/退出 1:添加 2:修改 3:删除 4:导出 5:启用/禁用 6:通过/拒绝 10删除订单
     *
     * @param type 类型 0:登录/退出 1:添加 2:修改 3:删除 4:导出 5:启用/禁用 6:通过/拒绝 10删除订单
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取操作时间
     *
     * @return add_date - 操作时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置操作时间
     *
     * @param add_date 操作时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }
}