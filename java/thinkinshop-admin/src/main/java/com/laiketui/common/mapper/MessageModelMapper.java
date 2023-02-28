package com.laiketui.common.mapper;

import com.laiketui.domain.dictionary.MessageModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 短信模板
 *
 * @author Trick
 * @date 2021/1/18 14:49
 */
public interface MessageModelMapper extends BaseMapper<MessageModel> {


    /**
     * 获取模板信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/18 14:50
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取模板信息-统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/18 14:50
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

}