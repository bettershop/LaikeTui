package com.laiketui.common.service.dubbo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.common.PublicGoodsService;
import com.laiketui.api.common.PublicStockService;
import com.laiketui.api.common.PubliceService;
import com.laiketui.api.common.admin.PublicAdminService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.Page;
import com.laiketui.domain.config.*;
import com.laiketui.domain.dictionary.DictionaryListModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.CartModel;
import com.laiketui.domain.mch.DistributionGradeModel;
import com.laiketui.domain.product.*;
import com.laiketui.domain.task.TaoBaoModel;
import com.laiketui.domain.user.UserFootprintModel;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.dic.DicVo;
import com.laiketui.domain.vo.goods.AddStockVo;
import com.laiketui.domain.vo.mch.UploadMerchandiseVo;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.LKTSnowflakeIdWorker;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.tool.TaobaoUtils;
import com.laiketui.root.utils.algorithm.DataAlgorithmTool;
import com.laiketui.root.utils.okhttp.HttpUtils;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 关于商品的公共类
 *
 * @author Trick
 * @date 2020/11/11 14:56
 */
@Service
public class PublicGoodsServiceImpl implements PublicGoodsService {

    private final Logger logger = LoggerFactory.getLogger(PublicGoodsServiceImpl.class);

    @Autowired
    private CustomerModelMapper customerModelMapper;

    @Autowired
    private ProLabelModelMapper proLabelModelMapper;

    @Override
    public Map<String, Object> addPage(int storeId, String adminName, int mchId, int type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //运费集
            List<Map<String, Object>> freightModelList = new ArrayList<>();
            //产品类型集
            List<Map<String, Object>> spTypeList = new ArrayList<>();
            //产品展示位
            List<Map<String, Object>> showAdrList = new ArrayList<>();
            //单位
            List<String> unitList = new ArrayList<>();
            //插件集
            Map<String, Object> pluginArrMap = new HashMap<>(16);


            LKTSnowflakeIdWorker snowflakeIdWorker = new LKTSnowflakeIdWorker();
            //添加商品编码
            ProductNumberModel productNumberModel = new ProductNumberModel();
            productNumberModel.setStore_id(storeId);
            productNumberModel.setMch_id(mchId + "");
            productNumberModel.setOperation(adminName);
            productNumberModel.setProduct_number(snowflakeIdWorker.nextId() + "");
            productNumberModel.setAddtime(new Date());
            int count = productNumberModelMapper.insertSelective(productNumberModel);
            if (count < 1) {
                logger.info("商品编码保存失败 参数:" + JSON.toJSONString(productNumberModel));
            }
            //获取运费
            FreightModel freightModel = new FreightModel();
            freightModel.setStore_id(storeId);
            freightModel.setMch_id(mchId);
            List<FreightModel> freightModels = freightModelMapper.select(freightModel);
            for (FreightModel freight : freightModels) {
                Map<String, Object> map = new HashMap<>(16);
                map.put("id", freight.getId());
                map.put("name", freight.getName());
                map.put("is_default", freight.getIs_default());
                freightModelList.add(map);
            }
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("status", 1);
            //获取产品类型
            ProLabelModel proLabelModel = new ProLabelModel();
            proLabelModel.setStore_id(storeId);
            List<ProLabelModel> proLabelModelList = proLabelModelMapper.select(proLabelModel);
            for (ProLabelModel proLabel : proLabelModelList) {
                Map<String, Object> goodsTypeMap = new HashMap<>(3);
                goodsTypeMap.put("name", proLabel.getName());
                goodsTypeMap.put("value", proLabel.getId());
                goodsTypeMap.put("status", false);
                spTypeList.add(goodsTypeMap);
            }
            //获取商品展示位
            parmaMap.put("name", "商品展示位置");
            List<Map<String, Object>> showAdrListTemp = dictionaryListModelMapper.getDictionaryDynamic(parmaMap);
            for (Map<String, Object> map : showAdrListTemp) {
                Map<String, Object> goodsShowAdrMap = new HashMap<>(3);
                goodsShowAdrMap.put("name", map.get("text"));
                goodsShowAdrMap.put("value", map.get("value"));
                goodsShowAdrMap.put("status", false);
                showAdrList.add(goodsShowAdrMap);
            }

            //获取单位
            parmaMap.put("name", "单位");
            List<Map<String, Object>> unitMap = dictionaryListModelMapper.getDictionaryDynamic(parmaMap);
            for (Map<String, Object> map : unitMap) {
                unitList.add(String.valueOf(map.get("text")));
            }
            //如果是平台则获取插件
            List<Map<String, Object>> pluginArr = new ArrayList<>();
            if (type == GloabConst.LktConfig.LKT_CONFIG_TYPE_PT) {
                //获取插件集...暂时不做
                //$Plugin_arr = $Plugin->product_plugin($db, $store_id, 'product', '');
            } else {
                //获取所有商品支持的活动
                pluginArr = publiceService.getGoodsActive(storeId);
            }
            pluginArrMap.put("active", pluginArr);

            resultMap.put("product_number", productNumberModel.getProduct_number());
            resultMap.put("plugin_list", pluginArrMap);
            resultMap.put("freight_list", freightModelList);
            resultMap.put("s_type", spTypeList);
            resultMap.put("show_adr", showAdrList);
            resultMap.put("unit", unitList);
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "addPage");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addPage");
        }

        return resultMap;
    }

    @Override
    public Map<String, Object> editPage(int storeId, String adminName, int mchId, int goodsId, int type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //运费信息 全部
            List<Map<String, Object>> freightModelList;
            //商品运费信息
            Map<String, Object> goodsFreightMap = new HashMap<>(16);
            //商品图片信息(封面图集合)
            List<String> productShowImgList = new ArrayList<>();
            //商品品牌信息 全部
            List<Map<String, Object>> productBrandList;

            //商品属性
            List<ProductClassModel> productClassList = new ArrayList<>();
            //插件集信息 Plugin_arr
            Map<String, Object> pluginArr = new HashMap<>(16);

            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setId(goodsId);
            //如果是平台，则查询商品信息无视店铺
            if (type != GloabConst.LktConfig.LKT_CONFIG_TYPE_PT) {
                //否则加店铺id
                productListModel.setMch_id(mchId);
            }
            productListModel = productListModelMapper.selectOne(productListModel);
            if (productListModel != null) {
                //获取图片信息
                String imgUrl = productListModel.getImgurl();
                String converMapUrl = productListModel.getCover_map();
                imgUrl = publiceService.getImgPath(imgUrl, storeId);
                productListModel.setImgurl(imgUrl);
                converMapUrl = publiceService.getImgPath(converMapUrl, storeId);
                productListModel.setCover_map(converMapUrl);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品不存在");
            }
            //获取店铺运费信息
            FreightModel freightModel = new FreightModel();
            freightModel.setStore_id(storeId);
            freightModel.setMch_id(mchId);
            freightModelList = freightModelMapper.getFreightInfo(freightModel);
            for (Map<String, Object> map : freightModelList) {
                boolean selected = false;
                String fid = map.get("id").toString();
                if (fid.equals(productListModel.getFreight())) {
                    selected = true;
                }
                map.put("selected", selected);
            }
            //获取商品封面图
            boolean flag = false;
            ProductImgModel productImgModel = new ProductImgModel();
            productImgModel.setProduct_id(goodsId);
            List<Map<String, Object>> productImgModelList = productImgModelMapper.getProductImgInfoByPid(productImgModel);
            if (GloabConst.LktConfig.LKT_CONFIG_TYPE_PT == type || GloabConst.LktConfig.LKT_CONFIG_TYPE_PC == type) {
                flag = true;
            }
            if (productImgModelList != null) {
                for (int i = 0; i < productImgModelList.size(); i++) {
                    Map<String, Object> map = productImgModelList.get(i);
                    String productUrl = map.get("product_url").toString();
                    productUrl = publiceService.getImgPath(productUrl, storeId);
                    productShowImgList.add(productUrl);
                    if (flag) {
                        int isCenter = productUrl.equals(productImgModel.getProduct_url()) ? 1 : 0;
                        map.put("is_center", isCenter);
                    } else {
                        map.clear();
                        map.put(i + "", productUrl);
                    }
                }
            }
            //商品分类+品牌处理
            String res = productListModel.getProduct_class();
            String[] resList = res.split("-");
            //商品所属分类顶级
            int classIdTop = Integer.parseInt(resList[1]);
            int classNum = resList.length - 1;
            //商品所属分类
            int classId = Integer.parseInt(resList[classNum]);
            for (String id : resList) {
                if (StringUtils.isEmpty(id)) {
                    continue;
                }
                ProductClassModel productClass = new ProductClassModel();
                productClass.setCid(Integer.parseInt(id));
                productClass = productClassModelMapper.selectOne(productClass);
                productClassList.add(productClass);
            }
            //获取当前品牌名称
            String brandName = null;
            BrandClassModel brandClassModel = new BrandClassModel();
            brandClassModel.setBrand_id(productListModel.getBrand_id());
            brandClassModel = brandClassModelMapper.selectOne(brandClassModel);
            if (brandClassModel != null) {
                brandName = brandClassModel.getBrand_name();
            }
            //获取顶级牌信息
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("status", 0);
            parmaMap.put("categories", classIdTop);
            productBrandList = brandClassModelMapper.getBrandClassDynamic(parmaMap);
            //商品主图
            Map<String, Object> defaultImgMap = new HashMap<>(16);
            defaultImgMap.put("product_url", productListModel.getImgurl());
            if (flag) {
                //品牌默认值
                Map<String, Object> defaultBrandMap = new HashMap<>(16);
                defaultBrandMap.put("brand_id", 0);
                defaultBrandMap.put("brand_name", "请选择品牌");
                if (productBrandList == null) {
                    productBrandList = new ArrayList<>();
                    productBrandList.add(defaultBrandMap);
                }
                defaultImgMap.put("is_center", 1);
            } else {
                //获取当前商品运费信息
                String fname = "";
                FreightModel goodsFreight = new FreightModel();
                goodsFreight.setId(Integer.parseInt(productListModel.getFreight()));
                goodsFreight = freightModelMapper.selectOne(goodsFreight);
                if (goodsFreight != null) {
                    fname = goodsFreight.getName();
                }
                goodsFreightMap.put("id", productListModel.getFreight());
                goodsFreightMap.put("name", fname);
            }
            //把商品主图放到图片列表中的第一个
            if (productImgModelList == null) {
                productImgModelList = new ArrayList<>();
                productImgModelList.add(defaultImgMap);
            } else {
                if (productImgModelList.size() < 1) {
                    productImgModelList.add(defaultImgMap);
                } else {
                    productImgModelList.set(0, defaultImgMap);
                }
            }

            //规格值集
            List<Map<String, Object>> strArrList = new ArrayList<>();
            List<Map<String, Object>> checkedAttrList = new ArrayList<>();
            List<Map<String, Object>> attrGroupList = new ArrayList<>();

            ConfiGureModel confiGureModel = new ConfiGureModel();
            confiGureModel.setPid(goodsId);
            confiGureModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
            List<ConfiGureModel> confiGureModelList = confiGureModelMapper.select(confiGureModel);
            if (confiGureModelList != null && confiGureModelList.size() > 0) {
                //规格处理
                String attribute = confiGureModelList.get(0).getAttribute();
                if (!StringUtils.isEmpty(attribute)) {
                    Map<String, Object> attributeMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(attribute, Map.class));
                    if (attributeMap != null) {
                        for (String key : attributeMap.keySet()) {
                            Map<String, Object> dataMap = new HashMap<>(16);
                            String attribyteKey = key;

                            int index = attribyteKey.indexOf("_LKT_");
                            if (index > 0) {
                                //属性名称
                                attribyteKey = attribyteKey.substring(0, attribyteKey.indexOf("_LKT_"));
                            }
                            dataMap.put("attr_group_name", attribyteKey);
                            attrGroupList.add(dataMap);
                        }
                    }
                    //属性名称集合,去重
                    List<String> tempList = new ArrayList<>();
                    for (ConfiGureModel confiGure : confiGureModelList) {
                        //当前属性信息
                        List<Map<String, Object>> attrLists = new ArrayList<>();
                        String attributeStr = confiGure.getAttribute();
                        Map<String, Object> attributeStrMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(attributeStr, Map.class));
                        if (attributeStrMap != null) {
                            for (String key : attributeStrMap.keySet()) {
                                String attributeKey = key;
                                String attribyteValue = attributeStrMap.get(key).toString();
                                int index = attributeKey.indexOf("_LKT_");
                                if (index > 0) {
                                    //获取id (尺码_LKT_8) 左边为名称 最后一个是id
                                    int keyId = attributeKey.lastIndexOf("-") + 1;
                                    int valueId = attribyteValue.indexOf("_") + 1;
                                    String keyIdTemp = attributeKey.substring(keyId);
                                    String valueIdTemp = attribyteValue.substring(0, valueId);
                                    Map<String, Object> dataMap = new HashMap<>(16);
                                    dataMap.put("id0", keyIdTemp);
                                    dataMap.put("id1", valueIdTemp);
                                    strArrList.add(dataMap);
                                    //获取名称
                                    attributeKey = attributeKey.substring(0, attributeKey.indexOf("_LKT"));
                                    attribyteValue = attribyteValue.substring(0, attribyteValue.indexOf("_LKT"));
                                }
                                for (Map<String, Object> map : attrGroupList) {
                                    //规格集 (attr_name:value,status:false)
                                    List<Map<String, Object>> attrList = new ArrayList<>();
                                    if (map.containsKey("attr_list")) {
                                        attrList = DataUtils.cast(map.get("attr_list"));
                                    }
                                    if (attributeKey.equals(map.get("attr_group_name").toString())) {
                                        //判断当前规格值是否存在数组中，不存在则添加
                                        if (!tempList.contains(attribyteValue)) {
                                            Map<String, Object> attrListMap = new HashMap<>(16);
                                            boolean isdsj = false;
                                            attrListMap.put("attr_name", attribyteValue);
                                            //商品是否待上架
                                            if (DictionaryConst.GoodsStatus.NOT_GROUNDING.toString().equals(productListModel.getStatus())) {
                                                isdsj = true;
                                            }
                                            attrListMap.put("status", isdsj);
                                            attrList.add(attrListMap);
                                            map.put("attr_list", attrList);
                                            tempList.add(attribyteValue);
                                        }
                                    }
                                }
                                Map<String, Object> attrListsMap = new HashMap<>(16);
                                attrListsMap.put("attr_id", "");
                                attrListsMap.put("attr_group_name", attributeKey);
                                attrListsMap.put("attr_name", attribyteValue);
                                attrLists.add(attrListsMap);
                            }
                        }

                        Map<String, Object> checkedAttrListMap = new HashMap<>(16);
                        checkedAttrListMap.put("attr_list", attrLists);
                        checkedAttrListMap.put("cbj", confiGure.getCostprice());
                        checkedAttrListMap.put("yj", confiGure.getYprice());
                        checkedAttrListMap.put("sj", confiGure.getPrice());
                        checkedAttrListMap.put("kucun", confiGure.getNum());
                        checkedAttrListMap.put("unit", confiGure.getUnit());
                        checkedAttrListMap.put("bar_code", confiGure.getBar_code());
                        checkedAttrListMap.put("img", publiceService.getImgPath(confiGure.getImg(), storeId));
                        checkedAttrListMap.put("cid", confiGure.getId());
                        checkedAttrList.add(checkedAttrListMap);
                    }
                }
            }

            //产品属性处理
            List<Map<String, Object>> sTypeList = new ArrayList<>();
            String sType = productListModel.getS_type();
            List<String> arr = new ArrayList<>();
            if (StringUtils.isNotEmpty(sType)) {
                arr = Arrays.asList(sType.split(","));
            }
            //展示位置
            List<Map<String, Object>> showAdrList;
            String showAdr = productListModel.getShow_adr();
            List<String> showAdrs = new ArrayList<>();
            if (!StringUtils.isEmpty(showAdr)) {
                showAdr = StringUtils.trim(showAdr, SplitUtils.DH);
                showAdrs = Arrays.asList(showAdr.split(","));
            }

            //获取商品展示位
            parmaMap.clear();
            parmaMap.put("status", 1);
            parmaMap.put("name", "商品展示位置");
            showAdrList = dictionaryListModelMapper.getDictionaryDynamic(parmaMap);
            for (Map<String, Object> map : showAdrList) {
                boolean status = false;
                String value = map.get("value").toString();
                String text = map.get("text").toString();
                map.clear();
                map.put("name", text);
                map.put("value", value);
                if (showAdrs.contains(value)) {
                    status = true;
                }
                map.put("status", status);
            }
            //获取商品类型
            ProLabelModel proLabelModel = new ProLabelModel();
            proLabelModel.setStore_id(storeId);
            List<ProLabelModel> proLabelModelList = proLabelModelMapper.select(proLabelModel);
            for (ProLabelModel proLabel : proLabelModelList) {
                Map<String, Object> spTypeMap = new HashMap<>(16);
                boolean status = false;
                spTypeMap.put("name", proLabel.getName());
                spTypeMap.put("value", proLabel.getId());
                if (arr.contains(proLabel.getId().toString())) {
                    status = true;
                }
                spTypeMap.put("status", status);
                sTypeList.add(spTypeMap);
            }
            //查询商品其它信息
            Map<String, Object> distributorsMap = new HashMap<>(16);
            if (GloabConst.LktConfig.LKT_CONFIG_TYPE_PT == type) {
                //获取插件 暂时不做...
//                $Plugin_arr = $Plugin->product_plugin($db, $store_id, 'product', $active, $distributor_id);
            } else {
                //获取商品支持的活动类型
                List<Map<String, Object>> activeMainList = publiceService.getGoodsActive(storeId, Integer.parseInt(productListModel.getActive()));
                pluginArr.put("active", activeMainList);
                //获取商品分销等级
                String dengjiName = "会员专区商品绑定等级";
                Integer fenxiaoId = productListModel.getDistributor_id();
                distributorsMap.put("id", fenxiaoId == null ? 0 : fenxiaoId);
                distributorsMap.put("name", dengjiName);
            }
            //获取商品初始值
            String initialStr = productListModel.getInitial();
            Map<String, String> initialMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(initialStr, Map.class));
            if (initialMap != null) {
                if (!initialMap.containsKey("stockWarn")) {
                    initialMap.put("stockWarn", productListModel.getMin_inventory().toString());
                }
            }
            //单位处理
            List<String> units = new ArrayList<>(16);
            List<Map<String, Object>> unitMapList = new ArrayList<>();
            parmaMap.clear();
            parmaMap.put("status", 1);
            parmaMap.put("name", "单位");
            List<Map<String, Object>> unitList = dictionaryListModelMapper.getDictionaryDynamic(parmaMap);
            for (Map<String, Object> map : unitList) {
                String unit = map.get("text").toString();
                if (GloabConst.LktConfig.LKT_CONFIG_TYPE_PC == type) {
                    boolean checked = false;
                    Map<String, Object> dataMap = new HashMap<>(16);
                    dataMap.put("name", unit);
                    if (initialMap != null) {
                        if (initialMap.get("unit").equals(unit)) {
                            checked = true;
                        }
                    }
                    dataMap.put("checked", checked);
                    unitMapList.add(dataMap);
                } else {
                    units.add(unit);
                }
            }

            resultMap.put("mch_id", mchId);
            resultMap.put("list", productListModel);
            resultMap.put("product_title", productListModel.getProduct_title());
            resultMap.put("subtitle", productListModel.getSubtitle());
            resultMap.put("keyword", productListModel.getKeyword());
            resultMap.put("weight", productListModel.getWeight());
            resultMap.put("product_number", productListModel.getProduct_number());
            resultMap.put("class_id", classId);
            resultMap.put("ctypes", productClassList);
            resultMap.put("brand_class", productBrandList);
            resultMap.put("brand_id", productListModel.getBrand_id());
            resultMap.put("imgurls", productShowImgList);
            resultMap.put("initial", initialMap);
            resultMap.put("status", productListModel.getStatus());
            if (units.size() > 0) {
                resultMap.put("unit", units);
            } else {
                resultMap.put("unit", unitMapList);
            }
            resultMap.put("attr_group_list", attrGroupList);
            resultMap.put("checked_attr_list", checkedAttrList);
            resultMap.put("min_inventory", productListModel.getMin_inventory());
            resultMap.put("freight_list", freightModelList);
            resultMap.put("sp_type", sTypeList);
            resultMap.put("active", productListModel.getActive());
            resultMap.put("Plugin_arr", pluginArr);
            resultMap.put("show_adr", showAdrList);
            resultMap.put("content", productListModel.getContent());
            resultMap.put("brand_name", brandName);
            resultMap.put("brand_class_list1", brandClassModel);
            resultMap.put("freight_list1", goodsFreightMap);
            resultMap.put("distributors", null);
            resultMap.put("distributors1", distributorsMap);
            resultMap.put("status", productListModel.getStatus());
            resultMap.put("richList", productListModel.getRichList());
            resultMap.put("strArr", strArrList);
            resultMap.put("cover_map", productListModel.getCover_map());
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据错误");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品明细数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "editPage");
        }

        return resultMap;
    }


    @Override
    public Map<String, Object> productList(int storeId, String adminName, int mchId, int type, Map<String, Object> map) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //结果集
            List<Map<String, Object>> list = new ArrayList<>();

            int pageNo = Integer.parseInt(map.get("page").toString());
            int pageSize = Integer.parseInt(map.get("pagesize").toString());
            Page page = Page.newBuilder(pageNo, pageSize, null);
            String pageTo = "";
            if (map.containsKey("pageto")) {
                pageTo = map.get("pageto").toString();
            }
            //分类 当前级别id+上级id : 当前级别名称
            Map<String, Object> productClassMap = new HashMap<>(16);
            //品牌
            Map<Integer, Object> brandClassMap = new HashMap<>(16);

            //分类集
            List<Map<String, Object>> productClassList = new ArrayList<>();
            //品牌集
            List<Map<String, Object>> brandList = new ArrayList<>();

            //获取分类下拉
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setStore_id(storeId);
            productClassModel.setSid(0);
            //获取所有分类 数据结构 -3-31-191-
            List<ProductClassModel> productClassModelAll = productClassModelMapper.getProductClassLevel(productClassModel);
            for (ProductClassModel productClassAll : productClassModelAll) {
                String coneCid = "-" + productClassAll.getCid() + "-";
                productClassMap.put(coneCid, productClassAll.getPname());
                //获取第一级
                productClassModel.setSid(productClassAll.getCid());
                List<ProductClassModel> productClassModeLeve1 = productClassModelMapper.getProductClassLevel(productClassModel);
                for (ProductClassModel productClassLeve1 : productClassModeLeve1) {
                    coneCid += productClassAll.getCid() + "-";
                    productClassMap.put(coneCid, productClassLeve1.getPname());
                    //获取第二级
                    productClassModel.setSid(productClassLeve1.getCid());
                    List<ProductClassModel> productClassModeLeve2 = productClassModelMapper.getProductClassLevel(productClassModel);
                    for (ProductClassModel productClass2 : productClassModeLeve2) {
                        coneCid += productClass2.getCid() + "-";
                        productClassMap.put(coneCid, productClass2.getPname());
                    }
                }
            }
            //获取品牌下拉
            BrandClassModel brandClassModel = new BrandClassModel();
            brandClassModel.setStore_id(storeId);
            brandClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            List<BrandClassModel> brandClassModelList = brandClassModelMapper.select(brandClassModel);
            for (BrandClassModel brandClass : brandClassModelList) {
                brandClassMap.put(brandClass.getBrand_id(), brandClass.getBrand_name());
            }

            //sql参数列表 导出全部
            Map<String, Object> parmaMap1 = new HashMap<>(16);
            //sql参数列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            if (type == GloabConst.LktConfig.LKT_CONFIG_TYPE_PT) {
                //1、先按照状态排序，优先显示待上架商品，待上架商品则按照发布时间排序；2、已上架和已下架商品则按照上架时间排序
                parmaMap.put("diy_sort", DataUtils.Sort.DESC.toString());
            } else {
                if (type == GloabConst.LktConfig.LKT_CONFIG_TYPE_PC) {
                    parmaMap.put("diy_sort", DataUtils.Sort.DESC.toString());
                } else {
                    parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());
                }
                String mchStatusTemp = map.get("mch_status") + "";
                if (!StringUtils.isEmpty(mchStatusTemp)) {
                    parmaMap.put("mch_id", mchId);
                    //商品类型 虚拟/实物
                    if (map.containsKey("commodity_type")) {
                        parmaMap.put("commodity_type", map.get("commodity_type"));
                    }
                    int mchStatus = Integer.parseInt(mchStatusTemp);
                    if (mchStatus == 1) {
                        //商品列表只显示已审核通过的商品
                        parmaMap.put("mch_status", DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS);
                    } else if (mchStatus == 2) {
                        //mch_status != 2
                        parmaMap.put("mch_status", -2);
                    } else if (mchStatus == 3) {
                        parmaMap.put("mch_status", DictionaryConst.GoodsMchExameStatus.EXAME_NOT_PASS_STATUS);
                    } else if (mchStatus == 4) {
                        parmaMap.put("mch_status", DictionaryConst.GoodsMchExameStatus.EXAME_STOP_STATUS);
                    } else if (mchStatus == 5) {
                        //待审核的
                        parmaMap.put("mch_status", DictionaryConst.GoodsMchExameStatus.EXAME_WAIT_STATUS);
                    }
                }
            }
            parmaMap1.putAll(parmaMap);

            //是否需要查询商品分类
            if (map.containsKey("product_class")) {
                Map<String, Object> brandMap = new HashMap<>(16);
                brandMap.put("brand_id", "0");
                brandMap.put("brand_name", "请选择品牌");

                int classId = Integer.parseInt(map.get("product_class").toString());
                if (classId > 0) {
                    //商品分类递归找上级
                    String classIdListStr = strOption(storeId, classId, "");
                    String[] classIds = StringUtils.trim(classIdListStr, "-").split("-");
                    //查询子分类要带出上级,查上级不能带出下级数据
                    parmaMap.put("product_class", classIds[classIds.length - 1]);
                    //商品分类顶级
                    int classIdTop = Integer.parseInt(classIds[0]);
                    for (String cid : classIds) {
                        int id = Integer.parseInt(cid);
                        ProductClassModel product = new ProductClassModel();
                        product.setStore_id(storeId);
                        product.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                        product.setCid(id);
                        product = productClassModelMapper.selectOne(product);
                        if (product != null) {
                            productClassList.add(brandMap);
                        }
                    }
                    //获取顶级类别品牌
                    Map<String, Object> parmaBrandMap = new HashMap<>(16);
                    parmaBrandMap.put("store_id", storeId);
                    parmaBrandMap.put("status", 0);
                    parmaBrandMap.put("categories", classIdTop);
                    brandList = brandClassModelMapper.getBrandClassDynamic(parmaBrandMap);
                }
                brandList.add(0, brandMap);
            }
            //商品Id
            if (map.containsKey("goodsId")) {
                Integer goodsId = MapUtils.getInteger(map, "goodsId");
                parmaMap.put("goodsId", goodsId);
            }
            //是否需要查询品牌id
            if (map.containsKey("brand_id")) {
                int brandIdTemp = Integer.parseInt(map.get("brand_id").toString());
                if (brandIdTemp != 0) {
                    parmaMap.put("brand_id", brandIdTemp);
                }
            }
            //是否需要查询商品上架状态
            if (map.containsKey("status")) {
                int statusTemp = Integer.parseInt(map.get("status").toString());
                if (statusTemp != 0) {
                    parmaMap.put("goodsStatus", statusTemp);
                }
            } else if (map.containsKey("statusList")) {
                List<Integer> statusList = DataUtils.cast(map.get("statusList"));
                if (statusList != null) {
                    parmaMap.put("statusList", statusList);
                }
            }
            //是否需要查询商品互动状态
            if (map.containsKey("active")) {
                int activeTemp = Integer.parseInt(map.get("active").toString());
                if (activeTemp != 0) {
                    parmaMap.put("active", activeTemp);
                }
            }
            //是否需要查询商品标题
            if (map.containsKey("product_title")) {
                String productTitle = map.get("product_title") + "";
                if (!StringUtils.isEmpty(productTitle)) {
                    if (productTitle.indexOf(" ") > 0) {
                        //多个标题搜索
                        List<String> productTitleList = Arrays.asList(productTitle.split(" "));
                        parmaMap.put("productTitleList", productTitleList);
                    } else {
                        parmaMap.put("product_title", productTitle);
                    }
                }
            }
            //是否需要查询店铺名称
            if (map.containsKey("mch_name")) {
                String mchName = map.get("mch_name") + "";
                if (StringUtils.isNotEmpty(mchName)) {
                    parmaMap.put("mch_name", mchName);
                }
            }
            if (map.containsKey("mchNameOrGoodsName")) {
                String key = map.get("mchNameOrGoodsName") + "";
                if (StringUtils.isNotEmpty(key)) {
                    parmaMap.put("mchNameOrGoodsName", key);
                }
            }
            //是否需要查询展示位置
            if (map.containsKey("show_adr")) {
                String showAdr = map.get("show_adr") + "";
                if (StringUtils.isNotEmpty(showAdr)) {
                    parmaMap.put("show_adr", showAdr);
                }
            }
            //是否需要查询商品标签
            if (map.containsKey("goodsTga")) {
                String showAdr = map.get("goodsTga") + "";
                if (StringUtils.isNotEmpty(showAdr)) {
                    parmaMap.put("s_type", showAdr);
                }
            }

            boolean isAll = false;
            if ("whole".equals(pageTo)) {
                //导出全部
                isAll = true;
            } else {
                //导出查询无需分页
                if (!"inquiry".equals(pageTo)) {
                    //导出本页 This_page
                    parmaMap.put("page", page.getPageNo());
                    parmaMap.put("pageSize", page.getPageSize());
                }
            }
            //根据条件统计
            int total = productListModelMapper.getProductListLeftJoinMchCountDynamic(parmaMap);
            List<Map<String, Object>> productList;
            if (isAll) {
                //导出查询
                productList = productListModelMapper.getProductListLeftJoinMchDynamic(parmaMap1);
            } else {
                productList = productListModelMapper.getProductListLeftJoinMchDynamic(parmaMap);
            }
            if (productList != null) {
                for (int i = 0; i < productList.size(); i++) {
                    Map<String, Object> goodMap = productList.get(i);
                    int goodsId = Integer.parseInt(goodMap.get("id").toString());
                    String classIds = goodMap.get("product_class").toString();
                    int brandId = Integer.parseInt(goodMap.get("brand_id").toString());
                    String initialStr = goodMap.get("initial").toString();
                    Map<String, String> initialMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(initialStr, Map.class));
                    if (initialMap == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据错误");
                    }
                    BigDecimal presentPrice = new BigDecimal(initialMap.get("sj"));
                    String unit = initialMap.get("unit");

                    //是否有未完成的订单
                    Integer rew = chaxun(goodsId, storeId);
                    goodMap.put("rew", 0);
                    if (rew != null) {
                        if (rew == 2) {
                            goodMap.put("rew", 2);
                        } else if (rew == 3) {
                            goodMap.put("rew", 3);
                        }
                    }
                    //获取上一条数据的id
                    Integer supperGoodsId = null;
                    boolean supperStatus = false;
                    if (i == 0) {
                        //第一页没有上一条
                        if (page.getPageNo() > 1) {
                            //获取上一页最后一条数据
                            Map<String, Object> parmaTempMap = new HashMap<>(16);
                            parmaTempMap.putAll(parmaMap);
                            parmaTempMap.put("page", page.getPageNo() - 1);
                            parmaTempMap.put("pageSize", 1);
                            //获取商品信息
                            List<Map<String, Object>> goodsFirstList = productListModelMapper.getProductListLeftJoinMchDynamic(parmaTempMap);
                            Map<String, Object> goodsFirstMap = goodsFirstList.get(0);
                            supperGoodsId = Integer.parseInt(goodsFirstMap.get("id").toString());
                            supperStatus = true;
                        }
                    } else {
                        //上一条数据id
                        supperGoodsId = Integer.parseInt(productList.get(i - 1).get("id").toString());
                        supperStatus = true;
                    }
                    goodMap.put("upper_status", supperStatus);
                    //获取下一条数据
                    Integer underneathGoodsId = null;
                    if (i == productList.size() - 1) {
                        Map<String, Object> parmaTempMap = new HashMap<>(16);
                        parmaTempMap.putAll(parmaMap);
                        parmaTempMap.put("pageSize", 1);
                        //当为最后一条数据
                        if (pageNo == 1) {
                            parmaTempMap.put("page", page.getPageNo());
                        } else {
                            int start = page.getPageNo() + page.getPageSize();
                            parmaTempMap.put("page", start);
                        }
                        //获取商品信息
                        List<Map<String, Object>> goodsFirstList = productListModelMapper.getProductListLeftJoinMchDynamic(parmaTempMap);
                        if (goodsFirstList.size() > 0) {
                            Map<String, Object> goodsFirstMap = goodsFirstList.get(0);
                            underneathGoodsId = Integer.parseInt(goodsFirstMap.get("id").toString());
                        }
                    } else {
                        underneathGoodsId = Integer.parseInt(productList.get(i + 1).get("id").toString());
                    }
                    goodMap.put("upper_id", supperGoodsId);
                    goodMap.put("underneath_id", underneathGoodsId);
                    String stype = goodMap.get("s_type") + "";
                    if (StringUtils.isNotEmpty(stype)) {
                        String[] stypeList = stype.split(",");
                        goodMap.put("s_type", stypeList);
                    }
                    //展示位置处理
                    String showAdr = goodMap.get("show_adr") + "";
                    List<String> showAdrNameList = new ArrayList<>();
                    if (StringUtils.isNotEmpty(showAdr)) {
                        showAdr = StringUtils.trim(showAdr, SplitUtils.DH);
                        String[] showAdrList = showAdr.split(",");
                        goodMap.put("showAdrList", showAdrList);
                        DicVo dicVo = new DicVo();
                        dicVo.setName("商品展示位置");
                        dicVo.setShowChild(true);
                        dicVo.setShowChild(true);
                        for (String showAdrId : showAdrList) {
                            dicVo.setValue(showAdrId);
                            Map<String, Object> showAdrMap = publicDictionaryService.getDictionaryByName(dicVo);
                            List<DictionaryListModel> showList = DataUtils.cast(showAdrMap.get("value"));
                            if (showList != null) {
                                for (DictionaryListModel dic : showList) {
                                    showAdrNameList.add(dic.getValue());
                                }
                            }
                        }
                    }
                    if (showAdrNameList.size() < 1) {
                        showAdrNameList.add("全部商品");
                    }
                    goodMap.put("showAdrNameList", showAdrNameList);
                    //总平台的商品分类名称
                    String commodityClassification = "";
                    //总平台的商品品牌名称
                    String commodityBrand = "";
                    //店主分类
                    String shopkeepersClassification = "";
                    if (StringUtils.isNotEmpty(classIds)) {
                        //-394-395-396- 转换成数组需要 -1
                        String[] classIdList = StringUtils.trim(classIds, "-").split("-");
                        int classId = Integer.parseInt(classIdList[classIdList.length - 1]);
                        ProductClassModel p = new ProductClassModel();
                        p.setStore_id(storeId);
                        p.setCid(classId);
                        p = productClassModelMapper.selectOne(p);
                        if (p != null) {
                            commodityClassification = p.getPname();
                        }
                        BrandClassModel b = new BrandClassModel();
                        b.setStore_id(storeId);
                        b.setBrand_id(brandId);
                        b = brandClassModelMapper.selectOne(b);
                        if (b != null) {
                            commodityBrand = b.getBrand_name();
                        }
                        goodMap.put("pname", commodityClassification);
                        goodMap.put("brand_name", commodityBrand);
                    }

                    //查询库存信息
                    ConfiGureModel confiGureModel = new ConfiGureModel();
                    Integer goodStockNum = confiGureModelMapper.countConfigGureNum(goodsId);
                    goodMap.put("num", goodStockNum);
                    //商品图片
                    String cgImg = "";
                    //获取价格
                    confiGureModel = confiGureModelMapper.getProductMinPriceAndMaxYprice(goodsId);
                    if (confiGureModel != null) {
                        unit = confiGureModel.getUnit();
                        presentPrice = confiGureModel.getPrice();
                    }

                    //获取商品主图
                    goodMap.put("imgurl", publiceService.getImgPath(productImgModelMapper.getProductImg(goodsId), storeId));
                    goodMap.put("unit", unit);
                    goodMap.put("price", presentPrice);
                    String statusName = "";
                    int status = Integer.parseInt(goodMap.get("status").toString());
                    if (DictionaryConst.GoodsStatus.NOT_GROUNDING == status) {
                        statusName = "待上架";
                    } else if (DictionaryConst.GoodsStatus.NEW_GROUNDING == status) {
                        statusName = "上架";
                    } else if (DictionaryConst.GoodsStatus.OFFLINE_GROUNDING == status) {
                        statusName = "下架";
                    }
                    goodMap.put("status_name", statusName);
                    goodMap.put("upper_shelf_time", goodMap.get("upper_shelf_time"));

                    list.add(goodMap);
                }
            }


            resultMap.put("list", list);
            resultMap.put("total", total);
        } catch (ClassCastException c) {
            c.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "productList");
        } catch (LaiKeApiException l) {
            logger.error("自定义异常 :", l);
            throw l;
        } catch (Exception e) {
            logger.error("商品列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "productList");
        }

        return resultMap;
    }


    @Override
    public String strOption(int storeId, int cid, String res) throws LaiKeApiException {
        try {
            logger.info("cid={} res={} 递归找上级", cid, res);
            //获取商品类别
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setCid(cid);
            productClassModel.setStore_id(storeId);
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            productClassModel = productClassModelMapper.selectOne(productClassModel);
            if (productClassModel != null) {
                int sid = productClassModel.getSid();
                res = cid + "-" + res;
                if (sid == 0) {
                    res = "-" + res;
                } else {
                    res = this.strOption(storeId, sid, res);
                }
            }
            return res;
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "strOption");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("递归商品类别 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "strOption");
        }
    }

    @Override
    public void getClassLevelAllInfo(int storeId, int cid, Map<Integer, List<ProductClassModel>> resultMap) throws LaiKeApiException {
        try {
            List<ProductClassModel> resultList = new ArrayList<>();
            //获取当前类别
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setCid(cid);
            productClassModel.setStore_id(storeId);
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            productClassModel = productClassModelMapper.selectOne(productClassModel);
            if (productClassModel != null) {
                resultList.add(productClassModel);
                resultMap.put(productClassModel.getLevel(), resultList);
                //获取上级类别
                int sid = productClassModel.getSid();
                //0代表最顶级
                if (sid != 0) {
                    this.getClassLevelAllInfo(storeId, sid, resultMap);
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("递归商品类别 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "strOption");
        }
    }

    @Override
    public List<Map<String, Object>> getClassLevelLowAll(int storeId, int sid) throws LaiKeApiException {
        try {
            List<Map<String, Object>> resultList = new ArrayList<>();
            //找下级
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setStore_id(storeId);
            productClassModel.setSid(sid);
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            List<ProductClassModel> productClassModelList = productClassModelMapper.select(productClassModel);
            for (ProductClassModel productClass : productClassModelList) {
                Map<String, Object> classTopMap = new HashMap<>(16);
                classTopMap.put("cid", productClass.getCid());
                classTopMap.put("sid", productClass.getSid());
                classTopMap.put("level", productClass.getLevel());
                classTopMap.put("cname", productClass.getPname());
                //递归获取下级
                classTopMap.put("child", getClassLevelLowAll(storeId, productClass.getCid()));
                resultList.add(classTopMap);
            }
            return resultList;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("递归商品类别下级 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getClassLevelLowAll");
        }
    }

    @Override
    public Integer getClassTop(int storeId, int cid) throws LaiKeApiException {
        try {
            //获取当前类别
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setCid(cid);
            productClassModel.setStore_id(storeId);
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            productClassModel = productClassModelMapper.selectOne(productClassModel);
            if (productClassModel != null) {
                //获取上级类别
                int sid = productClassModel.getSid();
                //0代表最顶级
                if (sid != 0) {
                    //继续获取上级
                    return this.getClassTop(storeId, sid);
                }
                return productClassModel.getCid();
            }
            return 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("递归商品类别 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "strOption");
        }
    }

    @Override
    public Integer chaxun(int id, int storeId) throws LaiKeApiException {
        try {
            //获取当前商品信息
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setActive("all");
            productListModel.setMch_status(DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS.toString());
            productListModel.setId(id);
            productListModel = productListModelMapper.selectOne(productListModel);
            if (productListModel != null) {
                //判断当前商品是否上架
                if (productListModel.getStatus().equals(DictionaryConst.GoodsStatus.NEW_GROUNDING.toString())) {
                    Map<String, Object> parmaMap = new HashMap<>();
                    parmaMap.put("store_id", storeId);
                    parmaMap.put("mch_status", DictionaryConst.GoodsStatus.NEW_GROUNDING);
                    parmaMap.put("r_status", storeId);
                    List<Integer> rstatusList = new ArrayList<>();
                    rstatusList.add(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID);
                    rstatusList.add(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_CONSIGNMENT);
                    rstatusList.add(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_DISPATCHED);
                    parmaMap.put("rstatusList", rstatusList);
                    List<Map<String, Object>> resultGoodsList = productListModelMapper.getProductListJoinOrderDetailsDynamic(parmaMap);
                    if (resultGoodsList != null) {
                        return 3;
                    }
                }
                return 1;
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "chaxun");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("chaxun 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "chaxun");
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean executeTaoBaoById(List<Integer> widList) throws LaiKeApiException {
        try {
            Object savePoint;
            for (int wid : widList) {
                //标记任务执行中
                TaoBaoWorkModel taoBaoWorkModel = new TaoBaoWorkModel();
                taoBaoWorkModel.setId(wid);
                taoBaoWorkModel.setStatus(TaoBaoWorkModel.TAOBAO_STATUS_GETTING_ING);
                int count = taoBaoWorkModelMapper.updateByPrimaryKeySelective(taoBaoWorkModel);
                if (count > 0) {
                    //获取待爬取的任务
                    TaoBaoModel taoBaoModel = new TaoBaoModel();
                    taoBaoModel.setW_id(wid);
                    taoBaoModel.setStatus(TaoBaoModel.TAOBAO_STATUS_TO_BE_OBTAINED);
                    List<TaoBaoModel> taoBaoModelList = taoBaoModelMapper.select(taoBaoModel);
                    for (TaoBaoModel taobaoTask : taoBaoModelList) {
                        //标记获取中
                        TaoBaoModel updateTaoBao = new TaoBaoModel();
                        updateTaoBao.setId(taobaoTask.getId());
                        updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_GETTING_ING);
                        count = taoBaoModelMapper.updateByPrimaryKeySelective(updateTaoBao);
                        if (count > 0) {
                            //设置回滚点
                            savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                            updateTaoBao = getTaoBaoData(taobaoTask);
                            if (TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL.equals(updateTaoBao.getStatus())) {
                                logger.debug(">>>>>>>淘宝抓取失败,回滚中<<<<<<<");
                                TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                            }
                            TaoBaoWorkModel taoBaoWorkUpdate = new TaoBaoWorkModel();
                            taoBaoWorkUpdate.setId(taoBaoModel.getW_id());
                            taoBaoWorkUpdate.setAdd_date(new Date());
                            count = taoBaoWorkModelMapper.updateByPrimaryKeySelective(taoBaoWorkUpdate);
                            count = taoBaoModelMapper.updateByPrimaryKeySelective(updateTaoBao);
                            logger.debug(">>>>>>>标记获取状态 结果{}<<<<<<<", count > 0);
                        }
                    }
                    //标记执行完毕
                    taoBaoWorkModel.setStatus(TaoBaoWorkModel.TAOBAO_STATUS_CRAWLING_SUCCESS);
                } else {
                    taoBaoWorkModel.setStatus(TaoBaoWorkModel.TAOBAO_STATUS_CRAWLING_FAIL);
                }
                taoBaoWorkModelMapper.updateByPrimaryKeySelective(taoBaoWorkModel);
            }
        } catch (LaiKeApiException l) {
            l.printStackTrace();
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean restoreExecuteTaoBaoById(int taskId) throws LaiKeApiException {
        try {
            //获取任务信息
            TaoBaoModel taoBaoModel = new TaoBaoModel();
            taoBaoModel.setId(taskId);
            taoBaoModel = taoBaoModelMapper.selectOne(taoBaoModel);
            if (taoBaoModel != null) {
                //设置回滚点
                Object savePoint = TransactionAspectSupport.currentTransactionStatus().createSavepoint();
                TaoBaoModel updateTaoBao = getTaoBaoData(taoBaoModel);
                if (TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL.equals(updateTaoBao.getStatus())) {
                    logger.debug(">>>>>>>淘宝抓取失败,回滚中<<<<<<<");
                    TransactionAspectSupport.currentTransactionStatus().rollbackToSavepoint(savePoint);
                }
                TaoBaoWorkModel taoBaoWorkUpdate = new TaoBaoWorkModel();
                taoBaoWorkUpdate.setId(taoBaoModel.getW_id());
                taoBaoWorkUpdate.setAdd_date(new Date());
                taoBaoWorkModelMapper.updateByPrimaryKeySelective(taoBaoWorkUpdate);
                int count = taoBaoModelMapper.updateByPrimaryKeySelective(updateTaoBao);
                logger.debug(">>>>>>>标记获取状态 结果{}<<<<<<<", count > 0);
                return true;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "任务不存在", "restoreExecuteTaoBaoById");
            }
        } catch (LaiKeApiException l) {
            l.printStackTrace();
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 淘宝抓取商品
     * 【TaskServiceImpl.getTaoBaoData 请保持一致】
     *
     * @param taoBaoModel -
     * @return TaoBaoModel
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/1 13:52
     */
    private TaoBaoModel getTaoBaoData(TaoBaoModel taoBaoModel) throws LaiKeApiException {
        TaoBaoModel updateTaoBao = new TaoBaoModel();
        try {
            //标记获取中
            updateTaoBao.setId(taoBaoModel.getId());
            updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_GETTING_ING);
            taoBaoModelMapper.updateByPrimaryKeySelective(updateTaoBao);
            //检查商品是否重复
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(taoBaoModel.getStore_id());
            productListModel.setProduct_number(taoBaoModel.getItemid());
            productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
            int count = productListModelMapper.selectCount(productListModel);
            if (count < 0) {
                updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
                updateTaoBao.setMsg("请勿重复抓取");
                logger.debug("请勿重复抓取 商品参数:{}", JSON.toJSONString(productListModel));
                return updateTaoBao;
            }
            //获取客户店铺信息
            AdminModel adminModel = new AdminModel();
            adminModel.setStore_id(taoBaoModel.getStore_id());
            adminModel.setType(1);
            adminModel = adminModelMapper.selectOne(adminModel);
            if (adminModel == null) {
                logger.debug("客户店铺不存在{}", JSON.toJSONString(adminModel));
                updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
                updateTaoBao.setMsg("业务数据错误");
                taoBaoModelMapper.updateByPrimaryKeySelective(updateTaoBao);
                return updateTaoBao;
            }

            //开始获取淘宝商品数据
            String url = String.format(GloabConst.OtherUrl.API_TAOBAO_GOODSDATA_URL, taoBaoModel.getItemid());
            String detailUrl = String.format(GloabConst.OtherUrl.API_TAOBAO_GOODSDATA_DETAIL_URL, taoBaoModel.getItemid());

            //爬取网页
            TaobaoUtils.trustEveryone();

            //商品详情
            Connection.Response response = Jsoup
                    .connect(detailUrl)
                    .validateTLSCertificates(false)
                    .timeout(TaobaoUtils.TIME_OUT)
                    .method(Connection.Method.GET)
                    .userAgent(TaobaoUtils.USER_AGENT)
                    .execute();
            Document goodsDetailDom = response.parse();

            //商品规格
            response = Jsoup
                    .connect(url)
                    .validateTLSCertificates(false)
                    .timeout(TaobaoUtils.TIME_OUT)
                    .method(Connection.Method.GET)
                    .userAgent(TaobaoUtils.USER_AGENT)
                    .execute();
            Document document = response.parse();

            //获取页面所有属性类型>值
            Elements goodsAttrDom = document.select("#J_isku>.tb-skin>dl>dd>ul");
            if (goodsAttrDom.size() < 1) {
                logger.debug("淘宝抓取失败 网页内容: {}", goodsAttrDom.html());
                logger.debug(">>>>>商品规格信息爬取失败--【商品已经下架或者不是淘宝商品】<<<<<");
                updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
                updateTaoBao.setMsg("商品规格信息爬取失败【商品已经下架或者不是淘宝商品】");
                return updateTaoBao;
            }

            //保存商品
            ProductListModel saveProductListModel = new ProductListModel();
            saveProductListModel.setStore_id(taoBaoModel.getStore_id());
            saveProductListModel.setWeight("0");
            saveProductListModel.setScan(taoBaoModel.getItemid());
            saveProductListModel.setProduct_number(taoBaoModel.getItemid());
            saveProductListModel.setProduct_class(taoBaoModel.getCid());
            saveProductListModel.setBrand_id(taoBaoModel.getBrand_id());
            saveProductListModel.setFreight("0");
            saveProductListModel.setMch_id(adminModel.getShop_id());
            saveProductListModel.setMch_status(DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS.toString());
            saveProductListModel.setPublisher("task");
            saveProductListModel.setRichList("");
            //抓取的商品统一推荐
            saveProductListModel.setS_type(String.format(",%s,", DictionaryConst.LKT_SPLX_003));
            //抓取展示位置统一全部商品
            saveProductListModel.setShow_adr(String.format(",%s,", DictionaryConst.GoodsShowAdr.GOODSSHOWADR_DEFAULT));

            //产品详情
            saveProductListModel.setContent(getGoodsContext(document));
            //关键词
            String goodsKeywords = goodsDetailDom.getElementsByAttributeValue("name", "keywords").attr("content");
            saveProductListModel.setKeyword(goodsKeywords);

            //产品标题
            String goodsTitle = document.select("#J_Title>.tb-main-title").attr("data-title");
            saveProductListModel.setProduct_title(goodsTitle);

            ProductListModel productListCount = new ProductListModel();
            productListCount.setProduct_title(goodsTitle);
            productListCount.setStore_id(taoBaoModel.getStore_id());
            productListCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
            count = productListModelMapper.selectCount(productListCount);
            if (count > 0) {
                updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
                updateTaoBao.setMsg(String.format("商品【%s】 已存在", goodsTitle));
                return updateTaoBao;
            }
            //产品图片
            Elements goodsImages = document.select("#J_UlThumb>li");
            //商品轮播图集
            List<String> goodsImageUrls = new ArrayList<>();
            for (Element element : goodsImages) {
                String imageUrl = GloabConst.HttpProtocol.HTTPS + ":" + element.select("div>a>img").attr("data-src");
                imageUrl = imageUrl.replace(".jpg_50x50.jpg", ".jpg_250x250.jpg");
                //上传外链图片
                String imageName = publiceService.uploadImage(imageUrl, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_001), taoBaoModel.getStore_id());
                goodsImageUrls.add(ImgUploadUtils.getUrlImgByName(imageName, true));
            }
            //产品主图
            String goodsImageMain = document.getElementById("J_ImgBooth").attr("src");
            //上传产品主图
            String goodsImageMainName = publiceService.uploadImage(GloabConst.HttpProtocol.HTTPS + ":" + goodsImageMain, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_001), taoBaoModel.getStore_id());
            saveProductListModel.setImgurl(ImgUploadUtils.getUrlImgByName(goodsImageMainName, true));
            saveProductListModel.setCover_map(saveProductListModel.getImgurl());

            //商品价格
            BigDecimal goodsPrice = new BigDecimal(document.select("#J_FrmBid>input[name='current_price']").attr("value"));
            //价格信息 a:5:{s:3:"cbj";s:3:"200";s:2:"yj";s:4:"2000";s:2:"sj";s:4:"1179";s:4:"unit";s:3:"件";s:5:"kucun";s:5:"10000";}
            Map<String, String> initialMap = new HashMap<>(16);
            initialMap.put("cbj", goodsPrice.toString());
            initialMap.put("yj", goodsPrice.toString());
            initialMap.put("sj", goodsPrice.toString());
            initialMap.put("unit", "件");
            initialMap.put("kucun", "0");
            saveProductListModel.setInitial(SerializePhpUtils.JavaSerializeByPhp(initialMap));

            //获取库存规格信息
            String pattern = "(?s)skuMap(.*?):(.*?)}\\);";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(document.html());
            while (m.find()) {
                logger.debug(">>>>>网页爬取开始<<<<<");
                String skuMapStr = "{" + StringUtils.trim(m.group());
                skuMapStr = skuMapStr.replace("skuMap", "\"skuMap\"");
                skuMapStr = skuMapStr.replace("propertyMemoMap", "\"propertyMemoMap\"");
                skuMapStr = skuMapStr.substring(0, skuMapStr.length() - 3);
                //爬取的数据结构 - 所有规格排列组合信息
                Map<String, Map<String, Object>> skuJsonMap = JSON.parseObject(skuMapStr, new TypeReference<Map<String, Map<String, Object>>>() {
                });
                //商品规格信息 结构: {;节点;节点;节点... : {价格信息}}
                Map<String, Object> skuMap = skuJsonMap.get("skuMap");

                String[][] groupNodes = new String[goodsAttrDom.size()][];
                int x = 0;
                for (Element attrNameAll : goodsAttrDom) {
                    logger.debug(">>>网页父节点{}爬取开始<<<", x);
                    //属性名称
                    String attrName = attrNameAll.attr("data-property");
                    //属性值节点
                    Elements goodsAttrValueDom = attrNameAll.select("li");
                    //属性集合
                    Map<String, Object> attrMap = new HashMap<>(16);
                    //节点id集合
                    List<String> nodeIdAllList = new ArrayList<>();
                    //属性名称_LKT_属性id
                    String attributeStr = "%s_LKT_%s";

                    //准备添加属性
                    SkuModel saveSkuModel = new SkuModel();
                    saveSkuModel.setAdmin_name("task");
                    saveSkuModel.setStore_id(1);
                    //父级 属性在sku表里是否存在
                    Integer skuFatherId = skuModelMapper.getAttributeByName(1, DictionaryConst.SkuType.SKUTYPE_NAME, attrName, 0);
                    if (skuFatherId == null) {
                        //添加属性名称
                        saveSkuModel.setSid(0);
                        saveSkuModel.setName(attrName);
                        saveSkuModel.setType(DictionaryConst.SkuType.SKUTYPE_NAME);
                        saveSkuModel.setAdd_date(new Date());
                        saveSkuModel = publicStockService.saveSku(saveSkuModel, 0);
                        skuFatherId = saveSkuModel.getId();
                    }
                    //添加属性值
                    int i = 0;
                    for (Element attrValueAll : goodsAttrValueDom) {
                        logger.debug(">>当前网页节点{}爬取开始<<", i);
                        String attrValue = attrValueAll.select("a>span").text();
                        //节点id
                        String nodeId = attrValueAll.attr("data-value");
                        //子集 属性在sku表里是否存在
                        Integer skuSonId = skuModelMapper.getAttributeByName(1, DictionaryConst.SkuType.SKUTYPE_VALUE, attrValue, skuFatherId);
                        if (skuSonId == null) {
                            //没有数据则添加
                            saveSkuModel.setId(null);
                            saveSkuModel.setSid(skuFatherId);
                            saveSkuModel.setName(attrValue);
                            saveSkuModel.setType(DictionaryConst.SkuType.SKUTYPE_VALUE);
                            saveSkuModel.setAdd_date(new Date());
                            saveSkuModel = publicStockService.saveSku(saveSkuModel, i);
                            skuSonId = saveSkuModel.getId();
                            logger.debug("属性值添加完成 id={} ", skuSonId);
                        } else {
                            logger.debug("父级id{}下属性{}的id已存在,无需新增属性 ", skuFatherId, attrValue);
                        }
                        //结构: 网页节点属性id + {xxx_LKT_xxx}...
                        attrMap.put(String.format(attributeStr, attrName, skuFatherId), String.format(attributeStr, attrValue, skuSonId));
                        nodeIdAllList.add(nodeId + JSON.toJSONString(attrMap));
                        logger.debug(">>当前网页节点{}爬取完成<<", i);
                        i++;
                    }
                    //解决组合少一个的情况
                    if (x == 0) {
                        nodeIdAllList.add("");
                    }
                    groupNodes[x] = nodeIdAllList.toArray(new String[0]);
                    logger.debug(">>>网页父节点{}爬取完成<<<", x);
                    x++;
                }
                logger.debug(">>>>>网页爬取完成<<<<<");
                logger.debug(">>>>>商品发布开始<<<<<");
                //发布商品
                int sort = productListModelMapper.getGoodsMaxSort(taoBaoModel.getStore_id());
                saveProductListModel.setLabel("");
                saveProductListModel.setSort(sort);
                saveProductListModel.setAdd_date(new Date());
                count = productListModelMapper.insertSelective(saveProductListModel);
                if (count < 1) {
                    updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
                    updateTaoBao.setMsg("商品保存失败");
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品保存失败", "getTaoBaoData");
                }
                //添加轮播图
                publiceService.addRotationImage(goodsImageUrls, saveProductListModel.getId(), false);

                logger.debug(">>>>>商品发布成功 id={}<<<<<", saveProductListModel.getId());
                //获取所有可能的组合价格信息
                List<List<String>> resultList = new ArrayList<>();
                DataAlgorithmTool.combination(groupNodes, new String[groupNodes.length], 0, 0, resultList);
                //添加规格库存信息
                for (List<String> groupNodeId : resultList) {
                    StringBuilder nodeIdstr = new StringBuilder();
                    //因为网页里是用‘;’来分隔做key
                    nodeIdstr.append(";");
                    for (String nodeId : groupNodeId) {
                        nodeIdstr.append(nodeId).append(";");
                    }
                    //拆分
                    Map<String, String> nodeIdMap = StringUtils.getSplit(nodeIdstr.toString(), "{", "}", ",");
                    for (String key : nodeIdMap.keySet()) {
                        String valueJson = "[" + nodeIdMap.get(key) + "]";
                        logger.debug(">>>添加规格库存开始 {}<<<", nodeIdMap.get(key));
                        //找到当前id价格信息
                        if (skuMap.containsKey(key)) {
                            ConfiGureModel confiGureModel = new ConfiGureModel();
                            //获取属性
                            List<Map<String, String>> attributeList = JSON.parseObject(valueJson, new TypeReference<List<Map<String, String>>>() {
                            });
                            Map<String, String> attributeMap = new HashMap<>(16);
                            for (Map<String, String> map : attributeList) {
                                attributeMap.putAll(map);
                            }
                            //获取当前属性节点价格信息
                            Map<String, Object> goodsStockMap = JSON.parseObject(String.valueOf(skuMap.get(key)), new TypeReference<Map<String, Object>>() {
                            });
                            BigDecimal price = new BigDecimal(String.valueOf(goodsStockMap.get("price")));
                            String num = String.valueOf(goodsStockMap.get("stock"));
                            //下载规格图片
                            String attrImgUrlPath = "";
                            logger.debug("↓↓↓↓↓ 正在下载规格图片 url:{} ↓↓↓↓↓", url);
                            String attrImgId = StringUtils.trim(key, ";");
                            String[] tempIds = attrImgId.split(";");
                            attrImgId = tempIds[0];
                            if (tempIds.length > 1) {
                                attrImgId = tempIds[1];
                            }
                            String tempUrl = document.select(String.format("#J_isku>.tb-skin>dl>dd>ul>li[data-value='%s']>a", attrImgId)).attr("style");
                            String pattern1 = "\\((.*?)\\)";
                            Pattern r1 = Pattern.compile(pattern1);
                            Matcher m1 = r1.matcher(tempUrl);
                            while (m1.find()) {
                                String path = m1.group();
                                attrImgUrlPath = GloabConst.HttpProtocol.HTTPS + ":" + path.substring(1, path.length() - 1);
                                attrImgUrlPath = attrImgUrlPath.replace(".jpg_30x30", ".jpg_200x200");
                                logger.debug("正在上传规格图片 url:{}", attrImgUrlPath);
                                attrImgUrlPath = publiceService.uploadImage(attrImgUrlPath, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, Integer.parseInt(DictionaryConst.StoreSource.LKT_LY_001), taoBaoModel.getStore_id());
                                logger.debug("上传后的规格图片 url:{}", attrImgUrlPath);
                            }
                            logger.debug("↓↓↓↓↓ 正在下载规格图片 完成 ↓↓↓↓↓");
                            //添加属性
                            confiGureModel.setPid(saveProductListModel.getId());
                            confiGureModel.setCostprice(price);
                            confiGureModel.setPrice(price);
                            confiGureModel.setYprice(price);
                            confiGureModel.setImg(ImgUploadUtils.getUrlImgByName(attrImgUrlPath, true));
                            confiGureModel.setMin_inventory(10);
                            confiGureModel.setTotal_num(Integer.parseInt(num));
                            confiGureModel.setNum(Integer.parseInt(num));
                            confiGureModel.setUnit("件");
                            confiGureModel.setBar_code(taoBaoModel.getItemid());
                            confiGureModel.setBargain_price(price);
                            confiGureModel.setStatus("0");
                            confiGureModel.setColor("");
                            confiGureModel.setAttribute(SerializePhpUtils.JavaSerializeByPhp(attributeMap));
                            confiGureModel.setCtime(new Date());
                            count = confiGureModelMapper.insertSelective(confiGureModel);
                            if (count < 1) {
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品规格库存保存失败", "getTaoBaoData");
                            }
                            //添加库存
                            StockModel stockModel = new StockModel();
                            stockModel.setStore_id(taoBaoModel.getStore_id());
                            stockModel.setProduct_id(saveProductListModel.getId());
                            stockModel.setAttribute_id(confiGureModel.getId());
                            stockModel.setFlowing_num(confiGureModel.getNum());
                            stockModel.setTotal_num(confiGureModel.getTotal_num());
                            stockModel.setContent("添加库存");
                            stockModel.setType(DictionaryConst.StockType.STOCKTYPE_WAREHOUSING);
                            if (confiGureModel.getMin_inventory() >= confiGureModel.getNum()) {
                                stockModel.setContent("库存预警");
                                stockModel.setType(DictionaryConst.StockType.AGREEMENTTYPE_WAREHOUSING_WARNING);
                            }
                            stockModel.setAdd_date(new Date());
                            count = stockModelMapper.insertSelective(stockModel);
                            if (count < 1) {
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品规格库存记录信息保存失败", "getTaoBaoData");
                            }

                            logger.debug(">>>添加规格库存成功 id={}<<<", confiGureModel.getId());
                        } else {
                            logger.debug(">>>未获取到当前节点价格信息 节点id={}<<<", key);
                        }
                    }
                    logger.debug(">>>>添加规格库存结束<<<<");
                }
            }
            updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_SUCCESS);
        } catch (NullPointerException nul) {
            updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
            updateTaoBao.setMsg("抓取失败，链接/爬取数据有误!");
            logger.debug("爬取淘宝数据 抓取失败: ", nul);
        } catch (LaiKeApiException l) {
            updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
            updateTaoBao.setMsg(l.getMessage());
            logger.debug("爬取淘宝数据 抓取失败: ", l);
        } catch (Exception e) {
            updateTaoBao.setStatus(TaoBaoModel.TAOBAO_STATUS_CRAWLING_FAIL);
            updateTaoBao.setMsg("淘宝抓取失败!");
            logger.debug("爬取淘宝数据 异常: ", e);
        }
        return updateTaoBao;
    }

    /**
     * 获取商品明细
     *
     * @param dom -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/6 10:58
     */
    private String getGoodsContext(Document dom) throws LaiKeApiException {
        StringBuilder content = new StringBuilder();
        try {
            String pattern = "(?s)apiImgInfo(.*?):(.*?),";
            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(dom.getElementsByTag("script").html());
            String imgUrlApi = "https://gd1.alicdn.com/imgextra/%s";
            Map<String, Object> imgUrlMap = new HashMap<>(16);
            while (m.find()) {
                String imgUrlApiJson = "{" + StringUtils.trim(m.group()).replace(",", "}");
                Map<String, String> imgUrlApiMap = new HashMap<>(16);
                imgUrlApiMap = JSON.parseObject(imgUrlApiJson, new TypeReference<Map<String, String>>() {
                });
                String goodsImgUrlApi = GloabConst.HttpProtocol.HTTPS + ":" + imgUrlApiMap.get("apiImgInfo");
                String resultJson = HttpUtils.get(goodsImgUrlApi);
                resultJson = resultJson.replace("$callback(", "");
                resultJson = resultJson.substring(0, resultJson.length() - 1);
                imgUrlMap = JSON.parseObject(resultJson, new TypeReference<Map<String, Object>>() {
                });
            }
            content.append("<p>");
            for (String key : imgUrlMap.keySet()) {
                if (key.length() > 10) {
                    content.append("<img src=\"").append(String.format(imgUrlApi, key)).append("\">");
                }
            }
            content.append("</p>");
        } catch (Exception e) {
            logger.error(">>>>>>>抓取商品详情 【异常失败】<<<<<<<", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品规格库存保存失败", "getGoodsContext");
        }
        return content.toString();
    }

    @Override
    public Map<String, Object> getClassifiedBrands(int storeId, Integer classId, Integer brandId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        //产品类别集
        List<Map<String, Object>> productClassList = new ArrayList<>();
        //品牌集
        List<Map<String, Object>> brandList;
        try {
            Integer classTopId = null;

            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setStore_id(storeId);
            if (classId == null || classId == 0) {
                //获取产品顶级类别信息
                productClassModel.setSid(0);
                List<ProductClassModel> productClassModelList = productClassModelMapper.getProductClassLevel(productClassModel);
                for (ProductClassModel productClass : productClassModelList) {
                    Map<String, Object> productClassMap = new HashMap<>(16);
                    productClassMap.put("cid", productClass.getCid());
                    productClassMap.put("pname", productClass.getPname());
                    productClassMap.put("level", productClass.getLevel());
                    productClassMap.put("status", false);
                    productClassList.add(productClassMap);
                }
                if (productClassModelList.size() > 0) {
                    classTopId = productClassModelList.get(0).getCid();
                }
            } else {
                //获取产品子类别信息
                productClassModel.setCid(classId);
                ProductClassModel productClassModelFirstLeve = productClassModelMapper.selectOne(productClassModel);
                //查询下级,没有下级则查询同级
                productClassModel = new ProductClassModel();
                productClassModel.setStore_id(storeId);
                productClassModel.setSid(classId);
                List<ProductClassModel> productClassLowLeves = productClassModelMapper.getProductClassLevel(productClassModel);
                if (productClassLowLeves != null) {
                    //递归获取顶级
                    classTopId = getClassTop(storeId, classId);
                    //获取所有下级类别信息
                    for (ProductClassModel productClass : productClassLowLeves) {
                        Map<String, Object> productClassMap = new HashMap<>(16);
                        productClassMap.put("cid", productClass.getCid());
                        productClassMap.put("pname", productClass.getPname());
                        productClassMap.put("level", productClass.getLevel());
                        productClassMap.put("status", false);
                        productClassList.add(productClassMap);
                    }
                } else {
                    //查询同级
                    productClassModel = new ProductClassModel();
                    productClassModel.setStore_id(storeId);
                    productClassModel.setSid(productClassModelFirstLeve.getSid());
                    List<ProductClassModel> productClassLeves = productClassModelMapper.getProductClassLevel(productClassModel);
                    for (ProductClassModel productClass : productClassLeves) {
                        Map<String, Object> productClassMap = new HashMap<>(16);
                        //是否选中标识
                        boolean flag = productClass.getCid().equals(classId);
                        productClassMap.put("cid", productClass.getCid());
                        productClassMap.put("pname", productClass.getPname());
                        productClassMap.put("level", productClass.getLevel());
                        productClassMap.put("status", flag);
                        productClassList.add(productClassMap);
                    }
                }
            }

            //获取品牌信息
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("status", 0);
            parmaMap.put("categories", classTopId);
            brandList = brandClassModelMapper.getBrandClassDynamic(parmaMap);

            List<Object> tempList = new ArrayList<>();
            if (productClassList.size() > 0) {
                tempList.add(productClassList);
            }
            resultMap.put("class_list", tempList);
            resultMap.put("brand_list", brandList);
        } catch (ClassCastException c) {
            c.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "getClassifiedBrands");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取分类 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "数据异常", "getClassifiedBrands");
        }

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> attribute1(int storeId, PageModel pageModel, List<String> attributeList) throws LaiKeApiException {
        //属性集
        List<Map<String, Object>> attributes = new ArrayList<>();
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            List<Integer> storeIds = new ArrayList<>();
            storeIds.add(0);
            storeIds.add(storeId);
            parmaMap.put("storeIds", storeIds);
            parmaMap.put("status", 1);
            parmaMap.put("type", DictionaryConst.SkuType.SKUTYPE_NAME);
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());
            if (pageModel != null) {
                parmaMap.put("pageStart", pageModel.getPageNo());
                parmaMap.put("pageEnd", pageModel.getPageSize());
            }
            List<Map<String, Object>> attributeAllList = skuModelMapper.getAttributeDynamic(parmaMap);

            for (Map<String, Object> map : attributeAllList) {
                boolean status = false;
                Map<String, Object> attrbuteMap = new HashMap<>(16);
                attrbuteMap.put("id", map.get("id").toString());
                String name = map.get("name").toString();
                attrbuteMap.put("text", name);
                if (attributeList != null && attributeList.size() > 0) {
                    if (attributeList.contains(name)) {
                        status = true;
                    }
                }
                attrbuteMap.put("status", status);

                attributes.add(attrbuteMap);
            }

        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "attribute1");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取属性名称 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "attribute1");
        }

        return attributes;
    }

    @Override
    public Map<String, Object> attributeName1(int storeId, String attributeName, Map<String, Object> attrValue) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            List<Integer> storeIds = new ArrayList<>();
            storeIds.add(0);
            storeIds.add(storeId);
            parmaMap.put("storeIds", storeIds);
            parmaMap.put("status", 1);
            parmaMap.put("name", attributeName);
            List<Map<String, Object>> attributeAllList = skuModelMapper.getAttributeDynamic(parmaMap);
            if (attributeAllList != null && attributeAllList.size() > 0) {
                //获取当前属性被选中的属性值
                List<Map<String, Object>> attrCheckedList = new ArrayList<>();
                if (attrValue != null && attrValue.containsKey("attr_list")) {
                    attrCheckedList = DataUtils.cast(attrValue.get("attr_list"));
                }
                for (Map<String, Object> map : attributeAllList) {
                    //属性Id
                    int sid = Integer.parseInt(map.get("id").toString());
                    //属性名称
                    String mainName = map.get("name").toString();
                    //获取该类型下的属性值
                    parmaMap.clear();
                    parmaMap.put("type", DictionaryConst.SkuType.SKUTYPE_VALUE);
                    parmaMap.put("sid", sid);
                    List<Map<String, Object>> attributeValueList = skuModelMapper.getAttributeDynamic(parmaMap);
                    //处理属性值
                    for (Map<String, Object> valueMap : attributeValueList) {
                        boolean valueStatus = false;
                        String name = valueMap.get("name").toString();
                        //判断是否被选中
                        if (attrCheckedList != null) {
                            for (Map<String, Object> value : attrCheckedList) {
                                String checkName = DataUtils.cast(value.get("attr_name"));
                                if (!StringUtils.isEmpty(checkName)) {
                                    if (name.equals(checkName)) {
                                        valueStatus = true;
                                    }
                                }
                            }
                        }
                        valueMap.put("value", name);
                        valueMap.put("status", valueStatus);
                        valueMap.remove("name");
                    }
                    resultMap.put(mainName, attributeValueList);
                }
            } else {
                resultMap.put(attributeName, new ArrayList<>());
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "attribute1");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取属性值 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "attribute1");
        }

        return resultMap;
    }

    @Override
    public boolean addProduct(UploadMerchandiseVo vo, String adminName, int mchId, int type) throws LaiKeApiException {
        //保存规格属性集
        List<ConfiGureModel> saveConfigGureList = new ArrayList<>();
        //是否为编辑商品
        boolean isEdit = false;
        try {
            if (vo.getActive() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请选择支持活动类型");
            }

            //获取最后一条被添加的商品编号
            ProductNumberModel productNumberModel = new ProductNumberModel();
            productNumberModel.setStatus(ProductNumberModel.STATUS_USED);
            productNumberModel.setStore_id(vo.getStoreId());
            productNumberModel.setMch_id(mchId + "");
            productNumberModel.setOperation(adminName);
            productNumberModel = productNumberModelMapper.getGoodsNumberLow(productNumberModel);
            if (productNumberModel == null) {
                productNumberModel = new ProductNumberModel();
                productNumberModel.setProduct_number(BuilderIDTool.getGuid());
            }

            ProductListModel productListOld = null;
            if (vo.getpId() != null) {
                isEdit = true;
                productListOld = productListModelMapper.selectByPrimaryKey(vo.getpId());
            }

            ProductListModel productListModel = new ProductListModel();
            //判断商品名称是否重复
            if (productListOld == null || !productListOld.getProduct_title().equals(vo.getProductTitle())) {
                productListModel.setStore_id(vo.getStoreId());
                productListModel.setProduct_title(vo.getProductTitle());
                productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
                productListModel.setMch_id(mchId);
                if (productListModelMapper.selectCount(productListModel) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "您有存在该商品,请勿重复添加");
                }
            }
            productListModel = new ProductListModel();
            if (isEdit) {
                productListModel.setId(vo.getpId());
            }
            productListModel.setStore_id(vo.getStoreId());
            productListModel.setProduct_title(vo.getProductTitle());
            productListModel.setSubtitle(vo.getSubtitle());
            productListModel.setScan(vo.getScan());
            if (StringUtils.isEmpty(vo.getScan())) {
                productListModel.setScan("");
            }
            productListModel.setProduct_number(productNumberModel.getProduct_number());
            productListModel.setProduct_class(vo.getProductClassId());
            productListModel.setBrand_id(StringUtils.stringParseInt(vo.getBrandId()));
            productListModel.setKeyword(vo.getKeyword());
            productListModel.setWeight(vo.getWeight());
            productListModel.setImgurl(vo.getShowImg());
            productListModel.setContent(vo.getContent());
            productListModel.setRichList(StringUtils.isNotEmpty(vo.getRichList()) ? vo.getRichList() : "");
            productListModel.setMin_inventory(vo.getStockWarn());
            //序号
            productListModel.setSort(vo.getSort());
            //处理商品类型
            List<Object> typeList = new ArrayList<>();
            if (StringUtils.isNotEmpty(vo.getsType())) {
                typeList = new ArrayList<>(Arrays.asList(vo.getsType().split(SplitUtils.DH)));
            }
            productListModel.setS_type(StringUtils.stringImplode(typeList, SplitUtils.DH, true));

            productListModel.setShow_adr(vo.getDisplayPosition());
            productListModel.setDistributor_id(vo.getDistributorId());
            productListModel.setFreight(vo.getFreightId() + "");
            productListModel.setActive(vo.getActive() + "");
            productListModel.setMch_id(mchId);
            productListModel.setMch_status(vo.getMchStatus() + "");
            productListModel.setInitial(vo.getInitial());
            productListModel.setWeight_unit(vo.getUnit());
            productListModel.setVolume(vo.getVolume());
            if (StringUtils.isEmpty(vo.getCoverMap())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请上传封面图");
            }
            productListModel.setCover_map(ImgUploadUtils.getUrlImgByName(vo.getCoverMap(), true));
            //是否为分销商品
            int isDistribution = 0;

            // 活动类型处理+展示位置数据处理
            List<String> showAdrs = new ArrayList<>();
            if (StringUtils.isNotEmpty(productListModel.getShow_adr())) {
                showAdrs = new ArrayList<>(Arrays.asList(productListModel.getShow_adr().split(SplitUtils.DH)));
            }
            //展示位
            showAdrs.add(DictionaryConst.GoodsShowAdr.GOODSSHOWADR_DEFAULT.toString());
            if (productListModel.getActive().equals(DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_PRICE.toString())) {
                String showAdrStr = productListModel.getShow_adr();
                if (!StringUtils.isEmpty(showAdrStr)) {
                    showAdrs = Arrays.asList(showAdrStr.split(SplitUtils.DH));
                }
            } else if (productListModel.getActive().equals(DictionaryConst.GoodsActive.GOODSACTIVE_VIP_DISCOUNT.toString())) {
                isDistribution = 1;
            }

            if (vo.getMchStatus() == null) {
                // 默认暂不审核
                vo.setMchStatus(DictionaryConst.GoodsMchExameStatus.EXAME_STOP_STATUS);
            }
            productListModel.setMch_status(vo.getMchStatus().toString());
            //非平台则需要审核
            if (GloabConst.LktConfig.LKT_CONFIG_TYPE_PT == type) {
                //平台发布商品直接审核通过
                productListModel.setMch_status(DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS.toString());
            } else if (GloabConst.LktConfig.LKT_CONFIG_TYPE_MCH == type) {
                //判断是否是自营店铺,自营店铺直接跳过审核
                if (customerModelMapper.getStoreMchId(vo.getStoreId()).equals(mchId)) {
                    logger.debug("店铺id{}为自营店铺,发布商品自动跳过审核", mchId);
                    productListModel.setMch_status(DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS.toString());
                }
            }
            //商品展示位置数据处理
            List<Object> adrList = new ArrayList<>(showAdrs);
            productListModel.setShow_adr(StringUtils.stringImplode(adrList, SplitUtils.DH, true));

            productListModel.setIs_distribution(isDistribution);
            productListModel.setPublisher(adminName);
            //校验商品数据
            productListModel = DataCheckTool.checkGoodsDataFormate(productListModel);

            //递归找上级
            String classIdStr = strOption(vo.getStoreId(), Integer.parseInt(productListModel.getProduct_class()), "");
            if (StringUtils.isEmpty(classIdStr)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品类别id不存在");
            }
            productListModel.setProduct_class(classIdStr);
            BrandClassModel brandClassModel = new BrandClassModel();
            brandClassModel.setBrand_id(productListModel.getBrand_id());
            brandClassModel = brandClassModelMapper.selectOne(brandClassModel);
            if (brandClassModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "品牌不存在");
            }
            String classTop = classIdStr.split("-")[1];
            if (!brandClassModel.getCategories().contains(String.format(",%s,", classTop))) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品类别不在该品牌下");
            }

            //商品展示图处理
            List<String> goodsImgUrlList = new ArrayList<>();
            String goodsImgUrl = productListModel.getImgurl();
            goodsImgUrlList = Arrays.asList(goodsImgUrl.split(","));

            //处理属性 [{"cbj":"1","yj":"12","sj":"11","kucun":"111","attr_list":[{"attr_id":"","attr_name":"蓝色","attr_group_name":"颜色"}]}]
            String attrStr = vo.getAttrArr();
            if (StringUtils.isEmpty(attrStr)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请填写属性", "addProduct");
            }
            //编码处理
            attrStr = URLDecoder.decode(attrStr, GloabConst.Chartset.UTF_8);
            //当前无需回收的属性 id
            List<Integer> notRecycleAttrIds = new ArrayList<>();

            logger.debug("新上传的商品规格参数 :{}", attrStr);
            List<Map<String, Object>> attrAllList = JSON.parseObject(attrStr, new TypeReference<List<Map<String, Object>>>() {
            });
            int stockNum = 0;
            //当前所有属性的图片
            Map<String, Object> attrValueImageMap = new HashMap<>(16);
            for (Map<String, Object> map : attrAllList) {
                //成本价
                BigDecimal cbj = new BigDecimal(map.get("cbj").toString());
                BigDecimal price = new BigDecimal(map.get("sj").toString());
                BigDecimal yprice = new BigDecimal(map.get("yj").toString());
                //条形码
                String attrBraCode = MapUtils.getString(map, "bar_code");
                //当前规格库存
                if (!map.containsKey("kucun")) {
                    logger.debug("保存商品的时候库存为空>>>{}", JSON.toJSONString(map));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "库存不能为空");
                }
                Integer currentNum = MapUtils.getInteger(map, "kucun");
                if (currentNum == null || currentNum == 0) {
                    logger.debug("保存商品的时候库存为空>>>{}", JSON.toJSONString(map));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "库存不能为空");
                }
                stockNum += currentNum;

                //[{"attr_group_name":"颜色","attr_list":[{"attr_name":"蓝色","status":true},{"attr_name":"天蓝"}]},
                // {"attr_group_name":"尺码","attr_list":[{"attr_name":"M","status":true}]}]
                List<Map<String, Object>> attrList = DataUtils.cast(map.get("attr_list"));
                if (attrList == null) {
                    attrList = new ArrayList<>();
                }
                //用于保存属性的集合 (颜色分类_LKT_144=花色_LKT_145...)
                Map<String, Object> attrbiuteStrMap = new HashMap<>(16);
                ConfiGureModel saveConfiGureModel = new ConfiGureModel();
                for (Map<String, Object> attrMap : attrList) {
                    //当前规格key和value
                    String attributeGroupName = attrMap.get("attr_group_name").toString();
                    String attributeName = attrMap.get("attr_name").toString();
                    logger.debug("正在处理规格：{}{}", attributeGroupName, attributeName);
                    //记录规格
                    int attrNameId;
                    SkuModel saveSkuModel = new SkuModel();
                    saveSkuModel.setStore_id(vo.getStoreId());
                    saveSkuModel.setAdmin_name(adminName);
                    saveSkuModel.setType(DictionaryConst.SkuType.SKUTYPE_NAME);
                    saveSkuModel.setStatus(SkuModel.SKU_STATUS_INVALID);
                    //店铺发布、编辑商品时,设置的新属性,保存在SKU中,只要是商品未审核通过,则应不生效,只有审核通过或者平台手动开启方可生效
                    if (productListModel.getMch_status().equals(DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS.toString())) {
                        saveSkuModel.setStatus(SkuModel.SKU_STATUS_TAKE_EFFECT);
                    }
                    saveSkuModel.setRecycle(0);
                    //根据属性名称获取属性id
                    int attrGourpNameId = getAttributeId(vo.getStoreId(), DictionaryConst.SkuType.SKUTYPE_NAME, attributeGroupName, 0);
                    if (attrGourpNameId > 0) {
                        // 当属性名称ID不为0，SKU表里有数据
                        attrNameId = getAttributeId(vo.getStoreId(), DictionaryConst.SkuType.SKUTYPE_VALUE, attributeName, attrGourpNameId);
                        if (attrNameId == 0) {
                            //没找到则添加属性
                            String code = publicGoodsService.getSkuCode(vo.getStoreId(), null, attrGourpNameId);
                            saveSkuModel.setCode(code);
                            saveSkuModel.setType(DictionaryConst.SkuType.SKUTYPE_VALUE);
                            saveSkuModel.setName(attributeName);
                            saveSkuModel.setAdd_date(new Date());
                            saveSkuModel.setSid(attrGourpNameId);
                            skuModelMapper.insertSelective(saveSkuModel);
                            attrNameId = saveSkuModel.getId();
                        }
                    } else {
                        //全部都没有则全部添加
                        String code = publicGoodsService.getSkuCode(vo.getStoreId(), attributeGroupName, null);
                        saveSkuModel.setCode(code);
                        saveSkuModel.setName(attributeGroupName);
                        saveSkuModel.setType(DictionaryConst.SkuType.SKUTYPE_NAME);
                        saveSkuModel.setSid(0);
                        saveSkuModel.setAdd_date(new Date());
                        skuModelMapper.insertSelective(saveSkuModel);
                        attrGourpNameId = saveSkuModel.getId();
                        code = publicGoodsService.getSkuCode(vo.getStoreId(), null, saveSkuModel.getId());
                        saveSkuModel.setId(null);
                        saveSkuModel.setSid(attrGourpNameId);
                        saveSkuModel.setCode(code);
                        saveSkuModel.setName(attributeName);
                        saveSkuModel.setType(DictionaryConst.SkuType.SKUTYPE_VALUE);
                        saveSkuModel.setAdd_date(new Date());
                        skuModelMapper.insertSelective(saveSkuModel);
                        attrNameId = saveSkuModel.getId();
                    }

                    //拼接属性 规格+str+id
                    attributeGroupName += "_LKT_" + attrGourpNameId;
                    attributeName += "_LKT_" + attrNameId;
                    attrMap.put("attr_group_name", attributeGroupName);
                    attrMap.put("attr_name", attributeName);
                    //属性序列化处理
                    attrbiuteStrMap.put(attributeGroupName, attributeName);
                }
                saveConfiGureModel.setCostprice(cbj);
                saveConfiGureModel.setPrice(price);
                saveConfiGureModel.setYprice(yprice);
                saveConfiGureModel.setNum(currentNum);
                saveConfiGureModel.setTotal_num(currentNum);
                saveConfiGureModel.setBar_code(attrBraCode);

                //序列化
                saveConfiGureModel.setAttribute(SerializePhpUtils.JavaSerializeByPhp(attrbiuteStrMap));
                //当前属性图片
                attrValueImageMap.put(saveConfiGureModel.getAttribute(), ImgUploadUtils.getUrlImgByName(map.get("img") + "", true));
                //如果是修改
                if (isEdit) {
                    //获取之前商品属性信息 看当前属性是否是之前的属性,如果是之前的属性则做修改 否则做添加
                    ConfiGureModel confiGureOld = new ConfiGureModel();
                    confiGureOld.setPid(productListModel.getId());
                    confiGureOld.setAttribute(saveConfiGureModel.getAttribute());
                    confiGureOld = confiGureModelMapper.selectOne(confiGureOld);
                    if (confiGureOld != null) {
                        //标记无需回收的规格
                        notRecycleAttrIds.add(confiGureOld.getId());
                        //修改当前属性
                        ConfiGureModel confiGureNew = new ConfiGureModel();
                        confiGureNew.setId(confiGureOld.getId());
                        //规格图片处理
                        String currentImage = attrValueImageMap.get(confiGureOld.getAttribute()) + "";
                        if (!StringUtils.isEmpty(currentImage)) {
                            confiGureNew.setImg(currentImage);
                        }
                        confiGureNew.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
                        confiGureNew.setCostprice(saveConfiGureModel.getCostprice());
                        confiGureNew.setYprice(saveConfiGureModel.getYprice());
                        confiGureNew.setPrice(saveConfiGureModel.getPrice());
                        confiGureNew.setNum(saveConfiGureModel.getNum());
                        confiGureNew.setTotal_num(saveConfiGureModel.getTotal_num());
                        int count = confiGureModelMapper.updateByPrimaryKeySelective(confiGureNew);
                        if (count < 1) {
                            logger.info("修改商品规格信息失败 参数:" + JSON.toJSONString(productListModel));
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "属性保存失败", "addProduct");
                        }
                        String content;

                        StockModel saveStockModel = new StockModel();
                        saveStockModel.setTotal_num(saveConfiGureModel.getTotal_num());
                        saveStockModel.setFlowing_num(confiGureOld.getTotal_num());
                        if (!confiGureOld.getNum().equals(saveConfiGureModel.getNum())) {
                            //清空之前的库存记录重新记录
                            StockModel stockDel = new StockModel();
                            stockDel.setStore_id(vo.getStoreId());
                            stockDel.setProduct_id(productListModel.getId());
                            stockDel.setAttribute_id(confiGureOld.getId());
                            stockModelMapper.delete(stockDel);
                            //添加库存信息
                            content = adminName + "增加商品规格库存" + saveConfiGureModel.getNum();
                            saveStockModel.setId(null);
                            saveStockModel.setStore_id(vo.getStoreId());
                            saveStockModel.setProduct_id(productListModel.getId());
                            saveStockModel.setAttribute_id(saveConfiGureModel.getId());
                            saveStockModel.setTotal_num(saveConfiGureModel.getTotal_num());
                            saveStockModel.setType(DictionaryConst.StockType.STOCKTYPE_WAREHOUSING);
                            saveStockModel.setContent(content);
                            saveStockModel.setFlowing_num(saveConfiGureModel.getNum());
                            saveStockModel.setAdd_date(new Date());
                            count = stockModelMapper.insertSelective(saveStockModel);
                            if (count < 1) {
                                logger.info("库存记录失败 参数:" + JSON.toJSONString(saveStockModel));
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "库存记录失败", "addProduct");
                            }
                        }
                        //如果库存有变化则清空之前的库存记录

                        // 当属性库存低于等于预警值
                        if (confiGureOld.getMin_inventory() >= saveConfiGureModel.getNum()) {
                            content = "预警";
                            saveStockModel.setId(null);
                            saveStockModel.setType(DictionaryConst.StockType.AGREEMENTTYPE_WAREHOUSING_WARNING);
                            saveStockModel.setContent(content);
                            saveStockModel.setUser_id(adminName);
                            saveStockModel.setAdd_date(new Date());
                            count = stockModelMapper.insertSelective(saveStockModel);
                            if (count < 1) {
                                logger.info("库存记录失败 参数:" + JSON.toJSONString(saveStockModel));
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "库存预警记录失败", "addProduct");
                            }
                        }

                        //做了修改无需做保存,下一个
                        continue;
                    }
                }
                //需要保存的新规格
                saveConfigGureList.add(saveConfiGureModel);
            }
            //第一张为商品主图
            productListModel.setImgurl(ImgUploadUtils.getUrlImgByName(goodsImgUrlList.get(0), true));
            productListModel.setNum(stockNum);
            productListModel.setAdd_date(new Date());
            //保存商品
            int count;
            if (isEdit) {
                count = productListModelMapper.updateByPrimaryKeySelective(productListModel);
            } else {
                productListModel.setLabel("");
                //获取最新序号
                int sort = productListModelMapper.getGoodsMaxSort(vo.getStoreId());
                productListModel.setSort(sort);
                count = productListModelExtendMapper.saveGoods(productListModel);
                //添加跳转路径
                publicAdminService.addJumpPath(vo, productListModel.getId() + "", productListModel.getProduct_title(), JumpPathModel.JumpType.JUMP_TYPE0_GOODS, JumpPathModel.JumpType.JUMP_TYPE_APP,
                        GloabConst.LaikeTuiUrl.JumpPath.GOODS_APP, new String[]{productListModel.getId() + ""});
                publicAdminService.addJumpPath(vo, productListModel.getId() + "", productListModel.getProduct_title(), JumpPathModel.JumpType.JUMP_TYPE0_GOODS, JumpPathModel.JumpType.JUMP_TYPE_PC,
                        GloabConst.LaikeTuiUrl.JumpPath.GOODS_PC, new String[]{productListModel.getId() + ""});
            }
            if (count < 1) {
                logger.info("商品保存失败 参数:" + JSON.toJSONString(productListModel));
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品保存失败", "addProduct");
            }
            //轮播图处理
            if (isEdit) {
                //修改之前图片,把之前图片删除
                ProductImgModel delProductImgModel = new ProductImgModel();
                delProductImgModel.setProduct_id(productListModel.getId());
                count = productImgModelMapper.delete(delProductImgModel);
                logger.info("产品之前展示图删除失败 商品id:{} 删除状态:{}", productListModel.getId(), count > 0);
            }
            //添加轮播图
            for (String img : goodsImgUrlList) {
                ProductImgModel saveImg = new ProductImgModel();
                saveImg.setProduct_url(ImgUploadUtils.getUrlImgByName(img, true));
                saveImg.setProduct_id(productListModel.getId());
                saveImg.setSeller_id(adminName);
                saveImg.setAdd_date(new Date());
                count = productImgModelMapper.insertSelective(saveImg);
                if (count < 1) {
                    logger.info("产品展示图上传失败 参数:" + JSON.toJSONString(saveImg));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "产品展示图上传失败", "addProduct");
                }
            }
            //规格回收处理
            if (isEdit) {
                //回收除了当前的规格其它属性
                count = confiGureModelMapper.delAppointConfiGureInfo(productListModel.getId(), notRecycleAttrIds);
                if (count < 1) {
                    logger.debug("未回收规格,可能是在原来的基础上做了新增/修改;规格Id集:{}", notRecycleAttrIds);
                }
            }

            //添加库存、规格信息
            for (ConfiGureModel confiGureModel : saveConfigGureList) {
                //规格图片处理
                String currentImage = ImgUploadUtils.getUrlImgByName(attrValueImageMap.get(confiGureModel.getAttribute()) + "", false);
                //验证属性图片是否上传
                if (StringUtils.isEmpty(currentImage)) {
                    //没有图片则查看当前属性是否存在过，如果存在过则取之前图片
                    ConfiGureModel confiGureOld = new ConfiGureModel();
                    confiGureOld.setPid(productListModel.getId());
                    confiGureOld.setAttribute(confiGureModel.getAttribute());
                    confiGureOld = confiGureModelMapper.selectOne(confiGureOld);
                    if (confiGureOld == null) {
                        logger.debug("属性id【{}】没有上传图片", confiGureModel.getId());
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请上传属性图片");
                    } else {
                        //属性老图
                        currentImage = confiGureOld.getImg();
                        logger.debug("商品id{},属性字符串{},未上传新属性图片,延续用之前的图片", productListModel.getId(), confiGureModel.getAttribute());
                    }
                }
                confiGureModel.setImg(currentImage);

                //库存数量
                int attrNum = confiGureModel.getNum();
                confiGureModel.setNum(0);
                confiGureModel.setTotal_num(0);
                confiGureModel.setPid(productListModel.getId());
                confiGureModel.setMin_inventory(productListModel.getMin_inventory());
                confiGureModel.setBar_code(productListModel.getScan());
                confiGureModel.setColor("");
                confiGureModel.setCtime(new Date());
                confiGureModel.setUnit(productListModel.getWeight_unit());
                count = confiGureModelMapper.insertSelective(confiGureModel);
                if (count < 1) {
                    logger.info("库存记录添加失败 参数:" + JSON.toJSONString(confiGureModel));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "库存记录添加失败", "addProduct");
                }

                //添加库存信息
                AddStockVo addStockVo = new AddStockVo();
                addStockVo.setId(confiGureModel.getId());
                addStockVo.setPid(confiGureModel.getPid());
                addStockVo.setAddNum(attrNum);
                publicStockService.addGoodsStock(addStockVo, adminName);
                // 预警
                publicStockService.addStockWarning(vo.getStoreId(), confiGureModel.getId());
            }

            return true;
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "addProduct");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("保存商品 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品上传失败", "addProduct");
        }
    }

    @Override
    public String getSkuCode(int storeId, String skuName, Integer sid) throws LaiKeApiException {
        String code = "LKT_%s_%s";
        try {
            if (sid == null || sid == 0) {
                //生成父类属性code规则
                String skuNamePY = PinyinUtils.getPinYinHeadChar(skuName).toUpperCase();
                String codeTemp = skuNamePY;
                int count;
                int index = 0;
                do {
                    codeTemp = String.format(code, codeTemp, storeId);
                    SkuModel sku = new SkuModel();
                    sku.setSid(0);
                    sku.setCode(codeTemp);
                    count = skuModelMapper.selectCount(sku);
                    if (count > 0) {
                        //父类重名+1
                        index++;
                        codeTemp = skuNamePY + index;
                    }
                } while (count > 0);
                code = codeTemp;
            } else {
                int index = 0;
                //获取属性上级code LKT_YS_0
                SkuModel sku = new SkuModel();
                sku.setId(sid);
                sku.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                sku = skuModelMapper.selectOne(sku);
                if (sku == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ERROR, "上级不存在");
                }
                String thisCode = sku.getCode();
                //获取之前最新子级规则然后+1
                String code1 = skuModelMapper.getAttributeByCode(sid);
                String str = "_";
                if (!StringUtils.isEmpty(code1)) {
                    //属性子级拼接规则 LKT_name_storeId_子级名称_001(++)...
                    String codeTemp = code1.substring(code1.lastIndexOf("_"));
                    if (codeTemp.indexOf("R") > 0) {
                        str += "R";
                    }
                    index = Integer.parseInt(code1.substring(code1.lastIndexOf(str) + str.length()));
                }
                //生成子属性code规则
                code = thisCode + str + String.format("%03d", index + 1);
            }
            //判断code是否重复重复则拼接数字
            SkuModel skuCount = new SkuModel();
            skuCount.setStore_id(storeId);
            skuCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            int count;
            int num = 0;
            String codeTemp = code;
            do {
                skuCount.setCode(codeTemp);
                count = skuModelMapper.selectCount(skuCount);
                //已存在的sku则继续拼接,直到名称不相同
                logger.debug("已存在的sku则继续拼接,直到名称不相同 {}", codeTemp);
                num++;
                //重名规则'R'
                codeTemp = code + String.format("_R%s", num);
            } while (count > 0);
            if (num > 1) {
                code = codeTemp;
            }
        } catch (Exception e) {
            logger.error("获取属性代码 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getSkuCode");
        }
        return code;
    }

    public static void main(String[] args) {
        String code1 = "LKT_CM_0_002_1";
        String codeTemp = code1.substring(code1.lastIndexOf("_"));
        String str = "_";
        if (codeTemp.indexOf("R") > 0) {
            str += "R";
        }
        int index = Integer.parseInt(code1.substring(code1.lastIndexOf(str) + str.length()));

        System.out.println(index);
    }

    @Override
    public boolean delGoodsById(int storeId, int goodsId, Integer mchId) throws LaiKeApiException {
        try {
            ProductListModel productListModel = productListModelMapper.selectByPrimaryKey(goodsId);
            if (productListModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商品不存在");
            }
            if (mchId != null) {
                if (!productListModel.getMch_id().equals(mchId)) {
                    logger.info("商品回收失败 商品属于店铺{},传入的店铺id{}", productListModel.getMch_id(), mchId);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.ILLEGAL_INVASION, "非法入侵");
                }
            }
            ProductListModel delGoods = new ProductListModel();
            delGoods.setId(goodsId);
            delGoods.setRecycle(DictionaryConst.ProductRecycle.RECOVERY.toString());
            //回收商品
            int count = productListModelMapper.updateByPrimaryKeySelective(delGoods);
            if (count < 1) {
                logger.info("商品回收失败 参数:{}", JSON.toJSONString(delGoods));
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品删除失败", "cancellationShop");
            }
            //回收商品属性
            count = confiGureModelMapper.delConfiGureInfo(goodsId);
            if (count < 1) {
                logger.info("商品属性回收失败,可能属性已经回收 商品id:{}", goodsId);
            }
            //删除商品图片列表
            ProductImgModel productImgModel = new ProductImgModel();
            productImgModel.setProduct_id(goodsId);
            count = productImgModelMapper.delete(productImgModel);
            if (count < 1) {
                logger.info("商品图片删除数量:{} 参数:{}", count, JSON.toJSONString(productImgModel));
            }
            //删除商品购物车数据
            CartModel cartModel = new CartModel();
            cartModel.setStore_id(storeId);
            cartModel.setGoods_id(goodsId);
            count = cartModelMapper.delete(cartModel);
            if (count < 1) {
                logger.info("购物车删除数量:{} 参数:{}", count, JSON.toJSONString(cartModel));
            }
            //删除足迹
            UserFootprintModel userFootprintModel = new UserFootprintModel();
            userFootprintModel.setStore_id(storeId);
            userFootprintModel.setP_id(goodsId);
            count = userFootprintModelMapper.delete(userFootprintModel);
            if (count < 1) {
                logger.info("足迹删除数量:{} 参数:{}", count, JSON.toJSONString(userFootprintModel));
            }
            //删除收藏
            count = userCollectionModelMapper.delGoodsOrMchCollection(storeId, goodsId, mchId);
            if (count < 1) {
                logger.info("收藏删除数量:{} 参数:{}", count, JSON.toJSONString(cartModel));
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delGoodsById");
        }
    }

    @Override
    public void upperAndLowerShelves(int storeId, String goodsIds, Integer mchId, Integer status) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(goodsIds)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            String[] goodsIdList = goodsIds.split(",");
            for (String goodsId : goodsIdList) {
                //获取商品信息
                ProductListModel productListModel = new ProductListModel();
                productListModel.setId(Integer.parseInt(goodsId));
                productListModel = productListModelMapper.selectOne(productListModel);
                if (productListModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品不存在", "upperAndLowerShelves");
                }
                ProductListModel updateGoods = new ProductListModel();
                updateGoods.setId(productListModel.getId());
                if (status == 0) {
                    //下架 删除轮播图
                    delBanner(storeId, goodsIds, "productId");
                    updateGoods.setStatus(DictionaryConst.GoodsStatus.OFFLINE_GROUNDING.toString());
                    //上架时间置空
                    productListModelMapper.setUpperTimeNull(Integer.parseInt(goodsId));
                } else {
                    //商品上架 验证商品数据完整性
                    try {
                        productListModel.setInitial(GloabConst.ManaValue.MANA_VALUE_PICK);
                        DataCheckTool.checkGoodsDataFormate(productListModel);
                        //验证商品是否有属性,没有属性则不能上架
                        ConfiGureModel confiGureModel = new ConfiGureModel();
                        confiGureModel.setPid(updateGoods.getId());
                        confiGureModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
                        int count = confiGureModelMapper.selectCount(confiGureModel);
                        if (count < 1) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品没有属性");
                        }
                    } catch (LaiKeApiException l) {
                        logger.info("商品{}上架失败 原因:{}", updateGoods.getId(), l.getMessage());
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.GOODS_DATA_INCOMPLETE, "请先去完善商品信息", "upperAndLowerShelves");
                    }
                    if (productListModel.getNum() < 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.GOODS_DATA_INCOMPLETE, "上架失败:库存不足", "upperAndLowerShelves");
                    }
                    //标记商品上架
                    updateGoods.setStatus(DictionaryConst.GoodsStatus.NEW_GROUNDING.toString());
                    //上架时间
                    updateGoods.setUpper_shelf_time(new Date());
                }
                int count = productListModelMapper.updateByPrimaryKeySelective(updateGoods);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "操作失败", "upperAndLowerShelves");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("上下架商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "upperAndLowerShelves");
        }
    }

    @Override
    public boolean delBanner(int storeId, String goodsIds, String str) throws LaiKeApiException {
        try {
            //获取跳转url参数中包含的轮播图
            List<BannerModel> bannerModelList = bannerModelMapper.getBannersByUrl(storeId, str, goodsIds);
            for (BannerModel bannerModel : bannerModelList) {
                int count = bannerModelMapper.clearBannerById(storeId, bannerModel.getId());
                logger.info("删除轮播图id{} 执行结果:", count > 0);
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除轮播图 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delBanner");
        }
    }

    @Override
    public boolean editProduct(UploadMerchandiseVo vo, String adminName, int mchId, int type) throws LaiKeApiException {
        try {
            if (vo.getpId() != null && vo.getpId() > 0) {
                return addProduct(vo, adminName, mchId, type);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数不完整", "editProduct");
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "editProduct");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("重新编辑商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "editProduct");
        }
    }

    @Override
    public List<Integer> getProductSettings(int storeId) throws LaiKeApiException {
        List<Integer> goodsStautsList = new ArrayList<>();
        try {
            ProductConfigModel productConfigModel = new ProductConfigModel();
            productConfigModel.setStore_id(storeId);
            productConfigModel = productConfigModelMapper.selectOne(productConfigModel);
            if (productConfigModel != null) {
                goodsStautsList.add(DictionaryConst.ProductStatus.PRODUCTSTATUS_END_STATUS);
                if (productConfigModel.getIs_open().equals(ProductConfigModel.DISPLAY_BEAN_GOODS)) {
                    goodsStautsList.add(DictionaryConst.ProductStatus.UNSOLD_STATUS);
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商品设置 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getProductSettings");
        }
        return goodsStautsList;
    }

    /**
     * 获取属性id
     *
     * @param storeId -
     * @param type    -
     * @param name    -
     * @param sid     -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/17 19:18
     */
    private int getAttributeId(int storeId, int type, String name, int sid) throws LaiKeApiException {
        try {
            //参数列表
            Map<String, Object> parmaMap = new HashMap<>();
            List<Integer> storeIds = new ArrayList<>();
            storeIds.add(0);
            storeIds.add(storeId);
            parmaMap.put("storeIds", storeIds);
            parmaMap.put("type", type);
            parmaMap.put("name", name);
            parmaMap.put("sid", sid);
            List<Map<String, Object>> attributeValueList = skuModelMapper.getAttributeDynamic(parmaMap);
            for (Map<String, Object> map : attributeValueList) {
                return Integer.parseInt(map.get("id").toString());
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "getAttributeId");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取属性id 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAttributeId");
        }
        return 0;
    }

    @Autowired
    private ProductNumberModelMapper productNumberModelMapper;

    @Autowired
    private FreightModelMapper freightModelMapper;

    @Autowired
    private DictionaryListModelMapper dictionaryListModelMapper;

    @Autowired
    private PublicGoodsService publicGoodsService;

    @Autowired
    private ProductClassModelMapper productClassModelMapper;

    @Autowired
    private BrandClassModelMapper brandClassModelMapper;

    @Autowired
    private SkuModelMapper skuModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private ProductListModelExtendMapper productListModelExtendMapper;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private ProductImgModelMapper productImgModelMapper;

    @Autowired
    private StockModelMapper stockModelMapper;

    @Autowired
    private CartModelMapper cartModelMapper;

    @Autowired
    private UserFootprintModelMapper userFootprintModelMapper;

    @Autowired
    private UserCollectionModelMapper userCollectionModelMapper;

    @Autowired
    private BannerModelMapper bannerModelMapper;

    @Autowired
    private TaoBaoModelMapper taoBaoModelMapper;

    @Autowired
    private TaoBaoWorkModelMapper taoBaoWorkModelMapper;

    @Autowired
    private PublicStockService publicStockService;
    @Autowired
    private AdminModelMapper adminModelMapper;
    @Autowired
    private ProductConfigModelMapper productConfigModelMapper;

    @Autowired
    private PublicAdminService publicAdminService;

    @Autowired
    private PublicDictionaryService publicDictionaryService;

}
