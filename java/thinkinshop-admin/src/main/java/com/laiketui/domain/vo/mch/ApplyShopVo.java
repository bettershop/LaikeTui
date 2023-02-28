package com.laiketui.domain.vo.mch;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 申请开通店铺参数
 *
 * @author Trick
 * @date 2020/11/10 11:45
 */
@ApiModel(description = "申请开通店铺参数")
public class ApplyShopVo extends MainVo {

    @ApiModelProperty(value = "店铺名称", name = "name")
    private String name;
    @ApiModelProperty(value = "店铺信息", name = "shop_information")
    @ParamsMapping("shop_information")
    private String shop_information;
    @ApiModelProperty(value = "经营范围", name = "shop_range")
    @ParamsMapping("shop_range")
    private String shop_range;
    @ApiModelProperty(value = "真实姓名", name = "realname")
    private String realname;
    @ApiModelProperty(value = "身份证id", name = "ID_number")
    @ParamsMapping("ID_number")
    private String ID_number;
    @ApiModelProperty(value = "手机号", name = "tel")
    private String tel;
    @ParamsMapping("city_all")
    @ApiModelProperty(value = "联系地址,省市区以-分割", name = "city_all")
    private String city_all;
    @ApiModelProperty(value = "联系地址,详细地址", name = "address")
    private String address;
    @ApiModelProperty(value = "店铺性质：1.个人 2.企业", name = "shop_nature")
    @ParamsMapping("shop_nature")
    private int shop_nature;

    @ApiModelProperty(value = "营业执照", name = "business_license")
    @ParamsMapping("business_license")
    private String businessLicense;

    public String getBusinessLicense() {
        return businessLicense;
    }

    public void setBusinessLicense(String businessLicense) {
        this.businessLicense = businessLicense;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShop_information() {
        return shop_information;
    }

    public void setShop_information(String shop_information) {
        this.shop_information = shop_information;
    }

    public String getShop_range() {
        return shop_range;
    }

    public void setShop_range(String shop_range) {
        this.shop_range = shop_range;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getID_number() {
        return ID_number;
    }

    public void setID_number(String ID_number) {
        this.ID_number = ID_number;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCity_all() {
        return city_all;
    }

    public void setCity_all(String city_all) {
        this.city_all = city_all;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getShop_nature() {
        return shop_nature;
    }

    public void setShop_nature(int shop_nature) {
        this.shop_nature = shop_nature;
    }

}
