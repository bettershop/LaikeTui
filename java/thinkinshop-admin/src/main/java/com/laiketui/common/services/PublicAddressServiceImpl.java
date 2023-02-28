package com.laiketui.common.services;

import com.laiketui.api.common.PublicAddressService;
import com.laiketui.common.mapper.UserAddressMapper;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.root.exception.LaiKeApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 通用地址服务
 *
 * @author wangxian
 */
@Service("commonAddressService")
public class PublicAddressServiceImpl implements PublicAddressService {

    private Logger logger = LoggerFactory.getLogger(PublicAddressServiceImpl.class);

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public UserAddress findAddress(Map<String, Object> map) {
        UserAddress userAddress = new UserAddress();
        try {
            userAddress.setUid((String) map.get("user_id"));
            userAddress.setStore_id((Integer) map.get("store_id"));
            Object addressIdObj = map.get("address_id");
            if (addressIdObj != null) {
                userAddress.setId((Integer) addressIdObj);
            }
            /**
             * 根据不同的条件来查找用户的地址
             */
            List<UserAddress> retList = userAddressMapper.select(userAddress);
            if (!CollectionUtils.isEmpty(retList)) {
                return retList.get(0);
            } else {
                userAddress.setIs_default(1);
                retList = userAddressMapper.select(userAddress);
                if (!CollectionUtils.isEmpty(retList)) {
                    userAddress = retList.get(0);
                } else {
                    userAddress.setIs_default(null);
                    retList = userAddressMapper.select(userAddress);
                    if (!CollectionUtils.isEmpty(retList)) {
                        userAddress = retList.get(0);
                        userAddress.setIs_default(1);
                        userAddressMapper.updateByPrimaryKeySelective(userAddress);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        return userAddress;
    }

    @Override
    public UserAddress findAddress(int storeId, String uid, Integer id) throws LaiKeApiException {
        UserAddress resultUserAddress;
        try {
            //获取收货地址信息
            UserAddress userAddress = new UserAddress();
            userAddress.setStore_id(storeId);
            userAddress.setUid(uid);
            if (id != null && id > 0) {
                userAddress.setId(id);
                resultUserAddress = userAddressMapper.selectOne(userAddress);
                if (resultUserAddress != null) {
                    return resultUserAddress;
                }
            }
            //获取用户默认地址
            userAddress.setIs_default(1);
            resultUserAddress = userAddressMapper.selectOne(userAddress);
            if (resultUserAddress == null) {
                //获取最新地址
                resultUserAddress = userAddressMapper.getUserAddress(storeId, uid);
                if (resultUserAddress == null) {
                    //没有地址
                    return null;
                }
                //把最新地址设置成默认地址
                UserAddress userAddressUpdate = new UserAddress();
                userAddressUpdate.setId(userAddress.getId());
                userAddressUpdate.setIs_default(1);
                int count = userAddressMapper.updateByPrimaryKeySelective(userAddressUpdate);
                if (count > 0) {
                    resultUserAddress.setIs_default(1);
                }
            }
        } catch (Exception e) {
            logger.error("获取用户地址 异常:", e);
            return null;
        }
        return resultUserAddress;
    }
}
