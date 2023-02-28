package com.laiketui.domain.vo.goods;

import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * 添加淘宝任务参数
 *
 * @author Trick
 * @date 2021/1/5 14:08
 */
@ApiModel(description = "添加淘宝任务参数")
public class AddTaoBaoVo extends MainVo {

    @ApiModelProperty(value = "任务id", name = "taskWorkId")
    private Integer taskWorkId;
    @ApiModelProperty(value = "任务名称", name = "taskName")
    private String taskName;
    @ApiModelProperty(value = "商品类别", name = "classId")
    private String classId;
    @ApiModelProperty(value = "商品品牌", name = "brandId")
    private Integer brandId;
    @ApiModelProperty(value = "淘宝链接id", name = "itemId")
    private List<String> itemId;

    public Integer getTaskWorkId() {
        return taskWorkId;
    }

    public void setTaskWorkId(Integer taskWorkId) {
        this.taskWorkId = taskWorkId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public List<String> getItemId() {
        return itemId;
    }

    public void setItemId(List<String> itemId) {
        this.itemId = itemId;
    }
}
