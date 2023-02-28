package com.laiketui.root.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: wx
 * @date: Created in 2019/10/26 14:17
 * @version: 1.0
 * @modified By:
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpApiMethod {
    /**接口keyvalue*/
    String apiKey() default "" ;
    /**是否需要登陆*/
    boolean login() default false;
    /**版本号*/
    String version() default "1.0.0";
    /**模块*/
    String module() default "laike-app";
    /**所在服务服务*/
    @Deprecated
    String uri() default "http://localhost:8080/" ;
    /**url映射*/
    String[] urlMapping() default "" ;
    /**超时时间*/
    int timeOut() default 8000;
}
