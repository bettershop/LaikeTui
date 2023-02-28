package com.laiketui.domain.vo.mch;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑添加店铺
 *
 * @author Trick
 * @date 2021/1/26 14:08
 */
@ApiModel(description = "编辑店铺信息")
public class AddMchVo extends MainVo {

    @ApiModelProperty(value = "店铺Id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "直播房间号", name = "roomid")
    private Integer roomid;
    @ApiModelProperty(value = "店铺信息", name = "mchInfo")
    private String mchInfo;
    @ApiModelProperty(value = "店铺经营范围", name = "confines")
    private String confines;
    @ApiModelProperty(value = "联系电话", name = "tel")
    private String tel;
    @ApiModelProperty(value = "省", name = "shen")
    private String shen;
    @ApiModelProperty(value = "市", name = "shi")
    private String shi;
    @ApiModelProperty(value = "县", name = "xian")
    private String xian;
    @ApiModelProperty(value = "详细地址", name = "address")
    private String address;
    @ApiModelProperty(value = "店铺性质：0.个人 1.企业", name = "nature")
    private String nature;
    @ApiModelProperty(value = "营业状态", name = "0=未营业 1=营业 2=打样")
    private Integer isOpen;
    @ApiModelProperty(value = "店铺头像url", name = "logo")
    private String logo;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomid() {
        return roomid;
    }

    public void setRoomid(Integer roomid) {
        this.roomid = roomid;
    }

    public String getMchInfo() {
        return mchInfo;
    }

    public void setMchInfo(String mchInfo) {
        this.mchInfo = mchInfo;
    }

    public String getConfines() {
        return confines;
    }

    public void setConfines(String confines) {
        this.confines = confines;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getShen() {
        return shen;
    }

    public void setShen(String shen) {
        this.shen = shen;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getXian() {
        return xian;
    }

    public void setXian(String xian) {
        this.xian = xian;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }
}
