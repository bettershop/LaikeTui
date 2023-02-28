package com.laiketui.modules.backend.controller.saas;

import com.laiketui.api.common.admin.PublicRoleService;
import com.laiketui.api.modules.backend.role.RoleService;
import com.laiketui.api.modules.backend.saas.RoleManagerService;
import com.laiketui.domain.mch.RoleModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.menu.AddMenuVo;
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

import java.util.Arrays;

/**
 * 权限管理
 *
 * @author Trick
 * @date 2021/1/28 16:15
 */
@Api(tags = "多商户后台-权限管理")
@RestController
@RequestMapping("/saas/role")
public class RoleController {

    @Autowired
    private RoleManagerService roleManagerService;

    @ApiOperation("获取核心菜单列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "sid", value = "上级id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getMenuInfo")
    @HttpApiMethod(apiKey = "saas.role.getMenuInfo")
    public Result getMenuInfo(MainVo vo, String name, Integer id, Integer sid) {
        try {
            return Result.success(roleManagerService.getMenuInfo(vo, name, id, sid));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("菜单分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "上级id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "sid", value = "下级id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "菜单类型", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "菜单名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "isCore", value = "是否是核心", dataType = "int", paramType = "form")
    })
    @PostMapping("/getMenuLeveInfo")
    @HttpApiMethod(apiKey = "saas.role.getMenuLeveInfo")
    public Result getMenuLeveInfo(MainVo vo, String name, Integer id, Integer sid, Integer type, Integer isCore) {
        try {
            return Result.success(roleManagerService.getMenuLeveInfo(vo, name, id, sid, type, isCore));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑菜单")
    @PostMapping("/addMenuInfo")
    @HttpApiMethod(apiKey = "saas.role.addMenuInfo")
    public Result addMenuInfo(AddMenuVo vo) {
        try {
            roleManagerService.addMenuInfo(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("菜单置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "菜单id", dataType = "int", paramType = "form")
    })
    @PostMapping("/moveTopMenuSort")
    @HttpApiMethod(apiKey = "saas.role.moveTopMenuSort")
    public Result moveTopMenuSort(MainVo vo, int id) {
        try {
            roleManagerService.moveMenuSort(vo, id, 0, 1);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("菜单上下移动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "downId", value = "换位id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "upId", value = "换位id2", dataType = "int", paramType = "form")
    })
    @PostMapping("/moveMenuSort")
    @HttpApiMethod(apiKey = "saas.role.moveMenuSort")
    public Result moveMenuSort(MainVo vo, int downId, int upId) {
        try {
            roleManagerService.moveMenuSort(vo, downId, upId, 2);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "menuId", value = "菜单id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delMenu")
    @HttpApiMethod(apiKey = "saas.role.delMenu")
    public Result delMenu(int menuId) {
        try {
            roleManagerService.delMenu(menuId);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private RoleService roleService;

    @ApiOperation("获取管理员角色信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "管理员id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getRoleListInfo")
    @HttpApiMethod(apiKey = "saas.role.getRoleListInfo")
    public Result getRoleListInfo(MainVo vo, Integer id) {
        try {
            return Result.success(roleService.getRoleListInfo(vo, RoleModel.STATUS_CLIENT, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "权限id", dataType = "int", paramType = "form")
    })
    @PostMapping("/addUserRoleMenu")
    @HttpApiMethod(apiKey = "saas.role.addUserRoleMenu")
    public Result addUserRoleMenu(AddRoleVo vo, Integer id) {
        try {
            vo.setStatus(RoleModel.STATUS_CLIENT);
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
    @HttpApiMethod(apiKey = "saas.role.delUserRoleMenu")
    public Result delUserRoleMenu(AddRoleVo vo, Integer id) {
        try {
            roleService.delUserRoleMenu(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private PublicRoleService publicRoleService;

    @ApiOperation("更具权限获取管理员")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "权限id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "商城名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "isBind", value = "是否绑定 1=已绑定", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/getBindListInfo")
    @HttpApiMethod(apiKey = "saas.role.getBindListInfo")
    public Result getBindListInfo(int roleId, String name, int isBind) {
        try {
            return Result.success(publicRoleService.getBindListInfo(roleId, name, isBind));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("验证商户是否绑定")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminIds", value = "管理员id集", dataType = "int", allowMultiple = true, paramType = "form")
    })
    @PostMapping("/verificationBind")
    @HttpApiMethod(apiKey = "saas.role.verificationBind")
    public Result verificationBind( Integer[] adminIds) {
        try {
            return Result.success(publicRoleService.verificationBind(Arrays.asList(adminIds)));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("绑定/解绑角色")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "权限id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "adminIds", value = "管理员id集", dataType = "int", allowMultiple = true, paramType = "form")
    })
    @PostMapping("/bindRole")
    @HttpApiMethod(apiKey = "saas.role.bindRole")
    public Result bindRole(int roleId, Integer[] adminIds) {
        try {
            return Result.success(roleManagerService.bindRole(roleId, Arrays.asList(adminIds)));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取角色拥有的菜单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/getRoleMenu")
    @HttpApiMethod(apiKey = "saas.role.getRoleMenu")
    public Result getRoleMenu(MainVo vo, int roleId) {
        try {
            return Result.success(roleManagerService.getRoleMenu(vo, roleId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("根据权限获取菜单(动态路由结构)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId", value = "角色Id", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/getAsyncRoutesByRoutes")
    @HttpApiMethod(apiKey = "saas.role.getAsyncRoutesByRoutes")
    public Result getAsyncRoutesByRoutes(MainVo vo) {
        try {
            return Result.success(roleManagerService.getAsyncRoutesByRoutes(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
