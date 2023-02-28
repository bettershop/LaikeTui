package com.laiketui.root.utils.tool;

import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.exception.LaiKeCommonException;
import com.laiketui.root.utils.common.SplitUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author wangxian
 */
public final class DataUtils {
    private final static Logger logger = LoggerFactory.getLogger(DataUtils.class);

    /**
     * 0
     */
    public static final BigDecimal ZERO_BIGDECIMAL = new BigDecimal(0);

    /**
     * 0
     */
    public static final Double ZERO_DOUBLE = new Double(0);

    /**
     * 0
     */
    public static final Integer ZERO_INT = new Integer(0);

    /**
     * 0
     */
    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);

    /**
     * 获取map 中key 对应的 string 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static String getStringVal(Map<String, Object> map, String key) {
        if (map == null) {
            return null;
        }
        Object valObj = map.get(key);
        if (valObj != null) {
            return valObj.toString();
        }
        return null;
    }

    /**
     * 获取map 中key 对应的 String 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static String getStringVal(Map<String, Object> map, String key, String defaultVal) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            return defaultVal;
        }
        return new String(tmpval);
    }

    /**
     * 获取map 中key 对应的 BigDecimal 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static BigDecimal getBigDecimalVal(Map<String, Object> map, String key) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            throw new LaiKeCommonException("转化为BigDecimal失败");
        }
        return new BigDecimal(tmpval);
    }

    /**
     * 获取map 中key 对应的 BigDecimal 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static BigDecimal getBigDecimalVal(Map<String, Object> map, String key, BigDecimal defaultVal) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            return defaultVal;
        }
        return new BigDecimal(tmpval);
    }

    /**
     * 获取map 中key 对应的 Double 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Double getDoubleVal(Map<String, Object> map, String key) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            throw new LaiKeCommonException("转化为Double失败");
        }
        return new Double(tmpval);
    }

    /**
     * 获取map 中key 对应的 Double 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Double getDoubleVal(Map<String, Object> map, String key, Double defaultVal) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            return defaultVal;
        }
        return new Double(tmpval);
    }


    /**
     * 获取map 中key 对应的 Integer 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Integer getIntegerVal(Map<String, Object> map, String key) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            throw new LaiKeCommonException("转化为Integer失败");
        }
        return new Integer(tmpval);
    }

    /**
     * 获取map 中key 对应的 Integer 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Integer getIntegerVal(Map<String, Object> map, String key, Integer defaultVal) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            return defaultVal;
        }
        return new Integer(tmpval);
    }

    /**
     * 获取map 中key 对应的 Float 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Float getFloatVal(Map<String, Object> map, String key) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            throw new LaiKeCommonException("转化为Float失败");
        }
        return new Float(tmpval);
    }

    /**
     * 获取map 中key 对应的 Float 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Float getFloatVal(Map<String, Object> map, String key, Float defaultVal) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            return defaultVal;
        }
        return new Float(tmpval);
    }

    /**
     * 获取map 中key 对应的 Float 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Boolean getBooleanVal(Map<String, Object> map, String key) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            throw new LaiKeCommonException("转化为Boolean失败");
        }
        return new Boolean(tmpval);
    }

    /**
     * 获取map 中key 对应的 Boolean 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Boolean getBooleanVal(Map<String, Object> map, String key, Boolean defaultVal) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            return defaultVal;
        }
        return new Boolean(tmpval);
    }

    /**
     * 获取map 中key 对应的 Date 类型值
     *
     * @param map
     * @param key
     * @return
     */
    public static Date getDateVal(Map<String, Object> map, String key) {
        String tmpval = getStringVal(map, key);
        if (tmpval == null) {
            throw new LaiKeCommonException("转化为Date失败");
        }
        return new Date(tmpval);
    }

    private interface Constant {
        int DESC = 1, ASC = 2;
    }

    public enum Sort {
        /**
         * 排序 降序 升序
         */
        DESC(Constant.DESC), ASC(Constant.ASC);

        int value;

        Sort(int val) {
            value = val;
        }
    }

    /**
     * map排序  排序类型为 时间/数字
     *
     * @param list    -
     * @param sortKey - 排序键
     * @param sort    -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/11 10:31
     */
    public static List<Map<String, Object>> mapSort(List<Map<String, Object>> list, String sortKey, Sort sort) throws LaiKeApiException {
        try {
            if (list == null) {
                return null;
            }
            switch (sort.value) {
                case Constant.DESC:
                    //从大至小排序
                    list.sort((o1, o2) -> {
                        String key = o1.get(sortKey).toString();
                        if (StringUtils.isInteger(key)) {
                            int num1 = Integer.parseInt(key);
                            int num2 = Integer.parseInt(o2.get(sortKey).toString());
                            return num1 < num2 ? 1 : -1;
                        } else if (DateUtil.isDate(key)) {
                            return !DateUtil.dateCompare(o1.get(sortKey).toString(), o2.get(sortKey).toString()) ? 1 : -1;
                        }
                        //其它排序可以在下面继续新增
                        return 0;
                    });
                    break;
                case Constant.ASC:
                    //从小至大排序
                    list.sort((o1, o2) -> {
                        String key = o1.get(sortKey).toString();
                        if (StringUtils.isInteger(key)) {
                            int num1 = Integer.parseInt(key);
                            int num2 = Integer.parseInt(o2.get(sortKey).toString());
                            return num1 < num2 ? 1 : -1;
                        } else if (DateUtil.isDate(key)) {
                            return DateUtil.dateCompare(o1.get(sortKey).toString(), o2.get(sortKey).toString()) ? 1 : -1;
                        }
                        //其它排序可以在下面继续新增
                        return 0;
                    });
                    break;
                default:
                    break;
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取指定键
     *
     * @param map  -
     * @param keys -
     * @return Map
     * @author Trick
     * @date 2021/3/11 17:21
     */
    public static Map<String, Object> getSpecifiedMap(Map<String, Object> map, String... keys) {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            for (String key : keys) {
                if (map.containsKey(key)) {
                    resultMap.put(key, map.get(key));
                }
            }
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 数组转list
     *
     * @return List
     * @author Trick
     * @date 2021/2/3 15:33
     */
    public static <T> List<T> convertToList(T[] list) {
        try {
            List<T> resultList = new ArrayList<>();
            if (Objects.requireNonNull(list).length > 0) {
                resultList = cast(Arrays.asList(list));
            }
            return resultList;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 专门处理 unchecked cast warning
     *
     * @param obj -
     * @author Trick
     * @date 2021/2/3 15:32
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Object obj) {
        try {
            return (T) obj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取服务器地址
     *
     * @param request -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/3/16 17:40
     */
    public static String getServcerUrl(HttpServletRequest request) throws LaiKeApiException {
        String basePath = "";
        try {
            String scheme = request.getScheme();
            String serverName = request.getServerName();
            int port = request.getServerPort();
            String path = request.getContextPath();
            basePath = scheme + "://" + serverName + ":" + port + path;
            return basePath;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String random(int min, int max) throws LaiKeApiException {
        try {
            return random(min, max, true) + "";
        } catch (Exception l) {
            throw l;
        }
    }

    public static String random(double min, double max) throws LaiKeApiException {
        try {
            return random(min, max, false) + "";
        } catch (Exception l) {
            throw l;
        }
    }

    /**
     * 取范围随机数
     *
     * @param min - 范围起始值
     * @param max - 范围阈值
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/19 11:01
     */
    public static double random(double min, double max, boolean isInt) throws LaiKeApiException {
        try {
            String minStr = min + "";
            String maxStr = max + "";
            logger.debug("取范围随机数: {}~{}", min, max);
            int minInt = 0;
            int maxInt = 0;
            //倍数 以最短的效数位为准 入 0.123 0.4567 倍数是3
            int minNum = 1;
            if (!isInt) {
                if (BigDecimal.valueOf(min).intValue() < min) {
                    if (minStr.indexOf(SplitUtils.XSD) > 0) {
                        //转整数
                        minNum = minStr.substring(minStr.indexOf(".")).length() - 1;
                        minNum = BigDecimal.valueOf(Math.pow(10, minNum)).intValue();
                        min *= minNum;
                    }
                }
                if (BigDecimal.valueOf(max).intValue() < max) {
                    if (maxStr.indexOf(SplitUtils.XSD) > 0) {
                        //转整数
                        int maxNum = maxStr.substring(maxStr.indexOf(".")).length() - 1;
                        if (minNum > maxNum) {
                            //以最短得小数位为准
                            minNum = maxNum;
                        } else {
                            maxNum = BigDecimal.valueOf(Math.pow(10, maxNum)).intValue();
                        }
                        max *= maxNum;
                    }
                }
            }
            minInt = new BigDecimal(String.valueOf(min)).intValue();
            maxInt = new BigDecimal(String.valueOf(max)).intValue();
            Random random = new Random();
            BigDecimal vallue = new BigDecimal(random.nextInt(maxInt) % (maxInt - minInt + 1) + minInt);
            return vallue.divide(BigDecimal.valueOf(minNum), minNum / 10, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 比较a和b是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equalBigDecimal(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) == 0;
    }

    /**
     * 比较a和0是否相等
     *
     * @param a
     * @return
     */
    public static boolean equalBigDecimalZero(BigDecimal a) {
        return equalBigDecimal(a, ZERO_BIGDECIMAL);
    }


}
