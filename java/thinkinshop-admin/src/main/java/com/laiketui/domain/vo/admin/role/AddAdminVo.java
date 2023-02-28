package com.laiketui.domain.vo.admin.role;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/修改管理员参数
 *
 * @author Trick
 * @date 2021/1/13 15:45
 */
@ApiModel(description = "添加/修改管理员参数")
public class AddAdminVo extends MainVo {
    @ApiModelProperty(value = "管理员id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "管理员账号", name = "adminName")
    private String adminName;
    @ApiModelProperty(value = "管理员密码", name = "adminPWD")
    private String adminPWD;
    @ApiModelProperty(value = "角色id", name = "roleId")
    private Integer roleId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPWD() {
        return adminPWD;
    }

    public void setAdminPWD(String adminPWD) {
        this.adminPWD = adminPWD;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
