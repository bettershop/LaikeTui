package com.laiketui.common.mapper;

import com.laiketui.domain.message.MessageLoggingModal;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 功能：管理后台首页消息弹窗
 *
 * @author wangxian
 */
public interface MessageLoggingModalMapper extends BaseMapper<MessageLoggingModal> {

    /**
     * 更新弹窗消息表的记录状态
     *
     * @param storeId
     * @param mchId
     * @param parameter
     * @param type
     * @return
     */
    @Update("update lkt_message_logging set is_popup = 1 , read_or_not = 1  where store_id = #{storeId} and mch_id = #{mchId} and parameter = #{parameter} and type in ( ${type} ) ")
    int updateMessLogInfo(int storeId, int mchId, String parameter, String type);


    @Update("update lkt_message_logging set read_or_not = 1  where store_id = #{storeId} and mch_id = #{mchId} and type = #{type} ")
    int noticeRead(int storeId, int mchId, String type);

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;
}