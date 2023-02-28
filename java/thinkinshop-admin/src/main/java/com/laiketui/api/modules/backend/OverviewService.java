package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.saas.OverviewVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 功能导览
 *
 * @author Trick
 * @date 2021/1/26 11:30
 */
public interface OverviewService {
    /**
     * 功能导览页面数据
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/15 11:49
     */
    Map<String, Object> index(MainVo vo) throws LaiKeApiException;


    /**
     * 编辑商城导览列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/16 9:54
     */
    Map<String, Object> functionList(OverviewVo vo) throws LaiKeApiException;

    /**
    * 排序出现问题的时候使用
    *
    * @param vo -
    * @throws LaiKeApiException-
    * @author Trick
    * @date 2021/9/14 20:32
    */
    void rsort(MainVo vo) throws LaiKeApiException;

    /**
     * 置顶
     *
     * @param vo  -
     * @param id  -
     * @param sid -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/16 11:07
     */
    void sortTop(MainVo vo, int id, Integer sid) throws LaiKeApiException;

    /**
     * 上下移动
     *
     * @param vo  -
     * @param id  -
     * @param id2 -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/16 14:48
     */
    void move(MainVo vo, int id, int id2) throws LaiKeApiException;


    /**
     * 是否显示开关
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/16 14:48
     */
    void isDisplaySwitch(MainVo vo, int id) throws LaiKeApiException;
}
