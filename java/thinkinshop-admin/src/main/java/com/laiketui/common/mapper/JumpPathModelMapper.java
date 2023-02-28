package com.laiketui.common.mapper;

import com.laiketui.domain.config.JumpPathModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 跳转路径
 *
 * @author Trick
 * @date 2021/6/30 14:10
 */
public interface JumpPathModelMapper extends BaseMapper<JumpPathModel> {


    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}