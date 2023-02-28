package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 资源管理
 *
 * @author Trick
 * @date 2021/7/21 16:42
 */
public interface ResourcesService {

    /**
     * 资源列表
     *
     * @param vo      -
     * @param mchName -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/21 16:44
     */
    Map<String, Object> index(MainVo vo, String mchName) throws LaiKeApiException;

    /**
     * 批量下载图片
     *
     * @param vo       -
     * @param response -
     * @param imgIds   -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/22 10:22
     */
    void downForZip(MainVo vo, HttpServletResponse response, String imgIds) throws LaiKeApiException;

    /**
     * 批量删除
     *
     * @param vo     -
     * @param imgIds -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/22 14:59
     */
    void del(MainVo vo, String imgIds) throws LaiKeApiException;

}
