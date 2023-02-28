package com.laiketui.domain.api;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/26 13:57
 * @version: 1.0
 * @modified By:
 */

public class DubboApi extends LaiKeApi {
    /**
     * 组
     */
    private String group;

    /**
     * 版本
     */
    private String version;

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "DubboApi{" +
                "group='" + group + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
