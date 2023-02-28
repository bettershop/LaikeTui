package com.laiketui.domain.message;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息弹窗模型类
 * @author wangxian
 */
@Table(name = "lkt_message_logging")
public class MessageLoggingModal implements Serializable {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 店铺id
     */
    private Integer mch_id;

    /**
     * 消息类型 1.订单(待发货) 2.订单(售后) 3.订单(提醒发货) 4.订单(订单关闭) 5.订单(新订单) 6.订单(收货) 7.商品(审核) 8.商品(下架) 9.商品(补货) 10.商品(新商品上架) 11.商品(分类) 12.商品(品牌)
     */
    private Integer type;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 内容
     */
    private String content;

    /**
     * 是否弹窗 0.未弹 1.已弹
     */
    private Integer is_popup;

    /**
     * 是否已读 0.未读 1.已读
     */
    private Integer read_or_not;

    /**
     * 添加时间
     */
    private Date add_date;

    /**
     * 获取ID
     *
     * @return id - ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置ID
     *
     * @param id ID
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
     * 获取消息类型 1.订单(待发货) 2.订单(售后) 3.订单(提醒发货) 4.订单(订单关闭) 5.订单(新订单) 6.订单(收货) 7.商品(审核) 8.商品(下架) 9.商品(补货) 10.商品(新商品上架) 11.商品(分类) 12.商品(品牌)
     *
     * @return type - 消息类型 1.订单(待发货) 2.订单(售后) 3.订单(提醒发货) 4.订单(订单关闭) 5.订单(新订单) 6.订单(收货) 7.商品(审核) 8.商品(下架) 9.商品(补货) 10.商品(新商品上架) 11.商品(分类) 12.商品(品牌)
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置消息类型 1.订单(待发货) 2.订单(售后) 3.订单(提醒发货) 4.订单(订单关闭) 5.订单(新订单) 6.订单(收货) 7.商品(审核) 8.商品(下架) 9.商品(补货) 10.商品(新商品上架) 11.商品(分类) 12.商品(品牌)
     *
     * @param type 消息类型 1.订单(待发货) 2.订单(售后) 3.订单(提醒发货) 4.订单(订单关闭) 5.订单(新订单) 6.订单(收货) 7.商品(审核) 8.商品(下架) 9.商品(补货) 10.商品(新商品上架) 11.商品(分类) 12.商品(品牌)
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 获取参数
     *
     * @return parameter - 参数
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * 设置参数
     *
     * @param parameter 参数
     */
    public void setParameter(String parameter) {
        this.parameter = parameter == null ? null : parameter.trim();
    }

    /**
     * 获取内容
     *
     * @return content - 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     *
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取是否弹窗 0.未弹 1.已弹
     *
     * @return is_popup - 是否弹窗 0.未弹 1.已弹
     */
    public Integer getIs_popup() {
        return is_popup;
    }

    /**
     * 设置是否弹窗 0.未弹 1.已弹
     *
     * @param is_popup 是否弹窗 0.未弹 1.已弹
     */
    public void setIs_popup(Integer is_popup) {
        this.is_popup = is_popup;
    }

    /**
     * 获取是否已读 0.未读 1.已读
     *
     * @return read_or_not - 是否已读 0.未读 1.已读
     */
    public Integer getRead_or_not() {
        return read_or_not;
    }

    /**
     * 设置是否已读 0.未读 1.已读
     *
     * @param read_or_not 是否已读 0.未读 1.已读
     */
    public void setRead_or_not(Integer read_or_not) {
        this.read_or_not = read_or_not;
    }

    /**
     * 获取添加时间
     *
     * @return add_date - 添加时间
     */
    public Date getAdd_date() {
        return add_date;
    }

    /**
     * 设置添加时间
     *
     * @param add_date 添加时间
     */
    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }
}