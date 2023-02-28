package com.laiketui.common.mapper;

import com.laiketui.domain.config.GuideModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 引导图sql
 *
 * @author Trick
 * @date 2020/10/26 9:23
 */
public interface GuideModelMapper extends BaseMapper<GuideModel> {


    /**
     * 获取某商城引导图
     *
     * @param guideModel -
     * @return GuideModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/26 9:24
     */
    @Select("select * from lkt_guide where store_id = #{store_id} and type = #{type} and source = #{source} order by sort,add_date desc limit 3")
    List<GuideModel> getGuidedGraph(GuideModel guideModel) throws LaiKeApiException;


    /**
     * 获取引导图
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 16:43
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取引导图-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 16:43
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Update("update lkt_guide_menu set guide_sort = #{sort} where store_id = #{storeId} and role_id = #{roleId} and menu_id = #{mid}")
    int sortMove(int storeId, int roleId, int mid, int sort);

    @Update("update lkt_guide_menu set guide_sort = #{sort} where id=#{id}")
    int test(int sort, int id);


    /**
     * 功能导览 上下移动
     *
     * @param storeId -
     * @param roleId -
     * @param id     -
     * @param moveId -
     * @return int
     * @author Trick
     * @date 2021-06-16 15:11:27
     */
    @Update("update lkt_guide_menu a,lkt_guide_menu b set a.guide_sort= b.guide_sort,b.guide_sort=a.guide_sort " +
            " where a.store_id=#{storeId} and a.store_id=b.store_id " +
            " and a.role_id=#{roleId} and b.role_id=#{roleId} and a.menu_id=#{id} and b.menu_id=#{moveId}")
    int moveSort(int storeId, int roleId, int id, int moveId);

    /**
     * 是否显示菜单开关
     *
     * @param storeId -
     * @param roleId  -
     * @param mid     -
     * @param show    -
     * @return int
     * @author Trick
     * @date 2021/6/16 15:34
     */
    @Update("update lkt_guide_menu set is_display = #{show} where store_id = #{storeId} and role_id = #{roleId} and menu_id = #{mid}")
    int displaySwitch(int storeId, int roleId, int mid, int show);
}