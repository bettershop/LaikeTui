package com.laiketui.api.common;

import com.laiketui.domain.config.AdminCgModel;

import java.util.List;

/**
 * 通用省市县接口
 * @author wangxian
 */
public interface PublicAdminGroupService {

    /**
     * 根据条件获取结果
     *
     * @param adminCgModel
     * @return
     */
    List<AdminCgModel> getAdminCgModel(AdminCgModel adminCgModel);

}
