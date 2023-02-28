package com.laiketui.api.modules.backend.users;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.user.AddUserVo;
import com.laiketui.domain.vo.user.UpdateUserVo;
import com.laiketui.domain.vo.user.UserVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Map;

/**
 * 会员管理
 *
 * @author Trick
 * @date 2021/1/7 10:59
 */
public interface UserManagerService {


    /**
     * 加载会员列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 11:07
     */
    Map<String, Object> getUserInfo(UserVo vo, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 修改用户资料
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 16:08
     */
    boolean updateUserById(UpdateUserVo vo) throws LaiKeApiException;

    /**
    * 获取会员等级类型
    *
    * @param vo -
    * @return LaiKeApiException
    * @throws LaiKeApiException-
    * @author Trick
    * @date 2021/10/22 19:37
    */
    Map<String,Object> getUserGradeType(MainVo vo) throws LaiKeApiException;

    /**
     * 给用户充值
     *
     * @param vo    -
     * @param id    - 用户主键id
     * @param money -
     * @param type - 1=余额充值 2=积分充值 3=
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 17:09
     */
    boolean userRechargeMoney(MainVo vo, int id, BigDecimal money, Integer type) throws LaiKeApiException;


    /**
     * 删除用户
     *
     * @param vo -
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 18:13
     */
    boolean delUserById(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 保存用户
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 10:53
     */
    void saveUser(AddUserVo vo) throws LaiKeApiException;

}
