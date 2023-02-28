package com.laiketui.common.services;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.laiketui.api.common.PublicPaymentConfigService;
import com.laiketui.common.mapper.OrderDataModelMapper;
import com.laiketui.common.mapper.OrderModelMapper;
import com.laiketui.common.mapper.PaymentConfigModelMapper;
import com.laiketui.common.mapper.PaymentModelMapper;
import com.laiketui.domain.order.OrderDataModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.payment.PaymentModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.SerializePhpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

/**
 * 支付配置信息
 *
 * @author wangxian
 */
@Service
public class PublicPaymentConfigServiceImpl implements PublicPaymentConfigService {

    private final Logger logger = LoggerFactory.getLogger(PublicPaymentConfigServiceImpl.class);

    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;

    @Autowired
    private PaymentModelMapper paymentModelMapper;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private OrderDataModelMapper orderDataModelMapper;


    @Override
    public String getPaymentConfig(int storeId, String payType) {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("payType", payType);
            String configStr = paymentConfigModelMapper.getPaymentConfigInfo(storeId, payType);
            configStr = configStr.replaceAll("%2B", "\\+");
            jsonObject.put("payConfig", configStr);
            return jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商城的支付信息失败:{}", e.getMessage());
            throw new LaiKeApiException("", "", "");
        }
    }

    @Override
    public String getPaymentConfigByPayNo(String payNo) {
        try {
            OrderModel orderModel = new OrderModel();
            orderModel.setReal_sno(payNo);
            orderModel = orderModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取支付配置信息失败", "getPaymentConfigByPayNo");
            }
            int storeId = orderModel.getStore_id();
            String payType = orderModel.getPay();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("payType", payType);
            jsonObject.put("storeId", storeId);
            String configStr = paymentConfigModelMapper.getPaymentConfigInfo(storeId, payType);
//            configStr = URLEncoder.encode(configStr, GloabConst.Chartset.UTF_8);
            jsonObject.put("payConfig", configStr);

            return jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商城的支付信息失败:{}", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取支付配置信息失败", "getPaymentConfigByPayNo");
        }
    }

    @Override
    public String getPaymentConfigByMemberPayNo(String payNo) {
        try {
            OrderDataModel orderModel = new OrderDataModel();
            orderModel.setTrade_no(payNo);
            orderModel = orderDataModelMapper.selectOne(orderModel);
            if (orderModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取支付配置信息失败", "getPaymentConfigByPayNo");
            }
            String data = orderModel.getData();
            Map map = SerializePhpUtils.getUnserializeObj(data, Map.class);
            int storeId = DataUtils.getIntegerVal(map, "store_id");
            String payType = DataUtils.getStringVal(map, "pay");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("payType", payType);
            jsonObject.put("storeId", storeId);
            jsonObject.put("payConfig", paymentConfigModelMapper.getPaymentConfigInfo(storeId, payType));
            return jsonObject.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商城的支付信息失败:{}", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取支付配置信息失败", "getPaymentConfigByPayNo");
        }
    }

    @Override
    public List<PaymentModel> getPayment() {
        try {
            return paymentModelMapper.selectAll();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取平台支付信息失败", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取平台支付信息失败", "getPayment");
        }
    }

    @Override
    public Map<String, String> getPaymentMap() {
        Map<String, String> retMap = Maps.newConcurrentMap();
        try {
            List<PaymentModel> paymentModels = paymentModelMapper.selectAll();
            for (PaymentModel paymentModel : paymentModels) {
                retMap.put(paymentModel.getClass_name(), paymentModel.getName());
            }
            return retMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取平台支付信息失败", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "获取平台支付信息失败", "getPayment");
        }
    }

}
