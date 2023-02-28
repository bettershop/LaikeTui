package com.laiketui.api.common;

import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.mch.AddFreihtVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 关于店铺公共类
 *
 * @author Trick
 * @date 2020/11/13 9:11
 */
public interface PublicMchService {


    /**
     * 验证店铺是否存在
     *
     * @param storeId -
     * @param userId  -
     * @param shopId  -
     * @return MchModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/13 9:14
     */
    MchModel verificationMchExis(int storeId, String userId, int shopId) throws LaiKeApiException;

    /**
     * 自提结算
     *
     * @param storeId       商城id
     * @param shopId        店铺id
     * @param res
     * @param shopAddressId 门店地址
     * @return
     */
    Map<String, Object> settlement(int storeId, int shopId, String res, int shopAddressId, int storeType) throws LaiKeApiException;


    /**
     * 买家确认收货,店主收入
     * 【php mch.parameter】
     * 【自提不会有跨店订单,所以店铺id只会有一个】
     *
     * @param storeId      -
     * @param shopId       - 店铺id
     * @param orderno      - 订单号
     * @param paymentMoney - 订单金额
     * @param allow        - 积分
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 11:10
     */
    void clientConfirmReceipt(int storeId, int shopId, String orderno, BigDecimal paymentMoney, BigDecimal allow) throws LaiKeApiException;


    /**
     * 运费列表
     * 【php freight.freight_list】
     *
     * @param storeId -
     * @param mchId   -
     * @param name    -
     * @param isUse   - 是否只显示被使用的运费信息
     * @param page    -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 14:51
     */
    Map<String, Object> freightList(int storeId, int mchId, String name, Integer isUse, PageModel page) throws LaiKeApiException;


    /**
     * 添加/修改运费
     * 【php freight.freight_add】
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 16:15
     */
    boolean freightAdd(AddFreihtVo vo) throws LaiKeApiException;


    /**
     * 编辑运费显示页面
     * 【php freight.freight_modify_show】
     *
     * @param storeId -
     * @param mchId   -
     * @param id      -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/2 9:10
     */
    Map<String, Object> freightModifyShow(int storeId, int mchId, int id) throws LaiKeApiException;


    /**
     * 删除运费
     * 【php freight.freight_del】
     *
     * @param storeId -
     * @param ids     -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/1 17:25
     */
    void freightDel(int storeId, String ids) throws LaiKeApiException;


    /**
     * 修改默认运费
     * 【php freight.set_default】
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 16:15
     */
    void setDefault(AddFreihtVo vo) throws LaiKeApiException;


    /**
     * 生成提取码
     * 【php mch.extraction_code】
     *
     * @return String
     * @throws LaiKeApiException -
     * @author wangxian
     * @date 2020/12/14 10:49
     */
    String extractionCode() throws LaiKeApiException;

    /**
     * 创建二维码
     * 【 php mch.extraction_code_img】
     *
     * @param extractionCode
     * @return
     * @throws LaiKeApiException
     */
    String createQRCodeImg(String extractionCode, int storeId, int store_type) throws LaiKeApiException;


    /**
     * 注销商户
     *
     * @param storeId -
     * @param mchId   -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/26 15:19
     */
    boolean cancellationShop(int storeId, int mchId) throws LaiKeApiException;


    /**
     * 是否缴纳保证金
     *
     * @param vo     -
     * @param userId -
     * @return boolean
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/26 10:49
     */
    boolean isPromisePay(MainVo vo, String userId) throws LaiKeApiException;

    /**
     * 支付保证金
     *
     * @param vo      -
     * @param payType - 支付方式 wallet_pay=钱包支付...
     * @param pwd     -
     * @param userId  -
     * @param orderNo - 临时订单号
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/26 10:47
     */
    Map<String, Object> paymentPromise(MainVo vo, String payType, String pwd, String userId, String orderNo) throws LaiKeApiException;

}
