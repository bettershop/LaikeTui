package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.laiketui.api.common.PublicUserService;
import com.laiketui.api.common.PubliceService;
import com.laiketui.api.common.order.PublicIntegralService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.mch.BankCardModel;
import com.laiketui.domain.mch.MchConfigModel;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.user.FinanceConfigModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.plugin.integral.AddScoreVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.algorithm.Md5Util;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.jwt.JwtUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * 会员公共接口
 *
 * @author Trick
 * @date 2020/12/23 10:26
 */
@Service
public class PublicUserServiceImpl implements PublicUserService {
    private final Logger logger = LoggerFactory.getLogger(PublicUserServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public BigDecimal orderTotal(int storeId, int id, String userId, int flag, int method) throws LaiKeApiException {
        BigDecimal totalAmt = new BigDecimal("0");
        try {
            //获取当前等级信息
            UserGradeModel currentGradeModel = new UserGradeModel();
            currentGradeModel.setId(id);
            currentGradeModel.setStore_id(storeId);
            currentGradeModel = userGradeModelMapper.selectOne(currentGradeModel);
            if (currentGradeModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "非法数据", "orderTotal");
            }
            if (flag != 3) {
                //非升级
                if (method == 1) {
                    totalAmt = currentGradeModel.getMoney();
                } else if (method == 2) {
                    totalAmt = currentGradeModel.getMoney_j();
                } else if (method == 3) {
                    totalAmt = currentGradeModel.getMoney_n();
                }
            } else {
                //升级 获取会员等级信息
                Map<String, Object> userGreadInfo = userBaseMapper.getUserGradeExpire(storeId, userId);
                if (userGreadInfo != null && !userGreadInfo.isEmpty()) {
                    //包月金额
                    BigDecimal baoyueAmt = new BigDecimal(String.valueOf(userGreadInfo.get("bymoney")));
                    //到期时间 年月日
                    String gradeEnd = DateUtil.dateFormate(String.valueOf(userGreadInfo.get("grade_end")), GloabConst.TimePattern.YMD);
                    //当前时间
                    String nowDate = DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD);
                    //拆分年月日
                    String[] gradeEndList = gradeEnd.split("-");
                    String[] nowDateList = nowDate.split("-");
                    //月份倍数
                    BigDecimal monthNum;
                    //根据要升级的等级计算费用
                    int num = DateUtil.calculationMonthNum(gradeEndList, nowDateList);
                    monthNum = new BigDecimal(num);
                    //需要升级的会员一个月金额 - 当前用户会员一个月的金额 * 月数
                    totalAmt = monthNum.multiply(currentGradeModel.getMoney().subtract(baoyueAmt));
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请先开通会员", "orderTotal");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("会员等级订单金额计算 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "orderTotal");
        }

        return totalAmt;
    }

    @Override
    public boolean balancePay(String accessId, BigDecimal payPrice, String text, int type) throws LaiKeApiException {
        try {
            User userCache = RedisDataTool.getRedisUserCache(accessId, redisUtil);
            if (userCache != null) {
                int count = userBaseMapper.rechargeUserPrice(userCache.getId(), payPrice.negate());
                if (count < 1) {
                    logger.info("余额支付失败");
                    return false;
                }
                //记录一笔记录
                RecordModel recordModel = new RecordModel(userCache.getStore_id(), userCache.getUser_id(), payPrice, userCache.getMoney(), new Date(), text, type);
                if (recordModelMapper.insertSelective(recordModel) < 1) {
                    logger.info("余额支付失败 -金额记录失败 参数{}", JSON.toJSONString(recordModel));
                    return false;
                }
                //刷新用户信息
                User userInfo = new User();
                userInfo.setId(userCache.getId());
                userInfo = userBaseMapper.selectOne(userInfo);
                RedisDataTool.refreshRedisUserCache(accessId, userInfo, redisUtil);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.LOGIN_INVALID, "请重新登录");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("余额支付 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "balancePay");
        }
        return true;
    }


    @Override
    public void validatePayPwd(String userId, String pwd) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(userId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            if (StringUtils.isEmpty(pwd)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请输入支付密码");
            }
            User user = new User();
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            //重试次数
            Integer validateNum = user.getLogin_num();
            if (validateNum.equals(GloabConst.LktConfig.PASSWORD_VALIDATE_NUM)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.USER_PASSWORD_OVER, "已被锁定,请明天再试", "paymentPassword");
            }
            if (StringUtils.isEmpty(user.getPassword())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "未设置支付密码");
            }
            User userParam = new User();
            userParam.setId(user.getId());
            pwd = Md5Util.MD5endoce(pwd);
            if (user.getPassword().equals(pwd)) {
                //验证成功
                userParam.setLogin_num(0);
                int count = userBaseMapper.updateByPrimaryKeySelective(userParam);
                if (count < 0) {
                    logger.info("用户 id = " + user.getId() + " 支付密码验证通过,login_num 未清空");
                }
            } else {
                userParam.setLogin_num(++validateNum);
                userBaseMapper.updateUserInfoById(userParam);
                logger.info("用户 id = " + user.getId() + " 正在重试支付密码 当前次数 " + userParam.getLogin_num());
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_PASSWORD_ERROR, "支付密码不正确");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("验证支付密码 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "validatePayPwd");
        }
    }

    @Override
    public boolean userRechargeMoney(int storeId, int id, BigDecimal money, int type) throws LaiKeApiException {
        try {
            int count;
            //之前金额
            BigDecimal oldPrice;
            //记录类型
            int recordType;

            //获取用户信息
            User user = userBaseMapper.selectByPrimaryKey(id);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户不存在", "userRechargeMoney");
            }
            if (money.compareTo(BigDecimal.ZERO) == 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "您输入的充值金额为空或金额不正确");
            }

            boolean isRechaarge = true;
            String text = "系统%s";
            if (money.compareTo(BigDecimal.ZERO) > 0) {
                text = String.format(text, "充值" + money);
            } else {
                isRechaarge = false;
                text = String.format(text, "扣除" + money);
            }
            //1=余额 2=消费金额 3=积分
            switch (type) {
                case 1:
                    if (isRechaarge) {
                        //系统充值
                        recordType = 14;
                    } else {
                        //系统扣款
                        recordType = 11;
                        BigDecimal amt = user.getMoney().add(money);
                        if (user.getMoney().compareTo(BigDecimal.ZERO) <= 0 || amt.compareTo(BigDecimal.ZERO) < 0) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户余额不足,不能进行扣除");
                        }
                    }
                    text += "余额";
                    oldPrice = user.getMoney();
                    count = userBaseMapper.rechargeUserPrice(id, money);
                    break;
                case 2:
                    if (isRechaarge) {
                        //系统充值消费金额
                        recordType = 16;
                    } else {
                        //系统扣
                        recordType = 18;
                        BigDecimal amt = user.getConsumer_money().add(money);
                        if (user.getConsumer_money().compareTo(BigDecimal.ZERO) <= 0 || amt.compareTo(BigDecimal.ZERO) < 0) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户消费积分不足,不能进行扣除");
                        }
                    }
                    text += "消费金额";
                    oldPrice = user.getConsumer_money();
                    count = userBaseMapper.rechargeUserByConsumerMoney(id, money);
                    break;
                case 3:
                    //记录积分
                    AddScoreVo addScoreVo = new AddScoreVo();
                    addScoreVo.setOrderNo("");
                    addScoreVo.setUserId(user.getUser_id());
                    addScoreVo.setStoreId(storeId);
                    addScoreVo.setScoreOld(user.getScore());
                    //记录积分
                    String event = String.format("系统充值%s积分", money);
                    if (isRechaarge) {
                        //系统充值积分
                        recordType = 15;
                        addScoreVo.setType(6);
                    } else {
                        //系统扣
                        event = String.format("系统扣除%s积分", money);
                        recordType = 17;
                        addScoreVo.setType(5);
                        int amt = user.getScore() + money.intValue();
                        if (user.getScore() <= 0 || amt < 0) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户积分不足,不能进行扣除");
                        }
                    }
                    addScoreVo.setEvent(event);
                    addScoreVo.setScore(money.abs().intValue());
                    text += "积分";
                    oldPrice = new BigDecimal(String.valueOf(user.getScore()));
                    count = userBaseMapper.rechargeUserByScore(id, money);
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "userRechargeMoney");
            }
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败", "userRechargeMoney");
            }
            //添加充值记录
            RecordModel recordModel = new RecordModel(storeId, user.getUser_id(), money, oldPrice, new Date(), text, recordType);
            recordModelMapper.insertSelective(recordModel);

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("会员 充值/消费 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "userRechargeMoney");
        }
    }

    @Override
    public Map<String, Object> getIntoWallet(MainVo vo, Integer shopId, User user) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            user = userBaseMapper.selectByPrimaryKey(user.getId());
            BigDecimal minCharge;
            BigDecimal maxCharge;
            //商户/用户 余额
            BigDecimal accountMoney;
            //可用余额
            BigDecimal cashableMoney;
            String phone;
            String serviceChargeStr;
            //单位
            String unit = "元";

            //获取店铺/用户银行卡信息
            List<Map<String, Object>> bankCardModelMaps = new ArrayList<>();
            BankCardModel bankCardModel = new BankCardModel();
            bankCardModel.setStore_id(vo.getStoreId());
            bankCardModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);

            if (shopId != null) {
                //获取店铺信息
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(vo.getStoreId());
                mchModel.setUser_id(user.getUser_id());
                mchModel.setReview_status(DictionaryConst.ExameStatus.EXAME_PASS_STATUS);
                mchModel = mchModelMapper.selectOne(mchModel);
                if (mchModel != null) {
                    bankCardModel.setMch_id(mchModel.getId());
                    phone = mchModel.getTel();
                    accountMoney = mchModel.getAccount_money();
                    cashableMoney = mchModel.getCashable_money();
                    if (mchModel.getId().equals(shopId)) {
                        //获取店铺配置信息
                        MchConfigModel mchConfigModel = new MchConfigModel();
                        mchConfigModel.setStore_id(vo.getStoreId());
                        mchConfigModel = mchConfigModelMapper.selectOne(mchConfigModel);
                        if (mchConfigModel != null) {
                            minCharge = mchConfigModel.getMin_charge();
                            maxCharge = mchConfigModel.getMax_charge();
                            BigDecimal serviceCharge = mchConfigModel.getService_charge();
                            serviceCharge = serviceCharge.multiply(new BigDecimal("100"));
                            serviceChargeStr = serviceCharge + "%";
                            resultMap.put("total", cashableMoney.toString());
                        } else {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_NOT_DATA, "未获取到店铺配置信息", "intoWallet1");
                        }
                    } else {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户与店铺不匹配", "intoWallet1");
                    }
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在", "intoWallet1");
                }
            } else {
                bankCardModel.setUser_id(user.getUser_id());
                phone = user.getMobile();
                accountMoney = user.getMoney();
                //获取钱包配置信息
                FinanceConfigModel financeConfigModel = new FinanceConfigModel();
                financeConfigModel.setStore_id(vo.getStoreId());
                financeConfigModel = financeConfigModelMapper.selectOne(financeConfigModel);
                if (financeConfigModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_CONFIG, "钱包配置不存在");
                }
                unit = financeConfigModel.getUnit();
                minCharge = financeConfigModel.getMin_amount();
                maxCharge = financeConfigModel.getMax_amount();
                BigDecimal serviceCharge = financeConfigModel.getService_charge();
                serviceCharge = serviceCharge.multiply(new BigDecimal("100"));
                serviceChargeStr = serviceCharge + "%";

            }
            List<BankCardModel> bankCardModelList = bankCardModelMapper.select(bankCardModel);
            for (BankCardModel bankCard : bankCardModelList) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", bankCard.getId());
                map.put("Cardholder", bankCard.getCardholder());
                map.put("Bank_name", bankCard.getBank_name());
                String str = "%s 尾号(%s)";
                String bankNum = bankCard.getBank_card_number();
                map.put("Bank_card_number", String.format(str, bankCard.getBank_name(), bankNum.substring(bankNum.length() - 4)));
                map.put("branch", bankCard.getBranch());
                bankCardModelMaps.add(map);
            }

            StringBuilder pshd = new StringBuilder("请输入提现金额(大于").append(minCharge).append("小于").append(maxCharge).append(")");

            resultMap.put("min_amount", minCharge);
            resultMap.put("max_amount", maxCharge);
            resultMap.put("money", accountMoney);
            resultMap.put("pshd", pshd);
            resultMap.put("unit", unit);
            resultMap.put("bank_information", bankCardModelMaps);
            resultMap.put("mobile", phone);
            resultMap.put("service_charge", serviceChargeStr);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取店铺提现页面数据 异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getMchIntoWallet");
        }
        return resultMap;
    }

    @Override
    public void register(MainVo vo, String pid, User user) throws LaiKeApiException {
        try {
            //用户id
            String userId = "";
            //获取用户默认信息
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel != null) {
                if (StringUtils.isEmpty(user.getUser_name())) {
                    user.setUser_name(configModel.getWx_name());
                }
                if (StringUtils.isEmpty(user.getHeadimgurl())) {
                    user.setHeadimgurl(publiceService.getImgPath(configModel.getWx_headimgurl(), vo.getStoreId()));
                }
                userId = configModel.getUser_id();
            }
            if (StringUtils.isEmpty(userId)) {
                userId = "user";
            }
            //获取推荐人,没有推荐人默认第一条用户为推荐人
            String fatherId = "";
            User fatherUserInfo = userBaseMapper.getUserTop(vo.getStoreId());
            if (fatherUserInfo != null) {
                fatherId = fatherUserInfo.getUser_id();
            }
            //是否有传token,如果有token则表示进入商品详情
            String token = vo.getAccessId();
            if (StringUtils.isEmpty(token)) {
                //获取token
                token = JwtUtils.getToken();
            }
            user.setGrade(0);
            user.setMoney(new BigDecimal("0"));
            user.setScore(0);
            user.setLang(vo.getLanguage());
            user.setSource(String.valueOf(vo.getStoreType()));
            user.setAccess_id(token);
            user.setStore_id(vo.getStoreId());
            user.setReferee(fatherId);
            user.setRegister_data(new Date());
            int count = userBaseMapper.insertSelective(user);
            if (count < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.REGISTER_FAIL, "注册失败");
            }
            User userParameter = userBaseMapper.selectByPrimaryKey(user.getId());
            userParameter.setUser_id(userId + (user.getId() + 1));
            count = userBaseMapper.updateByPrimaryKeySelective(userParameter);
            if (count < 1) {
                logger.error(user.getId() + ": userid生成失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.REGISTER_FAIL, "注册失败,请联系管理员");
            }
            userId = userParameter.getUser_id();
            User loginUser = new User();
            loginUser.setUser_id(userId);
            User userNew = userBaseMapper.selectOne(loginUser);
            if (userNew == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "注册失败,网络繁忙");
            }
            BeanUtils.copyProperties(userNew, user);
            //操作记录
            String text = "会员%s注册成功";
            if (saveUserRecord(vo.getStoreId(), user.getUser_id(), String.format(text, user.getUser_id()), 0)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "注册失败,网络繁忙");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("注册用户公共方法 异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "register");
        }
    }

    @Override
    public boolean saveUserRecord(int storeId, String userId, String text, int type) throws LaiKeApiException {
        try {
            RecordModel recordModel = new RecordModel();
            recordModel.setStore_id(storeId);
            recordModel.setUser_id(userId);
            recordModel.setEvent(text);
            recordModel.setType(type);
            recordModel.setAdd_date(new Date());
            int count = recordModelMapper.insertSelective(recordModel);
            return count <= 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加一条操作记录 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "saveUserRecord");
        }
    }

    @Autowired
    private UserGradeModelMapper userGradeModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private MchConfigModelMapper mchConfigModelMapper;

    @Autowired
    private FinanceConfigModelMapper financeConfigModelMapper;

    @Autowired
    private BankCardModelMapper bankCardModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private ConfigModelMapper configModelMapper;
}
