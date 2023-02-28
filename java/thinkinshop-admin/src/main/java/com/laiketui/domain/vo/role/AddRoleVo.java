package com.laiketui.domain.vo.role;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 添加/修改角色参数
 *
 * @author Trick
 * @date 2021/1/14 16:35
 */
@ApiModel(description = "添加/修改角色参数")
public class AddRoleVo extends MainVo {
    @ApiModelProperty(value = "角色id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "角色名称", name = "roleName")
    private String roleName;
    @ApiModelProperty(value = "角色描述", name = "describe")
    private String describe;
    @ApiModelProperty(value = "状态 0:角色 1:客户端", name = "status")
    private Integer status;
    @ApiModelProperty(value = "角色菜单集合", name = "permissions")
    private List<Integer> permissions;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<Integer> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Integer> permissions) {
        this.permissions = permissions;
    }
}
