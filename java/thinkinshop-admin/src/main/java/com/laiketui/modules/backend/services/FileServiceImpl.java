package com.laiketui.modules.backend.services;

import com.laiketui.api.common.PubliceService;
import com.laiketui.api.modules.backend.FileService;
import com.laiketui.common.mapper.FilesRecordModelMapper;
import com.laiketui.common.mapper.ImgGroupModelMapper;
import com.laiketui.common.mapper.UploadConfigModelMapper;
import com.laiketui.domain.log.FilesRecordModel;
import com.laiketui.domain.upload.ImgGroupModel;
import com.laiketui.domain.upload.UploadConfigModel;
import com.laiketui.domain.upload.UploadImagModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.files.DelFilesVo;
import com.laiketui.domain.vo.files.FilesVo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

/**
 * 资源上传
 *
 * @author Trick
 * @date 2021/7/7 18:20
 */
@Service
public class FileServiceImpl implements FileService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ImgGroupModelMapper imgGroupModelMapper;

    @Autowired
    private FilesRecordModelMapper filesRecordModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private UploadConfigModelMapper uploadConfigModelMapper;

    @Override
    public Map<String, Object> index(FilesVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            ImgGroupModel imgGroupModel = null;
            if (vo.getGroupId() != null) {
                imgGroupModel = new ImgGroupModel();
                imgGroupModel.setStore_id(vo.getStoreId());
                imgGroupModel.setId(vo.getGroupId());
                imgGroupModel = imgGroupModelMapper.selectOne(imgGroupModel);
            }
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (vo.getGroupId() != null && vo.getGroupId() != -1) {
                parmaMap.put("groupId", vo.getGroupId());
            }
            if (StringUtils.isNotEmpty(vo.getTitle())) {
                parmaMap.put("title", vo.getTitle());
            }
            if (StringUtils.isNotEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
            }
            if (StringUtils.isNotEmpty(vo.getEndDate())) {
                parmaMap.put("endDate", vo.getEndDate());
            }
            parmaMap.put("add_time_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = filesRecordModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> list = filesRecordModelMapper.selectDynamic(parmaMap);

            for (Map<String, Object> map : list) {
                String imgName = MapUtils.getString(map, "image_name");
                StringBuilder imgUrl = new StringBuilder("");
                String uploadType = MapUtils.getString(map, "upload_mode");
                switch (uploadType) {
                    case GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST:
                        //获取远程路径
                        imgUrl.append(DataUtils.getServcerUrl(vo.getRequest()));
                        String path;
                        if (imgGroupModel != null) {
                            path = PinyinUtils.getPinYin(imgGroupModel.getName());
                            imgUrl.append(File.separator).append(path);
                        }
                        imgUrl.append(File.separator).append(imgName);
                        break;
                    case GloabConst.UploadConfigConst.IMG_UPLOAD_OSS:
                        imgUrl.append(publiceService.getImgPath(imgName, vo.getStoreId()));
                        break;
                    default:
                        break;
                }
                map.put("url", imgUrl);
            }

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("文件列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> groupList(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());
            List<Map<String, Object>> list = imgGroupModelMapper.selectDynamic(parmaMap);

            resultMap.put("list", list);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("图片上传分类列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "groupList");
        }
        return resultMap;
    }

    @Override
    public void createCatalogue(MainVo vo, String catalogueName, Integer id) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            if (StringUtils.isEmpty(catalogueName)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "目录名不能为空");
            }
            ImgGroupModel imgGroupSave = new ImgGroupModel();
            ImgGroupModel imgGroupOld = null;
            if (id != null) {
                imgGroupOld = imgGroupModelMapper.selectByPrimaryKey(id);
                if (imgGroupOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "目录不存在");
                }
            }
            if (imgGroupOld == null || !imgGroupOld.getName().equals(catalogueName)) {
                ImgGroupModel imgGroupModel = new ImgGroupModel();
                imgGroupModel.setStore_id(vo.getStoreId());
                imgGroupModel.setName(catalogueName);
                int count = imgGroupModelMapper.selectCount(imgGroupModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "目录名已存在");
                }
            }
            imgGroupSave.setName(catalogueName);
            if (imgGroupOld != null) {
                imgGroupSave.setId(id);
                row = imgGroupModelMapper.updateByPrimaryKeySelective(imgGroupSave);
            } else {
                imgGroupSave.setSort(100);
                imgGroupSave.setIs_default(0);
                imgGroupSave.setAdd_date(new Date());
                imgGroupSave.setStore_id(vo.getStoreId());
                row = imgGroupModelMapper.insertSelective(imgGroupSave);
            }
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "目录创建失败");
            }
            //服务器资源路径
            String realPath = ImgUploadUtils.DEFAULT_UPLOAD_PATH;
            //判断文件夹是否存在,不存在则创建
            File diyFile = new File(realPath + File.separator + PinyinUtils.getPinYin(catalogueName));
            if (!diyFile.exists() && !diyFile.isDirectory()) {
                logger.debug("目录:{} 不存在,创建状态:{}", diyFile.getPath(), diyFile.mkdir());
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("图片上传分类列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "groupList");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> uploadImage(UploadFileVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            FilesRecordModel filesRecordSave = new FilesRecordModel();
            FilesRecordModel filesRecordOld = null;
            if (vo.getId() != null) {
                filesRecordOld = filesRecordModelMapper.selectByPrimaryKey(vo.getId());
                if (filesRecordOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "图片不存在");
                }
            }
            List<String> imgUrls = new ArrayList<>();
            if (filesRecordOld == null) {
                //上传图片
                imgUrls = publiceService.uploadFiles(vo);
            } else {
                filesRecordSave.setId(filesRecordOld.getId());
                if (vo.getMchId() != null) {
                    filesRecordSave.setMch_id(vo.getMchId());
                }
                //分组
                if (vo.getGroupId() != null) {
                    filesRecordSave.setGroup(vo.getGroupId().toString());
                }
                //修改图片参数
                if (StringUtils.isNotEmpty(vo.getTitle())) {
                    filesRecordSave.setTitle(vo.getTitle());
                }
                if (StringUtils.isNotEmpty(vo.getExplain())) {
                    filesRecordSave.setExplain(vo.getExplain());
                }
                if (StringUtils.isNotEmpty(vo.getDescribe())) {
                    filesRecordSave.setDescribe(vo.getDescribe());
                }
                if (StringUtils.isNotEmpty(vo.getAlternativeText())) {
                    filesRecordSave.setAlternative_text(vo.getAlternativeText());
                }
                int row = filesRecordModelMapper.updateByPrimaryKeySelective(filesRecordSave);
                if (row < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败");
                }
                String imgUrl = publiceService.getImgPath(filesRecordOld.getImage_name(), vo.getStoreId());
                imgUrls.add(ImgUploadUtils.getUrlPure(imgUrl, true));
            }
            resultMap.put("imgUrls", imgUrls);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("图片上传 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadImage");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delCatalogue(MainVo vo, int id) throws LaiKeApiException {
        try {
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            ImgGroupModel imgGroupModel = imgGroupModelMapper.selectByPrimaryKey(id);
            if (imgGroupModel != null) {
                //服务器资源路径
                String realPath = ImgUploadUtils.DEFAULT_UPLOAD_PATH;
                //判断文件夹是否存在,不存在则创建
                File diyFile = new File(realPath + File.separator + PinyinUtils.getPinYin(imgGroupModel.getName()));
                if (diyFile.exists() && diyFile.isDirectory()) {
                    boolean flag = diyFile.delete();
                    logger.debug("目录:{} 存在,删除状态:{}", diyFile.getPath(), flag);
                    if (!flag) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "目录删除失败");
                    }
                } else {
                    logger.debug("目录:{} 不存在,删除失败", diyFile.getPath());
                }
                //删除图片分类
                int row = imgGroupModelMapper.deleteByPrimaryKey(id);
                if (row < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "分类删除失败");
                }

                //删除文件夹里面的文件
                FilesRecordModel filesRecordDel = new FilesRecordModel();
                filesRecordDel.setGroup(id + "");
                filesRecordModelMapper.deleteByPrimaryKey(filesRecordDel);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "目录不存在");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除目录 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delCatalogue");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delFile(MainVo vo, int id) throws LaiKeApiException {
        try {
            RedisDataTool.isLogin(vo.getAccessId(), redisUtil);
            FilesRecordModel filesRecordOld = filesRecordModelMapper.selectByPrimaryKey(id);
            if (filesRecordOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "图片不存在");
            }
            //删除数据库数据
            int row = filesRecordModelMapper.deleteByPrimaryKey(filesRecordOld.getId());
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "文件数据删除失败");
            }
            //是否有目录
            ImgGroupModel imgGroupModel = imgGroupModelMapper.selectByPrimaryKey(filesRecordOld.getGroup());

            DelFilesVo delFilesVo = new DelFilesVo();
            delFilesVo.setUploadType(filesRecordOld.getUpload_mode());
            delFilesVo.setFileName(filesRecordOld.getImage_name());
            if (imgGroupModel != null) {
                delFilesVo.setCatalogue(imgGroupModel.getName());
            }
            UploadImagModel imagModel = new UploadImagModel();
            //oss参数
            if (GloabConst.UploadConfigConst.IMG_UPLOAD_OSS.equals(filesRecordOld.getUpload_mode())) {
                //从数据库获取上传配置key
                UploadConfigModel uploadConfigModel = new UploadConfigModel();
                uploadConfigModel.setUpserver(GloabConst.UploadConfigConst.IMG_UPLOAD_OSS);
                List<UploadConfigModel> uploadConfigs = uploadConfigModelMapper.select(uploadConfigModel);
                //装载参数
                imagModel.setUploadConfigs(uploadConfigs);
            }
            delFilesVo.setUploadImagModel(imagModel);
            //执行删除
            ImgUploadUtils imgUploadUtils = new ImgUploadUtils();
            imgUploadUtils.imgDel(delFilesVo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除图片 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delFile");
        }
    }
}
