package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.common.base.Splitter;
import com.laiketui.api.common.PublicCouponService;
import com.laiketui.api.common.PubliceService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.coupon.*;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.product.ProductClassModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.user.UserRuleModel;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.coupon.AddCouponActivityVo;
import com.laiketui.domain.vo.coupon.CouponParmaVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.algorithm.DataAlgorithmTool;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.phprpc.util.PHPSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;

import static com.laiketui.root.consts.ErrorCode.BizErrorCode.COUPON_PLAFORM_SCREENING_ERROR;
import static com.laiketui.root.consts.ErrorCode.BizErrorCode.ORDER_COUPON_CF_ERROR;

/**
 * 优惠卷公共流程
 *
 * @author Trick
 * @date 2020/10/30 15:00
 */
@Service
public class PublicCouponServiceImpl implements PublicCouponService {

    private final Logger logger = LoggerFactory.getLogger(PublicCouponServiceImpl.class);

    @Autowired
    private CouponConfigModelMapper couponConfigModelMapper;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private CouponModalMapper couponModelMapper;

    @Autowired
    private CouponActivityModelMapper couponActivityModelMapper;

    @Autowired
    private CouponOrderModalMapper couponOrderModalMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private UserFirstModalMapper userFirstModalMapper;

    @Autowired
    private UserRuleModelMapper userRuleModelMapper;

    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private UserGradeModelMapper userGradeModelMapper;

    @Autowired
    private CouponPresentationRecordModelMapper couponPresentationRecordModelMapper;

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public Integer index(int storeId) throws LaiKeApiException {
        int isStatus = 0;
        try {
            //当前时间
            String sysdateStr = FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS).format(new Date());
            Date sysDate = FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS).parse(sysdateStr);

            //获取优惠卷状态
            CouponConfigModel couponModel = new CouponConfigModel();
            couponModel.setStore_id(storeId);
            couponModel.setMch_id(0);
            couponModel = couponConfigModelMapper.selectOne(couponModel);
            if (couponModel != null) {
                isStatus = couponModel.getIs_status();
            }
            //获取该商城所有优惠卷
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setStore_id(storeId);
            couponActivityModel.setRecycle(0);
            List<CouponActivityModel> couponActivityModelList = couponActivityModelMapper.getCouponActivityAll(couponActivityModel);
            for (CouponActivityModel couponActivity : couponActivityModelList) {
                couponActivityModel = new CouponActivityModel();
                couponActivityModel.setId(couponActivity.getId());
                if (couponActivity.getStatus() == 0) {
                    //未启用插件流程
                    //如果优惠券活动开始时间 >= 当前时间 则修改优惠卷为开启状态
                    couponActivityModel.setStatus(1);
                } else {
                    //所有启用或禁用的优惠卷 流程
                    //判断活动是否已结束，如果结束则标记为结束状态
                    if (couponActivity.getEnd_time().getTime() <= sysDate.getTime()) {
                        couponActivityModel.setStatus(3);
                    } else {
                        continue;
                    }
                }
                //修改优惠卷状态
                int count = couponActivityModelMapper.updateSwitchCouponActivity(couponActivityModel);
                if (count < 1) {
                    logger.debug("优惠卷状态修改失败 id:" + couponActivity.getId());
                }
            }


        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("优惠卷流程异常:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "coupon");
        }

        return isStatus;
    }


    @Override
    public Map<String, Object> settlementStoreCoupons(int storeId, String userId, List<Map<String, Object>> products, String couponIds, boolean canshu) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            //优惠金额
            BigDecimal preferentialAmount = DataUtils.ZERO_BIGDECIMAL;
            //平台优惠券id
            String couponId0 = "0";
            //临时变量
            List<String> couponIdList0 = new ArrayList<>();
            //返回的优惠券变量
            List<String> couponIdList = new ArrayList<>();
            int pos = 0;
            for (Map<String, Object> mchProductInfo : products) {
                // 店铺ID
                int mchId = DataUtils.getIntegerVal(mchProductInfo, "shop_id");
                BigDecimal mchProductTotal = DataUtils.getBigDecimalVal(mchProductInfo, "product_total");
                // 购买商品的分类信息
                List<String> mchProductClass = new ArrayList<>();
                //购买商品的商品ID
                List<String> mchProductId = new ArrayList<>();
                //店铺总运费,如果总运费是0则是免邮
                BigDecimal mchYunfei = new BigDecimal(MapUtils.getString(mchProductInfo, "freight_price"));
                List<Map<String, Object>> oneMchProducts = (List<Map<String, Object>>) mchProductInfo.get("list");
                for (Map<String, Object> product : oneMchProducts) {
                    product.put("discount", 0);
                    product.put("coupon_id", 0);
                    // 购买商品的分类信息
                    mchProductClass.add(DataUtils.getStringVal(product, "product_class"));
                    // 购买商品的商品ID
                    mchProductId.add(DataUtils.getStringVal(product, "pid"));
                }

                String couponId = "0";
                if (StringUtils.isNotEmpty(couponIds)) {
                    couponIdList0 = Splitter.on(SplitUtils.DH).omitEmptyStrings().trimResults().splitToList(couponIds);
                    //对应product里面的
                    couponId = couponIdList0.get(pos);
                    int couponPos = couponIdList0.size() - 1;
                    couponId0 = couponIdList0.get(couponPos);
                }

                couponIdList.add(couponId);
                // 获取用户满足条件的店铺优惠券
                Map<String, Object> userCoupons = getStoreCoupons(storeId, userId, mchId, oneMchProducts, mchProductTotal, mchProductClass, mchProductId, mchYunfei, couponId, canshu);
                mchProductInfo.put("list", userCoupons.get("products_list"));
                List<Map<String, Object>> arr = (List<Map<String, Object>>) userCoupons.get("list");
                mchProductInfo.put("coupon_list", arr);

                //不使用优惠券的情况 运费+商品金额
                BigDecimal shopMoney = mchYunfei.add(mchProductTotal);
                if (!CollectionUtils.isEmpty(arr)) {
                    //使用优惠卷
                    for (Map<String, Object> couponInfo : arr) {
                        Boolean couponStatus = DataUtils.getBooleanVal(couponInfo, "coupon_status");
                        String id = DataUtils.getStringVal(couponInfo, "coupon_id");
                        couponInfo.put("coupon_id", StringUtils.trim(id, SplitUtils.DH));
                        if (couponStatus && !"0".equals(id)) {
                            //获取优惠金额
                            BigDecimal money = DataUtils.getBigDecimalVal(couponInfo, "money");
                            couponIdList.set(couponIdList.size() - 1, (String) couponInfo.get("coupon_id"));
                            //使用优惠卷 商品总金额-优惠金额=优惠后的金额
                            shopMoney = shopMoney.subtract(money);
                            if (BigDecimal.ZERO.compareTo(shopMoney) == 0) {
                                shopMoney = BigDecimal.ZERO;
                            }
                            preferentialAmount = preferentialAmount.add(money);
                        }
                    }
                }
                mchProductInfo.put("shop_subtotal", shopMoney);
                pos++;
            }
            couponIdList.add(couponId0);
            resultMap.put("coupon_id", org.apache.commons.lang3.StringUtils.join(couponIdList, SplitUtils.DH));
            resultMap.put("products", products);
            resultMap.put("preferential_amount", preferentialAmount);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("店铺优惠券筛选 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.COUPON_MCH_SCREENING_ERROR, "店铺优惠券筛选失败", "settlementStoreCoupons");
        }
        return resultMap;
    }


    /**
     * 获取店铺优惠券
     *
     * @param storeId
     * @param userId
     * @param mchId
     * @param productsList
     * @param mchProductTotal
     * @param mchProductClass
     * @param mchProductId
     * @param mchYunfei
     * @param couponId
     * @param canshu
     * @return
     * @throws LaiKeApiException;
     */
    private Map<String, Object> getStoreCoupons(int storeId, String userId, int mchId, List<Map<String, Object>> productsList, BigDecimal mchProductTotal,
                                                List<String> mchProductClass, List<String> mchProductId, BigDecimal mchYunfei, String couponId, boolean canshu) throws LaiKeApiException {
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 当前时间
            Date time = new Date();
            List<Map<String, Object>> arr = new ArrayList<>();
            List<Map<String, Object>> arr1 = new ArrayList<>();

            // 查询该用户有多少使用中的优惠券属于该店铺
            Map<String, Object> map = new HashMap<>(16);
            map.put("store_id", storeId);
            map.put("user_id", userId);
            map.put("mch_id", mchId);
            //未使用的优惠券
            map.put("type", 0);
            //更新优惠券
            Map<String, Object> m = new HashMap<>(map);
            unUseCoupons(storeId, time, m);
            List<Map<String, Object>> usersCoupons = couponModelMapper.getUsersCoupons(map);
            if (!CollectionUtils.isEmpty(usersCoupons)) {
                for (Map<String, Object> userCoupon : usersCoupons) {
                    // 循环判断 优惠券是否绑定
                    // 优惠券id
                    int tmpCouponId = DataUtils.getIntegerVal(userCoupon, "id");
                    // 优惠券到期时间
                    Date expiryTime = (Date) userCoupon.get("expiry_time");
                    CouponModal couponModel = new CouponModal();
                    if (expiryTime.before(time)) {
                        // 当前时间 >= 优惠券到期时间
                        couponModel.setType(3);
                        couponModel.setId(tmpCouponId);
                    } else {
                        // 优惠券名称
                        String name = DataUtils.getStringVal(userCoupon, "name");
                        // 优惠券类型
                        String activityType = DataUtils.getStringVal(userCoupon, "activity_type");
                        // 面值
                        BigDecimal money = DataUtils.getBigDecimalVal(userCoupon, "money");
                        // 折扣值
                        BigDecimal discount = DataUtils.getBigDecimalVal(userCoupon, "discount");
                        // 满多少
                        BigDecimal zMoney = DataUtils.getBigDecimalVal(userCoupon, "z_money");
                        // 优惠券使用范围
                        int type = DataUtils.getIntegerVal(userCoupon, "type");
                        // 指定分类id
                        String productClassId = DataUtils.getStringVal(userCoupon, "product_class_id");
                        // 指定商品id
                        String productId1 = DataUtils.getStringVal(userCoupon, "product_id");
                        // 用户可以使用优惠券的场景
                        Map<String, Object> tmpUserCanUseCouponSences = new HashMap<>();
                        // 会员赠送优惠券
                        if (CouponModal.COUPON_TYPE_HYZS.equals(activityType)) {
                            tmpUserCanUseCouponSences = couponScreening(type, userCoupon, productId1, mchProductId, productClassId, mchProductClass);
                        } else {
                            // 原本运费为0 并且 在使用中的优惠券为免邮券
                            if (mchYunfei.compareTo(DataUtils.ZERO_BIGDECIMAL) == 0 && CouponModal.COUPON_TYPE_MY.equals(activityType)) {
                                tmpUserCanUseCouponSences = null;
                            } else {
                                tmpUserCanUseCouponSences = couponScreening(type, userCoupon, productId1, mchProductId, productClassId, mchProductClass);
                            }
                        }

                        // 商品总价
                        BigDecimal mchProductTotal0 = new BigDecimal(0);
                        if (tmpUserCanUseCouponSences != null) {
                            for (Map<String, Object> product : productsList) {
                                String pid = DataUtils.getStringVal(product, "pid");
                                BigDecimal membership_price = DataUtils.getBigDecimalVal(product, "membership_price", BigDecimal.ZERO);
                                BigDecimal num = DataUtils.getBigDecimalVal(product, "num", BigDecimal.ZERO);
                                String accord_with = DataUtils.getStringVal(tmpUserCanUseCouponSences, "accord_with", "");
                                if (accord_with.contains(pid)) {
                                    mchProductTotal0 = mchProductTotal0.add(membership_price.multiply(num));
                                }
                            }
                            Map<String, Object> list;
                            if (DataUtils.ZERO_BIGDECIMAL.compareTo(zMoney) == 0) {
                                // 当无限制时
                                if (money.compareTo(mchProductTotal0) >= 0) {
                                    // 优惠券金额 > 商品总价
                                    tmpUserCanUseCouponSences = null;
                                } else {
                                    tmpUserCanUseCouponSences.put("money", money);
                                }
                            } else {
                                //满减限制
                                if (zMoney.compareTo(mchProductTotal0) >= 0) {
                                    logger.debug(name + " 满减失败,不满足条件;当前店铺商品合计价格:{},满减阈值金额:{}", mchProductTotal0, zMoney);
                                    continue;
                                }
                            }
                            list = tmpUserCanUseCouponSences;
                            if (list != null) {
                                BigDecimal zongMoney = mchProductTotal0.multiply(discount).divide(new BigDecimal(10), 2, BigDecimal.ROUND_HALF_UP);
                                BigDecimal zongMoney1 = DataUtils.ZERO_BIGDECIMAL;
                                if (zongMoney.compareTo(DataUtils.ZERO_BIGDECIMAL) > 0) {
                                    zongMoney1 = mchProductTotal0.subtract(zongMoney);
                                }
                                if (CouponModal.COUPON_TYPE_ZK.equals(activityType)) {
                                    list.put("coupon_name", name + ":优惠" + zongMoney1 + "金额(" + discount + "折)");
                                    list.put("money", zongMoney1);
                                } else {
                                    final String value = DoubleFormatUtil.formattf(zongMoney1.doubleValue());
                                    String text = name + ":优惠" + money + "金额(" + money + ")";
                                    if (CouponModal.COUPON_TYPE_MJ.equals(activityType)) {
                                        list.put("money", money);
                                        list.put("coupon_name", text);
                                    } else if (CouponModal.COUPON_TYPE_HYZS.equals(activityType)) {
                                        // 当会员赠券为折扣时
                                        if (DataUtils.equalBigDecimalZero(money) && !DataUtils.equalBigDecimalZero(discount)) {
                                            list.put("coupon_name", name + ":优惠" + zongMoney1 + "金额(" + discount + ")");
                                            list.put("money", value);
                                        } else {
                                            list.put("coupon_name", name + ":优惠" + list.get("money") + "金额");
                                        }
                                    }
                                }
                                // 优惠券金额
                                list.put("mch_product_total", mchProductTotal0);
                                arr1.add(list);
                            }
                        }
                    }
                }
            }

            if (arr1.size() > 0) {
                //金额倒序排序 金额大的放第一位
                Collections.sort(arr1, new Comparator<Map<String, Object>>() {
                    @Override
                    public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                        BigDecimal sort1 = new BigDecimal(MapUtils.getString(o1, "money"));
                        BigDecimal sort2 = new BigDecimal(MapUtils.getString(o2, "money"));
                        return sort2.compareTo(sort1);
                    }
                });
                Map temp = new HashMap();
                temp.put("id", 0);
                temp.put("coupon_name", "不使用优惠券");
                temp.put("money", null);
                temp.put("accord_with", new ArrayList<>());
                temp.put("mch_product_total", mchProductTotal);
                arr1.add(temp);
                //修改默认优惠券
                int couponid = DataUtils.getIntegerVal(arr1.get(0), "id");
                CouponModal couponModal = new CouponModal();
                couponModal.setId(couponid);
                couponModal.setType(1);
                couponModelMapper.updateByPrimaryKeySelective(couponModal);

                int pos = 0;
                Map<String, Object> retTempMap = null;
                for (Map<String, Object> list : arr1) {
                    retTempMap = new HashMap<>();
                    boolean couponStatus = false;
                    String id = DataUtils.getStringVal(list, "id");
                    if (StringUtils.isNotEmpty(couponId) && "0".equals(couponId)) {
                        if (couponId.equals(id)) {
                            couponStatus = true;
                        }
                    } else {
                        // 当还未选择优惠券
                        if (canshu) {
                            // 设置默认优惠券
                            if (pos == 0) {
                                couponStatus = true;
                            }
                        } else {
                            // 不使用优惠券
                            if (couponId.equals(id)) {
                                couponStatus = true;
                            }
                        }
                    }
                    retTempMap.put("coupon_id", id);
                    BigDecimal money = DataUtils.getBigDecimalVal(list, "money", BigDecimal.ZERO);
                    retTempMap.put("coupon_name", list.get("coupon_name"));
                    retTempMap.put("coupon_status", couponStatus);
                    BigDecimal shopSubTotal = mchProductTotal.subtract(money);
                    retTempMap.put("shop_subtotal", shopSubTotal);
                    if ("0".equals(id)) {
                        retTempMap.put("money", 0);
                    } else {
                        retTempMap.put("money", money);
                    }
                    retTempMap.put("accord_with", list.get("accord_with"));
                    retTempMap.put("mch_product_total", list.get("mch_product_total"));
                    pos = pos + 1;
                    arr.add(retTempMap);
                }
            }

            for (Map<String, Object> tmp : arr) {
                boolean couponStatus = (boolean) tmp.get("coupon_status");
                if (couponStatus) {
                    List<String> accordWiths = (List<String>) tmp.get("accord_with");
                    if (accordWiths.size() > 0) {
                        for (String accordWith : accordWiths) {
                            for (Map<String, Object> productInfo : productsList) {
                                if (accordWith.equals(MapUtils.getString(productInfo, "pid"))) {
                                    BigDecimal membership_price = new BigDecimal(MapUtils.getString(productInfo, "membership_price"));
                                    // 注意取值来源
                                    BigDecimal mch_product_total = new BigDecimal(MapUtils.getString(tmp, "mch_product_total"));
                                    BigDecimal money = new BigDecimal(MapUtils.getString(tmp, "money"));
                                    int num = Integer.parseInt(MapUtils.getString(productInfo, "num"));
                                    //单商品总价
                                    BigDecimal singleCommodity = membership_price.multiply(new BigDecimal(num));
                                    //单个商品的总优惠金额
                                    BigDecimal singleCommodityYouhuiMoney = singleCommodity.divide(mch_product_total, 2, BigDecimal.ROUND_HALF_UP).multiply(money);
                                    productInfo.put("discount", DoubleFormatUtil.format(singleCommodityYouhuiMoney.doubleValue()));
                                    productInfo.put("coupon_id", couponId);
                                    //优惠后金额
                                    BigDecimal amount_after_discount = singleCommodity.subtract(singleCommodityYouhuiMoney);
                                    productInfo.put("amount_after_discount", DoubleFormatUtil.format(amount_after_discount.doubleValue()));
                                }
                            }
                        }
                    }
                }
            }


            retMap.put("products_list", productsList);
            retMap.put("list", arr);
            return retMap;
        } catch (Exception e) {
            logger.error("店铺优惠券获取 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.COUPON_MCH_SCREENING_ERROR, "店铺优惠券获取失败", "getStoreCoupons");
        }
    }

    /**
     * couponScreening
     * 店铺优惠券筛选
     *
     * @param type
     * @param userCoupon
     * @param productId1
     * @param mchProductId
     * @param productClassId
     * @param mchProductClass
     */
    private Map<String, Object> couponScreening(int type, Map<String, Object> userCoupon, String productId1,
                                                List<String> mchProductId, String productClassId, List<String> mchProductClass) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 全部商品 符合
            if (CouponActivityModel.USE_RANG_TYPE_ALL == type) {
                userCoupon.put("accord_with", mchProductId);
                resultMap = userCoupon;
            } else if (CouponActivityModel.USE_RANG_TYPE_PRODUCT == type) {
                //指定商品
                resultMap = productAccord(userCoupon, productId1, mchProductId);
                int productStatus = (int) resultMap.get("product_status");
                if (productStatus != 0) {
                    resultMap = (Map<String, Object>) resultMap.get("list");
                }
            } else if (CouponActivityModel.USE_RANG_TYPE_CLASS == type) {
                //指定分类
                resultMap = calssAccord(userCoupon, productClassId, mchProductClass, mchProductId);
                int calssStatus = (int) resultMap.get("class_status");
                if (calssStatus != 0) {
                    resultMap = (Map<String, Object>) resultMap.get("list");
                }
            }
            return resultMap;
        } catch (LaiKeApiException e) {
            e.printStackTrace();
            throw e;
        } catch (Exception e) {
            logger.error("优惠券筛选 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.COUPON_SCREENING_ERROR, "优惠券筛选失败", "couponScreening");
        }
    }

    /**
     * 获取指定分类
     *
     * @param userCoupon
     * @param productClassId
     * @param mchProductClasses
     * @param mchProductId
     * @return
     * @throws LaiKeApiException;
     */
    private Map<String, Object> calssAccord(Map<String, Object> userCoupon, String productClassId, List<String> mchProductClasses, List<String> mchProductId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap();
        try {
            // (优惠券指定商品ID,购买商品ID)
            // 商品状态 0:代表购买商品 不符合 优惠券指定 分类
            int calssStatus = 0;
            // 存储符合的商品ID
            List<String> fhtjClassIds = new ArrayList<String>();
            userCoupon.put("accord_with", fhtjClassIds);
            productClassId = getUnserText(productClassId);
            for (String mchProductClass : mchProductClasses) {
                //todo 可能出错
                if (productClassId.indexOf(mchProductClass + SplitUtils.DH) > -1) {
                    // 符合
                    fhtjClassIds.add(mchProductClass);
                    // 商品状态 1:代表购买商品 符合 优惠券指定 商品
                    calssStatus = 1;
                }
            }
            resultMap.put("calssStatus", calssStatus);
            resultMap.put("list", userCoupon);
            return resultMap;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.COUPON_SCREENING_ERROR, "优惠券筛选失败:指定分类", "couponScreening");
        }
    }

    /**
     * 获取反序列化的数据 12，12，212，
     *
     * @param content
     * @return
     */
    public String getUnserText(String content) {
        PHPSerializer p = new PHPSerializer();
        Object array1 = null;
        try {
            array1 = p.unserialize(content.getBytes());
            return new String((byte[]) array1) + SplitUtils.DH;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 获取符合使用优惠券条件的指定商品信息
     *
     * @param userCoupon
     * @param productId1
     * @param mchProductIds
     * @return
     */
    private Map<String, Object> productAccord(Map<String, Object> userCoupon, String productId1, List<String> mchProductIds) {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            // (优惠券指定商品ID,购买商品ID)
            // 商品状态 0:代表购买商品 不符合 优惠券指定 商品
            int productStatus = 0;
            // 存储符合的商品ID
            List<String> appointProductIds = new ArrayList<>();
            productId1 = getUnserText(productId1);
            for (String mchProductId : mchProductIds) {
                if (productId1.contains(mchProductId + SplitUtils.DH)) {
                    // 符合
                    appointProductIds.add(mchProductId);
                    // 商品状态 1:代表购买商品 符合 优惠券指定 商品
                    productStatus = 1;
                }
            }
            resultMap.put("product_status", productStatus);
            //符合的id集
            userCoupon.put("accord_with", appointProductIds);
            resultMap.put("list", userCoupon);
            return resultMap;
        } catch (Exception e) {
            logger.error("优惠券筛选失败:指定商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.COUPON_SCREENING_ERROR, "优惠券筛选失败:指定商品", "productAccord");
        }
    }


    @Override
    public Map<String, Object> settlementPlaformCoupons(int storeId, String userId, List<Map<String, Object>> products, String couponIds) throws LaiKeApiException {
        Map<String, Object> retMap = new HashMap<>();
        try {
            // 当前时间
            Date time = new Date();
            List<Map<String, Object>> arr = new ArrayList<>();
            List<Map<String, Object>> arr1 = new ArrayList<>();

            BigDecimal discount = BigDecimal.ONE;
            //商品总价(会员折扣后)
            BigDecimal shopSubtotal = BigDecimal.ZERO;
            //商品总价(折扣后、去除运费)
            BigDecimal shopGoodsTal = BigDecimal.ZERO;
            //平台优惠券id
            String platfromCouponId = "0";
            // 购买商品的分类信息
            List<String> productClasses = new ArrayList<>();
            // 购买商品的商品ID
            List<String> productIdes = new ArrayList<>();
            if (StringUtils.isNotEmpty(couponIds)) {
                String[] couponids = couponIds.split(SplitUtils.DH);
                platfromCouponId = couponids[couponids.length - 1];
            }

            //计算当前店铺商品价格信息
            for (Map<String, Object> mchProductInfo : products) {
                // 商品总价(使用店铺优惠券后的总价)
                BigDecimal mchProductTotal = DataUtils.getBigDecimalVal(mchProductInfo, "shop_subtotal", BigDecimal.ZERO);
                //运费
                BigDecimal yunFei = new BigDecimal(MapUtils.getString(mchProductInfo, "freight_price"));
                shopSubtotal = shopSubtotal.add(mchProductTotal);
                //去除运费的纯价格
                shopGoodsTal = shopSubtotal.subtract(yunFei);

                List<Map<String, Object>> oneMchProducts = (List<Map<String, Object>>) mchProductInfo.get("list");
                for (Map<String, Object> product : oneMchProducts) {
                    productClasses.add((String) product.get("product_class"));
                    productIdes.add(String.valueOf(product.get("pid")));
                }
            }

            Map<String, Object> map = new HashMap<>(16);
            map.put("store_id", storeId);
            map.put("user_id", userId);
            //平台店铺id为0
            map.put("mch_id", 0);
            //  type 类型 0:未使用 1:使用中 2:已使用 3:已过期
            map.put("type", 0);
            //修改使用中的优惠券状态
            Map<String, Object> m = new HashMap<>(map);
            unUseCoupons(storeId, time, m);
            List<Map<String, Object>> usersCoupons = couponModelMapper.getUsersCoupons(map);
            if (!CollectionUtils.isEmpty(usersCoupons)) {
                //获取可以使用的优惠券
                for (Map<String, Object> userCoupon : usersCoupons) {
                    // 优惠券id
                    int tmpCouponId = MapUtils.getIntValue(userCoupon, "id");
                    // 优惠券到期时间
                    Date expiryTime = (Date) userCoupon.get("expiry_time");
                    CouponModal couponModel = new CouponModal();
                    if (expiryTime.before(time)) {
                        // 当前时间 >= 优惠券到期时间
                        //  type 类型 0:未使用 1:使用中 2:已使用 3:已过期
                        couponModel.setType(3);
                        couponModel.setId(tmpCouponId);
                    } else {
                        // 优惠券名称
                        String name = DataUtils.getStringVal(userCoupon, "name", "");
                        // 优惠券类型
                        String activityType = DataUtils.getStringVal(userCoupon, "activity_type", "");
                        // 面值
                        BigDecimal money = DataUtils.getBigDecimalVal(userCoupon, "money", BigDecimal.ZERO);
                        // 折扣值
                        discount = DataUtils.getBigDecimalVal(userCoupon, "discount");
                        // 满多少
                        BigDecimal zMoney = DataUtils.getBigDecimalVal(userCoupon, "z_money", BigDecimal.ZERO);
                        // 优惠券使用范围
                        int type = DataUtils.getIntegerVal(userCoupon, "type");
                        // 指定分类id
                        String productClassId = (String) userCoupon.get("product_class_id");
                        // 指定商品id
                        String productId1 = (String) userCoupon.get("product_id");
                        // 用户可以使用优惠券的场景
                        Map<String, Object> tmpUserCanUseCouponSences = new HashMap<>();
                        if (CouponModal.COUPON_TYPE_MY.equals(activityType)) {
                            userCoupon.put("money", money);
                            userCoupon.put("coupon_name", name);
                            tmpUserCanUseCouponSences = screenCouponList(CouponModal.COUPON_TYPE_MY, type, shopSubtotal, zMoney, money, userCoupon, productId1, productIdes, productClasses, productClassId);
                        } else if (CouponModal.COUPON_TYPE_MJ.equals(activityType)) {
                            //满减限制
                            userCoupon.put("coupon_name", name + ":优惠" + money + "金额");
                            userCoupon.put("money", money);
                            tmpUserCanUseCouponSences = screenCouponList(CouponModal.COUPON_TYPE_MJ, type, shopSubtotal, zMoney, money, userCoupon, productId1, productIdes, productClasses, productClassId);

                        } else if (CouponModal.COUPON_TYPE_ZK.equals(activityType)) {
                            BigDecimal zongMoney = shopSubtotal.multiply(discount).multiply(new BigDecimal("100")).divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
                            BigDecimal zongMoney1 = BigDecimal.ZERO;
                            if (zongMoney.compareTo(BigDecimal.ZERO) > 0) {
                                zongMoney1 = shopSubtotal.subtract(zongMoney);
                            }
                            userCoupon.put("coupon_name", name + ":优惠" + zongMoney1 + "金额(" + discount + "折)");
                            userCoupon.put("money", zongMoney1);
                            tmpUserCanUseCouponSences = screenCouponList(CouponModal.COUPON_TYPE_ZK, type, shopSubtotal, zMoney, money, userCoupon, productId1, productIdes, productClasses, productClassId);
                        } else {
                            //会员赠送券
                            if (CouponModal.COUPON_TYPE_HYZS.equals(activityType)) {
                                if (BigDecimal.ZERO.compareTo(money) == 0 && BigDecimal.ZERO.compareTo(discount) != 0) {
                                    BigDecimal zongMoney = shopSubtotal.multiply(discount).multiply(new BigDecimal("100")).divide(new BigDecimal("1000"), 2, BigDecimal.ROUND_HALF_UP);
                                    BigDecimal zongMoney1 = BigDecimal.ZERO;
                                    if (zongMoney.compareTo(BigDecimal.ZERO) > 0) {
                                        zongMoney1 = shopSubtotal.subtract(zongMoney);
                                    }
                                    // 当会员赠券为折扣时
                                    userCoupon.put("money", zongMoney1);
                                    userCoupon.put("coupon_name", name + ":优惠" + zongMoney1 + "金额(" + discount + "折)");
                                    tmpUserCanUseCouponSences = screenCouponList(CouponModal.COUPON_TYPE_MJ, type, shopSubtotal, zongMoney1, money, userCoupon, productId1, productIdes, productClasses, productClassId);
                                } else {
                                    userCoupon.put("money", money);
                                    userCoupon.put("coupon_name", name + ":优惠" + money + "金额");
                                    tmpUserCanUseCouponSences = screenCouponList(CouponModal.COUPON_TYPE_ZK, type, shopSubtotal, zMoney, money, userCoupon, productId1, productIdes, productClasses, productClassId);
                                }
                            }
                        }
                        if (tmpUserCanUseCouponSences == null || tmpUserCanUseCouponSences.isEmpty()) {
                            //该卷不满足条件
                            continue;
                        }
                        arr1.add(tmpUserCanUseCouponSences);
                    }
                }
            }

            BigDecimal couponDiscount = new BigDecimal("0");
            if (!CollectionUtils.isEmpty(arr1)) {
                arr1.sort((o1, o2) -> {
                    BigDecimal sort1 = (BigDecimal) o1.get("money");
                    BigDecimal sort2 = (BigDecimal) o2.get("money");
                    return sort2.compareTo(sort1);
                });
                Map<String, Object> retTmp;
                //循环处理当前使用的优惠券
                for (Map<String, Object> couponInfos : arr1) {
                    retTmp = new HashMap<>();
                    //是否使用优惠券标识
                    boolean couponStatus = false;
                    BigDecimal money = DataUtils.getBigDecimalVal(couponInfos, "money", BigDecimal.ZERO);
                    if (StringUtils.isNotEmpty(platfromCouponId) && !"0".equals(platfromCouponId)) {
                        if (platfromCouponId.equals(String.valueOf(couponInfos.get("id")))) {
                            //如果是使用,则标记该优惠券使用中
                            couponStatus = true;
                            CouponModal couponModal = new CouponModal();
                            couponModal.setId(Integer.parseInt(platfromCouponId));
                            couponModal.setType(1);
                            couponModelMapper.updateByPrimaryKeySelective(couponModal);
                        }
                    }

                    retTmp.put("coupon_id", couponInfos.get("id"));
                    retTmp.put("discount_type", "coupon");
                    retTmp.put("activity_type", couponInfos.get("activity_type"));
                    retTmp.put("money", couponInfos.get("money"));
                    retTmp.put("coupon_name", couponInfos.get("coupon_name"));
                    retTmp.put("coupon_status", couponStatus);
                    retTmp.put("shop_subtotal", DoubleFormatUtil.format(shopSubtotal.subtract(money).doubleValue()));
                    if (couponStatus) {
                        //平台总共优惠金额
                        couponDiscount = couponDiscount.add(money);
                    }
                    arr.add(retTmp);
                }
            }

            //计算每个商品优惠后的价格
            for (Map<String, Object> mchProducts : products) {
                List<Map<String, Object>> onlyProducts = DataUtils.cast(mchProducts.get("list"));
                //店铺商品支付总价(+运费*数量)
                BigDecimal orderShopPrice = new BigDecimal(MapUtils.getString(mchProducts, "shop_subtotal", "0"));
                //循环当前订单店铺下所有商品
                for (Map<String, Object> product : onlyProducts) {
                    if (!CollectionUtils.isEmpty(arr)) {
                        //当前商品运费
                        BigDecimal freightPrice = new BigDecimal(MapUtils.getString(product, "freight_price", "0"));
                        //当前商品实际支付价格
                        BigDecimal goodsPayPrice = new BigDecimal(MapUtils.getString(product, "amount_after_discount", "0")).add(freightPrice);
                        //计算平台优惠后的商品价格 如果有多个商品 则每个商品平摊优惠金额
                        BigDecimal youHuiPrice = goodsPayPrice.subtract(DataAlgorithmTool.orderPriceAverage(orderShopPrice, goodsPayPrice, couponDiscount)).subtract(freightPrice);
                        //当前商品优惠后金额(商品最终成交金额 不算运费)
                        product.put("amount_after_discount", youHuiPrice);
                    }
                    String couponId = MapUtils.getString(product, "coupon_id");
                    product.put("coupon_id", couponId + SplitUtils.DH + platfromCouponId);
                }
            }

            retMap.put("products", products);
            retMap.put("list", arr);
            return retMap;
        } catch (Exception e) {
            logger.error("平台优惠券筛选 异常", e);
            throw new LaiKeApiException(COUPON_PLAFORM_SCREENING_ERROR, "平台优惠券筛选失败", "settlementPlaformCoupons");
        }
    }

    @Override
    public Map<String, Object> mycoupon(int storeId, String userId, Integer type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //去使用
            List<Map<String, Object>> toUseList = new ArrayList<>();
            //已使用
            List<Map<String, Object>> useList = new ArrayList<>();
            //已过期
            List<Map<String, Object>> expiredUseList = new ArrayList<>();

            //获取用户优惠卷列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("user_id", userId);
            if (type != null) {
                List<Integer> typeList = new ArrayList<>();
                if (type == 1) {
                    //已使用
                    typeList.add(CouponModal.COUPON_TYPE_USED);
                } else if (type == 2) {
                    //已过期
                    typeList.add(CouponModal.COUPON_TYPE_EXPIRED);
                } else {
                    //未使用
                    typeList.add(CouponModal.COUPON_TYPE_NOT_USED);
                    typeList.add(CouponModal.COUPON_TYPE_IN_USE);
                }
                parmaMap.put("typeList", typeList);
            }
            parmaMap.put("type_sort", DataUtils.Sort.ASC.toString());
            parmaMap.put("add_time_sort", DataUtils.Sort.ASC.toString());
            List<Map<String, Object>> couponModalList = couponModelMapper.getCouponInfoListDynamic(parmaMap);
            for (Map<String, Object> map : couponModalList) {
                //优惠卷id
                int couponId = Integer.parseInt(map.get("id").toString());
                //活动id
                int hid = StringUtils.stringParseInt(map.get("hid") + "");
                //优惠卷到期时间
                Date expiryDate = (Date) map.get("expiry_time");
                //优惠卷名称
                String couponName = "";
                //优惠卷使用状态
                int couponType = Integer.parseInt(map.get("type").toString());

                map.put("add_time", DateUtil.dateFormate(map.get("add_time").toString(), GloabConst.TimePattern.YMD));
                map.put("expiry_time", DateUtil.dateFormate(map.get("expiry_time").toString(), GloabConst.TimePattern.YMD));
                //查询活动信息
                CouponActivityModel couponActivityModel = new CouponActivityModel();
                couponActivityModel.setStore_id(storeId);
                couponActivityModel.setId(hid);
                couponActivityModel = couponActivityModelMapper.selectOne(couponActivityModel);
                if (couponActivityModel != null) {
                    couponName = couponActivityModel.getName();
                    //优惠卷所属店铺id
                    int mchId = couponActivityModel.getMch_id();
                    //获取店铺信息
                    if (mchId != 0) {
                        MchModel mchModel = new MchModel();
                        mchModel.setStore_id(storeId);
                        mchModel.setId(mchId);
                        mchModel = mchModelMapper.selectOne(mchModel);
                        if (mchModel != null) {
                            couponName = String.format("[%s]%s", mchModel.getName(), couponName);
                        }
                    }
                    //消费限制处理
                    String limitStr = "无限制";
                    if (couponActivityModel.getZ_money().floatValue() > 0) {
                        limitStr = String.format("满%s可用", couponActivityModel.getZ_money().toString());
                    }
                    map.put("name", couponName);
                    map.put("limit", limitStr);
                    map.put("money", couponActivityModel.getMoney());
                    map.put("discount", couponActivityModel.getDiscount());
                    map.put("activity_type", couponActivityModel.getActivity_type());
                    map.put("Instructions", couponActivityModel.getInstructions());
                }
                //过期处理
                if (DateUtil.dateCompare(new Date(), expiryDate) && couponType != CouponModal.COUPON_TYPE_USED) {
                    //修改当前优惠卷状态
                    int count = couponModelMapper.updateCoupon(CouponModal.COUPON_TYPE_EXPIRED, storeId, userId, couponId);
                    if (count < 1) {
                        logger.info(String.format("优惠卷修改过期失败 优惠卷id:%s", couponId));
                    }
                }
                //优惠卷状态处理
                String pointStr = null;
                if (CouponModal.COUPON_TYPE_NOT_USED == couponType) {
                    pointStr = "去使用";
                    toUseList.add(map);
                } else if (CouponModal.COUPON_TYPE_IN_USE == couponType) {
                    //查询是否有订单在使用该优惠卷
                    parmaMap.clear();
                    parmaMap.put("store_id", storeId);
                    parmaMap.put("user_id", userId);
                    parmaMap.put("coupon_id", couponId);
                    //订单未关闭
                    parmaMap.put("statusType", 1);
                    int count = orderModelMapper.countOrdersNumDynamic(parmaMap);
                    if (count > 0) {
                        pointStr = "已使用";
                        useList.add(map);
                    } else {
                        pointStr = "去使用";
                        toUseList.add(map);
                    }
                } else if (CouponModal.COUPON_TYPE_USED == couponType) {
                    pointStr = "已使用";
                    useList.add(map);
                } else if (CouponModal.COUPON_TYPE_EXPIRED == couponType) {
                    pointStr = "已过期";
                    expiredUseList.add(map);
                }
                map.put("point", pointStr);
            }

            //查询出用户未使用的赠送商品
            parmaMap.clear();
            parmaMap.put("store_id", storeId);
            parmaMap.put("user_id", userId);
            parmaMap.put("attrIdNotNull", "notNull");
            List<Map<String, Object>> userFreeGoodsList = userFirstModalMapper.getUserFirstInfoDynamic(parmaMap);
            for (Map<String, Object> map : userFreeGoodsList) {
                //商品规格id
                int attrId = Integer.parseInt(map.get("attr_id").toString());
                //是否使用了首次开通赠送商品券 0-未使用 1-已使用
                int isUse = Integer.parseInt(map.get("flag").toString());
                //兑换卷失效日期
                String endTime = DateUtil.dateFormate(map.get("end_time").toString(), GloabConst.TimePattern.YMD);

                //默认有效天数
                int validDay = 7;
                //获取配置有效天数
                UserRuleModel userRuleModel = new UserRuleModel();
                userRuleModel.setStore_id(storeId);
                userRuleModel = userRuleModelMapper.selectOne(userRuleModel);
                if (userRuleModel != null) {
                    validDay = userRuleModel.getValid();
                }
                map.put("valid", validDay);

                //获取商品库存信息
                parmaMap.clear();
                parmaMap.put("store_id", storeId);
                parmaMap.put("active", DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_PRICE);
                parmaMap.put("goodsStatus", DictionaryConst.GoodsStatus.NEW_GROUNDING);
                //该商品处于上架且库存足够才可使用
                parmaMap.put("stockNum_gt", 0);
                parmaMap.put("attr_id", attrId);
                List<Map<String, Object>> configureList = confiGureModelMapper.getProductListLeftJoinMchDynamic(parmaMap);
                for (Map<String, Object> configureMap : configureList) {
                    //库存id
                    int goodsId = Integer.parseInt(configureMap.get("goodsId").toString());
                    //规格id
                    int attrbuiteId = Integer.parseInt(configureMap.get("attr_id").toString());
                    //商品图片处理
                    String imgUrl = configureMap.get("imgurl").toString();
                    imgUrl = publiceService.getImgPath(imgUrl, storeId);
                    map.put("imgUrl", imgUrl);
                    map.put("product_title", configureMap.get("product_title").toString());
                    //json_encode(array(array('pid' => $res_1[0]->id), array('cid' => $res_1[0]->attr_id), array('num' => 1)));
                    List<Map<String, Object>> fatherList = new ArrayList<>();
                    Map<String, Object> goodsMap = new HashMap<>(16);
                    goodsMap.put("pid", goodsId);
                    Map<String, Object> attrMap = new HashMap<>(16);
                    attrMap.put("cid", attrbuiteId);
                    Map<String, Object> stockMap = new HashMap<>(16);
                    stockMap.put("num", 1);
                    fatherList.add(goodsMap);
                    fatherList.add(attrMap);
                    fatherList.add(stockMap);
                    map.put("order_list", JSON.toJSONString(fatherList));
                    map.put("activity_type", 5);
                    String txt = "1、本券可用于购买会员特惠商品，也可用于正价商品，不能与其它优惠一起使用\\n2、本券一次使用一张，自领取日起有效期%s天";
                    map.put("Instructions", String.format(txt, validDay));
                    map.put("expiry_time", endTime);

                    if (isUse == 1) {
                        map.put("point", "已使用");
                        map.put("type", 2);
                        useList.add(map);
                    } else {
                        //未使用
                        if (!DateUtil.dateCompare(DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMDHMS), endTime)) {
                            map.put("point", "去兑换");
                            map.put("type", 0);
                            toUseList.add(map);
                        } else {
                            map.put("point", "已过期");
                            map.put("type", 3);
                            expiredUseList.add(map);
                        }
                    }
                }
            }

            List<Map<String, Object>> dataList = new ArrayList<>();
            if (type.equals(0)) {
                dataList = toUseList;
            } else if (type.equals(1)) {
                dataList = useList;
            } else if (type.equals(2)) {
                dataList = expiredUseList;
            }

            resultMap.put("list", dataList);
            //未使用
            resultMap.put("wsy_num", toUseList.size());
            //已使用
            resultMap.put("ysy_num", useList.size());
            //已过期
            resultMap.put("ygq_num", expiredUseList.size());
        } catch (Exception e) {
            logger.error("获取用户优惠卷 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mycoupon");
        }
        return resultMap;
    }


    /**
     * 【平台】优惠券 条件删选
     *
     * @param activeType
     * @param type
     * @param shopSubtotal
     * @param zMoney
     * @param money
     * @param userCoupon
     * @param productId1
     * @param productIdes
     * @param productClasses
     * @param productClassId
     * @return
     */
    private Map<String, Object> screenCouponList(String activeType, int type, BigDecimal shopSubtotal, BigDecimal zMoney, BigDecimal money, Map<String, Object> userCoupon, String productId1, List<String> productIdes, List<String> productClasses, String productClassId) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            if (CouponActivityModel.USE_RANG_TYPE_ALL == type) {
                // 全部商品
                if (BigDecimal.ZERO.compareTo(zMoney) == 0) {
                    if (CouponModal.COUPON_TYPE_ZK.equals(activeType)) {
                        userCoupon.put("money", shopSubtotal);
                    }
                    resultMap = userCoupon;
                } else {
                    //满减/免邮门槛
                    if (shopSubtotal.compareTo(zMoney) >= 0) {
                        // 商品总价 >= 优惠券满多少
                        resultMap = userCoupon;
                    }
                }
            } else if (CouponActivityModel.USE_RANG_TYPE_PRODUCT == type) {
                // (优惠券指定商品ID,购买商品ID)
                String productId1s = getUnserText(productId1);
                // 商品状态 1:代表购买商品 符合 优惠券指定 商品
                int productStatus = 1;
                //  平台的优惠券必须要满足所有的条件才能使用
                for (String pid : productIdes) {
                    if (!productId1s.contains(pid + SplitUtils.DH)) {
                        // 商品状态 0:代表购买商品 不符合 优惠券指定 商品
                        productStatus = 0;
                        break;
                    }
                }
                if (productStatus != 0) {
                    // 符合
                    if (BigDecimal.ZERO.compareTo(zMoney) == 0) {
                        if (shopSubtotal.compareTo(money) > 0) {
                            // 优惠券金额
                            userCoupon.put("money", money);
                        } else {
                            userCoupon.put("money", shopSubtotal);
                        }
                        resultMap = userCoupon;
                    } else {
                        //满减/免邮门槛
                        if (shopSubtotal.compareTo(zMoney) >= 0) {
                            // 商品总价 >= 优惠券满多少
                            resultMap = userCoupon;
                        }
                    }
                }
            } else if (CouponActivityModel.USE_RANG_TYPE_CLASS == type) {

                String tmpProductClassId = getUnserText(productClassId);
                // 商品属于优惠券指定的分类
                // 必须要所有的分类都满足才能使用优惠券
                int calssStatus = 1;

                for (String productClass : productClasses) {
                    if (!tmpProductClassId.contains(productClass + SplitUtils.DH)) {
                        calssStatus = 0;
                        break;
                    }
                }

                if (calssStatus != 0) {
                    // 符合
                    if (BigDecimal.ZERO.compareTo(zMoney) == 0) {
                        if (shopSubtotal.compareTo(money) > 0) {
                            // 优惠券金额
                            userCoupon.put("money", money);
                        } else {
                            userCoupon.put("money", shopSubtotal);
                        }
                        resultMap = userCoupon;
                    } else {
                        //满减/免邮门槛
                        if (shopSubtotal.compareTo(zMoney) >= 0) {
                            // 商品总价 >= 优惠券满多少
                            resultMap = userCoupon;
                        }
                    }
                }
            }
            return resultMap;
        } catch (Exception e) {
            logger.error("【平台】优惠券筛选失败 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.COUPON_PLAFORM_SCREENING_ERROR, "【平台】优惠券筛选失败", "screenCouponList");
        }
    }

    /**
     * 优惠券是否绑定
     *
     * @param storeId
     * @param time
     * @param map
     */
    private void unUseCoupons(int storeId, Date time, Map map) {
        try {
            map.put("type", new Integer(1));
            //使用中的优惠券
            List<Map<String, Object>> usersCoupons = couponModelMapper.getUsersCoupons(map);
            if (!CollectionUtils.isEmpty(usersCoupons)) {
                // 存在 使用中 的优惠券
                for (Map<String, Object> userCoupon : usersCoupons) {
                    // 循环判断 优惠券是否绑定
                    // 优惠券id
                    int tmpCouponId = DataUtils.getIntegerVal(userCoupon, "id");
                    // 优惠券到期时间
                    Date expiryTime = (Date) userCoupon.get("expiry_time");
                    // 根据优惠券id,查询订单表(查看优惠券是否绑定)
                    Example example = new Example(OrderModel.class);
                    Example.Criteria criteria = example.createCriteria();
                    criteria.andEqualTo("store_id", storeId);
                    criteria.andEqualTo("coupon_id", tmpCouponId);
                    criteria.andCondition(" status not in(6,7) ");
                    OrderModel orderModel = orderModelMapper.selectOneByExample(example);
                    if (orderModel == null) {
                        // 没有数据,表示优惠券没绑定
                        int type = 0;
                        if (expiryTime.before(time)) {
                            // 当前时间 >= 优惠券到期时间 0:未使用 1:使用中 2:已使用 3:已过期
                            type = 3;
                        }
                        //影响行数
                        couponModelMapper.updateCoupon(type, storeId, null, tmpCouponId);
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw e;
        }
    }


    /**
     * 更新优惠券状态 返回0失败1成功 coupon-update_coupon
     *
     * @param storeId
     * @param userId
     * @param couponIds
     * @param type
     * @return
     */
    @Override
    public int updateCoupons(int storeId, String userId, String couponIds, int type) {
        try {
            // 优惠券ID数组
            String[] couponIdList = couponIds.split(SplitUtils.DH);
            for (String couponIdStr : couponIdList) {
                int couponId = Integer.parseInt(couponIdStr);
                if (couponId != 0) {
                    int row = couponModelMapper.updateCoupon(type, storeId, userId, couponId);
                    if (row < 0) {
                        return 0;
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return 0;
        }
        return 1;
    }


    /**
     * 优惠券关联订单 返回0 表示关联失败 1 表示成功 添加关联订单信息 ： coupon_sno
     *
     * @param storeId
     * @param userId
     * @param couponIds
     * @param sNo
     * @param type
     * @return
     */
    @Override
    public int couponWithOrder(int storeId, String userId, String couponIds, String sNo, String type) {
        try {
            // 优惠券ID数组
            String[] couponIdsArray = couponIds.split(SplitUtils.DH);
            for (String couponIdStr : couponIdsArray) {
                int couponId = Integer.parseInt(couponIdStr);
                if (couponId != 0) {
                    if ("add".equals(type)) {
                        CouponOrderModal couponOrderModal = new CouponOrderModal();
                        couponOrderModal.setStore_id(storeId);
                        couponOrderModal.setUser_id(userId);
                        couponOrderModal.setCoupon_id(couponId);
                        couponOrderModal.setsNo(sNo);
                        couponOrderModal.setRecycle(0);
                        couponOrderModal.setAdd_date(new Date());
                        int arow = couponOrderModalMapper.insertSelective(couponOrderModal);
                        if (arow <= 0) {
                            return 0;
                        }
                    } else {
                        int r0 = couponOrderModalMapper.updateCouponOrder(storeId, userId, couponId);
                        if (r0 <= 0) {
                            return 0;
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("优惠券关联订单 异常", e);
            return 0;
        }
        return 1;
    }

    @Override
    public String receive(int storeId, String userId, int id) throws LaiKeApiException {
        String resultStr;
        try {
            //获取优惠卷活动
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setId(id);
            couponActivityModel.setStore_id(storeId);
            couponActivityModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            couponActivityModel = couponActivityModelMapper.selectOne(couponActivityModel);
            if (couponActivityModel != null) {
                //获取用户领取当前活动优惠卷数量
                CouponModal couponModal = new CouponModal();
                couponModal.setStore_id(storeId);
                couponModal.setUser_id(userId);
                couponModal.setHid(id);
                int num = couponModelMapper.selectCount(couponModal);
                //判断是否超过了限制
                if (couponActivityModel.getReceive() == 0 || couponActivityModel.getReceive() > num) {
                    //是否启用
                    if (CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN == couponActivityModel.getStatus()) {
                        //是否还有优惠卷
                        if (couponActivityModel.getNum() > 0) {
                            //扣取活动优惠卷数量
                            int count = couponActivityModelMapper.updateCouponByNum(id, 1);
                            if (count < 1) {
                                logger.debug(String.format("会员【%s】领取优惠卷 id[%s] 时,修改优惠卷数量-1失败", userId, id));
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "receive");
                            }
                            //添加用户优惠卷
                            CouponModal coupon = new CouponModal();
                            coupon.setHid(id);
                            coupon.setStore_id(storeId);
                            coupon.setMch_id(couponActivityModel.getMch_id());
                            coupon.setUser_id(userId);
                            coupon.setExpiry_time(couponActivityModel.getEnd_time());
                            coupon.setAdd_time(new Date());
                            count = couponModelMapper.insertSelective(coupon);
                            if (count < 1) {
                                logger.debug(String.format("会员【%s】领取优惠卷 id[%s] 时,添加用户优惠卷失败", userId, id));
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "receive");
                            }
                            resultStr = String.format("您领取了%s优惠卷!", couponActivityModel.getName());
                            logger.debug(String.format("会员【%s】领取了优惠卷 id[%s] ", userId, id));
                        } else {
                            resultStr = "您来晚了";
                            logger.debug(String.format("会员【%s】领取优惠卷 id[%s] 时,活动已结束", userId, id));
                        }
                    } else {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "该优惠卷还未启动", "receive");
                    }
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "您已经领取过了", "receive");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "优惠卷不存在", "receive");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("领取优惠卷 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "receive");
        }
        return resultStr;
    }

    @Override
    public Map<String, Object> getUser(int storeId, int hid, Integer grade, String name, PageModel pageModel) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //获取活动信息
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setId(hid);
            couponActivityModel = couponActivityModelMapper.selectByPrimaryKey(hid);
            if (couponActivityModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "活动不存在");
            }
            //获取用户优惠卷信息
            CouponModal couponModal = new CouponModal();
            couponModal.setHid(hid);
            couponModal.setStore_id(storeId);
            couponModal.setFree_or_not(CouponModal.COUPON_GIVE);
            List<CouponModal> couponModalList = couponModelMapper.select(couponModal);
            List<String> userIdList = new ArrayList<>();
            for (CouponModal coupon : couponModalList) {
                userIdList.add(coupon.getUser_id());
            }
            //获取会员列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("notIdList", userIdList);
            parmaMap.put("grade", grade);
            parmaMap.put("user_name1", name);
            parmaMap.put("Register_data_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", pageModel.getPageNo());
            parmaMap.put("pageEnd", pageModel.getPageSize());
            int total = userBaseMapper.countDynamic(parmaMap);
            List<Map<String, Object>> userListMap = userBaseMapper.selectDynamic(parmaMap);
            for (Map<String, Object> map : userListMap) {
                UserGradeModel userGradeModel = userGradeModelMapper.selectByPrimaryKey(map.get("grade"));
                String gradeName = "普通会员";
                if (userGradeModel != null) {
                    gradeName = userGradeModel.getName();
                }
                map.put("user_name", map.get("user_name"));
                map.put("grade_name", gradeName);
            }

            resultMap.put("total", total);
            resultMap.put("userList", userListMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取会员列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getUser");
        }

        return resultMap;
    }

    @Override
    public List<Map<String, Object>> splitOrder(int storeId, String userId, String sNo) {
        List<Map<String, Object>> resultList = new ArrayList<>();
        Map<String, Object> retMap = new HashMap<>();
        try {

            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(storeId);
            orderModel.setsNo(sNo);
            orderModel.setUser_id(userId);
            orderModel = orderModelMapper.selectOne(orderModel);

            if (orderModel != null) {
                // 店铺ID字符串
                String mchId = orderModel.getMch_id();
                // 优惠券ID
                String couponIdStr = orderModel.getCoupon_id();
                // 会员折扣
                BigDecimal gradeRate = orderModel.getGrade_rate();
                // 去掉字符串第一个字符和最后一个字符
                mchId = com.laiketui.root.utils.tool.StringUtils.trim(mchId, SplitUtils.DH);
                // 店铺id字符串
                List<String> shopIds = Splitter.on(SplitUtils.DH).splitToList(mchId);
                // 跨店铺
                if (!CollectionUtils.isEmpty(shopIds) && shopIds.size() > 1) {
                    if (!StringUtils.equals(couponIdStr, "0") && StringUtils.isNotEmpty(couponIdStr)) {
                        // 优惠券ID数组
                        List<String> couponIdList = Splitter.on(SplitUtils.DH).splitToList(couponIdStr);
                        // 最后一个优惠券ID健名
                        int couponIdPos = couponIdList.size() - 1;
                        // 最后一个优惠券ID(平台发行优惠券)
                        Integer platformCouponId = Integer.valueOf(couponIdList.get(couponIdPos));
                        // 小计之和
                        BigDecimal sumOfSubtotal = BigDecimal.ZERO;
                        int pos = 0;
                        for (String couponIdVal : couponIdList) {
                            int couponId = Integer.parseInt(couponIdVal);
                            // 优惠金额
                            BigDecimal preferentialAmount = BigDecimal.ZERO;
                            // 店铺商品总价
                            BigDecimal mchSpzPrice = BigDecimal.ZERO;
                            // 当前健名 != 最后一个优惠券ID健名 （店铺发行优惠券）
                            if (couponIdPos != pos) {
                                if (couponId != 0) {
                                    // 根据商城ID、用户ID、店铺优惠券ID，查询优惠券信息
                                    List<Map<String, Object>> couponsInfoList = couponModelMapper.getCouponsInfoList(storeId, userId, couponId);
                                    if (!CollectionUtils.isEmpty(couponsInfoList)) {
                                        Map<String, Object> couponsInfoMap = couponsInfoList.get(0);
                                        // 店铺ID
                                        int mchIdTmp = DataUtils.getIntegerVal(couponsInfoMap, "mchId");
                                        // 优惠券类型
                                        int activity_type = DataUtils.getIntegerVal(couponsInfoMap, "activity_type");
                                        // 优惠券类型 1免邮 2满减 3折扣 4会员赠送
                                        String type = DataUtils.getStringVal(couponsInfoMap, "type");
                                        // 面值
                                        BigDecimal money = DataUtils.getBigDecimalVal(couponsInfoMap, "money");
                                        // 折扣值
                                        BigDecimal discount = DataUtils.getBigDecimalVal(couponsInfoMap, "discount");
                                        // 分类id
                                        String productClassId = DataUtils.getStringVal(couponsInfoMap, "productClassId");
                                        // 商品id
                                        String productId1 = DataUtils.getStringVal(couponsInfoMap, "product_id");
                                        // 去除字符串最后一个逗号
                                        productId1 = com.laiketui.root.utils.tool.StringUtils.rtrim(SerializePhpUtils.getUnserializeString(productId1), SplitUtils.DH);
                                        // 字符串转数组
                                        List<String> productIdList = Splitter.on(SplitUtils.DH).splitToList(productId1);
                                        productClassId = com.laiketui.root.utils.tool.StringUtils.rtrim(SerializePhpUtils.getUnserializeString(productClassId), SplitUtils.DH);
                                        // 字符串转数组
                                        List<String> product_class_list = Splitter.on(SplitUtils.DH).splitToList(productId1);
                                        // 符合优惠券的商品总价
                                        BigDecimal accordWith = BigDecimal.ZERO;
                                        // 根据商城ID、订单号、店铺ID，查询订单详情
                                        List<Map<String, Object>> orderDetails4CouponList = orderModelMapper.getOrderDetails(storeId, sNo, mchIdTmp);
                                        if (!CollectionUtils.isEmpty(orderDetails4CouponList)) {
                                            for (Map<String, Object> orderDetailsInfo : orderDetails4CouponList) {
                                                BigDecimal pPrice = DataUtils.getBigDecimalVal(orderDetailsInfo, "pPrice");
                                                BigDecimal num = DataUtils.getBigDecimalVal(orderDetailsInfo, "num");
                                                String pId = DataUtils.getStringVal(orderDetailsInfo, "pId");
                                                String productClass = DataUtils.getStringVal(orderDetailsInfo, "productClass");

                                                BigDecimal multiply = pPrice.multiply(gradeRate).multiply(num);

                                                if (CouponModal.COUPON_TYPE_MY.equals(type)) {
                                                    // 符合优惠券的商品总价
                                                    accordWith = accordWith.add(multiply);
                                                } else if (CouponModal.COUPON_TYPE_MJ.equals(type)) {
                                                    if (productIdList.contains(pId)) {
                                                        // 符合优惠券的商品总价
                                                        accordWith = accordWith.add(multiply);
                                                    }
                                                } else if (CouponModal.COUPON_TYPE_MJ.equals(type)) {
                                                    for (String product_class_tmp : product_class_list) {
                                                        if (productClass.indexOf(product_class_tmp) > -1) {
                                                            // 符合优惠券的商品总价
                                                            accordWith = accordWith.add(multiply);
                                                        }
                                                    }
                                                }
                                                // 符合优惠券的商品总价
                                                mchSpzPrice = mchSpzPrice.add(multiply);
                                            }
                                        }

                                        // 打完折扣后的金额
                                        BigDecimal zongMoney = accordWith.multiply(discount).divide(BigDecimal.TEN);
                                        // 不是打折优惠
                                        BigDecimal zongMoney1 = BigDecimal.ZERO;
                                        if (zongMoney.compareTo(BigDecimal.ZERO) == 1) {
                                            // 打折优惠
                                            zongMoney1 = accordWith.subtract(zongMoney);
                                        }
                                        if (CouponModal.COUPON_TYPE_MJ.equals(activity_type)) {
                                            // 满减券 // 店铺优惠金额
                                            preferentialAmount = money;
                                        } else if (CouponModal.COUPON_TYPE_ZK.equals(activity_type)) {
                                            // 折扣券 // 店铺优惠金额
                                            preferentialAmount = zongMoney1;
                                        }
                                    }
                                } else {
                                    mchId = shopIds.get(pos);
                                    List<Map<String, Object>> orderDetails4CouponList = orderModelMapper.getOrderDetails(storeId, sNo, Integer.parseInt(mchId));
                                    if (!CollectionUtils.isEmpty(orderDetails4CouponList)) {
                                        for (Map<String, Object> orderDetailsInfo : orderDetails4CouponList) {
                                            BigDecimal pPrice = DataUtils.getBigDecimalVal(orderDetailsInfo, "pPrice");
                                            BigDecimal num = DataUtils.getBigDecimalVal(orderDetailsInfo, "num");
                                            // 符合优惠券的商品总价
                                            mchSpzPrice = mchSpzPrice.add(pPrice.multiply(gradeRate).multiply(num));
                                        }
                                    }
                                }
                                // 优惠ID
                                retMap.put("couponIdStr", couponIdVal);
                                // 店铺ID
                                retMap.put("mchId", mchId);
                                // 店铺优惠金额
                                retMap.put("preferentialAmount", preferentialAmount);
                                // 店铺小计
                                BigDecimal subtotal = mchSpzPrice.subtract(preferentialAmount);
                                retMap.put("subtotal", subtotal);
                                // 小计之和
                                sumOfSubtotal = sumOfSubtotal.add(subtotal);
                            }
                            resultList.add(retMap);
                            pos++;
                        }

                        //平台优惠
                        BigDecimal platformPreferentialAmount = BigDecimal.ZERO;
                        if (platformCouponId != 0) {
                            // 根据商城ID、用户ID、平台优惠券ID，查询优惠券信息
                            List<Map<String, Object>> couponsInfoList = couponModelMapper.getCouponsInfoList(storeId, userId, platformCouponId);
                            Map<String, Object> platformCouponInfoMap = null;
                            if (!CollectionUtils.isEmpty(couponsInfoList)) {
                                platformCouponInfoMap = couponsInfoList.get(0);
                                // 优惠券类型
                                int activityType = DataUtils.getIntegerVal(platformCouponInfoMap, "activityType");
                                // 面值
                                BigDecimal money = DataUtils.getBigDecimalVal(platformCouponInfoMap, "money");
                                // 折扣值
                                BigDecimal discount = DataUtils.getBigDecimalVal(platformCouponInfoMap, "discount");
                                // 打完折扣后的金额
                                BigDecimal zongMoney = sumOfSubtotal.multiply(discount).multiply(new BigDecimal("100"));
                                // 优惠金额
                                BigDecimal zongMoney1 = BigDecimal.ZERO;
                                // 不是打折优惠
                                if (zongMoney.compareTo(BigDecimal.ZERO) == 1) {
                                    zongMoney1 = sumOfSubtotal.subtract(zongMoney);
                                }
                                if (CouponModal.COUPON_TYPE_MJ.equals(activityType)) {
                                    // 满减券
                                    platformPreferentialAmount = money;
                                } else if (CouponModal.COUPON_TYPE_ZK.equals(activityType)) {
                                    // 折扣券
                                    platformPreferentialAmount = zongMoney1;
                                }
                            }
                        }

                        for (Map<String, Object> retMapTmp : resultList) {
                            String couponIdStrTmp = DataUtils.getStringVal(retMapTmp, "subtotal");
                            retMapTmp.put("couponIdStr", couponIdStrTmp + SplitUtils.DH + platformCouponId);
                            retMapTmp.put("mah_id", retMapTmp.get("mchId"));
                            retMapTmp.put("mah_coupon_amount", retMapTmp.get("preferential_amount"));

                            //订单优惠金额 = 店铺优惠金额+(店铺小计/小计之和*平台优化金额)
                            BigDecimal preferential_amount = DataUtils.getBigDecimalVal(retMapTmp, "preferential_amount");
                            BigDecimal subtotal = DataUtils.getBigDecimalVal(retMapTmp, "subtotal");
                            BigDecimal val = preferential_amount.add(subtotal.divide(sumOfSubtotal).multiply(platformPreferentialAmount));
                            retMapTmp.put("preferential_amount", val);
                        }
                    }
                }
            }
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ORDER_COUPON_CF_ERROR, "优惠券拆分订单失败", "splitOrder");
        }
    }

    @Override
    public List<Map<String, Object>> mobileTerminalCouponCenter(int storeId, String userId, Integer type, PageModel pageModel) throws LaiKeApiException {
        List<Map<String, Object>> dataList;
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("status", CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
            parmaMap.put("start_time_sort", DataUtils.Sort.DESC.toString());
            //查询该商城所有未开启的优惠券
            List<Map<String, Object>> couponActivityList = couponActivityModelMapper.getCouponActivityDynamic(parmaMap);
            for (Map<String, Object> map : couponActivityList) {
                //优惠卷id
                int id = Integer.parseInt(map.get("id").toString());
                //活动开始时间
                Date startTime = (Date) map.get("start_time");
                if (!DateUtil.dateCompare(new Date(), startTime)) {
                    //启用活动
                    CouponActivityModel couponActivityModel = new CouponActivityModel();
                    couponActivityModel.setId(id);
                    couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
                    int count = couponActivityModelMapper.updateByPrimaryKeySelective(couponActivityModel);
                    if (count < 1) {
                        logger.info("活动启动用失败 参数:" + JSON.toJSONString(couponActivityModel));
                    } else {
                        logger.debug("活动启动用成功 参数:" + JSON.toJSONString(couponActivityModel));
                    }
                }
            }

            parmaMap.clear();
            parmaMap.put("store_id", storeId);
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            parmaMap.put("status", CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
            parmaMap.put("activity_type_4", "!4");
            parmaMap.put("is_display", 1);
            parmaMap.put("start_time_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("page", pageModel.getPageNo());
            parmaMap.put("pageSize", pageModel.getPageSize());
            if (type == 1) {
                parmaMap.put("mch_id", 0);
            } else {
                //店铺id集
                List<Integer> mchIdList = new ArrayList<>();
                //获取所有已审核的店铺信息
                MchModel mchModel = new MchModel();
                mchModel.setReview_status(DictionaryConst.MchExameStatus.EXAME_PASS_STATUS.toString());
                List<MchModel> mchModelList = mchModelMapper.select(mchModel);
                for (MchModel mch : mchModelList) {
                    mchIdList.add(mch.getId());
                }
                parmaMap.put("mchList", mchIdList);
            }
            dataList = couponActivityModelMapper.getCouponActivityDynamic(parmaMap);
            couponCenter(storeId, userId, dataList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("移动端-领券中心 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mobileTerminalCouponCenter");
        }
        return dataList;
    }

    @Override
    public List<Map<String, Object>> proCoupon(int storeId, String userId, int goodsId) throws LaiKeApiException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
            //获取商品信息
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setId(goodsId);
            productListModel = productListModelMapper.selectOne(productListModel);
            if (productListModel != null) {
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("store_id", storeId);
                parmaMap.put("status", CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
                parmaMap.put("mch_id", productListModel.getMch_id());
                parmaMap.put("start_time_sort", DataUtils.Sort.DESC.toString());
                //根据商品所属店铺获取活动信息
                List<Map<String, Object>> resultList = couponActivityModelMapper.getCouponActivityDynamic(parmaMap);
                for (Map<String, Object> map : resultList) {
                    //优惠卷活动id
                    int activityId = Integer.parseInt(map.get("id").toString());
                    //优惠卷类型
                    int activityType = Integer.parseInt(map.get("activity_type").toString());
                    //满减金额
                    BigDecimal fullReductionAmt = new BigDecimal(map.get("z_money").toString());
                    //领取限制
                    int receiveNum = StringUtils.stringParseInt(map.get("z_money") + "");
                    //优惠卷使用类型
                    int type = StringUtils.stringParseInt(map.get("type") + "");
                    //剩余数量
                    int surplusNum = Integer.parseInt(map.get("num").toString());
                    //指定商品id集
                    String goodsIdSerialize = map.get("product_id") + "";
                    //指定商品类型id集
                    String goodsTypeSerialize = map.get("product_class_id") + "";
                    //活动开始时间
                    Date startDate = (Date) map.get("start_time");
                    //活动结束时间
                    Date endDate = (Date) map.get("end_time");
                    map.put("startDate", DateUtil.dateFormate(startDate, GloabConst.TimePattern.YMD3));
                    map.put("endDate", DateUtil.dateFormate(endDate, GloabConst.TimePattern.YMD3));

                    String limitStr = "无限制";
                    if (fullReductionAmt.floatValue() > 0) {
                        limitStr = String.format("满%s可用", fullReductionAmt);
                    }
                    map.put("limit", limitStr);

                    if (!DateUtil.dateCompare(new Date(), endDate)) {
                        CouponActivityModel couponActivityModel = new CouponActivityModel();
                        couponActivityModel.setId(activityId);
                        couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_END);
                        //修改优惠卷状态
                        int count = couponActivityModelMapper.updateSwitchCouponActivity(couponActivityModel);
                        if (count < 1) {
                            logger.debug("优惠卷状态修改失败 id:" + couponActivityModel.getId());
                        }
                    } else {
                        String point;
                        int pointType;
                        if (StringUtils.isEmpty(userId)) {
                            point = "立即领取";
                            pointType = 1;
                        } else {
                            //获取用户领取该取活动优惠卷的数量
                            CouponModal couponModal = new CouponModal();
                            couponModal.setStore_id(storeId);
                            couponModal.setUser_id(userId);
                            couponModal.setHid(activityId);
                            int userNum = couponModelMapper.selectCount(couponModal);
                            if (userNum > 0) {
                                //已领取
                                if (receiveNum > userNum) {
                                    if (surplusNum > 0) {
                                        point = "立即领取";
                                        pointType = 1;
                                    } else {
                                        point = "可用商品";
                                        pointType = 2;
                                    }
                                } else {
                                    point = "可用商品";
                                    pointType = 2;
                                }
                            } else {
                                if (surplusNum > 0) {
                                    point = "立即领取";
                                    pointType = 1;
                                } else {
                                    point = "已抢光";
                                    pointType = 3;
                                }
                            }
                        }
                        map.put("point", point);
                        map.put("point_type", pointType);
                    }

                    //非会员赠送
                    int pointType = StringUtils.stringParseInt(map.get("point_type") + "");
                    if (!CouponModal.COUPON_TYPE_HYZS.equals(activityType + "")) {
                        if (type == CouponActivityModel.USE_RANG_TYPE_ALL) {
                            if (pointType != 3) {
                                dataList.add(map);
                            }
                        } else if (type == CouponActivityModel.USE_RANG_TYPE_PRODUCT) {
                            //s:15:"757,906,907,909";
                            String goodsIds = SerializePhpUtils.getUnserializeByBasic(goodsIdSerialize, String.class);
                            if (goodsIds != null) {
                                List<String> goodsIdList = Arrays.asList(goodsIds.split(","));
                                //当前商品是否包含在指定商品中
                                if (goodsIdList.contains(goodsId + "")) {
                                    if (pointType != 3) {
                                        dataList.add(map);
                                    }
                                }
                            }
                        } else if (type == CouponActivityModel.USE_RANG_TYPE_CLASS) {
                            //s:8:"8,58,394";
                            String goodsTypes = SerializePhpUtils.getUnserializeByBasic(goodsTypeSerialize, String.class);
                            if (goodsTypes != null) {
                                //优惠卷所属的商品类别集id
                                Set<String> goodsClassIdList = new HashSet<>(Arrays.asList(goodsTypes.split(",")));
                                //当前商品类别集
                                List<String> classIdList = Arrays.asList(StringUtils.trim(productListModel.getProduct_class(), "-").split("-"));
                                //当前商品类别是否包含在指定商品中
                                for (String goodsClassId : goodsClassIdList) {
                                    if (classIdList.contains(goodsClassId)) {
                                        if (pointType != 3) {
                                            dataList.add(map);
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品不存在", "proCoupon");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("移动端获取商品可用优惠券活动 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "proCoupon");
        }
        return dataList;
    }

    @Override
    public List<Map<String, Object>> pcCoupon(int storeId, String userId, Integer type) throws LaiKeApiException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setStore_id(storeId);
            couponActivityModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_NOT_USE);
            couponActivityModel.setIs_display(1);
            List<CouponActivityModel> couponActivityModelList = couponActivityModelMapper.select(couponActivityModel);
            for (CouponActivityModel couponActivity : couponActivityModelList) {
                //如果活动未启动,则看是否到了启动的时间
                if (!DateUtil.dateCompare(new Date(), couponActivity.getStart_time())) {
                    //开启活动
                    CouponActivityModel couponActivityUpdate = new CouponActivityModel();
                    couponActivityUpdate.setId(couponActivity.getId());
                    couponActivityUpdate.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
                    couponActivityModelMapper.updateByPrimaryKeySelective(couponActivityUpdate);
                }
            }
            List<Integer> mchIdList = mchModelMapper.getStoreMchIdList(storeId);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("activity_type_4", "activity_type_4");
            parmaMap.put("is_display", 1);
            parmaMap.put("mchIdList", mchIdList);
            parmaMap.put("status", CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
            parmaMap.put("start_time_sort", "desc");
            parmaMap.put("page", 0);
            parmaMap.put("pageSize", 8);
            List<Map<String, Object>> couponList = couponActivityModelMapper.getCouponActivityDynamic(parmaMap);

            dataList = couponData(storeId, 0, userId, type, couponList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PC商城-领券中心 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "pcCoupon");
        }

        return dataList;
    }

    @Override
    public List<Map<String, Object>> mchCoupon(int storeId, String userId, int mchId, int type) throws LaiKeApiException {
        List<Map<String, Object>> dataList = new ArrayList<>();
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("status", CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
            parmaMap.put("mch_id", mchId);
            parmaMap.put("start_time_sort", "desc");
            //根据商品所属店铺获取活动信息
            List<Map<String, Object>> resultList = couponActivityModelMapper.getCouponActivityDynamic(parmaMap);
            dataList = couponData(storeId, mchId, userId, type, resultList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("PC端-店铺获取商品可用优惠券活动 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchCoupon");
        }
        return dataList;
    }

    /**
     * 优惠卷数据处理
     *
     * @param resultList -
     * @author Trick
     * @date 2021/6/17 17:07
     */
    private List<Map<String, Object>> couponData(int storeId, Integer mchId, String userId, int type, List<Map<String, Object>> resultList) {
        List<Map<String, Object>> dataList = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            //优惠卷活动id
            int activityId = Integer.parseInt(map.get("id").toString());
            //优惠卷类型
            int activityType = Integer.parseInt(map.get("activity_type").toString());
            //满减金额
            BigDecimal fullReductionAmt = new BigDecimal(map.get("z_money").toString());
            //领取限制
            int receive = Integer.parseInt(map.get("receive").toString());
            //剩余数量
            int surplusNum = Integer.parseInt(map.get("num").toString());
            //活动结束时间
            Date endDate = (Date) map.get("end_time");
            //活动开始时间
            Date startDate = (Date) map.get("start_time");
            map.put("start_time", DateUtil.dateFormate(startDate, GloabConst.TimePattern.YMD3));
            map.put("end_time", DateUtil.dateFormate(endDate, GloabConst.TimePattern.YMD3));

            //优惠卷名称
            String name = map.get("name").toString();
            if (mchId != 0) {
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(storeId);
                mchModel.setId(mchId);
                mchModel = mchModelMapper.selectOne(mchModel);
                if (mchModel != null) {
                    map.put("coupon_name", String.format("[%s]%s", mchModel.getName(), name));
                }
            }

            //消费限制处理
            String limitStr = "无限制";
            if (fullReductionAmt.floatValue() > 0) {
                limitStr = String.format("满%s可用", fullReductionAmt);
            }
            map.put("limit", limitStr);
            //判断活动是否过期
            if (DateUtil.dateCompare(new Date(), endDate)) {
                //设置活动已过期
                CouponActivityModel couponActivityModel = new CouponActivityModel();
                couponActivityModel.setId(activityId);
                couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_END);
                int count = couponActivityModelMapper.updateByPrimaryKeySelective(couponActivityModel);
                if (count < 1) {
                    logger.info("活动已结束,但是状态修改失败 参数" + JSON.toJSONString(couponActivityModel));
                }
            } else {
                String point = "已抢光";
                int pointType = 3;
                if (surplusNum > 0) {
                    //还又剩余券
                    if (StringUtils.isEmpty(userId)) {
                        if (type == 1) {
                            point = "立即领取";
                        } else {
                            point = "领取";
                        }
                        pointType = 1;
                    } else {
                        //获取当前活动用户领取的优惠卷信息
                        CouponModal couponModal = new CouponModal();
                        couponModal.setUser_id(userId);
                        couponModal.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                        couponModal.setHid(activityId);
                        int receivedNum = couponModelMapper.selectCount(couponModal);
                        if (receivedNum > 0) {
                            //如果已经领取 判断是否超过领取数量限制
                            if (receive > receivedNum) {
                                //继续领取
                                if (type == 1) {
                                    point = "立即领取";
                                } else {
                                    point = "领取";
                                }
                                pointType = 1;
                            } else {
                                //超过领取限制
                                if (type == 1) {
                                    point = "可用商品";
                                } else {
                                    point = "已领取";
                                }
                                pointType = 2;
                            }
                        } else {
                            //未领取
                            if (type == 1) {
                                point = "立即领取";
                            } else {
                                point = "领取";
                            }
                            pointType = 1;
                        }
                    }
                }
                map.put("point", point);
                map.put("point_type", pointType);
            }
            //非会员赠送
            if (!CouponModal.COUPON_TYPE_HYZS.equals(activityType + "")) {
                dataList.add(map);
            }
        }
        return dataList;
    }

    @Override
    public Map<String, Object> storeCoupons(CouponParmaVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //优惠卷类型集
            List<String> couponTypeNameList = new ArrayList<>();
            //数据总数量
            int total;
            int count;

            //获取店铺信息
            MchModel mchModel = new MchModel();
            if (vo.getMchId() != null && vo.getMchId() != 0) {
                mchModel.setStore_id(vo.getStoreId());
                mchModel.setId(vo.getMchId());
                mchModel = mchModelMapper.selectOne(mchModel);
            }
            if (mchModel != null) {
                //获取优惠卷活动信息
                Map<String, Object> parmaMap = new HashMap<>(16);
                parmaMap.put("store_id", vo.getStoreId());
                parmaMap.put("mch_id", vo.getMchId());
                parmaMap.put("add_time_sort", DataUtils.Sort.DESC.toString());
                parmaMap.put("page", vo.getPageNo());
                parmaMap.put("pageSize", vo.getPageSize());
                if (vo.getHid() != null) {
                    parmaMap.put("id", vo.getHid());
                }
                if (!StringUtils.isEmpty(vo.getName())) {
                    parmaMap.put("likeName", vo.getName());
                }
                if (vo.getActivityType() != null && vo.getActivityType() != 0) {
                    parmaMap.put("activity_type", vo.getActivityType());
                }
                if (vo.getStatus() != null && vo.getStatus() != 0) {
                    parmaMap.put("status", vo.getStatus());
                }
                if (vo.getIsOverdue() != null) {
                    if (vo.getIsOverdue() == 0) {
                        parmaMap.put("validate_not_overdue", new Date());
                    } else {
                        parmaMap.put("validate_overdue", new Date());
                    }
                }

                total = couponActivityModelMapper.countCouponActivityDynamic(parmaMap);
                List<Map<String, Object>> resultData = new ArrayList<>();
                if (total > 0) {
                    resultData = couponActivityModelMapper.getCouponActivityDynamic(parmaMap);
                    for (Map<String, Object> map : resultData) {
                        //活动id
                        int id = Integer.parseInt(map.get("id").toString());
                        //优惠卷类型
                        int acType = Integer.parseInt(map.get("activity_type").toString());
                        //优惠卷类型别名
                        String acTypeName = "";
                        //优惠卷使用范围
                        int type = Integer.parseInt(map.get("type").toString());
                        //活动开始时间
                        Date startDate = (Date) map.get("start_time");
                        //活动结束时间
                        Date endDate = (Date) map.get("end_time");
                        //范围类型
                        String typeName = "";
                        if (type == 1) {
                            typeName = "全部商品";
                        } else if (type == 2) {
                            typeName = "指定商品";
                            if (vo.getHid() != null) {
                                //分类数据处理
                                List<Map<String, Object>> goodsIdMaps = null;
                                String goodsIds = SerializePhpUtils.getUnserializeByBasic(MapUtils.getString(map, "product_id"), String.class);
                                if (goodsIds != null) {
                                    List<String> goodsIdList = DataUtils.convertToList(goodsIds.split(SplitUtils.DH));
                                    Map<String, Object> parmaMap1 = new HashMap<>(16);
                                    parmaMap1.put("store_id", vo.getStoreId());
                                    parmaMap1.put("goodsIdList", goodsIdList);
                                    goodsIdMaps = productListModelMapper.selectDynamic(parmaMap1);
                                }
                                map.put("goodsIdList", goodsIdMaps);
                            }
                        } else if (type == 3) {
                            typeName = "指定分类";
                            if (vo.getHid() != null) {
                                //分类数据处理
                                List<Map<String, Object>> classIdMaps = null;
                                String classIds = SerializePhpUtils.getUnserializeByBasic(MapUtils.getString(map, "product_class_id"), String.class);
                                if (classIds != null) {
                                    List<String> classIdList = DataUtils.convertToList(classIds.split(SplitUtils.DH));
                                    Map<String, Object> parmaMap1 = new HashMap<>(16);
                                    parmaMap1.put("store_id", vo.getStoreId());
                                    parmaMap1.put("classIdList", classIdList);
                                    classIdMaps = productClassModelMapper.selectDynamic(parmaMap1);
                                }
                                map.put("classIdList", classIdMaps);
                            }
                        }
                        map.put("type", typeName);
                        CouponActivityModel updateCouponActivityModel = new CouponActivityModel();
                        updateCouponActivityModel.setId(id);
                        //是否已经结束
                        boolean isEnd = false;
                        boolean falg = true;
                        int statusTemp = CouponActivityModel.COUPON_ACTIVITY_STATUS_NOT_USE;
                        if (DateUtil.dateCompare(new Date(), endDate)) {
                            statusTemp = CouponActivityModel.COUPON_ACTIVITY_STATUS_END;
                            isEnd = true;
                        } else if (DateUtil.dateCompare(startDate, new Date())) {
                            //互动是否已开启
                            statusTemp = CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN;
                        } else {
                            falg = false;
                        }
                        map.put("isEnd", isEnd);
                        updateCouponActivityModel.setStatus(statusTemp);
                        if (falg) {
                            count = couponActivityModelMapper.updateByPrimaryKeySelective(updateCouponActivityModel);
                            if (count < 1) {
                                logger.info("修改活动状态,但是状态修改失败 参数" + JSON.toJSONString(updateCouponActivityModel));
                            }
                        }
                        map.put("status", statusTemp);
                        //优惠卷是否被使用
                        int delStatus = 2;
                        parmaMap.clear();
                        parmaMap.put("store_id", vo.getStoreId());
                        parmaMap.put("hid", id);
                        List<Integer> typeList = new ArrayList<>();
                        typeList.add(CouponModal.COUPON_TYPE_NOT_USED);
                        typeList.add(CouponModal.COUPON_TYPE_IN_USE);
                        parmaMap.put("typeList", typeList);
                        count = couponActivityModelMapper.countCouponActivityDynamic(parmaMap);
                        if (count > 0) {
                            //未被使用
                            delStatus = 1;
                        }
                        map.put("del_status", delStatus);

                        map.put("start_time", DateUtil.dateFormate(startDate, GloabConst.TimePattern.YMDHMS));
                        map.put("end_time", DateUtil.dateFormate(endDate, GloabConst.TimePattern.YMDHMS));
                    }
                }
                resultMap.put("coupon_type", couponTypeNameList);
                resultMap.put("list", resultData);
                resultMap.put("total", total);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在", "storeCoupons");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("店铺优惠券列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "storeCoupons");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> addStoreCouponsPage(int storeId, String userId, int mchId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //优惠卷类型集
            List<Map<String, Object>> couponTypeList = new ArrayList<>();

            //获取店铺信息
            MchModel mchModel = new MchModel();
            mchModel.setStore_id(storeId);
            mchModel.setId(mchId);
            mchModel.setUser_id(userId);
            mchModel = mchModelMapper.selectOne(mchModel);
            if (mchModel != null) {
                //获取全局优惠卷配置信息
                CouponConfigModel couponConfigModel = new CouponConfigModel();
                couponConfigModel.setStore_id(storeId);
                couponConfigModel.setMch_id(0);
                couponConfigModel = couponConfigModelMapper.selectOne(couponConfigModel);
                if (couponConfigModel != null && !StringUtils.isEmpty(couponConfigModel.getCoupon_type())) {
                    String[] couponTypes = couponConfigModel.getCoupon_type().split(",");
                    for (String coupon : couponTypes) {
                        Map<String, Object> couponTypeMap = new HashMap<>(16);
                        String name;
                        if (CouponModal.COUPON_TYPE_MJ.equals(coupon)) {
                            name = "满减卷";
                        } else if (CouponModal.COUPON_TYPE_ZK.equals(coupon)) {
                            name = "折扣卷";
                        } else {
                            continue;
                        }
                        couponTypeMap.put("value", coupon);
                        couponTypeMap.put("name", name);
                        couponTypeList.add(couponTypeMap);
                    }
                }
                //限领类型
                int limitType = CouponConfigModel.LIMIT_TYPE_SINGLE;
                if (couponConfigModel != null) {
                    limitType = couponConfigModel.getLimit_type();
                }

                resultMap.put("coupon_type_list", couponTypeList);
                resultMap.put("limit_type", limitType);
                return resultMap;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在", "addStoreCouponsPage");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加店铺优惠卷页面 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addStoreCouponsPage");
        }
    }

    @Override
    public boolean addStoreCoupons(AddCouponActivityVo vo, String userId) throws LaiKeApiException {
        try {
            if (CouponModal.COUPON_TYPE_HYZS.equals(vo.getActivityType() + "")) {
                //会员赠送无限发行量
                vo.setCirculation(99999999);
            }
            CouponActivityModel oldCouponActivityModel = new CouponActivityModel();
            //剩余数量
            int num = vo.getCirculation();
            //是否是修改标识
            boolean isUpdate = false;
            if (vo.getId() != null) {
                isUpdate = true;
                oldCouponActivityModel.setId(vo.getId());
                oldCouponActivityModel = couponActivityModelMapper.selectOne(oldCouponActivityModel);
                if (oldCouponActivityModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "优惠活动不存在", "addStoreCoupons");
                }
                //编辑时 数量必须大于已领取数量
                CouponModal couponModal = new CouponModal();
                couponModal.setStore_id(vo.getStoreId());
                couponModal.setHid(oldCouponActivityModel.getId());
                int receiveNum = couponModelMapper.selectCount(couponModal);
                if (receiveNum >= vo.getCirculation()) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠券发行数量必须大于已领取数量");
                }
                //发行数量-领取数量
                num -= receiveNum;
                if (num < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠券发行数量必须大于已领取数量");
                }
            }

            if (vo.getMchId() != 0) {
                //获取店铺信息
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(vo.getStoreId());
                if (!StringUtils.isEmpty(userId)) {
                    mchModel.setUser_id(userId);
                }
                mchModel.setId(vo.getMchId());
                mchModel = mchModelMapper.selectOne(mchModel);
                if (mchModelMapper.selectCount(mchModel) < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在", "addStoreCoupons");
                }
            }

            if (vo.getActivityType() < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择优惠卷", "addStoreCoupons");
            }
            if (StringUtils.isEmpty(vo.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠卷不能为空", "addStoreCoupons");
            }
            BigDecimal discount = null;
            BigDecimal money = null;
            int count;
            //装载优惠卷数据
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setStore_id(vo.getStoreId());
            couponActivityModel.setName(vo.getName());
            couponActivityModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            couponActivityModel.setMch_id(vo.getMchId());
            couponActivityModel.setGrade_id(0);
            couponActivityModel.setNum(num);
            couponActivityModel.setDay(0);
            //过期时间
            couponActivityModel.setStart_time(DateUtil.dateFormateToDate(vo.getStartTime(), GloabConst.TimePattern.YMDHMS));
            couponActivityModel.setEnd_time(DateUtil.dateFormateToDate(vo.getEndTime(), GloabConst.TimePattern.YMDHMS));

            if (!vo.getName().equals(oldCouponActivityModel.getName())) {
                //优惠卷名称重复校验
                count = couponActivityModelMapper.selectCount(couponActivityModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠券名称已存在", "addStoreCoupons");
                }
            }

            //优惠卷类型 校验流程
            if (CouponModal.COUPON_TYPE_MJ.equals(StringUtils.toString(vo.getActivityType()))) {
                if (vo.getMoney() == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠卷面值不能为空", "addStoreCoupons");
                }
                //验证使用门槛
                if (vo.getZmoney() == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠券使用门槛不能为空", "addStoreCoupons");
                }
                couponActivityModel.setCirculation(vo.getCirculation());
                couponActivityModel.setZ_money(vo.getZmoney());
                discount = BigDecimal.ZERO;
                money = vo.getMoney();
            } else if (CouponModal.COUPON_TYPE_ZK.equals(StringUtils.toString(vo.getActivityType()))) {
                //验证折扣值
                if (vo.getDiscount() == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "折扣值不能为空", "addStoreCoupons");
                }
                couponActivityModel.setCirculation(vo.getCirculation());
                couponActivityModel.setZ_money(vo.getZmoney());
                discount = vo.getDiscount();
                money = BigDecimal.ZERO;
            } else if (CouponModal.COUPON_TYPE_HYZS.equals(StringUtils.toString(vo.getActivityType()))) {
                UserGradeModel userGradeModel = userGradeModelMapper.selectByPrimaryKey(vo.getGradeId());
                if (userGradeModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "等级不存在");
                }
                couponActivityModel.setGrade_id(userGradeModel.getId());
                //如果是会员赠券则过期时间为当前时间+1年
                couponActivityModel.setDay(vo.getDay());
                couponActivityModel.setCirculation(999999999);
                vo.setLimitCount(couponActivityModel.getCirculation());
                couponActivityModel.setNum(couponActivityModel.getCirculation());
                couponActivityModel.setZ_money(vo.getZmoney());
                couponActivityModel.setStart_time(new Date());
                couponActivityModel.setEnd_time(DateUtil.getAddMonth(couponActivityModel.getStart_time(), 12));
                //折扣/满减
                if (vo.getDiscount() == null || vo.getDiscount().compareTo(BigDecimal.ZERO) == 0) {
                    money = vo.getMoney();
                    discount = BigDecimal.ZERO;
                } else {
                    money = BigDecimal.ZERO;
                    discount = vo.getDiscount();
                }
                couponActivityModel.setZ_money(vo.getZmoney());
            } else if (CouponModal.COUPON_TYPE_MY.equals(StringUtils.toString(vo.getActivityType()))) {
                //免邮卷
                couponActivityModel.setCirculation(vo.getCirculation());
                //免邮券门槛
                couponActivityModel.setZ_money(vo.getZmoney());
            }
            //优惠卷使用范围 校验流程
            if (CouponActivityModel.USE_RANG_TYPE_PRODUCT == vo.getType()) {
                if (StringUtils.isEmpty(vo.getMenuList())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择商品", "addStoreCoupons");
                }
                //验证商品
                StringBuilder goodsIdListStr = new StringBuilder();
                String[] goodsNames = vo.getMenuList().split(",");
                for (String goodsName : goodsNames) {
                    ProductListModel productListModel = new ProductListModel();
                    productListModel.setStore_id(vo.getStoreId());
                    productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
                    //以前的逻辑是根据商品名称，现在改成id
                    if (StringUtils.isInteger(goodsName)) {
                        productListModel.setId(Integer.valueOf(goodsName));
                    } else {
                        productListModel.setProduct_title(goodsName);
                    }
                    if (vo.getMchId() != 0) {
                        productListModel.setMch_id(vo.getMchId());
                    }
                    productListModel = productListModelMapper.selectOne(productListModel);
                    if (productListModel != null) {
                        goodsIdListStr.append(productListModel.getId()).append(",");
                    } else {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "列表中有商品不存在", "addStoreCoupons");
                    }
                }
                String goodsIds = StringUtils.trim(goodsIdListStr.toString(), SplitUtils.DH);
                couponActivityModel.setProduct_id(goodsIds);
            } else if (CouponActivityModel.USE_RANG_TYPE_CLASS == vo.getType()) {
                if (StringUtils.isEmpty(vo.getClassList())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择分类", "addStoreCoupons");
                }
                couponActivityModel.setProduct_class_id(vo.getClassList());
            }
            couponActivityModel.setInstructions(vo.getInstructions());
            couponActivityModel.setActivity_type(vo.getActivityType());
            couponActivityModel.setType(vo.getType());
            //获取全局优惠卷活动配置信息
            CouponConfigModel couponConfigModel = new CouponConfigModel();
            couponConfigModel.setStore_id(vo.getStoreId());
            couponConfigModel.setMch_id(0);
            couponConfigModel = couponConfigModelMapper.selectOne(couponConfigModel);
            //领取限制
            couponActivityModel.setReceive(1);
            if (couponConfigModel != null && couponConfigModel.getLimit_type() == 1) {
                if (vo.getLimitCount() == null || vo.getLimitCount() <= 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请输入领取限制");
                }
                couponActivityModel.setReceive(vo.getLimitCount());
            }
            //过期处理
            if (!DateUtil.dateCompare(new Date(), couponActivityModel.getEnd_time())) {
                couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_END);
                logger.info("添加/编辑优惠卷活动时 【活动已结束】 优惠卷名称:{}", vo.getName());
            }
            //校验数据
            couponActivityModel.setDiscount(discount);
            couponActivityModel.setMoney(money);
            couponActivityModel = DataCheckTool.checkCouponActivityData(couponActivityModel);
            //添加/修改数据
            if (isUpdate) {
                couponActivityModel.setId(vo.getId());
                count = couponActivityModelMapper.updateByPrimaryKeySelective(couponActivityModel);
            } else {
                couponActivityModel.setSkip_type(1);
                couponActivityModel.setAdd_time(new Date());
                count = couponActivityModelMapper.insertSelective(couponActivityModel);
            }
            if (count < 1) {
                logger.info("添加/编辑优惠卷 失败 参数:" + JSON.toJSONString(couponActivityModel));
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障", "addStoreCoupons");
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加优惠卷 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addStoreCoupons");
        }
    }


    @Override
    public Map<String, Object> getCouponsInfoById(int storeId, int id) throws LaiKeApiException {
        Map<String, Object> resultMap;
        try {
            //指定商品集
            String goodsNames = null;
            //指定商品类别集
            String goodsClassNames = null;

            //优惠卷类型集[{value:"2",name:"优惠卷类型"}...]
            List<Map<String, String>> couponTypeNameList = new ArrayList<>();
            //获取优惠卷信息
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setId(id);
            couponActivityModel = couponActivityModelMapper.selectOne(couponActivityModel);
            if (couponActivityModel != null) {
                //指定商品处理
                if (!StringUtils.isEmpty(couponActivityModel.getProduct_id())) {
                    String goodsIds = SerializePhpUtils.getUnserializeByBasic(couponActivityModel.getProduct_id(), String.class);
                    if (goodsIds != null) {
                        String[] goodsIdList = goodsIds.split(",");
                        StringBuilder goodsNameStrs = new StringBuilder();
                        for (String goodsId : goodsIdList) {
                            ProductListModel productListModel = new ProductListModel();
                            productListModel.setId(Integer.parseInt(goodsId));
                            productListModel = productListModelMapper.selectOne(productListModel);
                            if (productListModel != null) {
                                goodsNameStrs.append(productListModel.getProduct_title()).append(",");
                            }
                        }
                        goodsNames = StringUtils.trim(goodsNameStrs.toString(), ",");
                    }
                }
                //指定商品类别处理
                if (!StringUtils.isEmpty(couponActivityModel.getProduct_class_id())) {
                    String goodsClassIds = SerializePhpUtils.getUnserializeByBasic(couponActivityModel.getProduct_class_id(), String.class);
                    assert goodsClassIds != null;
                    String[] goodsClassIdList = goodsClassIds.split(",");
                    StringBuilder goodsNameStrs = new StringBuilder();
                    for (String goodsClassId : goodsClassIdList) {
                        ProductClassModel productClassModel = new ProductClassModel();
                        productClassModel.setCid(Integer.parseInt(goodsClassId));
                        productClassModel = productClassModelMapper.selectOne(productClassModel);
                        if (productClassModel != null) {
                            goodsNameStrs.append(productClassModel.getPname()).append(",");
                        }
                    }
                    goodsClassNames = StringUtils.trim(goodsNameStrs.toString(), ",");
                }
                //获取全局优惠卷信息
                CouponConfigModel couponConfigModel = new CouponConfigModel();
                couponConfigModel.setStore_id(storeId);
                couponConfigModel.setMch_id(0);
                couponConfigModel = couponConfigModelMapper.selectOne(couponConfigModel);
                if (couponConfigModel != null) {
                    String couponTypes = couponConfigModel.getCoupon_type();
                    if (!StringUtils.isEmpty(couponTypes)) {
                        String[] couponTypeList = couponTypes.split(",");
                        for (String couponType : couponTypeList) {
                            String typeName;
                            if ("2".equals(couponType)) {
                                typeName = "满减卷";
                            } else if ("3".equals(couponType)) {
                                typeName = "折扣卷";
                            } else {
                                continue;
                            }
                            Map<String, String> couponTypeMap = new HashMap<>(16);
                            couponTypeMap.put("value", couponType);
                            couponTypeMap.put("name", typeName);
                            couponTypeNameList.add(couponTypeMap);
                        }
                    }
                }
                resultMap = JSON.parseObject(JSON.toJSONString(couponActivityModel), new TypeReference<Map<String, Object>>() {
                });
                resultMap.put("limitCount", couponActivityModel.getReceive());
                resultMap.put("start_time", DateUtil.dateFormate(couponActivityModel.getStart_time(), GloabConst.TimePattern.YMDHMS));
                resultMap.put("end_time", DateUtil.dateFormate(couponActivityModel.getEnd_time(), GloabConst.TimePattern.YMDHMS));
                resultMap.put("coupon_type", couponTypeNameList);
                resultMap.put("product_name", goodsNames);
                resultMap.put("product_class_name1", goodsClassNames);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "优惠卷不存在", "getCouponsInfoById");
            }
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取优惠卷信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getCouponsInfoById");
        }
    }

    @Override
    public Map<String, Object> seeCoupon(int storeId, int id, Integer mchId, Integer status, String sNo, String name, String pageTo, PageModel pageModel) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap1 = new HashMap<>(16);
            parmaMap1.put("store_id", storeId);
            parmaMap1.put("status", CouponModal.COUPON_STAUS_OPEN);
            parmaMap1.put("hid", id);
            parmaMap1.put("mch_id", mchId);
            parmaMap1.put("add_time_sort", DataUtils.Sort.DESC.toString());
            //参数列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.putAll(parmaMap1);
            if (!StringUtils.isEmpty(sNo)) {
                parmaMap.put("likeOrderno", sNo);
            }
            if (!StringUtils.isEmpty(name)) {
                parmaMap.put("user_name", name);
            }
            if (status != null) {
                parmaMap.put("couponType", status);
            }
            parmaMap.put("free_or_not", 0);
            parmaMap.put("page", pageModel.getPageNo());
            parmaMap.put("pageSize", pageModel.getPageSize());
            int total = couponModelMapper.countUserCouponActivityDynamic(parmaMap);
            List<Map<String, Object>> couponInfoList = new ArrayList<>();
            if (total > 0) {
                couponInfoList = couponModelMapper.getUserCouponActivityDynamic(parmaMap);
                for (Map<String, Object> map : couponInfoList) {
                    //优惠卷id
                    int couponId = Integer.parseInt(map.get("id").toString());
                    //优惠卷活动id
                    int hid = Integer.parseInt(map.get("hid").toString());
                    //用户id
                    String userid = StringUtils.toString(map.get("user_id"));
                    //活动名称
                    String hName = StringUtils.toString(map.get("name"));

                    //获取关联订单
                    List<String> orderList = new ArrayList<>();
                    CouponOrderModal couponOrderModal = new CouponOrderModal();
                    couponOrderModal.setStore_id(storeId);
                    couponOrderModal.setCoupon_id(couponId);
                    couponOrderModal.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    List<CouponOrderModal> couponOrderModalList = couponOrderModalMapper.select(couponOrderModal);
                    for (CouponOrderModal couponOrder : couponOrderModalList) {
                        orderList.add(couponOrder.getsNo());
                    }
                    map.put("orderList", orderList);

                    //到期时间
                    Date expiryTime = (Date) map.get("expiry_time");
                    //优惠卷是否过期
                    if (DateUtil.dateCompare(new Date(), expiryTime)) {
                        CouponModal couponModal = new CouponModal();
                        couponModal.setId(couponId);
                        couponModal.setType(CouponModal.COUPON_TYPE_EXPIRED);
                        int count = couponModelMapper.updateByPrimaryKeySelective(couponModal);
                        if (count < 1) {
                            logger.error("优惠卷已过期,但是修改状态失败 参数" + JSON.toJSONString(couponModal));
                        }
                    }
                    if (StringUtils.isEmpty(hName)) {
                        //没有活动名称则用公司名称
                        ConfigModel configModel = new ConfigModel();
                        configModel.setStore_id(storeId);
                        configModel = configModelMapper.selectOne(configModel);
                        if (configModel != null) {
                            map.put("name", configModel.getCompany());
                        }
                    }
                    //获取当前用户领取优惠卷得数量
                    CouponModal couponModal = new CouponModal();
                    couponModal.setStore_id(storeId);
                    couponModal.setHid(hid);
                    couponModal.setUser_id(userid);
                    int receiveNum = couponModelMapper.selectCount(couponModal);
                    map.put("receive", receiveNum);
                    map.put("add_time", DateUtil.dateFormate(map.get("add_time") + "", GloabConst.TimePattern.YMDHMS));
                }
            }


            resultMap.put("list", couponInfoList);
            resultMap.put("total", total);
            return resultMap;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("店铺查看优惠卷领取信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "seeCoupon");
        }
    }

    @Override
    public boolean delMchCoupon(int storeId, int id, Integer mchId) throws LaiKeApiException {
        try {
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setStore_id(storeId);
            if (mchId != null) {
                couponActivityModel.setMch_id(mchId);
            }
            couponActivityModel.setId(id);
            int count = couponActivityModelMapper.selectCount(couponActivityModel);
            if (count > 0) {
                //删除优惠卷活动
                CouponActivityModel couponActivityDel = new CouponActivityModel();
                couponActivityDel.setId(id);
                couponActivityDel.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                count = couponActivityModelMapper.updateByPrimaryKeySelective(couponActivityDel);
                if (count < 1) {
                    logger.info("删除店铺活动失败 参数:{}", JSON.toJSONString(couponActivityDel));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障", "delMchCoupon");
                }
                //删除未使用的优惠卷
                count = couponModelMapper.delNotUsedCoupon(storeId, couponActivityModel.getId(), mchId);
                logger.info("删除未使用的优惠卷 删除数量:{}", count);
                //获取使用中的优惠卷
                CouponModal couponModal = new CouponModal();
                couponModal.setHid(id);
                couponModal.setStore_id(storeId);
                couponModal.setType(CouponModal.COUPON_TYPE_IN_USE);
                List<CouponModal> couponModalList = couponModelMapper.select(couponModal);
                for (CouponModal coupon : couponModalList) {
                    //删除未使用的优惠卷 订单使用了,但是未付款则不删除
                    OrderModel orderModel = new OrderModel();
                    orderModel.setStore_id(storeId);
                    orderModel.setCoupon_id(coupon.getId().toString());
                    orderModel.setStatus(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_UNPAID);
                    count = orderModelMapper.selectCount(orderModel);
                    if (count < 1) {
                        CouponModal couponModalDel = new CouponModal();
                        couponModalDel.setId(coupon.getId());
                        couponModalDel.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                        count = couponModelMapper.updateByPrimaryKeySelective(couponModalDel);
                        if (count < 1) {
                            logger.info("删除用户优惠卷失败 参数" + JSON.toJSONString(couponActivityDel));
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障", "delMchCoupon");
                        }
                    } else {
                        logger.debug(String.format("用户优惠卷id:%s 删除失败,正在被订单使用中", coupon.getId()));
                    }
                }
                return true;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "活动不存在", "delMchCoupon");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除店铺活动 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delMchCoupon");
        }
    }

    @Override
    public List<Map<String, Object>> mchFenlei(int storeId, Integer mchId) throws LaiKeApiException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            //店铺拥有的所有类别id
            Set<Integer> goodsClassIdList = new HashSet<>();

            //获取店铺所有商品信息
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setMch_id(mchId);
            productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
            productListModel.setMch_status(DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS.toString());
            productListModel.setActive(DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_PRICE.toString());
            List<ProductListModel> productListModelList = productListModelMapper.select(productListModel);
            for (ProductListModel productList : productListModelList) {
                //商品类别 -3-31-191-
                String[] classIds = StringUtils.trim(productList.getProduct_class(), "-").split("-");
                for (String classId : classIds) {
                    goodsClassIdList.add(Integer.parseInt(classId));
                }
            }
            for (int classId : goodsClassIdList) {
                //当前类别信息
                Map<String, Object> classMap = new HashMap<>(16);
                //获取平台商品类别 一级目录
                ProductClassModel productClassModel = new ProductClassModel();
                productClassModel.setStore_id(storeId);
                productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                productClassModel.setCid(classId);
                productClassModel.setLevel(0);
                ProductClassModel goodsClassLevel = productClassModelMapper.selectOne(productClassModel);
                if (goodsClassLevel == null) {
                    //继续找下级目录 二级目录
                    productClassModel.setLevel(1);
                    goodsClassLevel = productClassModelMapper.selectOne(productClassModel);
                    if (goodsClassLevel == null) {
                        //三级目录
                        productClassModel.setLevel(2);
                        goodsClassLevel = productClassModelMapper.selectOne(productClassModel);
                    }
                } else {
                    classMap.put("open", true);
                }
                if (goodsClassLevel == null) {
                    logger.info("未找到该类别  参数:" + JSON.toJSONString(productClassModel));
                    continue;
                }
                classMap.put("id", classId);
                classMap.put("pId", goodsClassLevel.getSid());
                classMap.put("name", goodsClassLevel.getPname());
                classMap.put("level", goodsClassLevel.getLevel());

                resultList.add(classMap);
            }
            return resultList;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取店铺所有商品分类 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchFenlei");
        }
    }

    @Override
    public List<Map<String, Object>> mchProduct(int storeId, int mchId, String name, PageModel pageModel) throws LaiKeApiException {
        List<Map<String, Object>> resultList;
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("shop_id", mchId);
            if (!StringUtils.isEmpty(name)) {
                parmaMap.put("keyword", name);
            }
            parmaMap.put("status", DictionaryConst.GoodsStatus.NEW_GROUNDING);
            parmaMap.put("sort_sort", "desc");
            parmaMap.put("pageNo", pageModel.getPageNo());
            parmaMap.put("pageSize", pageModel.getPageSize());
            resultList = productListModelMapper.getProductListDynamic(parmaMap);
            for (Map<String, Object> map : resultList) {
                String imgUrl = map.get("imgurl").toString();
                imgUrl = publiceService.getImgPath(imgUrl, storeId);
                map.put("imgurl", imgUrl);
            }
            return resultList;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取店铺商品信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "mchProduct");
        }
    }

    @Override
    public boolean giveCoupons(int storeId, List<String> userIdList, int hid) throws LaiKeApiException {
        try {
            //获取优惠卷活动信息
            CouponActivityModel couponActivityModel = new CouponActivityModel();
            couponActivityModel.setId(hid);
            couponActivityModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
            couponActivityModel = couponActivityModelMapper.selectOne(couponActivityModel);
            if (couponActivityModel != null) {
                //优惠卷数量是否充足
                if (userIdList.size() > couponActivityModel.getNum()) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "优惠券剩余数量不足");
                }
                //优惠卷有效天数
                Date endTime = DateUtil.getAddDate(couponActivityModel.getDay());
                for (String userId : userIdList) {
                    //获取用户信息
                    User user = new User();
                    user.setUser_id(userId);
                    user = userBaseMapper.selectOne(user);
                    if (user == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, userId + "用户不存在");
                    }
                    //添加用户优惠卷信息
                    CouponModal couponModal = new CouponModal();
                    couponModal.setStore_id(storeId);
                    couponModal.setUser_id(userId);
                    couponModal.setFree_or_not(1);
                    couponModal.setExpiry_time(couponActivityModel.getEnd_time());
                    couponModal.setHid(couponActivityModel.getId());
                    couponModal.setAdd_time(new Date());
                    int count = couponModelMapper.insertSelective(couponModal);
                    if (count < 1) {
                        logger.info("优惠卷领取失败 参数:{}", JSON.toJSONString(couponModal));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "优惠卷领取失败");
                    }
                    //再赠送优惠卷记录表里添加一条数据
                    CouponPresentationRecordModel couponPresentationRecordModel = new CouponPresentationRecordModel();
                    couponPresentationRecordModel.setStore_id(storeId);
                    couponPresentationRecordModel.setCoupon_activity_id(hid);
                    couponPresentationRecordModel.setCoupon_id(couponModal.getId());
                    couponPresentationRecordModel.setUser_id(userId);
                    couponPresentationRecordModel.setMobile(user.getMobile());
                    couponPresentationRecordModel.setActivity_type(couponActivityModel.getActivity_type());
                    couponPresentationRecordModel.setAdd_date(new Date());
                    count = couponPresentationRecordModelMapper.insertSelective(couponPresentationRecordModel);
                    if (count < 1) {
                        logger.info("优惠卷赠送记录失败 参数:{}", JSON.toJSONString(couponPresentationRecordModel));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "优惠卷赠送记录失败");
                    }
                }
                //修改优惠卷剩余数量
                int count = couponActivityModelMapper.updateCouponByNum(hid, userIdList.size());
                if (count < 1) {
                    logger.info("修改优惠卷剩余数量修改失败 id:{}", hid);
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "优惠卷数量扣减失败");
                }
                return true;
            } else {
                logger.info("优惠卷活动不存在或者未启动");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "优惠卷活动不存在或者未启动");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("赠送优惠卷 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "giveCoupons");
        }
    }


    /**
     * 领券中心数据处理
     * 【php coupon.mycoupon】
     *
     * @param storeId -
     * @param userId  -
     * @param list    - 优惠卷活动信息
     * @throws LaiKeApiException; -
     * @author Trick
     * @date 2020/12/7 16:54
     */
    private void couponCenter(int storeId, String userId, List<Map<String, Object>> list) throws LaiKeApiException {
        try {
            //条件参数列表
            Map<String, Object> parmaMap = new HashMap<>(16);
            for (Map<String, Object> map : list) {
                //优惠卷活动id
                int activityId = Integer.parseInt(map.get("id").toString());
                //店铺id
                int mchId = Integer.parseInt(map.get("mch_id").toString());
                //活动名称
                String activityName = map.get("name").toString();
                //满减金额
                BigDecimal fullReductionAmt = new BigDecimal(map.get("z_money").toString());
                //领取限制
                int receive = Integer.parseInt(map.get("receive").toString());
                //剩余数量
                int surplusNum = Integer.parseInt(map.get("num").toString());
                //活动结束时间
                Date endDate = (Date) map.get("end_time");
                //活动开始时间
                Date startDate = (Date) map.get("start_time");
                map.put("start_time", DateUtil.dateFormate(startDate, GloabConst.TimePattern.YMD));
                map.put("end_time", DateUtil.dateFormate(endDate, GloabConst.TimePattern.YMD));
                if (mchId > 0) {
                    MchModel mchModel = new MchModel();
                    mchModel.setStore_id(storeId);
                    mchModel.setId(mchId);
                    mchModel = mchModelMapper.selectOne(mchModel);
                    if (mchModel != null) {
                        map.put("name", String.format("[%s]%s", mchModel.getName(), activityName));
                    }
                }
                //消费限制处理
                String limitStr = "无限制";
                if (fullReductionAmt.floatValue() > 0) {
                    limitStr = String.format("满%s可用", fullReductionAmt.toString());
                }
                map.put("limit", limitStr);
                //判断活动是否过期
                if (DateUtil.dateCompare(new Date(), endDate)) {
                    //设置活动已过期
                    CouponActivityModel couponActivityModel = new CouponActivityModel();
                    couponActivityModel.setId(activityId);
                    couponActivityModel.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_END);
                    int count = couponActivityModelMapper.updateByPrimaryKeySelective(couponActivityModel);
                    if (count < 1) {
                        logger.info("活动已结束,但是状态修改失败 参数" + JSON.toJSONString(couponActivityModel));
                    }
                } else {
                    //用户未领卷
                    String point;
                    int pointType;
                    if (StringUtils.isEmpty(userId)) {
                        point = "立即领卷";
                        pointType = 1;
                    } else {
                        //获取当前活动用户领取的优惠卷信息
                        CouponModal couponModal = new CouponModal();
                        couponModal.setUser_id(userId);
                        couponModal.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                        couponModal.setHid(activityId);
                        int count = couponModelMapper.selectCount(couponModal);
                        if (count > 0) {
                            //判断是否超过领取数量限制
                            if (receive > count) {
                                point = "立即领取";
                                pointType = 1;
                            } else {
                                parmaMap.clear();
                                parmaMap.put("store_id", storeId);
                                parmaMap.put("user_id", userId);
                                parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
                                parmaMap.put("hid", activityId);
                                List<Integer> typeList = new ArrayList<>();
                                typeList.add(CouponModal.COUPON_TYPE_NOT_USED);
                                typeList.add(CouponModal.COUPON_TYPE_IN_USE);
                                parmaMap.put("typeList", typeList);
                                count = couponModelMapper.countCouponDynamic(parmaMap);
                                if (count > 0) {
                                    point = "去使用";
                                    pointType = 2;
                                } else {
                                    point = "";
                                    pointType = 4;
                                }
                            }
                        } else {
                            if (surplusNum > 0) {
                                point = "立即领取";
                                pointType = 1;
                            } else {
                                point = "已抢光";
                                pointType = 3;
                            }
                        }
                    }
                    map.put("point", point);
                    map.put("point_type", pointType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("领券中心数据处理 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "couponCenter");
        }
    }


    @Autowired
    private ProductClassModelMapper productClassModelMapper;

    @Autowired
    private ConfigModelMapper configModelMapper;

}