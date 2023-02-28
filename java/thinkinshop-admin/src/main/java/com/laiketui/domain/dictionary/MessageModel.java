package com.laiketui.domain.dictionary;

import javax.persistence.*;
import java.util.Date;


/**
 * 短信模板列模型
 *
 * @author Trick
 * @date 2020/9/24 14:44
 */
@Table(name = "lkt_message")
public class MessageModel {
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
     * 短信签名
     */
    @Column(name = "SignName")
    private String signName;

    /**
     * 短信模板名称
     */
    private String name;

    /**
     * 类型 0:验证码 1:自定义
     */
    private Integer type;

    /**
     * 类别 0:通用 1:申请通过 2:申请拒绝 3:订单提现 4:发货提现 5:收货提现
     */
    private Integer type1;

    /**
     * 短信模板Code
     */
    @Column(name = "TemplateCode")
    private String templateCode;

    /**
     * 内容
     */
    private String content;

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
     * 获取短信签名
     *
     * @return SignName - 短信签名
     */
    public String getSignName() {
        return signName;
    }

    /**
     * 设置短信签名
     *
     * @param signName 短信签名
     */
    public void setSignName(String signName) {
        this.signName = signName == null ? null : signName.trim();
    }

    /**
     * 获取短信模板名称
     *
     * @return name - 短信模板名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置短信模板名称
     *
     * @param name 短信模板名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取类型 0:验证码 1:自定义
     *
     * @return type - 类型 0:验证码 1:自定义
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0:验证码 1:自定义
     *
     * @param type 类型 0:验证码 1:自定义
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
     * 获取短信模板Code
     *
     * @return TemplateCode - 短信模板Code
     */
    public String getTemplateCode() {
        return templateCode;
    }

    /**
     * 设置短信模板Code
     *
     * @param templateCode 短信模板Code
     */
    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
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