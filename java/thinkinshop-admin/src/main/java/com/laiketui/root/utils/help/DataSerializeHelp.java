package com.laiketui.root.utils.help;


import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.SerializePhpUtils;

import java.util.Map;

/**
 * 序列化后的一些处理
 *
 * @author Trick
 * @date 2020/12/31 14:50
 */
public class DataSerializeHelp {


    /**
     * 获取属性键值对值
     *
     * @param attribute -
     * @return String 颜色分类:碧玺灰,车辆版本:8系双门轿跑车首发限量版
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/4 11:19
     */
    public static String getAttributeStr(String attribute) throws LaiKeApiException {
        try {
            Map<String, Object> attributeMap = SerializePhpUtils.getUnserializeObj(attribute, Map.class);
            StringBuilder attributeStr = new StringBuilder("");
            for (String key : attributeMap.keySet()) {
                String attribyteKey = key;
                String attribyteValue = attributeMap.get(key) + "";

                int index = key.indexOf("_LKT_");
                if (index > 0) {
                    attribyteKey = key.substring(0, attribyteKey.indexOf("_LKT"));
                    //属性值
                    attribyteValue = attribyteValue.substring(0, attribyteValue.indexOf("_LKT_"));
                }
                attributeStr.append(attribyteKey);
                attributeStr.append(":");
                attributeStr.append(attribyteValue);
                attributeStr.append(",");
            }
            return StringUtils.trim(attributeStr.toString(), ",");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_HANDLE_FAIL);
        }
    }

}
