package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModelProperty;

/**
 * 获取商品(规格)列表 参数
 *
 * @author Trick
 * @date 2021/8/3 16:58
 */
public class GoodsConfigureVo extends MainVo {
    @ApiModelProperty(value = "分类id", name = "cid")
    private Integer cid;
    @ApiModelProperty(value = "品牌id", name = "brandId")
    private Integer brandId;
    @ApiModelProperty(value = "商品标题", name = "productTitle")
    private String productTitle;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }
}
