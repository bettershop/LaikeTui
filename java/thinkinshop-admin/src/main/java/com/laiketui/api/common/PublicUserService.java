package com.laiketui.api.common;

import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 关于用户公共接口
 *
 * @author Trick
 * @date 2020/12/23 10:23
 */
public interface PublicUserService {


    /**
     * 会员等级金额计算
     * 【php: rechargeService.orderTotal】
     *
     * @param storeId -
     * @param id      - 会员等级id
     * @param userId  -用户id
     * @param flag    - 开通-续费-升级 标识
     * @param method  - 开通时长
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/23 10:13
     */
    BigDecimal orderTotal(int storeId, int id, String userId, int flag, int method) throws LaiKeApiException;


    /**
     * 余额支付
     *
     * @param accessId -
     * @param payPrice -
     * @param text     - 操作信息
     * @param type     - 操作类型 :RecordModel.BUYING_MEMBERS...
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/23 14:38
     */
    boolean balancePay(String accessId, BigDecimal payPrice, String text, int type) throws LaiKeApiException;

    /**
     * 验证支付密码
     *
     * @param userId -
     * @param pwd    -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/28 16:27
     */
    void validatePayPwd(String userId, String pwd) throws LaiKeApiException;


    /**
     * 给用户充值
     *
     * @param storeId -
     * @param id      - 用户主键id
     * @param money   - 充值/扣减
     * @param type    - 1=余额 2=消费金额 3=积分
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 17:09
     */
    boolean userRechargeMoney(int storeId, int id, BigDecimal money, int type) throws LaiKeApiException;

    /**
     * 获取店铺/用户提现数据
     *
     * @param vo     -
     * @param shopId -
     * @param user   -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/18 15:17
     */
    Map<String, Object> getIntoWallet(MainVo vo, Integer shopId, User user) throws LaiKeApiException;

    /**
     * 注册用户公共方法
     *
     * @param vo   -
     * @param pid  -
     * @param user -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/30 17:20
     */
    void register(MainVo vo, String pid, User user) throws LaiKeApiException;

    /**
     * 添加一条操作记录
     *
     * @param storeId -
     * @param userId  -
     * @param text    -
     * @param type    -
     * @return boolean -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/25 14:46
     */
    boolean saveUserRecord(int storeId, String userId, String text, int type) throws LaiKeApiException;
}
