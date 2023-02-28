package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.notie.PublicNoticeService;
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

import java.util.Map;

/**
 * 公告管理
 *
 * @author Trick
 * @date 2021/1/19 15:43
 */
@Api(tags = "后台-公告管理")
@RestController
@RequestMapping("/admin/notice")
public class PublicNoticeController {

    @Autowired
    private PublicNoticeService publicNoticeService;

    @ApiOperation("获取售后列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "公告id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getPublicNoticeInfo")
    @HttpApiMethod(apiKey = "admin.notice.getPublicNoticeInfo")
    public Result getPublicNoticeInfo(MainVo vo, Integer id) {
        try {
            Map<String, Object> result = publicNoticeService.getPublicNoticeInfo(vo, id);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("商城消息通知")
    @PostMapping("/noticeList")
    @HttpApiMethod(apiKey = "admin.notice.noticeList")
    public Result noticeList(MainVo vo) {
        try {
            return Result.success(publicNoticeService.noticeList(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("标记通知已读状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "types", value = "消息类型,一键已读,多个类型用,隔开", dataType = "string", paramType = "form"),
    })
    @PostMapping("/noticeRead")
    @HttpApiMethod(apiKey = "admin.notice.noticeRead")
    public Result noticeRead(MainVo vo, Integer id, String types) {
        try {
            publicNoticeService.noticeRead(vo, id, types);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
