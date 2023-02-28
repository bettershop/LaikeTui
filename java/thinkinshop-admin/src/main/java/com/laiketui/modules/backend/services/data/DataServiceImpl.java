package com.laiketui.modules.backend.services.data;

import com.laiketui.api.common.PubliceService;
import com.laiketui.api.modules.backend.data.DataService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.GoodsDataUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 数据报表实现
 *
 * @author Trick
 * @date 2021/1/12 9:27
 */
@Service
public class DataServiceImpl implements DataService {
    private final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Override
    public Map<String, Object> getAddUserInfo(MainVo vo, String startDate, String endDate) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //列表可以查询任意时间段,图表只能查询一个月的数据
            //没有时间默认七天
            if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
                endDate = DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD);
                Date tempDate = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                startDate = DateUtil.dateFormate(DateUtil.getAddDate(tempDate, -6), GloabConst.TimePattern.YMD);
            } else {
                //如果开始时间大于结束时间
                if (DateUtil.dateCompare(startDate, endDate, GloabConst.TimePattern.YMD)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "开始时间不能大于结束时间");
                }
                //最多只能查询31天的数据
                Date startToDate = DateUtil.dateFormateToDate(startDate, GloabConst.TimePattern.YMD);
                Date endToDate = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                if (startToDate == null || endToDate == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "时间参数不正确");
                }
                int day = DateUtil.getBetweenDate(endToDate.getTime() / 1000, startToDate.getTime() / 1000);
                if (day > 31) {
                    Date tempDate = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                    startDate = DateUtil.dateFormate(DateUtil.getAddDate(tempDate, -31), GloabConst.TimePattern.YMD);
                }
            }
            Date startDateTime = DateUtil.dateFormateToDate(startDate, GloabConst.TimePattern.YMD);
            Date endDateTime = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
            if (startDateTime == null || endDateTime == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "时间参数不正确");
            }
            //计算两个时间差
            long dayNum = DateUtil.dateConversion(endDateTime.getTime() / 1000, startDateTime.getTime() / 1000, DateUtil.TimeType.DAY) + 1;

            //微信
            List<Map<String, Object>> dataWxList = userBaseMapper.getNewAddUserWxInfo(vo.getStoreId(), startDate, endDate);
            //app
            List<Map<String, Object>> dataAppList = userBaseMapper.getNewAddUserAppInfo(vo.getStoreId(), startDate, endDate);
            //pc
            List<Map<String, Object>> dataPcList = userBaseMapper.getNewAddUserPcInfo(vo.getStoreId(), startDate, endDate);

            //构造图表数据结构
            List<Map<String, Object>> resultWxList = new ArrayList<>();
            List<Map<String, Object>> resultAppList = new ArrayList<>();
            List<Map<String, Object>> resultPcList = new ArrayList<>();
            for (int i = 0; i < dayNum; i++) {
                Date currentDate = DateUtil.getAddDate(startDateTime, i);
                String currentDateStr = DateUtil.dateFormate(currentDate, GloabConst.TimePattern.YMD);
                //微信
                resultWxList.add(getData(dataWxList, currentDateStr));
                //app
                resultAppList.add(getData(dataAppList, currentDateStr));
                //pc
                resultPcList.add(getData(dataPcList, currentDateStr));
            }

            resultMap.put("startDate", startDate);
            resultMap.put("endDate", endDate);
            resultMap.put("sum_arr_wx", resultWxList);
            resultMap.put("sum_arr_app", resultAppList);
            resultMap.put("sum_arr_pc", resultPcList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取新增会员列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAddUserInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getAddUserList(MainVo vo, String startDate, String endDate) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //列表可以查询任意时间段,图表只能查询一个月的数据
            //新增会员列表数据
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("startDate", startDate);
            parmaMap.put("endDate", endDate);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("Register_data_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> dataList = userBaseMapper.selectDynamic(parmaMap);
            for (Map<String,Object> map : dataList){
                map.put("user_name", map.get("user_name"));
            }
            int total = userBaseMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取新增用户列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAddUserList");
        }
        return resultMap;
    }

    private Map<String, Object> getData(List<Map<String, Object>> dataList, String currentDateStr) {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("rdate", currentDateStr);
        resultMap.put("sum", 0);
        for (int x = 0; x < dataList.size(); x++) {
            Map<String, Object> map = dataList.get(x);
            String date = MapUtils.getString(map, "rdate");
            if (currentDateStr.equals(date)) {
                //有数据
                resultMap.putAll(map);
                dataList.remove(x);
                return resultMap;
            }
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserDistributionInfo(MainVo vo, String startDate, String endDate) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //没有时间默认七天
            if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
                endDate = DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD);
                Date tempDate = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                startDate = DateUtil.dateFormate(DateUtil.getAddDate(tempDate, -6), GloabConst.TimePattern.YMD);
            }

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("startDate", startDate);
            parmaMap.put("endDate", endDate);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            //微信
            List<Integer> sourceList = new ArrayList<>();
            sourceList.add(Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_001));
            sourceList.add(Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_003));
            sourceList.add(Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_004));
            sourceList.add(Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_005));
            parmaMap.put("sourceList", sourceList);
            int countWxList = userBaseMapper.countDynamic(parmaMap);
            parmaMap.remove("sourceList");
            //app
            parmaMap.put("source", DictionaryConst.StoreSource.LKT_LY_002);
            int countAppList = userBaseMapper.countDynamic(parmaMap);
            //pc
            parmaMap.put("source", DictionaryConst.StoreSource.LKT_LY_006);
            int countPcList = userBaseMapper.countDynamic(parmaMap);

            resultMap.put("startDate", startDate);
            resultMap.put("endDate", endDate);
            resultMap.put("num_wx", countWxList);
            resultMap.put("num_app", countAppList);
            resultMap.put("num_pc", countPcList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取会员分布列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserDistributionInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserConsumptionInfo(MainVo vo, String startDate, String endDate) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("startDate", startDate);
            parmaMap.put("endDate", endDate);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> dataListMap = userBaseMapper.selectUserConsumptionReport(parmaMap);
            int total = userBaseMapper.countUserConsumptionReport(parmaMap);

            resultMap.put("list", dataListMap);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取会员消费报表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserConsumptionInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getOrderReport(MainVo vo, String startDate, String endDate, Integer type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            //没有时间默认七天
            if (type != null && type != 2) {
                //1=今天 2= 本周 3=本月 4=本年
                startDate = DateUtil.getSpanDate(type, true);
                endDate = DateUtil.getSpanDate(type, false);
            } else {
                if (StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
                    //最近7天
                    endDate = DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD);
                    Date tempDate = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                    startDate = DateUtil.dateFormate(DateUtil.getAddDate(tempDate, -6), GloabConst.TimePattern.YMD);
                }
            }

            parmaMap.put("startDate", startDate);
            parmaMap.put("endDate", endDate);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataMap = null;
            List<Map<String, Object>> dataListMap;
            if (type == null) {
                //统计
                dataMap = orderModelMapper.selectOrdersReportDynamic(parmaMap);
                //明细
                parmaMap.put("groupbyDate", "groupby");
                dataListMap = orderModelMapper.selectOrdersReportDynamic(parmaMap);

                Date startDateTime = DateUtil.dateFormateToDate(startDate, GloabConst.TimePattern.YMD);
                Date endDateTime = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                //计算两个时间差
                long dayNum = DateUtil.dateConversion(endDateTime.getTime() / 1000, startDateTime.getTime() / 1000, DateUtil.TimeType.DAY) + 1;
                //没有日期的在用默认代替
                for (int i = 0; i < dayNum; i++) {
                    Date currentDate = DateUtil.getAddDate(startDateTime, i);
                    String currentDateStr = DateUtil.dateFormate(currentDate, GloabConst.TimePattern.YMD);
                    Map<String, Object> resMap = new HashMap<>(16);
                    resMap.put("r_date", currentDateStr);
                    resMap.put("num", 0);
                    resMap.put("z_price", 0);

                    if (i < dataListMap.size()) {
                        Map<String, Object> map = dataListMap.get(i);
                        String date = MapUtils.getString(map, "rdate");
                        if (!currentDateStr.equals(date)) {
                            //没数据
                            dataListMap.add(i, resMap);
                            i++;
                        }
                    } else {
                        dataListMap.add(resMap);
                    }
                }
            } else {
                List<Map<String, Object>> resultOrderList = new ArrayList<>();
                Date startDateTemp = DateUtil.dateFormateToDate(startDate, GloabConst.TimePattern.YMD);
                Date endDateTemp = DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMD);
                if (type != 4) {
                    dataListMap = orderModelMapper.getOrdersNumReportByDay(vo.getStoreId(), null, startDate, endDate);
                    //计算两个时间差
                    long dayNum = DateUtil.dateConversion(endDateTemp.getTime() / 1000, startDateTemp.getTime() / 1000, DateUtil.TimeType.DAY) + 1;
                    for (int i = 0; i < dayNum; i++) {
                        Date currentDate = DateUtil.getAddDate(startDateTemp, i);
                        String currentDateStr = DateUtil.dateFormate(currentDate, GloabConst.TimePattern.YMD);
                        resultOrderList.add(getData(dataListMap, currentDateStr));
                    }
                } else {
                    //统计年，本年按月统计
                    dataListMap = orderModelMapper.getOrdersNumReportByMonth(vo.getStoreId(), null, startDate, endDate);
                    //本年
                    for (int i = 0; i < 12; i++) {
                        Date currentDate = DateUtil.getAddMonth(startDateTemp, i);
                        String currentDateStr = DateUtil.dateFormate(currentDate, GloabConst.TimePattern.YM);
                        resultOrderList.add(getData(dataListMap, currentDateStr));
                    }
                }
                dataListMap = resultOrderList;
            }


            resultMap.put("data", dataMap);
            resultMap.put("list", dataListMap);
            resultMap.put("startDate", startDate);
            resultMap.put("endDate", endDate);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取订单报表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getOrderReport");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getGoodsReport(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //获取普通所有上架的商品数量
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(vo.getStoreId());
            productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
            productListModel.setStatus(DictionaryConst.GoodsStatus.NEW_GROUNDING.toString());
            //获取平台上架数量
            int goodsNum = productListModelMapper.selectCount(productListModel);

            //获取平台对接
            int mchUserNum = mchModelMapper.countMchUserNum(vo.getStoreId());
            //前十商品销售排行
            List<Map<String, Object>> goodsTop10 = productListModelMapper.countGoodsTop10(vo.getStoreId());
            //商品类目入库出库报表
            List<Map<String, Object>> classGoodsReportList = stockModelMapper.getGoodsStockReportByClass(vo.getStoreId());

            resultMap.put("top10", goodsTop10);
            resultMap.put("product_num", goodsNum);
            resultMap.put("customer_num", mchUserNum);
            resultMap.put("stock", classGoodsReportList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品报表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsReport");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getGoodsReportGoodsList(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //预警商品列表
            Integer total = stockModelMapper.countGoodsWarningList(vo.getStoreId());
            List<Map<String, Object>> goodsWarningReportList = stockModelMapper.getGoodsWarningList(vo.getStoreId(), vo.getPageNo(), vo.getPageSize());
            for (Map<String, Object> map : goodsWarningReportList) {
                String attribute = GoodsDataUtils.getProductSkuValue(MapUtils.getString(map, "attribute"));
                map.put("attribute", attribute);
                map.put("img", publiceService.getImgPath(MapUtils.getString(map, "img"), vo.getStoreId()));
            }
            resultMap.put("goodsStock", goodsWarningReportList);
            resultMap.put("total", total == null ? 0 : total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取【商品报表】库存预警商品列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsReport");
        }
        return resultMap;
    }

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private StockModelMapper stockModelMapper;

    @Autowired
    private PubliceService publiceService;
}
