package com.laiketui.domain.config;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_message_list")
public class MessageListModel {
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
     * 类型 0:验证码 1:短信通知
     */
    private Integer type;

    /**
     * 类别 0:通用 1:申请通过 2:申请拒绝 3:订单提现 4:发货提现 5:收货提现
     */
    private Integer type1;

    /**
     * 模板id
     */
    @Column(name = "Template_id")
    private Integer template_id;

    /**
     * code
     * 存储键值对,键为占位符,值为占位符的值
     */
    private String content;

    /**
     * 类型 0:商城 1:店铺
     */
    private Integer status;

    /**
     * 客户id/店主id
     */
    private String admin_id;

    /**
     * 创建时间
     */
    private Date add_time;

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
     * 获取类型 0:验证码 1:短信通知
     *
     * @return type - 类型 0:验证码 1:短信通知
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:验证码 1:短信通知
     *
     * @param type 类型 0:验证码 1:短信通知
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取类别 0:通用 1:申请通过 2:申请拒绝 3:订单提现 4:发货提现 5:收货提现
     *
     * @return type1 - 类别 0:通用 1:申请通过 2:申请拒绝 3:订单提现 4:发货提现 5:收货提现
     */
    public Integer getType1() {
        return type1;
    }

    /**
     * 设置类别 0:通用 1:申请通过 2:申请拒绝 3:订单提现 4:发货提现 5:收货提现
     *
     * @param type1 类别 0:通用 1:申请通过 2:申请拒绝 3:订单提现 4:发货提现 5:收货提现
     */
    public void setType1(Integer type1) {
        this.type1 = type1;
    }

    /**
     * 获取模板id
     *
     * @return Template_id - 模板id
     */
    public Integer getTemplate_id() {
        return template_id;
    }

    /**
     * 设置模板id
     *
     * @param template_id 模板id
     */
    public void setTemplate_id(Integer template_id) {
        this.template_id = template_id;
    }

    /**
     * 获取code
     *
     * @return content - code
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置code
     *
     * @param content code
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取类型 0:商城 1:店铺
     *
     * @return status - 类型 0:商城 1:店铺
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置类型 0:商城 1:店铺
     *
     * @param status 类型 0:商城 1:店铺
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取客户id/店主id
     *
     * @return admin_id - 客户id/店主id
     */
    public String getAdmin_id() {
        return admin_id;
    }

    /**
     * 设置客户id/店主id
     *
     * @param admin_id 客户id/店主id
     */
    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id == null ? null : admin_id.trim();
    }

    /**
     * 获取创建时间
     *
     * @return add_time - 创建时间
     */
    public Date getAdd_time() {
        return add_time;
    }

    /**
     * 设置创建时间
     *
     * @param add_time 创建时间
     */
    public void setAdd_time(Date add_time) {
        this.add_time = add_time;
    }
}