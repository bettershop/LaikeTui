package com.laiketui.domain.vo.admin.menu;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑菜单
 *
 * @author Trick
 * @date 2021/1/29 11:38
 */
@ApiModel(description = "添加/编辑菜单")
public class AddMenuVo extends MainVo {
    @ApiModelProperty(value = "菜单id", name = "mid")
    private Integer mid;

    @ApiModelProperty(value = "菜单名称", name = "menuName")
    private String menuName;
    @ApiModelProperty(value = "默认图标", name = "defaultLogo")
    private String defaultLogo;
    @ApiModelProperty(value = "选中图标", name = "chekedLogo")
    private String chekedLogo;
    @ApiModelProperty(value = "菜单等级", name = "level")
    private Integer level;
    @ApiModelProperty(value = "菜单分类 类型 0.控制台 1.商城", name = "menuClass")
    private Integer menuClass;
    @ApiModelProperty(value = "上级菜单", name = "fatherMenuId")
    private Integer fatherMenuId = 0;
    @ApiModelProperty(value = "文件夹path", name = "path")
    private String path;
    @ApiModelProperty(value = "菜单地址", name = "menuUrl")
    private String menuUrl;
    @ApiModelProperty(value = "是否是核心", name = "isCore")
    private Integer isCore = 0;

    @ApiModelProperty(value = "导览名称", name = "guideName")
    private String guideName;
    @ApiModelProperty(value = "导览简介", name = "briefintroduction")
    private String briefintroduction;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public String getBriefintroduction() {
        return briefintroduction;
    }

    public void setBriefintroduction(String briefintroduction) {
        this.briefintroduction = briefintroduction;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getDefaultLogo() {
        return defaultLogo;
    }

    public void setDefaultLogo(String defaultLogo) {
        this.defaultLogo = defaultLogo;
    }

    public String getChekedLogo() {
        return chekedLogo;
    }

    public void setChekedLogo(String chekedLogo) {
        this.chekedLogo = chekedLogo;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getMenuClass() {
        return menuClass;
    }

    public void setMenuClass(Integer menuClass) {
        this.menuClass = menuClass;
    }

    public Integer getFatherMenuId() {
        return fatherMenuId;
    }

    public void setFatherMenuId(Integer fatherMenuId) {
        this.fatherMenuId = fatherMenuId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getIsCore() {
        return isCore;
    }

    public void setIsCore(Integer isCore) {
        this.isCore = isCore;
    }
}
