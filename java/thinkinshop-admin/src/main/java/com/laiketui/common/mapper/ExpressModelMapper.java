package com.laiketui.common.mapper;

import com.laiketui.domain.config.ExpressModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 快递公司物流
 *
 * @author Trick
 * @date 2021/7/6 17:09
 */
public interface ExpressModelMapper extends BaseMapper<ExpressModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}