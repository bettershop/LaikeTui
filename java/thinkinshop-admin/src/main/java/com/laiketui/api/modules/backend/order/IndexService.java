package com.laiketui.api.modules.backend.order;


import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.*;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 订单列表
 *
 * @author wangxian
 */
public interface IndexService {

    /**
     * 后台订单列表
     * 【php orderslist>Index.order_index】
     *
     * @param adminOrderVo -
     * @return Map
     * @throws LaiKeApiException -
     * @author wangxian
     * @date 2021/7/19 14:50
     * @since Trick v1.0
     */
    Map<String, Object> index(AdminOrderListVo adminOrderVo, HttpServletResponse response) throws LaiKeApiException;

    /**
     * 订单统计 - 普通订单
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/19 17:24
     */
    Map<String, Object> orderCount(MainVo vo) throws LaiKeApiException;


    /**
     * 获取订单物流信息
     *
     * @param vo      -
     * @param orderno -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/4 15:29
     */
    Map<String, Object> kuaidishow(MainVo vo, String orderno) throws LaiKeApiException;

    /**
     * 关闭订单
     *
     * @param orderVo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> close(AdminOrderVo orderVo) throws LaiKeApiException;

    /**
     * 删除订单
     *
     * @param vo     -
     * @param orders -
     * @return Map
     * @throws LaiKeApiException-
     * @author wangxian
     * @date 2021/7/21 10:54
     * @since Trick v1.0
     */
    Map<String, Object> del(MainVo vo, String orders) throws LaiKeApiException;

    /**
     * 订单打印
     *
     * @param orderVo
     * @return
     * @throws LaiKeApiException
     */
    List<Map<String, Object>> orderPrint(AdminOrderVo orderVo) throws LaiKeApiException;

    /**
     * 搜索快递公司 express
     *
     * @return
     */
    Map<String, Object> searchExpress(String express) throws LaiKeApiException;

    /**
     * 进入发货界面
     *
     * @param adminDeliveryVo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> deliveryView(AdminDeliveryVo adminDeliveryVo) throws LaiKeApiException;

    /**
     * 订单发货
     *
     * @param vo             -
     * @param exId           -
     * @param exNo           -
     * @param orderDetailIds -
     * @throws LaiKeApiException-
     * @author Administrator
     * @date 2021/8/2 14:42
     */
    void deliverySave(MainVo vo, Integer exId, String exNo, String orderDetailIds) throws LaiKeApiException;

    /**
     * 编辑订单界面
     *
     * @param orderVo
     * @return
     * @throws LaiKeApiException
     */
    Map<String, Object> editOrderView(OrderModifyVo orderVo) throws LaiKeApiException;

    /**
     * 保存编辑订单
     *
     * @param orderVo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/2 10:21
     */
    void saveEditOrder(EditOrderVo orderVo) throws LaiKeApiException;

    /**
     * 代客下单
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/2 17:36
     */
    Map<String, Object> helpOrder(HelpOrderVo vo) throws LaiKeApiException;

    /**
     * 代客下单-结算
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/28 13:13
     */
    Map<String, Object> valetOrderSettlement(HelpOrderVo vo) throws LaiKeApiException;

    /**
     * 平台订单详情
     *
     * @param orderVo
     * @return
     */
    Map<String, Object> orderDetailsInfo(AdminOrderDetailVo orderVo);

}
