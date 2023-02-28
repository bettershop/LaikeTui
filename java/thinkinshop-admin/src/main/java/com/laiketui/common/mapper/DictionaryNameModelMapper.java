package com.laiketui.common.mapper;

import com.laiketui.domain.dictionary.DictionaryNameModel;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


/**
 * 数据字典名称表
 *
 * @author Trick
 * @date 2020/9/24 13:21
 */
public interface DictionaryNameModelMapper extends BaseMapper<DictionaryNameModel> {

    /**
     * 获取短信模板信息
     *
     * @param dicmodel -
     * @return DictionaryNameModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/24 14:25
     */
    DictionaryNameModel getSmsTemplate(DictionaryNameModel dicmodel) throws LaiKeApiException;


    /**
     * 动态查询
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 14:14
     */
    List<Map<String, Object>> selectDynamicInfo(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 动态统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 14:14
     */
    int countDynamicInfo(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 删除下级
     *
     * @param superName -
     * @return int
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 17:40
     */
    @Update("update lkt_data_dictionary_list set recycle = 1 where s_name = #{superName}")
    int delLower(String superName) throws LaiKeApiException;

    /**
     * 获取字典明细
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 16:43
     */
    @Override
    List<Map<String, Object>> selectDynamic(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取字典明细-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 16:43
     */
    @Override
    int countDynamic(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 获取字典名称
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 16:43
     */
    List<Map<String, Object>> selectDynamic1(Map<String, Object> map) throws LaiKeApiException;

    /**
     * 获取字典名称-统计
     *
     * @param map -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 16:43
     */
    int countDynamic1(Map<String, Object> map) throws LaiKeApiException;


    /**
     * 根据字典目录获取明细
     * (无视被删除的目录)
     * @param name -
     * @return List
     * @author Trick
     * @date 2021/6/1 11:04
     */
    @Select("select b.id,b.sid,b.value,b.text from lkt_data_dictionary_name a,lkt_data_dictionary_list b where a.name =#{name} and b.status=1 and  a.id=b.sid and b.recycle=0 ")
    List<Map<String, Object>> getDicByName(String name);

}