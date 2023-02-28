package com.laiketui.common.mapper;

import com.laiketui.domain.user.UserAddress;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @description 用户地址
 * @author wx
 * @date 2019/10/19 11:49
 * @return
 */
public interface UserAddressMapper extends BaseMapper<UserAddress> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     *  修改用户地址动态sql
     *
     * @author Trick
     * @date 2020/11/4 18:20
     * @param userAddress -
     * @return int
     * @throws  LaiKeApiException    -
     */
    int updateById(UserAddress userAddress)throws LaiKeApiException;


    /**
     *  设置默认地址
     * 最新的一条
     * @author Trick
     * @date 2020/11/5 13:56
     * @param uid -
     * @return int
     * @throws   LaiKeApiException   -
     */
    @Update("update lkt_user_address set is_default = 1 where uid = #{uid} order by id desc limit 1")
    int setDefaultAddress(String uid) throws LaiKeApiException;

    /**
     * 当用户没有默认地址时用最新的地址
     *
     * @param userId  -
     * @param storeId -
     * @return UserAddress
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/26 17:51
     */
    @Select("select * from lkt_user_address where store_id =#{storeId} and uid=#{userId} order by id desc limit 1")
    UserAddress getUserAddress(int storeId, String userId) throws LaiKeApiException;
}