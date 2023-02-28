package com.laiketui.api.modules.backend.saas;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.menu.AddMenuVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.List;
import java.util.Map;

/**
 * 权限管理
 *
 * @author Trick
 * @date 2021/1/28 16:16
 */
public interface RoleManagerService {
    /**
     * 获取菜单列表
     *
     * @param name -
     * @param id   -
     * @param vo   -
     * @param sid  -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 9:21
     */
    Map<String, Object> getMenuInfo(MainVo vo, String name, Integer id, Integer sid) throws LaiKeApiException;


    /**
     * 获取菜单级别列表
     *
     * @param vo     -
     * @param sid    -
     * @param id     -
     * @param name   -
     * @param type   -类型 0.后台管理 1.小程序 2.app 3.微信公众号 4.PC 5.生活号 6.报表 7.支付宝小程序
     * @param isCore -是否是核心
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 17:12
     */
    Map<String, Object> getMenuLeveInfo(MainVo vo, String name, Integer id, Integer sid, Integer type, Integer isCore) throws LaiKeApiException;


    /**
     * 添加/编辑菜单
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 11:42
     */
    void addMenuInfo(AddMenuVo vo) throws LaiKeApiException;

    /**
     * 菜单顺序移动
     *
     * @param vo     -
     * @param id     -
     * @param moveId - 被换位的id
     * @param type   -1=置顶 2=上移下移
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/6/9 19:05
     */
    void moveMenuSort(MainVo vo, int id, Integer moveId, Integer type) throws LaiKeApiException;

    /**
     * 删除菜单
     *
     * @param menuId -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 15:04
     */
    void delMenu(int menuId) throws LaiKeApiException;


    /**
     * 绑定/解绑角色
     *
     * @param roleId   -
     * @param adminIds -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 17:07
     */
    boolean bindRole(int roleId, List<Integer> adminIds) throws LaiKeApiException;

    /**
     * 根据角色获取菜单
     *
     * @param vo     -
     * @param roleId -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/13 10:45
     */
    Map<String, Object> getRoleMenu(MainVo vo, int roleId) throws LaiKeApiException;

    /**
     * 获取权限菜单---路由结构
     *
     * @param vo     -
     * @param roleId -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/23 14:25
     */
    Map<String, Object> getAsyncRoutesByRoutes(MainVo vo) throws LaiKeApiException;
}
