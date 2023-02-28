package com.laiketui.domain.mch;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lkt_return_goods")
public class ReturnGoodsModel {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 商城id
     */
    private Integer store_id;

    /**
     * 售后id
     */
    private Integer re_id;

    /**
     * 收货人
     */
    private String name;

    /**
     * 联系方式
     */
    private String tel;

    /**
     * 快递名称
     */
    private String express;

    /**
     * 快递单号
     */
    private String express_num;

    /**
     * 用户ID
     */
    private String uid;

    /**
     * 订单id
     */
    private String oid;

    /**
     * 填写时间
     */
    private Date add_data;

    /**
     * 用户id
     */
    private String user_id;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
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
     * 获取收货人
     *
     * @return name - 收货人
     */
    public String getName() {
        return name;
    }

    /**
     * 设置收货人
     *
     * @param name 收货人
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取联系方式
     *
     * @return tel - 联系方式
     */
    public String getTel() {
        return tel;
    }

    /**
     * 设置联系方式
     *
     * @param tel 联系方式
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * 获取快递名称
     *
     * @return express - 快递名称
     */
    public String getExpress() {
        return express;
    }

    /**
     * 设置快递名称
     *
     * @param express 快递名称
     */
    public void setExpress(String express) {
        this.express = express == null ? null : express.trim();
    }

    /**
     * 获取快递单号
     *
     * @return express_num - 快递单号
     */
    public String getExpress_num() {
        return express_num;
    }

    /**
     * 设置快递单号
     *
     * @param express_num 快递单号
     */
    public void setExpress_num(String express_num) {
        this.express_num = express_num == null ? null : express_num.trim();
    }

    /**
     * 获取用户ID
     *
     * @return uid - 用户ID
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置用户ID
     *
     * @param uid 用户ID
     */
    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    /**
     * 获取订单id
     *
     * @return oid - 订单id
     */
    public String getOid() {
        return oid;
    }

    /**
     * 设置订单id
     *
     * @param oid 订单明细id
     */
    public void setOid(String oid) {
        this.oid = oid == null ? null : oid.trim();
    }

    /**
     * 获取填写时间
     *
     * @return add_data - 填写时间
     */
    public Date getAdd_data() {
        return add_data;
    }

    /**
     * 设置填写时间
     *
     * @param add_data 填写时间
     */
    public void setAdd_data(Date add_data) {
        this.add_data = add_data;
    }

    /**
     * 获取用户id
     *
     * @return user_id - 用户id
     */
    public String getUser_id() {
        return user_id;
    }

    /**
     * 设置用户id
     *
     * @param user_id 用户id
     */
    public void setUser_id(String user_id) {
        this.user_id = user_id == null ? null : user_id.trim();
    }

    public Integer getRe_id() {
        return re_id;
    }

    public void setRe_id(Integer re_id) {
        this.re_id = re_id;
    }
}