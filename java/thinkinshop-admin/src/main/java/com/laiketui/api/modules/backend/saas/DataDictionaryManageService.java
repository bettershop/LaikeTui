package com.laiketui.api.modules.backend.saas;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 数据字典管理
 *
 * @author Trick
 * @date 2021/2/3 9:29
 */
public interface DataDictionaryManageService {


    /**
     * 获取商品属性信息
     *
     * @param vo       -
     * @param id       -
     * @param dataCode -
     * @param dataName -
     * @param sid      -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:31
     */
    Map<String, Object> getSkuInfo(MainVo vo, Integer id, Integer sid, String dataCode, String dataName, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 获取属性名称下拉
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:41
     */
    Map<String, Object> getSkuAttributeList(MainVo vo) throws LaiKeApiException;

    /**
     * 获取商品属性/属性值列表
     *
     * @param vo      -
     * @param keyword -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021-06-30 14:25:19
     */
    Map<String, Object> getSkuList(MainVo vo, String keyword) throws LaiKeApiException;

    /**
     * 商品属性生效开关
     *
     * @param id -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:33
     */
    boolean setSkuSwitch(Integer id) throws LaiKeApiException;

    /**
     * 添加/修改数据名称
     *
     * @param id      -
     * @param isOpen  -
     * @param skuName -
     * @param token   -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:37
     */
    boolean addSkuName(Integer id, String skuName, int isOpen, String token) throws LaiKeApiException;


    /**
     * 添加/修改商品属性
     *
     * @param sid           -
     * @param attributeList -
     * @param token         -
     * @param type          -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:42
     */
    void addSku(int sid, List<String> attributeList, String token, int type) throws LaiKeApiException;


    /**
     * 批量删除商品属性
     *
     * @param id -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/2/3 9:45
     */
    void delSku(List<Integer> id) throws LaiKeApiException;
}
