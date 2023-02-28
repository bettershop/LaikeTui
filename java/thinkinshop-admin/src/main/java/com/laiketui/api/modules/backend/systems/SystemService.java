package com.laiketui.api.modules.backend.systems;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddSystemVo;
import com.laiketui.domain.vo.systems.SetSystemVo;
import com.laiketui.root.exception.LaiKeApiException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 系统设置
 *
 * @author Trick
 * @date 2021/1/19 9:06
 */
public interface SystemService {


    /**
     * 获取系统基本配置
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 9:07
     */
    Map<String, Object> getSystemIndex(MainVo vo) throws LaiKeApiException;


    /**
     * 添加/修改系统配置信息
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 9:15
     */
    boolean addSystemConfig(AddSystemVo vo) throws LaiKeApiException;

    /**
     * 获取系统配置
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/4 14:49
     */
    Map<String, Object> getSetSystem(MainVo vo) throws LaiKeApiException;

    /**
     * 系统配置
     *
     * @param vo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/4 14:48
     */
    void setSystem(SetSystemVo vo) throws LaiKeApiException;


    /**
     * 获取协议列表
     *
     * @param vo -
     * @param id -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 11:12
     */
    Map<String, Object> getAgreementIndex(MainVo vo, Integer id) throws LaiKeApiException;


    /**
     * 添加/编辑协议
     *
     * @param vo      -
     * @param title   -
     * @param type    -
     * @param content -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 11:17
     */
    void addAgreement(MainVo vo, Integer id, String title, int type, String content) throws LaiKeApiException;

    /**
     * 删除协议
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/19 10:33
     */
    void delAgreement(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 修改常简问题
     *
     * @param vo            -
     * @param returnProblem -
     * @param payProblem    -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 14:15
     */
    boolean updateCommonProblem(MainVo vo, String returnProblem, String payProblem) throws LaiKeApiException;


    /**
     * 售后服务
     *
     * @param vo            -
     * @param refundPolicy  -
     * @param cancelOrderno -
     * @param refundMoney   -
     * @param refundExplain -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 14:29
     */
    boolean updateRefundService(MainVo vo, String refundPolicy, String cancelOrderno, String refundMoney, String refundExplain) throws LaiKeApiException;


    /**
     * 新手指南
     *
     * @param vo              -
     * @param shoppingProcess -
     * @param payType         -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 14:39
     */
    boolean updateBeginnerGuide(MainVo vo, String shoppingProcess, String payType) throws LaiKeApiException;


    /**
     * 关于我
     *
     * @param vo       -
     * @param auboutMe -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 14:31
     */
    boolean updateAboutMe(MainVo vo, String auboutMe) throws LaiKeApiException;


    /**
     * 上传图片
     *
     * @param vo    -
     * @param files -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 14:54
     */
    boolean uploadImages(MainVo vo, List<MultipartFile> files) throws LaiKeApiException;


    /**
     * 微信接口配置
     *
     * @param vo        -
     * @param appid     -
     * @param appsecret -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 16:22
     */
    boolean updateWeiXinApi(MainVo vo, String appid, String appsecret) throws LaiKeApiException;
}
