package com.laiketui.domain.vo.mch;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 上传商品参数
 *
 * @author Trick
 * @date 2020/11/17 14:48
 */
@ApiModel(description = "商品上传参数")
public class UploadMerchandiseVo extends MainVo {

    @ApiModelProperty(value = "店铺id", name = "shopId")
    @ParamsMapping("shop_id")
    private int shopId;
    @ApiModelProperty(value = "商品id(修改时传)", name = "pId")
    @ParamsMapping("p_id")
    private Integer pId;
    @ApiModelProperty(value = "商品标题", name = "productTitle")
    @ParamsMapping("product_title")
    private String productTitle;
    @ApiModelProperty(value = "小标题", name = "subtitle")
    private String subtitle;
    @ApiModelProperty(value = "条形码", name = "scan")
    private String scan;
    @ApiModelProperty(value = "产品类别", name = "productClassId")
    @ParamsMapping("product_class_id")
    private String productClassId;
    @ApiModelProperty(value = "品牌", name = "brandId")
    @ParamsMapping("brand_id")
    private String brandId;
    @ApiModelProperty(value = "关键词", name = "keyword")
    private String keyword;
    @ApiModelProperty(value = "重量", name = "weight")
    private String weight;
    @ApiModelProperty(value = "产品图片 多个用,分割", name = "showImg")
    private String showImg;
    /**
     * cbj=1,yj=998,sj=188,kucun=9999,unit=%E7%AE%B1,stockWarn=99
     */
    @ApiModelProperty(value = "初始值", name = "initial")
    private String initial;
    /**
     * [{"attr_group_name":"颜色","attr_list":[{"attr_name":"黄色"},{"attr_name":"黑色"}]}]
     */
    @ApiModelProperty(value = "属性/规格", name = "attrGroup")
    @ParamsMapping("attr_group")
    private String attrGroup;
    /**
     * [{"cbj":"1","yj":"2","sj":"10","unit":"盒","kucun":"10","image":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/pc/1624343889908.png","bar_code":"","attr_list":[{"attr_id":"","attr_name":"蓝色","attr_group_name":"颜色"}],"cid":"","img":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/pc/1624343889908.png"},{"cbj":"1","yj":"2","sj":"10","unit":"盒","kucun":"10","image":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/pc/1624343889908.png","bar_code":"","attr_list":[{"attr_id":"","attr_name":"粉色","attr_group_name":"颜色"}],"cid":"","img":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/pc/1624343889908.png"},{"cbj":"1","yj":"2","sj":"10","unit":"盒","kucun":"10","image":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1624334123710.jpg","bar_code":"","attr_list":[{"attr_id":"","attr_name":"天蓝色","attr_group_name":"颜色"}],"cid":"","img":"https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1624334123710.jpg"}]
     */
    @ApiModelProperty(value = "属性详细信息", name = "attrArr")
    @ParamsMapping("attr_arr")
    private String attrArr;
    @ApiModelProperty(value = "运费", name = "freightId", required = true)
    @ParamsMapping("freight_id")
    private Integer freightId;
    @ApiModelProperty(value = "显示位置", name = "displayPosition")
    @ParamsMapping("display_position")
    private String displayPosition;
    @ApiModelProperty(value = "显示标签集", name = "sType")
    @ParamsMapping("s_type")
    private String sType;
    @ApiModelProperty(value = "支持活动", name = "active")
    private Integer active;
    @ApiModelProperty(value = "关联的分销层级id", name = "distributorId")
    @ParamsMapping("distributor_id")
    private Integer distributorId;
    @ApiModelProperty(value = "产品内容", name = "content")
    private String content;
    @ApiModelProperty(value = "虚拟销量", name = "volume")
    private Integer volume;
    /**
     * [{"tagType":"p","value":"详情","style":"padding:10px;font-size:14px;"}]
     */
    @ApiModelProperty(value = "产品数组内容 (设置前端店铺商品详情插件)", name = "richList")
    private String richList;
    /**
     * 商品审核状态
     */
    @ApiModelProperty(value = "商品审核状态 1.待审核，2.审核通过，3.审核不通过，4.暂不审核", name = "mchStatus")
    @ParamsMapping("mch_status")
    private Integer mchStatus;
    @ApiModelProperty(value = "单位", name = "unit")
    private String unit;
    @ApiModelProperty(value = "库存预警", name = "stockWarn")
    private int stockWarn;
    @ApiModelProperty(value = "产品封面图", name = "coverMap")
    @ParamsMapping("cover_map")
    private String coverMap;

    @ApiModelProperty(value = "序号", name = "sort")
    private Integer sort;

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public String getProductClassId() {
        return productClassId;
    }

    public void setProductClassId(String productClassId) {
        this.productClassId = productClassId;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public String getAttrGroup() {
        return attrGroup;
    }

    public void setAttrGroup(String attrGroup) {
        this.attrGroup = attrGroup;
    }

    public String getAttrArr() {
        return attrArr;
    }

    public void setAttrArr(String attrArr) {
        this.attrArr = attrArr;
    }

    public Integer getFreightId() {
        return freightId;
    }

    public void setFreightId(Integer freightId) {
        this.freightId = freightId;
    }

    public String getDisplayPosition() {
        return displayPosition;
    }

    public void setDisplayPosition(String displayPosition) {
        this.displayPosition = displayPosition;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Integer getDistributorId() {
        return distributorId;
    }

    public void setDistributorId(Integer distributorId) {
        this.distributorId = distributorId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRichList() {
        return richList;
    }

    public void setRichList(String richList) {
        this.richList = richList;
    }

    public Integer getMchStatus() {
        return mchStatus;
    }

    public void setMchStatus(Integer mchStatus) {
        this.mchStatus = mchStatus;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStockWarn() {
        return stockWarn;
    }

    public void setStockWarn(int stockWarn) {
        this.stockWarn = stockWarn;
    }

    public String getCoverMap() {
        return coverMap;
    }

    public void setCoverMap(String coverMap) {
        this.coverMap = coverMap;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
