package com.laiketui.domain.vo;

import com.laiketui.domain.annotation.ParamsMapping;
import io.swagger.annotations.ApiModelProperty;

/**
 * 分页
 *
 * @author Trick
 * @date 2020/11/30 14:12
 */
public class PageModel {

    private static final int DEFAULT_PAGESIZE = 10;

    public PageModel() {
    }


    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页1", name = "start", hidden = true)
    private int pageNum;

    /**
     * 开始行-当前页转换
     */
    @ApiModelProperty(value = "当前页", name = "start")
    @ParamsMapping({"page", "start"})
    private int pageNo;

    /**
     * 每页数量
     */
    @ApiModelProperty(value = "每页数量", name = "end")
    @ParamsMapping({"pagesize", "end"})
    private int pageSize = DEFAULT_PAGESIZE;

    public int getPageNo() {
        return pageNo;
    }

    public PageModel(int pageNo) {
        setPageNo1(pageNo);
    }

    public PageModel(int pageStart, int pageSize) {
        this.pageNo = pageStart;
        this.pageSize = pageSize;
    }

    public PageModel(int pageStart, int pageSize, int pageNum) {
        this.pageNo = pageStart;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
    }

    public void setPageStart(int pageNo) {
        //自动计算分页起始行
        this.pageNo = pageNo * pageSize;
    }

    public void setPageNo(int pageNo) {
        this.pageNum = pageNo;
        this.setPageNo1(pageNo);
    }

    @Deprecated
    public void setPageNo1(int pageNo) {
        pageNum = pageNo;
        //前台第一页传1,后台数据从0开始
        if (pageNo <= 1) {
            pageNo = 0;
        } else {
            pageNo -= 1;
        }
        //自动计算分页起始行
        this.pageNo = pageNo * pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    @Deprecated
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        //需保证pageSize第一个注入不然会有问题
        setPageNo1(this.pageNum);
    }

    public int getPageNum() {
        if (pageNum < 1) {
            pageNum++;
        }
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
