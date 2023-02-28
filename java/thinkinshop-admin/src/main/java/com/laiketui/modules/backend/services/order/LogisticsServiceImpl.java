package com.laiketui.modules.backend.services.order;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.laiketui.api.modules.backend.order.LogisticsService;
import com.laiketui.common.mapper.ExpressModelMapper;
import com.laiketui.common.mapper.OrderDetailsModelMapper;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.domain.order.OrderDetailsModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.okhttp.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 功能：订单物流信息管理服务
 * @author wangxian
 */
@Service
public class LogisticsServiceImpl implements LogisticsService {

    private final Logger logger = LoggerFactory.getLogger(LogisticsServiceImpl.class);

    @Override
    public Map<String, Object> getLogistics(String orderNo) throws LaiKeApiException {
        Map retMap = Maps.newHashMap();
        Map retMapList = Maps.newHashMap();
        try {
            // 获取信息
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setR_sNo(orderNo);
            List<OrderDetailsModel> orderDetailsModels = orderDetailsModelMapper.select(orderDetailsModel);

            if (!CollectionUtils.isEmpty(orderDetailsModels)) {
                for ( OrderDetailsModel orderDetails : orderDetailsModels )  {
                    int store_id = orderDetails.getStore_id();
                    /**快递公司ID*/
                    Integer express_id = orderDetails.getExpress_id();
                    /**快递单号*/
                    String courierNum = orderDetails.getCourier_num();
                    if (express_id != null && !StringUtils.isEmpty(courierNum))  {
                        ExpressModel expressModel = new ExpressModel();
                        expressModel.setId(express_id);
                        expressModel = expressModelMapper.selectOne(expressModel);
                        //快递公司代码
                        String type = expressModel.getType();
                        String kuaidiName = expressModel.getKuaidi_name();
                        String url = String.format(GloabConst.OtherUrl.API_KUAIDI100_URL, type, courierNum);
                        String resultJson = HttpUtils.get(url);
                        JSONObject obj = JSONObject.parseObject(resultJson);
                        if (obj.containsKey("data")) {
                            retMap.put("code","1");
                            retMap.put("data",obj.get("data"));
                        } else {
                            retMap.put("code","0");
                            retMap.put("data",new ArrayList<>());
                        }

                        retMap.put("kuaidi_name",kuaidiName);
                        retMap.put("courier_num",courierNum);
                    } else {
                        retMap.put("code","0");
                        retMap.put("data",new ArrayList<>());
                    }
                    ((List)retMapList.get("list")).add(retMap);
                }
            }
            if (retMapList.size() == 0) {
                retMapList.put("code",0);
            } else {
                retMapList.put("code",1);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("获取订单物流信息失败，{}",e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.ABNORMAL_BIZ, "获取物流信息失败", "getLogistics");
        }
        return retMapList;
    }

    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;

    @Autowired
    private ExpressModelMapper expressModelMapper;


}
