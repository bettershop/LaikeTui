package com.laiketui.root.utils.algorithm;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Random;


/**
 * des3 加密/解密算法
 *
 * @author Trick
 * @date 2020/9/23 9:31
 */
public class CryptoUtil {

    private static final String DES3KEY = "www.laiketui.com";
    private static final String DES3STR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-=+";

    /**
     * 加密
     *
     * @param str
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String strEncode(String str) throws UnsupportedEncodingException {
        char[] chars = DES3STR.toCharArray();
        //随机生成0-64中的某一个数字
        Random random = new Random();
        int rand = random.nextInt(65);
        char ch = chars[rand];
        String mdkey = Objects.requireNonNull(Md5Util.MD5endoce(DES3KEY + ch)).toLowerCase();
        mdkey = mdkey.substring(rand % 8, rand % 8 + 11).toLowerCase();

        BASE64Encoder base64Encoder = new BASE64Encoder();
        str = base64Encoder.encode(str.getBytes());

        int i = 0;
        int j = 0;
        int k = 0;
        StringBuilder temp = new StringBuilder();
        char[] txtChars = str.toCharArray();
        char[] mdkeyChars = mdkey.toCharArray();
        for (i = 0; i < str.length(); i++) {
            k = k == mdkey.length() ? 0 : k;
            j = (rand + DES3STR.indexOf(txtChars[i]) + ord(mdkeyChars[k++])) % 64;
            temp.append(chars[j]);
        }

        return URLEncoder.encode(ch + temp.toString(), "UTF-8");
    }

    /**
     * 解密
     *
     * @return
     */
    public static String strDecode(String str) throws IOException {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            char[] chars = DES3STR.toCharArray();
            str = URLDecoder.decode(str, "utf-8");
            char ch = str.substring(0, 1).charAt(0);
            int num1 = DES3STR.indexOf(ch);
            String mdkey = Md5Util.MD5endoce(DES3KEY + ch);
            assert mdkey != null;
            mdkey = mdkey.substring(num1 % 8, num1 % 8 + 11).toLowerCase();
            str = str.substring(1, str.length());

            StringBuilder temp = new StringBuilder();
            int i = 0;
            int j = 0;
            int k = 0;
            char[] txtChars = str.toCharArray();
            char[] mdkeyChars = mdkey.toCharArray();

            for (i = 0; i < str.length(); i++) {
                k = k == mdkey.length() ? 0 : k;
                j = DES3STR.indexOf(txtChars[i]) - num1 - ord(mdkeyChars[k++]);
                while (j < 0) {
                    j += 64;
                }

                temp.append(chars[j]);
            }
            return new String(base64Decoder.decodeBuffer(String.valueOf(temp)), StandardCharsets.UTF_8);
        } catch (StringIndexOutOfBoundsException e) {
            return null;
        }
    }

    public static int ord(String s) {
        return s.length() > 0 ? (s.getBytes(StandardCharsets.UTF_8)[0] & 0xff) : 0;
    }

    public static int ord(char c) {
        return c < 0x80 ? c : ord(Character.toString(c));
    }

    public static void main(String[] args) throws IOException {
        String str = "qpfQAnyn8";
        String str1 = "xiaoqi123";
        String encoder = strEncode(str1);
        System.out.println(encoder);
        String decoder = strDecode(str);
        System.out.println(decoder);
    }
}
