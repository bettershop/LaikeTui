package com.laiketui.domain.systems;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统配置
 *
 * @author Trick
 * @date 2021/8/4 14:35
 */
@Table(name = "lkt_system_configuration")
public class SystemConfigurationModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商户id
     */
    private Integer store_id;

    /**
     * logo
     */
    private String logo;

    /**
     * 版权信息
     */
    private String copyright_information;

    /**
     * 备案信息
     */
    private String record_information;

    /**
     * 登录页链接
     */
    private String link_to_landing_page;

    /**
     * 添加时间
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
     * 获取商户id
     *
     * @return store_id - 商户id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商户id
     *
     * @param store_id 商户id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取logo
     *
     * @return logo - logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置logo
     *
     * @param logo logo
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 获取版权信息
     *
     * @return copyright_information - 版权信息
     */
    public String getCopyright_information() {
        return copyright_information;
    }

    /**
     * 设置版权信息
     *
     * @param copyright_information 版权信息
     */
    public void setCopyright_information(String copyright_information) {
        this.copyright_information = copyright_information == null ? null : copyright_information.trim();
    }

    /**
     * 获取备案信息
     *
     * @return record_information - 备案信息
     */
    public String getRecord_information() {
        return record_information;
    }

    /**
     * 设置备案信息
     *
     * @param record_information 备案信息
     */
    public void setRecord_information(String record_information) {
        this.record_information = record_information == null ? null : record_information.trim();
    }

    /**
     * 获取登录页链接
     *
     * @return link_to_landing_page - 登录页链接
     */
    public String getLink_to_landing_page() {
        return link_to_landing_page;
    }

    /**
     * 设置登录页链接
     *
     * @param link_to_landing_page 登录页链接
     */
    public void setLink_to_landing_page(String link_to_landing_page) {
        this.link_to_landing_page = link_to_landing_page == null ? null : link_to_landing_page.trim();
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