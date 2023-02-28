package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.app.AppConfigInfoVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * app管理
 *
 * @author Trick
 * @date 2021/1/22 9:40
 */
public interface ApplicationManageService {


    /**
     * 获取app版本配置信息
     *
     * @param storeId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 9:41
     */
    Map<String, Object> getVersionConfigInfo(int storeId) throws LaiKeApiException;


    /**
     * 添加/编辑版本配置信息
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 9:49
     */
    boolean addVersionConfigInfo(AppConfigInfoVo vo) throws LaiKeApiException;

}
