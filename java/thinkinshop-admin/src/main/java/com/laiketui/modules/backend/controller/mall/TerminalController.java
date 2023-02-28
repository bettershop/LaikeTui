package com.laiketui.modules.backend.controller.mall;

import com.laiketui.api.modules.backend.terminal.TerminalService;
import com.laiketui.domain.api.Result;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.mall.SaveTerminalAppVo;
import com.laiketui.domain.vo.admin.mall.SaveTerminalWeiXinVo;
import com.laiketui.root.annotation.HttpApiMethod;
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
 * 终端管理
 *
 * @author Trick
 * @date 2021/7/23 9:27
 */
@Api(tags = "后台-终端管理")
@RestController
@RequestMapping("/admin/terminal")
public class TerminalController {

    @Autowired
    private TerminalService terminalService;

    @ApiOperation(value = "终端管理页面数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "1=微信 2=APP", required = true, dataType = "int", paramType = "form", example = "1"),
    })
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.terminal.index")
    public Result index(MainVo vo, int type) {
        try {
            return Result.success(terminalService.index(vo, type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation(value = "保存终端配置(APP)")
    @PostMapping("/saveApp")
    @HttpApiMethod(apiKey = "admin.terminal.saveApp")
    public Result saveApp(SaveTerminalAppVo vo) {
        try {
            terminalService.saveApp(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation(value = "保存终端配置(微信小程序)")
    @PostMapping("/saveWeiXinApp")
    @HttpApiMethod(apiKey = "admin.terminal.saveWeiXinApp")
    public Result saveWeiXinApp(SaveTerminalWeiXinVo vo) {
        try {
            terminalService.saveWeiXin(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


}
