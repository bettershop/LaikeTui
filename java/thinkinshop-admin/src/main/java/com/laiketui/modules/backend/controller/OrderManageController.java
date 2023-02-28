package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.order.CommentsService;
import com.laiketui.api.modules.backend.order.IndexService;
import com.laiketui.api.modules.backend.order.RefundService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.main.RefundVo;
import com.laiketui.domain.vo.order.*;
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
 * 订单管理
 *
 * @author Trick
 * @date 2021/1/5 15:52
 */
@Api(tags = "后台-订单管理")
@RestController
@RequestMapping("/admin/order")
public class OrderManageController {

    @Autowired
    private RefundService refundService;

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private IndexService orderIndexService;

    @ApiOperation("获取售后列表")
    @PostMapping("/getRefundList")
    @HttpApiMethod(apiKey = "admin.order.getRefundList")
    public Result getRefundList(RefundQueryVo vo, HttpServletResponse response) {
        try {
            vo.setOrderType(DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            Map<String, Object>  ret = refundService.getRefundList(vo, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("售后 通过/拒绝")
    @PostMapping("/examine")
    @HttpApiMethod(apiKey = "admin.order.examine")
    public Result examine(RefundVo vo) {
        try {
            return Result.success(refundService.examine(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取评论列表")
    @PostMapping("/getCommentsInfo")
    @HttpApiMethod(apiKey = "admin.order.getCommentsInfo")
    public Result getCommentsInfo(CommentsInfoVo vo) {
        try {
            return Result.success(commentsService.getCommentsInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取评论明细")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "评论id", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getCommentsDetailInfoById")
    @HttpApiMethod(apiKey = "admin.order.getCommentsDetailInfoById")
    public Result getCommentsDetailInfoById(MainVo vo, int cid) {
        try {
            return Result.success(commentsService.getCommentsDetailInfoById(vo, cid));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("回复评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "commentText", value = "评论内容", dataType = "string", paramType = "form")
    })
    @PostMapping("/replyComments")
    @HttpApiMethod(apiKey = "admin.order.replyComments")
    public Result replyComments(MainVo vo, int commentId, String commentText) {
        try {
            return Result.success(commentsService.replyComments(vo, commentId, commentText));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("修改评论")
    @PostMapping("/updateCommentsDetailInfoById")
    @HttpApiMethod(apiKey = "admin.order.updateCommentsDetailInfoById")
    public Result updateCommentsDetailInfoById(UpdateCommentsInfoVo vo) {
        try {
            commentsService.updateCommentsDetailInfoById(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delComments")
    @HttpApiMethod(apiKey = "admin.order.delComments")
    public Result delComments(MainVo vo, int commentId) {
        try {
            return Result.success(commentsService.delComments(vo, commentId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("平台订单详情")
    @PostMapping("/orderDetailsInfo")
    @HttpApiMethod(apiKey = "admin.order.orderDetailsInfo")
    public Result orderDetailsInfo(AdminOrderDetailVo vo) {
        try {
            return Result.success(orderIndexService.orderDetailsInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("后台保存编辑订单信息")
    @PostMapping("/saveEditOrder")
    @HttpApiMethod(apiKey = "admin.order.saveEditOrder")
    public Result saveEditOrder(EditOrderVo vo) {
        try {
            orderIndexService.saveEditOrder(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("跳转编辑订单界面")
    @PostMapping("/editOrderView")
    @HttpApiMethod(apiKey = "admin.order.editOrderView")
    public Result editOrderView(OrderModifyVo vo) {
        try {
            return Result.success(orderIndexService.editOrderView(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("发货提交")
    @PostMapping("/deliverySave")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "exId", value = "快递公司", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "exNo", value = "快递单号", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "orderDetailIds", value = "订单明细id集", required = true, dataType = "string", paramType = "form"),
    })
    @HttpApiMethod(apiKey = "admin.order.deliverySave")
    public Result deliverySave(MainVo vo, Integer exId, String exNo, String orderDetailIds) {
        try {
            orderIndexService.deliverySave(vo, exId, exNo, orderDetailIds);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("发货界面")
    @PostMapping("/deliveryView")
    @HttpApiMethod(apiKey = "admin.order.deliveryView")
    public Result deliveryView(AdminDeliveryVo adminDeliveryVo) {
        try {
            return Result.success(orderIndexService.deliveryView(adminDeliveryVo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
    @ApiOperation("代客下单-结算")
    @PostMapping("/valetOrderSettlement")
    @HttpApiMethod(apiKey = "admin.valetOrder.Settlement")
    public Result valetOrderSettlement(HelpOrderVo vo) {
        try {
            return Result.success(orderIndexService.valetOrderSettlement(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("代客下单")
    @PostMapping("/helpOrder")
    @HttpApiMethod(apiKey = "admin.order.helpOrder")
    public Result helpOrder(HelpOrderVo vo) {
        try {
            return Result.success(orderIndexService.helpOrder(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("搜索物流公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "express", value = "物流名称", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/searchExpress")
    @HttpApiMethod(apiKey = "admin.order.searchExpress")
    public Result searchExpress(String express) {
        try {
            return Result.success(orderIndexService.searchExpress(express));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("订单打印")
    @PostMapping("/orderPrint")
    @HttpApiMethod(apiKey = "admin.order.orderPrint")
    public Result orderPrint(AdminOrderVo orderVo) {
        try {
            return Result.success(orderIndexService.orderPrint(orderVo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orders", value = "订单号集", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/del")
    @HttpApiMethod(apiKey = "admin.order.del")
    public Result del(MainVo vo, String orders) {
        try {
            return Result.success(orderIndexService.del(vo, orders));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("关闭订单")
    @PostMapping("/close")
    @HttpApiMethod(apiKey = "admin.order.close")
    public Result close(AdminOrderVo orderVo) {
        try {
            return Result.success(orderIndexService.close(orderVo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("订单列表")
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.order.index")
    public Result index(AdminOrderListVo vo, HttpServletResponse response) {
        try {
            Map<String, Object>  ret = orderIndexService.index(vo, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取订单物流信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderno", value = "订单号", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/kuaidishow")
    @HttpApiMethod(apiKey = "admin.order.kuaidishow")
    public Result kuaidishow(MainVo vo, String orderno) {
        try {
            return Result.success(orderIndexService.kuaidishow(vo, orderno));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("订单统计")
    @PostMapping("/orderCount")
    @HttpApiMethod(apiKey = "admin.order.orderCount")
    public Result orderCount(MainVo vo) {
        try {
            return Result.success(orderIndexService.orderCount(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
