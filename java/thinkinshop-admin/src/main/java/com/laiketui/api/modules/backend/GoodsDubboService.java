package com.laiketui.api.modules.backend;

import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.GoodsClassVo;
import com.laiketui.domain.vo.goods.GoodsConfigureVo;
import com.laiketui.domain.vo.mch.ApplyShopVo;
import com.laiketui.domain.vo.mch.UploadMerchandiseVo;
import com.laiketui.root.exception.LaiKeApiException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 后台添加
 *
 * @author Trick
 * @date 2020/12/28 17:27
 */
public interface GoodsDubboService {

    /**
     * 获取分类及品牌
     * 【php Ajax->getDefaultView 】
     *
     * @param vo      -
     * @param classId -
     * @param brandId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 10:28
     */
    Map<String, Object> getClassifiedBrands(MainVo vo, Integer classId, Integer brandId) throws LaiKeApiException;


    /**
     * 获取商品类别
     *
     * @param vo      -
     * @param classId -
     * @param brandId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 15:11
     */
    Map<String, Object> getClass(MainVo vo, Integer classId, Integer brandId) throws LaiKeApiException;

    /**
     * 选择商品类别
     *
     * @param vo      -
     * @param classId -
     * @param brandId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 15:11
     */
    Map<String, Object> choiceClass(MainVo vo, Integer classId, Integer brandId) throws LaiKeApiException;


    /**
     * 获取属性名称
     *
     * @param vo         -
     * @param attributes -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 16:24
     */
    Map<String, Object> getAttributeName(MainVo vo, String attributes) throws LaiKeApiException;

    /**
     * 获取商品列表-规格
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/8/3 16:59
     */
    Map<String, Object> getGoodsConfigureList(GoodsConfigureVo vo) throws LaiKeApiException;


    /**
     * 获取属性值
     *
     * @param vo         -
     * @param attributes -
     * @param attrId     -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/12 17:29
     */
    Map<String, Object> getAttributeValue(MainVo vo, String attributes, Integer attrId) throws LaiKeApiException;

    /**
     * 添加自营店铺
     * 【php AddAction.mch】
     *
     * @param vo   -
     * @param logo -
     * @return Map -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/28 17:32
     */
    Map<String,Object> addMch(ApplyShopVo vo, String logo) throws LaiKeApiException;


    /**
     * 加载添加商品页面数据
     * 【php AddAction.getDefaultView】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 10:02
     */
    Map<String, Object> getAddPage(MainVo vo) throws LaiKeApiException;


    /**
     * 添加商品
     * 【php AddAction.execute】
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 10:12
     */
    Map<String, Object> addGoods(UploadMerchandiseVo vo) throws LaiKeApiException;

    /**
     * 编辑商品序号
     *
     * @param vo   -
     * @param id   -
     * @param sort -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/10/14 17:34
     */
    void editSort(MainVo vo, Integer id, Integer sort) throws LaiKeApiException;

    /**
     * 根据id获取商品信息
     *
     * @param vo      -
     * @param goodsId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 14:12
     */
    Map<String, Object> getGoodsInfoById(MainVo vo, int goodsId) throws LaiKeApiException;


    /**
     * 后台删除商品
     *
     * @param vo  -
     * @param pId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 14:35
     */
    boolean delGoodsById(MainVo vo, String pId) throws LaiKeApiException;


    /**
     * 上下架商品
     *
     * @param vo       -
     * @param goodsIds -
     * @param status   -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 17:15
     */
    void upperAndLowerShelves(MainVo vo, String goodsIds, Integer status) throws LaiKeApiException;


    /**
     * 商品置顶
     *
     * @param vo      -
     * @param goodsId -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 17:43
     */
    boolean goodsByTop(MainVo vo, int goodsId) throws LaiKeApiException;


    /**
     * 商品位置移动
     *
     * @param vo             -
     * @param currentGoodsId - 当前商品id
     * @param moveGoodsId    - 需要移动的商品id
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/29 17:53
     */
    boolean goodsMovePosition(MainVo vo, int currentGoodsId, int moveGoodsId) throws LaiKeApiException;


    /**
     * 查询类别
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 10:12
     */
    Map<String, Object> getClassInfo(GoodsClassVo vo, HttpServletResponse response) throws LaiKeApiException;


    /**
     * 获取当前类别所有上级
     *
     * @param vo      -
     * @param classId -
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 13:55
     */
    Map<String, Object> getClassLevelTopAllInfo(MainVo vo, int classId) throws LaiKeApiException;

    /**
     * 删除当前类别
     *
     * @param vo      -
     * @param classId -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 14:34
     */
    void delClass(MainVo vo, int classId) throws LaiKeApiException;


    /**
     * 添加商品类别
     *
     * @param vo        -
     * @param classId   -
     * @param className -
     * @param ename     -
     * @param img       -
     * @param level     -
     * @param fatherId  -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 15:31
     */
    boolean addClass(MainVo vo, Integer classId, String className, String ename, String img, int level, int fatherId) throws LaiKeApiException;


    /**
     * 类别置顶
     *
     * @param vo      -
     * @param classId -
     * @return boolean
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/30 16:42
     */
    boolean classSortTop(MainVo vo, Integer classId) throws LaiKeApiException;
}
