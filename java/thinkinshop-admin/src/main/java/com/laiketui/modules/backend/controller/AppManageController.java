package com.laiketui.modules.backend.controller;

import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.modules.backend.third.WeixinManageService;
import com.laiketui.domain.vo.MainVo;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 小程序管理
 *
 * @author Trick
 * @date 2021/1/19 16:31
 */
@Api(tags = "后台-小程序管理")
@RestController
@RequestMapping("/admin/weixinApp")
public class AppManageController {
    @Autowired
    private WeixinManageService weixinManageService;

    @ApiOperation("获取小程序引导图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "引导图id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getWeiXinGuideImageInfo")
    @HttpApiMethod(apiKey = "admin.weixinApp.getWeiXinGuideImageInfo")
    public Result getWeiXinGuideImageInfo(MainVo vo, Integer id) {
        try {
            return Result.success(weixinManageService.getWeiXinGuideImageInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑微信引导图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "引导图id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "类型 0=安装 1=启动", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "sort", value = "序号", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "imgUrl", value = "引导图url", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/addWeiXinGuideImage")
    @HttpApiMethod(apiKey = "admin.weixinApp.addWeiXinGuideImage")
    public Result addWeiXinGuideImage(MainVo vo, Integer id, String imgUrl, Integer sort, int type) {
        try {
            return Result.success(weixinManageService.addWeiXinGuideImage(vo, id, imgUrl, sort, type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除引导图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "引导图id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delWeiXinGuideImage")
    @HttpApiMethod(apiKey = "admin.weixinApp.delWeiXinGuideImage")
    public Result delWeiXinGuideImage(MainVo vo, int id) {
        try {
            return Result.success(weixinManageService.delWeiXinGuideImage(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "模板id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "templateType", value = "模板行业", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "isUse", value = "是否应用", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "templateName", value = "模板名称", dataType = "int", paramType = "form")
    })
    @PostMapping("/getWeiXinTemplateInfo")
    public Result getWeiXinTemplateInfo(MainVo vo, Integer id, Integer templateType, Integer isUse, String templateName) {
        try {
            return Result.success(weixinManageService.getWeiXinTemplateInfo(vo, id, templateType, isUse, templateName));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑小程序模板")
    @PostMapping("/addWeiXinTemplate")
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
    public Result delWeiXinTemplate(int id) {
        try {
            return Result.success(weixinManageService.delWeiXinTemplate(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取微信预授权码")
    @PostMapping("/getPreAuthorizationCode")
    public Result getPreAuthorizationCode(MainVo vo) {
        try {
            return Result.success(weixinManageService.getPreAuthorizationCode(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取已授权小程序信息")
    @PostMapping("/getAuthorizationAppInfo")
    public Result getAuthorizationAppInfo(MainVo vo) {
        try {
            return Result.success(weixinManageService.getAuthorizationAppInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "授权事件接收URL", notes = "微信回调接口", hidden = true)
    @PostMapping("/ticketCallBack")
    public Result ticketCallBack(HttpServletRequest request, HttpServletResponse response) {
        try {
            return Result.success(weixinManageService.ticketCallBack(request, response));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "撤销审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "商城id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/revokeExamine")
    public Result revokeExamine(int storeId) {
        try {
            return Result.success(weixinManageService.revokeExamine(storeId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "删除小程序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delThirdMiniApp")
    public Result delThirdMiniApp(int id) {
        try {
            return Result.success(weixinManageService.delThirdMiniApp(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "下载普通二维码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "商城id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "width", value = "宽度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "1=普通二维码 2=小程序码 3=体验版", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/downQrcode")
    public Result downQrcode(int storeId, int width, int type) {
        try {
            return Result.success(weixinManageService.downQrcode(storeId, width, type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "小程序提交审核")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "商城id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "templateId", value = "模板id", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/submitAppExamine")
    public Result submitAppExamine(int storeId, String templateId) {
        try {
            return Result.success(weixinManageService.submitAppExamine(storeId, templateId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private PublicDictionaryService publicDictionaryService;

    @ApiOperation("获取行业下拉")
    @PostMapping("/getIndustry")
    public Result getIndustry() {
        try {
            return Result.success(publicDictionaryService.getDictionaryById("小程序模板行业", null));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
