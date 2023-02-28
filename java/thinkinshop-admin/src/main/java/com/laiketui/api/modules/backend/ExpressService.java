package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.ExpressSaveVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 物流公司管理
 *
 * @author Trick
 * @date 2021/7/6 16:48
 */
public interface ExpressService {

    /**
     * 物流公司列表
     *
     * @param vo       -
     * @param id       -
     * @param keyWord  -
     * @param sortType -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/6 16:49
     */
    Map<String, Object> index(MainVo vo, Integer id, String keyWord, Integer sortType) throws LaiKeApiException;


    /**
     * 物流开关
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/6 17:52
     */
    void expressSwitch(MainVo vo, Integer id) throws LaiKeApiException;


    /**
     * 添加/编辑物流
     *
     * @param vo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/6 17:52
     */
    void expressSave(ExpressSaveVo vo) throws LaiKeApiException;


    /**
     * 删除物流
     *
     * @param vo  -
     * @param ids -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/6 17:52
     */
    void expressDel(MainVo vo, String ids) throws LaiKeApiException;
}
