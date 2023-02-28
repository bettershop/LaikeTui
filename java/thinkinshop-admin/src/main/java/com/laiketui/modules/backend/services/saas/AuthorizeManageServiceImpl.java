package com.laiketui.modules.backend.services.saas;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.third.PublicThirdService;
import com.laiketui.api.modules.backend.saas.AuthorizeManageService;
import com.laiketui.common.mapper.ThirdMiniInfoModelMapper;
import com.laiketui.common.mapper.ThirdModelMapper;
import com.laiketui.domain.home.ThirdModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.weixin.AddThridVo;
import com.laiketui.domain.weixin.ThirdMiniInfoModel;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.okhttp.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 授权管理
 *
 * @author Trick
 * @date 2021/2/3 16:23
 */
@Service
public class AuthorizeManageServiceImpl implements AuthorizeManageService {
    private final Logger logger = LoggerFactory.getLogger(AuthorizeManageServiceImpl.class);

    @Autowired
    private ThirdMiniInfoModelMapper thirdMiniInfoModelMapper;

    @Autowired
    private PublicThirdService publicThirdService;

    @Autowired
    private ThirdModelMapper thirdModelMapper;

    @Override
    public Map<String, Object> getThirdInfo(Integer examineStatus, Integer releaseStatus, String appName, PageModel pageModel) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (StringUtils.isEmpty(appName)) {
                parmaMap.put("nick_name", appName);
            }
            if (examineStatus != null) {
                parmaMap.put("review_mark", examineStatus);
            }
            if (releaseStatus != null) {
                parmaMap.put("issue_mark", releaseStatus);
            }

            parmaMap.put("pageStart", pageModel.getPageNo());
            parmaMap.put("pageEnd", pageModel.getPageSize());

            int total = thirdMiniInfoModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> list = thirdMiniInfoModelMapper.selectDynamic(parmaMap);

            resultMap.put("total", total);
            resultMap.put("list", list);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取小程序发布列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getThirdInfo");
        }
        return resultMap;
    }

    @Override
    public boolean release(Integer id) throws LaiKeApiException {
        try {
            ThirdMiniInfoModel thirdMiniInfoModel = thirdMiniInfoModelMapper.selectByPrimaryKey(id);
            if (thirdMiniInfoModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "小程序不存在");
            }
            //获取token
            String token = publicThirdService.authorizerAccessToken(thirdMiniInfoModel.getStore_id());
            //设置业务域名
            if (publicThirdService.setServeDomain(thirdMiniInfoModel.getStore_id(), token)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "设置业务域名失败");
            }

            ThirdMiniInfoModel thirdMiniInfoModelUpdate = new ThirdMiniInfoModel();
            thirdMiniInfoModelUpdate.setId(thirdMiniInfoModel.getId());

            String url = String.format(GloabConst.WeiXinUrl.RELEASE_APP_POST, token);
            String resultJson = HttpUtils.post(url);
            Map<String, String> resultJsonMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            if ("0".equals(resultJsonMap.get("errcode"))) {
                thirdMiniInfoModelUpdate.setIssue_mark(ThirdMiniInfoModel.ISSUE_MARK_SUCCESS);
                return true;
            } else {
                thirdMiniInfoModelUpdate.setIssue_mark(ThirdMiniInfoModel.ISSUE_MARK_FAIL);
                thirdMiniInfoModelUpdate.setAuditid("");
                logger.debug("小程序发布失败:{}", resultJson);
            }
            return false;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("小程序发布 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "release");
        }
    }

    @Override
    public Map<String, Object> getThridParmate(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            ThirdModel thirdModelSave = new ThirdModel();
            resultMap.put("list", thirdModelMapper.selectOne(thirdModelSave));
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取小程序配置参数信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getThridParmate");
        }
        return resultMap;
    }

    @Override
    public boolean addThridParmate(AddThridVo vo) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(vo.getAppid())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "appid不能为空");
            }
            if (StringUtils.isEmpty(vo.getAppsecret())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "密钥不能为空");
            }
            ThirdModel thirdModelOld = null;
            if (vo.getId() != null) {
                thirdModelOld = thirdModelMapper.selectByPrimaryKey(vo.getId());
            }
            ThirdModel thirdModelSave = new ThirdModel();
            thirdModelSave.setAppid(vo.getAppid());
            thirdModelSave.setAppsecret(vo.getAppsecret());
            thirdModelSave.setCheck_token(vo.getCheckToken());
            thirdModelSave.setEncrypt_key(vo.getEncryptKey());
            thirdModelSave.setServe_domain(vo.getServeDomain());
            thirdModelSave.setWork_domain(vo.getWorkDomain());
            thirdModelSave.setRedirect_url(vo.getRedirectUrl());
            thirdModelSave.setMini_url(vo.getMiniUrl());
            thirdModelSave.setQr_code(vo.getQrCode());
            thirdModelSave.setEndurl(vo.getEndurl());

            if (thirdModelOld != null) {
                thirdModelSave.setId(thirdModelOld.getId());
                count = thirdModelMapper.updateByPrimaryKeySelective(thirdModelSave);
            } else {
                count = thirdModelMapper.insertSelective(thirdModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加/编辑小程序配置参数 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addThridParmate");
        }
    }
}
