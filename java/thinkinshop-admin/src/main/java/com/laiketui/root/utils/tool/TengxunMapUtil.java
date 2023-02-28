package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSONObject;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.okhttp.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 腾讯定位服务
 *
 * @author Trick
 * @date 2020/10/10 10:54
 */
public class TengxunMapUtil {
    private static final Logger logger = LoggerFactory.getLogger(TengxunMapUtil.class);


    public static void main(String[] args) {
        getlatAndLng("JJBBZ-N7Q35-6PBIN-QZ47K-W7EPV-MEFMN", "湖南省长沙市岳麓区");
    }

    /**
     * 腾讯地图通过定位反查详细地址
     *
     * @param key       密钥
     * @param latitude  维度
     * @param longitude 经度
     * @return String - 返回json https://lbs.qq.com/service/webService/webServiceGuide/webServiceGcoder
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/10 11:15
     */
    public static String getAddress(String key, String latitude, String longitude) throws LaiKeApiException {
        String success = "0";
        String location = latitude + "," + longitude;
        String url = GloabConst.TengXunUrl.TX_GET_ADDRESS;
        url = String.format(url, location, key);
        String json = HttpUtils.get(url);

        if (json.length() > 0) {
            JSONObject obj = JSONObject.parseObject(json);
            String returnCode = obj.get("status").toString();
            if (success.equals(returnCode)) {
                System.out.println(obj);
                return obj.getJSONObject("result").toString();
            } else {
                String msg = obj.get("message").toString();
                throw new LaiKeApiException(ErrorCode.BizErrorCode.TENGXUN_API_ERROR, msg, "getAddress");
            }
        } else {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_NOT_DATA, "未获取取到数据", "getAddress");
        }

    }

    /**
     * 获取地址经纬度
     *
     * @param key     -
     * @param address -
     * @return java.util.Map<java.lang.String, java.lang.String>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/10 14:20
     */
    public static Map<String, String> getlatAndLng(String key, String address) throws LaiKeApiException {
        Map<String, String> resultMap = new HashMap<>(16);
        try {
            String success = "0";
            String url = GloabConst.TengXunUrl.TX_GET_LONGITUDE_LATITUDE;
            url = String.format(url, address, key);
            String json = HttpUtils.get(url);

            if (json.length() > 0) {
                JSONObject obj = JSONObject.parseObject(json);
                String returnCode = obj.get("status").toString();
                if (success.equals(returnCode)) {
                    logger.debug(obj.toString());
                    resultMap.put("lng", obj.getJSONObject("result").getJSONObject("location").get("lng").toString());
                    resultMap.put("lat", obj.getJSONObject("result").getJSONObject("location").get("lat").toString());
                    return resultMap;
                } else {
                    String msg = obj.get("message").toString();
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.TENGXUN_API_ERROR, msg, "getlatAndLongitude");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_NOT_DATA, "未获取取到数据", "getlatAndLongitude");
            }
        } catch (Exception e) {
            logger.error("获取地址经纬度 异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getlatAndLongitude");
        }
    }


    /**
     * 根据经纬度获取距离
     *
     * @param key  - api密钥
     * @param to   - 起点坐标 格式[维度,精度]
     * @param from - 终点坐标 格式[维度,精度]
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/12 13:53
     */
    public static String getStoreDstance(String key, String to, String from) throws LaiKeApiException {
        String success = "0";
        String url = GloabConst.TengXunUrl.TX_GET_DISTANCEMATRIX;
        try {
            url = String.format(url, from, to, key);
            String json = HttpUtils.get(url);

            if (json.length() > 0) {
                JSONObject obj = JSONObject.parseObject(json);
                String returnCode = obj.get("status").toString();
                if (success.equals(returnCode)) {
                    System.out.println(obj);
                    //获取距离
                    JSONObject resultObj = obj.getJSONObject("result");

                    return resultObj.toString();
                } else {
                    String msg = obj.get("message").toString();
                    logger.info("获取附近店铺失败 原因:" + msg);
//                    throw new LaiKeApiException(ErrorCode.BizErrorCode.TENGXUN_API_ERROR, msg, "getStore");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取附近店铺 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取距离信息失败", "getStore");
        }

        return null;
    }

}
