package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.ApplicationManageService;
import com.laiketui.common.mapper.EditionModelMapper;
import com.laiketui.domain.config.EditionModel;
import com.laiketui.domain.vo.app.AppConfigInfoVo;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * app管理
 *
 * @author Trick
 * @date 2021/1/22 9:39
 */
@Service
public class ApplicationManageServiceImpl implements ApplicationManageService {
    private final Logger logger = LoggerFactory.getLogger(ApplicationManageServiceImpl.class);

    @Override
    public Map<String, Object> getVersionConfigInfo(int storeId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            EditionModel editionModel = new EditionModel();
            editionModel.setStore_id(storeId);
            editionModel = editionModelMapper.selectOne(editionModel);
            if (editionModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "版本配置信息不存在");
            }
            resultMap.put("data", editionModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取版本配置信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getVersionConfigInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addVersionConfigInfo(AppConfigInfoVo vo) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(vo.getAppName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "app名称不能为空");
            }
            if (StringUtils.isEmpty(vo.getAppVersion())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "版本号不能为空");
            } else if (!StringUtils.isInteger(vo.getAppVersion())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "版本号必须为正整数");
            }
            if (StringUtils.isEmpty(vo.getAndroidDownloadUrl()) || StringUtils.isEmpty(vo.getIosDownloadUrl())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "下载地址不能为空");
            }
            if (vo.getIsAutoUpdate() == null || vo.getIsAutoUpdate() < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "是否自动更新不能为空");
            }
            if (StringUtils.isEmpty(vo.getUpdateLogger())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "更新内容不能为空");
            }

            EditionModel editionModelOld = new EditionModel();
            editionModelOld.setStore_id(vo.getStoreId());
            editionModelOld = editionModelMapper.selectOne(editionModelOld);

            EditionModel editionModelSave = new EditionModel();
            editionModelSave.setEdition_url("");
            editionModelSave.setAppname(vo.getAppName());
            editionModelSave.setType(vo.getIsAutoUpdate());
            editionModelSave.setEdition(vo.getAppVersion());
            editionModelSave.setContent(vo.getUpdateLogger());
            editionModelSave.setIos_url(vo.getIosDownloadUrl());
            editionModelSave.setAndroid_url(vo.getAndroidDownloadUrl());

            if (editionModelOld != null) {
                if (Integer.parseInt(vo.getAppVersion()) < Integer.parseInt(editionModelOld.getEdition())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "当前版本号不能低于之前版本号");
                }
                editionModelSave.setId(editionModelOld.getId());
                count = editionModelMapper.updateByPrimaryKeySelective(editionModelSave);
            } else {
                editionModelSave.setStore_id(vo.getStoreId());
                editionModelSave.setAdd_date(new Date());
                count = editionModelMapper.insertSelective(editionModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加/编辑版本配置 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addVersionConfigInfo");
        }
    }


    @Autowired
    private EditionModelMapper editionModelMapper;


}
