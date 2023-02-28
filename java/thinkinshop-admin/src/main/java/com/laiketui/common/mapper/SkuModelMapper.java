package com.laiketui.common.mapper;

import com.laiketui.domain.config.SkuModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 属性 sql
 *
 * @author Trick
 * @date 2020/11/12 17:00
 */
public interface SkuModelMapper extends BaseMapper<SkuModel> {


    /**
     * 获取属性名称
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 17:01
     */
    List<Map<String, Object>> getAttributeDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取属性+值
     *
     * @param map -
     * @return List
     * @author Trick
     * @date 2021/5/31 19:19
     */
    List<Map<String, Object>> getAttributeDynamicAll(Map<String, Object> map);

    @Select("<script>" +
            "select a.id,b.id sid,a.name,b.name sname from lkt_sku a,lkt_sku b where a.id=b.sid and a.status=1 and a.recycle=0 " +
            "and b.status=1 and b.recycle=0  " +
            "<if test='keyword != null '> " +
            " and( a.name like concat('%',#{keyword},'%') or b.name like concat('%',#{keyword},'%') )" +
            "</if> " +
            "order by a.add_date limit #{pageStart},#{pageEnd}" +
            "</script>")
    List<Map<String, Object>> getSkuList(int pageStart, int pageEnd, String keyword);

    @Select("<script>" +
            "select count(distinct a.id) from lkt_sku a,lkt_sku b where a.id=b.sid and a.status=1 and a.recycle=0 " +
            "and b.status=1 and b.recycle=0  " +
            "<if test='keyword != null '> " +
            " and( a.name like concat('%',#{keyword},'%') or b.name like concat('%',#{keyword},'%') )" +
            "</if> " +
            "order by a.add_date" +
            "</script>")
    Integer countSkuList(String keyword);

    /**
     * 根据名称获取属性id
     *
     * @param storeId -
     * @param type    -
     * @param name    -
     * @param sid     -
     * @return int
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/21 13:03
     */
    @Select("select id from lkt_sku where store_id in (0,#{storeId}) and recycle = 0 and type = #{type} and name = #{name} and sid = #{sid}")
    Integer getAttributeByName(int storeId, int type, String name, int sid) throws LaiKeApiException;


    /**
     * 获取code
     *
     * @param sid -
     * @return Integer
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/21 14:08
     */
    @Select("select code from lkt_sku where recycle = 0 and status = 1 and sid = #{sid} order by add_date desc limit 1")
    String getAttributeByCode(int sid) throws LaiKeApiException;


    /**
     * 获取属性动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:51
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取属性动态sql-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:51
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    //属性是否使用中
    @Select("select count(1) from lkt_configure where attribute like concat('%','_${skuId}\";','%') and recycle=0")
    int countSkuIsUse(Integer skuId);
}