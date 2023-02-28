package com.laiketui.common.mapper;

import com.laiketui.domain.role.CoreMenuModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 核心菜单
 *
 * @author Trick
 * @date 2021/1/14 11:39
 */
public interface CoreMenuModelMapper extends BaseMapper<CoreMenuModel> {


    /**
     * 获取某级菜单
     *
     * @param sid -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/14 11:40
     */
    @Select("select * from lkt_core_menu where s_id = #{sid} and recycle = 0 order by sort")
    List<Map<String, Object>> getCoreMenuInfoBySid(int sid) throws LaiKeApiException;


    /**
     * 获取核心菜单信息
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 16:26
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取功能导览
     *
     * @param map -
     * @return List -
     * @throws LaiKeApiException -
     */
    List<Map<String, Object>> getFunctionOverview(Map<String, Object> map);


    List<Integer> getRoleMenuIds(Map<String, Object> map) throws LaiKeApiException;

    //获取角色拥有的权限信息
    List<Map<String, Object>> getRoleMenuInfos(Map<String, Object> map) throws LaiKeApiException;

    int countFunctionOverview(Map<String, Object> map);

    /**
     * 获取核心菜单信息 - 统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 16:26
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取最大的序号
     *
     * @param id -
     * @return int
     * @author Trick
     * @date 2021/6/10 9:26
     */
    @Select("select if(sort is null ,0,max(sort)) from lkt_core_menu where level=#{level}")
    int maxSort(int level);

    //获取平台菜单
    @Select("select * from lkt_core_menu where module='Platform' and type=0 and level=1")
    CoreMenuModel getSystemMenu();

    //获取当前级别菜单最新序号
    @Select("select if(sort is null ,0,max(sort)) from lkt_core_menu where level=#{level} and s_id=#{sid} and type=#{type}")
    int maxSortByLevel(int level, int sid, int type);

    //删除菜单
    @Delete("delete a,b,c from lkt_core_menu a left join lkt_role_menu b on a.id=b.menu_id left join lkt_guide_menu c on a.id=c.menu_id where a.id=#{menuId}")
    int delCoreMenu(int menuId);

    /**
     * 序号换位
     *
     * @param id     -
     * @param moveId -
     * @return int
     * @author Trick
     * @date 2021/6/10 9:38
     */
    @Update("update lkt_core_menu a,lkt_core_menu b set a.sort= b.sort,b.sort=a.sort where a.id=#{id} and b.id=#{moveId}")
    int moveSort(int id, int moveId);
}