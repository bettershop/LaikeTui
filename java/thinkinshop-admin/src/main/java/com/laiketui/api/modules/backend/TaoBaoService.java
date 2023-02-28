package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.AddTaoBaoVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 淘宝助手接口
 *
 * @author Trick
 * @date 2021/1/4 16:31
 */
public interface TaoBaoService {

    /**
     * 获取抓取任务
     *
     * @param vo       -
     * @param status   -
     * @param taskName -
     * @param id       - 任务id
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 16:56
     */
    Map<String, Object> getTaoBaoList(MainVo vo, Integer status, String taskName, Integer id) throws LaiKeApiException;


    /**
     * 批量删除淘宝抓取任务
     *
     * @param vo      -
     * @param taskIds -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 9:10
     */
    boolean delTaoBaoTask(MainVo vo, String taskIds) throws LaiKeApiException;


    /**
     * 批量执行任务
     *
     * @param vo      -
     * @param taskIds -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 10:39
     */
    boolean executeTaoBaoById(MainVo vo, String taskIds) throws LaiKeApiException;

    /**
     * 重抓数据
     *
     * @param vo     -
     * @param taskId -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 14:28
     */
    void restoreExecuteTaoBaoById(MainVo vo, int taskId) throws LaiKeApiException;

    /**
     * 添加淘宝任务
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/5 14:06
     */
    boolean addTaoBaoTask(AddTaoBaoVo vo) throws LaiKeApiException;
}
