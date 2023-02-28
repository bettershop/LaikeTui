package com.laiketui.root.utils.tool;

import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订单工具
 *
 * @author Trick
 * @date 2021/7/19 16:40
 */
public class OrderDataUtils {

    private static final Logger logger = LoggerFactory.getLogger(OrderDataUtils.class);

    /**
     * 订单状态
     */
    public enum OrderStatus {
        /**
         * 未支付
         */
        WAIT_PAY(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID),
        /**
         * 代发货
         */
        WAIT(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT),
        /**
         * 待收货
         */
        RECEIVED(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED),
        /**
         * 订单完成
         */
        SUCCESS(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_COMPLETE),
        /**
         * 关闭
         */
        CLOSE(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE),
        ;

        int value;

        OrderStatus(int val) {
            value = val;
        }

    }

    /**
     * 订单类型
     */
    public enum OrderType {
        /**
         * 普通订单
         */
        GM(DictionaryConst.OrdersType.ORDERS_HEADER_GM),
        /**
         * 竞拍
         */
        JP(DictionaryConst.OrdersType.ORDERS_HEADER_JP),
        /**
         * 充值
         */
        CZ(DictionaryConst.OrdersType.ORDERS_HEADER_CZ),
        /**
         * vip订单
         */
        DJ(DictionaryConst.OrdersType.ORDERS_HEADER_DJ),
        /**
         * 分销
         */
        FX(DictionaryConst.OrdersType.ORDERS_HEADER_FX),
        /**
         * 积分
         */
        IN(DictionaryConst.OrdersType.ORDERS_HEADER_IN),
        /**
         * 砍价
         */
        KJ(DictionaryConst.OrdersType.ORDERS_HEADER_KJ),
        /**
         * 开团
         */
        KT(DictionaryConst.OrdersType.ORDERS_HEADER_KT),
        /**
         * 秒杀
         */
        MS(DictionaryConst.OrdersType.ORDERS_HEADER_MS),
        /**
         * 拼团
         */
        PT(DictionaryConst.OrdersType.ORDERS_HEADER_PT),
        /**
         * 特惠
         */
        TH(DictionaryConst.OrdersType.ORDERS_HEADER_TH),
        /**
         * 平台秒杀
         */
        PM(DictionaryConst.OrdersType.PTHD_ORDER_PM),
        /**
         * 平台拼团
         */
        PP(DictionaryConst.OrdersType.PTHD_ORDER_PP),
        ;

        String value;

        OrderType(String val) {
            value = val;
        }

    }

    public static String getOrderStatus(OrderStatus orderStatus) throws LaiKeApiException {
        return getOrderStatus(orderStatus.value);
    }

    /**
     * 获取订单状态字符
     *
     * @param orderStatus -
     * @return String
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/19 16:55
     */
    public static String getOrderStatus(int orderStatus) throws LaiKeApiException {
        String orderName = "";
        try {
            //"待付款", "待发货", "待收货", "订单完成", "订单关闭"
            switch (orderStatus) {
                case DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID:
                    orderName = "待付款";
                    break;
                case DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT:
                    orderName = "待发货";
                    break;
                case DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED:
                    orderName = "待收货";
                    break;
                case DictionaryConst.OrdersStatus.ORDERS_R_STATUS_COMPLETE:
                    orderName = "订单完成";
                    break;
                case DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE:
                    orderName = "订单关闭";
                    break;
                default:
                    orderName = "未知状态";
                    break;
            }
        } catch (Exception e) {
            logger.error("订单状态获取失败 ", e);
            orderName = "";
        }
        return orderName;
    }

    public static String getOrderType(OrderType orderType) throws LaiKeApiException {
        return getOrderType(orderType.value);
    }

    /**
     * 根据订单头部获取字符
     *
     * @param orderType -
     * @return String
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/19 16:55
     */
    public static String getOrderType(String orderType) throws LaiKeApiException {
        String orderName = "";
        try {
            switch (orderType) {
                case DictionaryConst.OrdersType.ORDERS_HEADER_GM:
                    orderName = "普通";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_KJ:
                    orderName = "砍价";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_PT:
                    orderName = "拼团";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_KT:
                    orderName = "开团";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_FX:
                    orderName = "分销";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_JP:
                    orderName = "JP";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_TH:
                    orderName = "特惠";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_MS:
                    orderName = "秒杀";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_CZ:
                    orderName = "充值";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_IN:
                    orderName = "积分";
                    break;
                case DictionaryConst.OrdersType.ORDERS_HEADER_DJ:
                    orderName = "vip";
                    break;
                case DictionaryConst.OrdersType.PTHD_ORDER_PP:
                    orderName = "平台拼团";
                    break;
                case DictionaryConst.OrdersType.PTHD_ORDER_PM:
                    orderName = "平台秒杀";
                    break;
                default:
                    orderName = "未知类型";
                    break;
            }
        } catch (Exception e) {
            logger.error("订单状态获取失败 ", e);
            orderName = "";
        }
        return orderName;
    }

}
