package com.laiketui.common.mapper;

import com.laiketui.domain.product.TaoBaoWorkModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 淘宝任务
 *
 * @author Trick
 * @date 2021/1/4 17:03
 */
public interface TaoBaoWorkModelMapper extends BaseMapper<TaoBaoWorkModel> {


    /**
     * 获取淘宝任务列表信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 17:04
     */
    List<Map<String, Object>> getTaoBaoInfoList(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取淘宝任务列表信息-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 17:04
     */
    Integer getTaoBaoInfoCount(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取淘宝明细
     *
     * @param id        -
     * @param pageStart -
     * @param pageEnd   -
     * @return List
     * @author Trick
     * @date 2021/7/14 16:29
     */
    @Select("<script>" +
            "select b.*,a.title,c.product_title goodsName,c.id goodsId from lkt_taobao_work a,lkt_taobao b left join lkt_product_list c on b.itemid=c.product_number " +
            " where a.id=b.w_id and a.id=#{id} " +
            " group by b.itemid " +
            "<if test=\"pageStart != null and pageEnd != ''\">limit #{pageStart},#{pageEnd}</if>" +
            "</script>")
    List<Map<String, Object>> getTaoBaoDetailList(int id, int pageStart, int pageEnd);

    @Select("<script>" +
            "select count(1) from lkt_taobao_work a,lkt_taobao b left join lkt_product_list c on b.itemid=c.product_number " +
            " where a.id=b.w_id and a.id=#{id} " +
//            " group by b.itemid " +
            "</script>")
    int countTaoBaoDetailList(int id);

    /**
     * 批量删除
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 9:31
     */
    int batDelById(Map<String, Object> map) throws LaiKeApiException;
}