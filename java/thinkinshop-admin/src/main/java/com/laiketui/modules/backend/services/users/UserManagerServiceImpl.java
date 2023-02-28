package com.laiketui.modules.backend.services.users;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.PublicAddressService;
import com.laiketui.api.common.PublicMemberService;
import com.laiketui.api.common.PublicUserService;
import com.laiketui.api.modules.backend.users.UserManagerService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.mch.UserCollectionModel;
import com.laiketui.domain.order.OrderDetailsModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.user.UserRuleModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.user.AddUserVo;
import com.laiketui.domain.vo.user.UpdateUserVo;
import com.laiketui.domain.vo.user.UserVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * 会员管理
 *
 * @author Trick
 * @date 2021/1/7 10:58
 */
@Service
public class UserManagerServiceImpl implements UserManagerService {
    private final Logger logger = LoggerFactory.getLogger(UserManagerServiceImpl.class);

    @Autowired
    private ConfigModelMapper configModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private PublicMemberService publicMemberService;

    @Autowired
    private PublicAddressService publicAddressService;

    @Override
    public Map<String, Object> getUserInfo(UserVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            User queryUser = new User();
            queryUser.setStore_id(vo.getStoreId());
            queryUser.setUser_id(vo.getUid());
            queryUser.setGrade(vo.getGrade());
            queryUser.setIs_out(StringUtils.toString(vo.getIsOverdue()));
            queryUser.setSource(StringUtils.toString(vo.getSource()));
            queryUser.setMobile(vo.getTel());
            Map<String, Object> parmaMap = JSON.parseObject(JSON.toJSONString(queryUser), new TypeReference<Map<String, Object>>() {
            });
            if(StringUtils.isNotEmpty(vo.getUname())){
                parmaMap.put("user_name", vo.getUname());
            }

            parmaMap.put("Register_data_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> userList = userBaseMapper.selectDynamic(parmaMap);
            int total = userBaseMapper.countDynamic(parmaMap);
            for (Map<String, Object> map : userList) {
                map.put("user_name", map.get("user_name"));
                //用户id
                String userId = String.valueOf(map.get("user_id"));
                //用户的等级id
                int levelId = StringUtils.stringParseInt(String.valueOf(map.get("grade")));
                //订单类型
                String orderType = DictionaryConst.OrdersType.ORDERS_HEADER_GM;

                //获取用户消费金额
                BigDecimal orderPrice = orderModelMapper.sumUserOrderPrice(vo.getStoreId(), userId);
                map.put("z_price", orderPrice);
                //获取用户有效订单数量
                int orderNum = orderModelMapper.countUserEffectiveOrdernoNum(vo.getStoreId(), userId);
                map.put("z_num", orderNum);

                String registerDate = DateUtil.dateFormate(MapUtils.getString(map, "Register_data"), GloabConst.TimePattern.YMDHMS);
                String gradeEndDate = "暂无";
                if (map.containsKey("grade_end")) {
                    gradeEndDate = DateUtil.dateFormate(MapUtils.getString(map, "grade_end"), GloabConst.TimePattern.YMDHMS);
                }
                map.put("Register_data", registerDate);
                map.put("grade_end", gradeEndDate);

                //获取用户等级
                String levelName = "普通会员";
                String gradeDiscount = "暂无折扣";
                UserGradeModel userGradeModel = new UserGradeModel();
                userGradeModel.setStore_id(vo.getStoreId());
                userGradeModel.setId(levelId);
                userGradeModel = userGradeModelMapper.selectOne(userGradeModel);
                if (userGradeModel != null) {
                    levelName = userGradeModel.getName();
                    //获取会员折扣
                    BigDecimal vipDic = BigDecimal.valueOf(publicMemberService.getMemberGradeRate(orderType, userId, vo.getStoreId()));
                    if (new BigDecimal("1").compareTo(vipDic) != 0) {
                        gradeDiscount = vipDic.toString();
                    }
                }
                map.put("grade", levelName);
                map.put("gradeDiscount", gradeDiscount);

                String birthday = "";
                if (map.containsKey("birthday")) {
                    birthday = DateUtil.dateFormate(MapUtils.getString(map, "birthday"), GloabConst.TimePattern.YMDHMS);
                }
                map.put("birthday", birthday);
                String clientId = MapUtils.getString(map, "clientid");
                map.put("clientId", clientId);

                //是否有密码
                User user = userBaseMapper.selectByPrimaryKey(MapUtils.getString(map, "id"));
                boolean loginPwd = StringUtils.isNotEmpty(user.getMima());
                map.put("loginPwd", loginPwd);
                boolean isPaymentPwd = StringUtils.isNotEmpty(user.getPassword());
                map.put("isPaymentPwd", isPaymentPwd);

                //判断店铺是否是该用户的
                int isMch = 0;
                int count = mchModelMapper.countMchIsByUser(vo.getStoreId(), userId);
                if (count > 0) {
                    isMch = 1;
                }
                map.put("is_mch", isMch);

                //获取当前用户默认地址
                UserAddress userAddress = publicAddressService.findAddress(vo.getStoreId(), userId, null);
                map.put("userAddress", userAddress);
            }
            if (vo.getExportType() == 1) {
                exportUserList(userList, response);
                return null;
            }


            resultMap.put("total", total);
            resultMap.put("list", userList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("加载会员列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserInfo");
        }
        return resultMap;
    }

    private void exportUserList(List<Map<String, Object>> goodsList, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"会员ID", "会员账号", "会员手机号", "账户余额", "积分余额", "订单数", "交易金额", "到期时间", "注册时间"};
            //对应字段
            String[] kayList = new String[]{"user_id", "zhanghao", "mobile", "money", "score", "z_num", "z_price", "grade_end", "Register_data"};
            EasyPoiExcelUtil.excelExport("会员列表", headerList, kayList, goodsList, response);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportGoodsData");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUserById(UpdateUserVo vo) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(vo.getUserId())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "userid不能为空", "updateUserById");
            }
            User user = new User();
            user.setStore_id(vo.getStoreId());
            user.setUser_id(vo.getUserId());
            user = userBaseMapper.selectOne(user);
            if (user == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "用户不存在", "updateUserById");
            }
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setUser_name(vo.getUname());
            updateUser.setGrade(vo.getGrade());
            updateUser.setMobile(vo.getPhone());
            updateUser.setMima(vo.getPwd());
            updateUser.setPassword(vo.getPaypwd());
            updateUser.setScore(vo.getJifen());
            updateUser.setMoney(vo.getMoney());
            updateUser.setBirthday(DateUtil.dateFormateToDate(vo.getBirthday(), GloabConst.TimePattern.YMDHMS));
            //验证数据
            updateUser = DataCheckTool.checkUserDataFormate(updateUser);
            if (updateUser.equals(new User())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败,参数错误", "updateUserById");
            }
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (StringUtils.isNotEmpty(vo.getUname()) && !user.getUser_name().equals(vo.getUname())) {
                //验证用户名是否被注册
                Map<String, Object> parmaUnameMap = new HashMap<>(16);
                parmaUnameMap.putAll(parmaMap);
                parmaUnameMap.put("user_name", vo.getUname());
                count = userBaseMapper.countDynamic(parmaUnameMap);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户名已被注册", "updateUserById");
                }
            }
            if (StringUtils.isNotEmpty(vo.getPhone()) && !user.getMobile().equals(vo.getPhone())) {
                //验证手机号是否被注册
                Map<String, Object> parmaPhoneMap = new HashMap<>(16);
                parmaPhoneMap.putAll(parmaMap);
                parmaPhoneMap.put("mobile", vo.getPhone());
                count = userBaseMapper.countDynamic(parmaPhoneMap);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "手机号已被注册", "updateUserById");
                }
            }
            //修改会员等级
            if (vo.getGrade() != null && !user.getGrade().equals(vo.getGrade())) {
                UserGradeModel userGradeModel = userGradeModelMapper.selectByPrimaryKey(vo.getGrade());
                if (userGradeModel != null) {
                    updateUser.setGrade(userGradeModel.getId());
                    Date gradeEnd;
                    if (vo.getGradeType() == null) {
                        updateUser.setGrade_end(new Date());
                    } else {
                        if (vo.getGradeType() != 3) {
                            //包月、季、年
                            if (vo.getGradeType() == 1) {
                                gradeEnd = DateUtil.getAddMonth(new Date(), 1);
                            } else if (vo.getGradeType() == 2) {
                                gradeEnd = DateUtil.getAddMonth(new Date(), 3);
                            } else {
                                gradeEnd = DateUtil.getAddMonth(new Date(), 12);
                            }
                            updateUser.setGrade_end(gradeEnd);
                        }
                    }

                }
            }

            count = userBaseMapper.updateByPrimaryKeySelective(updateUser);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败", "updateUserById");
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("修改会员资料 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateUserById");
        }
    }

    @Override
    public Map<String, Object> getUserGradeType(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            UserRuleModel userRuleModel = userRuleModelMapper.getUserRule(vo.getStoreId());
            List<Map<String, Object>> resultList = new ArrayList<>();
            if (userRuleModel != null) {
                String gradeType = userRuleModel.getMethod();
                String[] list = gradeType.split(SplitUtils.DH);
                for (String type : list) {
                    Map<String, Object> map = new HashMap<>(16);
                    map.put("value", type);
                    switch (type) {
                        case "1":
                            map.put("label", "包月");
                            break;
                        case "2":
                            map.put("label", "包季");
                            break;
                        case "3":
                            map.put("label", "包年");
                            break;
                        default:
                            break;
                    }
                    resultList.add(map);
                }
            }

            resultMap.put("gradeType", resultList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取会员配置升级方式 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserGradeType");
        }
        return resultMap;
    }

    @Override
    public boolean userRechargeMoney(MainVo vo, int id, BigDecimal money, Integer type) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (type == null) {
                type = 1;
            }
            return publicUserService.userRechargeMoney(vo.getStoreId(), id, money, type);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("会员充值余额 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "userRechargeMoney");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delUserById(MainVo vo, int id) throws LaiKeApiException {
        try {
            //获取用户信息
            User user = userBaseMapper.selectByPrimaryKey(id);
            if (user == null) {
                logger.error("用户删除失败:用户不存在");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "用户不存在", "delUserById");
            }
            if (user.getMoney() != null && user.getMoney().doubleValue() > 0) {
                logger.error("用户删除失败:该用户有余额未使用");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "删除失败:该用户有余额未使用", "delUserById");
            }
            int count = orderModelMapper.countUserUnfinishedOrder(vo.getStoreId(), user.getUser_id());
            if (count > 0) {
                logger.error("用户删除失败:该用户有未完成的订单");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "删除失败:该用户有未完成的订单", "delUserById");
            }
            //判断用户会员是否过期
            if (user.getGrade() > 0 && DateUtil.dateCompare(user.getGrade_end(), new Date())) {
                logger.error("用户删除失败:该用户已加入vip,且换在vip会员有效期内");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "删除失败:该用户已加入vip,且换在vip会员有效期内", "delUserById");
            }
            //判断店铺是否未注销
            MchModel mchModel = new MchModel();
            mchModel.setUser_id(user.getUser_id());
            mchModel.setStore_id(vo.getStoreId());
            mchModel.setReview_status(DictionaryConst.MchExameStatus.EXAME_PASS_STATUS.toString());
            count = mchModelMapper.selectCount(mchModel);
            if (count > 0) {
                logger.error("用户删除失败:该用户有未注销的店铺");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "删除失败:该用户有未注销的店铺", "delUserById");
            }

            //删除用户
            count = userBaseMapper.deleteByPrimaryKey(id);
            if (count < 1) {
                logger.error("用户删除失败:用户删除失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败", "delUserById");
            }
            //删除用户订单
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(vo.getStoreId());
            orderModel.setUser_id(user.getUser_id());
            count = orderModelMapper.delete(orderModel);
            logger.error("删除订单{}个 userid={}", count, user.getUser_id());

            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setStore_id(vo.getStoreId());
            orderDetailsModel.setUser_id(user.getUser_id());
            count = orderDetailsModelMapper.delete(orderDetailsModel);
            logger.error("订单明细删除{}个 userid={}", count, user.getUser_id());
            //删除用户地址
            UserAddress userAddress = new UserAddress();
            userAddress.setStore_id(vo.getStoreId());
            userAddress.setUid(user.getUser_id());
            count = userAddressMapper.delete(userAddress);
            logger.error("用户地址删除{}个 userid={}", count, user.getUser_id());
            //删除用户收藏历史
            UserCollectionModel userCollectionModel = new UserCollectionModel();
            userCollectionModel.setStore_id(vo.getStoreId());
            userCollectionModel.setUser_id(user.getUser_id());
            count = userCollectionModelMapper.delete(userCollectionModel);
            logger.error("用户历史删除{}个 userid={}", count, user.getUser_id());

            //删除用户店铺信息
            count = mchModelMapper.delete(mchModel);
            logger.error("用户店铺删除{}个 userid={}", count, user.getUser_id());

            return true;
        } catch (LaiKeApiException l) {
            logger.error(l.getMessage());
            throw l;
        } catch (Exception e) {
            logger.error("删除用户 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delUserById");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(AddUserVo vo) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(vo.getUserName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "用户名不能为空");
            } else if (vo.getGrade() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "会员等级不能为空");
            } else if (StringUtils.isEmpty(vo.getPhone())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "手机号不能为空");
            } else if (StringUtils.isEmpty(vo.getMima())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "密码不能为空");
            } else if (StringUtils.isEmpty(vo.getZhanghao())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "账号不能为空");
            }
            User saveUser = new User();
            saveUser.setStore_id(vo.getStoreId());
            saveUser.setUser_name(vo.getUserName());
            saveUser.setSource(String.valueOf(vo.getSource()));
            saveUser.setZhanghao(vo.getZhanghao());
            saveUser.setMobile(vo.getPhone());
            saveUser.setGrade(vo.getGrade());
            saveUser.setMima(vo.getMima());
            saveUser.setHeadimgurl(vo.getHeaderUrl());
            //验证数据
            saveUser = DataCheckTool.checkUserDataFormate(saveUser);
            //生成userId
            String userId = "user";
            //获取配置信息
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel != null) {
                saveUser.setWx_name(configModel.getWx_name());
                if (StringUtils.isEmpty(saveUser.getHeadimgurl())) {
                    saveUser.setHeadimgurl(publiceService.getImgPath(configModel.getWx_headimgurl(), vo.getStoreId()));
                }
                if (StringUtils.isNotEmpty(configModel.getUser_id())) {
                    userId = configModel.getUser_id();
                }
            }
            userBaseMapper.insertSelective(saveUser);

            User userParameter = userBaseMapper.selectByPrimaryKey(saveUser.getId());
            userParameter.setUser_id(userId + (saveUser.getId() + 1));
            int count = userBaseMapper.updateByPrimaryKeySelective(userParameter);
            if (count < 1) {
                logger.error(saveUser.getId() + ": userid生成失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.REGISTER_FAIL, "注册失败,请联系管理员");
            }
        } catch (LaiKeApiException l) {
            logger.error(l.getMessage());
            throw l;
        } catch (Exception e) {
            logger.error("后台添加会员 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delUserById");
        }
    }


    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private UserGradeModelMapper userGradeModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Autowired
    private UserCollectionModelMapper userCollectionModelMapper;

    @Autowired
    private UserRuleModelMapper userRuleModelMapper;
}
