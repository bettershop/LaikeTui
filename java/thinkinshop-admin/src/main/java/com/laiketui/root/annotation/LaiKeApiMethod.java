package com.laiketui.root.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/15 16:51
 * @version: 1.0
 * @modified By:
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LaiKeApiMethod {

    //接口keyvalue
    String apiKey();
    //是否需要登陆
    boolean login() default false;
    //版本号
    String version() default "";
    //分组
    String group() default "";
    //模块
    String module() default "";
    //协议
    String protocol() default "dubbo";
    /**超时时间*/
    int timeOut() default 8000;
}
