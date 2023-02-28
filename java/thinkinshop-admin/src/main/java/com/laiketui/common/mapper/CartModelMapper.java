package com.laiketui.common.mapper;

import com.laiketui.domain.mch.CartModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 购物车
 *
 * @author Trick
 * @date 2020/10/20 10:29
 */
public interface CartModelMapper extends BaseMapper<CartModel> {


    /**
     * 获取用户/游客购物车数据
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/20 10:31
     */
    List<Map<String, Object>> getUserShopCartList(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取pc商城购物车数据
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/17 14:22
     */
    List<Map<String, Object>> getUserPcShopCartList(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 根据id修改购物车
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/21 10:51
     */
    int updateCartById(Map<String, Object> map) throws LaiKeApiException;
}