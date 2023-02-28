package com.laiketui.domain.vo.order;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 * @author wangxian
 */
@ApiModel(description = "回寄参数")
public class ReturnGoodsVo extends MainVo {

    @ApiModelProperty(value = "快递单号", name = "kdcode")
    private String kdcode;
    @ApiModelProperty(value = "快递名称", name = "kdname")
    private String kdname;
    @ApiModelProperty(value = "寄件人电话", name = "lxdh")
    private String lxdh;
    @ApiModelProperty(value = "寄件人", name = "lxr")
    private String lxr;
    @ApiModelProperty(value = "售后订单id", name = "id")
    private Integer id;

    public String getKdcode() {
        return kdcode;
    }

    public void setKdcode(String kdcode) {
        this.kdcode = kdcode;
    }

    public String getKdname() {
        return kdname;
    }

    public void setKdname(String kdname) {
        this.kdname = kdname;
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh;
    }

    public String getLxr() {
        return lxr;
    }

    public void setLxr(String lxr) {
        this.lxr = lxr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
