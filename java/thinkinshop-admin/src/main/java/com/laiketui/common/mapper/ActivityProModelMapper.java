package com.laiketui.common.mapper;

import com.laiketui.domain.product.ActivityProModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 活动商品表
 *
 * @author Trick
 * @date 2021/2/22 14:02
 */
public interface ActivityProModelMapper extends BaseMapper<ActivityProModel> {


    /**
     * 获取活动中的商品
     *
     * @param storeId    -
     * @param aid        -
     * @param statusList -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/22 14:03
     */
    @Select("<script>" +
            "select a.id,a.cover_map,a.product_title,a.imgurl,a.status,IF(a.volume <![CDATA[ < ]]> 0, 0, a.volume) volume,a.s_type,min(c.price) as price,c.yprice " +
            "from lkt_activity_pro as b left join lkt_product_list as a on b.p_id = a.id right join lkt_configure as c ON a.id = c.pid " +
            "where b.store_id = #{storeId} and b.activity_id = #{aid} and b.is_display = 1 and a.store_id = #{storeId} " +
            " <if test='statusList != null '> " +
            "   <foreach collection=\"statusList\" item=\"status\" separator=\",\" open=\"and a.status in(\" close=\")\"> " +
            "        #{status,jdbcType=INTEGER}" +
            "   </foreach> " +
            "</if> " +
            "and a.mch_status = 2 and a.recycle = 0 and a.active = 1 group by c.pid order by b.sort desc" +
            "</script>")
    List<Map<String, Object>> getActivityGoodsList(int storeId, int aid, List<Integer> statusList) throws LaiKeApiException;


    @Update("update lkt_activity_pro a,lkt_activity_pro b set a.sort=b.sort,b.sort = a.sort where a.id=#{id} and b.id=#{id1}")
    int move(int id, int id1) throws LaiKeApiException;


    @Select("select MAX(sort)+1 as sort from lkt_activity_pro where activity_id = #{activityId}")
    int maxActivity(int activityId);


    List<Map<String, Object>> selectGoodsDynamic(Map<String, Object> map) throws LaiKeApiException;
    int countGoodsDynamic(Map<String, Object> map) throws LaiKeApiException;

}