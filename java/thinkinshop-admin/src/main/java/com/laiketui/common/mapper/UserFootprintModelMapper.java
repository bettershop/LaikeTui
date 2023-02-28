package com.laiketui.common.mapper;

import com.laiketui.domain.user.UserFootprintModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 足迹 sql
 *
 * @author Trick
 * @date 2020/10/29 10:10
 */
public interface UserFootprintModelMapper extends BaseMapper<UserFootprintModel> {


    /**
     *  插入一条足迹
     *
     * @author Trick
     * @date 2020/10/29 10:10
     * @param userFootprintModel -
     * @return int
     * @throws  LaiKeApiException    -
     */
    @Insert("insert into lkt_user_footprint(store_id,user_id,p_id,add_time) values(#{store_id},#{user_id},#{p_id},#{add_time})")
    int saveUserFootprint(UserFootprintModel userFootprintModel) throws LaiKeApiException;


    /**
     *  根据日期范围获取足迹
     *
     * @author Trick
     * @date 2020/10/30 16:19
     * @param map -
     * @return List
     * @throws    LaiKeApiException  -
     */
    @Select("select * from lkt_user_footprint where store_id = #{store_id} and user_id = #{user_id} " +
            "and add_time > #{add_time} and add_time < #{add_time}")
    List<UserFootprintModel> getFootprintByDate(Map<String,Object> map) throws LaiKeApiException;

}