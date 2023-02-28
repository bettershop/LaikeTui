package com.laiketui.common.mapper;

import com.laiketui.domain.home.UiNavigationBarModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * ui导航栏
 *
 * @author Trick
 * @date 2021/2/23 10:56
 */
public interface UiNavigationBarModelMapper extends BaseMapper<UiNavigationBarModel> {


    /**
     * 获取ui导航栏
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/23 10:58
     */
    @Select("SELECT name,url,image  FROM `lkt_ui_navigation_bar` where  store_id = #{storeId} and isshow = '1' order by sort desc")
    List<Map<String, Object>> getNavigationInfo(int storeId) throws LaiKeApiException;

    @Select("select max(sort)+1 from lkt_ui_navigation_bar where  store_id = #{storeId}")
    int getMaxSort(int storeId);


    @Update("update lkt_ui_navigation_bar a,lkt_ui_navigation_bar b set a.sort=b.sort,b.sort = a.sort where a.id=#{id} and b.id=#{id1} ")
    int move(int id,int id1);

}