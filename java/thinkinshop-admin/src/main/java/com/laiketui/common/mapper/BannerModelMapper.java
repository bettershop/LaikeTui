package com.laiketui.common.mapper;

import com.laiketui.domain.config.BannerModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 轮播图sql映射
 *
 * @author Trick
 * @date 2020/10/10 16:06
 */
public interface BannerModelMapper extends BaseMapper<BannerModel> {


    /**
     * 查询轮播图,根据排序、轮播图id顺序排列
     * 首页轮播图
     *
     * @param bannerModel -
     * @return List<BannerModel>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/10 16:08
     */
    @Select("select * from lkt_banner where store_id = #{store_id} and type != #{type} and mch_id=#{mch_id} order by sort desc limit 0,3")
    List<BannerModel> getBanners(BannerModel bannerModel) throws LaiKeApiException;
    @Select("select * from lkt_banner where store_id = #{store_id} and type = #{type} and mch_id=#{mch_id} order by sort desc limit 0,3")
    List<BannerModel> getBannersByMchId(BannerModel bannerModel) throws LaiKeApiException;


    /**
     * 获取店铺主页轮播图
     *
     * @param storeId -
     * @param mchId   -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/23 15:19
     */
    @Select("select * from lkt_banner where store_id = #{storeId} and mch_id = #{mchId} order by sort desc limit 4")
    List<BannerModel> getBannersByMch(int storeId, int mchId) throws LaiKeApiException;


    /**
     * 根据url参数获取轮播图信息
     *
     * @param storeId  -
     * @param parmaStr -
     * @param key      -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 16:27
     */
    @Select("select * from lkt_banner where store_id = #{storeId} and url like CONCAT('%','${parmaStr}=${key}','%')")
    List<BannerModel> getBannersByUrl(int storeId, String parmaStr, String key) throws LaiKeApiException;


    /**
     * 根据id清空轮播图
     *
     * @param storeId -
     * @param id      -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 16:31
     */
    @Update("update lkt_banner set open_type = '',url = '' where store_id = #{storeId} and id = #{id}")
    int clearBannerById(int storeId, int id) throws LaiKeApiException;


    @Update("update lkt_banner a,lkt_banner b set a.sort=b.sort,b.sort = a.sort where a.id=#{id} and b.id=#{id1} ")
    int move(int id,int id1);



    /**
     * 获取最新序号
     *
     * @param storeId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 11:16
     */
    @Select("select if(MAX(sort) IS NULL, 0, MAX(sort))+1 as sort from lkt_banner where store_id = #{storeId}")
    int getMaxSort(int storeId) throws LaiKeApiException;

    /**
     * 获取轮播图信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 10:54
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取轮播图信息 - 统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 10:54
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}