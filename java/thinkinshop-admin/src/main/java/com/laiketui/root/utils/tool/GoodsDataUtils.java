package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSON;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.algorithm.DataAlgorithmTool;
import com.laiketui.root.utils.algorithm.Md5Util;
import com.laiketui.root.utils.common.SplitUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 选择商品规格弹数据结构处理
 *
 * @author Trick
 * @date 2020/10/22 10:06
 */
public class GoodsDataUtils {


    /**
     * 商品弹出插件数据结构【自选折扣】
     *
     * @param confiGureModelList - 商品规格集合【图片路径这里不做处理】
     * @param gradeRate          - 折扣
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/22 10:10
     */
    public static Map<String, Object> getGoodsAttributeInfo(List<ConfiGureModel> confiGureModelList, BigDecimal gradeRate) throws LaiKeApiException {
        try {
            return getGoodsAttributeInfo(confiGureModelList, gradeRate, null);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品插件数据获取失败", "getGoodsAttributeInfo");
        }
    }

    /**
     * 商品弹出插件数据结构【不打折】
     *
     * @param confiGureModelList - 商品规格集合【图片路径这里不做处理】
     * @param attreId            - 需要选中的规格id-可选,默认选择有库存的规格
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/22 10:10
     */
    public static Map<String, Object> getGoodsAttributeInfo(List<ConfiGureModel> confiGureModelList, Integer attreId) throws LaiKeApiException {
        try {
            return getGoodsAttributeInfo(confiGureModelList, new BigDecimal("1"), attreId);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品插件数据获取失败", "getGoodsAttributeInfo");
        }
    }

    public static Map<String, Object> getGoodsAttributeInfo(List<ConfiGureModel> confiGureModelList) throws LaiKeApiException {
        try {
            return getGoodsAttributeInfo(confiGureModelList, new BigDecimal("1"), null);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品插件数据获取失败", "getGoodsAttributeInfo");
        }
    }


    /**
     * 商品弹出插件数据结构
     *
     * @param confiGureModelList - 商品规格集合【图片路径这里不做处理】
     * @param gradeRate          - 折扣
     * @param attreId            - 需要选中的规格id-可选,默认选择有库存的规格
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/22 10:10
     */
    public static Map<String, Object> getGoodsAttributeInfo(List<ConfiGureModel> confiGureModelList, BigDecimal gradeRate, Integer attreId) throws LaiKeApiException {
        Map<String, Object> resultMap = new TreeMap<>();

        //商品规格和属性
        List<Object> attrList = new ArrayList<>();
        //商品规格库存信息
        List<Map<String, Object>> skuBeanList = new ArrayList<>();
        //商品组合信息
        List<Map<String, Object>> attributeList = new ArrayList<>();
        try {
            boolean flag = true;
            boolean checked = false;
            //attrList 临时节点
            Map<String, Object> attrListMapTemp = new HashMap<>(16);
            Set<String> attrKeyList = new HashSet<>();
            for (ConfiGureModel confiGure : confiGureModelList) {
                //折扣 原来价格 * 折扣 / 10 = 优惠价
                BigDecimal vipPrice = confiGure.getPrice();
                if (gradeRate.doubleValue() != 1) {
                    vipPrice = vipPrice.multiply(gradeRate);
                }
                confiGure.setPrice(vipPrice);
                //商品属性
                String attribute = confiGure.getAttribute();
                Map<String, Object> attributeMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(attribute, Map.class));
                if (attributeMap == null) {
                    break;
                }
                //attributes 子节点
                List<Map<String, Object>> attributesList = new ArrayList<>();
                //当前规格名称拼接
                StringBuilder attrStr = new StringBuilder();
                for (Object keyObj : attributeMap.keySet()) {
                    String key = keyObj.toString();
                    String value = attributeMap.get(key).toString();
                    String subValue = value;
                    String subKey = key;

                    //提取数据
                    if (value.indexOf("_LKT") > 0) {
                        subValue = value.substring(0, value.indexOf("_LKT"));
                    }
                    if (key.indexOf("_LKT") > 0) {
                        subKey = key.substring(0, key.indexOf("_LKT"));
                    }
                    attrKeyList.add(subKey);
                    attrStr.append(subValue);
                    //是否选中
                    out:
                    if (flag) {
                        //如果只有一个规格,则值判断库存
                        if (attributeMap.size() > 1) {
                            //根据规格id进行选中
                            if (attreId != null) {
                                if (!attreId.equals(confiGure.getId())) {
                                    break out;
                                }
                            }
                        }
                        //选中有库存的规格
                        if (confiGure.getNum() > 0) {
                            checked = true;
                            flag = false;
                        }
                    } else {
                        checked = false;
                    }
                    //attrList 父节点
                    getAttributeAttrList(subKey, subValue, attrListMapTemp, confiGure, checked);

                    String keyMd5 = Md5Util.MD5endoce(subKey);
                    String valueMd5 = Md5Util.MD5endoce(subValue);
                    Map<String, Object> attributesMap = new TreeMap<>();
                    attributesMap.put("attributeId", keyMd5);
                    attributesMap.put("attributeValId", valueMd5);
                    attributesList.add(attributesMap);
                }
                //skuBeanList 父节点
                Map<String, Object> skuBeanSonDetailMap = getSkuBeanList(attributesList, attrStr.toString(), confiGure);
                skuBeanList.add(skuBeanSonDetailMap);
                //attribute_list 父节点
                Map<String, Object> attributeSonDetailMap = getAttributeList(confiGure, attributeMap);
                attributeList.add(attributeSonDetailMap);
            }

            //attrList 父节点数据组装结构
            for (String key : attrListMapTemp.keySet()) {
                attrList.add(attrListMapTemp.get(key));
            }

            // attribute_list 父节点组装每种规格组合
            String[][] groupNodes = new String[attrKeyList.size()][];
            List<List<String>> resultList = new ArrayList<>();
            int i = 0;
            for (String key : attrKeyList) {
                Set<String> nodeIdAllList = new HashSet<>();
                //解决少一个组合的bug
                if (i == 0) {
                    nodeIdAllList.add("");
                }
                for (Map<String, Object> subSkuMap : attributeList) {
                    if (!subSkuMap.containsKey(key)) {
                        continue;
                    }

                    nodeIdAllList.add(key + SplitUtils.MH + subSkuMap.get(key));
                    String[] x = nodeIdAllList.toArray(new String[0]);
                    System.out.println(JSON.toJSONString(x));
                    groupNodes[i] = nodeIdAllList.toArray(new String[0]);
                }
                i++;
            }
            DataAlgorithmTool.combination(groupNodes, new String[groupNodes.length], 0, 0, resultList);
            //检查是否存在,存在则跳过,不存在则构造
            boolean isexits = false;
            for (List<String> attrStrs : resultList) {
                Map<String, Object> attrMapTemp = new HashMap<>(16);
                for (Map<String, Object> aMap : attributeList) {
                    //找到的数量
                    int num = 0;
                    for (String attrStr : attrStrs) {
                        System.out.println(attrStr);
                        if (attrStr == null) {
                            break;
                        }
                        String[] attrs = attrStr.split(SplitUtils.MH);
                        if (aMap.containsKey(attrs[0]) && aMap.containsValue(attrs[1])) {
                            num++;
                        }
                        attrMapTemp.put(attrs[0], attrs[1]);
                    }
                    isexits = num == attrStrs.size();
                    if (isexits) {
                        break;
                    }
                }
                if (!isexits) {
                    //构造数据
                    Map<String, Object> alistTemp = new HashMap<>(16);
                    alistTemp.putAll(attrMapTemp);
                    alistTemp.put("SkuID", 0);
                    alistTemp.put("Stock", 0);
                    alistTemp.put("Price", 0);
                    alistTemp.put("Pic", "");
                    attributeList.add(alistTemp);
                }
                isexits = false;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品插件数据获取失败", "getGoodsAttributeInfo");
        }

        resultMap.put("attrList", attrList);
        resultMap.put("skuBeanList", skuBeanList);
        resultMap.put("attribute_list", attributeList);
        return resultMap;
    }


    /**
     * 商品基本信息打包
     *
     * @param goodsList - 商品集
     * @param gradeRate - 折扣
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/27 10:16
     */
    public static void getGoodsInfo(List<Map<String, Object>> goodsList, BigDecimal gradeRate) throws LaiKeApiException {
        try {
            for (Map<String, Object> map : goodsList) {
                BigDecimal yprice = new BigDecimal(map.get("yprice") + "");
                BigDecimal vipYprice = new BigDecimal(map.get("price") + "");
                int sizeId = Integer.parseInt(map.get("cid") + "");
                BigDecimal vipPrice = vipYprice.multiply(gradeRate);
                String productTitle = map.get("product_title") + "";
                String name = map.get("name") + "";
                String color = map.get("color") + "";
                name = "null".equals(name) ? "" : name;
                color = "null".equals(color) ? "" : color;
                StringBuilder names = new StringBuilder(name).append(color);

                if (name.equals(color) || "默认".equals(color)) {
                    names = new StringBuilder("");
                }
                names.append(productTitle);


                map.put("name", names);
                map.put("price", yprice);
                map.put("price_yh", vipYprice);
                map.put("size", sizeId);
                map.put("vip_yprice", vipYprice);
                map.put("vip_price", vipPrice);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品基本信息打包失败", "getGoodsInfo");
        }
    }


    /**
     * 获取 attrList 子节点
     *
     * @param keyName         -
     * @param valueName       -
     * @param attrListMapTemp -
     * @param confiGure       - 当前属性信息
     * @param isChecked       - 是否选中,默认选中有库存的规格
     * @author Trick
     * @date 2020/10/22 10:18
     */
    private static void getAttributeAttrList(String keyName, String valueName, Map<String, Object> attrListMapTemp, ConfiGureModel confiGure, boolean isChecked) {
        Map<String, Object> attrSonDetailMap = new TreeMap<>();
        try {
            String keyMd5 = Md5Util.MD5endoce(keyName);
            String valueMd5 = Md5Util.MD5endoce(valueName);
            //attr节点
            List<Map<String, Object>> attrTempList = new ArrayList<>();
            //all 节点
            List<String> allTempList = new ArrayList<>();


            if (attrListMapTemp.containsKey(keyName)) {
                Map<String, Object> attrMap = DataUtils.cast(attrListMapTemp.get(keyName));
                if (attrMap != null) {
                    attrTempList = DataUtils.cast(attrMap.get("attr"));
                    allTempList = DataUtils.cast(attrMap.get("all"));
                }
            }
            if (attrTempList == null) {
                attrTempList = new ArrayList<>();
            }
            if (allTempList == null) {
                allTempList = new ArrayList<>();
            }
            if (allTempList.contains(valueName)) {
                return;
            }

            //attrList-数组结构-库存信息
            Map<String, Object> attrListMap = new TreeMap<>();
            attrListMap.put("id", valueMd5);
            attrListMap.put("attributeId", keyMd5);
            attrListMap.put("attrId", confiGure.getId());
            attrListMap.put("attributeValue", valueName);
            attrListMap.put("count", confiGure.getNum());
            attrListMap.put("price", confiGure.getPrice());
            //是否选中
            attrListMap.put("enable", isChecked);
            attrListMap.put("select", isChecked);
            attrTempList.add(attrListMap);

            allTempList.add(valueName);
            attrSonDetailMap.put("attr", attrTempList);
            attrSonDetailMap.put("all", allTempList);

            //attrList 节点
            if (!attrListMapTemp.containsKey(keyName)) {
                attrSonDetailMap.put("attrType", 1);
                attrSonDetailMap.put("id", keyMd5);
                attrSonDetailMap.put("attrName", keyName);
                attrListMapTemp.put(keyName, attrSonDetailMap);
            } else {
                attrSonDetailMap.putAll(attrListMapTemp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取 skuBeanList 子节点
     * 所有商品规格信息
     *
     * @param attributesList - attributes 子节点
     * @param valueName      - 规格信息拼装 例:蓝色M 白色L 黄色S
     * @param confiGure      -
     * @return List
     * @author Trick
     * @date 2020/10/22 10:18
     */
    private static Map<String, Object> getSkuBeanList(List<Map<String, Object>> attributesList, String valueName, ConfiGureModel confiGure) {
        Map<String, Object> skuBeanSonDetailMap = new TreeMap<>();
        try {
            //图片url
            String imgurl = confiGure.getImg();
            //属性id
            int cid = confiGure.getId();
            //价格
            BigDecimal price = confiGure.getPrice();
            //库存
            int stokeNum = confiGure.getNum();
            //单位
            String unit = confiGure.getUnit();

            skuBeanSonDetailMap.put("name", valueName);
            skuBeanSonDetailMap.put("imgurl", imgurl);
            skuBeanSonDetailMap.put("cid", cid);
            skuBeanSonDetailMap.put("price", price);
            skuBeanSonDetailMap.put("count", stokeNum);
            skuBeanSonDetailMap.put("unit", unit);
            skuBeanSonDetailMap.put("attributes", attributesList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skuBeanSonDetailMap;
    }

    /**
     * 获取 attribute_list 子节点
     *
     * @param confiGure    -
     * @param attributeMap -
     * @return List
     * @author Trick
     * @date 2020/10/22 10:18
     */
    private static Map<String, Object> getAttributeList(ConfiGureModel confiGure, Map<String, Object> attributeMap) {
        Map<String, Object> attrSonDetailMap = new TreeMap<>();
        try {
            //子节点
            attrSonDetailMap.put("SkuID", confiGure.getId());
            attrSonDetailMap.put("Stock", confiGure.getNum());
            attrSonDetailMap.put("Price", confiGure.getPrice());
            attrSonDetailMap.put("Pic", confiGure.getImg());
            for (Object keyObj : attributeMap.keySet()) {
                String key = keyObj.toString();
                String value = attributeMap.get(key).toString();
                String subValue = value;
                String subKey = key;
                //提取数据
                if (key.indexOf("_LKT") > 0) {
                    subKey = key.substring(0, key.indexOf("_LKT"));
                }
                if (value.indexOf("_LKT") > 0) {
                    subValue = value.substring(0, value.indexOf("_LKT"));
                }
                attrSonDetailMap.put(subKey, subValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attrSonDetailMap;
    }


    /**
     * 获取商品sku拼接字符串
     *
     * @param attrs
     * @return
     */
    public static String getProductSku(String attrs) {
        try {
            Map attributeMap = SerializePhpUtils.getUnserializeObj(attrs, Map.class);
            String Sku = null;
            if (attributeMap != null) {
                for (Object keyObj : attributeMap.keySet()) {
                    String key = keyObj.toString();
                    String value = attributeMap.get(key).toString();
                    //提取数据
                    if (value.indexOf("_LKT") > 0) {
                        value = value.substring(0, value.indexOf("_LKT"));
                    }
                    if (key.indexOf("_LKT") > 0) {
                        key = key.substring(0, key.indexOf("_LKT"));
                    }
                    Sku = key + SplitUtils.MH + value + SplitUtils.FH;
                }
            }
            return Sku;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取商品属性
     *
     * @param attrs -a:2:{s:12:"尺码_LKT_8";s:9:"S_LKT_118";s:12:"颜色_LKT_1";s:12:"蓝色_LKT_2";}
     * @return String - 尺码:S;颜色:蓝色
     * @author Trick
     * @date 2021/3/29 10:46
     */
    public static String getProductSkuValue(String attrs) {
        String result = "";
        try {
            Map<String, String> attributeMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(attrs, Map.class));
            StringBuilder sku = new StringBuilder();
            if (attributeMap == null) {
                return null;
            }
            for (String key : attributeMap.keySet()) {
                String value = attributeMap.get(key);
                //提取数据
                if (value.indexOf("_LKT") > 0) {
                    value = value.substring(0, value.indexOf("_LKT"));
                }
                if (key.indexOf("_LKT") > 0) {
                    key = key.substring(0, key.indexOf("_LKT"));
                }
                sku.append(key).append(SplitUtils.MH).append(value).append(SplitUtils.FH);
            }
            if (!StringUtils.isEmpty(sku.toString())) {
                result = StringUtils.trim(sku.toString(), SplitUtils.FH);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 获取商品状态
     *
     * @param status -
     * @return String
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/19 16:55
     */
    public static String getGoodsStatusStr(int status) throws LaiKeApiException {
        String name = "-";
        try {
            if (DictionaryConst.GoodsStatus.NOT_GROUNDING == status) {
                name = "待上架";
            } else if (DictionaryConst.GoodsStatus.NEW_GROUNDING == status) {
                name = "上架";
            } else if (DictionaryConst.GoodsStatus.OFFLINE_GROUNDING == status) {
                name = "下架";
            }
        } catch (Exception e) {
            name = "";
        }
        return name;
    }

    public static void main(String[] args) {
        String attrs = "a:2:{s:14:\"数量_LKT_126\";s:15:\"2000张_LKT_127\";s:13:\"默认_LKT_19\";s:14:\"大小_LKT_538\";}";
        attrs = "a:2:{s:12:\"尺码_LKT_8\";s:9:\"S_LKT_118\";s:12:\"颜色_LKT_1\";s:12:\"蓝色_LKT_2\";}";
//        getSize(attrs);
        System.out.println(getProductSkuValue(attrs));
    }


}
