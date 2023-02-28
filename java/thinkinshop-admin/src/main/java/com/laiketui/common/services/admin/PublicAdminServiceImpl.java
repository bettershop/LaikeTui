package com.laiketui.common.services.admin;

import com.laiketui.api.common.admin.PublicAdminService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.JumpPathModel;
import com.laiketui.domain.home.SystemMessageModel;
import com.laiketui.domain.mch.MchBrowseModel;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 公共后台方法
 *
 * @author Trick
 * @date 2021/8/13 17:22
 */
@Service
public class PublicAdminServiceImpl implements PublicAdminService {
    private final Logger logger = LoggerFactory.getLogger(PublicAdminServiceImpl.class);

    @Autowired
    private OrderModelMapper orderModelMapper;
    @Autowired
    private ReturnOrderModelMapper returnOrderModelMapper;
    @Autowired
    private MchBrowseModelMapper mchBrowseModelMapper;
    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private CommentsModelMapper commentsModelMapper;

    @Autowired
    private SystemTellModelMapper systemTellModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Override
    public Map<String, Object> index(MainVo vo, Integer mchId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //今天
            String toDayStartDate = DateUtil.getStartOfDay(new Date());
            String endDate = DateUtil.getEndOfDay(new Date());
            //昨天
            String yesterdayStartDate = DateUtil.getStartOfDay(DateUtil.getAddDate(new Date(), -1));
            String yesterdayEndDate = DateUtil.getEndOfDay(DateUtil.getAddDate(new Date(), -1));
            Date yesterdayStartDay = DateUtil.dateFormateToDate(yesterdayStartDate, GloabConst.TimePattern.YMDHMS);
            Date yesterdayEndDay = DateUtil.dateFormateToDate(yesterdayEndDate, GloabConst.TimePattern.YMDHMS);
            //本月
            String monthStartDayStr = DateUtil.getMonthFirstDay(new Date());
            String monthEndDayStr = DateUtil.getMonthLastDay(new Date());
            BigDecimal monthAmtTotal = new BigDecimal("0");
            Date monthStartDay = DateUtil.dateFormateToDate(monthStartDayStr, GloabConst.TimePattern.YMDHMS);
            Date monthEndDay = DateUtil.dateFormateToDate(monthEndDayStr, GloabConst.TimePattern.YMDHMS);

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (mchId != null) {
                if (mchId == 0) {
                    mchId = null;
                }
                parmaMap.put("mch_id", mchId);
            }

            //今天订单数量
            BigDecimal toDayOrder = orderModelMapper.sumSaleNumByDate(vo.getStoreId(), mchId, DateUtil.dateFormateToDate(toDayStartDate, GloabConst.TimePattern.YMDHMS), DateUtil.dateFormateToDate(endDate, GloabConst.TimePattern.YMDHMS));
            //待付款订单数
            Map<String, Object> parmaNoPayOrderMap = new HashMap<>(16);
            parmaNoPayOrderMap.putAll(parmaMap);
            parmaNoPayOrderMap.put("status", DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID);
            int noPayOrderNum = orderModelMapper.countDynamic(parmaNoPayOrderMap);
            //代发货订单数
            Map<String, Object> parmaNoSendOrderMap = new HashMap<>(16);
            parmaNoSendOrderMap.putAll(parmaMap);
            parmaNoSendOrderMap.put("status", DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);
            int noSendOrderNum = orderModelMapper.countDynamic(parmaNoSendOrderMap);
            //代发货订单数
            int orderReturnWaitNum = returnOrderModelMapper.countOrderReturnWait(vo.getStoreId(), mchId);
            //待评价数量
            int orderNotCommentsNum = commentsModelMapper.countCommentsOrderNum(vo.getStoreId(), mchId);
            //待收货数量
            Map<String, Object> notDeliverMap = new HashMap<>(16);
            notDeliverMap.putAll(parmaMap);
            notDeliverMap.put("status", DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED);
            int notDeliverNum = orderModelMapper.countDynamic(notDeliverMap);

            //订单统计相关数据
            int[] countOrderList = {toDayOrder.intValue(), noPayOrderNum, noSendOrderNum, orderReturnWaitNum, orderNotCommentsNum, notDeliverNum};
            //统计店铺总销售额
            BigDecimal countMchOrderSale = orderModelMapper.countMchOrderSale(vo.getStoreId(), mchId);
            //统计店铺总访问量
            int countMchBrowseNum;
            MchBrowseModel mchBrowseModel = new MchBrowseModel();
            mchBrowseModel.setStore_id(vo.getStoreId());
            if (mchId != null) {
                mchBrowseModel.setMch_id(mchId.toString());
            }
            countMchBrowseNum = mchBrowseModelMapper.countMchBrowseNum(mchBrowseModel);
            //统计店铺总支付订单量
            int countMchOrderNum = orderModelMapper.countMchOrderNum(vo.getStoreId(), mchId);
            //本月订单数量
            Map<String, Object> countOrderNumMap = new HashMap<>(16);
            countOrderNumMap.putAll(parmaMap);
            countOrderNumMap.put("statusList", new Integer[]{DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT, DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED, DictionaryConst.OrdersStatus.ORDERS_R_STATUS_COMPLETE});
            countOrderNumMap.put("startDate", monthStartDay);
            countOrderNumMap.put("endDate", monthEndDay);
            int monthOrderNum = orderModelMapper.countDynamic(countOrderNumMap);
            //昨日订单数
            countOrderNumMap.put("startDate", yesterdayStartDate);
            countOrderNumMap.put("endDate", yesterdayEndDate);
            int yesterdayOrderNum = orderModelMapper.countDynamic(countOrderNumMap);

            //总支付订单数量 数据图表(月表)
            List<Map<String, String>> getOrderNumByDayList = orderModelMapper.getOrderNumByDay(vo.getStoreId(), mchId, monthStartDayStr, monthEndDayStr);
            //获取转换率
            BigDecimal conversion = new BigDecimal("0");
            if (mchId != null) {
                int countOrderUserNum = orderModelMapper.countOrderUserNum(vo.getStoreId(), mchId);
                //如果没有访问量，但是有人买,代表转换率是100
                if (countMchBrowseNum == 0 && countOrderUserNum > 0) {
                    conversion = new BigDecimal("100");
                } else {
                    BigDecimal userNum = new BigDecimal(countOrderUserNum + "");
                    BigDecimal browseNum = new BigDecimal(countMchBrowseNum + "");
                    if (browseNum.compareTo(BigDecimal.ZERO) > 0) {
                        conversion = userNum.divide(browseNum, 6, BigDecimal.ROUND_FLOOR).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
                    }
                }
            }
            //日均销售额
            BigDecimal averageSale = new BigDecimal("0");
            if (mchId != null) {
                String orderDate = orderModelMapper.getOldOrderTime(mchId);
                if (!StringUtils.isEmpty(orderDate)) {
                    Date date = DateUtil.dateFormateToDate(orderDate, GloabConst.TimePattern.YMDHMS);
                    if (date != null) {
                        long time = date.getTime() / 1000;
                        long entTime = DateUtil.getTime();
                        //计算两个日期之间间隔多少天
                        int day = DateUtil.getBetweenDate(entTime, time);
                        averageSale = countMchOrderSale.divide(BigDecimal.valueOf(day), 2, BigDecimal.ROUND_HALF_UP);
                    }
                }
            }
            //日均访问量
            int browseNum = 0;
            if (mchId != null) {
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(vo.getStoreId());
                mchModel.setId(mchId);
                mchModel = mchModelMapper.selectOne(mchModel);
                if (!StringUtils.isEmpty(mchModel)) {
                    Date date = DateUtil.dateFormateToDate(mchModel.getAdd_time(), GloabConst.TimePattern.YMDHMS);
                    if (date != null) {
                        long time = date.getTime() / 1000;
                        long entTime = DateUtil.getTime();
                        //计算两个日期之间间隔多少天
                        int day = DateUtil.getBetweenDate(entTime, time, true);
                        BigDecimal browseCountNum = countMchOrderSale.divide(BigDecimal.valueOf(day), 2, BigDecimal.ROUND_HALF_UP);
                        browseNum = browseCountNum.intValue();
                    }
                }
            }
            //获取本周第一天
            Date weekDate = DateUtil.dateFormateToDate(DateUtil.getSpanDate(2, true), GloabConst.TimePattern.YMD);
            Date weekEndDate = DateUtil.dateFormateToDate(DateUtil.getSpanDate(2, false), GloabConst.TimePattern.YMD);
            //计算上周开始、结束日期
            Date lastWeekEndDate = DateUtil.getAddDate(weekDate, -1);
            Date lastWeekStartDate = DateUtil.getAddDate(weekDate, -7);

            //销售额周同比
            BigDecimal weekSale = BigDecimal.ZERO;
            //本周销售额
            BigDecimal thisWeekSale = orderModelMapper.sumSaleByDate(vo.getStoreId(), mchId, weekDate, null);
            //上周销售额
            BigDecimal oldWeekSale = orderModelMapper.sumSaleByDate(vo.getStoreId(), mchId, lastWeekStartDate, lastWeekEndDate);
            if (oldWeekSale.compareTo(BigDecimal.ZERO) == 0 && thisWeekSale.compareTo(BigDecimal.ZERO) > 0) {
                weekSale = new BigDecimal("100");
            } else if (thisWeekSale.compareTo(BigDecimal.ZERO) > 0 && oldWeekSale.compareTo(BigDecimal.ZERO) > 0) {
                weekSale = thisWeekSale.subtract(oldWeekSale).divide(oldWeekSale.multiply(new BigDecimal("100")), 2, BigDecimal.ROUND_HALF_UP);
            }
            //日销售占比
            BigDecimal daySale = BigDecimal.ZERO;
            //今天销售额
            BigDecimal toDaySale = orderModelMapper.sumSaleByDate(vo.getStoreId(), mchId, DateUtil.dateFormateToDate(toDayStartDate, GloabConst.TimePattern.YMDHMS), null);
            //昨天销售额
            BigDecimal yesterDaySale = orderModelMapper.sumSaleByDate(vo.getStoreId(), mchId, DateUtil.dateFormateToDate(yesterdayStartDate, GloabConst.TimePattern.YMDHMS), DateUtil.dateFormateToDate(yesterdayEndDate, GloabConst.TimePattern.YMDHMS));
            if (yesterDaySale.compareTo(BigDecimal.ZERO) == 0 && toDaySale.compareTo(BigDecimal.ZERO) > 0) {
                daySale = new BigDecimal("100");
            } else if (yesterDaySale.compareTo(BigDecimal.ZERO) > 0 && toDaySale.compareTo(BigDecimal.ZERO) > 0) {
                daySale = toDaySale.subtract(yesterDaySale).divide(yesterDaySale.multiply(new BigDecimal("100")), 2, BigDecimal.ROUND_HALF_UP);
            }
            //访客日同比
            BigDecimal dayBrowse = BigDecimal.ZERO;
            //今天销访客
            BigDecimal toDayBrowse = mchBrowseModelMapper.sumBrowseByDate(vo.getStoreId(), mchId, DateUtil.dateFormateToDate(toDayStartDate, GloabConst.TimePattern.YMDHMS), null);
            //昨天销访客
            BigDecimal yesterDayBrowse = mchBrowseModelMapper.sumBrowseByDate(vo.getStoreId(), mchId, DateUtil.dateFormateToDate(yesterdayStartDate, GloabConst.TimePattern.YMDHMS), DateUtil.dateFormateToDate(yesterdayEndDate, GloabConst.TimePattern.YMDHMS));
            if (yesterDayBrowse.compareTo(BigDecimal.ZERO) == 0 && toDayBrowse.compareTo(BigDecimal.ZERO) > 0) {
                dayBrowse = new BigDecimal("100");
            } else if (yesterDaySale.compareTo(BigDecimal.ZERO) > 0 && toDayBrowse.compareTo(BigDecimal.ZERO) > 0) {
                dayBrowse = toDayBrowse.subtract(yesterDayBrowse).divide(yesterDayBrowse.multiply(new BigDecimal("100")), 2, BigDecimal.ROUND_HALF_UP);
            }
            //访客周同比
            BigDecimal weekBrowse = BigDecimal.ZERO;
            //本周访客
            BigDecimal thisWeekBrowse = mchBrowseModelMapper.sumBrowseByDate(vo.getStoreId(), mchId, weekDate, null);
            //上周访客
            BigDecimal oldWeekBrowse = mchBrowseModelMapper.sumBrowseByDate(vo.getStoreId(), mchId, lastWeekStartDate, lastWeekEndDate);
            if (oldWeekBrowse.compareTo(BigDecimal.ZERO) == 0 && thisWeekBrowse.compareTo(BigDecimal.ZERO) > 0) {
                weekBrowse = new BigDecimal("100");
            } else if (thisWeekBrowse.compareTo(BigDecimal.ZERO) > 0 && oldWeekBrowse.compareTo(BigDecimal.ZERO) > 0) {
                weekBrowse = thisWeekBrowse.subtract(oldWeekBrowse).divide(oldWeekBrowse.multiply(new BigDecimal("100")), 2, BigDecimal.ROUND_HALF_UP);
            }

            //订单数量同比 + 百分比
            BigDecimal dayOrderProportionNum = BigDecimal.ZERO;
            BigDecimal dayOrder = BigDecimal.ZERO;
            //昨天订单数量
            BigDecimal yesterdayOrder = orderModelMapper.sumSaleNumByDate(vo.getStoreId(), mchId, DateUtil.dateFormateToDate(yesterdayStartDate, GloabConst.TimePattern.YMDHMS), DateUtil.dateFormateToDate(yesterdayEndDate, GloabConst.TimePattern.YMDHMS));
            if (yesterdayOrder.compareTo(BigDecimal.ZERO) == 0 && toDayOrder.compareTo(BigDecimal.ZERO) > 0) {
                dayOrder = new BigDecimal("100");
            } else if (yesterdayOrder.compareTo(BigDecimal.ZERO) > 0 && toDayOrder.compareTo(BigDecimal.ZERO) > 0) {
                dayOrderProportionNum = dayOrderProportionNum.add(toDayOrder).subtract(yesterdayOrder);
                dayOrder = dayOrderProportionNum.divide(yesterdayOrder, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
            }

            //今日销售额
            BigDecimal toDaySaleTotal = BigDecimal.ZERO;
            //中间柱形图数据
            List<Map<String, BigDecimal>> toDaySaleList = new ArrayList<>();
            //今日访问数
            List<Map<String, Integer>> toDayBrowseList = new ArrayList<>();
            //获取今天每小时销售额
            Map<String, BigDecimal> toDaySaleMap = new HashMap<>(16);
            List<Map<String, BigDecimal>> hourSaleList = orderModelMapper.getSaleNumByHour(vo.getStoreId(), mchId, new Date());
            for (Map<String, BigDecimal> hourMap : hourSaleList) {
                toDaySaleTotal = toDaySaleTotal.add(hourMap.get("value"));
                for (String key : hourMap.keySet()) {
                    toDaySaleMap.put(key, hourMap.get(key));
                }
            }

            //昨天访问量
            Integer yesterdayBrowseTotal;
            Map<String, Object> yesterdayBrowseMap = new HashMap<>(16);
            yesterdayBrowseMap.putAll(parmaMap);
            yesterdayBrowseMap.put("startDate", yesterdayStartDate);
            yesterdayBrowseMap.put("endDate", yesterdayEndDate);
            yesterdayBrowseMap.put("add_time_day_group", "add_time_day_group");
            yesterdayBrowseTotal = mchBrowseModelMapper.countDynamicByUser(yesterdayBrowseMap);
            if (yesterdayBrowseTotal == null) {
                yesterdayBrowseTotal = 0;
            }

            //获取今天每小时访问数
            int todayBrowseTotal = 0;
            Map<String, Integer> toDayBrowseMap = new HashMap<>(16);
            List<Map<String, Object>> hourBrowseList = mchBrowseModelMapper.getBrowseNumByHour(vo.getStoreId(), mchId, new Date());
            for (Map<String, Object> hourMap : hourBrowseList) {
                for (String key : hourMap.keySet()) {
                    toDayBrowseMap.put(key, StringUtils.stringParseInt(hourMap.get(key)));
                }
                todayBrowseTotal += StringUtils.stringParseInt(hourMap.get("value"));
            }
            //组装24小时数据
            for (int i = 0; i < 24; i++) {
                Map<String, Integer> browseMap = new HashMap<>(16);
                Map<String, BigDecimal> saleMap = new HashMap<>(16);
                String timeStr = String.format("%s时", i);
                browseMap.put(timeStr, toDayBrowseMap.getOrDefault(timeStr, 0));
                saleMap.put(timeStr, toDaySaleMap.getOrDefault(timeStr, BigDecimal.ZERO));
                toDayBrowseList.add(browseMap);
                toDaySaleList.add(saleMap);
            }

            //月销售额/访问+周销售额/访问
            List<Map<String, BigDecimal>> toDayMonthSaleList = new ArrayList<>();
            List<Map<String, Integer>> toDayMonthBrowseList = new ArrayList<>();
            List<Map<String, BigDecimal>> toDayWeekSaleList = new ArrayList<>();
            List<Map<String, Integer>> toDayWeekBrowseList = new ArrayList<>();

            //获本月每天销售额
            Map<String, BigDecimal> toDayMonthSaleMap = new HashMap<>(16);
            List<Map<String, BigDecimal>> monthSaleList = orderModelMapper.getSaleNumByMonth(vo.getStoreId(), mchId, monthStartDay, monthEndDay);
            for (Map<String, BigDecimal> monthMap : monthSaleList) {
                monthAmtTotal = monthAmtTotal.add(monthMap.get("value"));
                for (String key : monthMap.keySet()) {
                    toDayMonthSaleMap.put(key, monthMap.get(key));
                }
            }
            //获本月每天访问量
            int monthBrowseTotal = 0;
            Map<String, Integer> toDayMonthBrowseMap = new HashMap<>(16);
            List<Map<String, Object>> monthBrowseList = mchBrowseModelMapper.getBrowseNumByMonth(vo.getStoreId(), mchId, monthStartDay, monthEndDay);
            for (Map<String, Object> monthMap : monthBrowseList) {
                for (String key : monthMap.keySet()) {
                    int num = StringUtils.stringParseInt(monthMap.get(key));
                    toDayMonthBrowseMap.put(key, num);
                }
                monthBrowseTotal += MapUtils.getInteger(monthMap, "value");
            }
            //获取当月天数
            int monthDayNum = StringUtils.stringParseInt(DateUtil.dateFormate(DateUtil.getMonthLastDay(new Date()), GloabConst.TimePattern.D1, GloabConst.TimePattern.YMDHMS));
            //获取本周 day
            int weekStart = StringUtils.stringParseInt(DateUtil.dateFormate(weekDate, GloabConst.TimePattern.D1));
            int weekEnd = StringUtils.stringParseInt(DateUtil.dateFormate(weekEndDate, GloabConst.TimePattern.D1));
            //获取本月 month 字符串
            String monthStr = DateUtil.dateFormate(weekEndDate, GloabConst.TimePattern.MM);

            //组装当月每天数据
            for (int i = 1; i < monthDayNum; i++) {
                Map<String, Integer> browseMap = new HashMap<>(16);
                Map<String, Integer> browseWeekMap = new HashMap<>(16);
                Map<String, BigDecimal> saleMap = new HashMap<>(16);
                Map<String, BigDecimal> saleWeekMap = new HashMap<>(16);
                String timeStr = monthStr + "/" + i;
                if (i < 10) {
                    timeStr = String.format("%s/0%s", monthStr, i);
                }
                //判断是否是本周
                if (i >= weekStart && i <= weekEnd) {
                    browseWeekMap.put(timeStr, toDayMonthBrowseMap.getOrDefault(timeStr, 0));
                    saleWeekMap.put(timeStr, toDayMonthSaleMap.getOrDefault(timeStr, BigDecimal.ZERO));
                    toDayWeekSaleList.add(saleWeekMap);
                    toDayWeekBrowseList.add(browseWeekMap);
                }
                //本月
                browseMap.put(timeStr, toDayMonthBrowseMap.getOrDefault(timeStr, 0));
                saleMap.put(timeStr, toDayMonthSaleMap.getOrDefault(timeStr, BigDecimal.ZERO));
                toDayMonthBrowseList.add(browseMap);
                toDayMonthSaleList.add(saleMap);
            }

            //今日销售额同比(当日减去前一天的值除以前一日的营业额)
            BigDecimal dayAmtProportion = BigDecimal.ZERO;
            BigDecimal dayAmtProportionAmt = BigDecimal.ZERO;
            if (yesterDaySale.compareTo(BigDecimal.ZERO) == 0 && toDaySaleTotal.compareTo(BigDecimal.ZERO) > 0) {
                //同比百分之百
                dayAmtProportion = new BigDecimal("100");
            } else if (yesterDaySale.compareTo(BigDecimal.ZERO) > 0 && toDaySaleTotal.compareTo(BigDecimal.ZERO) > 0) {
                dayAmtProportionAmt = dayAmtProportionAmt.add(toDaySaleTotal).subtract(yesterDaySale);
                dayAmtProportion = dayAmtProportionAmt.divide(yesterDaySale, 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            //今日访客同比
            BigDecimal yesterdayProportionNum = BigDecimal.ZERO;
            BigDecimal yesterdayProportion = BigDecimal.ZERO;
            if (yesterdayBrowseTotal == 0 && todayBrowseTotal > 0) {
                //同比百分之百
                yesterdayProportion = new BigDecimal("100");
            } else if (yesterdayBrowseTotal > 0 && todayBrowseTotal > 0) {
                yesterdayProportionNum = new BigDecimal(todayBrowseTotal - yesterdayBrowseTotal);
                yesterdayProportion = yesterdayProportionNum.divide(new BigDecimal(yesterdayBrowseTotal), 6, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            //统计会员数量
            int userTotal = 0;
            User user = new User();
            user.setStore_id(vo.getStoreId());
            if (mchId != null) {
                user.setMchId(mchId);
            }
            userTotal = userBaseMapper.selectCount(user);
            //7天
            Date weekStartDate = DateUtil.getAddDate(new Date(), -6);
            String weekStartDateStr = DateUtil.dateFormate(weekStartDate, GloabConst.TimePattern.YMD);
            //统计一周的新增会员
            List<Map<String, Object>> newWeekUserList = new ArrayList<>();
            List<Map<String, Object>> newUserDataList = userBaseMapper.getNewUserByDate(vo.getStoreId(), mchId, weekStartDateStr, toDayStartDate);
            for (int i = 0; i < 7; i++) {
                Date currentDate = DateUtil.getAddDate(weekStartDate, i);
                String currentDateStr = DateUtil.dateFormate(currentDate, GloabConst.TimePattern.YMD);
                newWeekUserList.add(getData(newUserDataList, currentDateStr));
            }

            //获取公告
            Map<String, Object> systemNoticeMap = new HashMap<>(16);
            systemNoticeMap.put("store_id", 0);
            systemNoticeMap.put("pageStart", 0);
            systemNoticeMap.put("pageEnd", 3);
            resultMap.put("sysNotice", systemTellModelMapper.selectDynamic(systemNoticeMap));

            //日均销售额
            resultMap.put("average_sale", averageSale.toString());
            //今日销售同比
            resultMap.put("dayAmtProportion", dayAmtProportion.toString());
            resultMap.put("dayAmtProportionAmt", dayAmtProportionAmt.toString());
            //今日销售金额
            resultMap.put("toDaySaleTotal", toDaySaleTotal.toString());
            //昨日销售金额
            resultMap.put("yesterdaySale", yesterDaySale.toString());
            //本月总消费金额
            resultMap.put("monthAmtTotal", monthAmtTotal.toString());
            //本月订单数
            resultMap.put("monthOrderNum", monthOrderNum);
            //昨日订单数
            resultMap.put("yesterdayOrderNum", yesterdayOrderNum);

            //今天访客同比
            resultMap.put("yesterdayBrowseNum", yesterdayProportionNum);
            resultMap.put("yesterdayProportion", yesterdayProportion);
            //今天访客数量
            resultMap.put("todayBrowseTotal", todayBrowseTotal);
            //昨天客数量
            resultMap.put("yesterdayBrowseTotal", yesterdayBrowseTotal);
            //月访客数量
            resultMap.put("monthBrowseTotal", monthBrowseTotal);

            //会员统计报表
            resultMap.put("userTotal", userTotal);
            resultMap.put("newWeekUserList", newWeekUserList);

            //日均访问量
            resultMap.put("browse_num", browseNum);
            //统计店铺总访问量
            resultMap.put("coustomer_num_z", countMchBrowseNum);
            //转换率
            resultMap.put("conversion", conversion.toString() + "%");
            //订单统计相关数据
            resultMap.put("list_sno", countOrderList);
            //销售额周同比
            resultMap.put("yoy_week", weekSale);
            //日销售占比
            resultMap.put("yoy_day", daySale);
            //访客日同比
            resultMap.put("yoy_salesw", weekBrowse);
            //访客周同比
            resultMap.put("yoy_salesn", dayBrowse);
            //订单数量同比
            resultMap.put("yoy_order_num", dayOrderProportionNum);
            resultMap.put("yoy_order", dayOrder);
            //今日销售额占比图数据
            resultMap.put("z_sales_day", toDaySaleList);
            //今日访问数占比图数据
            resultMap.put("z_coustomer_day", toDayBrowseList);
            //本月销售额占比图数据
            resultMap.put("z_sales_month", toDayMonthSaleList);
            //本月访问数占比图数据
            resultMap.put("z_coustomer_month", toDayMonthBrowseList);
            //本周销售额占比图数据
            resultMap.put("z_sales_week", toDayWeekSaleList);
            //本周访问数占比图数据
            resultMap.put("z_coustomer_week", toDayWeekBrowseList);
            //统计店铺总销售额
            resultMap.put("z_total", countMchOrderSale);
            //统计店铺总支付订单量
            resultMap.put("z_num", countMchOrderNum);
            //总支付订单数量 数据图表(月表)
            resultMap.put("z_num_arr", getOrderNumByDayList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("首页 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    private Map<String, Object> getData(List<Map<String, Object>> dataList, String currentDateStr) {
        Map<String, Object> resultMap = new HashMap<>(16);
        resultMap.put("date", currentDateStr);
        resultMap.put("num", 0);
        for (int x = 0; x < dataList.size(); x++) {
            Map<String, Object> map = dataList.get(x);
            String date = MapUtils.getString(map, "date");
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
    public void addJumpPath(MainVo vo, String sid, String name, int type, int sourceType, GloabConst.LaikeTuiUrl.JumpPath jumpPath, String[] params) throws LaiKeApiException {
        try {
            JumpPathModel jumpPathModelSave = new JumpPathModel();
            JumpPathModel jumpPathModelOld = new JumpPathModel();
            jumpPathModelOld.setStore_id(vo.getStoreId());
            jumpPathModelOld.setName(name);
            jumpPathModelOld.setType0(type);
            jumpPathModelOld.setType(sourceType);
            jumpPathModelOld.setSid(sid);
            jumpPathModelOld = jumpPathModelMapper.selectOne(jumpPathModelOld);
            if (jumpPathModelOld != null) {
                jumpPathModelSave.setId(jumpPathModelOld.getId());
            }
            jumpPathModelSave.setStore_id(vo.getStoreId());
            jumpPathModelSave.setType0(type);
            jumpPathModelSave.setName(name);
            int paramStatus = params != null && params.length > 0 ? 1 : 0;
            jumpPathModelSave.setParameter_status(paramStatus);
            jumpPathModelSave.setStatus(1);
            jumpPathModelSave.setType(sourceType);
            jumpPathModelSave.setUrl(jumpPath.getPath());
            //参数处理
            jumpPathModelSave.setParameter(String.format(jumpPath.getParma(), DataUtils.cast(params)));

            if (jumpPathModelOld == null) {
                jumpPathModelSave.setSid(sid);
                jumpPathModelSave.setAdd_date(new Date());
                jumpPathModelMapper.insertSelective(jumpPathModelSave);
            } else {
                jumpPathModelSave.setAdd_date(jumpPathModelOld.getAdd_date());
                jumpPathModelSave.setId(jumpPathModelOld.getId());
                jumpPathModelMapper.updateByPrimaryKeySelective(jumpPathModelSave);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑导航栏跳转地址 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addJumpPath");
        }
    }


    @Override
    public void systemMessageSend(MainVo vo, int type, String tuiTitle, String tuiContext, String tuiUserId) throws LaiKeApiException {
        try {
            //站内推送退款信息
            SystemMessageModel systemMessageSave = new SystemMessageModel();
            systemMessageSave.setType(type);
            systemMessageSave.setSenderid("admin");
            systemMessageSave.setStore_id(vo.getStoreId());
            systemMessageSave.setRecipientid(tuiUserId);
            systemMessageSave.setTitle(tuiTitle);
            systemMessageSave.setContent(tuiContext);
            systemMessageSave.setTime(new Date());
            systemMessageModelMapper.insertSelective(systemMessageSave);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("系统推送消息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "systemMessageSend");
        }
    }

    @Autowired
    private JumpPathModelMapper jumpPathModelMapper;

    @Autowired
    private SystemMessageModelMapper systemMessageModelMapper;
}
