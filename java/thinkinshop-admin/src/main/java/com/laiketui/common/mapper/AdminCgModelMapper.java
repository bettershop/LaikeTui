package com.laiketui.common.mapper;

import com.laiketui.domain.config.AdminCgModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;

import java.util.List;
import java.util.Map;


/**
 * 省市县
 *
 * @author Trick
 * @date 2020/12/2 10:53
 */
public interface AdminCgModelMapper extends BaseMapper<AdminCgModel> {


    /**
     *  获取省市县动态sql
     * @author Trick
     * @date 2020/12/2 10:53
     * @param map -
     * @return  List
     * @throws  LaiKeApiException    -
     */
    List<Map<String, Object>> getAdminCgInfoDynamic(Map<String, Object> map) throws LaiKeApiException;

}