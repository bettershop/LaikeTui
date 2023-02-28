package com.laiketui.modules.backend.services.saas;

import com.laiketui.api.modules.backend.saas.ImageManageService;
import com.laiketui.common.mapper.ConfigModelMapper;
import com.laiketui.common.mapper.UploadConfigModelMapper;
import com.laiketui.domain.upload.UploadConfigModel;
import com.laiketui.domain.vo.admin.image.AddImageConfigVo;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 图片管理
 *
 * @author Trick
 * @date 2021/1/29 17:54
 */
@Service
public class ImageManageServiceImpl implements ImageManageService {
    private final Logger logger = LoggerFactory.getLogger(ImageManageServiceImpl.class);

    @Autowired
    private UploadConfigModelMapper uploadConfigModelMapper;

    @Override
    public Map<String, Object> getImageConfigInfo(int type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            UploadConfigModel uploadConfigModel = new UploadConfigModel();
            uploadConfigModel.setUpserver(String.valueOf(type));
            List<UploadConfigModel> uploadConfigModels = uploadConfigModelMapper.select(uploadConfigModel);

            resultMap.put("data", uploadConfigModels);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取上传图片配置 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getImageConfigInfo");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addImageConfigInfo(AddImageConfigVo vo) throws LaiKeApiException {
        try {
            int row = 0;
            UploadConfigModel uploadConfigModel = new UploadConfigModel();
            uploadConfigModel.setUpserver(vo.getType() + "");
            if (uploadConfigModelMapper.selectCount(uploadConfigModel) > 0) {
                //修改,删除之前的配置
                uploadConfigModelMapper.delete(uploadConfigModel);
            }
            //新增
            switch (vo.getType() + "") {
                case GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST:
                    for (int i = 0; i < 2; i++) {
                        UploadConfigModel uploadConfigModelSave = new UploadConfigModel();
                        uploadConfigModelSave.setType("本地");
                        uploadConfigModelSave.setUpserver(GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST);
                        switch (i) {
                            case 0:
                                uploadConfigModelSave.setAttr("uploadImg_domain");
                                if (StringUtils.isEmpty(vo.getUploadImgDomain())) {
                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写图片上传域名");
                                }
                                uploadConfigModelSave.setAttrvalue(vo.getUploadImgDomain());
                                break;
                            case 1:
                                uploadConfigModelSave.setAttr("uploadImg");
                                if (StringUtils.isEmpty(vo.getUploadImg())) {
                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写本地存储位置");
                                }
                                uploadConfigModelSave.setAttrvalue(vo.getUploadImg());
                                break;
                            default:
                                break;
                        }
                        row = uploadConfigModelMapper.insertSelective(uploadConfigModelSave);
                        if (row < 1) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "服务器繁忙");
                        }
                    }
                    break;
                case GloabConst.UploadConfigConst.IMG_UPLOAD_OSS:
                    for (int i = 0; i < 6; i++) {
                        UploadConfigModel uploadConfigModelSave = new UploadConfigModel();
                        uploadConfigModelSave.setType("阿里云oos");
                        uploadConfigModelSave.setUpserver(GloabConst.UploadConfigConst.IMG_UPLOAD_OSS);
                        switch (i) {
                            case 0:
                                uploadConfigModelSave.setAttr("Bucket");
                                if (StringUtils.isEmpty(vo.getOssbucket())) {
                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写存储空间名称");
                                }
                                uploadConfigModelSave.setAttrvalue(vo.getOssbucket());
                                break;
                            case 1:
                                uploadConfigModelSave.setAttr("Endpoint");
                                if (StringUtils.isEmpty(vo.getOssendpoint())) {
                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写Endpoint");
                                }
                                uploadConfigModelSave.setAttrvalue(vo.getOssendpoint());
                                break;
                            case 2:
                                uploadConfigModelSave.setAttr("isopenzdy");
                                uploadConfigModelSave.setAttrvalue(vo.getIsOpenDiyDomain() + "");
                                break;
                            case 3:
                                uploadConfigModelSave.setAttr("AccessKeyID");
                                if (StringUtils.isEmpty(vo.getOssaccesskey())) {
                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写Access Key ID");
                                }
                                uploadConfigModelSave.setAttrvalue(vo.getOssaccesskey());
                                break;
                            case 4:
                                uploadConfigModelSave.setAttr("AccessKeySecret");
                                if (StringUtils.isEmpty(vo.getOssaccesssecret())) {
                                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写Access Key Secret");
                                }
                                uploadConfigModelSave.setAttrvalue(vo.getOssaccesssecret());
                                break;
                            case 5:
                                uploadConfigModelSave.setAttr("imagestyle");
                                uploadConfigModelSave.setAttrvalue(vo.getOssimgstyleapi());
                                break;
                            default:
                                break;
                        }
                        row = uploadConfigModelMapper.insertSelective(uploadConfigModelSave);
                        if (row < 1) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "服务器繁忙");
                        }
                    }
                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            configModelMapper.updateConfigAll(vo.getType());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑图片上传配置 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addImageConfigInfo");
        }
    }

    @Autowired
    private ConfigModelMapper configModelMapper;
}
