package com.laiketui.modules.backend.services;


import com.laiketui.api.modules.backend.PcManageService;
import com.laiketui.common.mapper.BannerModelMapper;
import com.laiketui.domain.config.BannerModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.pc.AddBannerInfoVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * pc管理
 *
 * @author Trick
 * @date 2021/1/22 10:39
 */
@Service
public class PcManageServiceImpl implements PcManageService {
    private final Logger logger = LoggerFactory.getLogger(PcManageServiceImpl.class);

    @Override
    public Map<String, Object> getBannerInfo(MainVo vo, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("type", vo.getStoreType());
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            parmaMap.put("mch_id", 0);
            parmaMap.put("sort_sort", "desc");
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataList = bannerModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : dataList) {
                String imagUrl = String.valueOf(map.get("image"));
                imagUrl = publiceService.getImgPath(imagUrl, vo.getStoreId());
                map.put("image", imagUrl);
            }
            int total = bannerModelMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取轮播图信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getBannerInfo");
        }
        return resultMap;
    }

    @Override
    public boolean addBannerInfo(AddBannerInfoVo vo) throws LaiKeApiException {
        try {
            int count;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (StringUtils.isEmpty(vo.getImageUrl())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "轮播图不能为空");
            }
            if (StringUtils.isEmpty(vo.getPath())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "轮播图跳转路径不能为空");
            }
            BannerModel bannerModelOld = null;
            if (vo.getId() != null && vo.getId() > 0) {
                bannerModelOld = new BannerModel();
                bannerModelOld.setMch_id(0);
                bannerModelOld.setId(vo.getId());
                bannerModelOld.setStore_id(vo.getStoreId());
                bannerModelOld.setType(String.valueOf(vo.getStoreType()));
                bannerModelOld = bannerModelMapper.selectOne(bannerModelOld);
            }

            BannerModel bannerModelSave = new BannerModel();
            bannerModelSave.setOpen_type(vo.getPath());
            bannerModelSave.setImage(ImgUploadUtils.getUrlImgByName(vo.getImageUrl(), true));

            if (bannerModelOld != null) {
                bannerModelSave.setId(bannerModelOld.getId());
                count = bannerModelMapper.updateByPrimaryKeySelective(bannerModelSave);
            } else {
                bannerModelSave.setSort(bannerModelMapper.getMaxSort(vo.getStoreId()));
                bannerModelSave.setType(String.valueOf(vo.getStoreType()));
                bannerModelSave.setStore_id(vo.getStoreId());
                bannerModelSave.setAdd_date(new Date());
                count = bannerModelMapper.insertSelective(bannerModelSave);
            }
            //涉及首页,这里清空首页缓存
            redisUtil.del(String.format(GloabConst.RedisHeaderKey.JAVA_INDEX_CACHE, vo.getStoreId()));
            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑轮播图 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addBannerInfo");
        }
    }

    @Override
    public boolean delBannerById(int id) throws LaiKeApiException {
        try {
            BannerModel bannerModel = bannerModelMapper.selectByPrimaryKey(id);
            if (bannerModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "轮播图不存在");
            }
            //涉及首页,这里清空首页缓存
            redisUtil.del(String.format(GloabConst.RedisHeaderKey.JAVA_INDEX_CACHE, bannerModel.getStore_id()));
            return bannerModelMapper.deleteByPrimaryKey(id) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除轮播图 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delBannerById");
        }
    }

    @Override
    public boolean topBannerById(int id) throws LaiKeApiException {
        try {
            BannerModel bannerModel = bannerModelMapper.selectByPrimaryKey(id);
            if (bannerModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "轮播图不存在");
            }
            BannerModel bannerModelUpdate = new BannerModel();
            bannerModelUpdate.setId(id);
            bannerModelUpdate.setSort(bannerModelMapper.getMaxSort(bannerModel.getStore_id()));

            return bannerModelMapper.updateByPrimaryKeySelective(bannerModelUpdate) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("置顶轮播图 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "topBannerById");
        }
    }

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private BannerModelMapper bannerModelMapper;

    @Autowired
    private RedisUtil redisUtil;
}
