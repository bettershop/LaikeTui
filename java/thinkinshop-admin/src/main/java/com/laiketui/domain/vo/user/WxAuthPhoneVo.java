package com.laiketui.domain.vo.user;

import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 小程序用户信息授权参数
 * @author sunH_
 */
@ApiModel(description="小程序用户信息授权参数")
public class WxAuthPhoneVo extends MainVo {

    @ApiModelProperty(value="加密密文", name="encryptedData")
    private String encryptedData;

    @ApiModelProperty(value="偏移量", name="iv")
    private String iv;

    @ApiModelProperty(value="密钥", name="sessionKey")
    private String sessionKey;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
