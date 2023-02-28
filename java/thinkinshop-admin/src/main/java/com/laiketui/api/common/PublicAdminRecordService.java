package com.laiketui.api.common;

import com.laiketui.domain.log.AdminRecordModel;

public interface PublicAdminRecordService {

    /**
     * 增加管理员操作日志
     *
     * @param adminRecordModel
     * @return
     */
    int adminRecode(AdminRecordModel adminRecordModel);

}
