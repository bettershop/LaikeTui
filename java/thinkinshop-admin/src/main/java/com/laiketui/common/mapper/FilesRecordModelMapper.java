package com.laiketui.common.mapper;

import com.laiketui.domain.log.FilesRecordModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 图片上传记录
 *
 * @author Trick
 * @date 2021/7/8 10:19
 */
public interface FilesRecordModelMapper extends BaseMapper<FilesRecordModel> {

    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取图片上传配置 获取最新一条
     *
     * @param filesRecordModel -
     * @return FilesRecordModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/13 18:09
     */
    @Select("SELECT id,store_id,store_type,`group`,upload_mode,image_name,add_time " +
            " FROM lkt_files_record WHERE store_id = #{store_id} AND image_name = #{image_name} " +
            "order by add_time desc limit 1")
    FilesRecordModel getImageUrlOne(FilesRecordModel filesRecordModel) throws LaiKeApiException;

    @Select("<script> " +
            " SELECT image_name,store_id,store_type from lkt_files_record where " +
            " <if test='ids != null '> " +
            " <foreach collection=\"ids\" item=\"id\" separator=\",\" open=\" id in(\" close=\")\"> " +
            "   #{id,jdbcType=INTEGER} " +
            " </foreach> " +
            " </if>" +
            " </script> ")
    List<Map<String,Object>> getImgNameByIds(@Param("ids") List<String> ids) throws LaiKeApiException;

}