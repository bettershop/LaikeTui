package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.ExpressService;
import com.laiketui.common.mapper.ExpressModelMapper;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.ExpressSaveVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 物流公司管理
 *
 * @author Trick
 * @date 2021/7/6 16:48
 */
@Service
public class ExpressServiceImpl implements ExpressService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ExpressModelMapper expressModelMapper;

    @Override
    public Map<String, Object> index(MainVo vo, Integer id, String keyWord, Integer sortType) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (StringUtils.isNotEmpty(keyWord)) {
                parmaMap.put("kuaidi_name", keyWord);
            }
            parmaMap.put("id", id);
            String sort = DataUtils.Sort.DESC.toString();
            if (sortType != null && sortType == 0) {
                sort = DataUtils.Sort.ASC.toString();
            }
            parmaMap.put("sort_sort", sort);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = expressModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> list = expressModelMapper.selectDynamic(parmaMap);
            for (Map map: list) {
                map.put("add_date", DateUtil.dateFormate(MapUtils.getString(map,"add_date"), GloabConst.TimePattern.YMDHMS));
            }
            resultMap.put("total", total);
            resultMap.put("list", list);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("物流公司列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    @Override
    public void expressSwitch(MainVo vo, Integer id) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (id == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            ExpressModel expressOld = expressModelMapper.selectByPrimaryKey(id);
            if (expressOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "物流不存在");
            }
            ExpressModel expressUpdate = new ExpressModel();
            expressUpdate.setId(expressOld.getId());
            int isOpen = 1;
            if (expressOld.getIs_open() == isOpen) {
                isOpen = 2;
            }
            expressUpdate.setIs_open(isOpen);
            int row = expressModelMapper.updateByPrimaryKeySelective(expressUpdate);

            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "开关失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("物流开关 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "expressSwitch");
        }
    }

    @Override
    public void expressSave(ExpressSaveVo vo) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            ExpressModel expressSave = new ExpressModel();
            ExpressModel expressOld = null;
            if (vo.getId() != null) {
                expressOld = expressModelMapper.selectByPrimaryKey(vo.getId());
                if (expressOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "物流公司不存在");
                }
            }
            expressSave.setKuaidi_name(vo.getName());
            expressSave.setType(vo.getCode());
            expressSave.setSort(vo.getSort());
            expressSave.setIs_open(vo.getSwitchse());
            expressSave.setAdd_date(new Date());

            ExpressModel expressModel = new ExpressModel();
            expressModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            expressModel.setKuaidi_name(vo.getName());
            if (expressOld == null || !expressOld.getKuaidi_name().equals(vo.getName())) {
                if (expressModelMapper.selectCount(expressModel) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "物流公司已存在");
                }
            }
            if (expressOld == null || !expressOld.getType().equals(vo.getCode())) {
                expressModel.setKuaidi_name(null);
                expressModel.setType(vo.getCode());
                if (expressModelMapper.selectCount(expressModel) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "物流编码已存在");
                }
            }
            if (expressOld != null) {
                expressSave.setId(expressOld.getId());
                row = expressModelMapper.updateByPrimaryKeySelective(expressSave);
            } else {
                row = expressModelMapper.insertSelective(expressSave);
            }

            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑物流 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "expressSave");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void expressDel(MainVo vo, String ids) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (StringUtils.isEmpty(ids)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "id不能为空");
            }
            String[] idList = ids.split(",");
            for (String id : idList) {
                ExpressModel expressUpdate = new ExpressModel();
                expressUpdate.setId(Integer.parseInt(id));
                expressUpdate.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                row = expressModelMapper.updateByPrimaryKeySelective(expressUpdate);
                if (row < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "删除失败");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除物流 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "expressDel");
        }
    }
}
