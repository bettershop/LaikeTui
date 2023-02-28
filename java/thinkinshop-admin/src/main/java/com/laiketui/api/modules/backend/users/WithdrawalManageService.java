package com.laiketui.api.modules.backend.users;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.user.WalletVo;
import com.laiketui.domain.vo.user.WithdrawalVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 提现管理接口
 *
 * @author Trick
 * @date 2021/1/11 14:05
 */
public interface WithdrawalManageService {


    /**
     * 获取提现列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 14:07
     */
    Map<String, Object> getWithdrawalInfo(WithdrawalVo vo, HttpServletResponse response) throws LaiKeApiException;

    /**
     * 获取提现记录
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/10 14:41
     */
    Map<String, Object> getWithdrawalRecord(WithdrawalVo vo, HttpServletResponse response) throws LaiKeApiException;

    /**
     * 提现审核
     *
     * @param vo     -
     * @param id     -
     * @param status - 状态 0：审核中 1：审核通过 2：拒绝
     * @param refuse -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 15:32
     */
    void withdrawalExamine(MainVo vo, int id, int status, String refuse) throws LaiKeApiException;

    /**
     * 获取钱包参数信息
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 15:01
     */
    Map<String, Object> getWalletInfo(MainVo vo) throws LaiKeApiException;


    /**
     * 设置钱包参数
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 15:02
     */
    boolean setWalletInfo(WalletVo vo) throws LaiKeApiException;
}
