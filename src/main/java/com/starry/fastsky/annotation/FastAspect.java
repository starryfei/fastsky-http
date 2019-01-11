package com.starry.fastsky.annotation;


import java.lang.annotation.*;

/**
 * ClassName: FastAspect
 * Description: TODO
 *
 * @author: starryfei
 * @date: 2019-01-11 18:04
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastAspect {
    int order() default 1;
}
