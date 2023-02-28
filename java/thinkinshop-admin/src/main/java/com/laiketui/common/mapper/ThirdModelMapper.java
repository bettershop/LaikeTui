package com.laiketui.common.mapper;

import com.laiketui.domain.home.ThirdModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;


/**
 * 第三方授权表
 *
 * @author Trick
 * @date 2021/2/4 10:29
 */
public interface ThirdModelMapper extends BaseMapper<ThirdModel> {


    /**
     * 获取第三方授权信息
     *
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/4 10:30
     */
    @Select("select * from lkt_third order by id limit 1")
    Map<String, Object> selectThirdParmateOne() throws LaiKeApiException;

}