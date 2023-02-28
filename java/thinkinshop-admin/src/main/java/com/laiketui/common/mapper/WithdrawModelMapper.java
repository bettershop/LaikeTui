package com.laiketui.common.mapper;

import com.laiketui.domain.user.WithdrawModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 提现表 sql
 *
 * @author Trick
 * @date 2020/11/3 15:35
 */
public interface WithdrawModelMapper extends BaseMapper<WithdrawModel> {


    /**
     * 动态统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 15:40
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取店铺提现信息 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/30 11:44
     */
    List<Map<String, Object>> getWithdrawLeftUserBankAndMch(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取提现信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 14:21
     */
    List<Map<String, Object>> getWithdrawLeftUserBank(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 统计提现信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 14:21
     */
    int countWithdrawLeftUserBank(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取提现信息 - 店铺
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 14:21
     */
    List<Map<String, Object>> getWithdrawLeftMchBank(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 统计提现信息 - 店铺
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 14:21
     */
    int countWithdrawLeftMchBank(Map<String, Object> map) throws LaiKeApiException;
}