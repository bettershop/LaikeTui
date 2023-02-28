package com.laiketui.modules.backend.services.systems;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.modules.backend.systems.SystemService;
import com.laiketui.common.mapper.AgreementModelMapper;
import com.laiketui.common.mapper.ConfigModelMapper;
import com.laiketui.common.mapper.SystemConfigurationModelMapper;
import com.laiketui.domain.config.AgreementModel;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.systems.SystemConfigurationModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddSystemVo;
import com.laiketui.domain.vo.systems.SetSystemVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.utils.tool.DataCheckTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统配置
 *
 * @author Trick
 * @date 2021/1/15 9:28
 */
@Service
public class SystemServiceImpl implements SystemService {
    private final Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SystemConfigurationModelMapper systemConfigurationModelMapper;

    @Override
    public Map<String, Object> getSystemIndex(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if(configModel!=null){
                configModel.setLogo(publiceService.getImgPath(configModel.getLogo(), vo.getStoreId()));
                configModel.setLogo1(publiceService.getImgPath(configModel.getLogo1(), vo.getStoreId()));
                configModel.setWx_headimgurl(publiceService.getImgPath(configModel.getWx_headimgurl(), vo.getStoreId()));
                resultMap.put("data", configModel);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取系统基本配置信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSystemIndex");
        }
        return resultMap;
    }

    @Override
    public boolean addSystemConfig(AddSystemVo vo) throws LaiKeApiException {
        try {
            int count;
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);

            ConfigModel configModelSave = new ConfigModel();
            configModelSave.setIs_register(StringUtils.toString(vo.getIsRegister()));
            configModelSave.setLogo(vo.getLogoUrl());
            configModelSave.setLogo1(vo.getWxHeader());
            configModelSave.setWx_headimgurl(vo.getWxHeader());
            configModelSave.setH5_domain(vo.getPageDomain());
            configModelSave.setMessage_day(vo.getMessageSaveDay());
            configModelSave.setExp_time(vo.getAppLoginValid());
            configModelSave.setCustomer_service(vo.getServerClient());
            configModelSave.setTencent_key(vo.getTencentKey());
            configModelSave.setIs_push(vo.getUnipush());
            configModelSave.setPush_Appkey(vo.getPushAppkey());
            configModelSave.setPush_Appid(vo.getPushAppid());
            configModelSave.setPush_MasterECRET(vo.getPushMasterEcret());
            configModelSave.setIs_Kicking(vo.getIsKicking());
            configModelSave.setIs_express(vo.getIsExpress());
            configModelSave.setExpress_key(vo.getExpressKey());
            configModelSave.setExpress_number(vo.getExpressNumber());
            configModelSave.setExpress_address(vo.getExpressAddress());

            //校验数据
            configModelSave = DataCheckTool.checkConfigDataFormate(configModelSave);

            if (configModel != null) {
                configModelSave.setIs_register(vo.getIsRegister().toString());
                configModelSave.setId(configModel.getId());
                count = configModelMapper.updateByPrimaryKeySelective(configModelSave);
            } else {
                configModelSave.setStore_id(vo.getStoreId());
                configModelSave.setIs_register(vo.getIsRegister().toString());
                count = configModelMapper.insertSelective(configModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑系统配置信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addSystemConfig");
        }
    }

    @Override
    public Map<String, Object> getSetSystem(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            SystemConfigurationModel systemConfigurationModel = new SystemConfigurationModel();
            systemConfigurationModel.setStore_id(0);
            systemConfigurationModel = systemConfigurationModelMapper.selectOne(systemConfigurationModel);
            resultMap.put("config", systemConfigurationModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取系统配置 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSetSystem");
        }
        return resultMap;
    }

    @Override
    public void setSystem(SetSystemVo vo) throws LaiKeApiException {
        try {
            int row;
            if (StringUtils.isEmpty(vo.getLogoUrl())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "登录页logo不能为空");
            }
            if (StringUtils.isEmpty(vo.getCopyrightInformation())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "版权信息不能为空");
            }
            if (StringUtils.isEmpty(vo.getRecordInformation())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "备案信息不能为空");
            }
            SystemConfigurationModel systemConfigurationOld = null;
            if (vo.getId() != null) {
                systemConfigurationOld = systemConfigurationModelMapper.selectByPrimaryKey(vo.getId());
            }
            SystemConfigurationModel systemConfigurationSave = new SystemConfigurationModel();
            systemConfigurationSave.setStore_id(vo.getStoreId());
            systemConfigurationSave.setLogo(vo.getLogoUrl());
            systemConfigurationSave.setCopyright_information(vo.getCopyrightInformation());
            systemConfigurationSave.setRecord_information(vo.getRecordInformation());
            //解码
            vo.setLinkPageJson(URLDecoder.decode(vo.getLinkPageJson(), GloabConst.Chartset.UTF_8));
            if (StringUtils.isNotEmpty(vo.getLinkPageJson())) {
                try {
                    JSON.parseObject(vo.getLinkPageJson(), new TypeReference<List<Map<String, Object>>>() {
                    });
                } catch (Exception e) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "登录链接参数错误");
                }
                systemConfigurationSave.setLink_to_landing_page(vo.getLinkPageJson());
            }
            if (systemConfigurationOld == null) {
                systemConfigurationSave.setAdd_time(new Date());
                row = systemConfigurationModelMapper.insertSelective(systemConfigurationSave);
            } else {
                systemConfigurationSave.setId(systemConfigurationOld.getId());
                row = systemConfigurationModelMapper.updateByPrimaryKeySelective(systemConfigurationSave);
            }

            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "配置失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("系统配置 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setSystem");
        }
    }

    @Override
    public Map<String, Object> getAgreementIndex(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AgreementModel agreementModel = new AgreementModel();
            if (id != null && id > 0) {
                agreementModel.setId(id);
            }
            agreementModel.setStore_id(vo.getStoreId());
            List<AgreementModel> agreementModelList = agreementModelMapper.select(agreementModel);

            resultMap.put("list", agreementModelList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取协议列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAgreementIndex");
        }
        return resultMap;
    }

    @Override
    public void addAgreement(MainVo vo, Integer id, String title, int type, String content) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(title)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "协议标题不能为空");
            }
            if (StringUtils.isEmpty(content)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "内容不能为空");
            }
            AgreementModel agreementModel = null;
            if (id != null) {
                agreementModel = agreementModelMapper.selectByPrimaryKey(id);
            }

            AgreementModel agreementModelSave = new AgreementModel();
            agreementModelSave.setName(title);
            agreementModelSave.setType(type);
            agreementModelSave.setContent(content);
            agreementModelSave.setModify_date(new Date());
            if (agreementModel != null) {
                agreementModelSave.setId(agreementModel.getId());
                count = agreementModelMapper.updateByPrimaryKeySelective(agreementModelSave);
            } else {
                AgreementModel agreementCount = new AgreementModel();
                agreementCount.setStore_id(vo.getStoreId());
                agreementCount.setType(type);
                if (agreementModelMapper.selectCount(agreementCount) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "协议已存在");
                }
                agreementModelSave.setStore_id(vo.getStoreId());
                count = agreementModelMapper.insertSelective(agreementModelSave);
            }
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑协议 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addAgreement");
        }
    }

    @Override
    public void delAgreement(MainVo vo, int id) throws LaiKeApiException {
        try {
            AgreementModel agreementModel = agreementModelMapper.selectByPrimaryKey(id);
            if (agreementModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "协议不存在");
            }
            if (agreementModelMapper.deleteByPrimaryKey(id) < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除协议 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delAgreement");
        }
    }

    @Override
    public boolean updateCommonProblem(MainVo vo, String returnProblem, String payProblem) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(returnProblem)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "售后问题不能为空");
            }
            if (StringUtils.isEmpty(payProblem)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "支付问题不能为空");
            }
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请先设置基础配置");
            }
            ConfigModel configModelUpdate = new ConfigModel();
            configModelUpdate.setId(configModel.getId());
            configModelUpdate.setPayment_issues(payProblem);
            configModelUpdate.setAfter_sales_issues(returnProblem);
            count = configModelMapper.updateByPrimaryKeySelective(configModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改常简问题 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateCommonProblem");
        }
    }

    @Override
    public boolean updateRefundService(MainVo vo, String refundPolicy, String cancelOrderno, String refundMoney, String refundExplain) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(refundPolicy)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "退货政策不能为空");
            }
            if (StringUtils.isEmpty(cancelOrderno)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "取消订单不能为空");
            }
            if (StringUtils.isEmpty(refundMoney)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "退款流程不能为空");
            }
            if (StringUtils.isEmpty(refundExplain)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "退款说明不能为空");
            }
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请先设置基础配置");
            }
            ConfigModel configModelUpdate = new ConfigModel();
            configModelUpdate.setId(configModel.getId());
            configModelUpdate.setReturn_policy(refundPolicy);
            configModelUpdate.setRefund_process(refundMoney);
            configModelUpdate.setCancellation_order(cancelOrderno);
            configModelUpdate.setRefund_instructions(refundExplain);
            count = configModelMapper.updateByPrimaryKeySelective(configModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改常简问题 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateCommonProblem");
        }
    }

    @Override
    public boolean updateBeginnerGuide(MainVo vo, String shoppingProcess, String payType) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(shoppingProcess)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "购物流程不能为空");
            }
            if (StringUtils.isEmpty(payType)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "支付方式不能为空");
            }
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请先设置基础配置");
            }
            ConfigModel configModelUpdate = new ConfigModel();
            configModelUpdate.setId(configModel.getId());
            configModelUpdate.setPayment_method(payType);
            configModelUpdate.setShopping_process(shoppingProcess);
            count = configModelMapper.updateByPrimaryKeySelective(configModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改常简问题 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateCommonProblem");
        }
    }

    @Override
    public boolean updateAboutMe(MainVo vo, String auboutMe) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(auboutMe)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "内容不能为空");
            }
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请先设置基础配置");
            }
            ConfigModel configModelUpdate = new ConfigModel();
            configModelUpdate.setId(configModel.getId());
            configModelUpdate.setAboutus(auboutMe);
            count = configModelMapper.updateByPrimaryKeySelective(configModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("修改关于我 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "updateAboutMe");
        }
    }

    @Override
    public boolean uploadImages(MainVo vo, List<MultipartFile> files) throws LaiKeApiException {
        try {
            if (files == null || files.size() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "图片不能为空");
            }
            //上传图片
            List<String> imagUrls = publiceService.uploadImage(files, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, vo.getStoreType(), vo.getStoreId());

            logger.debug("图片上传成功,上传{}张,成功{}张", files.size(), imagUrls.size());
            return imagUrls.size() > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("图片上传 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadImages");
        }
    }

    @Override
    public boolean updateWeiXinApi(MainVo vo, String appid, String appsecret) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(appid)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "小程序appid不能为空");
            }
            if (StringUtils.isEmpty(appsecret)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "小程序密钥不能为空");
            }
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "请先设置基础配置");
            }

            ConfigModel configModelUpdate = new ConfigModel();
            configModelUpdate.setAppid(appid);
            configModelUpdate.setAppsecret(appsecret);
            count = configModelMapper.updateByPrimaryKeySelective(configModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("微信小程序配置 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadWeiXinApi");
        }
    }


    @Autowired
    private PubliceService publiceService;

    @Autowired
    private ConfigModelMapper configModelMapper;

    @Autowired
    private AgreementModelMapper agreementModelMapper;
}
