package com.laiketui.modules.backend.controller.order;


import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.ConfigVo;
import com.laiketui.modules.backend.services.order.ConfigServiceImpl;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.GloabConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单设置
 * @author Trick
 */
@Api(tags = "后台-订单设置")
@RestController
@RequestMapping("/admin/orderSet")
public class OrderSetController {

    @Autowired
    private ConfigServiceImpl configService;

    @ApiOperation("获取订单设置")
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.orderSet.index")
    public Result index(MainVo vo) {
        try {
            return Result.success(configService.configShow(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("订单设置")
    @PostMapping("/saveConfig")
    @HttpApiMethod(apiKey = "admin.orderSet.saveConfig")
    public Result saveConfig(ConfigVo vo) {
        try {
            configService.saveConfig(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
