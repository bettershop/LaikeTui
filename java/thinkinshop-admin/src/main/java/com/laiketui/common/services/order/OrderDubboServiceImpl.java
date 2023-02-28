package com.laiketui.common.service.dubbo.order;

import com.alibaba.fastjson.*;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.laiketui.api.common.*;
import com.laiketui.api.common.order.OrderDubboService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfiGureModel;
import com.laiketui.domain.config.ExpressModel;
import com.laiketui.domain.coupon.CouponModal;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.mch.*;
import com.laiketui.domain.order.*;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.product.StockModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserAddress;
import com.laiketui.domain.vo.OrderVo;
import com.laiketui.domain.vo.goods.AddStockVo;
import com.laiketui.domain.vo.order.*;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

import static com.laiketui.domain.order.OrderModel.*;
import static com.laiketui.root.consts.DictionaryConst.OrdersStatus.*;
import static com.laiketui.root.consts.ErrorCode.BizErrorCode.*;

/**
 * 普通订单流程
 *
 * @author wangxian
 */
@Service
public class OrderDubboServiceImpl implements OrderDubboService {

    private final Logger logger = LoggerFactory.getLogger(OrderDubboServiceImpl.class);

    @Autowired
    private PubliceService publiceService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;
    @Autowired
    private PublicAddressService commonAddressService;
    @Autowired
    private PublicOrderService publicOrderService;
    @Autowired
    private PublicMchService publicMchService;
    @Autowired
    private PublicCouponService publicCouponService;
    @Autowired
    private MchBrowseModelMapper mchBrowseModelMapper;
    @Autowired
    private OrderDetailsModelMapper orderDetailsModelMapper;
    @Autowired
    private OrderModelMapper orderModelMapper;
    @Autowired
    private CartModelMapper cartModelMapper;
    @Autowired
    private BuyAgainModalMapper buyAgainModalMapper;
    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;
    @Autowired
    private ProductListModelMapper productListModelMapper;
    @Autowired
    private StockModelMapper stockModelMapper;
    @Autowired
    private UserFirstModalMapper userFirstModalMapper;
    @Autowired
    private ReturnOrderModelMapper returnOrderModelMapper;

    @Autowired
    private ReturnRecordModelMapper returnRecordModelMapper;

    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private OrderConfigModalMapper orderConfigModalMapper;

    @Autowired
    private ServiceAddressModelMapper serviceAddressModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private ExpressModelMapper expressModelMapper;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private ReturnGoodsModelMapper returnGoodsModelMapper;

    @Autowired
    private PublicRefundService publicRefundService;

    @Autowired
    private PublicStockService publicStockService;

    @Override
    @Transactional
    public Map<String, Object> settlement(OrderVo vo) throws LaiKeApiException {
        try {
            logger.debug("订单信息：{}", JSONObject.toJSON(vo));
            // 获取用户信息 直接从redis 里面取 余额、收货信息
            User user;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil, true);
                user = userMapper.selectByPrimaryKey(user.getId());
            }
            String userId = user.getUser_id();

            String paypswd = user.getPassword();
            int wrongtimes = user.getLogin_num() == null ? 0 : user.getLogin_num();
            Date verificationtime = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(verificationtime);
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            verificationtime = calendar.getTime();
            Date now = new Date();
            //是否可以使用余额支付 默认false 表示不可以
            boolean enterless = false;
            if (wrongtimes == 5) {
                if (!now.before(verificationtime)) {
                    user.setLogin_num(0);
                    userMapper.updateByPrimaryKey(user);
                    enterless = true;
                }
            } else {
                enterless = true;
            }

            //是否有支付密码标记 0无;1有
            int passwordStatus = 1;
            if (StringUtils.isEmpty(paypswd)) {
                passwordStatus = 0;
            }

            //各个支付的开启、关闭状态
            Map<String, Object> map = new HashMap<>();
            map.put("store_id", vo.getStoreId());
            List<Map<String, Object>> paymentStatus = paymentConfigModelMapper.getPaymentConfigDynamic(map);
            Map<String, Integer> payment = Maps.newHashMap();
            for (Map<String, Object> paymentConf : paymentStatus) {
                int isOpen = DataUtils.getIntegerVal(paymentConf, "status", 0);
                payment.put(DataUtils.getStringVal(paymentConf, "class_name"), isOpen);
            }

            //获取用户的默认收货地址
            map.put("user_id", user.getUser_id());
            if (vo.getAddressId() != null && vo.getAddressId() != 0 && vo.getAddressId() != -1) {
                map.put("address_id", vo.getAddressId());
            }

            UserAddress userAddress = commonAddressService.findAddress(vo.getStoreId(), user.getUser_id(), vo.getAddressId());
            // 收货地址状态
            int addemt = userAddress == null ? 1 : 0;

            // 获取产品类型
            // [{"pid":"979"},{"cid":"5648"},{"num":1},{"sec_id":"6"}--秒杀id,{}] 运费、商品总价、
            //  cart_id: 3010,3009
            List<Map<String, Object>> productList = null;
            try {
                productList = JSON.parseObject(vo.getProductsInfo(), new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (JSONException j) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, vo.getProductsInfo() + ">参数格式不正确", "immediatelyCart");
            }

            //这里会有一个场景Bug,如果是购物车进来结算的,点击立即下单之后会删除购物车,然后切换一下自动调用结算接口,此时购物车已经无了,导致找不到数据-2021-09-14 16:23:36
            List<Map<String, Object>> productsListMap = publiceService.productsList(productList, vo.getCarts(), vo.getBuyType(), DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            if (CollectionUtils.isEmpty(productsListMap)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.STOCK_INSUFFICIENT, "库存不足2", "settlement");
            }
            //按照店铺归类的商品、运费、商品总价等信息
            Map<String, Object> productsInfo = publiceService.settlementProductsInfo(productsListMap, vo.getStoreId(), vo.getProductType());
            //运费信息
            Map<String, List<Map<String, Object>>> productsFreight = (Map<String, List<Map<String, Object>>>) productsInfo.get("products_freight");
            //计算会员的产品价格和订单产品总价
            List<Map<String, Object>> products = (List<Map<String, Object>>) productsInfo.get("products");
            //计算会员优惠价格
            Map<String, Object> memberProductsInfo = publiceService.getMemberPrice(products, userId, vo.getStoreId());
            //订单产品总计
            BigDecimal orderProductsTotal = DataUtils.getBigDecimalVal(memberProductsInfo, "products_total");
            //拿出商品信息
            productsListMap = (List<Map<String, Object>>) memberProductsInfo.get("products");
            BigDecimal gradeRate = DataUtils.getBigDecimalVal(memberProductsInfo, "grade_rate");

            //计算店铺运费、总订单运费
            BigDecimal yunfei = BigDecimal.ZERO;
            productsInfo = publicOrderService.getFreight(productsFreight, productsListMap, userAddress, vo.getStoreId(), vo.getProductType());
            products = (List<Map<String, Object>>) productsInfo.get("products");
            //是否是自提
            if (vo.getShopAddressId() != null && vo.getShopAddressId() != 0) {
                for (Map<String, Object> mchProductsInfo : products) {
                    //自提免运费
                    mchProductsInfo.put("freight_price", BigDecimal.ZERO);
                    BigDecimal productTotal = DataUtils.getBigDecimalVal(mchProductsInfo, "product_total");
                    BigDecimal freightPrice = DataUtils.getBigDecimalVal(mchProductsInfo, "freight_price");
                    mchProductsInfo.put("product_total", productTotal.subtract(freightPrice));
                }
            } else {
                yunfei = DataUtils.getBigDecimalVal(productsInfo, "yunfei");
            }

            //门点自提信息
            Map<String, Object> mdztInfo = new HashMap<>();
            //门店自提结算
            int shopStatus = 0;
            //店铺信息
            MchStoreModel mchStoreModel = null;
            if (products.size() == 1) {
                int mchId = (int) products.get(0).get("shop_id");
                if (vo.getShopAddressId() != null) {
                    mdztInfo = publicMchService.settlement(vo.getStoreId(), mchId, "", vo.getShopAddressId(), vo.getStoreType());
                    shopStatus = (int) mdztInfo.get("shop_status");
                    mchStoreModel = (MchStoreModel) mdztInfo.get("mch_store_info");
                    mchStoreModel.setAddress((String) mdztInfo.get("address"));
                }
            }

            //平台优惠券数组 // 平台优惠数组
            List<Map<String, Object>> discountList = new ArrayList<>();
            // 满减id
            int subtractionId = 0;
            // 优惠ID数组
            List<Integer> couponIdList = new ArrayList<>();

            //优惠券id拼接字符串
            String resultCouponId = "";

            String orgCouponStr = vo.getCouponId();

            String[] platformDiscountIdList = vo.getCouponId().split(SplitUtils.DH);
            if (platformDiscountIdList.length == 2) {
                platformDiscountIdList = Arrays.copyOf(platformDiscountIdList, platformDiscountIdList.length + 1);
                platformDiscountIdList[2] = "no_discount";
            }
            // 最后一调数据的键名
            int platformDiscountTypePos = platformDiscountIdList.length - 1;
            // 平台优惠的健名
            int platformDiscountPos = platformDiscountIdList.length - 2;
            // 优惠类型 三种 ：coupon，substration，nodiscount：第一次进来的时候为 0｜'' 1，12,0,nodiscount
            String discountType = platformDiscountIdList[platformDiscountTypePos];
            //String coupon:platformDiscountIdList
            for (int i = 0; i < platformDiscountIdList.length - 1; i++) {
                if (OrderVo.SUBTRACTION.equals(discountType)) {
                    if (i == platformDiscountPos) {
                        subtractionId = Integer.parseInt(platformDiscountIdList[i]);
                    }
                }
                couponIdList.add(Integer.valueOf(platformDiscountIdList[i]));
            }
            //优惠券id字符串只用来传参
            String couponIds = Joiner.on(SplitUtils.DH).join(couponIdList);

            // 满减--插件
            int autojian = 0;
            // 满减标记
            int isSubtraction = 0;
            // 减掉的金额
            BigDecimal reduceMoney = DataUtils.ZERO_BIGDECIMAL;
            // 满减名
            String reduceName = "";
            // 优惠券金额
            BigDecimal couponMoney = DataUtils.ZERO_BIGDECIMAL;
            // 优惠券名
            String couponName = "";
            //
            boolean couponStatus = DataUtils.FALSE;

            // 获取店铺的优惠券
            Map<String, Object> mchCouponsMap = publicCouponService.settlementStoreCoupons(vo.getStoreId(), userId, products, couponIds, vo.getCanshu());
            resultCouponId = (String) mchCouponsMap.get("coupon_id");
            //            products = (List<Map<String, Object>>) mchCouponsMap.get("products");
            //            // 店铺优惠之和
            BigDecimal mchPreferentialAmount = DataUtils.getBigDecimalVal(mchCouponsMap, "preferential_amount");

            //平台优惠券使用
            Map<String, Object> paltCouponsMap = publicCouponService.settlementPlaformCoupons(vo.getStoreId(), userId, products, couponIds);
            products = (List<Map<String, Object>>) paltCouponsMap.get("products");
            List<Map<String, Object>> couponList = (List<Map<String, Object>>) paltCouponsMap.get("list");
            if (couponList.size() > 0) {
                discountList = couponList;
            }

            // 如果选择了使用优惠券则 需要添加一条不使用
            Map<String, Object> noConpouSelectItem = new HashMap<>();
            noConpouSelectItem.put("coupon_id", "0");
            noConpouSelectItem.put("coupon_name", "不使用优惠券");
            noConpouSelectItem.put("discount_type", "no_discount");
            if (StringUtils.isEmpty(discountType) || "0".equals(discountType)) {
                noConpouSelectItem.put("coupon_status", true);
            } else {
                noConpouSelectItem.put("coupon_status", false);
            }
            discountList.add(noConpouSelectItem);

            int discount = 1;
            // 平台优惠类型的数量
            int platCouponNum1 = 0;
            if (!CollectionUtils.isEmpty(discountList) && discountList.size() > 0) {
                platCouponNum1 = discountList.size() - 1;
            }

            //优惠金额
            BigDecimal preferentialAmount = DataUtils.ZERO_BIGDECIMAL;
            //会员等级金额
            BigDecimal gradeRateAmount = DataUtils.ZERO_BIGDECIMAL;
            //实际付款金额
            BigDecimal payTotal;
            if (!StringUtils.isEmpty(vo.getGradeLevel()) && vo.getGradeLevel() != 0) {
                // 是商品兑换券
                orderProductsTotal = DataUtils.ZERO_BIGDECIMAL;
                // 实际付款金额 = 运费
                payTotal = yunfei;
            } else {

                if (OrderVo.SUBTRACTION.equals(discountType)) {
                    // 平台优惠金额
                    preferentialAmount = reduceMoney;
                } else if (OrderVo.COUPON.equals(discountType)) {
                    for (Map<String, Object> couponInfo : discountList) {
                        //为false的时候使用了优惠券
                        boolean couponStatusTmp = DataUtils.getBooleanVal(couponInfo, "coupon_status", false);
                        if (couponStatusTmp) {
                            // 平台优惠金额
                            preferentialAmount = DataUtils.getBigDecimalVal(couponInfo, "money");
                            //1免邮 2满减 3折扣 4会员赠送
                            int activityType = MapUtils.getIntValue(couponInfo, "activity_type");
                            if (activityType == 1 && vo.getGradeLevel() == 0) {
                                yunfei = BigDecimal.ZERO;
                                for (Map<String, Object> mchProduct : products) {
                                    mchProduct.put("freight_price", BigDecimal.ZERO);
                                    List<Map<String, Object>> onlyProducts = (List<Map<String, Object>>) mchProduct.get("list");
                                    for (Map<String, Object> product : onlyProducts) {
                                        product.put("freight_price", BigDecimal.ZERO);
                                    }
                                }
                            }
                        }
                    }
                }

                for (Map<String, Object> mchProductsInfo : products) {
                    gradeRateAmount = gradeRateAmount.add(DataUtils.getBigDecimalVal(mchProductsInfo, "grade_rate_amount"));
                }
                // 商品总价-店铺优惠之和-平台优惠+总运费
                payTotal = orderProductsTotal.subtract(mchPreferentialAmount).subtract(preferentialAmount).add(yunfei);
            }
            BigDecimal totalDiscount = mchPreferentialAmount.add(preferentialAmount).add(gradeRateAmount);

            //5.返回数据
            if (vo.getCanshu()) {
                couponIds = resultCouponId + ",0";
            }
            Map<String, Object> resultMap = new HashMap<>();
            //支付密码错误一天超过5此不允许再使用余额支付
            resultMap.put("enterless", enterless);
            // 自提标记 1为自提
            resultMap.put("shop_status", shopStatus);
            // 门店自提信息
            resultMap.put("shop_list", mchStoreModel);
            // 支付方式信息状态
            resultMap.put("payment", payment);
            // 商品列表 里面包含了店铺优惠券列表
            resultMap.put("products", products);
            // 是否分销
            resultMap.put("is_distribution", vo.getIsDistribution());
            // 密码状态
            resultMap.put("password_status", passwordStatus);
            // 商品总价
            resultMap.put("products_total", orderProductsTotal);
            // 用户余额
            resultMap.put("user_money", user.getMoney());
            // 实际支付金额
            resultMap.put("total", payTotal);

            resultMap.put("freight", yunfei);
            // 用户地址
            resultMap.put("address", userAddress == null ? new UserAddress() : userAddress);
            // 是否有收货地址
            resultMap.put("addemt", addemt);
            // 优惠券id
            resultMap.put("coupon_id", orgCouponStr);
            // 平台优惠类型的数量
            resultMap.put("coupon_num", platCouponNum1);
            // 平台优惠
            resultMap.put("preferential_amount", preferentialAmount);
            // 平台优惠券列表
            resultMap.put("coupon_list", discountList);
            // 是否满减
            resultMap.put("is_subtraction", isSubtraction);
            // 店铺优惠
            resultMap.put("mch_preferential_amount", mchPreferentialAmount);
            // 总折扣 会员优惠总金额
            resultMap.put("total_discount", totalDiscount);
            // 会员等级金额
            resultMap.put("grade_rate_amount", gradeRateAmount);
            // 会员等级折扣
            resultMap.put("grade_rate", gradeRate);
            // 满减名   TODO（可删掉）
            resultMap.put("reduce_name", reduceName);
            // 满减优惠 TODO（可删掉）
            resultMap.put("reduce_money", reduceMoney);
            // TODO  这以下几个key 是直接使用的声明时候的值
            // 优惠金额
            resultMap.put("coupon_money", couponMoney);
            //  优惠券状态
            resultMap.put("coupon_status", couponStatus);
            //  优惠 固定写成1了 ？
            resultMap.put("discount", discount);
            //  状态 固定写成1了 ？
            resultMap.put("status", "1");
            // 优惠文字
            resultMap.put("coupon_name", couponName);
            return resultMap;
        } catch (LaiKeApiException e) {
            logger.error("结算订单自定义 异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("结算订单 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "settlement");
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> payment(OrderVo vo) {
        logger.debug("下单信息：{}", JSONObject.toJSON(vo));
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 1.数据准备
            // 获取用户信息 直接从redis 里面取 余额、收货信息
            User user;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            }
            int storeId = vo.getStoreId();
            // 用户id
            String userId = user.getUser_id();
            // 商品总价
            BigDecimal productsTotal = BigDecimal.ZERO;
            //  商品数组--------['pid'=>66,'cid'=>88]
            List<Map<String, Object>> products = new ArrayList<>();
            // 用户使用积分
            int allow = vo.getAllow();
            // 门店地址id
            if (vo.getShopAddressId() == null) {
                vo.setShopAddressId(0);
            }
            int shopAddressId = vo.getShopAddressId();
            // 订单备注
            String remarksJson = vo.getRemarks();
            JSONArray remarkJsonarr = null;
            if (!StringUtils.isEmpty(remarksJson)) {
                remarkJsonarr = JSONObject.parseArray(remarksJson);
            }

            // 提交状态 1是再次购买 空是正常提交
            // 支付类型
            String payType = vo.getPayType();
            logger.info("订单支付类型：{}", payType);
            // 订单总价
            BigDecimal total;
            // 3.区分购物车结算和立即购买---列出选购商品
            List<Map<String, Object>> productList = null;
            try {
                productList = JSON.parseObject(vo.getProductsInfo(), new TypeReference<List<Map<String, Object>>>() {
                });
            } catch (JSONException j) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, vo.getProductsInfo() + ">参数格式不正确", "payment");
            }
            products = publiceService.productsList(productList, vo.getCarts(), vo.getBuyType(), DictionaryConst.OrdersType.ORDERS_HEADER_GM);
            if (CollectionUtils.isEmpty(products)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.STOCK_INSUFFICIENT, "库存不足1", "payment");
            }
            // 4.查询默认地址
            Map<String, Object> map = new HashMap<>();
            map.put("store_id", vo.getStoreId());

            //获取用户的默认收货地址
            map.put("user_id", user.getUser_id());
            map.put("address_id", vo.getAddressId());
            UserAddress userAddress = commonAddressService.findAddress(map);
            // 存储收货信息
            String mobile = userAddress.getTel();
            String name = userAddress.getName();
            String sheng = userAddress.getSheng();
            String shi = userAddress.getCity();
            String xian = userAddress.getQuyu();
            String addressXq = userAddress.getAddress();

            // 5.列出商品数组-计算总价和优惠，拿商品运费ID
            Map<String, Object> productsInfo = publiceService.settlementProductsInfo(products, vo.getStoreId(), vo.getProductType());
            //运费信息
            Map<String, List<Map<String, Object>>> productsFreight = (Map<String, List<Map<String, Object>>>) productsInfo.get("products_freight");
            //计算会员的产品价格和订单产品总价
            products = (List<Map<String, Object>>) productsInfo.get("products");
            //计算会员优惠价格
            Map<String, Object> memberProductsInfo = publiceService.getMemberPrice(products, userId, vo.getStoreId());
            //拿出商品信息
            products = (List<Map<String, Object>>) memberProductsInfo.get("products");
            BigDecimal gradeRate = DataUtils.getBigDecimalVal(memberProductsInfo, "grade_rate");
            productsTotal = DataUtils.getBigDecimalVal(memberProductsInfo, "products_total");

            //计算店铺运费、总订单运费
            BigDecimal yunfei = BigDecimal.ZERO;
            productsInfo = publicOrderService.getFreight(productsFreight, products, userAddress, vo.getStoreId(), vo.getProductType());
            products = (List<Map<String, Object>>) productsInfo.get("products");
            //
            if (vo.getShopAddressId() != 0) {
                yunfei = BigDecimal.ZERO;
                for (Map<String, Object> mchProductsInfo : products) {
                    mchProductsInfo.put("freight_price", BigDecimal.ZERO);
                    BigDecimal productTotal = DataUtils.getBigDecimalVal(mchProductsInfo, "product_total");
                    BigDecimal freightPrice = DataUtils.getBigDecimalVal(mchProductsInfo, "freight_price");
                    mchProductsInfo.put("product_total", productTotal.subtract(freightPrice));
                }
            } else {
                yunfei = DataUtils.getBigDecimalVal(productsInfo, "yunfei");
            }

            // 定义初始化数据
            int totalNum = 0;
            int discount = 1;
            // 自提信息店铺
            String otype = "GM";
            int orderStatus;
            int shopStatus = 0;
            String extractionCode = "";
            String extractionCodeImg = "";

            // 赠品ID
            int giveId = 0;
            // 满减ID
            int subtractionId = 0;
            // 满减优惠金额
            BigDecimal reduceMoney = BigDecimal.ZERO;
            // 满减名称
            String reduceNameArray = "";
            // 优惠券优惠金额
            BigDecimal couponMoney = BigDecimal.ZERO;
            //如果为0元订单，则订单状态为 1-已发货
            if (vo.getGradeLevel() != 0 && (DataUtils.equalBigDecimalZero(yunfei))) {
                orderStatus = 1;
            } else {
                //待付款
                orderStatus = 0;
            }

            //自提二维码生成、运费处理
            if (shopAddressId != 0) {
                int mchId = DataUtils.getIntegerVal(products.get(0), "shop_id");
                Map<String, Object> shopMap = publicMchService.settlement(vo.getStoreId(), mchId, "payment", shopAddressId, vo.getStoreType());
                sheng = DataUtils.getStringVal(shopMap, "sheng");
                shi = DataUtils.getStringVal(shopMap, "shi");
                xian = DataUtils.getStringVal(shopMap, "xian");
                addressXq = DataUtils.getStringVal(shopMap, "address");
                shopStatus = DataUtils.getIntegerVal(shopMap, "shop_status");
                extractionCode = DataUtils.getStringVal(shopMap, "extraction_code");
                extractionCodeImg = DataUtils.getStringVal(shopMap, "extraction_code_img");
                yunfei = BigDecimal.ZERO;
            }

            // 生成订单号
            String sNo = publicOrderService.createOrderNo(vo.getType());
            // 生成支付订单号
            String realSno = publicOrderService.createOrderNo(vo.getType());
            StringBuilder mchId = new StringBuilder();
            /**
             * 店铺订单备注
             */
            Map<String, String> mchRemarks = new HashMap<>();
            // 是否有备注标记
            boolean remarksStatus = false;
            List<String> couponIdList = new ArrayList<>();
            //优惠券id拼接字符串
            String[] platformDiscountIdList = vo.getCouponId().split(SplitUtils.DH);
            if (platformDiscountIdList.length == 2) {
                platformDiscountIdList = Arrays.copyOf(platformDiscountIdList, platformDiscountIdList.length + 1);
                platformDiscountIdList[2] = "no_discount";
            }
            // 最后一调数据的键名
            int platformDiscountTypePos = platformDiscountIdList.length - 1;
            // 平台优惠的健名
            int platformDiscountPos = platformDiscountIdList.length - 2;
            // 优惠类型 三种 ：coupon，substration，nodiscount：第一次进来的时候为 0｜'' 1，12,0,nodiscount
            String discountType = platformDiscountIdList[platformDiscountTypePos];
            for (int i = 0; i < platformDiscountIdList.length - 1; i++) {
                if (OrderVo.SUBTRACTION.equals(discountType)) {
                    if (i == platformDiscountPos) {
                        subtractionId = Integer.parseInt(platformDiscountIdList[i]);
                        couponIdList.add("0");
                        break;
                    }
                }
                couponIdList.add(platformDiscountIdList[i]);
            }
            //优惠券id字符串只用来传参
            String couponIds = Joiner.on(SplitUtils.DH).join(couponIdList);
            // 获取店铺的优惠券
            Map<String, Object> mchCouponsMap = publicCouponService.settlementStoreCoupons(vo.getStoreId(), userId, products, couponIds, vo.getCanshu());
            couponIds = DataUtils.getStringVal(mchCouponsMap, "coupon_id");
            products = (List<Map<String, Object>>) mchCouponsMap.get("products");
            // 店铺优惠之和
            BigDecimal mchPreferentialAmount = DataUtils.getBigDecimalVal(mchCouponsMap, "preferential_amount");
            // 店铺优惠券金额
            couponMoney = mchPreferentialAmount;
            BigDecimal preferential_amount = BigDecimal.ZERO;
            int is_subtraction;
            if (vo.getGradeLevel() != 0) {
                //会员特惠赠品
                productsTotal = BigDecimal.ZERO;
                total = yunfei;
                otype = "vipzs";
            } else {
                if (OrderVo.SUBTRACTION.equals(discountType)) {
                    // 平台优惠金额
                    preferential_amount = reduceMoney;
                } else if (OrderVo.COUPON.equals(discountType)) {
                    Map<String, Object> paltCouponsMap = publicCouponService.settlementPlaformCoupons(vo.getStoreId(), userId, products, couponIds);
                    products = (List<Map<String, Object>>) paltCouponsMap.get("products");
                    List<Map<String, Object>> couponList = (List<Map<String, Object>>) paltCouponsMap.get("list");
                    for (Map<String, Object> platformCoupon : couponList) {
                        boolean coupon_status = MapUtils.getBooleanValue(platformCoupon, "coupon_status");
                        if (coupon_status) {
                            BigDecimal money = DataUtils.getBigDecimalVal(platformCoupon, "money");
                            // 平台优惠金额
                            preferential_amount = money;
                            couponMoney = couponMoney.add(money);
                            int activity_type = DataUtils.getIntegerVal(platformCoupon, "activity_type");
                            if (activity_type == 1 && vo.getGradeLevel() == 0) {
                                yunfei = BigDecimal.ZERO;
                                for (Map<String, Object> mchProduct : products) {
                                    mchProduct.put("freight_price", BigDecimal.ZERO);
                                    List<Map<String, Object>> onlyProducts = (List<Map<String, Object>>) mchProduct.get("list");
                                    for (Map<String, Object> product : onlyProducts) {
                                        product.put("freight_price", BigDecimal.ZERO);
                                    }
                                }
                            }
                        }
                    }
                }
                // 商品总价-店铺优惠之和-平台优惠+总运费
                total = productsTotal.subtract(mchPreferentialAmount).subtract(preferential_amount).add(yunfei);
            }
            int pos = 0;
            for (Map<String, Object> mchProduct : products) {
                String shopId = String.valueOf(mchProduct.get("shop_id"));
                mchId.append(shopId).append(SplitUtils.DH);
                if (remarkJsonarr != null) {
                    String tmpDesc = remarkJsonarr.getString(pos++);
                    if (!StringUtils.isEmpty(tmpDesc)) {
                        mchRemarks.put(shopId, tmpDesc);
                        remarksStatus = true;
                    }
                }
                //如果是多店铺，添加一条购买记录
                MchBrowseModel mchBrowseModel = new MchBrowseModel();
                mchBrowseModel.setMch_id(shopId);
                mchBrowseModel.setStore_id(storeId);
                mchBrowseModel.setUser_id(userId);
                mchBrowseModel.setEvent("购买了商品");
                mchBrowseModel.setAdd_time(new Date());
                mchBrowseModelMapper.insertSelective(mchBrowseModel);
                List<Map<String, Object>> onlyProducts = (List<Map<String, Object>>) mchProduct.get("list");
                for (Map<String, Object> product : onlyProducts) {
                    int pid = DataUtils.getIntegerVal(product, "pid");
                    int cid = DataUtils.getIntegerVal(product, "cid");
                    int num = DataUtils.getIntegerVal(product, "num");
                    BigDecimal price = DataUtils.getBigDecimalVal(product, "price");
                    String product_title = DataUtils.getStringVal(product, "product_title");
                    String unit = DataUtils.getStringVal(product, "unit");
                    String size = DataUtils.getStringVal(product, "size");
                    String coupon_id = DataUtils.getStringVal(product, "coupon_id");
                    //如果没有优惠则为支付金额
                    BigDecimal amountAfterDiscountTmp = DataUtils.getBigDecimalVal(product, "amount_after_discount", total);

                    BigDecimal freightPrice = DataUtils.getBigDecimalVal(product, "freight_price");
                    // 循环插入订单附表 ，添加不同的订单详情
                    freightPrice = vo.getShopAddressId() != 0 ? BigDecimal.ZERO : freightPrice;

                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                    orderDetailsModel.setStore_id(storeId);
                    orderDetailsModel.setUser_id(userId);
                    orderDetailsModel.setP_id(pid);
                    orderDetailsModel.setP_name(product_title);
                    orderDetailsModel.setP_price(price);
                    orderDetailsModel.setNum(num);
                    orderDetailsModel.setUnit(unit);
                    orderDetailsModel.setR_sNo(sNo);
                    orderDetailsModel.setAdd_time(new Date());
                    orderDetailsModel.setR_status(orderStatus);
                    orderDetailsModel.setSize(size);
                    orderDetailsModel.setSid(cid + "");
                    orderDetailsModel.setFreight(freightPrice);
                    orderDetailsModel.setSettlement_type(0);
                    orderDetailsModel.setCoupon_id(coupon_id);
                    orderDetailsModel.setRecycle(0);
                    orderDetailsModel.setAfter_discount(amountAfterDiscountTmp);

                    int beres = orderDetailsModelMapper.insertSelective(orderDetailsModel);
                    // 如果添加失败
                    if (beres < 1) {
                        throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
                    }

                    totalNum += num;
                    if (!StringUtils.isEmpty(vo.getCarts()) && vo.getBuyType() != 1) {
                        Example cartModelExample = new Example(CartModel.class);
                        Example.Criteria criteria = cartModelExample.createCriteria();
                        criteria.andEqualTo("store_id", storeId);
                        criteria.andEqualTo("goods_id", pid);
                        criteria.andEqualTo("user_id", userId);
                        criteria.andEqualTo("size_id", cid);

                        // 删除对应购物车内容
                        int res_del = cartModelMapper.deleteByExample(cartModelExample);

                    } else if (!StringUtils.isEmpty(vo.getCarts()) && vo.getBuyType() == 1) {
                        Example buyAgainModalExample = new Example(BuyAgainModal.class);
                        Example.Criteria criteria = buyAgainModalExample.createCriteria();
                        criteria.andEqualTo("store_id", storeId);
                        criteria.andEqualTo("goods_id", pid);
                        criteria.andEqualTo("user_id", userId);
                        criteria.andEqualTo("size_id", cid);
                        // 删除对应再次购买购物车内容
                        int res_del = buyAgainModalMapper.deleteByExample(buyAgainModalExample);
                        if (res_del < 1) {
                            throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
                        }
                    }

                    Integer gradelevel = vo.getGradeLevel();
                    if (gradelevel == null || gradelevel == 0) {
                        ConfiGureModel confiGureModel = new ConfiGureModel();
                        confiGureModel.setId(cid);
                        confiGureModel.setPid(pid);
                        confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                        int gureModelNum = confiGureModel.getNum();

                        //非会员特惠商品才减库存
                        // 销量+1 库存-1
                        int res_del1 = productListModelMapper.reduceGoodsStockNum(pid, num);
                        if (res_del1 < 1) {
                            throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
                        }

                        int res_del2 = confiGureModelMapper.reduceGoodsStockNum(num, cid);
                        if (res_del2 < 1) {
                            throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
                        }

                        StockModel stockModel = new StockModel();
                        stockModel.setStore_id(storeId);
                        stockModel.setProduct_id(pid);
                        stockModel.setAttribute_id(cid);
                        stockModel.setTotal_num(gureModelNum);
                        stockModel.setFlowing_num(num);
                        stockModel.setType(1);
                        stockModel.setUser_id(userId);
                        stockModel.setAdd_date(new Date());
                        stockModel.setContent(userId + "生成订单所需" + num);
                        stockModelMapper.insertSelective(stockModel);
                    }
                }
            }

            String mainOrderRemarks = "";
            if (remarksStatus) {
                mainOrderRemarks = SerializePhpUtils.JavaSerializeByPhp(mchRemarks);
            }

            mchId = new StringBuilder(com.laiketui.root.utils.tool.StringUtils.rtrim(mchId.toString(), SplitUtils.DH));
            mchId = new StringBuilder(SplitUtils.DH + mchId + SplitUtils.DH);

            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(storeId);
            orderModel.setUser_id(userId);
            orderModel.setName(name);
            orderModel.setMobile(mobile);
            orderModel.setNum(totalNum);
            orderModel.setZ_price(total);
            orderModel.setsNo(sNo);
            orderModel.setSheng(sheng);
            orderModel.setShi(shi);
            orderModel.setXian(xian);
            orderModel.setAddress(addressXq);
            orderModel.setRemark(vo.getRemarks());
            orderModel.setPay(payType);
            orderModel.setAdd_time(new Date());
            orderModel.setCoupon_id(couponIds);
            orderModel.setSubtraction_id(subtractionId);
            orderModel.setConsumer_money(allow);
            orderModel.setCoupon_activity_name(reduceNameArray);
            orderModel.setSpz_price(productsTotal);
            orderModel.setStatus(orderStatus);
            orderModel.setReduce_price(reduceMoney);
            orderModel.setSource(vo.getStoreType());
            orderModel.setOtype(otype);
            orderModel.setMch_id(mchId.toString());
            orderModel.setP_sNo("");
            orderModel.setBargain_id(0);
            orderModel.setComm_discount(new BigDecimal(discount));
            orderModel.setRemarks(mainOrderRemarks);
            orderModel.setReal_sno(realSno);
            orderModel.setSelf_lifting(shopStatus);
            orderModel.setExtraction_code(extractionCode);
            orderModel.setExtraction_code_img(extractionCodeImg);
            orderModel.setGrade_rate(gradeRate);
            orderModel.setZ_freight(yunfei);
            orderModel.setCoupon_price(couponMoney);
            orderModel.setPreferential_amount(preferential_amount);
            orderModel.setSingle_store(shopAddressId);
            orderModel.setReadd(0);
            orderModel.setZhekou(BigDecimal.ZERO);
            orderModel.setRecycle(0);
            orderModel.setPick_up_store(0);
            orderModelMapper.insertSelective(orderModel);
            int r_o = orderModel.getId();
            if (r_o >= 0) {
                if (giveId != 0 && vo.getGradeLevel() == null) {
                    int row = productListModelMapper.reduceGoodsStockNum(giveId, 1);
                    if (row < 1) {
                        throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
                    }

                    row = confiGureModelMapper.reduceGiveGoodsStockNum(giveId);
                    if (row < 1) {
                        throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
                    }
                }
                //如果为会员等级赠送商品，修改兑换券状态
                if (vo.getGradeLevel() != 0 && vo.getGradeLevel() != null) {
                    int res_1 = userFirstModalMapper.updateUserGiveRecord(sNo, storeId, userId, vo.getGradeLevel());
                    if (res_1 < 0) {
                        throw new LaiKeApiException(ORDER_FAILED_UPDATE_COUPON_STATUS, "下单失败,更新兑换券使用状态失败", "payment");
                    }
                }

                if (!StringUtils.isEmpty(couponIds)) {
                    int updateResultFlag = publicCouponService.updateCoupons(storeId, userId, couponIds, CouponModal.COUPON_TYPE_USED);
                    if (updateResultFlag == 0) {
                        //回滚删除已经创建的订单
                        throw new LaiKeApiException(ORDER_FAILED_UPDATE_COUPON_STATUS, "下单失败,更新兑换券使用状态失败", "payment");
                    }
                    updateResultFlag = publicCouponService.couponWithOrder(storeId, userId, couponIds, sNo, "add");
                    if (updateResultFlag == 0) {
                        //回滚删除已经创建的订单
                        throw new LaiKeApiException(ORDER_FAILED_ADD_COUPON_ASSOSIATED_ORDERINFO, "添加优惠券关联订单数据失败", "payment");
                    }
                }
                //订单号
                resultMap.put("sNo", sNo);
                //订单总支付金额
                resultMap.put("total", total);
                //订单id
                resultMap.put("order_id", r_o);
                return resultMap;
            } else {
                throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("下单 异常", e);
            throw new LaiKeApiException(ORDER_FAILED_TRY_AGAIN_LATER, "下单失败,请稍后再试", "payment");
        }
    }

    @Override
    @Transactional
    public Map<String, Object> updateOrderRemark(OrderVo vo) {
        try {
            int row = orderModelMapper.updateOrderRemark(vo.getRemarks(), vo.getsNo(), vo.getStoreId());
            logger.info("更新结果：" + row);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new LaiKeApiException(API_OPERATION_FAILED, "操作失败", "");
        }
        return null;
    }

    @Override
    @Transactional
    public Map<String, Object> splitOrder(OrderVo vo) {
        Map<String, Object> resultMap = new HashMap<>();
        try {
            // 订单信息
            String orderList = vo.getOrderList();
            //orderList 字符串转数组
            Map<String, String> orderInfoMap = JSON.parseObject(orderList, Map.class);
            String sNo = orderInfoMap.get("sNo");

            //拼团/秒杀 跳过拆分
            if (sNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_PT) || sNo.contains(DictionaryConst.OrdersType.ORDERS_HEADER_MS)
                    || sNo.contains(DictionaryConst.OrdersType.PTHD_ORDER_PP)) {
                //操作成功
                resultMap.put("flag", true);
                return resultMap;
            }

            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            int storeId = vo.getStoreId();
            // 用户id
            String userId = user.getUser_id();
            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(vo.getStoreId());
            orderModel.setsNo(sNo);
            orderModel.setUser_id(userId);
            orderModel = orderModelMapper.selectOne(orderModel);
            // 判断订单是否存在、有效
            if (orderModel == null) {
                // 数据异常，返回错误提示
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, sNo + "订单不存在", "payment");
            }

            //获取订单数据
            // 优惠券ID
            String couponId = orderModel.getCoupon_id();
            // 满减ID
            Integer subtractionId = orderModel.getSubtraction_id();
            // 查询出的满减金额
            BigDecimal reducePrice = orderModel.getReduce_price();
            // 会员折扣
            BigDecimal gradeRate = orderModel.getGrade_rate();
            // 商品总价
            BigDecimal zSpzPrice = orderModel.getSpz_price();
            // 订单状态
            Integer status = orderModel.getStatus();
            Map remarks = new HashMap();
            String orderRemarks = orderModel.getRemarks();
            if (!StringUtils.isEmpty(orderRemarks)) {
                remarks = SerializePhpUtils.getUnserializeObj(orderModel.getRemarks(), Map.class);
            }

            if (status == ORDERS_R_STATUS_UNPAID) {
                // 如果未支付完成则不拆分订单
                //操作成功
                resultMap.put("flag", true);
                resultMap.put("message", "未支付完成则不拆分订单");
                return resultMap;
            }

            // 店铺ID字符串
            String mchId = orderModel.getMch_id();
            orderModel.setId(null);

            List<Integer> integerArrayList = new ArrayList<>();
            // 根据订单号，查询订单商品ID 更新商品的销量
            List<Map<String, Object>> orderProductsInfoList = orderModelMapper.getOrderProductInfo(storeId, sNo);
            if (!CollectionUtils.isEmpty(orderProductsInfoList)) {
                for (Map<String, Object> orderProductsInfoMap : orderProductsInfoList) {
                    Integer pid = DataUtils.getIntegerVal(orderProductsInfoMap, "p_id");
                    Integer num = DataUtils.getIntegerVal(orderProductsInfoMap, "num");
                    integerArrayList.add(pid);
                    int row = productListModelMapper.updateProductListVolume(num, storeId, pid);
                    if (row <= 0) {
                        throw new LaiKeApiException(API_OPERATION_FAILED, "操作失败", "splitOrder");
                    }
                }
            }

            String type = sNo.substring(0, 2);//获取订单号前两位字母（类型）
            // mch_id 和 shop_id 相同
            mchId = com.laiketui.root.utils.tool.StringUtils.trim(mchId, SplitUtils.DH);
            // 店铺id字符串
            List<String> shopIds = Splitter.on(SplitUtils.DH).splitToList(mchId);

            //当为多家店铺时拆单
            if (!CollectionUtils.isEmpty(shopIds) && shopIds.size() > 1) {
                List<Map<String, Object>> couponList = null;
                if ("0".equals(couponId) && !StringUtils.isEmpty(couponId)) {
                    //优惠券拆单
                    couponList = publicCouponService.splitOrder(storeId, userId, sNo);
                }

                List<OrderModel> splitOrders = new ArrayList<>();
                for (String shopId : shopIds) {
                    OrderModel orderModelTmp = new OrderModel();
                    BeanUtils.copyProperties(orderModel, orderModelTmp);
                    // 生成订单号
                    String sNoTmp = publicOrderService.createOrderNo(type);
                    orderModelTmp.setMch_id(SplitUtils.DH + shopId + SplitUtils.DH);
                    orderModelTmp.setsNo(sNoTmp);
                    orderModelTmp.setP_sNo(sNo);
                    //查询单个商品的价格，运费，数量
                    List<Map<String, Object>> orderDetailsList = orderModelMapper.getOrderDetails(storeId, sNo, Integer.parseInt(shopId));
                    //查询到数据
                    BigDecimal orderNum = BigDecimal.ZERO;
                    if (!CollectionUtils.isEmpty(orderDetailsList)) {
                        //商品总价
                        BigDecimal spzPrice = BigDecimal.ZERO;
                        BigDecimal totalFreight = BigDecimal.ZERO;
                        for (Map<String, Object> orderDetailsMap : orderDetailsList) {
                            Integer order_details_id = DataUtils.getIntegerVal(orderDetailsMap, "id");
                            BigDecimal num = DataUtils.getBigDecimalVal(orderDetailsMap, "num");
                            BigDecimal productPrice = DataUtils.getBigDecimalVal(orderDetailsMap, "p_price", BigDecimal.ZERO);
                            BigDecimal freight = DataUtils.getBigDecimalVal(orderDetailsMap, "freight");
                            orderNum = orderNum.add(num);
                            spzPrice = spzPrice.add(productPrice.multiply(gradeRate).multiply(num));
                            totalFreight = totalFreight.add(freight);
                            int row = orderDetailsModelMapper.updateOrderDetailsParentOrderNo(storeId, sNoTmp, order_details_id);
                            if (row < 1) {
                                logger.error("修改订单号失败！");
                                throw new LaiKeApiException(API_OPERATION_FAILED, "操作失败", "splitOrder");
                            }
                        }

                        orderModelTmp.setSpz_price(spzPrice);
                        orderModelTmp.setPay_time(new Date());
                        orderModelTmp.setSubtraction_id(0);
                        orderModelTmp.setReduce_price(new BigDecimal(0));
                        orderModelTmp.setCoupon_id("0");
                        orderModelTmp.setCoupon_price(new BigDecimal(0));
                        orderModelTmp.setGrade_rate(gradeRate);
                        orderModelTmp.setPreferential_amount(new BigDecimal(0));
                        //临时变量
                        BigDecimal spzPriceTmp = spzPrice;
                        if (StringUtils.isEmpty(couponId) && !"0".equals(couponId)) {
                            // 参与优惠券 查看php 中 couponlist的数据结构
                            if (!CollectionUtils.isEmpty(couponList) && couponList.size() > 0) {
                                for (Map<String, Object> couponInfoMap : couponList) {
                                    String mchIdTmp = DataUtils.getStringVal(couponInfoMap, "mah_id");
                                    if (shopId.equals(mchIdTmp)) {
                                        String couponIdTmp = DataUtils.getStringVal(couponInfoMap, "coupon_id");
                                        orderModelTmp.setCoupon_id(couponIdTmp);
                                        orderModelTmp.setCoupon_price(DataUtils.getBigDecimalVal(couponInfoMap, "preferential_amount"));
                                        spzPriceTmp = spzPrice.subtract(orderModelTmp.getCoupon_price());
                                        BigDecimal preferentialAmount = DataUtils.getBigDecimalVal(couponInfoMap, "preferential_amount");
                                        BigDecimal mahCouponAmount = DataUtils.getBigDecimalVal(couponInfoMap, "mah_coupon_amount");
                                        orderModelTmp.setPreferential_amount(preferentialAmount.subtract(mahCouponAmount));
                                        int row = publicCouponService.couponWithOrder(storeId, userId, couponIdTmp, sNoTmp, "add");
                                        if (row <= 0) {
                                            logger.error("添加优惠券关联订单数据失败！");
                                            throw new LaiKeApiException(ORDER_FAILED_ADD_COUPON_ASSOSIATED_ORDERINFO, "添加优惠券关联订单数据失败", "splitOrder");
                                        }
                                    }
                                }
                            }
                        }
                        if (subtractionId != 0 && subtractionId != null) {
                            // 参与满减
                            orderModelTmp.setSubtraction_id(subtractionId);
                            // 该店铺商品总价 除以 整个订单商品总价 乘以 优惠的满减金额
                            BigDecimal val = spzPrice.divide(zSpzPrice).multiply(reducePrice);
                            orderModelTmp.setReduce_price(val);
                            val = orderModelTmp.getPreferential_amount().add(orderModelTmp.getReduce_price());
                            orderModelTmp.setPreferential_amount(val);
                            spzPriceTmp = spzPrice.subtract(orderModelTmp.getReduce_price());
                        }

                        BigDecimal val = spzPriceTmp.add(totalFreight);
                        orderModelTmp.setZ_price(val);
                        orderModelTmp.setZ_freight(totalFreight);
                        orderModelTmp.setStatus(status);
                        orderModelTmp.setNum(orderNum.intValue());
                        orderModelTmp.setRemarks("");

                        if (!CollectionUtils.isEmpty(remarks)) {
                            String remarksStr = DataUtils.getStringVal(remarks, shopId, "");
                            if (!StringUtils.isEmpty(remarksStr)) {
                                String[] remark = {remarksStr};
                                orderModelTmp.setRemarks(SerializePhpUtils.JavaSerializeByPhp(remark));
                            }
                        }

                        splitOrders.add(orderModelTmp);

                        if (splitOrders.size() == shopIds.size()) {
                            orderModelMapper.insertList(splitOrders);
                            logger.info("保存拆单信息！");
                        }

                        int row = orderDetailsModelMapper.updateOrderDetailsStatus(storeId, sNoTmp, status);
                        if (row < 0) {
                            logger.error("订单拆分失败！");
                            throw new LaiKeApiException(API_OPERATION_FAILED, "操作失败", "splitOrder");
                        }
                    } else {
                        logger.error("没有查询到订单信息！");
                        throw new LaiKeApiException(API_OPERATION_FAILED, "操作失败", "splitOrder");
                    }
                }


                //拆单后关闭并逻辑删除原主订单
                Map conditionMap = Maps.newHashMap();
                conditionMap.put("status", ORDERS_R_STATUS_CLOSE);
                conditionMap.put("recycle", "1");
                conditionMap.put("orderno", sNo);
                int row = orderModelMapper.updateByOrdernoDynamic(conditionMap);
                if (row < 1) {
                    throw new LaiKeApiException(API_OPERATION_FAILED, "操作失败", "splitOrder");
                }
            }

            //TODO 订单成功后的 推送 和 消息记录
            //$this->orderMessage($sNo, $store_id, $user_id, $db, $shop_id,$Identification);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(API_OPERATION_FAILED, e.getMessage(), "splitOrder");
        }
        //操作成功
        resultMap.put("flag", true);
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, Object> orderList(OrderVo vo) {
        try {
            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil, true);
            return publicOrderService.orderList(vo, user);
        } catch (LaiKeApiException e) {
            logger.error("订单列表获取失败 异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("订单列表获取失败 异常", e);
            throw new LaiKeApiException(ORDER_LIST_FAILED, "订单列表获取失败", "orderList");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> remindDelivery(OrderVo vo) {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            int storeId = vo.getStoreId();
            // 订单id
            int orderId = vo.getOrderId();
            User user = null;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            }
            String userId = user.getUser_id();
            OrderModel order = new OrderModel();
            order.setStore_id(storeId);
            order.setUser_id(userId);
            order.setId(orderId);
            order = orderModelMapper.selectOne(order);
            if (order != null) {
                orderModelMapper.updateDeliveryRemind(storeId, userId, orderId);
                retMap.put("message", "操作成功");
                retMap.put("code", "200");
            } else {
                retMap.put("message", "已经提醒过了,请稍后再试！");
                retMap.put("code", ORDER_REMIND_ALREADY);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ORDER_REMIND_FAILED, "提醒发货失败", "remindDelivery");
        }
        return retMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> cancleOrder(OrderVo vo) {
        Map<String, Object> retMap = Maps.newHashMap();
        try {
            int storeId = vo.getStoreId();
            // 订单id
            int orderId = vo.getOrderId();
            // 根据微信id,查询用户id
            User user = null;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            }
            String userId = user.getUser_id();
            BigDecimal money = user.getMoney();
            // 根据订单id,查询订单列表(订单号)
            OrderModel order = new OrderModel();
            order.setStore_id(storeId);
            order.setUser_id(userId);
            order.setId(orderId);
            order = orderModelMapper.selectOne(order);
            if (order != null) {
                // 订单号
                String sNo = order.getsNo();
                // 订单状态
                int status = order.getStatus();
                // 订单总价
                BigDecimal zPrice = order.getZ_price();
                // 优惠券id
                String couponId = order.getCoupon_id();
                BigDecimal offsetBalance = order.getOffset_balance();
                String otype = order.getOtype();
                if ("vipzs".equals(otype)) {
                    userFirstModalMapper.cancleUserFirstRecord(sNo, storeId);
                }
                StringBuilder event = new StringBuilder();
                event.append(userId).append("取消订单号为").append(sNo).append("的订单");

                RecordModel recordModel = new RecordModel();
                recordModel.setStore_id(storeId);
                recordModel.setUser_id(userId);
                recordModel.setMoney(zPrice);
                recordModel.setOldmoney(money);
                recordModel.setAdd_date(new Date());
                recordModel.setEvent(event.toString());
                recordModel.setType(23);
                switch (status) {
                    case ORDER_PAYED:
                        userMapper.updateUserMoney(zPrice, storeId, userId);
                        recordModelMapper.insert(recordModel);
                        break;
                    case ORDER_UNRECEIVE:
                        //已经发货不支持取消订单
                        throw new LaiKeApiException(ORDER_DELIVERYED, "已经发货", "cancleOrder");
                    default:
                        if (offsetBalance.doubleValue() > 0) {
                            //修改用户余额
                            userMapper.updateUserMoney(offsetBalance, storeId, userId);
                            //添加日志
                            event.append(userId).append("退款").append(offsetBalance).append("元余额");
                            recordModel.setEvent(event.toString());
                            recordModelMapper.insert(recordModel);
                        }
                        break;
                }

                Map params = Maps.newHashMap();
                params.put("status", ORDER_CLOSE);
                params.put("cancleOrder", "yes");
                params.put("storeId", storeId);
                params.put("userId", userId);
                params.put("id", orderId);
                int row = orderModelMapper.updateOrderInfo(params);
                if (row < 1) {
                    logger.info(sNo + "修改订单状态失败:");
                }

                int row1 = orderDetailsModelMapper.updateOrderDetailsStatus(storeId, sNo, ORDER_CLOSE);
                logger.info(sNo + "订单关闭:");
                if (row1 < 1) {
                    logger.info("sNo 修改订单状态失败！");
                }
                if (row1 >= 0 && row >= 0) {
                    if (org.apache.commons.lang3.StringUtils.isNotEmpty(couponId)
                            && !"0".equals(couponId)) {
                        row = publicCouponService.updateCoupons(storeId, userId, couponId, 0);
                        if (row == 0) {
                            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "cancleOrder");
                        }
                        logger.info("会员" + userId + "取消订单号为" + sNo + "时,修改优惠券ID为" + couponId + "的状态！");
                        row = publicCouponService.couponWithOrder(storeId, userId, couponId, sNo, "update");
                        if (row == 0) {
                            //回滚删除已经创建的订单
                            throw new LaiKeApiException(ORDER_FAILED_ADD_COUPON_ASSOSIATED_ORDERINFO, "添加优惠券关联订单数据失败", "cancleOrder");
                        }
                    }
                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                    orderDetailsModel.setStore_id(storeId);
                    orderDetailsModel.setR_sNo(sNo);
                    List<OrderDetailsModel> orderDetailsModels = orderDetailsModelMapper.select(orderDetailsModel);
                    for (OrderDetailsModel orderDetailsInfo : orderDetailsModels) {
                        int pid = orderDetailsInfo.getP_id();
                        int goodsNum = orderDetailsInfo.getNum();
                        String attributeId = orderDetailsInfo.getSid();

                        ConfiGureModel confiGureModel = new ConfiGureModel();
                        confiGureModel.setId(Integer.valueOf(attributeId));
                        confiGureModel.setPid(pid);

                        confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                        int totalNum = confiGureModel.getNum();

                        row = productListModelMapper.addGoodsStockNum(pid, goodsNum);
                        if (row < 1) {
                            logger.info("修改商品库存失败！");
                        }
                        confiGureModel.setNum(goodsNum);
                        confiGureModel.setTotal_num(0);
                        row = confiGureModelMapper.addGoodsAttrStockNum(confiGureModel);
                        if (row < 1) {
                            logger.info("修改商品属性库存失败！");
                        }

                        String content = userId + "取消订单，返还" + goodsNum;
                        StockModel stockModel = new StockModel();
                        stockModel.setStore_id(storeId);
                        stockModel.setProduct_id(pid);
                        stockModel.setAttribute_id(Integer.valueOf(attributeId));
                        stockModel.setTotal_num(totalNum);
                        stockModel.setFlowing_num(goodsNum);
                        stockModel.setType(0);
                        stockModel.setUser_id(userId);
                        stockModel.setAdd_date(new Date());
                        stockModel.setContent(content);
                        stockModelMapper.insert(stockModel);
                    }
                    retMap.put("op_result", "操作成功");
                } else {
                    throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "cancleOrder");
                }
            }
            return retMap;
        } catch (LaiKeApiException l) {
            logger.error("取消订单 异常", l);
            throw l;
        } catch (Exception e) {
            logger.error("取消订单 异常", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, e.getMessage(), "cancleOrder");
        }
    }

    @Override
    @Transactional
    public Map<String, Object> loadMore(OrderVo vo) {
        try {
            int storeId = vo.getStoreId();
            String accessId = vo.getAccessId();
            String keyword = vo.getOrdervalue();
            // 页数
            int page = vo.getPageNo();
            //订单类型
            String orderType = vo.getOrderType();
            OrderConfigModal orderConfigModal = new OrderConfigModal();
            orderConfigModal.setStore_id(storeId);
            orderConfigModal = orderConfigModalMapper.selectOne(orderConfigModal);
            //订单失效天数
            int orderFailure = 2;
            // 订单过期删除时间
            int orderOverdue = 0;
            String company = "day";
            String unit = "day";
            if (orderConfigModal != null) {
                orderFailure = orderConfigModal.getOrder_failure();
                orderOverdue = orderConfigModal.getOrder_overdue();
                if ("天".equals(orderConfigModal.getCompany())) {
                    company = "day";
                } else {
                    company = "hour";
                }
                if ("天".equals(orderConfigModal.getUnit())) {
                    unit = "day";
                } else {
                    unit = "hour";
                }
            }

            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            // 根据微信id,查询用户id
            Map<String, Object> data = publicOrderService.orderList(vo, user);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ORDER_LIST_FAILED, "订单列表获取失败", "orderList");
        }
    }

    @Transactional
    @Override
    public Map<String, Object> delOrder(OrderVo vo) {
        Map retMap = Maps.newHashMap();
        try {
            int storeId = vo.getStoreId();
            // 订单id
            int orderId = vo.getOrderId();
            // 根据微信id,查询用户id
            User user = null;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            }
            String userId = user.getUser_id();
            // 根据订单id,查询订单列表(订单号)
            OrderModel order = new OrderModel();
            order.setStore_id(storeId);
            order.setUser_id(userId);
            order.setId(orderId);
            order = orderModelMapper.selectOne(order);
            if (order != null) {
                // 订单号
                String sNo = order.getsNo();
                int row1 = orderDetailsModelMapper.delOrderDetails(storeId, sNo);
                int row2 = orderModelMapper.delOrder(storeId, sNo);
                if (row1 >= 0 && row2 >= 0) {
                    Map<String, Object> parmaMap = new HashMap<>(16);
                    parmaMap.put("store_id", vo.getStoreId());
                    parmaMap.put("orderno", sNo);
                    parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
                    //回滚库存
                    List<Map<String, Object>> orderDetailsModelList = orderDetailsModelMapper.getOrderDetailByGoodsInfo(parmaMap);
                    for (Map<String, Object> detail : orderDetailsModelList) {
                        Integer status = MapUtils.getInteger(detail, "r_status");
                        int id = MapUtils.getIntValue(detail, "id");
                        int attrId = MapUtils.getIntValue(detail, "sid");
                        int goodsId = MapUtils.getIntValue(detail, "goodsId");
                        int num = MapUtils.getIntValue(detail, "num");
                        if (status.equals(DictionaryConst.OrdersStatus.ORDERS_R_STATUS_COMPLETE) || status.equals(ORDERS_R_STATUS_CLOSE)) {
                            logger.debug("订单明细id{} 状态{} 删除订单不回滚库存", id, status);
                            continue;
                        }
                        //回滚商品库存
                        AddStockVo addStockVo = new AddStockVo();
                        addStockVo.setStoreId(vo.getStoreId());
                        addStockVo.setId(attrId);
                        addStockVo.setPid(goodsId);
                        addStockVo.setAddNum(num);
                        addStockVo.setText("后台删除订单,返还" + num);
                        publicStockService.addGoodsStock(addStockVo, "admin");
                        //扣减商品销量
                        productListModelMapper.updateProductListVolume(-num, vo.getStoreId(), goodsId);
                    }

                    retMap.put("code", 200);
                    retMap.put("message", "操作成功");
                } else {
                    throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "delOrder");
                }
            } else {
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "delOrder");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "delOrder");
        }
        return retMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> buyAgain(BuyAgainVo vo) {
        Map retMap = new HashMap();
        try {
            int storeId = vo.getStoreId();
            // 订单id
            int orderId = vo.getId();
            OrderModel order = new OrderModel();
            order.setStore_id(storeId);
            order.setId(orderId);
            order = orderModelMapper.selectOne(order);
            if (order != null) {
                String userId = order.getUser_id();
                String sNo = order.getsNo();
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setR_sNo(sNo);
                orderDetailsModel.setStore_id(storeId);
                List<OrderDetailsModel> orderDetailsModelList = orderDetailsModelMapper.select(orderDetailsModel);
                // 可以添加购物车
                int cartType = 1;
                for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                    int num = productListModelMapper.getProductNum(storeId, orderDetails.getP_id(), Integer.valueOf(orderDetails.getSid()));
                    if (num > 0) {
                        if (num >= orderDetails.getNum()) {
                            // 可以添加购物车
                            cartType = 1;
                        } else {
                            throw new LaiKeApiException(INSUFFICIENT_STOCK, "库存不充足", "buyAgain");
                        }
                    } else {
                        throw new LaiKeApiException(OFF_SHELF, "商品已下架", "buyAgain");
                    }
                }
                List<Integer> cartId = new ArrayList<>();
                if (cartType == 1) {
                    for (OrderDetailsModel orderDetails : orderDetailsModelList) {
                        BuyAgainModal buyAgainModal = new BuyAgainModal();
                        buyAgainModal.setStore_id(storeId);
                        buyAgainModal.setUser_id(userId);
                        buyAgainModal.setGoods_id(orderDetails.getP_id());
                        buyAgainModal.setGoods_num(orderDetails.getNum());
                        buyAgainModal.setCreate_time(new Date());
                        buyAgainModal.setSize_id(orderDetails.getSid());
                        buyAgainModalMapper.insert(buyAgainModal);
                        // 得到添加数据的id
                        int id = buyAgainModal.getId();
                        if (id < 1) {
                            logger.info("添加购物车失败！");
                        }
                        cartId.add(id);
                    }
                }
                retMap.put("code", "200");
                retMap.put("cart_id", Joiner.on(SplitUtils.DH).join(cartId));
                retMap.put("message", "操作成功");
            } else {
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "buyAgain");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "buyAgain");
        }
        return retMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> orderSearch(OrderVo vo) {
        Map resultMap = Maps.newHashMap();
        try {
            int storeId = vo.getStoreId();
            // 订单号
            String sNo = vo.getsNo();
            // 根据微信id,查询用户id
            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
            orderDetailsModel.setStore_id(storeId);
            //TODO
            orderDetailsModel.setR_status(4);
            orderDetailsModel.setR_sNo(sNo);
            orderDetailsModel = orderDetailsModelMapper.selectOne(orderDetailsModel);
            JSONObject dataJson = new JSONObject();
            if (orderDetailsModel != null) {
                // 商品id
                int pid = orderDetailsModel.getP_id();
                // 类型
                int r_type = orderDetailsModel.getR_type();
                dataJson = JSONObject.parseObject(JSON.toJSONString(orderDetailsModel));
                OrderModel order = new OrderModel();
                order.setStore_id(storeId);
                order.setsNo(sNo);
                order.setUser_id(user.getUser_id());
                order = orderModelMapper.selectOne(order);
                int order_id = order.getId();

                // 根据产品id,查询产品列表 (产品图片)
                ProductListModel productListModel = new ProductListModel();
                productListModel.setStore_id(storeId);
                productListModel.setId(pid);
                productListModel = productListModelMapper.selectOne(productListModel);
                String url = "";
                if (productListModel != null) {
                    // 拼图片路径
                    url = publiceService.getImgPath(productListModel.getImgurl(), productListModel.getStore_id());
                }
                dataJson.put("order_id", order_id);
                dataJson.put("imgurl", url);
                if (r_type == 0) {
                    dataJson.put("prompt", "审核中");
                    dataJson.put("buyer", "");
                    dataJson.put("return_state", "");
                } else if (r_type == 1) {
                    ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
                    serviceAddressModel.setStore_id(storeId);
                    serviceAddressModel.setUid("admin");
                    serviceAddressModel.setIs_default(1);
                    serviceAddressModel.setType(2);
                    serviceAddressModel = serviceAddressModelMapper.selectOne(serviceAddressModel);
                    Map buyer = Maps.newHashMap();
                    buyer.put("tel", serviceAddressModel.getTel());
                    buyer.put("name", serviceAddressModel.getName());
                    buyer.put("address_xq", serviceAddressModel.getAddress_xq());
                    dataJson.put("prompt", "审核通过");
                    dataJson.put("buyer", buyer);
                    dataJson.put("return_state", "");
                } else if (r_type == 2) {
                    dataJson.put("prompt", "拒绝");
                    dataJson.put("buyer", "");
                    dataJson.put("return_state", "");
                } else if (r_type == 3) {
                    dataJson.put("prompt", "审核通过");
                    dataJson.put("buyer", "");
                    dataJson.put("return_state", "");
                } else if (r_type == 4) {
                    dataJson.put("prompt", "退货完成");
                    dataJson.put("buyer", "");
                    dataJson.put("return_state", "退货退款");
                } else if (r_type == 5) {
                    dataJson.put("prompt", "退货失败");
                    dataJson.put("buyer", "");
                    dataJson.put("return_state", "退货退款");
                }
                resultMap.put("code", 200);
                resultMap.put("data", dataJson);
                resultMap.put("message", "操作成功");
            } else {
                resultMap.put("code", 200);
                resultMap.put("data", null);
                resultMap.put("message", "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "orderSearch");
        }
        return resultMap;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> delCart(OrderVo vo) {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            int store_id = vo.getStoreId();
            // 订单id
            String cartIds = vo.getCarts();
            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            if (StringUtils.isEmpty(cartIds)) {
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "delCart");
            }

            String[] cartIdArrays = cartIds.split(SplitUtils.DH);
            for (String cartId : cartIdArrays) {
                CartModel cartModel = new CartModel();
                cartModel.setStore_id(store_id);
                cartModel.setId(Integer.valueOf(cartId));
                cartModel.setUser_id(user.getUser_id());
                List<CartModel> cartModels = cartModelMapper.select(cartModel);
                if (!CollectionUtils.isEmpty(cartModels)) {
                    int row = cartModelMapper.delete(cartModel);
                    if (row < 0) {
                        logger.error("删除购物车失败");
                        resultMap.put("code", ABNORMAL_BIZ);
                        resultMap.put("message", "操作失败");
                    }
                }
            }
            resultMap.put("code", 200);
            resultMap.put("message", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "delCart");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getPaymentConf(OrderVo vo) {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            int store_id = vo.getStoreId();
            // 接收参数 // 链接
            String url = vo.getUrl();
            // 支付类名称
            String type = vo.getPayClassName();
            // 返回参数
            Map<String, Object> tmpMap = new HashMap<>(16);
            tmpMap.put("config", "");
            tmpMap.put("url", url);
            if (!StringUtils.isEmpty(type)) {
                String config = paymentConfigModelMapper.getPaymentConfigInfo(store_id, type);
                if (!StringUtils.isEmpty(config)) {
                    config = config.replaceAll("%2B", "\\+");
                    tmpMap.put("config", JSON.parse(config));
                }
            }
            resultMap.put("data", tmpMap);
            resultMap.put("code", 200);
            resultMap.put("message", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "orderSearch");
        }
        return resultMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> orderDetails(OrderVo vo) {
        Map<String, Object> resultMap;
        try {
            resultMap = publicOrderService.ucOrderDetails(vo);
        } catch (LaiKeApiException e) {
            logger.error("订单详情 异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("订单详情异常", e);
            throw new LaiKeApiException(ILLEGAL_INVASION, "非法入侵", "orderDetails");
        }
        return resultMap;
    }

    /**
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> showLogistics(OrderVo vo) {
        Map<String, Object> map;
        try {
            map = publicOrderService.getLogistics(vo.getsNo());
        } catch (Exception e) {
            logger.error("物流获取异常", e);
            throw new LaiKeApiException(ILLEGAL_INVASION, "非法入侵", "showLogistics");
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> cancleApply(int storeId, int id) {
        Map map = new HashMap();
        try {
            ReturnOrderModel returnOrderModel = new ReturnOrderModel();
            returnOrderModel.setId(id);
            returnOrderModel.setStore_id(storeId);
            int row = returnOrderModelMapper.delete(returnOrderModel);
            if (row > 0) {
                map.put("code", 200);
            } else {
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "cancleApply");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "cancleApply");
        }
        return map;
    }

    @Override
    public Map<String, Object> returnOrderList(OrderVo vo) {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Map<String, Object>> product = new ArrayList<>();
        try {
            int storeId = vo.getStoreId();
            String keyword = vo.getKeyword();
            User user;
            if (vo.getUser() != null) {
                user = vo.getUser();
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil, true);
            }
            String userId = user.getUser_id();

            Map<String, Object> params = new HashMap<>(16);
            params.put("keyword", keyword);
            params.put("userId", userId);
            params.put("storeId", storeId);
            if (vo.getOrderId() != 0) {
                params.put("returnOrderId", vo.getOrderId());
            }
            params.put("start", vo.getPageNo());
            params.put("pageSize", vo.getPageSize());
            List<Map<String, Object>> list = returnOrderModelMapper.getReturnOrderList(params);
            if (!CollectionUtils.isEmpty(list)) {
                Map<String, Object> arr;
                for (Map<String, Object> returnOrderInfo : list) {
                    int p_id = DataUtils.getIntegerVal(returnOrderInfo, "goodsId");
                    returnOrderInfo.put("pid", p_id);
                    String r_sNo = DataUtils.getStringVal(returnOrderInfo, "sNo");
                    OrderModel orderModel = new OrderModel();
                    orderModel.setStore_id(storeId);
                    orderModel.setsNo(r_sNo);
                    orderModel = orderModelMapper.selectOne(orderModel);
                    if (orderModel == null) {
                        logger.warn("订单号不存在：{}", r_sNo);
                        continue;
                    }
                    int orderId = orderModel.getId();
                    arr = returnOrderInfo;
                    // 根据产品id,查询产
                    ProductListModel productListModel = new ProductListModel();
                    productListModel.setId(p_id);
                    productListModel = productListModelMapper.selectOne(productListModel);
                    String url = "";
                    Integer mchid = 0;
                    if (productListModel != null) {
                        // 拼图片路径
                        url = publiceService.getImgPath(productListModel.getImgurl(), storeId);
                        mchid = productListModel.getMch_id();
                    }

                    arr.put("shop_id", 0);
                    arr.put("shop_name", "");
                    arr.put("shop_logo", "");
                    if (mchid != null && mchid != 0) {
                        MchModel mchModel = new MchModel();
                        mchModel.setId(mchid);
                        mchModel = mchModelMapper.selectOne(mchModel);
                        if (mchModel != null) {
                            arr.put("shop_id", mchModel.getId());
                            arr.put("shop_name", mchModel.getName());
                            arr.put("shop_logo", publiceService.getImgPath(mchModel.getLogo(), storeId));
                        }
                    }
                    arr.put("order_id", orderId);
                    arr.put("imgurl", url);
                    String returnStatus = publicRefundService.getRefundStatus(vo.getStoreId(), r_sNo);
                    arr.put("prompt", returnStatus);
                    product.add(arr);
                }
                resultMap.put("list", product);
            } else {
                resultMap.put("list", new ArrayList<>());
            }
            resultMap.put("message", "操作成功");
            resultMap.put("code", 200);
        } catch (LaiKeApiException l) {
            logger.error("获取售后列表 异常:", l);
            throw l;
        } catch (Exception e) {
            logger.error("获取售后列表 异常:", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "returnOrderList");
        }
        return resultMap;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> okOrder(OrderVo orderVo) {
        try {
            return publicOrderService.okOrder(orderVo.getStoreId(), orderVo.getAccessId(), orderVo.getsNo(), orderVo.getRtype());
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "okOrder");
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Map<String, Object> returnData(ApplyReturnDataVo vo, MultipartFile file) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil, true);
            //商品图片集
            List<String> goodsNames = new ArrayList<>();
            List<String> imgUlrs = new ArrayList<>();
            if (file != null) {
                if (vo.getUploadNum() == 0) {
                    //清空缓存
                    redisUtil.del(GloabConst.RedisHeaderKey.RETURN_UPLOAD_KEY + vo.getOrderDetailsId());
                }
                //获取之前的图片
                Object obj = redisUtil.get(GloabConst.RedisHeaderKey.RETURN_UPLOAD_KEY + vo.getOrderDetailsId());
                if (obj != null) {
                    imgUlrs = DataUtils.cast(obj);
                    if (imgUlrs == null) {
                        imgUlrs = new ArrayList<>();
                    }
                }
                List<MultipartFile> files = new ArrayList<>();
                files.add(file);
                //默认使用oos
                String uploadType = GloabConst.UploadConfigConst.IMG_UPLOAD_OSS;
                //获取店铺信息
                /*ConfigModel configModel = new ConfigModel();
                configModel.setStore_id(vo.getStoreId());
                configModel = configModelMapper.selectOne(configModel);
                if (configModel != null) {
                    uploadType = configModel.getUpserver();
                }*/
                //图片上传
                List<String> urls = publiceService.uploadImage(files, uploadType, vo.getStoreType(), vo.getStoreId());
                if (urls.size() != files.size()) {
                    logger.info(String.format("图片上传失败 需上传:%s 实际上传:%s", files.size(), imgUlrs.size()));
                    throw new LaiKeApiException(ErrorCode.SysErrorCode.UPLOAD_FAIL, "图片上传失败", "returnData");
                }
                imgUlrs.add(ImgUploadUtils.getUrlImgByName(urls.get(0), true));
                redisUtil.set(GloabConst.RedisHeaderKey.RETURN_UPLOAD_KEY + vo.getOrderDetailsId(), imgUlrs, 10);
                if (vo.getUploadMaxNum() != vo.getUploadNum() + 1) {
                    return resultMap;
                }
            }
            //清空缓存
            redisUtil.del(GloabConst.RedisHeaderKey.RETURN_UPLOAD_KEY + vo.getOrderDetailsId());

            //支持批量售后
            List<String> detailIds = DataUtils.convertToList(vo.getOrderDetailsId().split(","));
            if (detailIds == null) {
                throw new LaiKeApiException(PARAMATER_ERROR, "参数错误");
            }
            //获取订单信息
            OrderModel orderModel = null;
            for (String did : detailIds) {
                int detailId = Integer.parseInt(did);

                //获取订单商品信息
                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
                orderDetailsModel.setStore_id(vo.getStoreId());
                orderDetailsModel.setUser_id(user.getUser_id());
                orderDetailsModel.setId(detailId);
                orderDetailsModel = orderDetailsModelMapper.selectOne(orderDetailsModel);
                if (orderDetailsModel == null) {
                    throw new LaiKeApiException(DATA_ILLEGAL_FAIL, "订单信息不存在", "returnData");
                }
                //是否达到售后上限
                ReturnOrderModel returnOrderCount = new ReturnOrderModel();
                returnOrderCount.setP_id(detailId);
                returnOrderCount.setStore_id(vo.getStoreId());
                returnOrderCount.setUser_id(user.getUser_id());
                returnOrderCount.setsNo(orderDetailsModel.getR_sNo());
                if (returnOrderModelMapper.selectCount(returnOrderCount) >= GloabConst.LktConfig.RETURN_ORDER_LIMIT) {
                    logger.debug("订单详情id{} 以达到申请限制", detailId);
                    throw new LaiKeApiException(PARAMATER_ERROR, "以达到售后申请限制");
                }

                goodsNames.add(orderDetailsModel.getP_name());
                //获取订单主表信息
                if (orderModel == null) {
                    orderModel = new OrderModel();
                    orderModel.setsNo(orderDetailsModel.getR_sNo());
                    orderModel = orderModelMapper.selectOne(orderModel);
                    if (orderModel == null) {
                        throw new LaiKeApiException(DATA_ILLEGAL_FAIL, "订单信息不存在", "returnData");
                    }
                }
                //添加售后订单信息
                ReturnOrderModel returnOrderModel = new ReturnOrderModel();
                returnOrderModel.setStore_id(vo.getStoreId());
                returnOrderModel.setUser_id(user.getUser_id());
                returnOrderModel.setsNo(orderDetailsModel.getR_sNo());
                returnOrderModel.setP_id(orderDetailsModel.getId());
                returnOrderModel.setPid(orderDetailsModel.getP_id());
                //退款类型 1:退货退款  2:退款 3:换货
                returnOrderModel.setRe_type(vo.getType());
                String content = vo.getExplain();
                if (!StringUtils.isEmpty(content)) {
                    content = URLDecoder.decode(content, CharEncoding.UTF_8);
                }
                returnOrderModel.setContent(content);
                returnOrderModel.setR_type(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_EXAMEWAIT_STATUS);
                returnOrderModel.setSid(Integer.parseInt(orderDetailsModel.getSid()));
                //如果是换货 不需要退款金额
                if (!DictionaryConst.ReturnRecordReType.RETURNORDERSTATUS_GOODS_REBACK.equals(vo.getType())) {
                    returnOrderModel.setRe_apply_money(vo.getRefundApplyMoney().floatValue());
                    returnOrderModel.setRe_money(vo.getRefundAmount().floatValue());
                    vo.setRefundApplyMoney(new BigDecimal("0"));
                } else {
                    //验证金额
                    if (vo.getRefundApplyMoney().compareTo(orderModel.getZ_price()) > 0) {
                        throw new LaiKeApiException(DATA_ILLEGAL_FAIL, "金额不能大于当前订单总金额", "returnData");
                    }
                }
                //记录凭证
                String phpImgUrlSerialize = "";
                if (imgUlrs.size() > 0) {
                    phpImgUrlSerialize = SerializePhpUtils.JavaSerializeByPhp(imgUlrs);
                }
                returnOrderModel.setRe_photo(phpImgUrlSerialize);
                returnOrderModel.setRe_time(new Date());

                int count = returnOrderModelMapper.insertSelective(returnOrderModel);
                if (count < 1) {
                    throw new LaiKeApiException(DATA_ILLEGAL_FAIL, "生成售后订单失败", "returnData");
                }
                //记录本次售后情况
                ReturnRecordModel returnRecordModel = new ReturnRecordModel();
                returnRecordModel.setUser_id(user.getUser_id());
                returnRecordModel.setStore_id(vo.getStoreId());
                returnRecordModel.setRe_type(vo.getType());
                returnRecordModel.setR_type(DictionaryConst.ReturnOrderStatus.RETURNORDERSTATUS_EXAMEWAIT_STATUS);
                returnRecordModel.setsNo(orderDetailsModel.getR_sNo());
                returnRecordModel.setP_id(orderDetailsModel.getId());
                returnRecordModel.setMoney(vo.getRefundApplyMoney());
                returnRecordModel.setRe_photo(phpImgUrlSerialize);
                returnRecordModel.setProduct_id(orderDetailsModel.getP_id());
                returnRecordModel.setAttr_id(Integer.parseInt(orderDetailsModel.getSid()));
                returnRecordModel.setRe_time(new Date());
                count = returnRecordModelMapper.insertSelective(returnRecordModel);
                if (count < 1) {
                    throw new LaiKeApiException(DATA_ILLEGAL_FAIL, "网络故障,售后记录失败", "returnData");
                }
                //记录订单改变之前的状态
                RecordModel recordModel = new RecordModel();
                recordModel.setStore_id(vo.getStoreId());
                recordModel.setUser_id(user.getUser_id());
                Map<String, Object> tempMap = new HashMap<>(16);
                tempMap.put("r_sNo", orderDetailsModel.getR_sNo());
                tempMap.put("r_status", orderDetailsModel.getR_status());
                tempMap.put("order_details_id", orderDetailsModel.getId());
                recordModel.setEvent(JSON.toJSONString(tempMap));
                count = recordModelMapper.insertSelective(recordModel);
                if (count < 1) {
                    logger.info(String.format("订单%s,操作记录失败", orderDetailsModel.getR_sNo()));
                }
            }
            if (orderModel == null) {
                throw new LaiKeApiException(DATA_ILLEGAL_FAIL, "订单信息不存在", "returnData");
            }

            resultMap.put("product_title", goodsNames);
            resultMap.put("sNo", orderModel.getsNo());
            resultMap.put("time", DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMDHMS));
            resultMap.put("refund_amount", vo.getRefundApplyMoney());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("申请售后 异常", e);
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常", "returnData");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> returnMethod(OrderVo vo) {
        logger.info("售后信息：{}", JSONObject.toJSON(vo));
        try {
            // 商城id
            int storeId = vo.getStoreId();
            // 订单详情id
            String orderDetailsId = vo.getOrderDetailsId();
            // 根据微信id,查询用户id
            User user;
            if (vo.getUser() != null) {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil, GloabConst.RedisHeaderKey.LOGIN_ACCESS_PC_SHOP_TOKEN, true);
            } else {
                user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            }
            BigDecimal refundPrice = BigDecimal.ZERO;
            List<Map<String, Object>> list = new ArrayList<>();
            String[] orderDetaisdIds = new String[0];
            Map<Integer, Object> rStatus = new HashMap<>();
            // 仅退款状态
            boolean refundOnlyStatus = false;
            // 退货退款状态
            boolean returnRefundStatus = false;
            // 换货状态
            boolean exchangeGoodsStatus = false;
            //
            boolean status = false;
            if (StringUtils.isNotEmpty(orderDetailsId)) {
                // 是字符串 移除两侧的逗号
                orderDetaisdIds = com.laiketui.root.utils.tool.StringUtils.trim(orderDetailsId, SplitUtils.DH).split(SplitUtils.DH);
            }
            // 同一订单下面多个自订单
            String sNo = "";
            Integer exchangeNum = 0;
            for (String orderDetailsIdStr : orderDetaisdIds) {
                int did = Integer.parseInt(orderDetailsIdStr);
                OrderDetailsModel orderDetails = new OrderDetailsModel();
                orderDetails.setStore_id(storeId);
                orderDetails.setUser_id(user.getUser_id());
                orderDetails.setId(did);
                orderDetails = orderDetailsModelMapper.selectOne(orderDetails);
                if (orderDetails != null) {
                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(orderDetails));
                    rStatus.put(orderDetails.getR_status(), orderDetails.getR_status());
                    sNo = orderDetails.getR_sNo();
                    String attributeId = orderDetails.getSid();
                    int sid = 0;
                    if (!StringUtils.isEmpty(attributeId)) {
                        sid = Integer.parseInt(attributeId);
                    }
                    exchangeNum = orderDetails.getExchange_num();
                    refundPrice = refundPrice.add(publicOrderService.getOrderPrice(did, storeId));
                    ConfiGureModel confiGureModel = confiGureModelMapper.selectByPrimaryKey(sid);
                    if (confiGureModel != null) {
                        jsonObject.put("image", publiceService.getImgPath(confiGureModel.getImg(), storeId));
                        if (exchangeNum == null || exchangeNum < 2) {
                            list.add(jsonObject.getInnerMap());
                        }
                    }
                }
            }

            OrderModel orderModel = new OrderModel();
            orderModel.setStore_id(storeId);
            orderModel.setsNo(sNo);
            orderModel = orderModelMapper.selectOne(orderModel);
            Integer selfLifting;
            String orderType = null;
            if (orderModel != null) {
                selfLifting = orderModel.getSelf_lifting();
                orderType = orderModel.getOtype();
            } else {
                selfLifting = 0;
            }

            if (selfLifting == 0) {
                if (rStatus.containsKey(1) &&
                        !DictionaryConst.OrdersType.ORDERS_HEADER_JP.equals(orderType) &&
                        !DictionaryConst.OrdersType.ORDERS_HEADER_KJ.equals(orderType)) {
                    // 可以退款
                    refundOnlyStatus = true;
                } else {
                    if ((exchangeNum == null || 0 == exchangeNum) &&
                            !DictionaryConst.OrdersType.ORDERS_HEADER_JP.equals(orderType) &&
                            !DictionaryConst.OrdersType.ORDERS_HEADER_KJ.equals(orderType)) {
                        // 可以退款
                        refundOnlyStatus = true;
                        // 可以退货退款
                        returnRefundStatus = true;
                    }
                    exchangeGoodsStatus = true;
                }
            } else {
                // 可以退款
                refundOnlyStatus = true;
            }

            for (Integer rstatustmp : rStatus.keySet()) {
                if (rstatustmp == 2) {
                    status = true;
                    break;
                }
            }

            Map<String, Object> data = Maps.newHashMap();
            data.put("orderType", orderType);
            data.put("refund_price", refundPrice);
            data.put("self_lifting", selfLifting);
            data.put("list", list);
            data.put("status", status);
            data.put("refund_only_status", refundOnlyStatus);
            data.put("return_refund_status", returnRefundStatus);
            data.put("exchange_goods_status", exchangeGoodsStatus);
            return data;
        } catch (Exception e) {
            logger.error("获取售后信息 异常:", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "returnMethod");
        }
    }


    @Override
    public Map<String, Object> seeSend(int storeId, int productId) {
        Map<String, Object> returnMap = new HashMap<>(16);
        try {
            String address = "";
            String name = "";
            String phone = "";
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setId(productId);
            // 根据商品id，查询商品信息
            productListModel = productListModelMapper.selectOne(productListModel);

            if (productListModel == null) {
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "seeSend");
            }

            // 店铺ID
            int mchId = productListModel.getMch_id();
            // 根据店铺ID，查询管理员信息
            AdminModel adminModel = new AdminModel();
            adminModel.setShop_id(mchId);
            adminModel.setStore_id(storeId);
            List<AdminModel> adminModels = adminModelMapper.select(adminModel);
            if (adminModels != null && adminModels.size() > 0) {
                ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
                serviceAddressModel.setStore_id(storeId);
                serviceAddressModel.setUid("admin");
                serviceAddressModel.setIs_default(1);
                serviceAddressModel.setType(2);
                serviceAddressModel = serviceAddressModelMapper.selectOne(serviceAddressModel);
                if (serviceAddressModel != null) {
                    address = serviceAddressModel.getAddress_xq();
                    name = serviceAddressModel.getName();
                    phone = serviceAddressModel.getTel();
                }
            } else {
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(storeId);
                mchModel.setId(mchId);
                mchModel = mchModelMapper.selectOne(mchModel);
                if (mchModel != null) {
                    address = mchModel.getSheng() + mchModel.getShi() + mchModel.getXian() + mchModel.getAddress();
                    name = mchModel.getName();
                    phone = mchModel.getTel();
                }
            }

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("sort_sort", DataUtils.Sort.DESC.toString());
            List<Map<String, Object>> list = expressModelMapper.selectDynamic(parmaMap);

            returnMap.put("code", 200);
            returnMap.put("address", address);
            returnMap.put("name", name);
            returnMap.put("phone", phone);
            returnMap.put("express", list);
            returnMap.put("message", "操作成功");
            return returnMap;
        } catch (Exception e) {
            logger.error("获取售后地址和快递信息异常 ", e);
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常", "returnData");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> backSend(ReturnGoodsVo returnGoodsVo) {
        Map returnMap = new HashMap(16);
        try {
            User user = RedisDataTool.getRedisUserCache(returnGoodsVo.getAccessId(), redisUtil);
            // 根据微信id,查询用户id
            String userId = user.getUser_id();

            ReturnOrderModel returnOrderModel = new ReturnOrderModel();
            returnOrderModel.setStore_id(returnGoodsVo.getStoreId());
            returnOrderModel.setId(returnGoodsVo.getId());
            returnOrderModel = returnOrderModelMapper.selectOne(returnOrderModel);
            Integer oid = returnOrderModel.getP_id();

            ReturnGoodsModel returnGoodsModel = new ReturnGoodsModel();
            returnGoodsModel.setStore_id(returnGoodsVo.getStoreId());
            returnGoodsModel.setName(returnGoodsVo.getLxr());
            returnGoodsModel.setTel(returnGoodsVo.getLxdh());
            returnGoodsModel.setExpress(returnGoodsVo.getKdname());
            returnGoodsModel.setExpress_num(returnGoodsVo.getKdcode());
            returnGoodsModel.setUser_id(userId);
            returnGoodsModel.setUid(userId);
            returnGoodsModel.setOid(oid + "");
            returnGoodsModel.setAdd_data(new Date());
            returnGoodsModel.setRe_id(returnGoodsVo.getId());
            int row = returnGoodsModelMapper.insert(returnGoodsModel);
            if (row < 1) {
                logger.error("添加回退物品信息失败.");
            }
            // rtype :类型 100:不在退货退款状态0:审核中 1:同意并让用户寄回 2:拒绝(退货退款) 3:用户已快递 4:收到寄回商品,同意并退款
            // 5：拒绝并退回商品 8:拒绝(退款) 9:同意并退款 10:拒绝(售后)11:同意并且寄回商品 12售后结束
            row = returnOrderModelMapper.updateReturnOrder(returnGoodsVo.getStoreId(), returnGoodsVo.getId(), 3);
            if (row >= 0) {
                returnMap.put("code", 200);
                returnMap.put("message", "操作成功");
                return returnMap;
            } else {
                row = returnOrderModelMapper.updateReturnOrder(returnGoodsVo.getStoreId(), returnGoodsVo.getId(), 1);
                if (row < 0) {
                    logger.error("修改售后订单失败！");
                }
                returnGoodsModelMapper.deleteByPrimaryKey(returnGoodsModel);
                throw new LaiKeApiException(BUSY_NETWORK, "网络异常", "returnData");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取售后地址和快递信息异常 :{}", e.getMessage());
            throw new LaiKeApiException(BUSY_NETWORK, "网络异常", "returnData");
        }
    }

    /**
     * 查看提货码
     *
     * @param vo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> seeExtractionCode(OrderVo vo) {
        Map resultMap = Maps.newHashMap();
        try {
            int storeId = vo.getStoreId();
            // 订单id
            int orderId = vo.getOrderId();
            // 根据微信id,查询用户id
            User user = RedisDataTool.getRedisUserCache(vo.getAccessId(), redisUtil);
            String userId = user.getUser_id();
            Map map = new HashMap();
            map.put("store_id", storeId);
            map.put("id", orderId);
            List<Map<String, Object>> orderInfoList = orderModelMapper.getOrderInfoLeftDetailDynamic(map);
            if (CollectionUtils.isEmpty(orderInfoList)) {
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "seeExtractionCode");
            }
            Map<String, Object> orderInfo = orderInfoList.get(0);
            // 提货码
            String extractionCode = DataUtils.getStringVal(orderInfo, "extraction_code");
            String extractionCodeImg = null;
            String[] rew = extractionCode.split(SplitUtils.DH);
            // 订单状态
            int status = DataUtils.getIntegerVal(orderInfo, "status");
            // 总价
            BigDecimal zPrice = DataUtils.getBigDecimalVal(orderInfo, "z_price");
            // 订单号
            String sNo = DataUtils.getStringVal(orderInfo, "sNo");
            // 待收货
            if (status == ORDERS_R_STATUS_DISPATCHED) {
                if (rew.length != 3) {
                    // 店铺
                    String extraction_code1 = publicMchService.extractionCode();
                    String[] extraction_code2 = extraction_code1.split(SplitUtils.DH);
                    extractionCode = extraction_code2[0];
                    extractionCodeImg = publicMchService.createQRCodeImg(extraction_code1, storeId, vo.getStoreType());
                } else {
                    long time = System.currentTimeMillis() / 1000;
                    if (Long.valueOf(rew[2]) <= time) {
                        // 提货码有效时间 小于等于 当前时间
                        String extraction_code1 = publicMchService.extractionCode();
                        String[] extraction_code2 = extraction_code1.split(SplitUtils.DH);
                        extractionCode = extraction_code2[0];
                        extractionCodeImg = publicMchService.createQRCodeImg(extraction_code1, storeId, vo.getStoreType());
                    } else {
                        // 提货码
                        extractionCode = rew[0];
                        // 提现码二维码
                        extractionCodeImg = DataUtils.getStringVal(orderInfo, "extraction_code_img");
                    }
                }
            } else {
                // 提货码
                extractionCode = rew[0];
                // 提现码二维码
                extractionCodeImg = DataUtils.getStringVal(orderInfo, "extraction_code_img");
            }
            int num = 0;
            // 商品ID
            int productId = DataUtils.getIntegerVal(orderInfo, "p_id");
            map.clear();
            map.put("store_id", storeId);
            map.put("user_id", userId);
            map.put("id", orderId);
            List<Map<String, Object>> orderCodesInfo = orderModelMapper.seeExtractionCode(map);
            List<Map<String, Object>> products = new ArrayList<>();
            for (int i = 0; i < orderCodesInfo.size(); i++) {
                Map<String, Object> productMap = orderCodesInfo.get(i);
                // 商品ID
                int proId = MapUtils.getInteger(productMap, "p_id");
                // 商品规格id
                int productSid = MapUtils.getInteger(productMap, "sid");
                Map params = new HashMap();
                params.put("store_id", storeId);
                params.put("sid", productSid);
                params.put("p_id", proId);
                List<Map<String, Object>> productInfoList = productListModelMapper.getGoodsTitleAndImg(params);
                if (CollectionUtils.isEmpty(productInfoList)) {
                    throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "seeExtractionCode");
                }
                Map<String, Object> productInfoMap = productInfoList.get(0);
                // 商品名称
                String product_title = DataUtils.getStringVal(productInfoMap, "product_title");
                // 拼图片路径
                String img = publiceService.getImgPath(DataUtils.getStringVal(productInfoMap, "img"), storeId);
                num = num + MapUtils.getInteger(productMap, "num");
                Map productRetMap = new HashMap();
                productRetMap.put("p_id", productId);
                productRetMap.put("product_title", product_title);
                productRetMap.put("p_price", DataUtils.getBigDecimalVal(productMap, "p_price", BigDecimal.ZERO));
                productRetMap.put("num", MapUtils.getInteger(productMap, "num"));
                productRetMap.put("sid", MapUtils.getInteger(productMap, "sid"));
                productRetMap.put("size", DataUtils.getStringVal(productMap, "size"));
                productRetMap.put("img", img);
                products.add(productRetMap);
            }
            Map data = Maps.newHashMap();
            data.put("status", status);
            data.put("extraction_code", extractionCode);
            data.put("extraction_code_img", extractionCodeImg);
            data.put("por_list", products);
            data.put("z_price", zPrice);
            data.put("sNo", sNo);
            data.put("num", num);

            return data;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查看提货码异常:{}", e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "seeExtractionCode");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> returndetails(RefundDetailsVo refundDetailsVo) {
        Map resultMap = Maps.newHashMap();
        List<ReturnOrderModel> returnOrderList = new ArrayList<>();
        try {
            // 售后订单id
            int id = refundDetailsVo.getId();
            int pid = refundDetailsVo.getPid();
            int storeId = refundDetailsVo.getStoreId();

            //查询详情订单信息
            Map returnOrder = returnOrderModelMapper.getReturnOrderMap(storeId, id);
            if (returnOrder == null) {
                throw new LaiKeApiException(ABNORMAL_BIZ, "网络异常", "returnData");
            }
            //订单详情ID
            int details_id = DataUtils.getIntegerVal(returnOrder, "p_id");
            ReturnGoodsModel send_info = new ReturnGoodsModel();
            ReturnGoodsModel return_info = new ReturnGoodsModel();
            //查询买家回寄信息
            ReturnGoodsModel returnGoodsModel = new ReturnGoodsModel();
            returnGoodsModel.setOid(details_id + "");
            returnGoodsModel.setStore_id(storeId);
            List<ReturnGoodsModel> returnGoodsModelList = returnGoodsModelMapper.select(returnGoodsModel);
            if (returnGoodsModelList != null && returnGoodsModelList.size() > 0) {
                send_info = returnGoodsModelList.get(0);
                if (returnGoodsModelList.size() > 1) {
                    //查询卖家退换信息
                    return_info = returnGoodsModelList.get(1);
                }
            }

            Map info = Maps.newHashMap();
            //退款信息
            //申请时间
            info.put("re_time", DateUtil.dateFormate((Date) returnOrder.get("re_time"), GloabConst.TimePattern.YMDHMS));
            //退款金额
            BigDecimal p_price = DataUtils.getBigDecimalVal(returnOrder, "real_money", BigDecimal.ZERO);
            info.put("p_price", p_price);
            if (DataUtils.equalBigDecimalZero(p_price)) {
                info.put("p_price", DataUtils.getBigDecimalVal(returnOrder, "re_apply_money", BigDecimal.ZERO));
            }

            //拒绝原因
            info.put("r_content", returnOrder.get("r_content"));
            //售后类型1
            info.put("re_type", returnOrder.get("re_type"));
            //售后商品名称
            info.put("p_name", returnOrder.get("p_name"));
            //售后订单
            info.put("r_sNo", returnOrder.get("sNo"));
            //售后类型
            info.put("type", returnOrder.get("r_type"));
            //退货原因
            info.put("content", returnOrder.get("content"));
            //凭证
            String re_photo = DataUtils.getStringVal(returnOrder, "re_photo", "");
            List<String> imagesList = new ArrayList<>();
            if (!StringUtils.isEmpty(re_photo)) {
                Map<Integer, String> photosMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(re_photo, Map.class));
                if (photosMap != null) {
                    for (Integer key : photosMap.keySet()) {
                        String img = photosMap.get(key);
                        // 获取图片路径
                        imagesList.add(publiceService.getImgPath(img, storeId));
                    }
                }
            }
            info.put("re_photo", imagesList.size() == 0 ? null : imagesList);

            // 根据商品id，查询商品信息
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setId(pid);

            String address = "";
            String name = "";
            String phone = "";

            //r0
            productListModel = productListModelMapper.selectOne(productListModel);
            if (productListModel != null) {
                // 店铺ID
                Integer mch_id = productListModel.getMch_id();
                // 根据店铺ID，查询管理员信息
                AdminModel adminModel = new AdminModel();
                adminModel.setStore_id(storeId);
                adminModel.setShop_id(mch_id);
                List<AdminModel> adminModels = adminModelMapper.select(adminModel);
                if (adminModels != null) {
                    // 存在，代表是自营商品
                    // 获取信息
                    ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
                    serviceAddressModel.setStore_id(storeId);
                    serviceAddressModel.setUid("admin");
                    // 0不是 1默认
                    serviceAddressModel.setIs_default(1);
                    // 1发货地址 2售后地址
                    serviceAddressModel.setType(2);
                    serviceAddressModel = serviceAddressModelMapper.selectOne(serviceAddressModel);
                    address = serviceAddressModel.getAddress_xq();
                    name = serviceAddressModel.getName();
                    phone = serviceAddressModel.getTel();
                } else {
                    MchModel mchModel = new MchModel();
                    mchModel.setStore_id(storeId);
                    mchModel.setId(mch_id);
                    mchModel = mchModelMapper.selectOne(mchModel);
                    if (mchModel != null) {
                        address = mchModel.getSheng() + mchModel.getShi() + mchModel.getAddress();
                        name = mchModel.getRealname();
                        phone = mchModel.getTel();
                    } else {
                        logger.info("获取店铺{}失败", mch_id);
                        throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "returndetails");
                    }
                }

                //查询售后记录
                ReturnOrderModel returnOrderModel = new ReturnOrderModel();
                returnOrderModel.setP_id(pid);
                returnOrderModel.setStore_id(storeId);
                List<ReturnOrderModel> returnOrderModelList = returnOrderModelMapper.select(returnOrderModel);

                if (!CollectionUtils.isEmpty(returnOrderModelList)) {
                    int pos = 0;
                    for (ReturnOrderModel returnOrderTmp : returnOrderModelList) {
                        if (pos < returnOrderModelList.size() - 1) {
                            returnOrderList.add(returnOrderTmp);
                        }
                    }
                }
            } else {
                logger.info("获取商品信息失败，{}", pid);
                throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "returndetails");
            }
            Map store_info = Maps.newHashMap();
            store_info.put("address", address);
            store_info.put("name", name);
            store_info.put("phone", phone);
            resultMap.put("code", 200);
            resultMap.put("message", "操作成功");
            resultMap.put("info", info);
            resultMap.put("store_info", store_info);
            resultMap.put("record", returnOrderList);
            resultMap.put("send_info", send_info);
            resultMap.put("return_info", return_info);
        } catch (Exception e) {
            logger.error(" 获取售后订单详情失败：", e);
            throw new LaiKeApiException(ABNORMAL_BIZ, "业务异常", "returndetails");
        }
        return resultMap;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> confirmReceipt(ReturnConfirmReceiptVo vo) {
        Map resultMap = Maps.newHashMap();
        try {
            // 商城ID
            int storeId = vo.getStoreId();
            //售后订单ID
            int id = vo.getId();
            String access_id = vo.getAccessId();
            User user = RedisDataTool.getRedisUserCache(access_id, redisUtil);
            String userId = user.getUser_id();
            ReturnOrderModel returnOrderModel = new ReturnOrderModel();
            returnOrderModel.setStore_id(storeId);
            returnOrderModel.setId(id);
            returnOrderModel = returnOrderModelMapper.selectOne(returnOrderModel);
            int exchange_num = 0;
            int oid = 0;
            if (returnOrderModel != null) {
                //订单详情ID
                oid = returnOrderModel.getP_id();
                //订单号
//                String sNo = returnOrderModel.getsNo();
                OrderDetailsModel orderDetailsModel1 = new OrderDetailsModel();
                orderDetailsModel1.setStore_id(storeId);
                orderDetailsModel1.setUser_id(userId);
                orderDetailsModel1.setId(oid);
//                orderDetailsModel1.setR_sNo(sNo);
                orderDetailsModel1 = orderDetailsModelMapper.selectOne(orderDetailsModel1);
                if (orderDetailsModel1 != null) {
                    exchange_num = orderDetailsModel1.getExchange_num() + 1;
                }
            } else {
                logger.error("售后确认收货异常");
                throw new LaiKeApiException(ABNORMAL_BIZ, "售后确认收货异常", "confirmReceipt");
            }

            int row = returnOrderModelMapper.updateReturnOrder(storeId, id, 12);
            if (row < 0) {
                throw new LaiKeApiException(ABNORMAL_BIZ, "售后确认收货异常", "confirmReceipt");
            }

            row = orderDetailsModelMapper.confirmReceiptOrderDetaisl(exchange_num, storeId, userId, oid);
            if (row < 0) {
                throw new LaiKeApiException(ABNORMAL_BIZ, "售后确认收货异常", "confirmReceipt");
            } else {
                resultMap.put("code", 200);
                resultMap.put("message", "操作成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("售后确认收货异常", e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "售后确认收货异常", "confirmReceipt");
        }

        return resultMap;
    }

    /**
     * 撤销申请
     *
     * @param vo
     * @return
     */
    @Override
    public Map<String, Object> cancelApplication(CancleAfterSaleApplyVo vo) {
        Map resultMap = Maps.newHashMap();
        try {
            int id = vo.getId();
            ReturnOrderModel returnOrderModel = new ReturnOrderModel();
            returnOrderModel.setId(id);
            returnOrderModelMapper.delete(returnOrderModel);
            //操作成功
            resultMap.put("code", "200");
            resultMap.put("message", "撤销成功");
            return resultMap;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("售后确认收货异常", e.getMessage());
            throw new LaiKeApiException(ABNORMAL_BIZ, "撤销审核失败", "cancelApplication");
        }
    }


}
