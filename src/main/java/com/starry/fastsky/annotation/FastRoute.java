package com.starry.fastsky.annotation;

import com.starry.fastsky.enums.Response;

import java.lang.annotation.*;

/**
 * ClassName: FastRoute
 * Description: 路由注解
 * Author: starryfei
 * Date: 2019-01-11 10:43
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastRoute {
    String path() default "";
    Response type() default Response.TEXT;
}
