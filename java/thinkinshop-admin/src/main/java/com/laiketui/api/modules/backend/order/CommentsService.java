package com.laiketui.api.modules.backend.order;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.CommentsInfoVo;
import com.laiketui.domain.vo.order.UpdateCommentsInfoVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 评论管理
 *
 * @author Trick
 * @date 2021/1/6 15:57
 */
public interface CommentsService {


    /**
     * 获取评论列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 16:07
     */
    Map<String, Object> getCommentsInfo(CommentsInfoVo vo) throws LaiKeApiException;


    /**
     * 获取评论详细信息
     *
     * @param vo  -
     * @param cid - 评论id
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 17:23
     */
    Map<String, Object> getCommentsDetailInfoById(MainVo vo, int cid) throws LaiKeApiException;


    /**
     * 修改评论信息
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/6 18:07
     */
    void updateCommentsDetailInfoById(UpdateCommentsInfoVo vo) throws LaiKeApiException;


    /**
     * 回复
     *
     * @param vo          -
     * @param commentId   -
     * @param commentText -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 9:30
     */
    boolean replyComments(MainVo vo, int commentId, String commentText) throws LaiKeApiException;


    /**
     * 删除评论
     *
     * @param vo        -
     * @param commentId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/7 9:52
     */
    boolean delComments(MainVo vo, int commentId) throws LaiKeApiException;
}
