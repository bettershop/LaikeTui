package com.laiketui.common.mapper;

import com.laiketui.domain.role.RoleMenuModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 权限菜单
 *
 * @author Trick
 * @date 2021/1/14 15:47
 */
public interface RoleMenuModelMapper extends BaseMapper<RoleMenuModel> {


    /**
     * 获取用户拥有的权限id
     *
     * @param roleId -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 15:44
     */
    @Select("select menu_id from lkt_role_menu where role_id = #{id}")
    List<Integer> getUserRoleMenuInfoToId(int roleId) throws LaiKeApiException;

    /**
     * 根据角色和url判断是否有权限
     *
     * @param roleId -
     * @param url    -
     * @author Trick
     * @date 2021/8/3 15:37
     */
    @Select("select count(1) from lkt_role_menu as a left join lkt_core_menu as b on a.menu_id = b.id " +
            "where a.role_id in (#{roleId}) and b.recycle = 0 and b.url = #{url}")
    int countButtonRole(String roleId, String url);

    /**
     * 根据权限删除对应菜单
     *
     * @param roleId   -
     * @param menuPtId -  平台一级菜单id
     * @return int
     * @author Trick
     * @date 2021/10/28 14:09
     */
    @Delete("delete a.* from lkt_role_menu a,lkt_core_menu b where  a.menu_id=b.id and a.menu_id!=#{menuPtId} and b.s_id!=#{menuPtId} and b.type!=0 " +
            "and a.role_id=#{roleId}")
    int deleteMenu(int roleId, int menuPtId);
}