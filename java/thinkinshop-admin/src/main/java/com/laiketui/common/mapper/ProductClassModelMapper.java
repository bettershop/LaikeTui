package com.laiketui.common.mapper;

import com.laiketui.domain.product.ProductClassModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 商品分类
 *
 * @author Trick
 * @date 2020/10/10 17:44
 */
public interface ProductClassModelMapper extends BaseMapper<ProductClassModel> {


    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 查询商品并分类显示返回JSON至小程序(一级分类)
     *
     * @param storeId-
     * @return List<ProductClassModel>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/10 17:47
     */
    @Select("select cid,pname,english_name from lkt_product_class where store_id = #{storeId} and is_display=0 and recycle = 0 and sid=0 order by sort desc")
    List<ProductClassModel> getProduct(int storeId) throws LaiKeApiException;


    /**
     * 获取类目信息
     *
     * @param productClassModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/19 15:39
     */
    List<ProductClassModel> getProductClassLevel(ProductClassModel productClassModel) throws LaiKeApiException;

    /**
     * 匹配商品类目名称
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/15 14:41
     */
    @Select("select * from lkt_product_class where store_id = #{store_id} and recycle = 0 and pname LIKE CONCAT('%', #{pname},'%') order by sort desc")
    List<ProductClassModel> getGoodsClass(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取当前店铺所有的类别
     *
     * @param mchId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/23 14:51
     */
    @Select("select distinct b.cid,b.pname from lkt_product_list a,lkt_product_class b where mch_id=#{mchId} " +
            " and substring_index(substring_index(a.product_class, '-', 2), '-', -1) =  b.cid ")
    List<ProductClassModel> getGoodsClassByMch(int mchId) throws LaiKeApiException;

    //获取商品类别名称
    @Select(" select b.pname from lkt_product_list a,lkt_product_class b where substring_index(substring_index(a.product_class, '-', 2), '-', -1)=b.cid " +
            " and a.id=#{goodsId} ")
    String getGoodsClassName(int goodsId);


    /**
     * 获取最新序号
     *
     * @param storeId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 15:46
     */
    @Select("select if(sort is null,0,max(sort)) +1 from lkt_product_class where store_id=#{storeId}")
    int getGoodsClassMaxSort(int storeId) throws LaiKeApiException;

    /**
     * 单表动态统计
     *
     * @param productClassModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 15:38
     */
    int getGoodsClassCount(ProductClassModel productClassModel) throws LaiKeApiException;


    @Update("update lkt_product_class a,lkt_product_class b set a.sort=b.sort,b.sort = a.sort where a.cid=#{id} and b.cid=#{id1} ")
    int move(int id, int id1);
}