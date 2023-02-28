package com.laiketui.root.utils.orders;

import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Random;

/**
 * 订单帮助类
 *
 * @author Trick
 * @date 2020/10/27 15:10
 */
public class OrdersUtils {

    private static final Logger logger = LoggerFactory.getLogger(OrdersUtils.class);


    /**
     * 生成订单号
     * 年月日+时间戳(秒)+两位随机
     *
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/27 15:19
     */
    public static String builderOrderNo() throws LaiKeApiException {
        StringBuilder orderNo = new StringBuilder();
        try {
            String dateFormate = FastDateFormat.getInstance(GloabConst.TimePattern.YMD1).format(new Date());
            long dateTime = System.currentTimeMillis() / 1000;
            String randomNum = String.format("%d", new Random(10).nextInt(99));
            orderNo.append(dateFormate);
            orderNo.append(dateTime);
            orderNo.append(randomNum);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("生成订单号失败" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "BuilderOrderNo");
        }
        return orderNo.toString();
    }

    /**
     * 获取订单类型 默认返回GM : 普通订单类型
     * @param sno
     * @return
     */
    public static String getOrderType(String sno){
        if(StringUtils.isEmpty(sno)){
            return DictionaryConst.OrdersType.ORDERS_HEADER_GM;
        }
        return sno.substring(0,2);
    }

    public static void main(String[] args) {
        System.out.println(OrdersUtils.builderOrderNo());
    }
}
