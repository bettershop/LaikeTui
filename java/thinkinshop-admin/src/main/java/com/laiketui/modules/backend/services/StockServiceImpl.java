package com.laiketui.modules.backend.services;

import com.laiketui.api.common.PublicGoodsService;
import com.laiketui.api.common.PublicStockService;
import com.laiketui.api.modules.backend.StockService;
import com.laiketui.common.mapper.ConfiGureModelMapper;
import com.laiketui.common.mapper.ProductListModelMapper;
import com.laiketui.common.mapper.StockModelMapper;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.product.StockModel;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.domain.vo.goods.AddStockVo;
import com.laiketui.domain.vo.goods.StockInfoVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.help.DataSerializeHelp;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.GoodsDataUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 库存管理
 *
 * @author Trick
 * @date 2021/1/4 9:14
 */
@Service
public class StockServiceImpl implements StockService {
    private final Logger logger = LoggerFactory.getLogger(StockServiceImpl.class);


    @Override
    public Map<String, Object> getStockInfo(StockInfoVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            if (!StringUtils.isEmpty(vo.getProductTitle())) {
                parmaMap.put("product_number", vo.getProductNumber());
            }
            if (!StringUtils.isEmpty(vo.getMchName())) {
                parmaMap.put("mch_name_like", vo.getMchName());
            }
            if (!StringUtils.isEmpty(vo.getProductTitle())) {
                parmaMap.put("product_title_like", vo.getProductTitle());
            }

            int total = stockModelMapper.stockInfoCountDynamic(parmaMap);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> list = stockModelMapper.getStorckInfoDynamic(parmaMap);
            data(vo.getStoreId(), list);
            resultMap.put("excelDataList", list);

            if (vo.getExportType() == 1) {
                List<Map<String, Object>> maps = stockModelMapper.exportQuery(parmaMap);
                exportStockData(maps, response);
                return null;
            }

            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品库存信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getStockInfo");
        }
        return resultMap;
    }

    //导出库存列表
    private void exportStockData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"商品名称", "商品售价", "商品规格", "商品总库存", "剩余库存", "供货商", "上架时间"};
            //对应字段
            String[] kayList = new String[]{"product_title", "price", "specifications", "total_num", "num", "shop_name", "upper_shelf_time"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("库存列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品库存数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportStockData");
        }
    }

    @Override
    public Map<String, Object> getStockDetailInfo(StockInfoVo vo, Integer attrId, Integer pid, Integer type, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("product_id", pid);
            parmaMap.put("attribute_id", attrId);
            if (type != null) {
                if (type == 3) {
                    List<Integer> types = new ArrayList<>();
                    types.add(0);
                    types.add(1);
                    parmaMap.put("stockTypeList", types);
                } else {
                    parmaMap.put("stockType", type);
                }
            }
            if (!StringUtils.isEmpty(vo.getMchName())) {
                parmaMap.put("mch_name", vo.getMchName());
            }
            if (!StringUtils.isEmpty(vo.getProductTitle())) {
                parmaMap.put("product_title_like", vo.getProductTitle());
            }
            if (!StringUtils.isEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
            }
            if (!StringUtils.isEmpty(vo.getEndDate())) {
                parmaMap.put("endDate", vo.getEndDate());
            }

            int total = stockModelMapper.goodsStockInfoCountDynamic(parmaMap);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> list = stockModelMapper.getGoodsStorckInfoDynamic(parmaMap);
            data(vo.getStoreId(), list);

            if (vo.getExportType() == 1 && type != null) {
                //0.入库 1.出库 2.预警
                if (type == 0) {
                    exportStockAddData(list, response);
                } else if (type == 1) {
                    exportStockOutData(list, response);
                } else if (type == 2) {
                    exportStockWarningData(list, response);
                }
                return null;
            }


            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品详细库存信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getStockDetailInfo");
        }
        return resultMap;
    }

    //导出库存预警列表
    private void exportStockWarningData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"商品名称", "商品售价", "商品规格", "商品状态", "商品总库存", "剩余库存", "店铺", "上架时间"};
            //对应字段
            String[] kayList = new String[]{"product_title", "price", "specifications", "statusName", "total_num", "num", "name", "upper_shelf_time"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("库存预警列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品库存数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportStockData");
        }
    }

    //导出库存入库列表
    private void exportStockAddData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"商品名称", "商品售价", "商品规格", "商品状态", "商品总库存", "入库数量", "入库时间", "店铺"};
            //对应字段
            String[] kayList = new String[]{"product_title", "price", "specifications", "statusName", "total_num", "flowing_num", "add_date", "name"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("库存入库列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品库存数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportStockAddData");
        }
    }

    //导出库存出库列表
    private void exportStockOutData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"商品名称", "商品售价", "商品规格", "商品状态", "商品总库存", "出库数量", "店铺", "入库时间"};
            //对应字段
            String[] kayList = new String[]{"product_title", "price", "specifications", "statusName", "total_num", "flowing_num", "name", "add_date"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("库存出库列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(true);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出库存出库列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportStockOutData");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> addStock(AddStockVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (vo.getAddNum() < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "数量请输入正整数", "addStock");
            }
            int count = productListModelMapper.addGoodsStockNum(vo.getPid(), vo.getAddNum());
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败", "addStock");
            }
            ConfiGureModel confiGureModel = new ConfiGureModel();
            confiGureModel.setTotal_num(vo.getAddNum());
            confiGureModel.setNum(vo.getAddNum());
            confiGureModel.setId(vo.getId());
            confiGureModel.setPid(vo.getPid());
            count = confiGureModelMapper.addGoodsAttrStockNum(confiGureModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败", "addStock");
            }
            //获取库存信息
            confiGureModel = new ConfiGureModel();
            confiGureModel.setId(vo.getId());
            confiGureModel.setPid(vo.getPid());
            confiGureModel = confiGureModelMapper.selectOne(confiGureModel);


            String text = user.getName() + "增加商品库存" + vo.getAddNum();
            StockModel stockModel = new StockModel();
            stockModel.setStore_id(vo.getStoreId());
            stockModel.setProduct_id(vo.getPid());
            stockModel.setAttribute_id(vo.getId());
            stockModel.setTotal_num(confiGureModel.getTotal_num());
            stockModel.setFlowing_num(vo.getAddNum());
            stockModel.setType(DictionaryConst.StockType.STOCKTYPE_WAREHOUSING);
            stockModel.setContent(text);
            stockModel.setAdd_date(new Date());
            count = stockModelMapper.insertSelective(stockModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败", "addStock");
            }
            //是否需要库存添加库存预警
            if (!publicStockService.addStockWarning(vo.getStoreId(), vo.getId())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败", "addStock");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加库存 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addStock");
        }
        return resultMap;
    }


    /**
     * 数据处理
     *
     * @param storeId               -
     * @param outExcelStockInfoList -
     * @author Trick
     * @date 2021/1/4 13:08
     */
    private void data(int storeId, List<Map<String, Object>> outExcelStockInfoList) {
        for (Map<String, Object> map : outExcelStockInfoList) {
            //图片处理
            String imgUrl = map.get("imgurl").toString();
            imgUrl = publiceService.getImgPath(imgUrl, storeId);
            map.put("imgurl", imgUrl);
            //属性处理
            String attribute = map.get("attribute") + "";
            attribute = DataSerializeHelp.getAttributeStr(attribute);
            map.put("specifications", attribute);

            int status = Integer.parseInt(map.get("status").toString());
            map.put("statusName", GoodsDataUtils.getGoodsStatusStr(status));
        }
    }

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private StockModelMapper stockModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private PublicGoodsService publicGoodsService;

    @Autowired
    private PublicStockService publicStockService;
}
