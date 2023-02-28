package com.laiketui.common.mapper;

import com.laiketui.domain.mch.MchPromiseModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 店铺保证金记录表
 *
 * @author Trick
 * @date 2021/10/25 16:48
 */
public interface MchPromiseModelMapper extends BaseMapper<MchPromiseModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}