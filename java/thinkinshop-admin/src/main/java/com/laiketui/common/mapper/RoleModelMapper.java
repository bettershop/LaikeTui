package com.laiketui.common.mapper;

import com.laiketui.domain.mch.RoleModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 权限
 *
 * @author Trick
 * @date 2021/1/14 9:37
 */
public interface RoleModelMapper extends BaseMapper<RoleModel> {


    /**
     * 获取权限列表
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 9:38
     */
    List<Map<String, Object>> selectRoleInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取权限列表-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 9:38
     */
    int countRoleInfo(Map<String, Object> map) throws LaiKeApiException;

}