package com.laiketui.modules.backend.services.saas;

import com.laiketui.api.common.PublicGoodsService;
import com.laiketui.api.modules.backend.saas.DataDictionaryManageService;
import com.laiketui.common.mapper.SkuModelMapper;
import com.laiketui.domain.config.SkuModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 数据字典管理
 *
 * @author Trick
 * @date 2021/2/3 9:29
 */
@Service
public class DataDictionaryManageServiceImpl implements DataDictionaryManageService {
    private final Logger logger = LoggerFactory.getLogger(DataDictionaryManageServiceImpl.class);

    @Autowired
    private SkuModelMapper skuModelMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PublicGoodsService publicGoodsService;

    @Override
    public Map<String, Object> getSkuInfo(MainVo vo, Integer id, Integer sid, String dataCode, String dataName, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            if (id != null) {
                parmaMap.put("id", id);
            } else {
                parmaMap.put("pageStart", vo.getPageNo());
                parmaMap.put("pageEnd", vo.getPageSize());
            }
            if (!StringUtils.isEmpty(dataCode)) {
                parmaMap.put("code", dataCode);
            }
            if (!StringUtils.isEmpty(dataName)) {
                parmaMap.put("nameLike", dataName);
            }
            if (sid != null) {
                parmaMap.put("sid", sid);
            }


            int total = skuModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> dataList = skuModelMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : dataList) {
                //获取明细
                if (id != null) {
                    //获取属性值集
                    Map<String, Object> parmaSkuMap = new HashMap<>(16);
                    parmaSkuMap.put("sid", id);
                    parmaSkuMap.put("type", SkuModel.SKU_TYPE_ATTRIBUTE_VALUE);
                    parmaSkuMap.put("pageStart", vo.getPageNo());
                    parmaSkuMap.put("pageEnd", vo.getPageSize());
                    int sunSkustotal = skuModelMapper.countDynamic(parmaSkuMap);
                    List<Map<String, Object>> sunSkusList = skuModelMapper.selectDynamic(parmaSkuMap);

                    map.put("sunSkusTotal", sunSkustotal);
                    map.put("sunSkus", sunSkusList);
                }
                map.put("add_date", DateUtil.dateFormate(MapUtils.getString(map,"add_date"), GloabConst.TimePattern.YMDHMS));
                //是否生效 0:不是 1:是 status
                String statusName = "失效";
                if (MapUtils.getIntValue(map, "status") == 1) {
                    statusName = "生效";
                }
                map.put("statusName", statusName);
            }
            if (vo.getExportType().equals(1)) {
                exportSkuData(dataList, response);
                return null;
            }

            resultMap.put("total", total);
            resultMap.put("list", dataList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品属性信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSkuInfo");
        }
        return resultMap;
    }

    //导出sku
    private void exportSkuData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"数据编码", "数据名称", "添加人", "添加时间", "是否生效"};
            //对应字段
            String[] kayList = new String[]{"code", "name", "admin_name", "add_date", "statusName"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("sku列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(false);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出[sku]数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportSkuData");
        }
    }

    @Override
    public Map<String, Object> getSkuAttributeList(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("type", SkuModel.SKU_TYPE_ATTRIBUTE_NAME);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> dataList = skuModelMapper.selectDynamic(parmaMap);
            int total = skuModelMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取属性名称下拉 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSkuAttributeList");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getSkuList(MainVo vo, String keyword) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            if (StringUtils.isEmpty(keyword)) {
                keyword = null;
            }
            List<Map<String, Object>> dataList = skuModelMapper.getSkuList(vo.getPageNo(), vo.getPageSize(), keyword);
            int total = skuModelMapper.countSkuList(keyword);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品属性/属性值列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSkuList");
        }
        return resultMap;
    }

    @Override
    public boolean setSkuSwitch(Integer id) throws LaiKeApiException {
        try {
            SkuModel skuModel = new SkuModel();
            skuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            skuModel.setId(id);
            skuModel = skuModelMapper.selectOne(skuModel);
            if (skuModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "属性不存在");
            }
            Integer status = SkuModel.SKU_STATUS_TAKE_EFFECT;
            if (status.equals(skuModel.getStatus())) {
                status = SkuModel.SKU_STATUS_INVALID;
            }
            SkuModel skuModelUpdate = new SkuModel();
            skuModelUpdate.setId(skuModel.getId());
            skuModelUpdate.setStatus(status);
            int count = skuModelMapper.updateByPrimaryKeySelective(skuModelUpdate);

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商品属性生效开关 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setSkuSwitch");
        }
    }

    @Override
    public boolean addSkuName(Integer id, String skuName, int isOpen, String token) throws LaiKeApiException {
        try {
            int count = 0;
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(token, redisUtil);
            if (adminModel != null) {
                if (StringUtils.isEmpty(skuName)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性名称不能为空");
                }

                SkuModel skuModelOld = null;
                if (id != null) {
                    skuModelOld = new SkuModel();
                    skuModelOld.setId(id);
                    skuModelOld.setType(SkuModel.SKU_TYPE_ATTRIBUTE_NAME);
                    skuModelOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    skuModelOld = skuModelMapper.selectOne(skuModelOld);
                    if (skuModelOld == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "属性不存在");
                    }
                    //只有未生效的情况下才能修改
                    if (SkuModel.SKU_STATUS_TAKE_EFFECT.equals(skuModelOld.getStatus())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "属性生效中,禁止修改");
                    }
                }
                SkuModel skuModelSave = new SkuModel();
                skuModelSave.setAdd_date(new Date());
                if (skuModelOld == null || !skuModelOld.getName().equals(skuName)) {
                    SkuModel skuModel = new SkuModel();
                    skuModel.setName(skuName);
                    skuModel.setType(SkuModel.SKU_TYPE_ATTRIBUTE_NAME);
                    skuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    count = skuModelMapper.selectCount(skuModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "属性名称已存在");
                    }
                    skuModelSave.setName(skuName);
                }
                if (skuModelOld != null) {
                    skuModelSave.setId(id);
                    count = skuModelMapper.updateByPrimaryKeySelective(skuModelSave);
                } else {
                    String code = publicGoodsService.getSkuCode(adminModel.getStore_id(), skuName, null);
                    skuModelSave.setCode(code);
                    skuModelSave.setAdmin_name(adminModel.getName());
                    skuModelSave.setType(SkuModel.SKU_TYPE_ATTRIBUTE_NAME);
                    skuModelSave.setStatus(isOpen);
                    count = skuModelMapper.insertSelective(skuModelSave);
                }
            }
            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改数据名称 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addSkuName");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addSku(int sid, List<String> attributeList, String token, int type) throws LaiKeApiException {
        try {
            int count;
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(token, redisUtil);
            if (attributeList == null || attributeList.size() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性值不能为空");
            }
            SkuModel skuModelOld = new SkuModel();
            skuModelOld.setId(sid);
            skuModelOld.setType(SkuModel.SKU_TYPE_ATTRIBUTE_NAME);
            skuModelOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            skuModelOld = skuModelMapper.selectOne(skuModelOld);
            if (skuModelOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "属性名称不存在");
            }
            //标记属性是否使用中,如果使用中则不能删除,但是可以添加
            boolean isUse = false;
            //获取当前属性下全部的值
            SkuModel skuModel = new SkuModel();
            skuModel.setSid(skuModelOld.getId());
            skuModel.setType(SkuModel.SKU_TYPE_ATTRIBUTE_VALUE);
            skuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            List<SkuModel> skuModelSunList = new ArrayList<>();
            if (type == 1) {
                //只有编辑的是时候需要
                skuModelSunList = skuModelMapper.select(skuModel);
                //判断该属性是否使用中,使用中则不能删除
                if (skuModelMapper.countSkuIsUse(skuModelOld.getId()) > 0) {
                    isUse = true;
                }
            }

            //添加/修改
            List<String> skuNameTemp = new ArrayList<>();
            for (String value : attributeList) {
                if (StringUtils.isEmpty(value)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性值不能为空");
                } else if (skuNameTemp.contains(value)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性值:" + value + "重复");
                }
                skuNameTemp.add(value);
                SkuModel skuModelSave = new SkuModel();
                skuModelSave.setName(value);
                //获取属性信息
                SkuModel skuModelSunOld = null;
                for (SkuModel sku : skuModelSunList) {
                    if (sku.getName().equals(value)) {
                        skuModelSunOld = sku;
                        //每次处理一个属性则移除一个,剩下的则删除
                        skuModelSunList.remove(sku);
                        break;
                    }
                }
                if (skuModelSunOld != null) {
                    skuModelSave.setId(skuModelSunOld.getId());
                    count = skuModelMapper.updateByPrimaryKeySelective(skuModelSave);
                } else {
                    if (type == 0) {
                        SkuModel skuCount = new SkuModel();
                        skuCount.setSid(skuModelOld.getId());
                        skuCount.setType(SkuModel.SKU_TYPE_ATTRIBUTE_VALUE);
                        skuCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                        skuCount.setName(value);
                        count = skuModelMapper.selectCount(skuCount);
                        if (count > 0) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性值:" + value + "重复");
                        }
                    }
                    skuModelSave.setSid(skuModelOld.getId());
                    skuModelSave.setStore_id(skuModelOld.getStore_id());
                    skuModelSave.setAdmin_name(adminModel.getName());
                    skuModelSave.setStatus(SkuModel.SKU_STATUS_TAKE_EFFECT);
                    skuModelSave.setType(SkuModel.SKU_TYPE_ATTRIBUTE_VALUE);
                    String code = publicGoodsService.getSkuCode(adminModel.getStore_id(), null, skuModelOld.getId());
                    skuModelSave.setCode(code);
                    skuModelSave.setAdd_date(new Date());
                    count = skuModelMapper.insertSelective(skuModelSave);
                }
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障");
                }
            }

            //删除剩下的属性值
            List<Integer> delAttributeList = new ArrayList<>();
            for (SkuModel sku : skuModelSunList) {
                delAttributeList.add(sku.getId());
                if (isUse) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性使用中,无法删除" + sku.getName());
                }
            }
            try {
                delSku(delAttributeList);
            } catch (LaiKeApiException l) {
                logger.debug("sku 删除失败 {}", l.getMessage());
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改商品属性 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addSku");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delSku(List<Integer> idList) throws LaiKeApiException {
        try {
            int count;
            if (idList != null) {
                for (Integer id : idList) {
                    SkuModel skuModel = new SkuModel();
                    skuModel.setId(id);
                    skuModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    skuModel = skuModelMapper.selectOne(skuModel);
                    if (skuModel == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "属性不存在");
                    }
                    if (skuModel.getSid() == 0) {
                        if (SkuModel.SKU_STATUS_TAKE_EFFECT.equals(skuModel.getStatus())) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "列表中属性生效中,删除失败");
                        }
                    }

                    SkuModel skuModelUpdate = new SkuModel();
                    skuModelUpdate.setId(skuModel.getId());
                    skuModelUpdate.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                    count = skuModelMapper.updateByPrimaryKeySelective(skuModelUpdate);

                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障");
                    }
                }

            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("批量删除商品属性 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delSku");
        }
    }

}
