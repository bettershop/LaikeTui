package com.laiketui.modules.backend.controller.order;

import com.laiketui.api.modules.backend.order.OrderSettlementService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.OrderSettlementVo;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.GloabConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 订单结算
 *
 * @author Trick
 * @date 2021/7/7 11:30
 */
@Api(tags = "后台-订单结算")
@RestController
@RequestMapping("/admin/orderSettlement")
public class OrderSettlementController {

    @Autowired
    private OrderSettlementService orderSettlementService;

    @ApiOperation("订单结算列表")
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.orderSettlement.index")
    public Result index(OrderSettlementVo vo, HttpServletResponse response) {
        try {
            vo.setOrderType(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            Map<String, Object> ret = orderSettlementService.index(vo, response);
            return ret == null ? Result.exportFile():Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("订单详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单号", dataType = "string", paramType = "form")
    })
    @PostMapping("/detail")
    @HttpApiMethod(apiKey = "admin.orderSettlement.detail")
    public Result detail(MainVo vo, String orderNo) {
        try {
            return Result.success(orderSettlementService.detail(vo, orderNo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除结算订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单id", dataType = "int", paramType = "form")
    })
    @PostMapping("/del")
    @HttpApiMethod(apiKey = "admin.orderSettlement.del")
    public Result del(MainVo vo, int id) {
        try {
            orderSettlementService.del(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
