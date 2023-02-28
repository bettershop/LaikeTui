package com.laiketui.modules.backend.services.order;

import com.laiketui.api.modules.backend.order.CommentsService;
import com.laiketui.common.mapper.CommentsImgModelMapper;
import com.laiketui.common.mapper.CommentsModelMapper;
import com.laiketui.common.mapper.ReplyCommentsModelMapper;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.product.CommentsImgModel;
import com.laiketui.domain.product.CommentsModel;
import com.laiketui.domain.product.ReplyCommentsModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.order.CommentsInfoVo;
import com.laiketui.domain.vo.order.UpdateCommentsInfoVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 评论管理
 *
 * @author Trick
 * @date 2021/1/6 15:56
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    private final Logger logger = LoggerFactory.getLogger( CommentsServiceImpl.class);

    @Override
    public Map<String, Object> getCommentsInfo(CommentsInfoVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("status", 1);
            parmaMap.put("type", vo.getType());
            if (!StringUtils.isEmpty(vo.getStartDate())) {
                parmaMap.put("startDate", vo.getStartDate());
                if (!StringUtils.isEmpty(vo.getEndDate())) {
                    parmaMap.put("endDate", vo.getEndDate());
                }
            }
            //订单类型
            if (StringUtils.isNotEmpty(vo.getOrderType())) {
                parmaMap.put("orderType", vo.getEndDate());
            }
            parmaMap.put("orderno", vo.getOrderno());
            int count = commentsModelMapper.countCommentsOrderDynamic(parmaMap);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> commentsList = commentsModelMapper.getCommentsOrderDynamic(parmaMap);
            for (Map<String, Object> map : commentsList) {
                int commentId = MapUtils.getIntValue(map, "id");
                List<String> imgUrls = commentsImgModelMapper.getCommentsImages(commentId);
                List<String> resultImgUrl = new ArrayList<>();
                for (String img : imgUrls) {
                    resultImgUrl.add(publiceService.getImgPath(img, vo.getStoreId()));
                }
                map.put("commentImgList", resultImgUrl);
            }

            resultMap.put("total", count);
            resultMap.put("list", commentsList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取评论列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getCommentsDetailInfoById(MainVo vo, int cid) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("commentId", cid);
            //类型(0=全部,1=好评,2=中评,3=差评,4=有图)
            parmaMap.put("type", 0);
            List<Map<String, Object>> commentsMap = publiceService.getGoodsCommentList(parmaMap);

            resultMap.put("list", commentsMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCommentsDetailInfoById(UpdateCommentsInfoVo vo) throws LaiKeApiException {
        try {
            //获取评论信息
            CommentsModel commentsModel = new CommentsModel();
            commentsModel.setStore_id(vo.getStoreId());
            commentsModel.setId(vo.getCid());
            commentsModel = commentsModelMapper.selectOne(commentsModel);
            if (commentsModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "评论不存在");
            }
            //删除之前评论图片
            CommentsImgModel commentsImgModel = new CommentsImgModel();
            commentsImgModel.setComments_id(commentsModel.getId());
            commentsImgModelMapper.delete(commentsImgModel);
            //保存修改后的图片
            int count;
            if (StringUtils.isNotEmpty(vo.getCommentImgUrls())) {
                for (String img : vo.getCommentImgUrls()) {
                    img = ImgUploadUtils.getUrlImgByName(img, true);
                    CommentsImgModel saveCommentsImgModel = new CommentsImgModel();
                    saveCommentsImgModel.setComments_id(commentsModel.getId());
                    saveCommentsImgModel.setType(0);
                    saveCommentsImgModel.setComments_url(img);
                    saveCommentsImgModel.setAdd_time(new Date());
                    count = commentsImgModelMapper.insertSelective(saveCommentsImgModel);
                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败");
                    }
                }
            }
            //追评图
            if (StringUtils.isNotEmpty(vo.getReviewImgList())) {
                for (String img : vo.getReviewImgList()) {
                    img = ImgUploadUtils.getUrlImgByName(img, true);
                    CommentsImgModel saveCommentsImgModel = new CommentsImgModel();
                    saveCommentsImgModel.setComments_id(commentsModel.getId());
                    saveCommentsImgModel.setType(1);
                    saveCommentsImgModel.setComments_url(img);
                    saveCommentsImgModel.setAdd_time(new Date());
                    count = commentsImgModelMapper.insertSelective(saveCommentsImgModel);
                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败");
                    }
                }
            }

            //修改评论
            CommentsModel updateComment = new CommentsModel();
            updateComment.setId(commentsModel.getId());
            if (StringUtils.isEmpty(vo.getCommentText())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "评论不能为空");
            }
            updateComment.setContent(vo.getCommentText());
            if (!StringUtils.isEmpty(vo.getReview())) {
                updateComment.setReview_time(new Date());
                updateComment.setReview(vo.getReview());
            }
            updateComment.setCommentType(String.valueOf(vo.getCommentType()));

            count = commentsModelMapper.updateByPrimaryKeySelective(updateComment);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("修改评论 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean replyComments(MainVo vo, int commentId, String commentText) throws LaiKeApiException {
        try {
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);

            ReplyCommentsModel replyCommentsModel = new ReplyCommentsModel();
            replyCommentsModel.setStore_id(vo.getStoreId());
            replyCommentsModel.setCid(String.valueOf(commentId));
            int count = replyCommentsModelMapper.selectCount(replyCommentsModel);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.ILLEGAL_INVASION, "已经回复过了");
            }

            replyCommentsModel.setUid(adminModel.getName());
            replyCommentsModel.setContent(commentText);
            replyCommentsModel.setAdd_time(new Date());
            count = replyCommentsModelMapper.insertSelective(replyCommentsModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "回复失败");
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delComments(MainVo vo, int commentId) throws LaiKeApiException {
        try {
            CommentsModel commentsModel = commentsModelMapper.selectByPrimaryKey(commentId);
            if (commentsModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "评论不存在");
            }
            //删除评论
            int count = commentsModelMapper.deleteByPrimaryKey(commentId);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败");
            }
            //删除评论图片
            CommentsImgModel commentsImgModel = new CommentsImgModel();
            commentsImgModel.setComments_id(commentId);
            commentsImgModelMapper.delete(commentsImgModel);
            //删除回复
            ReplyCommentsModel replyCommentsModel = new ReplyCommentsModel();
            replyCommentsModel.setCid(String.valueOf(commentId));
            replyCommentsModelMapper.delete(replyCommentsModel);

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常");
        }
    }

    @Autowired
    private CommentsModelMapper commentsModelMapper;

    @Autowired
    private CommentsImgModelMapper commentsImgModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private ReplyCommentsModelMapper replyCommentsModelMapper;

    @Autowired
    private RedisUtil redisUtil;
}
