package com.laiketui.api.modules.backend.saas;

import com.laiketui.domain.vo.admin.image.AddImageConfigVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 图片管理
 *
 * @author Trick
 * @date 2021/1/29 17:54
 */
public interface ImageManageService {

    /**
     * 获取上传图片配置
     *
     * @param type -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 17:55
     */
    Map<String, Object> getImageConfigInfo(int type) throws LaiKeApiException;


    /**
     * 添加/编辑图片上传配置
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 18:06
     */
    void addImageConfigInfo(AddImageConfigVo vo) throws LaiKeApiException;
}
