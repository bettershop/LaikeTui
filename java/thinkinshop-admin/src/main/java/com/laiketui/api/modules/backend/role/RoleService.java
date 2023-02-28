package com.laiketui.api.modules.backend.role;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.role.AddAdminVo;
import com.laiketui.domain.vo.admin.role.LoggerAdminVo;
import com.laiketui.domain.vo.role.AddRoleVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 权限管理
 *
 * @author Trick
 * @date 2021/1/13 12:15
 */
public interface RoleService {


    /**
     * 获取管理员列表
     *
     * @param vo -
     * @param id -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 14:38
     */
    Map<String, Object> getAdminInfo(MainVo vo, Integer id) throws LaiKeApiException;


    /**
     * 获取角色列表信息
     *
     * @param vo     -
     * @param status - 状态 0:角色 1:客户端
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 15:37
     */
    Map<String, Object> getRoleListInfo(MainVo vo, int status, Integer id) throws LaiKeApiException;


    /**
     * 添加/修改管理员信息
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 15:48
     */
    void addAdminInfo(AddAdminVo vo) throws LaiKeApiException;

    /**
     * 删除管理员
     *
     * @param vo -
     * @param id -
     * @return void
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 16:41
     */
    void delAdminInfo(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 启用/禁用管理员
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 17:04
     */
    void stopAdmin(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 获取管理员日志信息
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 17:22
     */
    Map<String, Object> getAdminLoggerInfo(LoggerAdminVo vo, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 批量删除管理员日志
     *
     * @param vo  -
     * @param ids -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 17:37
     */
    boolean delAdminLogger(MainVo vo, String ids) throws LaiKeApiException;


    /**
     * 获取权限列表
     *
     * @param vo -
     * @param id - 权限id
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 9:31
     */
    Map<String, Object> getUserRoleInfo(MainVo vo, Integer id) throws LaiKeApiException;

    /**
     * 获取权限下拉
     *
     * @param vo -
     * @param roleId -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/30 14:09
     */
    Map<String, Object> getUserRoles(MainVo vo, Integer roleId) throws LaiKeApiException;


    /**
     * 获取所有菜单列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 15:51
     */
    Map<String, Object> getUserRoleMenuInfo(MainVo vo) throws LaiKeApiException;


    /**
     * 添加/编辑用户角色菜单
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 16:06
     */
    void addUserRoleMenu(AddRoleVo vo, Integer id) throws LaiKeApiException;


    /**
     * 删除角色
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 17:28
     */
    void delUserRoleMenu(MainVo vo, int id) throws LaiKeApiException;
}
