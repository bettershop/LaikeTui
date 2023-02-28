package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.LabelService;
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

/**
 * 商品标签管理
 *
 * @author Trick
 * @date 2021/6/25 18:04
 */
@Api(tags = "后台-商品标签")
@RestController
@RequestMapping("/admin/label")
public class LabelController {

    @Autowired
    private LabelService labelService;


    @ApiOperation("商品标签列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "商品名称", dataType = "string", paramType = "form"),
    })
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.label.index")
    public Result index(MainVo vo, String name, Integer id) {
        try {
            return Result.success(labelService.index(vo, name, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("添加/编辑商品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "商品名称", required = true, dataType = "string", paramType = "form"),
    })
    @PostMapping("/addGoodsLabel")
    @HttpApiMethod(apiKey = "admin.label.addGoodsLabel")
    public Result addGoodsLabel(MainVo vo, String name, Integer id) {
        try {
            labelService.addGoodsLabel(vo, name, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("删除商品标签")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delGoodsLabel")
    @HttpApiMethod(apiKey = "admin.label.delGoodsLabel")
    public Result delGoodsLabel(MainVo vo, int id) {
        try {
            labelService.delGoodsLabel(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
