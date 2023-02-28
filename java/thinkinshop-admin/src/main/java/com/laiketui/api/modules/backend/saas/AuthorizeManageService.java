package com.laiketui.api.modules.backend.saas;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.weixin.AddThridVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 授权管理
 *
 * @author Trick
 * @date 2021/2/3 16:23
 */
public interface AuthorizeManageService {
    /**
     * 获取小程序发布列表
     *
     * @param examineStatus -
     * @param releaseStatus -
     * @param appName       -
     * @param pageModel     -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 17:21
     */
    Map<String, Object> getThirdInfo(Integer examineStatus, Integer releaseStatus, String appName, PageModel pageModel) throws LaiKeApiException;


    /**
     * 发布小程序代码
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 17:22
     */
    boolean release(Integer id) throws LaiKeApiException;


    /**
     * 获取小程序配置参数信息
     *
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/4 10:26
     */
    Map<String, Object> getThridParmate(MainVo vo) throws LaiKeApiException;

    /**
     * 添加/编辑小程序配置参数
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/4 9:21
     */
    boolean addThridParmate(AddThridVo vo) throws LaiKeApiException;
}
