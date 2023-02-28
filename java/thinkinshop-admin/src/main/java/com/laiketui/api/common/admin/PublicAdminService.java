package com.laiketui.api.common.admin;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.GloabConst;

import java.util.Map;

/**
 * 公共后台方法
 *
 * @author Trick
 * @date 2021/8/13 17:23
 */
public interface PublicAdminService {

    /**
     * 首页统计报表
     *
     * @param vo    -
     * @param mchId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 10:11
     */
    Map<String, Object> index(MainVo vo, Integer mchId) throws LaiKeApiException;

    /**
     * 添加/编辑导航栏跳转地址
     *
     * @param vo       -
     * @param type     -跳转类型 JumpPathModel.JumpType
     * @param sid      - 上级id
     * @param jumpPath - 跳转路径
     * @param params   - 参数
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/15 10:21
     */
    void addJumpPath(MainVo vo, String sid, String name, int type, int sourceType, GloabConst.LaikeTuiUrl.JumpPath jumpPath, String[] params) throws LaiKeApiException;

    /**
     * 系统推送消息
     *
     * @param vo         -
     * @param type       - 消息类型
     * @param tuiTitle   - 推送标题
     * @param tuiContext - 推送内容
     * @param tuiUserId  - 推送人
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/22 17:33
     */
    void systemMessageSend(MainVo vo, int type, String tuiTitle, String tuiContext, String tuiUserId) throws LaiKeApiException;
}
