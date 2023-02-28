package com.laiketui.common.mapper;

import com.laiketui.domain.config.ActivityModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 活动表
 *
 * @author Trick
 * @date 2021/2/23 18:00
 */
public interface ActivityModelMapper extends BaseMapper<ActivityModel> {
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


    @Select("select if(sort is null,0,MAX(sort)+1) as sort from lkt_activity where store_id = #{storeId}")
    int maxActivity(int storeId);


    @Update("update lkt_activity a,lkt_activity b set a.sort=b.sort,b.sort = a.sort where a.id=#{id} and b.id=#{id1}")
    int move(int id, int id1) throws LaiKeApiException;

}