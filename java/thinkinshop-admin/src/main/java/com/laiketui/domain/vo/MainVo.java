package com.laiketui.domain.vo;

import com.laiketui.domain.annotation.ParamsMapping;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 主要参数
 *
 * @author Trick
 * @date 2020/10/28 15:29
 */
@ApiModel(description = "基类")
public class MainVo extends PageModel implements Serializable {

    @ApiModelProperty(value = "商城id", name = "storeId", example = "1")
    @ParamsMapping({"store_id"})
    private int storeId = 0;

    @ApiModelProperty(value = "语言", name = "language", example = "en")
    private String language = "cn";

    @ApiModelProperty(value = "token", name = "accessId", example = "1234")
    @ParamsMapping({"TOKEN", "access_id"})
    private String accessId;

    @ApiModelProperty(value = "商城类型", name = "storeType", example = "1")
    @ParamsMapping({"store_type", "mch_type"})
    private int storeType = 1;

    @ApiModelProperty(value = "导出 1=导出数据", name = "exportType")
    private Integer exportType = 0;

    public Integer getExportType() {
        return exportType;
    }

    public void setExportType(Integer exportType) {
        this.exportType = exportType;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getAccessId() {
        return accessId;
    }

    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    public int getStoreType() {
        return storeType;
    }

    public void setStoreType(int storeType) {
        this.storeType = storeType;
    }

}
