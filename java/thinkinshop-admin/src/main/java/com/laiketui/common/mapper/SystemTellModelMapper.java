package com.laiketui.common.mapper;

import com.laiketui.domain.systems.SystemTellModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 系统公告
 *
 * @author Trick
 */
public interface SystemTellModelMapper extends BaseMapper<SystemTellModel> {


    /**
     * 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 11:48
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态sql-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 11:48
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}