package com.laiketui.api.common;

import com.laiketui.domain.config.ExpressModel;

import java.util.List;

/**
 * 物流公司信息服务接口
 *
 * @author wangxian
 */
public interface PublicExpressService {

    /**
     * 获取所有物流公司信息
     * @return
     */
    List<ExpressModel> getExpressInfo();



}
