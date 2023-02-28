package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.AdminUserService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.UpdateAdminVo;
import com.laiketui.domain.vo.user.AdminLoginVo;
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
 * 商城后台登录
 *
 * @author Trick
 * @date 2021/1/26 11:30
 */
@Api(tags = "后台-商城后台登录")
@RestController
@RequestMapping("/admin/saas/user")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;

    @ApiOperation("后台登录")
    @PostMapping("/login")
    @HttpApiMethod(apiKey = "admin.saas.user.login")
    public Result login(AdminLoginVo vo) {
        try {
            return Result.success(adminUserService.login(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("赋予系统管理员商城")
    @PostMapping("/setUserAdmin")
    @HttpApiMethod(apiKey = "admin.saas.user.setUserAdmin")
    public Result setUserAdmin(MainVo vo) {
        try {
            return Result.success(adminUserService.setUserAdmin(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("修改基本信息")
    @PostMapping("/updateAdminInfo")
    @HttpApiMethod(apiKey = "admin.saas.user.updateAdminInfo")
    public Result updateAdminInfo(UpdateAdminVo vo) {
        try {
            adminUserService.updateAdminInfo(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
