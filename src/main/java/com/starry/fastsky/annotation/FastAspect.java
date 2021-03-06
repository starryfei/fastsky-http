package com.starry.fastsky.annotation;


import java.lang.annotation.*;

/**
 * ClassName: FastAspect
 * Description: 切面注解
 *
 * @author: starryfei
 * @date: 2019-01-11 18:04
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FastAspect {
    String[] value() default {};
}
