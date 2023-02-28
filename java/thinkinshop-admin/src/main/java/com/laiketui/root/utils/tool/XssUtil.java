package com.laiketui.root.utils.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * xss防范规则
 *
 * @author Trick
 * @date 2020/10/28 14:39
 */
public class XssUtil {
    private static Pattern[] patterns = new Pattern[]{
            // Script fragments
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            // src='...'
            Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // lonely script tags
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // eval(...)
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // expression(...)
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // javascript:...
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            // vbscript:...
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            // 空格英文单双引号
            Pattern.compile("[\\s\'\"]+", Pattern.CASE_INSENSITIVE),
            // onload(...)=...
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            // alert
            Pattern.compile("alert(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile("<", Pattern.MULTILINE | Pattern.DOTALL),
            Pattern.compile(">", Pattern.MULTILINE | Pattern.DOTALL),
            //Checks any html tags i.e. <script, <embed, <object etc.
            Pattern.compile("(<(script|iframe|embed|frame|frameset|object|img|applet|body|html|style|layer|link|ilayer|meta|bgsound))")
    };

    /**
     * xss替换函数
     *
     * @param value 需要替换的字符
     * @return 替换后的字符
     */
    public static String stripXSS(String value) {
        if (value != null) {
            value = value.replaceAll("\0", "");

            for (Pattern scriptPattern : patterns) {
                value = scriptPattern.matcher(value).replaceAll("");
            }
        }
        return value;
    }

    /**
     * xss校验函数
     *
     * @param value 需要校验的字符
     * @return 返回值：true 表示存在xss漏洞，false：不存在
     */
    public static boolean checkIsXSS(String value) {
        boolean isXss = false;
        if (value != null) {
            for (Pattern scriptPattern : patterns) {
                Matcher matcher = scriptPattern.matcher(value);
                if (matcher.find()) {
                    isXss = true;
                    break;
                }
            }
        }
        return isXss;
    }

    public static void main(String[] args) {
        String str = "这是正常字符";
        boolean result = XssUtil.checkIsXSS(str);
        System.out.println(str + " 是否包含XSS字符：" + result);

        String str2 = "这是xss字符\'\"<script>alert(111111)</script>";
        boolean result2 = XssUtil.checkIsXSS(str2);
        System.out.println(str2 + " 是否包含XSS字符：" + result2);

        System.out.println(XssUtil.stripXSS(str2));
    }
}
