package com.laiketui.domain.vo.mch;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加我的门店
 *
 * @author Trick
 * @date 2020/11/30 15:15
 */
@ApiModel(description = "添加/修改我的店铺参数")
public class AddStoreVo extends MainVo {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "店铺id", name = "shop_id")
    @ParamsMapping("shop_id")
    private Integer shopId;
    @ApiModelProperty(value = "店铺名称", name = "name")
    private String name;
    @ApiModelProperty(value = "联系电话", name = "mobile")
    private String mobile;
    @ApiModelProperty(value = "营业时间", name = "business_hours")
    @ParamsMapping("business_hours")
    private String businessHours;
    @ApiModelProperty(value = "省市区", name = "city_all")
    @ParamsMapping("city_all")
    private String cityAll;
    @ApiModelProperty(value = "详细地址", name = "address")
    private String address;
    @ApiModelProperty(value = "是否默认", name = "is_default")
    @ParamsMapping("is_default")
    private Integer isDefault;

    public Integer getShopId() {
        return shopId;
    }

    public void setShopId(Integer shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getCityAll() {
        return cityAll;
    }

    public void setCityAll(String cityAll) {
        this.cityAll = cityAll;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
