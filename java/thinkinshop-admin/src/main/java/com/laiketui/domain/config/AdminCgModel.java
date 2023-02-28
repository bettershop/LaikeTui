package com.laiketui.domain.config;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "admin_cg_group")
public class AdminCgModel {
    @Id
    @Column(name = "GroupID")
    private Integer groupID;

    @Column(name = "G_CName")
    private String g_CName;

    @Column(name = "G_ParentID")
    private Integer g_ParentID;

    @Column(name = "G_ShowOrder")
    private Integer g_ShowOrder;

    @Column(name = "G_Level")
    private Integer g_Level;

    @Column(name = "G_ChildCount")
    private Integer g_ChildCount;

    @Column(name = "G_Delete")
    private Integer g_Delete;

    @Column(name = "G_Num")
    private Integer g_Num;

    /**
     * @return GroupID
     */
    public Integer getGroupID() {
        return groupID;
    }

    /**
     * @param groupID
     */
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    /**
     * @return G_CName
     */
    public String getG_CName() {
        return g_CName;
    }

    /**
     * @param g_CName
     */
    public void setG_CName(String g_CName) {
        this.g_CName = g_CName == null ? null : g_CName.trim();
    }

    /**
     * @return G_ParentID
     */
    public Integer getG_ParentID() {
        return g_ParentID;
    }

    /**
     * @param g_ParentID
     */
    public void setG_ParentID(Integer g_ParentID) {
        this.g_ParentID = g_ParentID;
    }

    /**
     * @return G_ShowOrder
     */
    public Integer getG_ShowOrder() {
        return g_ShowOrder;
    }

    /**
     * @param g_ShowOrder
     */
    public void setG_ShowOrder(Integer g_ShowOrder) {
        this.g_ShowOrder = g_ShowOrder;
    }

    /**
     * @return G_Level
     */
    public Integer getG_Level() {
        return g_Level;
    }

    /**
     * @param g_Level
     */
    public void setG_Level(Integer g_Level) {
        this.g_Level = g_Level;
    }

    /**
     * @return G_ChildCount
     */
    public Integer getG_ChildCount() {
        return g_ChildCount;
    }

    /**
     * @param g_ChildCount
     */
    public void setG_ChildCount(Integer g_ChildCount) {
        this.g_ChildCount = g_ChildCount;
    }

    /**
     * @return G_Delete
     */
    public Integer getG_Delete() {
        return g_Delete;
    }

    /**
     * @param g_Delete
     */
    public void setG_Delete(Integer g_Delete) {
        this.g_Delete = g_Delete;
    }

    /**
     * @return G_Num
     */
    public Integer getG_Num() {
        return g_Num;
    }

    /**
     * @param g_Num
     */
    public void setG_Num(Integer g_Num) {
        this.g_Num = g_Num;
    }
}