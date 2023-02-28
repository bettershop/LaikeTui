package com.laiketui.common.mapper;

import com.laiketui.domain.weixin.ThirdMiniInfoModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 授权小程序信息表
 *
 * @author Trick
 * @date 2021/1/21 18:30
 */
public interface ThirdMiniInfoModelMapper extends BaseMapper<ThirdMiniInfoModel> {

    /**
     * 动态sql
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021-02-03 17:28:33
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
     * @date 2021-02-03 17:28:38
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}