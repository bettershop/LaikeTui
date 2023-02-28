package com.laiketui.domain.vo.dic;

/**
 * 字典查询参数
 *
 * @author Trick
 * @date 2021/9/17 16:51
 */
public class DicVo {
    //字典名称
    private String name;
    //字典id
    private Integer id;
    //字典明细id
    private Integer sid;

    //是否显示被标记删除的字典
    private boolean isShowFather;
    //是否显示被标记删除的字典明细
    private boolean isShowChild;

    //值
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }

    public boolean isShowFather() {
        return isShowFather;
    }

    public void setShowFather(boolean showFather) {
        isShowFather = showFather;
    }

    public boolean isShowChild() {
        return isShowChild;
    }

    public void setShowChild(boolean showChild) {
        isShowChild = showChild;
    }
}
