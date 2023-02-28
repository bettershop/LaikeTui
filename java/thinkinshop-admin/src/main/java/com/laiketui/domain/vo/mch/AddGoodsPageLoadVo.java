package com.laiketui.domain.vo.mch;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加商品页面-加载更多 参数
 *
 * @author Trick
 * @date 2020/11/11 11:46
 */
@ApiModel(description = "添加商品页面-加载更多参数")
public class AddGoodsPageLoadVo extends MainVo {
    @ApiModelProperty(value = "店铺id", name = "shop_id")
    @ParamsMapping("shop_id")
    private int shopId;
    @ApiModelProperty(value = "一级分类id", name = "product_class_id")
    @ParamsMapping("product_class_id")
    private Integer productClassId;
    @ApiModelProperty(value = "二级分类id", name = "product_class_id1")
    @ParamsMapping("product_class_id1")
    private Integer productClassId1;
    @ApiModelProperty(value = "产品品牌id", name = "brand_id")
    @ParamsMapping("brand_id")
    private Integer brandId;
    @ApiModelProperty(value = "产品名称", name = "proName")
    private String proName;
    @ApiModelProperty(value = "当前页", name = "page")
    private int page;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public Integer getProductClassId() {
        return productClassId;
    }

    public void setProductClassId(Integer productClassId) {
        this.productClassId = productClassId;
    }

    public Integer getProductClassId1() {
        return productClassId1;
    }

    public void setProductClassId1(Integer productClassId1) {
        this.productClassId1 = productClassId1;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
