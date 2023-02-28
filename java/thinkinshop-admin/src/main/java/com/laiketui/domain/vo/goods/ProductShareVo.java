package com.laiketui.domain.vo.goods;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 制作商品分享带参数二维码
 * @author Trick
 * @date 2020/12/25 9:20
 */
@ApiModel(description = "商品分享参数")
public class ProductShareVo extends MainVo {

    @ApiModelProperty(value = "商品id", name = "pid")
    private Integer pid;
    @ApiModelProperty(value = "商品类型 JP-竞拍商品", name = "product_type")
    @ParamsMapping("product_type")
    private String productType;
    @ApiModelProperty(value = "竞拍商品id", name = "auction_id")
    @ParamsMapping("auction_id")
    private Integer auctionId;
    @ApiModelProperty(value = "商品类型 JP-竞拍商品", name = "type")
    private Integer type;
    @ApiModelProperty(value = "前端路径", name = "path")
    private String path;
    @ApiModelProperty(value = "商品ID", name = "scene")
    private String scene;
    @ApiModelProperty(value = "参数", name = "proType")
    private String proType;
    @ApiModelProperty(value = "参数", name = "reUser")
    private String reUser;


    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public void setAuctionId(Integer auctionId) {
        this.auctionId = auctionId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }

    public String getReUser() {
        return reUser;
    }

    public void setReUser(String reUser) {
        this.reUser = reUser;
    }
}
