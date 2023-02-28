package com.laiketui.api.modules.backend;


import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 商品标签管理
 *
 * @author Trick
 * @date 2021/6/25 18:05
 */
public interface LabelService {

    /**
     * 获取商品列表
     *
     * @param vo   -
     * @param name -
     * @param id   -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/25 18:06
     */
    Map<String, Object> index(MainVo vo, String name, Integer id) throws LaiKeApiException;

    /**
     * 添加/编辑商品标签
     *
     * @param vo   -
     * @param name -
     * @param id   -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/7 10:30
     */
    void addGoodsLabel(MainVo vo, String name, Integer id) throws LaiKeApiException;

    /**
     * 删除商品标签
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/7 10:40
     */
    void delGoodsLabel(MainVo vo, int id) throws LaiKeApiException;
}
