package com.laiketui.common.mapper;

import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 商品属性表
 *
 * @author Trick
 * @date 2020/10/10 19:29
 */
public interface ConfiGureModelMapper extends BaseMapper<ConfiGureModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取某产品库存数量
     *
     * @param pid -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 14:09
     */
    @Select("select ifnull(sum(num),0) as num from lkt_configure where pid = #{pid} and recycle = 0")
    Integer countConfigGureNum(int pid) throws LaiKeApiException;

    /**
     * 获取当前规格库存
     *
     * @param attrId -
     * @return Integer
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/31 18:35
     */
    @Select("select ifnull(sum(num),0) as num from lkt_configure where id = #{attrId}")
    Integer sumConfigGureNum(int attrId) throws LaiKeApiException;

    /**
     * 获取某产品最高价格
     *
     * @param pid -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 14:09
     */
    @Select("SELECT ifnull(max(price),0) FROM lkt_configure where pid = #{pid}")
    BigDecimal getProductMaxPrice(int pid) throws LaiKeApiException;

    /**
     * 获取某产品最低价格
     *
     * @param pid -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 14:09
     */
    @Select("SELECT ifnull(min(price),0) FROM lkt_configure where pid = #{pid}")
    BigDecimal getProductMinPrice(int pid) throws LaiKeApiException;


    /**
     * 获取商品最小出售价格、最大原价格
     *
     * @param pid -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/9 12:08
     */
    @Select("SELECT ifnull(min(price),0) price, ifnull(max(yprice),0) yprice,img,unit FROM lkt_configure WHERE pid = #{pid}")
    ConfiGureModel getProductMinPriceAndMaxYprice(int pid) throws LaiKeApiException;

    /**
     * 获取活动中商品最低价格
     *
     * @param configModel -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 14:09
     */
    @Select("select min(c.price) price from lkt_configure as c " +
            " left join lkt_product_list as p on c.pid=p.id" +
            " where c.pid=#{pid}")
    BigDecimal getActivityGoodsLowPrice(ConfiGureModel configModel) throws LaiKeApiException;

    /**
     * 获取活动中商品最低价格 + 规格
     *
     * @param configModel -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 14:09
     */
    @Select("select min(c.price) from lkt_configure as c " +
            " left join lkt_product_list as p on c.pid=p.id" +
            " where c.pid=#{pid} " +
            " and c.id=#{id}")
    BigDecimal getActivityGoodsLowPriceByAttrId(ConfiGureModel configModel) throws LaiKeApiException;


    /**
     * 增/减商品规格库存
     *
     * @param confiGureModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/19 15:40
     */
    @Update("update lkt_configure set total_num = total_num + #{total_num},num = num + #{num} where pid = #{pid} and id = #{id}")
    int addGoodsAttrStockNum(ConfiGureModel confiGureModel) throws LaiKeApiException;


    /**
     * 增加商品规格库存
     *
     * @param num -
     * @param id  -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/14 16:40
     */
    @Update("update lkt_configure set total_num = total_num + #{num},num = num + #{num} where id = #{id}")
    int addGoodsAttrStockNumByPid(int num, int id) throws LaiKeApiException;


    /**
     * 删除某商品规格信息
     *
     * @param pid -
     * @return pid
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/20 17:44
     */
    @Update("update lkt_configure set recycle = 1 where pid=#{pid}")
    int delConfiGureInfo(int pid) throws LaiKeApiException;


    /**
     * 回收除了当前的属性
     *
     * @param pid     -
     * @param pidList -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/22 16:48
     */
    @Update("<script>" +
            "update lkt_configure set recycle = 1 where " +
            " pid=#{pid} " +
            " <if test='pidList != null '> " +
            "   <foreach collection=\"pidList\" item=\"id\" separator=\",\" open=\"and id not in(\" close=\")\"> " +
            "        #{id,jdbcType=INTEGER}" +
            "   </foreach> " +
            "</if> " +
            "</script>")
    int delAppointConfiGureInfo(int pid, List<Integer> pidList) throws LaiKeApiException;

    /**
     * 增减库存
     *
     * @param num -
     * @param cid -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/19 16:47
     */
    @Update("update lkt_configure set num = num - #{num} where id = #{cid}")
    int reduceGoodsStockNum(int num, int cid) throws LaiKeApiException;

    /**
     * 更新赠品库存
     *
     * @param give_id
     * @return
     */
    @Update("update lkt_configure set num = num - 1 where pid = #{give_id} order by id limit 1")
    int reduceGiveGoodsStockNum(int give_id);


    /**
     * 获取商品库存信息动态 sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/7 15:27
     */
    List<Map<String, Object>> getProductListLeftJoinMchDynamic(Map<String, Object> map) throws LaiKeApiException;

    int countProductListLeftJoinMchDynamic(Map<String, Object> map);


    /**
     * 获取商品库存信息
     *
     * @param storeId -
     * @param goodsId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/23 16:30
     */
    @Select("select a.id as attr_id,b.id,b.product_title,a.num from lkt_configure as a left join lkt_product_list as b on a.pid = b.id left join lkt_mch as c on b.mch_id = c.id  " +
            "where b.store_id = #{storeId} and c.store_id = b.store_id and b.active = 1 and b.status = 2  and a.num > 0 and b.id = #{goodsId} group by b.id")
    Map<String, Object> getGoodsConfigureList(int storeId, int goodsId) throws LaiKeApiException;


    /**
     * 获取商品库存和属性库存信息
     *
     * @param attrId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 15:13
     */
    @Select("SELECT b.min_inventory, a.num,a.pid,a.total_num,b.product_title,a.price,a.img FROM lkt_configure a, lkt_product_list b " +
            "WHERE a.id = #{attrId} AND b.id = a.pid AND b.id = a.pid")
    Map<String, Object> getGoodsStockInfo(int attrId) throws LaiKeApiException;

    /**
     * 获取商品最低价格商品信息
     *
     * @param goodsId -
     * @return ConfiGureModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/26 14:21
     */
    @Select("select * from lkt_configure where pid=#{goodsId} having min(price)=price order by ctime desc limit 1")
    ConfiGureModel getGoodsMinPriceInfo(int goodsId) throws LaiKeApiException;

    /**
     * 库存预警商品报表
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 12:03
     */
    @Select("SELECT a.product_number, a.product_title, c.img, c.attribute, c.total_num , c.num, b.add_date FROM lkt_configure c LEFT JOIN lkt_product_list a ON c.pid = a.id LEFT JOIN ( SELECT max(add_date) AS add_date, type, attribute_id FROM lkt_stock WHERE type = 2 GROUP BY attribute_id ) b ON c.id = b.attribute_id WHERE a.store_id = #{storeId} AND a.recycle = 0 AND c.num <= a.min_inventory")
    List<Map<String, Object>> getStock(int storeId) throws LaiKeApiException;


    /**
     * 获取拼团商品规格信息
     *
     * @param goodsId    -
     * @param activityNo -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/2 15:10
     */
    @Select("select b.* from lkt_group_product a,lkt_configure b where a.attr_id=b.id and b.pid=#{goodsId} and activity_no =#{activityNo}")
    List<ConfiGureModel> selectGroupGureListByPid(int goodsId, int activityNo) throws LaiKeApiException;

    /**
     * 获取拼团商品规格信息(平台)
     *
     * @param goodsId    -
     * @param activityNo -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/4/2 15:10
     */
    @Select("select b.* from lkt_pt_group_product a,lkt_configure b where a.attr_id=b.id and b.pid=#{goodsId} and activity_no =#{activityNo}")
    List<ConfiGureModel> selectPtGroupGureListByPid(int goodsId, int activityNo) throws LaiKeApiException;


    /**
     * 获取库存记录相关信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/2 15:42
     */
    List<Map<String, Object>> selectStockInfo(Map<String, Object> map) throws LaiKeApiException;

    int countStockInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取商品库存最小的规格信息
     *
     * @return
     */
    @Select(" select x.id,x.num from lkt_configure x where x.pid=#{goodsId} and x.num>0 and x.recycle=0 " +
            " and num = (select min(y.num) from lkt_configure y where y.pid=x.pid and y.num>0 and y.recycle=x.recycle ) " +
            " order by ctime desc " +
            " limit 1 ")
    Map<String, Object> getAttrStockNumByMin(int goodsId);

}