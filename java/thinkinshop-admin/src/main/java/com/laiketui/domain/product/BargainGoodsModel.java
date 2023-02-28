package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Trick
 */
@Table(name = "lkt_bargain_goods")
public class BargainGoodsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer store_id;

    private Integer goods_id;

    /**
     * 产品属性id
     */
    private Integer attr_id;

    /**
     * 产品属性值1.猜你喜欢，2.热销，3.新品
     */
    private String is_type;

    /**
     * 最低价
     */
    private BigDecimal min_price;

    /**
     * 活动开始时间
     */
    private String begin_time;

    /**
     * 活动结束时间
     */
    private String end_time;

    /**
     * 购买时间
     */
    private Integer buytime;

    /**
     * 砍价人数 0为不限制
     */
    private Integer man_num;

    /**
     * 砍价方式数据
     */
    private String status_data;

    /**
     * 状态0.未开始1.进行中2.已结束
     */
    private Integer status;

    /**
     * 是否显示:0,不显示   1,显示
     */
    private Integer is_show;

    private String addtime;

    /**
     * 权重值，越大排序越靠前
     */
    private Integer sort;

    /**
     * 是否删除:0否   1是
     */
    private Integer is_delete;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return store_id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * @param store_id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * @return goods_id
     */
    public Integer getGoods_id() {
        return goods_id;
    }

    /**
     * @param goods_id
     */
    public void setGoods_id(Integer goods_id) {
        this.goods_id = goods_id;
    }

    /**
     * 获取产品属性id
     *
     * @return attr_id - 产品属性id
     */
    public Integer getAttr_id() {
        return attr_id;
    }

    /**
     * 设置产品属性id
     *
     * @param attr_id 产品属性id
     */
    public void setAttr_id(Integer attr_id) {
        this.attr_id = attr_id;
    }

    /**
     * 获取产品属性值1.猜你喜欢，2.热销，3.新品
     *
     * @return is_type - 产品属性值1.猜你喜欢，2.热销，3.新品
     */
    public String getIs_type() {
        return is_type;
    }

    /**
     * 设置产品属性值1.猜你喜欢，2.热销，3.新品
     *
     * @param is_type 产品属性值1.猜你喜欢，2.热销，3.新品
     */
    public void setIs_type(String is_type) {
        this.is_type = is_type == null ? null : is_type.trim();
    }

    /**
     * 获取最低价
     *
     * @return min_price - 最低价
     */
    public BigDecimal getMin_price() {
        return min_price;
    }

    /**
     * 设置最低价
     *
     * @param min_price 最低价
     */
    public void setMin_price(BigDecimal min_price) {
        this.min_price = min_price;
    }

    /**
     * 获取活动开始时间
     *
     * @return begin_time - 活动开始时间
     */
    public String getBegin_time() {
        return begin_time;
    }

    /**
     * 设置活动开始时间
     *
     * @param begin_time 活动开始时间
     */
    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time == null ? null : begin_time.trim();
    }

    /**
     * 获取活动结束时间
     *
     * @return end_time - 活动结束时间
     */
    public String getEnd_time() {
        return end_time;
    }

    /**
     * 设置活动结束时间
     *
     * @param end_time 活动结束时间
     */
    public void setEnd_time(String end_time) {
        this.end_time = end_time == null ? null : end_time.trim();
    }

    /**
     * 获取购买时间
     *
     * @return buytime - 购买时间
     */
    public Integer getBuytime() {
        return buytime;
    }

    /**
     * 设置购买时间
     *
     * @param buytime 购买时间
     */
    public void setBuytime(Integer buytime) {
        this.buytime = buytime;
    }

    /**
     * 获取砍价人数 0为不限制
     *
     * @return man_num - 砍价人数 0为不限制
     */
    public Integer getMan_num() {
        return man_num;
    }

    /**
     * 设置砍价人数 0为不限制
     *
     * @param man_num 砍价人数 0为不限制
     */
    public void setMan_num(Integer man_num) {
        this.man_num = man_num;
    }

    /**
     * 获取砍价方式数据
     *
     * @return status_data - 砍价方式数据
     */
    public String getStatus_data() {
        return status_data;
    }

    /**
     * 设置砍价方式数据
     *
     * @param status_data 砍价方式数据
     */
    public void setStatus_data(String status_data) {
        this.status_data = status_data == null ? null : status_data.trim();
    }

    /**
     * 获取状态0.未开始1.进行中2.已结束
     *
     * @return status - 状态0.未开始1.进行中2.已结束
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态0.未开始1.进行中2.已结束
     *
     * @param status 状态0.未开始1.进行中2.已结束
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取是否显示:0,不显示   1,显示
     *
     * @return is_show - 是否显示:0,不显示   1,显示
     */
    public Integer getIs_show() {
        return is_show;
    }

    /**
     * 设置是否显示:0,不显示   1,显示
     *
     * @param is_show 是否显示:0,不显示   1,显示
     */
    public void setIs_show(Integer is_show) {
        this.is_show = is_show;
    }

    /**
     * @return addtime
     */
    public String getAddtime() {
        return addtime;
    }

    /**
     * @param addtime
     */
    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    /**
     * 获取权重值，越大排序越靠前
     *
     * @return sort - 权重值，越大排序越靠前
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置权重值，越大排序越靠前
     *
     * @param sort 权重值，越大排序越靠前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }

    /**
     * 获取是否删除:0否   1是
     *
     * @return is_delete - 是否删除:0否   1是
     */
    public Integer getIs_delete() {
        return is_delete;
    }

    /**
     * 设置是否删除:0否   1是
     *
     * @param is_delete 是否删除:0否   1是
     */
    public void setIs_delete(Integer is_delete) {
        this.is_delete = is_delete;
    }
}