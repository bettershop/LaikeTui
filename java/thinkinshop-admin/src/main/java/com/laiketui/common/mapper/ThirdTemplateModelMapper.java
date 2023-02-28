package com.laiketui.common.mapper;

import com.laiketui.domain.weixin.ThirdTemplateModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 小程序模板
 *
 * @author Trick
 * @date 2021/1/20 10:43
 */
public interface ThirdTemplateModelMapper extends BaseMapper<ThirdTemplateModel> {


    /**
     * 动态sql
     *
     * @param map -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 11:23
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态sql
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 11:23
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

}