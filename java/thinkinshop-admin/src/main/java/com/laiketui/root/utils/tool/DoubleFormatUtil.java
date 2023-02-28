package com.laiketui.root.utils.tool;

import java.math.BigDecimal;

/**
 * @author wangxian
 */
public final class DoubleFormatUtil {

    /**
     * 留两位小数点
     * @param num
     * @return
     */
    public static double format(double num,int scale){
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(num));
        return bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 留两位小数点
     * @param num
     * @return
     */
    public static double format(double num){
        return format(num,2);
    }

    /**
     * 留两位小数点
     * @param num
     * @return
     */
    public static String formattf(double num){
        return String.valueOf(format(num,2));
    }

}
