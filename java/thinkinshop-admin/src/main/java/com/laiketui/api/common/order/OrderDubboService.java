package com.laiketui.api.common.order;


import com.laiketui.domain.vo.OrderVo;
import com.laiketui.domain.vo.order.*;
import com.laiketui.root.exception.LaiKeApiException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 订单服务类
 *
 * @author wangxian
 */
public interface OrderDubboService {

    /**
     * 订单确认页面
     *
     * @param vo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> settlement(OrderVo vo);


    /**
     * 下单：生成订单
     *
     * @param vo
     * @return
     */
    Map<String, Object> payment(OrderVo vo);

    /**
     * 更新订单备注
     *
     * @param vo
     * @return
     */
    Map<String, Object> updateOrderRemark(OrderVo vo);


    /**
     * 拆分订单
     *
     * @param vo
     * @return
     */
    Map<String, Object> splitOrder(OrderVo vo);


    /**
     * 申请售后
     * 【php order.ReturnData】
     *
     * @param vo   -
     * @param file -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/4 15:21
     */
    Map<String, Object> returnData(ApplyReturnDataVo vo, MultipartFile file) throws LaiKeApiException;


    /**
     * 订单列表
     *
     * @param vo
     * @return
     */
    Map<String, Object> orderList(OrderVo vo);


    /**
     * 提醒发货
     *
     * @return
     */
    Map<String, Object> remindDelivery(OrderVo vo);


    /**
     * 取消订单
     *
     * @return
     */
    Map<String, Object> cancleOrder(OrderVo vo);

    /**
     * 加载更多订单
     *
     * @param vo
     * @return
     */
    Map<String, Object> loadMore(OrderVo vo);

    /**
     * 删除订单
     *
     * @param vo
     * @return
     */
    Map<String, Object> delOrder(OrderVo vo);

    /**
     * 再次购买
     *
     * @param vo
     * @return
     */
    Map<String, Object> buyAgain(BuyAgainVo vo);

    /**
     * 搜索
     *
     * @param vo
     * @return
     */
    Map<String, Object> orderSearch(OrderVo vo);

    /**
     * 删除购物车
     *
     * @param vo
     * @return
     */
    Map<String, Object> delCart(OrderVo vo);


    /**
     * 获取支付方式
     *
     * @param vo
     * @return
     */
    Map<String, Object> getPaymentConf(OrderVo vo);

    /**
     * 订单详情 移动端个人中心
     *
     * @param vo
     * @return
     */
    Map<String, Object> orderDetails(OrderVo vo);

    /**
     * 查看物流
     *
     * @param vo
     * @return
     */
    Map<String, Object> showLogistics(OrderVo vo);


    /**
     * 撤销申请
     *
     * @param storeId
     * @param id
     * @return
     */
    Map<String, Object> cancleApply(int storeId, int id);

    /**
     * 退货信息
     *
     * @return
     */
    Map<String, Object> returnOrderList(OrderVo vo);

    /**
     * 订单详情/订单列表-确认收货
     *
     * @param orderVo
     * @return
     */
    Map<String, Object> okOrder(OrderVo orderVo);

    /**
     * 订单点击退货后，进入的页面 return_method
     *
     * @param vo
     * @return
     */
    Map<String, Object> returnMethod(OrderVo vo);


    /**
     * 获取收货地址和快递信息
     *
     * @param stroeId
     * @param productId
     * @return
     */
    Map<String, Object> seeSend(int stroeId, int productId);

    /**
     * 快递回寄信息
     *
     * @param returnGoodsVo
     * @return
     */
    Map<String, Object> backSend(ReturnGoodsVo returnGoodsVo);

    /**
     * 查看提货码
     *
     * @param vo
     * @return
     */
    Map<String, Object> seeExtractionCode(OrderVo vo);

    /**
     * 售后订单详情
     *
     * @return
     */
    Map<String, Object> returndetails(RefundDetailsVo refundDetailsVo);

    /**
     * 售后确认收货
     *
     * @param params
     * @return
     */
    Map<String, Object> confirmReceipt(ReturnConfirmReceiptVo vo);

    /**
     * 撤销售后申请
     *
     * @param params
     * @return
     */
    Map<String, Object> cancelApplication(CancleAfterSaleApplyVo params);


}
