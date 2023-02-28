package com.laiketui.common.mapper;

import com.laiketui.domain.user.UserSearchModel;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * 用户搜索过的关键字
 *
 * @author Trick
 * @date 2021/6/18 10:20
 */
public interface UserSearchModelMapper extends BaseMapper<UserSearchModel> {

    /**
     * 获取用户搜索过的关键字
     *
     * @param storeId   -
     * @param token     -
     * @param startDate -
     * @param endDate   -
     * @return List
     * @author Trick
     * @date 2021/6/18 10:22
     */
    @Select("select keyword from lkt_user_search where store_id = #{storeId} and token = #{token} and add_date >= #{startDate} and add_date <= #{endDate} order by add_date desc")
    List<String> getUserSearchKey(int storeId, String token, Date startDate, Date endDate);

    @Select("select keyword from lkt_user_search where store_id = #{storeId} and user_id = #{userId} and add_date >= #{startDate} and add_date <= #{endDate} order by add_date desc")
    List<String> getUserSearchByUserId(int storeId, String userId, Date startDate, Date endDate);

    /**
     * 删除关键字
     *
     * @author Trick
     * @date 2021/6/18 10:47
     */
    @Delete("delete from lkt_user_search where store_id = #{storeId} and token = #{token} and keyword = #{key}")
    int delUserSearchKey(int storeId, String token, String key);

    @Delete("delete from lkt_user_search where store_id = #{storeId} and user_id = #{userId} and keyword = #{key}")
    int delUserSearchByUserId(int storeId, String userId, String key);

    /**
     * 一键清空关键字
     *
     * @author Trick
     * @date 2021/6/18 10:56
     */
    @Delete("delete from lkt_user_search where store_id = #{storeId} and token = #{token}")
    int delUserSearchKeyAll(int storeId, String token);

    @Delete("delete from lkt_user_search where store_id = #{storeId} and user_id = #{userId}")
    int delUserSearchByUserIdAll(int storeId, String userId);

    /**
     * 更新关键字搜索时间
     *
     * @author Trick
     * @date 2021/6/18 11:17
     */
    @Select("update lkt_user_search set add_date = #{sysDatem} where store_id = #{storeId} and user_id = #{userId} or token=#{token} and keyword = #{key}")
    int updateAddDate(Date sysDatem, int storeId, String userId, String token, String key);

}