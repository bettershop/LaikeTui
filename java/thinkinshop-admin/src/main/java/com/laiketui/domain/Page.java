package com.laiketui.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/18 18:08
 * @version:
 * @modified By:
 */
public class Page implements Serializable {
    private static final Logger logger = LoggerFactory.getLogger(Page.class);
    private static ObjectMapper mapper = new ObjectMapper();
    public static int DEFAULT_PAGESIZE = 10;
    //当前页码
    private int pageNo;
    //每页行数
    private int pageSize;
    //总记录数
    private int totalRecord;
    //总页数
    private int totalPage;
    //查询条件
    private Map<String, String> params;
    //数组查询条件
    private Map<String, List<String>> paramLists;
    //Url地址
    private String searchUrl;

    //可以显示的页号(分隔符"|"，总页数变更时更新)
    private String pageNoDisp;

    private Page() {
        pageNo = 1;
        pageSize = DEFAULT_PAGESIZE;
        totalRecord = 0;
        totalPage = 0;
        params = Maps.newHashMap();
        paramLists = Maps.newHashMap();
        searchUrl = "";
        pageNoDisp = "";
    }

    public static Page newBuilder(int pageNo, int pageSize, String url){
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setSearchUrl(url);
        return page;
    }

    /**
     * 查询条件转JSON
     */
    public String getParaJson(){
        Map<String, Object> map = Maps.newHashMap();
        for (String key : params.keySet()){
            if ( params.get(key) != null  ){
                map.put(key, params.get(key));
            }
        }
        String json="";
        try {
            json = mapper.writeValueAsString(map);
        } catch (Exception e) {
            logger.error("转换JSON失败", params, e);
        }
        return json;
    }

    /**
     * 数组查询条件转JSON
     */
    public String getParaListJson(){
        Map<String, Object> map = Maps.newHashMap();
        for (String key : paramLists.keySet()){
            List<String> lists = paramLists.get(key);
            if ( lists != null && lists.size()>0 ){
                map.put(key, lists);
            }
        }
        String json="";
        try {
            json = mapper.writeValueAsString(map);
        } catch (Exception e) {
            logger.error("转换JSON失败", params, e);
        }
        return json;
    }

    /**
     * 总件数变化时，更新总页数并计算显示样式
     */
    private void refreshPage(){
        //总页数计算
        totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : (totalRecord/pageSize + 1);
        //防止超出最末页（浏览途中数据被删除的情况）
        if ( pageNo > totalPage && totalPage!=0){
            pageNo = totalPage;
        }
        pageNoDisp = computeDisplayStyleAndPage();
    }

    /**
     * 计算页号显示样式
     *  这里实现以下的分页样式("[]"代表当前页号)，可根据项目需求调整
     *   [1],2,3,4,5,6,7,8..12,13
     *   1,2..5,6,[7],8,9..12,13
     *   1,2..6,7,8,9,10,11,12,[13]
     */
    private String computeDisplayStyleAndPage(){
        List<Integer> pageDisplays = Lists.newArrayList();
        if ( totalPage <= 11 ){
            for (int i=1; i<=totalPage; i++){
                pageDisplays.add(i);
            }
        }else if ( pageNo < 7 ){
            for (int i=1; i<=8; i++){
                pageDisplays.add(i);
            }
            pageDisplays.add(0);// 0 表示 省略部分(下同)
            pageDisplays.add(totalPage-1);
            pageDisplays.add(totalPage);
        }else if ( pageNo> totalPage-6 ){
            pageDisplays.add(1);
            pageDisplays.add(2);
            pageDisplays.add(0);
            for (int i=totalPage-7; i<=totalPage; i++){
                pageDisplays.add(i);
            }
        }else{
            pageDisplays.add(1);
            pageDisplays.add(2);
            pageDisplays.add(0);
            for (int i=pageNo-2; i<=pageNo+2; i++){
                pageDisplays.add(i);
            }
            pageDisplays.add(0);
            pageDisplays.add(totalPage-1);
            pageDisplays.add(totalPage);
        }
        return Joiner.on("|").join(pageDisplays.toArray());
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        this.totalRecord = totalRecord;
        refreshPage();
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Map<String, List<String>> getParamLists() {
        return paramLists;
    }

    public void setParamLists(Map<String, List<String>> paramLists) {
        this.paramLists = paramLists;
    }
    public String getSearchUrl() {
        return searchUrl;
    }
    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }
    public String getPageNoDisp() {
        return pageNoDisp;
    }
    public void setPageNoDisp(String pageNoDisp) {
        this.pageNoDisp = pageNoDisp;
    }
}
