package com.laiketui.modules.backend.controller.saas;

import com.laiketui.api.modules.backend.saas.AuthorizeManageService;
import com.laiketui.api.modules.backend.third.WeixinManageService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.weixin.AddThridVo;
import com.laiketui.domain.vo.weixin.AddWeiXinAppTemplateVo;
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
 * 多商户后台-授权管理
 *
 * @author Trick
 * @date 2021/2/3 16:21
 */
@Api(tags = "多商户后台-授权管理")
@RestController
@RequestMapping("/saas/authorize")
public class AuthorizeManageController {

    @Autowired
    private WeixinManageService weixinManageService;

    @ApiOperation("获取模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "templateType", value = "模板行业", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "isUse", value = "是否应用", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "templateName", value = "模板名称", dataType = "int", paramType = "form")
    })
    @PostMapping("/getWeiXinTemplateInfo")
    @HttpApiMethod(apiKey = "saas.authorize.getWeiXinTemplateInfo")
    public Result getWeiXinTemplateInfo(MainVo vo, Integer id, Integer templateType, Integer isUse, String templateName) {
        try {
            return Result.success(weixinManageService.getWeiXinTemplateInfo(vo, id, templateType, isUse, templateName));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑小程序模板")
    @PostMapping("/addWeiXinTemplate")
    @HttpApiMethod(apiKey = "saas.authorize.addWeiXinTemplate")
    public Result addWeiXinTemplate(AddWeiXinAppTemplateVo vo) {
        try {
            return Result.success(weixinManageService.addWeiXinTemplate(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("启用/停用 小程序模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板id", dataType = "int", paramType = "form")
    })
    @PostMapping("/setUseWeiXinTemplate")
    @HttpApiMethod(apiKey = "saas.authorize.setUseWeiXinTemplate")
    public Result setUseWeiXinTemplate(int id) {
        try {
            return Result.success(weixinManageService.setUseWeiXinTemplate(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除小程序模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delWeiXinTemplate")
    @HttpApiMethod(apiKey = "saas.authorize.delWeiXinTemplate")
    public Result delWeiXinTemplate(int id) {
        try {
            return Result.success(weixinManageService.delWeiXinTemplate(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "删除小程序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delThirdMiniApp")
    @HttpApiMethod(apiKey = "saas.authorize.delThirdMiniApp")
    public Result delThirdMiniApp(int id) {
        try {
            return Result.success(weixinManageService.delThirdMiniApp(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private AuthorizeManageService authorizeManageService;

    @ApiOperation(value = "获取小程序发布列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "examineStatus", value = "审核状态", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "releaseStatus", value = "发布状态", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "appName", value = "小程序名称", dataType = "string", paramType = "form")
    })
    @PostMapping("/getThirdInfo")
    @HttpApiMethod(apiKey = "saas.authorize.getThirdInfo")
    public Result getThirdInfo(Integer examineStatus, Integer releaseStatus, String appName, PageModel pageModel) {
        try {
            return Result.success(authorizeManageService.getThirdInfo(examineStatus, releaseStatus, appName, pageModel));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "发布小程序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "form")
    })
    @PostMapping("/release")
    @HttpApiMethod(apiKey = "saas.authorize.release")
    public Result release(Integer id) {
        try {
            return Result.success(authorizeManageService.release(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "获取小程序配置参数信息")
    @PostMapping("/getThridParmate")
    @HttpApiMethod(apiKey = "saas.authorize.getThridParmate")
    public Result getThridParmate(MainVo vo) {
        try {
            return Result.success(authorizeManageService.getThridParmate(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "添加/编辑小程序配置参数")
    @PostMapping("/addThridParmate")
    @HttpApiMethod(apiKey = "saas.authorize.addThridParmate")
    public Result addThridParmate(AddThridVo vo) {
        try {
            return Result.success(authorizeManageService.addThridParmate(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
