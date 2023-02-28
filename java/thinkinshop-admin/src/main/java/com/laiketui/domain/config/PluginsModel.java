package com.laiketui.domain.config;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_plugins")
public class PluginsModel {
    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 插件名称
     */
    private String plugin_name;

    /**
     * 插件编码
     */
    private String plugin_code;

    /**
     * 插件图标
     */
    private String plugin_img;

    /**
     * 安装时间
     */
    private Date optime;

    /**
     * 安装状态0失败1成功
     */
    private Integer status;

    /**
     * 卸载状态0未卸载1已卸载
     */
    private Integer flag = 0;

    /**
     * 操作人用户名
     */
    private String opuser;

    /**
     * 获取主键
     *
     * @return id - 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取插件名称
     *
     * @return plugin_name - 插件名称
     */
    public String getPlugin_name() {
        return plugin_name;
    }

    /**
     * 设置插件名称
     *
     * @param plugin_name 插件名称
     */
    public void setPlugin_name(String plugin_name) {
        this.plugin_name = plugin_name == null ? null : plugin_name.trim();
    }

    /**
     * 获取插件编码
     *
     * @return plugin_code - 插件编码
     */
    public String getPlugin_code() {
        return plugin_code;
    }

    /**
     * 设置插件编码
     *
     * @param plugin_code 插件编码
     */
    public void setPlugin_code(String plugin_code) {
        this.plugin_code = plugin_code == null ? null : plugin_code.trim();
    }

    /**
     * 获取插件图标
     *
     * @return plugin_img - 插件图标
     */
    public String getPlugin_img() {
        return plugin_img;
    }

    /**
     * 设置插件图标
     *
     * @param plugin_img 插件图标
     */
    public void setPlugin_img(String plugin_img) {
        this.plugin_img = plugin_img == null ? null : plugin_img.trim();
    }

    /**
     * 获取安装时间
     *
     * @return optime - 安装时间
     */
    public Date getOptime() {
        return optime;
    }

    /**
     * 设置安装时间
     *
     * @param optime 安装时间
     */
    public void setOptime(Date optime) {
        this.optime = optime;
    }

    /**
     * 获取安装状态0失败1成功
     *
     * @return status - 安装状态0失败1成功
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置安装状态0失败1成功
     *
     * @param status 安装状态0失败1成功
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取卸载状态0未卸载1已卸载
     *
     * @return flag - 卸载状态0未卸载1已卸载
     */
    public Integer getFlag() {
        return flag;
    }

    /**
     * 设置卸载状态0未卸载1已卸载
     *
     * @param flag 卸载状态0未卸载1已卸载
     */
    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    /**
     * 获取操作人用户名
     *
     * @return opuser - 操作人用户名
     */
    public String getOpuser() {
        return opuser;
    }

    /**
     * 设置操作人用户名
     *
     * @param opuser 操作人用户名
     */
    public void setOpuser(String opuser) {
        this.opuser = opuser == null ? null : opuser.trim();
    }
}