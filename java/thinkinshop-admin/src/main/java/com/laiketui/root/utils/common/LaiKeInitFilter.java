package com.laiketui.root.utils.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

public class LaiKeInitFilter implements TypeFilter {

    private  final Logger logger = LoggerFactory.getLogger(LaiKeInitFilter.class);

    private ClassMetadata classMetadata;

    /**
     * MetadataReader:读取到当前正在扫描类的信息
     * MetadataReaderFactory:可以获取到其他任何类信息
     */
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前正在扫描的类信息
        classMetadata = metadataReader.getClassMetadata();
        //获取当前类资源(类的路径)
        String className = classMetadata.getClassName();
        if(className.contains("com.laiketui")){
            if (className.contains("com.laiketui.root.common")
            ||className.contains("com.laiketui.root.license")
            ||className.contains("com.laiketui.root.gateway.dto")
            ||className.contains("com.laiketui.root.gateway.util.APIServiceUtils")
            ||className.contains("com.laiketui.root.gateway.util.LaiKeParamsUtils")
            ||className.contains("com.laiketui.root.gateway.util.MethodUtil")
            ||className.contains("com.laiketui.root.utils.tool.InvokerUtils")
            ){
                logger.info("##,{}" ,className);
                return true;
            }
        }
        //excludeFilters返回true---->会被过滤掉
        //includeFilters返回true---->会通过
        return false;
    }
}

