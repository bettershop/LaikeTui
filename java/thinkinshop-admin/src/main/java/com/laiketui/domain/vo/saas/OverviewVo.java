package com.laiketui.domain.vo.saas;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 查询功能导览列表参数
 *
 * @author Trick
 * @date 2021/6/16 10:05
 */
@ApiModel(description = "查询功能导览列表参数")
public class OverviewVo extends MainVo {

    @ApiModelProperty(value = "主键id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "上级Id", name = "sid")
    private Integer sid;
    @ApiModelProperty(value = "导览名、菜单id、菜单名", name = "name")
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
