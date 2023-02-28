package com.laiketui.api.modules.backend.users;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.user.AddUserGradeVo;
import com.laiketui.domain.vo.user.AddUserRuleVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 用户等级管理
 *
 * @author Trick
 * @date 2021/1/8 11:26
 */
public interface UserGradeService {


    /**
     * 获取等级列表
     *
     * @param vo  -
     * @param gid - 可选
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 11:27
     */
    Map<String, Object> getUserGradeInfo(MainVo vo, Integer gid) throws LaiKeApiException;


    /**
     * 保存用户等级
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 14:40
     */
    void addUserGrade(AddUserGradeVo vo) throws LaiKeApiException;


    /**
     * 获取赠送商品
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 17:29
     */
    Map<String, Object> getGiveGoodsList(MainVo vo) throws LaiKeApiException;


    /**
     * 删除用户等级
     *
     * @param vo -
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 17:34
     */
    boolean delUserGrade(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 获取用户配置信息
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/8 17:55
     */
    Map<String, Object> getUserConfigInfo(MainVo vo) throws LaiKeApiException;


    /**
     *  增加/修改会员规则
     * @author Trick
     * @date 2021/1/8 18:05
     * @param vo -
     * @throws   LaiKeApiException   -
     */
    void addUserRule(AddUserRuleVo vo) throws LaiKeApiException;
}
