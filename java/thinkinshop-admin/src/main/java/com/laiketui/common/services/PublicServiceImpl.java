
package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.laiketui.api.common.PublicMemberService;
import com.laiketui.api.common.PubliceService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.Page;
import com.laiketui.domain.SmsMessageModel;
import com.laiketui.domain.config.*;
import com.laiketui.domain.coupon.CouponConfigModel;
import com.laiketui.domain.dictionary.DictionaryListModel;
import com.laiketui.domain.dictionary.DictionaryNameModel;
import com.laiketui.domain.dictionary.MessageModel;
import com.laiketui.domain.log.AdminRecordModel;
import com.laiketui.domain.log.FilesRecordModel;
import com.laiketui.domain.log.RecordModel;
import com.laiketui.domain.log.SignRecordModel;
import com.laiketui.domain.mch.*;
import com.laiketui.domain.message.MessageLoggingModal;
import com.laiketui.domain.order.BuyAgainModal;
import com.laiketui.domain.payment.PaymentConfigModel;
import com.laiketui.domain.payment.PaymentModel;
import com.laiketui.domain.product.CommentsImgModel;
import com.laiketui.domain.product.ProductImgModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.product.ReplyCommentsModel;
import com.laiketui.domain.upload.ImgGroupModel;
import com.laiketui.domain.upload.UploadConfigModel;
import com.laiketui.domain.upload.UploadImagModel;
import com.laiketui.domain.user.FinanceConfigModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.user.UserGradeModel;
import com.laiketui.domain.user.WithdrawModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Withdrawals1Vo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.utils.algorithm.MobileUtils;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.jwt.JwtUtils;
import com.laiketui.root.utils.okhttp.HttpUtils;
import com.laiketui.root.utils.tool.*;
import com.laiketui.root.utils.weixin.Jssdk;
import io.jsonwebtoken.Claims;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 公共服务
 *
 * @author Trick
 * @date 2020/10/15 17:07
 */
@Service
public class PublicServiceImpl implements PubliceService {

    private final Logger logger = LoggerFactory.getLogger(PublicServiceImpl.class);
    @Autowired
    private PublicMemberService publicMemberService;
    @Autowired
    private CartModelMapper cartModelMapper;
    @Autowired
    private CommentsImgModelMapper commentsImgModelMapper;
    @Autowired
    private ReplyCommentsModelMapper replyCommentsModelMapper;
    @Autowired
    private AgreementModelMapper agreementModelMapper;
    @Autowired
    private CommentsModelMapper commentsModelMapper;
    @Autowired
    private UserCollectionModelMapper userCollectionModelMapper;
    @Autowired
    private FilesRecordModelMapper filesRecordModelMapper;
    @Autowired
    private UploadConfigModelMapper uploadConfigModelMapper;
    @Autowired
    private ProductConfigModelMapper productConfigModelMapper;
    @Autowired
    private ProductListModelMapper productListModelMapper;
    @Autowired
    private ConfigModelMapper configModelMapper;
    @Autowired
    private UserGradeModelMapper userGradeModelMapper;
    @Autowired
    private UserBaseMapper userBaseMapper;
    @Autowired
    private MchModelMapper mchModelMapper;
    @Autowired
    private ConfiGureModelMapper confiGureModelMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AdminModelMapper adminModelMapper;
    @Autowired
    private BuyAgainModalMapper buyAgainModalMapper;
    @Autowired
    private ImgGroupModelMapper imgGroupModelMapper;

    @Autowired
    private PaymentModelMapper paymentModelMapper;

    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;

    @Override
    public String getImgPath(String imgName, String storeId) throws LaiKeApiException {
        String imageUrl = "";
        try {
            //获取图片完整路径
            FilesRecordModel filesRecordModel = new FilesRecordModel();
            filesRecordModel.setStore_id(storeId);
            filesRecordModel.setImage_name(imgName);
            filesRecordModel = filesRecordModelMapper.getImageUrlOne(filesRecordModel);
            if (filesRecordModel != null) {
                //获取文件上传配置
                UploadConfigModel uploadConfigModel = new UploadConfigModel();
                uploadConfigModel.setUpserver(filesRecordModel.getUpload_mode());
                List<UploadConfigModel> uploadConfigModels = uploadConfigModelMapper.select(uploadConfigModel);
                //获取图片完整路径
                imageUrl = ImgUploadUtils.getImgPath(filesRecordModel, uploadConfigModels);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取图片路径失败!");
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片路径获取失败", "getImgPath");
        }

        return imageUrl;
    }

    @Override
    public String getImgPath(String imgName, Integer storeId) throws LaiKeApiException {
        String imageUrl = "";
        try {
            if (storeId == null || StringUtils.isEmpty(imgName)) {
                return imageUrl;
            }
            //获取图片完整路径
            FilesRecordModel filesRecordModel = new FilesRecordModel();
            filesRecordModel.setStore_id(storeId + "");
            filesRecordModel.setImage_name(imgName);
            filesRecordModel = filesRecordModelMapper.getImageUrlOne(filesRecordModel);
            if (filesRecordModel != null) {
                //获取文件上传配置
                UploadConfigModel uploadConfigModel = new UploadConfigModel();
                uploadConfigModel.setUpserver(filesRecordModel.getUpload_mode());
                List<UploadConfigModel> uploadConfigModels = uploadConfigModelMapper.select(uploadConfigModel);
                //获取图片完整路径
                imageUrl = ImgUploadUtils.getImgPath(filesRecordModel, uploadConfigModels);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取图片路径失败! 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片路径获取失败", "getImgPath");
        }

        return imageUrl;
    }

    @Override
    public List<String> uploadFiles(UploadFileVo vo) throws LaiKeApiException {
        List<String> imgUrls = null;
        try {
            if (GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST.equals(vo.getUploadType())) {
                vo.setUploadType(GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST);
            } else if (GloabConst.UploadConfigConst.IMG_UPLOAD_OSS.equals(vo.getUploadType())) {
                vo.setUploadType(GloabConst.UploadConfigConst.IMG_UPLOAD_OSS);
            }

            UploadImagModel imagModel = new UploadImagModel();
            imagModel.setUploadType(vo.getUploadType());
            if (vo.getGroupId() != null && vo.getGroupId() > 0) {
                ImgGroupModel imgGroupModel = imgGroupModelMapper.selectByPrimaryKey(vo.getGroupId());
                if (imgGroupModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "图片分组不存在");
                }
                //指定上传路径
                imagModel.setPath(PinyinUtils.getPinYin(imgGroupModel.getName()));
            }
            //从数据库获取上传配置key
            UploadConfigModel uploadConfigModel = new UploadConfigModel();
            uploadConfigModel.setUpserver(imagModel.getUploadType());
            List<UploadConfigModel> uploadConfigs = uploadConfigModelMapper.select(uploadConfigModel);
            //装载参数
            imagModel.setUploadConfigs(uploadConfigs);
            imagModel.setMultipartFiles(DataUtils.convertToList(vo.getImage()));
            //图片上传
            ImgUploadUtils imgUpload = new ImgUploadUtils();
            imgUrls = imgUpload.imgUpload(imagModel, vo.getStoreId(), vo.getStoreType());

            if (imgUrls != null && imgUrls.size() > 0) {
                //添加上传记录信息
                for (String imgUrl : imgUrls) {
                    FilesRecordModel filesRecordModel = new FilesRecordModel();
                    filesRecordModel.setImage_name(ImgUploadUtils.getUrlImgByName(imgUrl, true));
                    filesRecordModel.setStore_id(vo.getStoreId() + "");
                    filesRecordModel.setStore_type(vo.getStoreType() + "");
                    filesRecordModel.setGroup(vo.getGroupId() + "");
                    filesRecordModel.setUpload_mode(vo.getUploadType());
                    if (StringUtils.isNotEmpty(vo.getTitle())) {
                        filesRecordModel.setTitle(vo.getTitle());
                    }
                    if (StringUtils.isNotEmpty(vo.getExplain())) {
                        filesRecordModel.setTitle(vo.getExplain());
                    }
                    if (StringUtils.isNotEmpty(vo.getAlternativeText())) {
                        filesRecordModel.setTitle(vo.getAlternativeText());
                    }
                    if (StringUtils.isNotEmpty(vo.getDescribe())) {
                        filesRecordModel.setTitle(vo.getDescribe());
                    }
                    if (vo.getMchId() != null) {
                        filesRecordModel.setMch_id(vo.getMchId());
                    }
                    filesRecordModel.setAdd_time(new Date());
                    int count = filesRecordModelMapper.insertSelective(filesRecordModel);
                    if (count < 1) {
                        logger.info("图片上传失败,图片记录失败 参数：" + JSON.toJSONString(filesRecordModel));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadImage");
                    }
                }
                return imgUrls;
            } else {
                throw new LaiKeApiException(ErrorCode.SysErrorCode.UPLOAD_FAIL, "图片上传失败", "uploadImage");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取图片路径失败!", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片上传失败", "uploadImage");
        }
    }

    @Override
    public List<String> uploadImage(List<MultipartFile> multipartFiles, String uploadType, int storeType, int storeId, Integer mchId) throws LaiKeApiException {
        List<String> imgUrls;
        try {
            UploadImagModel imagModel = new UploadImagModel();
            imagModel.setUploadType(uploadType);
            //从数据库获取上传配置key
            UploadConfigModel uploadConfigModel = new UploadConfigModel();
            uploadConfigModel.setUpserver(imagModel.getUploadType() + "");
            List<UploadConfigModel> uploadConfigs = uploadConfigModelMapper.select(uploadConfigModel);

            //装载参数
            imagModel.setUploadConfigs(uploadConfigs);
            imagModel.setMultipartFiles(multipartFiles);
            //图片上传
            ImgUploadUtils imgupload = new ImgUploadUtils();
            imgUrls = imgupload.imgUpload(imagModel, storeId, storeType);

            if (imgUrls != null && imgUrls.size() > 0) {
                //添加上传记录信息
                for (String imgUrl : imgUrls) {
                    FilesRecordModel filesRecordModel = new FilesRecordModel();
                    filesRecordModel.setImage_name(ImgUploadUtils.getUrlImgByName(imgUrl, true));
                    filesRecordModel.setStore_id(storeId + "");
                    filesRecordModel.setStore_type(storeType + "");
                    filesRecordModel.setGroup("-1");
                    filesRecordModel.setUpload_mode(GloabConst.UploadConfigConst.IMG_UPLOAD_OSS);
                    if (mchId != null) {
                        filesRecordModel.setMch_id(mchId);
                    }
                    filesRecordModel.setAdd_time(new Date());
                    int count = filesRecordModelMapper.insertSelective(filesRecordModel);
                    if (count < 1) {
                        logger.info("图片上传失败,图片记录失败 参数：" + JSON.toJSONString(filesRecordModel));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "uploadImage");
                    }
                }
                return imgUrls;
            } else {
                throw new LaiKeApiException(ErrorCode.SysErrorCode.UPLOAD_FAIL, "图片上传失败", "uploadImage");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("上传图片异常! ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片上传失败", "uploadImage");
        }
    }

    @Override
    public List<String> uploadImage(List<MultipartFile> multipartFiles, String uploadType, int storeType, int storeId) throws LaiKeApiException {
        try {
            //默认使用oos
            if (StringUtils.isEmpty(uploadType)) {
                uploadType = GloabConst.UploadConfigConst.IMG_UPLOAD_OSS;
            }
            return uploadImage(multipartFiles, uploadType, storeType, storeId, null);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取图片路径失败!", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片路径获取失败", "getImgPath");
        }
    }

    @Override
    public String uploadImage(String url, String uploadType, int storeType, int storeId) throws LaiKeApiException {
        InputStream stream = null;
        String imageUrl = null;
        try {
            //获取图片信息
            File openFile = new File(url);
            String imageSuffix = ImgUploadUtils.getUrlImgBySuffix(openFile.getName());
            List<MultipartFile> fileList = new ArrayList<>();
            //加载图片
            stream = HttpUtils.getFile(url);
            MultipartFile file = new MockMultipartFile("temp", BuilderIDTool.getGuid() + imageSuffix, ImgUploadUtils.getUrlImgByMimeType(imageSuffix), stream);
            fileList.add(file);
            //上传图片
            List<String> imageUrlList = uploadImage(fileList, uploadType, storeType, storeId);
            for (String imgUrl : imageUrlList) {
                imageUrl = imgUrl;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("上传外链图片异常! " + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片外链上传失败", "uploadImage");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException io) {
                    io.printStackTrace();
                    logger.error("上传外链图片异常!流关闭异常 " + io.getMessage());
                }
            }
        }
        return imageUrl;
    }

    @Override
    public void addRotationImage(List<String> imageNameList, int pid, boolean isUpdate) throws LaiKeApiException {
        try {
            if (!isUpdate) {
                //删除之前商品的轮播图
                ProductImgModel productImg = new ProductImgModel();
                productImg.setProduct_id(pid);
                productImgModelMapper.delete(productImg);
            }
            for (String imageName : imageNameList) {
                ProductImgModel productImgModel = new ProductImgModel();
                productImgModel.setProduct_url(imageName);
                productImgModel.setProduct_id(pid);
                productImgModel.setAdd_date(new Date());
                int count = productImgModelMapper.insertSelective(productImgModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "轮播图添加失败", "addRotationImage");
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加商品轮播图失败 异常! " + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "添加商品轮播图失败", "addRotationImage");
        }
    }

    @Override
    public boolean verifyToken(String token) throws LaiKeApiException {
        boolean flag = false;
        try {
            try {
                if (StringUtils.isEmpty(token)) {
                    return true;
                }
                //验证token的合法性
                JwtUtils.verifyJwt(token);
            } catch (LaiKeApiException l) {
                //过期或者不存在
                l.printStackTrace();
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取Token失败!");
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取Token失败", "verifyToken");
        }
        return flag;
    }

    @Override
    public Map<String, Object> verifyToken(MainVo vo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        boolean flag = false;
        try {
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(vo.getStoreId());
            configModel = configModelMapper.selectOne(configModel);
            if (configModel != null) {
                String isRegister = configModel.getIs_register();
                //只有app才支持免注册
                if (GloabConst.LktConfig.REGISTER_TYPE2.equals(isRegister) && DictionaryConst.StoreSource.LKT_LY_001.equals(vo.getStoreType() + "")) {
                    // 当注册为免注册，并且来源为小程序
                    flag = true;
                } else {
                    try {
                        //验证token的合法性
                        Claims claims = JwtUtils.verifyJwt(vo.getAccessId());
                        resultMap.put("tokenMap", claims);
                    } catch (LaiKeApiException l) {
                        //过期或者不存在
                        l.printStackTrace();
                        flag = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取Token失败!");
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取Token失败", "verifyToken");
        }
        resultMap.put("msg", flag);
        return resultMap;
    }

    @Override
    public BigDecimal getUserGradeRate(int storeId, User user) throws LaiKeApiException {
        BigDecimal gradeRate = new BigDecimal("1");
        try {
            //会员折扣率
            if (user != null && user.getGrade() != null && user.getGrade().doubleValue() != 0) {
                //获取会员等级对应的折扣
                UserGradeModel userGradeModel = userGradeModelMapper.getUserGradeInfo(storeId, user.getUser_id(), new Date());
                if (userGradeModel != null && gradeRate.compareTo(userGradeModel.getRate()) != 0 && BigDecimal.ZERO.compareTo(userGradeModel.getRate()) != 0) {
                    gradeRate = userGradeModel.getRate().divide(new BigDecimal("10"), 2, BigDecimal.ROUND_HALF_UP);
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取折扣失败", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "折扣获取失败", "getUserGradeRate");
        }
        return gradeRate;
    }

    @Override
    public BigDecimal getUserGradeRate(int storeId, User user, boolean isLowGrate) throws LaiKeApiException {
        BigDecimal gradeRate = new BigDecimal("1");
        try {
            //会员折扣率
            if (user != null) {
                gradeRate = getUserGradeRate(storeId, user);
            } else if (isLowGrate) {
                //获取未注册的折扣率 为了吸引顾客 用最低折扣
                gradeRate = userGradeModelMapper.getGradeLow(storeId).divide(new BigDecimal("10"), 2, BigDecimal.ROUND_HALF_UP);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取折扣失败", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "折扣获取失败", "getUserGradeRate_1");
        }
        return gradeRate;
    }

    @Override
    public User getUserInfo(String accessId) throws LaiKeApiException {
        User user = null;
        try {
            Object userObj = redisUtil.get(GloabConst.RedisHeaderKey.LOGIN_ACCESS_TOKEN + accessId);
            if (userObj != null) {
                user = (User) userObj;
                user.setIsVip(GloabConst.UserIdentity.USER_VIP);
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.LOGIN_INVALID, "未登录", "getUserInfo");
            }
            return user;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取用户信息失败");
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取用户信息失败", "getUserInfo");
        }
    }

    @Override
    public List<Map<String, Object>> getNearbyShops(String tencentKey, int storeId, String latitude, String longitude, Page pageModel) throws LaiKeApiException {
        //优选店铺集
        List<Map<String, Object>> mchStoreList = new ArrayList<>();
        try {
            //判断店铺开关
            if (!this.frontPlugin(storeId, DictionaryConst.Plugin.MCH, null)) {
                return mchStoreList;
            }

            //所有商户下的店铺位置信息集
            List<String> longitudeAndLatitudes = new ArrayList<>();

            //获取所有审核通过的店铺
            MchModel mchModel = new MchModel();
            mchModel.setStore_id(storeId);
            mchModel.setReview_status(DictionaryConst.ExameStatus.EXAME_PASS_STATUS);
            List<MchModel> mchModels = mchModelMapper.select(mchModel);
            for (MchModel mch : mchModels) {
                Map<String, Object> mchMap = new HashMap<>(16);
                //获取商户logo图片地址
                String imgLogoUrl = this.getImgPath(mch.getLogo(), mchModel.getStore_id());
                //获取商户所属人信息
                User configUser = new User();
                configUser.setUser_id(mch.getUser_id());
                configUser.setStore_id(mch.getStore_id());
                configUser = userBaseMapper.selectOne(configUser);
                //获取商户下的店铺信息
                if (configUser == null) {
                    continue;
                }
                //店铺经纬度处理
                StringBuffer longitudeAndLatitude = new StringBuffer();
                String configLatitude = mch.getLatitude();
                String configLongitude = mch.getLongitude();
                if (StringUtils.isNotEmpty(configLatitude) && StringUtils.isNotEmpty(configLongitude)) {
                    longitudeAndLatitude.append(configLatitude).append(",").append(configLongitude);
                    longitudeAndLatitudes.add(longitudeAndLatitude.toString());
                } else {
                    logger.debug("当前店铺id{} 没有位置,不进行推荐 ", mch.getId());
                    continue;
                }
                //计算离当前店铺距离
                String to = String.join(";", longitudeAndLatitudes);
                String addressStoreJson = TengxunMapUtil.getStoreDstance(tencentKey, to, latitude + "," + longitude);
                Map<String, List<Map<String, String>>> rowsMap = JSON.parseObject(addressStoreJson, new TypeReference<Map<String, List<Map<String, String>>>>() {
                });
                if (rowsMap != null) {
                    for (Map<String, String> elementsMap : rowsMap.get(GloabConst.ManaValue.MANA_VALUE_ROWS)) {
                        List<Map<String, Object>> distanceList = JSON.parseObject(elementsMap.get("elements"), new TypeReference<List<Map<String, Object>>>() {
                        });

                        for (Map<String, Object> distanceMap : distanceList) {
                            //距离 米
                            BigDecimal distance = new BigDecimal(distanceMap.get("distance") + "");
                            //换算千米
                            mchMap.put("distance", distance.divide(BigDecimal.valueOf(1000), 2, BigDecimal.ROUND_HALF_UP));
                        }
                    }
                }
                mchMap.put("LongitudeAndLatitude", longitudeAndLatitude);
                mchMap.put("user_name", configUser.getUser_name());
                mchMap.put("user_id", configUser.getUser_id());
                mchMap.put("logo", imgLogoUrl);
                mchMap.put("headimgurl", configUser.getHeadimgurl());
                mchMap.put("source", configUser.getSource());
                mchMap.put("shop_id", mch.getId());
                mchMap.put("name", mch.getName());
                mchStoreList.add(mchMap);
            }
            //升序
            mchStoreList.sort((o1, o2) -> {
                if (o1.containsKey("distance") && o2.containsKey("distance")) {
                    Double name1 = Double.parseDouble(o1.get("distance") + "");//name1是从你list里面拿出来的一个
                    Double name2 = Double.parseDouble(o2.get("distance") + ""); //name1是从你list里面拿出来的第二个name
                    return name1.compareTo(name2);
                } else {
                    return 0;
                }
            });
            List<Map<String, Object>> mchStoreListTemp = new ArrayList<>();
            int maxNum = Math.min(pageModel.getPageSize(), mchStoreList.size());
            for (int i = pageModel.getPageNo(); i < maxNum; i++) {
                mchStoreListTemp.add(mchStoreList.get(i));
            }
            mchStoreList = mchStoreListTemp;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取附近店铺 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取附近店铺失败", "getNearbyShops");
        }

        return mchStoreList;
    }

    @Override
    public List<Map<String, Object>> productsList(List<Map<String, Object>> goodsInfoList, String cartIds, int buyType, String orderHead) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        List<Map<String, Object>> ret = new ArrayList<>();
        try {
            if (StringUtils.isEmpty(cartIds)) {
                int pid = 0;
                int cid = 0;
                int num = 0;
                Integer secId = null;
                for (Map<String, Object> map : goodsInfoList) {
                    if (map.containsKey("pid")) {
                        pid = Integer.parseInt(map.get("pid").toString());
                    } else if (map.containsKey("cid")) {
                        cid = Integer.parseInt(map.get("cid").toString());
                    } else if (map.containsKey("num")) {
                        num = Integer.parseInt(map.get("num").toString());
                    } else if (map.containsKey("sec_id")) {
                        secId = Integer.parseInt(map.get("sec_id").toString());
                        resultMap.put("sec_id", secId);
                    }
                }
                //获取商品信息
                ProductListModel productListModel = new ProductListModel();
                productListModel.setId(pid);
                productListModel = productListModelMapper.selectOne(productListModel);
                if (productListModel != null) {
                    //判断商品是否上架
                    if (DictionaryConst.GoodsStatus.NOT_GROUNDING.equals((Integer.parseInt(productListModel.getStatus())))) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.GOODS_OFF_SHELF, "商品未上架", "productsList");
                    }
                    //砍价订单不需要判断库存,因为在砍价的时候就吧库存扣除了
                    if (!DictionaryConst.OrdersType.ORDERS_HEADER_KJ.equals(orderHead)) {
                        int stockNum;
                        ConfiGureModel confiGureModel = new ConfiGureModel();
                        confiGureModel.setId(cid);
                        confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                        if (confiGureModel != null) {
                            stockNum = confiGureModel.getNum();
                        } else {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品无库存信息", "productsList");
                        }
                        if (stockNum < num) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.STOCK_INSUFFICIENT, "库存不足", "productsList");
                        }
                    }

                    resultMap.put("pid", pid);
                    resultMap.put("cid", cid);
                    resultMap.put("num", num);
                    ret.add(resultMap);
                } else {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商品不存在", "productsList");
                }
            } else {
                //购物车数据不为空
                String[] cartIdsArray = cartIds.split(SplitUtils.DH);
                for (String cartId : cartIdsArray) {
                    if (buyType != 0) {
                        Example example = new Example(BuyAgainModal.class);
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andGreaterThan("goods_num", 0);
                        criteria.andEqualTo("id", cartId);
                        List<BuyAgainModal> buyAgainModalList = buyAgainModalMapper.selectByExample(example);
                        for (BuyAgainModal buyAgainModal : buyAgainModalList) {
                            resultMap = new HashMap<>(16);
                            resultMap.put("pid", buyAgainModal.getGoods_id());
                            resultMap.put("num", buyAgainModal.getGoods_num());
                            resultMap.put("cid", buyAgainModal.getSize_id());
                            ret.add(resultMap);
                        }
                    } else {
                        Example example = new Example(CartModel.class);
                        Example.Criteria criteria = example.createCriteria();
                        criteria.andGreaterThan("goods_num", 0);
                        criteria.andEqualTo("id", cartId);
                        List<CartModel> cartModels = cartModelMapper.selectByExample(example);
                        for (CartModel cartModel : cartModels) {
                            resultMap = new HashMap<>(16);
                            resultMap.put("pid", cartModel.getGoods_id());
                            resultMap.put("num", cartModel.getGoods_num());
                            resultMap.put("cid", cartModel.getSize_id());
                            ret.add(resultMap);
                        }
                    }
                }
            }

        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "productsList");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("处理立即购买异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "productsList");
        }
        return ret;
    }

    @Override
    public Map<String, Object> commodityInformation(int storeId, int mchId) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //在售商品数量
            int goodsNum;
            //已售数量
            int quantitySold;
            //收藏数量
            int collectionNum;

            Map<String, Object> parmaMap = new HashMap<>(16);

            List<Integer> goodsStatus = new ArrayList<>();
            //获取商户产品配置
            ProductConfigModel productConfigModel = new ProductConfigModel();
            productConfigModel.setStore_id(storeId);
            productConfigModel = productConfigModelMapper.selectOne(productConfigModel);
            //已上架
            goodsStatus.add(DictionaryConst.GoodsStatus.NEW_GROUNDING);
            if (productConfigModel != null && productConfigModel.getIs_open() != 0) {
                //已下架
                goodsStatus.add(DictionaryConst.GoodsStatus.OFFLINE_GROUNDING);
            }
            //获取匹配到的商品
            parmaMap.put("store_id", storeId);
            parmaMap.put("mch_id", mchId);
            parmaMap.put("mch_status", DictionaryConst.GoodsMchExameStatus.EXAME_PASS_STATUS);
            parmaMap.put("GoodsStatus", goodsStatus);
            //在售商品数量
            goodsNum = productListModelMapper.countProductListDynamic(parmaMap);
            //获取商品销售信息
            quantitySold = productListModelMapper.sumDynamic(parmaMap).intValue();

            //获取收藏数量
            UserCollectionModel userCollectionModel = new UserCollectionModel();
            userCollectionModel.setStore_id(storeId);
            userCollectionModel.setMch_id(mchId);
            collectionNum = userCollectionModelMapper.selectCount(userCollectionModel);

            resultMap.put("quantity_on_sale", goodsNum);
            resultMap.put("quantity_sold", quantitySold);
            resultMap.put("collection_num", collectionNum);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品信息异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "commodityInformation");
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getGoodsCommentList(Map<String, Object> parmaMap) throws LaiKeApiException {
        List<Map<String, Object>> resultList;
        try {
            int storId = (int) parmaMap.get("store_id");

            resultList = commentsModelMapper.getCommentsUserDynamicByPid(parmaMap);
            for (Map<String, Object> map : resultList) {
                int id = Integer.parseInt(map.get("id") + "");
                int attributeId = Integer.parseInt(map.get("attribute_id") + "");
                int anonymous = Integer.parseInt(map.get("anonymous") + "");
                //用户头像
                String headimgurl = map.get("headimgurl") + "";
                //评论时间
                String addTime = DateUtil.dateFormate(map.get("add_time").toString(), GloabConst.TimePattern.YMDHMS);
                //追平时间
                String reviewTime = "";
                if (map.containsKey("review_time")) {
                    reviewTime = DateUtil.dateFormate(map.get("review_time").toString(), GloabConst.TimePattern.YMDHMS);
                }
                //时间处理
                String time = addTime.substring(0, 10);
                //评论图片
                List<Map<String, String>> imgUrls = new ArrayList<>();
                //追平图片
                List<Map<String, String>> reviewImages = new ArrayList<>();
                //回复
                String replyAdmin = "";

                //统计图片数量
                CommentsImgModel commentsImgModel = new CommentsImgModel();
                commentsImgModel.setComments_id(id);
                List<CommentsImgModel> commentsImgModelList = commentsImgModelMapper.select(commentsImgModel);
                if (commentsImgModelList != null) {
                    for (CommentsImgModel commentsImg : commentsImgModelList) {
                        Map<String, String> commentMap = new HashMap<>(16);
                        //获取图片路径
                        String imgUrl = getImgPath(commentsImg.getComments_url(), storId);
                        commentMap.put("url", imgUrl);
                        if (commentsImg.getType() == 0) {
                            //评论图片
                            imgUrls.add(commentMap);
                        } else {
                            //追评
                            reviewImages.add(commentMap);
                        }
                    }
                }
                //获取商品属性
                ConfiGureModel confiGureModel = new ConfiGureModel();
                confiGureModel.setId(attributeId);
                confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                //属性处理
                Map<String, String> attributeMap = DataUtils.cast(SerializePhpUtils.getUnserializeObj(confiGureModel.getAttribute(), Map.class));
                if (attributeMap == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ERROR, "数据错误");
                }
                StringBuilder attribute = new StringBuilder("");
                for (String key : attributeMap.keySet()) {
                    String attribyteKey = key;
                    String attribyteValue = attributeMap.get(key) + "";

                    int index = key.indexOf("_LKT_");
                    if (index > 0) {
                        attribyteKey = key.substring(0, attribyteKey.indexOf("_LKT"));
                        //属性值
                        attribyteValue = attribyteValue.substring(0, attribyteValue.indexOf("_LKT_"));
                    }
                    attribute.append(attribyteKey);
                    attribute.append(":");
                    attribute.append(attribyteValue);
                    attribute.append(",");
                }
                String attributeString = attribute.substring(0, attribute.lastIndexOf(","));

                //是否匿名处理
                if (anonymous == 1) {
                    map.put("user_name", "匿名");
                } else {
                    String userName = "匿名";
                    if (map.containsKey("user_name")) {
                        userName = map.get("user_name") + "";
                    }
                    map.put("user_name", userName);
                }
                //查询该条评论的回复
                ReplyCommentsModel replyCommentsModel = new ReplyCommentsModel();
                replyCommentsModel.setStore_id(storId);
                replyCommentsModel.setCid(id + "");
                replyCommentsModel = replyCommentsModelMapper.selectOne(replyCommentsModel);
                if (replyCommentsModel != null) {
                    replyAdmin = replyCommentsModel.getContent();
                }

                map.put("time", time);
                map.put("add_time", addTime);
                map.put("review_time", reviewTime);
                map.put("headimgurl", headimgurl);
                map.put("images", imgUrls);
                map.put("review_images", reviewImages);
                map.put("attribute_str", attributeString);
                map.put("replyAdmin", replyAdmin);
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "getGoodsCommentList");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取商品评论信息异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsCommentList");
        }
        return resultList;
    }

    @Override
    public boolean validatePhoneCode(String header, String phone, String keyCode) throws LaiKeApiException {
        try {
            //校验验证码
            Object pcodeObj = redisUtil.get(header + phone);
            if (pcodeObj == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_PHONE_CODE_NOT_DATA, "请重新获取验证码", "setPassword");
            }
            String pcode = pcodeObj.toString();
            if (!pcode.equals(keyCode)) {
                throw new LaiKeApiException(ErrorCode.ContainerErrorCode.JWT_VERIFY_FAIL, "验证码不正确", "setPassword");
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "validateSMS");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("校验短信异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "validateSMS");
        }
        return true;
    }

    @Override
    public Map<String, Integer> sign(User user) throws LaiKeApiException {
        Map<String, Integer> resultMap = new HashMap<>(16);
        //签到状态 0 未签 1 签过了
        int signStatus = 0;
        //签到插件是否启用
        int isStatus = 0;
        try {
            //当前时间
            Date sysDate = DateUtil.dateFormateToDate(DateUtil.getEndOfDay(new Date()), GloabConst.TimePattern.YMDHMS);
            //今天开始时间
            Date toDayDate = DateUtil.dateFormateToDate(DateUtil.getStartOfDay(new Date()), GloabConst.TimePattern.YMDHMS);
            if (user != null) {
                //查询签到活动
                SignConfigModel signConfigModel = new SignConfigModel();
                signConfigModel.setStore_id(user.getStore_id());
                if (signConfigModel != null) {
                    isStatus = signConfigModel.getIs_status();
                    //是否提醒
                    int isRemind = signConfigModel.getIs_remind();
                    //签到有效开始时间
                    Date starttime = FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS).parse(signConfigModel.getStarttime());
                    //签到结束时间
                    Date endtime = FastDateFormat.getInstance(GloabConst.TimePattern.YMDHMS).parse(signConfigModel.getEndtime());
                    //是否允许多次
                    int isManyTime = signConfigModel.getIs_many_time();
                    //间隔时间
                    String reset = signConfigModel.getReset();
                    //签到次数
                    int scoreNum = signConfigModel.getScore_num();

                    //签到插件是否开启
                    if (signConfigModel.getIs_status() > 0) {
                        // 今天开始时间大于签到结束时间 或者 今天开始时间小于签到开始时间 签到还没进行
                        if (sysDate == null || endtime.getTime() <= sysDate.getTime() || starttime.getTime() >= sysDate.getTime()) {
                            //关闭插件
                            signConfigModel = new SignConfigModel();
                            signConfigModel.setIs_status(0);
                            isStatus = 0;
                        }

                    }
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("签到流程异常:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "签到流程网络异常", "sigin");
        }
        resultMap.put("is_sign_status", isStatus);
        resultMap.put("sign_status", signStatus);
        return resultMap;
    }

    @Override
    public boolean frontPlugin(int storeId, String pluginCode, Map<String, Integer> pluginMap) throws LaiKeApiException {
        try {
            PluginsModel pluginsModel = pluginsModelMapper.getPluginInfo(pluginCode);
            if (DictionaryConst.Plugin.PLATFORMACTIVITIES.equals(pluginCode)) {
                if (pluginsModel != null) {
                    return true;
                }
                return true;
            }
            int status = 0;
            if (pluginsModel != null) {
                switch (pluginCode.toLowerCase()) {
                    case DictionaryConst.Plugin.COUPON:
                        CouponConfigModel couponConfigModel = new CouponConfigModel();
                        couponConfigModel.setStore_id(storeId);
                        couponConfigModel = couponConfigModelMapper.selectOne(couponConfigModel);
                        if (couponConfigModel != null) {
                            status = couponConfigModel.getIs_status();
                        }
                        break;
                    case DictionaryConst.Plugin.WALLET:
                        //钱包
                        ConfigModel configModel = new ConfigModel();
                        configModel.setStore_id(storeId);
                        configModel = configModelMapper.selectOne(configModel);
                        if (configModel != null) {
                            //钱包没有隐藏
                            if (configModel.getHide_your_wallet() == 0) {
                                PaymentModel paymentModel = new PaymentModel();
                                paymentModel.setClass_name(DictionaryConst.OrderPayType.ORDERPAYTYPE_WALLET_PAY);
                                paymentModel = paymentModelMapper.selectOne(paymentModel);
                                if (paymentModel == null) {
                                    break;
                                }
                                //钱包没有被禁用
                                PaymentConfigModel paymentConfigModel = new PaymentConfigModel();
                                paymentConfigModel.setStore_id(storeId);
                                paymentConfigModel.setPid(paymentModel.getId());
                                paymentConfigModel = paymentConfigModelMapper.selectOne(paymentConfigModel);
                                if (paymentConfigModel == null) {
                                    break;
                                }
                                if (paymentConfigModel.getStatus() == 1) {
                                    status = paymentConfigModel.getStatus();
                                }
                            }
                        }
                        break;
                    case DictionaryConst.Plugin.MCH:
                        MchConfigModel mchConfigModel = new MchConfigModel();
                        mchConfigModel.setStore_id(storeId);
                        mchConfigModel = mchConfigModelMapper.selectOne(mchConfigModel);
                        if (mchConfigModel != null) {
                            status = mchConfigModel.getIs_display();
                        }
                        break;
                    //暂时不知道怎么去判断,统一打开
                    case DictionaryConst.Plugin.DIY:
                    case DictionaryConst.Plugin.MEMBER:
                    default:
                        status = 1;
                        break;
                }
            }
            if (pluginMap != null) {
                pluginMap.put(pluginCode, status);
            }
            return status == 1;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("验证插件是否开启 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "frontPlugin");
        }
    }

    @Override
    public List<Map<String, Object>> getGoodsActive(int storeId) throws LaiKeApiException {
        try {
            return getGoodsActive(storeId, null);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品支持的活动类型 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsActive");
        }
    }

    @Override
    public List<Map<String, Object>> getGoodsActive(int storeId, Integer active) throws LaiKeApiException {
        List<Map<String, Object>> pluginArr = new ArrayList<>();
        try {
            //获取所有商品活动类型(插件)
            List<Map<String, Object>> goodsTagList = dictionaryNameModelMapper.getDicByName(DictionaryConst.DicName.DIC_GOODS_ACTIVE);
            for (Map<String, Object> goodsTagMap : goodsTagList) {
                Map<String, Object> dataMap = new HashMap<>(16);
                int value = StringUtils.stringParseInt(goodsTagMap.get("value"));
                //是否支持活动标识
                boolean isOpen = false;
                dataMap.put("name", goodsTagMap.get("text"));
                dataMap.put("value", value);
                if (DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_PRICE.equals(value) || DictionaryConst.GoodsActive.GOODSACTIVE_VIP_DISCOUNT.equals(value)) {
                    //正价、会员特惠
                    isOpen = true;
                } /*else if (DictionaryConst.GoodsActive.GOODSACTIVE_SECONDS.equals(value) && this.frontPlugin(storeId, DictionaryConst.Plugin.SECONDS, null)) {
                    //秒杀
                    isOpen = true;
                } else if (DictionaryConst.GoodsActive.GOODSACTIVE_INTEGRAL.equals(value) && this.frontPlugin(storeId, DictionaryConst.Plugin.INTEGRAL, null)) {
                    //积分
                    isOpen = true;
                } else if (DictionaryConst.GoodsActive.GOODSACTIVE_SUPPORT_PT.equals(value) && this.frontPlugin(storeId, DictionaryConst.Plugin.GOGROUP, null)) {
                    //拼团是否开启
                    isOpen = true;
                } else if (DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_KJ.equals(value) && this.frontPlugin(storeId, DictionaryConst.Plugin.BARGAIN, null)) {
                    //砍价
                    isOpen = true;
                } else if (DictionaryConst.GoodsActive.GOODSACTIVE_POSITIVE_JP.equals(value) && this.frontPlugin(storeId, DictionaryConst.Plugin.AUCTION, null)) {
                    //竞拍
                    isOpen = true;
                } else if (DictionaryConst.GoodsActive.GOODSACTIVE_SECONDS.equals(value) && this.frontPlugin(storeId, DictionaryConst.Plugin.SECONDS, null)) {
                    //秒杀
                    isOpen = true;
                }*/
                if (active != null) {
                    isOpen = isOpen && active.equals(value);
                }
                dataMap.put("status", isOpen);
                pluginArr.add(dataMap);
            }
            return pluginArr;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商品支持的活动类型 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getGoodsActive");
        }
    }

    @Override
    public void arrangeUserCart(MainVo vo, String userid) throws LaiKeApiException {
        try {
            //获取未登录前用户购物车信息
            CartModel cartModel = new CartModel();
            cartModel.setStore_id(vo.getStoreId());
            cartModel.setToken(vo.getAccessId());
            List<CartModel> cartModelList = cartModelMapper.select(cartModel);
            if (cartModelList != null) {
                for (CartModel cart : cartModelList) {
                    //规格id
                    int sizeId = Integer.parseInt(cart.getSize_id());
                    //购物车数量(未登录前)
                    int goodsNum = cart.getGoods_num();
                    //查询登陆后的用户购物车是否拥有这个商品,有则合并 无则绑定购物车
                    cartModel.setUser_id(userid);
                    cartModel.setGoods_id(cart.getGoods_id());
                    cartModel.setSize_id(cart.getSize_id());
                    CartModel userCart = cartModelMapper.selectOne(cartModel);
                    if (userCart != null) {
                        //当前购物车是自己的则用不用做处理
                        if (userid.equals(userCart.getUser_id())) {
                            continue;
                        }
                        //购物车数量(未登录后)
                        int myGoodsNum = userCart.getGoods_num();
                        //获取该商品某规格库存信息
                        ConfiGureModel confiGureModel = new ConfiGureModel();
                        confiGureModel.setId(sizeId);
                        confiGureModel = confiGureModelMapper.selectOne(confiGureModel);
                        if (confiGureModel != null) {
                            int stokNum = confiGureModel.getNum();
                            // 没登录时购物车数量 + 登入后已存在的购物车数量 >= 库存剩余数量
                            if ((goodsNum + myGoodsNum) >= stokNum) {
                                myGoodsNum = stokNum;
                            } else {
                                myGoodsNum += goodsNum;
                            }
                            //合并数量
                            CartModel updateCart = new CartModel();
                            updateCart.setId(userCart.getId());
                            updateCart.setGoods_num(myGoodsNum);
                            int count = cartModelMapper.updateByPrimaryKeySelective(updateCart);
                            if (count < 1) {
                                logger.info("合并购物车数量失败 参数" + JSON.toJSONString(updateCart));
                            }
                            //删除未登录之前的购物车数据
                            CartModel delCart = new CartModel();
                            delCart.setId(cart.getId());
                            count = cartModelMapper.delete(delCart);
                            if (count < 1) {
                                logger.info("删除购物车(未登录前)失败 参数" + JSON.toJSONString(delCart));
                            }
                        }
                    } else {
                        //登陆用户没有购物车数据,则绑定购物车userid
                        CartModel updateCart = new CartModel();
                        updateCart.setId(cart.getId());
                        updateCart.setUser_id(userid);
                        int count = cartModelMapper.updateByPrimaryKeySelective(updateCart);
                        if (count < 1) {
                            logger.info("用户登陆绑定未登录前的购物车失败 参数" + JSON.toJSONString(updateCart));
                        }
                    }
                }
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "arrangeUserCart");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("整理用户购物车 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "arrangeUserCart");
        }
    }

    @Override
    public Map<String, Object> getAgreement(int storeId, int type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            AgreementModel agreementModel = new AgreementModel();
            agreementModel.setStore_id(storeId);
            agreementModel.setType(type);
            agreementModel = agreementModelMapper.selectOne(agreementModel);

            resultMap.put("agreement", agreementModel.getContent());
            resultMap.put("content", agreementModel.getContent());
            resultMap.put("name", agreementModel.getName());
            return resultMap;
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "getAgreement");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取协议 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAgreement");
        }
    }

    @Override
    public Map<String, Object> settlementProductsInfo(List<Map<String, Object>> products, int storeId, String productType) {
        //商品信息
        Map<String, Object> resutlProducts = new HashMap<>();
        try {
            //商品总价
            BigDecimal orderProductTotal = new BigDecimal(0);
            if (CollectionUtils.isEmpty(products)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数不正确", "settlementProductsInfo");
            }
            //店铺商品信息
            Map<String, List<Map<String, Object>>> mchProductsResult = new HashMap<>();
            //店铺运费处理 products_freight
            Map<String, List<Map<String, Object>>> mchReightResult = new HashMap<>();
            for (Map<String, Object> map : products) {
                map.put("store_id", storeId);
                Map<String, Object> tmpRet;
                //是否免运费
                boolean isFreeFreight = false;
                tmpRet = productListModelMapper.settlementProductsInfo(map);
                if (!CollectionUtils.isEmpty(tmpRet)) {
                    //商品信息
                    tmpRet.putAll(map);
                    // 产品单价 变成分
                    BigDecimal productPrice = new BigDecimal(String.valueOf(tmpRet.get("price")));
                    //如果是秒杀,则获取秒杀价格
                    if (DictionaryConst.OrdersType.ORDERS_HEADER_MS.equals(productType)) {
                        int type = MapUtils.getIntValue(tmpRet, "price_type");
                        //规格价格
                        BigDecimal attrPrice = new BigDecimal(MapUtils.getString(tmpRet, "attrPrice"));
                        if (type == 0) {
                            //百分比
                            productPrice = attrPrice.multiply(productPrice).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
                        }
                    }


                    BigDecimal num = new BigDecimal(String.valueOf(map.get("num")));
                    int pid = Integer.parseInt(String.valueOf(map.get("pid")));
                    String weight = String.valueOf(tmpRet.get("weight"));
                    BigDecimal delta = productPrice.multiply(num);
                    orderProductTotal = orderProductTotal.add(delta);
                    //规格处理
                    String key = "attribute";
                    String attrs = (String) tmpRet.get(key);
                    String sku = GoodsDataUtils.getProductSkuValue(attrs);
                    tmpRet.put("size", sku);
                    tmpRet.remove(key);
                    //图片处理
                    key = "img";
                    String imgPath = getImgPath((String) tmpRet.get(key), storeId);
                    tmpRet.put(key, imgPath);

                    //运费处理
                    if (!isFreeFreight) {
                        //店铺id
                        String mch_key = String.valueOf(tmpRet.get("mch_id"));
                        //运费id
                        String fidStr = MapUtils.getString(tmpRet, "freight", "");
                        BigDecimal freight;

                        //店铺商品运费处理
                        Map<String, Object> freightInfo = new HashMap<>();
                        freightInfo.put("num", num);
                        freightInfo.put("pid", pid);
                        if (!org.apache.commons.lang3.StringUtils.isEmpty(fidStr)) {
                            freight = new BigDecimal(fidStr);
                            freightInfo.put("freight_id", freight);
                        }
                        freightInfo.put("weight", weight);

                        if (mchReightResult.containsKey(mch_key)) {
                            mchReightResult.get(mch_key).add(freightInfo);
                        } else {
                            List<Map<String, Object>> mchProductInfo = new ArrayList<>();
                            mchProductInfo.add(freightInfo);
                            mchReightResult.put(mch_key, mchProductInfo);
                        }

                        //按照店铺分类存储商品信息
                        if (mchProductsResult.containsKey(mch_key)) {
                            mchProductsResult.get(mch_key).add(tmpRet);
                        } else {
                            List<Map<String, Object>> mchProductInfo = new ArrayList<>();
                            mchProductInfo.add(tmpRet);
                            mchProductsResult.put(mch_key, mchProductInfo);
                        }
                    }

                }
            }

            //所有店铺id集合
            Set<String> mchids = mchProductsResult.keySet();
            //多店铺信息和商品信息集合
            List<Map<String, Object>> returnMchInfoAndProducts = new ArrayList<>();
            //店铺信息和店铺对应的商品信息已经店铺商品总价

            //结算界面按店铺结算的基本信息
            for (String mchid : mchids) {
                Map<String, Object> mchInfoAndProducts = new HashMap<>();
                //该店铺的商品
                List<Map<String, Object>> thisMchProducts = mchProductsResult.get(mchid);
                //商品信息
                mchInfoAndProducts.put("list", thisMchProducts);
                //店铺信息
                MchModel mchModel = new MchModel();
                mchModel.setId(Integer.parseInt(mchid));
                mchModel = mchModelMapper.selectOne(mchModel);
                mchInfoAndProducts.put("shop_id", mchModel.getId());
                mchInfoAndProducts.put("shop_name", mchModel.getName());
                mchInfoAndProducts.put("shop_logo", mchModel.getLogo());
                //店铺商品总价
                AtomicInteger mchProductTotal = new AtomicInteger(0);
                for (Map<String, Object> product : thisMchProducts) {
                    int productPrice = BigDecimal.valueOf(Double.parseDouble(String.valueOf(product.get("price"))) * 100).intValue();
                    int num = Integer.parseInt(String.valueOf(product.get("num")));
                    int delta = productPrice * num;
                    mchProductTotal = new AtomicInteger(mchProductTotal.addAndGet(delta));
                }
                mchInfoAndProducts.put("product_total", mchProductTotal.doubleValue() / 100.0);
                returnMchInfoAndProducts.add(mchInfoAndProducts);
            }

            //订单商品总额
            resutlProducts.put("products_total", orderProductTotal);
            //按店铺区分的商品信息
            resutlProducts.put("products", returnMchInfoAndProducts);
            //按店铺分类的商品运费信息
            resutlProducts.put("products_freight", mchReightResult);

            //TODO 以下这两个key没有使用到 ，php中使用的是第一个商品的信息
            resutlProducts.put("product_class", "==");
            resutlProducts.put("product_id", "111");

            return resutlProducts;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.ORDER_SETTLEMENT_ERROR, "获取订单确认信息失败", "settlementProductsInfo");
        }
    }

    @Override
    public List<Integer> getCheckedZiXuanGoodsList(int storeId, int shopId) throws LaiKeApiException {
        List<Integer> goodsIds = new ArrayList<>();
        try {
            //获取商城自营店铺信息
            AdminModel adminModel = new AdminModel();
            adminModel.setStore_id(storeId);
            adminModel.setType(1);
            adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            adminModel = adminModelMapper.selectOne(adminModel);
            //获取当前店铺非自选商品集,当前商品不能和平台自选商品一样,一样则剔除
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setMch_id(shopId);
            productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS + "");
            productListModel.setIs_zixuan("0");
            List<ProductListModel> productListModelList = productListModelMapper.select(productListModel);
            for (ProductListModel productList : productListModelList) {
                //自选商品名称
                String zxName = productList.getProduct_title();
                //查询平台自选商品
                productListModel.setMch_id(adminModel.getShop_id());
                productListModel.setIs_zixuan("1");
                List<ProductListModel> ptGoodsList = productListModelMapper.select(productListModel);
                for (ProductListModel ptGoods : ptGoodsList) {
                    if (!StringUtils.isEmpty(ptGoods.getCommodity_str())) {
                        //获取商品id集 选中后都会往这个字段新增一个id
                        List<Integer> checkedGoodsIds = DataUtils.cast(SerializePhpUtils.getUnserializeArray1(ptGoods.getCommodity_str(), List.class));
                        if (checkedGoodsIds != null) {
                            //是否已经选中了
                            if (checkedGoodsIds.contains(productList.getId())) {
                                goodsIds.add(productList.getId());
                            }
                        }
                        //商品名称是否相同
                        if (ptGoods.getProduct_title().equals(zxName)) {
                            goodsIds.add(productList.getId());
                        }
                    }
                }
            }
            return goodsIds;
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "getZiXuanGoodsList");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取自选商品 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getZiXuanGoodsList");
        }
    }

    @Override
    public Map<String, Object> getMemberPrice(List<Map<String, Object>> params, String userId, int storeId) throws LaiKeApiException {
        try {
            if (CollectionUtils.isEmpty(params)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数异常", "getMemberPrice");
            }
            //默认没有折扣
            BigDecimal gradeRate = new BigDecimal("1");
            ProductListModel productListModel = new ProductListModel();
            int pid = (Integer) ((List<Map<String, Object>>) (params.get(0).get("list"))).get(0).get("pid");
            productListModel.setId(pid);
            productListModel = productListModelMapper.selectOne(productListModel);
            String productType = DictionaryConst.OrdersType.ORDERS_HEADER_GM;
            String activeStr = productListModel.getActive();
            //特惠商品
            if ("6".equals(activeStr)) {
                productType = DictionaryConst.OrdersType.ORDERS_HEADER_TH;
            }
            gradeRate = new BigDecimal(String.valueOf(publicMemberService.getMemberGradeRate(productType, userId, storeId)));
            //订单产品总价
            BigDecimal orderProductTotal = new BigDecimal(0);
            //按照店铺计算
            for (Map<String, Object> mchAllInfo : params) {
                //店铺商品信息
                List<Map<String, Object>> mchProductsInfo = (List<Map<String, Object>>) mchAllInfo.get("list");
                if (CollectionUtils.isEmpty(mchProductsInfo)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数异常", "getMemberPrice");
                }
                //店铺商品总价
                BigDecimal mchSellProductTotal = new BigDecimal(0);
                //会员商品总价
                BigDecimal mchMemberProductTotal = new BigDecimal(0);
                for (Map<String, Object> productInfo : mchProductsInfo) {
                    //产品原始售价
                    BigDecimal productPrice = BigDecimal.valueOf(Double.valueOf(String.valueOf(productInfo.get("price"))));
                    //产品数量
                    BigDecimal num = new BigDecimal(String.valueOf(productInfo.get("num")));
                    //会员价
                    BigDecimal memberPrice = productPrice.multiply(gradeRate);
                    //设置会员价
                    productInfo.put("membership_price", memberPrice);
                    //会员单个产品总价
                    BigDecimal memberTotalPrice = memberPrice.multiply(num);
                    //售价总价
                    BigDecimal delta = productPrice.multiply(num);
                    //单个产品售价总价
                    mchSellProductTotal = mchSellProductTotal.add(delta);
                    //单个产品会员价总价
                    mchMemberProductTotal = mchMemberProductTotal.add(memberTotalPrice);
                    // 优惠后金额 如果没有优惠则原价
                    productInfo.put("amount_after_discount", memberTotalPrice);
                    //订单所有产品总价
                    orderProductTotal = orderProductTotal.add(memberTotalPrice);
                }
                //会员优惠价格
                mchAllInfo.put("grade_rate_amount", DoubleFormatUtil.format((mchSellProductTotal.subtract(mchMemberProductTotal)).doubleValue()));
                //店铺总产品价格
                mchAllInfo.put("product_total", DoubleFormatUtil.format(mchMemberProductTotal.doubleValue()));
            }

            Map<String, Object> result = new HashMap<>();
            //会员优惠
            result.put("grade_rate", gradeRate);
            result.put("products", params);
            //订单商品总价
            result.put("products_total", DoubleFormatUtil.format(orderProductTotal.doubleValue()));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.MEMBER_YOUHUI_ERROR, "会员优惠计算失败", "getMemberPrice");
        }

    }

    @Override
    public boolean sendSms(int storeId, String phone, int type, int smsType, Map<String, String> smsParmaMap) throws LaiKeApiException {
        try {
            StringBuilder redistKey = new StringBuilder();
            //模板名称
            String template;
            //验证码超时时间
            int outTime = 60;
            //注册验证码、通用模板 为五分钟
            if (GloabConst.VcodeCategory.REGISTER_CODE == smsType || GloabConst.VcodeCategory.CURRENCY_CODE == smsType) {
                outTime *= 5;
            }
            String header = RedisDataTool.getSmsHeade(smsType);

            //检查改手机号是否有发送给短信
            if (redisUtil.hasKey(redistKey.toString()) && type == 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.REGISTER_PCODE_NOT_INVALID, "操作频繁,请稍后重试", "sendSms");
            }
            redistKey.append(header);
            redistKey.append(phone);

            //验证短信模板是否存在
            MessageModel messageModel = new MessageModel();
            messageModel.setType1(smsType);
            messageModel.setStore_id(storeId);
            messageModel = messageModelMapper.selectOne(messageModel);
            if (messageModel == null || StringUtils.isEmpty(messageModel.getTemplateCode())) {
                logger.error("短信模板不存在,使用通用模板");
                messageModel = new MessageModel();
                messageModel.setType1(1);
                messageModel.setStore_id(storeId);
                messageModel = messageModelMapper.selectOne(messageModel);
                if (messageModel == null) {
                    logger.error("通用模板不存在");
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.SMS_TEMPLATE_NOT_EXIST, "短信模板不存在", "sendSms");
                }
            }
            template = messageModel.getTemplateCode();
            //获取短信服务api密钥
            MessageConfigModel messageConfigModel = new MessageConfigModel();
            messageConfigModel.setStore_id(storeId);
            messageConfigModel = messageConfigModelMapper.selectOne(messageConfigModel);
            if (messageConfigModel != null) {

                if (type == GloabConst.VcodeCategory.TYPE_VERIFICATION) {
                    //生成x位验证码
                    String pcode = String.format("%06d", new Random().nextInt(999999));
                    smsParmaMap = new HashMap<>(16);
                    smsParmaMap.put("code", pcode);
                }

                //发送短信
                SmsMessageModel smsmodel = MobileUtils.sendSms(phone, template, messageModel.getSignName(), messageConfigModel.getAccessKeyId(), messageConfigModel.getAccessKeySecret(), smsParmaMap);
                String result = "OK";
                boolean flag = result.equals(smsmodel.getCode());
                //测试
                /*SmsMessageModel smsmodel = new SmsMessageModel();
                smsmodel.setMessage("测试");
                boolean flag = true;*/
                if (flag) {
                    if (type == GloabConst.VcodeCategory.TYPE_VERIFICATION) {
                        flag = redisUtil.set(redistKey.toString(), smsParmaMap.get("code"), outTime);
                        if (!flag) {
                            logger.error(smsParmaMap.get("code") + " 验证码保存失败");
                            throw new LaiKeApiException(ErrorCode.SysErrorCode.SAVE_REDIS_FAIL, "网络故障,请稍后再试");
                        }
                    }
                } else {
                    logger.info("短信发送失败 参数:{},原因:{}", JSON.toJSONString(smsParmaMap), smsmodel.getMessage());
                    return false;
                }
                return true;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "平台未配置短信服务", "sendSms");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.info("短信发送失败:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "sendSms");
        }
    }

    @Override
    public boolean sendValidatorSmsTemplate(int storeId, String phone, String code, String template, Map<String, String> smsParmaMap) throws LaiKeApiException {
        try {
            //获取短信服务api密钥
            MessageConfigModel messageConfigModel = new MessageConfigModel();
            messageConfigModel.setStore_id(storeId);
            messageConfigModel = messageConfigModelMapper.selectOne(messageConfigModel);
            if (messageConfigModel != null) {
                //发送短信
                SmsMessageModel smsmodel = MobileUtils.sendSms(phone, code, template, messageConfigModel.getAccessKeyId(), messageConfigModel.getAccessKeySecret(), smsParmaMap);
                String result = "OK";
                boolean flag = result.equals(smsmodel.getCode());
                if (flag) {
                    return true;
                } else {
                    logger.info("短信发送失败 参数:{},原因:{}", JSON.toJSONString(smsParmaMap), smsmodel.getMessage());
                    return false;
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("短信发送失败:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "sendValidatorSmsTemplate");
        }
        return false;
    }

    @Override
    public String getWeiXinToken(int storeId) throws LaiKeApiException {
        String token = "";
        try {
            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(storeId);
            configModel = configModelMapper.selectOne(configModel);
            if (configModel != null) {
                token = jssdk.getAccessToken(configModel.getAppid(), configModel.getAppsecret());
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取微信token 异常:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWeiXinToken");
        }
        return token;
    }

    @Override
    public String getWeiXinAppQrcode(int storeId, int storeType, String path, int width) throws LaiKeApiException {
        InputStream stream = null;
        String imageUrl;
        try {
            //获取微信token
            String token = getWeiXinToken(storeId) + "." + GloabConst.UploadConfigConst.IMG_JPEG;
            //图片名称
            String fileName = BuilderIDTool.getGuid();
            List<MultipartFile> fileList = new ArrayList<>();

            //装载图片参数
            Map<String, Object> imageParmaMap = new HashMap<>(16);
            imageParmaMap.put("scene", fileName);
            imageParmaMap.put("path", path);
            imageParmaMap.put("width", width);
            String postDataJson = JSON.toJSONString(imageParmaMap);
            //请求url
            String apiUrl = String.format(GloabConst.WeiXinUrl.SHARE_B_GRCODE_GET_URL, token);
            stream = HttpUtils.postFile(apiUrl, postDataJson);
            MultipartFile file = new MockMultipartFile(fileName, fileName, MediaType.IMAGE_JPEG_VALUE, stream);
            fileList.add(file);
            List<String> imageUrlList = uploadImage(fileList, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, storeType, storeId);
            imageUrl = imageUrlList.get(0);
            logger.debug("生成小程序二维码路径:" + imageUrl);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("获取小程序二维码 异常:" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getWeiXinAppQrcode");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException io) {
                    io.printStackTrace();
                    logger.error("流关闭异常 " + io.getMessage());
                }
            }
        }
        return imageUrl;
    }

    @Override
    public String getStoreQrcode(int storeId, int storeType, String qrCodeUrl) throws LaiKeApiException {
        InputStream stream = null;
        String imageUrl;
        try {
            //图片名称
            String fileName = BuilderIDTool.getGuid() + "." + GloabConst.UploadConfigConst.IMG_JPEG;
            List<MultipartFile> fileList = new ArrayList<>();

            ConfigModel configModel = new ConfigModel();
            configModel.setStore_id(storeId);
            configModel = configModelMapper.selectOne(configModel);
            String logUrl = "";
            if (configModel != null) {
                logUrl = getImgPath(configModel.getLogo(), storeId);
            }
            File rqcodeFile = ImageTool.builderQrcode(logUrl, null, qrCodeUrl, ImgUploadUtils.DEFAULT_UPLOAD_PATH + fileName);
            if (rqcodeFile == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常");
            }
            stream = new FileInputStream(rqcodeFile);
            MultipartFile file = new MockMultipartFile(fileName, fileName, ImgUploadUtils.getUrlImgByMimeType(fileName), stream);
            fileList.add(file);
            List<String> imageUrlList = uploadImage(fileList, GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST, storeType, storeType);
            imageUrl = imageUrlList.get(0);
            logger.debug("商户logo:" + imageUrl);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.info("获取商户logo 异常:", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getStoreQrcode");
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException io) {
                    logger.error("流关闭异常 ", io);
                }
            }
        }
        return imageUrl;
    }

    @Override
    public boolean addAdminRecord(int storeId, String userId, String text, int type) throws LaiKeApiException {
        try {
            AdminRecordModel adminRecordModel = new AdminRecordModel();
            adminRecordModel.setStore_id(storeId);
            adminRecordModel.setAdmin_name(userId);
            adminRecordModel.setEvent(text);
            adminRecordModel.setType(type);
            adminRecordModel.setAdd_date(new Date());

            int count = adminRecordModelMapper.insertSelective(adminRecordModel);
            if (count > 0) {
                return true;
            }
        } catch (Exception e) {
            logger.info("添加操作记录 异常:", e);
        }
        return false;
    }

    @Override
    public List<DictionaryListModel> getDictionaryList(String name) {
        List<DictionaryListModel> retList = new ArrayList<>();
        try {
            DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
            dictionaryNameModel.setName(name);
            dictionaryNameModel = dictionaryNameModelMapper.selectOne(dictionaryNameModel);
            if (dictionaryNameModel != null) {
                int id = dictionaryNameModel.getId();
                DictionaryListModel dictionaryListModel = new DictionaryListModel();
                dictionaryListModel.setSid(id);
                retList = dictionaryListModelMapper.select(dictionaryListModel);
            }
            return retList;
        } catch (Exception e) {
            logger.info("获取字典 异常:", e);
            throw e;
        }
    }

    @Override
    public List<Map<String, Object>> getJoinCityCounty(int level, int groupId) throws LaiKeApiException {
        List<Map<String, Object>> resultList = new ArrayList<>();
        try {
            AdminCgModel adminCgModel = new AdminCgModel();
            adminCgModel.setG_ParentID(groupId);
            adminCgModel.setG_Level(level);
            List<AdminCgModel> adminCgModelList = adminCgModelMapper.select(adminCgModel);
            for (AdminCgModel adminCg : adminCgModelList) {
                Map<String, Object> map = new HashMap<>(2);
                map.put("GroupID", adminCg.getGroupID());
                map.put("G_CName", adminCg.getG_CName());
                resultList.add(map);
            }

        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("省市级联异常 ", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getJoinCityCounty");
        }
        return resultList;
    }

    @Override
    public void messageUpdate(int storeId, int mchId, int type, String parma, MessageLoggingModal messageLoggingUpdate) throws LaiKeApiException {
        try {
            MessageLoggingModal messageLoggingModal = new MessageLoggingModal();
            messageLoggingModal.setStore_id(storeId);
            messageLoggingModal.setMch_id(mchId);
            messageLoggingModal.setParameter(parma);
            messageLoggingModal.setType(type);
            messageLoggingModal = messageLoggingModalMapper.selectOne(messageLoggingModal);
            if (messageLoggingModal != null) {
                messageLoggingUpdate.setId(messageLoggingModal.getId());
                int row = messageLoggingModalMapper.updateByPrimaryKeySelective(messageLoggingUpdate);
                if (row < 1) {
                    logger.debug("后台消息修改失败 参数:{}", JSON.toJSONString(messageLoggingUpdate));
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("修改后台消息表信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "messageUpdate");
        }
    }

    @Override
    public boolean withdrawals(Withdrawals1Vo vo, User user) throws LaiKeApiException {
        try {
            WithdrawModel withdrawModel = new WithdrawModel();
            withdrawModel.setIs_mch(0);
            int count;
            //当前余额
            BigDecimal accountMoney;
            //可提现金额
            BigDecimal cashableMoney;
            //提现金额
            BigDecimal amoney = new BigDecimal(vo.getAmoney());
            //最低提现金额
            BigDecimal minCharge;
            //最大体现金额
            BigDecimal maxCharge;
            //提现手续费比例
            BigDecimal serviceCharge;
            //实际手续费
            BigDecimal costAmt = BigDecimal.ZERO;
            //记录
            RecordModel recordSave = new RecordModel();
            recordSave.setStore_id(vo.getStoreId());
            recordSave.setUser_id(user.getUser_id());
            //店铺和用户每天只能提现一次
            String startTime = DateUtil.getStartOfDay(new Date());
            String endTime = DateUtil.getEndOfDay(new Date());

            Map<String, Object> parmaMap = new HashMap<>(16);//提现验证规则
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("user_id", user.getUser_id());
            parmaMap.put("is_mch", vo.getShopId() != null ? 1 : 0);
            parmaMap.put("status", "0");
            count = withdrawModelMapper.countDynamic(parmaMap);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "您的上笔申请还未审核，请稍后再试");
            }
            //提现申请是否已达上限 每天可以提现一次
            parmaMap.put("startTime", startTime);
            parmaMap.put("endTime", endTime);
            parmaMap.put("status", "1");
            count = withdrawModelMapper.countDynamic(parmaMap);
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "提现申请次数已达上限！");
            }

            //提示
            String event = "%s%s申请提现:%s元";
            if (vo.getShopId() != null) {
                logger.debug("店主{}进行提现", user.getUser_id());
                recordSave.setIs_mch(1);
                withdrawModel.setIs_mch(1);
                //获取店铺信息
                MchModel mchModel = new MchModel();
                mchModel.setStore_id(vo.getStoreId());
                mchModel.setUser_id(user.getUser_id());
                mchModel.setReview_status(DictionaryConst.ExameStatus.EXAME_PASS_STATUS);
                mchModel = mchModelMapper.selectOne(mchModel);
                if (mchModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在");
                }
                int mchId = mchModel.getId();

                accountMoney = mchModel.getAccount_money();
                cashableMoney = mchModel.getCashable_money();
                if (mchId != vo.getShopId()) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "用户与店铺不匹配");
                }
                //获取商户配置信息
                MchConfigModel mchConfigModel = new MchConfigModel();
                mchConfigModel.setStore_id(vo.getStoreId());
                mchConfigModel = mchConfigModelMapper.selectOne(mchConfigModel);
                if (mchConfigModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.READ_NOT_DATA, "未获取到店铺配置信息");
                }

                minCharge = mchConfigModel.getMin_charge();
                maxCharge = mchConfigModel.getMax_charge();
                serviceCharge = mchConfigModel.getService_charge();
                //金额验证
                validateWithdrawalsAmt(amoney, minCharge, maxCharge);
                //提现金额不能大于最大可提现金额
                if (amoney.compareTo(cashableMoney) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "提现金额不能大于可提现金额！");
                }
                if (serviceCharge.compareTo(BigDecimal.ZERO) > 0) {
                    //计算手续费
                    costAmt = amoney.multiply(serviceCharge);
                }
                //扣减店铺可提现金额
                parmaMap.clear();
                parmaMap.put("t_money", amoney);
                parmaMap.put("store_id", vo.getStoreId());
                parmaMap.put("mch_id", mchModel.getId());
                count = mchModelMapper.withdrawal(parmaMap);
                if (count < 1) {
                    logger.warn("店铺金额扣减失败 mchModelMapper.withdrawal 参数:{}", JSON.toJSONString(parmaMap));
                    return false;
                }

                //提现成功
                event = String.format(event, "店主", user.getUser_id(), amoney);
            } else {
                logger.debug("用户{}进行提现", user.getUser_id());
                recordSave.setIs_mch(0);
                //获取钱包信息
                FinanceConfigModel financeConfigModel = new FinanceConfigModel();
                financeConfigModel.setStore_id(vo.getStoreId());
                financeConfigModel = financeConfigModelMapper.selectOne(financeConfigModel);
                if (financeConfigModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "数据异常!！");
                }
                minCharge = financeConfigModel.getMin_amount();
                maxCharge = financeConfigModel.getMax_amount();
                serviceCharge = financeConfigModel.getService_charge();
                if (serviceCharge.compareTo(BigDecimal.ZERO) > 0) {
                    //实际手续费
                    costAmt = amoney.multiply(serviceCharge);
                }
                //获取会员信息
                User client = userBaseMapper.selectByPrimaryKey(user.getId());
                accountMoney = client.getMoney();
                //会员提现不能提高出自己当前余额
                if (amoney.compareTo(accountMoney) > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "提现金额大于现有金额!！");
                }
                //金额验证
                validateWithdrawalsAmt(amoney, minCharge, maxCharge);
                //扣除余额
                count = userBaseMapper.rechargeUserPrice(user.getId(), amoney.negate());
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络繁忙,请稍后再试");
                }
                //提现成功
                event = String.format(event, "", user.getUser_id(), amoney);
            }
            withdrawModel.setStore_id(vo.getStoreId());
            withdrawModel.setUser_id(user.getUser_id());
            withdrawModel.setName(user.getUser_name());
            withdrawModel.setMobile(vo.getMobile());
            withdrawModel.setBank_id(vo.getBankId());
            withdrawModel.setMoney(amoney);
            withdrawModel.setZ_money(accountMoney);
            withdrawModel.setS_charge(costAmt);
            withdrawModel.setStatus("0");
            withdrawModel.setRecovery(DictionaryConst.ProductRecycle.NOT_STATUS + "");
            withdrawModel.setAdd_date(new Date());
            if (vo.getId() == null) {
                //添加一条提现记录
                count = withdrawModelMapper.insert(withdrawModel);
                if (count < 1) {
                    logger.warn("提现记录添加失败 withdrawModelMapper.insert 参数:{}", JSON.toJSONString(withdrawModel));
                    return false;
                }
            } else {
                //修改提现列表金额
                withdrawModel.setId(vo.getId());
                count = withdrawModelMapper.updateByPrimaryKeySelective(withdrawModel);
                if (count < 1) {
                    logger.warn("提现列表修改金额 参数:{}", JSON.toJSONString(withdrawModel));
                    return false;
                }
            }

            recordSave.setMoney(amoney);
            recordSave.setOldmoney(user.getMoney());
            recordSave.setEvent(event);
            recordSave.setType(2);
            recordSave.setAdd_date(new Date());
            count = recordModelMapper.insertSelective(recordSave);

            if (count < 1) {
                logger.warn("提现记录失败 recordModelMapper.insert 参数:" + JSON.toJSONString(recordSave));
                return false;
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("申请提现 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "withdrawals");
        }
    }

    @Autowired
    private RoleMenuModelMapper roleMenuModelMapper;

    @Override
    public boolean jurisdiction(int storeId, AdminModel admin, String url) throws LaiKeApiException {
        try {
            if (admin != null && admin.getType() != 1) {
                int count = roleMenuModelMapper.countButtonRole(admin.getRole(), url);
                if (count > 0) {
                    return true;
                }
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取后台权限按钮 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "jurisdiction");
        }

        return false;
    }

    /**
     * 提现金额验证
     *
     * @param amoney    -
     * @param minCharge -
     * @return boolean
     * @throws LaiKeApiException; -
     * @author Trick
     * @date 2021/3/8 16:36
     */
    private void validateWithdrawalsAmt(BigDecimal amoney, BigDecimal minCharge, BigDecimal maxCharge) throws LaiKeApiException {
        try {
            if (amoney.floatValue() <= 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "提现金额不能小于等于0");
            }
            //提现金额是否小于最低提现金额
            if (minCharge.compareTo(amoney) > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.WITHDRAWAL_LESS_THAN_AMT, "提现金额不能低于最低提现金额！");
            }
            if (amoney.compareTo(maxCharge) > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.WITHDRAWAL_GREATER_THAN_CASHABLE_AMT, "提现金额大于最大提现金额！");
            }
        } catch (LaiKeApiException l) {
            l.printStackTrace();
            logger.error("提现金额验证 异常 " + l.getMessage());
            throw l;
        }
    }

    @Autowired
    private MchConfigModelMapper mchConfigModelMapper;
    @Autowired
    private FinanceConfigModelMapper financeConfigModelMapper;
    @Autowired
    private WithdrawModelMapper withdrawModelMapper;
    @Autowired
    private RecordModelMapper recordModelMapper;

    @Autowired
    private AdminCgModelMapper adminCgModelMapper;

    @Autowired
    private DictionaryListModelMapper dictionaryListModelMapper;

    @Autowired
    private DictionaryNameModelMapper dictionaryNameModelMapper;

    @Autowired
    private MessageModelMapper messageModelMapper;

    @Autowired
    private MessageConfigModelMapper messageConfigModelMapper;

    @Autowired
    private AdminRecordModelMapper adminRecordModelMapper;

    @Autowired
    private ProductImgModelMapper productImgModelMapper;
    @Autowired
    private Jssdk jssdk;

    @Autowired
    private PluginsModelMapper pluginsModelMapper;

    @Autowired
    private CouponConfigModelMapper couponConfigModelMapper;

    @Autowired
    private MessageLoggingModalMapper messageLoggingModalMapper;
}
