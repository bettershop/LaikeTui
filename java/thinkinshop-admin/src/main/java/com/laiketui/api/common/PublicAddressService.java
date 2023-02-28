package com.laiketui.api.common;

import com.laiketui.domain.user.UserAddress;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;


/**
 * 通用用户收货地址
 *
 * @author wangxian
 */
public interface PublicAddressService {

    /**
     * 获取用户地址
     *
     * @return
     */
    UserAddress findAddress(Map<String, Object> map);


    /**
     * 获取用户地址
     * 【php Tool.find_address】
     *
     * @param storeId -
     * @param uid     -
     * @param id      - 地址id -可选
     * @return UserAddress
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/26 17:46
     */
    UserAddress findAddress(int storeId, String uid, Integer id) throws LaiKeApiException;
}
