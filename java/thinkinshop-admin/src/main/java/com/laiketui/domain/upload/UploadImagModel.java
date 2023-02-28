package com.laiketui.domain.upload;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传图片
 *
 * @author Trick
 * @date 2020/9/28 16:21
 */
public class UploadImagModel {

    /**
     * 公钥 accessKeyID
     */
    private String accessKeyId;

    /**
     * 私钥 AccessKeySecret
     */
    private String accessKeySecret;

    /**
     * 仓库
     */
    private String bucket;

    /**
     * 自定义域名
     */
    private String endpoint;

    /**
     * 图片样式
     */
    private String imagestyle;

    /**
     * 是否开启自定义域名
     */
    private String isopenzdy;

    /**
     * 文件上传路径（服务器本地-自定义路径）
     */
    private String path;

    /**
     * 文件流集合
     */
    private List<MultipartFile> multipartFiles;

    /**
     * 文件配置
     */
    private List<UploadConfigModel> uploadConfigs;

    /**
     * 上传方式
     */
    private String uploadType;

    /**
     * 服务器url
     */
    private String uploadImgDomain;
    /**
     *  上传路径
     */
    private String uploadImg;

    /**
     *  上传路径
     */
    private String uploadPath;

    /**
     * 图片名称,默认随机生成名称
     */
    private String imageName;

    public String getUploadImg() {
        return uploadImg;
    }

    public void setUploadImg(String uploadImg) {
        this.uploadImg = uploadImg;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getImagestyle() {
        return imagestyle;
    }

    public void setImagestyle(String imagestyle) {
        this.imagestyle = imagestyle;
    }

    public String getIsopenzdy() {
        return isopenzdy;
    }

    public void setIsopenzdy(String isopenzdy) {
        this.isopenzdy = isopenzdy;
    }

    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }

    public void setMultipartFiles(List<MultipartFile> multipartFiles) {
        this.multipartFiles = multipartFiles;
    }

    public String getUploadImgDomain() {
        return uploadImgDomain;
    }

    public void setUploadImgDomain(String uploadImgDomain) {
        this.uploadImgDomain = uploadImgDomain;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public List<UploadConfigModel> getUploadConfigs() {
        return uploadConfigs;
    }

    public void setUploadConfigs(List<UploadConfigModel> uploadConfigs) {
        this.uploadConfigs = uploadConfigs;
    }
}
