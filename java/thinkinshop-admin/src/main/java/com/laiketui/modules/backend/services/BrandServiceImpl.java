package com.laiketui.modules.backend.services;

import com.laiketui.api.modules.backend.BrandService;
import com.laiketui.common.mapper.BrandClassModelMapper;
import com.laiketui.common.mapper.CountryModelMapper;
import com.laiketui.common.mapper.ProductClassModelMapper;
import com.laiketui.common.mapper.ProductListModelMapper;
import com.laiketui.domain.dictionary.CountryModel;
import com.laiketui.domain.product.BrandClassModel;
import com.laiketui.domain.product.ProductClassModel;
import com.laiketui.domain.product.ProductListModel;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.Tool.ExcelParamVo;
import com.laiketui.domain.vo.goods.BrandClassVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.utils.common.SplitUtils;
import com.laiketui.root.utils.common.StringUtils;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.DictionaryConst;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.utils.tool.DateUtil;
import com.laiketui.root.utils.tool.EasyPoiExcelUtil;
import com.laiketui.root.utils.tool.ImgUploadUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 品牌
 *
 * @author Trick
 * @date 2020/12/30 16:58
 */
@Service
public class BrandServiceImpl implements BrandService {
    private final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);


    @Override
    public Map<String, Object> getBrandInfo(BrandClassVo vo, HttpServletResponse response) throws LaiKeApiException {
        Map<String, Object> resultMap = new HashMap<>(16);
        try {
            Map<String, Object> parmaMap = new HashMap<>(16);
            parmaMap.put("store_id", vo.getStoreId());
            parmaMap.put("brand_name", vo.getBrandName());
            parmaMap.put("brand_id", vo.getBrandId());
            parmaMap.put("pageStart", vo.getPageNo());
            parmaMap.put("pageEnd", vo.getPageSize());
            List<Map<String, Object>> productClassModelList = brandClassModelMapper.getBrandClassInfo(parmaMap);
            int total = brandClassModelMapper.countBrandClassInfo(parmaMap);

            //数据处理
            for (Map<String, Object> brandClass : productClassModelList) {
                //图片处理
                String pidUrl = MapUtils.getString(brandClass, "brand_pic");
                pidUrl = publiceService.getImgPath(pidUrl, vo.getStoreId());
                brandClass.put("brand_pic", pidUrl);
                //获取分类
                String[] cidList = StringUtils.trim(MapUtils.getString(brandClass, "categories"), ",").split(",");
                List<String> classNames = new ArrayList<>();
                for (String cid : cidList) {
                    ProductClassModel productClassModel = new ProductClassModel();
                    productClassModel.setCid(Integer.parseInt(cid));
                    productClassModel = productClassModelMapper.selectOne(productClassModel);
                    if (productClassModel != null) {
                        classNames.add(productClassModel.getPname());
                    }
                }
                brandClass.put("categories", classNames);
                brandClass.put("categoriesName", StringUtils.stringImplode(classNames, SplitUtils.DH));
                brandClass.put("brand_time", DateUtil.dateFormate(MapUtils.getString(brandClass, "brand_time"), GloabConst.TimePattern.YMDHMS));
            }
            if (vo.getExportType().equals(1)) {
                exportBrandData(productClassModelList, response);
                return null;
            }
            resultMap.put("brandInfoList", productClassModelList);
            resultMap.put("total", total);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取品牌信息 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getBrandInfo");
        }
        return resultMap;
    }

    //导出品牌
    private void exportBrandData(List<Map<String, Object>> list, HttpServletResponse response) throws LaiKeApiException {
        try {
            //表头
            String[] headerList = new String[]{"ID", "品牌名称", "所属分类", "添加时间"};
            //对应字段
            String[] kayList = new String[]{"brand_id", "brand_name", "categoriesName", "brand_time"};
            ExcelParamVo vo = new ExcelParamVo();
            vo.setTitle("品牌列表");
            vo.setHeaderList(headerList);
            vo.setValueList(kayList);
            vo.setList(list);
            vo.setResponse(response);
            vo.setNeedNo(false);
            EasyPoiExcelUtil.excelExport(vo);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("导出[品牌]数据 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "exportBrandData");
        }
    }

    @Override
    public List<CountryModel> getCountry(MainVo vo) throws LaiKeApiException {
        List<CountryModel> countryModelList;
        try {
            CountryModel countryModel = new CountryModel();
            countryModel.setIs_show(1);
            countryModelList = countryModelMapper.select(countryModel);
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取国家列表 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getCountry");
        }
        return countryModelList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean addBrand(BrandClassVo vo) throws LaiKeApiException {
        try {
            BrandClassModel brandClassOld = new BrandClassModel();
            //是否是修改
            boolean isUpdate = false;
            if (vo.getBrandId() != null) {
                isUpdate = true;
                brandClassOld.setStore_id(vo.getStoreId());
                brandClassOld.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                brandClassOld.setBrand_id(vo.getBrandId());
                brandClassOld = brandClassModelMapper.selectOne(brandClassOld);
                if (brandClassOld == null) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "品牌不存在", "addBrand");
                }
            }
            BrandClassModel saveBrand = new BrandClassModel();
            saveBrand.setStore_id(vo.getStoreId());
            saveBrand.setBrand_name(vo.getBrandName());
            saveBrand.setBrand_pic(ImgUploadUtils.getUrlImgByName(vo.getBrandLogo(), true));
            saveBrand.setCategories(vo.getBrandClass());
            saveBrand.setRemarks(vo.getRemarks());
            saveBrand.setProducer(vo.getProducer().toString());
            if (StringUtils.isEmpty(vo.getBrandClass())) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "品牌类别不能为空", "addBrand");
            }
            if (!isUpdate || !brandClassOld.getBrand_name().equals(vo.getBrandName().trim())) {
                if (StringUtils.isEmpty(saveBrand.getBrand_name())) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ILLEGAL_FAIL, "品牌名称不能为空", "addBrand");
                }
                //判断品牌名称是否存在
                BrandClassModel brandClassModel = new BrandClassModel();
                brandClassModel.setStore_id(vo.getStoreId());
                brandClassModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS);
                brandClassModel.setBrand_name(saveBrand.getBrand_name());
                int count = brandClassModelMapper.selectCount(brandClassModel);
                if (count > 0) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_ALREADY_EXISTS, "品牌名称已存在", "addBrand");
                }

            } else if (brandClassOld.getBrand_name().equals(vo.getBrandName().trim())) {
                saveBrand.setBrand_name(null);
            }
            saveBrand.setCategories("," + vo.getBrandClass() + ",");

            if (isUpdate) {
                if (StringUtils.equals(brandClassOld.getBrand_pic(), vo.getBrandLogo())) {
                    saveBrand.setBrand_pic(null);
                }
                if (StringUtils.equals(brandClassOld.getBrand_pic(), vo.getBrandLogo())) {
                    saveBrand.setCategories(null);
                }
                if (StringUtils.equals(brandClassOld.getProducer(), vo.getProducer().toString())) {
                    saveBrand.setProducer(null);
                }
                if (StringUtils.equals(brandClassOld.getRemarks(), vo.getRemarks())) {
                    saveBrand.setRemarks(null);
                }
            }

            int count;
            if (isUpdate) {
                saveBrand.setBrand_id(vo.getBrandId());
                saveBrand.setStore_id(brandClassOld.getStore_id());
                count = brandClassModelMapper.updateByPrimaryKeySelective(saveBrand);
            } else {
                saveBrand.setBrand_time(new Date());
                //获取最新序号
                int maxSort = brandClassModelMapper.getBrandClassMaxSort(vo.getStoreId());
                saveBrand.setSort(maxSort);
                count = brandClassModelMapper.insertSelective(saveBrand);
            }
            if (count > 0) {
                return true;
            }
        } catch (NullPointerException ne) {
            ne.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数不正确", "addBrand");
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("添加品牌信息 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "addBrand");
        }
        return false;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delBrand(MainVo vo, int brandId) throws LaiKeApiException {
        try {
            //判断该品牌是否绑定了商品
            ProductListModel productListModel = new ProductListModel();
            productListModel.setStore_id(vo.getStoreId());
            productListModel.setBrand_id(brandId);
            productListModel.setRecycle(DictionaryConst.ProductRecycle.NOT_STATUS + "");
            int num = productListModelMapper.selectCount(productListModel);
            if (num > 0) {
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "该品牌绑定了商品,无法删除");
            }
            BrandClassModel brandClassModel = new BrandClassModel();
            brandClassModel.setStore_id(vo.getStoreId());
            brandClassModel.setBrand_id(brandId);
            brandClassModel.setRecycle(DictionaryConst.ProductRecycle.RECOVERY);
            int count = brandClassModelMapper.updateByPrimaryKeySelective(brandClassModel);
            if (count > 0) {
                return true;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            logger.error("获取国家列表 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "getCountry");
        }
        return false;
    }

    @Override
    public boolean brandByTop(MainVo vo, int brandId) throws LaiKeApiException {
        try {
            BrandClassModel brandClassModel = new BrandClassModel();
            brandClassModel.setStore_id(vo.getStoreId());
            brandClassModel.setBrand_id(brandId);
            //获取最新序号
            int maxSort = brandClassModelMapper.getBrandClassMaxSort(vo.getStoreId());
            brandClassModel.setSort(maxSort);
            int count = brandClassModelMapper.updateByPrimaryKeySelective(brandClassModel);
            if (count > 0) {
                return true;
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("品牌置顶 异常" + e.getMessage());
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "网络异常", "brandByTop");
        }
        return false;
    }


    @Autowired
    private ProductClassModelMapper productClassModelMapper;

    @Autowired
    private BrandClassModelMapper brandClassModelMapper;

    @Autowired
    private PubliceService publiceService;

    @Autowired
    private CountryModelMapper countryModelMapper;

    @Autowired
    private ProductListModelMapper productListModelMapper;
}
