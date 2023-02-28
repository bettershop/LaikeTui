package com.laiketui.domain.vo.plugin.integral;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 增减积分
 *
 * @author Trick
 * @date 2021/11/9 17:50
 */
public class AddScoreVo extends MainVo {

    @ApiModelProperty(name = "userId", value = "用户userid")
    private String userId;
    @ApiModelProperty(name = "score", value = "获得积分")
    private Integer score;
    @ApiModelProperty(name = "scoreOld", value = "当前积分")
    private Integer scoreOld;
    @ApiModelProperty(name = "event", value = "积分说明")
    private String event;
    /**
     * type 类型: 0:签到 1:消费 2:首次关注得积分 3:转积分给好友 4:好友转积分 5:系统扣除 6:系统充值 7:抽奖 8:会员购物积分 9:分销升级奖励积分 10:积分过期
     */
    @ApiModelProperty(name = "type", value = "记录类型")
    private Integer type;

    @ApiModelProperty(name = "orderNo", value = "订单号")
    private String orderNo;

    @ApiModelProperty(name = "scoreInvalidDate", value = "积分过期时间")
    private Date scoreInvalidDate;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getScoreOld() {
        return scoreOld;
    }

    public void setScoreOld(Integer scoreOld) {
        this.scoreOld = scoreOld;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getScoreInvalidDate() {
        return scoreInvalidDate;
    }

    public void setScoreInvalidDate(Date scoreInvalidDate) {
        this.scoreInvalidDate = scoreInvalidDate;
    }
}
