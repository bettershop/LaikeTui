package com.laiketui.modules.backend.services.third;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.third.PublicThirdService;
import com.laiketui.api.modules.backend.third.WeixinManageService;
import com.laiketui.common.mapper.GuideModelMapper;
import com.laiketui.common.mapper.ThirdMiniInfoModelMapper;
import com.laiketui.common.mapper.ThirdModelMapper;
import com.laiketui.common.mapper.ThirdTemplateModelMapper;
import com.laiketui.common.services.third.dist.AesException;
import com.laiketui.common.services.third.dist.WXBizMsgCrypt;
import com.laiketui.domain.config.GuideModel;
import com.laiketui.domain.home.ThirdModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.weixin.AddWeiXinAppTemplateVo;
import com.laiketui.domain.weixin.ThirdMiniInfoModel;
import com.laiketui.domain.weixin.ThirdTemplateModel;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.okhttp.HttpUtils;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import com.laiketui.root.utils.weixin.XmlUtil;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 微信小程序管理
 *
 * @author Trick
 * @date 2021/1/19 16:32
 */
@Service
public class WeixinManageServiceImpl implements WeixinManageService {
    private final Logger logger = LoggerFactory.getLogger(WeixinManageServiceImpl.class);

    @Override
    public Map<String, Object> getWeiXinGuideImageInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            if (vo.getStoreId() > 0) {
                parmaMap.put("store_id", vo.getStoreId());
            }
            if (vo.getStoreType() > 0) {
                parmaMap.put("source", vo.getStoreType());
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> dataList = guideModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : dataList) {
                map.put("image", publiceService.getImgPath(MapUtils.getString(map, "image"), vo.getStoreId()));
            }
            int total = guideModelMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取微信引导图列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWeiXinGuideImageInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addWeiXinGuideImage(MainVo vo, Integer id, String imgUrl, Integer sort, int type) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(imgUrl)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "引导图不能为空");
            }
            GuideModel guideModelOld = null;
            if (id != null && id > 0) {
                guideModelOld = new GuideModel();
                guideModelOld.setId(id);
                guideModelOld.setStore_id(vo.getStoreId());
                guideModelOld = guideModelMapper.selectOne(guideModelOld);
                if (guideModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "引导图不存在");
                }
            }
            GuideModel guideModelSave = new GuideModel();
            guideModelSave.setImage(ImgUploadUtils.getUrlImgByName(imgUrl, true));
            guideModelSave.setSource(vo.getStoreType());
            guideModelSave.setType(type);
            if (sort == null) {
                sort = 100;
            }
            guideModelSave.setSort(sort);

            if (guideModelOld != null) {
                guideModelSave.setId(guideModelOld.getId());
                count = guideModelMapper.updateByPrimaryKeySelective(guideModelSave);
            } else {
                guideModelSave.setStore_id(vo.getStoreId());
                guideModelSave.setAdd_date(new Date());
                count = guideModelMapper.insertSelective(guideModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑微信引导图 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addWeiXinGuideImage");
        }
    }

    @Override
    public boolean delWeiXinGuideImage(MainVo vo, int id) throws LaiKeApiException {
        try {
            GuideModel guideModel = new GuideModel();
            guideModel.setId(id);
            guideModel.setStore_id(vo.getStoreId());
            guideModel = guideModelMapper.selectOne(guideModel);
            if (guideModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "引导图不存在");
            }
            return guideModelMapper.deleteByPrimaryKey(id) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除引导图 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delWeiXinGuideImage");
        }
    }

    @Override
    public Map<String, Object> getWeiXinTemplateInfo(MainVo vo, Integer id, Integer templateType, Integer isUse, String templateName) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            if (templateType != null) {
                parmaMap.put("trade_data", templateType);
            }
            if (isUse != null) {
                parmaMap.put("is_use", isUse);
            }
            if (!StringUtils.isEmpty(templateName)) {
                parmaMap.put("title", templateName);
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataList = thirdTemplateModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : dataList) {
                String imgUrl = String.valueOf(map.get("img_url"));
                imgUrl = publiceService.getImgPath(imgUrl, 0);
                map.put("img_url", imgUrl);
            }
            int total = thirdTemplateModelMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取微信小程序模板列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWeiXinTemplateInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addWeiXinTemplate(AddWeiXinAppTemplateVo vo) throws LaiKeApiException {
        try {
            int count;
            ThirdTemplateModel thirdTemplateModelOld = null;
            if (vo.getId() != null && vo.getId() > 0) {
                thirdTemplateModelOld = thirdTemplateModelMapper.selectByPrimaryKey(vo.getId());
                if (thirdTemplateModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "模板不存在");
                }
            }
            //校验数据
            if (StringUtils.isEmpty(vo.getTemplateName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "模板名称不能为空");
            } else {
                if (thirdTemplateModelOld == null || !vo.getTemplateName().equals(thirdTemplateModelOld.getTitle())) {
                    //校验模板名称
                    ThirdTemplateModel thirdTemplateModel = new ThirdTemplateModel();
                    thirdTemplateModel.setTitle(vo.getTemplateName());
                    count = thirdTemplateModelMapper.selectCount(thirdTemplateModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "模板名称已存在");
                    }
                }
            }
            if (vo.getIndustry() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "行业不能为空");
            }
            if (StringUtils.isEmpty(vo.getTemplateId())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "小程序模板id不能为空");
            }
            if (StringUtils.isEmpty(vo.getText())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "模板简介不能为空");
            }
            ThirdTemplateModel thirdTemplateModelSave = new ThirdTemplateModel();
            thirdTemplateModelSave.setLk_desc(vo.getText());
            thirdTemplateModelSave.setUpdate_time(new Date());
            thirdTemplateModelSave.setTitle(vo.getTemplateName());
            thirdTemplateModelSave.setTemplate_id(vo.getTemplateId());
            thirdTemplateModelSave.setTrade_data(vo.getIndustry().toString());
            thirdTemplateModelSave.setImg_url(ImgUploadUtils.getUrlImgByName(vo.getTemplateImageUrl()));

            if (thirdTemplateModelOld != null) {
                thirdTemplateModelSave.setId(thirdTemplateModelOld.getId());
                count = thirdTemplateModelMapper.updateByPrimaryKeySelective(thirdTemplateModelSave);
            } else {
                count = thirdTemplateModelMapper.insertSelective(thirdTemplateModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加/编辑小程序模板 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addWeiXinTemplate");
        }
    }

    @Override
    public boolean setUseWeiXinTemplate(int id) throws LaiKeApiException {
        try {
            ThirdTemplateModel thirdTemplateModel = thirdTemplateModelMapper.selectByPrimaryKey(id);
            if (thirdTemplateModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "模板不存在");
            }
            ThirdTemplateModel thirdTemplateModelUpdate = new ThirdTemplateModel();
            thirdTemplateModelUpdate.setId(id);
            int isUse = ThirdTemplateModel.IS_USE_OPEN;
            if (ThirdTemplateModel.IS_USE_OPEN.equals(thirdTemplateModel.getIs_use())) {
                isUse = ThirdTemplateModel.IS_USE_STOP;
            }
            thirdTemplateModelUpdate.setIs_use(isUse);
            int count = thirdTemplateModelMapper.updateByPrimaryKeySelective(thirdTemplateModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("停用/启用 小程序模板 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "stopWeiXinTemplate");
        }
    }

    @Override
    public boolean delWeiXinTemplate(int id) throws LaiKeApiException {
        try {
            ThirdTemplateModel thirdTemplateModel = thirdTemplateModelMapper.selectByPrimaryKey(id);
            if (thirdTemplateModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "模板不存在");
            }
            int count = thirdTemplateModelMapper.deleteByPrimaryKey(id);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除小程序模板 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delWeiXinTemplate");
        }
    }

    @Override
    public Map<String, Object> getPreAuthorizationCode(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            ThirdModel thirdTemplateModel = thirdModelMapper.selectByPrimaryKey(1);
            if (thirdTemplateModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "数据不存在");
            }
            //是否授权
            ThirdMiniInfoModel thirdMiniInfoModel = new ThirdMiniInfoModel();
            thirdMiniInfoModel.setStore_id(vo.getStoreId());
            thirdMiniInfoModel = thirdMiniInfoModelMapper.selectOne(thirdMiniInfoModel);
            if (thirdMiniInfoModel != null) {
                //已授权
                resultMap.put("data", thirdMiniInfoModel);
            } else {
                //未授权,引导授权url
                String url = String.format(GloabConst.WeiXinUrl.AUTHORIZATION_TO_THIRD_PARTY, thirdTemplateModel.getAppid(), publicThirdService.getPreAuthorizationCode(thirdTemplateModel.getAppid()), thirdTemplateModel.getRedirect_url(), 2);
                resultMap.put("url", url);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取微信授权信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getPreAuthorizationCode");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getAuthorizationAppInfo(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //小程序基本信息及发布状态
            ThirdMiniInfoModel thirdMiniInfoModel = new ThirdMiniInfoModel();
            thirdMiniInfoModel.setStore_id(vo.getStoreId());
            thirdMiniInfoModel = thirdMiniInfoModelMapper.selectOne(thirdMiniInfoModel);
            if (thirdMiniInfoModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "未授权,请返回授权页");
            }
            //获取审核状态 当 status = 1 时，返回的拒绝原因; status = 4 时，返回的延后原因
            resultMap.putAll(publicThirdService.getAuditstatus(vo.getStoreId(), thirdMiniInfoModel.getAuditid()));

            resultMap.put("nick_name", thirdMiniInfoModel.getNick_name());
            resultMap.put("head_img", thirdMiniInfoModel.getHead_img());
            resultMap.put("mini_id", thirdMiniInfoModel.getId());
            resultMap.put("issue_mark", thirdMiniInfoModel.getIssue_mark());
            resultMap.put("qr_code", thirdMiniInfoModel.getQrcode_url());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取已授权的app信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAuthorizationAppInfo");
        }
        return resultMap;
    }

    @Override
    public String ticketCallBack(HttpServletRequest request, HttpServletResponse response) throws LaiKeApiException {
        try {
            logger.debug("开始更新 ComponentVerifyTicket");
            //获取授权信息
            ThirdModel thirdModel = thirdModelMapper.selectByPrimaryKey(1);
            if (thirdModel == null) {
                logger.error("微信回调-获取验证票据失败:授权信息不存在");
                return null;
            }
            //时间戳
            String timetamp = request.getParameter("timetamp");
            //随机串
            String nonce = request.getParameter("nonce");
            //签名串
            String verifyMsgSig = request.getParameter("msg_signature");
            //密文
            String echoStr = request.getParameter("postdata");

            //装载参数
            String appid = thirdModel.getAppid();
            String token = thirdModel.getCheck_token();
            String encryptKey = thirdModel.getEncrypt_key();
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token,
                    encryptKey, appid);
            try {
                String resultStr = wxcpt.verifyUrl(verifyMsgSig, timetamp, nonce, echoStr);
                System.out.println(resultStr);
                Map<String, String> resultBodyMap = XmlUtil.xmlToMap(resultStr);
                if (resultBodyMap.containsKey("ComponentVerifyTicket")) {
                    ThirdModel thirdModelUpdate = new ThirdModel();
                    thirdModelUpdate.setId(thirdModel.getId());
                    thirdModelUpdate.setTicket(resultBodyMap.get("ComponentVerifyTicket"));
                    thirdModelUpdate.setTicket_time(new Date());
                    int count = thirdModelMapper.updateByPrimaryKeySelective(thirdModelUpdate);
                    logger.debug("更新ComponentVerifyTicket 状态{}", count > 0);
                    //更新component_access_token
                    publicThirdService.componentAccessToken(thirdModelUpdate.getTicket());
                    return "success";
                }
            } catch (AesException a) {
                logger.error("解密ComponentVerifyTicket失败 errorCode:{}", a.getCode());
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ticket回调 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "ticketCallBack");
        }
        return null;
    }

    @Override
    public String revokeExamine(int storeId) throws LaiKeApiException {
        try {
            String authorizerAccessToken = publicThirdService.authorizerAccessToken(storeId);
            String url = String.format(GloabConst.WeiXinUrl.UNDOCODEAUDIT_GET, authorizerAccessToken);

            String resultJson = HttpUtils.get(url);
            Map<String, String> resultMap = JSON.parseObject(resultJson, new TypeReference<Map<String, String>>() {
            });
            if ("0".equals(resultMap.get("errcode"))) {
                return GloabConst.ManaValue.MANA_VALUE_SUCCESS;
            } else {
                return resultJson;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("撤销审核 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "revokeExamine");
        }
    }

    @Override
    public String submitAppExamine(int storeId, String templateId) throws LaiKeApiException {
        try {
            //获取小程序信息
            ThirdModel thirdModel = thirdModelMapper.selectByPrimaryKey(1);
            if (thirdModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "小程序不存在");
            }
            //小程序接口请求地址
            String apiUrl = thirdModel.getMini_url();
            //客服接口请求地址
            String kefuUrl = thirdModel.getKefu_url();
            //获取授权的小程序appid
            ThirdMiniInfoModel thirdMiniInfoModel = new ThirdMiniInfoModel();
            thirdMiniInfoModel.setStore_id(storeId);
            thirdMiniInfoModel = thirdMiniInfoModelMapper.selectOne(thirdMiniInfoModel);
            if (thirdMiniInfoModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "小程序未审核");
            }
            //appid
            String authorizerAppid = thirdMiniInfoModel.getAuthorizer_appid();
            //上传代码
            String submitCodeResult = publicThirdService.uploadCode(storeId, templateId, authorizerAppid, apiUrl, kefuUrl);
            if (!GloabConst.ManaValue.MANA_VALUE_SUCCESS.equals(submitCodeResult)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "提交审核失败，请查看审核日志");
            }
            //获取授权token
            String authorToken = publicThirdService.authorizerAccessToken(storeId);
            //设置服务器域名
            if (!publicThirdService.setServeDomain(storeId, authorToken)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "设置服务器域名失败");
            }
            //提交审核
            if (!publicThirdService.submitReview(storeId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.API_OPERATION_FAILED, "审核提交失败");
            }

            return GloabConst.ManaValue.MANA_VALUE_SUCCESS;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("提交小程序审核 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "submitAppExamine");
        }
    }

    @Override
    public boolean delThirdMiniApp(int id) throws LaiKeApiException {
        try {
            ThirdMiniInfoModel thirdMiniInfoModel = thirdMiniInfoModelMapper.selectByPrimaryKey(id);
            if (thirdMiniInfoModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "小程序不存在");
            }
            return thirdMiniInfoModelMapper.deleteByPrimaryKey(id) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除小程序 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "revokeExamine");
        }
    }

    @Override
    public String downQrcode(int storeId, int width, int type) throws LaiKeApiException {
        try {
            InputStream stream = null;
            String imageUrl;
            try {
                //获取token
                String token = publicThirdService.authorizerAccessToken(storeId);
                //图片名称
                String fileName = BuilderIDTool.getGuid() + ".jpg";
                List<MultipartFile> fileList = new ArrayList<>();

                //装载图片参数
                Map<String, Object> imageParmaMap = new HashMap<>(16);
                imageParmaMap.put("scene", fileName);
                imageParmaMap.put("width", width);
                imageParmaMap.put("path", "pages/index/index");
                String postDataJson = JSON.toJSONString(imageParmaMap);
                //请求url
                String apiUrl = String.format(GloabConst.WeiXinUrl.SHARE_C_GRCODE_GET_URL, token);
                if (type == 2) {
                    //小程序码
                    apiUrl = String.format(GloabConst.WeiXinUrl.SHARE_A_GRCODE_GET_URL, token);
                } else if (type == 3) {
                    apiUrl = String.format(GloabConst.WeiXinUrl.EXPERIENCE_QRCODE_GET_URL, token);
                }
                stream = HttpUtils.postFile(apiUrl, postDataJson);
                MultipartFile file = new MockMultipartFile(fileName, fileName, MediaType.IMAGE_JPEG_VALUE, stream);
                fileList.add(file);
                List<String> imageUrlList = publiceService.uploadImage(fileList, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_002), storeId);
                imageUrl = imageUrlList.get(0);
                logger.debug("生成小程序二维码路径:" + imageUrl);
            } catch (LaiKeApiException l) {
                throw l;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("获取小程序二维码 异常:" + e.getMessage());
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "downQrcode");
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException io) {
                        io.printStackTrace();
                        logger.error("流关闭异常 " + io.getMessage());
                    }
                }
            }
            return imageUrl;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("下载普通二维码 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "downPtQrcode");
        }
    }


    @Autowired
    private PublicThirdService publicThirdService;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private GuideModelMapper guideModelMapper;

    @Autowired
    private ThirdTemplateModelMapper thirdTemplateModelMapper;

    @Autowired
    private ThirdModelMapper thirdModelMapper;

    @Autowired
    private ThirdMiniInfoModelMapper thirdMiniInfoModelMapper;

}
