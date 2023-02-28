package com.laiketui.common.mapper;

import com.laiketui.domain.order.OrderDetailsModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 订单明细 sql
 *
 * @author Trick
 * @date 2020/10/27 16:28
 */
public interface OrderDetailsModelMapper extends BaseMapper<OrderDetailsModel> {


    /**
     * 通过订单明细获取商品信息 动态Sql
     *
     * @param map -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/27 16:31
     */
    List<Map<String, Object>> getOrderDetailByGoodsInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 更新订单详情订单号
     *
     * @param storeId
     * @param newOrderNo
     * @param orderDetailsId
     * @return
     */
    @Update("update lkt_order_details set r_sNo = #{newOrderNo} where store_id = #{storeId} and id = #{orderDetailsId} ")
    int updateOrderDetailsParentOrderNo(int storeId, String newOrderNo, int orderDetailsId);

    /**
     * 更新订单详情订单状态
     *
     * @param storeId
     * @param orderNo
     * @param status
     * @return
     */
    @Update("update lkt_order_details set r_status = #{status} where store_id = #{storeId} and r_sNo = #{orderNo} ")
    int updateOrderDetailsStatus(int storeId, String orderNo, int status);


    /**
     * 根据订单号修改明细表信息 动态sql
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/26 17:31
     */
    int updateByOrdernoDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 动态统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/27 14:34
     */
    int countOrderDetailDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取订单总数
     *
     * @param storeId
     * @param sNo
     * @return
     */
    @Select("select sum(num) as sum from lkt_order_details where store_id = #{storeId} and r_sNo = #{sNo}")
    int getOrderDetailNum(int storeId, String sNo);

    /**
     * 删除订单详情
     *
     * @param storeId
     * @param sNo
     * @return
     */
    @Update("update lkt_order_details set recycle = 2 where store_id = #{storeId} and r_sNo = #{sNo} ")
    int delOrderDetails(int storeId, String sNo);

    @Update("update lkt_order_details set recycle = #{delType} where store_id = #{storeId} and r_sNo = #{sNo} ")
    int delOrderDetails1(int storeId, int delType, String sNo);

    //修改订单明细金额
    @Update("update lkt_order_details set after_discount = after_discount - #{amt} where id=#{id}")
    int updateOrderPrice(int id, BigDecimal amt);

    /**
     * 判断订单是否全在售后且未结束
     *
     * @param storeId
     * @param sNo
     * @return
     */
    @Select("select count(1) from lkt_order_details where store_id = #{storeId} and r_sNo = #{sNo} and r_status != 7 ")
    int getOrderDetailAllUnfinishSh(int storeId, String sNo);


    /**
     * 获取未关闭的订单明细
     *
     * @param storeId -
     * @param sNo     -
     * @return List
     * @author Trick
     * @date 2021/4/9 11:13
     */
    @Select("select * from lkt_order_details where store_id = #{storeId} and r_sNo = #{sNo} and r_status != 7 ")
    List<Map<String, Object>> getOrderDetailNotClose(int storeId, String sNo);


    /**
     * 获取满足自动收货的订单信息
     *
     * @param storeId    -
     * @param oType      - 订单类型
     * @param autoSecond -
     * @param sysDate    -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/15 9:50
     */
    @Select(" select a.id,a.r_sNo,a.deliver_time,a.user_id,a.store_id,a.p_id,attr.pid goodsId,a.p_price,a.num,b.user_id,b.otype,b.z_price  " +
            " from lkt_order_details a RIGHT JOIN lkt_order b on a.r_sno = b.sno and b.recycle=0 and b.`status`=2 and b.otype=#{oType} RIGHT JOIN lkt_configure attr on attr.id=a.sid " +
            " where a.store_id = #{storeId} and a.r_status = '2' and date_add(a.deliver_time, interval #{autoSecond} second) < #{sysDate} ")
    List<Map<String, Object>> getReceivingGoodsInfo(int storeId, String oType, int autoSecond, Date sysDate) throws LaiKeApiException;

    /**
     * 售后寄回商品 确定收货
     *
     * @param storeId
     * @param orderId
     * @return
     */
    @Update(" update lkt_order_details set r_type = 12,r_status = 5  where store_id = #{storeId} and id = #{orderId} ")
    int updateOkOrderDetails(int storeId, int orderId);


    /**
     * 获取待自动评价信息
     *
     * @param storeId -
     * @param day     -
     * @param sysDate -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/16 11:15
     */
    @Select(" SELECT a.r_sNo,a.user_id,a.p_id,attr.pid goodsId,a.sid  " +
            " FROM lkt_order_details a RIGHT JOIN lkt_order `order` on `order`.sno=a.r_sno and `order`.recycle=0 " +
            " RIGHT JOIN lkt_configure attr on attr.id=a.sid " +
            " and a.recycle=0 AND not EXISTS (select x.id from lkt_comments x where x.oid = `order`.sno ) " +
            " WHERE a.r_status = 5 " +
            " AND a.store_id = #{storeId} AND date_add(a.arrive_time, INTERVAL #{day} second) <= #{sysDate}")
    List<Map<String, Object>> getReceivingCommentsInfo(int storeId, int day, Date sysDate) throws LaiKeApiException;


    @Update("update lkt_order_details set exchange_num =  #{exnum} + 1 where store_id = #{stroeId} and user_id = #{userId}  and id = #{id} ")
    int confirmReceiptOrderDetaisl(int exnum, int stroeId, String userId, int id);

    /**
     * 获取状态为 1和 2的订单详记录数量
     *
     * @param orderNo
     * @return
     */
    @Select(" select count(1) from lkt_order_details where r_status in (${status}) and r_sNo= #{orderNo} ")
    int getOrderDetailsNum(String status, String orderNo);


    @Select("select l.p_sNo,d.id,l.source,l.remarks,l.pay_time,l.id as oid,l.spz_price,l.z_freight,u.user_name,l.sNo,l.name,\n" +
            "l.mobile,l.sheng,l.shi,l.z_price,l.xian,l.status,l.address,l.pay,l.trade_no,l.coupon_id,l.reduce_price,l.coupon_price,\n" +
            "l.allow,l.drawid,l.otype,l.grade_rate,l.preferential_amount,d.user_id,d.p_id,d.p_name,d.p_price,d.num,d.unit,d.add_time,\n" +
            "d.deliver_time,d.arrive_time,d.r_status,d.content,d.express_id,d.courier_num,d.sid,d.size,d.freight,e.kuaidi_name," +
            " c.total_num ,c.img ,c.num stockNum, " +
            "l.subtraction_id,d.after_discount\n" +
            "from lkt_order_details as d\n" +
            "left join lkt_order as l on l.sNo=d.r_sNo\n" +
            "left join lkt_user as u on u.user_id=l.user_id and u.store_id=#{storeId} \n" +
            "left join lkt_express as e on d.express_id=e.id\n" +
            "left join lkt_configure as c on c.id = d.sid\n" +
            "where l.store_id = #{storeId} and l.sNo=#{sNo} ")
    List<Map<String, Object>> getAdminOrderDetailsInfo(String sNo, int storeId);


    /**
     * 获取店售出的商品类Id
     *
     * @param mchId -
     * @return List
     * @author Trick
     * @date 2021/5/31 14:58
     */
    @Select("select distinct substring_index(substring_index(b.product_class, '-', 2), '-', -1) from lkt_order_details as a left join lkt_product_list as b on a.p_id = b.id left join lkt_order as c on a.r_sNo = c.sNo " +
            " where a.r_status in(1,2,5) and c.sNo is not null and b.mch_id = #{mchId} ")
    List<Integer> getSaleProductClassList(int mchId);

    /**
     * 获取用户以往订单商品类别
     *
     * @param storeId -
     * @param userId  -
     * @return List
     * @author Trick
     * @date 2021/6/23 14:20
     */
    @Select("select c.product_class from lkt_order_details as b left join lkt_product_list as c on b.p_id = c.id " +
            " where c.store_id = #{storeId} and b.user_id = #{userId} order by c.add_date desc ")
    List<Integer> getGoodsByUser(int storeId, String userId);

    /**
     * 根据商品类别统计金额
     *
     * @param mchId   -
     * @param classId -
     * @return Map
     * @author Trick
     * @date 2021/5/31 15:04
     */
    @Select("select SUM(a.after_discount) as price,SUM(a.freight) as freight from lkt_order_details as a left join lkt_product_list as b on a.p_id = b.id left join lkt_order as c on a.r_sNo = c.sNo\n" +
            " where a.r_status in(1,2,5) and b.mch_id = #{mchId} and c.sNo is not null and b.product_class like concat('%-',#{classId},'-%')  ")
    Map<String, BigDecimal> getSaleProductClassAmt(int mchId, int classId);

    @Select("select  d.num,c.costprice,c.price from lkt_order_details as d left join lkt_configure as c on d.sid = c.id " +
            " where d.r_sNo = #{orderno} and d.store_id = #{storeId}")
    List<Map<String, Object>> getbMchOrderIndexAmt(String orderno, int storeId);

    @Select("select lpl.imgurl,lpl.product_title,lpl.product_number,lod.p_price,lod.unit,lod.num,lod.size,lod.p_id,lod.courier_num,lod.express_id,lod.freight,lpl.brand_id ,lm.name as mchname,lod.r_status " +
            "from lkt_order_details as lod  " +
            "left join lkt_product_list as lpl on lpl.id=lod.p_id  " +
            "LEFT JOIN lkt_mch as lm on lm.id = lpl.mch_id  " +
            "where r_sNo=#{orderno}")
    List<Map<String, Object>> getbMchOrderIndexDetail(String orderno);


    List<Map<String, Object>> selectOrderInfoListDynamce(Map<String, Object> map);

    int countOrderInfoListDynamce(Map<String, Object> map);

    @Select("select d.id,u.user_name,d.user_id,d.p_id,d.p_name,d.p_price,d.num,d.unit,d.add_time,d.deliver_time,d.arrive_time,d.r_status,d.content,d.express_id," +
            " d.courier_num,d.sid,d.size,d.freight,e.kuaidi_name, c.total_num ,c.img,d.after_discount " +
            " from lkt_order_details as d left join lkt_order as l on l.sNo=d.r_sNo left " +
            " join lkt_user as u on u.user_id=l.user_id and u.store_id=#{storeId} " +
            " left join lkt_express as e on d.express_id=e.id " +
            " left join lkt_configure as c on c.id = d.sid " +
            " where l.store_id = #{storeId} and l.sNo=#{orderno}")
    List<Map<String, Object>> selectStoreOrderDetails(int storeId, String orderno);


    /**
     * 根据商城ID、订单号、店铺优惠券ID，查询不是这个订单详情的数据
     *
     * @param storeId
     * @param sNo
     * @param oneCouponId
     * @param orderId
     * @return
     */
    @Select("select id,r_status from lkt_order_details where store_id =  #{storeId} and r_sNo = #{sNo} and coupon_id like concat('%',#{oneCouponId},'%')  and id != #{orderId} ")
    List<Map<String, Object>> getOrderDetailsUseTheCoupon(int storeId, String sNo, String oneCouponId, int orderId);

    //关闭订单
    @Update("update lkt_order_details set r_status=7 where r_sno=#{orderNo}")
    int closeOrder(String orderNo);
}