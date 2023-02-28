package com.laiketui.modules.backend.services.saas;

import com.laiketui.api.modules.backend.saas.SysNoticeManageService;
import com.laiketui.common.mapper.SystemTellModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 多商户控制台-公告管理
 *
 * @author Trick
 * @date 2021/2/1 10:27
 */
@Service
public class SysNoticeManageServiceImpl implements SysNoticeManageService {
    private final Logger logger = LoggerFactory.getLogger(SysNoticeManageServiceImpl.class);

    @Autowired
    private SystemTellModelMapper systemTellModelMapper;

}
