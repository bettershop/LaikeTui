package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.coupon.CouponActivityModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.MchModel;
import com.laiketui.domain.mch.MchStoreModel;
import com.laiketui.domain.product.FreightModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.order.OrderModifyVo;
import com.laiketui.domain.vo.plugin.auction.AddAuctionVo;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.algorithm.CryptoUtil;
import com.laiketui.root.utils.algorithm.Md5Util;
import com.laiketui.root.utils.algorithm.MobileUtils;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.jwt.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 数据检查工具
 *
 * @author Trick
 * @date 2020/9/29 9:07
 */
public class DataCheckTool {

    private static final Logger logger = LoggerFactory.getLogger(DataCheckTool.class);

    /**
     * 对token进行验证签名
     *
     * @param configModel -
     * @param user        -
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/12 10:03
     */
    public static boolean verifyToken(ConfigModel configModel, User user) throws LaiKeApiException {
        try {
            if (configModel != null) {
                //当注册为免注册，并且来源为小程序
                if (GloabConst.LktConfig.REGISTER_TYPE2.equals(configModel.getIs_register()) && DictionaryConst.StoreSource.LKT_LY_002.equals(user.getSource())) {
                    return true;
                } else {
                    Claims resultMap = JwtUtils.verifyJwt(user.getAccess_id());
                    try {
                        if (!resultMap.isEmpty()) {
                            return true;
                        }
                    } catch (LaiKeApiException le) {
                        return false;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("token校验出现异常 :" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙", "checkNumAndLetter");
        }
        return false;
    }

    /**
     * 数字和英文检查
     *
     * @param str  需要验证的字符串
     * @param less 最少长度
     * @return many 最多长度
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 9:10
     */
    public static boolean checkNumAndLetter(String str, Integer less, Integer many) throws LaiKeApiException {
        try {
            String regex = String.format("[a-zA-Z\\d]{%d,%d}", less, many);
            if (Pattern.matches(regex, str)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙", "checkNumAndLetter");
        }
        return false;
    }

    /**
     * 检查字符串长度
     *
     * @param str  需要验证的字符串
     * @param less 最少长度
     * @return many 最多长度
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/30 15:10
     */
    public static boolean checkLength(String str, Integer less, Integer many) throws LaiKeApiException {
        try {
            str = str.trim();
            int length = str.length();
            if (length >= less && length <= many) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙", "checkNumAndLetter");
        }
        return false;
    }


    /**
     * 验证身份证 格式
     *
     * @param idcard - 身份证号
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/30 11:30
     */
    public static boolean checkIdCard(String idcard) throws LaiKeApiException {
        try {
            if (CheckIdCard.check(idcard)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙", "checkNumAndLetter");
        }
        return false;
    }


    /**
     * 验证图片格式
     *
     * @param fileName - 文件名
     * @return boolean
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 17:25
     */
    public static boolean checkUploadImgageFormate(String fileName) throws LaiKeApiException {
        if (!StringUtils.isEmpty(fileName)) {
            //获取文件后缀名
            String type = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".") + 1) : null;
            if (type != null) {
                if (GloabConst.UploadConfigConst.IMG_GIF.equals(type.toUpperCase())
                        || GloabConst.UploadConfigConst.IMG_JPG.equals(type.toUpperCase())
                        || GloabConst.UploadConfigConst.IMG_PNG.equals(type.toUpperCase())
                        || GloabConst.UploadConfigConst.IMG_JPEG.equals(type.toUpperCase())
                ) {
                    return true;
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.IMAGE_TYPE_NOT_CHECK, "文件类型错误", "checkUploadImgageFormate");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.IMAGE_TYPE_NULL, "文件类型为空", "checkUploadImgageFormate");
            }
        } else {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.IMAGE_NAME_NOT_NULL, "图片名不能为空", "checkUploadImgageFormate");
        }
    }


    /**
     * 验证店铺数据格式
     *
     * @param mch -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/30 11:03
     */
    public static MchModel checkMchDataFormate(MchModel mch) throws LaiKeApiException {
        MchModel mchModel = new MchModel();
        try {
            BeanUtils.copyProperties(mch, mchModel);
            boolean flag;
            //店铺名称限制 1-14 位
            if (!StringUtils.isEmpty(mch.getName())) {
                flag = checkLength(mch.getName(), 1, 14);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_NAME_NOT_CHECK, "店铺名称格式不正确", "checkMchDataFormate");
                }
                mchModel.setName(mch.getName());
            }
            //店铺信息限制 1-50 位
            if (!StringUtils.isEmpty(mch.getShop_information())) {
                flag = checkLength(mch.getShop_information(), 1, 50);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_INFO_NOT_CHECK, "店铺信息格式不正确", "checkMchDataFormate");
                }
                mchModel.setShop_information(mch.getShop_information());
            }
            //店铺经营范围限制 1-50 位
            if (!StringUtils.isEmpty(mch.getShop_range())) {
                flag = checkLength(mch.getShop_range(), 1, 50);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_RANGE_NOT_CHECK, "经营范围格式不正确", "checkMchDataFormate");
                }
                mchModel.setConfines(mch.getConfines());
            }
            //身份证 限制
            if (!StringUtils.isEmpty(mch.getID_number())) {
                flag = DataCheckTool.checkIdCard(mch.getID_number());
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_IDCARD_NOT_CHECK, "身份证格式不正确", "checkMchDataFormate");
                }
                mchModel.setID_number(mch.getID_number());
            }

            //联系电话 限制
            if (!StringUtils.isEmpty(mch.getTel())) {
                if (!MobileUtils.isMobile(mch.getTel())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_TEL_NOT_CHECK, "联系电话格式不正确", "checkMchDataFormate");
                }
                mchModel.setTel(mch.getTel());
            }

            //联系地址 限制
            if (!StringUtils.isEmpty(mch.getAddress())) {
                flag = checkLength(mch.getAddress(), 1, 140);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_ADDRESS_NOT_CHECK, "联系地址格式不正确", "checkMchDataFormate");
                }
                mchModel.setAddress(mch.getAddress());
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkMchDataFormate");
        }

        return mchModel;
    }


    /**
     * 门店信息验证
     *
     * @param mchStoreModel -
     * @return MchStoreModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 16:19
     */
    public static MchStoreModel checkStoreDataFormate(MchStoreModel mchStoreModel) throws LaiKeApiException {
        MchStoreModel mchStore = new MchStoreModel();
        try {
            BeanUtils.copyProperties(mchStoreModel, mchStore);
            boolean flag;
            //店铺名称限制 1-14 位
            if (!StringUtils.isEmpty(mchStore.getName())) {
                flag = checkLength(mchStore.getName(), 1, 14);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_NAME_NOT_CHECK, "店铺名称格式不正确", "checkMchDataFormate");
                }
            }

            //联系电话 限制
            if (!StringUtils.isEmpty(mchStore.getMobile())) {
                if (!MobileUtils.isMobile(mchStore.getMobile())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_TEL_NOT_CHECK, "联系电话格式不正确", "checkMchDataFormate");
                }
            }

            //联系地址 限制
            if (!StringUtils.isEmpty(mchStore.getAddress())) {
                flag = checkLength(mchStore.getAddress(), 1, 140);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_ADDRESS_NOT_CHECK, "联系地址格式不正确", "checkMchDataFormate");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkMchDataFormate");
        }

        return mchStore;
    }


    /**
     * 优惠卷活动数据验证
     *
     * @param couponActivityModel -
     * @return CouponActivityModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/9 16:15
     */
    public static CouponActivityModel checkCouponActivityData(CouponActivityModel couponActivityModel) throws LaiKeApiException {
        CouponActivityModel couponActivity = new CouponActivityModel();
        try {
            BeanUtils.copyProperties(couponActivityModel, couponActivity);
            //验证发行量
            if (couponActivity.getCirculation() != null) {
                if (couponActivity.getCirculation() < 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "发行数量必须是正整数", "checkMchDataFormate");
                } else if (couponActivity.getCirculation() == 0) {
                    couponActivity.setCirculation(999999999);
                }
            }
            //优惠卷面值
            if (couponActivity.getMoney() != null) {
                couponActivity.setMoney(couponActivity.getMoney().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //满减金额
            if (couponActivity.getZ_money() != null) {
                if (couponActivity.getZ_money().doubleValue() < 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "满减金额阈值不正确", "checkMchDataFormate");
                }
                couponActivity.setZ_money(couponActivity.getZ_money().setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            //优惠卷满减规则
            if (couponActivity.getMoney() != null && couponActivity.getZ_money() != null) {
                if (couponActivity.getZ_money().doubleValue() != 0 && couponActivity.getZ_money().doubleValue() <= couponActivity.getMoney().doubleValue()) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "优惠卷使用阈值不正确", "checkMchDataFormate");
                }
            }
            //商品集
            if (couponActivity.getProduct_id() != null) {
                couponActivity.setProduct_id(SerializePhpUtils.JavaSerializeByPhp(couponActivity.getProduct_id()));
            }
            //商品类别集
            if (couponActivity.getProduct_class_id() != null) {
                couponActivity.setProduct_class_id(SerializePhpUtils.JavaSerializeByPhp(couponActivity.getProduct_class_id()));
            }
            //开始时间和结束时间校验
            if (couponActivity.getStart_time() != null && couponActivity.getEnd_time() != null) {
                if (!DateUtil.dateCompare(couponActivity.getEnd_time(), couponActivity.getStart_time())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "活动结束时间必须大于开始时间", "checkMchDataFormate");
                }
                //结束时间必须大于当前时间
                if (!DateUtil.dateCompare(couponActivity.getEnd_time(), new Date())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "活动结束时间必须大于当前时间", "checkMchDataFormate");
                }
                //如果活动是否开始
                if (DateUtil.dateCompare(new Date(), couponActivity.getStart_time())) {
                    couponActivity.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_OPEN);
                } else {
                    couponActivity.setStatus(CouponActivityModel.COUPON_ACTIVITY_STATUS_NOT_USE);
                }
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkMchDataFormate");
        }

        return couponActivity;
    }

    /**
     * 运费数据验证
     *
     * @param freightModel -
     * @return com.laiketui.domain.distribution.FreightModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 16:20
     */
    public static FreightModel checkFreightDataFormate(FreightModel freightModel) throws LaiKeApiException {
        FreightModel freight = new FreightModel();
        try {
            BeanUtils.copyProperties(freightModel, freight);
            //运费规则名称
            if (StringUtils.isEmpty(freight.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "运费规则名称不能为空", "checkMchDataFormate");
            }
            //运费规则
            if (StringUtils.isEmpty(freight.getFreight())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "运费规则不能为空", "checkMchDataFormate");
            } else {
                System.out.println(freight.getFreight());
                System.out.println(URLDecoder.decode(freight.getFreight(), GloabConst.Chartset.UTF8));
                //判断是否有重复添加省份 [{"one":"1","name":"北京市,内蒙古自治区,上海市","freight":"1"},{"one":"2","name":"福建省,湖北省,海南省","freight":"2"},{"one":"1","name":"广东省,贵州省","freight":"1"},{"one":0,"freight":"1","name":"澳门"}]
                List<Map<String, Object>> freightList = JSON.parseObject(freight.getFreight(), new TypeReference<List<Map<String, Object>>>() {
                });
                Set<String> shenList = new HashSet<>();
                for (Map<String, Object> freightMap : freightList) {
                    String main = DataUtils.cast(freightMap.get("name"));
                    if (main != null) {
                        String[] list = main.split(",");
                        for (String shen : list) {
                            if (shenList.contains(shen)) {
                                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "有重复的省份", "checkMchDataFormate");
                            }
                            shenList.add(shen);
                        }
                    }
                }
                //序列化
                freight.setFreight(SerializePhpUtils.JavaSerializeByPhp(freightList));
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkMchDataFormate");
        }

        return freight;
    }

    /**
     * 验证用户数据格式
     *
     * @param user -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 10:55
     */
    public static User checkUserDataFormate(User user) throws LaiKeApiException {
        User userData = new User();
        boolean flag;
        try {
            BeanUtils.copyProperties(user, userData);
            //手机号限制 11 位
            if (!StringUtils.isEmpty(user.getMobile())) {
                if (!MobileUtils.isMobile(user.getMobile())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PHONE_NOT_CHECK, "手机号格式不正确", "checkUserDataFormate");
                }
                userData.setMobile(user.getMobile());
            }
            //出生日期不能大于当前日期
            if (user.getBirthday() != null) {
                if (user.getBirthday().getTime() >= System.currentTimeMillis()) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BIRTHDAY_NOT_CHECK, "出生日期格式不正确", "checkUserDataFormate");
                }
                userData.setBirthday(user.getBirthday());
            }
            //账号限制 6-15 位
            if (!StringUtils.isEmpty(user.getZhanghao())) {
                flag = DataCheckTool.checkNumAndLetter(user.getZhanghao(), 6, 15);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.ZHANGHAO_NOT_CHECK, "账号格式不正确", "checkUserDataFormate");
                }
                userData.setZhanghao(user.getZhanghao());
            }
            //用户名限制 6 位
            if (!StringUtils.isEmpty(user.getUser_name())) {
                userData.setUser_name(user.getUser_name());
            }
            //支付密码限制 6 位
            if (!StringUtils.isEmpty(user.getPassword())) {
                flag = DataCheckTool.checkNumAndLetter(user.getPassword(), 6, 6);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PAY_PASSWORD_NOT_CHECK, "支付密码格式不正确");
                }
                if (!StringUtils.isInteger(user.getPassword())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PAY_PASSWORD_NOT_CHECK, "支付密码只能为纯数字");
                }
                userData.setPassword(Md5Util.MD5endoce(user.getPassword()));
            }
            //密码限制 6-16 位
            if (!StringUtils.isEmpty(user.getMima())) {
                flag = DataCheckTool.checkNumAndLetter(user.getMima(), 6, 16);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MIMA_NOT_CHECK, "密码格式不正确", "checkUserDataFormate");
                }
                try {
                    String mima = CryptoUtil.strEncode(user.getMima());
                    userData.setMima(mima);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    logger.error(user.getMima() + " 密码加密出现错误");
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙", "checkUserDataFormate");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkMchDataFormate");
        }
        return userData;
    }


    /**
     * 验证商品数据
     *
     * @param model -
     * @return com.laiketui.domain.product.ProductListModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/17 15:48
     */
    public static ProductListModel checkGoodsDataFormate(ProductListModel model) throws LaiKeApiException {
        ProductListModel productListModel = new ProductListModel();
        try {
            //统一Url解码
            String URLDecoderStr = URLDecoder.decode(JSON.toJSONString(model), GloabConst.Chartset.UTF_8);
            model = JSON.parseObject(URLDecoderStr, ProductListModel.class);
            BeanUtils.copyProperties(model, productListModel);
            //商品名称校验
            if (!StringUtils.isEmpty(model.getProduct_title())) {
                if (model.getProduct_title().length() > 60) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "商品标题不能超过20个中文字长度", "checkGoodsDataFormate");
                }
                productListModel.setProduct_title(URLDecoder.decode(model.getProduct_title(), GloabConst.Chartset.UTF_8));
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "商品标题不能为空", "checkGoodsDataFormate");
            }
            //关键词
            if (!StringUtils.isEmpty(model.getKeyword())) {
                //去掉收尾','
                String keyword = model.getKeyword();
                keyword = StringUtils.trim(keyword, ",");
                productListModel.setKeyword(keyword);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "关键词不能为空", "checkGoodsDataFormate");
            }
            if (model.getVolume() != null) {
                if (!StringUtils.isInteger(model.getVolume() + "")) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "销量请输入整数");
                }
            }
            //重量校验
            if (!StringUtils.isEmpty(model.getWeight())) {
                float weight = Float.parseFloat(model.getWeight());
                if (weight < 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "重量不能为负数", "checkGoodsDataFormate");
                }
                //$formattedNum = number_format($num, 2, 2, ".", "");
                BigDecimal wegifhtFormate = new BigDecimal(model.getWeight());
                wegifhtFormate = wegifhtFormate.setScale(2, BigDecimal.ROUND_DOWN);
                productListModel.setWeight(wegifhtFormate.toString());
            } else {
                productListModel.setWeight("");
            }
            //商品类别
            if (StringUtils.isEmpty(model.getProduct_class())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "商品类别", "checkGoodsDataFormate");
            }
            //品牌
            if (model.getBrand_id() == null || model.getBrand_id() == 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "品牌不能为空", "checkGoodsDataFormate");
            }
            //产品封面图
            if (StringUtils.isEmpty(model.getCover_map())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请上传商品图片", "checkGoodsDataFormate");
            }
            //产品初始值
            if (!StringUtils.isEmpty(model.getInitial())) {
                //pick 跳过验证
                if (!GloabConst.ManaValue.MANA_VALUE_PICK.equals(model.getInitial())) {
                    //初始值处理
                    Map<String, Object> intialMap = new HashMap<>(16);
                    //cbj=1,yj=12,sj=11,kucun=111,unit=个,stockWarn=11
                    String intialStr = productListModel.getInitial();
                    intialStr = StringUtils.trim(intialStr);
                    String[] intialList = intialStr.split(",");
                    for (String str : intialList) {
                        int indexOf = str.indexOf("=");
                        String key = str.substring(0, indexOf);
                        String value = str.substring(indexOf + 1);
                        intialMap.put(key, value);
                    }
                    //校验其中值
                    if (!intialMap.containsKey("cbj") || StringUtils.isEmpty(intialMap.get("cbj").toString())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "成本价不能为空", "checkGoodsDataFormate");
                    } else if (!intialMap.containsKey("yj") || StringUtils.isEmpty(intialMap.get("yj").toString())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "原价不能为空", "checkGoodsDataFormate");
                    } else if (!intialMap.containsKey("sj") || StringUtils.isEmpty(intialMap.get("sj").toString())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "售价不能为空", "checkGoodsDataFormate");
                    } else if (!intialMap.containsKey("unit") || StringUtils.isEmpty(intialMap.get("unit").toString())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "单位不能为空", "checkGoodsDataFormate");
                    } else if (!intialMap.containsKey("kucun") || StringUtils.isEmpty(intialMap.get("kucun").toString())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "库存不能为空", "checkGoodsDataFormate");
                    }
                    //售价和成本价校验
                    BigDecimal cbj = new BigDecimal(MapUtils.getString(intialMap, "cbj"));
                    BigDecimal sj = new BigDecimal(MapUtils.getString(intialMap, "sj"));
                    if (cbj.compareTo(sj) > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "成本价需低于售价");
                    }
                    boolean flag = false;
                    if (Double.parseDouble(intialMap.get("cbj").toString()) < 0) {
                        flag = true;
                    } else if (Double.parseDouble(intialMap.get("yj").toString()) < 0) {
                        flag = true;
                    } else if (Double.parseDouble(intialMap.get("sj").toString()) < 0) {
                        flag = true;
                    } else if (StringUtils.isEmpty(intialMap.get("unit") + "")) {
                        flag = true;
                    } else if (Double.parseDouble(intialMap.get("kucun").toString()) < 0) {
                        flag = true;
                    }
                    if (flag) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "初始值不能为负数", "checkGoodsDataFormate");
                    }
                    //转换成php序列化
                    String phpSerial = SerializePhpUtils.JavaSerializeByPhp(intialMap);
                    productListModel.setInitial(phpSerial);
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "初始值不能为空", "checkGoodsDataFormate");
            }

            //库存预警校验
            Integer minInventory = model.getMin_inventory();
            if (minInventory != null && StringUtils.isInteger(minInventory.toString())) {
                if (minInventory <= 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "库存预警请输入大于0的整数", "checkGoodsDataFormate");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "库存预警请输入整数", "checkGoodsDataFormate");
            }
            //运费校验
            if (StringUtils.isEmpty(model.getFreight()) || "0".equals(model.getFreight())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请选择运费模板名称", "checkGoodsDataFormate");
            }
            //活动类型校验
            if (StringUtils.isEmpty(model.getActive())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请选择运费模板名称", "checkGoodsDataFormate");
            }
            //商品图片校验
            if (StringUtils.isEmpty(model.getImgurl())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "上传商品时,没有上传图片！", "checkGoodsDataFormate");
            }
            //默认分销id=0
            if (model.getDistributor_id() == null) {
                model.setDistributor_id(0);
            }
            //产品值属性
            if (!StringUtils.isEmpty(model.getS_type())) {
                model.setS_type(StringUtils.trim(model.getS_type(), SplitUtils.DH));
                //处理商品类型
                List<Object> typeList = new ArrayList<>(Arrays.asList(model.getS_type().split(SplitUtils.DH)));
                productListModel.setS_type(StringUtils.stringImplode(typeList, SplitUtils.DH, true));
            }
            //条形码
            if (!StringUtils.isEmpty(model.getScan())) {
                model.setScan("0");
            }

            return productListModel;
        } catch (LaiKeApiException e) {
            throw e;
        } catch (Exception e) {
            logger.error("商品数据校验 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkGoodsDataFormate");
        }
    }


    /**
     * 后台管理员参数验证
     *
     * @param adminModel -
     * @param isUpdate   - 是否是修改
     * @return AdminModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/13 16:06
     */
    public static AdminModel checkAdminDataFormate(AdminModel adminModel, boolean isUpdate) throws LaiKeApiException {
        AdminModel admin = new AdminModel();
        try {
            BeanUtils.copyProperties(adminModel, admin);
            //校验账号
            if (!StringUtils.isEmpty(adminModel.getName())) {
                if (!DataCheckTool.checkNumAndLetter(adminModel.getName(), 1, 20)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员账号不能超过20个字符内的英文数字长度");
                }
            } else if (!isUpdate) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员账号不能为空");
            }
            //校验密码
            if (!StringUtils.isEmpty(adminModel.getPassword())) {
                if (!DataCheckTool.checkLength(adminModel.getPassword(), 6, 20)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员密码长度为 6-20");
                }
                admin.setPassword(Md5Util.MD5endoce(adminModel.getPassword()));
            } else if (!isUpdate) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员密码不能为空");
            }
            if (!isUpdate && StringUtils.isEmpty(adminModel.getRole())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "请选择角色");
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkAdminDataFormate");
        }

        return admin;
    }

    /**
     * 校验系统配置参数
     *
     * @param configModel -
     * @return com.laiketui.domain.config.ConfigModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/19 10:18
     */
    public static ConfigModel checkConfigDataFormate(ConfigModel configModel) throws LaiKeApiException {
        ConfigModel config = new ConfigModel();
        try {
            BeanUtils.copyProperties(configModel, config);
            //验证logo
            if (StringUtils.isEmpty(configModel.getLogo())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "公司logo不能为空");
            } else {
                config.setLogo(ImgUploadUtils.getUrlImgByName(configModel.getLogo(), true));
            }
            //验证微信默认头像
            if (StringUtils.isEmpty(configModel.getLogo1())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "微信默认头像不能为空");
            } else {
                config.setWx_headimgurl(ImgUploadUtils.getUrlImgByName(configModel.getWx_headimgurl(), true));
            }
            //验证腾讯位置api开发密钥
            if (StringUtils.isEmpty(configModel.getTencent_key())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "腾讯位置服务开发密钥不能为空");
            }

            //是否需要验证推送
            if (config.getIs_push() != null && config.getIs_push() == 1) {
                if (StringUtils.isEmpty(configModel.getPush_Appkey())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "Appkey不能为空");
                } else if (StringUtils.isEmpty(configModel.getPush_Appid())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "Appid不能为空");
                } else if (StringUtils.isEmpty(configModel.getPush_MasterECRET())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "MasterECRET不能为空");
                }
            } else {
                config.setPush_Appkey(null);
                config.setPush_Appid(null);
                config.setPush_MasterECRET(null);
            }
            //是否需要验证快递100
            if (config.getIs_express() != null && config.getIs_express() == 1) {
                if (StringUtils.isEmpty(configModel.getExpress_address())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "查询接口地址不能为空");
                } else if (StringUtils.isEmpty(configModel.getExpress_number())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户编号不能为空");
                } else if (StringUtils.isEmpty(configModel.getExpress_key())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "接口调用key不能为空");
                }
            } else {
                config.setExpress_address(null);
                config.setExpress_number(null);
                config.setExpress_key(null);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkConfigDataFormate");
        }

        return config;
    }

    /**
     * 检测是否为商品
     *
     * @param str -
     * @return com.laiketui.domain.product.ProductListModel
     * @throws LaiKeApiException -
     * @author wx
     * @date 2020/12/21 11:49
     */
    public static boolean checkIsNum(String str) {
        return str.matches("-?[0-9]+.？[0-9]*");
    }

    /**
     * 校验编辑订单
     *
     * @throws LaiKeApiException -
     * @author wx
     * @date 2021/2/06 10:06
     */
    public static OrderModifyVo checkOrderModify(OrderModifyVo orderModifyVo) throws LaiKeApiException {
        try {
            List<String> checkList = new ArrayList<>();
            checkList.add("sheng");
            checkList.add("shi");
            checkList.add("xian");
            checkList.add("u_status");
            checkList.add("mobile");
            checkList.add("address");
            checkList.add("remarks");
            checkList.add("z_price");
            ValidationUtils.checkNotEmpty(orderModifyVo);

            //价格验证
            BigDecimal zPrice = orderModifyVo.getzPrice();
            if (zPrice.compareTo(BigDecimal.ZERO) < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkOrderModify");
            }

            //手机号码验证
            if (!StringUtils.isEmpty(orderModifyVo.getMobile())) {
                if (!MobileUtils.isMobile(orderModifyVo.getMobile())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_TEL_NOT_CHECK, "联系电话格式不正确", "checkOrderModify");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("检查订单编辑数据出错：{}", e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法", "checkOrderModify");
        }
        return orderModifyVo;
    }


    /**
     * 添加/编辑竞拍商品信息数据验证
     *
     * @param vo -
     * @return com.laiketui.domain.distribution.FreightModel
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/1 16:20
     */
    public static AddAuctionVo checkFreightDataFormate(AddAuctionVo vo) throws LaiKeApiException {
        AddAuctionVo auction = new AddAuctionVo();
        try {
            BeanUtils.copyProperties(vo, auction);
            if (!StringUtils.isEmpty(vo.getTitle())) {
                if (vo.getTitle().length() > 60) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "竞拍标题长度不能超过20个中文字符");
                }
            }
            if (vo.getPrice() == null || vo.getPrice().compareTo(BigDecimal.ZERO) < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "竞拍起价不能小于零");
            }
            if (vo.getAddPrice() == null || vo.getAddPrice().compareTo(BigDecimal.ZERO) < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "加价幅度不能小于零");
            }
            if (vo.getPromise() == null || vo.getPromise().compareTo(BigDecimal.ZERO) < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "保证金不能小于0");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据非法");
        }

        return auction;
    }

}
