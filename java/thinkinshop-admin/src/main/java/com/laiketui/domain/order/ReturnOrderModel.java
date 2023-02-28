package com.laiketui.domain.order;

import javax.persistence.*;
import java.util.Date;

@Table(name = "lkt_return_order")
public class ReturnOrderModel {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 退货退款
     */
    public static final int RE_TYPE_RETURN_REFUND = 1;
    /**
     * 退款
     */
    public static final int RE_TYPE_REFUND = 2;
    /**
     * 换货
     */
    public static final int RE_TYPE_EXCHANGE = 3;

    /**
     * 商城ID
     */
    private Integer store_id;

    /**
     * 用户ID
     */
    private String user_id;

    /**
     * 订单号
     */
    @Column(name="sNo")
    private String sNo;

    /**
     * 订单详情ID
     */
    private Integer p_id;

    /**
     * 退款类型 1:退货退款  2:退款 3:换货
     */
    private Integer re_type;

    /**
     * 售后原因
     */
    private String content;

    /**
     * 用户申请退款金额
     */
    private Float re_apply_money;

    /**
     * 退款金额
     */
    private Float re_money;

    /**
     * 实际退款金额
     */
    private Float real_money;

    /**
     * 申请售后时间
     */
    private Date re_time;

    /**
     * 上传凭证
     */
    private String re_photo;

    /**
     * 审核时间
     */
    private Date audit_time;

    /**
     * 拒绝原因
     */
    private String r_content;

    /**
     * 类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
     */
    private Integer r_type;

    /**
     * 商品属性id
     */
    private Integer sid;

    /**
     * 商品ID
     */
    private Integer pid;

    /**
     *
     */
    private Integer re_id = 0;

    public Integer getRe_id() {
        return re_id;
    }

    public void setRe_id(Integer re_id) {
        this.re_id = re_id;
    }

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取商城ID
     *
     * @return store_id - 商城ID
     */
    public Integer getStore_id() {
        return store_id;
    }

    /**
     * 设置商城ID
     *
     * @param store_id 商城ID
     */
    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户ID
     *
     * @param user_id 用户ID
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    /**
     * 获取订单号
     *
     * @return sNo - 订单号
     */
    public String getsNo() {
        return sNo;
    }

    /**
     * 设置订单号
     *
     * @param sNo 订单号
     */
    public void setsNo(String sNo) {
        this.sNo = sNo == null ? null : sNo.trim();
    }

    /**
     * 获取订单详情ID
     *
     * @return p_id - 订单详情ID
     */
    public Integer getP_id() {
        return p_id;
    }

    /**
     * 设置订单详情ID
     *
     * @param p_id 订单详情ID
     */
    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    /**
     * 获取退款类型 1:退货退款  2:退款 3:换货
     *
     * @return re_type - 退款类型 1:退货退款  2:退款 3:换货
     */
    public Integer getRe_type() {
        return re_type;
    }

    /**
     * 设置退款类型 1:退货退款  2:退款 3:换货
     *
     * @param re_type 退款类型 1:退货退款  2:退款 3:换货
     */
    public void setRe_type(Integer re_type) {
        this.re_type = re_type;
    }

    /**
     * 获取售后原因
     *
     * @return content - 售后原因
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置售后原因
     *
     * @param content 售后原因
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取用户申请退款金额
     *
     * @return re_apply_money - 用户申请退款金额
     */
    public Float getRe_apply_money() {
        return re_apply_money;
    }

    /**
     * 设置用户申请退款金额
     *
     * @param re_apply_money 用户申请退款金额
     */
    public void setRe_apply_money(Float re_apply_money) {
        this.re_apply_money = re_apply_money;
    }

    /**
     * 获取退款金额
     *
     * @return re_money - 退款金额
     */
    public Float getRe_money() {
        return re_money;
    }

    /**
     * 设置退款金额
     *
     * @param re_money 退款金额
     */
    public void setRe_money(Float re_money) {
        this.re_money = re_money;
    }

    /**
     * 获取实际退款金额
     *
     * @return real_money - 实际退款金额
     */
    public Float getReal_money() {
        return real_money;
    }

    /**
     * 设置实际退款金额
     *
     * @param real_money 实际退款金额
     */
    public void setReal_money(Float real_money) {
        this.real_money = real_money;
    }

    /**
     * 获取申请售后时间
     *
     * @return re_time - 申请售后时间
     */
    public Date getRe_time() {
        return re_time;
    }

    /**
     * 设置申请售后时间
     *
     * @param re_time 申请售后时间
     */
    public void setRe_time(Date re_time) {
        this.re_time = re_time;
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
     * 获取审核时间
     *
     * @return audit_time - 审核时间
     */
    public Date getAudit_time() {
        return audit_time;
    }

    /**
     * 设置审核时间
     *
     * @param audit_time 审核时间
     */
    public void setAudit_time(Date audit_time) {
        this.audit_time = audit_time;
    }

    /**
     * 获取拒绝原因
     *
     * @return r_content - 拒绝原因
     */
    public String getR_content() {
        return r_content;
    }

    /**
     * 设置拒绝原因
     *
     * @param r_content 拒绝原因
     */
    public void setR_content(String r_content) {
        this.r_content = r_content == null ? null : r_content.trim();
    }

    /**
     * 获取类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
     *
     * @return r_type - 类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
     */
    public Integer getR_type() {
        return r_type;
    }

    /**
     * 设置类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
     *
     * @param r_type 类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
     */
    public void setR_type(Integer r_type) {
        this.r_type = r_type;
    }

    /**
     * 获取商品属性id
     *
     * @return sid - 商品属性id
     */
    public Integer getSid() {
        return sid;
    }

    /**
     * 设置商品属性id
     *
     * @param sid 商品属性id
     */
    public void setSid(Integer sid) {
        this.sid = sid;
    }

    /**
     * 获取商品ID
     *
     * @return pid - 商品ID
     */
    public Integer getPid() {
        return pid;
    }

    /**
     * 设置商品ID
     *
     * @param pid 商品ID
     */
    public void setPid(Integer pid) {
        this.pid = pid;
    }
}