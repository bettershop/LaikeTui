package com.laiketui.modules.backend.services;

import com.laiketui.api.common.PublicMchService;
import com.laiketui.api.modules.backend.FreightService;
import com.laiketui.common.mapper.AdminCgModelMapper;
import com.laiketui.domain.config.AdminCgModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.mch.AddFreihtVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 运费管理
 *
 * @author Trick
 * @date 2020/12/31 15:12
 */
@Service
public class FreightServiceImpl implements FreightService {
    private final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> getFreightInfo(MainVo vo, Integer mchId, Integer fid, Integer status, String name) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (user != null) {
                PageModel pageModel = new PageModel(vo.getPageNo(), vo.getPageSize(), vo.getPageNum());
                if (fid != null) {
                    //更具id获取
                    resultMap = publicMchService.freightModifyShow(vo.getStoreId(), mchId, fid);
                } else if (status != null || !StringUtils.isEmpty(name)) {
                    //更具条件获取模板列表
                    resultMap = publicMchService.freightList(vo.getStoreId(), mchId, name, status, pageModel);
                } else {
                    //获取全部
                    resultMap = publicMchService.freightList(vo.getStoreId(), mchId, null, null, pageModel);
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取运费信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getFreightInfo");
        }
        return resultMap;
    }

    @Override
    public List<AdminCgModel> getRegion(MainVo vo, Integer level, Integer sid) throws LaiKeApiException {
        try {
            AdminCgModel adminCgModel = new AdminCgModel();
            adminCgModel.setG_Level(level);
            if (sid != null) {
                adminCgModel.setG_ParentID(sid);
            }
            return adminCgModelMapper.select(adminCgModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取地区信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getRegion");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addFreight(AddFreihtVo vo) throws LaiKeApiException {
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (user != null) {
                vo.setShopId(user.getShop_id());
                return publicMchService.freightAdd(vo);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改运费信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addFreight");
        }
        return false;
    }

    @Override
    public void freightSetDefault(MainVo vo, int id) throws LaiKeApiException {
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            AddFreihtVo addFreihtVo = new AddFreihtVo();
            addFreihtVo.setFid(id);
            addFreihtVo.setStoreId(vo.getStoreId());
            addFreihtVo.setShopId(user.getShop_id());
            publicMchService.setDefault(addFreihtVo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("运费设置默认开关 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "freightSetDefault");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delFreight(MainVo vo, String freightIds) throws LaiKeApiException {
        try {
            publicMchService.freightDel(vo.getStoreId(), freightIds);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改运费信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addFreight");
        }
    }


    @Autowired
    private PublicMchService publicMchService;

    @Autowired
    private AdminCgModelMapper adminCgModelMapper;

}
