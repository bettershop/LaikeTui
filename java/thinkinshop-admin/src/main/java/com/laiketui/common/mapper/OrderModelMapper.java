package com.laiketui.common.mapper;

import com.laiketui.domain.order.OrderModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 订单 sql
 *
 * @author Trick
 * @date 2020/11/4 15:09
 */
public interface OrderModelMapper extends BaseMapper<OrderModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态统计店铺营业额
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    List<Map<String, Object>> sumMchTurnoverReport(Map<String, Object> map);

    int countMchTurnoverReport(Map<String, Object> map);


    /**
     * 动态统计店铺-订单数量
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    List<Map<String, Object>> sumMchTurnoverOrderReport(Map<String, Object> map);

    int countMchTurnoverOrderReport(Map<String, Object> map);

    /**
     * 统计未评论的订单数量
     *
     * @param orderModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/4 14:44
     */
    @Select("SELECT count(1) FROM lkt_order a WHERE a.store_id = 1 AND a.user_id = #{user_id} " +
            " AND a.STATUS = 5 AND a.recycle = 0 and a.otype=#{otype}" +
            " AND not EXISTS(select x.id from lkt_comments x where x.store_id=a.store_id and x.oid=a.sno)")
    int countNotCommentNum(OrderModel orderModel) throws LaiKeApiException;


    /**
     * 统计订单数量动态
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/9 17:30
     */
    int countOrdersNumDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取订单表数据
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 18:16
     */
    List<Map<String, Object>> getOrdersNumDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取未关闭的订单信息
     *
     * @param orderno -
     * @return List
     * @author Trick
     * @date 2021/4/16 16:28
     */
    @Select("SELECT id,status from lkt_order where status != '6' and status != '7' and status != '8' and  real_sno = #{orderno}")
    OrderModel getNotCloseOrdersByNo(String orderno);

    /**
     * 更新订单备注
     *
     * @param remarks
     * @param sNo
     * @param storeId
     * @return
     */
    @Update("update lkt_order set remarks= #{remarks} where sNo = #{sNo} and store_id = #{storeId}")
    int updateOrderRemark(String remarks, String sNo, int storeId);


    /**
     * 修改订单状态
     */
    @Update("update lkt_order set status= #{status} where sNo = #{orderNo} and store_id = #{storeId}")
    int updateOrderStatusByOrderNo(int storeId, int status, String orderNo);

    /**
     * 订单结算
     */
    @Update("update lkt_order set status= 5,settlement_status=1,arrive_time=#{sysDate} where sNo = #{orderNo} and store_id = #{storeId}")
    int updateOrderSettlementByOrderNo(int storeId, String orderNo,Date sysDate);

    /**
     * 根据订单号，查询订单商品ID
     *
     * @param storeId
     * @param sNo
     * @return
     */
    @Select("select a.id,b.p_id,a.num,a.offset_balance from lkt_order as a left join lkt_order_details as b on a.sNo = b.r_sNo " +
            "where a.store_id = #{storeId} and b.r_sNo = #{sNo}")
    List<Map<String, Object>> getOrderProductInfo(int storeId, String sNo);

    /**
     * 优惠券拆分订单获取订单详情
     *
     * @param storeId
     * @param sNo
     * @param mchId
     * @return
     */
    @Select("select a.id,a.p_id,a.p_price,a.num,a.freight,b.product_class,a.r_sNo from lkt_order_details as a " +
            " left join lkt_product_list as b on a.p_id = b.id where a.store_id = #{storeId} and a.r_sNo = #{sNo} and b.mch_id = #{mchId}")
    List<Map<String, Object>> getOrderDetails(int storeId, String sNo, int mchId);

    /**
     * 更新订单状态
     *
     * @param storeId
     * @param sNo
     * @param userId
     * @return
     */
    @Update("update lkt_order set status = #{status} where store_id = #{storeId} and sNo = #{sNo} and user_id = #{userId} ")
    int updateOrderStatus(int storeId, String sNo, String userId, int status);


    /**
     * 关闭过期的订单
     *
     * @param id      -
     * @param endDate -
     * @return int -
     * @author Trick
     * @date 2020/12/14 16:19
     */
    @Update("update lkt_order set status = 7 where id=#{id} and status = 0 ")
    int updateOrderStatusById(int id);


    /**
     * 重置发货提醒
     *
     * @param id             -
     * @param deliveryStatus -
     * @param readd          -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/15 15:56
     */
    @Update("UPDATE lkt_order SET delivery_status = #{deliveryStatus},readd = #{readd},remind=null WHERE id = #{id} ")
    int updateDeliveryReset(int id, int deliveryStatus, int readd) throws LaiKeApiException;

    /**
     * 动态获取订单信息
     *
     * @param map -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/26 19:23
     */
    List<Map<String, Object>> getOrderInfoListDynamce(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 动态获取订单详情信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 9:08
     */
    List<Map<String, Object>> getOrderInfoLeftDetailDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 查看提货码
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    @Select("select a.z_price,a.sNo,a.status,a.mch_id,a.extraction_code,a.extraction_code_img,b.p_id,b.p_price,b.num,b.size,b.sid from lkt_order as a " +
            "left join lkt_order_details as b on a.sNo = b.r_sNo where a.store_id = #{store_id} and a.id = #{id} and a.user_id = #{user_id} ")
    List<Map<String, Object>> seeExtractionCode(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 根据订单号修改订单信息 动态sql
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/26 17:31
     */
    int updateByOrdernoDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 删除订单信息
     *
     * @param orderNo -
     * @return int
     * @author Trick
     * @date 2021/7/21 11:12
     */
    @Update("update lkt_order a,lkt_order_details b set a.recycle=1,b.recycle=1 where a.sno=b.r_sno and a.sno=#{orderNo}")
    int delOrderById(String orderNo);

    /**
     * 更新订单金额
     *
     * @param storeId      -
     * @param orderno      -
     * @param orderPrice   - 增加/减去 订单总金额
     * @param orderFreight - 增加/减去 订单运费
     * @param goodsPrice   - 增加/减去 商品价格
     * @return int
     * @throws LaiKeApiException -
     */
    @Update("update lkt_order set z_price = z_price - #{orderPrice},z_freight = z_freight - #{orderFreight},spz_price = spz_price - #{goodsPrice} " +
            "where store_id = #{storeId} and sNo = #{orderno} ")
    int updateOrderPrice(int storeId, String orderno, BigDecimal orderPrice, BigDecimal orderFreight, BigDecimal goodsPrice) throws LaiKeApiException;

    /**
     * 获取订单状态数量
     *
     * @param param
     * @return
     */
    int getUserCenterOrderNumByStatus(Map<String, Object> param);

    /**
     * 个人中心获取订单列表信息
     *
     * @param param
     * @return
     */
    List<Map<String, Object>> getUserCenterOrderList(Map<String, Object> param);

    /**
     * 个人中心获取订单列表数量
     *
     * @param param
     * @return
     */
    int getUserCenterOrderListCount(Map<String, Object> param);

    /**
     * 更新提醒发货
     *
     * @param storeId
     * @param userId
     * @param orderId
     * @return
     */
    @Update("update lkt_order set delivery_status='1',readd='0' where store_id = #{storeId} and user_id = #{userId} and id = #{orderId} ")
    int updateDeliveryRemind(int storeId, String userId, int orderId);


    /**
     * 更新订单信息
     *
     * @param map
     * @return
     */
    int updateOrderInfo(Map<String, Object> map);

    /**
     * 删除订单
     *
     * @param storeId
     * @param sNo
     * @return
     */
    @Update(" update lkt_order set recycle = 2  where store_id = #{storeId} and sNo = #{sNo} ")
    int delOrder(int storeId, String sNo);

    /**
     * 删除订单
     */
    @Update(" update lkt_order set recycle = #{delType}  where store_id = #{storeId} and sNo = #{sNo} ")
    int delOrder1(int storeId, int delType, String sNo);

    /**
     * 进入退款界面获取订单退款信息
     *
     * @param storeId
     * @param returnOrderDetailsId
     * @return
     */
    @Select("select a.id,m.freight,a.trade_no,m.num,a.sNo,a.pay,a.z_price,a.user_id,a.spz_price,m.p_price," +
            "  a.consumer_money,m.express_id,a.z_freight,m.after_discount " +
            "  from lkt_order as a LEFT JOIN lkt_order_details AS m ON a.sNo = m.r_sNo where a.store_id = #{storeId} and m.id = #{returnOrderDetailsId} ")
    Map<String, Object> getReturnOrderInfo(int storeId, int returnOrderDetailsId);

    /**
     * 余额支付更新订单
     *
     * @param params
     * @return
     */
    int wallectPayUpdateOrder(Map<String, Object> params);

    /**
     * 支付成功后更新订单信息
     *
     * @param pay
     * @param tradeNo
     * @param sNo
     * @param userId
     * @return
     */
    @Update("update lkt_order set status = 1,pay = #{pay},trade_no=#{tradeNo}," +
            "pay_time=CURRENT_TIMESTAMP where sNo = #{sNo} and user_id = #{userId} ")
    int payBackUpOrder(String pay, String tradeNo, String sNo, String userId);


    /**
     * 分佣标记已结算
     *
     * @param storeId -
     * @param orderno -
     * @return int
     * @author Trick
     * @date 2021/5/25 10:38
     */
    @Update("update lkt_order set commission_type = 1 where store_id = #{storeId} and sNo = #{orderno}")
    int disSettlement(int storeId, String orderno);


    /**
     * 标记订单是否已读
     *
     * @param storeId -
     * @param sNo     -
     * @param readd   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 14:26
     */
    @Update("update lkt_order set readd = #{readd} where store_id = #{storeId} and sNo = #{sNo}")
    int updateIsReadd(int storeId, String sNo, int readd) throws LaiKeApiException;


    /**
     * 统计用户订单金额
     *
     * @param storeId -
     * @param userId  -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 11:36
     */
    @Select("select SUM(z_price) as z_price from lkt_order where store_id = #{storeId} and user_id=#{userId} and status > 0 " +
            "and status not in (4,7,11) and pay_time != ''")
    BigDecimal sumUserOrderPrice(int storeId, String userId) throws LaiKeApiException;


    /**
     * 统计用户未退款的订单数量
     *
     * @param storeId -
     * @param userId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 14:56
     */
    @Select("SELECT count(1) FROM lkt_order WHERE store_id = #{storeId} AND user_id = #{userId} AND STATUS > 0 " +
            "AND STATUS NOT IN (4, 7, 11) AND pay_time != '' " +
            "AND sno NOT IN ( SELECT sNo FROM lkt_return_record " +
            "WHERE store_id = #{storeId} AND user_id = #{userId} AND r_type NOT IN (1, 4, 9, 11) )")
    int countUserEffectiveOrdernoNum(int storeId, String userId) throws LaiKeApiException;


    /**
     * 统计用户未完成的订单
     *
     * @param storeId -
     * @param userId  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 9:29
     */
    @Select("select count(1) from lkt_order as a ,lkt_user as b  where a.user_id = b.user_id and b.user_id = #{userId} and a.recycle=0 " +
            " and b.store_id=#{storeId} and a.store_id = b.store_id and (a.status in (0,1,2,3) or (a.status = 5 and a.settlement_status = 0) )")
    int countUserUnfinishedOrder(int storeId, String userId) throws LaiKeApiException;


    @Select("select count(1) from lkt_order as a,lkt_order_details b where a.sno = b.r_sno and b.p_id = #{goodsId} and a.recycle=0 and b.recycle=0 " +
            "and b.store_id=#{storeId} and a.store_id = b.store_id and (a.status in (0,1,2,3) or (a.status = 5 and a.settlement_status = 0) )")
    int countUserUnfinishedOrderByGoodsId(int storeId, int goodsId) throws LaiKeApiException;

    //订单除了当前商品未完成的商品数量 >0则表示还有其它未完成的订单
    @Select("select count(1) from lkt_order_details where r_sno=#{sno} and  and r_status not in(5,7) and id<> #{detailId}")
    int orderIsSettlement(int storeId, String sno, int detailId);

    /**
     * 统计店铺总销售额
     *
     * @param storeId -
     * @param mchId   -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 10:58
     */
    @Select("<script>" +
            "SELECT IFNULL(SUM(z_price), 0) FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            " AND mch_id = CONCAT(',',#{mchId},',') " +
            " </if> " +
            "and STATUS IN(1,2,5)" +
            " </script> ")
    BigDecimal countMchOrderSale(int storeId, Integer mchId) throws LaiKeApiException;


    /**
     * 统计店铺总支付订单量
     *
     * @param storeId -
     * @param mchId   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 10:58
     */
    @Select("<script>" +
            "SELECT IFNULL(count(id), 0) FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            " AND mch_id = CONCAT(',',#{mchId},',') " +
            " </if> " +
            " and STATUS IN(1,2,5)" +
            " </script> ")
    int countMchOrderNum(int storeId, Integer mchId) throws LaiKeApiException;


    /**
     * 订单报表
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 17:25
     */
    List<Map<String, Object>> selectOrdersReportDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 统计订单数量
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return List
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/17 13:40
     */
    @Select("<script>" +
            "SELECT DATE_FORMAT(add_time,'%Y-%m') rdate,IFNULL(count(distinct id),0) sum FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            " AND mch_id = CONCAT('%,',#{mchId},',%') " +
            " </if> " +
            " AND add_time <![CDATA[  >=  ]]> #{startDate} and add_time <![CDATA[  <=  ]]> #{endDate} " +
            " AND status not in(0,7) " +
            " group by DATE_FORMAT(add_time,'%Y-%m')" +
            " </script> ")
    List<Map<String, Object>> getOrdersNumReportByMonth(int storeId, Integer mchId, String startDate, String endDate) throws LaiKeApiException;

    @Select("<script>" +
            "SELECT DATE_FORMAT(add_time,'%Y-%m-%d') rdate,IFNULL(count(distinct id),0) sum FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            " AND mch_id = CONCAT('%,',#{mchId},',%') " +
            " </if> " +
            " AND add_time <![CDATA[  >=  ]]> #{startDate} and add_time <![CDATA[  <=  ]]> #{endDate} " +
            " AND status not in(0,7) " +
            " group by DATE_FORMAT(add_time,'%Y-%m-%d')" +
            " </script> ")
    List<Map<String, Object>> getOrdersNumReportByDay(int storeId, Integer mchId, String startDate, String endDate) throws LaiKeApiException;


    /**
     * 管理后台订单列表 - 订单信息
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    List<Map<String, Object>> adminOrderList(Map<String, Object> map) throws LaiKeApiException;

    int countAdminOrderList(Map<String, Object> map);

    /**
     * 管理后台订单总数 -
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    int getAdminOrderCount(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 管理后台订单列表 - 商品信息
     *
     * @return
     */
    List<Map<String, Object>> adminOrderListProductInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取订单发货列表信息
     *
     * @param storeId
     * @param orderNo
     * @return
     */
    List<Map<String, Object>> getDeleiveryOrders(int storeId, String orderNo) throws LaiKeApiException;


    /**
     * 根据订单详情id 获取订单信息
     *
     * @param storeId
     * @param orderDetailsId
     * @return
     * @throws LaiKeApiException
     */
    List<Map<String, Object>> getDeliveryOrderInfo(int storeId, String orderDetailsId) throws LaiKeApiException;

    /**
     * 获取一次性消费金额
     *
     * @param storeId    -
     * @param userId     -
     * @param statusList -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/19 14:22
     */
    @Select("<script>" +
            " select max(z_price) as z_price from lkt_order where store_id = #{storeId} and user_id=#{userId} and otype = 'FX' " +
            " <if test='statusList != null '> " +
            "   <foreach collection=\"statusList\" item=\"status\" separator=\",\" open=\"and status in(\" close=\")\"> " +
            "        #{status,jdbcType=INTEGER}" +
            "   </foreach> </if> " +
            "</script>")
    BigDecimal getDistributionPrice(int storeId, String userId, List<Integer> statusList) throws LaiKeApiException;


    List<Map<String, Object>> getOrderInfoListUser(Map<String, Object> map) throws LaiKeApiException;

    int countOrderInfoListUser(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 更具日期跨度获取店铺每天订单数量
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return List
     * @author Trick
     * @date 2021/5/28 11:31
     */
    @Select("<script>" +
            "SELECT DATE_FORMAT(add_time,'%m-%d') time,count(1) value FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            "AND mch_id = CONCAT(',',#{mchId},',') " +
            " </if> " +
            "and STATUS IN(1,2,5) " +
            " and DATE_FORMAT(add_time,'%Y-%m-%d') <![CDATA[  >=  ]]>#{startDate} and DATE_FORMAT(add_time,'%Y-%m-%d') <![CDATA[  <=  ]]> #{endDate} " +
            " group by DATE_FORMAT(add_time,'%Y-%m-%d')" +
            " </script> ")
    List<Map<String, String>> getOrderNumByDay(int storeId, Integer mchId, String startDate, String endDate);


    /**
     * 统计成功支付的用户量
     *
     * @param storeId -
     * @param mchId   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 13:49
     */
    @Select("select count(distinct a.user_id) from lkt_order as a INNER JOIN   lkt_user as b on a.user_id = b.user_id and a.status in (1,2,5) " +
            " AND mch_id = CONCAT(',',#{mchId},',') and b.store_id = #{storeId}")
    int countOrderUserNum(int storeId, int mchId) throws LaiKeApiException;

    /**
     * 获取最早下单的订单的日期
     *
     * @param mchId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 13:49
     */
    @Select("select add_time from lkt_order where  CONVERT(REPLACE(mch_id, ',', ''),unsigned INTEGER) = #{mchId} order by add_time limit 0,1")
    String getOldOrderTime(int mchId);


    /**
     * 更具订单时间统计已支付的订单金额
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 15:48
     */
    @Select("<script>" +
            "select IFNULL(SUM(z_price),0)  from lkt_order where store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            "AND CONVERT(REPLACE(mch_id, ',', ''),unsigned INTEGER) = #{mchId} " +
            " </if> " +
            "and STATUS IN(1,2,5) " +
            "<if test='startDate != null '> " +
            "   and add_time <![CDATA[  >=  ]]> #{startDate}" +
            "</if>" +
            "<if test='endDate != null '> " +
            "   and add_time <![CDATA[  <=  ]]> #{endDate}" +
            "</if>" +
            "</script>")
    BigDecimal sumSaleByDate(int storeId, Integer mchId, Date startDate, Date endDate);


    /**
     * 更具订单时间统计已支付的用户数量
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 15:48
     */
    @Select("<script>" +
            "select IFNULL(count(id),0)  from lkt_order where store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            "AND CONVERT(REPLACE(mch_id, ',', ''),unsigned INTEGER) = #{mchId}" +
            " </if> " +
            " and STATUS IN(1,2,5) " +
            "<if test='startDate != null '> " +
            "   and add_time <![CDATA[  >=  ]]> #{startDate}" +
            "</if>" +
            "<if test='endDate != null '> " +
            "   and add_time <![CDATA[  <=  ]]> #{endDate}" +
            "</if>" +
            "</script>")
    BigDecimal sumSaleNumByDate(int storeId, Integer mchId, Date startDate, Date endDate);


    /**
     * 统计某天每小时销售额
     *
     * @param storeId -
     * @param mchId   -
     * @param date    -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 16:30
     */
    @Select("<script>" +
            "SELECT CONCAT('',HOUR(add_time),'时') time,IFNULL(SUM(z_price),0) value FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            "AND mch_id = CONCAT(',',#{mchId},',')" +
            " </if> " +
            " AND TO_DAYS(add_time) = TO_DAYS(#{date}) AND STATUS IN(1, 2,5) group by HOUR(add_time) " +
            "</script>")
    List<Map<String, BigDecimal>> getSaleNumByHour(int storeId, Integer mchId, Date date);


    /**
     * 统计某月每天销售额
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 16:30
     */
    @Select("<script>" +
            "SELECT DATE_FORMAT(add_time,'%m/%d') time,IFNULL(SUM(z_price),0) value FROM `lkt_order` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            "AND mch_id = CONCAT(',',#{mchId},',') " +
            " </if> " +
            " AND add_time <![CDATA[  >=  ]]> #{startDate} and add_time <![CDATA[  <  ]]> #{endDate} AND STATUS IN(1, 2,5) group by DATE_FORMAT(add_time,'%Y-%m-%d')" +
            "</script>")
    List<Map<String, BigDecimal>> getSaleNumByMonth(int storeId, Integer mchId, Date startDate, Date endDate);


    /**
     * pc-店铺订单列表
     *
     * @param map -
     * @return List
     * @author Trick
     * @date 2021/6/3 10:07
     */
    List<Map<String, Object>> selectbMchOrderIndex(Map<String, Object> map);

    int countbMchOrderIndex(Map<String, Object> map);

    Map<String, Object> sumbMchOrderIndexByPrice(Map<String, Object> map);

}