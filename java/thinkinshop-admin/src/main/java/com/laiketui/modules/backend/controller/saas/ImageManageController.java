package com.laiketui.modules.backend.controller.saas;

import com.laiketui.api.modules.backend.saas.ImageManageService;
import com.laiketui.domain.vo.admin.image.AddImageConfigVo;
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
 * 多商户后台-图片管理
 *
 * @author Trick
 * @date 2021/1/29 17:53
 */
@Api(tags = "多商户后台-图片管理")
@RestController
@RequestMapping("/saas/image")
public class ImageManageController {

    @Autowired
    private ImageManageService imageManageService;

    @ApiOperation("获取商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "类型 1=本地 2=阿里云", dataType = "int", paramType = "form")
    })
    @PostMapping("/getImageConfigInfo")
    @HttpApiMethod(apiKey = "saas.image.getImageConfigInfo")
    public Result getImageConfigInfo(int type) {
        try {
            return Result.success(imageManageService.getImageConfigInfo(type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑图片上传配置")
    @PostMapping("/addImageConfigInfo")
    @HttpApiMethod(apiKey = "saas.image.addImageConfigInfo")
    public Result addImageConfigInfo(AddImageConfigVo vo) {
        try {
            imageManageService.addImageConfigInfo(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
