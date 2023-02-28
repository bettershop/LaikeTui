package com.laiketui.common.services;

import com.laiketui.api.common.PublicDictionaryService;
import com.laiketui.common.mapper.DictionaryListModelMapper;
import com.laiketui.common.mapper.DictionaryNameModelMapper;
import com.laiketui.common.mapper.ProductClassModelMapper;
import com.laiketui.domain.dictionary.DictionaryListModel;
import com.laiketui.domain.dictionary.DictionaryNameModel;
import com.laiketui.domain.mch.AdminModel;
import com.laiketui.domain.product.ProductClassModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.dic.DicVo;
import com.laiketui.domain.vo.systems.AddDictionaryDetailVo;
import com.laiketui.root.cache.RedisUtil;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.utils.tool.DataUtils;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.PinyinUtils;
import com.laiketui.root.utils.tool.RedisDataTool;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典操作
 *
 * @author Trick
 * @date 2020/12/28 14:55
 */
@Service
public class PublicDictionaryServiceImpl implements PublicDictionaryService {
    private final Logger logger = LoggerFactory.getLogger(PublicDictionaryServiceImpl.class);

    @Override
    public Map<String, Object> getDictionaryByName(String name) throws LaiKeApiException {
        Map<String, Object> resultMap;
        try {
            DicVo dicVo = new DicVo();
            dicVo.setName(name);
            resultMap = getDictionaryByName(dicVo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取字典数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryByName");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getDictionaryByName(DicVo dicVo) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
            dictionaryNameModel.setName(dicVo.getName());
            if (dicVo.getId() != null) {
                //根据id查找字典
                dictionaryNameModel.setId(dicVo.getId());
            }
            if (!dicVo.isShowFather()) {
                dictionaryNameModel.setStatus(DictionaryNameModel.STATUS_OPEN);
                dictionaryNameModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            }
            dictionaryNameModel = dictionaryNameModelMapper.selectOne(dictionaryNameModel);
            resultMap.put("name", dictionaryNameModel);
            if (dictionaryNameModel != null) {
                //获取明细
                DictionaryListModel dictionaryListModel = new DictionaryListModel();
                dictionaryListModel.setSid(dictionaryNameModel.getId());
                if (!dicVo.isShowChild()) {
                    dictionaryListModel.setStatus(DictionaryNameModel.STATUS_OPEN);
                    dictionaryListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                }
                if (dicVo.getSid() != null) {
                    //根据id查找明细
                    dictionaryListModel.setId(dicVo.getSid());
                }
                if (dicVo.getValue() != null) {
                    dictionaryListModel.setValue(dicVo.getValue());
                }
                List<DictionaryListModel> dictionaryListModelList = dictionaryListModelMapper.select(dictionaryListModel);
                resultMap.put("value", dictionaryListModelList);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取字典数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryByName");
        }
        return resultMap;
    }

    @Override
    public List<Map<String, Object>> getDictionaryById(String name, String superName) throws LaiKeApiException {
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("name", name);
            parmaMap.put("sname", superName);

            return dictionaryNameModelMapper.selectDynamic(parmaMap);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取字典数据 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryById");
        }
    }

    @Override
    public Map<String, Object> getDictionaryCatalogList() throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
            dictionaryNameModel.setStatus(DictionaryNameModel.STATUS_OPEN);
            dictionaryNameModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            List<DictionaryNameModel> dictionaryNameModelList = dictionaryNameModelMapper.select(dictionaryNameModel);

            resultMap.put("data", dictionaryNameModelList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取目录下拉 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryCatalogList");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getDictionaryInfo(MainVo vo, Integer id, String dicNo, String key, String value) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("id", id);
            parmaMap.put("code_like", dicNo);
            parmaMap.put("name_like", key);
            parmaMap.put("text_like", value);
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());

            int total = dictionaryNameModelMapper.countDynamicInfo(parmaMap);
            List<Map<String, Object>> dataList = dictionaryNameModelMapper.selectDynamicInfo(parmaMap);
            for (Map map: dataList) {
                map.put("add_date", DateUtil.dateFormate(MapUtils.getString(map,"add_date"), GloabConst.TimePattern.YMDHMS));
            }
            resultMap.put("total", total);
            resultMap.put("list", dataList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取字典明细数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getDictionaryCatalogInfo(MainVo vo, Integer id, String name) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("id", id);
            parmaMap.put("dicName", name);

            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            parmaMap.put("add_date_sort", DataUtils.Sort.DESC.toString());

            int total = dictionaryNameModelMapper.countDynamic1(parmaMap);
            List<Map<String, Object>> dataList = dictionaryNameModelMapper.selectDynamic1(parmaMap);

            resultMap.put("total", total);
            resultMap.put("list", dataList);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取字典目录数据 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryInfo");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> getDictionaryCode(int id) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        String code = "%s_%03d";
        try {
            int count;
            DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
            dictionaryNameModel.setId(id);
            dictionaryNameModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
            dictionaryNameModel = dictionaryNameModelMapper.selectOne(dictionaryNameModel);
            if (dictionaryNameModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "字典不存在");
            }
            //如果是短信模板类别则特殊处理 为短信模板设置提供数据 2021-10-22 14:36:10
            if ("短信模板类型".equals(dictionaryNameModel.getName())) {
                DictionaryListModel dictionaryListSms = new DictionaryListModel();
                dictionaryListSms.setStatus(DictionaryNameModel.STATUS_OPEN);
                dictionaryListSms.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                dictionaryListSms.setSid(dictionaryNameModel.getId());
                List<DictionaryListModel> listSms = dictionaryListModelMapper.select(dictionaryListSms);
                resultMap.put("list", listSms);
            }

            //获取当前字典数量
            DictionaryListModel dictionaryListModel = new DictionaryListModel();
            dictionaryListModel.setSid(id);
            count = dictionaryListModelMapper.selectCount(dictionaryListModel) + 1;
            code = String.format(code, dictionaryNameModel.getDic_code(), count);

            resultMap.put("code", code);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取字典数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryInfo");
        }
        return resultMap;
    }

    private String dicNameCodeBuilder(String codePinYin) throws LaiKeApiException {
        String code = "";
        try {
            DictionaryNameModel dictionaryNameCount = new DictionaryNameModel();
            int count;
            String key = codePinYin.toUpperCase();
            int num = 0;
            do {
                code = String.format("LKT_%s", key);
                dictionaryNameCount.setDic_code(code);
                count = dictionaryNameModelMapper.selectCount(dictionaryNameCount);
                if (count > 0) {
                    //重名规则 +1
                    num++;
                    key = codePinYin.toUpperCase() + num;
                }
            } while (count > 0);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取字典数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getDictionaryInfo");
        }
        return code;
    }

    @Override
    public boolean addDictionaryInfo(Integer id, String name, int isOpen, String token) throws LaiKeApiException {
        try {
            int count = 0;
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(token, redisUtil);
            if (adminModel != null) {
                DictionaryNameModel dictionaryNameModelOld = null;
                if (id != null) {
                    dictionaryNameModelOld = new DictionaryNameModel();
                    dictionaryNameModelOld.setId(id);
                    dictionaryNameModelOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    dictionaryNameModelOld = dictionaryNameModelMapper.selectOne(dictionaryNameModelOld);
                    if (dictionaryNameModelOld == null) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "字典不存在");
                    }
                    if (!DictionaryNameModel.STATUS_CLOSE.equals(dictionaryNameModelOld.getStatus())) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "生效状态禁止修改");
                    }
                }
                if (StringUtils.isEmpty(name)) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "字典名称不能为空");
                }

                if (dictionaryNameModelOld == null || !dictionaryNameModelOld.getName().equals(name)) {
                    DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
                    dictionaryNameModel.setName(StringUtils.trim(name));
                    dictionaryNameModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    count = dictionaryNameModelMapper.selectCount(dictionaryNameModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "数据名称存在");
                    }
                }

                DictionaryNameModel dictionaryNameModelSave = new DictionaryNameModel();
                dictionaryNameModelSave.setName(name);
                dictionaryNameModelSave.setStatus(isOpen);
                if (dictionaryNameModelOld != null) {
                    dictionaryNameModelSave.setId(dictionaryNameModelOld.getId());
                    count = dictionaryNameModelMapper.updateByPrimaryKeySelective(dictionaryNameModelSave);
                } else {
                    dictionaryNameModelSave.setDic_code(dicNameCodeBuilder(PinyinUtils.getPinYinHeadChar(name)).toUpperCase());
                    dictionaryNameModelSave.setAdmin_name(adminModel.getName());
                    dictionaryNameModelSave.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    dictionaryNameModelSave.setAdd_date(new Date());
                    count = dictionaryNameModelMapper.insertSelective(dictionaryNameModelSave);
                }

            }
            return count > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改字典表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addDictionaryInfo");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addDictionaryDetailInfo(AddDictionaryDetailVo vo) throws LaiKeApiException {
        try {
            int count;
            AdminModel adminModel = RedisDataTool.getRedisAdminUserCache(vo.getAccessId(), redisUtil);
            if (StringUtils.isEmpty(vo.getValueName())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "字典值不能为空");
            }
            if (StringUtils.isEmpty(vo.getValueCode())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "字典值代码不能为空");
            }
            DictionaryListModel dictionaryListModelOld = null;
            if (vo.getId() != null) {
                dictionaryListModelOld = new DictionaryListModel();
                dictionaryListModelOld.setId(vo.getId());
                dictionaryListModelOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                dictionaryListModelOld = dictionaryListModelMapper.selectOne(dictionaryListModelOld);
                if (dictionaryListModelOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典值不存在");
                }
            }
            if (dictionaryListModelOld == null) {
                //数据验证
                DictionaryListModel dictionaryListModel = new DictionaryListModel();
                dictionaryListModel.setCode(vo.getDataCode());
                dictionaryListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                count = dictionaryListModelMapper.selectCount(dictionaryListModel);

                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "字典编码已存在");
                }
            }
            DictionaryListModel dictionaryListFather = dictionaryListModelMapper.selectByPrimaryKey(vo.getSid());
            if (dictionaryListFather == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "字典目录不存在");
            }

            DictionaryListModel dictionaryListModelSave = new DictionaryListModel();
            dictionaryListModelSave.setText(vo.getValueCode());
            dictionaryListModelSave.setStatus(vo.getIsOpen());

            //值和code不能相同
            count = dictionaryListModelMapper.countDicListName(vo.getSid(), vo.getValueCode());
            if (count > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "字典值已存在");
            }
            if (dictionaryListModelOld != null) {
                dictionaryListModelSave.setId(dictionaryListModelOld.getId());
                count = dictionaryListModelMapper.updateByPrimaryKeySelective(dictionaryListModelSave);
            } else {
                if (StringUtils.isEmpty(vo.getDataCode())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "字典编码不能为空");
                }
                count = dictionaryListModelMapper.countDicListCode(vo.getSid(), vo.getValueName());
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "字典code已存在");
                }
                dictionaryListModelSave.setS_name("");
                //如果是 短信模板类别 则特殊处理 给短信配置提供数据源 2021-10-22 14:52:26
                if ("短信模板类型".equals(dictionaryListFather.getText())) {
                    logger.debug("短信模板类型 添加数值");
                    DictionaryListModel dictionaryListAttr = dictionaryListModelMapper.selectByPrimaryKey(vo.getAttrId());
                    if (dictionaryListAttr != null) {
                        dictionaryListModelSave.setS_name(dictionaryListAttr.getS_name());
                    }
                }
                dictionaryListModelSave.setValue(vo.getValueName());
                dictionaryListModelSave.setSid(vo.getSid());
                dictionaryListModelSave.setCode(vo.getDataCode());
                dictionaryListModelSave.setAdmin_name(adminModel.getName());
                dictionaryListModelSave.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                dictionaryListModelSave.setAdd_date(new Date());
                count = dictionaryListModelMapper.insertSelective(dictionaryListModelSave);
            }
            if (count < 1) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "操作失败");
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("添加/修改字典表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addDictionaryInfo");
        }
    }

    @Override
    public boolean switchDictionaryDetail(MainVo vo, int id) throws LaiKeApiException {
        try {
            DictionaryListModel dictionaryListModel = dictionaryListModelMapper.selectByPrimaryKey(id);
            if (dictionaryListModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典明细不存在");
            }
            DictionaryListModel dictionaryListUpdate = new DictionaryListModel();
            dictionaryListUpdate.setId(dictionaryListModel.getId());
            int isOpen = 0;
            if (dictionaryListModel.getStatus() == 0) {
                isOpen = 1;
            }
            dictionaryListUpdate.setStatus(isOpen);

            return dictionaryListModelMapper.updateByPrimaryKeySelective(dictionaryListUpdate) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("字典明细开关 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "switchDictionaryDetail");
        }
    }

    @Override
    public boolean switchDictionary(MainVo vo, int id) throws LaiKeApiException {
        try {
            DictionaryNameModel dictionaryNameModel = dictionaryNameModelMapper.selectByPrimaryKey(id);
            if (dictionaryNameModel == null) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典明细不存在");
            }
            DictionaryNameModel dictionaryNameUpdate = new DictionaryNameModel();
            dictionaryNameUpdate.setId(dictionaryNameModel.getId());
            int isOpen = 0;
            if (dictionaryNameModel.getStatus() == 0) {
                isOpen = 1;
            }
            dictionaryNameUpdate.setStatus(isOpen);

            return dictionaryNameModelMapper.updateByPrimaryKeySelective(dictionaryNameUpdate) > 0;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("字典开关 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "switchDictionaryDetail");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delDictionary(List<Integer> idList) throws LaiKeApiException {
        try {
            int count;
            for (Integer id : idList) {
                DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
                dictionaryNameModel.setId(id);
                dictionaryNameModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                dictionaryNameModel = dictionaryNameModelMapper.selectOne(dictionaryNameModel);
                if (dictionaryNameModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典目录不存在");
                }
                if (dictionaryNameModel.getStatus() == 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典生效中无法删除");
                }
                if (dictionaryNameModel.getIs_core() == 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "系统字典无法删除");
                }
                DictionaryListModel dictionaryListCount = new DictionaryListModel();
                dictionaryListCount.setSid(id);
                dictionaryListCount.setRecycle(0);
                count = dictionaryListModelMapper.selectCount(dictionaryListCount);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "该数据名称下有关联数据值，无法删除!");
                }

                //删除字典
                DictionaryNameModel dictionaryNameModelUpdate = new DictionaryNameModel();
                dictionaryNameModelUpdate.setId(dictionaryNameModel.getId());
                dictionaryNameModelUpdate.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                count = dictionaryNameModelMapper.updateByPrimaryKeySelective(dictionaryNameModelUpdate);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "字典删除失败");
                }
            }
            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除字典目录 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delDictionary");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delDictionaryDetailInfo(List<Integer> idList) throws LaiKeApiException {
        try {
            for (Integer id : idList) {
                DictionaryListModel dictionaryListModel = new DictionaryListModel();
                dictionaryListModel.setId(id);
                dictionaryListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                dictionaryListModel = dictionaryListModelMapper.selectOne(dictionaryListModel);
                if (dictionaryListModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典明细条目不存在");
                }
                if (dictionaryListModel.getStatus() == 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典生效中,删除失败");
                }
                DictionaryListModel dictionaryListModelUpdate = new DictionaryListModel();
                dictionaryListModelUpdate.setId(dictionaryListModel.getId());
                dictionaryListModelUpdate.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
                int count = dictionaryListModelMapper.updateByPrimaryKeySelective(dictionaryListModelUpdate);
                if (count < 1) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障");
                }
                DictionaryNameModel dictionaryNameModel = new DictionaryNameModel();
                dictionaryNameModel.setId(dictionaryListModel.getSid());
                dictionaryNameModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                dictionaryNameModel = dictionaryNameModelMapper.selectOne(dictionaryNameModel);
                if (dictionaryNameModel == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "字典目录不存在");
                }
                //特殊字典值校验
                if ("商品分类".equals(dictionaryNameModel.getName())) {
                    //查询当前级别正是否在使用
                    ProductClassModel productClassModel = new ProductClassModel();
                    productClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                    productClassModel.setLevel(Integer.parseInt(dictionaryListModel.getValue()));
                    count = productClassModelMapper.selectCount(productClassModel);
                    if (count > 0) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "该层级别正在使用,无法删除");
                    }
                }
                //删除特殊字典值
                if ("属性名".equals(dictionaryNameModel.getName())) {
                    //删除下级
                    count = dictionaryNameModelMapper.delLower(dictionaryNameModel.getName());
                    if (count < 1) {
                        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络故障");
                    }
                }
            }

            return true;
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("删除字典明细 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "delDictionaryDetailInfo");
        }
    }

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private DictionaryNameModelMapper dictionaryNameModelMapper;

    @Autowired
    private DictionaryListModelMapper dictionaryListModelMapper;

    @Autowired
    private ProductClassModelMapper productClassModelMapper;
}
