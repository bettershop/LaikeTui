package com.laiketui.domain.message;

import java.io.Serializable;

/**
 * @author wangxian
 */
public class TemplateData implements Serializable  {
    private String value;

    public TemplateData(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
