package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.data.DataService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 数据报表管理
 *
 * @author Trick
 * @date 2021/1/12 9:26
 */
@Api(tags = "后台-数据管理")
@RestController
@RequestMapping("/admin/data")
public class DataManagerController {

    @Autowired
    private DataService dataService;

    @ApiOperation("获取新增会员报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form"),
    })
    @PostMapping("/getAddUserInfo")
    @HttpApiMethod(apiKey = "admin.data.getAddUserInfo")
    public Result getAddUserInfo(MainVo vo, String startDate, String endDate) {
        try {
            return Result.success(dataService.getAddUserInfo(vo, startDate, endDate));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取新增会员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form"),
    })
    @PostMapping("/getAddUserList")
    @HttpApiMethod(apiKey = "admin.data.getAddUserList")
    public Result getAddUserList(MainVo vo, String startDate, String endDate) {
        try {
            return Result.success(dataService.getAddUserList(vo, startDate, endDate));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取新增会员比例")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form"),
    })
    @PostMapping("/getUserDistributionInfo")
    @HttpApiMethod(apiKey = "admin.data.getUserDistributionInfo")
    public Result getUserDistributionInfo(MainVo vo, String startDate, String endDate) {
        try {
            return Result.success(dataService.getUserDistributionInfo(vo, startDate, endDate));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取用户消费报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form"),
    })
    @PostMapping("/getUserConsumptionInfo")
    @HttpApiMethod(apiKey = "admin.data.getUserConsumptionInfo")
    public Result getUserConsumptionInfo(MainVo vo, String startDate, String endDate) {
        try {
            return Result.success(dataService.getUserConsumptionInfo(vo, startDate, endDate));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取订单报表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "1=今天 2= 本周 3=本月 4=本年", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getOrderReport")
    @HttpApiMethod(apiKey = "admin.data.getOrderReport")
    public Result getOrderReport(MainVo vo, String startDate, String endDate, Integer type) {
        try {
            return Result.success(dataService.getOrderReport(vo, startDate, endDate, type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商品报表")
    @PostMapping("/getGoodsReport")
    @HttpApiMethod(apiKey = "admin.data.getGoodsReport")
    public Result getGoodsReport(MainVo vo) {
        try {
            return Result.success(dataService.getGoodsReport(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取商品报表-加载商品列")
    @PostMapping("/getGoodsReportGoodsList")
    @HttpApiMethod(apiKey = "admin.data.getGoodsReportGoodsList")
    public Result getGoodsReportGoodsList(MainVo vo) {
        try {
            return Result.success(dataService.getGoodsReportGoodsList(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
