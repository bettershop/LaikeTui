package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.PcManageService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.pc.AddBannerInfoVo;
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
 * pc管理
 *
 * @author Trick
 * @date 2021/1/22 10:38
 */
@Api(tags = "后台-pc管理")
@RestController
@RequestMapping("/admin/pc")
public class PcManageController {
    @Autowired
    private PcManageService pcManageService;

    @ApiOperation("获取app版本配置信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getBannerInfo")
    public Result getBannerInfo(MainVo vo, Integer id) {
        try {
            return Result.success(pcManageService.getBannerInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑轮播图信息")
    @PostMapping("/addBannerInfo")
    public Result addBannerInfo(AddBannerInfoVo vo) {
        try {
            return Result.success(pcManageService.addBannerInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除轮播图")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delBannerById")
    public Result delBannerById(int id) {
        try {
            return Result.success(pcManageService.delBannerById(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("轮播图置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "轮播图id", dataType = "int", paramType = "form")
    })
    @PostMapping("/topBannerById")
    public Result topBannerById(int id) {
        try {
            return Result.success(pcManageService.topBannerById(id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
