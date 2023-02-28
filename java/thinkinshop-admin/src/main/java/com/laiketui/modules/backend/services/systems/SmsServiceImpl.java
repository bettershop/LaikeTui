package com.laiketui.modules.backend.services.systems;

import com.alibaba.fastjson.JSON;
import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.modules.backend.systems.SmsService;
import com.laiketui.common.mapper.MessageConfigModelMapper;
import com.laiketui.common.mapper.MessageListModelMapper;
import com.laiketui.common.mapper.MessageModelMapper;
import com.laiketui.domain.config.MessageConfigModel;
import com.laiketui.domain.config.MessageListModel;
import com.laiketui.domain.dictionary.MessageModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddMessageListVo;
import com.laiketui.domain.vo.systems.AddMessageVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.SerializePhpUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 短信配置
 *
 * @author Trick
 * @date 2021/1/15 9:28
 */
@Service
public class SmsServiceImpl implements SmsService {
    private final Logger logger = LoggerFactory.getLogger(SmsServiceImpl.class);

    @Autowired
    private PublicDictionaryService publicDictionaryService;

    @Override
    public Map<String, Object> getSmsInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("storeId", vo.getStoreId());
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> dataList = messageListModelMapper.selectMessageListInfo(parmaMap);
            int total = messageListModelMapper.countMessageListInfo(parmaMap);

            //只有通知类型是动态的
            for (Map<String, Object> map : dataList) {
                int type = (int) map.get("type");
                String typeName = "验证码";
                if (type == 1) {
                    typeName = "短信通知";
                    String text = map.get("content").toString();
                    String tempLateText = map.get("content1").toString();
                    Map<String, String> templateParma = DataUtils.cast(SerializePhpUtils.getUnserializeObj(text, Map.class));
                    if (templateParma != null) {
                        int i = 0;
                        Object[] strList = new Object[templateParma.size()];
                        for (String key : templateParma.keySet()) {
                            strList[i] = templateParma.get(key);
                            i++;
                        }
                        String str = StringUtils.replace(tempLateText, "${", "}", strList);
                        map.put("content1", str);
                    }
                }
                map.put("add_time", DateUtil.dateFormate(MapUtils.getString(map,"add_time"), GloabConst.TimePattern.YMDHMS));
                map.put("typeName", typeName);
            }

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取短信列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSmsInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addMessageList(AddMessageListVo vo) throws LaiKeApiException {
        try {
            int count;
            if (vo.getType() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信类别不能为空");
            }
            if (vo.getCategory() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信类型不能为空");
            }
            if (vo.getSmsTemplateId() == null || vo.getSmsTemplateId() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信模板id不能为空");
            }
            if (StringUtils.isEmpty(vo.getTemplateStr())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信模板内容不能为空");
            }
            //获取模板信息
            MessageModel messageModel = new MessageModel();
            messageModel.setId(vo.getSmsTemplateId());
            messageModel.setStore_id(vo.getStoreId());
            messageModel = messageModelMapper.selectOne(messageModel);
            if (messageModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "模板不存在");
            }
            //是否需要校验模板重复标识,修改的时候修改自身则不做校验
            boolean isCheck = true;
            //模板配置是否存在,存在则修改，不存在则添加
            MessageListModel messageListModelOld = new MessageListModel();
            messageListModelOld.setId(vo.getId());
            messageListModelOld.setStore_id(vo.getStoreId());
            messageListModelOld.setType(vo.getType());
            messageListModelOld.setType1(vo.getCategory());
            messageListModelOld.setTemplate_id(messageModel.getId());
            messageListModelOld = messageListModelMapper.selectOne(messageListModelOld);
            if (messageListModelOld != null) {
                if (messageListModelOld.getType().equals(vo.getType()) && messageListModelOld.getType1().equals(vo.getCategory())) {
                    isCheck = false;
                }
            }

            if (isCheck) {
                MessageListModel messageListModel = new MessageListModel();
                messageListModel.setStore_id(vo.getStoreId());
                messageListModel.setType(vo.getType());
                messageListModel.setType1(vo.getCategory());
                if (messageListModelMapper.selectCount(messageListModel) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "该类短信模板已添加,请勿重复添加");
                }
            }

            MessageListModel messageListModelSave = new MessageListModel();
            messageListModelSave.setType(vo.getType());
            messageListModelSave.setType1(vo.getCategory());
            messageListModelSave.setTemplate_id(vo.getSmsTemplateId());
            //获取模板参数列表 (尊敬的用户，店铺${store}处理退款订单${orderno}操作成功，退款金额：${amount})
            List<String> configParamLists = StringUtils.replace(messageModel.getContent(), "${", "}");
            //当前参数列表
            List<String> templateParamList = Arrays.asList(vo.getTemplateStr().split(","));
            if (configParamLists.size() != templateParamList.size()) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "模板参数数量不匹配,期望数量:" + configParamLists.size());
            }
            //存储键值对,键为占位符,值为占位符的值 a:3:{s:5:"store";s:8:"值";s:7:"orderno";s:10:"值";s:6:"amount";s:9:"值";}
            Map<String, String> templateParamMap = new HashMap<>(16);
            for (String parmaKey : configParamLists) {
                //占位符key
                String configParma = StringUtils.replaceSpecialStr(parmaKey, "");
//                templateParamMap.put(configParma, templateParamList.get(i));
                templateParamMap.put(configParma, "");
            }
            logger.debug("短信参数:{}", JSON.toJSONString(templateParamMap));
            String serialStr = SerializePhpUtils.JavaSerializeByPhp(templateParamMap);
            messageListModelSave.setContent(serialStr);
            if (messageListModelOld != null) {
                messageListModelSave.setId(messageListModelOld.getId());
                count = messageListModelMapper.updateByPrimaryKeySelective(messageListModelSave);
            } else {
                messageListModelSave.setStore_id(vo.getStoreId());
                messageListModelSave.setAdd_time(new Date());
                messageListModelSave.setAdmin_id("admin");
                count = messageListModelMapper.insertSelective(messageListModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑短信列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addMessageList");
        }
    }

    @Override
    public boolean delMessageList(MainVo vo, int id) throws LaiKeApiException {
        try {
            MessageListModel messageListModel = new MessageListModel();
            messageListModel.setStore_id(vo.getStoreId());
            messageListModel.setId(id);
            int count = messageListModelMapper.selectCount(messageListModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "短信配置模板不存在");
            }
            count = messageListModelMapper.deleteByPrimaryKey(id);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除短信配置信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delMessageList");
        }
    }

    @Override
    public Map<String, Object> getSmsTemplateInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> dataList = messageModelMapper.selectDynamic(parmaMap);
            int total = messageModelMapper.countDynamic(parmaMap);
            for (Map map: dataList) {
                map.put("add_time", DateUtil.dateFormate(MapUtils.getString(map,"add_time"), GloabConst.TimePattern.YMDHMS));
            }
            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取短信模板信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSmsTemplateInfo");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMessage(AddMessageVo vo, String phone) throws LaiKeApiException {
        try {
            int count;
            MessageModel messageModelSave = new MessageModel();
            if (vo.getType() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信类别不能为空");
            }
            if (vo.getCategory() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信类型不能为空");
            }
            if (StringUtils.isEmpty(vo.getSignName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信签名不能为空");
            }
            if (StringUtils.isEmpty(vo.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信模板名称不能为空");
            }
            if (StringUtils.isEmpty(vo.getCode())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信代码不能为空");
            }
            if (StringUtils.isEmpty(phone)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信接收号码不能为空");
            }
            List<String> parmaList;
            if (StringUtils.isEmpty(vo.getContent())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信内容不能为空");
            } else {
                parmaList = StringUtils.replace(vo.getContent(), "${", "}");
                if (parmaList.size() < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "模板至少需要一个参数不正确");
                }
            }

            //获取模板信息
            MessageModel messageModelOld = null;
            if (vo.getId() != null && vo.getId() > 0) {
                messageModelOld = new MessageModel();
                messageModelOld.setId(vo.getId());
                messageModelOld.setType(vo.getType());
                messageModelOld.setType1(vo.getCategory());
                messageModelOld = messageModelMapper.selectOne(messageModelOld);
                if (messageModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "短信模板不存在");
                }
                if (!messageModelOld.getTemplateCode().equals(vo.getCode())) {
                    //验证模板是否存在
                    MessageModel messageModel = new MessageModel();
                    messageModel.setStore_id(vo.getStoreId());
                    messageModel.setTemplateCode(vo.getCode());
                    count = messageModelMapper.selectCount(messageModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信模板已存在");
                    }
                }
            }

            messageModelSave.setType(vo.getType());
            messageModelSave.setName(vo.getName());
            messageModelSave.setType1(vo.getCategory());
            messageModelSave.setContent(vo.getContent());
            messageModelSave.setSignName(vo.getSignName());
            messageModelSave.setTemplateCode(vo.getCode());

            //验证名称
            if (messageModelOld == null || !messageModelOld.getName().equals(vo.getName())) {
                //验证模板名称
                MessageModel messageModel = new MessageModel();
                messageModel.setStore_id(vo.getStoreId());
                messageModel.setName(vo.getName());
                count = messageModelMapper.selectCount(messageModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "短信名称已存在");
                }
            }
            //验证模板
            Map<String, String> smsParmaMap = new HashMap<>(16);
            //装载参数
            for (String parma : parmaList) {
                String confgParma = StringUtils.replaceSpecialStr(parma, "");
                String text = "";
                if (vo.getType().equals(1)) {
                    //通知
                    text = parma;
                } else {
                    //验证码
                    text = BuilderIDTool.getNext(BuilderIDTool.Type.NUMBER, 6);
                }
                smsParmaMap.put(confgParma, text);
            }
            if (!publiceService.sendValidatorSmsTemplate(vo.getStoreId(), phone, messageModelSave.getTemplateCode(), vo.getSignName(), smsParmaMap)) {
                logger.debug("短信模板添加失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "短信模板添加失败");
            }

            if (messageModelOld != null) {
                messageModelSave.setId(vo.getId());
                count = messageModelMapper.updateByPrimaryKeySelective(messageModelSave);
            } else {
                messageModelSave.setStore_id(vo.getStoreId());
                messageModelSave.setAdd_time(new Date());
                count = messageModelMapper.insertSelective(messageModelSave);
            }

            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑短信模板 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addMessage");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delMessage(MainVo vo, int id) throws LaiKeApiException {
        try {
            MessageModel messageModel = new MessageModel();
            messageModel.setId(id);
            messageModel.setStore_id(vo.getStoreId());
            int count = messageModelMapper.selectCount(messageModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "模板不存在");
            }
            //是否正在使用
            MessageListModel messageListModel = new MessageListModel();
            messageListModel.setStore_id(vo.getStoreId());
            messageListModel.setTemplate_id(id);
            count = messageListModelMapper.selectCount(messageListModel);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "该模板正在使用");
            }

            //删除被引用的自定义配置
            count = messageModelMapper.deleteByPrimaryKey(id);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "删除失败");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除短信模板 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delMessage");
        }
    }

    @Override
    public Map<String, Object> getTemplateConfigInfo(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            MessageConfigModel messageConfigModel = new MessageConfigModel();
            messageConfigModel.setStore_id(vo.getStoreId());
            messageConfigModel = messageConfigModelMapper.selectOne(messageConfigModel);

            resultMap.put("data", messageConfigModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取模板全局配置信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getTemplateConfigInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addTemplateConfig(MainVo vo, String key, String secret, String signName) throws LaiKeApiException {
        try {
            int count;
            MessageConfigModel messageConfigModel = new MessageConfigModel();
            messageConfigModel.setStore_id(vo.getStoreId());
            messageConfigModel = messageConfigModelMapper.selectOne(messageConfigModel);

            MessageConfigModel messageConfigModelSave = new MessageConfigModel();
            messageConfigModelSave.setAccessKeyId(key);
            messageConfigModelSave.setSignName(signName);
            messageConfigModelSave.setAccessKeySecret(secret);
            if (messageConfigModel != null) {
                messageConfigModelSave.setId(messageConfigModel.getId());
                count = messageConfigModelMapper.updateByPrimaryKeySelective(messageConfigModelSave);
            } else {
                messageConfigModelSave.setStore_id(vo.getStoreId());
                messageConfigModelSave.setAdd_time(new Date());
                count = messageConfigModelMapper.insertSelective(messageConfigModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑短信模板 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addTemplateConfig");
        }
    }


    @Autowired
    private PubliceService publiceService;

    @Autowired
    private MessageListModelMapper messageListModelMapper;

    @Autowired
    private MessageModelMapper messageModelMapper;

    @Autowired
    private MessageConfigModelMapper messageConfigModelMapper;
}
