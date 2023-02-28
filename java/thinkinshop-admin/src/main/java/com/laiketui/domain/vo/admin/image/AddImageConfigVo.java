package com.laiketui.domain.vo.admin.image;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加/编辑图片上传配置
 *
 * @author Trick
 * @date 2021/1/29 18:06
 */
@ApiModel(description = "添加/编辑图片上传配置")
public class AddImageConfigVo {
    @ApiModelProperty(value = "上传方式 1=本地 2=oos", name = "type")
    private Integer type;

    @ApiModelProperty(value = "本地上传域名", name = "uploadImgDomain")
    private String uploadImgDomain;
    @ApiModelProperty(value = "本地上传路径", name = "uploadImg")
    private String uploadImg;
    @ApiModelProperty(value = "Bucket（存储空间名称）", name = "OSSBucket")
    private String ossbucket;
    @ApiModelProperty(value = "OSSEndpoint例子：oss-cn-hangzhou.aliyuncs.com，结尾不需要/", name = "OSSEndpoint")
    private String ossendpoint;
    @ApiModelProperty(value = "OSSAccessKey", name = "OSSAccessKey")
    private String ossaccesskey;
    @ApiModelProperty(value = "OSSAccessSecret", name = "OSSAccessSecret")
    private String ossaccesssecret;
    @ApiModelProperty(value = "自定义域名", name = "MyEndpoint")
    private String myEndpoint;
    @ApiModelProperty(value = "是否开启自定义域名", name = "isOpenDiyDomain")
    private Integer isOpenDiyDomain;
    @ApiModelProperty(value = "图片样式接口", name = "OSSimgstyleapi")
    private String ossimgstyleapi;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUploadImgDomain() {
        return uploadImgDomain;
    }

    public void setUploadImgDomain(String uploadImgDomain) {
        this.uploadImgDomain = uploadImgDomain;
    }

    public String getUploadImg() {
        return uploadImg;
    }

    public void setUploadImg(String uploadImg) {
        this.uploadImg = uploadImg;
    }

    public String getOssbucket() {
        return ossbucket;
    }

    public void setOssbucket(String ossbucket) {
        this.ossbucket = ossbucket;
    }

    public String getOssendpoint() {
        return ossendpoint;
    }

    public void setOssendpoint(String ossendpoint) {
        this.ossendpoint = ossendpoint;
    }

    public String getOssaccesskey() {
        return ossaccesskey;
    }

    public void setOssaccesskey(String ossaccesskey) {
        this.ossaccesskey = ossaccesskey;
    }

    public String getOssaccesssecret() {
        return ossaccesssecret;
    }

    public void setOssaccesssecret(String ossaccesssecret) {
        this.ossaccesssecret = ossaccesssecret;
    }

    public String getMyEndpoint() {
        return myEndpoint;
    }

    public void setMyEndpoint(String myEndpoint) {
        this.myEndpoint = myEndpoint;
    }

    public Integer getIsOpenDiyDomain() {
        return isOpenDiyDomain;
    }

    public void setIsOpenDiyDomain(Integer isOpenDiyDomain) {
        this.isOpenDiyDomain = isOpenDiyDomain;
    }

    public String getOssimgstyleapi() {
        return ossimgstyleapi;
    }

    public void setOssimgstyleapi(String ossimgstyleapi) {
        this.ossimgstyleapi = ossimgstyleapi;
    }
}
