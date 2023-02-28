package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.LabelService;
import com.laiketui.common.mapper.ProLabelModelMapper;
import com.laiketui.domain.product.ProLabelModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
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
 * 商品标签
 *
 * @author Trick
 * @date 2021/6/25 18:07
 */
@Service
public class LabelServiceImpl implements LabelService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ProLabelModelMapper proLabelModelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> index(MainVo vo, String name, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("id", id);
            parmaMap.put("name", name);
            parmaMap.put("add_time_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            int total = proLabelModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> list = proLabelModelMapper.selectDynamic(parmaMap);

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    @Override
    public void addGoodsLabel(MainVo vo, String name, Integer id) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            ProLabelModel proLabelSave = new ProLabelModel();
            ProLabelModel proLabelOld = null;
            if (id != null) {
                proLabelOld = proLabelModelMapper.selectByPrimaryKey(id);
                if (proLabelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品标签不存在");
                }
            }
            proLabelSave.setName(name);
            proLabelSave.setAdd_time(new Date());
            if (proLabelOld == null || !proLabelOld.getName().equals(name)) {
                ProLabelModel proLabelModel = new ProLabelModel();
                proLabelModel.setStore_id(vo.getStoreId());
                proLabelModel.setName(name);
                int count = proLabelModelMapper.selectCount(proLabelModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商品标签已存在");
                }
            }
            if (proLabelOld != null) {
                proLabelSave.setId(id);
                row = proLabelModelMapper.updateByPrimaryKeySelective(proLabelSave);
            } else {
                proLabelSave.setStore_id(vo.getStoreId());
                row = proLabelModelMapper.insertSelective(proLabelSave);
            }

            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "添加商品标签失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑商品标签 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addGoodsLabel");
        }
    }

    @Override
    public void delGoodsLabel(MainVo vo, int id) throws LaiKeApiException {
        try {
            int row;
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            ProLabelModel proLabelOld = proLabelModelMapper.selectByPrimaryKey(id);
            if (proLabelOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品标签不存在");
            }
            row = proLabelModelMapper.deleteByPrimaryKey(id);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "删除商品标签失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除商品标签 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delGoodsLabel");
        }
    }


}
