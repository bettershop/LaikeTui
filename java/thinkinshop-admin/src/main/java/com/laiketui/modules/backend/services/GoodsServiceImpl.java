package com.laiketui.modules.backend.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.PublicGoodsService;
import com.laiketui.api.common.PublicUserService;
import com.laiketui.api.common.admin.PublicAdminService;
import com.laiketui.api.modules.backend.GoodsDubboService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.JumpPathModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.product.ProductClassModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.goods.GoodsClassVo;
import com.laiketui.domain.vo.goods.GoodsConfigureVo;
import com.laiketui.domain.vo.mch.ApplyShopVo;
import com.laiketui.domain.vo.mch.UploadMerchandiseVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 商品管理
 *
 * @author Trick
 * @date 2020/12/28 17:33
 */
@Service
public class GoodsServiceImpl implements GoodsDubboService {
    private final Logger logger = LoggerFactory.getLogger(IndexServiceImpl.class);


    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Override
    public Map<String, Object> getClassifiedBrands(MainVo vo, Integer classId, Integer brandId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> resultClassMap = publicGoodsService.getClassifiedBrands(vo.getStoreId(), classId, brandId);
            resultMap.put("list", resultClassMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商品类别以及品牌信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getClassifiedBrands");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getClass(MainVo vo, Integer classId, Integer brandId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> resultClassMap = publicGoodsService.getClassifiedBrands(vo.getStoreId(), classId, brandId);
            resultMap.put("list", resultClassMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商品类别 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getClass");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> choiceClass(MainVo vo, Integer classId, Integer brandId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> resultClassMap = publicGoodsService.getClassifiedBrands(vo.getStoreId(), classId, brandId);
            resultMap.put("list", resultClassMap);
        } catch (LaiKeApiException l) {
            logger.error("选择商品类别 失败", l);
            throw l;
        } catch (Exception e) {
            logger.error("选择商品类别 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "choiceClass");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getAttributeName(MainVo vo, String attributes) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            String[] attributeId = null;
            if (StringUtils.isNotEmpty(attributes)) {
                attributeId = attributes.split(",");
            }
            PageModel pageModel = new PageModel(vo.getPageNo(), vo.getPageSize());
            //获取所有属性
            List<Map<String, Object>> resultClassList = publicGoodsService.attribute1(vo.getStoreId(), pageModel, DataUtils.convertToList(attributeId));
            resultMap.put("attribute", resultClassList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取属性名称 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAttributeName");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getGoodsConfigureList(GoodsConfigureVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            //获取商品信息
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("mch_status", DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS);
            parmaMap.put("active", DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_PRICE);
            parmaMap.put("goodsRecycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("goodsStatus", DictionaryConst.GoodsStatus.NEW_GROUNDING);
            parmaMap.put("stockNum_gt", 0);

            if (vo.getCid() != null) {
                parmaMap.put("product_class", vo.getCid());
            }
            if (vo.getBrandId() != null) {
                parmaMap.put("brand_id", vo.getBrandId());
            }
            if (!StringUtils.isEmpty(vo.getProductTitle())) {
                parmaMap.put("product_title", vo.getProductTitle());
            }
            List<Map<String, Object>> goodsList = confiGureModelMapper.getProductListLeftJoinMchDynamic(parmaMap);
            int total = confiGureModelMapper.countProductListLeftJoinMchDynamic(parmaMap);
            for (Map<String, Object> goods : goodsList) {
                goods.put("imgurl", publiceService.getImgPath(MapUtils.getString(goods, "imgurl"), vo.getStoreId()));
                goods.put("attribute", GoodsDataUtils.getProductSkuValue(MapUtils.getString(goods, "attribute")));
            }

            resultMap.put("goodsList", goodsList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品列表-规格 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsConfigureList");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getAttributeValue(MainVo vo, String attributes, Integer attrId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> attributeNameMap = new HashMap<>(16);
            String[] attributeIds = attributes.split(",");
            if (attributeIds.length > 0) {
                for (String attributeId : attributeIds) {
                    attributeNameMap.putAll(publicGoodsService.attributeName1(vo.getStoreId(), attributeId, null));
                }
                resultMap.put("list", attributeNameMap);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取属性值 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAttributeName");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> addMch(ApplyShopVo vo, String logo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            if (StringUtils.isEmpty(logo)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "店铺logo不能为空", "mch");
            } else if (StringUtils.isEmpty(vo.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "店铺名称不能为空");
            } else if (StringUtils.isEmpty(vo.getShop_information())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "店铺信息不能为空");
            } else if (StringUtils.isEmpty(vo.getShop_range())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "经营范围不能为空");
            } else if (StringUtils.isEmpty(vo.getRealname())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "真实姓名不能为空");
            } else if (StringUtils.isEmpty(vo.getID_number())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "身份证信号不能为空");
            } else if (StringUtils.isEmpty(vo.getTel())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "手机号不能为空");
            } else if (StringUtils.isEmpty(vo.getCity_all())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "联系地址不能为空");
            } else if (StringUtils.isEmpty(vo.getAddress())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "详细地址不能为空");
            }
            List<String> citys = Arrays.asList(vo.getCity_all().split("-"));
            String headUrl = logo;
            logo = ImgUploadUtils.getUrlImgByName(logo, true);

            MchModel saveMchModel = new MchModel();
            saveMchModel.setStore_id(vo.getStoreId());
            saveMchModel.setName(vo.getName());
            saveMchModel.setShop_information(vo.getShop_information());
            saveMchModel.setLogo(logo);
            saveMchModel.setReview_status(DictionaryConst.MchExameStatus.EXAME_PASS_STATUS.toString());
            saveMchModel.setIs_open(MchModel.IS_OPEN_IN_BUSINESS.toString());
            saveMchModel.setRoomid(0);
            saveMchModel.setID_number(vo.getID_number());
            saveMchModel.setTel(vo.getTel());
            saveMchModel.setShop_range(vo.getShop_range());
            saveMchModel.setRealname(vo.getRealname());

            int dataMax = 3;
            if (citys.size() == dataMax) {
                saveMchModel.setSheng(citys.get(0));
                saveMchModel.setShi(citys.get(1));
                saveMchModel.setXian(citys.get(2));
                saveMchModel.setAddress(vo.getAddress());
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "地址参数错误");
            }
            //验证店铺名称 自营店不做校验
            /*if (shopUtils.checkSenstiveWord(vo.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "店铺名称非法");
            }*/

            AdminModel adminModel = new AdminModel();
            adminModel.setStore_id(vo.getStoreId());
            adminModel.setType(AdminModel.TYPE_CLIENT);
            adminModel = adminModelMapper.selectOne(adminModel);
            if (adminModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "商户账号不存在", "mch");
            }
            if (adminModel.getShop_id() != null && adminModel.getShop_id() > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "自营店已存在");
            }

            //注册一个用户
            User user = new User();
            user.setMobile(adminModel.getTel());
            user.setMima(BuilderIDTool.getNext(BuilderIDTool.Type.NUMBER, 10));
            user.setZhanghao(BuilderIDTool.getNext(BuilderIDTool.Type.NUMBER, 10));
            //校验数据格式
            user = DataCheckTool.checkUserDataFormate(user);
            //检查账户/手机号是否已存在
            User userTemp = new User();
            userTemp.setZhanghao(user.getZhanghao());
            if (userBaseMapper.validataUserPhoneOrNoIsRegister(vo.getStoreId(), user.getZhanghao()) > 0) {
                logger.debug("创建自营店的时候,手机/账号已被注册!");
                user.setZhanghao(BuilderIDTool.getNext(BuilderIDTool.Type.ALPHA, 10));
                user.setMobile(null);
            }
            user.setUser_name(vo.getName());
            user.setHeadimgurl(headUrl);
            publicUserService.register(vo, null, user);
            logger.debug("创建自营店-用户{}注册成功", user.getUser_id());
            //校验店铺数据
            saveMchModel = DataCheckTool.checkMchDataFormate(saveMchModel);
            if (mchModelMapper.verifyStoreName(vo.getStoreId(), vo.getName(), user.getUser_id()) > 0) {
                logger.debug("店铺名称已存在,重新生成一个");
                saveMchModel.setName(BuilderIDTool.getGuid());
            }

            saveMchModel.setBusiness_license(ImgUploadUtils.getUrlImgByName(vo.getBusinessLicense(), true));
            saveMchModel.setUser_id(user.getUser_id());
            saveMchModel.setAdd_time(new Date());
            int count = mchModelMapper.insertSelective(saveMchModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "创建自营店铺失败", "mch");
            }
            AdminModel updateAdmin = new AdminModel();
            updateAdmin.setId(adminModel.getId());
            updateAdmin.setShop_id(saveMchModel.getId());
            count = adminModelMapper.updateByPrimaryKeySelective(updateAdmin);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "设置自营店铺失败", "mch");
            }
            resultMap.put("mchId", saveMchModel.getId());
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加自营店铺 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mch");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> getAddPage(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (user != null) {
                resultMap = publicGoodsService.addPage(vo.getStoreId(), user.getName(), user.getShop_id(), GloabConst.LktConfig.LKT_CONFIG_TYPE_PT);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("加载添加商品页面 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAddPage");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> addGoods(UploadMerchandiseVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            publicGoodsService.addProduct(vo, user.getName(), user.getShop_id(), GloabConst.LktConfig.LKT_CONFIG_TYPE_PT);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addGoods");
        }
        return resultMap;
    }

    @Override
    public void editSort(MainVo vo, Integer id, Integer sort) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            ProductListModel productListOld = productListModelMapper.selectByPrimaryKey(id);
            if (productListOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品不存在");
            }
            if (sort == null) {
                sort = 0;
            }
            ProductListModel productListUpdate = new ProductListModel();
            productListUpdate.setId(id);
            productListUpdate.setSort(sort);
            int row = productListModelMapper.updateByPrimaryKeySelective(productListUpdate);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("编辑商品序号 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addGoods");
        }
    }

    @Override
    public Map<String, Object> getGoodsInfoById(MainVo vo, int goodsId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            resultMap = publicGoodsService.editPage(vo.getStoreId(), user.getName(), 0, goodsId, GloabConst.LktConfig.LKT_CONFIG_TYPE_PT);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsInfoById");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delGoodsById(MainVo vo, String pId) throws LaiKeApiException {
        try {
            if (StringUtils.isEmpty(pId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误");
            }
            AdminModel user = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (user != null) {
                String[] pidList = pId.split(",");
                for (String goodsId : pidList) {
                    if (!publicGoodsService.delGoodsById(vo.getStoreId(), Integer.parseInt(goodsId), null)) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品删除失败");
                    }
                }
                return true;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除商品 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delGoodsById");
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void upperAndLowerShelves(MainVo vo, String goodsIds, Integer status) throws LaiKeApiException {
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            publicGoodsService.upperAndLowerShelves(vo.getStoreId(), goodsIds, null, status);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("上下架商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "upperAndLowerShelves");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean goodsByTop(MainVo vo, int goodsIds) throws LaiKeApiException {
        try {
            int maxSort = productListModelMapper.getGoodsMaxSort(vo.getStoreId());
            ProductListModel productListModel = new ProductListModel();
            productListModel.setId(goodsIds);
            productListModel.setSort(maxSort);
            int count = productListModelMapper.updateByPrimaryKeySelective(productListModel);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "置顶失败", "goodsByTop");
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商品置顶 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "goodsByTop");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean goodsMovePosition(MainVo vo, int currentGoodsId, int moveGoodsId) throws LaiKeApiException {
        try {
            //查询当前商品序号
            int currentSort = productListModelMapper.getGoodsSort(vo.getStoreId(), currentGoodsId);
            //查询被移动的商品序号
            int moveSort = productListModelMapper.getGoodsSort(vo.getStoreId(), moveGoodsId);

            //互换位置
            ProductListModel productListModel = new ProductListModel();
            productListModel.setId(currentGoodsId);
            productListModel.setSort(moveSort);
            int count = productListModelMapper.updateByPrimaryKeySelective(productListModel);
            if (count > 0) {
                productListModel.setId(moveGoodsId);
                productListModel.setSort(currentSort);
                count = productListModelMapper.updateByPrimaryKeySelective(productListModel);
                if (count > 0) {
                    return true;
                }
            }
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "移动失败", "goodsMovePosition");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商品上下移动 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "goodsMovePosition");
        }
    }

    @Override
    public Map<String, Object> getClassInfo(GoodsClassVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setStore_id(vo.getStoreId());
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            if (!StringUtils.isEmpty(vo.getClassName())) {
                productClassModel.setPname(vo.getClassName());
                if (vo.getLevel() == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "getClassInfo");
                }
                productClassModel.setLevel(vo.getLevel());
            } else {
                //1=查询下级,2=查询上级,3=根据类别Id查询 默认查询一级
                if (vo.getType() == null || vo.getType() == 0) {
                    //查询一级
                    productClassModel.setSid(0);
                } else if (vo.getType() == 1) {
                    //查询下级
                    if (vo.getClassId() == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "getClassInfo");
                    }
                    productClassModel.setSid(vo.getClassId());
                } else if (vo.getType() == 2) {
                    //查询上级
                    if (vo.getFatherId() == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "getClassInfo");
                    }
                    productClassModel.setSid(vo.getFatherId());
                } else if (vo.getType() == 3) {
                    //查询分类id
                    if (vo.getClassId() == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "getClassInfo");
                    }
                    productClassModel.setCid(vo.getClassId());
                }
            }
            PageModel pageModel = new PageModel(vo.getPageNo(), vo.getPageSize());
            productClassModel.setPageModel(pageModel);
            int total = productClassModelMapper.getGoodsClassCount(productClassModel);
            List<ProductClassModel> productClassModelList = productClassModelMapper.getProductClassLevel(productClassModel);
            List<Map<String, Object>> productClassModelMap = JSON.parseObject(JSON.toJSONString(productClassModelList), new TypeReference<List<Map<String, Object>>>() {
            });
            //图片处理
            for (Map<String, Object> map : productClassModelMap) {
                int level = MapUtils.getIntValue(map, "level") + 1;
                String levelFormat = "%s级分类";
                switch (level) {
                    case 1:
                        levelFormat = String.format(levelFormat, "一");
                        break;
                    case 2:
                        levelFormat = String.format(levelFormat, "二");
                        break;
                    case 3:
                        levelFormat = String.format(levelFormat, "三");
                        break;
                    default:
                        levelFormat = String.format(levelFormat, level);
                        break;
                }
                map.put("levelFormat", levelFormat);
                String imgUrl = publiceService.getImgPath(MapUtils.getString(map, "img"), vo.getStoreId());
                map.put("img", imgUrl);
                Date addDate = new Date(MapUtils.getLongValue(map, "add_date"));
                map.put("add_date", DateUtil.dateFormate(addDate, GloabConst.TimePattern.YMDHMS));
            }
            if (vo.getExportType().equals(1)) {
                exportGoodsClassData(productClassModelMap, response);
                return null;
            }
            resultMap.put("classInfo", productClassModelMap);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取类别信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getClassInfo");
        }
        return resultMap;
    }


    private void exportGoodsClassData(List<Map<String, Object>> goodsList, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"分类ID", "分类名称", "分类级别", "添加时间"};
            //对应字段
            String[] kayList = new String[]{"cid", "pname", "levelFormat", "add_date"};
            EasyPoiExcelUtil.excelExport("商品分类", headerList, kayList, goodsList, response);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品分类数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportGoodsClassData");
        }
    }

    @Override
    public Map<String, Object> getClassLevelTopAllInfo(MainVo vo, int classId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //获取当前类别信息
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setStore_id(vo.getStoreId());
            productClassModel.setCid(classId);
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            productClassModel = productClassModelMapper.selectOne(productClassModel);
            Map<Integer, List<ProductClassModel>> resultDataMap = new HashMap<>(16);
            if (productClassModel != null) {
                //图片处理
                String imgUrl = publiceService.getImgPath(productClassModel.getImg(), vo.getStoreId());
                productClassModel.setImg(imgUrl);
                //递归找上级
                publicGoodsService.getClassLevelAllInfo(vo.getStoreId(), classId, resultDataMap);
            }
            resultMap.put("classInfo", productClassModel);
            resultMap.put("levelInfoList", resultDataMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取当前类别所有上级 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getClassLevelAllInfo");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delClass(MainVo vo, int classId) throws LaiKeApiException {
        try {
            //校验商品是否设置了该类别,如果设置了则不能删除
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("classId", classId);
            if (productListModelMapper.countDynamic(parmaMap) > 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ERROR, "当前类别使用中,删除失败");
            }
            //递归找下级
            List<Integer> classIDList = new ArrayList<>();
            getClassLevelLowAll(vo.getStoreId(), classId, classIDList);
            classIDList.add(classId);
            for (Integer cid : classIDList) {
                //删除当前类别
                ProductClassModel productClassModel = new ProductClassModel();
                productClassModel.setCid(cid);
                productClassModel.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                int count = productClassModelMapper.updateByPrimaryKeySelective(productClassModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "删除失败", "delClass");
                }
                //删除跳转地址
                JumpPathModel jumpPathModel = new JumpPathModel();
                jumpPathModel.setSid(cid + "");
                jumpPathModel.setType0(JumpPathModel.JumpType.JUMP_TYPE0_GOODS_CLASS);
                jumpPathModelMapper.delete(jumpPathModel);
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除当前类别 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delClass");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addClass(MainVo vo, Integer classId, String className, String ename, String img, int level, int fatherId) throws LaiKeApiException {
        try {
            ProductClassModel productClassOld = null;
            //是否是编辑
            boolean isUpdate = false;
            if (classId != null) {
                isUpdate = true;
                productClassOld = productClassModelMapper.selectByPrimaryKey(classId);
                productClassOld.setCid(classId);
                productClassOld.setStore_id(vo.getStoreId());
                productClassOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                productClassOld = productClassModelMapper.selectOne(productClassOld);
                if (productClassOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "类别不存在", "addClass");
                }
            }

            ProductClassModel saveClass = new ProductClassModel();
            saveClass.setStore_id(vo.getStoreId());
            saveClass.setEnglish_name(ename);
            saveClass.setImg(ImgUploadUtils.getUrlImgByName(img, true));
            ProductClassModel productClassModel = new ProductClassModel();
            if (productClassOld == null || !className.equals(productClassOld.getPname())) {
                //判断类别名称是否存在
                productClassModel.setStore_id(vo.getStoreId());
                productClassModel.setLevel(level);
                productClassModel.setPname(className);
                productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                int count = productClassModelMapper.getGoodsClassCount(productClassModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "类名已存在", "addClass");
                }
                saveClass.setPname(className);
            }

            int count;
            if (level > 0 || fatherId > 0) {
                //判断上级是否存在
                productClassModel = new ProductClassModel();
                productClassModel.setStore_id(vo.getStoreId());
                productClassModel.setCid(fatherId);
                productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                productClassModel = productClassModelMapper.selectOne(productClassModel);
                if (productClassModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "上级不存在", "addClass");
                }
                //当前级别只能是上级的id
                int levelTemp = level - 1;
                if (productClassModel.getLevel() != levelTemp) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "上级和当前等级不符!");
                }
                saveClass.setIs_display(0);
            } else {
                level = 0;
                fatherId = 0;
            }
            saveClass.setLevel(level);
            saveClass.setSid(fatherId);
            int maxSort;
            //如果是修改了当前级别的等级则重新获取最小序号
            if (productClassOld == null || productClassOld.getLevel() != level) {
                maxSort = productClassModelMapper.getGoodsClassMaxSort(vo.getStoreId());
                //获取最新序号
                saveClass.setSort(maxSort);
            }
            if (!isUpdate) {
                //添加跳转路径只添加一级
                if (level == 0) {
                    publicAdminService.addJumpPath(vo, saveClass.getCid() + "", className, JumpPathModel.JumpType.JUMP_TYPE0_GOODS_CLASS, JumpPathModel.JumpType.JUMP_TYPE_APP,
                            GloabConst.LaikeTuiUrl.JumpPath.GOODS_CLASS_APP, new String[]{saveClass.getCid() + ""});
                    publicAdminService.addJumpPath(vo, saveClass.getCid() + "", className, JumpPathModel.JumpType.JUMP_TYPE0_GOODS_CLASS, JumpPathModel.JumpType.JUMP_TYPE_PC,
                            GloabConst.LaikeTuiUrl.JumpPath.GOODS_CLASS_PC, new String[]{saveClass.getCid() + ""});
                }
                saveClass.setAdd_date(new Date());
                count = productClassModelMapper.insertSelective(saveClass);
            } else {
                saveClass.setCid(classId);
                count = productClassModelMapper.updateByPrimaryKeySelective(saveClass);
            }

            if (count > 0) {
                return true;
            }
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数不正确", "addClass");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加当前类别 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addClass");
        }
        return false;
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean classSortTop(MainVo vo, Integer classId) throws LaiKeApiException {
        try {
            ProductClassModel updateClassModel = new ProductClassModel();
            updateClassModel.setCid(classId);
            //获取最新序号
            int maxSort = productClassModelMapper.getGoodsClassMaxSort(vo.getStoreId());
            updateClassModel.setSort(maxSort);

            //置顶
            int count = productClassModelMapper.updateByPrimaryKeySelective(updateClassModel);
            if (count > 0) {
                return true;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("类别置顶 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "classSortTop");
        }
        return false;
    }


    /**
     * 递归找下级
     *
     * @param storeId -
     * @param classId -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/30 14:41
     */
    private void getClassLevelLowAll(int storeId, int classId, List<Integer> classIdList) throws LaiKeApiException {
        try {
            //找下级
            ProductClassModel productClassModel = new ProductClassModel();
            productClassModel.setStore_id(storeId);
            productClassModel.setSid(classId);
            productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            List<ProductClassModel> productClassModelList = productClassModelMapper.select(productClassModel);
            for (ProductClassModel productClass : productClassModelList) {
                classIdList.add(productClass.getCid());
                getClassLevelLowAll(storeId, productClass.getCid(), classIdList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("递归找下级 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getClassLevelLowAll");
        }
    }


    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private PublicGoodsService publicGoodsService;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private ProductClassModelMapper productClassModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private PublicAdminService publicAdminService;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    private JumpPathModelMapper jumpPathModelMapper;

    @Autowired
    private ShopUtils shopUtils;
}
