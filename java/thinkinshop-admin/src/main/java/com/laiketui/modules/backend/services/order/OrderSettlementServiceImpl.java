package com.laiketui.modules.backend.services.order;

import com.laiketui.api.common.PublicOrderService;
import com.laiketui.api.modules.backend.order.OrderSettlementService;
import com.laiketui.common.mapper.OrderDetailsModelMapper;
import com.laiketui.common.mapper.OrderModelMapper;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.domain.vo.order.AdminOrderDetailVo;
import com.laiketui.domain.vo.order.OrderSettlementVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单结算
 *
 * @author Trick
 * @date 2021/7/7 11:31
 */
@Service
public class OrderSettlementServiceImpl implements OrderSettlementService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private PublicOrderService publicOrderService;

    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;

    @Override
    public Map<String, Object> index(OrderSettlementVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            resultMap = publicOrderService.getSettlementOrderList(vo);
            if (vo.getExportType() == 1) {
                exportOrderData(DataUtils.cast(resultMap.get("list")), response);
                return null;
            }
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单结算列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
    }

    /**
     * 导出订单结算
     *
     * @param goodsList -
     * @param response  -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/29 10:22
     */
    private void exportOrderData(List<Map<String, Object>> goodsList, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"结算单号", "结算金额", "佣金", "退还佣金", "订单编号", "订单金额", "店铺名称", "退单金额", "结算状态", "结算时间", "运费", "店铺优惠"
                    , "平台优惠", "订单生成时间"};
            //对应字段
            String[] kayList = new String[]{"id", "settlementPrice", "commission", "r_commission", "sNo", "z_price", "shopName", "return_money", "status_name", "arrive_time", "z_freight", "mch_discount"
                    , "preferential_amount", "add_time"};
            //对应字段
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("订单结算列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(goodsList);
            vo.setResponse(response);
            vo.setNeedNo(false);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出订单结算 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportOrderData");
        }
    }

    @Override
    public Map<String, Object> detail(MainVo vo, String orderNo) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            AdminOrderDetailVo adminOrderDetailVo = new AdminOrderDetailVo();
            adminOrderDetailVo.setStoreId(vo.getStoreId());
            adminOrderDetailVo.setId(orderNo);
            adminOrderDetailVo.setOrderType("see");
            return publicOrderService.orderPcDetails(adminOrderDetailVo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单结算列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(MainVo vo, int id) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            OrderModel orderModel = orderModelMapper.selectByPrimaryKey(id);
            if (orderModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "订单不存在");
            }
            OrderModel orderUpdate = new OrderModel();
            orderUpdate.setId(id);
            orderUpdate.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
            row = orderModelMapper.updateByPrimaryKeySelective(orderUpdate);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "删除失败");
            }
            row = orderDetailsModelMapper.delOrderDetails1(vo.getStoreId(), DictionaryConst.ProductRecycle.RECOVERY, orderModel.getsNo());
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "删除失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除结算订单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "del");
        }
    }
}
