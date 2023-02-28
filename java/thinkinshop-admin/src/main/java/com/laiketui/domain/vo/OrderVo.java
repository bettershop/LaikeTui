package com.laiketui.domain.vo;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.validation.annotation.Validated;

/**
 * 订单处理参数处理类
 *
 * @author wangxian
 */
@Validated
public class OrderVo extends MainVo {

    /**
     * 优惠券
     */
    public static final String COUPON = "coupon";

    /**
     * 满减
     */
    public static final String SUBTRACTION = "subtraction";

    /**
     * 未使用优惠
     */
    public static final String NO_DISCOUNT = "nodiscount";


    /**
     * 插件主键id
     */
    @ParamsMapping({"jifen_id", "bargain_id", "sec_id"})
    private Integer mainId;
    /**
     * 插件主键uuid
     */
    private String pluginId;

    public String getPluginId() {
        return pluginId;
    }

    public void setPluginId(String pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * 订单类型
     */
    @ParamsMapping({"product_type", "order_type"})
    private String type = "GM";

    public Integer getMainId() {
        return mainId;
    }

    public void setMainId(Integer mainId) {
        this.mainId = mainId;
    }

    /**
     * 订单号
     */
    @ParamsMapping("id")
    private String sNo;

    /**
     * 商品详情信息 [{"pid":"979"},{"cid":"5648"},{"num":1}]
     * 【拼团参数结构->ProductInfoParamVo】
     */
    @ParamsMapping("product")
    private String productsInfo;

    /**
     * 购物车记录id 逗号分割 123,234
     */
    @ParamsMapping("cart_id")
    private String carts;

    /**
     * 默认为0 再次购买=1
     */
    @ParamsMapping("buy_type")
    private Integer buyType = 0;

    /**
     * 商品类型
     */
    private String productType = "GM";

    /**
     * 是否为分销商品 默认0 不是分销商品；1：分销商品
     */
    @ParamsMapping("is_distribution")
    private int isDistribution = 0;

    /**
     * 收货地址id
     */
    @ParamsMapping("address_id")
    private Integer addressId = -1;

    /**
     * 门店地址id shop_address_id
     */
    @ParamsMapping("shop_address_id")
    private Integer shopAddressId = 0;

    /**
     * 惠惠券id等信息 0，0，flag,优惠类型 不能同时使用优惠券和满减
     * 0,0,0,no_discount : 表示 有两个点的没有使用优惠券，并且平台优惠券和满减也没有使用
     * 0,685,coupon 表示没有使用店铺优惠券，使用了平台优惠券
     */
    @ParamsMapping("coupon_id")
    private String couponId;


    /**
     * 规格id
     */
    @ParamsMapping("attrId")
    private Integer attrId;

    public Integer getAttrId() {
        return attrId;
    }

    public void setAttrId(Integer attrId) {
        this.attrId = attrId;
    }

    /**
     * 是否选择了优惠券
     */
    private boolean canshu = false;

    /**
     * 会员特惠 兑换券级别
     */
    private Integer gradeLevel = 0;

    /**
     * 支付时使用的优惠券id
     */
    private String useCouponId;
    /**
     * 用户使用积分
     */
    private Integer allow = 0;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * 支付类型()
     */
    @ParamsMapping("pay_type")
    private String payType;

    /**
     * { sNo: me.sNo, total: me.z_price_, order_id: me.orde_id }
     */
    @ParamsMapping("order_list")
    private String orderList;

    /**
     * 订单列表搜索
     */
    @ApiModelProperty(value = "关键字", name = "keyword")
    @ParamsMapping("keyword")
    private String keyword;

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * 商品名称/订单号
     */
    private String ordervalue;

    /**
     * 订单查询类型 前端orderType需要映射到此字段
     */
    @ApiModelProperty(value = "订单查询类型 payment(待付款) send(代发货) receipt(待收货) evaluete(待评价) return(售后)", name = "queryOrderType")
    @ParamsMapping("queryOrderType")
    private String queryOrderType;

    public String getQueryOrderType() {
        return queryOrderType;
    }

    public void setQueryOrderType(String queryOrderType) {
        this.queryOrderType = queryOrderType;
    }

    /**
     * 此字段和type一致
     */
    private String orderType;

    /**
     * 订单失效天数 默认2天
     */
    private int orderFailure = 2;

    /**
     * 单位
     */
    private String company = "day";

    /**
     * 订单id
     */
    @ParamsMapping("order_id")
    private int orderId;

    /**
     * 购物车id
     */
    @ParamsMapping("cart_id")
    private String cartId;

    /**
     * 链接
     */
    private String url;

    /**
     * 支付方式
     */
    private String payClassName;

    /**
     * 售后类型
     */
    private Integer rtype;

    /**
     * 订单详情id 1,12,3
     */
    @ParamsMapping("order_details_id")
    private String orderDetailsId;

    /**
     * 查看物流接口
     */
    @ParamsMapping("o_source")
    private String oSource;

    public String getoSource() {
        return oSource;
    }

    public void setoSource(String oSource) {
        this.oSource = oSource;
    }

    public String getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(String orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public Integer getRtype() {
        return rtype;
    }

    public void setRtype(Integer rtype) {
        this.rtype = rtype;
    }

    public String getPayClassName() {
        return payClassName;
    }

    public void setPayClassName(String payClassName) {
        this.payClassName = payClassName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getOrderFailure() {
        return orderFailure;
    }

    public void setOrderFailure(int orderFailure) {
        this.orderFailure = orderFailure;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = getType();
    }

    public String getOrdervalue() {
        return ordervalue;
    }

    public void setOrdervalue(String ordervalue) {
        this.ordervalue = ordervalue;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getAllow() {
        return allow;
    }

    public void setAllow(Integer allow) {
        this.allow = allow;
    }

    public String getUseCouponId() {
        return useCouponId;
    }

    public void setUseCouponId(String useCouponId) {
        this.useCouponId = useCouponId;
    }

    public boolean getCanshu() {
        return canshu;
    }

    public Integer getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(Integer gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public Integer getShopAddressId() {
        return shopAddressId;
    }

    public void setShopAddressId(Integer shopAddressId) {
        this.shopAddressId = shopAddressId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public int getIsDistribution() {
        return isDistribution;
    }

    public void setIsDistribution(int isDistribution) {
        this.isDistribution = isDistribution;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getBuyType() {
        return buyType;
    }

    public void setBuyType(Integer buyType) {
        this.buyType = buyType;
    }

    public String getCarts() {
        return carts;
    }

    public void setCarts(String carts) {
        this.carts = carts;
    }

    public String getProductsInfo() {
        return productsInfo;
    }

    public void setProductsInfo(String productsInfo) {
        this.productsInfo = productsInfo;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public void setCanshu(boolean canshu) {
        this.canshu = canshu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
        this.productType = type;
        this.orderType = type;
    }
}
