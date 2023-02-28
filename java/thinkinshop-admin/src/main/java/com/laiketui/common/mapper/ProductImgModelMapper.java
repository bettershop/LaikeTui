package com.laiketui.common.mapper;

import com.laiketui.domain.product.ProductImgModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 商品图片 sql
 *
 * @author Trick
 * @date 2020/11/16 11:17
 */
public interface ProductImgModelMapper extends BaseMapper<ProductImgModel> {


    /**
     *  获取商品图片信息
     *
     * @author Trick
     * @date 2020/11/16 11:18
     * @param productImgModel -
     * @return List
     * @throws  LaiKeApiException    -
     */
    @Select("select * from lkt_product_img where product_id = #{product_id} order by id asc")
    List<Map<String,Object>> getProductImgInfoByPid(ProductImgModel productImgModel) throws LaiKeApiException;


    //获取商品展示图 第一张
    @Select("select product_url from lkt_product_img where product_id = #{goodsId} order by id asc limit 1")
    String getProductImg(int goodsId) throws LaiKeApiException;

}