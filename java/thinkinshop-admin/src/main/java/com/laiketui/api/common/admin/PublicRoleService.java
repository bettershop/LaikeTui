package com.laiketui.api.common.admin;

import com.laiketui.domain.role.CoreMenuModel;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.List;
import java.util.Map;

/**
 * 关于菜单、权限
 *
 * @author Trick
 * @date 2021/1/29 9:42
 */
public interface PublicRoleService {


    /**
     * 更具菜单id获取树形结构
     *
     * @param roleId - 可选 角色id,用于选中
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 9:44
     */
    List<Map<String, Object>> getMenuTreeList(Integer roleId) throws LaiKeApiException;


    /**
     * 更具权限id获取树形结构
     *
     * @param roleId - 可选 角色id,用于选中
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021-06-11 15:56:42
     */
    List<Map<String, Object>> getRoleTreeList(Integer roleId) throws LaiKeApiException;


    /**
     * 获取当前权限id获取上级菜单信息
     *
     * @param fatherId -
     * @param list     -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 11:22
     */
    void getRoleFatherById(int fatherId, List<CoreMenuModel> list) throws LaiKeApiException;

    /**
     * 获取所有商户
     *
     * @param roleId -
     * @param name   -
     * @param isBind - 是否绑定
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 16:38
     */
    Map<String, Object> getBindListInfo(int roleId, String name, int isBind) throws LaiKeApiException;


    /**
     * 验证商户是否绑定角色
     *
     * @param adminIds -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 17:11
     */
    boolean verificationBind(List<Integer> adminIds) throws LaiKeApiException;


}
