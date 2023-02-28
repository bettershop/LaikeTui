package com.laiketui.domain.vo.plugin.sign;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑签到配置
 *
 * @author Trick
 * @date 2021/5/11 16:52
 */
@ApiModel(description = "添加/编辑签到配置")
public class AddSignConfigVo extends MainVo {

    @ApiModelProperty(name = "isStatus", value = "签到插件是否启用")
    @ParamsMapping("is_status")
    private Integer isStatus;
    @ApiModelProperty(name = "scoreNum", value = "签到次数")
    @ParamsMapping("score_num")
    private Integer scoreNum;
    @ApiModelProperty(name = "starttime", value = "签到有效开始时间")
    private String starttime;
    @ApiModelProperty(name = "endtime", value = "签到有效结束时间")
    private String endtime;
    @ApiModelProperty(name = "isRemind", value = "是否提醒")
    @ParamsMapping("is_remind")
    private Integer isRemind;
    @ApiModelProperty(name = "isManyTime", value = "是否允许多次 0:不允许  1:允许")
    @ParamsMapping("is_many_time")
    private Integer isManyTime;
    @ApiModelProperty(name = "resetH", value = "签到重置时间 小时")
    @ParamsMapping("reset_h")
    private Integer resetH;
    @ApiModelProperty(name = "resetI", value = "签到重置时间 分钟")
    @ParamsMapping("reset_i")
    private Integer resetI;

    @ApiModelProperty(name = "score", value = "单次签到领取积分数量")
    private Integer score;
    @ApiModelProperty(name = "scoreJson", value = "连续签到json {1:10},{2:20}")
    private String scoreJson;
    @ApiModelProperty(name = "detail", value = "规则")
    private String detail;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getIsStatus() {
        return isStatus;
    }

    public void setIsStatus(Integer isStatus) {
        this.isStatus = isStatus;
    }

    public Integer getScoreNum() {
        return scoreNum;
    }

    public void setScoreNum(Integer scoreNum) {
        this.scoreNum = scoreNum;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public Integer getIsRemind() {
        return isRemind;
    }

    public void setIsRemind(Integer isRemind) {
        this.isRemind = isRemind;
    }

    public Integer getIsManyTime() {
        return isManyTime;
    }

    public void setIsManyTime(Integer isManyTime) {
        this.isManyTime = isManyTime;
    }

    public Integer getResetH() {
        return resetH;
    }

    public void setResetH(Integer resetH) {
        this.resetH = resetH;
    }

    public Integer getResetI() {
        return resetI;
    }

    public void setResetI(Integer resetI) {
        this.resetI = resetI;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScoreJson() {
        return scoreJson;
    }

    public void setScoreJson(String scoreJson) {
        this.scoreJson = scoreJson;
    }
}
