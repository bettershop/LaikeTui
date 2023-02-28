package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.laiketui.api.common.order.PublicIntegralService;
import com.laiketui.api.common.pay.PublicPaymentService;
import com.laiketui.common.config.wechatpay.WechatConfigInfo;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.order.NoticeModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.pay.PaymentVo;
import com.laiketui.domain.vo.plugin.integral.AddScoreVo;
import com.laiketui.libs.wechatpay.WXPay;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.RedisDataTool;
import com.laiketui.root.utils.weixin.AppletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.laiketui.root.consts.ErrorCode.BizErrorCode.API_OPERATION_FAILED;
import static com.laiketui.root.consts.ErrorCode.BizErrorCode.INSUFFICIENT_BALANCE;


@Service("publicWechatServiceImpl")
public class PublicWechatServiceImpl implements PublicPaymentService {

    private final Logger logger = LoggerFactory.getLogger(PublicWechatServiceImpl.class);

    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private NoticeModelMapper noticeModelMapper;

    /**
     * 获取微信支付配置
     *
     * @param paymentVo
     * @return
     * @throws Exception
     */
    private WechatConfigInfo getWechatConfigInfo(PaymentVo paymentVo) throws Exception {
        //微信app支付 默认app支付
        String className = paymentVo.getPayType();
        int storeId = paymentVo.getStoreId();
        String paymentJson = paymentConfigModelMapper.getPaymentConfigInfo(storeId, className);
        paymentJson = paymentJson.replaceAll("%2B", "\\+");
        logger.info(className + "支付配置信息：" + paymentJson);
        JSONObject payJson = JSONObject.parseObject(paymentJson);
        String appID = payJson.getString("appid");
        logger.info("appID:{}", appID);
        String mchID = payJson.getString("mch_id");
        logger.info("mchID:{}", mchID);
        String key = payJson.getString("mch_key");
        logger.info("key:{}", key);
        String certPath = payJson.getString("sslcert_path");
        logger.info("certPath:{}", certPath);
        String notifyUrl = payJson.getString("notify_url");
        logger.info("notifyUrl:{}", notifyUrl);
        String appSecreKey = payJson.getString("appsecret");
        logger.info("appSecreKey:{}", appSecreKey);
        String cert_p12 = payJson.getString("cert_p12");
        logger.info("cert_p12:{}", cert_p12);
        return new WechatConfigInfo(appID, mchID, key, cert_p12 == null ? "/var/pay/apiclient_cert.p12" : cert_p12, notifyUrl, appSecreKey);
    }

    @Override
    public String qrCodeOrder(String appid, String privateKey, String publicKey, String orderno, BigDecimal orderAmt) throws LaiKeApiException {
        return null;
    }

    @Override
    public Map<String, String> refundOrder(int storeId, Integer id, String className, String treadeNo, BigDecimal refundAmt) throws LaiKeApiException {
        Map<String, String> resultMap = new HashMap<>(16);
        try {
            logger.info(">>微信退款开始>>");
            //获取商户支付配置信息
            Map<String, Object> parmaMap = new HashMap<>(16);
            PaymentVo paymentVo = new PaymentVo();
            paymentVo.setPayType(className);
            paymentVo.setStoreId(storeId);
            WechatConfigInfo config = getWechatConfigInfo(paymentVo);
            if (config != null) {
                WXPay wxpay = new WXPay(config);
                Map<String, String> params = new HashMap<>();
                params.put("appid", config.getAppID());
                params.put("mch_id", config.getMchID());
                //商户订单号
                params.put("out_trade_no", treadeNo);
                //商户退款单号
                params.put("out_refund_no", treadeNo.concat(String.valueOf(System.currentTimeMillis())));
                //订单金额
                OrderModel orderModel = new OrderModel();
                orderModel.setId(id);
                orderModel = orderModelMapper.selectOne(orderModel);
                BigDecimal yb = new BigDecimal("100");
                params.put("total_fee", String.valueOf(orderModel.getZ_price().multiply(yb).intValue()));
                //退款金额
                params.put("refund_fee", String.valueOf(refundAmt.multiply(yb).intValue()));
                Map<String, String> resultMap1 = wxpay.refund(params);
                logger.info("#########退款信息#########start####");
                logger.info("退款信息，{}", JSONObject.toJSONString(resultMap1));
                logger.info("#########退款信息#########end######");
                resultMap.put("code", GloabConst.ManaValue.MANA_VALUE_SUCCESS);
                //发送模板消息
                NoticeModel noticeModel = new NoticeModel();
                noticeModel.setStore_id(paymentVo.getStoreId());
                noticeModel = noticeModelMapper.selectOne(noticeModel);
                if (Objects.isNull(noticeModel)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "该商城暂无模板");
                }
                //当前用户信息
                User userEntity = new User();
                userEntity.setUser_id(orderModel.getUser_id());
                User user = userBaseMapper.selectOne(userEntity);
                //发送通知
                String response = AppletUtil.sendMessage(AppletUtil.getAccessToken(config.getAppID(), config.getAppSecreKey()), user.getWx_id(), noticeModel.getPay_success());
                logger.error("===================微信消息推送返回值：{}", response);
            } else {
                logger.info("商户未配置支付信息,无法退款!");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商户未配置支付信息,无法退款", "returnAlipay");
            }
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("微信退款 异常:{}", JSON.toJSONString(e));
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "returnAlipay");
        }
    }

    @Override
    public void walletPay(String userId, BigDecimal money, String token) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(userId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "用户id为空");
            }
            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "用户不存在");
            }
            //清空密码尝试次数
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setLogin_num(0);
            userBaseMapper.updateByPrimaryKeySelective(updateUser);
            // 用户余额
            BigDecimal userMoney = user.getMoney();
            // 用户余额 大于 余额抵扣金额
            if (userMoney.compareTo(money) >= 0) {
                // 根据微信id,修改用户余额
                int row = userMapper.walletPayUpdateUserAccount(money, user.getStore_id(), userId);
                if (row < 0) {
                    throw new LaiKeApiException(API_OPERATION_FAILED, "支付失败", "pay");
                }

                String event = userId + "使用了" + money.toString() + "元余额";
                RecordModel recordModel = new RecordModel();
                recordModel.setStore_id(user.getStore_id());
                recordModel.setUser_id(userId);
                recordModel.setMoney(money);
                recordModel.setOldmoney(userMoney);
                recordModel.setEvent(event);
                recordModel.setType(4);
                recordModel.setAdd_date(new Date());
                recordModel.setIs_mch(0);
                recordModelMapper.insert(recordModel);

                //刷新缓存
                User userCache = userBaseMapper.selectByPrimaryKey(user.getId());
                RedisDataTool.refreshRedisUserCache(token, userCache, redisUtil);
            } else {
                throw new LaiKeApiException(INSUFFICIENT_BALANCE, "余额不足", "pay");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("余额支付 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "walletPay");
        }
    }

    @Override
    public void integralPay(String userId, BigDecimal money, String orderNo, BigDecimal orderPrice, String token) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(userId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "用户id为空");
            }
            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "用户不存在");
            }
            // 用户余额
            BigDecimal userMoney = new BigDecimal(user.getScore());
            // 用户余额 大于 余额抵扣金额
            if (userMoney.compareTo(money) >= 0) {
                // 根据微信id,修改用户余额
                int row = userMapper.scorePayUpdateUserAccount(money, user.getStore_id(), userId);
                if (row < 0) {
                    throw new LaiKeApiException(API_OPERATION_FAILED, "支付失败", "pay");
                }

                //刷新缓存
                User userCache = userBaseMapper.selectByPrimaryKey(user.getId());
                RedisDataTool.refreshRedisUserCache(token, userCache, redisUtil);
            } else {
                throw new LaiKeApiException(INSUFFICIENT_BALANCE, "余额不足", "integralPay");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("积分支付 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "integralPay");
        }
    }

    @Override
    public void walletReturnPay(String userId, BigDecimal money, String token) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(userId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "用户id为空");
            }
            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "用户不存在");
            }
            // 退还
            int row = userMapper.updateUserMoney(money, user.getStore_id(), userId);
            //判断是否添加成功
            if (row > 0) {
                logger.info("余额退款成功");
            } else {
                logger.info("余额退款失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "退款失败");
            }
            // 添加买家退款日志
            String event = userId + "退款" + money + "元余额";
            RecordModel recordModel = new RecordModel();
            recordModel.setStore_id(user.getStore_id());
            recordModel.setUser_id(userId);
            recordModel.setOldmoney(money);
            recordModel.setMoney(money);
            recordModel.setEvent(event);
            recordModel.setType(5);
            row = recordModelMapper.insertSelective(recordModel);

            if (row > 0) {
                logger.info("新增记录成功");
            } else {
                logger.info("新增记录失败");
            }
            //刷新缓存
            RedisDataTool.refreshRedisUserCache(token, userBaseMapper.selectByPrimaryKey(user.getId()), redisUtil);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("钱包退款 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "walletReturnPay");
        }
    }

    @Autowired
    private PaymentModelMapper paymentModelMapper;


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private RedisUtil redisUtil;
}
