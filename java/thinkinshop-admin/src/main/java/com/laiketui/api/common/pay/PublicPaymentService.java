package com.laiketui.api.common.pay;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 支付接口
 *
 * @author Trick
 * @date 2020/12/3 15:10
 */
public interface PublicPaymentService {

    /**
     * 支付成功回调地址
     */
    String RETURNURL = "http://trick.free.idcfengye.com/mch/alipayCallBack";
    /**
     * 支付成功异步回调地址
     */
    String NOTIFYURL = "http://trick.free.idcfengye.com/mch/alipayCallBack";

    /**
     * 二维码支付
     *
     * @param appid      -
     * @param privateKey -
     * @param publicKey  -
     * @param orderno    - 订单号
     * @param orderAmt   - 订单金额
     * @return String -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/3 15:13
     */
    String qrCodeOrder(String appid, String privateKey, String publicKey, String orderno, BigDecimal orderAmt) throws LaiKeApiException;


    /**
     * 退款
     *
     * @param storeId   -
     * @param id        - 订单id
     * @param className - 支付方式
     * @param treadeNo  -  交易流水号
     * @param refundAmt - 退款金额
     * @return Map<String, String>
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/3 15:31
     */
    Map<String, String> refundOrder(int storeId, Integer id, String className, String treadeNo, BigDecimal refundAmt) throws LaiKeApiException;


    /**
     * 余额支付
     *
     * @param userId -
     * @param money  -
     * @param token  -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/26 11:57
     */
    default void walletPay(String userId, BigDecimal money, String token) throws LaiKeApiException {
    }

    /**
     * 积分支付
     *
     * @param userId     -
     * @param money      -
     * @param orderNo    -
     * @param orderPrice -
     * @param token      -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/11/2 11:46
     */
    default void integralPay(String userId, BigDecimal money, String orderNo, BigDecimal orderPrice, String token) throws LaiKeApiException {

    }

    /**
     * 钱包退款
     *
     * @param userId -
     * @param money  -
     * @param token  -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/26 14:24
     */
    default void walletReturnPay(String userId, BigDecimal money, String token) throws LaiKeApiException {
    }

}
