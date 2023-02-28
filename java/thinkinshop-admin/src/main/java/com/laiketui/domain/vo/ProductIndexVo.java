package com.laiketui.domain.vo;

import com.laiketui.domain.annotation.ParamsMapping;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * 商品详情首页参数
 *
 * @author Trick
 * @date 2020/10/29 9:29
 */
@Validated
public class ProductIndexVo extends MainVo {

    /**
     * 商品id
     */
    @NotNull
    private Integer pro_id;

    /**
     * 插件id
     */
    private Integer id;

    /**
     * 订单类型
     */
    private String type;

    @ParamsMapping("bargain_id")
    private int bargainId;


    public Integer getPro_id() {
        return pro_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBargainId() {
        return bargainId;
    }

    public void setBargainId(int bargainId) {
        this.bargainId = bargainId;
    }
}
