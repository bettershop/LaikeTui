package com.laiketui.root.gateway.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/21 15:36
 * @version: 1.0
 * @modified By:
 */
public class LaiKeApiDto {


    /**
     * 接口简称
     */
    private String api;
    /**
     * 协议
     */
    private String protocol;
    /**
     * 参数
     */
    private String params;
    /**
     * 版本号
     */
    private String version;
    /**
     * 分组
     */
    @Deprecated
    private String group;
    /**
     * 前端传来的token
     */
    private String accessId;
    /**
     * 所属模块
     */
    private String module = "app" ;

    /**
     * 兼容原有php系统
     */
    private String m;
    private String app;
    private String action;
    private Integer store_id;
    private Integer store_type;
    private String access_id;
    /**
     * 语言
     */
    private String language;

    private InputStream inputStream;
    private OutputStream outputStream;
    /**
     * 单文件上传
     */
    private MultipartFile image;
    /**
     * 单文件上传
     */
    private MultipartFile file;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    /**
     * 文件流集合
     */
    private MultipartFile[] files;

    private String exportType;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public String getExportType() {
        return exportType;
    }

    public void setExportType(String exportType) {
        this.exportType = exportType;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public Integer getStore_type() {
        return store_type;
    }

    public void setStore_type(Integer store_type) {
        this.store_type = store_type;
    }

    public String getAccess_id() {
        return access_id;
    }

    public void setAccess_id(String access_id) {
        this.access_id = access_id;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
