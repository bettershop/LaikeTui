package com.laiketui.modules.backend.services;

import com.laiketui.api.common.PublicGoodsService;
import com.laiketui.api.modules.backend.TaoBaoService;
import com.laiketui.common.mapper.TaoBaoModelMapper;
import com.laiketui.common.mapper.TaoBaoWorkModelMapper;
import com.laiketui.domain.product.TaoBaoWorkModel;
import com.laiketui.domain.task.TaoBaoModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.AddTaoBaoVo;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 淘宝助手实现
 *
 * @author Trick
 * @date 2021/1/4 16:31
 */
@Service
public class TaoBaoServiceImpl implements TaoBaoService {
    private final Logger logger = LoggerFactory.getLogger(TaoBaoServiceImpl.class);

    @Override
    public Map<String, Object> getTaoBaoList(MainVo vo, Integer status, String taskName, Integer id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("title_like", taskName);
            if (!StringUtils.isEmpty(taskName)) {
                parmaMap.put("title_like", taskName);
            }
            if (status != null) {
                parmaMap.put("status", status);
            }
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> list;
            int total;

            if (id != null) {
                total = taoBaoWorkModelMapper.countTaoBaoDetailList(id);
                list = taoBaoWorkModelMapper.getTaoBaoDetailList(id, vo.getPageNo(), vo.getPageSize());
            } else {
                total = taoBaoWorkModelMapper.getTaoBaoInfoCount(parmaMap);
                parmaMap.put("store_id", vo.getStoreId());
                list = taoBaoWorkModelMapper.getTaoBaoInfoList(parmaMap);
            }
            resultMap.put("total", total);
            resultMap.put("list", list);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取抓取任务", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getTaoBaoWorkeList");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delTaoBaoTask(MainVo vo, String taskIds) throws LaiKeApiException {
        try {
            if (!StringUtils.isEmpty(taskIds)) {
                String[] taskIdStr = taskIds.split(",");
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("taskIds", taskIdStr);
                int count = taoBaoWorkModelMapper.batDelById(parmaMap);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败", "delTaoBaoTask");
                }
                logger.debug("任务删除成功,一共删除{}条记录", count);
                parmaMap.clear();
                parmaMap.put("workIds", taskIdStr);
                count = taoBaoModelMapper.batDelById(parmaMap);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败", "delTaoBaoTask");
                }
                logger.debug("任务删除成功,一共删除{}条记录", count);

                return true;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "delTaoBaoTask");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("批量删除淘宝任务 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delTaoBaoTask");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean executeTaoBaoById(MainVo vo, String taskIds) throws LaiKeApiException {
        try {
            List<Integer> taskList = new ArrayList<>();
            if (!StringUtils.isEmpty(taskIds)) {
                String[] taskIdStr = taskIds.split(",");
                for (String taskId : taskIdStr) {
                    taskList.add(Integer.parseInt(taskId));
                }
            }
            return publicGoodsService.executeTaoBaoById(taskList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("批量执行淘宝任务 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "executeTaoBaoById");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void restoreExecuteTaoBaoById(MainVo vo, int taskId) throws LaiKeApiException {
        try {
            publicGoodsService.restoreExecuteTaoBaoById(taskId);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("重新执行淘宝任务 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "restoreExecuteTaoBaoById");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addTaoBaoTask(AddTaoBaoVo vo) throws LaiKeApiException {
        try {
            boolean isUpdate = false;
            TaoBaoWorkModel taoBaoWorkModelOld = null;
            //获取原来任务信息
            if (vo.getTaskWorkId() != null && vo.getTaskWorkId() > 0) {
                isUpdate = true;
                taoBaoWorkModelOld = new TaoBaoWorkModel();
                taoBaoWorkModelOld.setStore_id(vo.getStoreId());
                taoBaoWorkModelOld.setId(vo.getTaskWorkId());
                taoBaoWorkModelOld = taoBaoWorkModelMapper.selectOne(taoBaoWorkModelOld);
                if (taoBaoWorkModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "任务不存在", "addTaoBaoTask");
                }
            }
            TaoBaoWorkModel saveTaoBaoWorkModel = new TaoBaoWorkModel();
            saveTaoBaoWorkModel.setStore_id(vo.getStoreId());
            if (StringUtils.isEmpty(vo.getTaskName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "任务名称不能为空", "addTaoBaoTask");
            }
            if (StringUtils.isEmpty(vo.getClassId())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "类别不能为空", "addTaoBaoTask");
            }
            if (vo.getBrandId() == null || vo.getBrandId() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "品牌不能为空", "addTaoBaoTask");
            }
            if (vo.getItemId() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "淘宝链接不能为空", "addTaoBaoTask");
            }
            saveTaoBaoWorkModel.setTitle(vo.getTaskName());
            saveTaoBaoWorkModel.setStatus(TaoBaoWorkModel.TAOBAO_STATUS_TO_BE_OBTAINED);

            int count;
            int wid;
            if (!isUpdate) {
                //添加任务
                saveTaoBaoWorkModel.setCreattime(new Date());
                count = taoBaoWorkModelMapper.insertSelective(saveTaoBaoWorkModel);
                wid = saveTaoBaoWorkModel.getId();
            } else {
                TaoBaoModel taoBaoModel = new TaoBaoModel();
                taoBaoModel.setStore_id(vo.getStoreId());
                taoBaoModel.setW_id(taoBaoWorkModelOld.getId());
                wid = taoBaoWorkModelOld.getId();
                count = taoBaoModelMapper.delete(taoBaoModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败", "addTaoBaoTask");
                }
                //修改
                saveTaoBaoWorkModel.setId(taoBaoWorkModelOld.getId());
                count = taoBaoWorkModelMapper.updateByPrimaryKeySelective(saveTaoBaoWorkModel);
            }
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败", "addTaoBaoTask");
            }

            boolean flag = false;
            //添加任务明细
            for (String itemId : vo.getItemId()) {
                if (!StringUtils.isInteger(itemId)) {
                    continue;
                }
                TaoBaoModel saveTaoBaoModel = new TaoBaoModel();
                saveTaoBaoModel.setStore_id(vo.getStoreId());
                saveTaoBaoModel.setW_id(wid);
                saveTaoBaoModel.setLink(String.format(GloabConst.OtherUrl.API_TAOBAO_GOODSDATA_URL, itemId));
                saveTaoBaoModel.setItemid(itemId);
                saveTaoBaoModel.setStatus(TaoBaoModel.TAOBAO_STATUS_TO_BE_OBTAINED);
                saveTaoBaoModel.setCreattime(new Date());
                //递归找上级
                String cid = publicGoodsService.strOption(vo.getStoreId(), Integer.parseInt(vo.getClassId()), "");
                saveTaoBaoModel.setCid(cid);
                saveTaoBaoModel.setBrand_id(vo.getBrandId());
                count = taoBaoModelMapper.insertSelective(saveTaoBaoModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败", "addTaoBaoTask");
                }
                flag = true;
            }
            if (!flag) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "淘宝链接不正确", "addTaoBaoTask");
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加淘宝抓取任务 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addTaoBaoTask");
        }
    }


    @Autowired
    private TaoBaoModelMapper taoBaoModelMapper;

    @Autowired
    private TaoBaoWorkModelMapper taoBaoWorkModelMapper;

    @Autowired
    private PublicGoodsService publicGoodsService;
}
