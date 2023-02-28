package com.laiketui.common.mapper;

import com.laiketui.domain.role.GuideMenuModel;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * 功能导览菜单
 *
 * @author Trick
 * @date 2021/10/21 16:44
 */
public interface GuideMenuModelMapper extends BaseMapper<GuideMenuModel> {

    @Select("select if(guide_sort is null,0,max(guide_sort)) from lkt_guide_menu where store_id=#{storeId} and role_id=#{roleId}")
    int maxSort(int storeId, int roleId);

    //删除权限功能导览菜单
    @Delete(" delete a.* from lkt_guide_menu a,lkt_core_menu b where  a.menu_id=b.id and a.menu_id!=#{menuPtId} and b.s_id!=#{menuPtId} and b.type!=0 " +
            " and a.role_id=#{roleId} ")
    int deleteGuidMenu(int menuPtId, int roleId);
}