package com.laiketui.modules.backend.controller;

import com.laiketui.api.modules.backend.FileService;
import com.laiketui.domain.api.Result;
import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.files.FilesVo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.annotation.HttpApiMethod;
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

import javax.servlet.http.HttpServletRequest;


/**
 * 图片上传列表
 *
 * @author Trick
 * @date 2021/7/7 18:16
 */
@Api(tags = "文件上传")
@RestController
@RequestMapping("/resources/file")
public class FileController {
    @Autowired
    private FileService fileService;

    @ApiOperation("文件列表")
    @PostMapping("/index")
    @HttpApiMethod(apiKey = "resources.file.index")
    public Result index(FilesVo vo, HttpServletRequest request) {
        try {
            vo.setRequest(request);
            return Result.success(fileService.index(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("获取文件分组")
    @PostMapping("/groupList")
    @HttpApiMethod(apiKey = "resources.file.groupList")
    public Result groupList(MainVo vo) {
        try {
            return Result.success(fileService.groupList(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("创建/修改自定义目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "catalogueName", value = "目录名称", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "id", value = "分组id", dataType = "int", paramType = "form")
    })
    @PostMapping("/createCatalogue")
    @HttpApiMethod(apiKey = "resources.file.createCatalogue")
    public Result createCatalogue(MainVo vo, String catalogueName, Integer id) {
        try {
            fileService.createCatalogue(vo, catalogueName, id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

    @ApiOperation("文件上传")
    @PostMapping("/uploadFiles")
    @HttpApiMethod(apiKey = "resources.file.uploadFiles")
    public Result uploadFiles(UploadFileVo vo) {
        try {
            return Result.success(fileService.uploadImage(vo));
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("删除目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "目录id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delCatalogue")
    @HttpApiMethod(apiKey = "resources.file.delCatalogue")
    public Result delCatalogue(MainVo vo, int id) {
        try {
            fileService.delCatalogue(vo,id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }


    @ApiOperation("删除文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片id", dataType = "int", paramType = "form")
    })
    @PostMapping("/delFile")
    @HttpApiMethod(apiKey = "resources.file.delFile")
    public Result delFile(MainVo vo, int id) {
        try {
            fileService.delFile(vo,id);
            return Result.success(GloabConst.ManaValue.MANA_VALUE_SUCCESS);
        } catch (LaiKeApiException e) {
            return Result.error(e.getCode(), e.getMessage());
        }
    }

}
