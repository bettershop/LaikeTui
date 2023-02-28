package com.laiketui.common.mapper;

import com.laiketui.domain.dictionary.DictionaryListModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


/**
 * 数据字典表
 *
 * @author Trick
 * @date 2020/9/24 13:25
 */
public interface DictionaryListModelMapper extends BaseMapper<DictionaryListModel> {


    /**
     * 获取字典值
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 10:50
     */
    List<Map<String, Object>> getDictionaryDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * value不能相同
     */
    @Select("select count(1) from lkt_data_dictionary_list a,lkt_data_dictionary_name b where a.sid=#{sid} " +
            " and a.sid=b.id and a.code like concat(b.dic_code,'%') and a.value=#{value}")
    int countDicListCode(int sid, String value);

    /**
     * text 不能相同
     */
    @Select("select count(1) from lkt_data_dictionary_list a,lkt_data_dictionary_name b where a.sid=#{sid} " +
            " and a.sid=b.id and a.code like concat(b.dic_code,'%') and a.text=#{name}")
    int countDicListName(int sid, String name);

}