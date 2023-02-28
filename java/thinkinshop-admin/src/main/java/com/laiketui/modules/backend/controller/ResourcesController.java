package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.ResourcesService;
import com.laiketui.domain.vo.MainVo;
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

import javax.servlet.http.HttpServletResponse;

/**
 * 资源管理
 *
 * @author Trick
 * @date 2021/7/21 16:38
 */
@Api(tags = "后台-资源管理")
@RestController
@RequestMapping("/admin/resources")
public class ResourcesController {

    @Autowired
    private ResourcesService resourcesService;


    @ApiOperation("获取资源列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mchName", value = "店铺名称", dataType = "int", paramType = "form"),
    })
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.resources.index")
    public Result index(MainVo vo, String mchName) {
        try {
            return Result.success(resourcesService.index(vo, mchName));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("批量下载")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgIds", value = "图片id集", dataType = "string", paramType = "form"),
    })
    @RequestMapping("/downForZip")
    @HttpApiMethod(apiKey = "admin.resources.downForZip")
    public Result downForZip(MainVo vo, HttpServletResponse response, String imgIds) {
        try {
            resourcesService.downForZip(vo, response, imgIds);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("批量删除")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgIds", value = "图片id集", dataType = "string", paramType = "form"),
    })
    @PostMapping("/del")
    @HttpApiMethod(apiKey = "admin.resources.del")
    public Result del(MainVo vo, String imgIds) {
        try {
            resourcesService.del(vo, imgIds);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
