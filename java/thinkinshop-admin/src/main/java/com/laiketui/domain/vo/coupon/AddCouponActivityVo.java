package com.laiketui.domain.vo.coupon;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * 添加/编辑优惠卷参数
 *
 * @author Trick
 * @date 2020/12/9 12:14
 */
@ApiModel(description = "添加/编辑优惠卷参数")
public class AddCouponActivityVo extends MainVo {

    @ApiModelProperty(value = "优惠卷id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "店铺id", name = "mch_id")
    @ParamsMapping("mch_id")
    private int mchId;

    @ApiModelProperty(value = "活动类型  1=免邮 2=满减 3=折扣 4=会员赠送", name = "activity_type")
    @ParamsMapping("activity_type")
    private int activityType;

    @ApiModelProperty(value = "活动名称", name = "name")
    private String name;
    @ApiModelProperty(value = "发行数量", name = "circulation")
    private int circulation;
    @ApiModelProperty(value = "优惠卷面值", name = "money")
    private BigDecimal money;
    @ApiModelProperty(value = "折扣值", name = "discount")
    private BigDecimal discount;
    @ApiModelProperty(value = "满减值", name = "z_money")
    @ParamsMapping("z_money")
    private BigDecimal zmoney;
    @ApiModelProperty(value = "优惠卷使用范围 1：全部商品 2:指定商品 3：指定分类", name = "type")
    private int type;
    @ApiModelProperty(value = "商品id集", name = "menu_list")
    @ParamsMapping("menu_list")
    private String menuList;
    @ApiModelProperty(value = "商品类别集", name = "class_list")
    @ParamsMapping("class_list")
    private String classList;
    @ApiModelProperty(value = "活动开始时间", name = "start_time")
    @ParamsMapping("start_time")
    private String startTime;
    @ApiModelProperty(value = "活动结束时间", name = "end_time")
    @ParamsMapping("end_time")
    private String endTime;
    @ApiModelProperty(value = "有效时间(会员卷)", name = "day")
    private Integer day;
    @ApiModelProperty(value = "会员等级id(会员卷)", name = "gradeId")
    private Integer gradeId;
    @ApiModelProperty(value = "使用说明", name = "Instructions")
    private String instructions;
    @ApiModelProperty(value = "领取张数限制", name = "limitCount")
    private Integer limitCount;

    public Integer getLimitCount() {
        return limitCount;
    }

    public void setLimitCount(Integer limitCount) {
        this.limitCount = limitCount;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getMchId() {
        return mchId;
    }

    public void setMchId(int mchId) {
        this.mchId = mchId;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCirculation() {
        return circulation;
    }

    public void setCirculation(int circulation) {
        this.circulation = circulation;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getZmoney() {
        return zmoney;
    }

    public void setZmoney(BigDecimal zmoney) {
        this.zmoney = zmoney;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMenuList() {
        return menuList;
    }

    public void setMenuList(String menuList) {
        this.menuList = menuList;
    }

    public String getClassList() {
        return classList;
    }

    public void setClassList(String classList) {
        this.classList = classList;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
