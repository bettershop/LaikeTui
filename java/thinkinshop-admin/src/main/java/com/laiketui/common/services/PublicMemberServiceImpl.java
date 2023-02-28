package com.laiketui.common.services;

import com.laiketui.api.common.PublicMemberService;
import com.laiketui.api.common.order.PublicIntegralService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.IntegralConfigModel;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.user.UserRuleModel;
import com.laiketui.domain.vo.plugin.integral.AddScoreVo;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.utils.tool.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;

import static com.laiketui.root.consts.ErrorCode.BizErrorCode.*;

/**
 * 会员通用服务
 *
 * @author wangxian
 */
@Service
public class PublicMemberServiceImpl implements PublicMemberService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public double getMemberGradeRate(String orderType, String userId, int storeId) {
        try {
            int flag = 1;
            if (StringUtils.isNotEmpty(orderType)) {
                switch (orderType) {
                    case DictionaryConst.OrdersType.ORDERS_HEADER_GM:
                        flag = 1;
                        break;
                    case DictionaryConst.OrdersType.ORDERS_HEADER_PT:
                    case DictionaryConst.OrdersType.ORDERS_HEADER_KJ:
                    case DictionaryConst.OrdersType.ORDERS_HEADER_JP:
                    case DictionaryConst.OrdersType.ORDERS_HEADER_FX:
                    case DictionaryConst.OrdersType.ORDERS_HEADER_MS:
                    case DictionaryConst.OrdersType.ORDERS_HEADER_TH:
                        flag = -1;
                        break;
                    default:
                        break;
                }
            }
            UserRuleModel ruleModel = new UserRuleModel();
            ruleModel.setStore_id(storeId);
            UserRuleModel userRuleModel = userRuleModelMapper.selectOne(ruleModel);
            //是否可以参与特惠 true 可以 false 不能
            boolean can = false;
            String actives = userRuleModel.getActive();
            if (!StringUtils.isEmpty(actives)) {
                for (String active : userRuleModel.getActive().split(SplitUtils.DH)) {
                    if (active.equals("" + flag)) {
                        can = true;
                        break;
                    }
                }
            }

            //
            double rate = 1.0;
            if (can) {
                Example example = new Example(User.class);
                Example.Criteria criteria = example.createCriteria();
                criteria.andEqualTo("store_id", storeId);
                criteria.andEqualTo("user_id", userId);
                criteria.andGreaterThan("grade_end", new Date());
                User user = userMapper.selectOneByExample(example);
                if (user != null) {
                    int grade = user.getGrade();
                    UserGradeModel userGradeModel = new UserGradeModel();
                    userGradeModel.setId(grade);
                    UserGradeModel userGrade = userGradeModelMapper.selectOne(userGradeModel);
                    if (userGrade != null) {
                        BigDecimal userGradeRateate = userGrade.getRate();
                        rate = userGradeRateate.divide(new BigDecimal(10)).doubleValue();
                    }
                }
            }
            return rate;
        } catch (Exception e) {
            logger.error("会员优惠计算 异常", e);
            throw new LaiKeApiException(MEMBER_YOUHUI_ERROR, "会员优惠计算失败", "getMemberGradeRate");
        }
    }

    @Override
    public void memberSettlement(int storeId, String userId, String sNo, BigDecimal zPrice, int type) throws LaiKeApiException {
        try {
            int row;
            String event;
            //积分插件是否打开
            IntegralConfigModel integralConfigModel = new IntegralConfigModel();
            if (integralConfigModel == null || integralConfigModel.getStatus() == 0) {
                logger.debug("积分插件未打开 不计算积分");
                return;
            }

            UserRuleModel userRuleModel = new UserRuleModel();
            userRuleModel.setStore_id(storeId);
            userRuleModel = userRuleModelMapper.selectOne(userRuleModel);

            //获取是否开启生日特权 0-不开启 1-开启
            int is_birthday = 0;
            // 获取生日特权积分倍数
            int bir_multiple = 0;

            // 积分发送规则 0-付款后 1-收货后
            int jifen_m = 1;

            //是否打开vip等比例积分开关
            boolean vipProportion = false;
            if (userRuleModel != null) {
                //获取是否开启生日特权 0-不开启 1-开启
                is_birthday = userRuleModel.getIs_birthday();
                //生日特权倍数
                bir_multiple = userRuleModel.getBir_multiple();

                //Vip等比例
                vipProportion = userRuleModel.getIs_jifen() == 1;
                //会员等比例积分发送规则 1-付款后 0-收货后
                jifen_m = userRuleModel.getJifen_m() == 0 ? 1 : 0;
            }

            //查询是否是会员身份
            BigDecimal grade = BigDecimal.valueOf(this.getMemberGradeRate(DictionaryConst.OrdersType.ORDERS_HEADER_GM, userId, storeId));

            User user = new User();
            user.setStore_id(storeId);
            user.setUser_id(userId);
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(DATA_NOT_EXIST, "会员不存在");
            }
            AddScoreVo addScoreVo = new AddScoreVo();
            addScoreVo.setType(8);
            addScoreVo.setOrderNo(sNo);
            addScoreVo.setUserId(userId);
            addScoreVo.setStoreId(storeId);
            addScoreVo.setScoreOld(user.getScore());

            int score = 0;
            if (new BigDecimal("1").compareTo(grade) > 0) {
                // 生日特权流程 生日当天购买任意商品赠*n积分
                if (is_birthday == 1 && type == 1) {
                    if (user.getBirthday() != null) {
                        String birthday = DateUtil.dateFormate(user.getBirthday(), GloabConst.TimePattern.MD);
                        String time = DateUtil.dateFormate(new Date(), GloabConst.TimePattern.MD);
                        if (time.equals(birthday)) {
                            score = zPrice.multiply(new BigDecimal(bir_multiple)).intValue();
                            //记录积分
                            event = String.format("%s 生日期间购物获得%s积分 [%s]", userId, score, "支付后");
                            addScoreVo.setScore(score);
                            addScoreVo.setEvent(event);
                        }
                    }
                } else if (vipProportion && jifen_m == type) {
                    //vip等比例积分
                    score = zPrice.setScale(0, BigDecimal.ROUND_UP).intValue();
                    //记录积分
                    event = String.format("%s vip等比例购物获得%s积分 [%s]", userId, score, type == 1 ? "支付后" : "收货后");
                    addScoreVo.setScore(score);
                    addScoreVo.setEvent(event);
                }

            }

        } catch (LaiKeApiException l) {
            logger.error("会员结算 异常", l);
            throw l;
        } catch (Exception e) {
            logger.error("会员结算 异常", e);
            throw new LaiKeApiException(MEMBER_YOUHUI_ERROR, "会员优惠计算失败", "memberSettlement");
        }
    }

    @Override
    public void returnUserMoney(int storeId, String userId, BigDecimal price) throws LaiKeApiException {
        try {
            //获取买家信息
            User clientUser = new User();
            clientUser.setUser_id(userId);
            clientUser.setStore_id(storeId);
            clientUser = userBaseMapper.selectOne(clientUser);
            if (clientUser != null) {
                String text = clientUser.getUser_id() + "退款" + price + "元余额";
                //买家当前余额
                BigDecimal currentPrice = clientUser.getMoney();
                //余额退还
                int count = userBaseMapper.rechargeUserPrice(clientUser.getId(), price);
                if (count < 1) {
                    logger.info("用户退款>余额退还 失败! " + text);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "returnUserMoney");
                }
                //记录一笔退还记录
                RecordModel recordModel = new RecordModel();
                recordModel.setStore_id(storeId);
                recordModel.setUser_id(userId);
                recordModel.setMoney(price);
                recordModel.setOldmoney(currentPrice);
                recordModel.setEvent(text);
                recordModel.setType(RecordModel.RECORDTYPE_RETURNAMT);
                count = recordModelMapper.insertSelective(recordModel);

                if (count < 1) {
                    logger.info("用户退款>记录退款信息 失败! " + text);
                } else {
                    logger.info("用户退款>记录退款 成功! " + text);
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "未找到用户信息", "returnUserMoney");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("退还钱包 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "returnUserMoney");
        }
    }

    @Autowired
    private UserRuleModelMapper userRuleModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGradeModelMapper userGradeModelMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

}
