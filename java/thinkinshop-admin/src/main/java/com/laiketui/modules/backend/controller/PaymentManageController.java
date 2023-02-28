package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.PaymentManageService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.GloabConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 支付管理
 *
 * @author Trick
 * @date 2021/7/15 15:12
 */

@Api(tags = "后台-支付管理")
@RestController
@RequestMapping("/admin/payment")
public class PaymentManageController {

    @Autowired
    private PaymentManageService paymentManageService;

    @ApiOperation("支付类型列表")
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.payment.index")
    public Result index(MainVo vo, Integer id) {
        try {
            return Result.success(paymentManageService.index(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取支付配置参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "支付类型列表id", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/paymentParmaInfo")
    @HttpApiMethod(apiKey = "admin.payment.paymentParmaInfo")
    public Result paymentParmaInfo(MainVo vo, int id) {
        try {
            return Result.success(paymentManageService.paymentParmaInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("设置支付配置参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "支付类型列表id", dataType = "int", required = true, paramType = "form"),
            @ApiImplicitParam(name = "status", value = "是否启用 1=启用 0=未启用", dataType = "int", required = true, paramType = "form"),
            @ApiImplicitParam(name = "json", value = "支付配置参数json", dataType = "string", required = true, paramType = "form")
    })
    @PostMapping("/setPaymentParma")
    @HttpApiMethod(apiKey = "admin.payment.setPaymentParma")
    public Result setPaymentParma(MainVo vo, String json, int id, Integer status) {
        try {
            json = json.replaceAll("\\+","%2B");
            paymentManageService.setPaymentParma(vo, json, id, status);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "支付类型列表id", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/setPaymentSwitch")
    @HttpApiMethod(apiKey = "admin.payment.setPaymentSwitch")
    public Result setPaymentSwitch(MainVo vo, int id) {
        try {
            paymentManageService.setPaymentSwitch(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("微信p12文件上传")
    @PostMapping("/uploadCertP12")
    @HttpApiMethod(apiKey = "admin.payment.uploadCertP12")
    public Result uploadFiles(UploadFileVo vo) {
        try {
            return Result.success(paymentManageService.uploadCertP12(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
