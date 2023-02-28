package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 添加评论参数
 *
 * @author Trick
 * @date 2021/6/23 14:53
 */
@ApiModel(description = "添加评论参数")
public class AddCommentVo extends MainVo {

    @ApiModelProperty(value = "属性id", name = "attrId")
    private Integer id;
    @ApiModelProperty(value = "是否匿名", name = "anonymous 0=匿名 1=否")
    private int anonymous;
    @ApiModelProperty(value = "评分", name = "start")
    private int start;
    @ApiModelProperty(value = "评论内容", name = "comment")
    private String comment;

    @ApiModelProperty(value = "订单详情id", required = true, name = "orderDetailsId")
    private Integer orderDetailsId;
    @ApiModelProperty(value = "图片url集", name = "imgUrls")
    private List<String> imgUrls;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(int anonymous) {
        this.anonymous = anonymous;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getOrderDetailsId() {
        return orderDetailsId;
    }

    public void setOrderDetailsId(Integer orderDetailsId) {
        this.orderDetailsId = orderDetailsId;
    }

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }
}
