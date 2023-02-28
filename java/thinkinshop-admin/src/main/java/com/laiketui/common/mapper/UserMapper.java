package com.laiketui.common.mapper;

import com.laiketui.domain.user.User;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;

/**
 * @author wangxian
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 取消订单时调用 反钱
     *
     * @param zPirce
     * @param storeId
     * @param userId
     * @return
     */
    @Update("update lkt_user set money = money + #{zPirce} where store_id = #{storeId} and user_id = #{userId} ")
    int updateUserMoney(BigDecimal zPirce, int storeId, String userId);

    /**
     * 更新用户登陆次数
     *
     * @param storeId
     * @param userId
     * @return
     */
    @Update("update lkt_user set login_num = 0 where store_id = #{storeId} and user_id = #{userId} ")
    int updateUserLoginnum(int storeId, String userId);

    /**
     * 余额支付更新账户
     *
     * @param storeId
     * @param userId
     * @return
     */
    @Update("update lkt_user set money = money-#{paymentMoney} where store_id = #{storeId} and user_id = #{userId} and money > 0")
    int walletPayUpdateUserAccount(BigDecimal paymentMoney, int storeId, String userId);

    /**
     * 积分支付
     */
    @Update("update lkt_user set score = score-#{paymentMoney} where store_id = #{storeId} and user_id = #{userId} and score > 0")
    int scorePayUpdateUserAccount(BigDecimal paymentMoney, int storeId, String userId);

    /**
     * 更新账户积分
     *
     * @param storeId
     * @param userId
     * @return
     */
    @Update(" update lkt_user set score = score+ #{score} where store_id = #{storeId} and user_id = #{userId}  ")
    int updateUserScore(int score, int storeId, String userId);


}