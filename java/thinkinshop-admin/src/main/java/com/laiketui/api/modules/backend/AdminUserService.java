package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.UpdateAdminVo;
import com.laiketui.domain.vo.user.AdminLoginVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 商城后台登录
 *
 * @author Trick
 * @date 2021/1/26 11:30
 */
public interface AdminUserService {
    /**
     * 后台登录
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/6/15 15:55
     */
    Map<String, Object> login(AdminLoginVo vo) throws LaiKeApiException;

    /**
     * 赋予系统管理员商城
     *
     * @param vo -
     * @throws LaiKeApiException-
     * @return Map
     * @author Trick
     * @date 2021/8/17 16:27
     */
    Map<String,Object> setUserAdmin(MainVo vo) throws LaiKeApiException;

    /**
     * 修改当前管理员基本信息
     *
     * @param vo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/6 11:38
     */
    void updateAdminInfo(UpdateAdminVo vo) throws LaiKeApiException;


}
