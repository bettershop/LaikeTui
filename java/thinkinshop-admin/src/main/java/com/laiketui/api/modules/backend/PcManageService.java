package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.pc.AddBannerInfoVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * pc管理接口
 *
 * @author Trick
 * @date 2021/1/22 10:39
 */
public interface PcManageService {


    /**
     * 获取轮播图信息
     *
     * @param vo -
     * @param id -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 10:43
     */
    Map<String, Object> getBannerInfo(MainVo vo, Integer id) throws LaiKeApiException;

    /**
     * 添加/编辑轮播图信息
     *
     * @param vo -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 10:47
     */
    boolean addBannerInfo(AddBannerInfoVo vo) throws LaiKeApiException;


    /**
     * 删除轮播图
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 10:50
     */
    boolean delBannerById(int id) throws LaiKeApiException;


    /**
     * 轮播图置顶
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/22 10:50
     */
    boolean topBannerById(int id) throws LaiKeApiException;
}
