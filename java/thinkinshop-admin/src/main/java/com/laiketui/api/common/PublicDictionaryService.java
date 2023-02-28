package com.laiketui.api.common;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.dic.DicVo;
import com.laiketui.domain.vo.systems.AddDictionaryDetailVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.List;
import java.util.Map;

/**
 * 字典操作
 *
 * @author Trick
 * @date 2020/12/28 14:56
 */
public interface PublicDictionaryService {


    /**
     * 根据名称获取字典明细
     *
     * @param name -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/28 15:00
     */
    Map<String, Object> getDictionaryByName(String name) throws LaiKeApiException;

    public static void main(String[] args) {
        int num = 0;
        do {
            num++;
            System.out.println(num);
        } while (num < 1);
    }

    /**
     * 根据名称获取字典明细
     *
     * @param dicVo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/9/17 16:27
     */
    Map<String, Object> getDictionaryByName(DicVo dicVo) throws LaiKeApiException;


    /**
     * 根据id获取字典信息
     *
     * @param name      - 目录名称
     * @param superName - 可选上级
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/15 16:34
     */
    List<Map<String, Object>> getDictionaryById(String name, String superName) throws LaiKeApiException;


    /**
     * 获取字典数据列表
     *
     * @param vo    -
     * @param id    -
     * @param dicNo -
     * @param key   -
     * @param value -
     * @return List
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 14:03
     */
    Map<String, Object> getDictionaryInfo(MainVo vo, Integer id, String dicNo, String key, String value) throws LaiKeApiException;


    /**
     * 获取数据名称列表
     *
     * @param vo   -
     * @param id   -
     * @param name -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 17:00
     */
    Map<String, Object> getDictionaryCatalogInfo(MainVo vo, Integer id, String name) throws LaiKeApiException;

    /**
     * 获取目录下拉
     *
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 15:59
     */
    Map<String, Object> getDictionaryCatalogList() throws LaiKeApiException;


    /**
     * 根据字典目录id获取字典代码
     *
     * @param id -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 16:14
     */
    Map<String,Object> getDictionaryCode(int id) throws LaiKeApiException;


    /**
     * 添加数据字典目录
     *
     * @param id     -
     * @param name   -
     * @param token  -
     * @param isOpen -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 15:07
     */
    boolean addDictionaryInfo(Integer id, String name, int isOpen, String token) throws LaiKeApiException;


    /**
     * 删除字典目录
     *
     * @param idList -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 17:24
     */
    boolean delDictionary(List<Integer> idList) throws LaiKeApiException;


    /**
     * 添加/修改字典表明细
     *
     * @param vo -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 14:41
     */
    void addDictionaryDetailInfo(AddDictionaryDetailVo vo) throws LaiKeApiException;


    /**
     * 字典明细开关
     *
     * @param vo -
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 18:36
     */
    boolean switchDictionaryDetail(MainVo vo, int id) throws LaiKeApiException;


    /**
     * 字典开关
     *
     * @param vo -
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/28 18:36
     */
    boolean switchDictionary(MainVo vo, int id) throws LaiKeApiException;

    /**
     * 删除字典明细
     *
     * @param idList -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/1 17:24
     */
    boolean delDictionaryDetailInfo(List<Integer> idList) throws LaiKeApiException;
}
