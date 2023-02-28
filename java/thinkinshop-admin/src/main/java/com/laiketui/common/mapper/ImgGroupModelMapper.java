package com.laiketui.common.mapper;

import com.laiketui.domain.upload.ImgGroupModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * 图片分组表
 *
 * @author Trick
 * @date 2021/7/8 9:29
 */
public interface ImgGroupModelMapper extends BaseMapper<ImgGroupModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}