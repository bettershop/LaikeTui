package com.laiketui.root.utils.tool;

import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.laiketui.domain.log.FilesRecordModel;
import com.laiketui.domain.upload.UploadConfigModel;
import com.laiketui.domain.upload.UploadImagModel;
import com.laiketui.domain.vo.files.DelFilesVo;
import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.consts.GloabConst;
import com.laiketui.root.exception.LaiKeApiException;
import com.laiketui.root.utils.common.BuilderIDTool;
import com.laiketui.root.utils.common.LKTSnowflakeIdWorker;
import com.laiketui.root.utils.common.SplitUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.FileNameMap;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 图片上传工具类
 *
 * @author Trick
 * @date 2020/9/25 16:43
 */
public class ImgUploadUtils {

    private static final Logger logger = LoggerFactory.getLogger(ImgUploadUtils.class);

    /**
     * oos 公共图片 - 店铺分享主图
     */
    public static final String OOS_DEFAULT_IMAGE_NAME_MCH = "https://laikeds.oss-cn-shenzhen.aliyuncs.com/public/shop_bg.png";
    /**
     * oos 公共图片 - 默认底图logo
     */
    public static final String OOS_DEFAULT_IMAGE_NAME_LOGO = "https://laikeds.oss-cn-shenzhen.aliyuncs.com/public/logo.jpg";

    static {
        try {
            //获取本地默认上传路径
            DEFAULT_UPLOAD_PATH = ResourceUtils.getURL("classpath:").getPath() + "static" + File.separator;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            logger.error("本地默认上传路径获取失败!{}", e.getMessage());
        }
    }

    /**
     * 本地默认上传地址
     */
    public static String DEFAULT_UPLOAD_PATH;

    /**
     * 获取文件完整路径
     *
     * @param filesRecordModel   -
     * @param uploadConfigModels -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/10/10 13:26
     */
    public static String getImgPath(FilesRecordModel filesRecordModel, List<UploadConfigModel> uploadConfigModels) throws LaiKeApiException {
        StringBuilder fileUrl = new StringBuilder();
        String uploadDomain = "";
        switch (filesRecordModel.getUpload_mode()) {
            case GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST + "":
                //本地上传
                for (UploadConfigModel uploadConfig : uploadConfigModels) {
                    //根据配置拼接url
                    switch (uploadConfig.getAttr()) {
                        case GloabConst.UploadConfigConst.UPLOADIMG_DOMAIN:
                            //图片上传域名
                            uploadDomain = uploadConfig.getAttrvalue();
                            break;
                        case GloabConst.UploadConfigConst.UPLOADIMG:
                            //图片上传位置
                            fileUrl.append(uploadConfig.getAttrvalue());
                            break;
                        default:
                            break;
                    }
                    //文件夹路径
                    fileUrl.append(GloabConst.UploadConfigConst.IMG_PATH);
                    fileUrl.append(filesRecordModel.getStore_id());
                    fileUrl.insert(0, uploadDomain);
                    fileUrl.append(filesRecordModel.getImage_name());
                }
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_OSS + "":
                //域名
                StringBuilder domainUrl = new StringBuilder();
                String endpoint = "";
                String diyEndpoint = "";
                //2021-09-29 16:57:14 oss图片增加日期分组(按天)
                String fileDateDay = "";
                Date sysDate = DateUtil.dateFormateToDate("2021-09-29 00:00:00", GloabConst.TimePattern.YMDHMS);
                if (DateUtil.dateCompare(filesRecordModel.getAdd_time(), sysDate)) {
                    //上传时间,图片上传按照天分组了
                    fileDateDay = DateUtil.dateFormate(filesRecordModel.getAdd_time(), GloabConst.TimePattern.YMD1);
                }
                //是否开启自定义域名
                boolean isOpenDiyUrl = false;
                for (UploadConfigModel uploadConfig : uploadConfigModels) {
                    //根据配置拼接url
                    switch (uploadConfig.getAttr()) {
                        case GloabConst.UploadConfigConst.BUCKET:
                            //oos仓库
                            uploadDomain = uploadConfig.getAttrvalue();
                            break;
                        case GloabConst.UploadConfigConst.ENDPOINT:
                            //域名
                            endpoint = uploadConfig.getAttrvalue();
                            break;
                        case GloabConst.UploadConfigConst.ISOPENZDY:
                            //自定义域名开关
                            if ("1".equals(uploadConfig.getAttrvalue())) {
                                isOpenDiyUrl = true;
                            }
                            break;
                        case GloabConst.UploadConfigConst.MYENDPOINT:
                            //自定义域名
                            diyEndpoint = uploadConfig.getAttrvalue();
                            break;
                        default:
                            break;
                    }
                }
                if (isOpenDiyUrl) {
                    //自定义域名
                    domainUrl.append(diyEndpoint);
                } else {
                    domainUrl
                            //仓库
                            .append(uploadDomain).append(SplitUtils.XSD)
                            //域名
                            .append(endpoint);
                }
                if (StringUtils.isNotEmpty(fileDateDay)) {
                    //新增日期文件夹
                    filesRecordModel.setImage_name(fileDateDay + SplitUtils.FXG + filesRecordModel.getImage_name());
                }
                fileUrl
                        //请求协议
                        .append(GloabConst.UploadConfigConst.OOS_HTTP_HEADER)
                        //仓库
                        .append(domainUrl)
                        //店铺id
                        .append(SplitUtils.FXG).append(filesRecordModel.getStore_id())
                        //来源
                        .append(SplitUtils.FXG).append(filesRecordModel.getStore_type())
                        //图片名称
                        .append(SplitUtils.FXG).append(filesRecordModel.getImage_name())
                ;
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_TXY + "":
                //腾讯云上传
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "暂不支持腾讯云", "uploadLocalhost");
            case GloabConst.UploadConfigConst.IMG_UPLOAD_QNY + "":
                //七牛云上传
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "暂不支持七牛云", "uploadLocalhost");
            default:
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "uploadLocalhost");
        }

        return fileUrl.toString();
    }

    /**
     * 图片上传
     *
     * @param imageModel - 图片配置参数
     * @return Map
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/28 14:26
     */
    public List<String> imgUpload(UploadImagModel imageModel, int storeId, int storeType) throws LaiKeApiException {
        //图片路径集合
        List<String> urls;
        imageModel = getConfigValue(imageModel);
        switch (imageModel.getUploadType()) {
            case GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST:
                urls = uploadLocalhost(imageModel);
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_OSS:
                urls = uploadOss(imageModel, storeId + SplitUtils.FXG + storeType);
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_TXY:
                urls = uploadTxy(imageModel);
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_QNY:
                urls = uploadQny(imageModel);
                break;
            default:
                logger.error("上传类型不存在{}", imageModel.getUploadType());
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "imgUpload");
        }

        return urls;
    }

    /**
     * 批量下载图片
     *
     * @param imageModel -
     * @param imgMaps    -
     * @param storeId    -
     * @param storeType  -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/22 10:33
     */
    public void downImages(UploadImagModel imageModel, List<Map<String, Object>> imgMaps, HttpServletResponse response, int storeId, int storeType) throws LaiKeApiException {
        imageModel = getConfigValue(imageModel);
        switch (imageModel.getUploadType()) {
            case GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST:
            case GloabConst.UploadConfigConst.IMG_UPLOAD_TXY:
            case GloabConst.UploadConfigConst.IMG_UPLOAD_QNY:
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_OSS:
                downImagesOss(imageModel, imgMaps, response);
                break;
            default:
                logger.error("上传类型不存在{}", imageModel.getUploadType());
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "downImages");
        }
    }


    /**
     * 删除图片
     *
     * @param vo -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 17:54
     */
    public void imgDel(DelFilesVo vo) throws LaiKeApiException {
        switch (vo.getUploadType()) {
            case GloabConst.UploadConfigConst.IMG_UPLOAD_LOCALHOST:
                imgDelLocalhost(vo.getCatalogue(), vo.getFileName());
                break;
            case GloabConst.UploadConfigConst.IMG_UPLOAD_OSS:
                imgDelOss(getConfigValue(vo.getUploadImagModel()), vo.getStoreId() + SplitUtils.FXG + vo.getStoreType() + vo.getFileName());
                break;
            default:
                logger.error("上传类型不存在{}", vo.getUploadType());
                throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "imgDel");
        }
    }

    /**
     * 批量下载oss
     *
     * @param imageModel -
     * @param imgMaps    -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/22 10:39
     */
    private void downImagesOss(UploadImagModel imageModel, List<Map<String, Object>> imgMaps, HttpServletResponse response) throws LaiKeApiException {
        try {
            if (imgMaps != null && imgMaps.size() > 0) {
                //连接oss
                ClientConfiguration conf = new ClientConfiguration();
                conf.setMaxConnections(200);
                conf.setSocketTimeout(10000);
                conf.setConnectionTimeout(50000);
                conf.setRequestTimeoutEnabled(true);
                conf.setRequestTimeout(100000);
                conf.setMaxErrorRetry(15);
                // 需要开启，默认不开启
                // 设置请求超时，单位毫秒，默认值300秒
                conf.setRequestTimeout(3000);
                OSSClient ossClient = new OSSClient(imageModel.getEndpoint(), imageModel.getAccessKeyId(), imageModel.getAccessKeySecret());

                //创建临时文件
//                File zipFile = File.createTempFile(BuilderIDTool.getGuid(), ".zip", new File(imageModel.getUploadPath()));
//                FileOutputStream f = new FileOutputStream(zipFile);
                // 对于每一个要被存放到压缩包的文件，都必须调用ZipOutputStream对象的putNextEntry()方法，确保压缩包里面文件不同名
                CheckedOutputStream cos = new CheckedOutputStream(response.getOutputStream(), new Adler32());
                List<String> imgNames = new ArrayList<>();
                //提示信息
                List<String> info = new ArrayList<>();
                try (ZipOutputStream zip = new ZipOutputStream(cos)) {
                    for (Map<String, Object> map : imgMaps) {
                        String imgUrl = MapUtils.getString(map, "image_name");
                        String imgName = ImgUploadUtils.getUrlImgByName(imgUrl, true);
                        int storeId = MapUtils.getIntValue(map, "store_id");
                        String storeType = MapUtils.getString(map, "store_type");
                        //获取图片
                        try (OSSObject ossObject = ossClient.getObject(imageModel.getBucket(), String.format("%s/%s/%s", storeId, storeType, imgName))) {
                            //获取图片流
                            try (InputStream inputStream = ossObject.getObjectContent()) {
                                //处理图片同名
                                if (imgNames.contains(imgName)) {
                                    imgName += BuilderIDTool.getNext(BuilderIDTool.Type.NUMBER, 3);
                                }
                                imgNames.add(imgName);
                                zip.putNextEntry(new ZipEntry(imgName));
                                byte[] buff = new byte[4*1024];
                                int bytesRead;
                                // 向压缩文件中输出数据
                                logger.info("当前下载=>"+imgName);
                                while ((bytesRead = inputStream.read(buff)) != -1) {
                                    zip.write(buff,0,bytesRead);
                                }
                                inputStream.close();
                                // 当前文件写完，定位为写入下一条项目
                                zip.closeEntry();
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error("图片下载失败 ", e);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("图片不存在 {}", imgUrl);
                            //创建一个错误日志文件,如果失败了则生成一个文件到压缩包里 (xx图下载失败)
                            info.add(String.format("【%s】图片下载失败", imgUrl));
                        }
                    }
                    //写入日志
                    if (info.size() > 0) {
                        String infoFilePath = builderFile(StringUtils.stringImplode(info, "\n"), ".txt");
                        if (infoFilePath != null) {
                            File tempFile = File.createTempFile(BuilderIDTool.getGuid(), ".txt", new File(infoFilePath));
                            try (FileInputStream fis = new FileInputStream(tempFile)) {
                                try (BufferedInputStream is = new BufferedInputStream(fis)) {
                                    zip.putNextEntry(new ZipEntry("下载失败列表.txt"));
                                    int bytesRead;
                                    while ((bytesRead = is.read()) != -1) {
                                        zip.write(bytesRead);
                                    }
                                    zip.closeEntry();
                                    zip.finish();
                                } catch (Exception ignored) {
                                    //Who cares?
                                }
                            } catch (Exception ignored) {
                                //Who cares?
                            } finally {
                                logger.debug("临时文件删除状态 :" + tempFile.delete());
                            }
                        }
                    }
                    ossClient.shutdown();
                } catch (Exception e) {
                    //Who cares?
                    logger.error("图片下载失败 ", e);
                } finally {
                    logger.debug("文件下载完成");
                }
            }
        } catch (Exception e) {
            logger.error("文件下载 - 阿里云oss下载 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "文件不能为空", "downImagesOss");
        }
    }

    /**
     * 创建一个文件
     *
     * @param context -  文件名
     * @param suffix  - 后缀名
     * @return String - 反回一个绝对路径
     * @throws IOException-
     * @author Trick
     * @date 2021/7/22 17:40
     */
    public static String builderFile(String context, String suffix) throws IOException {
        File file = null;
        FileInputStream fis = null;
        BufferedInputStream buff = null;
        BufferedOutputStream out = null;
        try {
            //创建一个文件
            file = File.createTempFile(BuilderIDTool.getGuid(), suffix, new File("E:\\test"));
            fis = new FileInputStream(file);
            buff = new BufferedInputStream(fis);
            out = new BufferedOutputStream(new FileOutputStream(file));
            out.write(context.getBytes());

            return file.getCanonicalPath();
        } catch (Exception e) {
            logger.error("文件创建失败 异常:", e);
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (buff != null) {
                buff.close();
            }
            if (out != null) {
                out.close();
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        try {
            OSSClient ossClient = new OSSClient("oss-cn-shenzhen.aliyuncs.com", "LTAI4Fm8MFnadgaCdi6GGmkN", "NhBAJuGtx218pvTE4IBTZcvRzrFrH4");
//            ossClient.deleteObject("laikeds", "1/1/1413307862188302336.jpg");

            List<String> list = Collections.singletonList("https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/1/1603261562696.jpeg");

//            File zipFile = File.createTempFile("test", ".zip");
            File zipFile = new File("E:\\test\\xxx.zip");
            FileOutputStream f = new FileOutputStream(zipFile);
            CheckedOutputStream csum = new CheckedOutputStream(f, new Adler32());

            ZipOutputStream zos = new ZipOutputStream(csum);
            for (String path : list) {
                String imgName = ImgUploadUtils.getUrlImgByName(path, true);
                OSSObject ossObject = ossClient.getObject("laikeds", "1/0/1603261562696.jpeg");
                InputStream inputStream = ossObject.getObjectContent();

                zos.putNextEntry(new ZipEntry(imgName));
                int bytesRead = 0;
                // 向压缩文件中输出数据
                while ((bytesRead = inputStream.read()) != -1) {
                    zos.write(bytesRead);
                }
                inputStream.close();
                // 当前文件写完，定位为写入下一条项目
                zos.closeEntry();
            }
            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /**
     * 图片删除 - 阿里云oss删除
     *
     * @param imageModel -
     * @param imgName    -
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 17:56
     */
    private void imgDelOss(UploadImagModel imageModel, String imgName) throws LaiKeApiException {
        try {
            OSSClient ossClient = new OSSClient(imageModel.getEndpoint(), imageModel.getAccessKeyId(), imageModel.getAccessKeySecret());
            ossClient.deleteObject(imageModel.getBucket(), imgName);
        } catch (Exception e) {
            logger.error("图片删除 - 阿里云oss删除 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "文件不能为空", "imgDelOss");
        }
    }

    /**
     * 图片删除 - 本地删除
     *
     * @param catalogue - 目录*
     * @param fileName  - 文件名称
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2021/7/8 17:56
     */
    private void imgDelLocalhost(String catalogue, String fileName) throws LaiKeApiException {
        try {
            //服务器资源路径
            StringBuilder realPath = new StringBuilder(ImgUploadUtils.DEFAULT_UPLOAD_PATH);
            //是否有目录
            if ( StringUtils.isNotEmpty(catalogue)) {
                realPath.append(File.separator).append(PinyinUtils.getPinYin(catalogue));
            }
            realPath.append(File.separator).append(fileName);
            //判断文件是否存在
            File diyFile = new File(realPath.toString());
            //删除本地图片
            if (diyFile.exists()) {
                boolean flag = diyFile.delete();
                logger.debug("路径:{} 文件存在,删除状态:{}", diyFile.getPath(), flag);
                if (!flag) {
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "文件删除失败");
                }
            } else {
                logger.debug("路径:{} 文件不存在,删除失败", diyFile.getPath());
                throw new LaiKeApiException(ErrorCode.BizErrorCode.DATA_NOT_EXIST, "文件不存在");
            }
        } catch (Exception e) {
            logger.error("图片删除 - 本地删除 异常", e);
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "文件不能为空", "imgDelLocalhost");
        }
    }

    /**
     * 图片上传 - 阿里oss上传
     *
     * @param imageModel - 图片上传配置,imgName - 图片名称,file -文件流
     * @return List<String>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 16:42
     */
    private List<String> uploadOss(UploadImagModel imageModel, String path) throws LaiKeApiException {
        List<String> urls = new ArrayList<>();
        //转过来的文件不为空
        if (imageModel.getMultipartFiles() != null && imageModel.getMultipartFiles().size() > 0) {
            for (MultipartFile file : imageModel.getMultipartFiles()) {
                StringBuilder pathBuilder = new StringBuilder(path);
                String url = "";
                //取到文件名
                String fileName = file.getOriginalFilename();
                //校验图片
                if (DataCheckTool.checkUploadImgageFormate(fileName)) {
                    //处理图片重名
                    LKTSnowflakeIdWorker lktSnowflakeIdWorker = new LKTSnowflakeIdWorker();
                    String imgName = lktSnowflakeIdWorker.nextId() + getUrlImgBySuffix(fileName);
                    //增加时间文件夹
                    pathBuilder.append(SplitUtils.FXG).append(DateUtil.dateFormate(new Date(), GloabConst.TimePattern.YMD1));
                    pathBuilder.append(SplitUtils.FXG).append(imgName);

                    OSSClient ossClient = new OSSClient(imageModel.getEndpoint(), imageModel.getAccessKeyId(), imageModel.getAccessKeySecret());
                    PutObjectResult resultOss;
                    try {
                        //url = 店铺id/来源/时间(yyyymmdd)/图片名称
                        resultOss = ossClient.putObject(imageModel.getBucket(), pathBuilder.toString(), file.getInputStream());
                    } catch (IOException e) {
                        logger.error(fileName + "图片上传失败 异常", e);
                        throw new LaiKeApiException(ErrorCode.SysErrorCode.UPLOAD_FAIL, "图片上传失败", "uploadLocalhost");
                    }
                    System.out.println(JSON.toJSONString(resultOss));
                    //获取url链接
                    url = this.getUrl(pathBuilder.toString(), ossClient, imageModel.getBucket());
                    ossClient.shutdown();
                }

                urls.add(url);
            }
        } else {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "文件不能为空", "uploadOss");
        }
        return urls;
    }


    /**
     * 图片上传 - 本地上传
     *
     * @param imageModel - 图片上传配置,imgName - 图片名称,file -文件流
     * @return List<String>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 16:42
     */
    private List<String> uploadLocalhost(UploadImagModel imageModel) throws LaiKeApiException {
        List<String> urls = new ArrayList<>();
        //转过来的文件不为空
        if (imageModel.getMultipartFiles() != null && imageModel.getMultipartFiles().size() > 0) {
            for (MultipartFile file : imageModel.getMultipartFiles()) {
                String url = "";
                //取到文件名
                String fileName = file.getOriginalFilename();
                if (DataCheckTool.checkUploadImgageFormate(fileName)) {
                    //处理图片重名
                    String imgName = BuilderIDTool.getSnowflakeId() + ImgUploadUtils.getUrlImgBySuffix(fileName);
                    try {
                        //服务器资源路径
                        String realPath = DEFAULT_UPLOAD_PATH;
                        //自定义目录
                        StringBuilder diyPath = new StringBuilder("");
                        if (StringUtils.isNotEmpty(imageModel.getPath())) {
                            diyPath.append(File.separator);
                            diyPath.append(imageModel.getPath());
                            diyPath.append(File.separator);
                            //判断文件夹是否存在,不存在则创建
                            File diyFile = new File(realPath + File.separator + diyPath);
                            if (!diyFile.exists() && !diyFile.isDirectory()) {
                                logger.debug("目录:{} 不存在,创建状态:{}", diyFile.getPath(), diyFile.mkdir());
                            }
                        }
                        url = imgName;
                        //拼接上传路径
                        String path = realPath + diyPath + imgName;
                        logger.debug("【本地上传】上传路径{}", path);
                        file.transferTo(new File(path));
                        url = path;
                    } catch (IOException e) {
                        logger.error(fileName + "图片上传失败" + e);
                        throw new LaiKeApiException(ErrorCode.SysErrorCode.UPLOAD_FAIL, "图片上传失败", "uploadLocalhost");
                    }
                }

                urls.add(url);
            }

        } else {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "uploadLocalhost");
        }
        return urls;
    }

    /**
     * 图片上传 - 腾讯云
     *
     * @param imageModel - 图片上传配置,imgName - 图片名称,file -文件流
     * @return List<String>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 16:42
     */
    private List<String> uploadTxy(UploadImagModel imageModel) throws LaiKeApiException {

        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "暂不支持腾讯云", "uploadLocalhost");
    }

    /**
     * 图片上传 - 七牛云
     *
     * @param imageModel - 图片上传配置,imgName - 图片名称,file -文件流
     * @return List<String>
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/9/29 16:42
     */
    private List<String> uploadQny(UploadImagModel imageModel) throws LaiKeApiException {


        throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "暂不支持七牛云", "uploadLocalhost");
    }


    /**
     * 获取配置数据
     *
     * @param uploadImagModel 文件配置参数
     * @return UploadImagModel
     * @author Trick
     * @date 2020/9/30 13:52
     */
    private UploadImagModel getConfigValue(UploadImagModel uploadImagModel) {
        if (uploadImagModel == null || uploadImagModel.getUploadConfigs() == null) {
            throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "getConfigValue");
        }
        //装载参数
        for (UploadConfigModel confg : uploadImagModel.getUploadConfigs()) {
            String attr = confg.getAttr();
            String attrVlaue = confg.getAttrvalue();
            switch (attr) {
                case GloabConst.UploadConfigConst.ACCESSKEYID:
                    uploadImagModel.setAccessKeyId(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.ACCESSKEYSECRET:
                    uploadImagModel.setAccessKeySecret(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.BUCKET:
                    uploadImagModel.setBucket(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.ENDPOINT:
                    uploadImagModel.setEndpoint(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.ISOPENZDY:
                    uploadImagModel.setIsopenzdy(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.IMAGESTYLE:
                    uploadImagModel.setImagestyle(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.UPLOADIMG_DOMAIN:
                    uploadImagModel.setUploadImgDomain(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.UPLOADIMG:
                    uploadImagModel.setUploadImg(attrVlaue);
                    break;
                case GloabConst.UploadConfigConst.MYENDPOINT:

                    break;
                default:
                    throw new LaiKeApiException(ErrorCode.BizErrorCode.PARAMATER_ERROR, "参数错误", "getConfigValue");
            }
        }
        return uploadImagModel;
    }

    /**
     * 获得url链接
     *
     * @param key        图片名称
     * @param ossClient  -
     * @param bucketName - 仓库名称
     * @return String
     */
    private String getUrl(String key, OSSClient ossClient, String bucketName) {
        // 设置URL过期时间为10年 3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }


    /**
     * 获取url中的图片名称
     *
     * @param imgUrl -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/11/18 15:44
     */
    @Deprecated
    public static String getUrlImgByName(String imgUrl) throws LaiKeApiException {
        try {
            return getUrlImgByName(imgUrl, true);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取图片名称失败", "getUrlImgByName");
        }
    }

    /**
     * 获取图片名称
     *
     * @param imgUrl  -
     * @param isParma -  是否去掉url参数
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021/5/20 14:04
     */
    public static String getUrlImgByName(String imgUrl, boolean isParma) throws LaiKeApiException {
        StringBuilder sb = new StringBuilder(imgUrl);
        try {
            if (StringUtils.isNotEmpty(imgUrl)) {
                int paramIndex = sb.indexOf("?");
                int nameLastIndex = sb.lastIndexOf("/");
                if (isParma) {
                    if (paramIndex > 0) {
                        //去掉多余的参数
                        sb.replace(paramIndex, sb.length(), "");
                        nameLastIndex = sb.lastIndexOf("/");
                    }
                } else {
                    int maxNum = 50;
                    int i = 0;
                    while (paramIndex > 0 && nameLastIndex > paramIndex) {
                        System.out.println(i);
                        //去掉参数中的 '/'
                        sb.replace(nameLastIndex, sb.length(), "");
                        nameLastIndex = sb.lastIndexOf("/");
                        if (i > maxNum) {
                            break;
                        }
                        i++;
                    }
                }
                imgUrl = sb.substring(nameLastIndex + 1, sb.length());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取图片名称失败", "getUrlImgByName");
        }
        return imgUrl;
    }


    /**
     * 净化url
     *
     * @param imgUrl  -
     * @param isParma -  是否去掉url参数
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2021-07-01 10:53:14
     */
    public static String getUrlPure(String imgUrl, boolean isParma) throws LaiKeApiException {
        StringBuilder sb = new StringBuilder(imgUrl);
        try {
            if (!StringUtils.isEmpty(imgUrl)) {
                if (isParma) {
                    if (imgUrl.indexOf("?") > 0) {
                        //去掉多余的参数
                        sb.replace(imgUrl.lastIndexOf("?"), imgUrl.length(), "");
                    }
                }
                imgUrl = sb.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取图片名称失败", "getUrlImgByName");
        }
        return imgUrl;
    }


    /**
     * 获取url中图片后缀
     *
     * @param imgUrl -
     * @return String
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/22 16:30
     */
    public static String getUrlImgBySuffix(String imgUrl) throws LaiKeApiException {
        try {
            if (!StringUtils.isEmpty(imgUrl)) {
                String imageName = getUrlImgByName(imgUrl);
                return imageName.substring(imageName.lastIndexOf("."));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "获取图片后缀失败", "getUrlImgBySuffix");
        }
        return null;
    }

    /**
     * 根据后缀获取 Mime
     *
     * @param suffix -
     * @return String
     * @throws LaiKeApiException-
     * @author Trick
     * @date 2020/12/22 16:36
     */
    public static String getUrlImgByMimeType(String suffix) throws LaiKeApiException {
        try {
            if (!StringUtils.isEmpty(suffix)) {
                FileNameMap fileNameMap = URLConnection.getFileNameMap();
                return fileNameMap.getContentTypeFor(suffix);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "根据后缀获取Mime失败", "getUrlImgBySuffix");
        }
        return null;
    }


}
