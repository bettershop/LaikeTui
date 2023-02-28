package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.AdminUserService;
import com.laiketui.common.mapper.AdminModelMapper;
import com.laiketui.common.mapper.CustomerModelMapper;
import com.laiketui.common.mapper.RecordModelMapper;
import com.laiketui.common.mapper.SystemTellModelMapper;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.CustomerModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.UpdateAdminVo;
import com.laiketui.domain.vo.user.AdminLoginVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.algorithm.Md5Util;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.jwt.JwtUtils;
import com.laiketui.root.utils.tool.DataCheckTool;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 商城后台登录
 *
 * @author Trick
 * @date 2021/1/26 11:30
 */
@Service
public class AdminUserServiceImpl implements AdminUserService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CustomerModelMapper customerModelMapper;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private SystemTellModelMapper systemTellModelMapper;

    @Override
    public Map<String, Object> login(AdminLoginVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            if (StringUtils.isEmpty(vo.getUserName()) || StringUtils.isEmpty(vo.getPwd())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请输入账号/密码");
            }

            AdminModel adminModel = new AdminModel();
            adminModel.setName(vo.getUserName());
            adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            if (StringUtils.isNotEmpty(vo.getCustomerNumber())) {
                //客户登录
                CustomerModel customerModel = new CustomerModel();
                customerModel.setCustomer_number(vo.getCustomerNumber());
                customerModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                customerModel = customerModelMapper.selectOne(customerModel);
                if (customerModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请输入正确的商户编号");
                }
                adminModel.setStore_id(customerModel.getId());
            } else {
                //管理员登录
                adminModel.setStore_id(0);
            }
            adminModel.setPassword(Md5Util.MD5endoce(vo.getPwd()));
            adminModel = adminModelMapper.selectOne(adminModel);
            if (adminModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "账号或密码错误,请重新输入");
            }
            //还有几天过期
            Integer endDay = null;
            //非管理员
            if (adminModel.getType() != 0) {
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("store_id", 0);
                parmaMap.put("startDate_lt", new Date());
                parmaMap.put("endDate_gt", new Date());
                //系统是否正在维护中
                if (systemTellModelMapper.countDynamic(parmaMap) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "系统正在维护中");
                }

                CustomerModel customerModel = customerModelMapper.selectByPrimaryKey(adminModel.getStore_id());
                if (customerModel != null) {
                    if (customerModel.getStatus() == 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "您的授权已到期,请联系管理员再使用,谢谢");
                    } else if (customerModel.getStatus() == 2) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "您的商城已锁定,请联系管理员再使用,谢谢");
                    }
                    //到期时间
                    Date endDate = customerModel.getEnd_date();
                    //提前一个星期提醒客户
                    Date dingDate = DateUtil.getAddDate(endDate, -7);
                    if (!DateUtil.dateCompare(endDate, new Date())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "您的授权已到期,请联系客服完成续费再使用,谢谢");
                    }
                    if (DateUtil.dateCompare(new Date(), dingDate)) {
                        //提醒客户商城要过期了
                        endDay = Integer.parseInt(DateUtil.dateConversion(endDate.getTime() / 1000 - System.currentTimeMillis(), DateUtil.TimeType.DAY) + "");
                    }
                }
            }
            if (adminModel.getPassword().equals(Md5Util.MD5endoce(vo.getPwd()))) {
                if (adminModel.getLogin_num() >= 3) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "账号已锁定,请联系客服");
                }
                if (adminModel.getStatus() == 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "账号已被禁用!若有疑问,请与商城管理员联系");
                }
                //生成token
                String token = JwtUtils.getToken();
                //登录成功
                AdminModel adminUpdate = new AdminModel();
                adminUpdate.setId(adminModel.getId());
                adminUpdate.setToken(token);
                adminUpdate.setLogin_num(0);
                adminUpdate.setStatus(2);
                adminModelMapper.updateByPrimaryKeySelective(adminUpdate);
            } else {
                //登录失败
                if (StringUtils.isNotEmpty(vo.getCustomerNumber())) {
                    if (adminModel.getType() != 0) {
                        adminModelMapper.adminLoginFail(adminModel.getId());
                    }
                    if (adminModel.getLogin_num() + 1 >= 3) {
                        //三次输入错误锁定账户
                        AdminModel adminUpdate = new AdminModel();
                        adminUpdate.setId(adminModel.getId());
                        adminUpdate.setStatus(1);
                        adminModelMapper.updateByPrimaryKeySelective(adminUpdate);
                        if (adminModel.getType() != 0) {
                            //如果是客户,锁定商城
                            CustomerModel customerUpdate = new CustomerModel();
                            customerUpdate.setId(adminModel.getStore_id());
                            customerUpdate.setStatus(2);
                            customerModelMapper.updateByPrimaryKeySelective(customerUpdate);
                        }
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "账号已锁定,请联系客服");
                    }
                }
                RecordModel recordModel = new RecordModel();
                recordModel.setStore_id(vo.getStoreId());
                recordModel.setUser_id(vo.getUserName());
                recordModel.setEvent("登录密码错误");
                recordModelMapper.insertSelective(recordModel);
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "密码错误,请重新输入");
            }
            adminModel = adminModelMapper.selectByPrimaryKey(adminModel);
            //之前用户是否登录,如果登录则删除缓存
            String tokenOld = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN + adminModel.getId()) + "";
            if (!StringUtils.isEmpty(tokenOld)) {
                redisUtil.del(tokenOld);
            }
            //映射token 用于单点登录
            redisUtil.set(GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN + adminModel.getToken(), adminModel, GloabConst.LktConfig.LOGIN_EXISTENCE_TIME);
            redisUtil.set(GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_FLAG + adminModel.getId(), GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN + adminModel.getToken(), GloabConst.LktConfig.LOGIN_EXISTENCE_TIME);

            RecordModel recordModel = new RecordModel();
            recordModel.setStore_id(vo.getStoreId());
            recordModel.setUser_id(vo.getUserName());
            recordModel.setEvent("登录成功");
            recordModelMapper.insertSelective(recordModel);

            String info = "登录成功!";
            if (endDay != null) {
                String dingStr = String.format("您的账号还有%s天到期,请及时续费!", endDay);
                if (endDay == 0) {
                    dingStr = "您的账号将在今日到期,请及时续费";
                }
                info = dingStr;
            }
            resultMap.put("status", 1);
            resultMap.put("info", info);
            resultMap.put("name", adminModel.getName());
            resultMap.put("token", adminModel.getToken());
            resultMap.put("mchId", adminModel.getShop_id());
            resultMap.put("storeId", adminModel.getStore_id());
            resultMap.put("portrait", adminModel.getPortrait());
            resultMap.put("nickname", adminModel.getNickname());
            resultMap.put("birthday", adminModel.getBirthday());
            resultMap.put("sex", adminModel.getSex());
            resultMap.put("phone", adminModel.getTel());
            resultMap.put("type", adminModel.getType());
            resultMap.put("role", adminModel.getRole());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("后台登录 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "login");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> setUserAdmin(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (adminModel.getType() == 0) {
                //只有系统管理员才执行
                CustomerModel customerModel = customerModelMapper.selectByPrimaryKey(vo.getStoreId());
                if (customerModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商城不存在");
                }
                AdminModel storeAdmin = adminModelMapper.selectByPrimaryKey(customerModel.getAdmin_id());
                if (storeAdmin == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商城没有管理员");
                }
                adminModel.setStore_id(storeAdmin.getStore_id());
                adminModel.setShop_id(storeAdmin.getShop_id());
                //刷新缓存
                RedisDataTool.refreshRedisAdminCache(vo.getAccessId(), adminModel, GloabConst.RedisHeaderKey.LOGIN_ACCESS_MANAGE_TOKEN, redisUtil);
            }
            resultMap.put("mchId", adminModel.getShop_id());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("赋予管理员商城信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setUserAdmin");
        }
        return resultMap;
    }

    @Override
    public void updateAdminInfo(UpdateAdminVo vo) throws LaiKeApiException {
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            adminModel = adminModelMapper.selectByPrimaryKey(adminModel.getId());

            AdminModel adminUpdate = new AdminModel();
            adminUpdate.setId(adminModel.getId());
            adminUpdate.setNickname(vo.getNickname());
            adminUpdate.setPortrait(vo.getPortrait());
            adminUpdate.setBirthday(vo.getBirthday());
            adminUpdate.setSex(vo.getSex());
            adminUpdate.setTel(vo.getPhone());

            if (StringUtils.isNotEmpty(vo.getPasswordOld()) && StringUtils.isNotEmpty(vo.getPassword())) {
                if (DataCheckTool.checkLength(adminModel.getPassword(), 6, 20)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员密码长度为 6-20");
                }
                String pwd = Md5Util.MD5endoce(vo.getPasswordOld());
                if (!adminModel.getPassword().equals(pwd)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "原密码不正确");
                }
                pwd = Md5Util.MD5endoce(vo.getPassword());
                if (adminModel.getPassword().equals(pwd)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "原密与旧密码相同");
                }
                adminUpdate.setPassword(pwd);
            }

            int row = adminModelMapper.updateByPrimaryKeySelective(adminUpdate);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("修改基本信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateAdminInfo");
        }
    }
}
