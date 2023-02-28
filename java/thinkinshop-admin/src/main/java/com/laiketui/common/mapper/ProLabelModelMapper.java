package com.laiketui.common.mapper;

import com.laiketui.domain.product.ProLabelModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 商品标签
 *
 * @author Trick
 * @date 2021/6/25 18:12
 */
public interface ProLabelModelMapper extends BaseMapper<ProLabelModel> {
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}