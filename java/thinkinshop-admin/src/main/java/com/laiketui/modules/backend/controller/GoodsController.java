package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.*;
import com.laiketui.domain.annotation.ParamsMapping;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.goods.*;
import com.laiketui.domain.vo.mch.AddFreihtVo;
import com.laiketui.domain.vo.mch.ApplyShopVo;
import com.laiketui.domain.vo.mch.UploadMerchandiseVo;
import com.laiketui.api.common.PubliceService;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.consts.GloabConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 后台-商品管理
 *
 * @author Trick
 * @date 2020/12/28 17:26
 */
@Api(tags = "后台-商品管理")
@RestController
@RequestMapping("/admin/goods")
public class GoodsController {

    @Autowired
    private GoodsDubboService goodsDubboService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private FreightService freightService;

    @Autowired
    private TaoBaoService taoBaoService;

    @Autowired
    private StockService stockService;

    @ApiOperation("获取类别信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "类型id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getClass")
    @HttpApiMethod(apiKey = "admin.goods.getClass")
    public Result getClass(MainVo vo, Integer classId, Integer brandId) {
        try {
            Map<String, Object> result = goodsDubboService.getClass(vo, classId, brandId);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("选择类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "商品类型id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "brandId", value = "品牌id", dataType = "int", paramType = "form")
    })
    @PostMapping("/choiceClass")
    @HttpApiMethod(apiKey = "admin.goods.choiceClass")
    public Result choiceClass(MainVo vo, Integer classId, Integer brandId) {
        try {
            return Result.success(goodsDubboService.choiceClass(vo, classId, brandId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取商品列表(规格)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attribute_str", value = "属性id,多个用','", dataType = "string", paramType = "form")
    })
    @PostMapping("/getGoodsConfigureList")
    @HttpApiMethod(apiKey = "admin.goods.getGoodsConfigureList")
    public Result getGoodsConfigureList(GoodsConfigureVo vo) {
        try {
            return Result.success(goodsDubboService.getGoodsConfigureList(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商品属性(规格)名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attribute_str", value = "属性id,多个用','", dataType = "string", paramType = "form")
    })
    @PostMapping("/get_attribute_name")
    @HttpApiMethod(apiKey = "admin.goods.get_attribute_name")
    public Result get_attribute_name(MainVo vo, @RequestParam("attribute_str") String attributes) {
        try {
            Map<String, Object> result = goodsDubboService.getAttributeName(vo, attributes);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商品属性(规格)值")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attribute_str", value = "属性id,多个用','", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "attr_arr", value = "属性id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getAttributeValue")
    @HttpApiMethod(apiKey = "admin.goods.getAttributeValue")
    public Result getAttributeValue(MainVo vo, @RequestParam("attribute_str") String attributes, @RequestParam("attr_arr") Integer attrId) {
        try {
            Map<String, Object> result = goodsDubboService.getAttributeValue(vo, attributes, attrId);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加自营店铺")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "logo", value = "logo URL", required = true, dataType = "string", paramType = "form"),
    })
    @PostMapping("/addMch")
    @HttpApiMethod(apiKey = "admin.goods.addMch")
    public Result addMch(ApplyShopVo vo, String logo) {
        try {
            return Result.success(goodsDubboService.addMch(vo, logo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取添加商品页面数据")
    @PostMapping("/getAddPage")
    @HttpApiMethod(apiKey = "admin.goods.getAddPage")
    public Result getAddPage(MainVo vo) {
        try {
            return Result.success(goodsDubboService.getAddPage(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("根据id获取商品信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/getGoodsInfoById")
    @HttpApiMethod(apiKey = "admin.goods.getGoodsInfoById")
    public Result getGoodsInfoById(MainVo vo, int goodsId) {
        try {
            return Result.success(goodsDubboService.getGoodsInfoById(vo, goodsId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加商品")
    @PostMapping("/addGoods")
    @HttpApiMethod(apiKey = "admin.goods.addGoods")
    public Result addGoods(UploadMerchandiseVo vo) {
        try {
            return Result.success(goodsDubboService.addGoods(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("修改序号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "商品id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "sort", value = "序号", required = true, dataType = "int", paramType = "form"),
    })
    @PostMapping("/editSort")
    @HttpApiMethod(apiKey = "admin.goods.editSort")
    public Result editSort(MainVo vo, Integer id, Integer sort) {
        try {
            goodsDubboService.editSort(vo, id, sort);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("批量删除商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id 集", dataType = "string", required = true, paramType = "form")
    })
    @PostMapping("/delGoodsById")
    @HttpApiMethod(apiKey = "admin.goods.delGoodsById")
    public Result delGoodsById(MainVo vo, String goodsId) {
        try {
            boolean result = goodsDubboService.delGoodsById(vo, goodsId);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("上下架商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsIds", value = "商品id集", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "status", value = "1=上架 0=下架", dataType = "int", paramType = "form"),
    })
    @PostMapping("/upperAndLowerShelves")
    @HttpApiMethod(apiKey = "admin.goods.upperAndLowerShelves")
    public Result upperAndLowerShelves(MainVo vo, String goodsIds, Integer status) {
        try {
            goodsDubboService.upperAndLowerShelves(vo, goodsIds, status);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("商品置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "int", paramType = "form")
    })
    @PostMapping("/goodsByTop")
    @HttpApiMethod(apiKey = "admin.goods.goodsByTop")
    public Result goodsByTop(MainVo vo, int goodsId) {
        try {
            boolean result = goodsDubboService.goodsByTop(vo, goodsId);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("上下移动商品位置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "moveGoodsId", value = "上一个/下一个商品的id", dataType = "int", paramType = "form")
    })
    @PostMapping("/goodsMovePosition")
    @HttpApiMethod(apiKey = "admin.goods.goodsMovePosition")
    public Result goodsMovePosition(MainVo vo, int goodsId, int moveGoodsId) {
        try {
            boolean result = goodsDubboService.goodsMovePosition(vo, goodsId, moveGoodsId);
            return Result.success(result);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取分类信息")
    @PostMapping("/getClassInfo")
    @HttpApiMethod(apiKey = "admin.goods.getClassInfo")
    public Result getClassInfo(GoodsClassVo vo, HttpServletResponse response) {
        try {
            return Result.success(goodsDubboService.getClassInfo(vo, response));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("找当前类别所有上级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "类别id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getClassLevelTopAllInfo")
    @HttpApiMethod(apiKey = "admin.goods.getClassLevelTopAllInfo")
    public Result getClassLevelTopAllInfo(MainVo vo, int classId) {
        try {
            return Result.success(goodsDubboService.getClassLevelTopAllInfo(vo, classId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除当前类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "类别id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delClass")
    @HttpApiMethod(apiKey = "admin.goods.delClass")
    public Result delClass(MainVo vo, int classId) {
        try {
            goodsDubboService.delClass(vo, classId);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑类别")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "类别id(修改)", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "className", value = "类别名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "ename", value = "类别英文副标题", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "level", value = "需要添加的类别等级", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "fatherId", value = "上级id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "img", value = "类别图标", dataType = "string", paramType = "form")
    })
    @PostMapping("/addClass")
    @HttpApiMethod(apiKey = "admin.goods.addClass")
    public Result addClass(MainVo vo, Integer classId, String className, String ename, String img, int level, int fatherId) {
        try {
            return Result.success(goodsDubboService.addClass(vo, classId, className, ename, img, level, fatherId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("类别置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "classId", value = "类别id", dataType = "int", paramType = "form")
    })
    @PostMapping("/classSortTop")
    @HttpApiMethod(apiKey = "admin.goods.classSortTop")
    public Result classSortTop(MainVo vo, Integer classId) {
        try {
            return Result.success(goodsDubboService.classSortTop(vo, classId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取国家列表")
    @PostMapping("/getCountry")
    @HttpApiMethod(apiKey = "admin.goods.getCountry")
    public Result getCountry(MainVo vo) {
        try {
            return Result.success(brandService.getCountry(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取品牌信息")
    @PostMapping("/getBrandInfo")
    @HttpApiMethod(apiKey = "admin.goods.getBrandInfo")
    public Result getBrandInfo(BrandClassVo vo, HttpServletResponse response) {
        try {
            return Result.success(brandService.getBrandInfo(vo, response));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("保存/编辑品牌信息")
    @PostMapping("/addBrand")
    @HttpApiMethod(apiKey = "admin.goods.addBrand")
    public Result addBrand(BrandClassVo vo) {
        try {
            return Result.success(brandService.addBrand(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "品牌id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delBrand")
    @HttpApiMethod(apiKey = "admin.goods.delBrand")
    public Result delBrand(MainVo vo, int brandId) {
        try {
            return Result.success(brandService.delBrand(vo, brandId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("品牌置顶")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "brandId", value = "品牌id", dataType = "int", paramType = "form")
    })
    @PostMapping("/brandByTop")
    @HttpApiMethod(apiKey = "admin.goods.brandByTop")
    public Result brandByTop(MainVo vo, int brandId) {
        try {
            return Result.success(brandService.brandByTop(vo, brandId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取运费列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fid", value = "运费id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "mchId", value = "店铺id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "status", value = "使用状态 0=未使用 1=使用中", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "模板名称", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getFreightInfo")
    @HttpApiMethod(apiKey = "admin.goods.getFreightInfo")
    public Result getFreightInfo(MainVo vo, Integer mchId, Integer fid, Integer status, String name) {
        try {
            return Result.success(freightService.getFreightInfo(vo, mchId, fid, status, name));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取地区列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "level", value = "默认全部 2=省 3=市 4=区", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "sid", value = "上级id", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getRegion")
    @HttpApiMethod(apiKey = "admin.goods.getRegion")
    public Result getRegion(MainVo vo, Integer level, Integer sid) {
        try {
            return Result.success(freightService.getRegion(vo, level, sid));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("运费设置默认开关")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "运费id", dataType = "int", paramType = "form")
    })
    @PostMapping("/freightSetDefault")
    @HttpApiMethod(apiKey = "admin.goods.freightSetDefault")
    public Result freightSetDefault(MainVo vo, int id) {
        try {
            freightService.freightSetDefault(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改运费")
    @PostMapping("/addFreight")
    @HttpApiMethod(apiKey = "admin.goods.addFreight")
    public Result addFreight(AddFreihtVo vo) {
        try {
            return Result.success(freightService.addFreight(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除运费")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "freightIds", value = "运费模板id,支持多个", dataType = "int", paramType = "form")
    })
    @PostMapping("/delFreight")
    @HttpApiMethod(apiKey = "admin.goods.delFreight")
    public Result delFreight(MainVo vo, @ParamsMapping("idList") String freightIds) {
        try {
            freightService.delFreight(vo, freightIds);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("库存列表")
    @PostMapping("/getStockInfo")
    @HttpApiMethod(apiKey = "admin.goods.getStockInfo")
    public Result getStockInfo(StockInfoVo vo, HttpServletResponse response) {
        try {
            Map<String, Object> ret = stockService.getStockInfo(vo, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取商品库存详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "attrId", value = "属性id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "pid", value = "商品id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "类型 0.入库 1.出库 2.预警 3.入库+出库", dataType = "int", paramType = "form")
    })
    @PostMapping("/getStockDetailInfo")
    @HttpApiMethod(apiKey = "admin.goods.getStockDetailInfo")
    public Result getStockDetailInfo(StockInfoVo vo, Integer attrId, Integer pid, Integer type, HttpServletResponse response) {
        try {
            Map<String, Object> ret = stockService.getStockDetailInfo(vo, attrId, pid, type, response);
            return ret == null ? Result.exportFile() : Result.success(ret);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加库存")
    @PostMapping("/addStock")
    @HttpApiMethod(apiKey = "admin.goods.addStock")
    public Result getStockDetailInfo(AddStockVo vo) {
        try {
            return Result.success(stockService.addStock(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取淘宝抓取任务列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "状态：0.待执行 1.执行中 2.执行完成", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "taskName", value = "任务名称", dataType = "string", paramType = "form")
    })
    @PostMapping("/getTaoBaoWorkeList")
    @HttpApiMethod(apiKey = "admin.goods.getTaoBaoWorkeList")
    public Result getTaoBaoWorkeList(MainVo vo, Integer status, String taskName) {
        try {
            return Result.success(taoBaoService.getTaoBaoList(vo, status, taskName, null));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取淘宝抓取任务明细列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wid", value = "任务id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/getTaoBaoWorkeListDetail")
    @HttpApiMethod(apiKey = "admin.goods.getTaoBaoWorkeListDetail")
    public Result getTaoBaoWorkeListDetail(MainVo vo, int wid) {
        try {
            return Result.success(taoBaoService.getTaoBaoList(vo, null, null, wid));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("批量删除淘宝抓取任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wids", value = "任务id", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/delTaoBaoTask")
    @HttpApiMethod(apiKey = "admin.goods.delTaoBaoTask")
    public Result delTaoBaoTask(MainVo vo, String wids) {
        try {
            return Result.success(taoBaoService.delTaoBaoTask(vo, wids));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("批量执行任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wids", value = "任务id", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/executeTaoBaoById")
    @HttpApiMethod(apiKey = "admin.goods.executeTaoBaoById")
    public Result executeTaoBaoById(MainVo vo, String wids) {
        try {
            return Result.success(taoBaoService.executeTaoBaoById(vo, wids));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("重新执行任务")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "taskId", value = "任务id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/restoreExecuteTaoBaoById")
    @HttpApiMethod(apiKey = "admin.goods.restoreExecuteTaoBaoById")
    public Result restoreExecuteTaoBaoById(MainVo vo, int taskId) {
        try {
            taoBaoService.restoreExecuteTaoBaoById(vo, taskId);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑任务")
    @PostMapping("/addTaoBaoTask")
    @HttpApiMethod(apiKey = "admin.goods.addTaoBaoTask")
    public Result addTaoBaoTask(AddTaoBaoVo vo) {
        try {
            return Result.success(taoBaoService.addTaoBaoTask(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @Autowired
    private PubliceService publiceService;

    @ApiOperation("商品支持的活动类型")
    @PostMapping("/getGoodsActive")
    @HttpApiMethod(apiKey = "admin.goods.getGoodsActive")
    public Result getGoodsActive(MainVo vo) {
        try {
            return Result.success(publiceService.getGoodsActive(vo.getStoreId()));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


}
