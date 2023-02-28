package com.laiketui.root.utils.common;

import com.laiketui.root.exception.LaiKeApiException;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/30 12:02
 * @version: 1.0
 * @modified By:
 */
@Component
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T> {

    /**
     * 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 10:56
     */
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态sql-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/20 10:56
     */
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态统计 - key=需统计的字段 必须
     *
     * @param map -
     * @return BigDecimal
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/7/5 10:43
     */
    BigDecimal sumDynamic(Map<String, Object> map) throws LaiKeApiException;
}
