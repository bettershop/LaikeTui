package com.laiketui.api.modules.backend.order;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.ConfigVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 订单设置
 *
 * @author wangxian
 */
public interface ConfigService {

    /**
     * 获取订单设置
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author wangxian
     * @date 2021/7/26 16:41
     * @see Trick
     */
    Map<String, Object> configShow(MainVo vo) throws LaiKeApiException;

    /**
     * 保存订单设置
     *
     * @param configVo -
     * @throws LaiKeApiException-
     * @author wangxian
     * @date 2021/7/26 18:31
     * @see Trick
     */
    void saveConfig(ConfigVo configVo) throws LaiKeApiException;

}
