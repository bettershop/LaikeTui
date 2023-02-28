package com.laiketui.common.mapper;

import com.laiketui.domain.home.SystemMessageModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 获取我的消息 sql
 *
 * @author Trick
 * @date 2020/10/16 17:20
 */
public interface SystemMessageModelMapper extends BaseMapper<SystemMessageModel> {


    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取我的消息
     *
     * @param systemMessageModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 17:23
     */
    List<Map<String, Object>> getMessageList(SystemMessageModel systemMessageModel) throws LaiKeApiException;


    /**
     * 通用修改
     *
     * @param systemMessageModel -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/16 17:23
     */
    int updateMessage(SystemMessageModel systemMessageModel) throws LaiKeApiException;


    /**
     * 删除过期的消息
     *
     * @param storeId -
     * @param endTime -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/15 17:08
     */
    @Delete("delete from lkt_system_message where store_id = #{storeId} and time<#{endTime}")
    int delMessageExpire(int storeId, Date endTime) throws LaiKeApiException;

    /**
     * 批量通知
     *
     * @param map -
     * @return int
     * @author Trick
     * @date 2021/11/19 18:03
     */
    int noticeUserAll(Map<String, Object> map);

}