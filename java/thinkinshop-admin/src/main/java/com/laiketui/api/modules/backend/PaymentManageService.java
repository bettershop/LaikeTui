package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 支付管理
 *
 * @author Trick
 * @date 2021/7/15 15:22
 */
public interface PaymentManageService {

    /**
     * 支付管理列表
     *
     * @param vo -
     * @param id -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/15 15:23
     */
    Map<String, Object> index(MainVo vo, Integer id) throws LaiKeApiException;

    /**
     * 获取支付参数
     *
     * @param vo    -
     * @param payId -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/15 15:42
     */
    Map<String, Object> paymentParmaInfo(MainVo vo, int payId) throws LaiKeApiException;

    /**
     * 支付参数修改
     *
     * @param vo     -
     * @param json   -
     * @param id     -
     * @param status -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/15 15:23
     */
    void setPaymentParma(MainVo vo, String json, Integer id, Integer status) throws LaiKeApiException;

    /**
     * 支付配置开关
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/15 17:19
     */
    void setPaymentSwitch(MainVo vo, int id) throws LaiKeApiException;

    /**
     * 文件上传
     * @param vo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/15 17:19
     */
    Map<String, Object> uploadCertP12(UploadFileVo vo) throws LaiKeApiException;
}
