package com.laiketui.thinkinshop;

import com.google.common.io.Files;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Vector;

public class OpImages {

    static String[] paths = {
        "/Users/wangxian/Documents/HBuilderProjects/LaiKeJavaPagesOpen"
    };

    public static Vector<String> getAllFile(String datasetpath,Vector<String> vecFile){
        File file = new File(datasetpath);
        File[] subFile = file.listFiles();
        for (int i = 0; i < subFile.length; i++) {
            if (subFile[i].isDirectory()) {
                getAllFile(subFile[i].getAbsolutePath(),vecFile);
            } else {
                int finalI = i;
                file.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        if (name.contains(".vue")){
                            if(!vecFile.contains(subFile[finalI].getAbsolutePath())){
                                vecFile.add(subFile[finalI].getAbsolutePath());
                            }
                        }
                        return false;
                    }
                });
            }
        }
        return vecFile;
    }

    public static void main(String[] args) throws Exception {
        Vector<String> vecFile = new Vector<>();
        for (String path : paths) {
            getAllFile(path,vecFile);
        }

        BufferedReader bufferedReader = null;
        String line = null;
        Vector<String> rets  = new Vector<>();
        for (String s : vecFile) {
            bufferedReader = new BufferedReader(new FileReader(new File(s)));
            line = bufferedReader.readLine();
            while(line!=null){
                if((!line.contains("static/img")) &&
                        (!line.contains("unpackage")) &&
                        (!line.contains("laike.png")) &&
                        (!line.contains("openpage.png")) &&
                        (!line.contains("return")) &&
                        (!line.contains("}images/icon1/coupon_time.png) no-repeat")) &&
                        (!line.contains("guider.png")) &&
                        (!line.equals("images/icon1/jia")) &&
                        (!line.equals("'images/icon1/storeBottom.png'")) &&
                        (!line.equals("images/icon/")) &&
                        (!line.equals("images/icon1/zhifubaozhifu2x.png")) &&
                        (!line.equals("images/icon1/weixinzhifu2x.png")) &&
                        (line.contains("images/")) &&
                        (!line.contains("https://laikeds.")) &&
                        (!line.contains("https://xiaochengxu.houjiemeishi.com/V3/images/icon1/home_title_bg.png")) &&
                        (!line.contains("https://xiaochengxu.laiketui.com/V3")) &&
                        (!line.contains("-->")) &&
                        (line.contains(".png")||
                        line.contains(".PNG")||
                        line.contains(".JPG")||
                        line.contains(".GIF")||
                        line.contains(".bmp")||
                        line.contains(".BMP")||
                        line.contains(".tif")||
                        line.contains(".TIF")||
                        line.contains(".pcx")||
                        line.contains(".PCX")||
                        line.contains(".tga")||
                        line.contains(".TGA")||
                        line.contains(".exif")||
                        line.contains(".EXIF")||
                        line.contains(".fpx")||
                        line.contains(".FPX")||
                        line.contains(".svg")||
                        line.contains(".SVG")||
                        line.contains(".PSD")||
                        line.contains(".psd")||
                        line.contains(".cdr")||
                        line.contains(".CDR")||
                        line.contains(".pcd")||
                        line.contains(".PCD")||
                        line.contains(".dxf")||
                        line.contains(".DXF")||
                        line.contains(".ufo")||
                        line.contains(".UFO")||
                        line.contains(".eps")||
                        line.contains(".EPS")||
                        line.contains(".ai")||
                        line.contains(".AI")||
                        line.contains(".raw")||
                        line.contains(".RAW")||
                        line.contains(".WMF")||
                        line.contains(".webp")||
                        line.contains(".WEBP")||
                        line.contains(".avif")||
                        line.contains(".AVIF")||
                        line.contains(".jpg")||
                        line.contains(".gif"))){
                    if(!rets.contains(line)){
                        String[] rs = line.split("[+]");

                        if(rs.length>1){
                            String temp = rs[1].replaceAll("\\'","").replaceAll(",","").replaceAll(";","");
                            if (temp.indexOf("//")!=-1){
                                rets.add( temp.substring(0,temp.indexOf("//")).trim());
                            }else{
                                rets.add( temp.trim());
                            }
                        }else{
                            rets.add( line.trim());
                        }
                    }
                }
                line = bufferedReader.readLine();
            }
        }
        rets.add("images/icon1/coupon_bg1.png");
        rets.add("images/icon1/coupon_time.png");
        rets.add("images/icon1/jia+2x.png");
        rets.add("images/icon/add+2x.png");
        rets.add("images/icon1/zhifubaozhifu2x.png");
        rets.add("images/icon1/weixinzhifu2x.png");
        int i = 1;

        String img_pre_from = "/Users/wangxian/Documents/HBuilderProjects/svn/php/v3admin/";
        String img_to = "/Users/wangxian/Desktop/soft/thinkinshop/src/main/resources/static/";
        for (String ret : rets) {
//            System.out.println( i + " " + ret);
            File tofile = new File(img_to+ret);
            try {
                Files.copy(new File(img_pre_from+ret),tofile);
            }catch (Exception e){
                System.err.println(img_pre_from+ret);
            }
        }

//        System.out.println(vecFile);
    }

}
