package com.laiketui.api.modules.backend.systems;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 搜索配置
 *
 * @author Trick
 * @date 2021/1/19 15:16
 */
public interface SearchService {


    /**
     * 获取搜索配置信息
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 15:21
     */
    Map<String, Object> getSearchConfigIndex(MainVo vo) throws LaiKeApiException;


    /**
     * 添加/编辑搜索配置
     *
     * @param vo       -
     * @param isOpen   -
     * @param limitNum -
     * @param keyword  -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 15:21
     */
    boolean addSearchConfig(MainVo vo, int isOpen, int limitNum, String keyword) throws LaiKeApiException;
}
