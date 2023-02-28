package com.laiketui.common.services;

import com.laiketui.api.common.PublicAdminRecordService;
import com.laiketui.common.mapper.AdminRecordModelMapper;
import com.laiketui.domain.log.AdminRecordModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 管理员操作日志
 * @author wangxian
 */
@Service
public class PublicAdminRecordServiceImpl implements PublicAdminRecordService {

    /***/
    @Autowired
    private AdminRecordModelMapper adminRecordModelMapper;

    @Override
    public int adminRecode(AdminRecordModel adminRecordModel) {
        //服务器所在系统时间
        adminRecordModel.setAdd_date(new Date());
        return adminRecordModelMapper.insert(adminRecordModel);
    }


}
