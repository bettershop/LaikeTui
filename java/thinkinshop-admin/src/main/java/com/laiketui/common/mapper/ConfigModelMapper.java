package com.laiketui.common.mapper;

import com.laiketui.domain.config.ConfigModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;


/**
 * 配置
 *
 * @author Trick
 * @date 2020/10/13 10:50
 */
public interface ConfigModelMapper extends BaseMapper<ConfigModel> {


    /**
     * 获取所有配置信息
     *
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/14 15:38
     */
    @Select("select * from lkt_config order by store_id")
    List<ConfigModel> getConfigAllInfo() throws LaiKeApiException;


    /**
     * 修改所有商城上传图片设置
     *
     * @param upserver -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/29 18:37
     */
    @Update("update lkt_config set upserver = #{upserver}")
    int updateConfigAll(int upserver) throws LaiKeApiException;


    @Update("update lkt_config set app_domain_name = #{domainUrl},modify_date = CURRENT_TIMESTAMP where store_id = #{storeId}")
    int updateConfigDomain(String domainUrl,int storeId) throws LaiKeApiException;
}