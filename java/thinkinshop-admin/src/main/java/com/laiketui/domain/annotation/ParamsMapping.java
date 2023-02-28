package com.laiketui.domain.annotation;

import java.lang.annotation.*;

/**
 * php：参数映射
 */
@Inherited
@Target({ ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ParamsMapping {

    /**
     * 参数名(别名)列表
     */
    String[] value();
}
