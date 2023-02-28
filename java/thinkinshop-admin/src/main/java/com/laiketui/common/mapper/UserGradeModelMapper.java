package com.laiketui.common.mapper;

import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 用户折扣
 *
 * @author Trick
 * @date 2020/10/14 17:46
 */
public interface UserGradeModelMapper extends BaseMapper<UserGradeModel> {


    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取最低折扣
     *
     * @param storeId -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/14 17:46
     */
    @Select("select if(rate is null,1,min(rate)) from lkt_user_grade where store_id = #{storeId} and rate >0 ")
    BigDecimal getGradeLow(int storeId) throws LaiKeApiException;


    /**
     * 获取所有会员等级
     *
     * @param storeId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/15 17:50
     */
    @Select("select * from lkt_user_grade where store_id = #{storeId} order by rate desc")
    List<Map<String, Object>> getUserGradeAll(int storeId) throws LaiKeApiException;

    /**
     * 获取可升级的会员等级
     *
     * @param storeId -
     * @param rate    -
     * @return UserGradeModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/23 19:20
     */
    @Select("select * from lkt_user_grade where store_id = #{storeId} and rate < #{rate}")
    List<Map<String, Object>> getUserGradeRateInfo(int storeId, BigDecimal rate) throws LaiKeApiException;

    /**
     * 获取可升级的会员等级 统计
     *
     * @param storeId -
     * @param rate    -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/24 10:47
     */
    @Select("select count(1) from lkt_user_grade where store_id = #{storeId} and rate < #{rate}")
    int getUserGradeRateInfoCount(int storeId, BigDecimal rate) throws LaiKeApiException;


    /**
     * 获取等级信息-分页
     *
     * @param userGradeModel -
     * @param pageStar       -
     * @param pageEnd        -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 11:36
     */
    @Select("<script>" +
            "select * from lkt_user_grade where store_id = #{userGradeModel.store_id} " +
            "<if test=\"userGradeModel.id != null and userGradeModel.id != '' \">and id = #{userGradeModel.id} </if>" +
            "order by rate desc " +
            "limit #{pageStar},#{pageEnd}" +
            "</script>")
    List<Map<String, Object>> getUserGradeInfoPage(UserGradeModel userGradeModel, int pageStar, int pageEnd) throws LaiKeApiException;

    /**
     * 获取等级信息-统计
     *
     * @param userGradeModel -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 11:36
     */
    @Select("<script>" +
            "select count(1) from lkt_user_grade where store_id = #{store_id} " +
            "<if test=\"id != null and id != '' \">and id = #{id} </if>" +
            "</script>")
    int countUserGradeInfoPage(UserGradeModel userGradeModel) throws LaiKeApiException;

    /**
     * 获取会员未过期的等级信息
     *
     * @param storeId -
     * @param userId  -
     * @param sysDate -
     * @return User
     * @author Trick
     * @date 2021/4/21 15:32
     */
    @Select("select a.* from lkt_user_grade a,lkt_user b  where a.store_id=#{storeId} and b.user_id=#{userId} and b.grade_end > #{sysDate} and a.id = b.grade")
    UserGradeModel getUserGradeInfo(int storeId, String userId, Date sysDate);

    /**
     * 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 15:21
     */
    List<Map<String, Object>> getUserGradeInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取最低折扣会员等级
     *
     * @param storeId -
     * @param rate    -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/24 10:12
     */
    @Select("select * from lkt_user_grade where store_id = #{storeId} and rate <= #{rate}")
    List<Map<String, Object>> getUserGradeLowRateInfo(int storeId, BigDecimal rate) throws LaiKeApiException;


    /**
     * 重置用户会员等级
     *
     * @param storeId -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/11 11:03
     */
    @Update("update lkt_user_grade set pro_id = NULL where store_id = #{storeId} and pro_id is not NULL")
    int updateUserGradeReProId(int storeId) throws LaiKeApiException;

}