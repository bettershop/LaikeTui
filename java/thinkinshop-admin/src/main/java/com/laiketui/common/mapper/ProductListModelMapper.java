package com.laiketui.common.mapper;

import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 商品表
 *
 * @author Trick
 * @date 2020/10/10 18:02
 */
public interface ProductListModelMapper extends BaseMapper<ProductListModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    BigDecimal sumDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 查询商品并分类显示返回JSON至小程序
     *
     * @param map -
     * @return List<ProductListModel>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/10 18:04
     */
    List<Map<String, Object>> getProductList(Map<String, Object> map) throws LaiKeApiException;

    int countProductList(Map<String, Object> map);

    /**
     * 获取被搜索最多的商品
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/15 14:41
     */
    List<ProductListModel> getHotGoods(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 增加商品总库存
     *
     * @param pid -
     * @param num -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/19 16:47
     */
    @Update("update lkt_product_list set num = num + #{num} where id = #{pid}")
    int addGoodsStockNum(int pid, int num) throws LaiKeApiException;

    /**
     * 根据商品获取商品信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 16:30
     */
    @Select("select a.product_title,c.img,c.price,a.id from lkt_product_list AS a " +
            " LEFT JOIN lkt_configure AS c ON a.id = c.pid" +
            " where store_id = #{store_id} and a.id = #{pid}")
    List<Map<String, Object>> getGoodsJoinConfigureById(Map<String, Object> map) throws LaiKeApiException;

    @Select("SELECT pl.product_title , c.* " +
            "    from lkt_product_list as pl " +
            "    LEFT JOIN lkt_configure as c on c.pid = pl.id " +
            "    where 1 and pl.store_id = $store_id and pid = #{storeId} and c.id = #{attrId}")
    Map<String, Object> getGoodsJoinConfigureByAttrid(int storeId, int attrId) throws LaiKeApiException;

    /**
     * 获取商品标题和图片
     *
     * @param map
     * @return
     * @throws LaiKeApiException
     */
    @Select(" select a.product_title,c.img from lkt_product_list AS a " +
            " LEFT JOIN lkt_configure AS c ON a.id = c.pid where a.store_id = #{store_id} and a.id = #{p_id} AND c.id= #{sid} ")
    List<Map<String, Object>> getGoodsTitleAndImg(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取被匹配的商品 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 9:24
     */
    List<Map<String, Object>> getProductListDynamic(Map<String, Object> map) throws LaiKeApiException;

    int countProductListDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取商品信息 -
     * left join lkt_mch
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/13 14:04
     */
    List<Map<String, Object>> getProductListLeftJoinMchDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取商品信息统计
     * left join lkt_mch
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/13 14:04
     */
    int getProductListLeftJoinMchCountDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取商品信息
     * left join lkt_order_details
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/13 16:53
     */
    List<Map<String, Object>> getProductListJoinOrderDetailsDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 搜索量+1
     *
     * @param productListModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 9:28
     */
    @Update("update lkt_product_list set search_num = search_num+1 where id = #{id}")
    int updateAddSearchNum(ProductListModel productListModel) throws LaiKeApiException;

    //上架时间置空
    @Update("update lkt_product_list set upper_shelf_time = null where id = #{goodsId}")
    int setUpperTimeNull(int goodsId) throws LaiKeApiException;

    /**
     * 获取商品最低价格和最高价格
     *
     * @param productListModel -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 12:39
     */
    @Select("select min(b.price) minPrice,max(b.price) maxPrice,min(b.yprice) minYprice,max(b.yprice) maxYprice from lkt_product_list a " +
            "left join lkt_configure b ON a.id = b.pid " +
            "where  a.store_id = #{store_id} " +
            "AND a.id = #{id} " +
            "AND b.recycle = 0")
    Map<String, BigDecimal> getGoodsPriceMinAndMax(ProductListModel productListModel) throws LaiKeApiException;

    /**
     * 评论数量+1
     *
     * @param goodsId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/28 14:57
     */
    @Update("update lkt_product_list set comment_num = comment_num+1 where id = #{goodsId}")
    int updateAddCommentNum(int goodsId) throws LaiKeApiException;


    /**
     * 统计店铺总销量
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 11:29
     */
    int countProductListVolume(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 统计店铺 销售总数、在售总数
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 11:29
     */
    Map<String, Object> countProductListByTotal(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 动态统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/26 17:30
     */
    int countProdcuctListDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取商品信息
     * LEFT JOIN lkt_configure
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/29 10:27
     */
    List<Map<String, Object>> getProductListJoinConfigureDynamic(Map<String, Object> map) throws LaiKeApiException;

    int countProductListJoinConfigureDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取运费信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/29 15:41
     */
    List<Map<String, Object>> getProductListJoinFreightDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取商品单表动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/30 11:43
     */
    List<Map<String, Object>> getGoodsInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 订单确认页面获取商品信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author wangxian
     * @date 2020/11/11 10:17
     */
    @Select("select m.product_title,m.volume,c.price,c.unit,c.attribute,c.img,c.yprice,m.freight,m.product_class,m.brand_id,m.weight,m.mch_id,m.is_distribution " +
            "from lkt_product_list AS m LEFT JOIN lkt_configure AS c ON m.id = c.pid  " +
            "where m.store_id = #{store_id} and c.num > 0 and c.num >= #{num} and m.status = 2 and m.id = #{pid} and c.id = #{cid}")
    Map<String, Object> settlementProductsInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 减少商品总库存
     *
     * @param pid -
     * @param num -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/19 16:47
     */
    @Update("update lkt_product_list set num = num - #{num} where id = #{pid}")
    int reduceGoodsStockNum(int pid, int num) throws LaiKeApiException;


    /**
     * 更新商品销量
     *
     * @param num     -
     * @param storeId -
     * @param pid     -
     * @return int
     * @throws LaiKeApiException -
     */
    @Update("update lkt_product_list set volume = volume + #{num} where store_id = #{storeId} and id = #{pid}")
    int updateProductListVolume(int num, int storeId, int pid) throws LaiKeApiException;

    /**
     * 获取状态为上架的商品属性库存
     *
     * @param storeId
     * @param pid
     * @param sid
     * @return
     */
    @Select("select b.num from lkt_product_list as a " +
            "left join lkt_configure as b on a.id = b.pid where a.store_id = #{storeId} " +
            "and a.status = 2 and a.mch_status = 2 and b.pid = #{pid} and b.id = #{sid} " +
            "and a.recycle = 0")
    int getProductNum(int storeId, int pid, int sid);


    /**
     * 获取商城商品最新顺序号
     *
     * @param storeId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/19 13:50
     */
    @Select("select if(sort is null,1,MAX(sort)+1) as sort from lkt_product_list where store_id = #{storeId} ")
    int getGoodsMaxSort(int storeId) throws LaiKeApiException;


    /**
     * 获取商城【店铺】商品最新顺序号
     *
     * @param storeId -
     * @param mchId   -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/19 13:50
     */
    @Select("select if(sort is null,1,MAX(sort)+1) as sort from lkt_product_list where store_id = #{storeId} and mch_id=#{mchId} ")
    int getGoodsMaxSortByMch(int storeId, int mchId) throws LaiKeApiException;

    /**
     * 查询商品序号
     *
     * @param storeId -
     * @param id      -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 18:07
     */
    @Select("select sort from lkt_product_list where store_id = #{storeId} and id = #{id}")
    int getGoodsSort(int storeId, int id) throws LaiKeApiException;


    /**
     * 获取特惠商品
     *
     * @param storeId   -
     * @param pageModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/24 10:27
     */
    @Select("select a.id,a.product_title,a.subtitle,a.imgurl,a.volume,min(c.price) as price,c.yprice from lkt_product_list AS a RIGHT JOIN lkt_configure AS c ON a.id = c.pid " +
            "where a.store_id = #{storeId} and a.status = 2 and a.mch_status = 2 and a.recycle = 0 and a.active = 6  " +
            "group by c.pid  order by a.sort desc,a.add_date DESC limit #{pageModel.pageNo},#{pageModel.pageSize}")
    List<Map<String, Object>> getGoodsTeHuiInfo(int storeId, PageModel pageModel) throws LaiKeApiException;


    /**
     * 获取赠送商品
     *
     * @param storeId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 17:29
     */
    @Select(" select b.id,b.product_title from lkt_configure as a left join lkt_product_list as b on a.pid = b.id " +
            " left join lkt_mch as c on b.mch_id = c.id  where b.store_id = #{storeId} and b.recycle=0 " +
            " and c.store_id = b.store_id and b.active = 1 and b.status = 2  and a.num > 0  group by b.id ")
    List<Map<String, Object>> getGiveGoodsList(int storeId) throws LaiKeApiException;


    /**
     * 获取销售前10的商品信息
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/12 18:08
     */
    @Select("select id,product_title,volume from lkt_product_list where store_id = #{storeId} order by volume desc limit 10")
    List<Map<String, Object>> countGoodsTop10(int storeId) throws LaiKeApiException;


    /**
     * 获取商品详细信息
     *
     * @param storeId -
     * @param goodsId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 17:51
     */
    @Select("SELECT c.pname AS className, b.name AS freightName, d.brand_name AS brandName, a.* FROM lkt_product_list a " +
            "LEFT JOIN lkt_freight b ON b.id = a.freight " +
            " LEFT JOIN lkt_product_class c ON c.cid = substring_index(substring_index(a.product_class, '-', 2), '-', -1) " +
            " LEFT JOIN lkt_brand_class d ON d.brand_id = a.brand_id " +
            "where a.store_id=#{storeId} " +
            "and a.id=#{goodsId}")
    List<Map<String, Object>> selectGoodsDetailInfo(int storeId, int goodsId) throws LaiKeApiException;


    /**
     * 获取拼团商品详细信息
     *
     * @param storeId -
     * @param goodsId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/30 18:53
     */
    @Select("SELECT b.name ,b.freight ,a.mch_id FROM lkt_product_list a " +
            "LEFT JOIN lkt_freight b ON b.id = a.freight " +
            "where a.store_id=#{storeId} " +
            "and a.id=#{goodsId}")
    Map<String, Object> selectGoodsFreightInfo(int storeId, int goodsId) throws LaiKeApiException;
}