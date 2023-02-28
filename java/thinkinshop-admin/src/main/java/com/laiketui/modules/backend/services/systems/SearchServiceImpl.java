package com.laiketui.modules.backend.services.systems;

import com.laiketui.api.modules.backend.systems.SearchService;
import com.laiketui.common.mapper.HotKeywordsModelMapper;
import com.laiketui.domain.config.HotKeywordsModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 搜索配置
 *
 * @author Trick
 * @date 2021/1/15 9:31
 */
@Service
public class SearchServiceImpl implements SearchService {
    private final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Override
    public Map<String, Object> getSearchConfigIndex(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            HotKeywordsModel hotKeywordsModel = new HotKeywordsModel();
            hotKeywordsModel.setStore_id(vo.getStoreId());
            hotKeywordsModel = hotKeywordsModelMapper.selectOne(hotKeywordsModel);

            resultMap.put("data", hotKeywordsModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取查询配置信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSearchConfigIndex");
        }
        return resultMap;
    }

    @Override
    public boolean addSearchConfig(MainVo vo, int isOpen, int limitNum, String keyword) throws LaiKeApiException {
        try {
            int count;
            HotKeywordsModel hotKeywordsModel = new HotKeywordsModel();
            hotKeywordsModel.setStore_id(vo.getStoreId());
            hotKeywordsModel = hotKeywordsModelMapper.selectOne(hotKeywordsModel);
            if (limitNum < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "上限至少一个");
            }
            if (StringUtils.isEmpty(keyword)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "关键字不能为空");
            } else if (keyword.split(",").length > limitNum) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "关键字不能大于限制数量");
            }

            HotKeywordsModel hotKeywordsModelSave = new HotKeywordsModel();
            hotKeywordsModelSave.setNum(limitNum);
            hotKeywordsModelSave.setIs_open(isOpen);
            hotKeywordsModelSave.setKeyword(keyword);
            if (hotKeywordsModel != null) {
                hotKeywordsModelSave.setId(hotKeywordsModel.getId());
                count = hotKeywordsModelMapper.updateByPrimaryKeySelective(hotKeywordsModelSave);
            } else {
                hotKeywordsModelSave.setStore_id(vo.getStoreId());
                count = hotKeywordsModelMapper.insertSelective(hotKeywordsModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑搜搜配置 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addSearchConfig");
        }
    }

    @Autowired
    private HotKeywordsModelMapper hotKeywordsModelMapper;
}
