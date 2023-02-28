package com.laiketui.common.mapper;

import com.laiketui.domain.mch.MchBrowseModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 商城浏览 sql
 *
 * @author Trick
 * @date 2020/10/29 11:59
 */
public interface MchBrowseModelMapper extends BaseMapper<MchBrowseModel> {


    /**
     * 动态查询
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/10 14:49
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/10 14:49
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
    Integer countDynamicByUser(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 保存浏览记录
     *
     * @param mchBrowseModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/29 12:00
     */
    @Insert("insert into lkt_mch_browse(store_id,token,mch_id,user_id,event,add_time) " +
            " values (#{store_id},#{token},#{mch_id},#{user_id},#{event},#{add_time})")
    int saveBrowse(MchBrowseModel mchBrowseModel) throws LaiKeApiException;


    /**
     * 统计店铺访问量
     *
     * @param mchBrowseModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/9 18:32
     */
    @Select("<script>" +
            "select if(a.num is null,0,sum(a.num)) num from (select count(distinct user_id) num from lkt_mch_browse where store_id = #{store_id} " +
            " <if test='mch_id != null '> " +
            "and mch_id = #{mch_id} " +
            " </if> " +
            " group by  DATE_FORMAT(add_time,'%Y-%m-%d') ) a" +
            "</script>")
    int countMchBrowseNum(MchBrowseModel mchBrowseModel) throws LaiKeApiException;

    /**
     * 获取店铺访问记录动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/24 15:47
     */
    List<Map<String, Object>> getMchBrowseListDynamic(Map<String, Object> map) throws LaiKeApiException;

    int countMchBrowseListDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取用户足迹明细
     *
     * @param storeId -
     * @param mchId   -
     * @param userId  -
     * @return List
     * @author Trick
     * @date 2021/5/27 10:58
     */
    @Select("select event,DATE_FORMAT(add_time,'%H:%i:%s') AS add_time from lkt_mch_browse " +
            " where store_id = #{storeId} and mch_id = #{mchId} and user_id = #{userId} and TO_DAYS(add_time) = TO_DAYS(NOW()) order by add_time desc")
    List<Map<String, Object>> getMchBrowseByUser(int storeId, int mchId, String userId);

    @Select("select count(1) AS add_time from lkt_mch_browse " +
            " where store_id = #{storeId} and mch_id = #{mchId} and user_id = #{userId} and TO_DAYS(add_time) = TO_DAYS(NOW()) order by add_time desc")
    int countMchBrowseByUser(int storeId, int mchId, String userId);

    /**
     * 删除用户足迹
     *
     * @param storeId -
     * @param mchId   -
     * @param userId  -
     * @return int
     * @author Trick
     * @date 2021/5/27 11:09
     */
    @Delete("delete from lkt_mch_browse where store_id = #{storeId} and mch_id = #{mchId} and user_id = #{userId}")
    int delMchBrowseByUserId(int storeId, int mchId, String userId);


    /**
     * 更具订单时间统计用户访问数量
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 15:48
     */
    @Select("<script>" +
            "select IFNULL(count(distinct user_id),0)  from lkt_mch_browse where store_id = #{storeId} " +
            "<if test='mchId != null '> " +
            " AND mch_id = concat(',',#{mchId},',') " +
            "</if>" +
            "<if test='startDate != null '> " +
            "   and add_time <![CDATA[  >=  ]]> #{startDate}" +
            "</if>" +
            "<if test='endDate != null '> " +
            "   and add_time <![CDATA[  <=  ]]> #{endDate}" +
            "</if>" +
            " </script> ")
    BigDecimal sumBrowseByDate(int storeId, Integer mchId, Date startDate, Date endDate);


    /**
     * 统计某天每小时访问数
     *
     * @param storeId -
     * @param mchId   -
     * @param date    -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 16:30
     */
    @Select("<script>" +
            "select CONCAT('',HOUR(add_time),'时') time, IFNULL(count(distinct user_id),0) value from lkt_mch_browse where store_id = #{storeId}" +
            " <if test='mchId != null '> " +
            " and mch_id = #{mchId} " +
            " </if> " +
            " and TO_DAYS(add_time) = TO_DAYS(#{date}) group by HOUR(add_time) " +
            " </script> ")
    List<Map<String, Object>> getBrowseNumByHour(int storeId, Integer mchId, Date date);


    /**
     * 统计某月每天访问数
     *
     * @param storeId   -
     * @param mchId     -
     * @param startDate -
     * @param endDate   -
     * @return BigDecimal
     * @author Trick
     * @date 2021/5/28 16:30
     */
    @Select("<script>" +
            "SELECT DATE_FORMAT(add_time,'%m/%d') time,IFNULL(count(distinct user_id),0) value FROM `lkt_mch_browse` WHERE store_id = #{storeId} " +
            " <if test='mchId != null '> " +
            " AND mch_id = CONCAT(',',#{mchId},',') " +
            " </if> " +
            " AND add_time <![CDATA[  >=  ]]> #{startDate} and add_time <![CDATA[  <  ]]> #{endDate} group by user_id,DATE_FORMAT(add_time,'%Y-%m-%d')" +
            " </script> ")
    List<Map<String, Object>> getBrowseNumByMonth(int storeId, Integer mchId, Date startDate, Date endDate);


}