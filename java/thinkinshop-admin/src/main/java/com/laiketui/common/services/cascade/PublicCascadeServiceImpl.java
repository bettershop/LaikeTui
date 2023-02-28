package com.laiketui.common.services.cascade;

import com.laiketui.common.mapper.AdminCgModelMapper;
import com.laiketui.common.mapper.MessageListModelMapper;
import com.laiketui.common.mapper.MessageModelMapper;
import com.laiketui.common.mapper.UserGradeModelMapper;
import com.laiketui.domain.config.AdminCgModel;
import com.laiketui.domain.dictionary.MessageModel;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.common.cascade.PublicCascadeService;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.consts.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共级联查询
 *
 * @author Trick
 * @date 2021/1/7 15:16
 */
@Service
public class PublicCascadeServiceImpl implements PublicCascadeService {
    private final Logger logger = LoggerFactory.getLogger(PublicCascadeServiceImpl.class);

    @Autowired
    private MessageModelMapper messageModelMapper;

    @Autowired
    private MessageListModelMapper messageListModelMapper;

    @Override
    public List<UserGradeModel> getGradeList(int storeId) throws LaiKeApiException {
        List<UserGradeModel> userGradeModelList;
        try {
            //获取会员等级下拉列表
            UserGradeModel userGradeModel = new UserGradeModel();
            userGradeModel.setStore_id(storeId);
            userGradeModelList = userGradeModelMapper.select(userGradeModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取会员等级列表 异常:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGradeList");
        }
        return userGradeModelList;
    }

    @Override
    public Map<String, Object> getSourceList(int storeId) throws LaiKeApiException {
        Map<String, Object> resultData;
        try {
            //获取来源下拉
            resultData = publicDictionaryService.getDictionaryByName("来源");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取来源列表 异常:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGradeList");
        }
        return resultData;
    }

    @Override
    public Map<String, Object> getSmsTypeList(MainVo vo, String name) throws LaiKeApiException {
        Map<String, Object> resultData = new HashMap<>(16);
        try {
            List<Map<String, Object>> dictionaryListModelList = new ArrayList<>();
            if (!StringUtils.isEmpty(name)) {
                //获取类别
                dictionaryListModelList = publicDictionaryService.getDictionaryById("短信模板类别", name);
            } else {
                dictionaryListModelList.add(publicDictionaryService.getDictionaryByName("短信模板类型"));
            }
            resultData.put("list", dictionaryListModelList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取短信类型 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSmsTypeList");
        }
        return resultData;
    }

    @Override
    public Map<String, Object> getSmsTemplateList(Integer type, Integer id) throws LaiKeApiException {
        Map<String, Object> resultData = new HashMap<>(16);
        try {
            if (type == null) {
                type = 0;
            }
            //获取短信模板 类型 0:验证码 1:短信通知
            List<MessageModel> messageListModelList;
            MessageModel messageModel = new MessageModel();
            if (id != null) {
                messageModel.setType1(id);
            } else {
                messageModel.setType(type);
            }
            messageListModelList = messageModelMapper.select(messageModel);
            resultData.put("templateList", messageListModelList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取短信模板 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSmsTemplateList");
        }
        return resultData;
    }

    @Override
    public List<Map<String, Object>> getJoinCityCounty(String groupName, int groupId) throws LaiKeApiException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            AdminCgModel adminCgModel = new AdminCgModel();
            adminCgModel.setG_ParentID(groupId);
            adminCgModel.setG_CName(groupName);
            List<AdminCgModel> adminCgModelList = adminCgModelMapper.select(adminCgModel);
            for (AdminCgModel adminCg : adminCgModelList) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("GroupID", adminCg.getGroupID());
                map.put("G_CName", adminCg.getG_CName());
                resultList.add(map);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("省市级联异常 " + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getJoinCityCounty");
        }
        return resultList;
    }

    @Autowired
    private AdminCgModelMapper adminCgModelMapper;

    @Autowired
    private UserGradeModelMapper userGradeModelMapper;

    @Autowired
    private PublicDictionaryService publicDictionaryService;
}
