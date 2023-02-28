package com.laiketui.common.services;

import com.laiketui.api.common.PublicAdminGroupService;
import com.laiketui.common.mapper.AdminCgModelMapper;
import com.laiketui.domain.config.AdminCgModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 通用省市县接口
 * @author wangxian
 */
@Service
public class PublicAdminGroupServiceImpl implements PublicAdminGroupService {

    private Logger logger = LoggerFactory.getLogger(PublicAdminGroupServiceImpl.class);

    @Autowired
    public AdminCgModelMapper adminCgModelMapper;

    @Override
    public List<AdminCgModel> getAdminCgModel(AdminCgModel adminCgModel) {
        try {
            return adminCgModelMapper.select(adminCgModel);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取省市县数据异常：{}",e.getMessage());
        }
        return null;
    }
}
