package com.laiketui.modules.backend.services;

import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.common.PublicGoodsService;
import com.laiketui.api.common.admin.PublicAdminService;
import com.laiketui.api.modules.backend.IndexDubboService;
import com.laiketui.common.mapper.AdminModelMapper;
import com.laiketui.common.mapper.ProductConfigModelMapper;
import com.laiketui.common.mapper.ProductListModelMapper;
import com.laiketui.domain.config.ProductConfigModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.DefaultViewVo;
import com.laiketui.modules.backend.consts.StoreConst;
 
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 首页
 *
 * @author Trick
 * @date 2020/12/28 14:05
 */
@Service
public class IndexServiceImpl implements IndexDubboService {
    private final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PublicAdminService publicAdminService;

    @Override
    public Map<String, Object> home(MainVo vo, Integer mchId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            resultMap.putAll(publicAdminService.index(vo, mchId));
        } catch (Exception e) {
            logger.error("商城首页 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "home");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> index(DefaultViewVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            //获取商品信息
            Map<String, Object> goodsDataMap;
            Map<String, Object> map = new HashMap<>(16);
            map.put("page", vo.getPageNo());
            map.put("pagesize", vo.getPageSize());
            map.put("pageto", "");

            if (vo.getStatus() != null) {
                map.put("status", vo.getStatus());
            } else {
                //获取商品状态
                List<Integer> statusList = new ArrayList<>();
                statusList.add(DictionaryConst.GoodsStatus.NOT_GROUNDING);
                statusList.add(DictionaryConst.GoodsStatus.NEW_GROUNDING);
                statusList.add(DictionaryConst.GoodsStatus.OFFLINE_GROUNDING);
                map.put("statusList", statusList);
            }
            if (vo.getCid() != null) {
                map.put("product_class", vo.getCid());
            }
            if (vo.getBrandId() != null) {
                map.put("brand_id", vo.getBrandId());
            }
            if (StringUtils.isNotEmpty(vo.getProductTitle()) && StringUtils.isNotEmpty(vo.getMchName())) {
                map.put("mchNameOrGoodsName", vo.getMchName());
            } else {
                map.put("product_title", vo.getProductTitle());
                map.put("mch_name", vo.getMchName());
            }
            if (vo.getShowAdr() != null) {
                map.put("show_adr", vo.getShowAdr());
            }
            if (vo.getActive() != null) {
                map.put("active", vo.getActive());
            }
            if (vo.getGoodsTga() != null) {
                map.put("goodsTga", vo.getGoodsTga());
            }
            goodsDataMap = publicGoodsService.productList(vo.getStoreId(), user.getName(), 0, GloabConst.LktConfig.LKT_CONFIG_TYPE_PT, map);
            if (vo.getExportType().equals(1)) {
                exportGoodsData(DataUtils.cast(goodsDataMap.get("list")), response);
                return null;
            }
            // TODO: 2020/12/28 获取有那些插件暂时不做

            //获取字典信息
            Map<String, Object> goodsStatusMap = publicDictionaryService.getDictionaryByName("商品状态");
            Map<String, Object> goodsShowMap = publicDictionaryService.getDictionaryByName("商品展示位置");

            //产品开关
            int isOpen = 0;
            ProductConfigModel productConfigModel = new ProductConfigModel();
            productConfigModel.setStore_id(vo.getStoreId());
            productConfigModel = productConfigModelMapper.selectOne(productConfigModel);
            if (productConfigModel != null) {
                isOpen = productConfigModel.getIs_open();
            }
            //获取商城客户店铺
            Integer mchId = null;
            AdminModel adminModel = new AdminModel();
            adminModel.setStore_id(vo.getStoreId());
            adminModel.setType(AdminModel.TYPE_CLIENT);
            adminModel = adminModelMapper.selectOne(adminModel);
            if (adminModel != null) {
                mchId = adminModel.getShop_id();
            }

            resultMap.put("mch_id", mchId);
            resultMap.put("store_id", vo.getStoreId());
            resultMap.put("class_id", vo.getCid());
            resultMap.put("ctypes", mchId);
            resultMap.put("brand_id", vo.getBrandId());
            resultMap.putAll(goodsDataMap);

            resultMap.put("is_open", isOpen);
            resultMap.put("select2", goodsStatusMap);
            resultMap.put("select3", goodsShowMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("后台首页", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "index");
        }
        return resultMap;
    }

    private void exportGoodsData(List<Map<String, Object>> goodsList, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"商品ID", "商品标题", "分类名称", "库存", "商品状态", "销量", "发布时间", "商品品牌", "价格", "所属店铺", "上架时间"};
            //对应字段
            String[] kayList = new String[]{"id", "product_title", "pname", "num", "status_name", "volume", "add_date", "brand_name", "price", "name", "upper_shelf_time"};
            EasyPoiExcelUtil.excelExport("商品列表", headerList, kayList, goodsList, response);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportGoodsData");
        }
    }


    @Override
    public Map<String, Object> goodsStatus(MainVo vo, String ids) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            String[] goodsIds = ids.split(",");
            boolean status = true;
            boolean xp = true;
            boolean rx = true;
            boolean tj = true;
            for (String goodsId : goodsIds) {
                ProductListModel productListModel = new ProductListModel();
                productListModel.setStore_id(vo.getStoreId());
                productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
                productListModel.setId(Integer.parseInt(goodsId));
                productListModel = productListModelMapper.selectOne(productListModel);
                if (productListModel != null) {
                    if (DictionaryConst.ProductStatus.PRODUCTSTATUS_END_STATUS.toString().equals(productListModel.getStatus())) {
                        status = false;
                    }
                    List<String> stypes = Arrays.asList(productListModel.getS_type().split(","));
                    if (stypes.contains(DictionaryConst.GoodsStype.NEW_PRODUCT.toString())) {
                        xp = false;
                    }
                    if (stypes.contains(DictionaryConst.GoodsStype.HOT_GROUNDING.toString())) {
                        rx = false;
                    }
                    if (stypes.contains(DictionaryConst.GoodsStype.TOP_GROUNDING.toString())) {
                        tj = false;
                    }
                }
            }
            resultMap.put("status", status);
            resultMap.put("xp", xp);
            resultMap.put("rx", rx);
            resultMap.put("tj", tj);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商品状态信息" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "execute");
        }
        return resultMap;
    }


    @Override
    public Map<String, Object> isopen(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            int status = 0;
            ProductConfigModel productConfigModel = new ProductConfigModel();
            productConfigModel.setStore_id(vo.getStoreId());
            productConfigModel = productConfigModelMapper.selectOne(productConfigModel);
            if (productConfigModel != null) {
                ProductConfigModel updateProductConfig = new ProductConfigModel();
                updateProductConfig.setId(productConfigModel.getId());
                updateProductConfig.setIs_open(ProductConfigModel.NO_DISPLAY_BEAN_GOODS);
                if (ProductConfigModel.NO_DISPLAY_BEAN_GOODS.equals(productConfigModel.getIs_open())) {
                    updateProductConfig.setIs_open(ProductConfigModel.DISPLAY_BEAN_GOODS);
                }
                int count = productConfigModelMapper.updateByPrimaryKeySelective(updateProductConfig);
                if (count > 0) {
                    status = 1;
                }
            }

            resultMap.put("status", status);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("是否显示下架商品" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "isopen");
        }
        return resultMap;
    }


    @Autowired
    private PublicGoodsService publicGoodsService;

    @Autowired
    private PublicDictionaryService publicDictionaryService;

    @Autowired
    private ProductConfigModelMapper productConfigModelMapper;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

}
