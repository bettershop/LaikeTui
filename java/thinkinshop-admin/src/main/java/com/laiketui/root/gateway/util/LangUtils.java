package com.laiketui.root.gateway.util;

import java.util.Locale;

public class LangUtils {
    private static String language="zh";
    private static String country="CN";

    public static void setLanguage(String language) {
        LangUtils.language = language;
        LangUtils.country = getCountry();
    }

    private static String getCountry() {
        return "en".equals(language) ? "US" : "CN";
    }

    public static Locale getLocale() {
        return new Locale(language, country);
    }
}
