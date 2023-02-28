package com.laiketui.common.mapper;

import com.laiketui.domain.mch.MchModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 店铺 sql
 *
 * @author Trick
 * @date 2020/10/15 15:04
 */
public interface MchModelMapper extends BaseMapper<MchModel> {


    /**
     * 获取搜索最多的店铺信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/15 15:05
     */
    @Select("select * from lkt_mch where store_id = #{store_id} and review_status = 1 order by collection_num desc limit #{page},#{pageSize}")
    List<MchModel> getHotMch(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 模糊查询店铺信息
     *
     * @param storeId   -
     * @param mchName   -
     * @param pageStart -
     * @param pageEnd   -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 10:45
     */
    @Select("<script> " +
            "select * from lkt_mch where store_id = #{storeId} and review_status = 1 " +
            "<if test=\"mchName != null and mchName!='' \"  > and name like concat('%',#{mchName},'%')</if>" +
            " order by collection_num desc " +
            " limit #{pageStart},#{pageEnd} " +
            "</script>")
    List<MchModel> getLikeMchByName(int storeId, String mchName, Integer pageStart, Integer pageEnd) throws LaiKeApiException;


    /**
     * 获取店铺信息动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/20 16:34
     */
    List<MchModel> getMchDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 店铺收藏数-1
     *
     * @param mchModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/22 16:37
     */
    @Update("update lkt_mch set collection_num = collection_num - 1 where store_id = #{store_id} and id = #{id}")
    int cancelCollection(MchModel mchModel) throws LaiKeApiException;


    /**
     * 店铺收藏数+1
     *
     * @param mchModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/23 17:56
     */
    @Update("update lkt_mch set collection_num = collection_num + 1 where store_id = #{store_id} and id = #{id}")
    int addCollection(MchModel mchModel) throws LaiKeApiException;

    /**
     * 修改店铺账户金额
     *
     * @param mchId -
     * @param money -
     * @author Trick
     * @date 2021/9/24 18:59
     */
    @Update("update lkt_mch set account_money = account_money + #{money} " +
            " where id = #{mchId} ")
    int mchSettlement(int mchId, BigDecimal money);

    /**
     * 买家收货,增加余额、积分
     *
     * @param mchId    -
     * @param money    - 收入金额
     * @param integral - 收入积分
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 16:55
     */
    @Update("update lkt_mch set account_money = account_money + #{money},integral_money = integral_money + #{integral} " +
            " where id = #{mchId} ")
    int clientConfirmReceipt(int mchId, BigDecimal money, BigDecimal integral) throws LaiKeApiException;

    /**
     * 增加店铺可提现账户金额
     *
     * @param mchId -
     * @param money - 收入金额
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 16:55
     */
    @Update("update lkt_mch set cashable_money = cashable_money + #{money}  where id = #{mchId} ")
    int refuseWithdraw(int mchId, BigDecimal money) throws LaiKeApiException;

    /**
     * 提现扣减金额
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/3 16:55
     */
    @Update("update lkt_mch set cashable_money = cashable_money - #{t_money} " +
            " where store_id = #{store_id} and id = #{mch_id} ")
    int withdrawal(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 店铺结算金额
     *
     * @param storeId -
     * @param mchId   -
     * @param money   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/16 15:15
     */
    @Update("update lkt_mch set account_money=account_money-#{money}, cashable_money = cashable_money + #{money} where store_id = #{storeId} and id = #{mchId}")
    int settlementMch(int storeId, int mchId, BigDecimal money) throws LaiKeApiException;

    /**
     * 获取店铺信息
     *
     * @param mchId
     * @param storeId
     * @return
     * @throws LaiKeApiException
     */
    @Select("select id,name,logo from lkt_mch where store_id = #{storeId} and id = #{mchId}  ")
    MchModel getMchInfo(String mchId, int storeId) throws LaiKeApiException;

    /**
     * 获取店铺信息 通过产品id
     *
     * @param pid
     * @return
     * @throws LaiKeApiException
     */
    @Select(" SELECT m.id,m.name,m.logo  from lkt_product_list as l right join lkt_configure attr on attr.pid=l.id  LEFT JOIN lkt_mch as m on l.mch_id = m.id where attr.id=#{attrId} ")
    MchModel getMchInfoByPid(int attrId) throws LaiKeApiException;


    /**
     * 用户是否是店主
     *
     * @param storeId -
     * @param userId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 15:07
     */
    @Select("select count(1) from lkt_mch where user_id = #{userId} and store_id=#{storeId} and review_status in(0,2)")
    int countMchIsByUser(int storeId, String userId) throws LaiKeApiException;


    /**
     * 平台对接商家数量
     *
     * @param storeId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 18:05
     */
    @Select("select COUNT(1) from lkt_mch as m left join lkt_user as u on m.user_id = u.user_id where m.store_id = #{storeId} and u.store_id = m.store_id and m.review_status = 1")
    int countMchUserNum(int storeId) throws LaiKeApiException;


    /**
     * 获取店铺信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 11:43
     */
    List<Map<String, Object>> getMchUserInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取店铺信息-统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 11:43
     */
    int countMchUserInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 验证店铺名称是否存在
     *
     * @param storeId -
     * @param mchName -
     * @param userId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/1 14:56
     */
    @Select("select count(1) from lkt_mch where store_id = #{storeId} and name = #{mchName} and review_status!=2 and recovery = 0 and user_id != #{userId}")
    Integer verifyStoreName(int storeId, String mchName, String userId) throws LaiKeApiException;

    /**
     * 获取上传所有店铺id
     *
     * @param storeId -
     * @return List
     * @author Trick
     * @date 2021/6/17 17:00
     */
    List<Integer> getStoreMchIdList(int storeId);

    /**
     * 统计店铺未完成的订单
     * (订单已完成但是未结算也算未完成)
     * @param storeId -
     * @param userId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021-10-29 11:51:49
     */
    @Select("select count(1) FROM lkt_order as a ,lkt_mch as b " +
            " WHERE a.mch_id =concat(',', b.id ,',') " +
            " and b.user_id = #{userId} " +
            " and b.store_id=#{storeId} " +
            " and a.store_id = b.store_id " +
            " and a.recycle = 0 " +
            " and (a.status in (0,1,2,3) or (a.status = 5 and a.settlement_status = 0) )")
    int countMchUnfinishedOrder(int storeId, String userId) throws LaiKeApiException;

}