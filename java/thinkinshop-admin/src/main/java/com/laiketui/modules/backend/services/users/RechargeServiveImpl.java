package com.laiketui.modules.backend.services.users;

import com.laiketui.api.modules.backend.users.RechargeServive;
import com.laiketui.common.mapper.RecordModelMapper;
import com.laiketui.common.mapper.UserAddressMapper;
import com.laiketui.common.mapper.UserBaseMapper;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.domain.vo.SaveAddressVo;
import com.laiketui.domain.vo.user.RechargeVo;
import com.laiketui.domain.vo.user.UserMoneyVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.algorithm.MobileUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 充值列表
 *
 * @author Trick
 * @date 2021/1/11 15:48
 */
@Service
public class RechargeServiveImpl implements RechargeServive {
    private final Logger logger = LoggerFactory.getLogger(RechargeServiveImpl.class);

    @Override
    public Map<String, Object> getRechargeInfo(RechargeVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("type", "record");
            if (vo.getId() != null && vo.getId() > 0) {
                parmaMap.put("id", vo.getId());
            }
            if (!StringUtils.isEmpty(vo.getUserid())) {
                parmaMap.put("userid", vo.getUserid());
            }
            if (!StringUtils.isEmpty(vo.getPhone())) {
                parmaMap.put("like_mobile", vo.getPhone());
            }
            if (!StringUtils.isEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
                if (!StringUtils.isEmpty(vo.getEndDate())) {
                    parmaMap.put("endDate", vo.getEndDate());
                }
            }
            if (!StringUtils.isEmpty(vo.getUserName())) {
                parmaMap.put("like_name", vo.getUserName());
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataList = recordModelMapper.getUserWalletRecordInfo(parmaMap);
            int total = recordModelMapper.countUserWalletRecordInfo(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取充值记录" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRechargeInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getUserMoneyInfo(UserMoneyVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (!StringUtils.isEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
                if (!StringUtils.isEmpty(vo.getEndDate())) {
                    parmaMap.put("endDate", vo.getEndDate());
                }
            }
            if (!StringUtils.isEmpty(vo.getUserName())) {
                parmaMap.put("user_name", vo.getUserName());
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataList = userBaseMapper.selectDynamic(parmaMap);
            int total = userBaseMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户资金记录" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRechargeInfo");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveAddress(SaveAddressVo vo, String userId) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            //校验手机号是否正确
            if (MobileUtils.isMobile(vo.getMobile())) {
                //是否为默认
                int isDefualt = vo.getIsDefault();
                //拆分省市县
                String[] place = vo.getPlace().split("-");
                //合并地址
                StringBuilder addressXq = new StringBuilder("");
                for (String str : place) {
                    addressXq.append(str);
                }
                addressXq.append(vo.getAddress());

                //获取当前用户默认地址
                UserAddress userAddress = new UserAddress();
                userAddress.setStore_id(vo.getStoreId());
                userAddress.setUid(userId);
                userAddress.setIs_default(1);
                userAddress = userAddressMapper.selectOne(userAddress);
                if (userAddress != null) {
                    //判断是否为默认地址模式
                    if (isDefualt == 1) {
                        //重置当前默认的地址
                        UserAddress updateUserAddress = new UserAddress();
                        updateUserAddress.setId(userAddress.getId());
                        updateUserAddress.setUid(userId);
                        updateUserAddress.setIs_default(0);
                        int count = userAddressMapper.updateById(updateUserAddress);
                        if (count < 1) {
                            logger.info("用户:" + userId + "重置默认地址失败");
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "添加地址失败");
                        }
                    } else {
                        isDefualt = 0;
                    }
                }

                //添加地址
                UserAddress addUserAddress = new UserAddress();
                addUserAddress.setStore_id(vo.getStoreId());
                addUserAddress.setName(vo.getUserName());
                addUserAddress.setTel(vo.getMobile());
                addUserAddress.setSheng(place[0]);
                addUserAddress.setCity(place[1]);
                addUserAddress.setQuyu(place[2]);
                addUserAddress.setAddress(vo.getAddress());
                addUserAddress.setAddress_xq(addressXq.toString());
                addUserAddress.setUid(userId);
                addUserAddress.setIs_default(isDefualt);
                int count = userAddressMapper.insertSelective(addUserAddress);
                if (count < 1) {
                    logger.info("用户:" + userId + "添加地址失败");
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "添加地址失败");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "手机号码不正确", "saveAddress");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("保存地址异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "saveAddress");
        }
    }


    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private UserAddressMapper userAddressMapper;

}
