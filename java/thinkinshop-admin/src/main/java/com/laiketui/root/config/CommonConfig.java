package com.laiketui.root.config;

//import com.laiketui.root.handler.PhpAliasModelAttributeMethodProcessor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Administrator
 */
@Configuration
public class CommonConfig {

    /**
     * dubbo元数据上报
     *
     * @return
     */
//    @Bean
//    public MetadataReportConfig metadataReportConfig() {
//        MetadataReportConfig metadataReportConfig = new MetadataReportConfig();
//        metadataReportConfig.setAddress("zookeeper://127.0.0.1:2181");
//        return metadataReportConfig;
//    }

    /*@Bean
    protected PhpAliasModelAttributeMethodProcessor processor() {
        return new PhpAliasModelAttributeMethodProcessor(true);
    }*/

    @Bean
    public static PropertySourcesPlaceholderConfigurer swaggerProperties() {
        PropertySourcesPlaceholderConfigurer p = new PropertySourcesPlaceholderConfigurer();
        p.setIgnoreUnresolvablePlaceholders(true);
        return p;
    }



}
