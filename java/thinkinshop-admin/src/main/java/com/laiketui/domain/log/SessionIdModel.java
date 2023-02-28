package com.laiketui.domain.log;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_session_id")
public class SessionIdModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * session_id
     */
    private String session_id;

    /**
     * 内容
     */
    private String content;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 类型 0.发送短信 1.商


品 2.退货申请 3.评论
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
     * 获取session_id
     *
     * @return session_id - session_id
     */
    public String getSession_id() {
        return session_id;
    }

    /**
     * 设置session_id
     *
     * @param session_id session_id
     */
    public void setSession_id(String session_id) {
        this.session_id = session_id == null ? null : session_id.trim();
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
     * 获取添加时间
     *
     * @return add_date - 添加时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置添加时间
     *
     * @param add_date 添加时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    /**
     * 获取类型 0.发送短信 1.商


品 2.退货申请 3.评论
     *
     * @return type - 类型 0.发送短信 1.商


品 2.退货申请 3.评论
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 0.发送短信 1.商


品 2.退货申请 3.评论
     *
     * @param type 类型 0.发送短信 1.商


品 2.退货申请 3.评论
     */
    public void setType(Integer type) {
        this.type = type;
    }
}