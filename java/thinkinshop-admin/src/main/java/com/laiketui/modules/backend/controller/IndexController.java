package com.laiketui.modules.backend.controller;

import com.google.common.collect.Maps;
import com.laiketui.api.modules.backend.IndexDubboService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.admin.DefaultViewVo;
import com.laiketui.root.annotation.HttpApiMethod;
import com.laiketui.domain.api.Result;
import com.laiketui.root.exception.LaiKeApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 首页
 *
 * @author Trick
 * @date 2020/12/28 14:04
 */
@Api(tags = "首页")
@RestController("backIndexController")
@RequestMapping("/admin/goods")
public class IndexController {

    @Autowired
    private IndexDubboService indexDubboService;

    /**
     * 获取底部信息
     * @return
     */
    @PostMapping( value = "/getFooter", produces = { "application/json;charset=UTF-8" } )
    @HttpApiMethod(apiKey = "SaaS.b.c")
    public Result c() {
        Map retMap = Maps.newHashMap();
        retMap.put("a","联系地址：长沙市岳麓区绿地中央广场5栋3408 0731-86453408");
        retMap.put("b","Copyright © 2021 湖南壹拾捌号网络技术有限公司[官方网站] ");
        retMap.put("c","湘ICP备17020355号");
        retMap.put("d","未授权版来客推商城");
        StringBuilder copyRightHtml = new StringBuilder();
        copyRightHtml.append("未经<a href='https://www.laiketui.com/' target='_blank' >来客推电商</a>系统授权的用户，仅供从事学习研究之用，不具备商业运作的合法性，如果未获取授权而从事商业行为，<a href='https://www.laiketui.com/' target='_blank' >来客推</a>保留对其使用系统停止升级、关闭、甚至对其商业运作行为媒体曝光和追究法律责任的起诉权利，授权请前往<a href='https://www.laiketui.com/' target='_blank' >www.laiketui.com</a>咨询。");
        retMap.put("e",copyRightHtml);
        return Result.success(retMap);
    }

    @ApiOperation("商城首页")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mchId", value = "店铺id", dataType = "int", paramType = "form")
    })
    @PostMapping("/home")
    @HttpApiMethod(apiKey = "admin.home.index")
    public Result home(MainVo vo, Integer mchId) {
        try {
            return Result.success(indexDubboService.home(vo, mchId));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("后台商品列表")
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "admin.goods.index")
    public Result index(DefaultViewVo vo, HttpServletResponse response) {
        try {
            vo.setMchName(vo.getProductTitle());
            Map<String, Object>  ret = indexDubboService.index(vo, response);
            return ret == null ? Result.exportFile() : Result.success(ret) ;
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("商品状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "商品id集", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/goodsStatus")
    @HttpApiMethod(apiKey = "admin.goods.goodsStatus")
    public Result goodsStatus(MainVo vo, String ids) {
        try {
            return Result.success(indexDubboService.goodsStatus(vo, ids));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("是否显示下架商品开关")
    @PostMapping("/isOpen")
    @HttpApiMethod(apiKey = "admin.goods.isOpen")
    public Result isOpen(MainVo vo) {
        try {
            return Result.success(indexDubboService.isopen(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
