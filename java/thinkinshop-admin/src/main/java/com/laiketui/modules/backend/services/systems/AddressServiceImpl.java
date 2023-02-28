package com.laiketui.modules.backend.services.systems;

import com.laiketui.api.modules.backend.systems.AddressService;
import com.laiketui.common.mapper.ServiceAddressModelMapper;
import com.laiketui.domain.mch.ServiceAddressModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.AddressVo;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址管理
 *
 * @author Trick
 * @date 2021/1/15 9:28
 */
@Service
public class AddressServiceImpl implements AddressService {
    private final Logger logger = LoggerFactory.getLogger(AddressServiceImpl.class);

    @Override
    public Map<String, Object> getAddressInfo(MainVo vo, Integer id, String name, Integer type) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            if (type == null || type < 1) {
                type = ServiceAddressModel.TYPE_RETURN_GOODS;
            }
            //设置默认地址
            int count = serviceAddressModelMapper.setDefualtAddress(vo.getStoreId(), type);
            logger.debug("是否存没有默认地址 {}", count > 0);
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("uid", "admin");
            if (StringUtils.isNotEmpty(name)) {
                parmaMap.put("nameOrTel", name);
            }
            if (id != null && id > 0) {
                parmaMap.put("id", id);
            }
            parmaMap.put("is_default_sort", DataUtils.Sort.DESC.toString());
            parmaMap.put("type", type);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());

            List<Map<String, Object>> dataList = serviceAddressModelMapper.selectDynamic(parmaMap);
            int total = serviceAddressModelMapper.countDynamic(parmaMap);

            resultMap.put("list", dataList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取地址列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getAddressInfo");
        }
        return resultMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addAddressInfo(AddressVo vo) throws LaiKeApiException {
        try {
            int count;
            ServiceAddressModel serviceAddressModelOld = null;
            if (vo.getId() != null && vo.getId() > 0) {
                serviceAddressModelOld = new ServiceAddressModel();
                serviceAddressModelOld.setStore_id(vo.getStoreId());
                serviceAddressModelOld.setId(vo.getId());
                serviceAddressModelOld = serviceAddressModelMapper.selectOne(serviceAddressModelOld);
                if (serviceAddressModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "地址不存在");
                }
            }
            if (StringUtils.isEmpty(vo.getName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "联系人不能为空");
            }
            if (vo.getType() == null || vo.getType() < 1) {
                vo.setType(ServiceAddressModel.TYPE_RETURN_GOODS);
            }
            if (StringUtils.isEmpty(vo.getShen())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择省/市辖区");
            }
            if (StringUtils.isEmpty(vo.getShi()) || StringUtils.isEmpty(vo.getXian())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "请选择区县");
            }
            if (StringUtils.isEmpty(vo.getAddress())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "详细地址不能为空");
            }
            if (vo.getCode() == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "邮政编码不能为空");
            } else if (String.valueOf(vo.getCode()).length() != 6) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "邮政编码为6位数字");
            }

            if (StringUtils.isEmpty(vo.getTel())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "联系人电话不能为空");
            } else if (vo.getTel().length() > 15) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "联系电话格式错误");
            } else {
                if (serviceAddressModelOld == null || !vo.getTel().equals(serviceAddressModelOld.getTel())) {
                    //验证电话是否存在
                    ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
                    serviceAddressModel.setStore_id(vo.getStoreId());
                    serviceAddressModel.setUid("admin");
                    serviceAddressModel.setTel(vo.getTel());
                    serviceAddressModel.setType(vo.getType());
                    count = serviceAddressModelMapper.selectCount(serviceAddressModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "联系电话已存在");
                    }
                }
            }
            //如果是默认,则取消之前的默认
            if (vo.getIsDefault() == 1) {
                serviceAddressModelMapper.updateDefualtClear(vo.getStoreId(), vo.getType());
            }
            //添加/修改地址
            ServiceAddressModel serviceAddressModelSave = new ServiceAddressModel();
            serviceAddressModelSave.setUid("admin");
            serviceAddressModelSave.setTel(vo.getTel());
            serviceAddressModelSave.setName(vo.getName());
            serviceAddressModelSave.setCode(vo.getCode());
            serviceAddressModelSave.setType(vo.getType());
            serviceAddressModelSave.setSheng(vo.getShen());
            serviceAddressModelSave.setShi(vo.getShi());
            serviceAddressModelSave.setXian(vo.getXian());
            serviceAddressModelSave.setAddress(vo.getAddress());
            serviceAddressModelSave.setIs_default(vo.getIsDefault());
            serviceAddressModelSave.setAddress_xq(vo.getShen() + vo.getShi() + vo.getXian() + vo.getAddress());

            if (serviceAddressModelOld != null) {
                serviceAddressModelSave.setId(vo.getId());
                count = serviceAddressModelMapper.updateByPrimaryKeySelective(serviceAddressModelSave);
            } else {
                serviceAddressModelSave.setStore_id(vo.getStoreId());
                serviceAddressModelSave.setCode(vo.getCode());
                count = serviceAddressModelMapper.insertSelective(serviceAddressModelSave);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加/编辑地址信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addAddressInfo");
        }
    }

    @Override
    public void setDefaultAddress(MainVo vo, int id) throws LaiKeApiException {
        try {
            ServiceAddressModel serviceAddressModelOld = new ServiceAddressModel();
            serviceAddressModelOld.setStore_id(vo.getStoreId());
            serviceAddressModelOld.setId(id);
            serviceAddressModelOld = serviceAddressModelMapper.selectOne(serviceAddressModelOld);
            if (serviceAddressModelOld == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "地址不存在");
            }
            int isDefault = 1;
            if (serviceAddressModelOld.getIs_default() == isDefault) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "当前地址已是默认");
            }
            //取消之前的默认
            serviceAddressModelMapper.updateDefualtClear(vo.getStoreId(), serviceAddressModelOld.getType());
            //设置当前为默认
            ServiceAddressModel serviceAddressUpdate = new ServiceAddressModel();
            serviceAddressUpdate.setIs_default(isDefault);
            serviceAddressUpdate.setId(id);
            int row = serviceAddressModelMapper.updateByPrimaryKeySelective(serviceAddressUpdate);
            if (row < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "设置失败", "setDefaultAddress");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("设置默认 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "setDefaultAddress");
        }
    }

    @Override
    public boolean delAddress(MainVo vo, int id, int type) throws LaiKeApiException {
        try {
            ServiceAddressModel serviceAddressModel = new ServiceAddressModel();
            serviceAddressModel.setStore_id(vo.getStoreId());
            serviceAddressModel.setId(id);
            serviceAddressModel = serviceAddressModelMapper.selectOne(serviceAddressModel);
            if (serviceAddressModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "地址不存在");
            }
            int count = serviceAddressModelMapper.deleteByPrimaryKey(id);
            if (count > 0) {
                //设置默认
                count = serviceAddressModelMapper.setDefualtAddress(vo.getStoreId(), type);
            }

            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除地址 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delAddress");
        }
    }


    @Autowired
    private ServiceAddressModelMapper serviceAddressModelMapper;

}
