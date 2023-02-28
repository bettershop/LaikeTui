package com.laiketui.common.mapper;

import com.laiketui.domain.order.ReturnOrderModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 售后 sql
 *
 * @author Trick
 * @date 2020/11/4 14:18
 */
public interface ReturnOrderModelMapper extends BaseMapper<ReturnOrderModel> {


    /**
     * 获取售后表信息 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 14:19
     */
    List<ReturnOrderModel> getReturnOrderListDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 统计售后表信息 动态sql
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/25 15:45
     */
    int countReturnOrderListDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 统计售后订单动态sql
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/9 17:58
     */
    int countRturnOrderNumDynamic(Map<String, Object> map) throws LaiKeApiException;

    List<Map<String, Object>> selectRturnOrderNumDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 通过退款信息获取商品信息 动态Sql
     *
     * @param map -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/25 17:24
     */
    List<Map<String, Object>> getReturnOrderByGoodsInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 通过退款信息获取商品信息 统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 17:14
     */
    int getReturnOrderByGoodsCount(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取售后订单信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 14:29
     */
    List<Map<String, Object>> getReturnOrderJoinOrderListDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * @param map
     * @return
     */
    int getUnFinishShouHouOrder(Map<String, Object> map);

    /**
     * 获取售后订单信息(个人中心订单售后列表)
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     */
    List<Map<String, Object>> getReturnOrderList(Map<String, Object> map);

    /**
     * 获取售后订单信息(个人中心订单售后列表)
     *
     * @param int -
     * @return List
     * @throws LaiKeApiException -
     */
    int getReturnOrderListCount(Map<String, Object> map);

    /**
     * 更新
     *
     * @param storeId
     * @param id
     * @return
     */
    @Update("update lkt_return_order set r_type = #{rType} where store_id = #{storeId} and id = #{id} ")
    int updateReturnOrder(int storeId, int id, int rType);


    /**
     * 获取售后信息
     *
     * @param storeId -
     * @param id      - 售后订单id
     * @return Map
     * @author Trick
     * @date 2021/1/6 11:16
     */
    @Select("select a.*,CAST(a.r_type AS char) rtype,b.p_name,attr.pid goodsId,b.num,b.p_price,b.deliver_time,b.size,b.freight,b.after_discount from lkt_return_order as a " +
            " RIGHT JOIN lkt_configure attr ON attr.id = a.sid " +
            " left join lkt_order_details as b on a.p_id = b.id where a.store_id = #{storeId} and a.id = #{id} ")
    Map<String, Object> getReturnOrderMap(int storeId, int id);


    /**
     * 获取用户售后订单信息
     *
     * @param storeId -
     * @param userId  -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 14:33
     */
    @Select("select sNo from lkt_return_record where store_id = #{storeId} and user_id = #{userId} and r_type not in (1,4,9,11)")
    List<String> getReturnOrdernoList(int storeId, int userId) throws LaiKeApiException;

    /**
     * 获取订单状态
     *
     * @param storeId
     * @param orderId
     * @return
     * @throws LaiKeApiException
     */
    @Select(" select ifnull(count(1),0) from lkt_return_order where store_id = #{storeId} and p_id = #{orderId} and r_type in (0,1,3,11) ")
    int getOrderStatus(int storeId, int orderId) throws LaiKeApiException;


    /**
     * 获取售后订单详情
     *
     * @param storeId -
     * @param id      -
     * @return List
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/3/11 16:36
     */
    @Select("select a.*,b.p_name,d.img,b.p_price,b.num,b.size from lkt_return_order as a left join lkt_order_details as b on a.p_id = b.id " +
            "left join lkt_product_list as c on b.p_id = c.id left join lkt_configure as d on b.sid = d.id" +
            " where a.store_id = #{storeId} and a.id = #{id} order by re_time desc")
    List<Map<String, Object>> selectReturnOrderInfo(int storeId, int id) throws LaiKeApiException;


    /**
     * 获取用户回寄信息
     *
     * @param oid     - 订单详情id
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/11 16:54
     */
    @Select("SELECT * FROM lkt_return_goods WHERE oid =  #{oid} and store_id = #{storeId} order by add_data desc ")
    List<Map<String, Object>> selectReturnGoodsInfo(int oid, int storeId) throws LaiKeApiException;


    /**
     * 订单是否在售后且未结束
     *
     * @param stroeId -
     * @param orderno -
     * @return Integer
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/24 10:26
     */
    @Select("select count(1) from lkt_return_order where store_id = #{stroeId}  and sNo = #{orderno} and r_type not in (2,4,5,8,9,10,12)")
    Integer orderReturnIsNotEnd(int stroeId, String orderno) throws LaiKeApiException;

    //订单明细是否在售后且未结束
    @Select("select count(1) from lkt_return_order where store_id = #{storeId} and sNo = #{orderNo} and p_id=#{detailId} and r_type not in (2,4,5,8,9,10,12)")
    Integer orderDetailReturnIsNotEnd(int storeId, String orderNo,Integer detailId) throws LaiKeApiException;


    /**
     * 店铺带退款的订单数
     *
     * @param storeId -
     * @param mchId   -
     * @return Integer
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 10:39
     */
    @Select("<script>" +
            "select count(1) from lkt_return_order a left join lkt_product_list b on a.pid = b.id " +
            " where a.store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            "  and b.mch_id = #{mchId} " +
            " </if> " +
            "and a.r_type in(0,1,3,11) " +
            " </script> ")
    Integer countOrderReturnWait(int storeId, Integer mchId) throws LaiKeApiException;


    //根据状态统计售后数量
    @Select("select count(a.id) from lkt_return_order a inner join lkt_order o on o.sNo=a.sNo left join lkt_product_list b on a.pid = b.id " +
            " where a.store_id = #{storeId} and a.r_type in(0,1,3,11) and a.r_type = #{rType} and o.otype=#{orderType}")
    Integer countOrderReturnWaitByStoreStatus(int storeId,int rType,String orderType) throws LaiKeApiException;


    /**
     * 获取未退款、未失效、订单完成的订单信息
     *
     * @param storeId -
     * @param invalid -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/25 9:56
     */
    @Select("select a.sNo from lkt_order as a left join lkt_return_order as b on a.sNo = b.sNo where a.store_id = #{storeId} and a.otype = 'FX' and a.status = 5 and a.commission_type = 0 and b.sNo is null and a.arrive_time <=#{invalid} " +
            " union all " +
            "select a.sNo from lkt_order as a left join lkt_return_order as b on a.sNo = b.sNo where a.store_id = #{storeId} and a.otype = 'FX' and a.status = 5 and a.commission_type = 0 and b.r_type not in(0,1,3) and b.re_type !=3 and a.arrive_time <=#{invalid} ")
    List<Map<String, Object>> selectReturnNotMoney(int storeId, Date invalid) throws LaiKeApiException;

    List<Map<String, Object>> selectRturnOrderNumDynamic1(Map<String, Object> map);
    int countRturnOrderNumDynamic1(Map<String, Object> map);

    @Select("select ifnull(sum(real_money),0) as total from lkt_return_order where re_type in (1,2) and r_type in (4,9) and sNo = #{orderno}")
    BigDecimal getReturnAmtByOrder(String orderno);


    //获取最新售后信息 - 订单
    @Select("select * from lkt_return_order where sNo=#{orderNo} order by re_time desc limit 1")
    ReturnOrderModel getReturnNewInfoBySno(String orderNo);

    //获取最新售后信息 - 详情
    @Select("select * from lkt_return_order where p_id=#{detailId} order by re_time desc  limit 1")
    ReturnOrderModel getReturnNewInfoByDetailId(int detailId);
}