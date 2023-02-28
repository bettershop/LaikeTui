package com.laiketui.domain.vo.plugin.group;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑拼团商品
 *
 * @author Trick
 * @date 2021/5/10 9:45
 */
@ApiModel(description = "添加/编辑拼团商品")
public class AddGroup extends MainVo {
    @ApiModelProperty(name = "id", value = "拼团活动id")
    @ParamsMapping("activity_no")
    private Integer id;

    /**
     * {"starttime":"2021-05-13 00:00:00","overtype":1,"endtime":"2021-05-29 00:00:00"}
     */
    @ApiModelProperty(name = "gdata", value = "开始starttime、结束时间endtime json")
    private String gdata;
    @ApiModelProperty(name = "id", value = "商品id")
    @ParamsMapping("goods_id")
    private Integer goodsId;
    /**
     * {"2":"70~60"}
     */
    @ApiModelProperty(name = "glevel", value = "拼团人数 json")
    private String glevel;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGdata() {
        return gdata;
    }

    public void setGdata(String gdata) {
        this.gdata = gdata;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGlevel() {
        return glevel;
    }

    public void setGlevel(String glevel) {
        this.glevel = glevel;
    }
}
