package com.laiketui.common.mapper;

import com.laiketui.domain.user.User;
import com.laiketui.root.exception.LaiKeApiException;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 用户dao层接口
 *
 * @author Trick
 * @date 2020/9/23 10:07
 */
public interface UserBaseMapper extends Mapper<User> {


    /**
    * 重写
    *
    * @param user -
    * @return User
    * @author Trick
    * @date 2021/10/27 15:17
    */
    @Override
    User selectOne(User user);

    @Override
    @Select("select a.*,b.id mchId from lkt_user a left join lkt_mch b on a.user_id=b.user_id and b.recovery=0 where a.id=#{id}")
    User selectByPrimaryKey(Object id);

    /**
     * 修改用户资料
     *
     * @param user -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/28 17:30
     */
    int updateUserInfoById(User user) throws LaiKeApiException;


    /**
     * 更新token
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/19 14:28
     */
    int updateUserAccessId(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 根据账号/手机号获取用户信息
     *
     * @param user -
     * @return User
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/6 10:45
     */
    @Select("select * from lkt_user where store_id = #{store_id} " +
            " and (zhanghao = #{zhanghao} or mobile = #{zhanghao}) " +
            " limit 1 ")
    User getUserByzhanghao(User user) throws LaiKeApiException;


    /**
     * 验证账号是否被注册
     *
     * @param storeId  -
     * @param zhanghao -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/25 14:09
     */
    @Select("select count(1) from lkt_user where store_id = #{storeId}  and (zhanghao = #{zhanghao} or mobile = #{zhanghao})  ")
    int validataUserPhoneOrNoIsRegister(int storeId, String zhanghao) throws LaiKeApiException;


    /**
     * 余额增减
     *
     * @param uid   -
     * @param price -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 17:19
     */
    @Update("update lkt_user set money = money + #{price} where id = #{uid}")
    int rechargeUserPrice(int uid, BigDecimal price) throws LaiKeApiException;


    /**
     * 积分增减
     *
     * @param id    -
     * @param price -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 17:19
     */
    @Update("update lkt_user set score = score + #{price} where id = #{id}")
    int rechargeUserByScore(int id, BigDecimal price) throws LaiKeApiException;


    /**
     * 消费金额增减
     *
     * @param id    -
     * @param price -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 17:19
     */
    @Update("update lkt_user set consumer_money = consumer_money + #{price} where id = #{id}")
    int rechargeUserByConsumerMoney(int id, BigDecimal price) throws LaiKeApiException;


    /**
     * 重置密码次数
     *
     * @param storeId -
     * @param time    -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/16 13:46
     */
    @Update("update lkt_user set login_num = 0 where verification_time <= #{time} and store_id = #{storeId} and login_num > 0")
    int resettingPwdNum(int storeId, String time) throws LaiKeApiException;


    /**
     * 重置token
     *
     * @param storeId -
     * @param token   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/24 17:36
     */
    @Update("update lkt_user set access_id ='' where store_id = #{storeId} and access_id=#{token}")
    int resettingToken(int storeId, String token) throws LaiKeApiException;


    /**
     * 重置token
     *
     * @param storeId -
     * @param token   -
     * @return int
     * @author Trick
     * @date 2021/5/26 10:16
     */
    @Update("update lkt_user set mch_token ='' where store_id = #{storeId} and mch_token=#{token}")
    int resettingMchToken(int storeId, String token);


    /**
     * 获取会员等级信息
     *
     * @param storeId -
     * @param userId  -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/23 10:39
     */
    @Select("select  a.*,b.rate,b.money bymoney,b.money_j,b.money_n,b.id from lkt_user as a left join lkt_user_grade as b on a.grade = b.id " +
            "where a.store_id = #{storeId} and a.store_id = b.store_id and a.user_id = #{userId}")
    Map<String, Object> getUserGradeExpire(int storeId, String userId) throws LaiKeApiException;


    /**
     * 统计新注册用户-微信
     *
     * @param storId    -
     * @param startDate -
     * @param endDate   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 11:08
     */
    @Select("select COUNT(*) as sum,DATE_FORMAT(Register_data,'%Y-%m-%d') as rdate from lkt_user where store_id = #{storId} " +
            "and source in (1,3,4,5) group by rdate " +
            "having rdate between #{startDate} " +
            "and #{endDate} " +
            "order by rdate desc")
    List<Map<String, Object>> getNewAddUserWxInfo(int storId, String startDate, String endDate) throws LaiKeApiException;


    /**
     * 统计新注册用户 - app
     *
     * @param storId    -
     * @param startDate -
     * @param endDate   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 11:08
     */
    @Select("select COUNT(*) as sum,DATE_FORMAT(Register_data,'%Y-%m-%d') as rdate from lkt_user where store_id = #{storId} " +
            "and source =2 group by rdate " +
            "having rdate between #{startDate} " +
            "and #{endDate} " +
            "order by rdate desc")
    List<Map<String, Object>> getNewAddUserAppInfo(int storId, String startDate, String endDate) throws LaiKeApiException;


    /**
     * 统计新注册用户 - pc
     *
     * @param storId    -
     * @param startDate -
     * @param endDate   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 11:08
     */
    @Select("select COUNT(*) as sum,DATE_FORMAT(Register_data,'%Y-%m-%d') as rdate from lkt_user where store_id = #{storId} " +
            "and source = 6 group by rdate " +
            "having rdate between #{startDate} " +
            "and #{endDate} " +
            "order by rdate desc")
    List<Map<String, Object>> getNewAddUserPcInfo(int storId, String startDate, String endDate) throws LaiKeApiException;


    /**
     * 会员消费报表
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 16:37
     */
    List<Map<String, Object>> selectUserConsumptionReport(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 会员消费报表-统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 16:37
     */
    int countUserConsumptionReport(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取用户信息
     *
     * @param map -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 11:23
     */
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取用户信息 - 统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 11:23
     */
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取第一条用户信息
     *
     * @param storeId -
     * @return User
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/7 15:48
     */
    @Select("select * from lkt_user where store_id =#{storeId}  limit 0,1")
    User getUserTop(int storeId) throws LaiKeApiException;


    /**
     * 动态统计店铺营业额
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    List<Map<String, Object>> sumMchTurnoverReport(Map<String, Object> map);
    int countMchTurnoverReport(Map<String, Object> map);

    @Select("<script>" +
            "SELECT DATE_FORMAT(register_data,'%Y-%m-%d') date,IFNULL(count(distinct id),0) num FROM `lkt_user` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            " AND mchId = #{mchId} " +
            " </if> " +
            " AND Register_data <![CDATA[  >=  ]]> #{startDate} and Register_data <![CDATA[  <=  ]]> #{endDate} " +
            " group by DATE_FORMAT(Register_data,'%Y-%m-%d')" +
            " </script> ")
    List<Map<String, Object>> getNewUserByDate(int storeId, Integer mchId, String startDate, String endDate);

    /**
     * wx_id条件查询
     * @param openId
     * @return
     */
    @Select("select * from lkt_user where wx_id = #{openId}")
    User getUserByOpenId(String openId);

    /**
     * 查询手机号是否绑定其他账号
     * @param accessId
     * @param mobile
     * @return
     */
    @Select("select id from lkt_user where store_id =  #{storeId} and (access_id != #{accessId} or access_id is null) and mobile = #{mobile} and is_lock = 0")
    User getUserByMobile(int storeId, String accessId, String mobile);

    /**
     * 同步用户信息
     * @param accessId
     * @param mobile
     * @param storeId
     * @return
     */
    @Update("update lkt_user set mobile = #{mobile} where store_id = #{storeId} and access_id = #{accessId} ")
    int syncUserInfo(String accessId,String mobile,int storeId);


    /**
     * 根据商户ID 和token id 获取 用户信息
     * @param accessId
     * @param accessId
     * @return
     */
    @Select("select * from lkt_user where store_id = #{storeId} and access_id = #{accessId}")
    User getUserByToken(String accessId, int storeId);


    @Select("select user_id from lkt_user where store_id=#{storeId}")
    List<String> getUserAllByUserId(int storeId);

}
