package com.laiketui.common.mapper;

import com.laiketui.domain.task.TaoBaoModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 淘宝助手
 *
 * @author Trick
 * @date 2020/12/15 17:36
 */
public interface TaoBaoModelMapper extends BaseMapper<TaoBaoModel> {


    /**
     * 根据商城+状态获取任务
     *
     * @param storeId -
     * @param status  -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/15 17:38
     */
    @Select("select * from lkt_taobao where store_id=#{storeId} and status=#{status} order by creattime asc")
    List<TaoBaoModel> selectTaoBao(int storeId, int status) throws LaiKeApiException;


    /**
     * 批量删除
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 9:30
     */
    int batDelById(Map<String, Object> map) throws LaiKeApiException;

}