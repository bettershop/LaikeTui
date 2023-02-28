package com.laiketui.modules.backend.services.order;

import com.google.common.collect.Maps;
import com.laiketui.api.modules.backend.order.ConfigService;
import com.laiketui.common.mapper.OrderConfigModalMapper;
import com.laiketui.domain.order.OrderConfigModal;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.ConfigVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单设置
 *
 * @author wangxian
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    private final Logger logger = LoggerFactory.getLogger( ConfigServiceImpl.class);

    @Autowired
    private OrderConfigModalMapper orderConfigModalMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> configShow(MainVo vo) throws LaiKeApiException {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            OrderConfigModal orderConfigModal = new OrderConfigModal();
            orderConfigModal.setStore_id(vo.getStoreId());
            orderConfigModal = orderConfigModalMapper.selectOne(orderConfigModal);
            //发货时限
            int day = 0;
            int hour = 0;
            //订单自动好评
            int autoGoodCommentDay = 1;
            //订单失效
            int orderFailure = 2;
            //订单售后失效时间
            int orderAfter = 7;
            //自动收货时间
            int autoTheGoods = 2;
            //发货时限
            int orderShip = 0;
            //提醒限制
            int remind = 1;
            //包邮设置 0.未开启 1.开启
            int packageSettings = 0;
            //同件
            int samePiece = 0;
            //同单
            int sameOrder = 0;
            //提醒限制(天)
            int remindDay = 0;
            //提醒限制(小时)
            int remindHour = 0;

            //自动评价设置几后自动好评
            if (orderConfigModal != null) {
                packageSettings = orderConfigModal.getPackage_settings();
                samePiece = orderConfigModal.getSame_piece();
                sameOrder = orderConfigModal.getSame_order();
                orderFailure = orderConfigModal.getOrder_failure();
                orderAfter = orderConfigModal.getOrder_after();
                autoTheGoods = orderConfigModal.getAuto_the_goods();
                orderShip = orderConfigModal.getOrder_ship();
                remind = orderConfigModal.getRemind();
                autoGoodCommentDay = orderConfigModal.getAuto_good_comment_day();
                hour = orderShip;
                //发货提醒间隔处理
                if (remind == 1) {
                    //店主查看发货提醒后，买家多久后能再次提醒。0.表示只能提醒一次)
                    remindDay = new BigDecimal(remind).divide(new BigDecimal("24"), BigDecimal.ROUND_DOWN).intValue();
                    remindHour = remind % 24;
                }

                //发货时限处理
                if (orderShip > 24) {
                    day = new BigDecimal(orderShip).divide(new BigDecimal("24"), BigDecimal.ROUND_DOWN).intValue();
                    hour = orderShip % 24;
                }

            }

            retMap.put("same_order", sameOrder);
            retMap.put("same_piece", samePiece);
            retMap.put("package_settings", packageSettings);
            retMap.put("day", day);
            retMap.put("hour", hour);
            retMap.put("remind_hour", remindHour);
            retMap.put("remind_day", remindDay);
            retMap.put("auto_good_comment_day", autoGoodCommentDay);
            retMap.put("auto_the_goods", autoTheGoods);
            retMap.put("order_after", orderAfter);
            retMap.put("order_failure", orderFailure);

        }catch (LaiKeApiException l){
            throw l;
        }catch (Exception e) {
            logger.error("获取订单设置 异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "configShow");
        }
        return retMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveConfig(ConfigVo configVo) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(configVo.getAccessId(), redisUtil);
            //确认收货时间
            int autoTheGoods = configVo.getAutoTheGoods();
            //订单失效时间
            int orderFailure = configVo.getOrderFailure();
            //订单售后时间
            int orderAfter = configVo.getOrderAfter();
            //提醒限制 天
            int remindDay = configVo.getRemindDay();
            //提醒限制 小时
            int remindHour = configVo.getRemindHour();
            //提醒限制 存储单位（小时）
            int remind = 0;
            //多少天默认好评
            int autoGoodCommentDay = configVo.getAutoGoodCommentDay();
            //同件
            int samePiece = configVo.getSamePiece();
            //同单
            int sameOrder = configVo.getSameOrder();

            if (configVo.getPackageSettings() == 1) {
                if (samePiece != 0 || sameOrder != 0) {
                    if (samePiece <= 0) {
                        throw new LaiKeApiException("400", "同件数量不能为负数或零", "saveConfig");
                    }
                    if (sameOrder <= 0) {
                        throw new LaiKeApiException("400", "同单数量不能为负数或零", "saveConfig");
                    }
                }
            } else {
                samePiece = 0;
                sameOrder = 0;
            }

            if (autoTheGoods != 0) {
                if (autoTheGoods <= 0) {
                    throw new LaiKeApiException("400", "自动收货时间不能为负数或零", "saveConfig");
                }
            }

            if (orderFailure != 0) {
                if (orderFailure <= 0) {
                    throw new LaiKeApiException("400", "订单过期删除时间不能为负数或零", "saveConfig");
                }
            }
            if (orderAfter != 0) {
                if (orderAfter <= 0) {
                    throw new LaiKeApiException("400", "订单售后时间不能为负数或零", "saveConfig");
                }
            }
            if (remindDay < 0) {
                throw new LaiKeApiException("400", "请输入正确的提醒限制时间", "saveConfig");
            }
            if (remindHour < 0) {
                throw new LaiKeApiException("400", "请输入正确的提醒限制时间", "saveConfig");
            }
            if (remindDay > 0 || remindHour > 0) {
                remind = remindHour + (remindDay * 24);
            }

            int row;
            OrderConfigModal orderConfigOld = new OrderConfigModal();
            orderConfigOld.setStore_id(configVo.getStoreId());
            orderConfigOld = orderConfigModalMapper.selectOne(orderConfigOld);

            OrderConfigModal orderConfigSave = new OrderConfigModal();
            orderConfigSave.setAuto_good_comment_day(autoGoodCommentDay);
            orderConfigSave.setOrder_failure(orderFailure);
            orderConfigSave.setOrder_after(orderAfter);
            orderConfigSave.setAuto_the_goods(autoTheGoods);
            orderConfigSave.setRemind(remind);
            orderConfigSave.setModify_date(new Date());
            orderConfigSave.setPackage_settings(configVo.getPackageSettings());
            orderConfigSave.setSame_piece(samePiece);
            orderConfigSave.setSame_order(sameOrder);

            if (orderConfigOld != null) {
                orderConfigSave.setId(orderConfigOld.getId());
                row = orderConfigModalMapper.updateByPrimaryKeySelective(orderConfigSave);
            } else {
                orderConfigSave.setStore_id(configVo.getStoreId());
                row = orderConfigModalMapper.insertSelective(orderConfigSave);
            }
            if (row < 0) {
                throw new LaiKeApiException("400", "未知原因，订单设置修改失败！", "saveConfig");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("订单设置失败 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "saveConfig");
        }
    }
}
