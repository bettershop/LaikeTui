package com.laiketui.common.mapper;

import com.laiketui.domain.config.PluginsModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 插件表sql映射
 *
 * @author Trick
 * @date 2020/10/10 9:32
 */
public interface PluginsModelMapper extends BaseMapper<PluginsModel> {


    /**
     * 获取插件信息
     *
     * @param code - 接口code
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/10 9:58
     */
    @Select("select * from lkt_plugins " +
            " where flag=0  and status = 1 and plugin_code=#{code}" +
            " GROUP BY plugin_code")
    PluginsModel getPluginInfo(String code) throws LaiKeApiException;


    /**
     * 获取所有安装成功并且未卸载的所有插件
     *
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 9:10
     */
    @Select("select * from lkt_plugins where flag=0  and status = 1 GROUP BY plugin_code")
    List<PluginsModel> getPluginsAll() throws LaiKeApiException;

}