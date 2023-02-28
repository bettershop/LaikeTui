package com.laiketui.modules.backend.controller.saas;

import com.laiketui.api.modules.backend.saas.ShopManageService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.saas.AddShopVo;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.IpUtil;
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
import java.util.Map;

/**
 * 商户管理
 *
 * @author Trick
 * @date 2021/1/28 9:17
 */
@Api(tags = "多商户后台-商户管理")
@RestController
@RequestMapping("/saas/shop")
public class ShopManageController {

    @Autowired
    private ShopManageService shopManageService;

    @ApiOperation("获取商户信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeName", value = "商城名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", dataType = "string", paramType = "form"),
    })
    @PostMapping("/getShopInfo")
    @HttpApiMethod(apiKey = "saas.shop.getShopInfo")
    public Result getShopInfo(MainVo vo, String storeName, String startDate, String endDate, HttpServletResponse response) {
        try {
            Map<String, Object> ret = shopManageService.getShopInfo(vo, storeName, startDate, endDate, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("是否启用开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "商城id", dataType = "int", paramType = "form")
    })
    @PostMapping("/setStoreOpenSwitch")
    @HttpApiMethod(apiKey = "saas.shop.setStoreOpenSwitch")
    public Result setStoreOpenSwitch(Integer storeId) {
        try {
            return Result.success(shopManageService.setStoreOpenSwitch(storeId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("设置默认商城")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "storeId", value = "商城id", dataType = "int", paramType = "form")
    })
    @PostMapping("/setStoreDefaultSwitch")
    @HttpApiMethod(apiKey = "saas.shop.setStoreDefaultSwitch")
    public Result setStoreDefaultSwitch(Integer storeId) {
        try {
            shopManageService.setStoreDefaultSwitch(storeId);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑商城")
    @PostMapping("/addStore")
    @HttpApiMethod(apiKey = "saas.shop.addStore")
    public Result addStore(HttpServletRequest request, AddShopVo vo) {
        try {
            //获取ip地址
            String ip = IpUtil.getIpAddr(request);
            return Result.success(shopManageService.addStore(vo, ip));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除商城")
    @PostMapping("/delStore")
    @HttpApiMethod(apiKey = "saas.shop.delStore")
    public Result delStore(int storeId) {
        try {
            return Result.success(shopManageService.delStore(storeId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminId", value = "管理员id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "pwd", value = "密码", dataType = "string", paramType = "form")
    })
    @PostMapping("/resetAdminPwd")
    @HttpApiMethod(apiKey = "saas.shop.resetAdminPwd")
    public Result resetAdminPwd(int adminId, String pwd) {
        try {
            return Result.success(shopManageService.resetAdminPwd(adminId, pwd));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
