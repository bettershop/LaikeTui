package com.laiketui.modules.backend.services.notie;

import com.laiketui.api.modules.backend.notie.PublicNoticeService;
import com.laiketui.common.mapper.MessageLoggingModalMapper;
import com.laiketui.common.mapper.SystemTellModelMapper;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.message.MessageLoggingModal;
import com.laiketui.domain.systems.SystemTellModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddNoticeVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 公告管理
 *
 * @author Trick
 * @date 2021/1/19 15:44
 */
@Service
public class PublicNoticeServiceImpl implements PublicNoticeService {
    private final Logger logger = LoggerFactory.getLogger(PublicNoticeServiceImpl.class);

    @Override
    public Map<String, Object> getPublicNoticeInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", 0);
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            parmaMap.put("add_time_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> systemTellModelList = systemTellModelMapper.selectDynamic(parmaMap);
            int total = systemTellModelMapper.countDynamic(parmaMap);
            for (Map<String, Object> map : systemTellModelList) {
                String status;
                Date startDate = DateUtil.dateFormateToDate(MapUtils.getString(map, "startdate"), GloabConst.TimePattern.YMDHMS);
                Date endDate = DateUtil.dateFormateToDate(MapUtils.getString(map, "enddate"), GloabConst.TimePattern.YMDHMS);
                if (DateUtil.dateCompare(new Date(), endDate)) {
                    status = "已失效";
                } else if (DateUtil.dateCompare(startDate, new Date())) {
                    status = "未生效";
                } else {
                    status = "生效中";
                }

                map.put("status", status);
            }

            resultMap.put("list", systemTellModelList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取系统公告 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getPublicNoticeInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addSysNoticeInfo(AddNoticeVo vo) throws LaiKeApiException {
        try {
            int count;
            SystemTellModel systemTellModelOld = null;
            if (vo.getId() != null) {
                systemTellModelOld = systemTellModelMapper.selectByPrimaryKey(vo.getId());
                if (systemTellModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "公告不存在");
                }
            }
            if (StringUtils.isEmpty(vo.getTitle())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "标题不能为空");
            }
            if (vo.getType() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "公告类型不能为空");
            }
            if (systemTellModelOld == null) {
                if (StringUtils.isEmpty(vo.getStartDate())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "开始时间不能为空");
                } else if (StringUtils.isEmpty(vo.getEndDate())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "结束时间不能为空");
                } else if (!DateUtil.dateCompare(vo.getEndDate(), vo.getStartDate())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "开始时间不能大于结束时间");
                }
            }
            if (StringUtils.isEmpty(vo.getContent())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "公告内容不能为空");
            }
            SystemTellModel systemTellModelSave = new SystemTellModel();
            systemTellModelSave.setType(vo.getType());
            systemTellModelSave.setTitle(vo.getTitle());
            systemTellModelSave.setContent(vo.getContent());
            systemTellModelSave.setTimetype(vo.getIsTime());
            systemTellModelSave.setStartdate(vo.getStartDate());
            systemTellModelSave.setEnddate(vo.getEndDate());
            if (systemTellModelOld != null) {
                systemTellModelSave.setId(systemTellModelOld.getId());
                count = systemTellModelMapper.updateByPrimaryKeySelective(systemTellModelSave);
            } else {
                systemTellModelSave.setAdd_time(new Date());
                count = systemTellModelMapper.insertSelective(systemTellModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取系统公告 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addSysNoticeInfo");
        }
    }

    @Override
    public boolean delSysNoticeInfo(int storeId, Integer id) throws LaiKeApiException {
        try {
            SystemTellModel systemTellModel = new SystemTellModel();
            systemTellModel.setId(id);
            systemTellModel = systemTellModelMapper.selectOne(systemTellModel);
            if (systemTellModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "系统公告不存在");
            }
            int count = systemTellModelMapper.deleteByPrimaryKey(id);

            return count > 0;
        } catch (Exception e) {
            logger.error("删除系统公告 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delSysNoticeInfo");
        }
    }

    @Override
    public Map<String, Object> noticeList(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            //订单(待发货)、订单(售后)、订单(提醒发货)、订单(订单关闭)、订单(新订单)、订单(收货)
            int[] orderTypes = new int[]{1, 2, 3, 4, 5, 6};
            Map<String, Object> orderMap = new HashMap<>(16);
            // 商品(审核)、商品(补货)
            int[] goodsTypes = new int[]{7, 9};
            Map<String, Object> goodsMap = new HashMap<>(16);

            //获取订单相关的通知
            noticeData(vo.getStoreId(), user.getShop_id(), orderTypes, orderMap);
            //获取商品相关的通知
            noticeData(vo.getStoreId(), user.getShop_id(), goodsTypes, goodsMap);

            List<Map<String, Object>> resultList = new ArrayList<>();
            resultList.add(orderMap);
            resultList.add(goodsMap);
            resultMap.put("list", resultList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("商城消息通知 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "noticeList");
        }
        return resultMap;
    }

    //通知消息结构辅助方法
    private void noticeData(int storeId, int mchId, int[] types, Map<String, Object> resultMap) throws LaiKeApiException {
        try {
            Map<String, Object> map = new HashMap<>(16);
            map.put("store_id", storeId);
            map.put("mch_id", mchId);
            map.put("read_or_not", 0);
            int totalMain = 0;
            List<Map<String, Object>> mainList = new ArrayList<>();
            //获取当前订单的通知
            for (int type : types) {
                map.put("type", type);
                int total = messageLoggingModalMapper.countDynamic(map);
                totalMain += total;
                List<Map<String, Object>> noticeList = messageLoggingModalMapper.selectDynamic(map);
                List<Map<String, Object>> noticeListTemp = new ArrayList<>();
                for (Map<String, Object> notice : noticeList) {
                    Map<String, Object> noticeMap = new HashMap<>(16);
                    noticeMap.put("id", MapUtils.getInteger(notice, "id"));
                    noticeMap.put("parameter", MapUtils.getString(notice, "parameter"));
                    noticeMap.put("content", MapUtils.getString(notice, "content"));
                    noticeMap.put("add_date", DateUtil.dateFormate(MapUtils.getString(notice, "add_date"), GloabConst.TimePattern.YMDHMS));
                    noticeListTemp.add(noticeMap);
                }
                Map<String, Object> noticeMap = new HashMap<>(16);
                noticeMap.put("list", noticeListTemp);
                noticeMap.put("total", total);
                noticeMap.put("type", type);
                mainList.add(noticeMap);
            }
            resultMap.put("list", mainList);
            resultMap.put("total", totalMain);
            resultMap.put("type", Arrays.toString(types));
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("商城消息通知通知 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "noticeData");
        }
    }

    @Override
    public void noticeRead(MainVo vo, Integer id, String types) throws LaiKeApiException {
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            String[] typeList = null;
            MessageLoggingModal messageLoggingModal = new MessageLoggingModal();
            messageLoggingModal.setStore_id(vo.getStoreId());
            messageLoggingModal.setRead_or_not(1);
            if (id != null) {
                messageLoggingModal.setId(id);
                messageLoggingModalMapper.updateByPrimaryKeySelective(messageLoggingModal);
            }
            if (types != null) {
                typeList = types.split(SplitUtils.DH);
            }
            if (typeList != null) {
                for (String type : typeList) {
                    messageLoggingModal.setType(Integer.parseInt(type));
                    messageLoggingModalMapper.noticeRead(vo.getStoreId(), user.getShop_id(), type);
                }
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("标记消息已读 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "noticeRead");
        }
    }

    @Autowired
    private SystemTellModelMapper systemTellModelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MessageLoggingModalMapper messageLoggingModalMapper;
}
