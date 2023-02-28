package com.laiketui.domain.product;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 竞拍商品表
 *
 * @author Trick
 * @date 2021/2/23 9:50
 */
@Table(name = "lkt_auction_product")
public class AuctionProductModel {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    public interface Status {
        /**
         * 未开始
         */
        int NOT_START = 0;
        /**
         * 进行中
         */
        int ONGOING = 1;
        /**
         * 结束
         */
        int END = 2;
        /**
         * 流拍
         */
        int LOSE = 3;
    }

    /**
     * 品牌id
     */
    private Integer brand_id;

    /**
     * 竞拍标题
     */
    private String title;

    /**
     * 内容描述
     */
    private String content;

    /**
     * 开始时间
     */
    private Date starttime;

    /**
     * 结束时间
     */
    private Date endtime;

    /**
     * 竞拍状态：0-未开始 1-进行中 2-已结束 3-已流拍
     */
    private Integer status;

    /**
     * 竞拍起价
     */
    private BigDecimal price;

    /**
     * 竞拍加价
     */
    private BigDecimal add_price;

    /**
     * 当前价格
     */
    private BigDecimal current_price;

    /**
     * 图片路径
     */
    private String imgurl;

    /**
     * 产品及规格
     */
    private String attribute;

    /**
     * 市场价
     */
    private BigDecimal market_price;

    /**
     * 保留显示的天数
     */
    private Integer days;

    /**
     * 结束显示的时间
     */
    private Date invalid_time;

    /**
     * 保证金
     */
    private BigDecimal promise;

    /**
     * 参与人数
     */
    private Integer pepole;

    /**
     * 最终成交人
     */
    private String user_id;

    /**
     * 是否付款，0-未付款，1-付款
     */
    private Integer is_buy;

    /**
     * 订单编号
     */
    private String trade_no;

    /**
     * 最低开拍人数
     */
    private Integer low_pepole;

    /**
     * 出价等待时间（单位是秒）
     */
    private Integer wait_time;

    /**
     * 店铺id
     */
    private Integer mch_id;

    /**
     * 是否回收  0：不回收  1：回收
     */
    private Integer recycle;

    /**
     * 竞拍商品标签类型，根据数据字典
     */
    private String s_type;

    /**
     * 是否显示 0 不显示  ， 1 显示
     */
    private Integer is_show;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城id
     *
     * @return store_id - 商城id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城id
     *
     * @param store_id 商城id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取品牌id
     *
     * @return brand_id - 品牌id
     */
    public Integer getBrand_id() {
        return brand_id;
    }

    /**
     * 设置品牌id
     *
     * @param brand_id 品牌id
     */
    public void setBrand_id(Integer brand_id) {
        this.brand_id = brand_id;
    }

    /**
     * 获取竞拍标题
     *
     * @return title - 竞拍标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置竞拍标题
     *
     * @param title 竞拍标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取内容描述
     *
     * @return content - 内容描述
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容描述
     *
     * @param content 内容描述
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取开始时间
     *
     * @return starttime - 开始时间
     */
    public Date getStarttime() {
        return starttime;
    }

    /**
     * 设置开始时间
     *
     * @param starttime 开始时间
     */
    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    /**
     * 获取结束时间
     *
     * @return endtime - 结束时间
     */
    public Date getEndtime() {
        return endtime;
    }

    /**
     * 设置结束时间
     *
     * @param endtime 结束时间
     */
    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    /**
     * 获取竞拍状态：0-未开始 1-进行中 2-已结束 3-已流拍
     *
     * @return status - 竞拍状态：0-未开始 1-进行中 2-已结束 3-已流拍
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置竞拍状态：0-未开始 1-进行中 2-已结束 3-已流拍
     *
     * @param status 竞拍状态：0-未开始 1-进行中 2-已结束 3-已流拍
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取竞拍起价
     *
     * @return price - 竞拍起价
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置竞拍起价
     *
     * @param price 竞拍起价
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取竞拍加价
     *
     * @return add_price - 竞拍加价
     */
    public BigDecimal getAdd_price() {
        return add_price;
    }

    /**
     * 设置竞拍加价
     *
     * @param add_price 竞拍加价
     */
    public void setAdd_price(BigDecimal add_price) {
        this.add_price = add_price;
    }

    /**
     * 获取当前价格
     *
     * @return current_price - 当前价格
     */
    public BigDecimal getCurrent_price() {
        return current_price;
    }

    /**
     * 设置当前价格
     *
     * @param current_price 当前价格
     */
    public void setCurrent_price(BigDecimal current_price) {
        this.current_price = current_price;
    }

    /**
     * 获取图片路径
     *
     * @return imgurl - 图片路径
     */
    public String getImgurl() {
        return imgurl;
    }

    /**
     * 设置图片路径
     *
     * @param imgurl 图片路径
     */
    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    /**
     * 获取产品及规格
     *
     * @return attribute - 产品及规格
     */
    public String getAttribute() {
        return attribute;
    }

    /**
     * 设置产品及规格
     *
     * @param attribute 产品及规格
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute == null ? null : attribute.trim();
    }

    /**
     * 获取市场价
     *
     * @return market_price - 市场价
     */
    public BigDecimal getMarket_price() {
        return market_price;
    }

    /**
     * 设置市场价
     *
     * @param market_price 市场价
     */
    public void setMarket_price(BigDecimal market_price) {
        this.market_price = market_price;
    }

    /**
     * 获取保留显示的天数
     *
     * @return days - 保留显示的天数
     */
    public Integer getDays() {
        return days;
    }

    /**
     * 设置保留显示的天数
     *
     * @param days 保留显示的天数
     */
    public void setDays(Integer days) {
        this.days = days;
    }

    /**
     * 获取结束显示的时间
     *
     * @return invalid_time - 结束显示的时间
     */
    public Date getInvalid_time() {
        return invalid_time;
    }

    /**
     * 设置结束显示的时间
     *
     * @param invalid_time 结束显示的时间
     */
    public void setInvalid_time(Date invalid_time) {
        this.invalid_time = invalid_time;
    }

    /**
     * 获取保证金
     *
     * @return promise - 保证金
     */
    public BigDecimal getPromise() {
        return promise;
    }

    /**
     * 设置保证金
     *
     * @param promise 保证金
     */
    public void setPromise(BigDecimal promise) {
        this.promise = promise;
    }

    /**
     * 获取参与人数
     *
     * @return pepole - 参与人数
     */
    public Integer getPepole() {
        return pepole;
    }

    /**
     * 设置参与人数
     *
     * @param pepole 参与人数
     */
    public void setPepole(Integer pepole) {
        this.pepole = pepole;
    }

    /**
     * 获取最终成交人
     *
     * @return user_id - 最终成交人
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置最终成交人
     *
     * @param user_id 最终成交人
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取是否付款，0-未付款，1-付款
     *
     * @return is_buy - 是否付款，0-未付款，1-付款
     */
    public Integer getIs_buy() {
        return is_buy;
    }

    /**
     * 设置是否付款，0-未付款，1-付款
     *
     * @param is_buy 是否付款，0-未付款，1-付款
     */
    public void setIs_buy(Integer is_buy) {
        this.is_buy = is_buy;
    }

    /**
     * 获取订单编号
     *
     * @return trade_no - 订单编号
     */
    public String getTrade_no() {
        return trade_no;
    }

    /**
     * 设置订单编号
     *
     * @param trade_no 订单编号
     */
    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no == null ? null : trade_no.trim();
    }

    /**
     * 获取最低开拍人数
     *
     * @return low_pepole - 最低开拍人数
     */
    public Integer getLow_pepole() {
        return low_pepole;
    }

    /**
     * 设置最低开拍人数
     *
     * @param low_pepole 最低开拍人数
     */
    public void setLow_pepole(Integer low_pepole) {
        this.low_pepole = low_pepole;
    }

    /**
     * 获取出价等待时间（单位是秒）
     *
     * @return wait_time - 出价等待时间（单位是秒）
     */
    public Integer getWait_time() {
        return wait_time;
    }

    /**
     * 设置出价等待时间（单位是秒）
     *
     * @param wait_time 出价等待时间（单位是秒）
     */
    public void setWait_time(Integer wait_time) {
        this.wait_time = wait_time;
    }

    /**
     * 获取店铺id
     *
     * @return mch_id - 店铺id
     */
    public Integer getMch_id() {
        return mch_id;
    }

    /**
     * 设置店铺id
     *
     * @param mch_id 店铺id
     */
    public void setMch_id(Integer mch_id) {
        this.mch_id = mch_id;
    }

    /**
     * 获取是否回收  0：不回收  1：回收
     *
     * @return recycle - 是否回收  0：不回收  1：回收
     */
    public Integer getRecycle() {
        return recycle;
    }

    /**
     * 设置是否回收  0：不回收  1：回收
     *
     * @param recycle 是否回收  0：不回收  1：回收
     */
    public void setRecycle(Integer recycle) {
        this.recycle = recycle;
    }

    /**
     * 获取竞拍商品标签类型，根据数据字典
     *
     * @return s_type - 竞拍商品标签类型，根据数据字典
     */
    public String getS_type() {
        return s_type;
    }

    /**
     * 设置竞拍商品标签类型，根据数据字典
     *
     * @param s_type 竞拍商品标签类型，根据数据字典
     */
    public void setS_type(String s_type) {
        this.s_type = s_type == null ? null : s_type.trim();
    }

    /**
     * 获取是否显示 0 不显示  ， 1 显示
     *
     * @return is_show - 是否显示 0 不显示  ， 1 显示
     */
    public Integer getIs_show() {
        return is_show;
    }

    /**
     * 设置是否显示 0 不显示  ， 1 显示
     *
     * @param is_show 是否显示 0 不显示  ， 1 显示
     */
    public void setIs_show(Integer is_show) {
        this.is_show = is_show;
    }
}