package com.laiketui.api.common;

import com.laiketui.domain.order.OrderDataModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.OrderVo;
import com.laiketui.domain.vo.mch.FrontDeliveryVo;
import com.laiketui.domain.vo.mch.MchOrderDetailVo;
import com.laiketui.domain.vo.mch.MchOrderIndexVo;
import com.laiketui.domain.vo.order.*;
import com.laiketui.domain.vo.pay.PaymentVo;
import com.laiketui.domain.vo.pc.MchPcOrderIndexVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 订单通用服务
 *
 * @author wangxian
 */
public interface PublicOrderService {

    /**
     * 计算运费
     *
     * @param freightMap
     * @param products
     * @param userAddress
     * @param storeId
     * @param productType
     * @return
     */
    Map<String, Object> getFreight(Map<String, List<Map<String, Object>>> freightMap, List<Map<String, Object>> products, UserAddress userAddress, int storeId, String productType);

    /**
     * 计算运费
     * 【根据商品设置的运费模板计算运费】
     *
     * @param goodsAddressId - 商品运费模板id
     * @param userAddress    - 当前用户选择的运费模型数据 - 可选
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/26 18:15
     */
    BigDecimal getFreight(Integer goodsAddressId, UserAddress userAddress) throws LaiKeApiException;

    /**
     * 创建订单号码
     *
     * @param orderType
     * @return
     */
    String createOrderNo(String orderType);

    /**
     * 订单列表（pc后台）
     * 【php DeliveryHelper.order_index】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021-07-19 15:03:25
     */
    Map<String, Object> pcMchOrderIndex(AdminOrderListVo vo) throws LaiKeApiException;

    /**
     * 订单列表（移动店铺端）
     * 【php DeliveryHelper.a_mch_order_index】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 17:06
     */
    Map<String, Object> aMchOrderIndex(MchOrderIndexVo vo) throws LaiKeApiException;


    /**
     * pc店铺端-订单列表
     * 【php DeliveryHelper.b_mch_order_index】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/3 9:51
     */
    Map<String, Object> bMchOrderIndex(MchPcOrderIndexVo vo) throws LaiKeApiException;

    /**
     * 支付后修改订单信息
     *
     * @param storeId -
     * @param sNo     -
     * @param tradeNo - 第三方支付订单号
     * @param userId  -
     * @param payType - 支付方式
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/6 14:10
     */
    BigDecimal orderPayment(int storeId, String sNo, String tradeNo, String userId, String payType) throws LaiKeApiException;

    /**
     * 发货
     * 【php DeliveryHelper.frontDelivery】
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/26 16:43
     */
    void frontDelivery(FrontDeliveryVo vo) throws LaiKeApiException;


    /**
     * 订单详情/订单列表-确认收货
     *
     * @param storeId  -
     * @param accessId -
     * @param orderno  -
     * @param rType    -退货状态
     * @return Map
     * @throws LaiKeApiException -
     * @author wang xian
     * @date 2021/4/6 10:23
     */
    Map<String, Object> okOrder(int storeId, String accessId, String orderno, Integer rType) throws LaiKeApiException;

    /**
     * 发货
     * 【php DeliveryHelper.frontDelivery】
     *
     * @param vo             -
     * @param exId           -
     * @param exNo           -
     * @param orderDetailIds -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/26 16:43
     */
    void adminDelivery(MainVo vo, Integer exId, String exNo, String orderDetailIds) throws LaiKeApiException;


    /**
     * 获取订单详情
     * 【php DeliveryHelper.mch_order_details】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/27 11:21
     */
    Map<String, Object> mchOrderDetails(MchOrderDetailVo vo) throws LaiKeApiException;


    /**
     * 订单详情(后台、PC店铺)
     * 【php DeliveryHelper.order_details】
     *
     * @param storeId  -
     * @param ordernno -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/4 9:24
     */
    Map<String, Object> storeOrderDetails(int storeId, String ordernno) throws LaiKeApiException;


    /**
     * 统一修改订单状态
     * 【如果明细都是一个状态，则修正主表状态】
     *
     * @param storeId -
     * @param orderno -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/27 11:21
     */
    void updateOrderState(int storeId, String orderno) throws LaiKeApiException;


    /**
     * 订单列表(用户个人中心)
     *
     * @param vo
     * @param user
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> orderList(OrderVo vo, User user) throws LaiKeApiException;

    /**
     * 订单列表 pc管理后台订单列表
     *
     * @param vo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> orderPcList(AdminOrderVo vo) throws LaiKeApiException;


    /**
     * 根据订单号获取物流信息
     *
     * @param orderNo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/15 9:39
     */
    Map<String, Object> getLogistics(String orderNo) throws LaiKeApiException;

    /**
     * 移动端用户个人中心订单详情
     *
     * @param vo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> ucOrderDetails(OrderVo vo) throws LaiKeApiException;

    /**
     * 获取可以退款的金额
     *
     * @param orderDetailId
     * @param storeId
     * @return
     * @throws LaiKeApiException
     */
    BigDecimal getOrderPrice(int orderDetailId, int storeId) throws LaiKeApiException;

    /**
     * 支付完成订单更新
     *
     * @param orderModel
     * @return
     * @throws LaiKeApiException
     */
    Map payBackUpOrder(OrderModel orderModel) throws LaiKeApiException;

    /**
     * 支付完成订单更新(充值、续费、升级)
     *
     * @param orderDataModel
     * @return
     * @throws LaiKeApiException
     */
    Map payBackUpOrderMember(OrderDataModel orderDataModel) throws LaiKeApiException;

    /**
     * 店铺保证金
     *
     * @param orderDataModel -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/11/11 15:57
     */
    Map<String, Object> payBackUpOrderMchPromise(OrderDataModel orderDataModel) throws LaiKeApiException;


    /**
     * 获取订单信息
     *
     * @param orderNo   -
     * @param paymentVo - 此参数非特殊处理无需传
     * @param userId    - 此参数非特殊处理无需传
     * @return OrderModel
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/11/12 14:26
     */
    OrderModel getOrderInfo(String orderNo, PaymentVo paymentVo, String userId) throws LaiKeApiException;
    OrderModel getOrderInfo(String orderNo) throws LaiKeApiException;

    /**
     * 后台订单编辑接口
     *
     * @param orderModifyVo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> modifyOrder(OrderModifyVo orderModifyVo) throws LaiKeApiException;

    /**
     * 后台订单详情接口
     *
     * @param adminOrderVo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> orderPcDetails(AdminOrderDetailVo adminOrderVo) throws LaiKeApiException;


    /**
     * 获取支付配置信息
     * 【php Tool.getPayment】
     *
     * @param storeId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/29 11:34
     */
    Map<String, Object> getPaymentConfig(int storeId) throws LaiKeApiException;


    /**
     * 根据订单id/订单号获取订单信息
     *
     * @param storeId -
     * @param id      -
     * @param orderno -
     * @return OrderModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/12 15:57
     */
    OrderModel getOrderInfoById(int storeId, Integer id, String orderno) throws LaiKeApiException;


    /**
     * 根据订单详情id获取订单信息
     *
     * @param storeId -
     * @param did     - 订单详情id
     * @return OrderModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/12 16:11
     */
    OrderModel getOrderInfoByDetailId(int storeId, int did) throws LaiKeApiException;

    /**
     * 获取订单结算列表
     *
     * @param vo-
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/27 19:08
     */
    Map<String, Object> getSettlementOrderList(OrderSettlementVo vo) throws LaiKeApiException;

    /**
     * 根据订单类型获取订单配置
     *
     * @param storeId -
     * @param oType   -  订单类型
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/11/10 11:35
     */
    Map<String, Object> getOrderConfig(int storeId, String oType) throws LaiKeApiException;
}
