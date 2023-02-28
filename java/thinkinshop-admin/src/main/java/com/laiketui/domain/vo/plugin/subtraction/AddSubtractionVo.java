package com.laiketui.domain.vo.plugin.subtraction;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑满减
 *
 * @author Trick
 * @date 2021/5/13 9:48
 */
@ApiModel(description = "添加/编辑满减")
public class AddSubtractionVo extends MainVo {
    @ApiModelProperty(name = "id", value = "满减id")
    private Integer id;
    @ApiModelProperty(name = "title", value = "活动标题")
    private String title;
    @ApiModelProperty(name = "name", value = "活动名称")
    private String name;
    @ApiModelProperty(name = "imgurls", value = "满减图片")
    private String imgurls;
    @ApiModelProperty(name = "subtractionType", value = "满减类型")
    @ParamsMapping("subtraction_type")
    private Integer subtractionType;
    @ApiModelProperty(name = "subtractionRange", value = "满减应用范围")
    @ParamsMapping("subtraction_range")
    private Integer subtractionRange;
    @ApiModelProperty(name = "menuList", value = "已选项")
    @ParamsMapping("menu_list")
    private String menuList;
    @ApiModelProperty(name = "startTime", value = "满减生效时间")
    @ParamsMapping("start_time")
    private String startTime;
    @ApiModelProperty(name = "endTime", value = "满减失效时间")
    @ParamsMapping("end_time")
    private String endTime;
    @ApiModelProperty(name = "reduce", value = "单笔订单满x,立减x {'10':'5','50':'30'}")
    @ParamsMapping("reduce")
    private String reduce;

    public String getReduce() {
        return reduce;
    }

    public void setReduce(String reduce) {
        this.reduce = reduce;
    }

    public Integer getSubtractionRange() {
        return subtractionRange;
    }

    public void setSubtractionRange(Integer subtractionRange) {
        this.subtractionRange = subtractionRange;
    }

    public String getMenuList() {
        return menuList;
    }

    public void setMenuList(String menuList) {
        this.menuList = menuList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgurls() {
        return imgurls;
    }

    public void setImgurls(String imgurls) {
        this.imgurls = imgurls;
    }

    public Integer getSubtractionType() {
        return subtractionType;
    }

    public void setSubtractionType(Integer subtractionType) {
        this.subtractionType = subtractionType;
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
}
