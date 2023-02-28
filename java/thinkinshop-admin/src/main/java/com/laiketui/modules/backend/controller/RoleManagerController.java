package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.role.RoleService;
import com.laiketui.domain.mch.RoleModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.role.AddAdminVo;
import com.laiketui.domain.vo.admin.role.LoggerAdminVo;
import com.laiketui.domain.vo.role.AddRoleVo;
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

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 权限管理
 *
 * @author Trick
 * @date 2021/1/13 12:14
 */
@Api(tags = "后台-权限管理")
@RestController
@RequestMapping("/admin/role")
public class RoleManagerController {

    @Autowired
    private RoleService roleService;

    @ApiOperation("获取管理员列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getAdminInfo")
    @HttpApiMethod(apiKey = "admin.role.getAdminInfo")
    public Result getAdminInfo(MainVo vo, Integer id) {
        try {
            return Result.success(roleService.getAdminInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取管理员角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getRoleListInfo")
    @HttpApiMethod(apiKey = "admin.role.getRoleListInfo")
    public Result getRoleListInfo(MainVo vo, Integer id) {
        try {
            return Result.success(roleService.getRoleListInfo(vo, RoleModel.STATUS_ROLE, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改管理员信息")
    @PostMapping("/addAdminInfo")
    @HttpApiMethod(apiKey = "admin.role.addAdminInfo")
    public Result addAdminInfo(AddAdminVo vo) {
        try {
            roleService.addAdminInfo(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除管理员信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delAdminInfo")
    @HttpApiMethod(apiKey = "admin.role.delAdminInfo")
    public Result delAdminInfo(MainVo vo, int id) {
        try {
            roleService.delAdminInfo(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("禁用/启用管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/stopAdmin")
    @HttpApiMethod(apiKey = "admin.role.stopAdmin")
    public Result stopAdmin(MainVo vo, int id) {
        try {
            roleService.stopAdmin(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取管理员日志列表")
    @PostMapping("/getAdminLoggerInfo")
    @HttpApiMethod(apiKey = "admin.role.getAdminLoggerInfo")
    public Result getAdminLoggerInfo(LoggerAdminVo vo, HttpServletResponse response) {
        try {
            Map<String, Object> ret = roleService.getAdminLoggerInfo(vo, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("批量删除管理员日志")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "管理员id集", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/delAdminLogger")
    @HttpApiMethod(apiKey = "admin.role.delAdminLogger")
    public Result delAdminLogger(MainVo vo, String ids) {
        try {
            return Result.success(roleService.delAdminLogger(vo, ids));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取权限列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getUserRoleInfo")
    @HttpApiMethod(apiKey = "admin.role.getUserRoleInfo")
    public Result getUserRoleInfo(MainVo vo, Integer id) {
        try {
            return Result.success(roleService.getUserRoleInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取权限下拉")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getUserRoles")
    @HttpApiMethod(apiKey = "admin.role.getUserRoles")
    public Result getUserRoles(MainVo vo, Integer id) {
        try {
            return Result.success(roleService.getUserRoles(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取权限菜单列表")
    @PostMapping("/getUserRoleMenuInfo")
    @HttpApiMethod(apiKey = "admin.role.getUserRoleMenuInfo")
    public Result getUserRoleMenuInfo(MainVo vo) {
        try {
            return Result.success(roleService.getUserRoleMenuInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", dataType = "int", paramType = "form")
    })
    @PostMapping("/addUserRoleMenu")
    @HttpApiMethod(apiKey = "admin.role.addUserRoleMenu")
    public Result addUserRoleMenu(AddRoleVo vo, Integer id) {
        try {
            vo.setStatus(RoleModel.STATUS_ROLE);
            roleService.addUserRoleMenu(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delUserRoleMenu")
    @HttpApiMethod(apiKey = "admin.role.delUserRoleMenu")
    public Result delUserRoleMenu(AddRoleVo vo, Integer id) {
        try {
            roleService.delUserRoleMenu(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }
}
