package com.laiketui.modules.backend.services.saas;

import com.laiketui.api.common.admin.PublicRoleService;
import com.laiketui.api.modules.backend.saas.ShopManageService;
import com.laiketui.common.mapper.*;
import com.laiketui.domain.config.ConfigModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.mch.CustomerModel;
import com.laiketui.domain.mch.RoleModel;
import com.laiketui.domain.payment.PaymentConfigModel;
import com.laiketui.domain.payment.PaymentModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.saas.AddShopVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.algorithm.Md5Util;
import com.laiketui.root.utils.algorithm.MobileUtils;
import com.laiketui.root.utils.tool.*;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商户管理
 *
 * @author Trick
 * @date 2021/1/28 9:20
 */
@Service
public class ShopManageServiceImpl implements ShopManageService {
    private final Logger logger = LoggerFactory.getLogger(ShopManageServiceImpl.class);

    @Autowired
    private PublicRoleService publicRoleService;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Map<String, Object> getShopInfo(MainVo vo, String storeName, String startDate, String endDate, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("recycle", DictionaryConst.ProductRecycle.NOT_STATUS);
            if (vo.getStoreId() != 0) {
                parmaMap.put("store_id", vo.getStoreId());
            }
            if (!StringUtils.isEmpty(storeName)) {
                parmaMap.put("store_name", storeName);
            }
            if (!StringUtils.isEmpty(startDate)) {
                parmaMap.put("startDate", startDate);
                if (!StringUtils.isEmpty(endDate)) {
                    parmaMap.put("endDate", endDate);
                }
            }
            parmaMap.put("is_default_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            int total = customerModelMapper.countDynamic(parmaMap);
            List<Map<String, Object>> dataList = customerModelMapper.selectDynamic(parmaMap);

            for (Map<String, Object> map : dataList) {
                List<Map<String, Object>> menuList = null;
                int id = Integer.parseInt(map.get("id") + "");
                int adminId = Integer.parseInt(map.get("admin_id") + "");
                int status = MapUtils.getIntValue(map, "status");
                Date expireDate = DateUtil.dateFormateToDate(map.get("end_date") + "", GloabConst.TimePattern.YMDHMS);
                //处理过期商城
                if (expireDate != null) {
                    storeExpireHandle(id, expireDate);
                }
                String statusName = "启用";
                if (status == 1) {
                    statusName = "禁用";
                } else if (status == 2) {
                    statusName = "锁定";
                }
                map.put("statusName", statusName);
                map.put("add_date", DateUtil.dateFormate(MapUtils.getString(map, "add_date"), GloabConst.TimePattern.YMDHMS));
                map.put("end_date", DateUtil.dateFormate(MapUtils.getString(map, "end_date"), GloabConst.TimePattern.YMDHMS));

                //获取权限
                AdminModel adminModel = new AdminModel();
                adminModel.setId(adminId);
                adminModel = adminModelMapper.selectOne(adminModel);
                if (adminModel != null) {
                    String roleName = "";
                    if (StringUtils.isNotEmpty(adminModel.getRole())) {
                        RoleModel roleModel = roleModelMapper.selectByPrimaryKey(adminModel.getRole());
                        if (roleModel != null) {
                            roleName = roleModel.getName();
                        }
                    }
                    map.put("roleName", roleName);
                    map.put("adminName", adminModel.getName());
                    //只有单id的时候才需要查看权限明细
                    if (vo.getStoreId() > 0) {
                        //获取拥有的权限
                        if (!StringUtils.isEmpty(adminModel.getRole())) {
                            menuList = publicRoleService.getMenuTreeList(Integer.parseInt(adminModel.getRole()));
                        }
                        map.put("roleList", menuList);
                    }
                    map.put("roleId", adminModel.getRole());
                }
            }
            if (vo.getExportType().equals(1)) {
                exportStoreData(dataList, response);
                return null;
            }

            resultMap.put("total", total);
            resultMap.put("dataList", dataList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取商城列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getShopInfo");
        }
        return resultMap;
    }

    private void exportStoreData(List<Map<String, Object>> goodsList, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"商城ID", "商户名", "手机", "价格", "公司名称", "购买时间", "到期时间", "状态"};
            //对应字段
            String[] kayList = new String[]{"id", "name", "mobile", "price", "company", "add_date", "end_date", "statusName"};
            EasyPoiExcelUtil.excelExport("商品列表", headerList, kayList, goodsList, response);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出商品数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportGoodsData");
        }
    }

    /**
     * 商城过期处理
     *
     * @param storeId -
     * @param endDate - 过期时间
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/1/28 10:09
     */
    private void storeExpireHandle(int storeId, Date endDate) throws LaiKeApiException {
        try {
            boolean isExpire = false;
            CustomerModel customerModel = new CustomerModel();
            customerModel.setId(storeId);
            //过期延期7天,超过7天则回收商城
            Date delayedTime = DateUtil.getAddDate(endDate, 7);
            //判断是否过期
            if (DateUtil.dateCompare(new Date(), endDate)) {
                //标记过期
                isExpire = true;
                customerModel.setStatus(CustomerModel.CUSTOMER_STATUS_EXPIRE);
                logger.debug("商城id={}已过期", storeId);
            }
            //判断是否需要回收商城
            if (DateUtil.dateCompare(new Date(), delayedTime)) {
                //标记过期
                customerModel.setStatus(CustomerModel.CUSTOMER_STATUS_EXPIRE);
                logger.debug("商城id={}超时未缴费,回收商城", storeId);
            }
            if (isExpire) {
                int count = customerModelMapper.updateByPrimaryKeySelective(customerModel);
                logger.debug("商城已过期 处理结果{}", count > 0);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("商城过期处理 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "storeExpireHandle");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean setStoreOpenSwitch(Integer storeId) throws LaiKeApiException {
        try {
            int count;
            //获取商城信息
            CustomerModel customerModel = new CustomerModel();
            customerModel.setId(storeId);
            customerModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            customerModel = customerModelMapper.selectOne(customerModel);
            if (customerModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商城不存在");
            }
            CustomerModel customerModelUpdate = new CustomerModel();
            customerModelUpdate.setId(customerModel.getId());
            if (CustomerModel.CUSTOMER_STATUS_OPEN.equals(customerModel.getStatus())) {
                //锁定商城
                customerModelUpdate.setStatus(CustomerModel.CUSTOMER_STATUS_LOCK);
                logger.debug("正在锁定商城id{}", storeId);
                //禁用管理员
                AdminModel adminModel = new AdminModel();
                adminModel.setId(customerModel.getAdmin_id());
                adminModel.setLogin_num(3);
                adminModel.setStatus(AdminModel.STATUS_DISABLE);
                count = adminModelMapper.updateByPrimaryKeySelective(adminModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "禁用失败");
                }
            } else if (CustomerModel.CUSTOMER_STATUS_LOCK.equals(customerModel.getStatus()) || CustomerModel.CUSTOMER_STATUS_EXPIRE.equals(customerModel.getStatus())) {
                if (DateUtil.dateCompare(new Date(), customerModel.getEnd_date())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "商户已到期,请续费再启用");
                }
                logger.debug("正在启用商城id{}", storeId);
                //启用商城
                customerModelUpdate.setStatus(CustomerModel.CUSTOMER_STATUS_OPEN);
                //启用管理员
                AdminModel adminModel = new AdminModel();
                adminModel.setId(customerModel.getAdmin_id());
                adminModel.setLogin_num(0);
                adminModel.setStatus(AdminModel.STATUS_OPEN);
                count = adminModelMapper.updateByPrimaryKeySelective(adminModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "启用失败");
                }
            }
            count = customerModelMapper.updateByPrimaryKeySelective(customerModelUpdate);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败");
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("是否启用开关 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setStoreOpenSwitch");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void setStoreDefaultSwitch(Integer storeId) throws LaiKeApiException {
        try {
            int count = 0;
            //获取商城信息
            CustomerModel customerModel = new CustomerModel();
            customerModel.setId(storeId);
            customerModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            customerModel = customerModelMapper.selectOne(customerModel);
            if (customerModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商城不存在");
            }
            CustomerModel customerModelUpdate = new CustomerModel();
            customerModelUpdate.setId(customerModel.getId());
            customerModelUpdate.setIs_default(1);
            if (customerModel.getIs_default() == 1) {
                //关掉默认,则默认一个商城
                customerModelUpdate.setIs_default(0);
                count = customerModelMapper.setDefault();
            } else {
                //设置默认，把之前默认都清掉
                count = customerModelMapper.removeDefault();
            }
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败");
            }
            count = customerModelMapper.updateByPrimaryKeySelective(customerModelUpdate);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "修改失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("设置默认商城 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setStoreDefaultSwitch");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addStore(AddShopVo vo, String ip) throws LaiKeApiException {
        try {
            int count;
            if (StringUtils.isEmpty(vo.getMobile())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "手机号不能为空");
            } else if (!MobileUtils.isMobile(vo.getMobile())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "手机号格式不正确");
            }
            if (vo.getPrice() == null || vo.getPrice().doubleValue() < 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商城价格不能为空");
            }
            if (StringUtils.isEmpty(vo.getEmail())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "邮箱不能为空");
            }
            if (StringUtils.isEmpty(vo.getEndDate())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "到期时间不能为空");
            } else if (DateUtil.dateCompare(new Date(), DateUtil.dateFormateToDate(vo.getEndDate(), GloabConst.TimePattern.YMDHMS))) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "到期时间不能小于当前时间");
            }
            if (vo.getIsOpen() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "是否启用不能为空");
            }
            if (StringUtils.isNotEmpty(vo.getAdminPwd()) && !DataCheckTool.checkLength(vo.getAdminPwd(), 6, 16)) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "密码不符合规范6-16");
            }

            CustomerModel customerModelOld = null;
            if (vo.getStoreId() != null) {
                customerModelOld = new CustomerModel();
                customerModelOld.setId(vo.getStoreId());
                customerModelOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                customerModelOld = customerModelMapper.selectOne(customerModelOld);
                if (customerModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "商城不存在");
                }
            }
            CustomerModel customerModelSave = new CustomerModel();
            customerModelSave.setName(vo.getStoreName());
            customerModelSave.setCustomer_number(vo.getStoreNo());
            customerModelSave.setCompany(vo.getCompany());
            customerModelSave.setMobile(vo.getMobile());
            customerModelSave.setPrice(vo.getPrice());
            customerModelSave.setEmail(vo.getEmail());
            customerModelSave.setEnd_date(DateUtil.dateFormateToDate(vo.getEndDate(), GloabConst.TimePattern.YMDHMS));
            customerModelSave.setStatus(vo.getIsOpen());
            //新增增段 2021-06-08 11:07:39
            customerModelSave.setContact_address(vo.getContactAddress());
            customerModelSave.setContact_number(vo.getContactNumber());
            customerModelSave.setCopyright_information(vo.getCopyrightInformation());
            customerModelSave.setRecord_information(vo.getRecordInformation());
            customerModelSave.setOfficial_website(vo.getWebsite());
            customerModelSave.setMerchant_logo(vo.getLogUrl());
            //end

            if (StringUtils.isEmpty(vo.getStoreNo())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "客户编号不能为空");
            }
            if (StringUtils.isEmpty(vo.getStoreDomain())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商城根目录域名不能为空");
            }
            if (StringUtils.isEmpty(vo.getCompany())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "公司名称不能为空");
            }
            if (StringUtils.isEmpty(vo.getCopyrightInformation())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "版权信息不能为空");
            }
            if (StringUtils.isEmpty(vo.getRecordInformation())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "备案信息不能为空");
            }
            if (StringUtils.isEmpty(vo.getLogUrl())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "商户logo不能为空");
            }
            if (StringUtils.isEmpty(vo.getAdminAccount())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "管理员账号不能为空");
            }
            if (StringUtils.isEmpty(vo.getStoreName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请填写客户名称");
            }

            CustomerModel customerCount = new CustomerModel();
            if(customerModelOld==null || !customerModelOld.getCustomer_number().equals(vo.getStoreNo())){
                customerCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                customerCount.setCustomer_number(vo.getStoreNo());
                count = customerModelMapper.selectCount(customerCount);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "客户编号已存在");
                }
            }

            if (customerModelOld == null) {
                customerCount = new CustomerModel();
                customerCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                customerCount.setEmail(vo.getEmail());
                count = customerModelMapper.selectCount(customerCount);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "邮箱已存在");
                }
                customerCount = new CustomerModel();
                customerCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                customerCount.setName(vo.getStoreName());
                count = customerModelMapper.selectCount(customerCount);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "客户名称已存在");
                }
                if (StringUtils.isEmpty(vo.getAdminPwd())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "管理员密码不能为空");
                }
                if (vo.getRoleId() == null || vo.getRoleId() == 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择角色权限");
                }
                String password = Md5Util.MD5endoce(vo.getAdminPwd());

                customerModelSave.setAdmin_id(0);
                customerModelSave.setFunction("2");
                customerModelSave.setAdd_date(new Date());
                //添加管理员
                AdminModel adminModel = new AdminModel();
                adminModel.setName(vo.getAdminAccount());
                adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                count = adminModelMapper.selectCount(adminModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "管理员账号已存在");
                }
                //添加商城
                count = customerModelMapper.insertSelective(customerModelSave);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商城添加失败");
                }
                AdminModel adminModelSave = new AdminModel();
                adminModelSave.setPassword(vo.getAdminPwd());
                adminModelSave.setType(AdminModel.TYPE_CLIENT);
                adminModelSave.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                adminModelSave.setStore_id(customerModelSave.getId());
                adminModelSave.setTel(vo.getMobile());
                adminModelSave.setName(vo.getAdminAccount());
                adminModelSave.setRole(vo.getRoleId() + "");
                adminModelSave.setIp(ip);
                adminModelSave.setAdd_date(new Date());
                //校验数据
                adminModelSave = DataCheckTool.checkAdminDataFormate(adminModelSave, false);
                adminModelSave.setPassword(password);
                count = adminModelMapper.insertSelective(adminModelSave);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "管理员添加失败");
                }
                //修改商城管理员
                CustomerModel customerModelTemp = new CustomerModel();
                customerModelTemp.setId(customerModelSave.getId());
                customerModelTemp.setAdmin_id(adminModelSave.getId());
                count = customerModelMapper.updateByPrimaryKeySelective(customerModelTemp);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商城管理员添加失败");
                }

                //添加系统设置
                ConfigModel configModel = new ConfigModel();
                configModel.setStore_id(customerModelSave.getId());
                configModel.setCompany(vo.getCompany());
                configModel.setDomain(vo.getStoreDomain());
                configModel.setUpserver(GloabConst.UploadConfigConst.IMG_UPLOAD_OSS);
                configModel.setModify_date(new Date());
                configModel.setUploadImg("");
                count = configModelMapper.insertSelective(configModel);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "系统设置添加失败");
                }
                //获取支付方式列表
                List<PaymentModel> paymentModelList = paymentModelMapper.selectAll();
                for (PaymentModel paymentModel : paymentModelList) {
                    //添加支付方式
                    PaymentConfigModel paymentConfigModel = new PaymentConfigModel();
                    paymentConfigModel.setStore_id(customerModelSave.getId());
                    paymentConfigModel.setPid(paymentModel.getId());
                    paymentConfigModel.setStatus(paymentModel.getStatus());
                    count = paymentConfigModelMapper.insertSelective(paymentConfigModel);
                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "支付方式添加失败");
                    }
                }
            } else {
                if (!customerModelOld.getEmail().equals(vo.getEmail())) {
                    customerCount = new CustomerModel();
                    customerCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    customerCount.setEmail(vo.getEmail());
                    count = customerModelMapper.selectCount(customerCount);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "邮箱已存在");
                    }
                }

                if (!customerModelOld.getName().equals(vo.getStoreName())) {
                    customerCount = new CustomerModel();
                    customerCount.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    customerCount.setName(vo.getStoreName());
                    count = customerModelMapper.selectCount(customerCount);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "客户名称已存在");
                    }
                }
                //修改管理员账号/密码
                AdminModel adminModelOld = adminModelMapper.selectByPrimaryKey(customerModelOld.getAdmin_id());
                adminModelOld = adminModelMapper.selectOne(adminModelOld);
                if (adminModelOld != null) {
                    AdminModel adminModelUpdate = new AdminModel();
                    adminModelUpdate.setId(adminModelOld.getId());
                    if (!vo.getAdminAccount().equals(adminModelOld.getName())) {
                        AdminModel adminModel = new AdminModel();
                        adminModel.setName(vo.getAdminAccount());
                        adminModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                        count = adminModelMapper.selectCount(adminModel);
                        if (count > 0) {
                            throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "管理员账号已存在");
                        }
                        adminModelUpdate.setName(vo.getAdminAccount());
                    }
                    if (StringUtils.isEmpty(adminModelOld.getTel()) || adminModelOld.getTel().equals(vo.getMobile())) {
                        adminModelUpdate.setTel(vo.getMobile());
                    }
                    if (CustomerModel.CUSTOMER_STATUS_LOCK.equals(vo.getIsOpen())) {
                        adminModelUpdate.setStatus(CustomerModel.CUSTOMER_STATUS_LOCK);
                    }
                    adminModelUpdate.setIp(ip);
                    adminModelUpdate = DataCheckTool.checkAdminDataFormate(adminModelUpdate, true);
                    count = adminModelMapper.updateByPrimaryKeySelective(adminModelUpdate);
                    if (StringUtils.isNotEmpty(vo.getAdminPwd())) {
                        String pwd = Md5Util.MD5endoce(vo.getAdminPwd());
                        if (!adminModelOld.getPassword().equals(pwd)) {
                            adminModelUpdate.setPassword(pwd);
                        }
                    }
                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "修改失败");
                    }
                }
                customerModelSave.setId(customerModelOld.getId());
                count = customerModelMapper.updateByPrimaryKeySelective(customerModelSave);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商城修改失败");
                }
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/编辑商城 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addStore");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delStore(int storeId) throws LaiKeApiException {
        try {
            int count;
            //获取上传信息
            CustomerModel customerModel = new CustomerModel();
            customerModel.setId(storeId);
            customerModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            customerModel = customerModelMapper.selectOne(customerModel);
            if (customerModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "商城不存在");
            }
            //删除商城
            CustomerModel customerModelUpdate = new CustomerModel();
            customerModelUpdate.setId(storeId);
            customerModelUpdate.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
            count = customerModelMapper.updateByPrimaryKeySelective(customerModelUpdate);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商城删除失败");
            }
            //删除管理员
            count = adminModelMapper.delAdminByStoreId(storeId);
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "商城删除失败");
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除商城 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delStore");
        }
    }

    @Override
    public boolean resetAdminPwd(int adminId, String pwd) throws LaiKeApiException {
        try {
            AdminModel adminModel = new AdminModel();
            adminModel.setId(adminId);
            adminModel = adminModelMapper.selectOne(adminModel);
            if (adminModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "管理员不存在");
            }
            if (!StringUtils.isEmpty(pwd)) {
                if (!DataCheckTool.checkNumAndLetter(pwd, 6, 20)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员密码长度为 6-20");
                }
            } else {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "管理员密码不能为空");
            }
            AdminModel adminModelUpdate = new AdminModel();
            adminModelUpdate.setId(adminId);
            adminModelUpdate.setPassword(Md5Util.MD5endoce(pwd));
            adminModelUpdate.setToken("");

            return adminModelMapper.updateByPrimaryKeySelective(adminModelUpdate) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("重置管理员密码 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "resetAdminPwd");
        }
    }

    @Override
    public boolean resetAdminPswd(int adminId, String pwd) throws LaiKeApiException {
        try {
            AdminModel adminModelUpdate = new AdminModel();
            adminModelUpdate.setId(adminId);
            adminModelUpdate.setPassword(Md5Util.MD5endoce(pwd));
            adminModelUpdate.setToken("");
            return adminModelMapper.updateByPrimaryKeySelective(adminModelUpdate) > 0;
        } catch (LaiKeApiException l) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean addAdmin(String u, String p) throws LaiKeApiException {
        try {
            String password = Md5Util.MD5endoce(p);
            AdminModel adminModelSave = new AdminModel();
            adminModelSave.setPassword(p);
            adminModelSave.setType(AdminModel.TYPE_SYSTEM_ADMIN);
            adminModelSave.setAdmin_type(AdminModel.TYPE_SYSTEM_ADMIN);
            adminModelSave.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            adminModelSave.setStore_id(0);
            adminModelSave.setName(u);
            adminModelSave.setAdd_date(new Date());
            adminModelSave.setPassword(password);
            adminModelMapper.insertSelective(adminModelSave);
            return true;
        } catch (LaiKeApiException l) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    @Autowired
    private CustomerModelMapper customerModelMapper;

    @Autowired
    private AdminModelMapper adminModelMapper;

    @Autowired
    private ConfigModelMapper configModelMapper;

    @Autowired
    private UserBaseMapper userBaseMapper;

    @Autowired
    private PaymentModelMapper paymentModelMapper;

    @Autowired
    private PaymentConfigModelMapper paymentConfigModelMapper;

    @Autowired
    private RoleModelMapper roleModelMapper;
}
