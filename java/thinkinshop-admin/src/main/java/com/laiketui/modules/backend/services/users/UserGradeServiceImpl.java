package com.laiketui.modules.backend.services.users;

import com.laiketui.api.modules.backend.users.UserGradeService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.user.UserRuleModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.user.AddUserGradeVo;
import com.laiketui.domain.vo.user.AddUserRuleVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 用户等级管理
 *
 * @author Trick
 * @date 2021/1/8 11:26
 */
@Service
public class UserGradeServiceImpl implements UserGradeService {
    private final Logger logger = LoggerFactory.getLogger(UserGradeServiceImpl.class);

    @Override
    public Map<String, Object> getUserGradeInfo(MainVo vo, Integer gid) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            UserGradeModel userGradeModel = new UserGradeModel();
            userGradeModel.setStore_id(vo.getStoreId());
            if (gid != null && gid > 0) {
                userGradeModel.setId(gid);
            }
            List<Map<String, Object>> userGradeModelList = userGradeModelMapper.getUserGradeInfoPage(userGradeModel, vo.getPageNo(), vo.getPageSize());
            int total = userGradeModelMapper.countUserGradeInfoPage(userGradeModel);
            //获取晋升条件
            List<String> gradeRuleList = new ArrayList<>();
            UserRuleModel userRuleModel = new UserRuleModel();
            userRuleModel.setStore_id(vo.getStoreId());
            userRuleModel = userRuleModelMapper.selectOne(userRuleModel);
            if (userRuleModel != null) {
                gradeRuleList = Arrays.asList(userRuleModel.getUpgrade().split(","));
            }
            for (Map<String, Object> map : userGradeModelList) {
                String imgUrl = publiceService.getImgPath(MapUtils.getString(map, "imgurl"), vo.getStoreId());
                String imgUrls = publiceService.getImgPath(MapUtils.getString(map, "imgurl_s"), vo.getStoreId());

                map.put("imgurl", imgUrl);
                map.put("imgurl_s", imgUrls);
            }

            resultMap.put("total", total);
            resultMap.put("list", userGradeModelList);
            resultMap.put("upgrade", gradeRuleList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取会员等级列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUserGradeInfo");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserGrade(AddUserGradeVo vo) throws LaiKeApiException {
        try {
            int count;
            UserGradeModel oldGradeModel = null;
            if (vo.getId() != null && vo.getId() > 0) {
                oldGradeModel = new UserGradeModel();
                oldGradeModel.setStore_id(vo.getStoreId());
                oldGradeModel.setId(vo.getId());
                oldGradeModel = userGradeModelMapper.selectOne(oldGradeModel);
                if (oldGradeModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "会员等级不存在");
                }
            }
            if (StringUtils.isEmpty(vo.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "会员等级名称不能为空");
            } else if (vo.getDiscountRate() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "会员折扣不能为空");
            }
            if (vo.getMoney() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包月金额不能为空");
            } else if (vo.getMoneyJ() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包季金额不能为空");
            } else if (vo.getMoneyN() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包年金额不能为空");
            }
            UserGradeModel saveUsergrade = new UserGradeModel();
            saveUsergrade.setName(vo.getName());
            saveUsergrade.setMoney(vo.getMoney());
            saveUsergrade.setMoney_j(vo.getMoneyJ());
            saveUsergrade.setMoney_n(vo.getMoneyN());
            saveUsergrade.setRemark(vo.getRemarks());
            saveUsergrade.setRate(vo.getDiscountRate());
            saveUsergrade.setPro_id(vo.getGoodsId());
            saveUsergrade.setFont_color(vo.getFontColor());
            saveUsergrade.setDate_color(vo.getDateColor());

            if (oldGradeModel != null && !StringUtils.isEmpty(saveUsergrade.getName()) && oldGradeModel.getName().equals(saveUsergrade.getName())) {
                logger.debug("名称未变动,无需校验");
                saveUsergrade.setName(null);
                saveUsergrade.setId(vo.getId());
            } else {
                if (!StringUtils.isEmpty(saveUsergrade.getName())) {
                    //验证名称
                    if ("普通会员".equals(vo.getName())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "等级名称已存在");
                    }
                    UserGradeModel userGradeModel = new UserGradeModel();
                    userGradeModel.setStore_id(vo.getStoreId());
                    userGradeModel.setName(vo.getName());
                    count = userGradeModelMapper.selectCount(userGradeModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "等级名称已存在");
                    }
                    saveUsergrade.setStore_id(vo.getStoreId());
                }
            }
            //验证折扣
            if (oldGradeModel != null && saveUsergrade.getRate() != null && oldGradeModel.getRate().compareTo(saveUsergrade.getRate()) == 0) {
                logger.debug("折扣未变动,无需校验");
            } else {
                if (saveUsergrade.getRate() != null) {
                    //验证折扣
                    UserGradeModel userGradeModel = new UserGradeModel();
                    userGradeModel.setStore_id(vo.getStoreId());
                    userGradeModel.setRate(saveUsergrade.getRate());
                    count = userGradeModelMapper.selectCount(userGradeModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "折扣率不能与其它级别想相同");
                    }
                }
            }

            //校验折扣规则
            if (vo.getDiscountRate() != null) {
                //获取会员等级规则
                UserRuleModel userRuleModel = new UserRuleModel();
                userRuleModel.setStore_id(vo.getStoreId());
                userRuleModel = userRuleModelMapper.selectOne(userRuleModel);
                if (userRuleModel == null || StringUtils.isEmpty(userRuleModel.getMethod())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "会员等级规则未配置");
                }
                List<String> moneyList = Arrays.asList(userRuleModel.getMethod().split(","));

                //级别未发生变化则不校验规则
                if (oldGradeModel == null || oldGradeModel.getRate().compareTo(vo.getDiscountRate()) != 0) {
                    //不能比上级便宜
                    Map<String, Object> parmaMap = new HashMap<>(16);
                    parmaMap.put("store_id", vo.getStoreId());
                    //上下级不能是自己,如果是自己则不校验
                    if (oldGradeModel != null) {
                        parmaMap.put("current_rate", oldGradeModel.getRate());
                    }
                    parmaMap.put("rate", saveUsergrade.getRate());
                    parmaMap.put("rate_symbol", "lt");
                    parmaMap.put("rate_sort", DataUtils.Sort.DESC.toString());
                    parmaMap.put("pageStart", 0);
                    parmaMap.put("pageEnd", 1);
                    List<Map<String, Object>> gradeMapList = userGradeModelMapper.getUserGradeInfoDynamic(parmaMap);
                    for (Map<String, Object> map : gradeMapList) {
                        BigDecimal money = (BigDecimal) map.get("money");
                        BigDecimal moneyj = (BigDecimal) map.get("money_j");
                        BigDecimal moneyn = (BigDecimal) map.get("money_n");
                        if (vo.getMoney().compareTo(money) >= 0 && moneyList.contains("1")) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包月金额应比上一级等级小");
                        } else if (vo.getMoney().compareTo(moneyj) >= 0 && moneyList.contains("2")) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包季金额应比上一级等级小");
                        } else if (vo.getMoney().compareTo(moneyn) >= 0 && moneyList.contains("3")) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包季金额应比上一级等级小");
                        }
                    }
                    //不能比下级贵
                    parmaMap.put("rate_sort", DataUtils.Sort.ASC.toString());
                    parmaMap.put("rate_symbol", "gt");
                    gradeMapList = userGradeModelMapper.getUserGradeInfoDynamic(parmaMap);
                    for (Map<String, Object> map : gradeMapList) {
                        BigDecimal money = (BigDecimal) map.get("money");
                        BigDecimal moneyj = (BigDecimal) map.get("money_j");
                        BigDecimal moneyn = (BigDecimal) map.get("money_n");
                        if (money.compareTo(vo.getMoney()) >= 0 && moneyList.contains("1")) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包月金额应比下一级等级大");
                        } else if (moneyj.compareTo(vo.getMoney()) >= 0 && moneyList.contains("2")) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包季金额应比下一级等级大");
                        } else if (moneyn.compareTo(vo.getMoney()) >= 0 && moneyList.contains("3")) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "包年金额应比下一级等级大");
                        }
                    }
                }
            }
            //月费不得高于季费，季费不得高于年费
            if (vo.getMoney().compareTo(vo.getMoneyJ()) >= 0 || vo.getMoneyJ().compareTo(vo.getMoneyN()) >= 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "月费不得高于季费，季费不得高于年费");
            }
            //图片处理
            if (!StringUtils.isEmpty(vo.getBackImage())) {
                saveUsergrade.setImgurl(ImgUploadUtils.getUrlImgByName(vo.getBackImage(), true));
            }
            if (!StringUtils.isEmpty(vo.getMiniImage())) {
                saveUsergrade.setImgurl_s(ImgUploadUtils.getUrlImgByName(vo.getMiniImage(), true));
            }
            if (oldGradeModel != null) {
                saveUsergrade.setId(oldGradeModel.getId());
                count = userGradeModelMapper.updateByPrimaryKeySelective(saveUsergrade);
            } else {
                count = userGradeModelMapper.insertSelective(saveUsergrade);
            }

            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("保存/编辑会员等级 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addUserGrade");
        }
    }

    @Override
    public Map<String, Object> getGiveGoodsList(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            List<Map<String, Object>> goodsList = productListModelMapper.getGiveGoodsList(vo.getStoreId());
            resultMap.put("list", goodsList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取赠送商品列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGiveGoodsList");
        }
        return resultMap;
    }

    @Override
    public boolean delUserGrade(MainVo vo, int id) throws LaiKeApiException {
        try {
            //判断是否有用户在该等级
            User user = new User();
            user.setGrade(id);
            int count = userBaseMapper.selectCount(user);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "已有用户为该等级会员，不可删除");
            }
            count = userGradeModelMapper.deleteByPrimaryKey(id);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除会员等级 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delUserGrade");
        }
    }

    @Override
    public Map<String, Object> getUserConfigInfo(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //默认名称
            String defaultName = "";
            //默认头像
            String defaultImgUrl = "";
            //获取会员规则
            UserRuleModel userRuleModel = new UserRuleModel();
            userRuleModel.setStore_id(vo.getStoreId());
            List<UserRuleModel> userRuleModelList = userRuleModelMapper.select(userRuleModel);
            for (UserRuleModel userRule : userRuleModelList) {
                userRule.setPoster(publiceService.getImgPath(userRule.getPoster(), vo.getStoreId()));
            }

            //获取配置信息
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel != null) {
                defaultName = configModel.getWx_name();
                defaultImgUrl = configModel.getWx_headimgurl();
                defaultImgUrl = publiceService.getImgPath(defaultImgUrl, vo.getStoreId());
            }
            //获取会员等级
            UserGradeModel userGradeModel = new UserGradeModel();
            userGradeModel.setStore_id(vo.getStoreId());
            List<UserGradeModel> userGradeModelList = userGradeModelMapper.select(userGradeModel);

            resultMap.put("wx_name", defaultName);
            resultMap.put("wx_headimgurl", defaultImgUrl);
            resultMap.put("grade", userGradeModelList);
            resultMap.put("gradeRule", userRuleModelList);
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除会员等级 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delUserGrade");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addUserRule(AddUserRuleVo vo) throws LaiKeApiException {
        try {
            int count;
            //获取会员分享海报
            String posterImg = ImgUploadUtils.getUrlImgByName(vo.getPoster(), true);
            //获取头像和用户名
            String wxName;
            String wxHeadimg;
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请完善系统设置");
            }
            wxName = vo.getWxName();
            wxHeadimg = ImgUploadUtils.getUrlImgByName(vo.getWxImgUrl(), true);
            ConfigModel updateConfigModel = new ConfigModel();
            updateConfigModel.setId(configModel.getId());
            updateConfigModel.setStore_id(configModel.getStore_id());
            if (StringUtils.isNotEmpty(wxHeadimg)) {
                updateConfigModel.setWx_headimgurl(wxHeadimg);
            }
            if (StringUtils.isNotEmpty(wxName)) {
                updateConfigModel.setWx_name(wxName);
            }
            count = configModelMapper.updateByPrimaryKeySelective(updateConfigModel);
            logger.debug("设置用户默认信息 {}", count > 0);

            if (vo.getIsProduct() == 0) {
                //重置每个会员等级赠送商品
                count = userGradeModelMapper.updateUserGradeReProId(vo.getStoreId());
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "关闭会员赠送商品失败");
                }
            }
            //购物赠送积分开关
            if (vo.getProportionOfGifts() == 1) {
                //比例验证
                if (vo.getProportionOfGifts() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "赠送比例请填写大于0的整数");
                } else if (vo.getProportionOfGifts() > 100) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "赠送比例请填写小于100的整数");
                }
                //过期时间验证
                if (vo.getScore() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "积分过期时间请填写大于0的整数");
                }
            }
            //自动续费开关
            if (vo.getIsAutoSwitch() == 1) {
                if (vo.getAutoDayExpire() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "自动续费到期日期请填写大于0的整数");
                }
            }
            //积分返回倍数
            if (vo.getBirMultiple() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "积分返回倍数请填写大于0的整数");
            }
            //赠送商品有效期
            if (vo.getValid() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "赠送商品有效期请填写大于0的整数");
            }
            //等级晋升设置 1-购买会员服务 2-补差额 1是必须有的
            StringBuilder upgrade = new StringBuilder("1");
            if (StringUtils.isNotEmpty(vo.getUpgrade())) {
                upgrade.append(SplitUtils.DH).append(vo.getUpgrade());
            }
            vo.setUpgrade(upgrade.toString());

            //返现比例 返现比例去掉了
            /*if (vo.getBackScale() == null || vo.getBackScale().doubleValue() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "返现百分比请填写大于0的数");
            }*/
            //是否存在配置 不存在则添加
            UserRuleModel userRuleModel = new UserRuleModel();
            userRuleModel.setStore_id(vo.getStoreId());
            userRuleModel = userRuleModelMapper.selectOne(userRuleModel);
            UserRuleModel saveUserRule = new UserRuleModel();
            saveUserRule.setStore_id(vo.getStoreId());
            saveUserRule.setIs_auto(vo.getIsAutoSwitch());
            saveUserRule.setAuto_time(vo.getAutoDayExpire());
            saveUserRule.setMethod(vo.getMethod());
            saveUserRule.setActive(vo.getActive());
            saveUserRule.setIs_wallet(vo.getIsWallet());
            saveUserRule.setUpgrade(vo.getUpgrade());
            saveUserRule.setIs_birthday(vo.getIsBirthday());
            saveUserRule.setBir_multiple(vo.getBirMultiple());
            saveUserRule.setIs_product(vo.getIsProduct());
            saveUserRule.setRule(vo.getRule());
            saveUserRule.setIs_jifen(vo.getIsJifen());
            saveUserRule.setJifen_m(vo.getJifenM());
            saveUserRule.setBack(vo.getBack());
//            saveUserRule.setBack_scale(vo.getBackScale());
            saveUserRule.setPoster(posterImg);
            saveUserRule.setIs_limit(vo.getIsLimit());
            saveUserRule.setLevel(vo.getLevel());
            saveUserRule.setDistribute_l(vo.getDistributeL());
            saveUserRule.setValid(vo.getValid());
            saveUserRule.setScore(vo.getScore());
            saveUserRule.setBonus_points_shopping(vo.getBonusPointsShopping());
            saveUserRule.setProportion_of_gifts(vo.getProportionOfGifts());
            saveUserRule.setRelease_time(vo.getReleaseTime());
            if (userRuleModel != null) {
                //修改
                saveUserRule.setId(userRuleModel.getId());
                count = userRuleModelMapper.updateByPrimaryKeySelective(saveUserRule);
            } else {
                //添加
                saveUserRule.setStore_id(vo.getStoreId());
                count = userRuleModelMapper.insertSelective(saveUserRule);
            }
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加会员等级配置 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addUserRule");
        }
    }

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private UserGradeModelMapper userGradeModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private UserRuleModelMapper userRuleModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private ConfigModelMapper configModelMapper;
}
