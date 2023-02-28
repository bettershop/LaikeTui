package com.laiketui.root.gateway.util;

import com.alibaba.druid.util.StringUtils;
import com.laiketui.root.utils.common.SplitUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class I18nUtils {

    @Value("${spring.messages.basenames}")
    private String basenames;
    @Value("${spring.messages.encoding}")
    private String encoding;
    private static MessageSource messageSource;
    /**
     *  国际化前缀 统一使用laike.tips.
     */
    private final static String TIP_KEY_PRE = "laike.tips.";

    private final ReloadableResourceBundleMessageSource bundleMessageSource = new ReloadableResourceBundleMessageSource();

    @PostConstruct
    public void initMessageSource() {

        if(!StringUtils.isEmpty(basenames)){
            bundleMessageSource.setBasenames(basenames.split(SplitUtils.DH));
        }else{
            bundleMessageSource.setBasenames("/i18n/tips");
        }

        bundleMessageSource.setDefaultEncoding(encoding);
        bundleMessageSource.setCacheSeconds(10);
        bundleMessageSource.setDefaultEncoding(encoding);
        messageSource = bundleMessageSource;
    }

    public static String getMessage(String key)  {
        // 通过国际化文件的key获取值
        String tips = null;
        try{
            tips = messageSource.getMessage(TIP_KEY_PRE + key, null, LangUtils.getLocale());
        }catch (NoSuchMessageException exception){
            //如果没有key则直接报错
            exception.printStackTrace();
            tips = null;
        }

        return tips;
    }





}
