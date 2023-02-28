package com.laiketui.root.utils.tool;

import com.laiketui.root.consts.ErrorCode;
import com.laiketui.root.exception.LaiKeApiException;
import org.iherus.codegen.Codectx;
import org.iherus.codegen.qrcode.QrcodeConfig;
import org.iherus.codegen.qrcode.QreyesFormat;
import org.iherus.codegen.qrcode.SimpleQrcodeGenerator;
import sun.font.FontDesignMetrics;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 图片处理工具
 *
 * @author Trick
 * @date 2020/12/24 15:22
 */
public class ImageTool {

    /**
     * 生成一张底图
     *
     * @param imgBackGroundX - 宽度
     * @param imgBackGroundY - 长度
     * @param color          - 颜色
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/18 13:55
     */
    public static BufferedImage builderBaseMap(int imgBackGroundX, int imgBackGroundY, Color color) throws LaiKeApiException {
        try {
            //生成底图
            BufferedImage mainImage = new BufferedImage(imgBackGroundX, imgBackGroundY, BufferedImage.TYPE_INT_RGB);
            Graphics2D graphics2D = mainImage.createGraphics();
            graphics2D.setBackground(color);
            graphics2D.fillRect(0, 0, imgBackGroundX, imgBackGroundY);
            graphics2D.dispose();
            return mainImage;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片加水印失败", "imageWatermark");
        }
    }

    /**
     * 图片加水印
     *
     * @param image - 图片流
     * @param text  - 文字
     * @param x     - x坐标
     * @param y     - y坐标
     * @param color - 颜色
     * @param font  - 自提
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/18 13:55
     */
    public static void imageWatermark(BufferedImage image, String text, int x, int y, Color color, Font font) throws LaiKeApiException {
        try {
            //从原图中找出一片区域来显示水印文本
            Graphics2D g = image.createGraphics();
            //字体、字体大小，透明度，旋转角度
            g.setFont(font);
            char[] data = text.toCharArray();
            g.rotate(0);
            g.setColor(color);
            //设置文本显示坐标
            g.drawChars(data, 0, data.length, x, y);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "图片加水印失败", "imageWatermark");
        }
    }

    /**
     * 计算字体间距
     *
     * @return int -
     * @author Trick
     * @date 2021/3/23 18:39
     */
    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    public static void main(String[] args) {
        Font font1 = new Font("微软雅黑", Font.PLAIN, 26);
        System.out.println(getWordWidth(font1, "99999999") + "99999999".length() * 11);
        System.out.println(getWordWidth(font1, "字字字字字字字字"));
    }

    /**
     * 计算字体高度
     *
     * @return int -
     * @author Trick
     * @date 2021/3/23 18:39
     */
    public static int getWordHeight(Font font) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.getHeight();
    }

    /**
     * 获取文字集合,每个下标都代表一行
     *
     * @param font       -
     * @param content    -
     * @param maxLineNum -当前行支持多少个像素
     * @return int -
     * @author Trick
     * @date 2021/3/23 18:39
     */
    public static List<String> getWord(Font font, String content, int maxLineNum) {
        List<String> list = new ArrayList<>();
        //当前字符串所占像素长度
        int titleFontX = ImageTool.getWordWidth(font, content);
        //获取一个字像素多少
        int pixelNum = ImageTool.getWordWidth(font, "字");
        //超出了多少像素
        int outNum = titleFontX - maxLineNum;
        if (outNum > 0) {
            //一行多少个字
            int lineNum = maxLineNum / pixelNum;
            //需要几行
            int line = content.length() / lineNum + 1;
            for (int i = 0; i < line; i++) {
                if (content.length() >= lineNum) {
                    list.add(content.substring(0, lineNum));
                    //更新字符串
                    content = content.substring(lineNum);
                } else {
                    list.add(content);
                }
            }
        } else {
            list.add(content);
        }
        return list;
    }

    /**
     * 生成二维码
     *
     * @param logo - 码眼 可选
     * @param data - 二维码内容
     * @param path - 保存路径
     * @return File -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/24 16:34
     */
    public static File builderQrcode(String logo, String data, String path) throws LaiKeApiException {
        try {
            DataCheckTool.checkUploadImgageFormate(path);
            if (new SimpleQrcodeGenerator().setRemoteLogo(logo).generate(data).toFile(path)) {
                return new File(path);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "生成二维码失败", "builderQrcode");
        }
        return null;
    }

    /**
     * 生成二维码
     *
     * @param logo  - 码眼 可选
     * @param style - 二维码样式
     * @param data  - 二维码内容
     * @param path  - 保存路径
     * @return File -
     * @throws LaiKeApiException -
     * @author Trick
     * @date 2020/12/24 16:34
     */
    public static File builderQrcode(String logo, QrcodeConfig style, String data, String path) throws LaiKeApiException {
        try {
            DataCheckTool.checkUploadImgageFormate(path);
            if (style == null) {
                style = new QrcodeConfig()
                        .setBorderSize(1)
                        .setPadding(10)
                        .setLogoBorderColor("#FFA07A")
                        .setLogoShape(Codectx.LogoShape.CIRCLE)
                        .setCodeEyesFormat(QreyesFormat.DR2_BORDER_C_POINT);
            }
            if (new SimpleQrcodeGenerator(style).setRemoteLogo(logo).generate(data).toFile(path)) {
                return new File(path);
            }
        } catch (LaiKeApiException l) {
            throw l;
        } catch (Exception e) {
            e.printStackTrace();
            throw new LaiKeApiException(ErrorCode.BizErrorCode.BUSY_NETWORK, "生成二维码失败", "builderQrcode");
        }
        return null;
    }

    /**
     * hex转rgv
     *
     * @param hex - #FFFFFF
     * @return Color
     * @author Trick
     * @date 2021/9/24 20:00
     */
    public static Color hexToRgb(String hex) {
        return new Color(
                Integer.valueOf(hex.substring(1, 3), 16),
                Integer.valueOf(hex.substring(3, 5), 16),
                Integer.valueOf(hex.substring(5, 7), 16)
        );
    }


    /*public static void main(String[] args) throws IOException {
        String content = "https://www.baidu.com/";
        String logoUrl = "https://laikeds.oss-cn-shenzhen.aliyuncs.com/1/0/1607075227573.jpeg";

        QrcodeConfig config = new QrcodeConfig()
                .setBorderSize(1)
                .setPadding(10)
                .setLogoBorderColor("#FFA07A")
                .setLogoShape(Codectx.LogoShape.CIRCLE)
                .setCodeEyesFormat(QreyesFormat.DR2_BORDER_C_POINT);

        new SimpleQrcodeGenerator(config).setRemoteLogo(logoUrl).generate(content).toFile("D:\\123.jpg");
    }*/

}
