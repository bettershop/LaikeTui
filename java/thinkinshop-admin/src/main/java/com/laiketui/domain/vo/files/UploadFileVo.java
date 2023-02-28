package com.laiketui.domain.vo.files;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传参数
 *
 * @author Trick
 * @date 2021/7/8 11:39
 */
@ApiModel(description = "文件上传参数")
public class UploadFileVo extends MainVo {

    @ApiModelProperty(value = "id", name = "id")
    private Integer id;
    @ApiModelProperty(value = "店铺id", name = "mchId")
    private Integer mchId;
    @ApiModelProperty(value = "分组id 默认-1(全部)", name = "groupId")
    private Integer groupId = -1;
    @ApiModelProperty(value = "上传类型 1=本地,2=阿里云oss", name = "uploadType")
    private String uploadType;
    @ApiModelProperty(value = "文件流集合", name = "image")
    private MultipartFile[] image;

    @ApiModelProperty(value = "图片标题", name = "title")
    private String title;
    @ApiModelProperty(value = "说明", name = "explain")
    private String explain;
    @ApiModelProperty(value = "代替文本", name = "alternativeText")
    private String alternativeText;
    @ApiModelProperty(value = "图像描述", name = "describe")
    private String describe;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMchId() {
        return mchId;
    }

    public void setMchId(Integer mchId) {
        this.mchId = mchId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public MultipartFile[] getImage() {
        return image;
    }

    public void setImage(MultipartFile[] image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getAlternativeText() {
        return alternativeText;
    }

    public void setAlternativeText(String alternativeText) {
        this.alternativeText = alternativeText;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
