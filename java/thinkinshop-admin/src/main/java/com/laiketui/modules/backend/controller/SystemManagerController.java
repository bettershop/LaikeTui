package com.laiketui.modules.backend.controller;

import com.laiketui.api.common.cascade.PublicCascadeService;
import com.laiketui.api.modules.backend.systems.AddressService;
import com.laiketui.api.modules.backend.systems.SearchService;
import com.laiketui.api.modules.backend.systems.SmsService;
import com.laiketui.api.modules.backend.systems.SystemService;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.systems.*;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 系统管理
 *
 * @author Trick
 * @date 2021/1/15 9:27
 */
@Api(tags = "后台-系统管理")
@RestController
@RequestMapping("/admin/system")
public class SystemManagerController {

    @Autowired
    AddressService addressService;

    @ApiOperation("获取地址列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "name", value = "联系人/手机号", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "类型（1发货地址 2售后地址） 默认2", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getAddressInfo")
    @HttpApiMethod(apiKey = "admin.system.getAddressInfo")
    public Result getAddressInfo(MainVo vo, Integer id, String name, Integer type) {
        try {
            return Result.success(addressService.getAddressInfo(vo, id, name, type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("设置默认地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "int", paramType = "form")
    })
    @PostMapping("/setDefaultAddress")
    @HttpApiMethod(apiKey = "admin.system.setDefaultAddress")
    public Result setDefaultAddress(MainVo vo, int id) {
        try {
            addressService.setDefaultAddress(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑地址")
    @PostMapping("/addAddressInfo")
    @HttpApiMethod(apiKey = "admin.system.addAddressInfo")
    public Result addAddressInfo(AddressVo vo) {
        try {
            return Result.success(addressService.addAddressInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除地址")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "类型（1发货地址 2售后地址） 默认2", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delAddress")
    @HttpApiMethod(apiKey = "admin.system.delAddress")
    public Result delAddress(MainVo vo, int id, int type) {
        try {
            return Result.success(addressService.delAddress(vo, id, type));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private SmsService smsService;

    @Autowired
    private PublicCascadeService publicCascadeService;

    @ApiOperation("获取短信列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "列表id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getSmsInfo")
    @HttpApiMethod(apiKey = "admin.system.getSmsInfo")
    public Result getSmsInfo(MainVo vo, Integer id) {
        try {
            return Result.success(smsService.getSmsInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("级联获取短信类型/类别/短信模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "superName", value = "上级名称", dataType = "string", paramType = "form")
    })
    @PostMapping("/getSmsTypeList")
    @HttpApiMethod(apiKey = "admin.system.getSmsTypeList")
    public Result getSmsTypeList(MainVo vo, String superName) {
        try {
            return Result.success(publicCascadeService.getSmsTypeList(vo, superName));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("级联获取短信模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "0:验证码 1:短信通知", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "id", value = "模板id", dataType = "int", paramType = "form"),
    })
    @PostMapping("/getSmsTemplateList")
    @HttpApiMethod(apiKey = "admin.system.getSmsTemplateList")
    public Result getSmsTemplateList(Integer type, Integer id) {
        try {
            return Result.success(publicCascadeService.getSmsTemplateList(type, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑短信列表")
    @PostMapping("/addMessageList")
    @HttpApiMethod(apiKey = "admin.system.addMessageList")
    public Result addMessageList(AddMessageListVo vo) {
        try {
            return Result.success(smsService.addMessageList(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除短信自定义配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "短信列表id", required = true, dataType = "int", paramType = "form")
    })
    @PostMapping("/delMessageList")
    @HttpApiMethod(apiKey = "admin.system.delMessageList")
    public Result delMessageList(MainVo vo, int id) {
        try {
            return Result.success(smsService.delMessageList(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取模板列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "短信模板id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getSmsTemplateInfo")
    @HttpApiMethod(apiKey = "admin.system.getSmsTemplateInfo")
    public Result getSmsTemplateInfo(MainVo vo, Integer id) {
        try {
            return Result.success(smsService.getSmsTemplateInfo(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "短信模板id", dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "phone", value = "接收手机号", dataType = "string", paramType = "form")
    })
    @PostMapping("/addMessageTemplate")
    @HttpApiMethod(apiKey = "admin.system.addMessage")
    public Result addMessage(AddMessageVo vo, String phone) {
        try {
            smsService.addMessage(vo, phone);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("删除模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "短信模板id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delMessage")
    @HttpApiMethod(apiKey = "admin.system.delMessage")
    public Result delMessage(AddMessageVo vo, int id) {
        try {
            smsService.delMessage(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取模板核心配置信息")
    @PostMapping("/getTemplateConfigInfo")
    @HttpApiMethod(apiKey = "admin.system.getTemplateConfigInfo")
    public Result getTemplateConfigInfo(MainVo vo) {
        try {
            return Result.success(smsService.getTemplateConfigInfo(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑核心设置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "accessKeyId", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "secret", value = "accessKeySecret", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "signName", value = "签名", required = true, dataType = "string", paramType = "form"),
    })
    @PostMapping("/addTemplateConfig")
    @HttpApiMethod(apiKey = "admin.system.addTemplateConfig")
    public Result addTemplateConfig(MainVo vo, String key, String secret, String signName) {
        try {
            return Result.success(smsService.addTemplateConfig(vo, key, secret, signName));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private SystemService systemService;

    @ApiOperation("获取系统基础配置")
    @PostMapping("/getSystemIndex")
    @HttpApiMethod(apiKey = "admin.system.getSystemIndex")
    public Result getSystemIndex(MainVo vo) {
        try {
            return Result.success(systemService.getSystemIndex(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改系统【基础配置】信息")
    @PostMapping("/addSystemConfig")
    @HttpApiMethod(apiKey = "admin.system.addSystemConfig")
    public Result addSystemConfig(AddSystemVo vo) {
        try {
            return Result.success(systemService.addSystemConfig(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取【系统配置】信息")
    @PostMapping("/getSetSystem")
    @HttpApiMethod(apiKey = "admin.system.getSetSystem")
    public Result getSetSystem(MainVo vo) {
        try {
            return Result.success(systemService.getSetSystem(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/修改【系统配置】信息")
    @PostMapping("/setSystem")
    @HttpApiMethod(apiKey = "admin.system.setSystem")
    public Result setSystem(SetSystemVo vo) {
        try {
            systemService.setSystem(vo);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("获取协议列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "协议id", dataType = "int", paramType = "form")
    })
    @PostMapping("/getAgreementIndex")
    @HttpApiMethod(apiKey = "admin.system.getAgreementIndex")
    public Result getAgreementIndex(MainVo vo, Integer id) {
        try {
            return Result.success(systemService.getAgreementIndex(vo, id));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑协议")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "title", value = "标题", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "type", value = "类型 0:注册 1:店铺 2:隐私", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "content", value = "协议id", required = true, dataType = "string", paramType = "form"),
    })
    @PostMapping("/addAgreement")
    @HttpApiMethod(apiKey = "admin.system.addAgreement")
    public Result addAgreement(MainVo vo, Integer id, String title, int type, String content) {
        try {
            systemService.addAgreement(vo, id, title, type, content);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("删除协议")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "string", paramType = "form"),
    })
    @PostMapping("/delAgreement")
    @HttpApiMethod(apiKey = "admin.system.delAgreement")
    public Result delAgreement(MainVo vo, int id) {
        try {
            systemService.delAgreement(vo, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("常见问题修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "returnProblem", value = "售后问题", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "payProblem", value = "支付问题", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/updateCommonProblem")
    @HttpApiMethod(apiKey = "admin.system.updateCommonProblem")
    public Result updateCommonProblem(MainVo vo, String returnProblem, String payProblem) {
        try {
            return Result.success(systemService.updateCommonProblem(vo, returnProblem, payProblem));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("新手指南修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "shoppingProcess", value = "购物流程", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "payType", value = "支付方式", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/updateBeginnerGuide")
    @HttpApiMethod(apiKey = "admin.system.updateBeginnerGuide")
    public Result updateBeginnerGuide(MainVo vo, String shoppingProcess, String payType) {
        try {
            return Result.success(systemService.updateBeginnerGuide(vo, shoppingProcess, payType));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("售后服务修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "refundPolicy", value = "退货政策", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "cancelOrderno", value = "取消订单", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "refundMoney", value = "退款流程", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "refundExplain", value = "退款说明", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/updateRefundService")
    @HttpApiMethod(apiKey = "admin.system.updateRefundService")
    public Result updateRefundService(MainVo vo, String refundPolicy, String cancelOrderno, String refundMoney, String refundExplain) {
        try {
            return Result.success(systemService.updateRefundService(vo, refundPolicy, cancelOrderno, refundMoney, refundExplain));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("关于我们修改")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "auboutMe", value = "关于我们", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/updateAboutMe")
    @HttpApiMethod(apiKey = "admin.system.updateAboutMe")
    public Result updateAboutMe(MainVo vo, HttpServletRequest request) {
        try {
            System.out.println(request.getParameter("auboutMe"));
            return Result.success(systemService.updateAboutMe(vo, request.getParameter("auboutMe")));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("上传图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "图片集合", required = true, dataType = "file", paramType = "form")
    })
    @PostMapping("/uploadImages")
    @HttpApiMethod(apiKey = "admin.system.uploadImages")
    public Result uploadImages(MainVo vo, List<MultipartFile> files) {
        try {
            return Result.success(systemService.uploadImages(vo, files));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("微信小程序配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "appid", value = "appid", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "appsecret", value = "appsecret", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/updateWeiXinApi")
    public Result updateWeiXinApi(MainVo vo, String appid, String appsecret) {
        try {
            return Result.success(systemService.updateWeiXinApi(vo, appid, appsecret));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @Autowired
    private SearchService searchService;

    @ApiOperation("搜搜配置查询")
    @PostMapping("/getSearchConfigIndex")
    @HttpApiMethod(apiKey = "admin.system.getSearchConfigIndex")
    public Result getSearchConfigIndex(MainVo vo) {
        try {
            return Result.success(searchService.getSearchConfigIndex(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("添加/编辑 搜搜配置")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "isOpen", value = "是否开启", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "limitNum", value = "关键词上限", required = true, dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "keyword", value = "关键词,多个用','分隔", required = true, dataType = "string", paramType = "form")
    })
    @PostMapping("/addSearchConfig")
    @HttpApiMethod(apiKey = "admin.system.addSearchConfig")
    public Result addSearchConfig(MainVo vo, int isOpen, int limitNum, String keyword) {
        try {
            return Result.success(searchService.addSearchConfig(vo, isOpen, limitNum, keyword));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


}
