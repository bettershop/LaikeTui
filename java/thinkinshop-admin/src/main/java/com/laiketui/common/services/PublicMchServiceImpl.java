package com.laiketui.common.services;

import com.alibaba.fastjson.JSON;
import com.laiketui.api.common.*;
import com.laiketui.api.common.pay.PublicPaymentService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.JumpPathModel;
import com.laiketui.domain.log.MchAccountLogModel;
import com.laiketui.domain.mch.*;
import com.laiketui.domain.order.OrderDataModel;
import com.laiketui.domain.order.OrderModel;
import com.laiketui.domain.product.FreightModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.user.User;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.PageModel;
import com.laiketui.domain.vo.mch.AddFreihtVo;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DataCheckTool;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.SerializePhpUtils;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.*;

/**
 * 关于店铺
 *
 * @author Trick
 * @date 2020/11/13 9:11
 */
@Service
public class PublicMchServiceImpl implements PublicMchService {

    private final Logger logger = LoggerFactory.getLogger(PublicMchServiceImpl.class);

    @Override
    public MchModel verificationMchExis(int storeId, String userId, int shopId) throws LaiKeApiException {
        try {
            MchModel mchModel = new MchModel();
            mchModel.setStore_id(storeId);
            mchModel.setUser_id(userId);
            mchModel.setReview_status(DictionaryConst.MchExameStatus.EXAME_PASS_STATUS.toString());
            mchModel = mchModelMapper.selectOne(mchModel);
            if (mchModel != null) {
                if (mchModel.getId() != shopId) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "非法数据", "verificationMchExis");
                }
                return mchModel;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在", "verificationMchExis");
            }
        } catch (ClassCastException c) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数类型不正确", "verificationMchExis");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("验证店铺是否存在 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "verificationMchExis");
        }
    }

    @Override
    public Map<String, Object> settlement(int storeId, int shopId, String res, int shopAddressId, int storeType) throws LaiKeApiException {

        Map<String, Object> resultMap = new HashMap<>(16);
        MchStoreModel mchStoreModel = new MchStoreModel();
        try {
            int shopStatus = 0;
            String sheng = "";
            String shi = "";
            String xian = "";
            String address = "";
            String extractionCode = "";
            String extractionCodeImg = "";
            if (shopId != 0) {
                mchStoreModel.setStore_id(storeId);
                mchStoreModel.setMch_id(shopId);
                if (shopAddressId != 0) {
                    mchStoreModel.setId(shopAddressId);
                } else {
                    mchStoreModel.setIs_default(1);
                    mchStoreModel = mchStoreModelMapper.selectOne(mchStoreModel);
                    if (mchStoreModel == null) {
                        mchStoreModel = new MchStoreModel();
                        mchStoreModel.setStore_id(storeId);
                        mchStoreModel.setMch_id(shopId);
                        mchStoreModel.setIs_default(null);
                    }
                }
                List<MchStoreModel> mchStoreModels = mchStoreModelMapper.select(mchStoreModel);
                if (mchStoreModels != null && mchStoreModels.size() > 0) {
                    mchStoreModel = mchStoreModels.get(0);
                    sheng = mchStoreModel.getSheng();
                    shi = mchStoreModel.getShi();
                    xian = mchStoreModel.getXian();
                    address = sheng + shi + xian + mchStoreModel.getAddress();
                    shopStatus = 1;
                    //
                    String flag = "payment";
                    if (StringUtils.isNotEmpty(res) && flag.equals(res)) {
                        extractionCode = extractionCode();
                        if (StringUtils.isNotEmpty(extractionCode)) {
                            extractionCodeImg = createQRCodeImg(extractionCode, storeId, storeType);
                        }
                    }
                } else {
                    shopStatus = 0;
                }
            }

            resultMap.put("extraction_code_img", extractionCodeImg);
            resultMap.put("extraction_code", extractionCode);
            resultMap.put("shop_status", shopStatus);
            resultMap.put("sheng", sheng);
            resultMap.put("shi", shi);
            resultMap.put("xian", xian);
            resultMap.put("address", address);
            //门店信息
            resultMap.put("mch_store_info", mchStoreModel);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "settlement");
        }
        return resultMap;
    }

    @Override
    public void clientConfirmReceipt(int storeId, int shopId, String orderno, BigDecimal paymentMoney, BigDecimal allow) throws LaiKeApiException {
        try {
            int count = mchModelMapper.clientConfirmReceipt(shopId, paymentMoney, allow);
            if (count < 1) {
                logger.error("买家自提确认收货,增加卖家收入失败 订单号:" + orderno);
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数异常");
            }
            //获取增加收入后的店铺信息
            MchModel mchModel = new MchModel();
            mchModel.setStore_id(storeId);
            mchModel.setId(shopId);
            mchModel = mchModelMapper.selectOne(mchModel);
            if (mchModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "非法数据", "clientConfirmReceipt");
            }
            //记录店铺收支信息
            MchAccountLogModel mchAccountLogModel = new MchAccountLogModel();
            mchAccountLogModel.setStore_id(mchModel.getStore_id());
            mchAccountLogModel.setMch_id(mchModel.getId().toString());
            mchAccountLogModel.setPrice(paymentMoney);
            mchAccountLogModel.setIntegral(mchModel.getIntegral_money().intValue());
            mchAccountLogModel.setAccount_money(mchModel.getAccount_money());
            mchAccountLogModel.setType(DictionaryConst.MchAccountLogType.MCHACCOUNTLOG_TYPE_ORDER);
            mchAccountLogModel.setAddtime(new Date());
            count = mchAccountLogModelMapper.insertSelective(mchAccountLogModel);
            if (count < 1) {
                logger.error("记录店铺收支信息失败 参数:" + JSON.toJSONString(mchAccountLogModel));
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "verificationMchExis");
            }
        } catch (LaiKeApiException l) {
            logger.error("买家自提确认收货 失败", l);
            throw l;
        } catch (Exception e) {
            logger.error("买家自提确认收货 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "clientConfirmReceipt");
        }
    }

    @Override
    public Map<String, Object> freightList(int storeId, int mchId, String name, Integer isUse, PageModel pageModel) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //运费数量
            int total;
            //运费信息集
            List<Map<String, Object>> freightList;

            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", storeId);
            parmaMap.put("mch_id", mchId);
            parmaMap.put("is_default_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("add_time_sort", DataUtils.Sort.ASC.toString());
            parmaMap.put("pageNo", pageModel.getPageNo());
            parmaMap.put("pageSize", pageModel.getPageSize());
            if (!StringUtils.isEmpty(name)) {
                parmaMap.put("likeName", name);
            }
            if (isUse != null) {
                if (isUse == 1) {
                    parmaMap.put("isNotNullId", "isNotNullId");
                } else if (isUse == 0) {
                    parmaMap.put("isNullId", "isNullId");
                }
            }
            total = freightModelMapper.countFreightInfoLeftGoodsDynamic(parmaMap);
            freightList = freightModelMapper.getFreightInfoLeftGoodsDynamic(parmaMap);
            for (Map<String, Object> map : freightList) {
                map.put("freight", SerializePhpUtils.getUnSerializeByFreight(MapUtils.getString(map, "freight")));
            }

            resultMap.put("total", total);
            resultMap.put("list", freightList);
            resultMap.put("start", pageModel.getPageNum());
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取运费列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "freightList");
        }
        return resultMap;
    }

    @Override
    public boolean freightAdd(AddFreihtVo vo) throws LaiKeApiException {
        try {
            int count;
            boolean isUpdate = false;
            FreightModel freightModelOld = new FreightModel();
            if (vo.getFid() != null && vo.getFid() > 0) {
                isUpdate = true;
                freightModelOld.setId(vo.getFid());
                freightModelOld = freightModelMapper.selectOne(freightModelOld);
            }
            FreightModel freightModel = new FreightModel();
            freightModel.setIs_default(vo.getIsDefault());
            freightModel.setName(vo.getName());
            freightModel.setType(vo.getType());
            String yfRule = null;
            if (StringUtils.isNotEmpty(vo.getHiddenFreight())) {
                yfRule = URLDecoder.decode(vo.getHiddenFreight(), CharEncoding.UTF_8);
            }
            freightModel.setFreight(yfRule);
            //验证数据
            freightModel = DataCheckTool.checkFreightDataFormate(freightModel);
            if (!isUpdate || !freightModelOld.getName().equals(vo.getName())) {
                //验证规则名称是否已存在
                FreightModel freight = new FreightModel();
                freight.setStore_id(vo.getStoreId());
                freight.setMch_id(vo.getShopId());
                freight.setName(freightModel.getName());
                count = freightModelMapper.selectCount(freight);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "规则名称已存在,请重新选择另一个名称", "freightAdd");
                }
            }
            //添加运费
            if (isUpdate) {
                freightModel.setId(freightModelOld.getId());
                count = freightModelMapper.updateByPrimaryKeySelective(freightModel);
            } else {
                freightModel.setStore_id(vo.getStoreId());
                freightModel.setMch_id(vo.getShopId());
                freightModel.setAdd_time(new Date());
                count = freightModelMapper.insertSelective(freightModel);
            }
            //判断是否是设置默认,如果是设置默认,则取消原来默认,修改当前为默认。
            if (vo.getIsDefault() != null && vo.getIsDefault() == 1) {
                //设置当前运费为默认
                vo.setFid(freightModel.getId());
                this.setDefault(vo);
            }

            if (count < 1) {
                logger.info("运费添加/修改失败 参数:" + JSON.toJSONString(freightModel));
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "运费添加/修改失败", "freightAdd");
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加运费 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "freightAdd");
        }
    }

    @Override
    public Map<String, Object> freightModifyShow(int storeId, int mchId, int id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            //运费id字符串集
            StringBuilder selCityArr = new StringBuilder();

            FreightModel freightModel = new FreightModel();
            freightModel.setStore_id(storeId);
            freightModel.setMch_id(mchId);
            freightModel.setId(id);
            freightModel = freightModelMapper.selectOne(freightModel);
            if (freightModel != null) {
                Map<Integer, LinkedHashMap<String, Object>> dataMap = SerializePhpUtils.getUnSerializeByFreight(freightModel.getFreight());

                //运费规则
                List<Map<String, Object>> freightList = new ArrayList<>();
                for (Integer key : dataMap.keySet()) {
                    LinkedHashMap<String, Object> yufeiMap = dataMap.get(key);
                    if (yufeiMap.isEmpty()) {
                        continue;
                    }

                    //参数列表
                    Map<String, Object> parmaMap = new HashMap<>(16);
                    //获取地区名称
                    String[] nameList = yufeiMap.get("name").toString().split(",");
                    parmaMap.put("nameList", nameList);
                    //获取运费信息
                    List<Map<String, Object>> freightInfoList = adminCgModelMapper.getAdminCgInfoDynamic(parmaMap);
                    for (Map<String, Object> map : freightInfoList) {
                        //拼接运费id
                        selCityArr.append(",").append(map.get("GroupID").toString());
                    }
                    //组装运费信息
                    Map<String, Object> yfMap = new HashMap<>(16);
                    yfMap.putAll(yufeiMap);
                    freightList.add(yfMap);
                }
                resultMap.put("list", freightList);
                resultMap.put("freight", freightList);
                resultMap.put("is_default", freightModel.getIs_default());
                resultMap.put("name", freightModel.getName());
                resultMap.put("type", freightModel.getType());
                resultMap.put("sel_city_arr", com.laiketui.root.utils.common.StringUtils.trim(selCityArr.toString(), ",").split(","));
                return resultMap;
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "数据不存在", "freightModifyShow");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("加载编辑运费页面 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "freightModifyShow");
        }
    }

    @Override
    public void freightDel(int storeId, String ids) throws LaiKeApiException {
        try {
            if (!StringUtils.isEmpty(ids)) {
                String[] fidList = ids.split(",");
                for (String id : fidList) {
                    //如果有商品应用了该运费,则不能删除
                    ProductListModel productListModel = new ProductListModel();
                    productListModel.setStore_id(storeId);
                    productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS.toString());
                    productListModel.setFreight(id);
                    int count = productListModelMapper.selectCount(productListModel);
                    if (count > 0) {
                        logger.info("含有商品设置该运费规则,不能删除 参数:" + JSON.toJSONString(productListModel));
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "含有商品设置该运费规则,不能删除", "freightDel");
                    }
                    FreightModel freightModel = new FreightModel();
                    freightModel.setId(Integer.parseInt(id));
                    count = freightModelMapper.deleteByPrimaryKey(freightModel);
                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "服务器繁忙", "freightDel");
                    }
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "非法参数", "freightDel");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除运费 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "freightDel");
        }
    }

    @Override
    public void setDefault(AddFreihtVo vo) throws LaiKeApiException {
        try {
            if (vo.getFid() == null) {
                logger.debug("运费id不能为空");
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "非法数据");
            }
            FreightModel freightModel = new FreightModel();
            freightModel.setId(vo.getFid());
            //获取当前运费信息
            freightModel = freightModelMapper.selectOne(freightModel);
            if (freightModel != null) {
                //之前默认修改成非默认
                freightModelMapper.setDefault(vo.getStoreId(), vo.getShopId(), 0, 1);

                FreightModel updateFreight = new FreightModel();
                updateFreight.setId(freightModel.getId());
                updateFreight.setIs_default(1);
                int count = freightModelMapper.updateByPrimaryKeySelective(updateFreight);
                if (count < 1) {
                    logger.info("设置运费默认失败 参数" + JSON.toJSONString(updateFreight));
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setDefault");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "数据不存在", "setDefault");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("设置默认运费 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setDefault");
        }
    }

    /**
     * 生成提取码
     *
     * @return
     */
    @Override
    public String extractionCode() {
        try {
            String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            StringBuilder stringBuilder = new StringBuilder();
            int codeLen = 6;
            for (int i = 0; i < codeLen; i++) {
                stringBuilder.append(str.charAt(new Random().nextInt(62)));
            }

            long time = System.currentTimeMillis() / 1000;
            long timeEnd = System.currentTimeMillis() / 1000 + 30 * 60 * 1000;

            stringBuilder.append(SplitUtils.DH).append(time).append(SplitUtils.DH).append(timeEnd);
            Example example = new Example(OrderModel.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andCondition(" status in (0,2) ");
            criteria.andEqualTo("extraction_code", stringBuilder.toString());
            OrderModel orderModel = orderModelMapper.selectOneByExample(example);
            if (orderModel != null) {
                return extractionCode();
            } else {
                return stringBuilder.toString();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public String createQRCodeImg(String extractionCode, int storeId, int storeType) throws LaiKeApiException {
        String retImgUrl = "";
        InputStream inputStream = null;
        try {
            File file = QRCode.from(extractionCode).to(ImageType.PNG).withCharset("utf-8").withSize(250, 250).file();
            inputStream = new FileInputStream(file);
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + ImageType.PNG.toString().toLowerCase();
            MultipartFile mfile = new MockMultipartFile(extractionCode, fileName, MediaType.IMAGE_PNG_VALUE, inputStream);
            List<MultipartFile> files = new ArrayList<>();
            files.add(mfile);
            List<String> imageUrlList = publiceService.uploadImage(files, GloabConst.UploadConfigConst.IMG_UPLOAD_OSS, storeType, storeId);
            retImgUrl = imageUrlList.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("生成二维码图片出错：{}", e.getMessage());
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return retImgUrl;
    }

    @Override
    public boolean cancellationShop(int storeId, int mchId) throws LaiKeApiException {
        try {
            //店铺是否有待处理的订单，如果有待处理的订单则不能注销
            MchModel mchModel = new MchModel();
            mchModel.setStore_id(storeId);
            mchModel.setId(mchId);
            mchModel.setRecovery(DictionaryConst.ProductRecycle.NOT_STATUS);
            mchModel = mchModelMapper.selectOne(mchModel);
            if (mchModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "店铺不存在", "cancellationShop");
            }
            //是否有未完成的订单
            int orderNum = mchModelMapper.countMchUnfinishedOrder(storeId, mchModel.getUser_id());
            if (orderNum > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.MCH_RETURN_PROMISE_PRICE_ERROR, "您的店铺账户尚有进行中订单,无法注销");
            }

            //删除店铺
            int count = mchModelMapper.deleteByPrimaryKey(mchModel.getId());
            if (count < 1) {
                logger.info("店铺注销失败 参数L:" + JSON.toJSONString(mchModel));
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "店铺注销失败", "cancellationShop");
            }
            //自营店不能删除
            AdminModel adminModel = adminModelMapper.getAdminCustomer(storeId);
            if (adminModel.getShop_id().equals(mchId)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.OPERATION_FAILED, "自营店不能删除");
            }
            //删除跳转地址
            JumpPathModel jumpPathModel = new JumpPathModel();
            jumpPathModel.setSid(mchModel.getId() + "");
            jumpPathModel.setType0(JumpPathModel.JumpType.JUMP_TYPE0_MCH);
            jumpPathModelMapper.delete(jumpPathModel);

            //删除店铺商品
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(storeId);
            productListModel.setMch_id(mchId);
            List<ProductListModel> productListModelList = productListModelMapper.select(productListModel);
            for (ProductListModel goods : productListModelList) {
                if (!publicGoodsService.delGoodsById(storeId, goods.getId(), mchId)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商品删除失败", "cancellationShop");
                }
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("注销 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "cancellationShop");
        }
    }

    @Override
    public boolean isPromisePay(MainVo vo, String userId) throws LaiKeApiException {
        boolean isPromisePay = false;
        try {
            User user = new User();
            user.setUser_id(userId);
            user = userMapper.selectOne(user);

            MchModel mchModel = new MchModel();
            mchModel.setStore_id(vo.getStoreId());
            mchModel.setUser_id(user.getUser_id());
            mchModel.setRecovery(DictionaryConst.ProductRecycle.NOT_STATUS);
            mchModel = mchModelMapper.selectOne(mchModel);
            if (mchModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.ILLEGAL_INVASION, "非法入侵");
            }

            MchPromiseModel mchPromiseCount = new MchPromiseModel();
            mchPromiseCount.setMch_id(mchModel.getId());
            mchPromiseCount.setStatus(MchPromiseModel.PromiseConstant.STATUS_PAY);
            if (mchPromiseModelMapper.selectCount(mchPromiseCount) > 0) {
                isPromisePay = true;
            }
        } catch (LaiKeApiException e) {
            logger.error("是否缴纳保证金 异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("是否缴纳保证金 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "isPromisePay");
        }
        return isPromisePay;
    }

    @Override
    public Map<String, Object> paymentPromise(MainVo vo, String payType, String pwd, String userId, String orderNo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            User user = new User();
            user.setUser_id(userId);
            user = userMapper.selectOne(user);

            MchConfigModel mchConfigModel = new MchConfigModel();
            mchConfigModel.setStore_id(vo.getStoreId());
            mchConfigModel = mchConfigModelMapper.selectOne(mchConfigModel);
            if (mchConfigModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ERROR, "商城店铺配置未初始化");
            }
            if (mchConfigModel.getPromise_switch() == 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ERROR, "店铺未开启保证金");
            }
            if (isPromisePay(vo, user.getUser_id())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "保证金已经缴纳");
            }
            MchPromiseModel mchPromiseSave = new MchPromiseModel();
            OrderDataModel orderDataSave = new OrderDataModel();

            String sno = publicOrderService.createOrderNo(DictionaryConst.OrdersType.ORDERS_HEADER_MCH_PROMISE);
            if (DictionaryConst.OrderPayType.ORDERPAYTYPE_WALLET_PAY.equals(payType)) {
                mchPromiseSave.setOrderNo(orderNo = sno);
                publicUserService.validatePayPwd(user.getUser_id(), pwd);
                publicPaymentService.walletPay(user.getUser_id(), mchConfigModel.getPromise_amt(), vo.getAccessId());
                orderDataSave.setStatus(1);
            } else {
                //第三方支付
                orderDataSave.setStatus(0);
            }

            //获取用户店铺
            MchModel mchModel = new MchModel();
            mchModel.setStore_id(vo.getStoreId());
            mchModel.setUser_id(user.getUser_id());
            mchModel.setRecovery(DictionaryConst.ProductRecycle.NOT_STATUS);
            mchModel = mchModelMapper.selectOne(mchModel);
            if (mchModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.ILLEGAL_INVASION, "非法入侵");
            }
            //下单操作
            Map<String, Object> dataMap = new HashMap<>(16);
            dataMap.put("paymentAmt", mchConfigModel.getPromise_amt());
            dataMap.put("storeId", mchModel.getStore_id());
            dataMap.put("mchId", mchModel.getId());
            orderDataSave.setData(JSON.toJSONString(dataMap));
            orderDataSave.setTrade_no(sno);
            orderDataSave.setOrder_type(payType);
            orderDataSave.setAddtime(new Date());
            int row = orderDataModelMapper.insertSelective(orderDataSave);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.OPERATION_FAILED, "操作失败");
            }

            if (StringUtils.isNotEmpty(orderNo)) {
                //是否有生成过订单,如果生成过则修改记录
                MchPromiseModel mchPromiseOld = new MchPromiseModel();
                mchPromiseOld.setMch_id(mchModel.getId());
                mchPromiseOld = mchPromiseModelMapper.selectOne(mchPromiseOld);

                //支付记录
                mchPromiseSave.setPay_type(payType);
                mchPromiseSave.setPromise_amt(mchConfigModel.getPromise_amt());
                mchPromiseSave.setStatus(MchPromiseModel.PromiseConstant.STATUS_PAY);
                if (mchPromiseOld == null) {
                    //支付成功
                    mchPromiseSave.setAdd_date(new Date());
                    mchPromiseSave.setMch_id(mchModel.getId());
                    mchPromiseSave.setId(BuilderIDTool.getGuid());
                    row = mchPromiseModelMapper.insertSelective(mchPromiseSave);
                } else {
                    mchPromiseSave.setId(mchPromiseOld.getId());
                    mchPromiseSave.setIs_return_pay(0);
                    //更新缴纳时间
                    mchPromiseSave.setAdd_date(new Date());
                    //修改时间
                    mchPromiseSave.setUpdate_date(new Date());
                    row = mchPromiseModelMapper.updateByPrimaryKeySelective(mchPromiseSave);
                }

                if (row < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PAYMENT_FAIL, "支付失败");
                }
            }
            resultMap.put("total", mchConfigModel.getPromise_amt());
            resultMap.put("orderId", orderDataSave.getId());
            resultMap.put("orderNo", orderDataSave.getTrade_no());
        } catch (LaiKeApiException e) {
            logger.error("支付保证金 异常", e);
            throw e;
        } catch (Exception e) {
            logger.error("支付保证金 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "paymentPromise");
        }
        return resultMap;
    }

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private PublicGoodsService publicGoodsService;

    @Autowired
    private OrderModelMapper orderModelMapper;

    @Autowired
    private MchModelMapper mchModelMapper;

    @Autowired
    private MchStoreModelMapper mchStoreModelMapper;

    @Autowired
    private MchAccountLogModelMapper mchAccountLogModelMapper;

    @Autowired
    private FreightModelMapper freightModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;

    @Autowired
    private AdminCgModelMapper adminCgModelMapper;
    @Autowired
    private AdminModelMapper adminModelMapper;


    @Autowired
    private PublicUserService publicUserService;

    @Autowired
    @Qualifier("publicWechatServiceImpl")
    private PublicPaymentService publicPaymentService;

    @Autowired
    private MchConfigModelMapper mchConfigModelMapper;

    @Autowired
    private MchPromiseModelMapper mchPromiseModelMapper;

    @Autowired
    private PublicOrderService publicOrderService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JumpPathModelMapper jumpPathModelMapper;

    @Autowired
    private OrderDataModelMapper orderDataModelMapper;
}
