package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.plugin.MchManageService;
import com.laiketui.domain.user.WithdrawModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.main.AddStoreConfigVo;
import com.laiketui.domain.vo.mch.AddMchVo;
import com.laiketui.domain.vo.user.WithdrawalVo;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 店铺管理
 *
 * @author Trick
 * @date 2021/1/26 11:30
 */
@Api(tags = "后台-店铺管理")
@RestController
@RequestMapping("/admin/mch")
public class MchManageController {

    @Autowired
    private MchManageService mchManageService;

    @ApiOperation("获取店铺信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "店铺id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "isOpen", value = "是否营业：0.未营业 1.营业中 2.打烊", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "promiseStatus", value = "保证金状态 1已交 0未交", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "店铺名称/用户Id", dataType = "string", paramType = "form")
    })
    @PostMapping("/getMchInfo")
    @HttpApiMethod(apiKey = "admin.mch.getMchInfo")
    public Result getMchInfo(MainVo vo, Integer id, Integer isOpen, String name, Integer promiseStatus) {
        try {
            return Result.success(mchManageService.getMchInfo(vo, id, isOpen, name, promiseStatus));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("修改商户信息")
    @PostMapping("/modifyMchInfo")
    @HttpApiMethod(apiKey = "admin.mch.modifyMchInfo")
    public Result modifyMchInfo(AddMchVo vo) {
        try {
            return Result.success(mchManageService.modifyMchInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除商户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mchId", value = "商户id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delMchInfo")
    @HttpApiMethod(apiKey = "admin.mch.delMchInfo")
    public Result delMchInfo(MainVo vo, int mchId) {
        try {
            return Result.success(mchManageService.delMchInfo(vo, mchId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取审核信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "reviewStatus", value = "审核状态：0.待审核 1.审核通过 2.审核不通过", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "商户名称/用户id", dataType = "string", paramType = "form")
    })
    @PostMapping("/getMchExamineInfo")
    @HttpApiMethod(apiKey = "admin.mch.getMchExamineInfo")
    public Result getMchExamineInfo(MainVo vo, Integer id, Integer reviewStatus, String name, HttpServletResponse response) {
        try {
            Map<String, Object> ret = mchManageService.getMchExamineInfo(vo, id, reviewStatus, name, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("审核通过/拒绝")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mchId", value = "商户id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "reviewStatus", value = "审核状态： 1.审核通过 2.审核不通过", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "text", value = "拒绝原因", dataType = "string", paramType = "form"),
    })
    @PostMapping("/examineMch")
    @HttpApiMethod(apiKey = "admin.mch.examineMch")
    public Result examineMch(MainVo vo, Integer mchId, Integer reviewStatus, String text) {
        try {
            return Result.success(mchManageService.examineMch(vo, mchId, reviewStatus, text));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商品审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mchName", value = "商户名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "goodsName", value = "商品名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getGoodsExamineInfo")
    @HttpApiMethod(apiKey = "admin.mch.getGoodsExamineInfo")
    public Result getGoodsExamineInfo(MainVo vo, String mchName, String goodsName, Integer goodsId) {
        try {
            return Result.success(mchManageService.getGoodsExamineInfo(vo, mchName, goodsName, goodsId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商品详情信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/getGoodsDetailInfo")
    @HttpApiMethod(apiKey = "admin.mch.getGoodsDetailInfo")
    public Result getGoodsDetailInfo(MainVo vo, int goodsId) {
        try {
            return Result.success(mchManageService.getGoodsDetailInfo(vo, goodsId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("商品审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "status", value = "审核状态 0=拒绝 1=通过", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "text", value = "拒绝理由", dataType = "string", paramType = "form")
    })
    @PostMapping("/goodsExamine")
    @HttpApiMethod(apiKey = "admin.mch.goodsExamine")
    public Result goodsExamine(MainVo vo, int goodsId, int status, String text) {
        try {
            return Result.success(mchManageService.goodsExamine(vo, goodsId, status, text));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取店铺提现审核列表")
    @PostMapping("/getWithdrawalExamineInfo")
    @HttpApiMethod(apiKey = "admin.mch.getWithdrawalExamineInfo")
    public Result getWithdrawalExamineInfo(WithdrawalVo vo, HttpServletResponse response) {
        try {
            Map<String, Object> result = mchManageService.getWithdrawalInfo(vo, WithdrawModel.EXAME_WAIT_STATUS, response);
            return result == null ? Result.exportFile() : Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("店铺提现审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "提现id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "stauts", required = true, value = "状态 1：审核通过 2：拒绝", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "text", value = "拒绝原因", dataType = "string", paramType = "form"),
    })
    @PostMapping("/withdrawalExamine")
    @HttpApiMethod(apiKey = "admin.mch.withdrawalExamine")
    public Result withdrawalExamine(MainVo vo, int id, int stauts, String text) {
        try {
            return Result.success(mchManageService.withdrawalExamineMch(vo, id, stauts, text));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取提现记录列表")
    @PostMapping("/getWithdrawalInfo")
    @HttpApiMethod(apiKey = "admin.mch.getWithdrawalInfo")
    public Result getWithdrawalInfo(WithdrawalVo vo, HttpServletResponse response) {
        try {
            Map<String, Object> ret = mchManageService.getWithdrawalInfo(vo, null, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商城配置信息")
    @PostMapping("/getStoreConfigInfo")
    @HttpApiMethod(apiKey = "admin.mch.getStoreConfigInfo")
    public Result getStoreConfigInfo(MainVo vo) {
        try {
            return Result.success(mchManageService.getStoreConfigInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改 商城配置")
    @PostMapping("/setStoreConfigInfo")
    @HttpApiMethod(apiKey = "admin.mch.setStoreConfigInfo")
    public Result setStoreConfigInfo(AddStoreConfigVo vo) {
        try {
            return Result.success(mchManageService.setStoreConfigInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
