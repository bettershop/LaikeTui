package com.laiketui.modules.backend.controller.saas;

import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.api.modules.backend.notie.PublicNoticeService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddNoticeVo;
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
 * 多商户控制台-公告管理
 *
 * @author Trick
 * @date 2021/2/1 10:25
 */
@Api(tags = "多商户后台-公告管理")
@RestController
@RequestMapping("/saas/sysNotice")
public class SysNoticeManageController {

    @Autowired
    private PublicNoticeService publicNoticeService;

    @ApiOperation("获取系统公告信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "公告id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getSysNoticeInfo")
    @HttpApiMethod(apiKey = "saas.sysNotice.getSysNoticeInfo")
    public Result getSysNoticeInfo(MainVo vo, Integer id) {
        try {
            return Result.success(publicNoticeService.getPublicNoticeInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("添加/编辑系统公告")
    @PostMapping("/addSysNoticeInfo")
    @HttpApiMethod(apiKey = "saas.sysNotice.addSysNoticeInfo")
    public Result addSysNoticeInfo(AddNoticeVo vo) {
        try {
            return Result.success(publicNoticeService.addSysNoticeInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除系统公告")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "公告id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "storeId", value = "商城id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delSysNoticeInfo")
    @HttpApiMethod(apiKey = "saas.sysNotice.delSysNoticeInfo")
    public Result delSysNoticeInfo(int storeId, int id) {
        try {
            return Result.success(publicNoticeService.delSysNoticeInfo(storeId, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @Autowired
    private PublicDictionaryService publicDictionaryService;

    @ApiOperation("获取公告类型")
    @PostMapping("/getNoticeTypeInfo")
    @HttpApiMethod(apiKey = "saas.sysNotice.getNoticeTypeInfo")
    public Result getNoticeTypeInfo() {
        try {
            return Result.success(publicDictionaryService.getDictionaryById("公告类型", null));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
