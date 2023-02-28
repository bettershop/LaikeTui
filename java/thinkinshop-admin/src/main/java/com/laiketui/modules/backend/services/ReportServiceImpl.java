package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.ReportService;
import com.laiketui.common.mapper.OrderModelMapper;
import com.laiketui.common.mapper.UserBaseMapper;
import com.laiketui.domain.vo.admin.MchReportVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 报表管理
 *
 * @author Trick
 * @date 2021/7/5 10:38
 */
@Service
public class ReportServiceImpl implements ReportService {
    private final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;


    @Override
    public Map<String, Object> mchTurnoverReport(MchReportVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (StringUtils.isNotEmpty(vo.getType())) {
                switch (vo.getType()) {
                    case "yesterday":
                        parmaMap.put("addTimeStartBetween", DateUtil.getStartOfDay(new Date()));
                        parmaMap.put("addTimeEndBetween", DateUtil.getEndOfDay(new Date()));
                        break;
                    case "week":
                        parmaMap.put("startDate", DateUtil.getAddDate(new Date(), -6));
                        break;
                    case "month":
                        parmaMap.put("startDate", DateUtil.getMonthFirstDay(new Date()));
                        break;
                    default:
                        parmaMap.put("startDate", DateUtil.getStartOfDay(new Date()));
                        break;
                }
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
            }
            if (StringUtils.isNotEmpty(vo.getEndDate())) {
                parmaMap.put("startEnd", vo.getEndDate());
            }
            if(StringUtils.isNotEmpty(vo.getMchName())){
                parmaMap.put("mchName", vo.getMchName());
            }

            String sortTypeStr = DataUtils.Sort.DESC.toString();
            if (vo.getSortType() == 0) {
                sortTypeStr = DataUtils.Sort.ASC.toString();
            }

            parmaMap.put("group_store_id", "group_store_id");
            parmaMap.put("total_sort", sortTypeStr);
            parmaMap.put("startPage", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = orderModelMapper.countMchTurnoverReport(parmaMap);
            List<Map<String, Object>> list = orderModelMapper.sumMchTurnoverReport(parmaMap);

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("店铺营业额报表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchTurnoverReport");
        }
        return resultMap;
    }


    @Override
    public Map<String, Object> mchTurnoverNewUserReport(MchReportVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (StringUtils.isNotEmpty(vo.getType())) {
                switch (vo.getType()) {
                    case "yesterday":
                        parmaMap.put("addTimeStartBetween", DateUtil.getStartOfDay(new Date()));
                        parmaMap.put("addTimeEndBetween", DateUtil.getEndOfDay(new Date()));
                        break;
                    case "week":
                        parmaMap.put("startDate", DateUtil.getAddDate(new Date(), -6));
                        break;
                    case "month":
                        parmaMap.put("startDate", DateUtil.getMonthFirstDay(new Date()));
                        break;
                    default:
                        parmaMap.put("startDate", DateUtil.getStartOfDay(new Date()));
                        break;
                }
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
            }
            if (StringUtils.isNotEmpty(vo.getEndDate())) {
                parmaMap.put("startEnd", vo.getEndDate());
            }
            if(StringUtils.isNotEmpty(vo.getMchName())){
                parmaMap.put("mchName", vo.getMchName());
            }
            String sortTypeStr = DataUtils.Sort.DESC.toString();
            if (vo.getSortType() == 0) {
                sortTypeStr = DataUtils.Sort.ASC.toString();
            }
            parmaMap.put("group_store_id", "group_store_id");
            parmaMap.put("total_sort", sortTypeStr);
            parmaMap.put("startPage", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = userBaseMapper.countMchTurnoverReport(parmaMap);
            List<Map<String, Object>> list = userBaseMapper.sumMchTurnoverReport(parmaMap);

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("商户新增用户报表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchTurnoverNewUserReport");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> mchTurnoverOrderReport(MchReportVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (StringUtils.isNotEmpty(vo.getType())) {
                switch (vo.getType()) {
                    case "yesterday":
                        parmaMap.put("addTimeStartBetween", DateUtil.getStartOfDay(new Date()));
                        parmaMap.put("addTimeEndBetween", DateUtil.getEndOfDay(new Date()));
                        break;
                    case "week":
                        parmaMap.put("startDate", DateUtil.getAddDate(new Date(), -6));
                        break;
                    case "month":
                        parmaMap.put("startDate", DateUtil.getMonthFirstDay(new Date()));
                        break;
                    default:
                        parmaMap.put("startDate", DateUtil.getStartOfDay(new Date()));
                        break;
                }
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
            }
            if (StringUtils.isNotEmpty(vo.getEndDate())) {
                parmaMap.put("startEnd", vo.getEndDate());
            }
            if(StringUtils.isNotEmpty(vo.getMchName())){
                parmaMap.put("mchName", vo.getMchName());
            }
            String sortTypeStr = DataUtils.Sort.DESC.toString();
            if (vo.getSortType() == 0) {
                sortTypeStr = DataUtils.Sort.ASC.toString();
            }
            parmaMap.put("group_store_id", "group_store_id");
            parmaMap.put("total_sort", sortTypeStr);
            parmaMap.put("startPage", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = orderModelMapper.countMchTurnoverOrderReport(parmaMap);
            List<Map<String, Object>> list = orderModelMapper.sumMchTurnoverOrderReport(parmaMap);

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("商户订单报表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchTurnoverOrderReport");
        }
        return resultMap;
    }
}
