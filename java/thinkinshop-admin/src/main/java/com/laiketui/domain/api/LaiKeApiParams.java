package com.laiketui.domain.api;

import com.google.common.base.Objects;

import java.io.Serializable;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/21 16:45
 * @version: 1.0
 * @modified By:
 */
public class LaiKeApiParams implements Serializable,Cloneable {

    /**参数位置*/
    private int pos;
    /**参数名称*/
    private String name;
    /**参数类型*/
    private String type;
    /**可以扩展为支持json字符串格式的多注解模式*/
    private String[] annotation;
    /**可以扩展为支持json字符串格式的多别名·模式*/
    /*
        {
            tel:'phone',
            mobile:'phone',
            dh:'phone',
        }
     */
    private String aliasNames;

    public LaiKeApiParams(int pos, String name, String type, String[] annotation, String aliasNames) {
        this.pos = pos;
        this.name = name;
        this.type = type;
        this.annotation = annotation;
        this.aliasNames = aliasNames;
    }

    public LaiKeApiParams(){}

    public String[] getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String[] annotation) {
        this.annotation = annotation;
    }

    public String getAliasNames() {
        return aliasNames;
    }

    public void setAliasNames(String aliasNames) {
        this.aliasNames = aliasNames;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LaiKeApiParams)) return false;
        LaiKeApiParams that = (LaiKeApiParams) o;
        return pos == that.pos &&
                Objects.equal(name, that.name) &&
                Objects.equal(type, that.type) &&
                Objects.equal(annotation, that.annotation) &&
                Objects.equal(aliasNames, that.aliasNames);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(pos, name, type, annotation,aliasNames);
    }
}
