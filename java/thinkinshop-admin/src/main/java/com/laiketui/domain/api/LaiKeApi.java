package com.laiketui.domain.api;


import com.google.common.base.Objects;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/16 16:56
 * @version: 1.0
 * @modified By:
 */
public class LaiKeApi implements Serializable,Cloneable {

    /**接口所在ip*/
    private String nodeIp;

    /**接口所在端口*/
    private int port;

    /**超时时间毫秒 默认8秒 */
    private int timeout = 8000;

    /**
     * api使用时候的key
     */
    private String apiKey;

    /**
     * api对应的class
     */
    private String apiClass;

    /**
     * api对应的主键
     */
    private String id;

    /**
     * 新增时间
     */
    private String addTime;

    /**
     * 更新时间
     */
    private String upTime;

    /**
     * 组
     */
    private String group;

    /**
     * 版本, 默认版本：1.0.0
     */
    private String version = "1.0.0";

    /**
     * 模块
     */
    private String modules;

    /**
     * 返回类型
     */
    private String returnType;

    /**
     *参数类型和名称
     */
    private String paramsJsonInfo;

    /**
     * 是否需要登陆 默认 false
     */
    private Boolean login;

    /**
     * 接口参数列表
     */
    private List<LaiKeApiParams> paramsList;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 协议
     */
    private String protocol;

    /**
     * 输入的参数
     */
    private String inputParams;

    /**
     * 请求中的参数
     */
    private Map context;

    /**
     * 对应地址
     */
    private String uri;

    /**
     * 默认请求方法 支持 post/get
     */
    private String requestMethodType = "POST";

    private byte[] datas;
    private InputStream inputStream;
    private OutputStream outputStream;

    /**
     * php 多个接口访问地址 映射一个 java 接口
     */
    private String[] phpUrlMapping;


    public String[] getPhpUrlMapping() {
        return phpUrlMapping;
    }

    public void setPhpUrlMapping(String[] phpUrlMapping) {
        this.phpUrlMapping = phpUrlMapping;
    }

    public String getRequestMethodType() {
        return requestMethodType;
    }

    public void setRequestMethodType(String requestMethodType) {
        this.requestMethodType = requestMethodType;
    }

    public Map getContext() {
        return context;
    }

    public void setContext(Map context) {
        this.context = context;
    }

    public byte[] getDatas() {
        return datas;
    }

    public void setDatas(byte[] datas) {
        this.datas = datas;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getApiClass() {
        return apiClass;
    }

    public void setApiClass(String apiClass) {
        this.apiClass = apiClass;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getUpTime() {
        return upTime;
    }

    public void setUpTime(String upTime) {
        this.upTime = upTime;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModules() {
        return modules;
    }

    public void setModules(String modules) {
        this.modules = modules;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getParamsJsonInfo() {
        return paramsJsonInfo;
    }

    public void setParamsJsonInfo(String paramsJsonInfo) {
        this.paramsJsonInfo = paramsJsonInfo;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public List<LaiKeApiParams> getParamsList() {
        return paramsList;
    }

    public void setParamsList(List<LaiKeApiParams> paramsList) {
        this.paramsList = paramsList;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getInputParams() {

        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaiKeApi)) return false;
        LaiKeApi laiKeApi = (LaiKeApi) o;
        return getPort() == laiKeApi.getPort() &&
                getTimeout() == laiKeApi.getTimeout() &&
                Objects.equal(getNodeIp(), laiKeApi.getNodeIp()) &&
                Objects.equal(getApiKey(), laiKeApi.getApiKey()) &&
                Objects.equal(getApiClass(), laiKeApi.getApiClass()) &&
                Objects.equal(getId(), laiKeApi.getId()) &&
                Objects.equal(getAddTime(), laiKeApi.getAddTime()) &&
                Objects.equal(getUpTime(), laiKeApi.getUpTime()) &&
                Objects.equal(getGroup(), laiKeApi.getGroup()) &&
                Objects.equal(getVersion(), laiKeApi.getVersion()) &&
                Objects.equal(getModules(), laiKeApi.getModules()) &&
                Objects.equal(getReturnType(), laiKeApi.getReturnType()) &&
                Objects.equal(getParamsJsonInfo(), laiKeApi.getParamsJsonInfo()) &&
                Objects.equal(getLogin(), laiKeApi.getLogin()) &&
                Objects.equal(getParamsList(), laiKeApi.getParamsList()) &&
                Objects.equal(getMethodName(), laiKeApi.getMethodName()) &&
                Objects.equal(getProtocol(), laiKeApi.getProtocol()) &&
                Objects.equal(getInputParams(), laiKeApi.getInputParams()) &&
                Objects.equal(getContext(), laiKeApi.getContext()) &&
                Objects.equal(getUri(), laiKeApi.getUri()) &&
                Objects.equal(getRequestMethodType(), laiKeApi.getRequestMethodType()) &&
                Objects.equal(getDatas(), laiKeApi.getDatas()) &&
                Objects.equal(getInputStream(), laiKeApi.getInputStream()) &&
                Objects.equal(getOutputStream(), laiKeApi.getOutputStream()) &&
                Objects.equal(getPhpUrlMapping(), laiKeApi.getPhpUrlMapping());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getNodeIp(), getPort(), getTimeout(), getApiKey(), getApiClass(), getId(), getAddTime(), getUpTime(), getGroup(), getVersion(), getModules(), getReturnType(), getParamsJsonInfo(), getLogin(), getParamsList(), getMethodName(), getProtocol(), getInputParams(), getContext(), getUri(), getRequestMethodType(), getDatas(), getInputStream(), getOutputStream(), getPhpUrlMapping());
    }

    @Override
    public String toString() {
        return "LaiKeApi{" +
                "nodeIp='" + nodeIp + '\'' +
                ", port=" + port +
                ", timeout=" + timeout +
                ", apiKey='" + apiKey + '\'' +
                ", apiClass='" + apiClass + '\'' +
                ", id='" + id + '\'' +
                ", addTime='" + addTime + '\'' +
                ", upTime='" + upTime + '\'' +
                ", group='" + group + '\'' +
                ", version='" + version + '\'' +
                ", modules='" + modules + '\'' +
                ", returnType='" + returnType + '\'' +
                ", paramsJsonInfo='" + paramsJsonInfo + '\'' +
                ", login=" + login +
                ", paramsList=" + paramsList +
                ", methodName='" + methodName + '\'' +
                ", protocol='" + protocol + '\'' +
                ", inputParams='" + inputParams + '\'' +
                ", context=" + context +
                ", uri='" + uri + '\'' +
                ", requestMethodType='" + requestMethodType + '\'' +
                ", datas=" + Arrays.toString(datas) +
                ", inputStream=" + inputStream +
                ", outputStream=" + outputStream +
                ", phpUrlMapping=" + Arrays.toString(phpUrlMapping) +
                '}';
    }
}
