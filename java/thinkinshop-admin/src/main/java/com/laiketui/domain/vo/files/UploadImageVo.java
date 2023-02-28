package com.laiketui.domain.vo.files;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 上传参数
 *
 * @author Trick
 * @date 2021/8/19 14:21
 */
public class UploadImageVo {
    /**
     * 文件集
     */
    private List<MultipartFile> multipartFiles;
    /**
     * 上传方式
     */
    private String uploadType;
    private int storeType;
    private int storeId;
    /**
     * 店铺id
     */
    private Integer mchId;

    /**
     * 图片名称
     */
    private String imageName;

    public List<MultipartFile> getMultipartFiles() {
        return multipartFiles;
    }

    public void setMultipartFiles(List<MultipartFile> multipartFiles) {
        this.multipartFiles = multipartFiles;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public Integer getMchId() {
        return mchId;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
