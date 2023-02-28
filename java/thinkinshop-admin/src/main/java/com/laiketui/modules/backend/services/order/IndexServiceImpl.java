package com.laiketui.modules.backend.services.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import com.laiketui.api.common.*;
import com.laiketui.api.modules.backend.order.IndexService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.domain.log.AdminRecordModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.MchBrowseModel;
import com.laiketui.domain.order.OrderDetailsModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.product.StockModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.domain.vo.goods.AddStockVo;
import com.laiketui.domain.vo.order.*;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.algorithm.DataAlgorithmTool;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.GoodsDataUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

import static com.laiketui.domain.order.OrderModel.ORDER_CLOSE;
import static com.laiketui.root.consts.DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CLOSE;
import static com.laiketui.root.consts.ErrorCode.BizErrorCode.ORDER_DATA_ERROR;

/**
 * 功能：管理后台订单管理功能接口
 *
 * @author wangxian
 */
@Service("orderIndexService")
public class IndexServiceImpl implements IndexService {

    private final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> index(AdminOrderListVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            if (vo.getSelfLifting() == null) {
                vo.setSelfLifting(1);
            }
            resultMap = publicOrderService.pcMchOrderIndex(vo);
            if (vo.getExportType().equals(1)) {
                exportOrderData(DataUtils.cast(resultMap.get("list")), response);
                return null;
            }
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("获取订单列表 异常：", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> kuaidishow(MainVo vo, String orderNo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            resultMap = publicOrderService.getLogistics(orderNo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取订单物流信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "kuaidishow");
        }
        return resultMap;
    }

    //导出订单列表
    private void exportOrderData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"订单编号", "创单时间", "产品名称", "规格", "数量", "小计", "订单总计", "数量", "订单状态", "订单类型", "会员ID", "联系人"
                    , "电话", "地址", "支付方式", "物流单号", "运费"};
            //对应字段
            String[] kayList = new String[]{"orderno", "createDate", "goodsName", "attrStr", "needNum", "goodsPrice", "orderPrice", "goodsNum", "status", "otype", "userId", "userName"
                    , "mobile", "addressInfo", "payName", "courier_num", "freight"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("订单列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出订单列表数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportOrderData");
        }
    }

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> orderCount(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("status", DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);

            //统计代发货的订单数量
            Map<String, Object> parmaOrderMap = new HashMap<>(16);
            parmaOrderMap.putAll(parmaMap);
            parmaOrderMap.put("orderType", DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            int orderNum = orderModelMapper.countAdminOrderList(parmaOrderMap);
            //统计待发货 实物 订单
            Map<String, Object> parmaMap1 = new HashMap<>(16);
            parmaMap1.putAll(parmaMap);
            parmaMap1.put("orderType", DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            parmaMap1.put("self_lifting", 0);
            int shiWuNum = orderModelMapper.countAdminOrderList(parmaMap1);
            //统计 活动 订单
            Map<String, Object> parmaMap2 = new HashMap<>(16);
            parmaMap2.putAll(parmaMap);
            List<String> list = new ArrayList<>();
            list.add(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            list.add(DictionaryConst.OrdersType.ORDERS_HEADER_VI);
            parmaMap2.put("self_lifting", 0);
            parmaMap2.put("orderTypeList_not", list);
            int activityNum = orderModelMapper.countAdminOrderList(parmaMap2);

            //统计 退货列表 订单
            int returnNum = returnOrderModelMapper.countOrderReturnWaitByStoreStatus(vo.getStoreId(), DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_EXAMEWAIT_STATUS, DictionaryConst.OrdersType.ORDERS_HEADER_GM);

            //统计秒杀订单
            OrderModel orderSecCount = new OrderModel();
            orderSecCount.setStore_id(vo.getStoreId());
            orderSecCount.setOtype(DictionaryConst.OrdersType.ORDERS_HEADER_MS);
            orderSecCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            orderSecCount.setStatus(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);
            int secOrderNum = orderModelMapper.selectCount(orderSecCount);
            //统计积分订单
            orderSecCount.setOtype(DictionaryConst.OrdersType.ORDERS_HEADER_IN);
            int inOrderNum = orderModelMapper.selectCount(orderSecCount);

            resultMap.put("orderNum", orderNum);
            resultMap.put("shiWuNum", shiWuNum);
            resultMap.put("activityNum", activityNum);
            resultMap.put("returnNum", returnNum);
            resultMap.put("secOrderNum", secOrderNum);
            resultMap.put("inOrderNum", inOrderNum);
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("订单统计 异常：", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "orderCount");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> close(AdminOrderVo orderVo) throws LaiKeApiException {
        Map returnMap = Maps.newHashMap();
        try {
            int storeId = orderVo.getStoreId();
            String sNo = orderVo.getOid();
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setR_status(ORDERS_R_STATUS_CLOSE);
            Map params = Maps.newHashMap();
            params.put("status", ORDER_CLOSE);
            params.put("storeId", storeId);
            params.put("orderno", sNo);
            int row = orderModelMapper.updateOrderInfo(params);
            if (row < 1) {
                logger.info(sNo + "修改订单状态失败:");
            }
            int row1 = orderDetailsModelMapper.updateOrderDetailsStatus(storeId, sNo, ORDER_CLOSE);
            logger.info(sNo + "订单关闭:");
            if (row1 < 1) {
                logger.info("sNo 修改订单状态失败！");
            }
            orderDetailsModel.setStore_id(storeId);
            orderDetailsModel.setR_sNo(sNo);
            List<OrderDetailsModel> orderDetailsModels = orderDetailsModelMapper.select(orderDetailsModel);
            for (OrderDetailsModel orderDetailsInfo : orderDetailsModels) {
                int pid = orderDetailsInfo.getP_id();
                int goodsNum = orderDetailsInfo.getNum();
                String attributeId = orderDetailsInfo.getSid();
                ConfiGureModel confiGureModel = new ConfiGureModel();
                confiGureModel.setId(Integer.valueOf(attributeId));
                confiGureModel.setPid(pid);
                confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                int totalNum = confiGureModel.getNum();
                row = productListModelMapper.addGoodsStockNum(pid, goodsNum);
                if (row < 1) {
                    logger.info("修改商品库存失败！");
                }
                confiGureModel.setNum(goodsNum);
                confiGureModel.setTotal_num(0);
                row = confiGureModelMapper.addGoodsAttrStockNum(confiGureModel);
                if (row < 1) {
                    logger.info("修改商品属性库存失败！");
                }
                String content = " 管理员关闭订单【" + orderDetailsInfo.getR_sNo() + "】，返还库存" + goodsNum;
                StockModel stockModel = new StockModel();
                stockModel.setStore_id(storeId);
                stockModel.setProduct_id(pid);
                stockModel.setAttribute_id(Integer.valueOf(attributeId));
                stockModel.setTotal_num(totalNum);
                stockModel.setFlowing_num(goodsNum);
                stockModel.setType(0);
                stockModel.setUser_id(orderDetailsInfo.getUser_id());
                stockModel.setAdd_date(new Date());
                stockModel.setContent(content);
                stockModelMapper.insert(stockModel);

                //记录日志
                String adminName = orderVo.getAdminName();
                AdminRecordModel adminRecordModel = new AdminRecordModel();
                adminRecordModel.setAdmin_name(adminName);
                adminRecordModel.setStore_id(storeId);
                adminRecordModel.setType(11);
                adminRecordModel.setEvent(adminName + ",关闭了订单。");
                publicAdminRecordService.adminRecode(adminRecordModel);
            }
            returnMap.put("code", 200);
            return returnMap;
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("关闭订单失败：{}", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "close");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> del(MainVo vo, String orders) throws LaiKeApiException {
        Map<String, Object> returnMap = Maps.newHashMap();
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            String adminName = adminModel.getName();
            int storeId = vo.getStoreId();
            String[] orderList = orders.split(SplitUtils.DH);
            for (String orderNo : orderList) {
                int row = orderModelMapper.delOrderById(orderNo);

                AdminRecordModel adminRecordModel = new AdminRecordModel();
                adminRecordModel.setAdmin_name(adminName);
                adminRecordModel.setStore_id(storeId);
                adminRecordModel.setType(11);
                StringBuilder text = new StringBuilder(adminName + ",关闭订单:" + orderNo);
                if (row < 1) {
                    text.append("【失败】");
                }
                adminRecordModel.setEvent(text.toString());
                publicAdminRecordService.adminRecode(adminRecordModel);
            }
            return returnMap;
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除订单失败：{}", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "del");
        }
    }

    @Override
    public List<Map<String, Object>> orderPrint(AdminOrderVo orderVo) throws LaiKeApiException {
        Map returnMap = Maps.newHashMap();
        try {
            //订单号
            String sNos = orderVo.getsNo();
            String[] orderNos = sNos.split(SplitUtils.DH);
            List<Map<String, Object>> orderDetailsInfo = new ArrayList<>();
            for (String sNo : orderNos) {
                AdminOrderDetailVo adminOrderDetailVo = new AdminOrderDetailVo();
                adminOrderDetailVo.setStoreId(orderVo.getStoreId());
                adminOrderDetailVo.setId(sNo);
                Map<String, Object> tmpOrderDetails = publicOrderService.orderPcDetails(adminOrderDetailVo);
                Map tmpMap = Maps.newHashMap();
                tmpMap.put("list", tmpOrderDetails);
                orderDetailsInfo.add(tmpMap);
            }
            return orderDetailsInfo;
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("打印订单失败", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "orderPrint");
        }
    }

    /**
     * 获取快递公司信息
     *
     * @param express
     * @return
     * @throws LaiKeApiException
     */
    @Override
    public Map<String, Object> searchExpress(String express) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> paramMap = new HashMap<>(16);
            paramMap.put("is_open", 1);
            paramMap.put("sort_sort", DataUtils.Sort.DESC.toString());
            if (StringUtils.isNotEmpty(express)) {
                paramMap.put("kuaidi_name", express);
            }

            int total = expressModelMapper.countDynamic(paramMap);
            List<Map<String, Object>> expressModelList = new ArrayList<>();
            if (total > 0) {
                expressModelList = expressModelMapper.selectDynamic(paramMap);
            }
            resultMap.put("total", total);
            resultMap.put("list", expressModelList);
            return resultMap;
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("获取快递信息失败：", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "searchExpress");
        }
    }

    @Override
    public Map<String, Object> deliveryView(AdminDeliveryVo adminDeliveryVo) throws LaiKeApiException {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            int storeId = adminDeliveryVo.getStoreId();
            String sNo = adminDeliveryVo.getsNo();
            List<Map<String, Object>> ordersList = orderModelMapper.getDeleiveryOrders(storeId, sNo);
            // todo 未知变量
            int put = 1;
            List<Map<String, Object>> returnGoodsList = new ArrayList<>();
            for (Map<String, Object> orderInfo : ordersList) {
                orderInfo.put("imgurl", publicService.getImgPath(MapUtils.getString(orderInfo, "imgurl"), storeId));
                int rstatus = MapUtils.getIntValue(orderInfo, "r_status");
                if (rstatus == 1) {
                    put = 0;
                }

                returnGoodsList.add(orderInfo);
            }
            retMap.put("express", searchExpress(null));
            retMap.put("id", sNo);
            retMap.put("put", put);
            retMap.put("goods", returnGoodsList);
            retMap.put("count", returnGoodsList.size());
            return retMap;
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("获取发货信息失败", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "deliveryView");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deliverySave(MainVo vo, Integer exId, String exNo, String orderDetailIds) throws LaiKeApiException {
        try {
            // 请选择快递公司
            if (exId == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择快递公司");
            }
            if (StringUtils.isEmpty(exNo)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请输入快递单号");
            }
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setExpress_id(exId);
            orderDetailsModel.setCourier_num(exNo);
            int count = orderDetailsModelMapper.selectCount(orderDetailsModel);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "快递单号已存在");
            }

            publicOrderService.adminDelivery(vo, exId, exNo, orderDetailIds);
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("订单发货 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "orderPrint");
        }

    }

    @Override
    public Map<String, Object> editOrderView(OrderModifyVo orderVo) throws LaiKeApiException {
        Map<String, Object> retMap = Maps.newConcurrentMap();
        try {
            // 订单id sno
            String sNo = orderVo.getsNo();
            AdminOrderDetailVo adminOrderDetailVo = new AdminOrderDetailVo();
            adminOrderDetailVo.setId(sNo);
            adminOrderDetailVo.setsNo(sNo);
            adminOrderDetailVo.setOrderType("modify");
            adminOrderDetailVo.setType(orderVo.getType());
            adminOrderDetailVo.setStoreId(orderVo.getStoreId());
            Map<String, Object> adminOrderDetailsMap = publicOrderService.orderPcDetails(adminOrderDetailVo);
            List<ExpressModel> expressModels = publicExpressServicer.getExpressInfo();
            adminOrderDetailsMap.put("express", expressModels);
            retMap.putAll(adminOrderDetailsMap);
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("订单编辑失败", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "订单详情获取失败", "editOrderView");
        }
        return retMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveEditOrder(EditOrderVo orderVo) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(orderVo.getAccessId(), redisUtil);
            if (StringUtils.isEmpty(orderVo.getOrderNo())) {
                throw new LaiKeApiException(ORDER_DATA_ERROR, "订单编辑失败");
            }
            //获取订单信息
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(orderVo.getStoreId());
            orderModel.setsNo(orderVo.getOrderNo());
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "订单不存在");
            }
            if (DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT != orderModel.getStatus()
                    && DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID != orderModel.getStatus()) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "订单已发货,不能进行修改");
            }

            OrderModel orderUpdate = new OrderModel();
            orderUpdate.setId(orderModel.getId());
            orderUpdate.setMobile(orderVo.getTel());
            orderUpdate.setSheng(orderVo.getShen());
            orderUpdate.setShi(orderVo.getShi());
            orderUpdate.setXian(orderVo.getXian());
            orderUpdate.setName(orderVo.getUserName());
            orderUpdate.setAddress(orderVo.getAddress());
            orderUpdate.setRemarks(orderVo.getRemarks());
            //修改订单信息
            if (DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID == orderModel.getStatus()) {
                //订单状态（只能修改成待发货）
                if (DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT == orderVo.getOrderStatus()) {
                    orderUpdate.setStatus(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);
                    //修改订单明细状态
                    row = orderDetailsModelMapper.updateOrderDetailsStatus(orderVo.getStoreId(), orderVo.getOrderNo(), orderUpdate.getStatus());
                    if (row < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败");
                    }
                }
                //未付款可以修改订单金额、订单状态（只能修改成待发货）
                if (StringUtils.isNotEmpty(orderVo.getOrderAmt())) {
                    orderUpdate.setZ_price(new BigDecimal(orderVo.getOrderAmt()));
                    //修改订单金额后需要记录差额
                    BigDecimal priceDiff = orderModel.getZ_price().subtract(orderUpdate.getZ_price());
                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                    orderDetailsModel.setStore_id(orderVo.getStoreId());
                    orderDetailsModel.setR_sNo(orderVo.getOrderNo());
                    List<OrderDetailsModel> orderDetailsModelList = orderDetailsModelMapper.select(orderDetailsModel);
                    for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                        //该单均摊差额
                        BigDecimal updatePrice = orderDetails.getAfter_discount().add(orderDetails.getFreight().divide(orderModel.getZ_price(), BigDecimal.ROUND_UP).multiply(priceDiff));
                        //修改订单明细金额
                        OrderDetailsModel orderDetailsUpdate = new OrderDetailsModel();
                        orderDetailsUpdate.setId(orderDetails.getId());
                        orderDetailsUpdate.setAfter_discount(orderDetails.getAfter_discount().subtract(updatePrice));
                        row = orderDetailsModelMapper.updateByPrimaryKeySelective(orderDetailsUpdate);
                        if (row < 1) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败");
                        }
                    }
                }
            }
            //修改订单基本信息
            row = orderModelMapper.updateByPrimaryKeySelective(orderUpdate);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单编辑失败", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "订单编辑失败", "saveEditOrder");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> helpOrder(HelpOrderVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            int row;
            //商品信息
            List<Map<String, Object>> goodsList;
            try {
                goodsList = JSON.parseObject(URLDecoder.decode(vo.getProducts(), GloabConst.Chartset.UTF_8), new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (Exception e) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商品参数错误");
            }
            //会员折扣
            BigDecimal grateDis;
            //获取会员信息
            User user = new User();
            user.setStore_id(vo.getStoreId());
            user.setUser_id(vo.getUserId());
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "会员不存在");
            }
            grateDis = BigDecimal.valueOf(publicMemberService.getMemberGradeRate(DictionaryConst.OrdersType.ORDERS_HEADER_GM, vo.getUserId(), vo.getStoreId()));
            //获取会员默认地址
            UserAddress userAddress = publicAddressService.findAddress(vo.getStoreId(), user.getUser_id(), null);
            if (userAddress == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请选择收货地址");
            }
            if (StringUtils.isEmpty(vo.getWipeOff())) {
                vo.setWipeOff("0");
            }

            OrderModel orderSave = new OrderModel();
            orderSave.setStore_id(vo.getStoreId());
            //生成订单号
            String orderNo = publicOrderService.createOrderNo(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            orderSave.setsNo(orderNo);
            //生产支付订单号
            String sealNo = publicOrderService.createOrderNo(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            orderSave.setReal_sno(sealNo);
            //订单商品总价
            BigDecimal orderGoodsPrice = new BigDecimal("0");
            //商品所属店铺
            Set<Integer> goodsMchIds = new HashSet<>();
            //订单商品个数
            int orderNeedNum = 0;

            for (Map<String, Object> goods : goodsList) {
                int needNum = MapUtils.getIntValue(goods, "num");
                int attrId = MapUtils.getIntValue(goods, "id");
                ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(attrId);
                BigDecimal goodsAmt = confiGureModel.getPrice().multiply(new BigDecimal(needNum));
                orderGoodsPrice = orderGoodsPrice.add(goodsAmt);
            }

            //获取商品信息
            for (Map<String, Object> goods : goodsList) {
                int needNum = MapUtils.getIntValue(goods, "num");
                int attrId = MapUtils.getIntValue(goods, "id");
                int goodsId = MapUtils.getIntValue(goods, "pid");
                String goodsName;
                BigDecimal goodsAttrPrice;
                //手动优惠金额
                BigDecimal manualOffer;
                //优惠金额
                BigDecimal afterDiscount;

                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("store_id", vo.getStoreId());
                parmaMap.put("goodsId", goodsId);
                parmaMap.put("attr_id", attrId);
                List<Map<String, Object>> configureList = confiGureModelMapper.getProductListLeftJoinMchDynamic(parmaMap);
                if (configureList == null || configureList.size() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商品参数错误");
                }
                Map<String, Object> configureMap = configureList.get(0);
                goodsName = MapUtils.getString(configureMap, "product_title");
                //商品单价
                goodsAttrPrice = new BigDecimal(MapUtils.getString(configureMap, "price"));
                //商品总价
                BigDecimal goodsAmt = goodsAttrPrice.multiply(new BigDecimal(needNum));
                //为店铺添加一条购买记录
                MchBrowseModel mchBrowseSave = new MchBrowseModel();
                mchBrowseSave.setStore_id(vo.getStoreId());
                mchBrowseSave.setMch_id(MapUtils.getString(configureMap, "mch_id"));
                mchBrowseSave.setUser_id(user.getUser_id());
                mchBrowseSave.setEvent("购买了商品");
                mchBrowseSave.setAdd_time(new Date());
                mchBrowseModelMapper.insertSelective(mchBrowseSave);
                if (StringUtils.isNotEmpty(mchBrowseSave.getMch_id())) {
                    goodsMchIds.add(Integer.valueOf(mchBrowseSave.getMch_id()));
                }

                //计算会员价
                afterDiscount = goodsAmt.multiply(grateDis);
                //计算手动优惠金额 如果有多个商品则每个商品分摊优惠金额
                BigDecimal tt = DataAlgorithmTool.orderPriceAverage(orderGoodsPrice.multiply(grateDis), afterDiscount, new BigDecimal(vo.getWipeOff()));
                manualOffer = afterDiscount.divide(orderGoodsPrice.multiply(grateDis), 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(vo.getWipeOff()));
                System.out.println(tt);
                System.out.println(manualOffer);
                //记录订单明细
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setStore_id(vo.getStoreId());
                orderDetailsModel.setUser_id(user.getUser_id());
                orderDetailsModel.setP_id(goodsId);
                orderDetailsModel.setP_name(goodsName);
                orderDetailsModel.setP_price(goodsAttrPrice);
                orderDetailsModel.setNum(needNum);
                orderDetailsModel.setUnit(MapUtils.getString(configureMap, "unit"));
                orderDetailsModel.setR_sNo(orderNo);
                orderDetailsModel.setAdd_time(new Date());
                orderDetailsModel.setR_status(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);
                orderDetailsModel.setSize(GoodsDataUtils.getProductSku(MapUtils.getString(configureMap, "attribute")));
                orderDetailsModel.setSid(attrId + "");
                orderDetailsModel.setManual_offer(manualOffer);
                orderDetailsModel.setAfter_discount(manualOffer);
                row = orderDetailsModelMapper.insertSelective(orderDetailsModel);
                if (row < 1) {
                    logger.debug("添加订单详情失败 代客下单失败");
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "下单失败");
                }
                //库存记录
                AddStockVo addStockVo = new AddStockVo();
                addStockVo.setId(attrId);
                addStockVo.setPid(goodsId);
                addStockVo.setAddNum(needNum);
                publicStockService.addGoodsStock(addStockVo, adminModel.getName());
                //预警逻辑
                publicStockService.addStockWarning(vo.getStoreId(), attrId);
            }
            //记录订单主信息
            orderSave.setUser_id(user.getUser_id());
            orderSave.setName(userAddress.getName());
            orderSave.setMobile(userAddress.getTel());
            orderSave.setNum(orderNeedNum);
            orderSave.setManual_offer(new BigDecimal(vo.getWipeOff()));
            orderSave.setZ_price(orderGoodsPrice.multiply(grateDis));
            if (BigDecimal.ZERO.compareTo(orderSave.getZ_price()) > 0) {
                orderSave.setZ_price(BigDecimal.ZERO);
            } else {
                orderSave.setZ_price(orderSave.getZ_price().subtract(orderSave.getManual_offer()));
            }
            orderSave.setSheng(userAddress.getSheng());
            orderSave.setShi(userAddress.getCity());
            orderSave.setXian(userAddress.getQuyu());
            orderSave.setAddress(userAddress.getAddress_xq());
            orderSave.setRemark("");
            orderSave.setPay(DictionaryConst.OrderPayType.ORDERPAYTYPE_WALLET_PAY);
            orderSave.setAdd_time(new Date());
            orderSave.setStatus(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);
            orderSave.setSpz_price(orderGoodsPrice);
            orderSave.setSource(Integer.valueOf(DictionaryConst.StoreSource.LKT_LY_006));
            orderSave.setOtype(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            orderSave.setMch_id(com.laiketui.root.utils.common.StringUtils.stringImplode(new ArrayList<>(goodsMchIds), SplitUtils.DH, true));
            orderSave.setRemarks("");
            orderSave.setGrade_rate(grateDis);
            orderSave.setOperation_type(3);
            orderSave.setPay_time(new Date());
            row = orderModelMapper.insertSelective(orderSave);
            if (row < 1) {
                logger.debug("添加订单失败 代客下单失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "下单失败");
            }
            //支付
            publicUserService.userRechargeMoney(vo.getStoreId(), user.getId(), orderSave.getZ_price().negate(), 1);

            resultMap.put("sNo", orderNo);
            resultMap.put("order_id", orderSave.getId());
            resultMap.put("total", orderSave.getZ_price());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("代客下单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "helpOrder");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> valetOrderSettlement(HelpOrderVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            //商品信息
            List<Map<String, Object>> goodsList;
            try {
                goodsList = JSON.parseObject(URLDecoder.decode(vo.getProducts(), GloabConst.Chartset.UTF_8), new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (Exception e) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商品参数错误");
            }
            //会员折扣
            BigDecimal grateDis = BigDecimal.valueOf(publicMemberService.getMemberGradeRate(DictionaryConst.OrdersType.ORDERS_HEADER_GM, vo.getUserId(), vo.getStoreId()));
            //获取会员信息
            User user = new User();
            user.setStore_id(vo.getStoreId());
            user.setUser_id(vo.getUserId());
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "会员不存在");
            }

            //订单商品总价
            BigDecimal orderGoodsPrice = BigDecimal.ZERO;
            //获取商品信息
            for (Map<String, Object> goods : goodsList) {
                int needNum = MapUtils.getIntValue(goods, "num");
                int attrId = MapUtils.getIntValue(goods, "id");
                int goodsId = MapUtils.getIntValue(goods, "pid");
                //获取商品规格信息
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("store_id", vo.getStoreId());
                parmaMap.put("goodsId", goodsId);
                parmaMap.put("attr_id", attrId);
                List<Map<String, Object>> configureList = confiGureModelMapper.getProductListLeftJoinMchDynamic(parmaMap);
                if (configureList == null || configureList.size() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商品参数错误");
                }
                Map<String, Object> configureMap = configureList.get(0);
                //商品单价
                BigDecimal goodsAttrPrice = new BigDecimal(MapUtils.getString(configureMap, "price"));
                //商品总价
                BigDecimal goodsAmt = goodsAttrPrice.multiply(new BigDecimal(needNum));
                orderGoodsPrice = orderGoodsPrice.add(goodsAmt);
            }
            //会员折扣总额
            BigDecimal vipDiscountPrice = orderGoodsPrice.multiply(grateDis);
            //计算订单金额
            BigDecimal orderPrice = vipDiscountPrice.subtract(new BigDecimal(vo.getWipeOff()));
            if (BigDecimal.ZERO.compareTo(orderPrice) > 0) {
                orderPrice = BigDecimal.ZERO;
            }

            //商品总价
            resultMap.put("goodsPriceTotal", orderGoodsPrice);
            //会员折金额
            resultMap.put("vipDiscountPrice", orderGoodsPrice.subtract(vipDiscountPrice));
            //支付金额
            resultMap.put("payPrice", orderPrice);

            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("代客下单-结算 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "计算失败", "valetOrder");
        }
    }

    @Override
    public Map<String, Object> orderDetailsInfo(AdminOrderDetailVo orderVo) {
        try {
            String sNo = orderVo.getsNo();
            orderVo.setId(sNo);
            orderVo.setsNo(sNo);
            orderVo.setOrderType("see");
            return publicOrderService.orderPcDetails(orderVo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单详情异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "订单编辑失败", "orderDetailsInfo");
        }
    }


    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    private PublicAddressService publicAddressService;

    @Autowired
    private PublicStockService publicStockService;

    @Autowired
    private MchBrowseModelMapper mchBrowseModelMapper;

    @Autowired
    private PublicMemberService publicMemberService;

    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;

    @Autowired
    private PublicAdminRecordService publicAdminRecordService;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private StockModelMapper stockModelMapper;

    @Autowired
    private PublicOrderService publicOrderService;

    @Autowired
    private PubliceService publicService;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private ReturnOrderModelMapper returnOrderModelMapper;

    @Autowired
    private ExpressModelMapper expressModelMapper;

    @Autowired
    private PublicExpressService publicExpressServicer;

}