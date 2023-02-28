package com.laiketui.domain.order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Trick
 */
@Table(name = "lkt_return_record")
public class ReturnRecordModel {
    /**
     * id自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户userid
     */
    private String user_id;

    /**
     * 店铺id
     */
    private Integer store_id;

    /**
     * 售后类型：1仅退款 2退货退款 3换货
     */
    private Integer re_type;

    /**
     * 售后状态
     */
    private Integer r_type;

    /**
     * 拒绝原因
     */
    private String content;

    /**
     * 售后订单号
     */
    @Column(name="sNo")
    private String sNo;

    /**
     * 售后金额
     */
    private BigDecimal money;

    /**
     * 实际退款金额
     */
    private BigDecimal real_money;

    /**
     * 上传凭证
     */
    private String re_photo;

    /**
     * 商品id
     */
    private Integer product_id;

    /**
     * 属性id
     */
    private Integer attr_id;

    /**
     * 快递公司id
     */
    private Integer express_id;

    /**
     * 快递单号
     */
    private String courier_num;

    /**
     * 添加日期
     */
    private Date re_time;

    /**
     * 备注
     */
    @Column(name = "`explain`")
    private String explain;

    /**
     * 上级详情订单id
     */
    private Integer p_id;

    /**
     * 获取id自增
     *
     * @return id - id自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id自增
     *
     * @param id id自增
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户userid
     *
     * @return user_id - 用户userid
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户userid
     *
     * @param user_id 用户userid
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取店铺id
     *
     * @return store_id - 店铺id
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置店铺id
     *
     * @param store_id 店铺id
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取售后类型：1仅退款 2退货退款 3换货
     *
     * @return re_type - 售后类型：1仅退款 2退货退款 3换货
     */
    public Integer getRe_type() {
        return re_type;
    }

    /**
     * 设置售后类型：1仅退款 2退货退款 3换货
     *
     * @param re_type 售后类型：1仅退款 2退货退款 3换货
     */
    public void setRe_type(Integer re_type) {
        this.re_type = re_type;
    }

    /**
     * 获取售后状态
     *
     * @return r_type - 售后状态
     */
    public Integer getR_type() {
        return r_type;
    }

    /**
     * 设置售后状态
     *
     * @param r_type 售后状态
     */
    public void setR_type(Integer r_type) {
        this.r_type = r_type;
    }

    /**
     * 获取拒绝原因
     *
     * @return content - 拒绝原因
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置拒绝原因
     *
     * @param content 拒绝原因
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取售后订单号
     *
     * @return sNo - 售后订单号
     */
    public String getsNo() {
        return sNo;
    }

    /**
     * 设置售后订单号
     *
     * @param sNo 售后订单号
     */
    public void setsNo(String sNo) {
        this.sNo = sNo == null ? null : sNo.trim();
    }

    /**
     * 获取售后金额
     *
     * @return money - 售后金额
     */
    public BigDecimal getMoney() {
        return money;
    }

    /**
     * 设置售后金额
     *
     * @param money 售后金额
     */
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    /**
     * 获取实际退款金额
     *
     * @return real_money - 实际退款金额
     */
    public BigDecimal getReal_money() {
        return real_money;
    }

    /**
     * 设置实际退款金额
     *
     * @param real_money 实际退款金额
     */
    public void setReal_money(BigDecimal real_money) {
        this.real_money = real_money;
    }

    /**
     * 获取上传凭证
     *
     * @return re_photo - 上传凭证
     */
    public String getRe_photo() {
        return re_photo;
    }

    /**
     * 设置上传凭证
     *
     * @param re_photo 上传凭证
     */
    public void setRe_photo(String re_photo) {
        this.re_photo = re_photo == null ? null : re_photo.trim();
    }

    /**
     * 获取商品id
     *
     * @return product_id - 商品id
     */
    public Integer getProduct_id() {
        return product_id;
    }

    /**
     * 设置商品id
     *
     * @param product_id 商品id
     */
    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    /**
     * 获取属性id
     *
     * @return attr_id - 属性id
     */
    public Integer getAttr_id() {
        return attr_id;
    }

    /**
     * 设置属性id
     *
     * @param attr_id 属性id
     */
    public void setAttr_id(Integer attr_id) {
        this.attr_id = attr_id;
    }

    /**
     * 获取快递公司id
     *
     * @return express_id - 快递公司id
     */
    public Integer getExpress_id() {
        return express_id;
    }

    /**
     * 设置快递公司id
     *
     * @param express_id 快递公司id
     */
    public void setExpress_id(Integer express_id) {
        this.express_id = express_id;
    }

    /**
     * 获取快递单号
     *
     * @return courier_num - 快递单号
     */
    public String getCourier_num() {
        return courier_num;
    }

    /**
     * 设置快递单号
     *
     * @param courier_num 快递单号
     */
    public void setCourier_num(String courier_num) {
        this.courier_num = courier_num == null ? null : courier_num.trim();
    }

    /**
     * 获取添加日期
     *
     * @return re_time - 添加日期
     */
    public Date getRe_time() {
        return re_time;
    }

    /**
     * 设置添加日期
     *
     * @param re_time 添加日期
     */
    public void setRe_time(Date re_time) {
        this.re_time = re_time;
    }

    /**
     * 获取备注
     *
     * @return explain - 备注
     */
    public String getExplain() {
        return explain;
    }

    /**
     * 设置备注
     *
     * @param explain 备注
     */
    public void setExplain(String explain) {
        this.explain = explain == null ? null : explain.trim();
    }

    /**
     * 获取上级详情订单id
     *
     * @return p_id - 上级详情订单id
     */
    public Integer getP_id() {
        return p_id;
    }

    /**
     * 设置上级详情订单id
     *
     * @param p_id 上级详情订单id
     */
    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }
}