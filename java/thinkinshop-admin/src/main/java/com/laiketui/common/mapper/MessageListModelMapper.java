package com.laiketui.common.mapper;

import com.laiketui.domain.config.MessageListModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 短信
 *
 * @author Trick
 * @date 2020/12/3 17:35
 */
public interface MessageListModelMapper extends BaseMapper<MessageListModel> {


    /**
     * 根据类型获取短信列表
     *
     * @param storeId -
     * @param type    -
     * @param type1   -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/3 17:37
     */
    @Select("select a.*,b.SignName,b.TemplateCode from lkt_message_list as a left join lkt_message as b on a.Template_id = b.id " +
            " where a.store_id = #{storeId} and a.type = #{type} and a.type1 = #{type1}")
    Map<String, Object> getMessageListInfoByType(int storeId, int type, int type1) throws LaiKeApiException;


    /**
     * 获取短信列表
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 15:25
     */
    @Select("<script>" +
            "select a.*, b. NAME,b.content AS content1 from lkt_message_list as a left join lkt_message as b on a.Template_id = b.id " +
            " where a.store_id = #{storeId}" +
            "<if test='id != null '>and a.id = #{id} </if>" +
            "<if test=\"pageStart != null and pageEnd != ''\">limit #{pageStart},#{pageEnd}</if>" +
            "</script>")
    List<Map<String, Object>> selectMessageListInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取短信列表-统计
     *
     * @param map -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 15:25
     */
    @Select("<script>" +
            "select count(1) from lkt_message_list as a left join lkt_message as b on a.Template_id = b.id " +
            " where a.store_id = #{storeId}" +
            "<if test='id != null '>and a.id = #{id} </if>" +
            "</script>")
    int countMessageListInfo(Map<String, Object> map) throws LaiKeApiException;
}