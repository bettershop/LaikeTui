package com.laiketui.modules.backend.services;

import com.laiketui.api.common.PubliceService;
import com.laiketui.api.modules.backend.ResourcesService;
import com.laiketui.common.mapper.FilesRecordModelMapper;
import com.laiketui.common.mapper.UploadConfigModelMapper;
import com.laiketui.domain.upload.UploadConfigModel;
import com.laiketui.domain.upload.UploadImagModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源管理
 *
 * @author Trick
 * @date 2021/7/21 16:43
 */
@Service
public class ResourcesServiceImpl implements ResourcesService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FilesRecordModelMapper filesRecordModelMapper;

    @Autowired
    private UploadConfigModelMapper uploadConfigModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> index(MainVo vo, String mchName) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (StringUtils.isNotEmpty(mchName)) {
                parmaMap.put("mch_name", mchName);
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = filesRecordModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> list = filesRecordModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : list) {
                map.put("imgUrl", publiceService.getImgPath(MapUtils.getString(map, "image_name"), vo.getStoreId()));
            }

            resultMap.put("total", total);
            resultMap.put("list", list);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("资源列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    @Override
    public void downForZip(MainVo vo, HttpServletResponse response, String imgIds) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(imgIds)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            List<Map<String, Object>> imgNameMaps = filesRecordModelMapper.getImgNameByIds(Arrays.asList(imgIds.split(SplitUtils.DH)));
            if (imgNameMaps == null || imgNameMaps.size() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "图片不存在");
            }

            UploadImagModel imagModel = new UploadImagModel();
            imagModel.setUploadType(GloabConst.UploadConfigConst.IMG_UPLOAD_OSS);
            //从数据库获取上传配置key
            UploadConfigModel uploadConfigModel = new UploadConfigModel();
            uploadConfigModel.setUpserver(imagModel.getUploadType());
            List<UploadConfigModel> uploadConfigs = uploadConfigModelMapper.select(uploadConfigModel);
            //装载参数
            imagModel.setUploadConfigs(uploadConfigs);

            //下载文件
            ImgUploadUtils file = new ImgUploadUtils();
            file.downImages(imagModel, imgNameMaps, response, vo.getStoreId(), vo.getStoreType());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("批量下载图片 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "downForZip");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void del(MainVo vo, String imgIds) throws LaiKeApiException {
        try {
            int row;
            List<String> ids = DataUtils.convertToList(imgIds.split(SplitUtils.DH));
            if (ids == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            for (String id : ids) {
                row = filesRecordModelMapper.deleteByPrimaryKey(id);
                if (row < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("批量删除 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "del");
        }
    }


}
