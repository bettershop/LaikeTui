package com.laiketui.common.mapper;

import com.laiketui.domain.product.StockModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 库存
 *
 * @author Trick
 * @date 2021/1/4 10:36
 */
public interface StockModelMapper extends BaseMapper<StockModel> {

    /**
     * 商品库存信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 10:36
     */
    List<Map<String, Object>> getStorckInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 导出查询
     * @param map
     * @return
     * @throws LaiKeApiException
     * @author sunH_
     * @date 2021/11/17 19:41
     */
    List<Map<String, Object>> exportQuery(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 商品库存详细统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 10:36
     */
    int stockInfoCountDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 商品库存详细信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 10:36
     */
    List<Map<String, Object>> getGoodsStorckInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 商品库存详细信息统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 10:36
     */
    int goodsStockInfoCountDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取商城类目库存报表
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 11:54
     */
    @Select("SELECT stock.topClass, stock.className , if(stock.sy_num IS NULL, 0, stock.sy_num) AS sy_num , if(stock.in_num IS NULL, 0, stock.in_num) AS in_num , if(stock.out_num IS NULL, 0, stock.out_num) AS out_num FROM ( SELECT a.cid AS topClass, a.pname AS className, SUM(num) AS sy_num , ( SELECT SUM(flowing_num) FROM lkt_stock x, lkt_product_list y WHERE x.store_id = b.store_id AND x.product_id = y.id AND x.type = 0 AND x.product_id = y.id AND y.recycle = 0 AND substring_index(substring_index(y.product_class, '-', 2), '-', -1) = a.cid ) AS in_num , ( SELECT SUM(flowing_num) FROM lkt_stock x, lkt_product_list y WHERE x.store_id = b.store_id AND x.product_id = y.id AND x.type = 1 AND x.product_id = y.id AND y.recycle = 0 AND substring_index(substring_index(y.product_class, '-', 2), '-', -1) = a.cid ) AS out_num FROM lkt_product_class a LEFT JOIN lkt_product_list b ON b.store_id = a.store_id AND b.recycle = 0 AND a.cid = substring_index(substring_index(b.product_class, '-', 2), '-', -1) WHERE a.store_id = #{storeId} AND a.`level` = 0 AND a.recycle = 0 GROUP BY topClass ORDER BY a.cid DESC ) stock")
    List<Map<String, Object>> getGoodsStockReportByClass(int storeId) throws LaiKeApiException;


    /**
     * 获取商品预警列表
     *
     * @param storeId   -
     * @param startPage -
     * @param endPage   -
     * @return List
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/13 11:32
     */
    @Select("SELECT a.id, a.product_title, c.img, c.attribute, c.total_num, c.num, b.add_date FROM lkt_configure AS c " +
            " LEFT JOIN lkt_product_list AS a ON c.pid = a.id " +
            " LEFT JOIN( SELECT max(add_date) AS add_date, type, attribute_id FROM lkt_stock WHERE type = 2 GROUP BY attribute_id) AS b ON c.id = b.attribute_id " +
            " WHERE a.store_id = #{storeId}  AND a.recycle = 0 AND c.num <= a.min_inventory and b.add_date is not null" +
            " ORDER BY a.add_date DESC " +
            " limit #{startPage},#{endPage}")
    List<Map<String, Object>> getGoodsWarningList(int storeId, int startPage, int endPage) throws LaiKeApiException;
    @Select("SELECT count(1) FROM lkt_configure AS c " +
            " LEFT JOIN lkt_product_list AS a ON c.pid = a.id " +
            " LEFT JOIN( SELECT max(add_date) AS add_date, type, attribute_id FROM lkt_stock WHERE type = 2 GROUP BY attribute_id) AS b ON c.id = b.attribute_id " +
            " WHERE a.store_id = #{storeId}  AND a.recycle = 0 AND c.num <= a.min_inventory and b.add_date is not null"
            )
    Integer countGoodsWarningList(int storeId) throws LaiKeApiException;
}