package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 修改评论参数
 *
 * @author Trick
 * @date 2021/1/6 16:13
 */
@ApiModel(description = "修改评论信息")
public class UpdateCommentsInfoVo extends MainVo {

    @ApiModelProperty(value = "评论id", name = "cid")
    private Integer cid;
    @ApiModelProperty(value = "评论内容", name = "commentText")
    private String commentText;
    @ApiModelProperty(value = "评级", name = "commentType")
    private int commentType;
    @ApiModelProperty(value = "追评内容", name = "review")
    private String review;
    @ApiModelProperty(value = "评论图片", name = "commentImgUrls")
    private List<String> commentImgUrls;
    @ApiModelProperty(value = "追评图片", name = "reviewImgList")
    private List<String> reviewImgList;


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public int getCommentType() {
        return commentType;
    }

    public void setCommentType(int commentType) {
        this.commentType = commentType;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public List<String> getCommentImgUrls() {
        return commentImgUrls;
    }

    public void setCommentImgUrls(List<String> commentImgUrls) {
        this.commentImgUrls = commentImgUrls;
    }

    public List<String> getReviewImgList() {
        return reviewImgList;
    }

    public void setReviewImgList(List<String> reviewImgList) {
        this.reviewImgList = reviewImgList;
    }
}
