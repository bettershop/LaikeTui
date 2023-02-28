package com.laiketui.api.modules.backend;


import com.laiketui.domain.vo.MainVo;
import com.laiketui.domain.vo.files.FilesVo;
import com.laiketui.domain.vo.files.UploadFileVo;
import com.laiketui.root.exception.LaiKeApiException;

import java.util.Map;

/**
 * 文件资源接口
 *
 * @author Trick
 * @date 2021/7/7 18:17
 */
public interface FileService {

    /**
     * 文件列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/7 18:18
     */
    Map<String, Object> index(FilesVo vo) throws LaiKeApiException;

    /**
     * 图片上传分类列表
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 9:36
     */
    Map<String, Object> groupList(MainVo vo) throws LaiKeApiException;

    /**
     * 图片上传
     *
     * @param vo -
     * @return Map
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 11:36
     */
    Map<String, Object> uploadImage(UploadFileVo vo) throws LaiKeApiException;

    /**
     * 创建目录
     *
     * @param vo            -
     * @param catalogueName -
     * @param id            -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 15:30
     */
    void createCatalogue(MainVo vo, String catalogueName, Integer id) throws LaiKeApiException;

    /**
     * 删除目录
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 16:59
     */
    void delCatalogue(MainVo vo, int id) throws LaiKeApiException;

    /**
     * 删除文件
     *
     * @param vo -
     * @param id -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 17:00
     */
    void delFile(MainVo vo, int id) throws LaiKeApiException;
}
