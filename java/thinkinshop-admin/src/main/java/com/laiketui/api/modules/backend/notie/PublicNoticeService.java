package com.laiketui.api.modules.backend.notie;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddNoticeVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 公告管理
 *
 * @author Trick
 * @date 2021/1/19 15:44
 */
public interface PublicNoticeService {

    /**
     * 获取公告列表
     *
     * @param vo -
     * @param id -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 15:45
     */
    Map<String, Object> getPublicNoticeInfo(MainVo vo, Integer id) throws LaiKeApiException;


    /**
     * 添加/编辑系统公告
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 10:51
     */
    boolean addSysNoticeInfo(AddNoticeVo vo) throws LaiKeApiException;


    /**
     * 删除系统公告
     *
     * @param storeId -
     * @param id      -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 11:23
     */
    boolean delSysNoticeInfo(int storeId, Integer id) throws LaiKeApiException;

    /**
     * 商城消息通知
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/7 16:27
     */
    Map<String, Object> noticeList(MainVo vo) throws LaiKeApiException;

    /**
     * 标记消息已读
     *
     * @param vo   -
     * @param id   -
     * @param types -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/7 20:13
     */
    void noticeRead(MainVo vo, Integer id, String types) throws LaiKeApiException;
}
