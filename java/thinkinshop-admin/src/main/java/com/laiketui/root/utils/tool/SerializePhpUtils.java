package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import org.apache.commons.collections.MapUtils;
import org.phprpc.util.AssocArray;
import org.phprpc.util.PHPSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * php序列化.反序列化
 *
 * @author Trick
 * @date 2020/10/13 11:22
 */
public class SerializePhpUtils {
    private static final Logger logg = LoggerFactory.getLogger(SerializePhpUtils.class);

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        String line = "" +
                "a:1:{i:0;a:3:{s:3:\"one\";s:2:\"10\";s:7:\"freight\";r:3;s:4:\"name\";s:366:\"北京市,天津市,河北省,山西省,内蒙古自治区,辽宁省,吉林省,黑龙江省,上海市,江苏省,浙江省,安徽省,福建省,江西省,山东省,河南省,湖北省,湖南省,广东省,广西省,海南省,重庆市,四川省,贵州省,云南省,西藏自治区,陕西省,甘肃省,青海省,宁夏市,新疆省,台湾省,香港特别行政区,澳门\";}}" +
                "";


        System.out.println(SerializePhpUtils.getUnSerializeByFreight(line));

        Map<Integer, Map<String, Object>> map = new HashMap<>(16);

        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> map1 = new LinkedHashMap<>(16);
        map1.put("one", "1");
        map1.put("name", "天津市,河北省,山西省,内蒙古自治区");
        map1.put("freight", "1");
        Map<String, Object> map2 = new LinkedHashMap<>(16);
        map2.put("one", "2");
        map2.put("name", "天津市,河北省,山西省,吉林省,福建省,江西省");
        map2.put("freight", "2");
        map.put(0, map1);
        map.put(1, map2);
        list.add(map1);
        list.add(map2);

        line = SerializePhpUtils.JavaSerializeByPhp(list);
//        Map<String, Object> freightList = JSON.parseObject(line, new TypeReference<Map<String, Object>>() {
//        });
        System.out.println(line);
//        List<Map<String, Object>> map = JSON.parseObject(line, new TypeReference<List<Map<String, Object>>>() {
//        });
        System.out.println(SerializePhpUtils.getUnSerializeByFreight(line));
//        System.out.println(SerializePhpUtils.getUnserializeObj(line, Map.class));

        Map<Integer,String> photosMap = SerializePhpUtils.getUnserializeObj("a:5:{s:3:\"cbj\";s:2:\"16\";s:2:\"yj\";s:2:\"16\";s:2:\"sj\";s:3:\"116\";s:4:\"unit\";s:3:\"件\";s:5:\"kucun\";s:3:\"100\";}", Map.class);
        System.out.println(photosMap);
    }


    /**
     * 反php序列化数组
     * lkt_sign_config.continuity
     * <p>
     * [{"3":"10"},{"30":"30"}]
     *
     * @param content -a:2:{i:0;a:1:{i:3;s:2:"10";}i:1;a:1:{i:30;s:2:"30";}}
     * @return List -
     */
    public static List<Map<Integer, Integer>> getSignConfigByContinuity(String content) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        List<Map<Integer, Integer>> resultList = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            AssocArray array1 = (AssocArray) p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8));
            List<AssocArray> dataMapList = DataUtils.cast(array1.toArrayList());
            if (dataMapList == null) {
                return null;
            }
            for (AssocArray array : dataMapList) {
                Map<Integer, Integer> resultMap = new HashMap<>(16);
                Map<Integer, byte[]> map = DataUtils.cast(array.toHashMap());
                if (map == null) {
                    return null;
                }
                for (Integer key : map.keySet()) {
                    resultMap.put(key, Integer.parseInt(new String(map.get(key))));
                }
                resultList.add(resultMap);
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }

    /**
     * 反序列化php对象
     * lkt_group_product.group_level
     * lkt_group_open.group_level
     *
     * @param content -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/16 18:31
     */
    public static Map<Integer, Object> getGroupByGroupLevel(String content) throws LaiKeApiException {
        Map<Integer, Object> resultMap = new HashMap<>(16);
        try {
            if (StringUtils.isEmpty(content)) {
                return resultMap;
            }
            PHPSerializer p = new PHPSerializer();
            AssocArray array = (AssocArray) p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8));
            Map<Integer, Object> map = array.toHashMap();

            for (Integer key : map.keySet()) {
                resultMap.put(key, new String((byte[]) map.get(key)));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGroupByGroupLevel");
        }
        return resultMap;
    }

    /**
     * 反序列化php对象
     * lkt_distribution_config.sets
     *
     * @param content -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/16 18:31
     */
    public static Map<String, Object> getDistributionConfigBySets(String content) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            if (StringUtils.isEmpty(content)) {
                return resultMap;
            }
            PHPSerializer p = new PHPSerializer();
            Map<String, Object> map = DataUtils.cast(p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8), Map.class));

            List<Integer> oneList = new ArrayList<>();
            List<Integer> teamList = new ArrayList<>();

            if (map != null) {
                if (map.containsKey("one")) {
                    AssocArray oneObj = (AssocArray) map.get("one");
                    if (oneObj != null) {
                        oneList = DataUtils.cast(oneObj.toArrayList());
                    }
                    AssocArray teamObj = (AssocArray) map.get("team");
                    teamList = DataUtils.cast(teamObj.toArrayList());
                }

                for (String key : map.keySet()) {
                    if (map.get(key) instanceof byte[]) {
                        map.put(key, new String((byte[]) map.get(key)));
                    }
                }
                resultMap.putAll(map);
            }
            resultMap.put("one", oneList);
            resultMap.put("team", teamList);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化lkt_distribution_config.sets: {} 失败！！！", content);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDistributionConfigBySets");
        }
        return resultMap;
    }

    /**
     * 序列化 lkt_freight.freight
     * 结构:
     * Map<integer,map<string,object>>
     *     or
     * List<Map<String, Object>>
     *
     * @param content - a:1:{s:1:"0";a:2:{s:3:"one";s:2:"10";s:4:"name";s:39:"北京市,内蒙古自治区,上海市,";}}
     * @return Map - {one=5, name=北京市,天津市,江西省,河南省,湖北省,湖南省,四川省,青海省...}{...}...
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 9:37
     */
    public static Map<Integer, LinkedHashMap<String, Object>> getUnSerializeByFreight(String content) throws LaiKeApiException {
        try {
            Map<Integer, LinkedHashMap<String, Object>> resultDataMap = new HashMap<>(16);

            PHPSerializer p = new PHPSerializer();
            Object obj = p.unserialize(content.getBytes(StandardCharsets.UTF_8), Map.class);
            Map<Integer, Object> dataMap = (LinkedHashMap<Integer, Object>) obj;

            for (Integer key : dataMap.keySet()) {
                AssocArray assocArray = (AssocArray) dataMap.get(key);
                LinkedHashMap<String, Object> resultMap = assocArray.toLinkedHashMap();

                resultMap.replaceAll((k, v) -> {
                    if (!StringUtils.isInteger(resultMap.get(k) + "")) {
                        return new String((byte[]) resultMap.get(k));
                    }
                    return resultMap.get(k);
                });

                String json = JSON.toJSONString(resultMap);
                LinkedHashMap<String, Object> map = JSON.parseObject(json, new TypeReference<LinkedHashMap<String, Object>>() {
                });
                for (String k : map.keySet()) {
                    if ("name".equals(k)) {
                        String name = map.get("name").toString().replace("&nbsp;", "");
                        name = StringUtils.trim(name, ",");
                        map.put("name", name);
                    }
                }
                resultDataMap.put(key, map);
            }

            return resultDataMap;
        } catch (Exception e) {
            logg.error("反序列化 lkt_freight.freight 异常: ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getPhpSerializeToHashMap");
        }
    }


    /**
     * 反序列化php对象
     * lkt_distribution_grade.sets
     *
     * @param content -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/16 18:31
     */
    public static Map<String, Object> getDistributionGradeBySets(String content) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            PHPSerializer p = new PHPSerializer();
            Map map = (Map) p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8), Map.class);

            AssocArray levelmoneyObj = (AssocArray) map.get("levelmoney");
            if (levelmoneyObj != null) {
                Map<Integer, Object> levelmoneyMap = levelmoneyObj.toLinkedHashMap();
                if (levelmoneyMap != null && !levelmoneyMap.isEmpty()) {
                    levelmoneyMap.replaceAll((k, v) -> new String((byte[]) levelmoneyMap.get(k)));
                }
                resultMap.put("levelmoney", levelmoneyObj);
            }
            AssocArray levelObj = (AssocArray) map.get("levelobj");
            if (levelObj != null) {
                Map<String, Object> levelObjMap = levelObj.toLinkedHashMap();
                if (levelObjMap != null && !levelObjMap.isEmpty()) {
                    levelObjMap.replaceAll((k, v) -> new String((byte[]) levelObjMap.get(k)));
                }
                resultMap.put("levelobj", levelObj);
            }
            resultMap.put("s_dengjiname", new String((byte[]) map.get("s_dengjiname")));
            //折扣
            BigDecimal zhekou = new BigDecimal("0");
            if (!StringUtils.isEmpty(map.get("zhekou").toString())) {
                zhekou = new BigDecimal(new String((byte[]) map.get("zhekou")));
            }
            BigDecimal priceType = new BigDecimal("0");
            if (!StringUtils.isEmpty(map.get("price_type"))) {
                priceType = new BigDecimal(map.get("price_type").toString());
            }
            //级差金额
            BigDecimal different = new BigDecimal("0");
            if (!StringUtils.isEmpty(map.get("different"))) {
                different = new BigDecimal(new String((byte[]) map.get("different")));
            }
            //平级金额
            BigDecimal sibling = new BigDecimal("0");
            if (!StringUtils.isEmpty(map.get("sibling"))) {
                sibling = new BigDecimal(new String((byte[]) map.get("sibling")));
            }
            //数值类型 1=元 other = 百分比
            BigDecimal directMtype = new BigDecimal("0");
            if (map.containsKey("direct_m_type")) {
                directMtype = new BigDecimal(map.get("direct_m_type").toString());
            }
            //直推分销奖金额
            BigDecimal directM = new BigDecimal("0");
            if (map.containsKey("direct_m")) {
                directM = new BigDecimal(new String((byte[]) map.get("direct_m")));
            }
            //间推分销奖金额
            BigDecimal indirectM = new BigDecimal("0");
            if (map.containsKey("indirect_m")) {
                indirectM = new BigDecimal(new String((byte[]) map.get("direct_m")));
            }


            resultMap.put("zhekou", zhekou);
            resultMap.put("price_type", priceType);
            resultMap.put("different", different);
            resultMap.put("sibling", sibling);
            resultMap.put("direct_m_type", directMtype);
            resultMap.put("direct_m", directM);
            resultMap.put("indirect_m", indirectM);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDistributionGradeBySets");
        }
        return resultMap;
    }

    /**
     * 反php序列化对象
     * 【此方法没有转换流】
     *
     * @param content -
     * @return T
     * @throws LaiKeApiException -
     */
    @Deprecated
    public static Map getUnserializeObj(String content) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            Map map = (Map) p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8), Map.class);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }

    /**
     * 反php序列化对象
     *
     * @param content -
     * @return T
     * @throws LaiKeApiException -
     */
    public static <T> T getUnserializeObj(String content, Class<T> cls) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            Map map = (Map) p.unserialize(content.getBytes(StandardCharsets.UTF_8), cls);
            if (map != null && !map.isEmpty()) {
                map.replaceAll((k, v) -> {
                    if (StringUtils.isInteger(map.get(k) + "")) {
                        return map.get(k);
                    }
                    Object val = map.get(k);
                    if (val != null) {
                        return new String((byte[]) val, StandardCharsets.UTF_8);
                    }
                    return val;
                });
            }
            String json = JSON.toJSONString(map);
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }

    public static <T> T getUnserializeByBasic(String content, Class<T> cls) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            Object obj = p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8), cls);
            return JSON.parseObject(JSON.toJSONString(obj), cls);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }

    /**
     * 反php序列化数组
     *
     * @param content -
     * @return List -
     */
    public static <T> T getUnserializeArray1(String content, Class<T> cls) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        String json = "";
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            AssocArray array1 = (AssocArray) p.unserialize(content.getBytes(StandardCharsets.UTF_8));
            json = JSON.toJSONString(array1.toArrayList());
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }


    /**
     * 反php序列化数组
     *
     * @param content -
     * @return List -
     */
    public static <T> T getUnserializeArray(String content, Class<T> cls) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        String json = "";
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            AssocArray array1 = (AssocArray) p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8));


            AssocArray array2 = (AssocArray) array1.toArrayList().get(0);
            Map<String, Object> map = array2.toHashMap();
            if (map != null && !map.isEmpty()) {
                map.replaceAll((k, v) -> new String((byte[]) map.get(k)));
            }
            json = JSON.toJSONString(map);
            return JSON.parseObject(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }


    /**
     * java bean 序列化成php格式
     *
     * @param obj -
     * @return java.lang.String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/11 17:56
     */
    public static String JavaSerializeByPhp(Object obj) throws LaiKeApiException {
        try {
            PHPSerializer p = new PHPSerializer();
            Object resultObj = p.serialize(obj);
            return new String((byte[]) resultObj);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("序列化object: " + JSON.toJSONString(obj) + " 失败！！！");
        }
        return null;
    }

    /**
     * 获取反序列化字符串
     *
     * @param content
     * @return
     * @throws LaiKeApiException
     */
    public static String getUnserializeString(String content) {
        PHPSerializer p = new PHPSerializer();
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            Object array1 = p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8));
            return new String((byte[]) array1);
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }

    /**
     * 是否是序列化字符串
     *
     * @param content
     * @return
     */
    public static boolean isSerialized(String content) {
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(content)) {
            String data = com.laiketui.root.utils.tool.StringUtils.trim(content, " ");
            if ("N;".equals(data)) {
                return true;
            }
            String reg = "^([adObis]):";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(data);
            if (!matcher.find()) {
                return false;
            }
            String g1 = matcher.group(1);
            switch (g1) {
                case "a":
                case "O":
                case "s":
                    reg = "^[badions]:[0-9]+:.*[;}]";
                    pattern = Pattern.compile(reg);
                    matcher = pattern.matcher(data);
                    if (matcher.find()) {
                        return true;
                    }
                    break;
                case "b":
                case "i":
                case "d":
                    reg = "^[badions]:[0-9.E-]+;";
                    pattern = Pattern.compile(reg);
                    matcher = pattern.matcher(data);
                    if (matcher.find()) {
                        return true;
                    }
                    break;
                default:
                    break;
            }
            return false;
        }
        return false;
    }

    /**
     * 反php序列化对象
     * lkt_auction_product.attribute
     *
     * @param content - a:1:{i:1270;a:1:{i:16625;s:5:"16625";}}
     * @return T  - {1270={16625=16625}}
     * @throws LaiKeApiException -
     */
    public static Map<Integer, Map<Integer, Object>> getAuctionProductAttribute(String content) throws LaiKeApiException {
        PHPSerializer p = new PHPSerializer();
        Map<Integer, Map<Integer, Object>> resultMap = new HashMap<>(16);
        try {
            if (StringUtils.isEmpty(content)) {
                return null;
            }
            Map<Integer, AssocArray> dataMap = DataUtils.cast(p.unserialize(content.getBytes(GloabConst.Chartset.UTF_8), Map.class));
            if (dataMap != null && !dataMap.isEmpty()) {
                dataMap.replaceAll((k, v) -> {
                    Map<Integer, Object> map = new HashMap<>(16);
                    if (v != null) {
                        map = DataUtils.cast(v.toHashMap());
                        if (map != null && !map.isEmpty()) {
                            map.replaceAll((kk, vv) -> new String((byte[]) vv));
                        }
                    }
                    resultMap.put(k, map);
                    return v;
                });
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            logg.error("反序列化object: " + content + " 失败！！！");
        }
        return null;
    }

}
