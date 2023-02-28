package com.laiketui.common.mapper;

import com.laiketui.domain.config.ExtensionModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * 推广
 *
 * @author Trick
 * @date 2020/12/25 11:04
 */
public interface ExtensionModelMapper extends BaseMapper<ExtensionModel> {


    /**
     * 根据商城ID、海报类型、来源、默认，查询推广图片表
     *
     * @param storeId   -
     * @param type      -
     * @param storeType -
     * @return ExtensionModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/25 11:07
     */
    @Select("select * from lkt_extension where store_id = #{storeId} and type =#{type} and isdefault='1' and store_type = #{storeType}")
    ExtensionModel getExtensionModel(int storeId, int type, int storeType) throws LaiKeApiException;

}