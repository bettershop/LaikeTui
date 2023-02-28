package com.laiketui.common.mapper;

import com.laiketui.domain.product.BrandClassModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 商品品牌 sql
 *
 * @author Trick
 * @date 2020/10/16 14:44
 */
public interface BrandClassModelMapper extends BaseMapper<BrandClassModel> {


    /**
     * 获取品牌动态
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 14:44
     */
    List<Map<String, Object>> getBrandClassDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取品牌信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 17:54
     */
    List<Map<String, Object>> getBrandClassInfo(Map<String, Object> map) throws LaiKeApiException;

    int countBrandClassInfo(Map<String, Object> map);

    /**
     * 获取品牌最新序号
     *
     * @param storeId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/31 9:49
     */
    @Select("select if(sort is null,0,max(sort))+1 from lkt_brand_class where store_id=#{storeId}")
    int getBrandClassMaxSort(int storeId) throws LaiKeApiException;

}