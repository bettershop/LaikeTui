package com.laiketui.modules.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.modules.backend.PaymentManageService;
import com.laiketui.common.mapper.ConfigModelMapper;
import com.laiketui.common.mapper.PaymentConfigModelMapper;
import com.laiketui.common.mapper.PaymentModelMapper;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.payment.PaymentConfigModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import com.laiketui.root.utils.tool.SerializePhpUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 支付管理
 *
 * @author Trick
 * @date 2021/7/15 15:22
 */
@Service
public class PaymentManageServiceImpl implements PaymentManageService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private PaymentModelMapper paymentModelMapper;

    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;

    @Autowired
    private ConfigModelMapper configModelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${node.wx-certp12-path}")
    private String certBasePath;

    @Override
    public Map<String, Object> index(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());

            List<Map<String, Object>> list = paymentModelMapper.getPaymentConfigInfo(parmaMap);
            int total = paymentModelMapper.countPaymentConfigInfo(parmaMap);

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品类别以及品牌信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> paymentParmaInfo(MainVo vo, int payId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //支付宝回调
            String notifyUrl = "";
            int status = 0;

            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            PaymentConfigModel paymentConfigModel = new PaymentConfigModel();
            paymentConfigModel.setPid(payId);
            paymentConfigModel.setStore_id(vo.getStoreId());
            paymentConfigModel = paymentConfigModelMapper.selectOne(paymentConfigModel);

            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "数据错误");
            }

            if (paymentConfigModel != null && StringUtils.isNotEmpty(paymentConfigModel) || payId == 3) {
                Map<String, Object> parma = new HashMap<>(16);
                if (paymentConfigModel != null) {
                    status = paymentConfigModel.getStatus();
                    try {
                        parma = JSON.parseObject(paymentConfigModel.getConfig_data(), new TypeReference<Map<String, Object>>() {
                        });
                    } catch (Exception e1) {
                        logger.debug("非json数据,正在尝试反php序列化");
                        try {
                            parma = DataUtils.cast(SerializePhpUtils.getUnserializeObj(paymentConfigModel.getConfig_data(), Map.class));
                        } catch (Exception e2) {
                            logger.debug("反php序列化失败");
                            parma = new HashMap<>(16);
                        }
                    }
                    if (parma == null) {
                        parma = new HashMap<>(16);
                    }
                }
                parma.put("status", status);

                String certPem = MapUtils.getString(parma,"cert_pem");
                if(!StringUtils.isEmpty(certPem)){
                    parma.put("cert_pem",certPem.replaceAll("%2B","\\+"));
                }
                String keyPem = MapUtils.getString(parma,"key_pem");
                if(!StringUtils.isEmpty(keyPem)){
                    parma.put("key_pem",keyPem.replaceAll("%2B","\\+"));
                }


                String encryptKey = MapUtils.getString(parma,"encryptKey");
                if(!StringUtils.isEmpty(encryptKey)){
                    parma.put("encryptKey",encryptKey.replaceAll("%2B","\\+"));
                }

                String rsaPrivateKey = MapUtils.getString(parma,"rsaPrivateKey");
                if(!StringUtils.isEmpty(rsaPrivateKey)){
                    parma.put("rsaPrivateKey",rsaPrivateKey.replaceAll("%2B","\\+"));
                }

                String alipayrsaPublicKey = MapUtils.getString(parma,"alipayrsaPublicKey");
                if(!StringUtils.isEmpty(alipayrsaPublicKey)){
                    parma.put("alipayrsaPublicKey",alipayrsaPublicKey.replaceAll("%2B","\\+"));
                }
                resultMap.put("config", parma);

            }
            resultMap.put("mrnotify_url", notifyUrl);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取支付参数 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "paymentParmaInfo");
        }
        return resultMap;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPaymentParma(MainVo vo, String json, Integer id, Integer status) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            PaymentConfigModel paymentConfigOld = null;
            PaymentConfigModel paymentConfigSave = new PaymentConfigModel();
            if (id != null) {
                paymentConfigOld = new PaymentConfigModel();
                paymentConfigOld.setStore_id(vo.getStoreId());
                paymentConfigOld.setPid(id);
                paymentConfigOld = paymentConfigModelMapper.selectOne(paymentConfigOld);
            }
            paymentConfigSave.setStatus(status);
            paymentConfigSave.setConfig_data(json);

            if (paymentConfigOld == null) {
                paymentConfigSave.setStore_id(vo.getStoreId());
                row = paymentConfigModelMapper.insertSelective(paymentConfigSave);
            } else {
                paymentConfigSave.setId(paymentConfigOld.getId());
                row = paymentConfigModelMapper.updateByPrimaryKeySelective(paymentConfigSave);
            }

            //

            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "配置失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("支付参数修改 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setPaymentParma");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setPaymentSwitch(MainVo vo, int id) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            PaymentConfigModel paymentConfigModel = new PaymentConfigModel();
            paymentConfigModel.setStore_id(vo.getStoreId());
            paymentConfigModel.setPid(id);
            paymentConfigModel = paymentConfigModelMapper.selectOne(paymentConfigModel);
            if (paymentConfigModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "支付方式不存在");
            }
            int isOpen = 1;
            if (paymentConfigModel.getStatus() == isOpen) {
                isOpen = 0;
            }
            PaymentConfigModel paymentConfigUpdate = new PaymentConfigModel();
            paymentConfigUpdate.setId(paymentConfigModel.getId());
            paymentConfigUpdate.setStatus(isOpen);
            //开关参数 是否显示 0否 1是
            row = paymentConfigModelMapper.updateByPrimaryKeySelective(paymentConfigUpdate);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("支付配置开关 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setPaymentSwitch");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> uploadCertP12(UploadFileVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            //上传certp12文件保存位置
            StringBuilder saveStorePath = new StringBuilder(certBasePath).append(SplitUtils.FXG).append(vo.getStoreId()).append(SplitUtils.FXG);
            File saveStoreFile = new File(saveStorePath.toString());

            if (!saveStoreFile.exists()) {
                saveStoreFile.mkdir();
            }

            saveStorePath = saveStorePath.append(SplitUtils.FXG).append(BuilderIDTool.getGuid());
            File saveFile = new File(saveStorePath.toString());
            if (!saveFile.exists()) {
                saveFile.mkdir();
            }

            File p12 = new File(saveStorePath.append(SplitUtils.FXG).append("apiclient_cert.p12").toString());
            for (MultipartFile file : vo.getImage()) {
                file.transferTo(p12);
            }
            resultMap.put("savePath", p12.getPath());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("文件上传异常{}", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadImage");
        }
        return resultMap;
    }
}