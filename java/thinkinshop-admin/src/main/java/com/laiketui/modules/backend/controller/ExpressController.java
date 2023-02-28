package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.ExpressService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.ExpressSaveVo;
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
 * 物流公司管理
 *
 * @author Trick
 * @date 2021/7/6 16:47
 */
@Api(tags = "后台-物流公司管理")
@RestController
@RequestMapping("/admin/express")
public class ExpressController {

    @Autowired
    private ExpressService expressService;

    @ApiOperation("物流公司列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "keyWord", value = "关键字搜索", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "sortType", value = "0=降序 默认升序", dataType = "int", paramType = "form")
    })
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.express.index")
    public Result index(MainVo vo, Integer id, String keyWord, Integer sortType) {
        try {
            return Result.success(expressService.index(vo, id, keyWord, sortType));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("物流开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "form")
    })
    @PostMapping("/expressSwitch")
    @HttpApiMethod(apiKey = "admin.express.expressSwitch")
    public Result expressSwitch(MainVo vo, Integer id) {
        try {
            expressService.expressSwitch(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("添加/编辑物流")
    @PostMapping("/expressSave")
    @HttpApiMethod(apiKey = "admin.express.expressSave")
    public Result expressSave(ExpressSaveVo vo) {
        try {
            expressService.expressSave(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("批量删除物流公司")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "id集", dataType = "string", paramType = "form")
    })
    @PostMapping("/expressDel")
    @HttpApiMethod(apiKey = "admin.express.expressDel")
    public Result expressDel(MainVo vo, String ids) {
        try {
            expressService.expressDel(vo, ids);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
