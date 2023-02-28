package com.laiketui.domain.vo.Tool;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 导出参数
 *
 * @author Trick
 * @date 2021/7/29 16:08
 */
public class ExcelParamVo {
    /**
     * excel标题
     */
    private String title;
    /**
     * 头部
     */
    private String[] headerList;
    /**
     * 内容key
     */
    private String[] valueList;
    /**
     * 内容集合
     */
    private List<Map<String, Object>> list;
    private HttpServletResponse response;

    /**
     * 头部文字
     */
    private String topTitle;

    public String getTopTitle() {
        return topTitle;
    }

    public void setTopTitle(String topTitle) {
        this.topTitle = topTitle;
    }

    /**
     * 合并单元格参数
     * 起始行、终止行号、起始列号、终止列号
     */
    private Integer[] merge;

    public Integer[] getMerge() {
        return merge;
    }

    public void setMerge(Integer[] merge) {
        this.merge = merge;
    }

    /**
     * 是否需要序号
     */
    private boolean isNeedNo;


    public boolean isNeedNo() {
        return isNeedNo;
    }

    public void setNeedNo(boolean needNo) {
        isNeedNo = needNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getHeaderList() {
        return headerList;
    }

    public void setHeaderList(String[] headerList) {
        this.headerList = headerList;
    }

    public String[] getValueList() {
        return valueList;
    }

    public void setValueList(String[] valueList) {
        this.valueList = valueList;
    }

    public List<Map<String, Object>> getList() {
        return list;
    }

    public void setList(List<Map<String, Object>> list) {
        this.list = list;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
