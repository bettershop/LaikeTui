package com.laiketui.domain.nodatamodel;


import java.io.Serializable;
import java.util.Date;

/**
 * lkt_group_product 表中的group_data字段序列化对象
 *
 * @author Trick
 * @date 2020/10/13 11:09
 */
public class StdClass implements Serializable {

    private Date starttime;
    private String overtype;
    private Date endtime;

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public String getOvertype() {
        return overtype;
    }

    public void setOvertype(String overtype) {
        this.overtype = overtype;
    }
}
