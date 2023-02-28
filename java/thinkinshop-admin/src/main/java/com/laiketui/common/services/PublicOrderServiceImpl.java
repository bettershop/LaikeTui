package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.laiketui.api.common.*;
import com.laiketui.api.common.order.PublicIntegralService;
import com.laiketui.api.common.pay.PublicPaymentService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.domain.config.IntegralConfigModel;
import com.laiketui.domain.coupon.CouponActivityModel;
import com.laiketui.domain.home.SystemMessageModel;
import com.laiketui.domain.log.AdminRecordModel;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.mch.*;
import com.laiketui.domain.message.MessageLoggingModal;
import com.laiketui.domain.order.*;
import com.laiketui.domain.payment.PaymentModel;
import com.laiketui.domain.product.AuctionProductModel;
import com.laiketui.domain.product.CommentsModel;
import com.laiketui.domain.product.FreightModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.OrderVo;
import com.laiketui.domain.vo.mch.FrontDeliveryVo;
import com.laiketui.domain.vo.mch.MchOrderDetailVo;
import com.laiketui.domain.vo.mch.MchOrderIndexVo;
import com.laiketui.domain.vo.order.*;
import com.laiketui.domain.vo.pay.PayVo;
import com.laiketui.domain.vo.pay.PaymentVo;
import com.laiketui.domain.vo.pc.MchPcOrderIndexVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.algorithm.Md5Util;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.okhttp.HttpUtils;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.laiketui.domain.order.OrderModel.ORDER_FINISH;
import static com.laiketui.domain.order.OrderModel.ORDER_UNRECEIVE;
import static com.laiketui.root.consts.DictionaryConst.OrdersStatus.*;
import static com.laiketui.root.consts.ErrorCode.BizErrorCode.*;


/**
 * 订单通用服务类
 *
 * @author wangxian
 */
@Service
public class PublicOrderServiceImpl implements PublicOrderService {

    private final Logger logger = LoggerFactory.getLogger(PublicOrderServiceImpl.class);

    @Override
    public Map<String, Object> getFreight(Map<String, List<Map<String, Object>>> freightMap, List<Map<String, Object>> products, UserAddress userAddress, int storeId, String productType) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            double yunfei = 0.0;
            int productNum = 0;
            //是否开启了包邮设置
            int packageSettings = 0;
            //
            boolean orderYunfei = false;
            //同件
            int samePiece = 0;
            //同单
            Integer sameOrder = null;
            if (DictionaryConst.OrdersType.ORDERS_HEADER_MS.equals(productType)) {

            } else {
                OrderConfigModal orderConfigModal = new OrderConfigModal();
                orderConfigModal.setStore_id(storeId);
                orderConfigModal = orderConfigModalMapper.selectOne(orderConfigModal);
                if (orderConfigModal != null) {
                    packageSettings = orderConfigModal.getPackage_settings();
                    // 同件
                    samePiece = orderConfigModal.getSame_piece();
                    // 同单
                    sameOrder = orderConfigModal.getSame_order();
                }
            }
            //店铺id集合
            Set<String> keys = freightMap.keySet();
            // 开启包邮设置
            if (packageSettings == 1) {
                for (String key : keys) {
                    List<Map<String, Object>> productFreights = freightMap.get(key);
                    for (Map<String, Object> productFreight : productFreights) {
                        int num = MapUtils.getInteger(productFreight, "num");
                        productNum += num;
                        if (samePiece <= num) {
                            productFreight.put("order_yunfei", true);
                        } else {
                            productFreight.put("order_yunfei", false);
                        }

                        if (sameOrder != null && productNum >= sameOrder) {
                            orderYunfei = true;
                        }
                    }
                }
            } else {
                for (String key : keys) {
                    List<Map<String, Object>> productFreights = freightMap.get(key);
                    for (Map<String, Object> productFreight : productFreights) {
                        productFreight.put("order_yunfei", false);
                    }
                }
            }

            if (orderYunfei) {
                //满足免邮条件
                for (Map<String, Object> product : products) {
                    product.put("freight", 0.0);
                }
            } else {
                //各个店铺下商品所需的运费
                Map<String, Object> freightIdMap = new HashMap<>();
                for (String key : keys) {
                    //运费信息
                    resultMap.put("freight_id", freightIdMap);
                    List<Map<String, Object>> productFreights = freightMap.get(key);
                    for (Map<String, Object> productFreight : productFreights) {
                        //获取运费模版id
                        Integer freightIdObj = MapUtils.getInteger(productFreight, "freight_id");
                        //单件运费
                        boolean singlProductYunfei = (boolean) productFreight.get("order_yunfei");
                        FreightModel freightModel = new FreightModel();
                        if (freightIdObj != null) {
                            BigDecimal freightId = BigDecimal.valueOf(freightIdObj);
                            freightModel.setId(freightId.intValue());
                        } else {
                            freightModel.setMch_id(Integer.parseInt(key));
                            freightModel.setStore_id(storeId);
                            freightModel.setIs_default(1);
                        }
                        freightModel = freightModelMapper.selectOne(freightModel);
                        if (freightModel != null) {
                            //单个商品免运费
                            if (singlProductYunfei) {
                                yunfei = 0;
                                List<Double> mchProductFreigths = new ArrayList<>();
                                if (freightIdMap.containsKey(key)) {
                                    mchProductFreigths = DataUtils.cast(freightIdMap.get(key));
                                    if (mchProductFreigths == null) {
                                        mchProductFreigths = new ArrayList<>();
                                    }
                                }
                                mchProductFreigths.add(0.0);
                                freightIdMap.put(key, mchProductFreigths);
                            } else {
                                //获取运费
                                BigDecimal goodsYunFei = this.getFreight(freightModel.getId(), userAddress);
                                //计算总运费
                                yunfei += goodsYunFei.doubleValue();
                                List<Double> mchProductFreigths = new ArrayList<>();
                                mchProductFreigths.add(goodsYunFei.doubleValue());
                                freightIdMap.put(key, mchProductFreigths);
                            }
                        }
                    }
                }
            }

            resultMap.put("yunfei", yunfei);
            resultMap.put("freight_ids", resultMap.get("freight_id"));

            if (DictionaryConst.OrdersType.ORDERS_HEADER_JP.equals(productType)) {
                for (Map<String, Object> product : products) {
                    product.put("freight_price", yunfei);
                }
            } else {
                Map<String, List<Double>> freight_ids = DataUtils.cast(resultMap.get("freight_ids"));
                //计算各个店铺下商品的运费
                for (Map<String, Object> mchProduct : products) {
                    BigDecimal freight_price_total = BigDecimal.ZERO;
                    BigDecimal products_num = BigDecimal.ZERO;
                    int shop_id = (int) mchProduct.get("shop_id");

                    List<Map<String, Object>> onlyProductsInfo = DataUtils.cast(mchProduct.get("list"));
                    if (onlyProductsInfo == null) {
                        onlyProductsInfo = new ArrayList<>();
                    }
                    //纯商品信息
                    int pos = 0;
                    for (Map<String, Object> productInfo : onlyProductsInfo) {
                        BigDecimal freight_price = BigDecimal.ZERO;
                        products_num = products_num.add(new BigDecimal(productInfo.get("num") + ""));
                        if (freight_ids != null) {
                            List<Double> shopGoodsYunFeiList = freight_ids.get(shop_id + "");
                            logger.debug("计算订单运费 当前店铺{},当前店铺商品集运费{}", shop_id, JSON.toJSONString(shopGoodsYunFeiList));
                            if (shopGoodsYunFeiList != null && shopGoodsYunFeiList.size() > pos) {
                                freight_price = freight_price.add(new BigDecimal(shopGoodsYunFeiList.get(pos++).toString()));
                                freight_price_total = freight_price_total.add(freight_price);
                            }
                        }
                        productInfo.put("freight_price", freight_price);
                    }
                    mchProduct.put("freight_price", DoubleFormatUtil.format(freight_price_total.doubleValue()));
                    mchProduct.put("products_num", products_num);
                    Double mchProductTotal = (Double) mchProduct.get("product_total");
                    mchProduct.put("product_total", DoubleFormatUtil.format(mchProductTotal));
                }
            }
            resultMap.put("products", products);
        } catch (Exception e) {
            logger.error("运费计算失败 异常", e);
            throw new LaiKeApiException(ORDER_JS_FREIGHT_ERROR, "运费计算失败", "getFreight");
        }
        return resultMap;
    }

    @Override
    public BigDecimal getFreight(Integer goodsAddressId, UserAddress userAddress) throws LaiKeApiException {
        BigDecimal yunfei = new BigDecimal("0");
        try {
            if (goodsAddressId == null) {
                return yunfei;
            }
            //获取商品运费模板信息
            FreightModel freightModel = freightModelMapper.selectByPrimaryKey(goodsAddressId);
            if (freightModel != null && userAddress != null) {
                Map<Integer, LinkedHashMap<String, Object>> dataMap = SerializePhpUtils.getUnSerializeByFreight(freightModel.getFreight());
                for (Integer key : dataMap.keySet()) {
                    LinkedHashMap<String, Object> yufeiMap = dataMap.get(key);
                    if (yufeiMap.isEmpty()) {
                        continue;
                    }
                    //参数列表
                    Map<String, Object> parmaMap = new HashMap<>(16);
                    //获取地区名称
                    String[] nameList = yufeiMap.get("name").toString().split(",");
                    parmaMap.put("nameList", nameList);
                    //获取运费信息
                    List<Map<String, Object>> freightInfoList = adminCgModelMapper.getAdminCgInfoDynamic(parmaMap);
                    for (Map<String, Object> map : freightInfoList) {
                        String name = map.get("G_CName").toString();
                        if (name.equals(userAddress.getSheng())) {
                            yunfei = new BigDecimal(yufeiMap.get("one").toString());
                            break;
                        }
                    }
                }
            }
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.debug("运费计算 异常", e);
            throw new LaiKeApiException(ORDER_JS_FREIGHT_ERROR, "运费计算失败", "getFreight");
        }
        return yunfei;
    }

    @Override
    public String createOrderNo(String orderType) {
        try {
            if (StringUtils.isEmpty(orderType)) {
                orderType = DictionaryConst.OrdersType.ORDERS_HEADER_GM;
            }
            StringBuilder sNo = new StringBuilder();
            Random random = new Random();
            sNo.append(orderType).append(FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS2).format(new Date())).append(random.nextInt(9))
                    .append(random.nextInt(9)).append(random.nextInt(9)).append(random.nextInt(9))
                    .append(random.nextInt(9)).append(random.nextInt(9));
            OrderModel orderModel = new OrderModel();
            orderModel.setsNo(sNo.toString());
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                return sNo.toString();
            } else {
                return createOrderNo(orderType);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new LaiKeApiException(ORDER_NUMBER_FAILED, "创建订单号失败", "createOrderNo");
        }
    }

    @Override
    public Map<String, Object> bMchOrderIndex(MchPcOrderIndexVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //查询参数列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("mch_id", vo.getShopId());
            parmaMap.put("group_sNo", "group_sNo");
            parmaMap.put("add_time_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            if (StringUtils.isNotEmpty(vo.getOrderType())) {
                parmaMap.put("orderType", vo.getOrderType());
            }
            if (StringUtils.isNotEmpty(vo.getOrderno())) {
                parmaMap.put("order_like", vo.getOrderno());
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
            }
            if (StringUtils.isNotEmpty(vo.getEndDate())) {
                parmaMap.put("endDate", vo.getEndDate());
            }
            if (StringUtils.isNotEmpty(vo.getOrderType())) {
                parmaMap.put("otype", vo.getOrderType().toUpperCase());
            }
            if (vo.getOrderStauts() != null) {
                switch (vo.getOrderStauts()) {
                    case ORDERS_R_STATUS_UNPAID:
                        parmaMap.put("status", ORDERS_R_STATUS_UNPAID);
                        break;
                    case ORDERS_R_STATUS_CONSIGNMENT:
                        parmaMap.put("status", ORDERS_R_STATUS_CONSIGNMENT);
                        break;
                    case ORDERS_R_STATUS_DISPATCHED:
                        parmaMap.put("status", ORDERS_R_STATUS_DISPATCHED);
                        break;
                    case ORDERS_R_STATUS_COMPLETE:
                        parmaMap.put("status", ORDERS_R_STATUS_COMPLETE);
                        break;
                    case ORDERS_R_STATUS_CLOSE:
                        parmaMap.put("status", ORDERS_R_STATUS_CLOSE);
                        break;
                }
            }
            List<Map<String, Object>> orderList = orderModelMapper.selectbMchOrderIndex(parmaMap);
            int total = orderModelMapper.countbMchOrderIndex(parmaMap);
            for (Map<String, Object> map : orderList) {
                Map<String, Object> resultOrderMap = new HashMap<>(16);
                int orderId = MapUtils.getIntValue(map, "id");
                String orderno = MapUtils.getString(map, "sNo");
                int orderStatus = MapUtils.getIntValue(map, "status");
                String otype = MapUtils.getString(map, "otype");
                //支付方式
                String payType = map.get("payName") + "";
                if (StringUtils.isEmpty(payType)) {
                    payType = "钱包";
                }
                //计算订单成本价
                List<Map<String, Object>> priceInfoList = orderDetailsModelMapper.getbMchOrderIndexAmt(orderno, vo.getStoreId());
                BigDecimal costPrice = new BigDecimal("0");
                for (Map<String, Object> priceInfo : priceInfoList) {
                    costPrice = costPrice.add(new BigDecimal(priceInfo.get("num") + "").multiply(new BigDecimal(priceInfo.get("costprice") + "")));
                }
                resultOrderMap.put("orderno", orderno);
                StringBuilder address = new StringBuilder(map.get("sheng").toString());
                address.append(map.get("shi").toString()).append(map.get("xian").toString()).append(map.get("address").toString());
                resultOrderMap.put("address", address);
                resultOrderMap.put("status", orderStatus);
                resultOrderMap.put("pay", payType);

                //订单明细
                List<Map<String, Object>> orderDetailList = orderDetailsModelMapper.getbMchOrderIndexDetail(orderno);
                //当前订单物流集
                List<String> wuliyList = new ArrayList<>();
                //统计当前订单总运费
                BigDecimal yunFei = new BigDecimal("0");
                //订单状态
                Set<Integer> orderStatusList = new HashSet<>();
                for (Map<String, Object> detail : orderDetailList) {
                    BigDecimal yf = new BigDecimal(MapUtils.getString(detail, "freight"));
                    yunFei = yunFei.add(yf);
                    //物流id
                    Integer expressId = MapUtils.getInteger(detail, "express_id");
                    if (expressId != null) {
                        ExpressModel expressModel = new ExpressModel();
                        expressModel.setId(expressId);
                        expressModel = expressModelMapper.selectOne(expressModel);
                        if (expressModel != null) {
                            String wuliu = String.format("%s(%s)", expressModel.getId(), expressModel.getKuaidi_name());
                            wuliyList.add(wuliu);
                        }
                    }
                    String imgUrl = MapUtils.getString(detail, "imgurl");
                    detail.put("imgUrl", publiceService.getImgPath(imgUrl, vo.getStoreId()));
                    //如果明细状态都是 7 则代修改订单为相同状态
                    int status = MapUtils.getIntValue(detail, "r_status");
                    if (ORDERS_R_STATUS_CLOSE == status) {
                        orderStatusList.add(status);
                    }
                }
                //是否处于同一状态
                if (orderStatusList.size() == 1) {
                    //状态是否未发生变化
                    if (!orderStatusList.contains(orderStatus)) {
                        OrderModel orderUpdate = new OrderModel();
                        orderUpdate.setId(orderId);
                        orderUpdate.setStatus(orderStatus = orderStatusList.iterator().next());
                        int row = orderModelMapper.updateByPrimaryKeySelective(orderUpdate);
                        if (row < 1) {
                            logger.debug("{} 订单和明细状态不一致,修改失败", orderno);
                        }
                    }
                }
                switch (orderStatus) {
                    case ORDERS_R_STATUS_UNPAID:
                        resultOrderMap.put("status", "待付款");
                        break;
                    case ORDERS_R_STATUS_CONSIGNMENT:
                        resultOrderMap.put("status", "待发货");
                        if (DictionaryConst.OrdersType.ORDERS_HEADER_PT.equals(otype.toUpperCase())) {
                            resultOrderMap.put("status", "拼团成功");
                        }
                        break;
                    case ORDERS_R_STATUS_DISPATCHED:
                        resultOrderMap.put("status", "待付款");
                        if (DictionaryConst.OrdersType.ORDERS_HEADER_PT.equals(otype.toUpperCase())) {
                            resultOrderMap.put("status", "拼团成功");
                        }
                        break;
                    case ORDERS_R_STATUS_COMPLETE:
                        resultOrderMap.put("status", "订单完成");
                        if (DictionaryConst.OrdersType.ORDERS_HEADER_PT.equals(otype.toUpperCase())) {
                            resultOrderMap.put("status", "拼团成功");
                        }
                        break;
                    case ORDERS_R_STATUS_CLOSE:
                        resultOrderMap.put("status", "订单关闭");
                        if (DictionaryConst.OrdersType.ORDERS_HEADER_PT.equals(otype.toUpperCase())) {
                            resultOrderMap.put("status", "拼团成功");
                        }
                        break;
                    default:
                        break;
                }

                resultOrderMap.put("freight", yunFei);
                resultOrderMap.put("courier_num", wuliyList);
                resultOrderMap.put("products", orderDetailList);

                map.clear();
                map.putAll(resultOrderMap);
            }

            resultMap.put("list", orderList);
            resultMap.put("total", total);
        } catch (Exception e) {
            logger.error("订单列表（pc店铺端） 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "aMchOrderIndex");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> pcMchOrderIndex(AdminOrderListVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("operation_type", vo.getOperationType());
            if (StringUtils.isNotEmpty(vo.getKeyWord())) {
                parmaMap.put("sNo", vo.getKeyWord());
            }
            //特殊订单
            if (vo.getSelfLifting() != null) {
                int selfLifting = 0;
                String orderType = null;
                List<String> list = new ArrayList<>();
                switch (vo.getSelfLifting()) {
                    case 1:
                        orderType = DictionaryConst.OrdersType.ORDERS_HEADER_GM;
                        break;
                    case 3:
                        orderType = DictionaryConst.OrdersType.ORDERS_HEADER_VI;
                        break;
                    case 4:
                        list.add(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
                        list.add(DictionaryConst.OrdersType.ORDERS_HEADER_VI);
                        parmaMap.put("orderTypeList_not", list);
                        break;
                    case 2:
                        selfLifting = 1;
                        break;
                    case 7:
                        orderType = DictionaryConst.OrdersType.ORDERS_HEADER_IN;
                        break;
                    case 8:
                        orderType = DictionaryConst.OrdersType.ORDERS_HEADER_MS;
                        break;
                }
                if (list.size() == 0) {
                    parmaMap.put("orderType", orderType);
                }
                parmaMap.put("self_lifting", selfLifting);
            }
            parmaMap.put("mch_name", vo.getMchName());
            parmaMap.put("ostatus", vo.getStatus());
            parmaMap.put("startdate", vo.getStartDate());
            parmaMap.put("enddate", vo.getEndDate());
            parmaMap.put("start", vo.getPageNo());
            parmaMap.put("pagesize", vo.getPageSize());
            parmaMap.put("group_sNo", "group_sNo");
            parmaMap.put("add_time_sort", DataUtils.Sort.ASC.toString());
            parmaMap.put("diy_status_sort", DataUtils.Sort.ASC.toString());
            if (vo.getId() != null) {
                parmaMap.put("orderId", vo.getId());
            }

            List<Map<String, Object>> resultList = new ArrayList<>();

            int total = orderModelMapper.countAdminOrderList(parmaMap);
            List<Map<String, Object>> orderList = new ArrayList<>();
            if (total > 0) {
                orderList = orderModelMapper.adminOrderList(parmaMap);
            }
            for (Map<String, Object> map : orderList) {
                String orderNo = MapUtils.getString(map, "sNo");
                //订单商品个数
                int goodsNum = MapUtils.getIntValue(map, "goodsNum");

                //导出需要导出明细,如果导出明细则会导致数量与前台不一致,这里先查询是否有多条明细,如果当前订单有多条明细则把明细放入集合
                if (vo.getExportType().equals(1)) {
                    Map<String, Object> orderParamMap = new HashMap<>(16);
                    orderParamMap.put("orderNo1", orderNo);
                    orderParamMap.put("group_detail_id", "group_detail_id");
                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                    orderDetailsModel.setR_sNo(orderNo);
                    int count = orderDetailsModelMapper.selectCount(orderDetailsModel);
                    if (count > 1) {
                        List<Map<String, Object>> orderDetailList = orderModelMapper.adminOrderList(orderParamMap);
                        for (Map<String, Object> detailMap : orderDetailList) {
                            detailMap.put("goodsNum", goodsNum);
                            pcMchOrderIndex(detailMap, vo.getStoreId(), resultList);
                        }
                        continue;
                    }
                }
                //导出end

                pcMchOrderIndex(map, vo.getStoreId(), resultList);
            }

            resultMap.put("total", total);
            resultMap.put("list", resultList);
        } catch (Exception e) {
            logger.error("订单列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "pcMchOrderIndex");
        }
        return resultMap;
    }

    //订单列表辅助方法
    private void pcMchOrderIndex(Map<String, Object> map, int storeId, List<Map<String, Object>> resultList) {
        try {
            Map<String, Object> res = new HashMap<>(16);
            res.put("id", MapUtils.getString(map, "id"));
            res.put("orderno", MapUtils.getString(map, "sNo"));
            res.put("mchOrderNo", MapUtils.getString(map, "real_sno"));
            res.put("createDate", DateUtil.dateFormate(MapUtils.getString(map, "add_time"), GloabConst.TimePattern.YMDHMS));
            res.put("mchName", MapUtils.getString(map, "shopName"));
            res.put("goodsName", MapUtils.getString(map, "product_title"));
            //商品图片
            String imgUrl = publiceService.getImgPath(MapUtils.getString(map, "img"), storeId);
            res.put("goodsImgUrl", imgUrl);
            //处理规格
            String attrStr = GoodsDataUtils.getProductSkuValue(MapUtils.getString(map, "attribute"));
            res.put("attrStr", attrStr);

            res.put("needNum", MapUtils.getString(map, "needNum"));
            res.put("num", MapUtils.getString(map, "num"));
            res.put("goodsPrice", MapUtils.getString(map, "goodsAmt"));
            res.put("orderPrice", MapUtils.getString(map, "z_price"));
            //下单类型1用户下单2店铺下单3平台下单
            res.put("operation_type", MapUtils.getString(map, "operation_type"));
            //订单状态
            res.put("status", OrderDataUtils.getOrderStatus(MapUtils.getIntValue(map, "status")));
            //订单类型
            res.put("otype", OrderDataUtils.getOrderType(MapUtils.getString(map, "otype")));
            //订单商品数量
            res.put("goodsNum", MapUtils.getString(map, "goodsNum"));
            //优惠后的金额
            res.put("after_discount", MapUtils.getString(map, "after_discount"));
            //支付方式
            String payType = MapUtils.getString(map, "pay");
            res.put("pay", payType);
            if (StringUtils.isNotEmpty(payType)) {
                PaymentModel paymentModel = new PaymentModel();
                paymentModel.setClass_name(payType);
                paymentModel = paymentModelMapper.selectOne(paymentModel);
                if (paymentModel != null) {
                    payType = paymentModel.getName();
                }
            }
            res.put("payName", payType);
            res.put("userId", MapUtils.getString(map, "user_id"));
            res.put("userName", MapUtils.getString(map, "user_name"));
            //获取物流信息
            String wuliuStr = "";
            Integer exId = MapUtils.getInteger(map, "express_id");
            ExpressModel expressModel = expressModelMapper.selectByPrimaryKey(exId);
            if (expressModel != null) {
                wuliuStr = String.format("%s(%s)", MapUtils.getString(map, "courier_num"), expressModel.getKuaidi_name());
            }
            res.put("expressStr", wuliuStr);
            res.put("mobile", MapUtils.getString(map, "mobile"));
            String addressInfo = MapUtils.getString(map, "address");
            res.put("address", addressInfo);
            String sheng = MapUtils.getString(map, "sheng"), shi = MapUtils.getString(map, "shi"), xian = MapUtils.getString(map, "xian");
            res.put("addressInfo", sheng + shi + xian + addressInfo);
            res.put("courier_num", MapUtils.getString(map, "courier_num"));
            res.put("freight", MapUtils.getString(map, "freight"));
            res.put("shopName", MapUtils.getString(map, "shopName"));

            resultList.add(res);
        } catch (Exception e) {
            logger.error("订单列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "pcMchOrderIndex");
        }
    }

    @Override
    public Map<String, Object> aMchOrderIndex(MchOrderIndexVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            int paymentNum = 0;
            int sendNum = 0;
            int retyrnNum = 0;

            //订单明细 一对多
            List<Map<String, Object>> orderDetailList = new ArrayList<>();
            //用于暂存订单主表信息 订单分组
            Map<String, Map<String, Object>> orderMap = new HashMap<>(16);

            //查询参数列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("mch_id", vo.getShopId());
            if (StringUtils.isNotEmpty(vo.getOrderHeadrType())) {
                parmaMap.put("orderType", vo.getOrderHeadrType());
            }
            if (StringUtils.isNotEmpty(vo.getOrderHeadrType())) {
                parmaMap.put("orderStauts", vo.getOrderStauts());
            }

            //统计当前店铺未付款数量
            Map<String, Object> unpaidParmaMap = new HashMap<>(16);
            unpaidParmaMap.putAll(parmaMap);
            List<String> statusList = new ArrayList<>();
            statusList.add(String.valueOf(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID));
            unpaidParmaMap.put("statusList", statusList);
            //统计当前店铺 未发货并且是配送 或者 待收货并且是自提 的数量
            Map<String, Object> notFinishedParmaMap = new HashMap<>(16);
            notFinishedParmaMap.putAll(parmaMap);
            notFinishedParmaMap.put("statusType", 2);
            //统计当前店铺售后订单 审核中、同意并让用户寄回、用户已快递 的数量
            Map<String, Object> returnOrderParmaMap = new HashMap<>(16);
            returnOrderParmaMap.putAll(parmaMap);
            List<String> rTypeList = new ArrayList<>();
            rTypeList.add(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_EXAMEWAIT_STATUS.toString());
            rTypeList.add(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_AGREE_REBACK.toString());
            rTypeList.add(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_USER_DELIVERED.toString());
            returnOrderParmaMap.put("rTypeList", rTypeList);

            if (StringUtils.isNotEmpty(vo.getKeyword())) {
                unpaidParmaMap.put("likeOrderno", vo.getKeyword());
                notFinishedParmaMap.put("likeOrderno", vo.getKeyword());
                returnOrderParmaMap.put("likeOrderno", vo.getKeyword());
                parmaMap.put("likeOrderno", vo.getKeyword());
            }
            paymentNum = orderModelMapper.countOrdersNumDynamic(unpaidParmaMap);
            sendNum = orderModelMapper.countOrdersNumDynamic(notFinishedParmaMap);
            retyrnNum = returnOrderModelMapper.countRturnOrderNumDynamic(returnOrderParmaMap);


            //判断是否为售后类型
            if (!"return".equals(vo.getOrderType())) {
                if ("payment".equals(vo.getOrderType())) {
                    //未付款
                    statusList = new ArrayList<>();
                    statusList.add(String.valueOf(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID));
                    parmaMap.put("statusList", statusList);
                } else if ("send".equals(vo.getOrderType())) {
                    //未发货
                    parmaMap.put("statusType", 2);
                }
                //平台活动id
                if (vo.getPlatformActivitiesId() != null) {
                    parmaMap.put("platform_activities_id", vo.getPlatformActivitiesId());
                }

                //根据用户id和前台参数,查询订单表 (id、订单号、订单价格、添加时间、订单状态、优惠券id)
                List<String> recycleList = new ArrayList<>();
                recycleList.add(String.valueOf(OrderModel.RECYCLE_SHOW));
                recycleList.add(String.valueOf(OrderModel.RECYCLE_USER));
                parmaMap.put("store_id", vo.getStoreId());
                parmaMap.put("mch_id", vo.getShopId());
                if (!StringUtils.isEmpty(vo.getKeyword())) {
                    parmaMap.put("likeOrderno", vo.getKeyword());
                }
                parmaMap.put("recycleList", recycleList);
                parmaMap.put("pageNo", vo.getPageNo());
                parmaMap.put("pageSize", vo.getPageSize());
                List<Map<String, Object>> orderMapList = orderModelMapper.getOrdersNumDynamic(parmaMap);
                for (Map<String, Object> map : orderMapList) {
                    //主键id
                    int oid = Integer.parseInt(map.get("id").toString());
                    //订单号
                    String orderno = map.get("sNo").toString();
                    //订单类型
                    String orderHeader = map.get("otype").toString();
                    //订单总状态
                    int status = Integer.parseInt(map.get("status").toString());
                    //优惠满减金额
                    BigDecimal reducePrice = new BigDecimal(map.get("reduce_price").toString());
                    //优惠卷金额
                    BigDecimal couponPrice = new BigDecimal(map.get("coupon_price").toString());
                    //下单日期
                    String orderDate = map.get("add_time").toString();
                    orderDate = DateUtil.dateFormate(orderDate, GloabConst.TimePattern.YMD2);
                    map.put("time", orderDate);
                    //店铺id集
                    String mchListStr = map.get("mch_id").toString();
                    List<String> mchList = Arrays.asList(StringUtils.trim(mchListStr, ",").split(","));
                    //订单价格
                    BigDecimal orderAmt = new BigDecimal(map.get("spz_price").toString());
                    //售后状态 1=售后未结束 2=全部在售后且未结束
                    int saleType = 0;

                    map.put("sale_type", 0);
                    //判断有无订单售后未结束
                    Map<String, Object> returnNotFinishedParamMap = new HashMap<>(16);
                    returnNotFinishedParamMap.put("store_id", vo.getStoreId());
                    returnNotFinishedParamMap.put("sNo", orderno);
                    //rType=2 订单是否全在售后且未结束
                    returnNotFinishedParamMap.put("rType", 2);
                    int count = returnOrderModelMapper.countRturnOrderNumDynamic(returnNotFinishedParamMap);
                    if (count > 0) {
                        saleType = 1;
                    }
                    //判断订单是否全在售后且未结束  rType=1 未完成售后的状态
                    returnNotFinishedParamMap.put("rType", 1);
                    int count1 = returnOrderModelMapper.countRturnOrderNumDynamic(returnNotFinishedParamMap);
                    if (count1 == count) {
                        saleType = 2;
                    }
                    map.put("sale_type", saleType);

                    //跨店订单订单标识  跨店订单 并且 未完成的
                    boolean orderStatus = mchList.size() <= 1 || status != 0;

                    //获取该订单的详情信息
                    Map<String, Object> orderDetailParmaMap = new HashMap<>(16);
                    orderDetailParmaMap.put("store_id", vo.getStoreId());
                    orderDetailParmaMap.put("orderno", orderno);
                    List<Map<String, Object>> orderGoodsInfoList = orderDetailsModelMapper.getOrderDetailByGoodsInfo(orderDetailParmaMap);

                    //订单明细状态如果全部都是一个状态，则修改主表状态
                    Set<Integer> rStatusList = new TreeSet<>();
                    //商品总价
                    BigDecimal goodsAmt = new BigDecimal("0");
                    //运费总价
                    BigDecimal freightAmt = new BigDecimal("0");
                    for (Map<String, Object> orderGoods : orderGoodsInfoList) {
                        int rStatus = Integer.parseInt(orderGoods.get("r_status").toString());
                        //订单详情状态
                        rStatusList.add(rStatus);
                        //图片处理
                        String imgUrl = orderGoods.get("img") + "";
                        if (StringUtils.isEmpty(imgUrl)) {
                            imgUrl = orderGoods.get("imgurl") + "";
                        }
                        imgUrl = publiceService.getImgPath(imgUrl, vo.getStoreId());
                        orderGoods.put("imgurl", imgUrl);

                        //处理跨店订单 且未支付
                        if (!orderStatus) {
                            //当前商品价格
                            BigDecimal currentGoodsAmt = new BigDecimal(orderGoods.get("p_price").toString());
                            //当前商品数量
                            BigDecimal currentGoodsNum = new BigDecimal(orderGoods.get("num").toString());
                            //当前商品运费价格
                            BigDecimal currentFreightAmt = new BigDecimal(orderGoods.get("freight").toString());

                            goodsAmt = goodsAmt.add(currentGoodsAmt).multiply(currentGoodsNum);
                            freightAmt = freightAmt.add(currentFreightAmt);
                        } else {
                            int isDistribution = Integer.parseInt(orderGoods.get("is_distribution").toString());
                            orderGoods.put("otype", orderHeader);
                            //是否是分销订单
                            if (isDistribution == 1) {
                                orderGoods.put("otype", DictionaryConst.OrdersType.ORDERS_HEADER_FX);
                            }
                        }
                    }

                    if (!orderStatus) {
                        // 该店铺商品总价 除以 整个订单商品总价 乘以 优惠的满减金额
                        BigDecimal reduceSumPrice = goodsAmt.divide(orderAmt, 2, BigDecimal.ROUND_HALF_UP).multiply(reducePrice);
                        // 该店铺商品总价 除以 整个订单商品总价 乘以 优惠的优惠券金额
                        BigDecimal couponSumPrice = goodsAmt.divide(orderAmt, 2, BigDecimal.ROUND_HALF_UP).multiply(couponPrice);
                        map.put("reduce_price", reduceSumPrice);
                        map.put("coupon_price", couponSumPrice);

                        //计算会员特惠 折扣*(商品总价格-满减价格-优惠卷价格)+运费价格
                        BigDecimal gradeRate = new BigDecimal(publicMemberService.getMemberGradeRate(orderHeader, vo.getUserId(), vo.getStoreId()) + "");
                        //商品最后价格 (商品总价格-满减价格-优惠卷价格)
                        BigDecimal goodsPrice = new BigDecimal(goodsAmt.toString());
                        goodsPrice = goodsPrice.subtract(reduceSumPrice).subtract(couponPrice);
                        //会员特惠价格 折扣*(商品总价格-满减价格-优惠卷价格)+运费价格
                        gradeRate = gradeRate.multiply(goodsPrice).add(freightAmt);

                        map.put("z_price", gradeRate);
                    }
                    map.put("list", orderGoodsInfoList);
                    map.put("order_status", orderStatus);
                    //全部是一个状态,修改主表状态
                    if (rStatusList.size() == 1) {
                        //订单关闭则不处理
                        int uStatus = rStatusList.iterator().next();
                        if (uStatus != DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE) {
                            //明细订单状态和总状态一样则不处理
                            if (uStatus != status) {
                                OrderModel orderModel = new OrderModel();
                                orderModel.setId(oid);
                                orderModel.setStatus(uStatus);
                                count = orderModelMapper.updateByPrimaryKeySelective(orderModel);
                                if (count < 1) {
                                    logger.info("订单状态修改失败 参数:" + JSON.toJSONString(orderModel));
                                }
                            }
                        }
                    }
                    //一对多 res节点
                    orderDetailList.add(map);
                }
            } else {
                //售后流程
                Map<String, Object> returnParmaMap = new HashMap<>(16);
                returnParmaMap.put("store_id", vo.getStoreId());
                returnParmaMap.put("mch_id", vo.getShopId());
                if (!StringUtils.isEmpty(vo.getKeyword())) {
                    returnParmaMap.put("likeOrderno", vo.getKeyword());
                }
                returnParmaMap.put("reTimeSort", DataUtils.Sort.DESC.toString());
                returnParmaMap.put("reTypeSort", DataUtils.Sort.ASC.toString());
                returnParmaMap.put("newReturn", "newReturn");
                returnParmaMap.put("pageNo", vo.getPageNo());
                returnParmaMap.put("pageSize", vo.getPageSize());
                List<Map<String, Object>> returnOrderMapList = returnOrderModelMapper.getReturnOrderByGoodsInfo(returnParmaMap);

                for (Map<String, Object> map : returnOrderMapList) {
                    map.put("pid", MapUtils.getInteger(map, "goodsId"));
                    //订单号
                    String orderno = map.get("sNo").toString();
                    //退款类型
                    int reType = Integer.parseInt(map.get("re_type").toString());
                    //文字说明
                    String typeText;

                    //图片处理
                    String imgUrl = map.get("img") + "";
                    if (StringUtils.isEmpty(imgUrl)) {
                        imgUrl = map.get("imgurl") + "";
                    }
                    imgUrl = publiceService.getImgPath(imgUrl, vo.getStoreId());
                    map.put("imgurl", imgUrl);

                    switch (reType) {
                        case ReturnOrderModel.RE_TYPE_RETURN_REFUND:
                            typeText = "退货退款";
                            break;
                        case ReturnOrderModel.RE_TYPE_REFUND:
                            typeText = "仅退款";
                            break;
                        default:
                            typeText = "换货";
                            break;
                    }
                    map.put("type", typeText);

                    Map<String, Object> orderInfo;
                    //获取订单主表信息
                    Map<String, Object> orderParmaMap = new HashMap<>(16);
                    orderParmaMap.put("store_id", vo.getStoreId());
                    orderParmaMap.put("orderno", orderno);
                    List<Map<String, Object>> orderMapList = orderModelMapper.getOrdersNumDynamic(orderParmaMap);
                    if (orderMapList != null && orderMapList.size() > 0) {
                        orderInfo = orderMapList.get(0);
                        int isDistribution = Integer.parseInt(map.get("is_distribution").toString());
                        map.put("otype", orderInfo.get("otype"));
                        if (isDistribution == 1) {
                            map.put("otype", DictionaryConst.OrdersType.ORDERS_HEADER_FX);
                        }
                        orderInfo.put("time", DateUtil.dateFormate(MapUtils.getString(map, "re_time"), GloabConst.TimePattern.YMD));
                        orderInfo.put("list", map);
                    } else {
                        logger.debug("【订单数据发生错误,订单号:{} 该订单不存在】", orderno);
                        continue;
                    }
                    //一对多 res节点
                    orderDetailList.add(orderInfo);
                }
            }
            //构造数据结构
            List<Map<String, Object>> data = new ArrayList<>();
            //暂存订单信息 时间分组
            Map<String, List<Map<String, Object>>> groupDateMap = new HashMap<>(16);
            for (Map<String, Object> map : orderDetailList) {
                String time = map.get("time").toString();
                //同一个时段合并
                List<Map<String, Object>> tempMap = new ArrayList<>();
                if (groupDateMap.containsKey(time)) {
                    tempMap = groupDateMap.get(time);
                }
                tempMap.add(map);
                groupDateMap.put(time, tempMap);
            }
            for (String time : groupDateMap.keySet()) {
                Map<String, Object> tempData = new HashMap<>(16);
                tempData.put("time", time);
                tempData.put("res", groupDateMap.get(time));
                data.add(tempData);
            }

            resultMap.put("list", DataUtils.mapSort(data, "time", DataUtils.Sort.DESC));
            resultMap.put("payment_num", paymentNum);
            resultMap.put("send_num", sendNum);
            resultMap.put("return_num", retyrnNum);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单列表（移动店铺端） 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "aMchOrderIndex");
        }
        return resultMap;
    }

    @Override
    public BigDecimal orderPayment(int storeId, String sNo, String tradeNo, String userId, String payType) throws LaiKeApiException {
        try {
            int row;
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(storeId);
            orderModel.setsNo(sNo);
            orderModel.setUser_id(userId);
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                throw new LaiKeApiException(PARAMATER_ERROR, "参数错误", "pay");
            }

            BigDecimal zPrice = orderModel.getZ_price();
            int selfLifting = orderModel.getSelf_lifting();

            Map<String, Object> conditionMap = Maps.newHashMap();
            conditionMap.put("zPrice", zPrice);
            conditionMap.put("payTime", new Date());
            conditionMap.put("storeId", storeId);
            conditionMap.put("sNo", sNo);
            conditionMap.put("pay", payType);
            if (!StringUtils.isEmpty(tradeNo)) {
                conditionMap.put("tradeNo", tradeNo);
            }

            // 默认待发货状态
            int status = DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT;
            if (selfLifting == 1) {
                //自提商品付款后直接待收货
                status = DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED;
            }

            conditionMap.put("status", status);
            row = orderModelMapper.wallectPayUpdateOrder(conditionMap);
            if (row < 1) {
                throw new LaiKeApiException(API_OPERATION_FAILED, "支付失败", "pay");
            }

            row = orderDetailsModelMapper.updateOrderDetailsStatus(storeId, sNo, status);
            if (row < 1) {
                throw new LaiKeApiException(API_OPERATION_FAILED, "支付失败", "pay");
            }

            return zPrice;
        } catch (Exception e) {
            logger.error("钱包支付 异常", e);
            throw new LaiKeApiException("", "钱包支付失败", "pay");
        }
    }

    @Override
    public void frontDelivery(FrontDeliveryVo vo) throws LaiKeApiException {
        try {
            //获取订单信息
            OrderModel orderModel = new OrderModel();
            orderModel.setsNo(vo.getsNo());
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel != null) {
                //修改订单明细状态为已发货
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("orderno", vo.getsNo());
                parmaMap.put("rstatus", ORDERS_R_STATUS_CONSIGNMENT);
                String[] ids = vo.getOrderDetailsId().split(SplitUtils.DH);
                parmaMap.put("orderDetailIds", ids);
                //快递公司id
                if (vo.getExpressId() != null) {
                    parmaMap.put("express_id", vo.getExpressId());
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请选择快递公司", "frontDelivery");
                }
                //快递单号
                if (!StringUtils.isEmpty(vo.getCourierNum())) {
                    if (vo.getCourierNum().length() >= 10 && vo.getCourierNum().length() <= 20) {
                        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                        orderDetailsModel.setExpress_id(vo.getExpressId());
                        orderDetailsModel.setCourier_num(vo.getCourierNum());
                        int exnum = orderDetailsModelMapper.selectCount(orderDetailsModel);
                        if (exnum > 0) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "发货时,快递单号已存在", "frontDelivery");
                        }
                        parmaMap.put("courier_num", vo.getCourierNum());
                    } else {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "发货时,快递单号输入错误", "frontDelivery");
                    }
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "发货时,未填写快递单号", "frontDelivery");
                }
                parmaMap.put("deliver_time", new Date());
                parmaMap.put("r_status", DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED);
                int count = orderDetailsModelMapper.updateByOrdernoDynamic(parmaMap);
                if (count < 1) {
                    logger.info("发货订单失败 参数:" + JSON.toJSONString(parmaMap));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据异常", "frontDelivery");
                }
                //获取当前订单下的商品是否都已发货
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setR_sNo(vo.getsNo());
                orderDetailsModel.setR_status(ORDERS_R_STATUS_CONSIGNMENT);
                int num = orderDetailsModelMapper.selectCount(orderDetailsModel);
                //修改订单主表状态 只有明细全部已发货才修改
                if (num == 0) {
                    OrderModel updateOrder = new OrderModel();
                    updateOrder.setId(orderModel.getId());
                    updateOrder.setStatus(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED);
                    count = orderModelMapper.updateByPrimaryKeySelective(updateOrder);
                    if (count < 1) {
                        logger.info("订单发货状态修改失败 参数:" + JSON.toJSONString(updateOrder));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "frontDelivery");
                    }
                }
                //站内推送退款信息
                SystemMessageModel systemMessageSave = new SystemMessageModel();
                systemMessageSave.setType(1);
                systemMessageSave.setSenderid("admin");
                systemMessageSave.setStore_id(orderModel.getStore_id());
                systemMessageSave.setRecipientid(orderModel.getUser_id());
                systemMessageSave.setTitle("系统消息");
                systemMessageSave.setContent("您购买的商品已经在赶去见您的路上啦!");
                systemMessageSave.setTime(new Date());
                systemMessageModelMapper.insertSelective(systemMessageSave);
                //TODO 【微信推送】暂时不做
                //$pusher->pushMessage($user_id, $db, $msg_title, $msg_content, $store_id, $user_id);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "订单不存在", "frontDelivery");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("发货 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "frontDelivery");
        }
    }

    @Override
    public Map<String, Object> okOrder(int storeId, String accessId, String orderno, Integer rType) {
        Map<String, Object> ret = Maps.newHashMap();
        try {
            // 根据授权id,查询用户id
            User user = RedisDataTool.getRedisUserCache(accessId, redisUtil, true);
            // 获取信息
            OrderModel orderModel = new OrderModel();
            orderModel.setsNo(orderno);
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                logger.error("订单不存在！");
                throw new LaiKeApiException(RECEIVING_FAILED, "确认收货失败", "okOrder");
            }

            // 售后商品回寄收货
            if (rType != null && rType == 11) {
                //查询订单信息
                ret.put("code", 200);
                int row = orderDetailsModelMapper.updateOkOrderDetails(storeId, orderModel.getId());
                if (row < 1) {
                    logger.error("确认收货失败！");
                    throw new LaiKeApiException(RECEIVING_FAILED, "确认收货失败", "okOrder");
                } else {
                    return ret;
                }
            }
            String userId = user.getUser_id();
            String sNo = orderModel.getsNo();
            //积分
            Integer allow = orderModel.getAllow();
            allow = allow == null ? 0 : allow;
            //订单金额
            BigDecimal zPrice = orderModel.getZ_price();
            //最终可到店铺账上的金额
            BigDecimal realMoney = BigDecimal.ZERO;
            OrderDetailsModel orderDetails = new OrderDetailsModel();
            orderDetails.setStore_id(storeId);
            orderDetails.setUser_id(userId);
            orderDetails.setR_sNo(orderModel.getsNo());
            List<OrderDetailsModel> orderDetailsModels = orderDetailsModelMapper.select(orderDetails);
            if (CollectionUtils.isEmpty(orderDetailsModels)) {
                throw new LaiKeApiException(PARAMETER_VAL_ERROR, "参数错误", "okOrder");
            }

            Map<String, Object> param = new HashMap<>(16);
            for (OrderDetailsModel orderDetailsModel : orderDetailsModels) {
                int orderDetailsId = orderDetailsModel.getId();
                Integer rStatus = orderDetailsModel.getR_status();
                // 待收货
                if (rStatus == ORDER_UNRECEIVE) {
                    //计算实际可到账的金额 如果是积分,优惠金额则是积分
                    BigDecimal afterDiscount = orderDetailsModel.getAfter_discount();
                    if (orderno.contains(DictionaryConst.OrdersType.ORDERS_HEADER_IN)) {
                        afterDiscount = BigDecimal.ZERO;
                    }
                    realMoney = realMoney.add(afterDiscount).add(orderDetailsModel.getFreight());
                    param.put("r_status", ORDER_FINISH);
                    param.put("arrive_time", new Date());
                    param.put("storeId", storeId);
                    param.put("detailId", orderDetailsId);
                    param.put("orderno", sNo);
                    int row = orderDetailsModelMapper.updateByOrdernoDynamic(param);
                    if (row < 0) {
                        throw new LaiKeApiException(RECEIVING_FAILED, "确认收货失败", "okOrder");
                    }
                }
            }
            //确认收货积分商品赠送积分
            if (orderModel.getOtype().equals(DictionaryConst.OrdersType.ORDERS_HEADER_IN)) {
                publicMemberService.memberSettlement(storeId, userId, sNo, zPrice, 0);
                //刷新缓存
                RedisDataTool.refreshRedisUserCache(accessId, user, redisUtil);
            }

            //订单完成
            int row = orderModelMapper.updateOrderStatus(storeId, sNo, userId, ORDER_FINISH);
            if (row < 0) {
                throw new LaiKeApiException(RECEIVING_FAILED, "确认收货失败", "okOrder");
            }

            // 已收货
            int mchId = Integer.parseInt(StringUtils.trim(orderModel.getMch_id(), SplitUtils.DH));

            //确认收货到店铺账上的金额不能是订单金额  一个订单多个商品,一个退款(少退),其余的确认收货,少退的部分还在订单金额里面,但实际上这笔钱已经结算到店铺可提现金额里面了
            publicMchService.clientConfirmReceipt(storeId, mchId, sNo, realMoney, new BigDecimal(allow));
            ret.put("code", 200);
            return ret;
        } catch (Exception e) {
            logger.error("确认收货 异常", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "okOrder");
        }

    }

    @Override
    public void adminDelivery(MainVo vo, Integer exId, String exNo, String orderDetailIds) throws LaiKeApiException {
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            int row;
            //订单明细集
            List<String> orderListIds = Arrays.asList(orderDetailIds.split(","));
            //获取订单信息
            int storeId = vo.getStoreId();
            int len = orderListIds.size();
            //是全部已经发货,如果详单都被发货,则同意修改订单状态为待收货
            boolean batchSend = false;
            //获取当前订单号
            String orderNo;
            OrderDetailsModel orderDetailsOld = new OrderDetailsModel();
            orderDetailsOld.setStore_id(vo.getStoreId());
            orderDetailsOld.setId(Integer.parseInt(orderListIds.get(0)));
            orderDetailsOld = orderDetailsModelMapper.selectOne(orderDetailsOld);
            if (orderDetailsOld == null) {
                throw new LaiKeApiException(DATA_NOT_EXIST, "详单不存在");
            }
            orderNo = orderDetailsOld.getR_sNo();
            OrderDetailsModel orderDetailsCount = new OrderDetailsModel();
            orderDetailsCount.setStore_id(vo.getStoreId());
            orderDetailsCount.setR_sNo(orderNo);
            //统计当前订单下有多少商品
            int orderGoodsNum = orderDetailsModelMapper.selectCount(orderDetailsCount);
            //统计当前发货次数
            orderDetailsCount.setR_status(ORDERS_R_STATUS_DISPATCHED);
            int curSendPos = orderDetailsModelMapper.selectCount(orderDetailsCount);
            //是否已经全部发货
            if (orderGoodsNum == len + curSendPos) {
                //批量发货
                batchSend = true;
            }
            //订单明细发货
            for (String id : orderListIds) {
                //修改订单明细 修改明细订单状态为待收货
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setId(Integer.parseInt(id));
                orderDetailsModel.setR_status(ORDERS_R_STATUS_DISPATCHED);
                orderDetailsModel.setExpress_id(exId);
                orderDetailsModel.setCourier_num(exNo);
                orderDetailsModel.setDeliver_time(new Date());
                row = orderDetailsModelMapper.updateByPrimaryKeySelective(orderDetailsModel);
                if (row < 1) {
                    logger.debug("订单{} 发货失败,明细id{}", orderNo, id);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "发货失败");
                }
            }
            //是否都已经完成了发货,完成了发货则修改订单全部状态为待收货
            if (batchSend) {
                row = orderModelMapper.updateOrderStatusByOrderNo(vo.getStoreId(), ORDERS_R_STATUS_DISPATCHED, orderNo);
                if (row < 1) {
                    logger.debug("订单{} 发货失败,状态修改失败", orderNo);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "发货失败");
                }
            }

            AdminRecordModel adminRecordModel = new AdminRecordModel();
            adminRecordModel.setStore_id(storeId);
            adminRecordModel.setAdmin_name(adminModel.getName());
            adminRecordModel.setEvent("操作订单号为 " + orderNo + " 的订单发货 ");
            publicAdminRecordService.adminRecode(adminRecordModel);
            // todo 消息订阅 、app 推送 、短信、站内消息
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("发货异常： ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "adminDelivery");
        }
    }


    @Override
    public Map<String, Object> storeOrderDetails(int storeId, String ordernno) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //订单基本信息
            Map<String, Object> orderInfoMap = new HashMap<>(16);
            //收货人信息
            Map<String, Object> receivingInfoMap = new HashMap<>(16);
            //商品信息
            List<Map<String, Object>> goodsList = new ArrayList<>();
            //下单人折扣
            BigDecimal orderUserGrate = new BigDecimal("1");

            //获取订单信息
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(storeId);
            orderModel.setsNo(ordernno);
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                throw new LaiKeApiException(DATA_NOT_EXIST, "订单不存在");
            }
            if (orderModel.getGrade_rate().compareTo(BigDecimal.ZERO) > 0) {
                orderUserGrate = orderModel.getGrade_rate();
            }
            //店铺优惠金额
            BigDecimal mchDisAmt = orderModel.getCoupon_price();
            if (orderModel.getSubtraction_id() != null) {
                if (orderModel.getSubtraction_id() != null && orderModel.getSubtraction_id() == 0) {
                    BigDecimal dicPirce = orderModel.getPreferential_amount();
                    //优惠卷金额+优惠金额
                    mchDisAmt = mchDisAmt.add(dicPirce);
                }
            }
            orderInfoMap.put("mch_discount", mchDisAmt);
            orderInfoMap.put("preferential_amount", orderModel.getPreferential_amount());

            int shopId = StringUtils.stringParseInt(orderModel.getMch_id().split(",")[1]);
            //获取订单设置
            OrderConfigModal orderConfigModal = new OrderConfigModal();
            orderConfigModal.setStore_id(storeId);
            orderConfigModal = orderConfigModalMapper.selectOne(orderConfigModal);
            //获取提现发货时间间隔 默认七天
            Date remindSendDate = DateUtil.getAddDate(new Date(), 7);
            if (orderConfigModal != null && orderConfigModal.getRemind() != null && orderConfigModal.getRemind() > 0) {
                BigDecimal remmindDay = new BigDecimal(orderConfigModal.getRemind()).divide(new BigDecimal("24"), 2, BigDecimal.ROUND_HALF_UP);
                long remndHour = orderConfigModal.getRemind() % 24;
                remndHour = DateUtil.dateConversion(remndHour, DateUtil.TimeType.TIME);
                remindSendDate = DateUtil.getAddDate(new Date(), remmindDay.intValue());
                remindSendDate = DateUtil.getAddDateBySecond(remindSendDate, Integer.parseInt(Long.toString(remndHour)));
            }
            //进入订单详情把未读状态改成已读状态，已读状态的状态不变
            MessageLoggingModal messageLoggingUpdate = new MessageLoggingModal();
            messageLoggingUpdate.setIs_popup(1);
            messageLoggingUpdate.setRead_or_not(1);
            if (orderModel.getReadd() == 0) {
                //订单未读处理
                OrderModel orderUpdate = new OrderModel();
                orderUpdate.setId(orderModel.getId());
                orderUpdate.setReadd(1);
                if (orderModel.getDelivery_status() == 1) {
                    orderUpdate.setRemind(remindSendDate);
                }
                int row = orderModelMapper.updateByPrimaryKeySelective(orderUpdate);
                if (row < 1) {
                    logger.debug("{} 修改已读状态失败", ordernno);
                }
                //后台已读订单处理
                publiceService.messageUpdate(storeId, shopId, 1, ordernno, messageLoggingUpdate);
                publiceService.messageUpdate(storeId, shopId, 3, ordernno, messageLoggingUpdate);
                publiceService.messageUpdate(storeId, shopId, 5, ordernno, messageLoggingUpdate);
            }
            if (ORDERS_R_STATUS_COMPLETE == orderModel.getStatus()) {
                publiceService.messageUpdate(storeId, shopId, 6, ordernno, messageLoggingUpdate);
            } else if (ORDERS_R_STATUS_CLOSE == orderModel.getStatus()) {
                publiceService.messageUpdate(storeId, shopId, 4, ordernno, messageLoggingUpdate);
            }
            orderInfoMap.put("orderno", orderModel.getsNo());
            //获取订单状态
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("name", "订单状态");
            parmaMap.put("value", orderModel.getStatus());
            List<Map<String, Object>> orderStatusMap = dictionaryNameModelMapper.selectDynamic(parmaMap);
            if (orderStatusMap != null) {
                orderInfoMap.put("status", orderStatusMap.get(0).get("text"));
            }
            orderInfoMap.put("z_freight", orderModel.getZ_freight());
            orderInfoMap.put("source", orderModel.getSource());
            //支付方式
            String payType = "未知";
            PaymentModel paymentModel = new PaymentModel();
            paymentModel.setClass_name(orderModel.getPay());
            paymentModel = paymentModelMapper.selectOne(paymentModel);
            if (paymentModel != null) {
                payType = paymentModel.getName();
            }
            orderInfoMap.put("payType", payType);
            orderInfoMap.put("orderDate", DateUtil.dateFormate(orderModel.getAdd_time(), GloabConst.TimePattern.YMDHMS));
            orderInfoMap.put("payDate", DateUtil.dateFormate(orderModel.getPay_time(), GloabConst.TimePattern.YMDHMS));

            //发货时间
            String sendDate = null;
            //运费
            BigDecimal yunfei = new BigDecimal("0");
            //快递单号
            List<Map<String, Object>> kuaidiList = new ArrayList<>();
            //会员优惠金额
            BigDecimal youhuiVipPrice;
            //商品总价
            BigDecimal goodsPrice = new BigDecimal("0");

            //优惠方式
            String discountType = "";
            if (orderModel.getSubtraction_id() != null && orderModel.getSubtraction_id() > 0) {
                discountType = "满减";
            } else if (StringUtils.isNotEmpty(orderModel.getCoupon_id())) {
                orderModel.setCoupon_id(StringUtils.trim(orderModel.getCoupon_id(), SplitUtils.DH));
                String[] couponIds = orderModel.getCoupon_id().split(",");
                if (!"0".equals(couponIds[0])) {
                    discountType = "优惠券";
                }
            }

            //获取明细信息
            List<Map<String, Object>> orderDetailList = orderDetailsModelMapper.selectStoreOrderDetails(storeId, ordernno);
            for (Map<String, Object> map : orderDetailList) {
                Map<String, Object> goodsMap = new HashMap<>(16);
                if (StringUtils.isNotEmpty(sendDate) && map.containsKey("deliver_time")) {
                    sendDate = map.get("deliver_time").toString();
                }
                //获取快递信息
                if (map.containsKey("express_id")) {
                    String courierNo = map.get("courier_num") + "";
                    int exId = StringUtils.stringParseInt(map.get("express_id"));
                    ExpressModel expressModel = new ExpressModel();
                    expressModel.setId(exId);
                    expressModel = expressModelMapper.selectOne(expressModel);
                    if (expressModel != null) {
                        Map<String, Object> kuaidiMap = new HashMap<>(16);
                        kuaidiMap.put("kuaidi_name", expressModel.getKuaidi_name());
                        kuaidiMap.put("kuaidi_no", courierNo);
                        kuaidiList.add(kuaidiMap);
                    }
                }
                //图片处理
                String goodsImgUrl = MapUtils.getString(map, "img");
                goodsImgUrl = publiceService.getImgPath(goodsImgUrl, storeId);

                BigDecimal yf = new BigDecimal(MapUtils.getString(map, "freight"));
                BigDecimal needNum = new BigDecimal(map.get("num").toString());
                BigDecimal price = new BigDecimal(map.get("p_price").toString());
                BigDecimal orderAmt = price.multiply(needNum);
                goodsPrice = goodsPrice.add(orderAmt);
                yunfei = yunfei.add(yf);

                goodsMap.put("goodsName", map.get("p_name"));
                goodsMap.put("goodsId", orderModel.getPid());
                goodsMap.put("goodsImgUrl", goodsImgUrl);
                goodsMap.put("attrId", map.get("sid"));
                goodsMap.put("size", map.get("size"));
                goodsMap.put("num", needNum);
                goodsMap.put("price", price);
                //小计
                goodsMap.put("subtotal", orderAmt);

                goodsList.add(goodsMap);
            }
            orderInfoMap.put("sendDate", sendDate);
            orderInfoMap.put("kuaidiList", kuaidiList);
            orderInfoMap.put("arriveDate", orderModel.getArrive_time());
            orderInfoMap.put("userid", orderModel.getUser_id());
            User user = new User();
            user.setUser_id(orderModel.getUser_id());
            user = userBaseMapper.selectOne(user);
            String userName = "";
            if (user != null) {
                userName = user.getUser_name();
            }
            orderInfoMap.put("userName", userName);
            orderInfoMap.put("remarks", orderModel.getRemarks());
            //收货人信息
            receivingInfoMap.put("name", orderModel.getName());
            receivingInfoMap.put("tel", orderModel.getMobile());
            receivingInfoMap.put("address", orderModel.getName());
            receivingInfoMap.put("sheng", orderModel.getSheng());
            receivingInfoMap.put("shi", orderModel.getShi());
            receivingInfoMap.put("xian", orderModel.getXian());
            receivingInfoMap.put("remark", orderModel.getRemark());
            //商品总价
            resultMap.put("goodsPrice", goodsPrice);
            //订单金额
            resultMap.put("z_price", orderModel.getZ_price());
            //运费
            resultMap.put("freightPrice", yunfei);
            //会员折扣价
            youhuiVipPrice = goodsPrice.subtract(goodsPrice.multiply(orderUserGrate));
            resultMap.put("youhuiVipPrice", youhuiVipPrice);

            resultMap.put("status", orderModel.getStatus());
            //优惠方式
            resultMap.put("discount_type", discountType);
            resultMap.put("pay_price", orderModel.getZ_price());
            //订单信息
            resultMap.put("orderInfo", orderInfoMap);
            //收货人信息
            resultMap.put("receivingInfo", receivingInfoMap);
            //商品信息
            resultMap.put("goodsList", goodsList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单详情(后台、PC店铺) 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "storeOrderDetails");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> mchOrderDetails(MchOrderDetailVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("orderno", vo.getsNo());
            parmaMap.put("mch_id", vo.getShopId());
            List<Map<String, Object>> orderDetailList = orderModelMapper.getOrdersNumDynamic(parmaMap);
            if (orderDetailList != null && orderDetailList.size() > 0) {
                for (Map<String, Object> order : orderDetailList) {
                    order.put("add_time", DateUtil.dateFormate(order.get("add_time") + "", GloabConst.TimePattern.YMDHMS));
                    //主键id
                    int id = Integer.parseInt(order.get("id").toString());
                    //店铺id
                    String mchIdListStr = order.get("mch_id").toString();
                    //订单号
                    String orderno = order.get("sNo").toString();
                    //订单金额
                    BigDecimal orderAmt = new BigDecimal(order.get("z_price").toString());
                    //商品总价
                    BigDecimal goodsAmt = new BigDecimal(order.get("spz_price").toString());
                    //满减金额
                    BigDecimal reduceAmt = new BigDecimal(order.get("reduce_price").toString());
                    //折扣
                    BigDecimal gradeRate = new BigDecimal(order.get("grade_rate").toString());
                    //优惠卷金额
                    BigDecimal couponAmt = new BigDecimal(order.get("coupon_price").toString());
                    //订单状态
                    int status = Integer.parseInt(order.get("status").toString());
                    //店铺id
                    String[] mchIdList = StringUtils.trim(mchIdListStr, ",").split(",");
                    //满减活动ID
                    Integer subtractionId = StringUtils.stringParseInt(order.get("coupon_id") + "");
                    //会员的等级折扣
                    BigDecimal greadeRate = new BigDecimal(order.get("grade_rate").toString());
                    //优惠卷金额
                    BigDecimal mchCouponPrice = new BigDecimal(order.get("coupon_price").toString());
                    //是否自提
                    int selfLifting = StringUtils.stringParseInt(order.get("self_lifting").toString());
                    //优惠金额
                    BigDecimal preferentialAmount = new BigDecimal(order.get("reduce_price").toString());

                    if (greadeRate.doubleValue() > 0 && (subtractionId != null && subtractionId != 0)) {
                        mchCouponPrice = mchCouponPrice.subtract(preferentialAmount);
                    }
                    //订单备注处理 getUnserializeObj
                    String remarks = order.get("remarks") + "";
                    if (StringUtils.isEmpty(remarks)) {
                        Map<String, Object> remarksMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(remarks));
                        if (remarksMap != null) {
                            for (String key : remarksMap.keySet()) {
                                if (StringUtils.isEmpty(key)) {
                                    remarks = remarksMap.get(key).toString();
                                }
                            }
                        } else {
                            remarks = "";
                        }
                    } else {
                        remarks = "";
                    }
                    //判断有无订单售后未结束
                    int saleType = 0;
                    parmaMap.clear();
                    parmaMap.put("store_id", vo.getStoreId());
                    parmaMap.put("sNo", vo.getsNo());
                    parmaMap.put("rType", 1);
                    int count = returnOrderModelMapper.countRturnOrderNumDynamic(parmaMap);
                    if (count > 0) {
                        saleType = 1;
                    }
                    //判断订单是否全在售后且未结束
                    parmaMap.clear();
                    parmaMap.put("store_id", vo.getStoreId());
                    parmaMap.put("orderno", vo.getsNo());
                    parmaMap.put("notStatus", DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE);
                    int returnOrderNum = orderDetailsModelMapper.countOrderDetailDynamic(parmaMap);
                    if (returnOrderNum == count) {
                        saleType = 2;
                    }


                    //是否未读,未读则标记已读状态
                    OrderModel orderModel = new OrderModel();
                    orderModel.setId(id);
                    orderModel.setReadd(1);
                    count = orderModelMapper.updateByPrimaryKeySelective(orderModel);
                    if (count < 1) {
                        logger.info("订单标记已读失败 参数" + JSON.toJSONString(orderModel));
                    }
                    //是否为自提 则联系信息显示店铺信息
                    if (selfLifting == 1) {
                        //获取店铺信息
                        MchStoreModel mchStoreModel = new MchStoreModel();
                        mchStoreModel.setStore_id(vo.getStoreId());
                        mchStoreModel.setMch_id(vo.getShopId());
                        mchStoreModel.setIs_default(1);
                        mchStoreModel = mchStoreModelMapper.selectOne(mchStoreModel);
                        if (mchStoreModel != null) {
                            order.put("name", mchStoreModel.getName());
                            order.put("mobile", mchStoreModel.getMobile());
                            order.put("address", mchStoreModel.getAddress());
                        }
                    }
                    //获取下单人名称
                    String clientUserName = "";
                    String clientUserId = order.get("user_id") + "";
                    User user = new User();
                    user.setUser_id(clientUserId);
                    user = userBaseMapper.selectOne(user);
                    if (user != null) {
                        clientUserName = new String(user.getUser_name().getBytes(StandardCharsets.UTF_8));
                    }
                    order.put("user_name", clientUserName);
                    //商品价格处理
                    parmaMap.clear();
                    parmaMap.put("store_id", vo.getStoreId());
                    parmaMap.put("orderno", orderno);
                    parmaMap.put("mch_id", vo.getShopId());
                    List<Map<String, Object>> orderDetailInfoList = orderDetailsModelMapper.getOrderDetailByGoodsInfo(parmaMap);
                    //运费总价
                    BigDecimal freightAmt = new BigDecimal("0");
                    //商品总价
                    for (Map<String, Object> orderGoods : orderDetailInfoList) {
                        //图片处理
                        String imgUrl = orderGoods.get("img") + "";
                        if (StringUtils.isEmpty(imgUrl)) {
                            imgUrl = orderGoods.get("imgurl") + "";
                        }
                        imgUrl = publiceService.getImgPath(imgUrl, vo.getStoreId());
                        orderGoods.put("pic", imgUrl);
                        //当前商品价格
                        BigDecimal currentGoodsAmt = new BigDecimal(orderGoods.get("p_price").toString());
                        //当前商品数量
                        BigDecimal currentGoodsNum = new BigDecimal(orderGoods.get("num").toString());
                        //当前商品运费价格
                        BigDecimal currentFreightAmt = new BigDecimal(orderGoods.get("freight").toString());

                        goodsAmt = currentGoodsAmt.multiply(currentGoodsNum);
                        freightAmt = freightAmt.add(currentFreightAmt);
                    }
                    order.put("list", orderDetailInfoList);
                    //跨店铺未付款订单标识
                    boolean flag = (mchIdList.length > 1 && status == 0);
                    order.put("order_status", !flag);

                    BigDecimal reduceSumPrice = new BigDecimal("0");
                    BigDecimal couponSumPrice = new BigDecimal("0");
                    if (orderAmt.doubleValue() > 0) {
                        // 该店铺商品总价 除以 整个订单商品总价 乘以 优惠的满减金额
                        reduceSumPrice = goodsAmt.divide(orderAmt, 2, BigDecimal.ROUND_HALF_UP).multiply(reduceAmt);
                        // 该店铺商品总价 除以 整个订单商品总价 乘以 优惠的优惠券金额
                        couponSumPrice = goodsAmt.divide(orderAmt, 2, BigDecimal.ROUND_HALF_UP).multiply(couponAmt);

                        //计算会员特惠 折扣*(商品总价格-满减价格-优惠卷价格)+运费价格
                        //商品最后价格 (商品总价格-满减价格-优惠卷价格)
                        BigDecimal goodsPrice = new BigDecimal(goodsAmt.toString());
                        goodsPrice = goodsPrice.subtract(reduceSumPrice).subtract(couponSumPrice);
                        //会员特惠价格 折扣*(商品总价格-满减价格-优惠卷价格)+运费价格
                        gradeRate = gradeRate.multiply(goodsPrice).add(freightAmt);
                    }

                    order.put("reduce_money", reduceSumPrice);
                    order.put("coupon_money", couponSumPrice);
                    order.put("z_price", orderAmt);
                    order.put("spz_price", goodsAmt);
                    order.put("z_freight", freightAmt);
                    order.put("grade_rate", gradeRate);
                    order.put("coupon_price", mchCouponPrice);
                    order.put("preferential_amount", preferentialAmount);
                    order.put("grade_rate_amount", gradeRate);
                    order.put("remarks", remarks);
                    order.put("sale_type", saleType);

                    resultMap.put("list", order);
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "订单不存在", "mchOrderDetails");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取订单明细 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchOrderDetails");
        }
        return resultMap;
    }

    @Override
    public void updateOrderState(int storeId, String orderno) throws LaiKeApiException {
        try {
            //获取订单明细
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setStore_id(storeId);
            orderDetailsModel.setR_sNo(orderno);
            List<OrderDetailsModel> orderDetailsModelList = orderDetailsModelMapper.select(orderDetailsModel);
            Set<Integer> orderStateList = new HashSet<>();
            for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                orderStateList.add(orderDetails.getR_status());
            }
            //判断订单商品是否都是同一个状态
            if (orderStateList.size() == 1) {
                //获取主表状态
                OrderModel orderModel = new OrderModel();
                orderModel.setsNo(orderno);
                orderModel = orderModelMapper.selectOne(orderModel);
                if (orderModel != null) {
                    if (orderStateList.contains(orderModel.getStatus())) {
                        logger.debug("订单商品状态和订单总状态都处于统一状态,无需修改");
                    } else {
                        //修订主表状态
                        OrderModel updateOrder = new OrderModel();
                        updateOrder.setId(orderModel.getId());
                        updateOrder.setStatus(orderStateList.iterator().next());
                        //如果都是关闭状态,则标记订单已经结算
                        if (orderStateList.contains(ORDERS_R_STATUS_CLOSE)) {
                            updateOrder.setSettlement_status(OrderDetailsModel.SETTLEMENT_TYPE_SETTLED);
                            //结算时间
                            updateOrder.setArrive_time(new Date());
                        }
                        int count = orderModelMapper.updateByPrimaryKeySelective(updateOrder);
                        if (count < 1) {
                            logger.debug("修订订单主表状态失败 参数:" + JSON.toJSONString(updateOrder));
                        } else {
                            logger.debug("修订订单主表状态成功 参数:" + JSON.toJSONString(updateOrder));
                        }
                    }
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "订单不存在", "updateOrderState");
                }
            } else {
                logger.debug("订单商品状态没有处于统一状态,无需修改");
            }
        } catch (Exception e) {
            logger.error("修改订单状态 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateOrderState");
        }
    }

    @Override
    public Map<String, Object> orderList(OrderVo orderVo, User user) throws LaiKeApiException {
        try {
            int storeId = orderVo.getStoreId();

            String keyword = orderVo.getKeyword();
            if (StringUtils.isEmpty(keyword)) {
                keyword = orderVo.getOrdervalue();
            }
            String orderType = orderVo.getQueryOrderType();
            //分页
            int start = orderVo.getPageNo();
            int orderFailure = orderVo.getOrderFailure();
            String company = orderVo.getCompany();
            String userId = user.getUser_id();
            // 订单状态 0待付款 1已付款/待发货 2待收货 5交易完成
            int[] orderStatus = {0, 1, 2, 5};
            // 角标 小红点
            Map<String, Object> resOrderMap = new HashMap<>();
            Map<String, Object> param = new HashMap<>();
            param.put("storeId", storeId);
            param.put("userId", userId);
            for (int status : orderStatus) {
                param.put("status", status);
                resOrderMap.put(String.valueOf(status), orderModelMapper.getUserCenterOrderNumByStatus(param));
            }
            param.remove("status");
            param.put("keyWord", keyword);
            param.put("orderType", orderType);
            if (StringUtils.isNotEmpty(orderVo.getOrderType())) {
                param.put("otype", orderVo.getOrderType());
            } else {
                //我的订单只显示普通订单
                param.put("otype", DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            }
            param.put("start", start);
            int orderListCount = orderModelMapper.getUserCenterOrderListCount(param);
            List<Map<String, Object>> dbOrderList = orderModelMapper.getUserCenterOrderList(param);
            List<Map<String, Object>> orderList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(dbOrderList)) {
                for (Map<String, Object> orderInfo : dbOrderList) {
                    Map<String, Object> info = orderInfo;
                    // 订单号
                    String sNo = DataUtils.getStringVal(orderInfo, "sNo");
                    // 店主ID
                    String mchId = DataUtils.getStringVal(orderInfo, "mch_id");
                    //判断是否为多店铺订单hgindex
                    String[] mchIdsArr = mchId.split(SplitUtils.DH);
                    if (mchIdsArr.length > 3) {
                        //是多店铺订单
                        info.put("ismch", true);
                    } else {
                        //不是多店铺订单
                        info.put("ismch", false);
                    }
                    info.put("shop_id", 0);
                    info.put("shop_name", "");
                    info.put("shop_logo", "");
                    if (StringUtils.isNotEmpty(mchId)) {
                        String mchid = com.laiketui.root.utils.tool.StringUtils.trim(mchId, SplitUtils.DH);
                        MchModel mchModel = mchModelMapper.getMchInfo(mchid, storeId);
                        if (mchModel != null) {
                            info.put("shop_id", mchModel.getId());
                            info.put("shop_name", mchModel.getName());
                            info.put("shop_logo", publiceService.getImgPath(mchModel.getLogo(), storeId));
                        }
                    }
                    // 根据订单号,查询订单详情
                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                    orderDetailsModel.setStore_id(storeId);
                    orderDetailsModel.setR_sNo(sNo);
                    List<OrderDetailsModel> orderDetailsModelList = orderDetailsModelMapper.select(orderDetailsModel);
                    info.put("list", orderDetailsModelList);
                    BigDecimal totalFreight = new BigDecimal(0);
                    Map<String, Object> detailsInfo = null;
                    JSONObject jsonObject = null;
                    JSONArray jsonArray = new JSONArray();
                    for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                        jsonObject = (JSONObject) JSONObject.toJSON(orderDetails);
                        detailsInfo = new HashMap<>();
                        BigDecimal freight = orderDetails.getFreight();
                        totalFreight = BigDecimal.valueOf(DoubleFormatUtil.format(totalFreight.doubleValue() + freight.doubleValue()));
                        Integer re_type = orderDetails.getR_type();
                        if (re_type != null && re_type > 0) {
                            detailsInfo.put("r_status", orderDetails.getR_status());
                            detailsInfo.put("p_id", orderDetails.getP_id());
                            detailsInfo.put("order_id", orderDetails.getId());
                            detailsInfo.put("id", orderDetails.getId());
                            detailsInfo.put("isreturn", 1);
                            int r_type = orderDetails.getR_type();
                            if (r_type == 0) {
                                detailsInfo.put("prompt", "审核中");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 1) {
                                ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
                                serviceAddressModel.setStore_id(storeId);
                                serviceAddressModel.setUid("admin");
                                serviceAddressModel.setType(1);
                                serviceAddressModel.setIs_default(1);
                                serviceAddressModel = serviceAddressModelMapper.selectOne(serviceAddressModel);
                                Map $buyer = new HashMap();
                                $buyer.put("tel", serviceAddressModel.getTel());
                                $buyer.put("name", serviceAddressModel.getName());
                                $buyer.put("address_xq", serviceAddressModel.getAddress_xq());
                                detailsInfo.put("prompt", "审核通过");
                                detailsInfo.put("buyer", $buyer);
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 2) {
                                detailsInfo.put("prompt", "审核拒绝");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 3) {
                                detailsInfo.put("prompt", "审核通过");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                                if (re_type == 3) {
                                    detailsInfo.put("prompt", "商品审核中");
                                }
                            } else if (r_type == 4) {
                                detailsInfo.put("prompt", "审核通过");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "退货退款");
                            } else if (r_type == 5) {
                                detailsInfo.put("prompt", "审核拒绝");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 8) {
                                detailsInfo.put("prompt", "审核拒绝");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 9) {
                                detailsInfo.put("prompt", "审核通过");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 11) {
                                detailsInfo.put("prompt", "退换中");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            } else if (r_type == 12) {
                                detailsInfo.put("prompt", "售后完成");
                                detailsInfo.put("buyer", "");
                                detailsInfo.put("return_state", "");
                            }
                            jsonObject.put("return", detailsInfo);
                            jsonArray.add(jsonObject);
                        }
                    }
                    List<OrderDetailsModel> tmpList = (List<OrderDetailsModel>) info.get("list");
                    if (tmpList.size() > 0) {
                        info.put("r_type", tmpList.get(0).getR_type());
                    } else {
                        info.put("r_type", "100");
                    }
                    info.put("z_freight", totalFreight);
                    int orderDetailNum = orderDetailsModelMapper.getOrderDetailNum(storeId, sNo);
                    info.put("sum", orderDetailNum);
                    int allow = 0;
                    Object allowObj = info.get("allow");
                    if (allowObj != null) {
                        allow = (int) info.get("allow");
                    }
                    List<JSONObject> templist = new ArrayList<>();
                    for (OrderDetailsModel orderDetailsModel1 : tmpList) {
                        jsonObject = (JSONObject) JSONObject.toJSON(orderDetailsModel1);
                        jsonObject.put("integral", DoubleFormatUtil.format(allow / orderDetailNum));
                        templist.add(jsonObject);
                    }
                    info.put("list", templist);
                    List<Map<String, Object>> product = new ArrayList<>();
                    boolean canBuy = true;
                    if (!CollectionUtils.isEmpty(templist)) {
                        for (JSONObject orderDetailsJsonObj : templist) {
                            //详情id
                            int detailId = MapUtils.getIntValue(orderDetailsJsonObj, "id");
                            // 产品规格id
                            int sid = orderDetailsJsonObj.getInteger("sid");
                            //不显示
                            orderDetailsJsonObj.put("comments_type", 0);
                            //判断订单评论状态
                            CommentsModel commentsModel = new CommentsModel();
                            commentsModel.setStore_id(storeId);
                            commentsModel.setOrder_detail_id(detailId);
                            commentsModel.setAttribute_id(sid);
                            commentsModel.setUid(userId);
                            commentsModel.setOid(sNo);
                            commentsModel = commentsModelMapper.selectOne(commentsModel);
                            int r_status = orderDetailsJsonObj.getInteger("r_status");
                            if (commentsModel != null) {
                                //待追评
                                orderDetailsJsonObj.put("comments_type", 2);
                                if (StringUtils.isNotEmpty(commentsModel.getReview())) {
                                    //评论完成
                                    orderDetailsJsonObj.put("comments_type", 3);
                                }
                            } else {
                                if (r_status == 5) {
                                    //待评价
                                    orderDetailsJsonObj.put("comments_type", 1);
                                }
                            }
                            boolean ismch = (boolean) info.get("ismch");
                            if (ismch) {
                                MchModel mchModel = mchModelMapper.getMchInfoByPid(sid);
                                orderDetailsJsonObj.put("shop_id", mchModel.getId());
                                orderDetailsJsonObj.put("shop_name", mchModel.getName());
                                orderDetailsJsonObj.put("shop_logo", publiceService.getImgPath(mchModel.getLogo(), storeId));
                            }
                            ConfiGureModel confiGureModel = new ConfiGureModel();
                            confiGureModel.setId(sid);
                            confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                            if (r_status == 0) {
                                //如果是代付款订单 查询规格价格是否改变
                                if (confiGureModel != null) {
                                    //当前规格设置价格
                                    BigDecimal currentPrice = confiGureModel.getPrice();
                                    BigDecimal orderPrice = orderDetailsJsonObj.getBigDecimal("p_price");
                                    if (currentPrice.equals(orderPrice)) {
                                        canBuy = false;
                                    }
                                }
                            }
                            String size = orderDetailsJsonObj.getString("size");
                            if (StringUtils.isNotEmpty(size)) {
                                orderDetailsJsonObj.put("size", com.laiketui.root.utils.tool.StringUtils.ltrim(size, SplitUtils.FH));
                            }
                            // 属性id
                            orderDetailsJsonObj.put("attribute_id", orderDetailsJsonObj.get("sid"));
                            detailsInfo = orderDetailsJsonObj.getInnerMap();
                            // 获取商品规格图片
                            String imgUrl = null;
                            if (confiGureModel != null) {
                                imgUrl = confiGureModel.getImg();
                            }
                            detailsInfo.put("imgurl", publiceService.getImgPath(imgUrl, storeId));
                            product.add(detailsInfo);
                            // 订单详情状态
                            int orderDetailsStatus = orderDetailsJsonObj.getInteger("r_status");
                            // 状态 0:未付款 1:未发货 2:待收货 5:已完成  7:订单关闭
                            int otherStatusOrderNum = 0;
                            int closeOrderNum = 0;
                            int allOrderNum = 0;
                            OrderDetailsModel orderDetailsModelTmp = new OrderDetailsModel();
                            orderDetailsModelTmp.setStore_id(storeId);
                            orderDetailsModelTmp.setR_sNo(sNo);
                            orderDetailsModelTmp.setR_status(orderDetailsStatus);
                            if (orderDetailsStatus != 7) {
                                //其他状态
                                otherStatusOrderNum = orderDetailsModelMapper.selectCount(orderDetailsModelTmp);
                            } else {
                                //已经关闭
                                closeOrderNum = orderDetailsModelMapper.selectCount(orderDetailsModelTmp);
                            }
                            orderDetailsModelTmp.setR_status(null);
                            //所有
                            allOrderNum = orderDetailsModelMapper.selectCount(orderDetailsModelTmp);

                            // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                            if ((otherStatusOrderNum + closeOrderNum) == allOrderNum) {
                                //如果订单数量相等 则修改父订单状态
                                orderModelMapper.updateOrderStatus(storeId, sNo, userId, orderDetailsStatus);
                                if (orderDetailsStatus > 0) {
                                    info.put("status", orderDetailsStatus);
                                }
                            }

                        }
                        //
                        info.put("sale_type", 0);
                        Map conditionMap = Maps.newHashMap();
                        conditionMap.put("storeId", storeId);
                        conditionMap.put("userId", userId);
                        conditionMap.put("sNo", sNo);
                        conditionMap.put("orderDetailsId", orderDetailsModel.getId());
                        int unFinishShouHouOrderNum = returnOrderModelMapper.getUnFinishShouHouOrder(conditionMap);
                        //判断有无订单售后未结束
                        if (unFinishShouHouOrderNum > 0) {
                            info.put("sale_type", 1);
                        }
                        info.put("list", product);
                        info.put("can_buy", canBuy);
                    }
                    orderList.add(info);
                }
            }

            Map retMap = Maps.newHashMap();
            retMap.put("order", orderList);
            retMap.put("order_failure", orderFailure);
            retMap.put("company", company);
            retMap.put("order_num", orderListCount);
            retMap.put("res_order", resOrderMap);
            return retMap;
        } catch (LaiKeApiException e) {
            logger.error("订单列表获取失败 ", e);
            throw e;
        } catch (Exception e) {
            logger.error("订单列表获取 异常 ", e);
            throw new LaiKeApiException(ORDER_LIST_FAILED, "订单列表获取失败", "orderList");
        }
    }

    @Override
    public Map<String, Object> orderPcList(AdminOrderVo vo) throws LaiKeApiException {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            int store_id = vo.getStoreId();
            String adminName = vo.getAdminName();
            // 订单号
            String sNo = vo.getsNo();
            // 品牌
            if (StringUtils.isNotEmpty(vo.getxOrder())) {
                vo.setStartdate(DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD) + "00:00:00");
                vo.setEnddate(DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD) + "23:59:59");
            }

            if ("1".equals(vo.getNewsStatus())) {
                /**已付款*/
                vo.setStatus("1");
            }

            if (StringUtils.isNotEmpty(vo.getsNo())) {
                vo.setsNo("%" + vo.getsNo() + "%");
            }
            Map<String, Object> paramMap = JSONObject.parseObject(JSONObject.toJSONString(vo)).getInnerMap();
            int total = orderModelMapper.getAdminOrderCount(paramMap);
            List<Map<String, Object>> returnList = new ArrayList<>();
            if (total > 0) {
                //支付方式
                List<PaymentModel> paymentModelList = paymentModelMapper.selectAll();
                Map<String, Object> payTypeShowName = Maps.newHashMap();
                for (PaymentModel paymentModel : paymentModelList) {
                    payTypeShowName.put(paymentModel.getClass_name(), paymentModel.getName());
                }
                AdminRecordModel adminRecordModel = new AdminRecordModel();
                adminRecordModel.setAdmin_name(adminName);
                adminRecordModel.setStore_id(store_id);
                adminRecordModel.setType(4);
                if ("This_page".equals(vo.getPageto())) {
                    adminRecordModel.setEvent("导出订单第" + vo.getPageNo() + "的信息 ");
                } else if ("whole".equals(vo.getPageto())) {
                    adminRecordModel.setEvent("导出订单全部信息");
                } else if ("inquiry".equals(vo.getPageto())) {
                    adminRecordModel.setEvent("导出订单全部信息");
                }
                paramMap.put("start", vo.getPageNo());
                paramMap.put("pagesize", vo.getPageSize());

                publicAdminRecordService.adminRecode(adminRecordModel);
                List<Map<String, Object>> orderList = orderModelMapper.adminOrderList(paramMap);

                if (!CollectionUtils.isEmpty(orderList)) {
                    String type = "1,3,5,";
                    for (Map<String, Object> orderInfo : returnList) {

                        int shopId = MapUtils.getIntValue(orderInfo, "shop_id");
                        int statusInt = MapUtils.getIntValue(orderInfo, "status");
                        if (StringUtils.isNotEmpty(sNo)) {
                            if (statusInt == ORDERS_R_STATUS_COMPLETE) {
                                type += "6,";
                            }
                            if (statusInt == ORDERS_R_STATUS_CLOSE) {
                                type += "4,";
                            }
                            messageLoggingModalMapper.updateMessLogInfo(store_id, shopId, vo.getsNo(), type);
                        }

                        int freight = 0;
                        String mchIds = MapUtils.getString(orderInfo, "mch_id");
                        String[] mchArrs = mchIds.split(SplitUtils.DH);
                        if (statusInt == ORDERS_R_STATUS_UNPAID) {
                            orderInfo.put("t_mch_id", "");
                        } else {
                            orderInfo.put("t_mch_id", mchArrs[1]);
                        }
                        int mchLen = mchArrs.length;
                        if (mchLen > 3) {
                            orderInfo.put("is_mch", 1);
                        } else {
                            orderInfo.put("is_mch", 0);
                        }
                        //地址
                        String sheng = MapUtils.getString(orderInfo, "sheng");
                        String shi = MapUtils.getString(orderInfo, "shi");
                        String xian = MapUtils.getString(orderInfo, "xian");
                        String address = MapUtils.getString(orderInfo, "address");
                        orderInfo.put("address", sheng + shi + xian + address);
                        orderInfo.put("statu", statusInt);
                        String pay = MapUtils.getString(orderInfo, "pay");
                        orderInfo.put("statu", payTypeShowName.getOrDefault(pay, "钱包"));
                        String userId = MapUtils.getString(orderInfo, "user_id");
                        List<Map<String, Object>> orderProductList = orderModelMapper.adminOrderListProductInfo(paramMap);
                        // 快递单号
                        List<String> courierNumList = new ArrayList<>();
                        String mchname = "";
                        if (!CollectionUtils.isEmpty(orderProductList)) {
                            for (Map<String, Object> vd : orderProductList) {
                                mchname = MapUtils.getString(orderInfo, "mchname");
                                freight += MapUtils.getDouble(orderInfo, "freight");
                                String imgurl = MapUtils.getString(orderInfo, "imgurl");
                                vd.put("imgurl", publiceService.getImgPath(imgurl, store_id));
                                int experId = MapUtils.getIntValue(orderInfo, "express_id");
                                if (experId != 0) {
                                    ExpressModel expressModel = new ExpressModel();
                                    expressModel.setId(experId);
                                    expressModel = expressModelMapper.selectOne(expressModel);
                                    // 快递单号
                                    courierNumList.add(MapUtils.getString(orderInfo, "courier_num") + "(" + expressModel.getKuaidi_name() + ")");
                                }
                                // 订单详情状态
                                int orderDetailsStatus = MapUtils.getIntValue(orderInfo, "r_status");
                                int otherStatusOrderNum = 0;
                                int closeOrderNum = 0;
                                int allOrderNum = 0;
                                OrderDetailsModel orderDetailsModelTmp = new OrderDetailsModel();
                                orderDetailsModelTmp.setStore_id(store_id);
                                orderDetailsModelTmp.setR_sNo(sNo);
                                orderDetailsModelTmp.setR_status(orderDetailsStatus);
                                if (orderDetailsStatus != 7) {
                                    //其他状态
                                    otherStatusOrderNum = orderDetailsModelMapper.selectCount(orderDetailsModelTmp);
                                } else {
                                    //已经关闭
                                    closeOrderNum = orderDetailsModelMapper.selectCount(orderDetailsModelTmp);
                                }
                                orderDetailsModelTmp.setR_status(null);
                                //所有
                                allOrderNum = orderDetailsModelMapper.selectCount(orderDetailsModelTmp);

                                // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                                if ((otherStatusOrderNum + closeOrderNum) == allOrderNum) {
                                    //如果订单数量相等 则修改父订单状态
                                    orderModelMapper.updateOrderStatus(store_id, sNo, userId, orderDetailsStatus);
                                }
                            }

                            Map courier_num_map = Maps.newHashMap();
                            for (String couriernum1 : courierNumList) {
                                if (!courier_num_map.containsKey(couriernum1)) {
                                    courier_num_map.put(couriernum1, couriernum1);
                                }
                            }
                            orderInfo.put("courier_num", courier_num_map);
                            orderInfo.put("products", orderProductList);
                            orderInfo.put("mchname", mchname);
                            getOrderStatus(orderInfo, statusInt);
                            orderInfo.put("freight", freight);
                            returnList.add(orderInfo);
                        }

                    }
                }
            }

            //todo 没有用
            retMap.put("brand_str", "");
            retMap.put("ordtype", "");
            retMap.put("list", returnList);
            retMap.put("total", total);
            retMap.put("source_str", publiceService.getDictionaryList("来源"));
            retMap.put("class", publiceService.getDictionaryList("订单状态"));
            return retMap;
        } catch (Exception e) {
            logger.error("订单列表获取 异常", e);
            throw new LaiKeApiException(ORDER_LIST_FAILED, "订单列表获取失败", "orderPcList");
        }
    }

    /**
     * 后台获取订单状态
     *
     * @param orderInfo
     * @param statusInt
     */
    private void getOrderStatus(Map<String, Object> orderInfo, int statusInt) {
        switch (statusInt) {
            case ORDERS_R_STATUS_UNPAID:
                orderInfo.put("status", "待付款");
                orderInfo.put("bgcolor", "#f5b1aa");
                break;
            case ORDERS_R_STATUS_CONSIGNMENT:
                orderInfo.put("status", "待发货");
                orderInfo.put("bgcolor", "#f09199");
                break;
            case ORDERS_R_STATUS_DISPATCHED:
                orderInfo.put("status", "待收货");
                orderInfo.put("bgcolor", "#f19072");
                break;
            case ORDERS_R_STATUS_COMPLETE:
                orderInfo.put("status", "订单完成");
                orderInfo.put("bgcolor", "#f7b977");
                break;
            case ORDERS_R_STATUS_CLOSE:
                orderInfo.put("status", "订单关闭");
                orderInfo.put("bgcolor", "#ffbd8b");
                break;
        }
    }

    @Override
    public Map<String, Object> getLogistics(String orderNo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            List<Object> resultList = new ArrayList<>(16);
            //物流信息集
            List<Object> dataList = new ArrayList<>();
            //商品信息快递单号分组
            Map<String, Map<String, Object>> goodsGroupMap = new HashMap<>(16);

            //获取订单信息
            OrderModel orderModel = new OrderModel();
            orderModel.setsNo(orderNo);
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel != null) {
                //物流去重,如果多个商品统一发货则只显示一个物流动态
                Set<String> wuLiuIds = new HashSet<>();
                //商品分组
                Map<String, List<Map<String, Object>>> goodsGroupList = new HashMap<>(16);

                //快递查询付费接口开关
                boolean isOpen = false;
                //获取配置信息
                ConfigModel configModel = new ConfigModel();
                configModel.setStore_id(orderModel.getStore_id());
                configModel = configModelMapper.selectOne(configModel);
                if (configModel != null && StringUtils.isNotEmpty(configModel.getExpress_address())) {
                    if (configModel.getIs_express() == 1) {
                        isOpen = true;
                    }
                }

                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setR_sNo(orderNo);
                List<OrderDetailsModel> orderDetailsModelList = orderDetailsModelMapper.select(orderDetailsModel);
                for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                    Map<String, Object> resultMapTemp = new HashMap<>(16);
                    Map<String, Object> goodsMap = new HashMap<>(16);
                    //快递公司id
                    Integer expressId = StringUtils.stringParseInt(orderDetails.getExpress_id() + "");
                    //快递单号
                    String courierNum = orderDetails.getCourier_num();
                    //快递名称
                    String kdName = "";
                    //快递公司代号
                    String kdCode = "";
                    String wuLiuKey = expressId + courierNum;
                    if (wuLiuIds.contains(wuLiuKey)) {
                        continue;
                    }

                    //获取快递公司代码
                    if (expressId != null && expressId > 0) {
                        ExpressModel expressModel = expressModelMapper.selectByPrimaryKey(expressId);
                        if (expressModel == null) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "物流公司不存在");
                        }
                        kdName = expressModel.getKuaidi_name();
                        kdCode = expressModel.getType();
                        String url;
                        String resultJson;
                        if (isOpen) {
                            url = configModel.getExpress_address();
                            Map<String, Object> parma = new HashMap<>(16);
                            parma.put("com", kdCode);
                            parma.put("num", courierNum);
                            parma.put("phone", "");
                            parma.put("from", "");
                            parma.put("to", "");
                            parma.put("resultv2", "1");
                            //请求参数
                            String param = JSON.toJSONString(parma);
                            //签名
                            String sign = Md5Util.MD5endoce(param + configModel.getExpress_key() + configModel.getExpress_number());
                            if (sign == null) {
                                throw new LaiKeApiException(DATA_ERROR, "数据错误");
                            }
                            url += "?param=" + param + "&sign=" + sign.toUpperCase() + "&customer=" + configModel.getExpress_number();

                            resultJson = HttpUtils.post(url);
                        } else {
                            url = String.format(GloabConst.OtherUrl.API_KUAIDI100_URL, kdCode, courierNum);
                            resultJson = HttpUtils.get(url);
                        }
                        logger.debug("正在获取编号{}的物流信息 url{}", courierNum, url);

                        JSONObject obj = JSONObject.parseObject(resultJson);
                        if (obj.containsKey("data")) {
                            dataList = DataUtils.cast(obj.get("data"));
                        } else {
                            dataList = new ArrayList<>();
                        }
                        //获取商品信息
                        ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(orderDetails.getSid());
                        goodsMap.put("img", publiceService.getImgPath(confiGureModel.getImg(), orderDetails.getStore_id()));
                        goodsMap.put("num", orderDetails.getNum());
                    }
                    //分组
                    if (goodsGroupMap.containsKey(courierNum)) {
                        //获取同单物流信息
                        resultMapTemp = goodsGroupMap.get(courierNum);
                        List<Map<String, Object>> goodsListTemp = goodsGroupList.get(courierNum);
                        resultMapTemp.put("pro_list", goodsListTemp);
                    } else {
                        //商品信息根据物流单分组
                        List<Map<String,Object>> goodsListTemp = new ArrayList<>();
                        if (goodsGroupList.containsKey(courierNum)) {
                            goodsListTemp.addAll(goodsGroupList.get(courierNum));
                        } else {
                            goodsListTemp.add(goodsMap);
                            goodsGroupList.put(courierNum, goodsListTemp);
                        }
                        //构造数据结构
                        resultMapTemp.put("list", dataList);
                        resultMapTemp.put("pro_list", goodsListTemp);
                        resultMapTemp.put("kuaidi_name", kdName);
                        resultMapTemp.put("courier_num", courierNum);
                        resultMapTemp.put("type", kdCode);
                        //是否为售后物流 1=订单物流 2=售后物流
                        resultMapTemp.put("shop_type", 1);
                        goodsGroupMap.put(courierNum, resultMapTemp);
                    }
                    wuLiuIds.add(wuLiuKey);
                    resultList.add(resultMapTemp);
                }
            } else if (StringUtils.isInteger(orderNo)) {
                //售后物流
                ReturnOrderModel returnOrderModel = returnOrderModelMapper.selectByPrimaryKey(orderNo);
                if (returnOrderModel != null) {
                    ReturnGoodsModel returnGoodsModel = new ReturnGoodsModel();
                    returnGoodsModel.setStore_id(returnOrderModel.getStore_id());
                    returnGoodsModel.setOid(String.valueOf(returnOrderModel.getP_id()));
                    List<ReturnGoodsModel> list = returnGoodsModelMapper.select(returnGoodsModel);
                    for (ReturnGoodsModel returnGoods : list) {
                        Map<String, Object> resultMapTemp;
                        //快递公司id
                        String express = returnGoods.getExpress();
                        //快递单号
                        String courierNum = returnGoods.getExpress_num();
                        //快递名称
                        String kdName = "";
                        //快递公司代号
                        String kdCode = "";

                        //获取订单明细
                        OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                        orderDetailsModel.setR_sNo(orderNo);
                        orderDetailsModel.setId(Integer.parseInt(returnGoods.getOid()));
                        OrderDetailsModel orderDetails = orderDetailsModelMapper.selectOne(orderDetailsModel);
                        //获取商品信息
                        ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(orderDetails.getSid());
                        //获取快递公司代码
                        if (express != null) {
                            ExpressModel expressModel = new ExpressModel();
                            expressModel.setKuaidi_name(express);
                            expressModel = expressModelMapper.selectOne(expressModel);
                            kdName = expressModel.getKuaidi_name();
                            kdCode = expressModel.getType();
                            String url = String.format(GloabConst.OtherUrl.API_KUAIDI100_URL, kdCode, courierNum);
                            logger.debug("正在获取编号{}的物流信息 url{}", courierNum, url);
                            String resultJson = HttpUtils.get(url);
                            JSONObject obj = JSONObject.parseObject(resultJson);
                            if (obj.containsKey("data")) {
                                dataList = DataUtils.cast(obj.get("data"));
                            } else {
                                dataList = new ArrayList<>();
                            }
                        }
                        //分组
                        if (goodsGroupMap.containsKey(courierNum)) {
                            resultMapTemp = goodsGroupMap.get(courierNum);
                        } else {
                            //构造数据结构
                            Map<String, Object> tempMap = new HashMap<>(16);
                            tempMap.put("list", dataList);
                            Map<String, Object> goodsMap = new HashMap<>(16);
                            //商品信息
                            List<Map<String, Object>> goodsInfo = new ArrayList<>();
                            goodsMap.put("img", publiceService.getImgPath(confiGureModel.getImg(), orderDetails.getStore_id()));
                            goodsMap.put("num", orderDetails.getNum());
                            goodsInfo.add(goodsMap);

                            tempMap.put("pro_list", goodsInfo);
                            tempMap.put("kuaidi_name", kdName);
                            tempMap.put("courier_num", courierNum);
                            tempMap.put("type", kdCode);
                            //是否为售后物流 1=订单物流 2=售后物流
                            tempMap.put("shop_type", 2);
                            resultMapTemp = tempMap;
                            goodsGroupMap.put(courierNum, resultMapTemp);
                        }
                        resultList.add(resultMapTemp);
                    }
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "订单不存在", "logistics");
            }

            resultMap.put("list", resultList);
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取物流信息 异常:", e);
            throw new LaiKeApiException(ORDER_LIST_FAILED, "获取物流信息 异常", "getLogistics");
        }
    }


    @Override
    public Map<String, Object> ucOrderDetails(OrderVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        JSONObject jsonObject = new JSONObject();
        // 订单中商品信息
        List<Map> productList = new ArrayList<>();
        // 没有批量退货按钮
        int batchDel = 1;
        try {
            int storeId = vo.getStoreId();
            Integer orderId = vo.getOrderId();
            User user;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil, true);
            }
            Map<String, Object> map = new HashMap<>();
            map.put("store_id", vo.getStoreId());
            List<Map<String, Object>> paymentStatus = paymentConfigModelMapper.getPaymentConfigDynamic(map);
            Map<String, Integer> payment = Maps.newHashMap();
            for (Map<String, Object> paymentConf : paymentStatus) {
                int isOpen = DataUtils.getIntegerVal(paymentConf, "status", 0);
                payment.put(DataUtils.getStringVal(paymentConf, "class_name"), isOpen);
            }

            // 根据微信id,查询用户id
            String userId = user.getUser_id();
            // 支付密码错误次数
            int loginNum = user.getLogin_num() == null ? 0 : user.getLogin_num();
            // 支付密码验证时间
            Date verificationTime = user.getVerification_time();
            if (verificationTime != null) {
                verificationTime = DateUtil.getAddDate(verificationTime, 1);
            }

            String user_password = user.getPassword();
            int passwordStatus = 0;
            if (!org.springframework.util.StringUtils.isEmpty(user_password)) {
                passwordStatus = 1;
            }

            Date time = new Date();
            // 是否可以输入密码
            boolean enterless = true;
            if (loginNum == 5) {
                if (verificationTime != null && time.after(verificationTime)) {
                    enterless = false;
                } else {
                    userMapper.updateUserLoginnum(storeId, userId);
                    enterless = true;
                }
            }

            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(storeId);
            orderModel.setId(orderId);
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                throw new LaiKeApiException(PARAMETER_VAL_ERROR, "参数错误", "orderDetails");
            }
            //获取插件订单配置
            Map<String, Object> configMap = getOrderConfig(storeId, orderModel.getOtype());
            //订单失效  - 秒
            int orderFailure = 3600;
            // 订单过期删除时间单位
            String company = "seconds";
            //订单售后时间
            int orderAfter = 7;
            if (configMap != null) {
                orderFailure = MapUtils.getIntValue(configMap, "orderFailureDay");
                orderFailure = orderFailure / 60 / 60;
                orderAfter = MapUtils.getIntValue(configMap, "orderAfter");
            }

            // 订单号
            String sNo = orderModel.getsNo();
            // 总价
            BigDecimal zPrice = orderModel.getZ_price();
            //订单状态
            int status = orderModel.getStatus();
            // 余额抵扣
            BigDecimal offset_balance = orderModel.getOffset_balance();
            offset_balance = offset_balance == null ? BigDecimal.ZERO : offset_balance;
            if (DictionaryConst.OrdersType.ORDERS_HEADER_GM.equals(orderModel.getOtype())
                    && zPrice.compareTo(offset_balance) != 0 && status != 0) {
                zPrice = zPrice.add(offset_balance);
            }
            //订单备注处理
            if (StringUtils.isNotEmpty(orderModel.getRemarks())) {
                // 订单备注
                Map<String, String> remarksMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(orderModel.getRemarks(), Map.class));
                for (String val : remarksMap.values()) {
                    orderModel.setRemark(val);
                    orderModel.setRemarks(val);
                }
            }

            /**
             * 收到取消订单
             */
            int hand_del = 0;

            // 订单时间
            Date addTime = orderModel.getAdd_time();
            String remarks = "";
            String dbRemarks = orderModel.getRemarks();
            if (!org.springframework.util.StringUtils.isEmpty(dbRemarks)) {
                if (SerializePhpUtils.isSerialized(dbRemarks)) {
                    // 订单备注
                    Map<String, String> remarksMap = SerializePhpUtils.getUnserializeObj(dbRemarks, Map.class);
                    for (String val : remarksMap.values()) {
                        remarks = val;
                    }
                } else {
                    remarks = dbRemarks;
                }
            }
            //联系人
            String name = orderModel.getName();
            //联系手机号
            String mobile1 = orderModel.getMobile();
            //隐藏操作

            mobile1 = mobile1.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
            String sheng = orderModel.getSheng();
            String shi = orderModel.getShi();
            String xian = orderModel.getXian();
            //联系地址
            String address = orderModel.getAddress();

            //订单类型
            String otype = orderModel.getOtype();
            //自动满减名称
            String couponActivityName = orderModel.getCoupon_activity_name();
            //满减金额
            BigDecimal reducePrice = orderModel.getReduce_price();
            if (DataUtils.equalBigDecimalZero(orderModel.getReduce_price())) {
                couponActivityName = "￥" + reducePrice;
            }
            // 优惠券ID
            String couponId = orderModel.getCoupon_id();
            // 优惠券金额
            BigDecimal couponPrice = orderModel.getCoupon_price();
            // 店铺ID
            String mchId = com.laiketui.root.utils.tool.StringUtils.trim(orderModel.getMch_id(), SplitUtils.DH);
            // 会员等级折扣
            BigDecimal gradeRate = orderModel.getGrade_rate();
            //提醒状态
            Integer deliveryStatus = orderModel.getDelivery_status();
            // 是否自提
            Integer selfLifting = orderModel.getSelf_lifting();
            // 自提门店
            Integer singleStore = orderModel.getSingle_store();
            //平台优惠金额
            BigDecimal preferentialAmount = orderModel.getPreferential_amount();

            String couponName = "";
            CouponActivityModel couponActivity = null;
            if (!org.springframework.util.StringUtils.isEmpty(couponId)) {
                orderModel.setCoupon_id(couponId = StringUtils.trim(orderModel.getCoupon_id(), SplitUtils.DH));
                String[] cpids = couponId.split(SplitUtils.DH);
                for (String cpid : cpids) {
                    if (!"0".equals(cpid)) {
                        couponActivity = couponActivityModelMapper.getOneCouponActivity(storeId, Integer.parseInt(cpid));
                        break;
                    }
                }
            }

            if (couponActivity != null) {
                if (couponActivity.getActivity_type() == 2) {
                    couponName = "(" + couponActivity.getDiscount().multiply(BigDecimal.TEN) + "折)";
                }
            }
            BigDecimal userMoney = BigDecimal.ZERO;
            if (status == 0) {
                // 未付款
                userMoney = user.getMoney();
            }

            BigDecimal productTotal = BigDecimal.ZERO;
            BigDecimal zFreight = BigDecimal.ZERO;
            String discountType = "";
            //类型：优惠券coupon、满减subtraction 0,12 或者  12,21 最后一个逗号后面的数字就是平台优惠券id
            String[] couponsId = com.laiketui.root.utils.tool.StringUtils.trim(couponId, SplitUtils.DH).split(SplitUtils.DH);
            String platCouponId = couponsId[couponsId.length - 1];

            if (!"0".equals(platCouponId)) {
                //平台优惠券优惠类型
                discountType = "优惠券";
            }
            int subtractionId = orderModel.getSubtraction_id();
            if (subtractionId != 0) {
                discountType = "满减";
            }
            if (subtractionId != 0) {
                couponPrice = couponPrice.subtract(preferentialAmount);
            }
            // 会员优惠金额
            BigDecimal gradeRateAmount = BigDecimal.ZERO;
            // 根据订单号,查询订单详情
            OrderDetailsModel orderDetailsInfo = new OrderDetailsModel();
            orderDetailsInfo.setStore_id(storeId);
            orderDetailsInfo.setR_sNo(sNo);
            List<OrderDetailsModel> orderDetailsModelsList = orderDetailsModelMapper.select(orderDetailsInfo);
            boolean userCanAfter = true;
            String mchName = "";
            if (!CollectionUtils.isEmpty(orderDetailsModelsList)) {
                // 是否有批量申请
                List<Integer> batch = new ArrayList<Integer>();
                // 快递单号
                Map courier_num_arr = Maps.newHashMap();
                for (OrderDetailsModel orderDetails : orderDetailsModelsList) {
                    // 商品售价
                    BigDecimal orderDetailsPPrice = orderDetails.getP_price();
                    // 商品数量
                    BigDecimal pNum = BigDecimal.valueOf(orderDetails.getNum());
                    // 优惠后的金额
                    BigDecimal afterDiscount = orderDetails.getAfter_discount();
                    // 总折扣
                    gradeRateAmount = gradeRateAmount.add(orderDetailsPPrice.subtract(orderDetailsPPrice.multiply(gradeRate)).multiply(pNum)).setScale(2, BigDecimal.ROUND_HALF_UP);
                    // 属性id
                    String sid = orderDetails.getSid();
                    ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(sid);
                    if (confiGureModel == null) {
                        throw new LaiKeApiException(PARAMETER_VAL_ERROR, "商品属性不存在");
                    }
                    //插件商品id
                    int pluginId = orderDetails.getP_id();
                    // 产品id
                    int p_id = confiGureModel.getPid();
                    orderDetails.setP_id(p_id);
                    // 数据结构转换
                    jsonObject = JSONObject.parseObject(JSON.toJSONString(orderDetails));
                    // 不显示
                    jsonObject.put("comments_type", 0);

                    //判断订单评论状态
                    CommentsModel commentsModel = new CommentsModel();
                    commentsModel.setStore_id(storeId);
                    commentsModel.setAttribute_id(confiGureModel.getId());
                    commentsModel.setUid(userId);
                    commentsModel.setOid(sNo);
                    commentsModel = commentsModelMapper.selectOne(commentsModel);
                    if (commentsModel != null) {
                        //待追评
                        jsonObject.put("comments_type", 2);
                        if (!org.springframework.util.StringUtils.isEmpty(commentsModel.getReview())) {
                            //评论完成
                            jsonObject.put("comments_type", 3);
                        }
                    } else {
                        int r_status = orderDetails.getR_status();
                        if (r_status == ORDERS_R_STATUS_COMPLETE) {
                            //待评价
                            jsonObject.put("comments_type", 1);
                        }
                    }

                    //根据产品id 查询店铺id
                    ProductListModel productListModel = new ProductListModel();
                    productListModel.setId(p_id);
                    productListModel.setStore_id(storeId);
                    productListModel = productListModelMapper.selectOne(productListModel);
                    if (productListModel != null) {
                        int shop_id = productListModel.getMch_id();
                        MchModel mchModel = new MchModel();
                        mchModel.setId(shop_id);
                        mchModel.setRecovery(0);
                        mchModel = mchModelMapper.selectOne(mchModel);
                        if (mchModel != null) {
                            jsonObject.put("shop_id", shop_id);
                            jsonObject.put("shop_name", mchModel.getName());
                            jsonObject.put("shop_logo", publiceService.getImgPath(mchModel.getLogo(), storeId));
                        } else {
                            jsonObject.put("shop_id", shop_id);
                            jsonObject.put("shop_name", "");
                            jsonObject.put("shop_logo", "");
                        }
                    }
                    //商品单价
                    orderDetailsPPrice = orderDetails.getP_price();
                    // 数量
                    BigDecimal num = BigDecimal.valueOf(orderDetails.getNum());
                    // 运费
                    BigDecimal freight = orderDetails.getFreight();
                    // 商品总价
                    productTotal = productTotal.add(orderDetailsPPrice.multiply(num));
                    //如果为竞拍商品，重新计算总价
                    // 运费总价
                    zFreight = zFreight.add(freight);
                    // 到货时间
                    Date arriveTime = orderDetails.getArrive_time();
                    //是否可以售后
                    if (arriveTime != null) {
                        //售后截至日期
                        Date afterDate = DateUtil.getAddDateBySecond(new Date(), orderAfter);
                        if (DateUtil.dateCompare(new Date(), afterDate)) {
                            userCanAfter = false;
                        }
                    }
                    //查询售后情况 售后记录会有多条，获取最新的售后信息
                    ReturnOrderModel returnOrderModel = null;
                    Map<String, Object> parmaMap = new HashMap<>(16);
                    parmaMap.put("store_id", storeId);
                    parmaMap.put("orderDetailId", orderDetails.getId());
                    parmaMap.put("orderNo", sNo);
                    parmaMap.put("re_time_sort", DataUtils.Sort.DESC.toString());
                    parmaMap.put("pageStart", 0);
                    parmaMap.put("pageEnd", 1);
                    List<ReturnOrderModel> returnOrderList = returnOrderModelMapper.getReturnOrderListDynamic(parmaMap);
                    if (returnOrderList != null && returnOrderList.size() > 0) {
                        returnOrderModel = returnOrderList.get(0);
                    }
                    //售后标识 判断申请售后按钮的显示
                    int type = 0;
                    if (returnOrderModel != null) {
                        type = 1;
                        //退款转态
                        jsonObject.put("prompt", publicRefundService.getRefundStatus(vo.getStoreId(), returnOrderModel.getP_id()));

                        if (returnOrderModel.getContent() != null) {
                            batch.add(1);
                            // 申请退货（已经申请，不能再申请退货）
                            jsonObject.put("is_repro", 1);
                        } else {
                            batch.add(0);
                            // 没有申请退货（可以退货）
                            jsonObject.put("is_repro", 0);
                        }
                        jsonObject.put("returnId", returnOrderModel.getId());
                    }
                    //是否申请过售后
                    jsonObject.put("s_type", type);

                    // 换货次数
                    Integer exchangeNum = orderDetails.getExchange_num();
                    jsonObject.put("exchange_status", false);
                    exchangeNum = exchangeNum == null ? 0 : exchangeNum;
                    if (exchangeNum < 2) {
                        jsonObject.put("exchange_status", true);
                    }
                    Date date = DateUtil.getAddDate(new Date(), -7);
                    if (arriveTime != null) {
                        if (arriveTime.before(date)) {
                            // 到货时间少于7天
                            jsonObject.put("info", 1);
                        } else {
                            // 已经到货
                            jsonObject.put("info", 0);
                        }
                    } else {
                        jsonObject.put("info", 0);
                    }

                    // 根据产品id,查询产品列表 (产品规格图片)
                    String url = publiceService.getImgPath(confiGureModel.getImg(), storeId);
                    Map<String, Object> productMap = jsonObject.getInnerMap();
                    productMap.put("imgurl", url);
                    productMap.put("sid", sid);
                    productMap.put("is_distribution", productListModel.getIs_distribution());
                    productMap.put("recycle", productListModel.getRecycle());
                    //不是普通订单则用插件id
                    if (!DictionaryConst.OrdersType.ORDERS_HEADER_GM.equals(orderModel.getOtype())) {
                        productMap.put("pluginId", pluginId);
                    }
                    productList.add(productMap);

                    // 订单详情状态
                    Integer rStatus = orderDetails.getR_status();
                    if (rStatus != null) {
                        //订单详情状态为4的订单
                        OrderDetailsModel orderDetails4 = new OrderDetailsModel();
                        orderDetails4.setStore_id(storeId);
                        orderDetails4.setR_sNo(sNo);
                        orderDetails4.setR_type(0);
                        orderDetails4.setR_status(4);
                        int orderDetails4Num = orderDetailsModelMapper.selectCount(orderDetails4);
                        orderDetails4.setR_type(null);
                        orderDetails4.setR_status(null);
                        //所有详细订单总数
                        int orderDetailsNot4Num = orderDetailsModelMapper.selectCount(orderDetails4);

                        // 如果订单下面的商品都处在同一状态,那就改订单状态为已完成
                        if (orderDetails4Num == orderDetailsNot4Num) {
                            //如果订单数量相等 则修改父订单状态
                            orderModelMapper.updateOrderStatus(storeId, sNo, userId, rStatus);
                        }
                    } else {
                        status = rStatus;
                    }
                }
                for (Integer val : batch) {
                    if (val == 0) {
                        // 有批量退货按钮
                        batchDel = 0;
                        break;
                    }
                }
            } else {
                batchDel = 0;
            }

            //插件使用
            boolean userCanOpen = true;
            boolean userCanCan = true;
            boolean userCanMs = true;
            boolean userCanBuyMs = true;
            boolean isagainCan = false;
            boolean isagainOpen = false;
            boolean isinpt = false;

            StringBuilder mchStoreAddress = new StringBuilder();
            if (selfLifting == 1) {
                MchStoreModel mchStoreModel = new MchStoreModel();
                mchStoreModel.setStore_id(storeId);
                mchStoreModel.setMch_id(Integer.valueOf(mchId));
                mchStoreModel.setId(singleStore);
                if (mchStoreModel != null) {
                    mchStoreAddress.append(mchStoreModel.getSheng()).append(mchStoreModel.getShi()).append(mchStoreModel.getXian()).append(mchStoreModel.getAddress());
                }
            }

            int saleType = 0;
            //判断有无订单售后未结束
            Map conditionMap = Maps.newHashMap();
            conditionMap.put("storeId", storeId);
            conditionMap.put("userId", userId);
            conditionMap.put("sNo", sNo);
            conditionMap.put("orderDetailsId", null);
            int count = returnOrderModelMapper.getUnFinishShouHouOrder(conditionMap);
            if (count > 0) {
                saleType = 1;
            }

            //判断订单是否全在售后且未结束
            int count1 = orderDetailsModelMapper.getOrderDetailAllUnfinishSh(storeId, sNo);
            if (count == count1) {
                saleType = 2;
            }


            Map<String, Object> retMapTmp = new HashMap<>(16);
            retMapTmp.put("status", status);
            retMapTmp.put("payment", payment);
            retMapTmp.put("hand_del", hand_del);
            retMapTmp.put("user_can_Open", userCanOpen);
            retMapTmp.put("user_can_after", userCanAfter);
            retMapTmp.put("user_can_ms", userCanMs);
            retMapTmp.put("user_can_buy_ms", userCanBuyMs);
            retMapTmp.put("user_can_can", userCanCan);
            retMapTmp.put("isagain_can", isagainCan);
            retMapTmp.put("isagain_open", isagainOpen);
            retMapTmp.put("isinpt", isinpt);
            retMapTmp.put("otype", otype);
            retMapTmp.put("remarks", remarks);
            retMapTmp.put("id", orderId);
            retMapTmp.put("sNo", sNo);
            retMapTmp.put("message", sNo);
            retMapTmp.put("z_price", zPrice);
            retMapTmp.put("product_total", productTotal);
            retMapTmp.put("mch_name", mchName);
            retMapTmp.put("z_freight", zFreight);
            retMapTmp.put("name", name);
            retMapTmp.put("mobile", mobile1);
            retMapTmp.put("address", address);
            retMapTmp.put("add_time", DateUtil.dateFormate(addTime, GloabConst.TimePattern.YMDHMS));
            retMapTmp.put("r_status", status);
            retMapTmp.put("list", productList);
            retMapTmp.put("user_money", userMoney);
            retMapTmp.put("order_failure", orderFailure);
            retMapTmp.put("company", company);
            retMapTmp.put("batch_del", batchDel);
            retMapTmp.put("coupon_activity_name", couponActivityName);
            retMapTmp.put("coupon_price", couponPrice);
            retMapTmp.put("coupon_name", couponName);
            retMapTmp.put("enterless", enterless);
            retMapTmp.put("offset_balance", offset_balance);
            retMapTmp.put("omsg", orderModel);
            retMapTmp.put("password_status", passwordStatus);
            retMapTmp.put("comm_discount", orderModel.getComm_discount());
            retMapTmp.put("grade_rate", gradeRate);
            retMapTmp.put("delivery_status", deliveryStatus);
            retMapTmp.put("self_lifting", selfLifting);
            retMapTmp.put("grade_rate_amount", gradeRateAmount);
            retMapTmp.put("discount_type", discountType);
            retMapTmp.put("preferential_amount", preferentialAmount);
            retMapTmp.put("sale_type", saleType);
            retMapTmp.put("gstatus", null);
            retMapTmp.put("pttype", null);
            retMapTmp.put("logistics", null);
            retMapTmp.put("pro_id", null);
            retMapTmp.put("order_no", null);
            retMapTmp.put("p_sNo", null);
            retMapTmp.put("allow", orderModel.getAllow());
            retMapTmp.put("is_end", null);
            retMapTmp.put("subtraction_list", null);
            resultMap.putAll(retMapTmp);

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单详情 异常", e);
            throw new LaiKeApiException(ILLEGAL_INVASION, "非法入侵", "orderDetails");
        }
        return resultMap;
    }

    @Override
    public BigDecimal getOrderPrice(int orderDetailId, int storeId) throws LaiKeApiException {
        try {
            //判断单个商品退款是否有使用优惠
            Map<String, Object> returnInfo = orderModelMapper.getReturnOrderInfo(storeId, orderDetailId);
            Integer express_id = DataUtils.getIntegerVal(returnInfo, "express_id", 0);
            //运费
            BigDecimal freight = DataUtils.getBigDecimalVal(returnInfo, "freight");
            //商品优惠后金额即实际支付金额
            BigDecimal after_discount = DataUtils.getBigDecimalVal(returnInfo, "after_discount");
            //计算实际支付金额
            BigDecimal price;
            //判断是否发货
            if (freight.compareTo(BigDecimal.ZERO) > 0 && express_id != null && express_id != 0) {
                //计算实际支付金额
                price = after_discount;
            } else {
                //计算实际支付金额
                price = after_discount.add(freight);
            }
            return price;
        } catch (Exception e) {
            logger.error("获取订单信息异常:", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "获取订单信息异常", "getOrderPrice");
        }
    }

    @Override
    public Map payBackUpOrder(OrderModel orderModel) throws LaiKeApiException {
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            /** 订单号 */
            String sNo = orderModel.getsNo();
            logger.info("支付回调更新订单{}开始", sNo);
            logger.info("订单信息：{}", JSON.toJSONString(orderModel));
            //1.数据准备
            /** 用户id */
            String userId = orderModel.getUser_id();
            /** 订单金额 */
            BigDecimal zPrice = orderModel.getZ_price();
            /** 微信支付单号 */
            String tradeNo = orderModel.getTrade_no();
            /** 支付类型 */
            String pay = orderModel.getPay();
            /** 商城id */
            int storeId = orderModel.getStore_id();


            //如果是积分订单,则扣除积分
            if (sNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_IN)) {
                User user = new User();
                user.setUser_id(userId);
                user = userBaseMapper.selectOne(user);
                //获取积分
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setR_sNo(orderModel.getsNo());
                orderDetailsModel = orderDetailsModelMapper.selectOne(orderDetailsModel);
                publicPaymentService.integralPay(user.getUser_id(), orderDetailsModel.getAfter_discount(), sNo, orderModel.getZ_price(), user.getAccess_id());
            }
            //普通订单流程
            orderPayment(orderModel.getStore_id(), orderModel.getsNo(), tradeNo, userId, pay);

            publicMemberService.memberSettlement(storeId, userId, sNo, zPrice, 1);
            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            //刷新缓存
            RedisDataTool.refreshRedisUserCache(user.getAccess_id(), user, redisUtil);

            //站内推送退款信息
            SystemMessageModel systemMessageSave = new SystemMessageModel();
            systemMessageSave.setType(1);
            systemMessageSave.setSenderid("admin");
            systemMessageSave.setStore_id(user.getStore_id());
            systemMessageSave.setRecipientid(user.getUser_id());
            systemMessageSave.setTitle("系统消息");
            systemMessageSave.setContent("您的宝贝马上就会发货啦!");
            systemMessageSave.setTime(new Date());
            systemMessageModelMapper.insertSelective(systemMessageSave);

            //TODO 短信发送
            // generate_code($db, $mobile, 1, 8, array("sNo" => $order_id));
            resultMap.put("code", 200);
            resultMap.put("message", "success");
            logger.info("支付回调更新订单完成");
        } catch (Exception e) {
            logger.error("支付回调出问题 异常", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "支付回调出问题", "payBackUpOrder");
        }
        return resultMap;
    }

    @Override
    public Map payBackUpOrderMember(OrderDataModel orderDataModel) throws LaiKeApiException {
        Map resultMap = Maps.newHashMap();
        try {
            /** 订单号 */
            String sNo = orderDataModel.getTrade_no();
            logger.info("支付回调更新订单{}开始", sNo);
            logger.info("订单信息：{}", JSON.toJSONString(orderDataModel));
            String data = orderDataModel.getData();
            Map map = SerializePhpUtils.getUnserializeObj(data, Map.class);
            //1.数据准备
            /** 用户id */
            String userId = DataUtils.getStringVal(map, "user_id");
            /** 订单金额 */
            BigDecimal zPrice = DataUtils.getBigDecimalVal(map, "total");
            /** 商城id */
            int storeId = DataUtils.getIntegerVal(map, "store_id");
            int row = orderDataModelMapper.updateStatus(orderDataModel.getId(), 1);
            logger.info("更新数据条数：{}", row);

            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);

            userMapper.updateUserMoney(zPrice, storeId, userId);

            RecordModel recordModel = new RecordModel();
            recordModel.setStore_id(storeId);
            recordModel.setUser_id(user.getUser_id());
            recordModel.setMoney(zPrice);
            recordModel.setOldmoney(user.getMoney());
            recordModel.setEvent(String.format("充值%s元", zPrice));
            recordModel.setType(1);
            recordModel.setAdd_date(new Date());
            recordModelMapper.insertSelective(recordModel);

            //TODO 短信发送
            // generate_code($db, $mobile, 1, 8, array("sNo" => $order_id));
            resultMap.put("code", 200);
            resultMap.put("message", "success");
            logger.info("支付回调更新订单完成");
        } catch (Exception e) {
            logger.error("支付回调出问题 异常", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "支付回调出问题", "payBackUpOrder");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> payBackUpOrderMchPromise(OrderDataModel orderDataModel) throws LaiKeApiException {
        Map<String, Object> resultMap = Maps.newHashMap();
        try {
            String sNo = orderDataModel.getTrade_no();
            logger.info("【店铺保证金】支付回调更新订单{}开始", sNo);
            logger.info("订单信息：{}", JSON.toJSONString(orderDataModel));
            String data = orderDataModel.getData();
            Map<String, Object> map = DataUtils.cast(SerializePhpUtils.getUnserializeObj(data, Map.class));
            if (map == null) {
                logger.error("订单信息数据出错");
                throw new LaiKeApiException(ABNORMAL_BIZ, "支付回调出问题", "payBackUpOrderMchPromise");
            }
            int row = orderDataModelMapper.updateStatus(orderDataModel.getId(), 1);
            if (row < 1) {
                logger.error("支付失败 修改临时订单状态失败");
                throw new LaiKeApiException(ABNORMAL_BIZ, "支付回调出问题", "payBackUpOrderMchPromise");
            }
            //用户id
            String userId = DataUtils.getStringVal(map, "user_id");
            //商城id
            int storeId = DataUtils.getIntegerVal(map, "store_id");
            MainVo vo = new MainVo();
            vo.setStoreId(storeId);

            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            vo.setAccessId(user.getAccess_id());
            //支付保证金
            publicMchService.paymentPromise(vo, orderDataModel.getOrder_type(), null, user.getUser_id(), sNo);
        } catch (Exception e) {
            logger.error("支付回调出问题 异常", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "支付回调出问题", "payBackUpOrder");
        }
        return resultMap;
    }

    @Override
    public OrderModel getOrderInfo(String orderNo) throws LaiKeApiException {
        OrderModel orderModel;
        try {
            orderModel = getOrderInfo(orderNo, null, null);
        } catch (Exception l) {
            throw l;
        }
        return orderModel;
    }

    @Override
    public OrderModel getOrderInfo(String orderNo, PaymentVo paymentVo, String userId) throws LaiKeApiException {
        OrderModel orderModel = new OrderModel();
        try {
            //获取订单信息
            if (orderNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_KJ)) {
                orderModel.setReal_sno(orderNo);
                orderModel = orderModelMapper.selectOne(orderModel);
            } else if (orderNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_DJ)) {
                OrderDataModel orderDataModel = new OrderDataModel();
                String data = orderDataModel.getData();
                Map<String, Object> map = DataUtils.cast(SerializePhpUtils.getUnserializeObj(data, Map.class));
                orderModel.setsNo(orderNo);
                orderModel.setReal_sno(orderNo);
                orderModel.setZ_price(DataUtils.getBigDecimalVal(map, "total"));
            } else if (orderNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_CZ)) {

                orderModel.setsNo(orderNo);
                orderModel.setReal_sno(orderNo);
                orderModel.setZ_price(paymentVo.getTotal());

                Map<String, Object> dataMap = new HashMap<>(16);
                dataMap.put("order_id", orderNo);
                dataMap.put("user_id", userId);
                dataMap.put("trade_no", orderNo);
                dataMap.put("pay", paymentVo.getPayType());
                dataMap.put("total", paymentVo.getTotal());
                dataMap.put("store_id", paymentVo.getStoreId());

                //非余额支付 添加一条临时订单
                OrderDataModel orderDataModel = new OrderDataModel();
                orderDataModel.setTrade_no(orderNo);
                orderDataModel.setData(SerializePhpUtils.JavaSerializeByPhp(dataMap));
                orderDataModel.setOrder_type(DictionaryConst.OrdersType.ORDERS_HEADER_CZ);
                orderDataModel.setAddtime(new Date());
                orderDataModel.setStatus(0);
                orderDataModelMapper.insert(orderDataModel);
            } else if (orderNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_MCH_PROMISE)) {
                OrderDataModel orderDataModel = new OrderDataModel();
                orderDataModel.setTrade_no(orderNo);
                orderDataModel = orderDataModelMapper.selectOne(orderDataModel);
                if (orderDataModel == null) {
                    orderModel = null;
                } else {
                    Map<String, Object> map = JSON.parseObject(orderDataModel.getData(), new TypeReference<Map<String, Object>>() {
                    });
                    orderModel.setReal_sno(orderNo);
                    orderModel.setsNo(orderNo);
                    orderModel.setStatus(orderDataModel.getStatus());
                    orderModel.setAdd_time(orderDataModel.getAddtime());
                    orderModel.setOtype(orderDataModel.getOrder_type());
                    orderModel.setMch_id(MapUtils.getString(map, "mchId"));
                    orderModel.setStore_id(MapUtils.getInteger(map, "storeId"));
                    orderModel.setZ_price(new BigDecimal(MapUtils.getString(map, "paymentAmt")));
                }
            } else {
                orderModel.setsNo(orderNo);
                orderModel = orderModelMapper.selectOne(orderModel);
            }
            if (orderModel == null) {
                throw new LaiKeApiException(DATA_NOT_EXIST, "订单不存在", "pay");
            }
        } catch (Exception e) {
            logger.error("支付-获取订单信息异常：", e);
            throw new LaiKeApiException(API_OPERATION_FAILED, "支付-获取订单信息失败", "getOrderInfo");
        }
        return orderModel;
    }

    @Override
    public Map<String, Object> modifyOrder(OrderModifyVo orderModifyVo) throws LaiKeApiException {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            //获取原订单信息
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(orderModifyVo.getStoreId());
            orderModel.setsNo(orderModifyVo.getsNo());
            orderModel = orderModelMapper.selectOne(orderModel);
            //订单状态
            int status = -1;
            //原订单总额
            BigDecimal oldz_price = BigDecimal.ZERO;
            BigDecimal z_price = orderModifyVo.getzPrice();
            if (orderModel == null) {
                throw new LaiKeApiException(ORDER_DATA_ERROR, "订单编辑失败", "modifyOrder");
            }
            status = orderModel.getStatus();
            oldz_price = orderModel.getZ_price();
            // 校验
//            DataCheckTool.checkOrderModify(orderModifyVo);
            int uStatus = orderModifyVo.getStatus();
            // 正式处理
            String type = orderModifyVo.getType();
            //
            Map params = Maps.newConcurrentMap();
            params.put("mobile", orderModifyVo.getMobile());
            params.put("name", orderModifyVo.getName());
            params.put("sheng", orderModifyVo.getSheng());
            params.put("shi", orderModifyVo.getShi());
            params.put("xian", orderModifyVo.getXian());
            params.put("address", orderModifyVo.getAddress());
            params.put("remarks", orderModifyVo.getRemarks());
            // 订单状态修改 原始状态为待付款 0
            // 此时管理员修改了订单状态
            if (status == ORDERS_R_STATUS_UNPAID && status != uStatus) {
                //更新状态为 已付款或者关闭
                if (uStatus == ORDERS_R_STATUS_CONSIGNMENT || uStatus == ORDERS_R_STATUS_CLOSE) {
                    params.put("status", uStatus);
                    params.put("orderno", orderModel.getsNo());
                    int rowOrder = orderDetailsModelMapper.updateByOrdernoDynamic(params);
                    int rowOrderDetails = orderModelMapper.updateByOrdernoDynamic(params);
                    if (rowOrder < 1 || rowOrderDetails < 1) {
                        logger.error("编辑订单状态失败！");
                        throw new LaiKeApiException(ORDER_DATA_ERROR, "订单编辑失败", "modifyOrder");
                    } else {
                        retMap.put("code", 200);
                        retMap.put("message", "success");
                    }
                }
            } else {
                params.clear();
                //若修改订单金额
                if (oldz_price.compareTo(z_price) != 0) {
                    //订单差额
                    BigDecimal sz_price = oldz_price.subtract(z_price);
                    //获取详单信息
                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                    orderDetailsModel.setR_sNo(orderModel.getsNo());
                    orderDetailsModel.setStore_id(orderModel.getStore_id());
                    List<OrderDetailsModel> orderDetailsModelList = orderDetailsModelMapper.select(orderDetailsModel);
                    params.clear();
                    for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                        BigDecimal up_price = orderDetails.getAfter_discount().add(orderDetails.getFreight()).multiply(sz_price);
                        //更新详单数据
                        params.put("detailId", orderDetails.getId());
                        params.put("afterDiscount", orderDetails.getAfter_discount().add(up_price));
                        int row = orderDetailsModelMapper.updateByOrdernoDynamic(params);
                        if (row < 0) {
                            throw new LaiKeApiException(ORDER_DATA_ERROR, "订单编辑失败", "modifyOrder");
                        }
                    }
                }
                params.clear();
                params = BeanUtils.bean2Map(orderModel);
                int rowOrder = orderModelMapper.updateByOrdernoDynamic(params);
                if (rowOrder < 0) {
                    throw new LaiKeApiException(ORDER_DATA_ERROR, "订单编辑失败", "modifyOrder");
                } else {
                    retMap.put("code", 200);
                    retMap.put("message", "success");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("编辑订单失败：{}", e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "编辑订单失败", "modifyOrder");
        }
        return retMap;
    }

    @Override
    public Map<String, Object> orderPcDetails(AdminOrderDetailVo adminOrderDetailVo) throws LaiKeApiException {
        Map<String, Object> retMap = Maps.newConcurrentMap();
        try {
            int store_id = adminOrderDetailVo.getStoreId();
            String sNo = adminOrderDetailVo.getId();
            String orderType = adminOrderDetailVo.getOrderType();
            String type = adminOrderDetailVo.getType();
            boolean update_s = StringUtils.isEmpty(type);

            OrderConfigModal orderConfigModal = new OrderConfigModal();
            orderConfigModal.setStore_id(store_id);
            orderConfigModal = orderConfigModalMapper.selectOne(orderConfigModal);

            Date remind_time;
            if (orderConfigModal != null) {
                int remind = orderConfigModal.getRemind();
                if (remind == 0) {
                    remind_time = DateUtil.getAddDate(new Date(), 7);
                } else {
                    int remind_day = remind / 24;
                    int remind_hour = remind % 24;
                    remind_time = DateUtil.getAddDate(new Date(), remind_day);
                    remind_time = DateUtil.getAddDateBySecond(remind_time, remind_hour * 3600);
                }
            } else {
                remind_time = DateUtil.getAddDate(new Date(), 7);
            }

            /*-----------进入订单详情把未读状态改成已读状态，已读状态的状态不变-------*/
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(store_id);
            orderModel.setsNo(sNo);
            orderModel = orderModelMapper.selectOne(orderModel);
            // 订单总价
            BigDecimal pay_price = BigDecimal.ZERO;
            Map params = Maps.newConcurrentMap();
            if (orderModel == null) {
                throw new LaiKeApiException(DATA_NOT_EXIST, "订单不存在");
            }
            pay_price = orderModel.getZ_price();
            String shopId = com.laiketui.root.utils.tool.StringUtils.trim(orderModel.getMch_id(), SplitUtils.DH);
            Integer readd = orderModel.getReadd();
            if (readd == 0) {
                params.put("readd", 1);
                params.put("store_id", store_id);
                params.put("orderno", sNo);
                int delivery_status = orderModel.getDelivery_status();
                if (delivery_status == 1) {
                    params.put("remind", remind_time);
                }
                int row = orderModelMapper.updateByOrdernoDynamic(params);

                if (row < 1) {
                    logger.error("修改订单失败");
                }

                String order_status = "1,3,5";
                messageLoggingModalMapper.updateMessLogInfo(store_id, Integer.parseInt(shopId), sNo, order_status);
            }
            int status = orderModel.getStatus();
            if (status == ORDERS_R_STATUS_COMPLETE) {
                messageLoggingModalMapper.updateMessLogInfo(store_id, Integer.parseInt(shopId), sNo, "6");
            }
            if (status == ORDERS_R_STATUS_CLOSE && shopId.length() == 1) {
                messageLoggingModalMapper.updateMessLogInfo(store_id, Integer.parseInt(shopId), sNo, "4");
            }
            /*--------------------------------------------------------------------------*/
            //支付方式
            Map<String, String> paymentModelMap = publicPaymentConfigService.getPaymentMap();
            List<Map<String, Object>> adminOrderDetailsList = orderDetailsModelMapper.getAdminOrderDetailsInfo(sNo, store_id);
            int num = adminOrderDetailsList.size();
            Map<String, Object> data = new HashMap<>(16);
            // 满减金额
            BigDecimal reduce_price = BigDecimal.ZERO;
            // 优惠券金额
            BigDecimal coupon_price = BigDecimal.ZERO;
            // 平台优惠券金额
            BigDecimal preferential_amount = BigDecimal.ZERO;
            // 积分
            int allow = 0;
            BigDecimal yunfei = BigDecimal.ZERO;
            //
            List<String> courier_num_arr = new ArrayList<>();
            //快递字符串信息 xxx(xxx),...
            String kdStrFormat = "%s(%s),";
            StringBuilder kdStr = new StringBuilder("");
            // 优惠金额
            BigDecimal yh_money = BigDecimal.ZERO;
            // 优惠类型
            String discount_type = "";
            // 优惠券ID
            Map<String, Object> orderDetailsData = adminOrderDetailsList.get(0);
            // 优惠券
            String coupon_id = DataUtils.getStringVal(orderDetailsData, "coupon_id", "0");
            // 满减活动ID
            Integer subtraction_id = DataUtils.getIntegerVal(orderDetailsData, "subtraction_id", 0);
            // 会员等级折扣
            BigDecimal grade_rate = DataUtils.getBigDecimalVal(orderDetailsData, "grade_rate", BigDecimal.ZERO);
            // 商品总价
            BigDecimal spz_price = DataUtils.getBigDecimalVal(orderDetailsData, "spz_price", BigDecimal.ZERO);
            // 总运费
            BigDecimal z_freight = DataUtils.getBigDecimalVal(orderDetailsData, "z_freight", BigDecimal.ZERO);
            // 订单类型
            String otype = DataUtils.getStringVal(orderDetailsData, "otype", "0");
            //$zifuchuan = trim(strrchr($coupon_id, ','),',');
            int zifuchuan = com.laiketui.root.utils.tool.StringUtils.trim(coupon_id, SplitUtils.DH).indexOf(SplitUtils.DH);

            if (subtraction_id != 0) {
                discount_type = "满减";
            } else if (zifuchuan != 0) {
                discount_type = "优惠券";
            }

            //订单商品总价小计
            BigDecimal goodsPrice = new BigDecimal("0");
            // 会员优惠金额
            BigDecimal grade_rate_amount = BigDecimal.ZERO;
            List<String> express_ids = new ArrayList<String>();
            courier_num_arr = new ArrayList<>();
            data.put("fh", 0);
            int orderDetailsStatus = -1;
            for (Map<String, Object> orderDetailsMap : adminOrderDetailsList) {
                //订单详情id
                int orderDetailId = MapUtils.getIntValue(orderDetailsMap, "id");
                //商品图片
                String goodsImgUrl = MapUtils.getString(orderDetailsMap, "img");
                goodsImgUrl = publiceService.getImgPath(goodsImgUrl, store_id);
                orderDetailsMap.put("pic", goodsImgUrl);
                // 联系人
                data.put("user_name", MapUtils.getString(orderDetailsMap, "user_name"));
                // 联系人
                data.put("name", DataUtils.getStringVal(orderDetailsMap, "name", ""));
                String remarks = DataUtils.getStringVal(orderDetailsMap, "remarks", "");
                Map<String, Object> remarksMap = new HashMap<>(16);
                //备注
                if (StringUtils.isNotEmpty(remarks)) {
                    if (SerializePhpUtils.isSerialized(remarks)) {
                        // 订单备注
                        remarksMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(remarks, Map.class));
                    }
                }
                data.put("remarks", remarksMap);

                //订单类型
                data.put("orderTypeName", OrderDataUtils.getOrderType(MapUtils.getString(orderDetailsMap, "otype")));
                //支付时间
                String payTime = MapUtils.getString(orderDetailsMap, "pay_time");
                if (StringUtils.isEmpty(payTime)) {
                    payTime = "";
                }
                //售后信息
                Map<String, Object> returnInfo = new HashMap<>(16);
                ReturnOrderModel returnOrderModel = returnOrderModelMapper.getReturnNewInfoByDetailId(orderDetailId);
                if (returnOrderModel != null) {
                    returnInfo.put("id", returnOrderModel.getId());
                    returnInfo.put("statusName", publicRefundService.getRefundStatus(store_id, returnOrderModel.getP_id()));
                }
                orderDetailsMap.put("returnInfo", returnInfo);


                data.put("pay_time", payTime);
                //来源
                data.put("source", MapUtils.getString(orderDetailsMap, "source"));
                //订单号
                data.put("sNo", MapUtils.getString(orderDetailsMap, "sNo"));
                // 联系电话
                data.put("oid", MapUtils.getString(orderDetailsMap, "oid"));
                // 联系电话
                data.put("mobile", MapUtils.getString(orderDetailsMap, "mobile"));
                //获取订单状态
                int orderStatus = MapUtils.getIntValue(orderDetailsMap, "status");
                orderDetailsMap.put("status", OrderDataUtils.getOrderStatus(orderStatus));
                //todo
                String grade_rate_tmp = DataUtils.getStringVal(orderDetailsMap, "grade_rate", "");
                if ("1.00".equalsIgnoreCase(grade_rate_tmp) || "0.00".equalsIgnoreCase(grade_rate_tmp) ||
                        "0".equalsIgnoreCase(grade_rate_tmp) || "1".equalsIgnoreCase(grade_rate_tmp)) {
                    data.put("grade_rate2", 1);
                    data.put("grade_rate", "-");
                } else {
                    data.put("grade_rate2", grade_rate_tmp);
                    BigDecimal grade = new BigDecimal(grade_rate_tmp).multiply(new BigDecimal("10"));
                    data.put("grade_rate", grade + "折");
                }

                BigDecimal pnum = DataUtils.getBigDecimalVal(orderDetailsMap, "num", BigDecimal.ZERO);
                BigDecimal p_price = DataUtils.getBigDecimalVal(orderDetailsMap, "p_price", BigDecimal.ONE);
                goodsPrice = goodsPrice.add(pnum.multiply(p_price));
                //优惠金额
                yh_money = yh_money.add(pnum.multiply(p_price)).subtract(pnum.multiply(p_price).multiply(new BigDecimal(grade_rate_tmp)));

                String sheng = DataUtils.getStringVal(orderDetailsMap, "sheng", "");
                String shi = DataUtils.getStringVal(orderDetailsMap, "shi", "");
                String xian = DataUtils.getStringVal(orderDetailsMap, "xian", "");
                String address = DataUtils.getStringVal(orderDetailsMap, "address", "");

                BigDecimal zheKou = new BigDecimal("0");
                if (orderModel.getGrade_rate().compareTo(BigDecimal.ZERO) >= 0) {
                    zheKou = orderModel.getGrade_rate().multiply(new BigDecimal("10"));
                }
                data.put("grade_rate", zheKou);
                // 详细地址
                data.put("address", new StringBuilder().append(sheng).append(shi).append(xian).append(address));
                data.put("sheng", sheng);
                data.put("shi", shi);
                data.put("xian", xian);
                data.put("r_address", address);
                // 添加时间
                data.put("add_time", MapUtils.getString(orderDetailsMap, "add_time"));
                data.put("z_price", DataUtils.getBigDecimalVal(orderDetailsMap, "z_price", BigDecimal.ZERO));
                data.put("user_id", DataUtils.getStringVal(orderDetailsMap, "user_id", ""));
                // 发货时间
                String deliverTime = "";
                if (orderDetailsMap.containsKey("deliver_time")) {
                    deliverTime = MapUtils.getString(orderDetailsMap, "deliver_time");
                }
                data.put("deliver_time", deliverTime);
                // 到货时间
                String arriveTime = "";
                if (orderDetailsMap.containsKey("arrive_time")) {
                    arriveTime = MapUtils.getString(orderDetailsMap, "arrive_time");
                }
                data.put("arrive_time", arriveTime);

                orderDetailsStatus = DataUtils.getIntegerVal(orderDetailsMap, "r_status", -1);
                // 订单详情状态
                data.put("r_status", orderDetailsStatus);
                data.put("status01", orderDetailsStatus);
                data.put("gstatus", orderDetailsStatus);
                // 订单类型
                data.put("otype", DataUtils.getStringVal(orderDetailsMap, "otype", ""));
                // 退货原因
                String contentReturn = "";
                if (orderDetailsMap.containsKey("content")) {
                    contentReturn = MapUtils.getString(orderDetailsMap, "content");
                }
                data.put("content", contentReturn);
                // 快递公司id
                String express_id = DataUtils.getStringVal(orderDetailsMap, "express_id", "");
                //
                data.put("express_id", express_id);
                if (!StringUtils.isEmpty(express_id)) {
                    if (!express_ids.contains(express_id)) {
                        express_ids.add(express_id);
                    }
                }

                String courier_num = DataUtils.getStringVal(orderDetailsMap, "courier_num", "");
                String kuaidi_name = DataUtils.getStringVal(orderDetailsMap, "kuaidi_name", "");
                if (StringUtils.isNotEmpty(courier_num)) {
                    List<Map<String, String>> cnsList = new ArrayList<>();
                    if (!courier_num_arr.contains(courier_num)) {
                        Map<String, String> tmpData = Maps.newConcurrentMap();
                        tmpData.put("num", DataUtils.getStringVal(orderDetailsMap, "num", ""));
                        tmpData.put("kuaidi_name", kuaidi_name);
                        cnsList.add(tmpData);
                        data.put("courier_num", cnsList);
                        courier_num_arr.add(courier_num);

                        kdStr.append(String.format(kdStrFormat, kuaidi_name, courier_num));
                    }
                }

                if (StringUtils.isNotEmpty(kuaidi_name)) {
                    data.put("fh", 1);
                }

                // 满减金额
                reduce_price = DataUtils.getBigDecimalVal(orderDetailsMap, "reduce_price", BigDecimal.ZERO);
                // 优惠券金额 优惠金额记录的是 店铺优惠卷+平台优惠卷金额
                coupon_price = DataUtils.getBigDecimalVal(orderDetailsMap, "coupon_price", BigDecimal.ZERO);
                // 平台优惠券金额
                preferential_amount = DataUtils.getBigDecimalVal(orderDetailsMap, "preferential_amount", BigDecimal.ZERO);
                if (preferential_amount.compareTo(BigDecimal.ZERO) > 0) {
                    //计算店铺优惠金额
                    coupon_price = coupon_price.subtract(preferential_amount);
                }
                // 积分
                allow = DataUtils.getIntegerVal(orderDetailsMap, "allow", 0);
                String pay = DataUtils.getStringVal(orderDetailsMap, "pay", "");
                String paytype = "";
                if (paymentModelMap.containsKey(pay)) {
                    paytype = paymentModelMap.get(pay);
                }
                // 支付方式
                data.put("paytype", paytype);
                data.put("lottery_status", 7);
                data.put("id", sNo);
                // 微信支付交易号
                data.put("trade_no", DataUtils.getStringVal(orderDetailsMap, "trade_no", ""));
                BigDecimal freight = DataUtils.getBigDecimalVal(orderDetailsMap, "", BigDecimal.ZERO);
                yunfei = yunfei.add(freight);
                data.put("paytype", paytype);
                // 根据产品id,查询产品主图
                ProductListModel productListModel = new ProductListModel();
                productListModel.setId(DataUtils.getIntegerVal(orderDetailsMap, "p_id", 0));
                //如果是其它插件订单则获取商品id
                if (!DictionaryConst.OrdersType.ORDERS_HEADER_GM.equals(orderModel.getOtype())) {
                    int attrId = MapUtils.getIntValue(orderDetailsMap, "sid");
                    ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(attrId);
                    if (confiGureModel == null) {
                        throw new LaiKeApiException(DATA_NOT_EXIST, "商品属性不存在");
                    }
                    productListModel.setId(confiGureModel.getPid());
                }

                productListModel = productListModelMapper.selectOne(productListModel);
                if (StringUtils.isNotEmpty(productListModel.getImgurl())) {
                    orderDetailsMap.put("pic", publiceService.getImgPath(productListModel.getImgurl(), store_id));
                }
            }
            yh_money = yh_money.add(coupon_price).add(reduce_price);
            // 运费
            data.put("yunfei", yunfei);
            data.put("express_name", "");
            if (!CollectionUtils.isEmpty(express_ids)) {
                for (String expressId : express_ids) {
                    // 根据快递公司id,查询快递公司表信息
                    ExpressModel expressModel = new ExpressModel();
                    expressModel.setId(Integer.parseInt(expressId));
                    expressModel = expressModelMapper.selectOne(expressModel);
                    String tmpKuaidiName = expressModel.getKuaidi_name();
                    if (StringUtils.isNotEmpty(tmpKuaidiName)) {
                        // 快递公司名称
                        data.put("express_name", data.get("express_name") + tmpKuaidiName + SplitUtils.DH);
                    }
                }
            }

            getOrderStatus(data, orderDetailsStatus);
            if ("modify".equalsIgnoreCase(orderType)) {
                //订单状态 为待收货可以编辑订单发货号
                if (orderDetailsStatus == ORDERS_R_STATUS_DISPATCHED) {
                    List<Map<String, String>> cnsList = DataUtils.cast(data.get("courier_num"));
                    if (!CollectionUtils.isEmpty(cnsList)) {
                        if (cnsList.size() == 1) {
                            data.put("courier_num", cnsList.get(0).get("num"));
                        }
                    }
                }
            }

            //订单状态
            List<String> orderStatusCnName = ImmutableList.of("待付款", "待发货", "待收货", "订单完成", "订单关闭");
            retMap.put("sdata", orderStatusCnName);
            retMap.put("yh_money", yh_money);
            //满减赠品
            retMap.put("zp_res", new ArrayList<>());
            retMap.put("update_s", update_s);
            retMap.put("data", data);
            retMap.put("detail", adminOrderDetailsList);
            retMap.put("reduce_price", reduce_price);
            retMap.put("coupon_price", coupon_price);
            retMap.put("preferential_amount", preferential_amount);
            retMap.put("grade_rate_amount", grade_rate_amount);
            retMap.put("allow", allow);
            retMap.put("num", num);
            retMap.put("discount_type", discount_type);
            retMap.put("pay_price", pay_price);
            retMap.put("z_freight", z_freight);
            retMap.put("grade_rate", grade_rate);
            //商品总价小计
            retMap.put("spz_price", goodsPrice);
            //快递
            retMap.put("expressStr", StringUtils.trim(kdStr.toString(), SplitUtils.DH));
            //订单备注
            String remark = "";
            remark = orderModel.getRemark() == null ? "" : orderModel.getRemark();
            retMap.put("remark", remark);
            //退款状态
            retMap.put("returnStatus", publicRefundService.getRefundStatus(store_id, sNo));
        } catch (Exception e) {
            logger.error("获取订单详情 异常", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "获取订单详情失败", "orderPcDetails");
        }
        return retMap;
    }

    @Override
    public Map<String, Object> getPaymentConfig(int storeId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            List<Map<String, Object>> resultPayConfigInfoMap = paymentConfigModelMapper.getPaymentConfigDynamic(parmaMap);
            for (Map<String, Object> map : resultPayConfigInfoMap) {
                String key = map.get("class_name").toString();
                resultMap.put(key, map.get("status").toString());
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取支付配置信息 异常:{}", e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "获取支付配置信息");
        }
    }

    @Override
    public OrderModel getOrderInfoById(int storeId, Integer id, String orderno) throws LaiKeApiException {
        OrderModel orderModel = new OrderModel();
        try {
            if (id != null) {
                orderModel = orderModelMapper.selectByPrimaryKey(id);
            } else {
                orderModel.setStore_id(storeId);
                orderModel.setsNo(orderno);
                orderModel = orderModelMapper.selectOne(orderModel);
            }
            return orderModel;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取订单信息 异常:{}", e.getMessage());
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常");
        }
    }

    @Override
    public OrderModel getOrderInfoByDetailId(int storeId, int did) throws LaiKeApiException {
        OrderModel orderModel = new OrderModel();
        try {
            OrderDetailsModel orderDetailsModel = orderDetailsModelMapper.selectByPrimaryKey(did);
            if (orderDetailsModel == null) {
                throw new LaiKeApiException(DATA_NOT_EXIST, "订单数据异常");
            }
            orderModel.setStore_id(storeId);
            orderModel.setsNo(orderDetailsModel.getR_sNo());
            orderModel = orderModelMapper.selectOne(orderModel);

            return orderModel;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取订单信息 异常:{}", e.getMessage());
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常");
        }
    }

    @Override
    public Map<String, Object> getSettlementOrderList(OrderSettlementVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (vo.getMchId() != null) {
                parmaMap.put("mchId", vo.getMchId());
            }
            parmaMap.put("add_time_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("start", vo.getPageNo());
            parmaMap.put("pagesize", vo.getPageSize());
            //是否结算
            if (vo.getStatus() != null) {
                if (vo.getStatus() == 1) {
                    //已结算
                    parmaMap.put("settlement_status", OrderDetailsModel.SETTLEMENT_TYPE_SETTLED);
                } else {
                    //未结算
                    parmaMap.put("settlement_status", OrderDetailsModel.SETTLEMENT_TYPE_UNSETTLED);
                }
            }

            parmaMap.put("orderId", vo.getId());
            parmaMap.put("mch_id", vo.getId());
            if (StringUtils.isNotEmpty(vo.getSearch())) {
                parmaMap.put("search", vo.getSearch());
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("settlement_StartDate", vo.getStartDate());
            }
            if (StringUtils.isNotEmpty(vo.getEndDate())) {
                parmaMap.put("settlement_EndDate", vo.getEndDate());
            }
            if (StringUtils.isNotEmpty(vo.getMchName())) {
                parmaMap.put("mch_name", vo.getMchName());
            }
            //订单类型
            if (StringUtils.isNotEmpty(vo.getOrderType())) {
                parmaMap.put("orderType", vo.getOrderType());
            }
            parmaMap.put("group_sNo", "group_sNo");

            int total = orderModelMapper.countAdminOrderList(parmaMap);
            List<Map<String, Object>> orderList = new ArrayList<>();
            if (total > 0) {
                orderList = orderModelMapper.adminOrderList(parmaMap);
                for (Map<String, Object> map : orderList) {
                    String orderType = MapUtils.getString(map, "otype");
                    String orderNo = MapUtils.getString(map, "sNo");
                    String couponPrice = map.get("coupon_price") + "";
                    if (StringUtils.isEmpty(couponPrice)) {
                        couponPrice = "0";
                    }
                    //是否结算标识settlement_status
                    Integer isSettlement = MapUtils.getInteger(map, "settlement_status");
                    int status = MapUtils.getIntValue(map, "status");
                    //佣金
                    BigDecimal commission = new BigDecimal("0");
                    //退还佣金
                    BigDecimal rcommission = new BigDecimal("0");

                    //退款金额
                    BigDecimal returnAmt = returnOrderModelMapper.getReturnAmtByOrder(orderNo);
                    map.put("commission", commission);
                    map.put("r_commission", rcommission);
                    map.put("return_money", returnAmt);
                    //优惠金额(该金额是总优惠券金额 平台+店铺)
                    BigDecimal mchDisAmt = new BigDecimal(couponPrice);
                    //平台优惠金额
                    BigDecimal dicPrice = new BigDecimal(map.get("preferential_amount").toString());
                    if (BigDecimal.ZERO.compareTo(dicPrice) < 0) {
                        //如果用了平台优惠券 则店铺优惠券 = 总优惠券金额-平台优惠金额
                        mchDisAmt = mchDisAmt.subtract(dicPrice);
                    }
                    map.put("mch_discount", mchDisAmt);
                    map.put("preferential_amount", dicPrice);
                    map.put("arrive_time", MapUtils.getString(map, "arrive_time"));

                    BigDecimal settlementPrice = BigDecimal.ZERO;
                    String statusName = "待结算";
                    if (OrderDetailsModel.SETTLEMENT_TYPE_SETTLED.equals(isSettlement)) {
                        statusName = "已结算";
                        //结算金额 订单金额就是结算金额
                        settlementPrice = new BigDecimal(MapUtils.getString(map, "z_price"));
                    }
                    map.put("settlementPrice", settlementPrice);
                    map.put("status_name", statusName);
                }
            }

            resultMap.put("list", orderList);
            resultMap.put("total", total);
        } catch (Exception e) {
            logger.error("获取订单信息 异常:", e);
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getOrderConfig(int storeId, String oType) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            int autoTeGood = DateUtil.dateConversion(7, DateUtil.TimeType.DAY);
            //订单失效
            //自动收货时间
            int orderFailureDay = DateUtil.dateConversion(1, DateUtil.TimeType.DAY);
            //发货提醒限制
            int remind = DateUtil.dateConversion(1, DateUtil.TimeType.TIME);
            //定时好评
            int commentDay = DateUtil.dateConversion(7, DateUtil.TimeType.DAY);
            //订单售后时间
            int orderAfter = DateUtil.dateConversion(7, DateUtil.TimeType.DAY);
            switch (oType.toUpperCase()) {
                //获取订单配置信息 普通订单设置单位是天 插件都是秒
                case DictionaryConst.OrdersType.ORDERS_HEADER_GM:
                    OrderConfigModal orderConfigModal = new OrderConfigModal();
                    orderConfigModal.setStore_id(storeId);
                    orderConfigModal = orderConfigModalMapper.selectOne(orderConfigModal);
                    if (orderConfigModal != null) {
                        autoTeGood = DateUtil.dateConversion(orderConfigModal.getAuto_the_goods(), DateUtil.TimeType.DAY);
                        orderFailureDay = DateUtil.dateConversion(orderConfigModal.getOrder_failure(), DateUtil.TimeType.TIME);
                        remind = DateUtil.dateConversion(orderConfigModal.getRemind(), DateUtil.TimeType.TIME);
                        commentDay = DateUtil.dateConversion(orderConfigModal.getAuto_good_comment_day(), DateUtil.TimeType.DAY);
                        orderAfter = DateUtil.dateConversion(orderConfigModal.getOrder_after(), DateUtil.TimeType.DAY);
                    }
                    break;
                default:
                    return null;
            }

            resultMap.put("remind", remind);
            resultMap.put("commentDay", commentDay);
            resultMap.put("autoTeGood", autoTeGood);
            resultMap.put("orderAfter", orderAfter);
            resultMap.put("orderFailureDay", orderFailureDay);
        } catch (Exception e) {
            logger.error("根据订单类型获取订单配置 异常:", e);
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常");
        }
        return resultMap;
    }

    @Autowired
    private PublicPaymentConfigService publicPaymentConfigService;

    @Autowired
    private ExpressModelMapper expressModelMapper;

    @Autowired
    private MessageLoggingModalMapper messageLoggingModalMapper;

    @Autowired
    private FreightModelMapper freightModelMapper;

    @Autowired
    private AdminCgModelMapper adminCgModelMapper;

    @Autowired
    private OrderDataModelMapper orderDataModelMapper;

    @Autowired
    private OrderConfigModalMapper orderConfigModalMapper;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private ReturnOrderModelMapper returnOrderModelMapper;

    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private PublicMemberService publicMemberService;

    @Autowired
    private MchStoreModelMapper mchStoreModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private ServiceAddressModelMapper serviceAddressModelMapper;

    @Autowired
    private CommentsModelMapper commentsModelMapper;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private ReturnGoodsModelMapper returnGoodsModelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;

    @Autowired
    private PaymentModelMapper paymentModelMapper;

    @Autowired
    private PublicMchService publicMchService;

    @Autowired
    private CouponActivityModelMapper couponActivityModelMapper;

    @Autowired
    private PublicAdminRecordService publicAdminRecordService;

    @Autowired
    private DictionaryNameModelMapper dictionaryNameModelMapper;

    @Autowired
    private PublicRefundService publicRefundService;

    @Autowired
    private SystemMessageModelMapper systemMessageModelMapper;

    @Autowired
    private ConfigModelMapper configModelMapper;


    @Autowired
    @Qualifier("publicWechatServiceImpl")
    private PublicPaymentService publicPaymentService;

}
