package com.laiketui.root.utils.tool;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音汉字互转
 *
 * @author Trick
 * @date 2020/10/15 10:10
 */
public class PinyinUtils {

    public static void main(String[] args) {
        String py = getPinYin("张学友");
        String pyHead = getPinYinHeadChar("刘德华");
        String cnAscii = getCnASCII("黎明");

        System.out.println(py);
        System.out.println(pyHead);
        System.out.println(cnAscii);
    }

    /**
     * 将汉字转换为全拼
     *
     * @param src -
     * @return String
     */
    public static String getPinYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        // 设置汉字拼音输出的格式
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder t4 = new StringBuilder();
        int t0 = t1.length;
        try {
            for (char c : t1) {
                // 判断是否为汉字字符
                if (Character.toString(c).matches("[\\u4E00-\\u9FA5]+")) {
                    // 将汉字的几种全拼都存到t2数组中
                    t2 = PinyinHelper.toHanyuPinyinStringArray(c, t3);
                    // 取出该汉字全拼的第一种读音并连接到字符串t4后
                    t4.append(t2[0]);
                } else {
                    // 如果不是汉字字符，直接取出字符并连接到字符串t4后
                    t4.append(Character.toString(c));
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return t4.toString();
    }

    /**
     * 提取每个汉字的首字母
     *
     * @param str -
     * @return String
     */
    public static String getPinYinHeadChar(String str) {
        StringBuilder convert = new StringBuilder();
        for (int j = 0; j < str.length(); j++) {
            char word = str.charAt(j);
            // 提取汉字的首字母
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
            if (pinyinArray != null) {
                convert.append(pinyinArray[0].charAt(0));
            } else {
                convert.append(word);
            }
        }
        return convert.toString();
    }

    /**
     * 将字符串转换成ASCII码
     *
     * @param cnStr -
     * @return String
     */
    public static String getCnASCII(String cnStr) {
        StringBuilder strBuf = new StringBuilder();
        // 将字符串转换成字节序列
        byte[] bgbk= cnStr.getBytes();
        for (byte b : bgbk) {
            // 将每个字符转换成ASCII码
            strBuf.append(Integer.toHexString(b & 0xff)).append(" ");
        }
        return strBuf.toString();
    }
}
