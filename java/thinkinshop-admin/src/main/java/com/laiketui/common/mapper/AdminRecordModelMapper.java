package com.laiketui.common.mapper;

import com.laiketui.domain.log.AdminRecordModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author Trick
 */
public interface AdminRecordModelMapper extends BaseMapper<AdminRecordModel> {


    /**
     * 获取管理员日志信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 17:25
     */
    List<Map<String, Object>> selectAdminLoggerInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取管理员日志信息 - 统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 17:32
     */
    int countAdminLoggerInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 删除管理员日志
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 17:43
     */
    int delAdminLoggerInfo(Map<String, Object> map) throws LaiKeApiException;
}