package com.starry.fastsky.annotation;

import java.lang.annotation.*;

/**
 * ClassName: FastController
 * Description: 路由注解
 * Author: starryfei
 * Date: 2019-01-11 11:27
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastController {
    String value() default "/";
}
