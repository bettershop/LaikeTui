package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.*;
import com.laiketui.api.common.pay.PublicPaymentService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.domain.coupon.CouponModal;
import com.laiketui.domain.home.SystemMessageModel;
import com.laiketui.domain.log.MchAccountLogModel;
import com.laiketui.domain.mch.CustomerModel;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.mch.ReturnGoodsModel;
import com.laiketui.domain.mch.ServiceAddressModel;
import com.laiketui.domain.order.OrderDetailsModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.order.ReturnOrderModel;
import com.laiketui.domain.order.ReturnRecordModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.product.StockModel;
import com.laiketui.domain.vo.main.RefundVo;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.SerializePhpUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 公共售后实现
 *
 * @author Trick
 * @date 2020/12/2 12:05
 */
@Service
public class PublicRefundServiceImpl implements PublicRefundService {

    private final Logger logger = LoggerFactory.getLogger(PublicRefundServiceImpl.class);

    @Autowired
    private PublicOrderService publicOrderService;

    @Override
    public boolean refund(RefundVo vo) throws LaiKeApiException {
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            //店铺联系方式
            String mchPhone = "";
            //是否短信配置
            boolean isSendNotice = false;

            //判断是否设置售后地址
            ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
            serviceAddressModel.setStore_id(vo.getStoreId());
            serviceAddressModel.setUid("admin");
            serviceAddressModel.setIs_default(1);
            serviceAddressModel.setType(DictionaryConst.ServiceAddressType.ServiceAddressType_AfterSale);
            int count = serviceAddressModelMapper.selectCount(serviceAddressModel);
            if (count < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请设置售后地址", "refund");
            }
            //获取电商平台管理员信息
            CustomerModel customerModel = new CustomerModel();
            customerModel.setId(vo.getStoreId());
            customerModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            customerModel = customerModelMapper.selectOne(customerModel);
            if (customerModel != null) {
                mchPhone = customerModel.getMobile();
            }
            //进入订单详情把未读状态改成已读状态，已读状态的状态不变
            parmaMap.put("id", vo.getId());
            List<Map<String, Object>> orderInfoList = returnOrderModelMapper.getReturnOrderJoinOrderListDynamic(parmaMap);
            if (orderInfoList != null && orderInfoList.size() > 0) {
                Map<String, Object> returnOrderInfoMap = orderInfoList.get(0);
                //订单读取状态
                int read = StringUtils.stringParseInt(returnOrderInfoMap.get("readd") + "");
                //订单id
                int orderId = StringUtils.stringParseInt(returnOrderInfoMap.get("oId").toString());
                //订单id
                String orderno = returnOrderInfoMap.get("sNo").toString();
                //订单详情id
                int detailId = StringUtils.stringParseInt(returnOrderInfoMap.get("detialId").toString());
                //订单金额
                BigDecimal orderPrice = new BigDecimal(returnOrderInfoMap.get("z_price").toString());
                //单个商品的实际支付金额
                BigDecimal goodsPayPrice = getOrderPrice(vo.getStoreId(), detailId);
                //买家userid
                String clientUserId = returnOrderInfoMap.get("user_id").toString();
                //买家名称
                String clientUserName = returnOrderInfoMap.get("name").toString();
                //买家手机号
                String clientUserPhon = returnOrderInfoMap.get("mobile").toString();
                //店铺id
                int mchId = StringUtils.stringParseInt(StringUtils.trim(returnOrderInfoMap.get("mch_id").toString(), ","));
                //商品id
                int goodsId = StringUtils.stringParseInt(returnOrderInfoMap.get("goodsId").toString());
                //属性id
                int attributeId = StringUtils.stringParseInt(returnOrderInfoMap.get("attributeId").toString());
                //支付方式
                String payType = returnOrderInfoMap.get("pay").toString();
                //订单商品使用的优惠卷id
                String couponId = returnOrderInfoMap.get("detailCouponId") + "";
                //满减活动id
                int subtractionId = StringUtils.stringParseInt(returnOrderInfoMap.get("subtraction_id") + "");
                //订单商品运费
                BigDecimal freight = new BigDecimal(returnOrderInfoMap.get("freight").toString());
                //抵扣余额
                BigDecimal offsetBalance = new BigDecimal(returnOrderInfoMap.get("offset_balance") + "");
                //支付流水号
                String realOrderno = MapUtils.getString(returnOrderInfoMap, "real_sno", "");
                //父订单id
                String psNo = returnOrderInfoMap.get("p_sNo").toString();
                //到货时间
                String arriveTime = returnOrderInfoMap.get("arrive_time") + "";
                //是否已到货标识
                boolean isArruve = !StringUtils.isEmpty(arriveTime) && !"0000-00-00 00:00:00".equals(arriveTime);
                int settlementType = MapUtils.getIntValue(returnOrderInfoMap, "settlement_type");
                //订单是否已经结算标识
                boolean isSettlement = settlementType != 0;
                if (isSettlement) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_NOT_REFUND_ERROR, "订单已经结算");
                }

//                $psNo = $r1[0]->p_sNo; // 父订单号

                //其它支付所支付金额
                BigDecimal outherPay = orderPrice.subtract(offsetBalance);
                //订单商品总价
                BigDecimal orderDetailPrice = new BigDecimal(returnOrderInfoMap.get("p_price").toString());
                //商品数量
                BigDecimal goodsNeedNum = new BigDecimal(returnOrderInfoMap.get("num").toString());
                orderDetailPrice = orderDetailPrice.multiply(goodsNeedNum);

                //退款金额限制
                if (orderPrice.compareTo(vo.getPrice()) < 0) {
                    logger.debug("订单详情id{}退款失败,金额错误 退款金额{} 实际订单支付金额{}", detailId, vo.getPrice(), goodsPayPrice);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "输入的金额有误,请重新输入");
                } else if (goodsPayPrice.compareTo(vo.getPrice()) < 0) {
                    logger.debug("订单详情id{}退款失败,金额错误 退款金额{} 实际商品支付金额{}", detailId, vo.getPrice(), goodsPayPrice);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "输入的金额有误,请重新输入");
                }

                if (read == 0) {
                    OrderModel orderModel = new OrderModel();
                    orderModel.setId(orderId);
                    orderModel.setReadd(1);
                    count = orderModelMapper.updateByPrimaryKeySelective(orderModel);
                    if (count < 1) {
                        logger.info("修改订单已读状态失败 参数:" + JSON.toJSONString(orderModel));
                    }
                }
                //当不是后台操作时
                if (vo.getMchId() != 0) {
                    //验证店铺
                    if (vo.getMchId() != mchId) {
                        logger.info("订单所属店铺不一致 参数:" + vo.getMchId() + ">>>" + mchId);
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "门店编号不正确", "refund");
                    }
                }
                //获取店铺信息
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(vo.getStoreId());
                mchModel.setId(mchId);
                mchModel = mchModelMapper.selectOne(mchModel);
                if (mchModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺数据异常", "refund");
                }
                //站内推送消息
                String tuiTitle = "退货/退款成功!";
                String tuiContext = "您的退货/退款申请已通过!";
                SystemMessageModel systemMessageSave = new SystemMessageModel();
                systemMessageSave.setStore_id(vo.getStoreId());
                systemMessageSave.setSenderid("admin");
                systemMessageSave.setRecipientid(clientUserId);
                systemMessageSave.setTitle(tuiTitle);
                systemMessageSave.setContent(tuiContext);
                systemMessageSave.setType(1);

                //实际退款金额
                BigDecimal realMoney = null;
                //订单售后流程
                if (vo.getType().equals(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK)
                        || vo.getType().equals(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_RECEIVED_REBAKGOODS)
                        || vo.getType().equals(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK_AMT)) {
                    logger.info("=====退款开始=====");
                    //同意退款
                    if (vo.getType().equals(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_RECEIVED_REBAKGOODS)
                            || vo.getType().equals(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK_AMT)) {
                        logger.info("===同意退款===");
                        //获取实际支付金额
                        logger.debug("同意订单详情id{}退款,订单商品付款金额{},实际退款金额{}", detailId, goodsPayPrice, vo.getPrice());
                        //TODO 【组合支付不要了】
                        if (DictionaryConst.OrderPayType.ORDERPAYTYPE_TT_ALIPAY.equals(payType)) {
                            payType = DictionaryConst.OrderPayType.ORDERPAYTYPE_ALIPAY;
                        } else if (DictionaryConst.OrderPayType.ORDERPAYTYPE_BAIDU_PAY.equals(payType)) {
                            payType = DictionaryConst.OrderPayType.ORDERPAYTYPE_WALLET_PAY;
                        }
                        //获取短信列表
                        Map<String, Object> messageInfo = messageListModelMapper.getMessageListInfoByType(vo.getStoreId(), GloabConst.VcodeCategory.TYPE_NOTICE, GloabConst.VcodeCategory.PAY_REFUND_ORDER);
                        String content;
                        String parmaJson;
                        Map<String, String> smsParmaMap = null;
                        if (messageInfo != null) {
                            isSendNotice = true;
                            content = messageInfo.get("content").toString();
                            parmaJson = JSON.toJSONString(SerializePhpUtils.getUnserializeObj(content, Map.class));
                            smsParmaMap = JSON.parseObject(parmaJson, new TypeReference<Map<String, String>>() {
                            });
                        } else {
                            logger.error("商城【{}】未配置通知短信服务!", vo.getStoreId());
                        }

                        logger.info("退款信息payType，{}", payType);
                        Map<String, String> map = new HashedMap();
                        //退款流程
                        switch (payType) {
                            case DictionaryConst.OrderPayType.ORDERPAYTYPE_WALLET_PAY:
                                //钱包支付全额退款
                                publicMemberService.returnUserMoney(vo.getStoreId(), clientUserId, vo.getPrice());
                                break;
                            case DictionaryConst.OrderPayType.ORDERPAYTYPE_APP_WECHAT:
                            case DictionaryConst.OrderPayType.ORDERPAYTYPE_MINI_WECHAT:
                            case DictionaryConst.OrderPayType.ORDERPAYTYPE_PC_WECHAT:
                            case DictionaryConst.OrderPayType.ORDERPAYTYPE_H5_WECHAT:
                            case DictionaryConst.OrderPayType.ORDERPAYTYPE_JSAPI_WECHAT:
                                //微信退款
                                logger.info("退款参数-{}-,", JSON.toJSONString(vo));
                                logger.info("退款参数orderId-{},", orderId);
                                logger.info("退款参数payType-{}-,", payType);
                                logger.info("退款参数realOrderno-{},", realOrderno);
                                map = publicWechatServiceImpl.refundOrder(vo.getStoreId(), orderId, payType, realOrderno, vo.getPrice());
                                logger.info("#########退款返回##### {}", JSONObject.toJSONString(map));
                                break;
                            default:
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "支付方式不存在", "refund");
                        }
                        MchAccountLogModel mchAccountLogModel = new MchAccountLogModel();
                        mchAccountLogModel.setStore_id(vo.getStoreId());
                        mchAccountLogModel.setMch_id(mchModel.getId().toString());
                        realMoney = vo.getPrice();
                        //刷新店铺信息
                        mchModel = mchModelMapper.selectByPrimaryKey(mchModel);
                        //收货后处理 收货后不管店铺输入多少退款金额,都按当前订单商品实际支付金额来。少退的会到可提现金额里面
                        if (isArruve) {
                            //店铺金额是否充足
                            if (vo.getPrice().compareTo(mchModel.getAccount_money()) > 0) {
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_NOT_REFUND_ERROR, "退款失败,店铺余额不足", "refund");
                            }

                            //计算店铺余额
                            count = mchModelMapper.mchSettlement(mchModel.getId(), realMoney);
                            if (count < 1) {
                                logger.info("退款失败 店铺{} 应退款{}", mchModel.getId(), realMoney);
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_NOT_REFUND_ERROR, "退款失败,网络故障", "refund");
                            }
                            //添加一条退款记录
                            mchAccountLogModel.setPrice(vo.getPrice());
                            mchAccountLogModel.setAccount_money(mchModel.getAccount_money());
                            mchAccountLogModel.setType(DictionaryConst.MchAccountLogType.MCHACCOUNTLOG_TYPE_REFUND);
                            mchAccountLogModel.setStatus(DictionaryConst.MchAccountLogStatus.MCHACCOUNTLOG_STATUS_EXPENDITURE);
                            mchAccountLogModel.setAddtime(new Date());
                            count = mchAccountLogModelMapper.insertSelective(mchAccountLogModel);
                            if (count < 1) {
                                logger.info("退款记录失败 参数:" + JSON.toJSONString(mchAccountLogModel));
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_NOT_REFUND_ERROR, "退款失败,网络故障");
                            }

                        }
                        //添加一条退款记录---少退的情况 是否少退,如果少退则增加店铺收入-立即结算到可提现金额
                        BigDecimal tuiMoney = goodsPayPrice.subtract(vo.getPrice());
                        if (tuiMoney.compareTo(BigDecimal.ZERO) > 0) {
                            //标记该商品已经结算
                            isSettlement = true;
                            //刷新店铺信息
                            mchModel = mchModelMapper.selectByPrimaryKey(mchModel);
                            //退款-少退的部分记录到账【入账】 这里记录的是店铺钱包,但是实际上到账是到可提现账户【这里最好记录可提现账户的流水】
                            mchAccountLogModel.setPrice(tuiMoney);
                            mchAccountLogModel.setAccount_money(mchModel.getAccount_money());
                            mchAccountLogModel.setType(DictionaryConst.MchAccountLogType.MCHACCOUNTLOG_TYPE_REFUND);
                            mchAccountLogModel.setStatus(DictionaryConst.MchAccountLogStatus.MCHACCOUNTLOG_STATUS_INCOME);
                            mchAccountLogModel.setAddtime(new Date());
                            count = mchAccountLogModelMapper.insertSelective(mchAccountLogModel);
                            if (count < 1) {
                                logger.info("退款记录失败(少退的情况) 参数:" + JSON.toJSONString(mchAccountLogModel));
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_NOT_REFUND_ERROR, "退款失败,网络故障");
                            }
                            //增加店铺收入[可提现账户]
                            count = mchModelMapper.refuseWithdraw(mchId, tuiMoney);
                            if (count < 1) {
                                logger.info("退款增加店铺收入失败(少退的情况) ");
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_NOT_REFUND_ERROR, "退款失败");
                            }
                        }

                        //获取商品库存信息
                        ConfiGureModel confiGureModel = new ConfiGureModel();
                        confiGureModel.setId(attributeId);
                        confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                        if (confiGureModel == null) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品库存不存在", "refund");
                        }
                        //回滚库存
                        count = confiGureModelMapper.reduceGoodsStockNum(goodsNeedNum.negate().intValue(), confiGureModel.getId());
                        if (count < 1) {
                            logger.info("回滚属性库存失败 商品id:" + goodsId + " 退货数量:" + goodsNeedNum);
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                        }
                        //减销量
                        count = productListModelMapper.updateProductListVolume(goodsNeedNum.negate().intValue(), vo.getStoreId(), goodsId);
                        if (count < 1) {
                            logger.info("销量扣减失败 商品id:" + goodsId + " 销量扣减数量:" + goodsNeedNum);
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                        }
                        // 增加商品库存
                        count = productListModelMapper.addGoodsStockNum(goodsId, goodsNeedNum.intValue());
                        if (count < 1) {
                            logger.info("商品库存回滚失败 商品id:" + goodsId + " 销量扣减数量:" + goodsNeedNum);
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                        }

                        //退款成功 增加商品总库存
                        String text = "退款成功,增加商品库存" + goodsNeedNum;
                        StockModel stockModel = new StockModel();
                        stockModel.setStore_id(vo.getStoreId());
                        stockModel.setProduct_id(goodsId);
                        stockModel.setAttribute_id(attributeId);
                        stockModel.setTotal_num(confiGureModel.getTotal_num());
                        stockModel.setFlowing_num(goodsNeedNum.intValue());
                        stockModel.setType(DictionaryConst.StockType.STOCKTYPE_WAREHOUSING);
                        stockModel.setContent(text);
                        stockModel.setAdd_date(new Date());
                        count = stockModelMapper.insertSelective(stockModel);
                        if (count < 1) {
                            logger.info("添加商品库存失败 参数:" + JSON.toJSONString(stockModel));
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                        }
                        //修改订单状态为关闭
                        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                        orderDetailsModel.setId(detailId);
                        if (isSettlement) {
                            orderDetailsModel.setSettlement_type(OrderDetailsModel.SETTLEMENT_TYPE_SETTLED);
                        }
                        orderDetailsModel.setR_status(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE);
                        orderDetailsModel.setAudit_time(new Date());
                        count = orderDetailsModelMapper.updateByPrimaryKeySelective(orderDetailsModel);
                        if (count < 1) {
                            logger.info("关闭订单失败 参数:" + JSON.toJSONString(orderDetailsModel));
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                        }
                        //修改订单金额 全额退款
                        count = orderModelMapper.updateOrderPrice(vo.getStoreId(), orderno, goodsPayPrice, freight, orderDetailPrice);
                        if (count < 1) {
                            logger.info(String.format("退款订单价格扣减失败 订单号:%s 扣减订单总金额:%s 扣减运费:%s 扣减订单总商品金额:%s", orderno, vo.getPrice(), freight, orderDetailPrice));
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                        }
                        //如果订单商品使用了优惠卷
                        if (!StringUtils.isEmpty(couponId)) {
                            if (!("0,0".equals(couponId) || "0".equals(couponId))) {
                                // 当订单详情使用了优惠券
                                // 订单详情使用的优惠券ID字符串 转数组
                                String[] couponList = couponId.split(SplitUtils.DH);
                                for (int i = 0; i < couponList.length; i++) {
                                    String tmpCouponId = couponList[i];
                                    if (!"0".equals(tmpCouponId) && null != tmpCouponId) {
                                        // 使用了优惠券
                                        if (i == 0 || (i == 1 && "".equals(psNo))) {
                                            // 使用了店铺优惠券 或 (使用了平台优惠券 并且 不是跨店铺订单)
                                            // 根据商城ID、订单号、店铺优惠券ID，查询不是这个订单详情的数据
                                            List<Map<String, Object>> otherOrders = orderDetailsModelMapper.getOrderDetailsUseTheCoupon(vo.getStoreId(), orderno, tmpCouponId, orderId);
                                            boolean flag = false;
                                            if (otherOrders != null && otherOrders.size() > 0) {
                                                // 存在(该订单里，还有其它详情使用了这张店铺优惠券)
                                                // 该订单里，有多少详情使用了这张店铺优惠券
                                                int size = otherOrders.size();
                                                // 该订单里，使用了这张店铺优惠券,并退款或退货退款成功的数量
                                                int returnNum = 0;
                                                for (Map<String, Object> otherOrderDetail : otherOrders) {
                                                    int orderStatus = MapUtils.getIntValue(otherOrderDetail, "r_status");
                                                    if (orderStatus == DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE) {
                                                        returnNum++;
                                                    }
                                                }
                                                if (returnNum == size) {
                                                    flag = true;
                                                }
                                            } else {
                                                flag = true;
                                            }
                                            if (flag) {
                                                // 该订单，使用了这张店铺优惠券的订单商品都退款或退款退款成功
                                                int row = publicCouponService.couponWithOrder(vo.getStoreId(), clientUserId, tmpCouponId, orderno, "update");
                                                if (row == 0) {
                                                    //回滚删除已经创建的订单
                                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                                                }
                                                // =2
                                                row = publicCouponService.updateCoupons(vo.getStoreId(), clientUserId, tmpCouponId, CouponModal.COUPON_TYPE_NOT_USED);
                                                if (row == 0) {
                                                    //回滚删除已经创建的订单
                                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        //统一订单状态
                        publicOrderService.updateOrderState(vo.getStoreId(), orderno);
                    }
                } else if (DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK_GOODS.equals(vo.getType())) {
                    tuiTitle = String.format("【%s】订单发货啦！", orderno);
                    tuiContext = "您购买的商品已经在赶去见您的路上啦！";
                    //添加一条快递数据
                    ReturnGoodsModel returnGoodsModel = new ReturnGoodsModel();
                    returnGoodsModel.setStore_id(vo.getStoreId());
                    returnGoodsModel.setName(clientUserName);
                    returnGoodsModel.setTel(clientUserPhon);
                    //查询快递公司信息
                    ExpressModel expressModel = new ExpressModel();
                    expressModel.setId(vo.getExpressId());
                    expressModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    expressModel = expressModelMapper.selectOne(expressModel);
                    if (expressModel == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "物流公司不存在");
                    }
                    returnGoodsModel.setExpress(expressModel.getKuaidi_name());
                    returnGoodsModel.setExpress_num(vo.getCourierNum());
                    returnGoodsModel.setUser_id(clientUserId);
                    returnGoodsModel.setOid(detailId + "");
                    returnGoodsModel.setRe_id(vo.getId());
                    returnGoodsModel.setAdd_data(new Date());
                    count = returnGoodsModelMapper.insertSelective(returnGoodsModel);
                    if (count < 1) {
                        logger.info("新增售后记录失败 参数:" + JSON.toJSONString(returnGoodsModel));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                    }
                    //修改明细物流信息
                    OrderDetailsModel updateOrderDetail = new OrderDetailsModel();
                    updateOrderDetail.setId(detailId);
                    updateOrderDetail.setExpress_id(expressModel.getId());
                    updateOrderDetail.setCourier_num(returnGoodsModel.getExpress_num());
                    count = orderDetailsModelMapper.updateByPrimaryKeySelective(updateOrderDetail);
                    if (count < 1) {
                        logger.info("修改订单物流信息失败 参数:" + JSON.toJSONString(updateOrderDetail));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                    }
                } else {
                    //0:审核中 2:拒绝(退货退款) 3:用户已快递 5：拒绝并退回商品 8:拒绝(退款)10:拒绝(售后)
                    tuiTitle = "退货/退款失败!";
                    tuiContext = String.format("您申请的退货/退款被拒绝!原因：%s", vo.getText());
                    //获取该订单最后一条售后信息
                    ReturnRecordModel returnRecordModel = returnRecordModelMapper.getReturnRecordByMax(vo.getStoreId(), detailId);
                    //编辑售后记录信息
                    ReturnRecordModel updateReturn = new ReturnRecordModel();
                    updateReturn.setId(returnRecordModel.getId());
                    updateReturn.setR_type(vo.getType());
                    updateReturn.setContent(vo.getText());
                    count = returnRecordModelMapper.updateByPrimaryKeySelective(updateReturn);
                    if (count < 1) {
                        logger.info("修改售后信息失败 参数:" + JSON.toJSONString(updateReturn));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                    }
                }
                //修改售后状态
                ReturnOrderModel updateReturnOrder = new ReturnOrderModel();
                updateReturnOrder.setId(vo.getId());
                updateReturnOrder.setR_type(vo.getType());
                updateReturnOrder.setR_content(vo.getText());
                if (realMoney != null && realMoney.compareTo(BigDecimal.ZERO) > 0) {
                    //应退金额
                    updateReturnOrder.setRe_money(goodsPayPrice.floatValue());
                    //实际退款金额
                    updateReturnOrder.setReal_money(realMoney.floatValue());
                }
                updateReturnOrder.setAudit_time(new Date());
                count = returnOrderModelMapper.updateByPrimaryKeySelective(updateReturnOrder);
                if (count < 1) {
                    logger.info("订单售后状态失败 参数:" + JSON.toJSONString(updateReturnOrder));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障,退款失败", "refund");
                }
                //TODO 【微信推送暂时不做】 发送微信小程序推送退款信息
                //站内推送退款信息
                systemMessageSave.setTitle(tuiTitle);
                systemMessageSave.setContent(tuiContext);
                systemMessageSave.setTime(new Date());
                systemMessageModelMapper.insertSelective(systemMessageSave);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "售后订单不存在", "refund");
            }
            return true;
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "refund");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("售后 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "refund");
        }
    }

    @Override
    public Map<String, Object> refundPageById(int storeId, int id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //获取售后订单细信息
            Map<String, Object> refundInfoMap = returnOrderModelMapper.getReturnOrderMap(storeId, id);
            if (refundInfoMap != null && !refundInfoMap.isEmpty()) {
                //商品id
                int pid = Integer.parseInt(refundInfoMap.get("goodsId").toString());
                refundInfoMap.put("pid", pid);
                //订单详情id
                int orderDetailId = Integer.parseInt(refundInfoMap.get("p_id").toString());
                //售后处理状态
                int rtype = Integer.parseInt(refundInfoMap.get("rtype").toString());
                //订单号
                String orderno = refundInfoMap.get("sNo").toString();
                refundInfoMap.put("re_time", DateUtil.dateFormate(MapUtils.getString(refundInfoMap,"re_time"), GloabConst.TimePattern.YMDHMS));

                //获取商品信息
                ProductListModel productListModel = new ProductListModel();
                productListModel.setId(pid);
                productListModel = productListModelMapper.selectOne(productListModel);
                if (productListModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品不存在", "refundPageById");
                }
                String imgUrl = publiceService.getImgPath(productListModel.getImgurl(), storeId);
                refundInfoMap.put("imgurl", imgUrl);

                //商品金额
                BigDecimal goodsPrice = new BigDecimal(refundInfoMap.get("p_price").toString());
                //实付金额
                BigDecimal paidPrice = new BigDecimal(refundInfoMap.get("after_discount").toString());
                //数量
                BigDecimal needNum = new BigDecimal(refundInfoMap.get("num").toString());
                //运费
                BigDecimal yunfei = new BigDecimal(refundInfoMap.get("freight").toString());
                //小计
                BigDecimal subtotal = goodsPrice.multiply(needNum).add(yunfei);
                //合计
                BigDecimal total = paidPrice.add(yunfei);
                refundInfoMap.put("z_price", subtotal);
                refundInfoMap.put("total", total);

                //获取售后凭证图
                List<String> rePhotoImgUrlList = new ArrayList<>();
                String rePhoto = String.valueOf(refundInfoMap.get("re_photo"));
                Map<Integer, String> rePhotoMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(rePhoto, Map.class));
                if (rePhotoMap != null) {
                    for (Integer key : rePhotoMap.keySet()) {
                        String photoImgUrl = rePhotoMap.get(key);
                        photoImgUrl = publiceService.getImgPath(photoImgUrl, storeId);
                        rePhotoImgUrlList.add(photoImgUrl);
                    }
                }

                //用户退货记录信息
                List<ReturnGoodsModel> returnGoodsModelList = null;
                if (DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_USER_DELIVERED == rtype
                        || DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK_GOODS == rtype
                        || DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AFTER_SALE_END == rtype) {
                    ReturnGoodsModel returnGoodsModel = new ReturnGoodsModel();
                    returnGoodsModel.setStore_id(storeId);
                    returnGoodsModel.setOid(String.valueOf(orderDetailId));
                    returnGoodsModel.setRe_id(id);
                    returnGoodsModelList = returnGoodsModelMapper.select(returnGoodsModel);
                }
                //标记已读
                orderModelMapper.updateIsReadd(storeId, orderno, OrderModel.READ);

                //查询售后记录
                ReturnRecordModel returnRecordModel = new ReturnRecordModel();
                returnRecordModel.setStore_id(storeId);
                returnRecordModel.setP_id(orderDetailId);
                List<ReturnRecordModel> returnRecordModels = returnRecordModelMapper.select(returnRecordModel);

                //用户退货记录信息
                resultMap.put("rdata", returnGoodsModelList);
                //售后记录信息
                resultMap.put("record", returnRecordModels);
                //售后订单细信息
                resultMap.put("list", refundInfoMap);
                //售后凭证信息
                resultMap.put("imgs", rePhotoImgUrlList);
            }
        } catch (Exception e) {
            logger.error("根据id查询售后信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getBrandInfo");
        }
        return resultMap;
    }

    @Override
    public String getRefundStatus(int storeId, int detailId) throws LaiKeApiException {
        String statusName;
        try {
            if (detailId == 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "详情id不能为空");
            }
            //查询售后情况
            ReturnOrderModel returnOrderModel = returnOrderModelMapper.getReturnNewInfoByDetailId(detailId);
            statusName = getRefundStatusByName(returnOrderModel);
        } catch (Exception e) {
            logger.error("获取售后状态【订单商品】 异常" + e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRefundStatus");
        }
        return statusName;
    }

    @Override
    public String getRefundStatus(int storeId, String orderNo) throws LaiKeApiException {
        String statusName;
        try {
            if (StringUtils.isEmpty(orderNo)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "订单号不能为空");
            }
            //查询售后情况
            ReturnOrderModel returnOrderModel = returnOrderModelMapper.getReturnNewInfoBySno(orderNo);
            statusName = getRefundStatusByName(returnOrderModel);
        } catch (Exception e) {
            logger.error("获取售后状态【订单】 异常" + e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRefundStatus");
        }
        return statusName;
    }

    private String getRefundStatusByName(ReturnOrderModel returnOrderModel) {
        String statusName = "";
        if (returnOrderModel != null) {
            int rType = returnOrderModel.getR_type();
            int reType = returnOrderModel.getRe_type();

            //退款转态
            if (rType == 0) {
                statusName = "待审核";
            } else if (reType == DictionaryConst.ReturnRecordReType.RETURNORDERTYPE_REFUSE_AMT
                    && (rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_USER_DELIVERED)) {
                statusName = "退款中";
            } else if (rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_RECEIVED_REBAKGOODS || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK_AMT) {
                statusName = "退款成功";
            } else if (reType != DictionaryConst.ReturnRecordReType.RETURNORDERSTATUS_GOODS_REBACK
                    && (rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_REFUSE_REBACKGOODS_AMT || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_REFUSE_REBACKGOODS || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_REFUSE_AMT)) {
                statusName = "退款失败";
            } else if (reType == DictionaryConst.ReturnRecordReType.RETURNORDERSTATUS_GOODS_REBACK
                    && (rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_USER_DELIVERED || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK_GOODS)) {
                statusName = "退换中";
            } else if (rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AFTER_SALE_END) {
                statusName = "换货成功";
            } else if (reType == DictionaryConst.ReturnRecordReType.RETURNORDERSTATUS_GOODS_REBACK
                    && (rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_REFUSE_REBACKGOODS || rType == DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_REFUSE_AFTER_SALE)) {
                statusName = "换货失败";
            }
        }
        return statusName;
    }

    /**
     * 获取订单实际支付金额
     * 【php tool.get_order_price】
     *
     * @param id - 订单明细id
     * @return BigDecimal
     * @throws LaiKeApiException; -
     * @author Trick
     * @date 2020/12/2 15:33
     */
    private BigDecimal getOrderPrice(int storeId, int id) throws LaiKeApiException {
        try {
            BigDecimal orderPrice;
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("detailId", id);
            //获取商品信息
            List<Map<String, Object>> orderGoodsInfoList = orderModelMapper.getOrderInfoLeftDetailDynamic(parmaMap);
            if (orderGoodsInfoList != null && orderGoodsInfoList.size() > 0) {
                Map<String, Object> map = orderGoodsInfoList.get(0);
                //快递id
                int expressId = StringUtils.stringParseInt(map.get("express_id") + "");
                //运费
                BigDecimal freight = new BigDecimal(map.get("freight").toString());
                //商品优惠后的实际金额
                BigDecimal afterDiscount = new BigDecimal("0");
                if (map.get("after_discount") != null) {
                    afterDiscount = new BigDecimal(map.get("after_discount").toString());
                }

                //判断是否发货
                if (freight.doubleValue() > 0 && expressId > 0) {
                    orderPrice = afterDiscount;
                } else {
                    //计算实际支付金额
                    orderPrice = afterDiscount.add(freight);
                }

                return orderPrice;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "未找到订单信息", "getOrderPrice");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取订单实际支付金额 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getOrderPrice");
        }
    }


    @Autowired
    private ServiceAddressModelMapper serviceAddressModelMapper;

    @Autowired
    private CustomerModelMapper customerModelMapper;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private ReturnOrderModelMapper returnOrderModelMapper;

    @Autowired
    private PublicMemberService publicMemberService;

    @Autowired
    @Qualifier("publicWechatServiceImpl")
    private PublicPaymentService publicWechatServiceImpl;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private MessageListModelMapper messageListModelMapper;

    @Autowired
    private MchAccountLogModelMapper mchAccountLogModelMapper;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private StockModelMapper stockModelMapper;

    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;

    @Autowired
    private ReturnGoodsModelMapper returnGoodsModelMapper;

    @Autowired
    private ExpressModelMapper expressModelMapper;

    @Autowired
    private ReturnRecordModelMapper returnRecordModelMapper;

    @Autowired
    private SystemMessageModelMapper systemMessageModelMapper;

    @Autowired
    private PublicCouponService publicCouponService;
}
