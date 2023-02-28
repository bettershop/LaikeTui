package com.laiketui.common.services;

import com.laiketui.api.common.PublicStockService;
import com.laiketui.common.mapper.ConfiGureModelMapper;
import com.laiketui.common.mapper.ProductListModelMapper;
import com.laiketui.common.mapper.SkuModelMapper;
import com.laiketui.common.mapper.StockModelMapper;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.domain.config.SkuModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.product.StockModel;
import com.laiketui.domain.vo.goods.AddStockVo;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.PinyinUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;


/**
 * 关于库存
 *
 * @author Trick
 * @date 2020/11/19 15:56
 */
@Service
public class PublicStockServiceImpl implements PublicStockService {

    private final Logger logger = LoggerFactory.getLogger(PublicStockServiceImpl.class);


    @Autowired
    private StockModelMapper stockModelMapper;

    @Autowired
    private SkuModelMapper skuModelMapper;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Override
    public SkuModel saveSku(SkuModel skuModel, int num) throws LaiKeApiException {
        try {
            String code = "";
            if (DictionaryConst.SkuType.SKUTYPE_NAME.equals(skuModel.getType())) {
                code = PinyinUtils.getPinYinHeadChar(skuModel.getName());
                code = "LKT_" + code + "_" + skuModel.getStore_id();
            } else {
                //获取属性上级code LKT_YS_0
                SkuModel sku = new SkuModel();
                sku.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                sku.setId(skuModel.getSid());
                sku = skuModelMapper.selectOne(sku);
                String thisCode = "";
                if (sku != null) {
                    thisCode = sku.getCode();
                }
                //获取之前最新子级规则然后+1
                String code1 = skuModelMapper.getAttributeByCode(skuModel.getSid());
                if (!StringUtils.isEmpty(code1)) {
                    //属性子级拼接规则 LKT_YS_0_子级名称_001(++)...
                    num = Integer.parseInt(code1.substring(code1.lastIndexOf("_") + 1));
                }
                code = thisCode + "_" + String.format("%03d", num + 1);
            }
            skuModel.setAdmin_name("admin");
            skuModel.setCode(code);
            int count = skuModelMapper.insertSelective(skuModel);
            if (count > 0) {
                return skuModel;
            }
        } catch (Exception e) {
            logger.error("属性添加 异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "saveSku");
        }
        return null;
    }

    @Override
    public boolean addStockWarning(int storeId, int attrId) throws LaiKeApiException {
        try {
            //获取属性库存信息
            Map<String, Object> map = confiGureModelMapper.getGoodsStockInfo(attrId);
            if (map != null && !map.isEmpty()) {
                int minNum = Integer.parseInt(map.get("min_inventory").toString());
                int num = Integer.parseInt(map.get("num").toString());
                int pid = Integer.parseInt(map.get("pid").toString());
                int totalNum = Integer.parseInt(map.get("total_num").toString());
                if (minNum >= num) {
                    String text = "预警";
                    StockModel stockModel = new StockModel();
                    stockModel.setStore_id(storeId);
                    stockModel.setProduct_id(pid);
                    stockModel.setAttribute_id(attrId);
                    stockModel.setTotal_num(totalNum);
                    //预警数量
                    stockModel.setFlowing_num(minNum - num);
                    stockModel.setType(DictionaryConst.StockType.AGREEMENTTYPE_WAREHOUSING_WARNING);
                    stockModel.setContent(text);
                    stockModel.setAdd_date(new Date());
                    int count = stockModelMapper.insertSelective(stockModel);
                    if (count > 0) {
                        logger.debug("库存预警添加成功 id={}", attrId);
                        return true;
                    }
                } else {
                    logger.debug("库存充足,无需预警 id={}", attrId);
                    return true;
                }
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "addStockWarning");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加库存预警 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addStockWarning");
        }
        return false;
    }

    @Override
    public void addGoodsStock(AddStockVo vo, String uname) throws LaiKeApiException {
        try {
            int id = vo.getId();
            int stockNum = vo.getAddNum();
            int goodsId = vo.getPid();
            //增加商品总库存
            int count = productListModelMapper.addGoodsStockNum(goodsId, stockNum);
            if (count < 1) {
                logger.debug("添加商品库存失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.OPERATION_FAILED, "添加失败");
            }
            ConfiGureModel confiGure = new ConfiGureModel();
            confiGure.setTotal_num(stockNum);
            confiGure.setNum(stockNum);
            confiGure.setPid(goodsId);
            confiGure.setId(id);
            count = confiGureModelMapper.addGoodsAttrStockNum(confiGure);
            if (count < 1) {
                logger.debug("添加商品规格库存失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.OPERATION_FAILED, "添加失败");
            }
            //刷新库存信息
            ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(id);

            String text = vo.getText();
            if (StringUtils.isEmpty(text)) {
                text = uname + "增加商品规格库存" + stockNum;
            }
            StockModel stockModel = new StockModel();
            stockModel.setStore_id(vo.getStoreId());
            stockModel.setProduct_id(confiGureModel.getPid());
            stockModel.setAttribute_id(confiGureModel.getId());
            stockModel.setTotal_num(confiGureModel.getTotal_num());
            stockModel.setFlowing_num(stockNum);
            if (stockNum > 0) {
                stockModel.setType(DictionaryConst.StockType.STOCKTYPE_WAREHOUSING);
            } else {
                stockModel.setType(DictionaryConst.StockType.AGREEMENTTYPE_WAREHOUSING_OUT);
            }
            stockModel.setContent(text);
            stockModel.setAdd_date(new Date());
            count = stockModelMapper.insertSelective(stockModel);
            if (count < 1) {
                logger.debug("库存记录失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败");
            }
            //是否需要库存添加库存预警
            if (!this.addStockWarning(vo.getStoreId(), id)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败");
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "addGoodsStock");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加商品库存 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addGoodsStock");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int refreshGoodsStock(int storeId, int goodsId) throws LaiKeApiException {
        int stockNum = 0;
        try {
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setId(goodsId);
            productListModel = productListModelMapper.selectOne(productListModel);
            if (productListModel != null) {
                stockNum = confiGureModelMapper.countConfigGureNum(goodsId);
                if (stockNum == productListModel.getNum()) {
                    logger.debug("商品id{} 无需修正库存 原有库存{} 规格总库存{}", goodsId, productListModel.getNum(), stockNum);
                    return stockNum;
                }
                logger.debug("商品id{} 正在修正库存 原有库存{} 规格总库存{}", goodsId, productListModel.getNum(), stockNum);
                ProductListModel productListUpdate = new ProductListModel();
                productListUpdate.setId(goodsId);
                productListUpdate.setNum(stockNum);
                int row = productListModelMapper.updateByPrimaryKeySelective(productListUpdate);
                if (row > 0) {
                    logger.debug("商品id{} 库存已经修正", goodsId);
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("修正商品总库存 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "refreshGoodsStock");
        }
        return stockNum;
    }


    @Override
    public void outStockNum(int storeId, int goodsId, int attrId, int needNum, boolean isPlugin) throws LaiKeApiException {
        try {
            ConfiGureModel confiGureModel = new ConfiGureModel();
            confiGureModel.setId(attrId);
            confiGureModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS + "");
            ConfiGureModel attr = confiGureModelMapper.selectOne(confiGureModel);
            if (attr == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "规格不存在");
            }
            if (attr.getNum() < needNum) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.STOCK_INSUFFICIENT, "库存不足");
            }
            logger.debug("规格id{} 出库{}个", attrId, needNum);
            String text = "商品规格出库" + needNum;
            if (isPlugin) {
                text += "插件添加商品," + text;
            }
            StockModel stockModel = new StockModel();
            stockModel.setStore_id(storeId);
            stockModel.setProduct_id(goodsId);
            stockModel.setAttribute_id(attrId);
            stockModel.setTotal_num(attr.getTotal_num());
            stockModel.setFlowing_num(needNum);
            stockModel.setType(DictionaryConst.StockType.AGREEMENTTYPE_WAREHOUSING_OUT);
            stockModel.setContent(text);
            stockModel.setAdd_date(new Date());
            int count = stockModelMapper.insertSelective(stockModel);
            if (count < 1) {
                logger.debug("库存记录失败");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加失败");
            }
            //扣除规格库存
            confiGureModelMapper.addGoodsAttrStockNumByPid(-needNum, attrId);
            //商品总库存
            productListModelMapper.addGoodsStockNum(goodsId, -needNum);
            if (!isPlugin) {
                //更新商品销量
                productListModelMapper.updateProductListVolume(needNum, storeId, goodsId);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("商品出库 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "outStockNum");
        }
    }

    @Override
    public void outStockNum(int storeId, int goodsId, int attrId, int needNum) throws LaiKeApiException {
        try {
            outStockNum(storeId, goodsId, attrId, needNum, false);
        } catch (LaiKeApiException l) {
            throw l;
        }
    }

}
