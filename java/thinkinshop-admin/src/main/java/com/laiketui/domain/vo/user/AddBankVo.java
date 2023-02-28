package com.laiketui.domain.vo.user;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 添加银行卡
 *
 * @author Trick
 * @date 2021/3/5 15:39
 */
@ApiModel(description = "添加银行卡参数")
public class AddBankVo extends MainVo {

    @ApiModelProperty(value = "主键id", name = "id")
    private Integer id;

    @ApiModelProperty(value = "持卡人", name = "Cardholder")
    @ParamsMapping("Cardholder")
    private String cardholder;
    @ApiModelProperty(value = "开户行名称", name = "Bank_name")
    @ParamsMapping("Bank_name")
    private String bankName;
    @ApiModelProperty(value = "开户支行名称", name = "branch")
    @ParamsMapping("branch")
    private String branchName;
    @ApiModelProperty(value = "银行卡号", name = "Bank_card_number")
    @ParamsMapping("Bank_card_number")
    private String bankCardNumber;
    @ApiModelProperty(value = "是否默认", name = "is_default")
    @ParamsMapping("is_default")
    private Integer isDefault = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardholder() {
        return cardholder;
    }

    public void setCardholder(String cardholder) {
        this.cardholder = cardholder;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBankCardNumber() {
        return bankCardNumber;
    }

    public void setBankCardNumber(String bankCardNumber) {
        this.bankCardNumber = bankCardNumber;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }
}
